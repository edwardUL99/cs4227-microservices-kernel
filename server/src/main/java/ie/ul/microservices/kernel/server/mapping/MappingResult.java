package ie.ul.microservices.kernel.server.mapping;

import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.api.requests.URL;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

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
     * The request after mapping
     */
    private APIRequest request;
    /**
     * The response entity from the context.
     */
    private ResponseEntity<?> response;

    /**
     * Create a default MappingResult
     */
    public MappingResult() {
        this(false, null, null, null, null);
    }

    /**
     * Construct a MappingResult
     * @param terminated true if the mapping has been terminated
     * @param microservice the mapped microservice
     * @param response the response to set
     */
    public MappingResult(boolean terminated, URL url, Microservice microservice, APIRequest request, ResponseEntity<?> response) {
        this.terminated = terminated;
        this.url = url;
        this.microservice = microservice;
        this.request = request;
        this.setResponse(response);
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

    /**
     * Get the request object after mapping
     * @return request object after mapping
     */
    public APIRequest getRequest() {
        return request;
    }

    /**
     * Set the request after mapping
     * @param request new after mapping request
     */
    public void setRequest(APIRequest request) {
        this.request = request;
    }

    /**
     * Get the response set on the result
     * @return the response to set
     */
    public ResponseEntity<?> getResponse() {
        return response;
    }

    /**
     * Sets the response. If a response is set {@link #isTerminated()} will be set to true
     * @param response the response to set
     */
    public void setResponse(ResponseEntity<?> response) {
        if (response != null) {
            this.terminated = true;
            this.response = response;
        }
    }

    /**
     * Check if this result is equal to the provided result
     * @param o the other object to compare to
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappingResult result = (MappingResult) o;
        return terminated == result.terminated && Objects.equals(url, result.url) && Objects.equals(microservice, result.microservice)
                && Objects.equals(request, result.request) && Objects.equals(response, result.response);
    }

    /**
     * Generate the hashcode for this object
     * @return the generated hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(terminated, url, microservice, request, response);
    }
}
