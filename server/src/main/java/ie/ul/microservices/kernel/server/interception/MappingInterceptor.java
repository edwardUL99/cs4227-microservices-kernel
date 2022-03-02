package ie.ul.microservices.kernel.server.interception;

import org.springframework.http.ResponseEntity;

/**
 * This interface represents the interceptor for mapping requests to the appropriate microservice
 */
public interface MappingInterceptor {
    /**
     * This interception point is called before the mapping takes place
     * @param context the object holding information relating to mapping the request
     * @param chain the chain interceptor in the chain, this should be called for processing to continue. To ensure
     * mapping completes, call chain.next(context). If chain.next(context) is not called, the mapping will not take place. If doing this,
     * you should set a meaningful response on the context using {@link MappingContext#setResponse(ResponseEntity)}. Do the same if calling
     * {@link MappingInterceptorChain#terminate(MappingContext)}
     */
    void onBeforeMapping(MappingContext context, MappingInterceptorChain chain);

    /**
     * This interception point is called after the mapping takes place
     * @param context the object holding information relating to mapping the request
     * @param chain the chain interceptor in the chain, this should be called for processing to continue. To ensure
     * mapping completes, call chain.next. If chain.next(context) is not called, mapping will be terminated. If doing this,
     * you should set a meaningful response on the context using {@link MappingContext#setResponse(ResponseEntity)}. Do the same if calling
     * {@link MappingInterceptorChain#terminate(MappingContext)}
     */
    void onAfterMapping(MappingContext context, MappingInterceptorChain chain);
}
