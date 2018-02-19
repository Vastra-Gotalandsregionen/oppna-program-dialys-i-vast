package se.vgregion.dialys.i.vast.controller.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import se.vgregion.dialys.i.vast.service.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Patrik Björk
 */
public class HttpUtil {

    public static String getUserIdFromRequest(HttpServletRequest request) {
        String userName;

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            userName = null;
        } else {

            String jwtToken = authorizationHeader.substring("Bearer".length()).trim();

            DecodedJWT jwt;
            jwt = JwtUtil.verify(jwtToken);

            userName = jwt.getSubject();
        }
        return userName;
    }
}
