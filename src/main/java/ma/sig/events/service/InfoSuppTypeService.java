package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.InfoSuppTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.InfoSuppType}.
 */
public interface InfoSuppTypeService {
    /**
     * Save a infoSuppType.
     *
     * @param infoSuppTypeDTO the entity to save.
     * @return the persisted entity.
     */
    InfoSuppTypeDTO save(InfoSuppTypeDTO infoSuppTypeDTO);

    /**
     * Updates a infoSuppType.
     *
     * @param infoSuppTypeDTO the entity to update.
     * @return the persisted entity.
     */
    InfoSuppTypeDTO update(InfoSuppTypeDTO infoSuppTypeDTO);

    /**
     * Partially updates a infoSuppType.
     *
     * @param infoSuppTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InfoSuppTypeDTO> partialUpdate(InfoSuppTypeDTO infoSuppTypeDTO);

    /**
     * Get all the infoSuppTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InfoSuppTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" infoSuppType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InfoSuppTypeDTO> findOne(Long id);

    /**
     * Delete the "id" infoSuppType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
