package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.OperationHistory;
import ma.sig.events.repository.OperationHistoryRepository;
import ma.sig.events.service.OperationHistoryService;
import ma.sig.events.service.dto.OperationHistoryDTO;
import ma.sig.events.service.mapper.OperationHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OperationHistory}.
 */
@Service
@Transactional
public class OperationHistoryServiceImpl implements OperationHistoryService {

    private final Logger log = LoggerFactory.getLogger(OperationHistoryServiceImpl.class);

    private final OperationHistoryRepository operationHistoryRepository;

    private final OperationHistoryMapper operationHistoryMapper;

    public OperationHistoryServiceImpl(
        OperationHistoryRepository operationHistoryRepository,
        OperationHistoryMapper operationHistoryMapper
    ) {
        this.operationHistoryRepository = operationHistoryRepository;
        this.operationHistoryMapper = operationHistoryMapper;
    }

    @Override
    public OperationHistoryDTO save(OperationHistoryDTO operationHistoryDTO) {
        log.debug("Request to save OperationHistory : {}", operationHistoryDTO);
        OperationHistory operationHistory = operationHistoryMapper.toEntity(operationHistoryDTO);
        operationHistory = operationHistoryRepository.save(operationHistory);
        return operationHistoryMapper.toDto(operationHistory);
    }

    @Override
    public OperationHistoryDTO update(OperationHistoryDTO operationHistoryDTO) {
        log.debug("Request to update OperationHistory : {}", operationHistoryDTO);
        OperationHistory operationHistory = operationHistoryMapper.toEntity(operationHistoryDTO);
        operationHistory = operationHistoryRepository.save(operationHistory);
        return operationHistoryMapper.toDto(operationHistory);
    }

    @Override
    public Optional<OperationHistoryDTO> partialUpdate(OperationHistoryDTO operationHistoryDTO) {
        log.debug("Request to partially update OperationHistory : {}", operationHistoryDTO);

        return operationHistoryRepository
            .findById(operationHistoryDTO.getOperationHistoryId())
            .map(existingOperationHistory -> {
                operationHistoryMapper.partialUpdate(existingOperationHistory, operationHistoryDTO);

                return existingOperationHistory;
            })
            .map(operationHistoryRepository::save)
            .map(operationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OperationHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OperationHistories");
        return operationHistoryRepository.findAll(pageable).map(operationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperationHistoryDTO> findOne(Long id) {
        log.debug("Request to get OperationHistory : {}", id);
        return operationHistoryRepository.findById(id).map(operationHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperationHistory : {}", id);
        operationHistoryRepository.deleteById(id);
    }
}
