package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.AccreditationType;
import ma.sig.events.repository.AccreditationTypeRepository;
import ma.sig.events.service.AccreditationTypeService;
import ma.sig.events.service.dto.AccreditationTypeDTO;
import ma.sig.events.service.mapper.AccreditationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccreditationType}.
 */
@Service
@Transactional
public class AccreditationTypeServiceImpl implements AccreditationTypeService {

    private final Logger log = LoggerFactory.getLogger(AccreditationTypeServiceImpl.class);

    private final AccreditationTypeRepository accreditationTypeRepository;

    private final AccreditationTypeMapper accreditationTypeMapper;

    public AccreditationTypeServiceImpl(
        AccreditationTypeRepository accreditationTypeRepository,
        AccreditationTypeMapper accreditationTypeMapper
    ) {
        this.accreditationTypeRepository = accreditationTypeRepository;
        this.accreditationTypeMapper = accreditationTypeMapper;
    }

    @Override
    public AccreditationTypeDTO save(AccreditationTypeDTO accreditationTypeDTO) {
        log.debug("Request to save AccreditationType : {}", accreditationTypeDTO);
        AccreditationType accreditationType = accreditationTypeMapper.toEntity(accreditationTypeDTO);
        accreditationType = accreditationTypeRepository.save(accreditationType);
        return accreditationTypeMapper.toDto(accreditationType);
    }

    @Override
    public AccreditationTypeDTO update(AccreditationTypeDTO accreditationTypeDTO) {
        log.debug("Request to update AccreditationType : {}", accreditationTypeDTO);
        AccreditationType accreditationType = accreditationTypeMapper.toEntity(accreditationTypeDTO);
        accreditationType = accreditationTypeRepository.save(accreditationType);
        return accreditationTypeMapper.toDto(accreditationType);
    }

    @Override
    public Optional<AccreditationTypeDTO> partialUpdate(AccreditationTypeDTO accreditationTypeDTO) {
        log.debug("Request to partially update AccreditationType : {}", accreditationTypeDTO);

        return accreditationTypeRepository
            .findById(accreditationTypeDTO.getAccreditationTypeId())
            .map(existingAccreditationType -> {
                accreditationTypeMapper.partialUpdate(existingAccreditationType, accreditationTypeDTO);

                return existingAccreditationType;
            })
            .map(accreditationTypeRepository::save)
            .map(accreditationTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccreditationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccreditationTypes");
        return accreditationTypeRepository.findAll(pageable).map(accreditationTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccreditationTypeDTO> findOne(Long id) {
        log.debug("Request to get AccreditationType : {}", id);
        return accreditationTypeRepository.findById(id).map(accreditationTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccreditationType : {}", id);
        accreditationTypeRepository.deleteById(id);
    }
}
