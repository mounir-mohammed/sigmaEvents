package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.AuthentificationTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.AuthentificationType}.
 */
public interface AuthentificationTypeService {
    /**
     * Save a authentificationType.
     *
     * @param authentificationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AuthentificationTypeDTO save(AuthentificationTypeDTO authentificationTypeDTO);

    /**
     * Updates a authentificationType.
     *
     * @param authentificationTypeDTO the entity to update.
     * @return the persisted entity.
     */
    AuthentificationTypeDTO update(AuthentificationTypeDTO authentificationTypeDTO);

    /**
     * Partially updates a authentificationType.
     *
     * @param authentificationTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AuthentificationTypeDTO> partialUpdate(AuthentificationTypeDTO authentificationTypeDTO);

    /**
     * Get all the authentificationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AuthentificationTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" authentificationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuthentificationTypeDTO> findOne(Long id);

    /**
     * Delete the "id" authentificationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
