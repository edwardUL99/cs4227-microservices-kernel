package ie.ul.microservices.kernel.api.server;

/**
 * The response to the request to unregister the microservice from the kernel
 */
public class UnregistrationResponse {
    /**
     * successful a boolean that determines if unregistration is successful
     */
    boolean successful;

    /**
     * constructs an unregistration response
     * @param successful true if successfully unregister
     */
    public UnregistrationResponse(boolean successful) {
        this.successful = successful;
    }

    /**
     * returns true if unregistration was successful
     * @return true if unregistration was successful
     */
    public boolean isSuccessful(){
        return successful;
    }
    
}
