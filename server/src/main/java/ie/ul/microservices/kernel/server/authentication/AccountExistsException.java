package ie.ul.microservices.kernel.server.authentication;

/**
 * This exception is thrown if an account already exists
 */
public class AccountExistsException extends RuntimeException {
    /**
     * A flag to determine if it already exists by email
     */
    private final boolean email;

    /**
     * Create an instance of the exception
     * @param message the message to display in the exception
     * @param email true if the account already exists by email, false by username
     */
    public AccountExistsException(String message, boolean email) {
        super(message);
        this.email = email;
    }

    /**
     * Determines if the account exists already by email
     * @return already exists by email, false if username
     */
    public boolean existsByEmail() {
        return email;
    }
}
