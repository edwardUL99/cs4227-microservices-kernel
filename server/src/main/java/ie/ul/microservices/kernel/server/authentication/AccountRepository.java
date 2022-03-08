package ie.ul.microservices.kernel.server.authentication;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * This interface provides a repository for storing accounts
 */
public interface AccountRepository extends CrudRepository<Account, String> {
    /**
     * Find the account by username
     * @param username the username of the account
     * @return the optional containing the account
     */
    Optional<Account> findByUsername(String username);

    /**
     * Find the account by email
     * @param email the email to find the account by
     * @return the optional containing the account
     */
    Optional<Account> findByEmail(String email);
}
