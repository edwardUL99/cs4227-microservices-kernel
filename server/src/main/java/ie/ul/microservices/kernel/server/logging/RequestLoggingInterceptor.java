package ie.ul.microservices.kernel.server.logging;

import ie.ul.microservices.kernel.api.interception.mapping.AllMappingInterceptor;
import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptorChain;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.models.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * This interceptor logs requests pre- and post-mapping
 */
@Component
@DependsOn("Interception")
public class RequestLoggingInterceptor extends AllMappingInterceptor {
    /**
     * The log instance to log requests
     */
    private final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    /**
     * This interception point is called before the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue. To ensure
     *                mapping completes, call chain.next(context). If chain.next(context) is not called, the mapping will not take place. If doing this,
     *                you should set a meaningful response on the context. Do the same if calling
     *                {@link MappingInterceptorChain#terminate(MappingContext)}
     */
    @Override
    public void onBeforeMapping(MappingContext context, MappingInterceptorChain chain) {
        log.info("BeforeMapping: Initial URL: {}, From: {}", context.getURL(), context.getRequest().getRemoteAddr());
        chain.next(context);
    }

    /**
     * This interception point is called after the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue. To ensure
     *                mapping completes, call chain.next. If chain.next(context) is not called, mapping will be terminated. If doing this,
     *                you should set a meaningful response on the context. Do the same if calling
     *                {@link MappingInterceptorChain#terminate(MappingContext)}
     */
    @Override
    public void onAfterMapping(MappingContext context, MappingInterceptorChain chain) {
        Microservice microservice = context.getMicroservice();

        if (microservice != null) {
            log.info("AfterMapping: Routed URL: {}, Microservice Name: {}, Microservice ID: {}", context.getURL(), microservice.getMicroserviceName(), microservice.getMicroserviceID());
        } else {
            URL url = URL.fromServletRequest(context.getRequest());
            String microserviceName = url.getBodyParts()[0];
            log.error("AfterMapping: Failed to route request as Microservice {} could not be found for URL: {}", microserviceName, url);
        }

        chain.next(context);
    }
}
