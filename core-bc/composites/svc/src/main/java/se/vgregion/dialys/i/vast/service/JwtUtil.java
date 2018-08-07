package se.vgregion.dialys.i.vast.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Patrik Björk
 */
@Service
public class JwtUtil {

    static String secret;

    @Value("${jwt.sign.secret}")
    private String jwtSignSecret;

    private static int MINUTES_AGE = 30;

    @PostConstruct
    public void init() {
        secret = jwtSignSecret;
    }

    public static String createTokenOld(String userName, String displayName, Boolean sjukskoterska, Boolean admin, Boolean pharmaceut, String[] roles) {
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
                    .withClaim("sjukskoterska", sjukskoterska)
                    .withClaim("admin", admin)
                    .withClaim("pharmaceut", pharmaceut)
                    .withIssuedAt(now)
                    .withExpiresAt(timeAhead)
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String createToken(User user) {
        try {
            Date timeAhead = Date.from(Instant.now().plus(MINUTES_AGE, ChronoUnit.MINUTES));
            Date now = Date.from(Instant.now());

            List<String> roles = new ArrayList<>();
            if (noValueIsFalse(user.getSjukskoterska())) {
                roles.add("sjukskoterska");
            }
            if (noValueIsFalse(user.getAdmin())) {
                roles.add("admin");
            }
            if (noValueIsFalse(user.getPharmaceut())) {
                roles.add("pharmaceut");
            }

            String r = JWT.create()
                    .withSubject(user.getUserName())
                    .withArrayClaim("roles", toTextArray(roles))
                    // .withClaim("user", (String)objectMapper.writeValueAsString(user))
                    .withClaim("pharmaceut", "" + noValueIsFalse(user.getPharmaceut()))
                    .withClaim("sjukskoterska", "" + noValueIsFalse(user.getSjukskoterska()))
                    .withClaim("admin", "" + noValueIsFalse(user.getAdmin()))
                    .withClaim("displayName", user.getName())
                    .withIssuedAt(now)
                    .withExpiresAt(timeAhead)
                    .sign(Algorithm.HMAC256(secret));
            return r;
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    private static boolean noValueIsFalse(Boolean thatMightBeNull) {
        if (thatMightBeNull == null) {
            return false;
        }
        return thatMightBeNull;
    }

    public static DecodedJWT verify(String jwtToken) throws JWTVerificationException {
        if (jwtToken.startsWith("Bearer")) {
            jwtToken = jwtToken.substring("Bearer".length()).trim();
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            return verifier.verify(jwtToken);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] toTextArray(List<String> that) {
        String[] result = new String[that.size()];
        int i = 0;
        for (String s : that) {
            result[i++] = s;
        }
        return result;
    }

}
