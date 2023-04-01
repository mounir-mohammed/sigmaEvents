package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.EventFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.EventForm}.
 */
public interface EventFormService {
    /**
     * Save a eventForm.
     *
     * @param eventFormDTO the entity to save.
     * @return the persisted entity.
     */
    EventFormDTO save(EventFormDTO eventFormDTO);

    /**
     * Updates a eventForm.
     *
     * @param eventFormDTO the entity to update.
     * @return the persisted entity.
     */
    EventFormDTO update(EventFormDTO eventFormDTO);

    /**
     * Partially updates a eventForm.
     *
     * @param eventFormDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventFormDTO> partialUpdate(EventFormDTO eventFormDTO);

    /**
     * Get all the eventForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventFormDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventFormDTO> findOne(Long id);

    /**
     * Delete the "id" eventForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
