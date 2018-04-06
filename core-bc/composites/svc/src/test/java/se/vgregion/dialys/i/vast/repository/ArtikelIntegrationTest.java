package se.vgregion.dialys.i.vast.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.dialys.i.vast.jpa.requisitions.Artikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.Flik;

import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class ArtikelIntegrationTest {

    @Autowired
    private ArtikelRepository artikelRepository;

    @Ignore
    @Transactional
    @Test
    public void updateAllMaxAntal() {
        List<Artikel> result = artikelRepository.findAll();
        Random random = new Random();
        for (Artikel artikel : result) {
            artikel.setMaxantal(random.nextInt(20));
            artikelRepository.save(artikel);
        }
        artikelRepository.flush();
    }

}
