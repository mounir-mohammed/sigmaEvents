package ma.sig.events.repository;

import java.util.List;
import java.util.Optional;
import ma.sig.events.domain.Accreditation;
import org.springframework.data.domain.Page;

public interface AccreditationRepositoryWithBagRelationships {
    Optional<Accreditation> fetchBagRelationships(Optional<Accreditation> accreditation);

    List<Accreditation> fetchBagRelationships(List<Accreditation> accreditations);

    Page<Accreditation> fetchBagRelationships(Page<Accreditation> accreditations);
}
