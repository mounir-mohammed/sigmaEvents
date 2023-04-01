package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.AttachementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Attachement}.
 */
public interface AttachementService {
    /**
     * Save a attachement.
     *
     * @param attachementDTO the entity to save.
     * @return the persisted entity.
     */
    AttachementDTO save(AttachementDTO attachementDTO);

    /**
     * Updates a attachement.
     *
     * @param attachementDTO the entity to update.
     * @return the persisted entity.
     */
    AttachementDTO update(AttachementDTO attachementDTO);

    /**
     * Partially updates a attachement.
     *
     * @param attachementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachementDTO> partialUpdate(AttachementDTO attachementDTO);

    /**
     * Get all the attachements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachementDTO> findOne(Long id);

    /**
     * Delete the "id" attachement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
