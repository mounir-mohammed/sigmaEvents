package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.DayPassInfo;
import ma.sig.events.repository.DayPassInfoRepository;
import ma.sig.events.service.DayPassInfoService;
import ma.sig.events.service.dto.DayPassInfoDTO;
import ma.sig.events.service.mapper.DayPassInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DayPassInfo}.
 */
@Service
@Transactional
public class DayPassInfoServiceImpl implements DayPassInfoService {

    private final Logger log = LoggerFactory.getLogger(DayPassInfoServiceImpl.class);

    private final DayPassInfoRepository dayPassInfoRepository;

    private final DayPassInfoMapper dayPassInfoMapper;

    public DayPassInfoServiceImpl(DayPassInfoRepository dayPassInfoRepository, DayPassInfoMapper dayPassInfoMapper) {
        this.dayPassInfoRepository = dayPassInfoRepository;
        this.dayPassInfoMapper = dayPassInfoMapper;
    }

    @Override
    public DayPassInfoDTO save(DayPassInfoDTO dayPassInfoDTO) {
        log.debug("Request to save DayPassInfo : {}", dayPassInfoDTO);
        DayPassInfo dayPassInfo = dayPassInfoMapper.toEntity(dayPassInfoDTO);
        dayPassInfo = dayPassInfoRepository.save(dayPassInfo);
        return dayPassInfoMapper.toDto(dayPassInfo);
    }

    @Override
    public DayPassInfoDTO update(DayPassInfoDTO dayPassInfoDTO) {
        log.debug("Request to update DayPassInfo : {}", dayPassInfoDTO);
        DayPassInfo dayPassInfo = dayPassInfoMapper.toEntity(dayPassInfoDTO);
        dayPassInfo = dayPassInfoRepository.save(dayPassInfo);
        return dayPassInfoMapper.toDto(dayPassInfo);
    }

    @Override
    public Optional<DayPassInfoDTO> partialUpdate(DayPassInfoDTO dayPassInfoDTO) {
        log.debug("Request to partially update DayPassInfo : {}", dayPassInfoDTO);

        return dayPassInfoRepository
            .findById(dayPassInfoDTO.getDayPassInfoId())
            .map(existingDayPassInfo -> {
                dayPassInfoMapper.partialUpdate(existingDayPassInfo, dayPassInfoDTO);

                return existingDayPassInfo;
            })
            .map(dayPassInfoRepository::save)
            .map(dayPassInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DayPassInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DayPassInfos");
        return dayPassInfoRepository.findAll(pageable).map(dayPassInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DayPassInfoDTO> findOne(Long id) {
        log.debug("Request to get DayPassInfo : {}", id);
        return dayPassInfoRepository.findById(id).map(dayPassInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DayPassInfo : {}", id);
        dayPassInfoRepository.deleteById(id);
    }
}
