package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.service.GenericJpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/generic")
public class GenericController {

    @Autowired
    private GenericJpa genericJpa;

    /**
     * @return
     * @RequestMapping(value = "/{id}", method = RequestMethod.GET)
     * public Patient getPatient(@PathVariable("id") Integer id) {
     */
    @ResponseBody
    @RequestMapping(value = "/foo/{baa}/", method = RequestMethod.GET)
    public List<Long> foo(@PathVariable("baa") String entity) {
        System.out.println(entity);
        return new ArrayList<>();
    }

    @ResponseBody
    @RequestMapping(value = "/counts/{entity}/{relation}/iids/{ids}", method = RequestMethod.GET)
    public List<Long> getCountsByIntegerIds(@PathVariable("entity") String entity,
                                @PathVariable("relation") String relation,
                                @PathVariable("ids") String ids) {
        List params = new ArrayList<>();
        for (String s : new ArrayList<>(Arrays.asList(ids.split("[^0-9]+")))) {
            if (s != null && !s.isEmpty()) {
                params.add(Integer.parseInt(s));
            }
        }
        System.out.println(params);
        return genericJpa.fetchCounts(entity, relation, params);
    }

    public static void main(String[] args) {
        System.out.println("i1i2".matches("(i\\d+)+"));

        List<Object> foo = new ArrayList<>(Arrays.asList("abc", "koo", 1, 2, 34));
        System.out.println(Arrays.asList(foo.toString().split("[^0-9a-öA-Ö]+")));
        System.out.println(Arrays.asList("123,456,789,9akdsfsd90".split("[^0-9]+")));
    }

    boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
