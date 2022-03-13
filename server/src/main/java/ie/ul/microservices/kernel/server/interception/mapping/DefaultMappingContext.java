package ie.ul.microservices.kernel.server.interception.mapping;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.api.requests.URL;
import org.springframework.http.ResponseEntity;

/**
 * This class represents the default implementation of the MappingContext
 */
public class DefaultMappingContext implements MappingContext {
    /**
     * The microservice in the context
     */
    private Microservice microservice;
    /**
     * The request the microservice is being mapped to
     */
    private APIRequest request;

    /**
     * A response that an interceptor can set to return instead of forwarding the request
     */
    private ResponseEntity<?> response;

    /**
     * The url the request is being mapped to
     */
    private URL url;
    /**
     * This holds the value of terminated for the mapping this context relates to
     */
    private boolean terminated;

    /**
     * Creates a default empty context
     */
    public DefaultMappingContext(APIRequest request) {
        this(null, request);
        this.url = request.getRequestURL();
    }

    /**
     * Create a context initialised with the provided values
     * @param microservice the mapped microservice
     * @param request the request received
     */
    public DefaultMappingContext(Microservice microservice, APIRequest request) {
        this.microservice = microservice;
        this.request = request;
    }

    /**
     * Get the request object received representing the request being mapped
     *
     * @return the request being mapped
     */
    @Override
    public APIRequest getRequest() {
        return this.request;
    }

    /**
     * Set the request object in the context
     *
     * @param request the request of the context
     */
    @Override
    public void setRequest(APIRequest request) {
        this.request = request;
    }

    /**
     * Get the response object that will be sent back to the client. This is usually null, but can be set using {@link #setResponse(ResponseEntity)}
     *
     * @return the response to send back.
     */
    @Override
    public ResponseEntity<?> getResponse() {
        return response;
    }

    /**
     * Set the response to send back. <b>Note:</b> this overrides the response sent back from mapping. I.e. if a response entity is available
     * after mapping, the request won't be forwarded and this response will be returned instead. Useful to send back error responses
     *
     * @param response the response to set in the context
     */
    @Override
    public void setResponse(ResponseEntity<?> response) {
        this.response = response;
    }

    /**
     * Get the URL the request is being mapped to
     *
     * @return the URL the request is being mapped to
     */
    @Override
    public URL getURL() {
        return this.url;
    }

    /**
     * Set the url of the request
     *
     * @param url the new URL
     */
    @Override
    public void setURL(URL url) {
        this.url = url;
    }

    /**
     * Get the name of the microservice the request is being mapped to. Like the host, this may not be available yet.
     *
     * @return the name of the microservice
     */
    @Override
    public Microservice getMicroservice() {
        return this.microservice;
    }

    /**
     * Set the microservice in the mapping context
     *
     * @param microservice the new microservice
     */
    @Override
    public void setMicroservice(Microservice microservice) {
        this.microservice = microservice;
    }

    /**
     * Mark the context as terminated
     */
    @Override
    public void terminate() {
        this.terminated = true;
    }

    /**
     * Determines if the context has been terminated by ending the interception chain early. This indicates that the request should not be sent to
     * the microservice
     *
     * @return true if terminated, false if not
     */
    @Override
    public boolean terminated() {
        return this.terminated;
    }
}
