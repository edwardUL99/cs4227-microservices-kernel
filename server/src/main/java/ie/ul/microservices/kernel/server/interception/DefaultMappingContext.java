package ie.ul.microservices.kernel.server.interception;

import ie.ul.microservices.kernel.server.interception.api.MappingContext;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.models.URL;

import javax.servlet.http.HttpServletRequest;

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
    private final HttpServletRequest request;
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
    public DefaultMappingContext(HttpServletRequest request) {
        this(null, request);
        this.url = URL.fromServletRequest(request);
    }

    /**
     * Create a context initialised with the provided values
     * @param microservice the mapped microservice
     * @param request the request received
     */
    public DefaultMappingContext(Microservice microservice, HttpServletRequest request) {
        this.microservice = microservice;
        this.request = request;
    }

    /**
     * Get the name of the microservice the request is being mapped to. Like the host, this may not be available yet.
     *
     * @return the name of the microservice
     */
    @Override
    public Microservice getMicroservice() {
        return null;
    }

    /**
     * Get the request object received representing the request being mapped
     *
     * @return the request being mapped
     */
    @Override
    public HttpServletRequest getRequest() {
        return this.request;
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
