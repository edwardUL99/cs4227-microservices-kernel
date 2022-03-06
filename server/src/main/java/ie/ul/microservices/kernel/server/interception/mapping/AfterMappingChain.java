package ie.ul.microservices.kernel.server.interception.mapping;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptor;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptorChain;
import ie.ul.microservices.kernel.server.interception.InterceptorChainEnd;

/**
 * This class represents the interception chain to handle onAfterMapping interceptions
 */
public class AfterMappingChain extends MappingInterceptorChain {
    /**
     * Construct a chain, providing an InterceptorChainEnd to accept the context from the last element in the chain
     *
     * @param end the end accepting the context that was passed through the chain
     */
    protected AfterMappingChain(InterceptorChainEnd<MappingContext> end) {
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
        next.onAfterMapping(context, this);
    }
}
