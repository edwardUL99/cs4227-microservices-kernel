package ie.ul.microservices.kernel.server.models;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;

/**
 * This class holds the parameters for managing a current request being mapped
 */
public class CurrentRequest {
    /**
     * The mapping context being managed
     */
    private MappingContext context;

    /**
     * Get the mapping context for this request
     * @return the request's mapping context
     */
    public MappingContext getContext() {
        return context;
    }

    /**
     * Set the context to store in the request
     * @param context the context for this request
     */
    public void setContext(MappingContext context) {
        this.context = context;
    }
}
