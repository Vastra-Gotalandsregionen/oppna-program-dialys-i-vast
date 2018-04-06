package se.vgregion.dialys.i.vast.controller.domain;


import org.springframework.beans.factory.annotation.Autowired;
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
        return flikRepository.findAll();
    }

}
