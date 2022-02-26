package ie.ul.microservices.kernel.server.interception;

import java.util.function.Consumer;

/**
 * This class represents the dispatcher to dispatch to mapping interceptors
 */
public class MappingDispatcher {
    /**
     * The chain for registering onBeforeMapping interceptors
     */
    private final MappingInterceptorChain onBeforeMapping;
    /**
     * The chain for registering onAfterMapping interceptors
     */
    private final MappingInterceptorChain onAfterMapping;

    /**
     * The singleton instance
     */
    private static MappingDispatcher instance;

    /**
     * Construct a dispatcher instance
     * @param end the end of the interceptor chain to consume context objects
     */
    public MappingDispatcher(InterceptorChainEnd<MappingContext> end) {
        this.onBeforeMapping = new BeforeMappingChain(end);
        this.onAfterMapping = new AfterMappingChain(end);
    }

    /**
     * Evaluate the registration strategy and invoke the action
     * @param strategy the strategy to evaluate
     * @param action perform the action on the interceptor chain that matches the registration strategy
     */
    private void evaluateAndInvokeRegistrationStrategy(RegistrationStrategy strategy, Consumer<MappingInterceptorChain> action) {
        switch (strategy) {
            case BEFORE: action.accept(onBeforeMapping);
                        break;
            case AFTER: action.accept(onAfterMapping);
                        break;
            case ALL: action.accept(onBeforeMapping);
                    action.accept(onAfterMapping);
                    break;
        }
    }

    /**
     * Register the interceptor with the dispatcher
     * @param interceptor the interceptor to register
     * @param strategy the token identifying what the interceptor is being registered for
     */
    public void registerMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy) {
        this.evaluateAndInvokeRegistrationStrategy(strategy, chain -> chain.addInterceptor(interceptor));
    }

    /**
     * Unregister the interceptor that was registered with the provided strategy
     * @param interceptor the interceptor to remove from the dispatcher
     * @param strategy the strategy used to register the interceptor
     */
    public void unregisterMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy) {
        this.evaluateAndInvokeRegistrationStrategy(strategy, chain -> chain.removeInterceptor(interceptor));
    }

    /**
     * Dispatches the context to the onBeforeMapping interceptor chain
     * @param context the context to dispatch
     */
    public void onBeforeMapping(MappingContext context) {
        this.onBeforeMapping.next(context);
    }

    /**
     * Dispatches the context to the onAfterMapping interceptor chain
     * @param context the context to dispatch
     */
    public void onAfterMapping(MappingContext context) {
        this.onAfterMapping.next(context);
    }

    /**
     * Initialise the singleton instance for the dispatcher.
     * @param end the end of the chain to consume the context passed through the chain
     */
    public static void initialise(InterceptorChainEnd<MappingContext> end) {
        instance = new MappingDispatcher(end);
    }

    /**
     * Get the singleton instance for the dispatcher. If {@link #initialise(InterceptorChainEnd)} has not been called, an IllegalStateException is thrown
     * @return singleton instance of the dispatcher
     */
    public static MappingDispatcher getInstance() {
        if (instance == null) {
            throw new IllegalStateException("The MappingDispatcher has not been initialised");
        }

        return instance;
    }

    /**
     * This enum is used to determine the events the interceptor should be registered for
     */
    public enum RegistrationStrategy {
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
