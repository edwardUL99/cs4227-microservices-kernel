package ie.ul.microservices.kernel.server.authentication;

import java.time.LocalDateTime;

/**
 * This class represents a JWT token
 */
public class Token {
    /**
     * The raw string token
     */
    private String raw;
    /**
     * The token's username
     */
    private String username;
    /**
     * The timestamp of when the token expires
     */
    private LocalDateTime expiration;

    /**
     * Create a default empty token
     */
    public Token() {
        this(null, null, null);
    }

    /**
     * Construct a token instance
     * @param raw the raw String JWT token
     * @param username the username represented by the token
     * @param expiration the timestamp of when the token expires
     */
    public Token(String raw, String username, LocalDateTime expiration) {
        this.raw = raw;
        this.username = username;
        this.expiration = expiration;
    }

    /**
     * Get the raw JWT token
     * @return raw JWT token
     */
    public String getRaw() {
        return raw;
    }

    /**
     * Set the raw JWT token
     * @param raw the raw JWT token
     */
    public void setRaw(String raw) {
        this.raw = raw;
    }

    /**
     * Get the username represented by the token
     * @return username represented by the token
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the token's username
     * @param username the username represented by the token
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the timestamp of when the token expires
     * @return the expiry timestamp of the token
     */
    public LocalDateTime getExpiration() {
        return expiration;
    }

    /**
     * Set the expiration of the token
     * @param expiration the expiration of the token
     */
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    /**
     * Determines if the token is expired.
     * @return true if expired, false if not
     */
    public boolean isExpired() {
        if (this.expiration != null) {
            return LocalDateTime.now().isAfter(this.expiration);
        } else {
            return false;
        }
    }
}
