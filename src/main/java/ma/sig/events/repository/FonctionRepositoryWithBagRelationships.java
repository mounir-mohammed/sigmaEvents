package ma.sig.events.repository;

import java.util.List;
import java.util.Optional;
import ma.sig.events.domain.Fonction;
import org.springframework.data.domain.Page;

public interface FonctionRepositoryWithBagRelationships {
    Optional<Fonction> fetchBagRelationships(Optional<Fonction> fonction);

    List<Fonction> fetchBagRelationships(List<Fonction> fonctions);

    Page<Fonction> fetchBagRelationships(Page<Fonction> fonctions);
}
