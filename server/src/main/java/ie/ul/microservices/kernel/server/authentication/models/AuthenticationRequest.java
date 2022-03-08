package ie.ul.microservices.kernel.server.authentication.models;

import javax.validation.constraints.NotNull;

/**
 * This class represents a request to authenticate users
 */
public class AuthenticationRequest {
    /**
     * The username of the account to authenticate
     */
    @NotNull
    private String username;
    /**
     * The password of the account to authenticate
     */
    @NotNull
    private String password;
    /**
     * The expiry of the token in hours
     */
    private Long expiry;

    /**
     * Create a default authentication request
     */
    public AuthenticationRequest() {
        this(null, null, 2L);
    }

    /**
     * Create an AuthenticationRequest
     * @param username the username of the user to authenticate
     * @param password the password to authenticate with
     * @param expiry the expiry of the token in hours
     */
    public AuthenticationRequest(String username, String password, Long expiry) {
        this.username = username;
        this.password = password;
        this.expiry = expiry;
    }

    /**
     * Retrieve the username used to authenticate with
     * @return authentication username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username to authenticate with
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password used for authentication
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the account to authenticate
     * @param password the password to authenticate with
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the token expiry in hours
     * @return token expiry
     */
    public Long getExpiry() {
        return expiry;
    }

    /**
     * Set the token expiry in hours
     * @param expiry the new token expiry
     */
    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }
}
