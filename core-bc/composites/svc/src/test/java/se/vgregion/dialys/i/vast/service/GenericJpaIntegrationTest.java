package se.vgregion.dialys.i.vast.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.dialys.i.vast.service.PatientFinder;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class GenericJpaIntegrationTest {

    @Autowired
    private GenericJpa genericJpa;

    @Test
    public void main() {
        List<Long> result = genericJpa.fetchCounts(
                "Mottagning",
                "patients",
                1, 2, 3, 4, 5, 6, 7, 8
        );
        System.out.println(result);

        result = genericJpa.fetchCounts(
                "Grupp",
                "artikels.pdArtikels",
                1, 2, 3, 4, 5, 6, 7, 8
        );
        System.out.println(result);
    }

}
