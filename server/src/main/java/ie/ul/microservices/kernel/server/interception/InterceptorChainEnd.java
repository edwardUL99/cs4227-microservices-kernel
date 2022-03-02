package ie.ul.microservices.kernel.server.interception;

/**
 * This interface represents the consumer at the end of an InterceptorChain. It consumes the context that was passed
 * through the chain
 * @param <C> the type of the context to consume
 */
public interface InterceptorChainEnd<C extends Context> {
    /**
     * This method receives the context from the interceptor chain
     * @param context the context passed through the chain. May be processed by any of the interceptors
     * along the chain
     */
    void consumeContext(C context);
}
