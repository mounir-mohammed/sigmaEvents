package ma.sig.events.service.impl;

import java.time.ZonedDateTime;
import java.util.*;
import ma.sig.events.domain.*;
import ma.sig.events.repository.*;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.AccreditationQueryService;
import ma.sig.events.service.AccreditationService;
import ma.sig.events.service.UserService;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.dto.MassUpdateAccreditationDTO;
import ma.sig.events.service.dto.SiteDTO;
import ma.sig.events.service.mapper.AccreditationMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Accreditation}.
 */
@Service
@Transactional
public class AccreditationServiceImpl implements AccreditationService {

    private final Logger log = LoggerFactory.getLogger(AccreditationServiceImpl.class);

    private final AccreditationRepository accreditationRepository;

    private final StatusRepository statusRepository;

    private final AccreditationMapper accreditationMapper;

    private final UserService userService;

    private final CategoryRepository categoryRepository;

    private final FonctionRepository fonctionRepository;

    private final SiteRepository siteRepository;

    private final AccreditationTypeRepository accreditationTypeRepository;

    private final CivilityRepository civilityRepository;

    private final SexeRepository sexeRepository;

    private final NationalityRepository nationalityRepository;

    private final OrganizRepository organizRepository;

    private final EventRepository eventRepository;

    private final CountryRepository countryRepository;

    private final DayPassInfoRepository dayPassInfoRepository;

    private final AttachementRepository attachementRepository;

    private final CodeRepository codeRepository;

    private final CityRepository cityRepository;

    private final AccreditationQueryService accreditationQueryService;

    public AccreditationServiceImpl(
        AccreditationRepository accreditationRepository,
        AccreditationMapper accreditationMapper,
        StatusRepository statusRepository,
        UserService userService,
        CategoryRepository categoryRepository,
        FonctionRepository fonctionRepository,
        SiteRepository siteRepository,
        AccreditationTypeRepository accreditationTypeRepository,
        CivilityRepository civilityRepository,
        SexeRepository sexeRepository,
        NationalityRepository nationalityRepository,
        OrganizRepository organizRepository,
        EventRepository eventRepository,
        CountryRepository countryRepository,
        DayPassInfoRepository dayPassInfoRepository,
        AttachementRepository attachementRepository,
        CodeRepository codeRepository,
        CityRepository cityRepository,
        AccreditationQueryService accreditationQueryService
    ) {
        this.accreditationRepository = accreditationRepository;
        this.accreditationMapper = accreditationMapper;
        this.statusRepository = statusRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.fonctionRepository = fonctionRepository;
        this.siteRepository = siteRepository;
        this.accreditationTypeRepository = accreditationTypeRepository;
        this.civilityRepository = civilityRepository;
        this.sexeRepository = sexeRepository;
        this.nationalityRepository = nationalityRepository;
        this.organizRepository = organizRepository;
        this.eventRepository = eventRepository;
        this.countryRepository = countryRepository;
        this.dayPassInfoRepository = dayPassInfoRepository;
        this.attachementRepository = attachementRepository;
        this.codeRepository = codeRepository;
        this.cityRepository = cityRepository;
        this.accreditationQueryService = accreditationQueryService;
    }

    @Override
    public AccreditationDTO save(AccreditationDTO accreditationDTO) {
        log.debug("Request to save Accreditation : {}", accreditationDTO);
        Accreditation accreditation = accreditationMapper.toEntity(accreditationDTO);
        accreditation = accreditationRepository.save(accreditation);
        return accreditationMapper.toDto(accreditation);
    }

    @Override
    public AccreditationDTO update(AccreditationDTO accreditationDTO) {
        log.debug("Request to update Accreditation : {}", accreditationDTO);
        Accreditation accreditation = accreditationMapper.toEntity(accreditationDTO);
        accreditation = accreditationRepository.save(accreditation);
        return accreditationMapper.toDto(accreditation);
    }

    @Override
    public Optional<AccreditationDTO> partialUpdate(AccreditationDTO accreditationDTO) {
        log.debug("Request to partially update Accreditation : {}", accreditationDTO);

        return accreditationRepository
            .findById(accreditationDTO.getAccreditationId())
            .map(existingAccreditation -> {
                accreditationMapper.partialUpdate(existingAccreditation, accreditationDTO);

                return existingAccreditation;
            })
            .map(accreditationRepository::save)
            .map(accreditationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccreditationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accreditations");
        return accreditationRepository.findAll(pageable).map(accreditationMapper::toDto);
    }

    public Page<AccreditationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return accreditationRepository.findAllWithEagerRelationships(pageable).map(accreditationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccreditationDTO> findOne(Long id) {
        log.debug("Request to get Accreditation : {}", id);
        return accreditationRepository.findOneWithEagerRelationships(id).map(accreditationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accreditation : {}", id);
        accreditationRepository.deleteById(id);
    }

    @Override
    public Optional<AccreditationDTO> validateAccreditation(Long accreditationId, Long statusId) {
        Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get().toString());
        return accreditationRepository
            .findById(accreditationId)
            .map(accreditation -> {
                accreditation.setStatus(statusRepository.findById(statusId).get());
                accreditation.setAccreditationUpdateDate(ZonedDateTime.now());
                accreditation.setAccreditationUpdatedByuser(currentUser.get().getLogin());
                accreditationRepository.save(accreditation);
                return Optional.of(accreditationMapper.toDto(accreditation));
            })
            .orElse(Optional.empty());
    }

    @Override
    public Optional<AccreditationDTO> printAccreditation(Long accreditationId, Long statusId) {
        Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get().toString());
        return accreditationRepository
            .findById(accreditationId)
            .map(accreditation -> {
                accreditation.setStatus(statusRepository.findById(statusId).get());
                accreditation.setAccreditationPrintDate(ZonedDateTime.now());
                accreditation.setAccreditationPrintStat(true);
                accreditation.setAccreditationPrintedByuser(currentUser.get().getLogin());
                Long printNumber = 1L;
                if (accreditation.getAccreditationPrintNumber() != null) {
                    printNumber = accreditation.getAccreditationPrintNumber() + 1;
                }
                accreditation.setAccreditationPrintNumber(printNumber);
                accreditationRepository.save(accreditation);
                return Optional.of(accreditationMapper.toDto(accreditation));
            })
            .orElse(Optional.empty());
    }

    @Override
    public Optional<Boolean> massPrintAccreditation(Long[] accreditationId, Long statusId) {
        Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get().toString());
        Optional<Status> status = statusRepository.findById(statusId);

        for (Long id : accreditationId) {
            Optional<Accreditation> accreditationOptional = accreditationRepository.findById(id);
            if (accreditationOptional.isPresent()) {
                Accreditation accreditation = accreditationOptional.get();
                accreditation.setStatus(status.get());
                accreditation.setAccreditationPrintDate(ZonedDateTime.now());
                accreditation.setAccreditationPrintStat(true);
                accreditation.setAccreditationPrintedByuser(currentUser.get().getLogin());
                Long printNumber = 1L;
                if (accreditation.getAccreditationPrintNumber() != null) {
                    printNumber = accreditation.getAccreditationPrintNumber() + 1;
                }
                accreditation.setAccreditationPrintNumber(printNumber);
                accreditationRepository.save(accreditation);
            } else {
                return Optional.of(false);
            }
        }

        return Optional.of(true);
    }

    @Override
    @Transactional
    public Optional<Boolean> massUpdate(MassUpdateAccreditationDTO massUpdateAccreditationDTO) {
        Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get().toString());

        Category category;
        Fonction fonction;
        AccreditationType accreditationType;
        Organiz organiz;
        Civility civility;
        Sexe sexe;
        Nationality nationality;
        Event event;
        Status status;
        Country country;
        City city;
        Attachement attachement;
        Code code;
        DayPassInfo dayPassInfo;
        Set<Site> sites = new HashSet<>();

        if (massUpdateAccreditationDTO.getCategory() != null) {
            category = categoryRepository.findById(massUpdateAccreditationDTO.getCategory().getCategoryId()).get();
        } else {
            category = null;
        }

        if (massUpdateAccreditationDTO.getFonction() != null) {
            fonction = fonctionRepository.findById(massUpdateAccreditationDTO.getFonction().getFonctionId()).get();
        } else {
            fonction = null;
        }

        if (massUpdateAccreditationDTO.getAccreditationType() != null) {
            accreditationType =
                accreditationTypeRepository.findById(massUpdateAccreditationDTO.getAccreditationType().getAccreditationTypeId()).get();
        } else {
            accreditationType = null;
        }

        if (massUpdateAccreditationDTO.getOrganiz() != null) {
            organiz = organizRepository.findById(massUpdateAccreditationDTO.getOrganiz().getOrganizId()).get();
        } else {
            organiz = null;
        }

        if (massUpdateAccreditationDTO.getCivility() != null) {
            civility = civilityRepository.findById(massUpdateAccreditationDTO.getCivility().getCivilityId()).get();
        } else {
            civility = null;
        }

        if (massUpdateAccreditationDTO.getSexe() != null) {
            sexe = sexeRepository.findById(massUpdateAccreditationDTO.getSexe().getSexeId()).get();
        } else {
            sexe = null;
        }

        if (massUpdateAccreditationDTO.getNationality() != null) {
            nationality = nationalityRepository.findById(massUpdateAccreditationDTO.getNationality().getNationalityId()).get();
        } else {
            nationality = null;
        }

        if (massUpdateAccreditationDTO.getEvent() != null) {
            event = eventRepository.findById(massUpdateAccreditationDTO.getEvent().getEventId()).get();
        } else {
            event = null;
        }

        if (massUpdateAccreditationDTO.getStatus() != null) {
            status = statusRepository.findById(massUpdateAccreditationDTO.getStatus().getStatusId()).get();
        } else {
            status = null;
        }

        if (massUpdateAccreditationDTO.getCountry() != null) {
            country = countryRepository.findById(massUpdateAccreditationDTO.getCountry().getCountryId()).get();
        } else {
            country = null;
        }

        if (massUpdateAccreditationDTO.getCity() != null) {
            city = cityRepository.findById(massUpdateAccreditationDTO.getCity().getCityId()).get();
        } else {
            city = null;
        }

        if (massUpdateAccreditationDTO.getAttachement() != null) {
            attachement = attachementRepository.findById(massUpdateAccreditationDTO.getAttachement().getAttachementId()).get();
        } else {
            attachement = null;
        }

        if (massUpdateAccreditationDTO.getCode() != null) {
            code = codeRepository.findById(massUpdateAccreditationDTO.getCode().getCodeId()).get();
        } else {
            code = null;
        }

        if (massUpdateAccreditationDTO.getDayPassInfo() != null) {
            dayPassInfo = dayPassInfoRepository.findById(massUpdateAccreditationDTO.getDayPassInfo().getDayPassInfoId()).get();
        } else {
            dayPassInfo = null;
        }

        if (massUpdateAccreditationDTO.getAccreditationUpdateSites()) {
            if (massUpdateAccreditationDTO.getSites() != null && massUpdateAccreditationDTO.getSites().size() > 0) {
                for (SiteDTO site : massUpdateAccreditationDTO.getSites()) {
                    sites.add(siteRepository.findById(site.getSiteId()).get());
                }
            }
        }

        try {
            for (Long id : massUpdateAccreditationDTO.getAccreditationIds()) {
                // Map AccreditationDTO to Accreditation
                this.findAccreditationByIdCheckEvent(id)
                    .ifPresent(existingAccreditation -> {
                        try {
                            existingAccreditation.setAccreditationUpdateDate(ZonedDateTime.now());
                            existingAccreditation.setAccreditationUpdatedByuser(currentUser.get().getLogin());

                            if (massUpdateAccreditationDTO.getAccreditationOccupation() != null) {
                                existingAccreditation.setAccreditationOccupation(massUpdateAccreditationDTO.getAccreditationOccupation());
                            }

                            if (massUpdateAccreditationDTO.getAccreditationStat() != null) {
                                existingAccreditation.setAccreditationStat(massUpdateAccreditationDTO.getAccreditationStat());
                            }

                            if (massUpdateAccreditationDTO.getAccreditationActivated() != null) {
                                existingAccreditation.setAccreditationActivated(massUpdateAccreditationDTO.getAccreditationActivated());
                            }

                            if (massUpdateAccreditationDTO.getAccreditationDateStart() != null) {
                                existingAccreditation.setAccreditationDateStart(massUpdateAccreditationDTO.getAccreditationDateStart());
                            }

                            if (massUpdateAccreditationDTO.getAccreditationDateEnd() != null) {
                                existingAccreditation.setAccreditationDateEnd(massUpdateAccreditationDTO.getAccreditationDateEnd());
                            }
                            if (category != null) {
                                existingAccreditation.setCategory(category);
                            }

                            if (fonction != null) {
                                existingAccreditation.setFonction(fonction);
                            }

                            if (accreditationType != null) {
                                existingAccreditation.setAccreditationType(accreditationType);
                            }

                            if (organiz != null) {
                                existingAccreditation.setOrganiz(organiz);
                            }

                            if (civility != null) {
                                existingAccreditation.setCivility(civility);
                            }

                            if (sexe != null) {
                                existingAccreditation.setSexe(sexe);
                            }

                            if (nationality != null) {
                                existingAccreditation.setNationality(nationality);
                            }

                            if (event != null) {
                                existingAccreditation.setEvent(event);
                            }

                            if (status != null) {
                                existingAccreditation.setStatus(status);
                            }

                            if (country != null) {
                                existingAccreditation.setCountry(country);
                            }

                            if (city != null) {
                                existingAccreditation.setCity(city);
                            }

                            if (attachement != null) {
                                existingAccreditation.setAttachement(attachement);
                            }

                            if (code != null) {
                                existingAccreditation.setCode(code);
                            }

                            if (dayPassInfo != null) {
                                existingAccreditation.setDayPassInfo(dayPassInfo);
                            }

                            if (StringUtils.isNotBlank(massUpdateAccreditationDTO.getAccreditationDescription())) {
                                existingAccreditation.setAccreditationDescription(massUpdateAccreditationDTO.getAccreditationDescription());
                            }

                            if (StringUtils.isNotBlank(massUpdateAccreditationDTO.getAccreditationParams())) {
                                existingAccreditation.setAccreditationParams(massUpdateAccreditationDTO.getAccreditationParams());
                            }

                            if (StringUtils.isNotBlank(massUpdateAccreditationDTO.getAccreditationAttributs())) {
                                existingAccreditation.setAccreditationAttributs(massUpdateAccreditationDTO.getAccreditationAttributs());
                            }

                            if (massUpdateAccreditationDTO.getAccreditationPhoto() != null) {
                                existingAccreditation.setAccreditationPhoto(massUpdateAccreditationDTO.getAccreditationPhoto());
                            }

                            if (StringUtils.isNotBlank(massUpdateAccreditationDTO.getAccreditationPhotoContentType())) {
                                existingAccreditation.setAccreditationPhotoContentType(
                                    massUpdateAccreditationDTO.getAccreditationPhotoContentType()
                                );
                            }
                            if (massUpdateAccreditationDTO.getAccreditationUpdateSites()) {
                                existingAccreditation.setSites(sites);
                            }

                            accreditationRepository.save(existingAccreditation);
                        } catch (Exception e) {
                            log.error("Error updating accreditation: " + id, e);
                        }
                    });
            }

            return Optional.of(true);
        } catch (Exception e) {
            log.error("Error updating accreditations", e);
            return Optional.of(false);
        }
    }

    @Override
    public Optional<Accreditation> findAccreditationByIdCheckEvent(Long id) {
        return accreditationQueryService.findAccreditationByIdCheckEvent(id);
    }
}
