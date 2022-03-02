package ie.ul.microservices.kernel.server.interception.mapping;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingDispatcher;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptorChain;
import ie.ul.microservices.kernel.api.interception.mapping.SingleMappingInterceptor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

// TODO delete this when proper interceptors are implemented. Shows how an interceptor is registered, must use the same @Component and @DependsOn annotations
// TODO This is only necessary if the interceptor needs dependency injection. Otherwise, it may be ok without @Component and manually instantiate it somewhere so it registers

@Component
@DependsOn("Interception") // needs interception to be setup first
public class ImplementationInterceptor extends SingleMappingInterceptor {
    /**
     * Create and register the interceptor with the given strategy
     */
    protected ImplementationInterceptor() {
        super(MappingDispatcher.RegistrationStrategy.BEFORE);
    }

    /**
     * This interception point is called before the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain    the next interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onBeforeMapping(MappingContext context, MappingInterceptorChain chain) {
        System.out.println("Received context " + context + " with URL " + context.getURL());
        chain.next(context);
    }
}
