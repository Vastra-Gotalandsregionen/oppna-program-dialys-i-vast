package se.vgregion.dialys.i.vast.controller.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.SearchLog;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestInfo;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad;
import se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.Patient;
import se.vgregion.dialys.i.vast.repository.PatientRepository;
import se.vgregion.dialys.i.vast.repository.SearchLogRepository;
import se.vgregion.dialys.i.vast.service.PatientFinder;
import se.vgregion.dialys.i.vast.util.ReflectionUtil;

import javax.annotation.PostConstruct;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final static int pageSize = 20;

    @Autowired
    private PatientRepository patientRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    void init() {
        //objectMapper.addMixIn(Pd.class, PdMixin.class);
        objectMapper.addMixIn(BestInfo.class, BestInfoMixin.class);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Patient> getPatients() {
        List<Patient> result = new ArrayList<>();
        patientRepository.findAll().forEach(i -> result.add(i));
        return result;
    }

    //@PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Patient> saveUser(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientRepository.save(patient));
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

    @Autowired
    SearchLogRepository searchLogRepository;

    @PreAuthorize("@authService.isLoggedIn(authentication)")
    @RequestMapping(value = "filter", method = RequestMethod.GET)
    @Transactional
    public String getPatients(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "query", required = false) String query,
                              @RequestParam(value = "userName", required = false) String userName,
                              @RequestParam(value = "status", defaultValue = "Aktiv") String status,
                              @RequestParam(value = "sort", required = false) String sort,
                              @RequestParam(value = "asc", required = false) boolean asc,
                              @RequestHeader(value = "Authorization") String authorization) throws JsonProcessingException {

        Pageable pageable = makePageable(page, sort, asc);

        if (query == null || query.trim().isEmpty()) {
            return objectMapper.writeValueAsString(new PageImpl(new ArrayList(), pageable, 0l));
        }

        String result = objectMapper.writeValueAsString(patientFinder.search(query, pageable, userName, status));



        SearchLog log = new SearchLog();
        log.setDate(new Date());
        log.setUserName(userName);
        log.setResult(result);
        log.setFilter(query);
        searchLogRepository.save(log);

        return result;
    }

    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer patientId) {
        patientRepository.delete(patientId);

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Patient getPatient(@PathVariable("id") Integer id) {
        Patient user = patientRepository.findOne(id);
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "pnr/{pnr}", method = RequestMethod.GET)
    public Patient getPatientByPnr(@PathVariable("pnr") String pnr) {
        Patient user = patientRepository.findByPnr(pnr);
        if (user == null) {
            return new Patient();
        }
        return user;
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
        makeTypeScriptVersion(PDArtikel.class);
    }

    public static void makeTypeScriptVersion(Class ofThat) throws IntrospectionException {
        BeanInfo bi = Introspector.getBeanInfo(ofThat);
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals("class")) continue;
            System.out.println(pd.getName() + ": "
                    + (isClassCollection(pd.getPropertyType()) ? "Array" :
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
            System.out.println("'" + pd.getName() + "': [this." + withBeanName + "." + pd.getName() + "],");
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

    /*private static class PdMixin {

        @JsonIgnore
        private Set<BestInfo> bestInfos;

    }*/

    private static class BestInfoMixin {

        @JsonIgnore
        private Set<BestPDRad> bestPDRads;

    }


}
