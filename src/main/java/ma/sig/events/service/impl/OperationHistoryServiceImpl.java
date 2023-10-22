package ma.sig.events.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.OperationHistory;
import ma.sig.events.domain.OperationType;
import ma.sig.events.repository.EventRepository;
import ma.sig.events.repository.OperationHistoryRepository;
import ma.sig.events.repository.OperationTypeRepository;
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

    private final OperationTypeRepository operationTypeRepository;

    private final EventRepository eventRepository;

    public OperationHistoryServiceImpl(
        OperationHistoryRepository operationHistoryRepository,
        OperationHistoryMapper operationHistoryMapper,
        OperationTypeRepository operationTypeRepository,
        EventRepository eventRepository
    ) {
        this.operationHistoryRepository = operationHistoryRepository;
        this.operationHistoryMapper = operationHistoryMapper;
        this.operationTypeRepository = operationTypeRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public OperationHistoryDTO save(OperationHistoryDTO operationHistoryDTO) {
        List<String> paramsSegments = this.getOperationHistoryParamsSegments(operationHistoryDTO.getOperationHistoryParams());
        if (!paramsSegments.isEmpty() && paramsSegments.size() > 1) {
            Event event;
            OperationType operationType;
            OperationHistoryDTO toGo = null;

            if (operationHistoryDTO.getEvent() != null) {
                event = eventRepository.findById(operationHistoryDTO.getEvent().getEventId()).get();
            } else {
                event = null;
            }

            if (operationHistoryDTO.getTypeoperation() != null) {
                operationType = operationTypeRepository.findById(operationHistoryDTO.getTypeoperation().getOperationTypeId()).get();
            } else {
                operationType = null;
            }

            for (String segment : paramsSegments) {
                OperationHistoryDTO segmentDTO = new OperationHistoryDTO();

                segmentDTO.setOperationHistoryDescription(operationHistoryDTO.getOperationHistoryDescription());
                segmentDTO.setOperationHistoryDate(operationHistoryDTO.getOperationHistoryDate());
                segmentDTO.setOperationHistoryUserID(operationHistoryDTO.getOperationHistoryUserID());
                segmentDTO.setOperationHistoryOldValue(operationHistoryDTO.getOperationHistoryOldValue());
                segmentDTO.setOperationHistoryNewValue(operationHistoryDTO.getOperationHistoryNewValue());
                segmentDTO.setOperationHistoryOldId(operationHistoryDTO.getOperationHistoryOldId());
                segmentDTO.setOperationHistoryNewId(operationHistoryDTO.getOperationHistoryNewId());
                segmentDTO.setOperationHistoryImportedFile(operationHistoryDTO.getOperationHistoryImportedFile());
                segmentDTO.setOperationHistoryImportedFilePath(operationHistoryDTO.getOperationHistoryImportedFilePath());
                segmentDTO.setOperationHistoryParams(segment);
                segmentDTO.setOperationHistoryAttributs(operationHistoryDTO.getOperationHistoryAttributs());
                segmentDTO.setOperationHistoryStat(operationHistoryDTO.getOperationHistoryStat());
                log.debug("Request to save OperationHistory segment: {}", segmentDTO);

                OperationHistory operationHistory = operationHistoryMapper.toEntity(segmentDTO);
                operationHistory.setEvent(event);
                operationHistory.setTypeoperation(operationType);
                operationHistory = operationHistoryRepository.save(operationHistory);
                toGo = operationHistoryMapper.toDto(operationHistory);
            }
            return toGo;
        } else {
            log.debug("Request to save one segment OperationHistory : {}", operationHistoryDTO);
            OperationHistory operationHistory = operationHistoryMapper.toEntity(operationHistoryDTO);
            operationHistory = operationHistoryRepository.save(operationHistory);
            return operationHistoryMapper.toDto(operationHistory);
        }
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

    public List<String> getOperationHistoryParamsSegments(String operationHistoryDescription) {
        List<String> segments = new ArrayList<>();
        if (operationHistoryDescription != null) {
            int maxSegmentLength = 200;
            for (int i = 0; i < operationHistoryDescription.length(); i += maxSegmentLength) {
                int endIndex = Math.min(i + maxSegmentLength, operationHistoryDescription.length());
                segments.add(operationHistoryDescription.substring(i, endIndex));
            }
        }
        return segments;
    }
}
