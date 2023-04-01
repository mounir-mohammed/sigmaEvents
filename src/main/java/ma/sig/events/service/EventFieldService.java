package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.EventFieldDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.EventField}.
 */
public interface EventFieldService {
    /**
     * Save a eventField.
     *
     * @param eventFieldDTO the entity to save.
     * @return the persisted entity.
     */
    EventFieldDTO save(EventFieldDTO eventFieldDTO);

    /**
     * Updates a eventField.
     *
     * @param eventFieldDTO the entity to update.
     * @return the persisted entity.
     */
    EventFieldDTO update(EventFieldDTO eventFieldDTO);

    /**
     * Partially updates a eventField.
     *
     * @param eventFieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventFieldDTO> partialUpdate(EventFieldDTO eventFieldDTO);

    /**
     * Get all the eventFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventFieldDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventField.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventFieldDTO> findOne(Long id);

    /**
     * Delete the "id" eventField.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
