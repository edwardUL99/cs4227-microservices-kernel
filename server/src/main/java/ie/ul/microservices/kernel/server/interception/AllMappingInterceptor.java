package ie.ul.microservices.kernel.server.interception;

/**
 * This class provides the base for a mapping interceptor that automatically registers itself as an interceptor that can
 * handle all mapping interception points (therefore, no no-op implementations are given)
 */
public abstract class AllMappingInterceptor implements MappingInterceptor {
    /**
     * Construct an interceptor instance
     */
    protected AllMappingInterceptor() {
        MappingDispatcher.getInstance().registerMappingInterceptor(this, MappingDispatcher.RegistrationStrategy.ALL);
    }
}
