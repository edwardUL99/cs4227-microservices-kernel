package ie.ul.microservices.kernel.server.interception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an abstract base for a MappingInterceptor instance that only provides an implementation
 * for one of the interception points (all are provided as no-op, but override the particular one)
 */
public abstract class SingleMappingInterceptor implements MappingInterceptor {
    /**
     * The strategy that was used to register the interceptor with
     */
    protected final MappingDispatcher.RegistrationStrategy strategy;

    /**
     * The log used for logging warnings
     */
    private Logger log = LoggerFactory.getLogger(SingleMappingInterceptor.class);

    /**
     * Create and register the interceptor with the given strategy
     * @param strategy the strategy to register the interceptor with
     */
    protected SingleMappingInterceptor(MappingDispatcher.RegistrationStrategy strategy) {
        if (strategy == MappingDispatcher.RegistrationStrategy.ALL) {
            log.warn("You should use the AllMappingInterceptor instead. This class is intended for use with the other" +
                    " RegistrationStrategies. This will still work, but since the class provides no-op implementations, " +
                    "it cannot be guaranteed that all the interception callbacks will be implemented");
        }

        this.strategy = strategy;
        MappingDispatcher.getInstance().registerMappingInterceptor(this, strategy);
    }

    /**
     * This interception point is called before the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param next    the next interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onBeforeMapping(MappingContext context, MappingInterceptorChain next) {
        // no-op
    }

    /**
     * This interception point is called after the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param next    the next interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onAfterMapping(MappingContext context, MappingInterceptorChain next) {
        // no-op
    }

    /**
     * Get the strategy used to register the interceptor
     * @return the strategy used registering the interceptor
     */
    public MappingDispatcher.RegistrationStrategy getStrategy() {
        return strategy;
    }
}
