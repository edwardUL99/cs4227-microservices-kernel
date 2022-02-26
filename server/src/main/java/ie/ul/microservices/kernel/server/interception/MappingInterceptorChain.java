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
    private List<MappingInterceptor> interceptors = new ArrayList<>();
    /**
     * The iterator to iterate through the interceptors
     */
    private ListIterator<MappingInterceptor> iterator;

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
        }
    }

    /**
     * When next is called, the next interceptor and context will be passed into this method. The method should delegate
     * the context to the appropriate interceptor method on next
     * @param next the next interceptor
     * @param context the context to pass into the interceptor
     */
    protected abstract void handle(MappingInterceptor next, MappingContext context);
}
