package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.Dummy;
import se.vgregion.dialys.i.vast.repository.DummyRepository;
import se.vgregion.dialys.i.vast.service.DummyOperations;

import java.util.Random;

@Controller
@RequestMapping("/ao3")
public class DummyController {

    @Autowired
    private DummyRepository dummyRepository;

    @Autowired
    private DummyOperations dummyOperations;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Page<Dummy> getAo3s(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "page", required = false) Integer page) {

        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "foretagsnamn").ignoreCase();
        Sort sort = new Sort(order);

        Pageable pageable = null;

        if (pageSize != null && page != null) {
            pageable = new PageRequest(page, pageSize, sort);
        } else {
            pageable = new PageRequest(0, Integer.MAX_VALUE, sort);
        }

        return dummyRepository.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Dummy getAo3(@PathVariable("id") Integer id) {
        return dummyRepository.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    public ResponseEntity<Dummy> saveAo3(@RequestBody Dummy dummy) {

        if (dummy.getId() == null) {
            // New entity.
            dummy.setId(Math.abs(new Random().nextInt()));
        }

        dummyOperations.save(dummy);
        return ResponseEntity.ok(dummy);
    }
}
