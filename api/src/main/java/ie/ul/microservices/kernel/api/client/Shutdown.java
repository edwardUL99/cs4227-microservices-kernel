package ie.ul.microservices.kernel.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This class provides functionality to shut down the application
 */
@Component
public class Shutdown {
    /**
     * The application context
     */
    private final ApplicationContext applicationContext;

    /**
     * Create a Shutdown instance
     * @param applicationContext the application context
     */
    @Autowired
    public Shutdown(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Shutdown the applications
     */
    public void shutdown() {
        SpringApplication.exit(applicationContext, () -> 0);
    }
}
