package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.InfoSuppType;
import ma.sig.events.repository.InfoSuppTypeRepository;
import ma.sig.events.service.InfoSuppTypeService;
import ma.sig.events.service.dto.InfoSuppTypeDTO;
import ma.sig.events.service.mapper.InfoSuppTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InfoSuppType}.
 */
@Service
@Transactional
public class InfoSuppTypeServiceImpl implements InfoSuppTypeService {

    private final Logger log = LoggerFactory.getLogger(InfoSuppTypeServiceImpl.class);

    private final InfoSuppTypeRepository infoSuppTypeRepository;

    private final InfoSuppTypeMapper infoSuppTypeMapper;

    public InfoSuppTypeServiceImpl(InfoSuppTypeRepository infoSuppTypeRepository, InfoSuppTypeMapper infoSuppTypeMapper) {
        this.infoSuppTypeRepository = infoSuppTypeRepository;
        this.infoSuppTypeMapper = infoSuppTypeMapper;
    }

    @Override
    public InfoSuppTypeDTO save(InfoSuppTypeDTO infoSuppTypeDTO) {
        log.debug("Request to save InfoSuppType : {}", infoSuppTypeDTO);
        InfoSuppType infoSuppType = infoSuppTypeMapper.toEntity(infoSuppTypeDTO);
        infoSuppType = infoSuppTypeRepository.save(infoSuppType);
        return infoSuppTypeMapper.toDto(infoSuppType);
    }

    @Override
    public InfoSuppTypeDTO update(InfoSuppTypeDTO infoSuppTypeDTO) {
        log.debug("Request to update InfoSuppType : {}", infoSuppTypeDTO);
        InfoSuppType infoSuppType = infoSuppTypeMapper.toEntity(infoSuppTypeDTO);
        infoSuppType = infoSuppTypeRepository.save(infoSuppType);
        return infoSuppTypeMapper.toDto(infoSuppType);
    }

    @Override
    public Optional<InfoSuppTypeDTO> partialUpdate(InfoSuppTypeDTO infoSuppTypeDTO) {
        log.debug("Request to partially update InfoSuppType : {}", infoSuppTypeDTO);

        return infoSuppTypeRepository
            .findById(infoSuppTypeDTO.getInfoSuppTypeId())
            .map(existingInfoSuppType -> {
                infoSuppTypeMapper.partialUpdate(existingInfoSuppType, infoSuppTypeDTO);

                return existingInfoSuppType;
            })
            .map(infoSuppTypeRepository::save)
            .map(infoSuppTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InfoSuppTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InfoSuppTypes");
        return infoSuppTypeRepository.findAll(pageable).map(infoSuppTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InfoSuppTypeDTO> findOne(Long id) {
        log.debug("Request to get InfoSuppType : {}", id);
        return infoSuppTypeRepository.findById(id).map(infoSuppTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InfoSuppType : {}", id);
        infoSuppTypeRepository.deleteById(id);
    }
}
