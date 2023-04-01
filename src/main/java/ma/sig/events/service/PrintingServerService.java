package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.PrintingServerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.PrintingServer}.
 */
public interface PrintingServerService {
    /**
     * Save a printingServer.
     *
     * @param printingServerDTO the entity to save.
     * @return the persisted entity.
     */
    PrintingServerDTO save(PrintingServerDTO printingServerDTO);

    /**
     * Updates a printingServer.
     *
     * @param printingServerDTO the entity to update.
     * @return the persisted entity.
     */
    PrintingServerDTO update(PrintingServerDTO printingServerDTO);

    /**
     * Partially updates a printingServer.
     *
     * @param printingServerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrintingServerDTO> partialUpdate(PrintingServerDTO printingServerDTO);

    /**
     * Get all the printingServers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrintingServerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" printingServer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrintingServerDTO> findOne(Long id);

    /**
     * Delete the "id" printingServer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
