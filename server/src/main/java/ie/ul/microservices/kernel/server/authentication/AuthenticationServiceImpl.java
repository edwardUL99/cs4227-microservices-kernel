package ie.ul.microservices.kernel.server.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class provides an implementation of the authentication service
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * The JWT instance for generating tokens
     */
    private final JWT jwt;

    /**
     * The encoder for encoding and validating raw passwords
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * The repository for saving accounts
     */
    private final AccountRepository accountRepository;

    /**
     * Construct a service instance
     * @param jwt the instance of JWT for generating tokens
     * @param passwordEncoder the encoder for encoding/validating JWT tokens
     * @param accountRepository the repository storing accounts
     */
    public AuthenticationServiceImpl(JWT jwt, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    /**
     * Authenticate the user with the provided username and raw password
     *
     * @param username the username of the account to authenticate
     * @param password the password of the account to authenticate
     * @param expiry   the number of hours the token should expire in
     * @return the generated token
     */
    @Override
    public Token authenticate(String username, String password, Long expiry) {
        Account account = accountRepository.findByUsername(username).orElse(null);

        if (account == null || !passwordEncoder.matches(password, account.getPassword())) {
            return null;
        } else {
            return jwt.generateToken(account, expiry);
        }
    }

    /**
     * Take the registration request and register the account
     *
     * @param account the account to create
     * @return the created account
     */
    @Override
    public Account register(Account account) throws AccountExistsException {
        String username = account.getUsername();
        String email = account.getEmail();

        if (accountRepository.findByUsername(username).isPresent()) {
            throw new AccountExistsException("The username " + username + " already exists", false);
        } else if (accountRepository.findByEmail(email).isPresent()) {
            throw new AccountExistsException("The e-mail " + email + " already exists", true);
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);

        return account;
    }
}
