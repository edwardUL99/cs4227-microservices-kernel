package ie.ul.microservices.kernel.api.server;

/**
 * This class represents the request to register the microservices
 */
public class RegistrationRequest {
    /** 
     * name - represents the name of the microservice making the request
     * host - represents the host address of the microservice making the request
     * port - represents the port number of the microservice making the request
     */
    private String name;
    private String host;
    private int port;

    /** 
     * constructs a registration request with given parameters
     * @param name the name of the microservice making the request
     * @param host the host address of the microservice making the request
     * @param port the port number of the microservice making the request
     */
    public RegistrationRequest(String name, String host, int port){
        this.name = name;
        this.host = host;
        this.port = port;
    }

    /**
     * gets the name of the microservice
     * @return name of the microservice
     */
    public String getName(){
        return this.name;
    }

    /**
     * gets the host address of the microservice
     * @return host address of the microservice
     */
    public String getHost(){
        return this.host;
    }

    /**
     * gets the port number of the microservice
     * @return port number of the microservice
     */
    public int getPort(){
        return this.port;
    }
}
