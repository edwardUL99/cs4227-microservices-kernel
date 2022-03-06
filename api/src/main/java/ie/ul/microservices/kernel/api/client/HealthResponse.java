package ie.ul.microservices.kernel.api.client;

import org.springframework.http.HttpStatus;

/**
 * This class represents the response to a health check from the kernel
 */

public class HealthResponse {
    private String microserviceName;
    private String microserviceID;
    private HttpStatus httpStatus;

    public HealthResponse(String microserviceName, String microserviceID, HttpStatus httpStatus) {
        this.microserviceName = microserviceName;
        this.microserviceID = microserviceID;
        this.httpStatus = httpStatus;
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
