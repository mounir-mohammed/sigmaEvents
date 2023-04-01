package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.CodeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Code}.
 */
public interface CodeService {
    /**
     * Save a code.
     *
     * @param codeDTO the entity to save.
     * @return the persisted entity.
     */
    CodeDTO save(CodeDTO codeDTO);

    /**
     * Updates a code.
     *
     * @param codeDTO the entity to update.
     * @return the persisted entity.
     */
    CodeDTO update(CodeDTO codeDTO);

    /**
     * Partially updates a code.
     *
     * @param codeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CodeDTO> partialUpdate(CodeDTO codeDTO);

    /**
     * Get all the codes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CodeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" code.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CodeDTO> findOne(Long id);

    /**
     * Delete the "id" code.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
