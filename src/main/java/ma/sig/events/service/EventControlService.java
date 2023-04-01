package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.EventControlDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.EventControl}.
 */
public interface EventControlService {
    /**
     * Save a eventControl.
     *
     * @param eventControlDTO the entity to save.
     * @return the persisted entity.
     */
    EventControlDTO save(EventControlDTO eventControlDTO);

    /**
     * Updates a eventControl.
     *
     * @param eventControlDTO the entity to update.
     * @return the persisted entity.
     */
    EventControlDTO update(EventControlDTO eventControlDTO);

    /**
     * Partially updates a eventControl.
     *
     * @param eventControlDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventControlDTO> partialUpdate(EventControlDTO eventControlDTO);

    /**
     * Get all the eventControls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventControlDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventControl.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventControlDTO> findOne(Long id);

    /**
     * Delete the "id" eventControl.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
