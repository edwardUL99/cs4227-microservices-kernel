package ie.ul.microservices.kernel.server.config;

import ie.ul.microservices.kernel.server.ShutdownListener;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;

/**
 * Configuration to register listeners
 */
@Configuration
public class ListenerConfig {
    /**
     * Create the listener to shut down microservices
     * @param registry registry to find microservices
     * @return the listener bean
     */
    @Bean
    @Autowired
    public ServletListenerRegistrationBean<ServletContextListener> servletListener(Registry registry) {
        ServletListenerRegistrationBean<ServletContextListener> srb
                = new ServletListenerRegistrationBean<>();
        srb.setListener(new ShutdownListener(registry));

        return srb;
    }
}
