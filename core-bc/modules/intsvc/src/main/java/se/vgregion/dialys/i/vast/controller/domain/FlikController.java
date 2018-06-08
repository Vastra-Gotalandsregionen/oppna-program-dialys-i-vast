package se.vgregion.dialys.i.vast.controller.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.Flik;
import se.vgregion.dialys.i.vast.repository.FlikRepository;

import java.util.List;

@RestController
@RequestMapping("/flik")

public class FlikController {

    @Autowired
    FlikRepository flikRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Flik> getAll(@RequestParam(value = "typ", required = false) String typ) {
        if (typ != null && !typ.trim().isEmpty())
            return flikRepository.findFliksByTypAndAktiv(typ, true);
        List<Flik> result = flikRepository.findAll();
        for (Flik flik : result) {
            System.out.println("flik.getGrupps().size() " + flik.getGrupps().size());
        }
        return result;
    }

    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "save", method = RequestMethod.PUT)
    public ResponseEntity<Flik> save(@RequestBody Flik mottagning) {
        Flik saved = flikRepository.save(mottagning);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "list", method = RequestMethod.PUT)
    public ResponseEntity<List<Flik>> save(@RequestBody List<Flik> fliks) {
        List<Flik> saved = flikRepository.save(fliks);
        return ResponseEntity.ok(saved);
    }

}
