package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestInfo;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad;
import se.vgregion.dialys.i.vast.repository.BestInfoRepository;
import se.vgregion.dialys.i.vast.repository.BestRadRepository;
import se.vgregion.dialys.i.vast.vymodel.BestArtikelRad;

import java.util.List;

@RestController
@RequestMapping("/bestInfo")
public class BestInfoController {

    @Autowired
    private BestInfoRepository bestInfoRepository;

/*    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<BestArtikelRad> getBestallningRaderInfo(@PathVariable("id") Integer id) {
        return bestRadRepository.getBestArtikelRads(id);
    }*/

    //@PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<BestInfo> saveUser(@RequestBody BestInfo bestInfo) {
        for (BestPDRad bestPDRad : bestInfo.getBestPDRads()) {
            bestPDRad.setBestInfo(bestInfo);
        }
        return ResponseEntity.ok(bestInfoRepository.save(bestInfo));
    }

}
