package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.PrintingModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.PrintingModel}.
 */
public interface PrintingModelService {
    /**
     * Save a printingModel.
     *
     * @param printingModelDTO the entity to save.
     * @return the persisted entity.
     */
    PrintingModelDTO save(PrintingModelDTO printingModelDTO);

    /**
     * Updates a printingModel.
     *
     * @param printingModelDTO the entity to update.
     * @return the persisted entity.
     */
    PrintingModelDTO update(PrintingModelDTO printingModelDTO);

    /**
     * Partially updates a printingModel.
     *
     * @param printingModelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrintingModelDTO> partialUpdate(PrintingModelDTO printingModelDTO);

    /**
     * Get all the printingModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrintingModelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" printingModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrintingModelDTO> findOne(Long id);

    /**
     * Delete the "id" printingModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
