package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.SettingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Setting}.
 */
public interface SettingService {
    /**
     * Save a setting.
     *
     * @param settingDTO the entity to save.
     * @return the persisted entity.
     */
    SettingDTO save(SettingDTO settingDTO);

    /**
     * Updates a setting.
     *
     * @param settingDTO the entity to update.
     * @return the persisted entity.
     */
    SettingDTO update(SettingDTO settingDTO);

    /**
     * Partially updates a setting.
     *
     * @param settingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SettingDTO> partialUpdate(SettingDTO settingDTO);

    /**
     * Get all the settings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SettingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" setting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SettingDTO> findOne(Long id);

    /**
     * Delete the "id" setting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
