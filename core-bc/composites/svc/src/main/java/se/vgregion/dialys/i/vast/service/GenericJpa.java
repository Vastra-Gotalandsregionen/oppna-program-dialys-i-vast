package se.vgregion.dialys.i.vast.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
        List<String> joins = toJoinParts(relation);
        String jpql = "select count(distinct j" + (joins.size() - 1) + ") from " + fromEntity + " e " +
                String.join(" ", joins) + " where e.id = ?1";

        for (Object id : ids) {
            Query q = entityManager.createQuery(
                    jpql);

            q.setParameter(1, id);
            result.add((Long) q.getSingleResult());
        }
        return result;
    }

    public static List<String> toJoinParts(String relation) {
        // String relation = "artikels.pdArtikels";
        String[] path = relation.split(Pattern.quote("."));
        List<String> joins = new ArrayList<>();
        String latestAlias = "e";
        for (int i = 0; i < path.length; i++) {
            joins.add(" join " + latestAlias + "." + path[i] + " " + (latestAlias = "j" + i));
        }
        System.out.println(joins);
        return joins;
    }

}
