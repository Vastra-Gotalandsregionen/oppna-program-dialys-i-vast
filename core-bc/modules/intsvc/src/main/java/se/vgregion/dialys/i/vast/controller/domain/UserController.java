package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.AbstractEntity;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.repository.MottagningRepository;
import se.vgregion.dialys.i.vast.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MottagningRepository mottagningRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    public List<User> getUsers(@RequestParam(value = "userNameFilter", required = false) String userNameFilter) {
        if (userNameFilter != null && !"".equals(userNameFilter.trim())) {
            return userRepository.findAllByUserName(userNameFilter);
        }
        return userRepository.findAllByOrderByUserName();
    }

    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<User> save(@RequestBody User user) {
        //System.out.println(user.getAnstallnings());
        return saveUserImp(user);
    }

    @Transactional
    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    protected ResponseEntity<User> saveUserImp(@RequestBody User user) {
        User fromDb = userRepository.findOne(user.getUserName());
        if (fromDb != null) {
            if (!AbstractEntity.equals(fromDb.getPassWord(), user.getPassWord())) {
                user.setPasswordEncryptionFlag(false);
            }
        }
        user = userRepository.save(user);
        return ResponseEntity.ok(userRepository.findOne(user.getUserName()));
    }

    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "/{userName}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("userName") String userName) {
        userRepository.delete(userName);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("userName") String userName) {
        User user = userRepository.findOne(userName);

        return ResponseEntity.ok(user);
    }

}
