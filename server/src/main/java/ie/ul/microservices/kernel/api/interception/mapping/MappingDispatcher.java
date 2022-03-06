package ie.ul.microservices.kernel.api.interception.mapping;

/**
 * This interface represents the API reference for the dispatcher registration and callback interface.
 * All interceptors implementing the
 *  * {@link MappingInterceptor} interface should be registered through this dispatcher.
 */
public interface MappingDispatcher {
    /**
     * Register the interceptor with the dispatcher
     * @param interceptor the interceptor to register
     * @param strategy the token identifying what the interceptor is being registered for
     */
    void registerMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy);

    /**
     * Unregister the interceptor that was registered with the provided strategy
     * @param interceptor the interceptor to remove from the dispatcher
     * @param strategy the strategy used to register the interceptor
     */
    void unregisterMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy);

    /**
     * Clears all registered interceptors
     */
    void clearInterceptors();

    /**
     * Dispatches the context to the onBeforeMapping interceptor chain
     * @param context the context to dispatch
     */
    void onBeforeMapping(MappingContext context);

    /**
     * Dispatches the context to the onAfterMapping interceptor chain
     * @param context the context to dispatch
     */
    void onAfterMapping(MappingContext context);

    /**
     * This enum is used to determine the events the interceptor should be registered for
     */
    enum RegistrationStrategy {
        /**
         * This interceptor is being registered to only handle before mapping events
         */
        BEFORE,
        /**
         * This interceptor is being registered to only handle after mapping events
         */
        AFTER,
        /**
         * This interceptor is being registered to handle both mapping events
         */
        ALL
    }
}
