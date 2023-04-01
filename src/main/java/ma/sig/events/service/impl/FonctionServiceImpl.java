package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Fonction;
import ma.sig.events.repository.FonctionRepository;
import ma.sig.events.service.FonctionService;
import ma.sig.events.service.dto.FonctionDTO;
import ma.sig.events.service.mapper.FonctionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Fonction}.
 */
@Service
@Transactional
public class FonctionServiceImpl implements FonctionService {

    private final Logger log = LoggerFactory.getLogger(FonctionServiceImpl.class);

    private final FonctionRepository fonctionRepository;

    private final FonctionMapper fonctionMapper;

    public FonctionServiceImpl(FonctionRepository fonctionRepository, FonctionMapper fonctionMapper) {
        this.fonctionRepository = fonctionRepository;
        this.fonctionMapper = fonctionMapper;
    }

    @Override
    public FonctionDTO save(FonctionDTO fonctionDTO) {
        log.debug("Request to save Fonction : {}", fonctionDTO);
        Fonction fonction = fonctionMapper.toEntity(fonctionDTO);
        fonction = fonctionRepository.save(fonction);
        return fonctionMapper.toDto(fonction);
    }

    @Override
    public FonctionDTO update(FonctionDTO fonctionDTO) {
        log.debug("Request to update Fonction : {}", fonctionDTO);
        Fonction fonction = fonctionMapper.toEntity(fonctionDTO);
        fonction = fonctionRepository.save(fonction);
        return fonctionMapper.toDto(fonction);
    }

    @Override
    public Optional<FonctionDTO> partialUpdate(FonctionDTO fonctionDTO) {
        log.debug("Request to partially update Fonction : {}", fonctionDTO);

        return fonctionRepository
            .findById(fonctionDTO.getFonctionId())
            .map(existingFonction -> {
                fonctionMapper.partialUpdate(existingFonction, fonctionDTO);

                return existingFonction;
            })
            .map(fonctionRepository::save)
            .map(fonctionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FonctionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fonctions");
        return fonctionRepository.findAll(pageable).map(fonctionMapper::toDto);
    }

    public Page<FonctionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return fonctionRepository.findAllWithEagerRelationships(pageable).map(fonctionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FonctionDTO> findOne(Long id) {
        log.debug("Request to get Fonction : {}", id);
        return fonctionRepository.findOneWithEagerRelationships(id).map(fonctionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fonction : {}", id);
        fonctionRepository.deleteById(id);
    }
}
