package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.CodeTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.CodeType}.
 */
public interface CodeTypeService {
    /**
     * Save a codeType.
     *
     * @param codeTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CodeTypeDTO save(CodeTypeDTO codeTypeDTO);

    /**
     * Updates a codeType.
     *
     * @param codeTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CodeTypeDTO update(CodeTypeDTO codeTypeDTO);

    /**
     * Partially updates a codeType.
     *
     * @param codeTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CodeTypeDTO> partialUpdate(CodeTypeDTO codeTypeDTO);

    /**
     * Get all the codeTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CodeTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" codeType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CodeTypeDTO> findOne(Long id);

    /**
     * Delete the "id" codeType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
