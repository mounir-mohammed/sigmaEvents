package ma.sig.events.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ma.sig.events.domain.Fonction;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FonctionRepositoryWithBagRelationshipsImpl implements FonctionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Fonction> fetchBagRelationships(Optional<Fonction> fonction) {
        return fonction.map(this::fetchAreas);
    }

    @Override
    public Page<Fonction> fetchBagRelationships(Page<Fonction> fonctions) {
        return new PageImpl<>(fetchBagRelationships(fonctions.getContent()), fonctions.getPageable(), fonctions.getTotalElements());
    }

    @Override
    public List<Fonction> fetchBagRelationships(List<Fonction> fonctions) {
        return Optional.of(fonctions).map(this::fetchAreas).orElse(Collections.emptyList());
    }

    Fonction fetchAreas(Fonction result) {
        return entityManager
            .createQuery(
                "select fonction from Fonction fonction left join fetch fonction.areas where fonction is :fonction",
                Fonction.class
            )
            .setParameter("fonction", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Fonction> fetchAreas(List<Fonction> fonctions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fonctions.size()).forEach(index -> order.put(fonctions.get(index).getFonctionId(), index));
        List<Fonction> result = entityManager
            .createQuery(
                "select distinct fonction from Fonction fonction left join fetch fonction.areas where fonction in :fonctions",
                Fonction.class
            )
            .setParameter("fonctions", fonctions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getFonctionId()), order.get(o2.getFonctionId())));
        return result;
    }
}
