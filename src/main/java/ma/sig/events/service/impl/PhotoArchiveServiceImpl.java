package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.PhotoArchive;
import ma.sig.events.repository.PhotoArchiveRepository;
import ma.sig.events.service.PhotoArchiveService;
import ma.sig.events.service.dto.PhotoArchiveDTO;
import ma.sig.events.service.mapper.PhotoArchiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PhotoArchive}.
 */
@Service
@Transactional
public class PhotoArchiveServiceImpl implements PhotoArchiveService {

    private final Logger log = LoggerFactory.getLogger(PhotoArchiveServiceImpl.class);

    private final PhotoArchiveRepository photoArchiveRepository;

    private final PhotoArchiveMapper photoArchiveMapper;

    public PhotoArchiveServiceImpl(PhotoArchiveRepository photoArchiveRepository, PhotoArchiveMapper photoArchiveMapper) {
        this.photoArchiveRepository = photoArchiveRepository;
        this.photoArchiveMapper = photoArchiveMapper;
    }

    @Override
    public PhotoArchiveDTO save(PhotoArchiveDTO photoArchiveDTO) {
        log.debug("Request to save PhotoArchive : {}", photoArchiveDTO);
        PhotoArchive photoArchive = photoArchiveMapper.toEntity(photoArchiveDTO);
        photoArchive = photoArchiveRepository.save(photoArchive);
        return photoArchiveMapper.toDto(photoArchive);
    }

    @Override
    public PhotoArchiveDTO update(PhotoArchiveDTO photoArchiveDTO) {
        log.debug("Request to update PhotoArchive : {}", photoArchiveDTO);
        PhotoArchive photoArchive = photoArchiveMapper.toEntity(photoArchiveDTO);
        photoArchive = photoArchiveRepository.save(photoArchive);
        return photoArchiveMapper.toDto(photoArchive);
    }

    @Override
    public Optional<PhotoArchiveDTO> partialUpdate(PhotoArchiveDTO photoArchiveDTO) {
        log.debug("Request to partially update PhotoArchive : {}", photoArchiveDTO);

        return photoArchiveRepository
            .findById(photoArchiveDTO.getPhotoArchiveId())
            .map(existingPhotoArchive -> {
                photoArchiveMapper.partialUpdate(existingPhotoArchive, photoArchiveDTO);

                return existingPhotoArchive;
            })
            .map(photoArchiveRepository::save)
            .map(photoArchiveMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoArchiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PhotoArchives");
        return photoArchiveRepository.findAll(pageable).map(photoArchiveMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoArchiveDTO> findOne(Long id) {
        log.debug("Request to get PhotoArchive : {}", id);
        return photoArchiveRepository.findById(id).map(photoArchiveMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhotoArchive : {}", id);
        photoArchiveRepository.deleteById(id);
    }
}
