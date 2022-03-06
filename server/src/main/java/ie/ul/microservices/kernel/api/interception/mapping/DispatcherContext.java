package ie.ul.microservices.kernel.api.interception.mapping;

/**
 * This class is used for storing and initialising the dispatcher instances
 */
public final class DispatcherContext {
    /**
     * The mapping dispatcher initialised in the context
     */
    private static MappingDispatcher dispatcher;

    /**
     * Set the implementation of the dispatcher to use
     * @param dispatcher the dispatcher implementation
     */
    public static void setDispatcher(MappingDispatcher dispatcher) {
        DispatcherContext.dispatcher = dispatcher;
    }

    /**
     * Get the dispatcher instance registered with the context
     * @return dispatcher instance
     * @throws IllegalStateException if not set
     */
    public static MappingDispatcher getDispatcher() {
        if (dispatcher == null) {
            throw new IllegalStateException("The dispatcher context has not been initialised");
        }

        return dispatcher;
    }
}
