package ma.sig.events.service.impl;

import java.util.Optional;
import ma.sig.events.domain.Organiz;
import ma.sig.events.repository.OrganizRepository;
import ma.sig.events.service.OrganizService;
import ma.sig.events.service.dto.OrganizDTO;
import ma.sig.events.service.mapper.OrganizMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organiz}.
 */
@Service
@Transactional
public class OrganizServiceImpl implements OrganizService {

    private final Logger log = LoggerFactory.getLogger(OrganizServiceImpl.class);

    private final OrganizRepository organizRepository;

    private final OrganizMapper organizMapper;

    public OrganizServiceImpl(OrganizRepository organizRepository, OrganizMapper organizMapper) {
        this.organizRepository = organizRepository;
        this.organizMapper = organizMapper;
    }

    @Override
    public OrganizDTO save(OrganizDTO organizDTO) {
        log.debug("Request to save Organiz : {}", organizDTO);
        Organiz organiz = organizMapper.toEntity(organizDTO);
        organiz = organizRepository.save(organiz);
        return organizMapper.toDto(organiz);
    }

    @Override
    public OrganizDTO update(OrganizDTO organizDTO) {
        log.debug("Request to update Organiz : {}", organizDTO);
        Organiz organiz = organizMapper.toEntity(organizDTO);
        organiz = organizRepository.save(organiz);
        return organizMapper.toDto(organiz);
    }

    @Override
    public Optional<OrganizDTO> partialUpdate(OrganizDTO organizDTO) {
        log.debug("Request to partially update Organiz : {}", organizDTO);

        return organizRepository
            .findById(organizDTO.getOrganizId())
            .map(existingOrganiz -> {
                organizMapper.partialUpdate(existingOrganiz, organizDTO);

                return existingOrganiz;
            })
            .map(organizRepository::save)
            .map(organizMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organizs");
        return organizRepository.findAll(pageable).map(organizMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizDTO> findOne(Long id) {
        log.debug("Request to get Organiz : {}", id);
        return organizRepository.findById(id).map(organizMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organiz : {}", id);
        organizRepository.deleteById(id);
    }
}
