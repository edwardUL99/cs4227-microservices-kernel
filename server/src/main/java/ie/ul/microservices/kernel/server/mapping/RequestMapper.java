package ie.ul.microservices.kernel.server.mapping;

/**
 * This represents an object that is capable of taking a request from a client and maps it to an associated microservice
 * that is registered. If no such microservice exists, or it exists, but not in a healthy state, the mapper should handle
 * this by ending the request chain and respond with an error.
 *
 * TODO decide methods
 */
public interface RequestMapper {
}
