package se.vgregion.dialys.i.vast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.dialys.i.vast.jpa.Dummy;
import se.vgregion.dialys.i.vast.repository.DummyRepository;

@Component
public class DummyOperations {

    @Autowired
    DummyRepository dummyRepository;

    @Transactional
    public Dummy save(Dummy that) {
        return dummyRepository.save(that);
    }

}
