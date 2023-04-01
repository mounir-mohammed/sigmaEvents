package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.EventControl;
import ma.sig.events.repository.EventControlRepository;
import ma.sig.events.service.EventControlService;
import ma.sig.events.service.dto.EventControlDTO;
import ma.sig.events.service.mapper.EventControlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventControl}.
 */
@Service
@Transactional
public class EventControlServiceImpl implements EventControlService {

    private final Logger log = LoggerFactory.getLogger(EventControlServiceImpl.class);

    private final EventControlRepository eventControlRepository;

    private final EventControlMapper eventControlMapper;

    public EventControlServiceImpl(EventControlRepository eventControlRepository, EventControlMapper eventControlMapper) {
        this.eventControlRepository = eventControlRepository;
        this.eventControlMapper = eventControlMapper;
    }

    @Override
    public EventControlDTO save(EventControlDTO eventControlDTO) {
        log.debug("Request to save EventControl : {}", eventControlDTO);
        EventControl eventControl = eventControlMapper.toEntity(eventControlDTO);
        eventControl = eventControlRepository.save(eventControl);
        return eventControlMapper.toDto(eventControl);
    }

    @Override
    public EventControlDTO update(EventControlDTO eventControlDTO) {
        log.debug("Request to update EventControl : {}", eventControlDTO);
        EventControl eventControl = eventControlMapper.toEntity(eventControlDTO);
        eventControl = eventControlRepository.save(eventControl);
        return eventControlMapper.toDto(eventControl);
    }

    @Override
    public Optional<EventControlDTO> partialUpdate(EventControlDTO eventControlDTO) {
        log.debug("Request to partially update EventControl : {}", eventControlDTO);

        return eventControlRepository
            .findById(eventControlDTO.getControlId())
            .map(existingEventControl -> {
                eventControlMapper.partialUpdate(existingEventControl, eventControlDTO);

                return existingEventControl;
            })
            .map(eventControlRepository::save)
            .map(eventControlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventControlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventControls");
        return eventControlRepository.findAll(pageable).map(eventControlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventControlDTO> findOne(Long id) {
        log.debug("Request to get EventControl : {}", id);
        return eventControlRepository.findById(id).map(eventControlMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventControl : {}", id);
        eventControlRepository.deleteById(id);
    }
}
