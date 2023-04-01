package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.InfoSupp;
import ma.sig.events.repository.InfoSuppRepository;
import ma.sig.events.service.InfoSuppService;
import ma.sig.events.service.dto.InfoSuppDTO;
import ma.sig.events.service.mapper.InfoSuppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InfoSupp}.
 */
@Service
@Transactional
public class InfoSuppServiceImpl implements InfoSuppService {

    private final Logger log = LoggerFactory.getLogger(InfoSuppServiceImpl.class);

    private final InfoSuppRepository infoSuppRepository;

    private final InfoSuppMapper infoSuppMapper;

    public InfoSuppServiceImpl(InfoSuppRepository infoSuppRepository, InfoSuppMapper infoSuppMapper) {
        this.infoSuppRepository = infoSuppRepository;
        this.infoSuppMapper = infoSuppMapper;
    }

    @Override
    public InfoSuppDTO save(InfoSuppDTO infoSuppDTO) {
        log.debug("Request to save InfoSupp : {}", infoSuppDTO);
        InfoSupp infoSupp = infoSuppMapper.toEntity(infoSuppDTO);
        infoSupp = infoSuppRepository.save(infoSupp);
        return infoSuppMapper.toDto(infoSupp);
    }

    @Override
    public InfoSuppDTO update(InfoSuppDTO infoSuppDTO) {
        log.debug("Request to update InfoSupp : {}", infoSuppDTO);
        InfoSupp infoSupp = infoSuppMapper.toEntity(infoSuppDTO);
        infoSupp = infoSuppRepository.save(infoSupp);
        return infoSuppMapper.toDto(infoSupp);
    }

    @Override
    public Optional<InfoSuppDTO> partialUpdate(InfoSuppDTO infoSuppDTO) {
        log.debug("Request to partially update InfoSupp : {}", infoSuppDTO);

        return infoSuppRepository
            .findById(infoSuppDTO.getInfoSuppId())
            .map(existingInfoSupp -> {
                infoSuppMapper.partialUpdate(existingInfoSupp, infoSuppDTO);

                return existingInfoSupp;
            })
            .map(infoSuppRepository::save)
            .map(infoSuppMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InfoSuppDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InfoSupps");
        return infoSuppRepository.findAll(pageable).map(infoSuppMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InfoSuppDTO> findOne(Long id) {
        log.debug("Request to get InfoSupp : {}", id);
        return infoSuppRepository.findById(id).map(infoSuppMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InfoSupp : {}", id);
        infoSuppRepository.deleteById(id);
    }
}
