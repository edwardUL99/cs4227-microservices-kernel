package ie.ul.microservices.kernel.server.mapping;

/**
 * This class represents an exception that occurred during mapping
 */
public class MappingException extends RuntimeException {
    /**
     * Construct an exception with the provided message
     * @param message the message of the exception
     */
    public MappingException(String message) {
        super(message);
    }

    /**
     * Construct the exception with the provided message and causing exception
     * @param message the message of the exception
     * @param cause the throwable that caused this exception
     */
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
