package ie.ul.microservices.kernel.server.authentication.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * This class represents an account used to authenticate into the system
 */
@Entity
public class Account {
    /**
     * The user's username
     */
    @Id
    @NotNull
    private String username;
    /**
     * The user's password (encrypted)
     */
    @NotNull
    private String password;
    /**
     * The user's email address
     */
    @Column(name="email", unique = true)
    @NotNull
    private String email;

    /**
     * Construct a default account
     */
    public Account() {
        this(null, null, null);
    }

    /**
     * Create an account instance
     * @param username the username of the account
     * @param password the password of the account
     * @param email the email of the account
     */
    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Get the user's username
     * @return username of the account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the account
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password of the account
     * @return account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the account's password
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the account's e-mail address
     * @return e-mail address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the account
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
