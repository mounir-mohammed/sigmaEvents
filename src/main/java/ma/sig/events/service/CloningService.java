package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.CloningDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Cloning}.
 */
public interface CloningService {
    /**
     * Save a cloning.
     *
     * @param cloningDTO the entity to save.
     * @return the persisted entity.
     */
    CloningDTO save(CloningDTO cloningDTO);

    /**
     * Updates a cloning.
     *
     * @param cloningDTO the entity to update.
     * @return the persisted entity.
     */
    CloningDTO update(CloningDTO cloningDTO);

    /**
     * Partially updates a cloning.
     *
     * @param cloningDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CloningDTO> partialUpdate(CloningDTO cloningDTO);

    /**
     * Get all the clonings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CloningDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cloning.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CloningDTO> findOne(Long id);

    /**
     * Delete the "id" cloning.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    CloningDTO clone(CloningDTO cloningDTO);
}
