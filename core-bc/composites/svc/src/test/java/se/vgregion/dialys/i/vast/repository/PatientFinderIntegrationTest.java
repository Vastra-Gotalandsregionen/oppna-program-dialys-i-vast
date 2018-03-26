package se.vgregion.dialys.i.vast.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.dialys.i.vast.jpa.requisitions.Patient;
import se.vgregion.dialys.i.vast.service.PatientFinder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class PatientFinderIntegrationTest {

    @Autowired
    private PatientFinder patientFinder;


    // @Ignore
    @Test
    public void filtering() {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "pnr").ignoreCase();
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(0, 20, sort);
        Page<Patient> result = patientFinder.search("", pageable, "jaabl", false);
        for (Patient patient : result.getContent()) {
            System.out.println(patient);
        }
    }

    @Test
    public void findHighestBeginningWith() throws Exception {

    }

}