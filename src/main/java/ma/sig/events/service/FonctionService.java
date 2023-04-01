package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.FonctionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Fonction}.
 */
public interface FonctionService {
    /**
     * Save a fonction.
     *
     * @param fonctionDTO the entity to save.
     * @return the persisted entity.
     */
    FonctionDTO save(FonctionDTO fonctionDTO);

    /**
     * Updates a fonction.
     *
     * @param fonctionDTO the entity to update.
     * @return the persisted entity.
     */
    FonctionDTO update(FonctionDTO fonctionDTO);

    /**
     * Partially updates a fonction.
     *
     * @param fonctionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FonctionDTO> partialUpdate(FonctionDTO fonctionDTO);

    /**
     * Get all the fonctions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FonctionDTO> findAll(Pageable pageable);

    /**
     * Get all the fonctions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FonctionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fonction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FonctionDTO> findOne(Long id);

    /**
     * Delete the "id" fonction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
