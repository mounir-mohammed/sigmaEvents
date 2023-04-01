package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Civility;
import ma.sig.events.repository.CivilityRepository;
import ma.sig.events.service.CivilityService;
import ma.sig.events.service.dto.CivilityDTO;
import ma.sig.events.service.mapper.CivilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Civility}.
 */
@Service
@Transactional
public class CivilityServiceImpl implements CivilityService {

    private final Logger log = LoggerFactory.getLogger(CivilityServiceImpl.class);

    private final CivilityRepository civilityRepository;

    private final CivilityMapper civilityMapper;

    public CivilityServiceImpl(CivilityRepository civilityRepository, CivilityMapper civilityMapper) {
        this.civilityRepository = civilityRepository;
        this.civilityMapper = civilityMapper;
    }

    @Override
    public CivilityDTO save(CivilityDTO civilityDTO) {
        log.debug("Request to save Civility : {}", civilityDTO);
        Civility civility = civilityMapper.toEntity(civilityDTO);
        civility = civilityRepository.save(civility);
        return civilityMapper.toDto(civility);
    }

    @Override
    public CivilityDTO update(CivilityDTO civilityDTO) {
        log.debug("Request to update Civility : {}", civilityDTO);
        Civility civility = civilityMapper.toEntity(civilityDTO);
        civility = civilityRepository.save(civility);
        return civilityMapper.toDto(civility);
    }

    @Override
    public Optional<CivilityDTO> partialUpdate(CivilityDTO civilityDTO) {
        log.debug("Request to partially update Civility : {}", civilityDTO);

        return civilityRepository
            .findById(civilityDTO.getCivilityId())
            .map(existingCivility -> {
                civilityMapper.partialUpdate(existingCivility, civilityDTO);

                return existingCivility;
            })
            .map(civilityRepository::save)
            .map(civilityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CivilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Civilities");
        return civilityRepository.findAll(pageable).map(civilityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CivilityDTO> findOne(Long id) {
        log.debug("Request to get Civility : {}", id);
        return civilityRepository.findById(id).map(civilityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Civility : {}", id);
        civilityRepository.deleteById(id);
    }
}
