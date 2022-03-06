package ie.ul.microservices.kernel.server.models;

import ie.ul.microservices.kernel.api.client.FrontController;
import ie.ul.microservices.kernel.api.client.HealthResponse;
import ie.ul.microservices.kernel.server.monitoring.HealthReporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This class represents a Microservice instance that has been registered on the kernel
 * TODO decide what fields to add to it
 */
public class Microservice implements FrontController{
    String microserviceName;
    String host;
    int port;
    HealthReporter healthReporter;
    boolean healthStatus;
    String microserviceID;

    /**
     * When each microservice is created a new object of which class
     * implements HealthReporter is created.
     * e.g. Microservice dbMicroservice = new Microservice(DBHealthReporter)
     */
    public Microservice(HealthReporter healthReporter) {
        this.healthReporter = healthReporter;
    }

    /**
     * Get the name of the microservice
     * @return the microservice name
     */
    public String getMicroserviceName() {
        return microserviceName;
    }

    /**
     * Set the microservice name
     * @param microserviceName the name of the microservice
     */
    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

     /**
     * Get the hostname of the microservice
     * @return the microservice hostname
     */
    public String getHost() {
        return host;
    }

    /**
     * Set the microservice hostname
     * @param host the host of the microservice
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * The port of the microservice
     * @return the microservice port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port of the microservice
     * @param port the microservice port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * gets the health status of the microservice
     * @return health status of the microservice
     */
    public boolean isHealthy(){
        return healthStatus;
    }

    /**
     * sets the health status of the microservice to specified health status
     * @param healthStatus new health status of the microservice
     */
    public void setHealthStatus(boolean healthStatus){
        this.healthStatus = healthStatus;
    }

    /**
     * Get the name of the microservice
     * @return the microservice name
     */
    public String getMicroserviceID() {
        return microserviceID;
    }

    /**
     * Set the microserviceID
     * @param microserviceID the ID of the microservice
     */
    public void setMicroserviceID(String microserviceID) {
        this.microserviceID = microserviceID;
    }

    @Override
    public ResponseEntity<HealthResponse> health() {
        HttpStatus httpStatus;
        boolean isHealthy = healthReporter.isHealthy();

        if(isHealthy) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        }

        HealthResponse healthResponse = new HealthResponse(getMicroserviceName(), getMicroserviceID(), httpStatus);
        //create ResponseEntity with body and status code
        return new ResponseEntity<>(healthResponse, httpStatus);
    }

    @Override
    public ResponseEntity<?> shutdown() {
        return null;
    }

}
