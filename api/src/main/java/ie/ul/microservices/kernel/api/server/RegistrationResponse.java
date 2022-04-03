package ie.ul.microservices.kernel.api.server;

/**
 * This class represents a response to a registration request
 */
public class RegistrationResponse {
    /**
     * microserviceID - represents the id given to a microservice if it successfully registered
     */
    private String microserviceID;

    /**
     * constructs a registration response with the given parameters
     * @param microserviceID the id of the microservice that attempted to register
     */
    public RegistrationResponse(String microserviceID) {
        this.microserviceID = microserviceID;
    }

    /**
     * gets the id of the microservice
     * @return microservice ID of the given microservice
     */
    public String getID(){
        return microserviceID;
    }
}
