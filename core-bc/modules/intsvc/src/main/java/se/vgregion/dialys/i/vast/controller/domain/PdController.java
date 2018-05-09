package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.Pd;
import se.vgregion.dialys.i.vast.repository.PdRepository;
import se.vgregion.dialys.i.vast.service.PatientFinder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pd")
public class PdController {

    private final static int pageSize = 20;

    @Autowired
    private PdRepository pdRepository;

    @Autowired
    private PatientFinder patientFinder;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Pd> getPds() {
        List<Pd> result = new ArrayList<>();
        pdRepository.findAll().forEach(i -> result.add(i));
        return result;
    }

    //@PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @Transactional
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Pd> save(@RequestBody Pd pd) {
        int i = 0;
        for (PDArtikel pdArtikel : pd.getPdArtikels()) {
            pdArtikel.setPd(pd);
            System.out.println(i + ": " + pdArtikel.getId() + " = " + pdArtikel);
            // Todo: Check if this is really the way to do this.
        }
        try {
            if (pd.getId() == null) {
                System.out.println("before getLatestPd");
                Pd previous = patientFinder.getLatestPd(pd.getPatient());
                if (previous != null) {
                    pd.setErsatter(previous.getId());
                }
            } else {
                System.out.println("before getOne");
                Pd fromDb = get(pd.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(pdRepository.save(pd));
    }

    /*@PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")*/
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer patientId) {
        pdRepository.delete(patientId);

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pd get(@PathVariable("id") Integer id) {
        Pd user = pdRepository.findOne(id);
        return user;
    }


}
