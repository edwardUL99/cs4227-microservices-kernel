package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.server.interception.MappingContext;
import ie.ul.microservices.kernel.server.interception.MappingContextFactory;
import ie.ul.microservices.kernel.server.interception.MappingDispatcher;
import ie.ul.microservices.kernel.server.mapping.MappingException;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.models.URL;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * This class provides the default implementation for the MappingService
 */
@Service("Mapping")
public class MappingServiceImpl implements MappingService {
    /**
     * The current context object for the mapping
     */
    private MappingContext context;
    /**
     * The dispatcher instance
     */
    private final MappingDispatcher dispatcher;
    /**
     * The registry to find microservices
     */
    private final Registry registry;
    /**
     * The factory for creating contexts
     */
    private static final MappingContextFactory contextFactory = new MappingContextFactory();

    /**
     * Create the mapping service and initialise the dispatcher
     */
    @Autowired
    public MappingServiceImpl(Registry registry) {
        MappingDispatcher.initialise(this);
        this.dispatcher = MappingDispatcher.getInstance();
        this.registry = registry;
    }

    /**
     * This method receives the context from the interceptor chain
     *
     * @param context the context passed through the chain. May be processed by any of the interceptors
     *                along the chain
     */
    @Override
    public void consumeContext(MappingContext context) {
        this.context = context;
    }

    /**
     * Dispatch the context to the consumer action and then return the returned context
     * @param context the context to dispatch
     * @param action the dispatch action
     * @return the context after dispatch completes, or null if it didn't finish the chain or terminated
     */
    private MappingContext dispatch(MappingContext context, Consumer<MappingContext> action) {
        action.accept(context);

        if (this.context == null || this.context.terminated()) {
            return null;
        } else {
            if (!this.context.equals(context)) {
                context = this.context;
            }

            this.context = null;

            return context;
        }
    }

    /**
     * Dispatches the before mapping event
     * @param context the context to dispatch
     * @return the context after it has been passed to registered interceptors
     */
    private MappingContext dispatchBeforeMapping(MappingContext context) {
        return dispatch(context, dispatcher::onBeforeMapping);
    }

    /**
     * Dispatches the context to the after mapping interception points
     * @param context the context to dispatch
     * @return the context after dispatching, or null if it was terminated
     */
    private MappingContext dispatchAfterMapping(MappingContext context) {
        return dispatch(context, dispatcher::onAfterMapping);
    }

    /**
     * Map the url from the microservice and the request
     * @param microservice the microservice to map the request from
     * @param request the request to map
     */
    private URL mapURLFromMicroservice(Microservice microservice, HttpServletRequest request) {
        URL url = URL.fromParameters(request.getScheme(), microservice.getHost(), microservice.getPort(), request.getRequestURI(), request.getQueryString());
        String[] bodyParts = url.getBodyParts();

        if (bodyParts.length > 0) {
            bodyParts = Arrays.copyOfRange(bodyParts, 1, bodyParts.length);
        }

        url.setBody(String.join("/", bodyParts));

        return url;
    }

    /**
     * If the microservice has not been mapped by a pre=mapping interceptor, use this method to map the microservice
     * name and find it
     * @param result the result being constructed
     * @param request the request being mapped
     */
    private void mapAndFindMicroservice(MappingResult result, HttpServletRequest request) {
        URL url = URL.fromServletRequest(request);
        String[] bodyParts = url.getBodyParts();

        if (bodyParts.length == 0) {
            throw new MappingException("No microservice name provided in the request URL");
        } else {
            String microserviceName = bodyParts[0];
            String[] bodyPartsNoName = Arrays.copyOfRange(bodyParts, 1, bodyParts.length);

            Microservice microservice = this.registry.getMicroservice(microserviceName);

            if (microservice != null && microservice.isHealthy()) {
                url = URL.fromParameters(url.getScheme(), microservice.getHost(), microservice.getPort(),
                        String.join("/", bodyPartsNoName), url.getQueryParams());
            } else {
                url.setHostname(null);
                url.setPort(0); // we don't know what to map to
            }

            result.setUrl(url);
            result.setMicroservice(microservice);
        }
    }

    /**
     * Do the mapping based on the given context
     * @param context the context to map from
     * @param result the result being constructed
     */
    private void doMap(MappingContext context, MappingResult result) {
        HttpServletRequest request = context.getRequest();
        Microservice mapped = context.getMicroservice();

        if (mapped != null) {
            URL url = mapURLFromMicroservice(mapped, request);
            result.setUrl(url);
            result.setMicroservice(mapped);
        } else {
            mapAndFindMicroservice(result, request);
        }

        context.setMicroservice(result.getMicroservice());
    }

    /**
     * Map the request to the microservice instance
     *
     * @param request the request to map
     * @return the mapped microservice. Null if no microservice can be found
     * @throws MappingException if an error occurs during mapping.
     */
    @Override
    public MappingResult mapRequest(HttpServletRequest request) throws MappingException {
        MappingResult result = new MappingResult();
        MappingContext context = contextFactory.createContext(request);

        context = dispatchBeforeMapping(context); // we are at the before mapping interception point, so invoke the interceptors here

        if (context == null) {
            // chain ended without terminating or context was terminated
            result.setTerminated(true);
        } else {
            doMap(context, result); // performs the mapping
            context = dispatchAfterMapping(context); // this is the after mapping interception point, so invoke the interceptors

            if (context == null) {
                result.setTerminated(true);
            } else {
                result.setMicroservice(context.getMicroservice());
            }
        }

        return result;
    }
}
