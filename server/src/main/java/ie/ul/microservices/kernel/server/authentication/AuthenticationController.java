package ie.ul.microservices.kernel.server.authentication;

import ie.ul.microservices.kernel.server.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a controller used to provide login and registration to the gateway
 */
@RestController
@RequestMapping(Constants.API_GATEWAY)
public class AuthenticationController {
    /**
     * The authentication service
     */
    private final AuthenticationService authenticationService;

    /**
     * Create a controller instance
     * @param authenticationService the service to use for authentication
     */
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticate the given request
     * @param request the request to authenticate
     * @return the response body
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        Token token = this.authenticationService.authenticate(request.getUsername(), request.getPassword(), request.getExpiry());

        if (token == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "invalid_credentials");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.ok(token);
        }
    }

    /**
     * Register the given account
     * @param account the account to register
     * @return the response body
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Account account) {
        try {
            Account registered = this.authenticationService.register(account);
            account.setPassword(null);

            return ResponseEntity.ok(registered);
        } catch (AccountExistsException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", (ex.existsByEmail()) ? "email_exists":"username_exists");

            return ResponseEntity.badRequest().body(response);
        }
    }
}
