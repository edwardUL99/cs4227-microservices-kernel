package ie.ul.microservices.kernel.server.interception;

/**
 * This interface represents the interceptor for mapping requests to the appropriate microservice
 */
public interface MappingInterceptor {
    /**
     * This interception point is called before the mapping takes place
     * @param context the object holding information relating to mapping the request
     * @param next the next interceptor in the chain, this should be called for processing to continue
     */
    void onBeforeMapping(MappingContext context, MappingInterceptorChain next);

    /**
     * This interception point is called after the mapping takes place
     * @param context the object holding information relating to mapping the request
     * @param next the next interceptor in the chain, this should be called for processing to continue
     */
    void onAfterMapping(MappingContext context, MappingInterceptorChain next);
}
