package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.Pd;
import se.vgregion.dialys.i.vast.repository.ArtikelRepository;
import se.vgregion.dialys.i.vast.repository.PdRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pd")
public class PdController {

    private final static int pageSize = 20;

    @Autowired
    private PdRepository pdRepository;

    @Autowired
    private ArtikelRepository artikelRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Pd> getPds() {
        List<Pd> result = new ArrayList<>();
        pdRepository.findAll().forEach(i -> result.add(i));
        return result;
    }

    //@PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @Transactional
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Pd> savePd(@RequestBody Pd pd) {
        System.out.println("Försöker spara en pd");
        int i = 0;
        for (PDArtikel pdArtikel : pd.getPdArtikels()) {
            System.out.println(i + "" + pdArtikel);
            i++;
            System.out.println(" " + pdArtikel.getArtikel());
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
    public Pd getPatient(@PathVariable("id") Integer id) {
        Pd user = pdRepository.findOne(id);
        return user;
    }


}
