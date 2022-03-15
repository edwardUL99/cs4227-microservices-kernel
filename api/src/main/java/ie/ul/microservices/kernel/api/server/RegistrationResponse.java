package ie.ul.microservices.kernel.api.server;

/**
 * This class represents a response to a registration request
 * TODO decide what should be contained in it
 */
public class RegistrationResponse {
    private String microserviceID;

    public RegistrationResponse(String microserviceID) {
        this.microserviceID = microserviceID;
    }

    public String getMicroserviceID(){
        return microserviceID;
    }
}
