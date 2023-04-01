package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.AttachementTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.AttachementType}.
 */
public interface AttachementTypeService {
    /**
     * Save a attachementType.
     *
     * @param attachementTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AttachementTypeDTO save(AttachementTypeDTO attachementTypeDTO);

    /**
     * Updates a attachementType.
     *
     * @param attachementTypeDTO the entity to update.
     * @return the persisted entity.
     */
    AttachementTypeDTO update(AttachementTypeDTO attachementTypeDTO);

    /**
     * Partially updates a attachementType.
     *
     * @param attachementTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachementTypeDTO> partialUpdate(AttachementTypeDTO attachementTypeDTO);

    /**
     * Get all the attachementTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachementTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachementType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachementTypeDTO> findOne(Long id);

    /**
     * Delete the "id" attachementType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
