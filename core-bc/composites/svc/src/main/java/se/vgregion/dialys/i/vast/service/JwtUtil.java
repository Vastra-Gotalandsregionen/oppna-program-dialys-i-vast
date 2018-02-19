package se.vgregion.dialys.i.vast.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Patrik Björk
 */
@Service
public class JwtUtil {

    private static String secret;

    @Value("${jwt.sign.secret}")
    private String jwtSignSecret;

    private static int MINUTES_AGE = 30;

    @PostConstruct
    public void init() {
        secret = jwtSignSecret;
    }

    public static String createToken(String userName, String displayName, String[] roles) {
        try {
            Date timeAhead = Date.from(Instant.now().plus(MINUTES_AGE, ChronoUnit.MINUTES));
            Date now = Date.from(Instant.now());

            if (roles == null) {
                roles = new String[0];
            }

            return JWT.create()
                    .withSubject(userName != null ? String.valueOf(userName) : null)
                    .withArrayClaim("roles", roles)
                    .withClaim("displayName", displayName)
                    .withIssuedAt(now)
                    .withExpiresAt(timeAhead)
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DecodedJWT verify(String jwtToken) throws JWTVerificationException {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            return verifier.verify(jwtToken);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
