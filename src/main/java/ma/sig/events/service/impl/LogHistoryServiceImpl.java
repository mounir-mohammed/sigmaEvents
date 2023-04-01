package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.LogHistory;
import ma.sig.events.repository.LogHistoryRepository;
import ma.sig.events.service.LogHistoryService;
import ma.sig.events.service.dto.LogHistoryDTO;
import ma.sig.events.service.mapper.LogHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LogHistory}.
 */
@Service
@Transactional
public class LogHistoryServiceImpl implements LogHistoryService {

    private final Logger log = LoggerFactory.getLogger(LogHistoryServiceImpl.class);

    private final LogHistoryRepository logHistoryRepository;

    private final LogHistoryMapper logHistoryMapper;

    public LogHistoryServiceImpl(LogHistoryRepository logHistoryRepository, LogHistoryMapper logHistoryMapper) {
        this.logHistoryRepository = logHistoryRepository;
        this.logHistoryMapper = logHistoryMapper;
    }

    @Override
    public LogHistoryDTO save(LogHistoryDTO logHistoryDTO) {
        log.debug("Request to save LogHistory : {}", logHistoryDTO);
        LogHistory logHistory = logHistoryMapper.toEntity(logHistoryDTO);
        logHistory = logHistoryRepository.save(logHistory);
        return logHistoryMapper.toDto(logHistory);
    }

    @Override
    public LogHistoryDTO update(LogHistoryDTO logHistoryDTO) {
        log.debug("Request to update LogHistory : {}", logHistoryDTO);
        LogHistory logHistory = logHistoryMapper.toEntity(logHistoryDTO);
        logHistory = logHistoryRepository.save(logHistory);
        return logHistoryMapper.toDto(logHistory);
    }

    @Override
    public Optional<LogHistoryDTO> partialUpdate(LogHistoryDTO logHistoryDTO) {
        log.debug("Request to partially update LogHistory : {}", logHistoryDTO);

        return logHistoryRepository
            .findById(logHistoryDTO.getLogHistory())
            .map(existingLogHistory -> {
                logHistoryMapper.partialUpdate(existingLogHistory, logHistoryDTO);

                return existingLogHistory;
            })
            .map(logHistoryRepository::save)
            .map(logHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LogHistories");
        return logHistoryRepository.findAll(pageable).map(logHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LogHistoryDTO> findOne(Long id) {
        log.debug("Request to get LogHistory : {}", id);
        return logHistoryRepository.findById(id).map(logHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogHistory : {}", id);
        logHistoryRepository.deleteById(id);
    }
}
