package ie.ul.microservices.kernel.server.interception;

/**
 * This interface represents the chain for interceptors
 * @param <C> the type of the context object
 */
public interface InterceptionChain<C extends Context> {
    /**
     * This method passes the context to the next in the chain
     * @param context the context object to pass to the interceptor
     */
    void next(C context);
}
