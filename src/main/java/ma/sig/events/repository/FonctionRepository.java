package ma.sig.events.repository;

import java.util.List;
import java.util.Optional;
import ma.sig.events.domain.Fonction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fonction entity.
 *
 * When extending this class, extend FonctionRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface FonctionRepository
    extends FonctionRepositoryWithBagRelationships, JpaRepository<Fonction, Long>, JpaSpecificationExecutor<Fonction> {
    default Optional<Fonction> findOneWithEagerRelationships(Long fonctionId) {
        return this.fetchBagRelationships(this.findById(fonctionId));
    }

    default List<Fonction> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Fonction> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
