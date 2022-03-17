package ie.ul.microservices.kernel.api.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * This class represents a request to unregister a microservice from the kernel
 * TODO think up fields
 */
public class UnregistrationRequest {
    String name;
    String microserviceID;

    public UnregistrationRequest(String name, String microserviceID){
        this.name = name;
        this.microserviceID = microserviceID;
    }

    public String GetName(){
        return name;
    }

    public String GetID(){
        return microserviceID;
    }
}
