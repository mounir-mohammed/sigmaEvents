package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.EventForm;
import ma.sig.events.repository.EventFormRepository;
import ma.sig.events.service.EventFormService;
import ma.sig.events.service.dto.EventFormDTO;
import ma.sig.events.service.mapper.EventFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventForm}.
 */
@Service
@Transactional
public class EventFormServiceImpl implements EventFormService {

    private final Logger log = LoggerFactory.getLogger(EventFormServiceImpl.class);

    private final EventFormRepository eventFormRepository;

    private final EventFormMapper eventFormMapper;

    public EventFormServiceImpl(EventFormRepository eventFormRepository, EventFormMapper eventFormMapper) {
        this.eventFormRepository = eventFormRepository;
        this.eventFormMapper = eventFormMapper;
    }

    @Override
    public EventFormDTO save(EventFormDTO eventFormDTO) {
        log.debug("Request to save EventForm : {}", eventFormDTO);
        EventForm eventForm = eventFormMapper.toEntity(eventFormDTO);
        eventForm = eventFormRepository.save(eventForm);
        return eventFormMapper.toDto(eventForm);
    }

    @Override
    public EventFormDTO update(EventFormDTO eventFormDTO) {
        log.debug("Request to update EventForm : {}", eventFormDTO);
        EventForm eventForm = eventFormMapper.toEntity(eventFormDTO);
        eventForm = eventFormRepository.save(eventForm);
        return eventFormMapper.toDto(eventForm);
    }

    @Override
    public Optional<EventFormDTO> partialUpdate(EventFormDTO eventFormDTO) {
        log.debug("Request to partially update EventForm : {}", eventFormDTO);

        return eventFormRepository
            .findById(eventFormDTO.getFormId())
            .map(existingEventForm -> {
                eventFormMapper.partialUpdate(existingEventForm, eventFormDTO);

                return existingEventForm;
            })
            .map(eventFormRepository::save)
            .map(eventFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventForms");
        return eventFormRepository.findAll(pageable).map(eventFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventFormDTO> findOne(Long id) {
        log.debug("Request to get EventForm : {}", id);
        return eventFormRepository.findById(id).map(eventFormMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventForm : {}", id);
        eventFormRepository.deleteById(id);
    }
}
