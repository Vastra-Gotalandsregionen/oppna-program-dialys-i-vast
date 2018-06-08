package se.vgregion.dialys.i.vast.controller.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.Artikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.Flik;
import se.vgregion.dialys.i.vast.jpa.requisitions.FlikRot;
import se.vgregion.dialys.i.vast.jpa.requisitions.Grupp;
import se.vgregion.dialys.i.vast.repository.ArtikelRepository;
import se.vgregion.dialys.i.vast.repository.FlikRepository;
import se.vgregion.dialys.i.vast.repository.FlikRotRepository;
import se.vgregion.dialys.i.vast.repository.GruppRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/flikrot")

public class FlikRotController {

    @Autowired
    private FlikRotRepository flikRotRepository;

    @Autowired
    private FlikRepository flikRepository;

    @Autowired
    private GruppRepository gruppRepository;

    @Autowired
    private ArtikelRepository artikelRepository;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<FlikRot> getAll() {
        return flikRotRepository.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FlikRot get(@PathVariable("id") String id) {
        return flikRotRepository.fetch(id);
    }

    //@PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @Transactional
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<FlikRot> save(@RequestBody FlikRot flikRot) {

        IdToItemMap previousIds = toIds(flikRotRepository.fetch(flikRot.getId()));
        IdToItemMap incommingIds = toIds(flikRot);

        FlikRot saved = flikRotRepository.save(flikRot);

        previousIds.artikels.forEach((i, a) -> {
            if (!incommingIds.artikels.keySet().contains(i)) {
                artikelRepository.delete(a.getId());
            }
        });

        previousIds.grupps.forEach((i, g) -> {
            if (!incommingIds.grupps.containsKey(i)) {
                remove(g);
            }
        });

        previousIds.fliks.forEach((i, f) -> {
            if (!incommingIds.fliks.containsKey(i)) {
                remove(f);
            }
        });

        return ResponseEntity.ok(saved);
    }

    private void remove(Flik flik) {
        for (Grupp grupp : flik.getGrupps()) {
            for (Artikel artikel : grupp.getArtikels()) {
                artikelRepository.delete(artikel.getId());
            }
            gruppRepository.delete(grupp.getId());
        }
        flikRepository.delete(flik.getId());
    }

    private void remove(Grupp grupp) {
        for (Artikel artikel : grupp.getArtikels()) {
            artikelRepository.delete(artikel.getId());
        }
        gruppRepository.delete(grupp.getId());
    }

    private IdToItemMap toIds(FlikRot flikRot) {
        IdToItemMap incommingIds = new IdToItemMap();
        for (Flik flik : flikRot.getFliks()) {
            flik.setFlikRot(flikRot);
            incommingIds.fliks.put(flik.getId(), flik);
            for (Grupp grupp : flik.getGrupps()) {
                grupp.setFlik(flik);
                incommingIds.grupps.put(grupp.getId(), grupp);
                for (Artikel artikel : grupp.getArtikels()) {
                    artikel.setGrupp(grupp);
                    incommingIds.artikels.put(artikel.getId(), artikel);
                }
            }
        }
        return incommingIds;
    }

    private class IdToItemMap {
        public HashingMap<Flik> fliks = new HashingMap<>();
        public HashingMap<Grupp> grupps = new HashingMap<>();
        public HashingMap<Artikel> artikels = new HashingMap<>();

        private class HashingMap<T> extends HashMap<Integer, T> {
            @Override
            public T put(Integer key, T value) {
                if (key != null)
                    return super.put(key, value);
                return null;
            }
        }

    }

    /*public void update(FlikRot flikRot) {
        FlikRot fromDb = flikRotRepository.fetch(flikRot.getId());



        Map<Integer, Grupp> storedGrupps = new HashMap<>();
        Map<Integer, Flik> storedFliks = new HashMap<>();

    }*/

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
