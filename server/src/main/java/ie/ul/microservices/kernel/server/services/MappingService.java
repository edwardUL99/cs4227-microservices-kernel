package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.server.mapping.MappingException;
import ie.ul.microservices.kernel.server.mapping.MappingResult;

/**
 * This interface represents a service for mapping requests. It is also responsible for dispatching to interceptors
 * throughout the mapping process. Extends the chain end interface as the service needs to be aware of the returned context
 */
public interface MappingService {
    /**
     * Map the request to the microservice instance
     * @param request the request to map
     * @return the result of mapping. If {@link MappingResult#isTerminated()} returns true, the request should not be forwarded
     * and an appropriate response saying the request cannot be handled should be returned
     * @throws MappingException if an error occurs during mapping.
     */
    MappingResult mapRequest(APIRequest request) throws MappingException;
}
