package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.AccreditationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Accreditation}.
 */
public interface AccreditationService {
    /**
     * Save a accreditation.
     *
     * @param accreditationDTO the entity to save.
     * @return the persisted entity.
     */
    AccreditationDTO save(AccreditationDTO accreditationDTO);

    /**
     * Updates a accreditation.
     *
     * @param accreditationDTO the entity to update.
     * @return the persisted entity.
     */
    AccreditationDTO update(AccreditationDTO accreditationDTO);

    /**
     * Partially updates a accreditation.
     *
     * @param accreditationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccreditationDTO> partialUpdate(AccreditationDTO accreditationDTO);

    /**
     * Get all the accreditations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccreditationDTO> findAll(Pageable pageable);

    /**
     * Get all the accreditations with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccreditationDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" accreditation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccreditationDTO> findOne(Long id);

    /**
     * Delete the "id" accreditation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
