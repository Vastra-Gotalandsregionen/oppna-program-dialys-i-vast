package se.vgregion.dialys.i.vast.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.json.LoginRequest;
import se.vgregion.dialys.i.vast.repository.UserRepository;
import se.vgregion.dialys.i.vast.service.JwtUtil;
import se.vgregion.dialys.i.vast.service.LdapLoginService;
import se.vgregion.dialys.i.vast.util.PasswordEncoder;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Patrik Björk
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LdapLoginService ldapLoginService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Value("${impersonate.enabled}")
    private boolean impersonateEnabled;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = null;
            if (request.getHeader("iv-user") != null) {
                user = ldapLoginService.loginOffline(request.getHeader("iv-user"));
            } else {
                user = ldapLoginService.login(loginRequest.getUsername(), loginRequest.getPassword());
                /*if (user.getInactivated() != null && user.getInactivated()) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }*/
            }
            String[] roles = getRoles(user);
            String token = JwtUtil.createToken(user.getUserName(), user.getName(), user.getSjukskoterska(), user.getAdmin(), user.getPharmaceut(), roles);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            try {
                User user = userRepository.findOne(loginRequest.getUsername());
                if (user == null) {
                    throw new FailedLoginException("No such user: " + loginRequest.getUsername());
                }
                if (user.getPasswordEncryptionFlag()) {
                    if (!PasswordEncoder.getInstance().matches(loginRequest.getPassword(), user.getPassWord())) {
                        throw new FailedLoginException("Password dit not match with user i db either.");
                    }
                    String res = user.getStatus();
                    if(res.equals("Inaktiv")){
                        throw new FailedLoginException( loginRequest.getUsername()+" är inte aktiv.");
                    }

                } else {
                    if (!user.getPassWord().equals(loginRequest.getPassword())) {
                        throw new FailedLoginException("Password dit not match with user i db either.");
                    } else {
                        user.setPassWord(PasswordEncoder.getInstance().encodePassword(user.getPassWord()));
                        user.setPasswordEncryptionFlag(true);
                        userRepository.save(user);
                    }
                }
                String[] roles = getRoles(user);
                String token = JwtUtil.createToken(user.getUserName(), user.getName(), user.getSjukskoterska(), user.getAdmin(), user.getPharmaceut(), roles);
                return ResponseEntity.ok(token);
            } catch (Exception fle) {
                LOGGER.warn(e.getClass().getCanonicalName() + " - " + e.getMessage());
                LOGGER.warn(fle.getClass().getCanonicalName() + " - " + fle.getMessage());
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

    }

    @RequestMapping(value = "/renew", method = RequestMethod.POST)
    public ResponseEntity<String> renewJwt(@RequestBody String jwt) {

        try {
            DecodedJWT decodedJWT = JwtUtil.verify(jwt);

            User user = userRepository.findOne(decodedJWT.getSubject());

            String[] roles = getRoles(user);

            String token = JwtUtil.createToken(user.getUserName(), user.getName(), user.getSjukskoterska(), user.getAdmin(), user.getPharmaceut(), roles);

            return ResponseEntity.ok(token);
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    String[] getRoles(User user) {
        return new String[]{
                "ADMIN"
        };
        /*String roleName = user.getRole().name();

        String[] roles;
        if (Role.ADMIN.name().equals(roleName) && impersonateEnabled) {
            roles = new String[]{roleName, Role.IMPERSONATE.name()};
        } else {
            roles = new String[]{roleName};
        }
        return roles;*/
    }

    @RequestMapping(value = "/impersonate", method = RequestMethod.POST)
    public ResponseEntity<String> impersonate(@RequestBody User userToImpersonate) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwtToken = authorizationHeader.substring("Bearer".length()).trim();

        DecodedJWT jwt;
        try {
            jwt = JwtUtil.verify(jwtToken);

            List<String> roles = jwt.getClaim("roles").asList(String.class);

            if (roles.contains("IMPERSONATE")) {
                User impersonated = ldapLoginService.loginWithoutPassword(userToImpersonate.getUserName());

                String[] impersonatedRoles = getRoles(impersonated);

                String token = JwtUtil.createToken(impersonated.getUserName(), impersonated.getName(),
                        impersonated.getSjukskoterska(), impersonated.getAdmin(), impersonated.getPharmaceut(),
                        impersonatedRoles);

                return ResponseEntity.ok(token);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (FailedLoginException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
