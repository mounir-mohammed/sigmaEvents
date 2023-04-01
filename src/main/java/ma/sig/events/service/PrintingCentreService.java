package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.PrintingCentreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.PrintingCentre}.
 */
public interface PrintingCentreService {
    /**
     * Save a printingCentre.
     *
     * @param printingCentreDTO the entity to save.
     * @return the persisted entity.
     */
    PrintingCentreDTO save(PrintingCentreDTO printingCentreDTO);

    /**
     * Updates a printingCentre.
     *
     * @param printingCentreDTO the entity to update.
     * @return the persisted entity.
     */
    PrintingCentreDTO update(PrintingCentreDTO printingCentreDTO);

    /**
     * Partially updates a printingCentre.
     *
     * @param printingCentreDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrintingCentreDTO> partialUpdate(PrintingCentreDTO printingCentreDTO);

    /**
     * Get all the printingCentres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrintingCentreDTO> findAll(Pageable pageable);

    /**
     * Get the "id" printingCentre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrintingCentreDTO> findOne(Long id);

    /**
     * Delete the "id" printingCentre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
