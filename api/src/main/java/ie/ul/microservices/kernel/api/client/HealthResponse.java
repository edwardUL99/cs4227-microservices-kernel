package ie.ul.microservices.kernel.api.client;

/**
 * This class represents the response to a health check from the kernel
 */

public class HealthResponse {
    /**
     * microserviceName - name of the microservice
     */
    private String microserviceName;

    /**
     * Constructs a health response with the given parameters
     * @param microserviceName the name of the microservice
     */
    public HealthResponse(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    /**
     * 
     * @return
     */
    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

}

