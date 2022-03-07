package ie.ul.microservices.kernel.api.client;

/**
 * This class represents the response to a health check from the kernel
 */

public class HealthResponse {
    private String microserviceName;
    private String microserviceID;

    public HealthResponse(String microserviceName, String microserviceID) {
        this.microserviceName = microserviceName;
        this.microserviceID = microserviceID;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public String getMicroserviceID() {
        return microserviceID;
    }

    public void setMicroserviceID(String microserviceID) {
        this.microserviceID = microserviceID;
    }

}

