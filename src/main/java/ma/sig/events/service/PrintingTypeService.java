package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.PrintingTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.PrintingType}.
 */
public interface PrintingTypeService {
    /**
     * Save a printingType.
     *
     * @param printingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PrintingTypeDTO save(PrintingTypeDTO printingTypeDTO);

    /**
     * Updates a printingType.
     *
     * @param printingTypeDTO the entity to update.
     * @return the persisted entity.
     */
    PrintingTypeDTO update(PrintingTypeDTO printingTypeDTO);

    /**
     * Partially updates a printingType.
     *
     * @param printingTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrintingTypeDTO> partialUpdate(PrintingTypeDTO printingTypeDTO);

    /**
     * Get all the printingTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrintingTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" printingType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrintingTypeDTO> findOne(Long id);

    /**
     * Delete the "id" printingType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
