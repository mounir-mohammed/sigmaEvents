package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.OperationTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.OperationType}.
 */
public interface OperationTypeService {
    /**
     * Save a operationType.
     *
     * @param operationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    OperationTypeDTO save(OperationTypeDTO operationTypeDTO);

    /**
     * Updates a operationType.
     *
     * @param operationTypeDTO the entity to update.
     * @return the persisted entity.
     */
    OperationTypeDTO update(OperationTypeDTO operationTypeDTO);

    /**
     * Partially updates a operationType.
     *
     * @param operationTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperationTypeDTO> partialUpdate(OperationTypeDTO operationTypeDTO);

    /**
     * Get all the operationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OperationTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" operationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperationTypeDTO> findOne(Long id);

    /**
     * Delete the "id" operationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
