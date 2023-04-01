package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.OperationHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.OperationHistory}.
 */
public interface OperationHistoryService {
    /**
     * Save a operationHistory.
     *
     * @param operationHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    OperationHistoryDTO save(OperationHistoryDTO operationHistoryDTO);

    /**
     * Updates a operationHistory.
     *
     * @param operationHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    OperationHistoryDTO update(OperationHistoryDTO operationHistoryDTO);

    /**
     * Partially updates a operationHistory.
     *
     * @param operationHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperationHistoryDTO> partialUpdate(OperationHistoryDTO operationHistoryDTO);

    /**
     * Get all the operationHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OperationHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" operationHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperationHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" operationHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
