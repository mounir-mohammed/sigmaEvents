package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.LogHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.LogHistory}.
 */
public interface LogHistoryService {
    /**
     * Save a logHistory.
     *
     * @param logHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    LogHistoryDTO save(LogHistoryDTO logHistoryDTO);

    /**
     * Updates a logHistory.
     *
     * @param logHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    LogHistoryDTO update(LogHistoryDTO logHistoryDTO);

    /**
     * Partially updates a logHistory.
     *
     * @param logHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LogHistoryDTO> partialUpdate(LogHistoryDTO logHistoryDTO);

    /**
     * Get all the logHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LogHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" logHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LogHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" logHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
