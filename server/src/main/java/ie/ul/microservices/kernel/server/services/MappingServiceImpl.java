package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.api.interception.mapping.DispatcherContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingDispatcher;
import ie.ul.microservices.kernel.server.Constants;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.server.interception.mapping.MappingContextFactory;
import ie.ul.microservices.kernel.server.interception.mapping.MappingDispatcherImpl;

import ie.ul.microservices.kernel.server.mapping.MappingException;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import ie.ul.microservices.kernel.server.models.CurrentRequest;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.models.URL;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * This class provides the default implementation for the MappingService
 */
@Service("Mapping")
public class MappingServiceImpl implements MappingService {
    /**
     * The current request
     */
    @Resource(name = "currentRequest")
    private CurrentRequest currentRequest;
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
        this.dispatcher = new MappingDispatcherImpl(this::consumeContext);
        DispatcherContext.setDispatcher(this.dispatcher);
        this.registry = registry;
    }

    /**
     * This method receives the context from the interceptor chain
     *
     * @param context the context passed through the chain. May be processed by any of the interceptors
     *                along the chain
     */
    private void consumeContext(MappingContext context) {
        this.currentRequest.setContext(context);
    }

    /**
     * Dispatch the context to the consumer action and then return the returned context
     * @param context the context to dispatch
     * @param action the dispatch action
     * @return the context after dispatch completes, or null if it didn't finish the chain or terminated
     */
    private MappingContext dispatch(MappingContext context, Consumer<MappingContext> action) {
        action.accept(context);

        MappingContext currentContext = this.currentRequest.getContext();

        if (currentContext == null && context != null) {
            context.terminate();

            return context;
        } else if (currentContext != null){
            if (!currentContext.equals(context)) {
                context = currentContext;
            }

            this.currentRequest.setContext(null);

            return context;
        } else {
            return null;
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
        URL url = URL.fromParameters(request.getScheme(), microservice.getHost(), microservice.getPort(),
                Constants.removeGatewayURL(request.getRequestURI()), request.getQueryString());
        String[] bodyParts = url.getBodyParts();

        if (bodyParts.length > 0) {
            bodyParts = Arrays.copyOfRange(bodyParts, 1, bodyParts.length);
        }

        url.setBody(Constants.joinURL(bodyParts));

        return url;
    }

    /**
     * If the microservice has not been mapped by a pre=mapping interceptor, use this method to map the microservice
     * name and find it
     * @param result the result being constructed
     * @param context the mapping context
     */
    private void mapAndFindMicroservice(MappingResult result, MappingContext context) {
        URL url = context.getURL();

        if (url == null) {
            url = URL.fromServletRequest(context.getRequest());
            context.setURL(url);
        }

        String[] bodyParts = url.getBodyParts();

        if (bodyParts.length == 0) {
            throw new MappingException("No microservice name provided in the request URL");
        } else {
            String microserviceName = bodyParts[0];
            String[] bodyPartsNoName = Arrays.copyOfRange(bodyParts, 1, bodyParts.length);
            String newBody = Constants.joinURL(bodyPartsNoName);

            Microservice microservice = this.registry.getMicroservice(microserviceName);

            if (microservice != null && microservice.isHealthy()) {
                url = URL.fromParameters(url.getScheme(), microservice.getHost(), microservice.getPort(),
                        newBody, url.getQueryParams());
            } else {
                url.setHostname(null);
                url.setPort(0); // we don't know what to map to
                url.setBody(newBody);
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
        Microservice mapped = context.getMicroservice();

        if (mapped != null) {
            URL url = mapURLFromMicroservice(mapped, context.getRequest());
            context.setURL(url);
            result.setUrl(url);
            result.setMicroservice(mapped);
        } else {
            mapAndFindMicroservice(result, context);
            context.setURL(result.getUrl());
            context.setMicroservice(result.getMicroservice());
        }
    }

    /**
     * This method merges the result from an after mapping interceptor with the context
     * @param result the result to merge the context into
     * @param context the context to merge into the result
     */
    private void mergeResultAfterInterceptor(MappingResult result, MappingContext context) {
        Microservice contextService = context.getMicroservice();
        Microservice resultService = result.getMicroservice();
        URL contextURL = context.getURL();

        if (contextURL == null || contextService == null) {
            context.setURL(URL.fromServletRequest(context.getRequest()));
        }

        if (!result.getUrl().equals(contextURL)) {
            result.setUrl(contextURL);
        }

        if (contextService != null && !contextService.equals(resultService)) {
            result.setMicroservice(contextService);
        }

        ResponseEntity<?> response = context.getResponse();

        if (response != null)
            result.setResponse(response);

        result.setRequest(context.getRequest());
    }

    /**
     * Performs the pre-mapping processing
     * @param result the result being processed
     * @param context the mapping context after pre-mapping, null if mapping has ended and context is no longer available
     */
    private MappingContext preMapping(MappingResult result, MappingContext context) {
        context = dispatchBeforeMapping(context); // we are at the before mapping interception point, so invoke the interceptors here

        if (context == null || context.terminated()) {
            // chain ended without terminating or context was terminated
            result.setTerminated(true);
        } else {
            result.setRequest(context.getRequest());
        }

        if (context != null)
            result.setResponse(context.getResponse());

        return context;
    }

    /**
     * Performs the post mapping processing
     * @param result the result being processed
     * @param context the mapping context
     */
    private void postMapping(MappingResult result, MappingContext context) {
        context = dispatchAfterMapping(context); // this is the after mapping interception point, so invoke the interceptors

        if (context == null|| context.terminated()) {
            // chain ended without terminating or context was terminated
            result.setTerminated(true);

            if (context != null)
                result.setResponse(context.getResponse());
        } else {
            mergeResultAfterInterceptor(result, context);
        }
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

        context = preMapping(result, context);

        if (context != null && !context.terminated()) {
            doMap(context, result);
            postMapping(result, context);
        }

        return result;
    }
}
