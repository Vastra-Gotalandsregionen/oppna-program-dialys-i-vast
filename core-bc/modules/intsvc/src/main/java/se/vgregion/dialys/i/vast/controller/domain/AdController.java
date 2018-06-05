package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.service.LdapLoginService;

import javax.security.auth.login.FailedLoginException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/ad")
public class AdController {

    @Autowired
    private LdapLoginService ldapLoginService;

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUser(@PathVariable("userName") String userName) {
        User user = null;
        try {
            user = ldapLoginService.findUser(userName);
        } catch (Exception e) {
             return ResponseEntity.ok(Collections.EMPTY_LIST);
        }
        if (user == null) {
            return ResponseEntity.ok(Collections.EMPTY_LIST);
        }
        return ResponseEntity.ok(Arrays.asList(user));
    }

}
