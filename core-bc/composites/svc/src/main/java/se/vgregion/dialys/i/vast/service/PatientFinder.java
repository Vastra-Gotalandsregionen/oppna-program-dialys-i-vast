package se.vgregion.dialys.i.vast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestInfo;
import se.vgregion.dialys.i.vast.jpa.requisitions.Patient;
import se.vgregion.dialys.i.vast.jpa.requisitions.Pd;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class PatientFinder {

    @PersistenceContext()
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    /**
     * Observe: This service removes all but one of the Patients Pd:s and their BestInfo:s.
     * This because only the first Pd and Bestinfos are used in the view using this service.
     *
     * @param constraints
     * @param pageable
     * @param userName
     * @param status
     * @return
     */
    public Page<Patient> search(String constraints, Pageable pageable, String userName, String status) {
        long startTime = System.currentTimeMillis();
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

        sb.append(" where ");
        if (!conditions.isEmpty()) {
            String jpql = String.join(" or ", conditions);
            sb.append("(");
            sb.append(jpql);
            sb.append(") and ");
        }

        User user = userRepository.findOne(userName);
        boolean worksAsPharmaceut = user != null && user.getPharmaceut() != null && user.getPharmaceut();

        if (!worksAsPharmaceut) {
            sb.append(" u.userName = ?" + i);
            i++;
            words.add(userName);
            sb.append(" and ");
        }
        sb.append(" p.status = ?" + i);
        i++;
        words.add(status);

        String countJpql = "select count(distinct p) from "
                + Patient.class.getSimpleName() // Pharamaceut:s can se all patients.
                + (worksAsPharmaceut ? " p" : (" p join p.mottagnings m"
                + " join m.users u "))
                + " left join p.pds pds "
                + " left join pds.bestInfos bestInfo "
                + sb.toString();

        String selectJpql = "select p from "
                + Patient.class.getSimpleName() // Pharamaceut:s can se all patients.
                + (worksAsPharmaceut ? " p" : (" p join p.mottagnings m"
                + " join m.users u "))
                + " left join fetch p.pds pds "
                + " left join fetch pds.bestInfos bestInfo "
                + sb.toString()
                + " "
                + makeOrderByPart("p", pageable);

        Page<Patient> result = query(
                Patient.class,
                selectJpql,
                countJpql,
                pageable,
                words
        );

        leavJustOnePdAndBestInfo(result);

        System.out.println("Time to search " + (System.currentTimeMillis() - startTime));

        return result;
    }

    static void leavJustOnePdAndBestInfo(Page<Patient> insideThose) {
        for (Patient patient : insideThose) {
            leavJustOnePdAndBestInfo(patient);
        }
    }

    static void leavJustOnePdAndBestInfo(Patient insideThat) {
        if (insideThat.getPds().size() > 1) {
            NavigableSet<Pd> pds = new TreeSet<>((o1, o2) -> o1.getDatum().before(o2.getDatum()) ? 1 : -1);
            pds.addAll(insideThat.getPds());
            insideThat.getPds().clear();
            Pd withHighestDateValue = pds.first();
            insideThat.getPds().add(withHighestDateValue);
            if (withHighestDateValue.getBestInfos().size() > 1) {
                NavigableSet<BestInfo> bestInfos = new TreeSet(
                        (Comparator<BestInfo>) (o1, o2) -> (o1.getDatum().before(o2.getDatum()) ? 1 : -1)
                );
                bestInfos.addAll(withHighestDateValue.getBestInfos());
                withHighestDateValue.getBestInfos().clear();
                withHighestDateValue.getBestInfos().add(bestInfos.first());
            }
        }
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


    @Transactional
    public Pd getLatestPd(Patient forThat) {
        String sql = "SELECT p FROM " +
                Pd.class.getSimpleName() +
                " p where p.typ = '" +
                forThat.getTyp() +
                "' and p.patient = ?1 order by p.datum desc";

        Query query = entityManager.createQuery(sql);
        query.setFirstResult(0);
        query.setMaxResults(1);
        query.setParameter(1, forThat);
        List result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return (Pd) result.get(0);
    }

}
