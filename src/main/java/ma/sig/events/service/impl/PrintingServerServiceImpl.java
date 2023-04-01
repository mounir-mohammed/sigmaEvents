package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.PrintingServer;
import ma.sig.events.repository.PrintingServerRepository;
import ma.sig.events.service.PrintingServerService;
import ma.sig.events.service.dto.PrintingServerDTO;
import ma.sig.events.service.mapper.PrintingServerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PrintingServer}.
 */
@Service
@Transactional
public class PrintingServerServiceImpl implements PrintingServerService {

    private final Logger log = LoggerFactory.getLogger(PrintingServerServiceImpl.class);

    private final PrintingServerRepository printingServerRepository;

    private final PrintingServerMapper printingServerMapper;

    public PrintingServerServiceImpl(PrintingServerRepository printingServerRepository, PrintingServerMapper printingServerMapper) {
        this.printingServerRepository = printingServerRepository;
        this.printingServerMapper = printingServerMapper;
    }

    @Override
    public PrintingServerDTO save(PrintingServerDTO printingServerDTO) {
        log.debug("Request to save PrintingServer : {}", printingServerDTO);
        PrintingServer printingServer = printingServerMapper.toEntity(printingServerDTO);
        printingServer = printingServerRepository.save(printingServer);
        return printingServerMapper.toDto(printingServer);
    }

    @Override
    public PrintingServerDTO update(PrintingServerDTO printingServerDTO) {
        log.debug("Request to update PrintingServer : {}", printingServerDTO);
        PrintingServer printingServer = printingServerMapper.toEntity(printingServerDTO);
        printingServer = printingServerRepository.save(printingServer);
        return printingServerMapper.toDto(printingServer);
    }

    @Override
    public Optional<PrintingServerDTO> partialUpdate(PrintingServerDTO printingServerDTO) {
        log.debug("Request to partially update PrintingServer : {}", printingServerDTO);

        return printingServerRepository
            .findById(printingServerDTO.getPrintingServerId())
            .map(existingPrintingServer -> {
                printingServerMapper.partialUpdate(existingPrintingServer, printingServerDTO);

                return existingPrintingServer;
            })
            .map(printingServerRepository::save)
            .map(printingServerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrintingServerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrintingServers");
        return printingServerRepository.findAll(pageable).map(printingServerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrintingServerDTO> findOne(Long id) {
        log.debug("Request to get PrintingServer : {}", id);
        return printingServerRepository.findById(id).map(printingServerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrintingServer : {}", id);
        printingServerRepository.deleteById(id);
    }
}
