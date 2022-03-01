package ie.ul.microservices.kernel.server.interception;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * This class represents configuration for the interception package.
 */
@Configuration(value = "Interception")
@DependsOn("Mapping")
public class InterceptionConfig {
//    /**
//     * Construct an InterceptionConfig instance
//     * @param mappingEnd the end of the interceptor chain for mapping interceptors
//     */
//    @Autowired
//    public InterceptionConfig(InterceptorChainEnd<MappingContext> mappingEnd) {
//        MappingDispatcher.initialise(mappingEnd);
//    }
}
