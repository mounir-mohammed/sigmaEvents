package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.PrintingType;
import ma.sig.events.repository.PrintingTypeRepository;
import ma.sig.events.service.PrintingTypeService;
import ma.sig.events.service.dto.PrintingTypeDTO;
import ma.sig.events.service.mapper.PrintingTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PrintingType}.
 */
@Service
@Transactional
public class PrintingTypeServiceImpl implements PrintingTypeService {

    private final Logger log = LoggerFactory.getLogger(PrintingTypeServiceImpl.class);

    private final PrintingTypeRepository printingTypeRepository;

    private final PrintingTypeMapper printingTypeMapper;

    public PrintingTypeServiceImpl(PrintingTypeRepository printingTypeRepository, PrintingTypeMapper printingTypeMapper) {
        this.printingTypeRepository = printingTypeRepository;
        this.printingTypeMapper = printingTypeMapper;
    }

    @Override
    public PrintingTypeDTO save(PrintingTypeDTO printingTypeDTO) {
        log.debug("Request to save PrintingType : {}", printingTypeDTO);
        PrintingType printingType = printingTypeMapper.toEntity(printingTypeDTO);
        printingType = printingTypeRepository.save(printingType);
        return printingTypeMapper.toDto(printingType);
    }

    @Override
    public PrintingTypeDTO update(PrintingTypeDTO printingTypeDTO) {
        log.debug("Request to update PrintingType : {}", printingTypeDTO);
        PrintingType printingType = printingTypeMapper.toEntity(printingTypeDTO);
        printingType = printingTypeRepository.save(printingType);
        return printingTypeMapper.toDto(printingType);
    }

    @Override
    public Optional<PrintingTypeDTO> partialUpdate(PrintingTypeDTO printingTypeDTO) {
        log.debug("Request to partially update PrintingType : {}", printingTypeDTO);

        return printingTypeRepository
            .findById(printingTypeDTO.getPrintingTypeId())
            .map(existingPrintingType -> {
                printingTypeMapper.partialUpdate(existingPrintingType, printingTypeDTO);

                return existingPrintingType;
            })
            .map(printingTypeRepository::save)
            .map(printingTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrintingTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrintingTypes");
        return printingTypeRepository.findAll(pageable).map(printingTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrintingTypeDTO> findOne(Long id) {
        log.debug("Request to get PrintingType : {}", id);
        return printingTypeRepository.findById(id).map(printingTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrintingType : {}", id);
        printingTypeRepository.deleteById(id);
    }
}
