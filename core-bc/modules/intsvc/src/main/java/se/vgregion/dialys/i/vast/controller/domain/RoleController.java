package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.Role;
import se.vgregion.dialys.i.vast.repository.RoleRepository;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> getUsers() {
        return roleRepository.findAll();
    }

    @Transactional
    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Role> save(@RequestBody Role user) {
        user = roleRepository.save(user);
        return ResponseEntity.ok(roleRepository.findOne(user.getId()));
    }

    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        roleRepository.delete(id);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Role> get(@PathVariable("id") Integer id) {
        Role user = roleRepository.findOne(id);

        return ResponseEntity.ok(user);
    }

}
