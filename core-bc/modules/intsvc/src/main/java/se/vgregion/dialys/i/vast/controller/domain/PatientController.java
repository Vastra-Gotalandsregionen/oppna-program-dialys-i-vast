package se.vgregion.dialys.i.vast.controller.domain;

import com.auth0.jwt.interfaces.DecodedJWT;
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
import se.vgregion.dialys.i.vast.jpa.ChangeLog;
import se.vgregion.dialys.i.vast.jpa.SearchLog;
import se.vgregion.dialys.i.vast.jpa.ViewLog;
import se.vgregion.dialys.i.vast.jpa.requisitions.*;
import se.vgregion.dialys.i.vast.repository.*;
import se.vgregion.dialys.i.vast.service.AuthService;
import se.vgregion.dialys.i.vast.service.JwtUtil;
import se.vgregion.dialys.i.vast.service.PatientFinder;
import se.vgregion.dialys.i.vast.util.ReflectionUtil;

import javax.annotation.PostConstruct;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final static int pageSize = 20;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ViewLogRepository viewLogRepository;

    @Autowired
    PatientFinder patientFinder;

    @Autowired
    SearchLogRepository searchLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChangeLogRepository changeLogRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    void init() {
        objectMapper.addMixIn(BestInfo.class, BestInfoMixin.class);
    }

    /*@RequestMapping(value = "", method = RequestMethod.GET)
    public List<Patient> getPatients() {
        List<Patient> result = new ArrayList<>();
        patientRepository.findAll().forEach(i -> result.add(i));
        return result;
    }*/

    //@PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<Patient> save(@RequestBody Patient patient, @RequestHeader(name = "authorization") String authorization) {
        DecodedJWT token = JwtUtil.verify(authorization);
        User user = userRepository.findOne(token.getSubject());
        if (patient.getId() != null) {
            Patient previousVersion = patientRepository.getOne(patient.getId());
            ChangeLog changeLog = new ChangeLog();
            try {
                String change = objectMapper.writeValueAsString(previousVersion);
                System.out.println("Längden på change är " + change.length());
                changeLog.setPreviousValue(change);
                changeLog.setDate(new Date());
                changeLog.setPatientId(patient.getId());
                changeLog.setUserName(user.getUserName());
                changeLogRepository.save(changeLog);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            patient.setRedigeringsdatum(new Date());
            patient.setRedigerare(user);
        } else {
            patient.setRegdatum(new Date());
            patient.setRegistrator(user);
        }
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

    private boolean isBlank(String s) {
        if (s == null) {
            return true;
        }
        return s.trim().equals("");
    }

    @PreAuthorize("@authService.isLoggedIn(authentication)")
    @RequestMapping(value = "filter", method = RequestMethod.GET)
    @Transactional
    public String getPatients(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "query", required = false) String query,
                              @RequestParam(value = "userName", required = false) String userName,
                              @RequestParam(value = "status", defaultValue = "Aktiv") String status,
                              @RequestParam(value = "sort", required = false) String sort,
                              @RequestParam(value = "week", required = false) String week,
                              @RequestParam(value = "day", required = false) String day,
                              @RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "asc", required = false) boolean asc,
                              @RequestHeader(value = "Authorization") String authorization) throws JsonProcessingException {

        Pageable pageable = makePageable(page, sort, asc);

        if (isBlank(query) && isBlank(week) && isBlank(day) && isBlank(status)) {
            return objectMapper.writeValueAsString(new PageImpl(new ArrayList(), pageable, 0l));
        }
        System.out.println("Type is: " + type);
        String result = objectMapper.writeValueAsString(patientFinder.search(query, pageable, userName, status, week, day, type));

        SearchLog log = new SearchLog();
        log.setDate(new Date());
        log.setUserName(userName);
        log.setResult(result);
        log.setFilter(query);
        searchLogRepository.save(log);

        return result;
    }

    /*@PreAuthorize("@authService.hasRole(authentication, 'admin')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer patientId) {
        patientRepository.delete(patientId);

        return ResponseEntity.ok().build();
    }*/

    @Autowired
    AuthService authService;

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Patient getPatient(@PathVariable("id") Integer id, @RequestHeader(value = "Authorization") String authorization) {
        DecodedJWT token = JwtUtil.verify(authorization);
        Patient patient = patientRepository.findOne(id);
        ViewLog viewLog = new ViewLog();
        viewLog.setUserName(token.getSubject());
        viewLog.setPatientId(id);
        viewLogRepository.save(viewLog);
        return patient;
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
