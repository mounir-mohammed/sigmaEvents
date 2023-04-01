package ma.sig.events.service;

import java.util.Optional;
import ma.sig.events.service.dto.PhotoArchiveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ma.sig.events.domain.PhotoArchive}.
 */
public interface PhotoArchiveService {
    /**
     * Save a photoArchive.
     *
     * @param photoArchiveDTO the entity to save.
     * @return the persisted entity.
     */
    PhotoArchiveDTO save(PhotoArchiveDTO photoArchiveDTO);

    /**
     * Updates a photoArchive.
     *
     * @param photoArchiveDTO the entity to update.
     * @return the persisted entity.
     */
    PhotoArchiveDTO update(PhotoArchiveDTO photoArchiveDTO);

    /**
     * Partially updates a photoArchive.
     *
     * @param photoArchiveDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhotoArchiveDTO> partialUpdate(PhotoArchiveDTO photoArchiveDTO);

    /**
     * Get all the photoArchives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhotoArchiveDTO> findAll(Pageable pageable);

    /**
     * Get the "id" photoArchive.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhotoArchiveDTO> findOne(Long id);

    /**
     * Delete the "id" photoArchive.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
