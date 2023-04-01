package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.AccreditationTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.AccreditationType}.
 */
public interface AccreditationTypeService {
    /**
     * Save a accreditationType.
     *
     * @param accreditationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AccreditationTypeDTO save(AccreditationTypeDTO accreditationTypeDTO);

    /**
     * Updates a accreditationType.
     *
     * @param accreditationTypeDTO the entity to update.
     * @return the persisted entity.
     */
    AccreditationTypeDTO update(AccreditationTypeDTO accreditationTypeDTO);

    /**
     * Partially updates a accreditationType.
     *
     * @param accreditationTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccreditationTypeDTO> partialUpdate(AccreditationTypeDTO accreditationTypeDTO);

    /**
     * Get all the accreditationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccreditationTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accreditationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccreditationTypeDTO> findOne(Long id);

    /**
     * Delete the "id" accreditationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
