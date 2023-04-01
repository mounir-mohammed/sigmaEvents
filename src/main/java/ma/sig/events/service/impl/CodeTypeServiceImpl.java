package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.CodeType;
import ma.sig.events.repository.CodeTypeRepository;
import ma.sig.events.service.CodeTypeService;
import ma.sig.events.service.dto.CodeTypeDTO;
import ma.sig.events.service.mapper.CodeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CodeType}.
 */
@Service
@Transactional
public class CodeTypeServiceImpl implements CodeTypeService {

    private final Logger log = LoggerFactory.getLogger(CodeTypeServiceImpl.class);

    private final CodeTypeRepository codeTypeRepository;

    private final CodeTypeMapper codeTypeMapper;

    public CodeTypeServiceImpl(CodeTypeRepository codeTypeRepository, CodeTypeMapper codeTypeMapper) {
        this.codeTypeRepository = codeTypeRepository;
        this.codeTypeMapper = codeTypeMapper;
    }

    @Override
    public CodeTypeDTO save(CodeTypeDTO codeTypeDTO) {
        log.debug("Request to save CodeType : {}", codeTypeDTO);
        CodeType codeType = codeTypeMapper.toEntity(codeTypeDTO);
        codeType = codeTypeRepository.save(codeType);
        return codeTypeMapper.toDto(codeType);
    }

    @Override
    public CodeTypeDTO update(CodeTypeDTO codeTypeDTO) {
        log.debug("Request to update CodeType : {}", codeTypeDTO);
        CodeType codeType = codeTypeMapper.toEntity(codeTypeDTO);
        codeType = codeTypeRepository.save(codeType);
        return codeTypeMapper.toDto(codeType);
    }

    @Override
    public Optional<CodeTypeDTO> partialUpdate(CodeTypeDTO codeTypeDTO) {
        log.debug("Request to partially update CodeType : {}", codeTypeDTO);

        return codeTypeRepository
            .findById(codeTypeDTO.getCodeTypeId())
            .map(existingCodeType -> {
                codeTypeMapper.partialUpdate(existingCodeType, codeTypeDTO);

                return existingCodeType;
            })
            .map(codeTypeRepository::save)
            .map(codeTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CodeTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CodeTypes");
        return codeTypeRepository.findAll(pageable).map(codeTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CodeTypeDTO> findOne(Long id) {
        log.debug("Request to get CodeType : {}", id);
        return codeTypeRepository.findById(id).map(codeTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CodeType : {}", id);
        codeTypeRepository.deleteById(id);
    }
}
