package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.InfoSuppDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.InfoSupp}.
 */
public interface InfoSuppService {
    /**
     * Save a infoSupp.
     *
     * @param infoSuppDTO the entity to save.
     * @return the persisted entity.
     */
    InfoSuppDTO save(InfoSuppDTO infoSuppDTO);

    /**
     * Updates a infoSupp.
     *
     * @param infoSuppDTO the entity to update.
     * @return the persisted entity.
     */
    InfoSuppDTO update(InfoSuppDTO infoSuppDTO);

    /**
     * Partially updates a infoSupp.
     *
     * @param infoSuppDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InfoSuppDTO> partialUpdate(InfoSuppDTO infoSuppDTO);

    /**
     * Get all the infoSupps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InfoSuppDTO> findAll(Pageable pageable);

    /**
     * Get the "id" infoSupp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InfoSuppDTO> findOne(Long id);

    /**
     * Delete the "id" infoSupp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
