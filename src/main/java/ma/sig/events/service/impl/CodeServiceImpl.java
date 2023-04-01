package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Code;
import ma.sig.events.repository.CodeRepository;
import ma.sig.events.service.CodeService;
import ma.sig.events.service.dto.CodeDTO;
import ma.sig.events.service.mapper.CodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Code}.
 */
@Service
@Transactional
public class CodeServiceImpl implements CodeService {

    private final Logger log = LoggerFactory.getLogger(CodeServiceImpl.class);

    private final CodeRepository codeRepository;

    private final CodeMapper codeMapper;

    public CodeServiceImpl(CodeRepository codeRepository, CodeMapper codeMapper) {
        this.codeRepository = codeRepository;
        this.codeMapper = codeMapper;
    }

    @Override
    public CodeDTO save(CodeDTO codeDTO) {
        log.debug("Request to save Code : {}", codeDTO);
        Code code = codeMapper.toEntity(codeDTO);
        code = codeRepository.save(code);
        return codeMapper.toDto(code);
    }

    @Override
    public CodeDTO update(CodeDTO codeDTO) {
        log.debug("Request to update Code : {}", codeDTO);
        Code code = codeMapper.toEntity(codeDTO);
        code = codeRepository.save(code);
        return codeMapper.toDto(code);
    }

    @Override
    public Optional<CodeDTO> partialUpdate(CodeDTO codeDTO) {
        log.debug("Request to partially update Code : {}", codeDTO);

        return codeRepository
            .findById(codeDTO.getCodeId())
            .map(existingCode -> {
                codeMapper.partialUpdate(existingCode, codeDTO);

                return existingCode;
            })
            .map(codeRepository::save)
            .map(codeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Codes");
        return codeRepository.findAll(pageable).map(codeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CodeDTO> findOne(Long id) {
        log.debug("Request to get Code : {}", id);
        return codeRepository.findById(id).map(codeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Code : {}", id);
        codeRepository.deleteById(id);
    }
}
