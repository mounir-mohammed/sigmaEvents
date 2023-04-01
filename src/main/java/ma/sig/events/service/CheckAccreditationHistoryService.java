package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.CheckAccreditationHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.CheckAccreditationHistory}.
 */
public interface CheckAccreditationHistoryService {
    /**
     * Save a checkAccreditationHistory.
     *
     * @param checkAccreditationHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    CheckAccreditationHistoryDTO save(CheckAccreditationHistoryDTO checkAccreditationHistoryDTO);

    /**
     * Updates a checkAccreditationHistory.
     *
     * @param checkAccreditationHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    CheckAccreditationHistoryDTO update(CheckAccreditationHistoryDTO checkAccreditationHistoryDTO);

    /**
     * Partially updates a checkAccreditationHistory.
     *
     * @param checkAccreditationHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CheckAccreditationHistoryDTO> partialUpdate(CheckAccreditationHistoryDTO checkAccreditationHistoryDTO);

    /**
     * Get all the checkAccreditationHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckAccreditationHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" checkAccreditationHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckAccreditationHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" checkAccreditationHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
