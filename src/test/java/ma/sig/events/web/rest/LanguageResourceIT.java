package ma.sig.events.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ma.sig.events.IntegrationTest;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Language;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.Setting;
import ma.sig.events.repository.LanguageRepository;
import ma.sig.events.service.criteria.LanguageCriteria;
import ma.sig.events.service.dto.LanguageDTO;
import ma.sig.events.service.mapper.LanguageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LanguageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LanguageResourceIT {

    private static final String DEFAULT_LANGUAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LANGUAGE_STAT = false;
    private static final Boolean UPDATED_LANGUAGE_STAT = true;

    private static final String ENTITY_API_URL = "/api/languages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{languageId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LanguageMapper languageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLanguageMockMvc;

    private Language language;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Language createEntity(EntityManager em) {
        Language language = new Language()
            .languageCode(DEFAULT_LANGUAGE_CODE)
            .languageName(DEFAULT_LANGUAGE_NAME)
            .languageDescription(DEFAULT_LANGUAGE_DESCRIPTION)
            .languageParams(DEFAULT_LANGUAGE_PARAMS)
            .languageAttributs(DEFAULT_LANGUAGE_ATTRIBUTS)
            .languageStat(DEFAULT_LANGUAGE_STAT);
        return language;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Language createUpdatedEntity(EntityManager em) {
        Language language = new Language()
            .languageCode(UPDATED_LANGUAGE_CODE)
            .languageName(UPDATED_LANGUAGE_NAME)
            .languageDescription(UPDATED_LANGUAGE_DESCRIPTION)
            .languageParams(UPDATED_LANGUAGE_PARAMS)
            .languageAttributs(UPDATED_LANGUAGE_ATTRIBUTS)
            .languageStat(UPDATED_LANGUAGE_STAT);
        return language;
    }

    @BeforeEach
    public void initTest() {
        language = createEntity(em);
    }

    @Test
    @Transactional
    void createLanguage() throws Exception {
        int databaseSizeBeforeCreate = languageRepository.findAll().size();
        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);
        restLanguageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isCreated());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate + 1);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getLanguageCode()).isEqualTo(DEFAULT_LANGUAGE_CODE);
        assertThat(testLanguage.getLanguageName()).isEqualTo(DEFAULT_LANGUAGE_NAME);
        assertThat(testLanguage.getLanguageDescription()).isEqualTo(DEFAULT_LANGUAGE_DESCRIPTION);
        assertThat(testLanguage.getLanguageParams()).isEqualTo(DEFAULT_LANGUAGE_PARAMS);
        assertThat(testLanguage.getLanguageAttributs()).isEqualTo(DEFAULT_LANGUAGE_ATTRIBUTS);
        assertThat(testLanguage.getLanguageStat()).isEqualTo(DEFAULT_LANGUAGE_STAT);
    }

    @Test
    @Transactional
    void createLanguageWithExistingId() throws Exception {
        // Create the Language with an existing ID
        language.setLanguageId(1L);
        LanguageDTO languageDTO = languageMapper.toDto(language);

        int databaseSizeBeforeCreate = languageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLanguageCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageRepository.findAll().size();
        // set the field null
        language.setLanguageCode(null);

        // Create the Language, which fails.
        LanguageDTO languageDTO = languageMapper.toDto(language);

        restLanguageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageRepository.findAll().size();
        // set the field null
        language.setLanguageName(null);

        // Create the Language, which fails.
        LanguageDTO languageDTO = languageMapper.toDto(language);

        restLanguageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLanguages() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList
        restLanguageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=languageId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].languageId").value(hasItem(language.getLanguageId().intValue())))
            .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE)))
            .andExpect(jsonPath("$.[*].languageName").value(hasItem(DEFAULT_LANGUAGE_NAME)))
            .andExpect(jsonPath("$.[*].languageDescription").value(hasItem(DEFAULT_LANGUAGE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].languageParams").value(hasItem(DEFAULT_LANGUAGE_PARAMS)))
            .andExpect(jsonPath("$.[*].languageAttributs").value(hasItem(DEFAULT_LANGUAGE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].languageStat").value(hasItem(DEFAULT_LANGUAGE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get the language
        restLanguageMockMvc
            .perform(get(ENTITY_API_URL_ID, language.getLanguageId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.languageId").value(language.getLanguageId().intValue()))
            .andExpect(jsonPath("$.languageCode").value(DEFAULT_LANGUAGE_CODE))
            .andExpect(jsonPath("$.languageName").value(DEFAULT_LANGUAGE_NAME))
            .andExpect(jsonPath("$.languageDescription").value(DEFAULT_LANGUAGE_DESCRIPTION))
            .andExpect(jsonPath("$.languageParams").value(DEFAULT_LANGUAGE_PARAMS))
            .andExpect(jsonPath("$.languageAttributs").value(DEFAULT_LANGUAGE_ATTRIBUTS))
            .andExpect(jsonPath("$.languageStat").value(DEFAULT_LANGUAGE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getLanguagesByIdFiltering() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        Long id = language.getLanguageId();

        defaultLanguageShouldBeFound("languageId.equals=" + id);
        defaultLanguageShouldNotBeFound("languageId.notEquals=" + id);

        defaultLanguageShouldBeFound("languageId.greaterThanOrEqual=" + id);
        defaultLanguageShouldNotBeFound("languageId.greaterThan=" + id);

        defaultLanguageShouldBeFound("languageId.lessThanOrEqual=" + id);
        defaultLanguageShouldNotBeFound("languageId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageCode equals to DEFAULT_LANGUAGE_CODE
        defaultLanguageShouldBeFound("languageCode.equals=" + DEFAULT_LANGUAGE_CODE);

        // Get all the languageList where languageCode equals to UPDATED_LANGUAGE_CODE
        defaultLanguageShouldNotBeFound("languageCode.equals=" + UPDATED_LANGUAGE_CODE);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageCodeIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageCode in DEFAULT_LANGUAGE_CODE or UPDATED_LANGUAGE_CODE
        defaultLanguageShouldBeFound("languageCode.in=" + DEFAULT_LANGUAGE_CODE + "," + UPDATED_LANGUAGE_CODE);

        // Get all the languageList where languageCode equals to UPDATED_LANGUAGE_CODE
        defaultLanguageShouldNotBeFound("languageCode.in=" + UPDATED_LANGUAGE_CODE);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageCode is not null
        defaultLanguageShouldBeFound("languageCode.specified=true");

        // Get all the languageList where languageCode is null
        defaultLanguageShouldNotBeFound("languageCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageCodeContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageCode contains DEFAULT_LANGUAGE_CODE
        defaultLanguageShouldBeFound("languageCode.contains=" + DEFAULT_LANGUAGE_CODE);

        // Get all the languageList where languageCode contains UPDATED_LANGUAGE_CODE
        defaultLanguageShouldNotBeFound("languageCode.contains=" + UPDATED_LANGUAGE_CODE);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageCodeNotContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageCode does not contain DEFAULT_LANGUAGE_CODE
        defaultLanguageShouldNotBeFound("languageCode.doesNotContain=" + DEFAULT_LANGUAGE_CODE);

        // Get all the languageList where languageCode does not contain UPDATED_LANGUAGE_CODE
        defaultLanguageShouldBeFound("languageCode.doesNotContain=" + UPDATED_LANGUAGE_CODE);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageName equals to DEFAULT_LANGUAGE_NAME
        defaultLanguageShouldBeFound("languageName.equals=" + DEFAULT_LANGUAGE_NAME);

        // Get all the languageList where languageName equals to UPDATED_LANGUAGE_NAME
        defaultLanguageShouldNotBeFound("languageName.equals=" + UPDATED_LANGUAGE_NAME);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageNameIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageName in DEFAULT_LANGUAGE_NAME or UPDATED_LANGUAGE_NAME
        defaultLanguageShouldBeFound("languageName.in=" + DEFAULT_LANGUAGE_NAME + "," + UPDATED_LANGUAGE_NAME);

        // Get all the languageList where languageName equals to UPDATED_LANGUAGE_NAME
        defaultLanguageShouldNotBeFound("languageName.in=" + UPDATED_LANGUAGE_NAME);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageName is not null
        defaultLanguageShouldBeFound("languageName.specified=true");

        // Get all the languageList where languageName is null
        defaultLanguageShouldNotBeFound("languageName.specified=false");
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageNameContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageName contains DEFAULT_LANGUAGE_NAME
        defaultLanguageShouldBeFound("languageName.contains=" + DEFAULT_LANGUAGE_NAME);

        // Get all the languageList where languageName contains UPDATED_LANGUAGE_NAME
        defaultLanguageShouldNotBeFound("languageName.contains=" + UPDATED_LANGUAGE_NAME);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageNameNotContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageName does not contain DEFAULT_LANGUAGE_NAME
        defaultLanguageShouldNotBeFound("languageName.doesNotContain=" + DEFAULT_LANGUAGE_NAME);

        // Get all the languageList where languageName does not contain UPDATED_LANGUAGE_NAME
        defaultLanguageShouldBeFound("languageName.doesNotContain=" + UPDATED_LANGUAGE_NAME);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageDescription equals to DEFAULT_LANGUAGE_DESCRIPTION
        defaultLanguageShouldBeFound("languageDescription.equals=" + DEFAULT_LANGUAGE_DESCRIPTION);

        // Get all the languageList where languageDescription equals to UPDATED_LANGUAGE_DESCRIPTION
        defaultLanguageShouldNotBeFound("languageDescription.equals=" + UPDATED_LANGUAGE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageDescription in DEFAULT_LANGUAGE_DESCRIPTION or UPDATED_LANGUAGE_DESCRIPTION
        defaultLanguageShouldBeFound("languageDescription.in=" + DEFAULT_LANGUAGE_DESCRIPTION + "," + UPDATED_LANGUAGE_DESCRIPTION);

        // Get all the languageList where languageDescription equals to UPDATED_LANGUAGE_DESCRIPTION
        defaultLanguageShouldNotBeFound("languageDescription.in=" + UPDATED_LANGUAGE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageDescription is not null
        defaultLanguageShouldBeFound("languageDescription.specified=true");

        // Get all the languageList where languageDescription is null
        defaultLanguageShouldNotBeFound("languageDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageDescriptionContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageDescription contains DEFAULT_LANGUAGE_DESCRIPTION
        defaultLanguageShouldBeFound("languageDescription.contains=" + DEFAULT_LANGUAGE_DESCRIPTION);

        // Get all the languageList where languageDescription contains UPDATED_LANGUAGE_DESCRIPTION
        defaultLanguageShouldNotBeFound("languageDescription.contains=" + UPDATED_LANGUAGE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageDescription does not contain DEFAULT_LANGUAGE_DESCRIPTION
        defaultLanguageShouldNotBeFound("languageDescription.doesNotContain=" + DEFAULT_LANGUAGE_DESCRIPTION);

        // Get all the languageList where languageDescription does not contain UPDATED_LANGUAGE_DESCRIPTION
        defaultLanguageShouldBeFound("languageDescription.doesNotContain=" + UPDATED_LANGUAGE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageParams equals to DEFAULT_LANGUAGE_PARAMS
        defaultLanguageShouldBeFound("languageParams.equals=" + DEFAULT_LANGUAGE_PARAMS);

        // Get all the languageList where languageParams equals to UPDATED_LANGUAGE_PARAMS
        defaultLanguageShouldNotBeFound("languageParams.equals=" + UPDATED_LANGUAGE_PARAMS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageParamsIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageParams in DEFAULT_LANGUAGE_PARAMS or UPDATED_LANGUAGE_PARAMS
        defaultLanguageShouldBeFound("languageParams.in=" + DEFAULT_LANGUAGE_PARAMS + "," + UPDATED_LANGUAGE_PARAMS);

        // Get all the languageList where languageParams equals to UPDATED_LANGUAGE_PARAMS
        defaultLanguageShouldNotBeFound("languageParams.in=" + UPDATED_LANGUAGE_PARAMS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageParams is not null
        defaultLanguageShouldBeFound("languageParams.specified=true");

        // Get all the languageList where languageParams is null
        defaultLanguageShouldNotBeFound("languageParams.specified=false");
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageParamsContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageParams contains DEFAULT_LANGUAGE_PARAMS
        defaultLanguageShouldBeFound("languageParams.contains=" + DEFAULT_LANGUAGE_PARAMS);

        // Get all the languageList where languageParams contains UPDATED_LANGUAGE_PARAMS
        defaultLanguageShouldNotBeFound("languageParams.contains=" + UPDATED_LANGUAGE_PARAMS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageParamsNotContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageParams does not contain DEFAULT_LANGUAGE_PARAMS
        defaultLanguageShouldNotBeFound("languageParams.doesNotContain=" + DEFAULT_LANGUAGE_PARAMS);

        // Get all the languageList where languageParams does not contain UPDATED_LANGUAGE_PARAMS
        defaultLanguageShouldBeFound("languageParams.doesNotContain=" + UPDATED_LANGUAGE_PARAMS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageAttributs equals to DEFAULT_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldBeFound("languageAttributs.equals=" + DEFAULT_LANGUAGE_ATTRIBUTS);

        // Get all the languageList where languageAttributs equals to UPDATED_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldNotBeFound("languageAttributs.equals=" + UPDATED_LANGUAGE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageAttributs in DEFAULT_LANGUAGE_ATTRIBUTS or UPDATED_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldBeFound("languageAttributs.in=" + DEFAULT_LANGUAGE_ATTRIBUTS + "," + UPDATED_LANGUAGE_ATTRIBUTS);

        // Get all the languageList where languageAttributs equals to UPDATED_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldNotBeFound("languageAttributs.in=" + UPDATED_LANGUAGE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageAttributs is not null
        defaultLanguageShouldBeFound("languageAttributs.specified=true");

        // Get all the languageList where languageAttributs is null
        defaultLanguageShouldNotBeFound("languageAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageAttributsContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageAttributs contains DEFAULT_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldBeFound("languageAttributs.contains=" + DEFAULT_LANGUAGE_ATTRIBUTS);

        // Get all the languageList where languageAttributs contains UPDATED_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldNotBeFound("languageAttributs.contains=" + UPDATED_LANGUAGE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageAttributs does not contain DEFAULT_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldNotBeFound("languageAttributs.doesNotContain=" + DEFAULT_LANGUAGE_ATTRIBUTS);

        // Get all the languageList where languageAttributs does not contain UPDATED_LANGUAGE_ATTRIBUTS
        defaultLanguageShouldBeFound("languageAttributs.doesNotContain=" + UPDATED_LANGUAGE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageStatIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageStat equals to DEFAULT_LANGUAGE_STAT
        defaultLanguageShouldBeFound("languageStat.equals=" + DEFAULT_LANGUAGE_STAT);

        // Get all the languageList where languageStat equals to UPDATED_LANGUAGE_STAT
        defaultLanguageShouldNotBeFound("languageStat.equals=" + UPDATED_LANGUAGE_STAT);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageStatIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageStat in DEFAULT_LANGUAGE_STAT or UPDATED_LANGUAGE_STAT
        defaultLanguageShouldBeFound("languageStat.in=" + DEFAULT_LANGUAGE_STAT + "," + UPDATED_LANGUAGE_STAT);

        // Get all the languageList where languageStat equals to UPDATED_LANGUAGE_STAT
        defaultLanguageShouldNotBeFound("languageStat.in=" + UPDATED_LANGUAGE_STAT);
    }

    @Test
    @Transactional
    void getAllLanguagesByLanguageStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where languageStat is not null
        defaultLanguageShouldBeFound("languageStat.specified=true");

        // Get all the languageList where languageStat is null
        defaultLanguageShouldNotBeFound("languageStat.specified=false");
    }

    @Test
    @Transactional
    void getAllLanguagesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            languageRepository.saveAndFlush(language);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        language.addEvent(event);
        languageRepository.saveAndFlush(language);
        Long eventId = event.getEventId();

        // Get all the languageList where event equals to eventId
        defaultLanguageShouldBeFound("eventId.equals=" + eventId);

        // Get all the languageList where event equals to (eventId + 1)
        defaultLanguageShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllLanguagesBySettingIsEqualToSomething() throws Exception {
        Setting setting;
        if (TestUtil.findAll(em, Setting.class).isEmpty()) {
            languageRepository.saveAndFlush(language);
            setting = SettingResourceIT.createEntity(em);
        } else {
            setting = TestUtil.findAll(em, Setting.class).get(0);
        }
        em.persist(setting);
        em.flush();
        language.addSetting(setting);
        languageRepository.saveAndFlush(language);
        Long settingId = setting.getSettingId();

        // Get all the languageList where setting equals to settingId
        defaultLanguageShouldBeFound("settingId.equals=" + settingId);

        // Get all the languageList where setting equals to (settingId + 1)
        defaultLanguageShouldNotBeFound("settingId.equals=" + (settingId + 1));
    }

    @Test
    @Transactional
    void getAllLanguagesByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            languageRepository.saveAndFlush(language);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        language.addPrintingCentre(printingCentre);
        languageRepository.saveAndFlush(language);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the languageList where printingCentre equals to printingCentreId
        defaultLanguageShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the languageList where printingCentre equals to (printingCentreId + 1)
        defaultLanguageShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLanguageShouldBeFound(String filter) throws Exception {
        restLanguageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=languageId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].languageId").value(hasItem(language.getLanguageId().intValue())))
            .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE)))
            .andExpect(jsonPath("$.[*].languageName").value(hasItem(DEFAULT_LANGUAGE_NAME)))
            .andExpect(jsonPath("$.[*].languageDescription").value(hasItem(DEFAULT_LANGUAGE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].languageParams").value(hasItem(DEFAULT_LANGUAGE_PARAMS)))
            .andExpect(jsonPath("$.[*].languageAttributs").value(hasItem(DEFAULT_LANGUAGE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].languageStat").value(hasItem(DEFAULT_LANGUAGE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restLanguageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=languageId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLanguageShouldNotBeFound(String filter) throws Exception {
        restLanguageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=languageId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLanguageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=languageId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLanguage() throws Exception {
        // Get the language
        restLanguageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Update the language
        Language updatedLanguage = languageRepository.findById(language.getLanguageId()).get();
        // Disconnect from session so that the updates on updatedLanguage are not directly saved in db
        em.detach(updatedLanguage);
        updatedLanguage
            .languageCode(UPDATED_LANGUAGE_CODE)
            .languageName(UPDATED_LANGUAGE_NAME)
            .languageDescription(UPDATED_LANGUAGE_DESCRIPTION)
            .languageParams(UPDATED_LANGUAGE_PARAMS)
            .languageAttributs(UPDATED_LANGUAGE_ATTRIBUTS)
            .languageStat(UPDATED_LANGUAGE_STAT);
        LanguageDTO languageDTO = languageMapper.toDto(updatedLanguage);

        restLanguageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, languageDTO.getLanguageId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(languageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testLanguage.getLanguageName()).isEqualTo(UPDATED_LANGUAGE_NAME);
        assertThat(testLanguage.getLanguageDescription()).isEqualTo(UPDATED_LANGUAGE_DESCRIPTION);
        assertThat(testLanguage.getLanguageParams()).isEqualTo(UPDATED_LANGUAGE_PARAMS);
        assertThat(testLanguage.getLanguageAttributs()).isEqualTo(UPDATED_LANGUAGE_ATTRIBUTS);
        assertThat(testLanguage.getLanguageStat()).isEqualTo(UPDATED_LANGUAGE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
        language.setLanguageId(count.incrementAndGet());

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, languageDTO.getLanguageId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(languageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
        language.setLanguageId(count.incrementAndGet());

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(languageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
        language.setLanguageId(count.incrementAndGet());

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLanguageWithPatch() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Update the language using partial update
        Language partialUpdatedLanguage = new Language();
        partialUpdatedLanguage.setLanguageId(language.getLanguageId());

        partialUpdatedLanguage
            .languageCode(UPDATED_LANGUAGE_CODE)
            .languageDescription(UPDATED_LANGUAGE_DESCRIPTION)
            .languageParams(UPDATED_LANGUAGE_PARAMS)
            .languageAttributs(UPDATED_LANGUAGE_ATTRIBUTS)
            .languageStat(UPDATED_LANGUAGE_STAT);

        restLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLanguage.getLanguageId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLanguage))
            )
            .andExpect(status().isOk());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testLanguage.getLanguageName()).isEqualTo(DEFAULT_LANGUAGE_NAME);
        assertThat(testLanguage.getLanguageDescription()).isEqualTo(UPDATED_LANGUAGE_DESCRIPTION);
        assertThat(testLanguage.getLanguageParams()).isEqualTo(UPDATED_LANGUAGE_PARAMS);
        assertThat(testLanguage.getLanguageAttributs()).isEqualTo(UPDATED_LANGUAGE_ATTRIBUTS);
        assertThat(testLanguage.getLanguageStat()).isEqualTo(UPDATED_LANGUAGE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateLanguageWithPatch() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Update the language using partial update
        Language partialUpdatedLanguage = new Language();
        partialUpdatedLanguage.setLanguageId(language.getLanguageId());

        partialUpdatedLanguage
            .languageCode(UPDATED_LANGUAGE_CODE)
            .languageName(UPDATED_LANGUAGE_NAME)
            .languageDescription(UPDATED_LANGUAGE_DESCRIPTION)
            .languageParams(UPDATED_LANGUAGE_PARAMS)
            .languageAttributs(UPDATED_LANGUAGE_ATTRIBUTS)
            .languageStat(UPDATED_LANGUAGE_STAT);

        restLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLanguage.getLanguageId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLanguage))
            )
            .andExpect(status().isOk());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testLanguage.getLanguageName()).isEqualTo(UPDATED_LANGUAGE_NAME);
        assertThat(testLanguage.getLanguageDescription()).isEqualTo(UPDATED_LANGUAGE_DESCRIPTION);
        assertThat(testLanguage.getLanguageParams()).isEqualTo(UPDATED_LANGUAGE_PARAMS);
        assertThat(testLanguage.getLanguageAttributs()).isEqualTo(UPDATED_LANGUAGE_ATTRIBUTS);
        assertThat(testLanguage.getLanguageStat()).isEqualTo(UPDATED_LANGUAGE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
        language.setLanguageId(count.incrementAndGet());

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, languageDTO.getLanguageId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(languageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
        language.setLanguageId(count.incrementAndGet());

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(languageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
        language.setLanguageId(count.incrementAndGet());

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(languageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeDelete = languageRepository.findAll().size();

        // Delete the language
        restLanguageMockMvc
            .perform(delete(ENTITY_API_URL_ID, language.getLanguageId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
