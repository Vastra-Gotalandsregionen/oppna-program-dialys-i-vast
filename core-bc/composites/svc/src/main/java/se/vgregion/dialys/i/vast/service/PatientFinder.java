package se.vgregion.dialys.i.vast.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;
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
    public Page<Patient> search(String constraints, Pageable pageable, String userName, Boolean onlyMyDatas) {
        if (constraints == null) {
            constraints = "";
        }
        System.out.println("pageable: " + pageable);
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

        sb.append(" where ");
        if (!conditions.isEmpty()) {
            String jpql = String.join(" or ", conditions);
            sb.append("(");
            sb.append(jpql);
            sb.append(") and ");
        }

        sb.append(" u.userName = ?" + i);
        i++;
        words.add(userName);

        String countJpql = "select count(distinct p) from "
                + Patient.class.getSimpleName()
                + " p join p.mottagnings m "
                + " join m.users u "
                // + (!onlyMyDatas ? (" join a.mottagning m join m.ansvarigs a2 ") : "")
                + "left join p.pds pds "
                + "left join pds.bestInfos bestInfo "
                + sb.toString();

        String selectJpql = "select p from "
                + Patient.class.getSimpleName() + " p "
                + " join fetch p.mottagnings m "
                + " join m.users u "
                // + (!onlyMyDatas ? (" join a.mottagning m join m.ansvarigs a2 ") : "")
                + "left join fetch p.pds pds "
                + "left join fetch pds.bestInfos bestInfo "
                + sb.toString()
                + " "
                + makeOrderByPart("p", pageable);

        return query(
                Patient.class,
                selectJpql,
                countJpql,
                pageable,
                words
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


    private TypedQuery toTypedQuery(String jpql, Class clazz) {
        try {
            return entityManager.createQuery(jpql, (Class<Long>) clazz);
        } catch (Exception e) {
            System.out.println(clazz);
            System.out.println(jpql);
            throw new RuntimeException(e);
        }
    }

    private <T> Page<T> query(Class<T> clazz, String selectJpql, String countJpql, Pageable pageable, List words) {
        TypedQuery<T> typedQuery = toTypedQuery(selectJpql, clazz);
        TypedQuery<Long> countQuery = toTypedQuery(countJpql, Long.class);

        int i = 1;
        for (Object w : words) {
            countQuery.setParameter(i, w);
            typedQuery.setParameter(i++, w);
        }

        typedQuery.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        Long count = (Long) countQuery.getSingleResult();

        Page<T> asPage = new PageImpl<T>(typedQuery.getResultList(), pageable, count);

        System.out.println(typedQuery);

        return asPage;
    }
    
}
