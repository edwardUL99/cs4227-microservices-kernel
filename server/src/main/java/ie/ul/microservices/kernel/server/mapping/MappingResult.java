package ie.ul.microservices.kernel.server.mapping;

import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.models.URL;

/**
 * This class represents the result of mapping
 */
public class MappingResult {
    /**
     * Indicates if the mapping has been terminated and should not be sent to the microservice
     */
    private boolean terminated;
    /**
     * The mapped url without hostname
     */
    private URL url;
    /**
     * The mapped microservice
     */
    private Microservice microservice;

    /**
     * Create a default MappingResult
     */
    public MappingResult() {
        this(false, null, null);
    }

    /**
     * Construct a MappingResult
     * @param terminated true if the mapping has been terminated
     * @param microservice the mapped microservice
     */
    public MappingResult(boolean terminated, URL url, Microservice microservice) {
        this.terminated = terminated;
        this.url = url;
        this.microservice = microservice;
    }

    /**
     * Determines if the mapping has been terminated
     * @return true if terminated, false if not
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Mark the result as being terminated/not terminated
     * @param terminated the new value for terminated
     */
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    /**
     * Get the url that's been mapped
     * @return the mapped URL
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Set the url of the result
     * @param url the url to set
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Get the mapped microservice. This might be null if the microservice was not found
     * @return the mapped microservice or null
     */
    public Microservice getMicroservice() {
        return microservice;
    }

    /**
     * Set the microservice stored in the result
     * @param microservice the microservice to store in the result
     */
    public void setMicroservice(Microservice microservice) {
        this.microservice = microservice;
    }
}
