package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.SexeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Sexe}.
 */
public interface SexeService {
    /**
     * Save a sexe.
     *
     * @param sexeDTO the entity to save.
     * @return the persisted entity.
     */
    SexeDTO save(SexeDTO sexeDTO);

    /**
     * Updates a sexe.
     *
     * @param sexeDTO the entity to update.
     * @return the persisted entity.
     */
    SexeDTO update(SexeDTO sexeDTO);

    /**
     * Partially updates a sexe.
     *
     * @param sexeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SexeDTO> partialUpdate(SexeDTO sexeDTO);

    /**
     * Get all the sexes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SexeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sexe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SexeDTO> findOne(Long id);

    /**
     * Delete the "id" sexe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
