package ie.ul.microservices.kernel.server.interception.mapping;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptor;
import ie.ul.microservices.kernel.api.interception.mapping.MappingDispatcher;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptorChain;
import ie.ul.microservices.kernel.server.interception.InterceptorChainEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the dispatcher to dispatch to mapping interceptors. All interceptors implementing the
 * {@link MappingInterceptor} interface should be registered through this dispatcher.
 */
public class MappingDispatcherImpl implements MappingDispatcher {
    /**
     * The map of registered interceptors
     */
    private final Map<MappingDispatcher.RegistrationStrategy, List<MappingInterceptor>> interceptors = new HashMap<>();

    /**
     * The end of the chain
     */
    private final InterceptorChainEnd<MappingContext> end;

    /**
     * Construct a dispatcher instance
     * @param end the end of the chain
     */
    public MappingDispatcherImpl(InterceptorChainEnd<MappingContext> end) {
        this.end = end;

        for (RegistrationStrategy strategy : RegistrationStrategy.values()) {
            if (strategy != RegistrationStrategy.ALL) {
                this.interceptors.put(strategy, new ArrayList<>());
            }
        }
    }

    /**
     * Register the interceptor with the dispatcher
     * @param interceptor the interceptor to register
     * @param strategy the token identifying what the interceptor is being registered for
     */
    @Override
    public synchronized void registerMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy) {
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
    @Override
    public synchronized void unregisterMappingInterceptor(MappingInterceptor interceptor, RegistrationStrategy strategy) {
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
        MappingInterceptorChain chain = new BeforeMappingChain(end);
        this.interceptors.get(RegistrationStrategy.BEFORE).forEach(chain::addInterceptor);

        return chain;
    }

    /**
     * Construct the chain of after mapping interceptors
     * @return the constructed chain
     */
    private MappingInterceptorChain constructAfterChain() {
        MappingInterceptorChain chain = new AfterMappingChain(end);
        this.interceptors.get(RegistrationStrategy.AFTER).forEach(chain::addInterceptor);

        return chain;
    }

    /**
     * Clears all registered interceptors
     */
    @Override
    public synchronized void clearInterceptors() {
        for (List<MappingInterceptor> interceptors : this.interceptors.values())
            interceptors.clear();
    }

    /**
     * Dispatches the context to the onBeforeMapping interceptor chain
     * @param context the context to dispatch
     */
    @Override
    public synchronized void onBeforeMapping(MappingContext context) {
        this.constructBeforeChain().next(context);
    }

    /**
     * Dispatches the context to the onAfterMapping interceptor chain
     * @param context the context to dispatch
     */
    @Override
    public synchronized void onAfterMapping(MappingContext context) {
        this.constructAfterChain().next(context);
    }
}
