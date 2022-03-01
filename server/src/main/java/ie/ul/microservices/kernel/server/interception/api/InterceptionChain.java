package ie.ul.microservices.kernel.server.interception.api;

/**
 * This interface represents the chain for interceptors
 * @param <C> the type of the context object
 */
public interface InterceptionChain<C extends Context> {
    /**
     * This method passes the context to the next in the chain. If the current interceptor is the last in the chain,
     * the context is passed to the end of the chain
     * @param context the context object to pass to the interceptor
     */
    void next(C context);

    /**
     * Terminates the chain prematurely at the current interceptor. This is equivalent to next not being called from
     * the current interceptor, except for it passes the context to the end of the chain. If this is not the last
     * interceptor, it skips all the interceptors not visited yet
     * @param context the context object as of the current interceptor chain
     */
    void terminate(C context);
}
