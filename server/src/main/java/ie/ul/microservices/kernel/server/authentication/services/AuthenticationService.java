package ie.ul.microservices.kernel.server.authentication.services;

import ie.ul.microservices.kernel.server.authentication.AccountExistsException;
import ie.ul.microservices.kernel.server.authentication.Token;
import ie.ul.microservices.kernel.server.authentication.models.Account;

/**
 * This interface represents a service for use authentication
 */
public interface AuthenticationService {
    /**
     * Authenticate the user with the provided username and raw password
     * @param username the username of the account to authenticate
     * @param password the password of the account to authenticate
     * @param expiry the number of hours the token should expire in
     * @return the generated token
     */
    Token authenticate(String username, String password, Long expiry);

    /**
     * Take the registration request and register the account
     * @param account the account to create
     * @return the created account
     * @throws AccountExistsException if the account already exists
     */
    Account register(Account account) throws AccountExistsException;
}
