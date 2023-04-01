package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.CivilityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Civility}.
 */
public interface CivilityService {
    /**
     * Save a civility.
     *
     * @param civilityDTO the entity to save.
     * @return the persisted entity.
     */
    CivilityDTO save(CivilityDTO civilityDTO);

    /**
     * Updates a civility.
     *
     * @param civilityDTO the entity to update.
     * @return the persisted entity.
     */
    CivilityDTO update(CivilityDTO civilityDTO);

    /**
     * Partially updates a civility.
     *
     * @param civilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CivilityDTO> partialUpdate(CivilityDTO civilityDTO);

    /**
     * Get all the civilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CivilityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" civility.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CivilityDTO> findOne(Long id);

    /**
     * Delete the "id" civility.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
