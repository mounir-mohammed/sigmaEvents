package ma.sig.events.repository;

import java.util.List;
import java.util.Optional;
import ma.sig.events.domain.Accreditation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Accreditation entity.
 *
 * When extending this class, extend AccreditationRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface AccreditationRepository
    extends AccreditationRepositoryWithBagRelationships, JpaRepository<Accreditation, Long>, JpaSpecificationExecutor<Accreditation> {
    default Optional<Accreditation> findOneWithEagerRelationships(Long accreditationId) {
        return this.fetchBagRelationships(this.findById(accreditationId));
    }

    default List<Accreditation> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Accreditation> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
