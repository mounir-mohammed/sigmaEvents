package ma.sig.events.service.impl;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import ma.sig.events.domain.*;
import ma.sig.events.repository.*;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.CloningService;
import ma.sig.events.service.UserService;
import ma.sig.events.service.dto.CloningDTO;
import ma.sig.events.service.mapper.CloningMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cloning}.
 */
@Service
@Transactional
public class CloningServiceImpl implements CloningService {

    private final Logger log = LoggerFactory.getLogger(CloningServiceImpl.class);

    private final CloningRepository cloningRepository;

    private final CategoryRepository categoryRepository;

    private final FonctionRepository fonctionRepository;

    private final AreaRepository areaRepository;

    private final SiteRepository siteRepository;

    private final AccreditationTypeRepository acreditationTypeRepository;

    private final OrganizRepository organizRepository;

    private final AccreditationRepository accreditationRepository;

    private final EventRepository eventRepository;

    private final CloningMapper cloningMapper;

    private final UserService userService;

    public CloningServiceImpl(
        CloningRepository cloningRepository,
        CategoryRepository categoryRepository,
        FonctionRepository fonctionRepository,
        AreaRepository areaRepository,
        SiteRepository siteRepository,
        AccreditationTypeRepository acreditationTypeRepository,
        OrganizRepository organizRepository,
        AccreditationRepository accreditationRepository,
        EventRepository eventRepository,
        CloningMapper cloningMapper,
        UserService userService
    ) {
        this.cloningRepository = cloningRepository;
        this.categoryRepository = categoryRepository;
        this.fonctionRepository = fonctionRepository;
        this.areaRepository = areaRepository;
        this.siteRepository = siteRepository;
        this.acreditationTypeRepository = acreditationTypeRepository;
        this.organizRepository = organizRepository;
        this.accreditationRepository = accreditationRepository;
        this.eventRepository = eventRepository;
        this.cloningMapper = cloningMapper;
        this.userService = userService;
    }

    @Override
    public CloningDTO save(CloningDTO cloningDTO) {
        log.debug("Request to save Cloning : {}", cloningDTO);
        Cloning cloning = cloningMapper.toEntity(cloningDTO);
        cloning = cloningRepository.save(cloning);
        return cloningMapper.toDto(cloning);
    }

    @Override
    public CloningDTO update(CloningDTO cloningDTO) {
        log.debug("Request to update Cloning : {}", cloningDTO);
        Cloning cloning = cloningMapper.toEntity(cloningDTO);
        cloning = cloningRepository.save(cloning);
        return cloningMapper.toDto(cloning);
    }

    @Override
    public Optional<CloningDTO> partialUpdate(CloningDTO cloningDTO) {
        log.debug("Request to partially update Cloning : {}", cloningDTO);

        return cloningRepository
            .findById(cloningDTO.getCloningId())
            .map(existingCloning -> {
                cloningMapper.partialUpdate(existingCloning, cloningDTO);

                return existingCloning;
            })
            .map(cloningRepository::save)
            .map(cloningMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CloningDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clonings");
        return cloningRepository.findAll(pageable).map(cloningMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CloningDTO> findOne(Long id) {
        log.debug("Request to get Cloning : {}", id);
        return cloningRepository.findById(id).map(cloningMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cloning : {}", id);
        cloningRepository.deleteById(id);
    }

    public CloningDTO clone(CloningDTO cloningDTO, String currentUserLogin) {
        log.debug("Request to clone event {} to : {}", cloningDTO.getCloningOldEventId(), cloningDTO.getCloningNewEventId());
        if (cloningDTO != null && cloningDTO.getCloningOldEventId() != null && cloningDTO.getCloningNewEventId() != null) {
            Event newEvent = eventRepository.getReferenceById(cloningDTO.getCloningNewEventId());
            Event oldEvent = eventRepository.getReferenceById(cloningDTO.getCloningOldEventId());

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            if (newEvent != null && oldEvent != null) {
                try {
                    Map<Long, Long> tempCategorys = new HashMap<>();
                    Map<Long, Long> tempFonctions = new HashMap<>();
                    Map<Long, Long> tempAreas = new HashMap<>();
                    Map<Long, Long> tempSites = new HashMap<>();
                    Map<Long, Long> tempOrganizs = new HashMap<>();
                    Map<Long, Long> tempAccreditationTypes = new HashMap<>();
                    Map<Long, Long> tempAccreditations = new HashMap<>();

                    List<Category> categorys = categoryRepository.findByEventEventId(oldEvent.getEventId());
                    List<Fonction> fonctions = fonctionRepository.findByEventEventId(oldEvent.getEventId());
                    List<Area> areas = areaRepository.findByEventEventId(oldEvent.getEventId());
                    List<Site> sites = siteRepository.findByEventEventId(oldEvent.getEventId());
                    List<Organiz> organizs = organizRepository.findByEventEventId(oldEvent.getEventId());
                    List<AccreditationType> acreditationTypes = acreditationTypeRepository.findByEventEventId(oldEvent.getEventId());
                    List<Accreditation> accreditations = accreditationRepository.findByEventEventId(oldEvent.getEventId());

                    List<Category> newCategorys = new ArrayList<>();
                    List<Fonction> newFonctions = new ArrayList<>();
                    List<Area> newAreas = new ArrayList<>();
                    List<Site> newSites = new ArrayList<>();
                    List<Organiz> newOrganizs = new ArrayList<>();
                    List<AccreditationType> newAcreditationTypes = new ArrayList<>();
                    List<Accreditation> newAccreditations = new ArrayList<>();

                    for (AccreditationType accreditationType : acreditationTypes) {
                        Long tempAccreditationTypeID = accreditationType.getAccreditationTypeId();
                        AccreditationType newAccreditationType = new AccreditationType();
                        newAccreditationType.setAccreditationTypeValue(accreditationType.getAccreditationTypeValue());
                        newAccreditationType.setAccreditationTypeAbreviation(accreditationType.getAccreditationTypeAbreviation());
                        newAccreditationType.setAccreditationTypeDescription(accreditationType.getAccreditationTypeDescription());
                        newAccreditationType.setAccreditationTypeAttributs(accreditationType.getAccreditationTypeAttributs());
                        newAccreditationType.setAccreditationTypeParams(accreditationType.getAccreditationTypeParams());
                        newAccreditationType.setAccreditationTypeStat(accreditationType.getAccreditationTypeStat());
                        newAccreditationType.setEvent(newEvent);
                        AccreditationType savedAccreditationType = acreditationTypeRepository.save(newAccreditationType);
                        newAcreditationTypes.add(savedAccreditationType);
                        tempAccreditationTypes.put(tempAccreditationTypeID, savedAccreditationType.getAccreditationTypeId());
                    }

                    for (Site site : sites) {
                        Long tempSiteID = site.getSiteId();
                        Site newSite = new Site();
                        newSite.setSiteName(site.getSiteName());
                        newSite.setSiteColor(site.getSiteColor());
                        newSite.setSiteAbreviation(site.getSiteAbreviation());
                        newSite.setSiteDescription(site.getSiteDescription());
                        newSite.setSiteLogo(site.getSiteLogo());
                        newSite.setSiteLogoContentType(site.getSiteLogoContentType());
                        newSite.setSiteAdresse(site.getSiteAdresse());
                        newSite.setSiteEmail(site.getSiteEmail());
                        newSite.setSiteTel(site.getSiteTel());
                        newSite.setSiteFax(site.getSiteFax());
                        newSite.setSiteResponsableName(site.getSiteResponsableName());
                        newSite.setSiteParams(site.getSiteParams());
                        newSite.setSiteAttributs(site.getSiteAttributs());
                        newSite.setSiteStat(site.getSiteStat());
                        newSite.setEvent(newEvent);
                        Site savedSite = siteRepository.save(newSite);
                        newSites.add(savedSite);
                        tempSites.put(tempSiteID, savedSite.getSiteId());
                    }

                    for (Area area : areas) {
                        Long tempAreaID = area.getAreaId();
                        Area newArea = new Area();
                        newArea.setAreaName(area.getAreaName());
                        newArea.setAreaAbreviation(area.getAreaAbreviation());
                        newArea.setAreaColor(area.getAreaColor());
                        newArea.setAreaDescription(area.getAreaDescription());
                        newArea.setAreaLogo(area.getAreaLogo());
                        newArea.setAreaLogoContentType(area.getAreaLogoContentType());
                        newArea.setAreaParams(area.getAreaParams());
                        newArea.setAreaAttributs(area.getAreaAttributs());
                        newArea.setAreaStat(area.getAreaStat());
                        newArea.setEvent(newEvent);
                        Area savedArea = areaRepository.save(newArea);
                        newAreas.add(savedArea);
                        tempAreas.put(tempAreaID, savedArea.getAreaId());
                    }

                    for (Organiz organiz : organizs) {
                        Long tempOrganizId = organiz.getOrganizId();
                        Organiz newOrganiz = new Organiz();
                        newOrganiz.setOrganizName(organiz.getOrganizName());
                        newOrganiz.setOrganizName(organiz.getOrganizName());
                        newOrganiz.setOrganizDescription(organiz.getOrganizDescription());
                        newOrganiz.setOrganizLogo(organiz.getOrganizLogo());
                        newOrganiz.setOrganizLogoContentType(organiz.getOrganizLogoContentType());
                        newOrganiz.setOrganizTel(organiz.getOrganizTel());
                        newOrganiz.setOrganizFax(organiz.getOrganizFax());
                        newOrganiz.setOrganizEmail(organiz.getOrganizEmail());
                        newOrganiz.setOrganizAdresse(organiz.getOrganizAdresse());
                        newOrganiz.setOrganizParams(organiz.getOrganizParams());
                        newOrganiz.setOrganizAttributs(organiz.getOrganizAttributs());
                        newOrganiz.setOrganizStat(organiz.getOrganizStat());
                        newOrganiz.setEvent(newEvent);
                        Organiz savedOrganiz = organizRepository.save(newOrganiz);
                        newOrganizs.add(savedOrganiz);
                        tempOrganizs.put(tempOrganizId, savedOrganiz.getOrganizId());
                    }

                    for (Category category : categorys) {
                        Long tempCategoryID = category.getCategoryId();
                        Category newCategory = new Category();
                        newCategory.setCategoryName(category.getCategoryName());
                        newCategory.setCategoryAbreviation(category.getCategoryAbreviation());
                        newCategory.setCategoryColor(category.getCategoryColor());
                        newCategory.setCategoryDescription(category.getCategoryDescription());
                        newCategory.setCategoryLogo(category.getCategoryLogo());
                        newCategory.setCategoryLogoContentType(category.getCategoryLogoContentType());
                        newCategory.setCategoryParams(category.getCategoryParams());
                        newCategory.setCategoryAttributs(category.getCategoryAttributs());
                        newCategory.setCategoryStat(category.getCategoryStat());
                        newCategory.setEvent(newEvent);
                        Category savedCategory = categoryRepository.save(newCategory);
                        newCategorys.add(savedCategory);
                        tempCategorys.put(tempCategoryID, savedCategory.getCategoryId());
                    }

                    for (Fonction fonction : fonctions) {
                        Long tempFonctionID = fonction.getFonctionId();
                        Fonction newFonction = new Fonction();
                        newFonction.setFonctionName(fonction.getFonctionName());
                        newFonction.setFonctionAbreviation(fonction.getFonctionAbreviation());
                        newFonction.setFonctionColor(fonction.getFonctionColor());
                        newFonction.setFonctionDescription(fonction.getFonctionDescription());
                        newFonction.setFonctionLogo(fonction.getFonctionLogo());
                        newFonction.setFonctionLogoContentType(fonction.getFonctionLogoContentType());
                        newFonction.setFonctionParams(fonction.getFonctionParams());
                        newFonction.setFonctionAttributs(fonction.getFonctionAttributs());
                        newFonction.setFonctionStat(fonction.getFonctionStat());
                        newFonction.setEvent(newEvent);
                        Category newCategory = findCategoryByMapping(newCategorys, tempCategorys, fonction.getCategory().getCategoryId());
                        newFonction.setCategory(newCategory);
                        for (Area area : fonction.getAreas()) {
                            Area newArea = findAreaByMapping(newAreas, tempAreas, area.getAreaId());
                            newFonction.getAreas().add(newArea);
                        }
                        Fonction savedFonction = fonctionRepository.save(newFonction);
                        newFonctions.add(savedFonction);
                        tempFonctions.put(tempFonctionID, savedFonction.getFonctionId());
                    }

                    for (Accreditation accreditation : accreditations) {
                        Long tempAccreditationID = accreditation.getAccreditationId();
                        Accreditation newAccreditation = new Accreditation();
                        newAccreditation.setAccreditationFirstName(accreditation.getAccreditationFirstName());
                        newAccreditation.setAccreditationSecondName(accreditation.getAccreditationSecondName());
                        newAccreditation.setAccreditationLastName(accreditation.getAccreditationLastName());
                        newAccreditation.setAccreditationBirthDay(accreditation.getAccreditationBirthDay());
                        newAccreditation.setAccreditationSexe(accreditation.getAccreditationSexe());
                        newAccreditation.setAccreditationOccupation(accreditation.getAccreditationOccupation());
                        newAccreditation.setAccreditationDescription(accreditation.getAccreditationDescription());
                        newAccreditation.setAccreditationEmail(accreditation.getAccreditationEmail());
                        newAccreditation.setAccreditationTel(accreditation.getAccreditationTel());
                        newAccreditation.setAccreditationPhoto(accreditation.getAccreditationPhoto());
                        newAccreditation.setAccreditationPhotoContentType(accreditation.getAccreditationPhotoContentType());
                        newAccreditation.setAccreditationCinId(accreditation.getAccreditationCinId());
                        newAccreditation.setAccreditationPasseportId(accreditation.getAccreditationPasseportId());
                        newAccreditation.setAccreditationCartePresseId(accreditation.getAccreditationCartePresseId());
                        newAccreditation.setAccreditationCarteProfessionnelleId(accreditation.getAccreditationCarteProfessionnelleId());
                        newAccreditation.setAccreditationDateStart(newEvent.getEventdateStart());
                        newAccreditation.setAccreditationDateEnd(newEvent.getEventdateEnd());
                        newAccreditation.setAccreditationParams(accreditation.getAccreditationParams());
                        newAccreditation.setAccreditationAttributs(accreditation.getAccreditationAttributs());
                        newAccreditation.setAccreditationStat(accreditation.getAccreditationStat());
                        newAccreditation.setAccreditationActivated(accreditation.getAccreditationActivated());
                        newAccreditation.setStatus(accreditation.getStatus());
                        newAccreditation.setCivility(accreditation.getCivility());
                        newAccreditation.setSexe(accreditation.getSexe());
                        newAccreditation.setNationality(accreditation.getNationality());
                        newAccreditation.setCountry(accreditation.getCountry());
                        newAccreditation.setCity(accreditation.getCity());
                        newAccreditation.setEvent(newEvent);

                        Category newCategory = findCategoryByMapping(
                            newCategorys,
                            tempCategorys,
                            accreditation.getCategory().getCategoryId()
                        );
                        newAccreditation.setCategory(newCategory);

                        Fonction newFonction = findFonctionByMapping(
                            newFonctions,
                            tempFonctions,
                            accreditation.getFonction().getFonctionId()
                        );
                        newAccreditation.setFonction(newFonction);

                        Organiz newOrganiz = findOrganizByMapping(newOrganizs, tempOrganizs, accreditation.getOrganiz().getOrganizId());
                        newAccreditation.setOrganiz(newOrganiz);

                        AccreditationType newAccreditationType = findAccreditationTypeByMapping(
                            newAcreditationTypes,
                            tempAccreditationTypes,
                            accreditation.getAccreditationType().getAccreditationTypeId()
                        );
                        newAccreditation.setAccreditationType(newAccreditationType);

                        for (Site site : accreditation.getSites()) {
                            Site newSite = findSiteByMapping(newSites, tempSites, site.getSiteId());
                            newAccreditation.getSites().add(newSite);
                        }

                        newAccreditation.setAccreditationCreationDate(ZonedDateTime.now());
                        newAccreditation.setAccreditationCreatedByuser(currentUserLogin);
                        Accreditation savedAccreditation = accreditationRepository.save(newAccreditation);
                        newAccreditations.add(savedAccreditation);
                        tempAccreditations.put(tempAccreditationID, savedAccreditation.getAccreditationId());
                    }

                    newEvent.setEventCloned(true);
                    newEvent.setEventParams(
                        "CLONED FROM EVENT " +
                        oldEvent.getEventId() +
                        " TO : " +
                        newEvent.getEventId() +
                        " AT " +
                        formattedDateTime +
                        " BY " +
                        currentUserLogin
                    );
                    newEvent.setEventAttributs("Category, Fonction, Area, Site, Organiz, Accreditation");
                    eventRepository.save(newEvent);

                    cloningDTO.setClonedEntitys("Category, Fonction, Area, Site, Organiz, Accreditation");
                    cloningDTO.setCloningDescription(
                        "CLONED FROM EVENT " +
                        oldEvent.getEventId() +
                        " TO : " +
                        newEvent.getEventId() +
                        " AT " +
                        formattedDateTime +
                        " BY " +
                        currentUserLogin
                    );
                    Cloning cloning = cloningMapper.toEntity(cloningDTO);
                    cloning = cloningRepository.save(cloning);
                    return cloningMapper.toDto(cloning);
                } catch (Exception e) {
                    log.error("ERROR CLONNING : " + e.getMessage(), e);
                    e.printStackTrace();
                    log.error("Exception : " + ExceptionUtils.getStackTrace(e));
                    return null;
                }
            } else {
                log.error("EVENT NOT FOUND");
                return null;
            }
        } else {
            log.error("OldEventId or NewEventId is NULL");
            return null;
        }
    }

    private Category findCategoryByMapping(List<Category> newList, Map<Long, Long> map, Long oldId) {
        if (map.containsKey(oldId)) {
            Long newId = map.get(oldId);

            for (Category category : newList) {
                if (category.getCategoryId().equals(newId)) {
                    return category;
                }
            }
        }
        return null;
    }

    private Area findAreaByMapping(List<Area> newList, Map<Long, Long> map, Long oldId) {
        if (map.containsKey(oldId)) {
            Long newId = map.get(oldId);

            for (Area area : newList) {
                if (area.getAreaId().equals(newId)) {
                    return area;
                }
            }
        }
        return null;
    }

    private Fonction findFonctionByMapping(List<Fonction> newList, Map<Long, Long> map, Long oldId) {
        if (map.containsKey(oldId)) {
            Long newId = map.get(oldId);

            for (Fonction fonction : newList) {
                if (fonction.getFonctionId().equals(newId)) {
                    return fonction;
                }
            }
        }
        return null;
    }

    private Site findSiteByMapping(List<Site> newList, Map<Long, Long> map, Long oldId) {
        if (map.containsKey(oldId)) {
            Long newId = map.get(oldId);

            for (Site site : newList) {
                if (site.getSiteId().equals(newId)) {
                    return site;
                }
            }
        }
        return null;
    }

    private Organiz findOrganizByMapping(List<Organiz> newList, Map<Long, Long> map, Long oldId) {
        if (map.containsKey(oldId)) {
            Long newId = map.get(oldId);

            for (Organiz organiz : newList) {
                if (organiz.getOrganizId().equals(newId)) {
                    return organiz;
                }
            }
        }
        return null;
    }

    private AccreditationType findAccreditationTypeByMapping(List<AccreditationType> newList, Map<Long, Long> map, Long oldId) {
        if (map.containsKey(oldId)) {
            Long newId = map.get(oldId);

            for (AccreditationType accreditationType : newList) {
                if (accreditationType.getAccreditationTypeId().equals(newId)) {
                    return accreditationType;
                }
            }
        }
        return null;
    }

    @Async
    public void cloneAsync(CloningDTO cloningDTO, String currentUserLogin) {
        // This runs in a separate thread
        this.clone(cloningDTO, currentUserLogin); // call your existing synchronous clone logic
    }
}
