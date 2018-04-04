package se.vgregion.dialys.i.vast.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.dialys.i.vast.jpa.requisitions.Flik;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class FlikRepositoryIntegrationTest {

    @Autowired
    private FlikRepository flikRepository;

    @Test
    public void getAll() {
        List<Flik> result = flikRepository.findAll();
        for (Flik flik : result) {
            System.out.println(flik);
        }
    }

}
