package se.vgregion.dialys.i.vast.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GenericJpa {

    @PersistenceContext()
    private EntityManager entityManager;


    public List<Long> fetchCounts(String fromEntity, String relation, Integer... ids) {
        return fetchCounts(fromEntity, relation, Arrays.asList(ids));
    }

    @Transactional
    public List<Long> fetchCounts(String fromEntity, String relation, List<Object> ids) {
        List<Long> result = new ArrayList<>();
        for (Object id : ids) {
            Query q = entityManager.createQuery(
                    "select count(r) " +
                            "from " + fromEntity + " e " +
                            "join e." + relation + " r " +
                            "where e.id = ?1");

            q.setParameter(1, id);
            result.add((Long) q.getSingleResult());
        }
        return result;
    }

}
