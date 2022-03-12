package ie.ul.microservices.kernel.server.authentication;

import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingDispatcher;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptorChain;
import ie.ul.microservices.kernel.api.interception.mapping.SingleMappingInterceptor;
import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.server.authentication.models.Account;
import ie.ul.microservices.kernel.server.authentication.repositories.AccountRepository;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * This class represents an interceptor to perform authentication before mapping takes place
 */
@Component
@DependsOn("Interception") // needs interception to be setup first
public class AuthenticationInterceptor extends SingleMappingInterceptor {
    /**
     * JWT instance for managing JWT tokens
     */
    private final JWT jwt;

    /**
     * The account repository to query if the account exists
     */
    private final AccountRepository accountRepository;

    /**
     * To log interception events
     */
    private final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    /**
     * The start of the bearer token
     */
    private static final String BEARER = "Bearer ";

    /**
     * Create the interceptor
     *
     * @param jwt the jwt instance to allow parsing of JWT tokens
     * @param accountRepository repository to query account existence
     */
    @Autowired
    protected AuthenticationInterceptor(JWT jwt, AccountRepository accountRepository) {
        super(MappingDispatcher.RegistrationStrategy.BEFORE);
        this.jwt = jwt;
        this.accountRepository = accountRepository;
    }



    /**
     * Authenticate the given request
     * @param request the request to authenticate
     * @return true if authenticated false if not authenticated
     */
    private boolean authenticate(APIRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.contains(BEARER)) {
            String bearer = authorization.substring(authorization.indexOf(BEARER) + BEARER.length());

            try {
                Token parsed = jwt.parse(bearer);

                if (parsed != null) {
                    String username = parsed.getUsername();
                    Account account = accountRepository.findByUsername(username).orElse(null);

                    return account != null;
                }
            } catch (JWTExpiredException | JwtException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    /**
     * This interception point is called before the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the next interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onBeforeMapping(MappingContext context, MappingInterceptorChain chain) {
        APIRequest request = context.getRequest();

        if (authenticate(request)) {
            log.info("JWT Authentication Succeeded. Proceeding with request mapping");
            chain.next(context);
        } else {
            log.error("JWT Authentication Failed. Ending request mapping");
            context.setResponse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }
}
