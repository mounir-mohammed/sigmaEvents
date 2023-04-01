package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Cloning;
import ma.sig.events.repository.CloningRepository;
import ma.sig.events.service.CloningService;
import ma.sig.events.service.dto.CloningDTO;
import ma.sig.events.service.mapper.CloningMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cloning}.
 */
@Service
@Transactional
public class CloningServiceImpl implements CloningService {

    private final Logger log = LoggerFactory.getLogger(CloningServiceImpl.class);

    private final CloningRepository cloningRepository;

    private final CloningMapper cloningMapper;

    public CloningServiceImpl(CloningRepository cloningRepository, CloningMapper cloningMapper) {
        this.cloningRepository = cloningRepository;
        this.cloningMapper = cloningMapper;
    }

    @Override
    public CloningDTO save(CloningDTO cloningDTO) {
        log.debug("Request to save Cloning : {}", cloningDTO);
        Cloning cloning = cloningMapper.toEntity(cloningDTO);
        cloning = cloningRepository.save(cloning);
        return cloningMapper.toDto(cloning);
    }

    @Override
    public CloningDTO update(CloningDTO cloningDTO) {
        log.debug("Request to update Cloning : {}", cloningDTO);
        Cloning cloning = cloningMapper.toEntity(cloningDTO);
        cloning = cloningRepository.save(cloning);
        return cloningMapper.toDto(cloning);
    }

    @Override
    public Optional<CloningDTO> partialUpdate(CloningDTO cloningDTO) {
        log.debug("Request to partially update Cloning : {}", cloningDTO);

        return cloningRepository
            .findById(cloningDTO.getCloningId())
            .map(existingCloning -> {
                cloningMapper.partialUpdate(existingCloning, cloningDTO);

                return existingCloning;
            })
            .map(cloningRepository::save)
            .map(cloningMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CloningDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clonings");
        return cloningRepository.findAll(pageable).map(cloningMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CloningDTO> findOne(Long id) {
        log.debug("Request to get Cloning : {}", id);
        return cloningRepository.findById(id).map(cloningMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cloning : {}", id);
        cloningRepository.deleteById(id);
    }
}
