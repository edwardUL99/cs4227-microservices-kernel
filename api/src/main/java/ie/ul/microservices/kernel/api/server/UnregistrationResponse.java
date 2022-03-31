package ie.ul.microservices.kernel.api.server;

/**
 * The response to the request to unregister the microservice from the kernel
 */
public class UnregistrationResponse {
    boolean successful;

    public UnregistrationResponse(boolean successful) {
        this.successful = successful;
    }

    public boolean IsSuccessful(){
        return successful;
    }
    
}
