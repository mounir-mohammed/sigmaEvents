package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.LanguageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Language}.
 */
public interface LanguageService {
    /**
     * Save a language.
     *
     * @param languageDTO the entity to save.
     * @return the persisted entity.
     */
    LanguageDTO save(LanguageDTO languageDTO);

    /**
     * Updates a language.
     *
     * @param languageDTO the entity to update.
     * @return the persisted entity.
     */
    LanguageDTO update(LanguageDTO languageDTO);

    /**
     * Partially updates a language.
     *
     * @param languageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LanguageDTO> partialUpdate(LanguageDTO languageDTO);

    /**
     * Get all the languages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LanguageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" language.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LanguageDTO> findOne(Long id);

    /**
     * Delete the "id" language.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
