package ie.ul.microservices.kernel.server.interception;

import ie.ul.microservices.kernel.server.models.CurrentRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.context.annotation.RequestScope;

/**
 * This class represents configuration for the interception package.
 */
@Configuration(value = "Interception")
@DependsOn("Mapping")
public class InterceptionConfig {
    /**
     * Creates a request bean for managing the current request
     * @return the current request bean
     */
    @Bean
    @RequestScope
    public CurrentRequest currentRequest() {
        return new CurrentRequest();
    }
}
