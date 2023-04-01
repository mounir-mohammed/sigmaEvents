package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.repository.PrintingModelRepository;
import ma.sig.events.service.PrintingModelService;
import ma.sig.events.service.dto.PrintingModelDTO;
import ma.sig.events.service.mapper.PrintingModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PrintingModel}.
 */
@Service
@Transactional
public class PrintingModelServiceImpl implements PrintingModelService {

    private final Logger log = LoggerFactory.getLogger(PrintingModelServiceImpl.class);

    private final PrintingModelRepository printingModelRepository;

    private final PrintingModelMapper printingModelMapper;

    public PrintingModelServiceImpl(PrintingModelRepository printingModelRepository, PrintingModelMapper printingModelMapper) {
        this.printingModelRepository = printingModelRepository;
        this.printingModelMapper = printingModelMapper;
    }

    @Override
    public PrintingModelDTO save(PrintingModelDTO printingModelDTO) {
        log.debug("Request to save PrintingModel : {}", printingModelDTO);
        PrintingModel printingModel = printingModelMapper.toEntity(printingModelDTO);
        printingModel = printingModelRepository.save(printingModel);
        return printingModelMapper.toDto(printingModel);
    }

    @Override
    public PrintingModelDTO update(PrintingModelDTO printingModelDTO) {
        log.debug("Request to update PrintingModel : {}", printingModelDTO);
        PrintingModel printingModel = printingModelMapper.toEntity(printingModelDTO);
        printingModel = printingModelRepository.save(printingModel);
        return printingModelMapper.toDto(printingModel);
    }

    @Override
    public Optional<PrintingModelDTO> partialUpdate(PrintingModelDTO printingModelDTO) {
        log.debug("Request to partially update PrintingModel : {}", printingModelDTO);

        return printingModelRepository
            .findById(printingModelDTO.getPrintingModelId())
            .map(existingPrintingModel -> {
                printingModelMapper.partialUpdate(existingPrintingModel, printingModelDTO);

                return existingPrintingModel;
            })
            .map(printingModelRepository::save)
            .map(printingModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrintingModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrintingModels");
        return printingModelRepository.findAll(pageable).map(printingModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrintingModelDTO> findOne(Long id) {
        log.debug("Request to get PrintingModel : {}", id);
        return printingModelRepository.findById(id).map(printingModelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrintingModel : {}", id);
        printingModelRepository.deleteById(id);
    }
}
