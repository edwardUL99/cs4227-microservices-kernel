package ie.ul.microservices.kernel.server.interception.mapping;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.server.interception.ContextFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * This factory is a context factory used for creating MappingContexts
 */
public class MappingContextFactory extends ContextFactory {
    /**
     * Create the context
     *
     * @param args optional args to pass in at construction time
     * @return the context to create
     */
    @Override
    public MappingContext createContext(Object... args) {
        Class<?>[] parameters = {HttpServletRequest.class};
        return (MappingContext) constructFromArgs(DefaultMappingContext.class, args, parameters);
    }
}
