package ie.ul.microservices.kernel.server.models;

import java.util.Objects;

/**
 * This class represents a Microservice instance that has been registered on the kernel
 * TODO decide what fields to add to it
 */
public class Microservice {
    String microserviceName;
    String host;
    int port;
    boolean healthStatus;
    String microserviceID;

    public Microservice(String host, int port, String microserviceName, boolean healthStatus) {
        this.host = host;
        this.port = port;
        this.microserviceName = microserviceName;
        this.healthStatus = healthStatus;
    }

    public Microservice() {
        this(null, 0, null, false);
    }

    /**
     * gets the health status of the microservice
     * @return health status of the microservice
     */
    public boolean isHealthy(){
        return healthStatus;
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
    public String getMicroserviceName() {
        return microserviceName;
    }

    public String getMicroserviceID() {
        return microserviceID;
    }

    /**
     * Set the microservice name
     * @param microserviceName the name of the microservice
     */
    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Microservice that = (Microservice) o;
        return port == that.port && healthStatus == that.healthStatus && Objects.equals(host, that.host) && Objects.equals(microserviceName, that.microserviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, microserviceName, healthStatus);
    }

    public void setMicroserviceID(String microserviceID) {
        this.microserviceID = microserviceID;
    }
}
