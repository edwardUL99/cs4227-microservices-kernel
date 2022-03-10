package ie.ul.microservices.kernel.api.server;

/**
 * This class represents the request to register the microservices
 * TODO decide what parameters are needed
 */
public class RegistrationRequest {
    String name;
    String host;
    int port;

    public RegistrationRequest(String name, String host, int port){
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getName(){
        return this.name;
    }

    public String getHost(){
        return this.host;
    }

    public int getPort(){
        return this.port;
    }
}
