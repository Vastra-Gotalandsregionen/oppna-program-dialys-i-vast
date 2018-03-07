package se.vgregion.dialys.i.vast.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.dialys.i.vast.jpa.requisitions.Patient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PatientFinder {

    @PersistenceContext()
    private EntityManager entityManager;

    @Transactional
    public Page<Patient> search(String constraints, Pageable pageable) {
        if (constraints == null) {
            constraints = "";
        }
        List<String> constraintsFrags = Arrays.asList(constraints.split(Pattern.quote(" ")));

        StringBuilder sb = new StringBuilder();

        List<String> conditions = new ArrayList<>();
        List words = new ArrayList();

        int i = 1;

        for (String k : Arrays.asList("pnr", "fornamn", "efternamn")) {
            for (String cs : constraintsFrags) {
                if (!cs.endsWith("%")) {
                    cs = cs + "%";
                }
                if (!cs.startsWith("%")) {
                    cs = "%" + cs;
                }
                cs = cs.toLowerCase();
                words.add(cs);
                conditions.add("lower(p." + k + ") like ?" + i);
                i++;
            }
        }

        if (!conditions.isEmpty()) {
            String jpql = String.join(" or ", conditions);
            sb.append(" where ");
            sb.append(jpql);
        }

        String countJpql = "select count(*) from "
                + Patient.class.getSimpleName()
                // + " p left join fetch p.ansvarig a "
                + " p "
                + sb.toString();

        System.out.println("Jpql: " + countJpql);
        System.out.println(words);

        return query(
                Patient.class,
                "select p from "
                        + Patient.class.getSimpleName()
                        + " p left join fetch p.ansvarig a "
                        + "left join fetch p.pds pds "
/*                        + "left join fetch pds.bestInfos bi "
                        + "left join fetch bi.bestPDRads "*/
                        + sb.toString()
                        + " "
                        + makeOrderByPart("p", pageable),
                countJpql,
                pageable, words
        );

    }

    private String makeOrderByPart(String forThatTypeAlias, Pageable pageable) {
        List<String> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            StringBuilder result = new StringBuilder();
            result.append(forThatTypeAlias);
            result.append(".");
            result.append(order.getProperty());
            result.append(" ");
            result.append(order.getDirection());
            orders.add(result.toString());
        }
        if (orders.isEmpty()) {
            return "";
        }
        return "order by " + String.join(", ", orders);
    }

    private <T> Page<T> query(Class<T> clazz, String selectJpql, String countJpql, Pageable pageable, List words) {
        TypedQuery<T> typedQuery = entityManager.createQuery(selectJpql, (Class) clazz);
        TypedQuery<T> countQuery = entityManager.createQuery(countJpql, (Class<T>) Long.class);

        int i = 1;
        for (Object w : words) {
            countQuery.setParameter(i, w);
            typedQuery.setParameter(i++, w);
        }

        typedQuery.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        Long count = (Long) countQuery.getSingleResult();

        Page<T> asPage = new PageImpl<T>(typedQuery.getResultList(), pageable, count);

        return asPage;
    }

}
