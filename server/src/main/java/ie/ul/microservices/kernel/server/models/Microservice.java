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

    String host;
    String port;
    String microserviceName;
    boolean healthStatus;
    String microserviceID;

    /**
     * gets the name of the microservice
     * @return name of the microservice
     */
    public String getName(){
        return microserviceName;
    }

    /**
     * sets the health status of the microservice to specified health status
     * @param healthStatus new health status of the microservice
     */
    public void setHealthStatus(boolean healthStatus){
        this.healthStatus = healthStatus;
    }

    /**
     * gets the health status of the microservice
     * @return health status of the microservice
     */
    public boolean isHealthy(){
        return healthStatus;
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
