package ie.ul.microservices.kernel.api.client;

/**
 * This class represents the response to a health check from the kernel
 */

public class HealthResponse {
    private String microserviceName;

    public HealthResponse(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

}

