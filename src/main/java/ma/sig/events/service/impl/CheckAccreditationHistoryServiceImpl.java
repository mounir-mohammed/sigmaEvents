package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.repository.CheckAccreditationHistoryRepository;
import ma.sig.events.service.CheckAccreditationHistoryService;
import ma.sig.events.service.dto.CheckAccreditationHistoryDTO;
import ma.sig.events.service.mapper.CheckAccreditationHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckAccreditationHistory}.
 */
@Service
@Transactional
public class CheckAccreditationHistoryServiceImpl implements CheckAccreditationHistoryService {

    private final Logger log = LoggerFactory.getLogger(CheckAccreditationHistoryServiceImpl.class);

    private final CheckAccreditationHistoryRepository checkAccreditationHistoryRepository;

    private final CheckAccreditationHistoryMapper checkAccreditationHistoryMapper;

    public CheckAccreditationHistoryServiceImpl(
        CheckAccreditationHistoryRepository checkAccreditationHistoryRepository,
        CheckAccreditationHistoryMapper checkAccreditationHistoryMapper
    ) {
        this.checkAccreditationHistoryRepository = checkAccreditationHistoryRepository;
        this.checkAccreditationHistoryMapper = checkAccreditationHistoryMapper;
    }

    @Override
    public CheckAccreditationHistoryDTO save(CheckAccreditationHistoryDTO checkAccreditationHistoryDTO) {
        log.debug("Request to save CheckAccreditationHistory : {}", checkAccreditationHistoryDTO);
        CheckAccreditationHistory checkAccreditationHistory = checkAccreditationHistoryMapper.toEntity(checkAccreditationHistoryDTO);
        checkAccreditationHistory = checkAccreditationHistoryRepository.save(checkAccreditationHistory);
        return checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);
    }

    @Override
    public CheckAccreditationHistoryDTO update(CheckAccreditationHistoryDTO checkAccreditationHistoryDTO) {
        log.debug("Request to update CheckAccreditationHistory : {}", checkAccreditationHistoryDTO);
        CheckAccreditationHistory checkAccreditationHistory = checkAccreditationHistoryMapper.toEntity(checkAccreditationHistoryDTO);
        checkAccreditationHistory = checkAccreditationHistoryRepository.save(checkAccreditationHistory);
        return checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);
    }

    @Override
    public Optional<CheckAccreditationHistoryDTO> partialUpdate(CheckAccreditationHistoryDTO checkAccreditationHistoryDTO) {
        log.debug("Request to partially update CheckAccreditationHistory : {}", checkAccreditationHistoryDTO);

        return checkAccreditationHistoryRepository
            .findById(checkAccreditationHistoryDTO.getCheckAccreditationHistoryId())
            .map(existingCheckAccreditationHistory -> {
                checkAccreditationHistoryMapper.partialUpdate(existingCheckAccreditationHistory, checkAccreditationHistoryDTO);

                return existingCheckAccreditationHistory;
            })
            .map(checkAccreditationHistoryRepository::save)
            .map(checkAccreditationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CheckAccreditationHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckAccreditationHistories");
        return checkAccreditationHistoryRepository.findAll(pageable).map(checkAccreditationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckAccreditationHistoryDTO> findOne(Long id) {
        log.debug("Request to get CheckAccreditationHistory : {}", id);
        return checkAccreditationHistoryRepository.findById(id).map(checkAccreditationHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckAccreditationHistory : {}", id);
        checkAccreditationHistoryRepository.deleteById(id);
    }
}
