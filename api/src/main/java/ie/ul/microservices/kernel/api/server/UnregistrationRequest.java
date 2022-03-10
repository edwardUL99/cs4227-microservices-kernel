package ie.ul.microservices.kernel.api.server;

/**
 * This class represents a request to unregister a microservice from the kernel
 * TODO think up fields
 */
public class UnregistrationRequest {
    String name;
    String microserviceID;

    public UnregistrationRequest(String name, String microserviceID){
        this.name = name;
        this.microserviceID = microserviceID;
    }
}
