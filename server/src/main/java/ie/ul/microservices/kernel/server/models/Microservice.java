package ie.ul.microservices.kernel.server.models;

/**
 * This class represents a Microservice instance that has been registered on the kernel
 * TODO decide what fields to add to it
 */
public class Microservice {

    String host;
    String port;
    String microserviceName;
    boolean healthStatus;

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
}
