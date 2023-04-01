package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.repository.PrintingCentreRepository;
import ma.sig.events.service.PrintingCentreService;
import ma.sig.events.service.dto.PrintingCentreDTO;
import ma.sig.events.service.mapper.PrintingCentreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PrintingCentre}.
 */
@Service
@Transactional
public class PrintingCentreServiceImpl implements PrintingCentreService {

    private final Logger log = LoggerFactory.getLogger(PrintingCentreServiceImpl.class);

    private final PrintingCentreRepository printingCentreRepository;

    private final PrintingCentreMapper printingCentreMapper;

    public PrintingCentreServiceImpl(PrintingCentreRepository printingCentreRepository, PrintingCentreMapper printingCentreMapper) {
        this.printingCentreRepository = printingCentreRepository;
        this.printingCentreMapper = printingCentreMapper;
    }

    @Override
    public PrintingCentreDTO save(PrintingCentreDTO printingCentreDTO) {
        log.debug("Request to save PrintingCentre : {}", printingCentreDTO);
        PrintingCentre printingCentre = printingCentreMapper.toEntity(printingCentreDTO);
        printingCentre = printingCentreRepository.save(printingCentre);
        return printingCentreMapper.toDto(printingCentre);
    }

    @Override
    public PrintingCentreDTO update(PrintingCentreDTO printingCentreDTO) {
        log.debug("Request to update PrintingCentre : {}", printingCentreDTO);
        PrintingCentre printingCentre = printingCentreMapper.toEntity(printingCentreDTO);
        printingCentre = printingCentreRepository.save(printingCentre);
        return printingCentreMapper.toDto(printingCentre);
    }

    @Override
    public Optional<PrintingCentreDTO> partialUpdate(PrintingCentreDTO printingCentreDTO) {
        log.debug("Request to partially update PrintingCentre : {}", printingCentreDTO);

        return printingCentreRepository
            .findById(printingCentreDTO.getPrintingCentreId())
            .map(existingPrintingCentre -> {
                printingCentreMapper.partialUpdate(existingPrintingCentre, printingCentreDTO);

                return existingPrintingCentre;
            })
            .map(printingCentreRepository::save)
            .map(printingCentreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrintingCentreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrintingCentres");
        return printingCentreRepository.findAll(pageable).map(printingCentreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrintingCentreDTO> findOne(Long id) {
        log.debug("Request to get PrintingCentre : {}", id);
        return printingCentreRepository.findById(id).map(printingCentreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrintingCentre : {}", id);
        printingCentreRepository.deleteById(id);
    }
}
