package ie.ul.microservices.kernel.server.monitoring;

import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.RegistryImpl;

import java.util.List;

/**
 * This interface represents an object that can monitor the registered microservices and update their statuses. Can be done
 * in real-time by making the implementation use threads, or it can be implemented in the same thread as the server and be scheduled
 * in some means to run between request and responses.
 *
 * TODO think of other methods. Maybe a means to retrieve log messages or errors
 */
public interface Monitor {
    /**
     * Start the monitor. The monitor should stay started until a stop request is received
     */
    void startMonitoring();

    /**
     * Stop the monitor. Once stopped, it should no longer monitor the registered microservices
     */
    void stopMonitoring();
}
