package ie.ul.microservices.kernel.server.interception;

import ie.ul.microservices.kernel.server.interception.api.InterceptorChainEnd;
import ie.ul.microservices.kernel.server.interception.api.MappingContext;
import ie.ul.microservices.kernel.server.interception.api.MappingInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the dispatcher to dispatch to mapping interceptors. All interceptors implementing the
 * {@link MappingInterceptor} interface should be registered through this dispatcher.
 */
public class MappingDispatcher {
    /**
     * The map of registered interceptors
     */
    private final Map<RegistrationStrategy, List<MappingInterceptor>> interceptors = new HashMap<>();

    /**
     * The end of the interceptor chains
     */
    private final InterceptorChainEnd<MappingContext> chainEnd;

    /**
     * The instance of the dispatcher
     */
    private static MappingDispatcher instance;

    /**
     * Construct a dispatcher instance
     * @param end the end of the interceptor chain to consume context objects
     */
    public MappingDispatcher(InterceptorChainEnd<MappingContext> end) {
        this.chainEnd = end;

        for (RegistrationStrategy strategy : RegistrationStrategy.values()) {
            if (strategy != RegistrationStrategy.ALL) {
                this.interceptors.put(strategy, new ArrayList<>());
            }
        }
    }

    /**
     * Initialise the mapping dispatcher
     * @param chainEnd the end of the chain to consume the mapping context
     */
    public static void initialise(InterceptorChainEnd<MappingContext> chainEnd) {
        instance = new MappingDispatcher(chainEnd);
    }

    /**
     * Retrieve the singleton instance
     * @return the singleton instance of the MappingDispatcher
     */
    public static MappingDispatcher getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("The MappingDispatcher is not initialised. Call initialise");
        }

        return instance;
    }

    /**
     * Register the interceptor with the dispatcher
     * @param interceptor the interceptor to register
     * @param strategy the token identifying what the interceptor is being registered for
     */
    public void registerMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy) {
        if (strategy == RegistrationStrategy.ALL) {
            for (RegistrationStrategy s : RegistrationStrategy.values()) {
                if (s != RegistrationStrategy.ALL) {
                    this.registerMappingInterceptor(interceptor, s);
                }
            }
        } else {
            List<MappingInterceptor> interceptors = this.interceptors.get(strategy);
            interceptors.add(interceptor);
        }
    }

    /**
     * Unregister the interceptor that was registered with the provided strategy
     * @param interceptor the interceptor to remove from the dispatcher
     * @param strategy the strategy used to register the interceptor
     */
    public void unregisterMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy) {
        if (strategy == RegistrationStrategy.ALL) {
            for (RegistrationStrategy s : RegistrationStrategy.values()) {
                if (s != RegistrationStrategy.ALL) {
                    this.unregisterMappingInterceptor(interceptor, s);
                }
            }
        } else {
            List<MappingInterceptor> interceptors = this.interceptors.get(strategy);
            interceptors.remove(interceptor);
        }
    }

    /**
     * Construct the chain of before mapping interceptors
     * @return the constructed chain
     */
    private MappingInterceptorChain constructBeforeChain() {
        MappingInterceptorChain chain = new BeforeMappingChain(chainEnd);
        this.interceptors.get(RegistrationStrategy.BEFORE).forEach(chain::addInterceptor);

        return chain;
    }

    /**
     * Construct the chain of after mapping interceptors
     * @return the constructed chain
     */
    private MappingInterceptorChain constructAfterChain() {
        MappingInterceptorChain chain = new AfterMappingChain(chainEnd);
        this.interceptors.get(RegistrationStrategy.AFTER).forEach(chain::addInterceptor);

        return chain;
    }

    /**
     * Clears all registered interceptors
     */
    public synchronized void clearInterceptors() {
        for (List<MappingInterceptor> interceptors : this.interceptors.values())
            interceptors.clear();
    }

    /**
     * Dispatches the context to the onBeforeMapping interceptor chain
     * @param context the context to dispatch
     */
    public synchronized void onBeforeMapping(MappingContext context) {
        this.constructBeforeChain().next(context);
    }

    /**
     * Dispatches the context to the onAfterMapping interceptor chain
     * @param context the context to dispatch
     */
    public synchronized void onAfterMapping(MappingContext context) {
        this.constructAfterChain().next(context);
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
