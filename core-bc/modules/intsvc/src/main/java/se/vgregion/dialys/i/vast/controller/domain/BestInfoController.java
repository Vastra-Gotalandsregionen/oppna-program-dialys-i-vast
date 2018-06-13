package se.vgregion.dialys.i.vast.controller.domain;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestInfo;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad;
import se.vgregion.dialys.i.vast.repository.BestInfoRepository;
import se.vgregion.dialys.i.vast.repository.BestRadRepository;
import se.vgregion.dialys.i.vast.repository.PDArtikelRepository;
import se.vgregion.dialys.i.vast.repository.PdRepository;
import se.vgregion.dialys.i.vast.service.JwtUtil;

@RestController
@RequestMapping("/bestInfo")
public class BestInfoController {

    @Autowired
    private BestInfoRepository bestInfoRepository;

    @Autowired
    private PDArtikelRepository pdArtikelRepository;

    @Autowired
    private PdRepository pdRepository;

    @Autowired
    private BestRadRepository bestRadRepository;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BestInfo get(@PathVariable("id") Integer id) {
        return bestInfoRepository.findOne(id);
    }

    //@PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<BestInfo> save(@RequestBody BestInfo bestInfo, @RequestHeader(value = "Authorization") String authorization) {
        String jwtToken = authorization.substring("Bearer".length()).trim();
        DecodedJWT jwt = JwtUtil.verify(jwtToken);
        bestInfo.setUtskrivare(jwt.getSubject());

        /*if (bestInfo.getId() != null) {
            for (BestPDRad bestPDRad : bestInfo.getBestPDRads()) {
                if (bestPDRad.getId() != null) {
                    BestPDRad fromDb = bestRadRepository.getOne(bestPDRad.getId());
                    if (fromDb != null) {
                        bestRadRepository.delete(fromDb);
                    }
                }
            }
        }*/
        for (BestPDRad bestPDRad : bestInfo.getBestPDRads()) {
            bestPDRad.setBestInfo(bestInfo);
            bestPDRad.setPdArtikel(pdArtikelRepository.findOne(bestPDRad.getPDArtikelID()));
            // bestPDRad.setId(null);
        }
        bestInfo.setPd(pdRepository.findOne(bestInfo.getPdid()));
        return ResponseEntity.ok(bestInfoRepository.save(bestInfo));
    }

}
