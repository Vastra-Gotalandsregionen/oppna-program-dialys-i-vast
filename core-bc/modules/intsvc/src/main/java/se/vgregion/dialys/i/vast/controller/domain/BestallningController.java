package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.repository.BestRadRepository;
import se.vgregion.dialys.i.vast.vymodel.BestArtikelRad;

import java.util.List;

@RestController
@RequestMapping("/bestallning")
public class BestallningController {

    @Autowired
    private BestRadRepository bestRadRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<BestArtikelRad> getBestallningRaderInfo(@PathVariable("id") Integer id) {
        return bestRadRepository.getBestArtikelRads(id);
    }
}
