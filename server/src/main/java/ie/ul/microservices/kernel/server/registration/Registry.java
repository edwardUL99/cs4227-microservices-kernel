package ie.ul.microservices.kernel.server.registration;

import java.util.List;

import ie.ul.microservices.kernel.server.models.Microservice;

/**
 * This interface represents a registry that is capable of storing registered microservices
 * TODO think of the methods a registry should provide
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
     * Each time the microservice registry is checked for
     * unhealthy services this function is called to
     * unregister them from the registry.
     * @param unHealthyMicroservices
     */
    void handleUnhealthyMicroservices(List<Microservice> unHealthyMicroservices);

    /**
     * Unregister the microservice from the registry
     * @param microservice the microservice to unregister
     */
    void unregisterMicroservice(Microservice microservice);
}
