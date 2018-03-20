package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.vgregion.dialys.i.vast.repository.BestRadRepository;
import se.vgregion.dialys.i.vast.vymodel.BestArtikelRad;

import java.util.List;

@Controller
@RequestMapping("/bestallning")
public class BestallningController {

    @Autowired
    private BestRadRepository bestRadRepository;

 /*   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<BestArtikelRad> getBestallningRaderInfo(@PathVariable("id") Integer id) {
        return bestRadRepository.bestArtikelrRader(id);
    }*/
}
