package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.Mottagning;
import se.vgregion.dialys.i.vast.repository.MottagningRepository;

import java.util.List;

@Controller
@RequestMapping("/mottagning")
public class MottagningController {

    @Autowired
    private MottagningRepository mottagningRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Mottagning> getAll() {
        return mottagningRepository.findAll();
    }

    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        mottagningRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Void> save(@RequestBody Mottagning mottagning) {
        mottagningRepository.save(mottagning);
        return ResponseEntity.ok().build();
    }

}
