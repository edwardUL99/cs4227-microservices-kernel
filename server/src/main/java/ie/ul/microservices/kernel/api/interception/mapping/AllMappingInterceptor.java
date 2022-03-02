package ie.ul.microservices.kernel.api.interception.mapping;

import org.springframework.stereotype.Component;

/**
 * This class provides the base for a mapping interceptor that automatically registers itself as an interceptor that can
 * handle all mapping interception points (therefore, no no-op implementations are given).
 *
 * Intended to be used within the context of Spring Dependency Injection
 */
@Component
public abstract class AllMappingInterceptor implements MappingInterceptor {
    /**
     * Construct and register the interceptor
     */
    protected AllMappingInterceptor() {
        DispatcherContext.getDispatcher().registerMappingInterceptor(this, MappingDispatcher.RegistrationStrategy.ALL);
    }
}
