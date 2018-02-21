package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.requisitions.*;
import se.vgregion.dialys.i.vast.repository.PatientRepository;
import se.vgregion.dialys.i.vast.service.PatientFinder;
import se.vgregion.dialys.i.vast.util.ReflectionUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final static int pageSize = 20;

    @Autowired
    private PatientRepository patientRepository;

    /*@Autowired
    private CustomPatientRepository customPatientRepository;*/

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Patient> getPatients() {
        List<String> sort = Arrays.asList("pnr");
        List<Patient> result = new ArrayList<>();
        patientRepository.findAll().forEach(i -> result.add(i));
        return result;
    }

    static Pageable makePageable(Integer requestedPage, String sort, Boolean asc) {
        Sort.Order pnrOrder = new Sort.Order(Sort.Direction.ASC, "pnr").ignoreCase();
        Sort.Order lastNameOrder = new Sort.Order(Sort.Direction.ASC, "efternamn").ignoreCase();

        Sort finalSort;
        if (sort != null && sort.length() > 0) {
            Sort.Order dynamicSort;

            Class<?> type = ReflectionUtil.getDeclaredField(sort, Patient.class).getType();

            if (type.equals(String.class)) {
                dynamicSort = new Sort.Order(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sort).ignoreCase();
            } else {
                dynamicSort = new Sort.Order(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sort);
            }
            finalSort = new Sort(dynamicSort, pnrOrder, lastNameOrder);
        } else {
            finalSort = new Sort(pnrOrder, lastNameOrder);
        }

        Pageable pageable = new PageRequest(requestedPage == null ? 0 : requestedPage, pageSize,
                finalSort);
        return pageable;
    }

    @Autowired
    PatientFinder patientFinder;

    @RequestMapping(value = "filter", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public Page<Patient> getPatients(@RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "query", required = false) String query,
                                     @RequestParam(value = "sort", required = false) String sort,
                                     @RequestParam(value = "asc", required = false) boolean asc) {

        Pageable pageable = makePageable(page, sort, asc);

        /*Iterable<Patient> examples = new ArrayList<>();
        patientRepository.findAll(examples, makePageable(page, sort, asc));
        Example<? extends Patient> example = new Example<>();
        patientRepository.count(example);*/

        return patientFinder.search(query, pageable);
    }

    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer patientId) {
        patientRepository.delete(patientId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Integer id) {
        Patient user = patientRepository.findOne(id);

        return ResponseEntity.ok(user);
    }

    public static void main(String[] args) throws IntrospectionException {
        //Patient patient = new Patient();
        /*BeanInfo bi = Introspector.getBeanInfo(Patient.class);
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            System.out.println(pd.getName() + ": " + pd.getPropertyType().getSimpleName().toLowerCase() + ";");
        }*/
        //makeForumBuilderPropertyMapping(Patient.class, "data");
        //makeCopyDataCode(Patient.class, "formModel", "data");
        makeTypeScriptVersion(Ansvarig.class);
    }

    public static void makeTypeScriptVersion(Class ofThat) throws IntrospectionException {
        BeanInfo bi = Introspector.getBeanInfo(ofThat);
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals("class")) continue;
            System.out.println(pd.getName() + ": "
                    +  (isClassCollection(pd.getPropertyType()) ? "Array" :
                    pd.getPropertyType().getSimpleName().toLowerCase()) + ";");
        }
    }

    public static boolean isClassCollection(Class c) {
        return Collection.class.isAssignableFrom(c) /*|| Map.class.isAssignableFrom(c)*/;
    }

    public static void makeForumBuilderPropertyMapping(Class<?> forThatType, String withBeanName) throws IntrospectionException {
        BeanInfo bi = Introspector.getBeanInfo(forThatType);
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        // 'agarform': [this.data.agarform]
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals("class")) continue;
            System.out.println("'"+pd.getName() + "': [this." + withBeanName + "." + pd.getName() + "],");
        }
    }

    public static void makeCopyDataCode(Class<?> forThatType, String fromVarName, String toVarName) throws IntrospectionException {
        BeanInfo bi = Introspector.getBeanInfo(forThatType);
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        // 'agarform': [this.data.agarform]
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals("class")) continue;
            System.out.println(toVarName + "." + pd.getName() + " = " + fromVarName + "." + pd.getName() + ";");
        }
    }

}
