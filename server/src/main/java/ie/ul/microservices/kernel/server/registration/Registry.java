package ie.ul.microservices.kernel.server.registration;

import java.util.List;

import ie.ul.microservices.kernel.server.models.Microservice;

/**
 * This interface represents a registry that is capable of storing registered microservices
 */
public interface Registry {
    /**
     * Get the list of registered microservices stored in the registry
     * @return list of all registered microservices
     */
    List<Microservice> getMicroservices();

    /**
     * Get the microservice stored in the registry with the specified name
     * @param name name of the microservice
     * @return the microservice instance with specified
     */
    Microservice getMicroservice(String name);

    /**
     * Register the given microservice into the registry
     * @param microservice the microservice to register
     */
    void registerMicroservice(Microservice microservice);

    /**
     * Register the microservice into the registry given these parameters
     * @param name - name of microservice
     * @param host - host address of microservice
     * @param port - port of the microservice
     * @return string ID representing microservice unique registration id
     */
    String registerMicroservice(String name, String host, int port);

    /**
     * Unregister the microservice from the registry
     * @param microservice the microservice to unregister
     */
    void unregisterMicroservice(Microservice microservice);

    /**
     * Unregister the microservice from the registry given these parameters
     * @param name - name of microservice
     * @param id - registration id of microservice
     * @return boolean value determining if registration was successful
     */
    boolean unregisterMicroservice(String name, String id);
}
