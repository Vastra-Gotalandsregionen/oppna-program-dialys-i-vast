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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.vgregion.dialys.i.vast.jpa.AbstractEntity;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.json.LoginRequest;
import se.vgregion.dialys.i.vast.repository.UserRepository;
import se.vgregion.dialys.i.vast.service.JwtUtil;
import se.vgregion.dialys.i.vast.service.LdapLoginService;
import se.vgregion.dialys.i.vast.util.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        User fromLdap = null;
        try {
            fromLdap = ldapLoginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            // Do nothing.
        }

        User fromDb = userRepository.findOneByLowercaseUserName(loginRequest.getUsername().toLowerCase());

        if (fromDb != null) {
            if (!AbstractEntity.equals("Aktiv", fromDb.getStatus())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if (fromLdap != null) {
                String token = JwtUtil.createToken(fromDb);
                syncUserInformation(fromLdap, fromDb);
                // System.out.println("Login-Token is " + token);
                return ResponseEntity.ok(token);
            } else {
                return loginWithLocalUser(loginRequest, fromDb);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Transactional
    ResponseEntity<String> loginWithLocalUser(LoginRequest loginRequest, User user) {
        if (user.getPasswordEncryptionFlag()) {
            if (!PasswordEncoder.getInstance().matches(loginRequest.getPassword(), user.getPassWord())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            if (!AbstractEntity.equals(user.getPassWord(), loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                user.setPassWord(PasswordEncoder.getInstance().encodePassword(user.getPassWord()));
                user.setPasswordEncryptionFlag(true);
                userRepository.save(user);
            }
        }
        return ResponseEntity.ok(JwtUtil.createToken(user));
    }

    @Transactional
    void syncUserInformation(User fromLdapVersion, User toDbVersion) {
        if (!AbstractEntity.equals(fromLdapVersion.getName(), toDbVersion.getName())) {
            toDbVersion.setName(fromLdapVersion.getName());
            userRepository.save(toDbVersion);
        }
    }


    @RequestMapping(value = "/renew", method = RequestMethod.POST)
    public ResponseEntity<String> renewJwt(@RequestBody String jwt) {

        try {
            DecodedJWT decodedJWT = JwtUtil.verify(jwt);

            User user = userRepository.findOne(decodedJWT.getSubject());

            // String[] roles = getRoles(user);

            // String token = JwtUtil.createToken(user.getUserName(), user.getName(), user.getSjukskoterska(), user.getAdmin(), user.getPharmaceut(), roles);

            String token = JwtUtil.createToken(user);

            return ResponseEntity.ok(token);
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    private String[] getRoles(User user) {
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

/*
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
*/
}
