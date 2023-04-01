package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.AuthentificationType;
import ma.sig.events.repository.AuthentificationTypeRepository;
import ma.sig.events.service.AuthentificationTypeService;
import ma.sig.events.service.dto.AuthentificationTypeDTO;
import ma.sig.events.service.mapper.AuthentificationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AuthentificationType}.
 */
@Service
@Transactional
public class AuthentificationTypeServiceImpl implements AuthentificationTypeService {

    private final Logger log = LoggerFactory.getLogger(AuthentificationTypeServiceImpl.class);

    private final AuthentificationTypeRepository authentificationTypeRepository;

    private final AuthentificationTypeMapper authentificationTypeMapper;

    public AuthentificationTypeServiceImpl(
        AuthentificationTypeRepository authentificationTypeRepository,
        AuthentificationTypeMapper authentificationTypeMapper
    ) {
        this.authentificationTypeRepository = authentificationTypeRepository;
        this.authentificationTypeMapper = authentificationTypeMapper;
    }

    @Override
    public AuthentificationTypeDTO save(AuthentificationTypeDTO authentificationTypeDTO) {
        log.debug("Request to save AuthentificationType : {}", authentificationTypeDTO);
        AuthentificationType authentificationType = authentificationTypeMapper.toEntity(authentificationTypeDTO);
        authentificationType = authentificationTypeRepository.save(authentificationType);
        return authentificationTypeMapper.toDto(authentificationType);
    }

    @Override
    public AuthentificationTypeDTO update(AuthentificationTypeDTO authentificationTypeDTO) {
        log.debug("Request to update AuthentificationType : {}", authentificationTypeDTO);
        AuthentificationType authentificationType = authentificationTypeMapper.toEntity(authentificationTypeDTO);
        authentificationType = authentificationTypeRepository.save(authentificationType);
        return authentificationTypeMapper.toDto(authentificationType);
    }

    @Override
    public Optional<AuthentificationTypeDTO> partialUpdate(AuthentificationTypeDTO authentificationTypeDTO) {
        log.debug("Request to partially update AuthentificationType : {}", authentificationTypeDTO);

        return authentificationTypeRepository
            .findById(authentificationTypeDTO.getAuthentificationTypeId())
            .map(existingAuthentificationType -> {
                authentificationTypeMapper.partialUpdate(existingAuthentificationType, authentificationTypeDTO);

                return existingAuthentificationType;
            })
            .map(authentificationTypeRepository::save)
            .map(authentificationTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthentificationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuthentificationTypes");
        return authentificationTypeRepository.findAll(pageable).map(authentificationTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthentificationTypeDTO> findOne(Long id) {
        log.debug("Request to get AuthentificationType : {}", id);
        return authentificationTypeRepository.findById(id).map(authentificationTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuthentificationType : {}", id);
        authentificationTypeRepository.deleteById(id);
    }
}
