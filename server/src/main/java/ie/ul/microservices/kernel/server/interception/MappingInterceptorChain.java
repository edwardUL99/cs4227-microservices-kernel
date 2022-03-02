package ie.ul.microservices.kernel.server.interception;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * This chain represents the abstract base chain for the MappingInterceptor
 */
public abstract class MappingInterceptorChain implements InterceptionChain<MappingContext> {
    /**
     * The list of interceptors in the chain
     */
    private final List<MappingInterceptor> interceptors = new ArrayList<>();
    /**
     * The iterator to iterate through the interceptors
     */
    private ListIterator<MappingInterceptor> iterator;
    /**
     * The end of the interceptor chain
     */
    private final InterceptorChainEnd<MappingContext> end;

    /**
     * Construct a chain, providing an InterceptorChainEnd to accept the context from the last element in the chain
     * @param end the end accepting the context that was passed through the chain
     */
    protected MappingInterceptorChain(InterceptorChainEnd<MappingContext> end) {
        this.end = end;
    }

    /**
     * Add the interceptor to the chain. This should be added before the first call to next
     * @param interceptor The interceptor to add to the chain
     */
    public void addInterceptor(MappingInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    /**
     * Remove the interceptor from the chain. This should be removed before the first call to next
     * @param interceptor the interceptor to remove from the chain
     */
    public void removeInterceptor(MappingInterceptor interceptor) {
        this.interceptors.remove(interceptor);
    }

    /**
     * This method passes the context to the next in the chain
     *
     * @param context the context object to pass to the interceptor
     */
    @Override
    public void next(MappingContext context) {
        if (this.iterator == null) {
            this.iterator = this.interceptors.listIterator();
        }

        if (this.iterator.hasNext()) {
            MappingInterceptor interceptor = this.iterator.next();
            this.handle(interceptor, context);
        } else {
            this.iterator = null;
            this.end.consumeContext(context);
        }
    }

    /**
     * Terminates the chain prematurely at the current interceptor. This is equivalent to next not being called from
     * the current interceptor, except for it passes the context to the end of the chain. If this is not the last
     * interceptor, it skips all the interceptors not visited yet
     * @param context the context as of the current interceptor
     */
    @Override
    public void terminate(MappingContext context) {
        this.iterator = null;
        context.terminate();
        this.end.consumeContext(context);
    }

    /**
     * When next is called, the next interceptor and context will be passed into this method. The method should delegate
     * the context to the appropriate interceptor method on next
     * @param next the next interceptor
     * @param context the context to pass into the interceptor
     */
    protected abstract void handle(MappingInterceptor next, MappingContext context);
}
