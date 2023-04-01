package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.AttachementType;
import ma.sig.events.repository.AttachementTypeRepository;
import ma.sig.events.service.AttachementTypeService;
import ma.sig.events.service.dto.AttachementTypeDTO;
import ma.sig.events.service.mapper.AttachementTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttachementType}.
 */
@Service
@Transactional
public class AttachementTypeServiceImpl implements AttachementTypeService {

    private final Logger log = LoggerFactory.getLogger(AttachementTypeServiceImpl.class);

    private final AttachementTypeRepository attachementTypeRepository;

    private final AttachementTypeMapper attachementTypeMapper;

    public AttachementTypeServiceImpl(AttachementTypeRepository attachementTypeRepository, AttachementTypeMapper attachementTypeMapper) {
        this.attachementTypeRepository = attachementTypeRepository;
        this.attachementTypeMapper = attachementTypeMapper;
    }

    @Override
    public AttachementTypeDTO save(AttachementTypeDTO attachementTypeDTO) {
        log.debug("Request to save AttachementType : {}", attachementTypeDTO);
        AttachementType attachementType = attachementTypeMapper.toEntity(attachementTypeDTO);
        attachementType = attachementTypeRepository.save(attachementType);
        return attachementTypeMapper.toDto(attachementType);
    }

    @Override
    public AttachementTypeDTO update(AttachementTypeDTO attachementTypeDTO) {
        log.debug("Request to update AttachementType : {}", attachementTypeDTO);
        AttachementType attachementType = attachementTypeMapper.toEntity(attachementTypeDTO);
        attachementType = attachementTypeRepository.save(attachementType);
        return attachementTypeMapper.toDto(attachementType);
    }

    @Override
    public Optional<AttachementTypeDTO> partialUpdate(AttachementTypeDTO attachementTypeDTO) {
        log.debug("Request to partially update AttachementType : {}", attachementTypeDTO);

        return attachementTypeRepository
            .findById(attachementTypeDTO.getAttachementTypeId())
            .map(existingAttachementType -> {
                attachementTypeMapper.partialUpdate(existingAttachementType, attachementTypeDTO);

                return existingAttachementType;
            })
            .map(attachementTypeRepository::save)
            .map(attachementTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachementTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachementTypes");
        return attachementTypeRepository.findAll(pageable).map(attachementTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachementTypeDTO> findOne(Long id) {
        log.debug("Request to get AttachementType : {}", id);
        return attachementTypeRepository.findById(id).map(attachementTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttachementType : {}", id);
        attachementTypeRepository.deleteById(id);
    }
}
