package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Sexe;
import ma.sig.events.repository.SexeRepository;
import ma.sig.events.service.SexeService;
import ma.sig.events.service.dto.SexeDTO;
import ma.sig.events.service.mapper.SexeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sexe}.
 */
@Service
@Transactional
public class SexeServiceImpl implements SexeService {

    private final Logger log = LoggerFactory.getLogger(SexeServiceImpl.class);

    private final SexeRepository sexeRepository;

    private final SexeMapper sexeMapper;

    public SexeServiceImpl(SexeRepository sexeRepository, SexeMapper sexeMapper) {
        this.sexeRepository = sexeRepository;
        this.sexeMapper = sexeMapper;
    }

    @Override
    public SexeDTO save(SexeDTO sexeDTO) {
        log.debug("Request to save Sexe : {}", sexeDTO);
        Sexe sexe = sexeMapper.toEntity(sexeDTO);
        sexe = sexeRepository.save(sexe);
        return sexeMapper.toDto(sexe);
    }

    @Override
    public SexeDTO update(SexeDTO sexeDTO) {
        log.debug("Request to update Sexe : {}", sexeDTO);
        Sexe sexe = sexeMapper.toEntity(sexeDTO);
        sexe = sexeRepository.save(sexe);
        return sexeMapper.toDto(sexe);
    }

    @Override
    public Optional<SexeDTO> partialUpdate(SexeDTO sexeDTO) {
        log.debug("Request to partially update Sexe : {}", sexeDTO);

        return sexeRepository
            .findById(sexeDTO.getSexeId())
            .map(existingSexe -> {
                sexeMapper.partialUpdate(existingSexe, sexeDTO);

                return existingSexe;
            })
            .map(sexeRepository::save)
            .map(sexeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SexeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sexes");
        return sexeRepository.findAll(pageable).map(sexeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SexeDTO> findOne(Long id) {
        log.debug("Request to get Sexe : {}", id);
        return sexeRepository.findById(id).map(sexeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sexe : {}", id);
        sexeRepository.deleteById(id);
    }
}
