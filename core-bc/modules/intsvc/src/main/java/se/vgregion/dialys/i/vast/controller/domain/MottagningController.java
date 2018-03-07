package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.vgregion.dialys.i.vast.jpa.requisitions.Ansvarig;
import se.vgregion.dialys.i.vast.jpa.requisitions.Mottagning;
import se.vgregion.dialys.i.vast.repository.MottagningRepository;

import java.util.List;

@Controller
@RequestMapping("/mottagning")
public class MottagningController {


    @Autowired
    private MottagningRepository mottagningRepository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Mottagning> getMottagnings() {
        return mottagningRepository.findAll();
    }
}
