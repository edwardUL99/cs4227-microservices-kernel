package ie.ul.microservices.kernel.server;

import ie.ul.microservices.kernel.api.requests.Request;
import ie.ul.microservices.kernel.api.requests.RequestBuilder;
import ie.ul.microservices.kernel.api.requests.RequestException;
import ie.ul.microservices.kernel.api.requests.RequestSender;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A listener to instruct microservices to shut down when the kernel shuts itself down
 */
public class ShutdownListener implements ServletContextListener {
    /**
     * Registry to find microservices
     */
    private final Registry registry;
    /**
     * Object used for sending requests
     */
    private final RequestSender requestSender = new RequestSender();

    /**
     * Create a listener instance
     * @param registry registry for finding microservices
     */
    public ShutdownListener(Registry registry) {
        this.registry = registry;
    }

    /**
     * Shutdown all microservices
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        for (Microservice microservice : registry.getMicroservices()) {
            if (microservice.isHealthy()) {
                try {
                    String host = microservice.getHost();
                    int port = microservice.getPort();

                    Request request = new RequestBuilder()
                            .withUrl(String.format("http://%s:%d/front/shutdown/", host, port))
                            .withMethod(HttpMethod.POST)
                            .build();

                    requestSender.sendRequest(request);
                } catch (RequestException ignored) {}
            }
        }
    }
}
