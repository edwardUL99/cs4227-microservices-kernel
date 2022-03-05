package ie.ul.microservices.kernel.server.models;

import ie.ul.microservices.kernel.api.client.ForwardedRequest;
import ie.ul.microservices.kernel.api.client.FrontController;
import ie.ul.microservices.kernel.api.client.HealthResponse;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class represents a Microservice instance that has been registered on the kernel
 * TODO decide what fields to add to it
 */
public class Microservice implements FrontController{

    String microserviceName;
    String host;
    int port;
    boolean healthStatus;
    String microserviceID;

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
     * Set the microservice name
     * @param microserviceName the name of the microservice
     */
    public void setMicroserviceID(String microserviceID) {
        this.microserviceID = microserviceID;
    }

    @Override
    public ResponseEntity<HealthResponse> health() {
        int responseCode = 0;
        try {
            URL url = new URL("/health");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            responseCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HealthResponse healthResponse = HealthResponse.builder()
                .microserviceName(microserviceName)
                .microserviceID(microserviceID)
                .build();

        return ResponseEntity(healthResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> lookup() {
        return null;
    }

    @Override
    public ResponseEntity<?> shutdown() {
        return null;
    }

    @Override
    public ResponseEntity<?> forward(ForwardedRequest request) {
        return null;
    }
}
