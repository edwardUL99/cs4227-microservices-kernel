package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.server.interception.InterceptorChainEnd;
import ie.ul.microservices.kernel.server.interception.MappingContext;

/**
 * This interface represents a service for mapping requests. It is also responsible for dispatching to interceptors
 * throughout the mapping process. Extends the chain end interface as the service needs to be aware of the returned context
 */
public interface MappingService extends InterceptorChainEnd<MappingContext> {
}
