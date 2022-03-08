package ie.ul.microservices.kernel.server.authentication;

import ie.ul.microservices.kernel.server.authentication.models.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utilities for creating and verifying JWT tokens
 */
@Component
public class JWT {
    /**
     * The property file secret
     */
    @Value("${jwt.secret}")
    private String secretString;

    /**
     * Get the key used for signing
     * @return the key for signing JWT tokens
     */
    private Key getKey() {
        return Keys.hmacShaKeyFor(secretString.getBytes());
    }

    /**
     * Parse the raw JWT token into a Token object
     * @param token the raw JWT token contained in the request
     * @return the parsed token
     * @throws JWTExpiredException if the token is expired
     * @throws JwtException if it can't be parsed
     */
    public Token parse(String token) throws JWTExpiredException, JwtException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            LocalDateTime expiration = claims.getExpiration().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            return new Token(token, username, expiration);
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
            throw new JWTExpiredException();
        }
    }

    /**
     * Generate a token for the given account
     * @param account the account to generate the token for
     * @param expiry the time in hours to expire the token at. If null or -1, default 2 hours is used
     * @return the generated token
     */
    public Token generateToken(Account account, Long expiry) {
        Map<String, Object> claims = new HashMap<>();
        expiry = (expiry == null || expiry == -1L) ? 2:expiry;

        LocalDateTime expiration = LocalDateTime.now().plusHours(expiry);
        String username = account.getUsername();
        String token = Jwts.builder().setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getKey()).compact();

        return new Token(token, username, expiration);
    }
}
