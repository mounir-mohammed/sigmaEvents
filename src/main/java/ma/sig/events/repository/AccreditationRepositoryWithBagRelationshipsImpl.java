package ma.sig.events.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ma.sig.events.domain.Accreditation;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AccreditationRepositoryWithBagRelationshipsImpl implements AccreditationRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Accreditation> fetchBagRelationships(Optional<Accreditation> accreditation) {
        return accreditation.map(this::fetchSites);
    }

    @Override
    public Page<Accreditation> fetchBagRelationships(Page<Accreditation> accreditations) {
        return new PageImpl<>(
            fetchBagRelationships(accreditations.getContent()),
            accreditations.getPageable(),
            accreditations.getTotalElements()
        );
    }

    @Override
    public List<Accreditation> fetchBagRelationships(List<Accreditation> accreditations) {
        return Optional.of(accreditations).map(this::fetchSites).orElse(Collections.emptyList());
    }

    Accreditation fetchSites(Accreditation result) {
        return entityManager
            .createQuery(
                "select accreditation from Accreditation accreditation left join fetch accreditation.sites where accreditation is :accreditation",
                Accreditation.class
            )
            .setParameter("accreditation", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Accreditation> fetchSites(List<Accreditation> accreditations) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, accreditations.size()).forEach(index -> order.put(accreditations.get(index).getAccreditationId(), index));
        List<Accreditation> result = entityManager
            .createQuery(
                "select distinct accreditation from Accreditation accreditation left join fetch accreditation.sites where accreditation in :accreditations",
                Accreditation.class
            )
            .setParameter("accreditations", accreditations)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getAccreditationId()), order.get(o2.getAccreditationId())));
        return result;
    }
}
