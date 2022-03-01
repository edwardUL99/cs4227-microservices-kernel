package ie.ul.microservices.kernel.server.interception.api;

import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.models.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface represents the context object for mapping the requests
 */
public interface MappingContext extends Context {
    /**
     * Get the name of the microservice the request is being mapped to. Like the host, this may not be available yet.
     * @return the name of the microservice
     */
    Microservice getMicroservice();

    /**
     * Get the request object received representing the request being mapped
     * @return the request being mapped
     */
    HttpServletRequest getRequest();

    /**
     * Get the URL the request is being mapped to
     * @return the URL the request is being mapped to
     */
    URL getURL();

    /**
     * Set the url of the request
     * @param url the new URL
     */
    void setURL(URL url);

    /**
     * Set the microservice in the mapping context
     * @param microservice the new microservice
     */
    void setMicroservice(Microservice microservice);

    /**
     * Mark the context as terminated
     */
    void terminate();

    /**
     * Determines if the context has been terminated by ending the interception chain early. This indicates that the request should not be sent to
     * the microservice
     * @return true if terminated, false if not
     */
    boolean terminated();
}
