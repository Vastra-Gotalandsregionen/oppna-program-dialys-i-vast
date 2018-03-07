package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.vgregion.dialys.i.vast.jpa.requisitions.Ansvarig;
import se.vgregion.dialys.i.vast.repository.AnsvarigRepository;

import java.util.List;

@Controller
@RequestMapping("/ansvarig")
public class AnsvarigController {


    @Autowired
    private AnsvarigRepository ansvarigRepository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Ansvarig> getAnsvarigs() {
        return ansvarigRepository.findAll();
    }
}
