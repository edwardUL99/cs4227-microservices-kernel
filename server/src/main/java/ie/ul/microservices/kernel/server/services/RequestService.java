package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.server.mapping.MappingResult;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface represents a service used to send requests to the microservices
 */
public interface RequestService {
    /**
     * Send the request to the provided microservice. If the request fails because the microservice isn't available, the microservice should
     * be marked as unhealthy and removed from the registry
     * @param result the result of mapping the request
     * @param request the request to send to the client
     * @return the response of the request
     */
    ResponseEntity<?> sendRequest(MappingResult result, HttpServletRequest request);
}
