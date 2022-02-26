package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.server.interception.MappingContext;
import ie.ul.microservices.kernel.server.interception.MappingDispatcher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * This class provides the default implementation for the MappingService
 */
@Service
@Order(1) // order 1 to ensure that it initialises the dispatcher before interceptors register with it
public class MappingServiceImpl implements MappingService {
    /**
     * Create the mapping service and initialise the dispatcher
     */
    public MappingServiceImpl() {
        MappingDispatcher.initialise(this);
    }

    /**
     * This method receives the context from the interceptor chain
     *
     * @param context the context passed through the chain. May be processed by any of the interceptors
     *                along the chain
     */
    @Override
    public void consumeContext(MappingContext context) {
        System.out.println(context);
    }
}
