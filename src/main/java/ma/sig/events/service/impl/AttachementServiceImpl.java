package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Attachement;
import ma.sig.events.repository.AttachementRepository;
import ma.sig.events.service.AttachementService;
import ma.sig.events.service.dto.AttachementDTO;
import ma.sig.events.service.mapper.AttachementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Attachement}.
 */
@Service
@Transactional
public class AttachementServiceImpl implements AttachementService {

    private final Logger log = LoggerFactory.getLogger(AttachementServiceImpl.class);

    private final AttachementRepository attachementRepository;

    private final AttachementMapper attachementMapper;

    public AttachementServiceImpl(AttachementRepository attachementRepository, AttachementMapper attachementMapper) {
        this.attachementRepository = attachementRepository;
        this.attachementMapper = attachementMapper;
    }

    @Override
    public AttachementDTO save(AttachementDTO attachementDTO) {
        log.debug("Request to save Attachement : {}", attachementDTO);
        Attachement attachement = attachementMapper.toEntity(attachementDTO);
        attachement = attachementRepository.save(attachement);
        return attachementMapper.toDto(attachement);
    }

    @Override
    public AttachementDTO update(AttachementDTO attachementDTO) {
        log.debug("Request to update Attachement : {}", attachementDTO);
        Attachement attachement = attachementMapper.toEntity(attachementDTO);
        attachement = attachementRepository.save(attachement);
        return attachementMapper.toDto(attachement);
    }

    @Override
    public Optional<AttachementDTO> partialUpdate(AttachementDTO attachementDTO) {
        log.debug("Request to partially update Attachement : {}", attachementDTO);

        return attachementRepository
            .findById(attachementDTO.getAttachementId())
            .map(existingAttachement -> {
                attachementMapper.partialUpdate(existingAttachement, attachementDTO);

                return existingAttachement;
            })
            .map(attachementRepository::save)
            .map(attachementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attachements");
        return attachementRepository.findAll(pageable).map(attachementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachementDTO> findOne(Long id) {
        log.debug("Request to get Attachement : {}", id);
        return attachementRepository.findById(id).map(attachementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attachement : {}", id);
        attachementRepository.deleteById(id);
    }
}
