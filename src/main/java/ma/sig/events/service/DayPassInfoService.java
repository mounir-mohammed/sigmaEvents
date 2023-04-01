package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.DayPassInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.DayPassInfo}.
 */
public interface DayPassInfoService {
    /**
     * Save a dayPassInfo.
     *
     * @param dayPassInfoDTO the entity to save.
     * @return the persisted entity.
     */
    DayPassInfoDTO save(DayPassInfoDTO dayPassInfoDTO);

    /**
     * Updates a dayPassInfo.
     *
     * @param dayPassInfoDTO the entity to update.
     * @return the persisted entity.
     */
    DayPassInfoDTO update(DayPassInfoDTO dayPassInfoDTO);

    /**
     * Partially updates a dayPassInfo.
     *
     * @param dayPassInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DayPassInfoDTO> partialUpdate(DayPassInfoDTO dayPassInfoDTO);

    /**
     * Get all the dayPassInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DayPassInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dayPassInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DayPassInfoDTO> findOne(Long id);

    /**
     * Delete the "id" dayPassInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
