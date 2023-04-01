package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.CheckAccreditationReport;
import ma.sig.events.repository.CheckAccreditationReportRepository;
import ma.sig.events.service.CheckAccreditationReportService;
import ma.sig.events.service.dto.CheckAccreditationReportDTO;
import ma.sig.events.service.mapper.CheckAccreditationReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckAccreditationReport}.
 */
@Service
@Transactional
public class CheckAccreditationReportServiceImpl implements CheckAccreditationReportService {

    private final Logger log = LoggerFactory.getLogger(CheckAccreditationReportServiceImpl.class);

    private final CheckAccreditationReportRepository checkAccreditationReportRepository;

    private final CheckAccreditationReportMapper checkAccreditationReportMapper;

    public CheckAccreditationReportServiceImpl(
        CheckAccreditationReportRepository checkAccreditationReportRepository,
        CheckAccreditationReportMapper checkAccreditationReportMapper
    ) {
        this.checkAccreditationReportRepository = checkAccreditationReportRepository;
        this.checkAccreditationReportMapper = checkAccreditationReportMapper;
    }

    @Override
    public CheckAccreditationReportDTO save(CheckAccreditationReportDTO checkAccreditationReportDTO) {
        log.debug("Request to save CheckAccreditationReport : {}", checkAccreditationReportDTO);
        CheckAccreditationReport checkAccreditationReport = checkAccreditationReportMapper.toEntity(checkAccreditationReportDTO);
        checkAccreditationReport = checkAccreditationReportRepository.save(checkAccreditationReport);
        return checkAccreditationReportMapper.toDto(checkAccreditationReport);
    }

    @Override
    public CheckAccreditationReportDTO update(CheckAccreditationReportDTO checkAccreditationReportDTO) {
        log.debug("Request to update CheckAccreditationReport : {}", checkAccreditationReportDTO);
        CheckAccreditationReport checkAccreditationReport = checkAccreditationReportMapper.toEntity(checkAccreditationReportDTO);
        checkAccreditationReport = checkAccreditationReportRepository.save(checkAccreditationReport);
        return checkAccreditationReportMapper.toDto(checkAccreditationReport);
    }

    @Override
    public Optional<CheckAccreditationReportDTO> partialUpdate(CheckAccreditationReportDTO checkAccreditationReportDTO) {
        log.debug("Request to partially update CheckAccreditationReport : {}", checkAccreditationReportDTO);

        return checkAccreditationReportRepository
            .findById(checkAccreditationReportDTO.getCheckAccreditationReportId())
            .map(existingCheckAccreditationReport -> {
                checkAccreditationReportMapper.partialUpdate(existingCheckAccreditationReport, checkAccreditationReportDTO);

                return existingCheckAccreditationReport;
            })
            .map(checkAccreditationReportRepository::save)
            .map(checkAccreditationReportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CheckAccreditationReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckAccreditationReports");
        return checkAccreditationReportRepository.findAll(pageable).map(checkAccreditationReportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckAccreditationReportDTO> findOne(Long id) {
        log.debug("Request to get CheckAccreditationReport : {}", id);
        return checkAccreditationReportRepository.findById(id).map(checkAccreditationReportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckAccreditationReport : {}", id);
        checkAccreditationReportRepository.deleteById(id);
    }
}
