package ie.ul.microservices.kernel.server.interception;

/**
 * This class represents the interception chain to handle onBeforeMapping interceptions
 */
public class BeforeMappingChain extends MappingInterceptorChain {
    /**
     * Construct a chain, providing an InterceptorChainEnd to accept the context from the last element in the chain
     *
     * @param end the end accepting the context that was passed through the chain
     */
    protected BeforeMappingChain(InterceptorChainEnd<MappingContext> end) {
        super(end);
    }

    /**
     * When next is called, the next interceptor and context will be passed into this method. The method should delegate
     * the context to the appropriate interceptor method on next
     *
     * @param next    the next interceptor
     * @param context the context to pass into the interceptor
     */
    @Override
    protected void handle(MappingInterceptor next, MappingContext context) {
        next.onBeforeMapping(context, this);
    }
}
