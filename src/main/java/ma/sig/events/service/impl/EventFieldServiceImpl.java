package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.EventField;
import ma.sig.events.repository.EventFieldRepository;
import ma.sig.events.service.EventFieldService;
import ma.sig.events.service.dto.EventFieldDTO;
import ma.sig.events.service.mapper.EventFieldMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventField}.
 */
@Service
@Transactional
public class EventFieldServiceImpl implements EventFieldService {

    private final Logger log = LoggerFactory.getLogger(EventFieldServiceImpl.class);

    private final EventFieldRepository eventFieldRepository;

    private final EventFieldMapper eventFieldMapper;

    public EventFieldServiceImpl(EventFieldRepository eventFieldRepository, EventFieldMapper eventFieldMapper) {
        this.eventFieldRepository = eventFieldRepository;
        this.eventFieldMapper = eventFieldMapper;
    }

    @Override
    public EventFieldDTO save(EventFieldDTO eventFieldDTO) {
        log.debug("Request to save EventField : {}", eventFieldDTO);
        EventField eventField = eventFieldMapper.toEntity(eventFieldDTO);
        eventField = eventFieldRepository.save(eventField);
        return eventFieldMapper.toDto(eventField);
    }

    @Override
    public EventFieldDTO update(EventFieldDTO eventFieldDTO) {
        log.debug("Request to update EventField : {}", eventFieldDTO);
        EventField eventField = eventFieldMapper.toEntity(eventFieldDTO);
        eventField = eventFieldRepository.save(eventField);
        return eventFieldMapper.toDto(eventField);
    }

    @Override
    public Optional<EventFieldDTO> partialUpdate(EventFieldDTO eventFieldDTO) {
        log.debug("Request to partially update EventField : {}", eventFieldDTO);

        return eventFieldRepository
            .findById(eventFieldDTO.getFieldId())
            .map(existingEventField -> {
                eventFieldMapper.partialUpdate(existingEventField, eventFieldDTO);

                return existingEventField;
            })
            .map(eventFieldRepository::save)
            .map(eventFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventFieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventFields");
        return eventFieldRepository.findAll(pageable).map(eventFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventFieldDTO> findOne(Long id) {
        log.debug("Request to get EventField : {}", id);
        return eventFieldRepository.findById(id).map(eventFieldMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventField : {}", id);
        eventFieldRepository.deleteById(id);
    }
}
