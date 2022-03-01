package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.server.mapping.MappingException;
import ie.ul.microservices.kernel.server.mapping.MappingResult;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface represents a service for mapping requests. It is also responsible for dispatching to interceptors
 * throughout the mapping process. Extends the chain end interface as the service needs to be aware of the returned context
 */
public interface MappingService {
    /**
     * Map the request to the microservice instance
     * @param request the request to map
     * @return the mapped microservice. Null if no microservice can be found
     * @throws MappingException if an error occurs during mapping.
     */
    MappingResult mapRequest(HttpServletRequest request) throws MappingException;
}
