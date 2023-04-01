package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.OrganizDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.Organiz}.
 */
public interface OrganizService {
    /**
     * Save a organiz.
     *
     * @param organizDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizDTO save(OrganizDTO organizDTO);

    /**
     * Updates a organiz.
     *
     * @param organizDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizDTO update(OrganizDTO organizDTO);

    /**
     * Partially updates a organiz.
     *
     * @param organizDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizDTO> partialUpdate(OrganizDTO organizDTO);

    /**
     * Get all the organizs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizDTO> findAll(Pageable pageable);

    /**
     * Get the "id" organiz.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizDTO> findOne(Long id);

    /**
     * Delete the "id" organiz.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
