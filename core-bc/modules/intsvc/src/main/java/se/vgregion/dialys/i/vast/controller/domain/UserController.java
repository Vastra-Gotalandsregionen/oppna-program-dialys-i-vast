package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {
        System.out.println("getUsers");
        return userRepository.findAllByOrderByUserName();
    }

    @Transactional
    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        // return ResponseEntity.ok(userRepository.save(user));
        user = userRepository.save(user);
        return ResponseEntity.ok(userRepository.findOne(user.getUserName()));
    }

    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
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

    /*@RequestMapping(value = "/{userName}/thumbnailPhoto", method = RequestMethod.GET, produces = "image/jpg")
    public ResponseEntity<byte[]> getUserThumbnailPhoto(@PathVariable("userName") String userName) {
        User user = userRepository.findOne(userName);

        return ResponseEntity.ok(user.getThumbnailPhoto());
    }*/
}
