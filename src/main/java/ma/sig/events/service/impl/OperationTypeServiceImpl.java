package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.OperationType;
import ma.sig.events.repository.OperationTypeRepository;
import ma.sig.events.service.OperationTypeService;
import ma.sig.events.service.dto.OperationTypeDTO;
import ma.sig.events.service.mapper.OperationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OperationType}.
 */
@Service
@Transactional
public class OperationTypeServiceImpl implements OperationTypeService {

    private final Logger log = LoggerFactory.getLogger(OperationTypeServiceImpl.class);

    private final OperationTypeRepository operationTypeRepository;

    private final OperationTypeMapper operationTypeMapper;

    public OperationTypeServiceImpl(OperationTypeRepository operationTypeRepository, OperationTypeMapper operationTypeMapper) {
        this.operationTypeRepository = operationTypeRepository;
        this.operationTypeMapper = operationTypeMapper;
    }

    @Override
    public OperationTypeDTO save(OperationTypeDTO operationTypeDTO) {
        log.debug("Request to save OperationType : {}", operationTypeDTO);
        OperationType operationType = operationTypeMapper.toEntity(operationTypeDTO);
        operationType = operationTypeRepository.save(operationType);
        return operationTypeMapper.toDto(operationType);
    }

    @Override
    public OperationTypeDTO update(OperationTypeDTO operationTypeDTO) {
        log.debug("Request to update OperationType : {}", operationTypeDTO);
        OperationType operationType = operationTypeMapper.toEntity(operationTypeDTO);
        operationType = operationTypeRepository.save(operationType);
        return operationTypeMapper.toDto(operationType);
    }

    @Override
    public Optional<OperationTypeDTO> partialUpdate(OperationTypeDTO operationTypeDTO) {
        log.debug("Request to partially update OperationType : {}", operationTypeDTO);

        return operationTypeRepository
            .findById(operationTypeDTO.getOperationTypeId())
            .map(existingOperationType -> {
                operationTypeMapper.partialUpdate(existingOperationType, operationTypeDTO);

                return existingOperationType;
            })
            .map(operationTypeRepository::save)
            .map(operationTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OperationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OperationTypes");
        return operationTypeRepository.findAll(pageable).map(operationTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperationTypeDTO> findOne(Long id) {
        log.debug("Request to get OperationType : {}", id);
        return operationTypeRepository.findById(id).map(operationTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperationType : {}", id);
        operationTypeRepository.deleteById(id);
    }
}
