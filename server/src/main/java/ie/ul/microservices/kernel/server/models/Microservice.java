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

    public String getName(){
        return microserviceName;
    }

    public void setHealthStatus(boolean healthStatus){
        this.healthStatus = healthStatus;
    }

    public boolean isHealthy(){
        return healthStatus;
    }
}
