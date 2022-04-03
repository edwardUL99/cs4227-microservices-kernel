package ie.ul.microservices.kernel.api.server;
/**
 * This class represents a request to unregister a microservice from the kernel
 * TODO think up fields
 */
public class UnregistrationRequest {
    /**
     * name - represents the name of the microservice making the request
     * microserviceID - represents the ID of the microservice making the request
     */
    private String name;
    private String microserviceID;

    /**
     * constructs an unregistration request with the given parameters
     * @param name represents the name of the microservice making the request
     * @param microserviceID represents the id of the microservice making the request
     */
    public UnregistrationRequest(String name, String microserviceID){
        this.name = name;
        this.microserviceID = microserviceID;
    }

    /**
     * gets the name of the microservice
     * @return name of the microservice
     */
    public String getName(){
        return name;
    }

    /**
     * gets the id of the microservice
     * @return id of the microservice
     */
    public String getID(){
        return microserviceID;
    }
}
