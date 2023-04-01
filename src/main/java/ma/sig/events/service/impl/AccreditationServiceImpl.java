package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Accreditation;
import ma.sig.events.repository.AccreditationRepository;
import ma.sig.events.service.AccreditationService;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.mapper.AccreditationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Accreditation}.
 */
@Service
@Transactional
public class AccreditationServiceImpl implements AccreditationService {

    private final Logger log = LoggerFactory.getLogger(AccreditationServiceImpl.class);

    private final AccreditationRepository accreditationRepository;

    private final AccreditationMapper accreditationMapper;

    public AccreditationServiceImpl(AccreditationRepository accreditationRepository, AccreditationMapper accreditationMapper) {
        this.accreditationRepository = accreditationRepository;
        this.accreditationMapper = accreditationMapper;
    }

    @Override
    public AccreditationDTO save(AccreditationDTO accreditationDTO) {
        log.debug("Request to save Accreditation : {}", accreditationDTO);
        Accreditation accreditation = accreditationMapper.toEntity(accreditationDTO);
        accreditation = accreditationRepository.save(accreditation);
        return accreditationMapper.toDto(accreditation);
    }

    @Override
    public AccreditationDTO update(AccreditationDTO accreditationDTO) {
        log.debug("Request to update Accreditation : {}", accreditationDTO);
        Accreditation accreditation = accreditationMapper.toEntity(accreditationDTO);
        accreditation = accreditationRepository.save(accreditation);
        return accreditationMapper.toDto(accreditation);
    }

    @Override
    public Optional<AccreditationDTO> partialUpdate(AccreditationDTO accreditationDTO) {
        log.debug("Request to partially update Accreditation : {}", accreditationDTO);

        return accreditationRepository
            .findById(accreditationDTO.getAccreditationId())
            .map(existingAccreditation -> {
                accreditationMapper.partialUpdate(existingAccreditation, accreditationDTO);

                return existingAccreditation;
            })
            .map(accreditationRepository::save)
            .map(accreditationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccreditationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accreditations");
        return accreditationRepository.findAll(pageable).map(accreditationMapper::toDto);
    }

    public Page<AccreditationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return accreditationRepository.findAllWithEagerRelationships(pageable).map(accreditationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccreditationDTO> findOne(Long id) {
        log.debug("Request to get Accreditation : {}", id);
        return accreditationRepository.findOneWithEagerRelationships(id).map(accreditationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accreditation : {}", id);
        accreditationRepository.deleteById(id);
    }
}
