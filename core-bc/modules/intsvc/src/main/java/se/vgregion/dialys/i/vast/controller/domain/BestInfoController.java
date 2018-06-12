package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestInfo;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad;
import se.vgregion.dialys.i.vast.repository.BestInfoRepository;
import se.vgregion.dialys.i.vast.repository.PDArtikelRepository;
import se.vgregion.dialys.i.vast.repository.PdRepository;

@RestController
@RequestMapping("/bestInfo")
public class BestInfoController {

    @Autowired
    private BestInfoRepository bestInfoRepository;

    @Autowired
    private PDArtikelRepository pdArtikelRepository;

    @Autowired
    private PdRepository pdRepository;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BestInfo get(@PathVariable("id") Integer id) {
        return bestInfoRepository.findOne(id);
    }

    //@PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<BestInfo> save(@RequestBody BestInfo bestInfo) {
        for (BestPDRad bestPDRad : bestInfo.getBestPDRads()) {
            bestPDRad.setBestInfo(bestInfo);
            bestPDRad.setPdArtikel(pdArtikelRepository.findOne(bestPDRad.getPDArtikelID()));
        }
        bestInfo.setPd(pdRepository.findOne(bestInfo.getPdid()));
        return ResponseEntity.ok(bestInfoRepository.save(bestInfo));
    }

}
