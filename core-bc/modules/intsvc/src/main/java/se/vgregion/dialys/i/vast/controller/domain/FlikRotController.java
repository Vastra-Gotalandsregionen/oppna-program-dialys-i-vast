package se.vgregion.dialys.i.vast.controller.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.Artikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.Flik;
import se.vgregion.dialys.i.vast.jpa.requisitions.FlikRot;
import se.vgregion.dialys.i.vast.jpa.requisitions.Grupp;
import se.vgregion.dialys.i.vast.repository.FlikRotRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/flikrot")

public class FlikRotController {

    @Autowired
    private FlikRotRepository flikRotRepository;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<FlikRot> getAll() {
        return flikRotRepository.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "default", method = RequestMethod.GET)
    public FlikRot getDefault() {
        return flikRotRepository.fetchDefault();
    }

    //@PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<FlikRot> save(@RequestBody FlikRot flikRot) {
        System.out.println(FlikRotController.class + " save()");
        for (Flik flik : flikRot.getFliks()) {
            flik.setFlikRot(flikRot);
            for (Grupp grupp : flik.getGrupps()) {
                grupp.setFlik(flik);
                for (Artikel artikel : grupp.getArtikels()) {
                    artikel.setGrupp(grupp);
                }
            }
        }
        FlikRot saved = flikRotRepository.save(flikRot);
        return ResponseEntity.ok(saved);
    }

    class FlikRotExt extends FlikRot {

        private List<Integer> deletedFlikIds = new ArrayList<>();

        private List<Integer> deletedGruppIds = new ArrayList<>();

        public List<Integer> getDeletedFlikIds() {
            return deletedFlikIds;
        }

        public void setDeletedFlikIds(List<Integer> deletedFlikIds) {
            this.deletedFlikIds = deletedFlikIds;
        }

        public List<Integer> getDeletedGruppIds() {
            return deletedGruppIds;
        }

        public void setDeletedGruppIds(List<Integer> deletedGruppIds) {
            this.deletedGruppIds = deletedGruppIds;
        }

    }


}
