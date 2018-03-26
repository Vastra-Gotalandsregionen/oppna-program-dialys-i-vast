package se.vgregion.dialys.i.vast.controller.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.Artikel;
import se.vgregion.dialys.i.vast.repository.ArtikelRepository;

import java.util.List;

@RestController
@RequestMapping("/artikels")

public class ArtikelController {
    @Autowired
    ArtikelRepository artikelRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Artikel> getArtikelsForRekvisition(@PathVariable("id") Integer id)
    {
        List<Artikel> rest = artikelRepository.getArtikelsForRekvisition(id);
        return rest;
    }

}
