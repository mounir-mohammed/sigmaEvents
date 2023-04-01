package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.NationalityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Nationality}.
 */
public interface NationalityService {
    /**
     * Save a nationality.
     *
     * @param nationalityDTO the entity to save.
     * @return the persisted entity.
     */
    NationalityDTO save(NationalityDTO nationalityDTO);

    /**
     * Updates a nationality.
     *
     * @param nationalityDTO the entity to update.
     * @return the persisted entity.
     */
    NationalityDTO update(NationalityDTO nationalityDTO);

    /**
     * Partially updates a nationality.
     *
     * @param nationalityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NationalityDTO> partialUpdate(NationalityDTO nationalityDTO);

    /**
     * Get all the nationalities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NationalityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" nationality.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NationalityDTO> findOne(Long id);

    /**
     * Delete the "id" nationality.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
