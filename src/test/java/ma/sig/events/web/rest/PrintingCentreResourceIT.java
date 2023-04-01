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
import ma.sig.events.domain.City;
import ma.sig.events.domain.Country;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Language;
import ma.sig.events.domain.Organiz;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.domain.PrintingServer;
import ma.sig.events.domain.PrintingType;
import ma.sig.events.repository.PrintingCentreRepository;
import ma.sig.events.service.criteria.PrintingCentreCriteria;
import ma.sig.events.service.dto.PrintingCentreDTO;
import ma.sig.events.service.mapper.PrintingCentreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PrintingCentreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrintingCentreResourceIT {

    private static final String DEFAULT_PRINTING_CENTRE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_CENTRE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_CENTRE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_CENTRE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PRINTING_CENTRE_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRINTING_CENTRE_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRINTING_CENTRE_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PRINTING_CENTRE_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_CENTRE_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_CENTRE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_CENTRE_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_CENTRE_TEL = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_CENTRE_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_CENTRE_FAX = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_CENTRE_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINTING_CENTRE_STAT = false;
    private static final Boolean UPDATED_PRINTING_CENTRE_STAT = true;

    private static final String ENTITY_API_URL = "/api/printing-centres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{printingCentreId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrintingCentreRepository printingCentreRepository;

    @Autowired
    private PrintingCentreMapper printingCentreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrintingCentreMockMvc;

    private PrintingCentre printingCentre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingCentre createEntity(EntityManager em) {
        PrintingCentre printingCentre = new PrintingCentre()
            .printingCentreDescription(DEFAULT_PRINTING_CENTRE_DESCRIPTION)
            .printingCentreName(DEFAULT_PRINTING_CENTRE_NAME)
            .printingCentreLogo(DEFAULT_PRINTING_CENTRE_LOGO)
            .printingCentreLogoContentType(DEFAULT_PRINTING_CENTRE_LOGO_CONTENT_TYPE)
            .printingCentreAdresse(DEFAULT_PRINTING_CENTRE_ADRESSE)
            .printingCentreEmail(DEFAULT_PRINTING_CENTRE_EMAIL)
            .printingCentreTel(DEFAULT_PRINTING_CENTRE_TEL)
            .printingCentreFax(DEFAULT_PRINTING_CENTRE_FAX)
            .printingCentreResponsableName(DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME)
            .printingParams(DEFAULT_PRINTING_PARAMS)
            .printingAttributs(DEFAULT_PRINTING_ATTRIBUTS)
            .printingCentreStat(DEFAULT_PRINTING_CENTRE_STAT);
        return printingCentre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingCentre createUpdatedEntity(EntityManager em) {
        PrintingCentre printingCentre = new PrintingCentre()
            .printingCentreDescription(UPDATED_PRINTING_CENTRE_DESCRIPTION)
            .printingCentreName(UPDATED_PRINTING_CENTRE_NAME)
            .printingCentreLogo(UPDATED_PRINTING_CENTRE_LOGO)
            .printingCentreLogoContentType(UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE)
            .printingCentreAdresse(UPDATED_PRINTING_CENTRE_ADRESSE)
            .printingCentreEmail(UPDATED_PRINTING_CENTRE_EMAIL)
            .printingCentreTel(UPDATED_PRINTING_CENTRE_TEL)
            .printingCentreFax(UPDATED_PRINTING_CENTRE_FAX)
            .printingCentreResponsableName(UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME)
            .printingParams(UPDATED_PRINTING_PARAMS)
            .printingAttributs(UPDATED_PRINTING_ATTRIBUTS)
            .printingCentreStat(UPDATED_PRINTING_CENTRE_STAT);
        return printingCentre;
    }

    @BeforeEach
    public void initTest() {
        printingCentre = createEntity(em);
    }

    @Test
    @Transactional
    void createPrintingCentre() throws Exception {
        int databaseSizeBeforeCreate = printingCentreRepository.findAll().size();
        // Create the PrintingCentre
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);
        restPrintingCentreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeCreate + 1);
        PrintingCentre testPrintingCentre = printingCentreList.get(printingCentreList.size() - 1);
        assertThat(testPrintingCentre.getPrintingCentreDescription()).isEqualTo(DEFAULT_PRINTING_CENTRE_DESCRIPTION);
        assertThat(testPrintingCentre.getPrintingCentreName()).isEqualTo(DEFAULT_PRINTING_CENTRE_NAME);
        assertThat(testPrintingCentre.getPrintingCentreLogo()).isEqualTo(DEFAULT_PRINTING_CENTRE_LOGO);
        assertThat(testPrintingCentre.getPrintingCentreLogoContentType()).isEqualTo(DEFAULT_PRINTING_CENTRE_LOGO_CONTENT_TYPE);
        assertThat(testPrintingCentre.getPrintingCentreAdresse()).isEqualTo(DEFAULT_PRINTING_CENTRE_ADRESSE);
        assertThat(testPrintingCentre.getPrintingCentreEmail()).isEqualTo(DEFAULT_PRINTING_CENTRE_EMAIL);
        assertThat(testPrintingCentre.getPrintingCentreTel()).isEqualTo(DEFAULT_PRINTING_CENTRE_TEL);
        assertThat(testPrintingCentre.getPrintingCentreFax()).isEqualTo(DEFAULT_PRINTING_CENTRE_FAX);
        assertThat(testPrintingCentre.getPrintingCentreResponsableName()).isEqualTo(DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME);
        assertThat(testPrintingCentre.getPrintingParams()).isEqualTo(DEFAULT_PRINTING_PARAMS);
        assertThat(testPrintingCentre.getPrintingAttributs()).isEqualTo(DEFAULT_PRINTING_ATTRIBUTS);
        assertThat(testPrintingCentre.getPrintingCentreStat()).isEqualTo(DEFAULT_PRINTING_CENTRE_STAT);
    }

    @Test
    @Transactional
    void createPrintingCentreWithExistingId() throws Exception {
        // Create the PrintingCentre with an existing ID
        printingCentre.setPrintingCentreId(1L);
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        int databaseSizeBeforeCreate = printingCentreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrintingCentreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrintingCentreNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = printingCentreRepository.findAll().size();
        // set the field null
        printingCentre.setPrintingCentreName(null);

        // Create the PrintingCentre, which fails.
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        restPrintingCentreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isBadRequest());

        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrintingCentres() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList
        restPrintingCentreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingCentreId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingCentreId").value(hasItem(printingCentre.getPrintingCentreId().intValue())))
            .andExpect(jsonPath("$.[*].printingCentreDescription").value(hasItem(DEFAULT_PRINTING_CENTRE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingCentreName").value(hasItem(DEFAULT_PRINTING_CENTRE_NAME)))
            .andExpect(jsonPath("$.[*].printingCentreLogoContentType").value(hasItem(DEFAULT_PRINTING_CENTRE_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].printingCentreLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRINTING_CENTRE_LOGO))))
            .andExpect(jsonPath("$.[*].printingCentreAdresse").value(hasItem(DEFAULT_PRINTING_CENTRE_ADRESSE)))
            .andExpect(jsonPath("$.[*].printingCentreEmail").value(hasItem(DEFAULT_PRINTING_CENTRE_EMAIL)))
            .andExpect(jsonPath("$.[*].printingCentreTel").value(hasItem(DEFAULT_PRINTING_CENTRE_TEL)))
            .andExpect(jsonPath("$.[*].printingCentreFax").value(hasItem(DEFAULT_PRINTING_CENTRE_FAX)))
            .andExpect(jsonPath("$.[*].printingCentreResponsableName").value(hasItem(DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME)))
            .andExpect(jsonPath("$.[*].printingParams").value(hasItem(DEFAULT_PRINTING_PARAMS)))
            .andExpect(jsonPath("$.[*].printingAttributs").value(hasItem(DEFAULT_PRINTING_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].printingCentreStat").value(hasItem(DEFAULT_PRINTING_CENTRE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getPrintingCentre() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get the printingCentre
        restPrintingCentreMockMvc
            .perform(get(ENTITY_API_URL_ID, printingCentre.getPrintingCentreId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.printingCentreId").value(printingCentre.getPrintingCentreId().intValue()))
            .andExpect(jsonPath("$.printingCentreDescription").value(DEFAULT_PRINTING_CENTRE_DESCRIPTION))
            .andExpect(jsonPath("$.printingCentreName").value(DEFAULT_PRINTING_CENTRE_NAME))
            .andExpect(jsonPath("$.printingCentreLogoContentType").value(DEFAULT_PRINTING_CENTRE_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.printingCentreLogo").value(Base64Utils.encodeToString(DEFAULT_PRINTING_CENTRE_LOGO)))
            .andExpect(jsonPath("$.printingCentreAdresse").value(DEFAULT_PRINTING_CENTRE_ADRESSE))
            .andExpect(jsonPath("$.printingCentreEmail").value(DEFAULT_PRINTING_CENTRE_EMAIL))
            .andExpect(jsonPath("$.printingCentreTel").value(DEFAULT_PRINTING_CENTRE_TEL))
            .andExpect(jsonPath("$.printingCentreFax").value(DEFAULT_PRINTING_CENTRE_FAX))
            .andExpect(jsonPath("$.printingCentreResponsableName").value(DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME))
            .andExpect(jsonPath("$.printingParams").value(DEFAULT_PRINTING_PARAMS))
            .andExpect(jsonPath("$.printingAttributs").value(DEFAULT_PRINTING_ATTRIBUTS))
            .andExpect(jsonPath("$.printingCentreStat").value(DEFAULT_PRINTING_CENTRE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getPrintingCentresByIdFiltering() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        Long id = printingCentre.getPrintingCentreId();

        defaultPrintingCentreShouldBeFound("printingCentreId.equals=" + id);
        defaultPrintingCentreShouldNotBeFound("printingCentreId.notEquals=" + id);

        defaultPrintingCentreShouldBeFound("printingCentreId.greaterThanOrEqual=" + id);
        defaultPrintingCentreShouldNotBeFound("printingCentreId.greaterThan=" + id);

        defaultPrintingCentreShouldBeFound("printingCentreId.lessThanOrEqual=" + id);
        defaultPrintingCentreShouldNotBeFound("printingCentreId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreDescription equals to DEFAULT_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldBeFound("printingCentreDescription.equals=" + DEFAULT_PRINTING_CENTRE_DESCRIPTION);

        // Get all the printingCentreList where printingCentreDescription equals to UPDATED_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldNotBeFound("printingCentreDescription.equals=" + UPDATED_PRINTING_CENTRE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreDescription in DEFAULT_PRINTING_CENTRE_DESCRIPTION or UPDATED_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldBeFound(
            "printingCentreDescription.in=" + DEFAULT_PRINTING_CENTRE_DESCRIPTION + "," + UPDATED_PRINTING_CENTRE_DESCRIPTION
        );

        // Get all the printingCentreList where printingCentreDescription equals to UPDATED_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldNotBeFound("printingCentreDescription.in=" + UPDATED_PRINTING_CENTRE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreDescription is not null
        defaultPrintingCentreShouldBeFound("printingCentreDescription.specified=true");

        // Get all the printingCentreList where printingCentreDescription is null
        defaultPrintingCentreShouldNotBeFound("printingCentreDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreDescriptionContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreDescription contains DEFAULT_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldBeFound("printingCentreDescription.contains=" + DEFAULT_PRINTING_CENTRE_DESCRIPTION);

        // Get all the printingCentreList where printingCentreDescription contains UPDATED_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldNotBeFound("printingCentreDescription.contains=" + UPDATED_PRINTING_CENTRE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreDescription does not contain DEFAULT_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldNotBeFound("printingCentreDescription.doesNotContain=" + DEFAULT_PRINTING_CENTRE_DESCRIPTION);

        // Get all the printingCentreList where printingCentreDescription does not contain UPDATED_PRINTING_CENTRE_DESCRIPTION
        defaultPrintingCentreShouldBeFound("printingCentreDescription.doesNotContain=" + UPDATED_PRINTING_CENTRE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreNameIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreName equals to DEFAULT_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldBeFound("printingCentreName.equals=" + DEFAULT_PRINTING_CENTRE_NAME);

        // Get all the printingCentreList where printingCentreName equals to UPDATED_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreName.equals=" + UPDATED_PRINTING_CENTRE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreNameIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreName in DEFAULT_PRINTING_CENTRE_NAME or UPDATED_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldBeFound("printingCentreName.in=" + DEFAULT_PRINTING_CENTRE_NAME + "," + UPDATED_PRINTING_CENTRE_NAME);

        // Get all the printingCentreList where printingCentreName equals to UPDATED_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreName.in=" + UPDATED_PRINTING_CENTRE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreName is not null
        defaultPrintingCentreShouldBeFound("printingCentreName.specified=true");

        // Get all the printingCentreList where printingCentreName is null
        defaultPrintingCentreShouldNotBeFound("printingCentreName.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreNameContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreName contains DEFAULT_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldBeFound("printingCentreName.contains=" + DEFAULT_PRINTING_CENTRE_NAME);

        // Get all the printingCentreList where printingCentreName contains UPDATED_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreName.contains=" + UPDATED_PRINTING_CENTRE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreNameNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreName does not contain DEFAULT_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreName.doesNotContain=" + DEFAULT_PRINTING_CENTRE_NAME);

        // Get all the printingCentreList where printingCentreName does not contain UPDATED_PRINTING_CENTRE_NAME
        defaultPrintingCentreShouldBeFound("printingCentreName.doesNotContain=" + UPDATED_PRINTING_CENTRE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreAdresse equals to DEFAULT_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldBeFound("printingCentreAdresse.equals=" + DEFAULT_PRINTING_CENTRE_ADRESSE);

        // Get all the printingCentreList where printingCentreAdresse equals to UPDATED_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldNotBeFound("printingCentreAdresse.equals=" + UPDATED_PRINTING_CENTRE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreAdresse in DEFAULT_PRINTING_CENTRE_ADRESSE or UPDATED_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldBeFound(
            "printingCentreAdresse.in=" + DEFAULT_PRINTING_CENTRE_ADRESSE + "," + UPDATED_PRINTING_CENTRE_ADRESSE
        );

        // Get all the printingCentreList where printingCentreAdresse equals to UPDATED_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldNotBeFound("printingCentreAdresse.in=" + UPDATED_PRINTING_CENTRE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreAdresse is not null
        defaultPrintingCentreShouldBeFound("printingCentreAdresse.specified=true");

        // Get all the printingCentreList where printingCentreAdresse is null
        defaultPrintingCentreShouldNotBeFound("printingCentreAdresse.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreAdresseContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreAdresse contains DEFAULT_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldBeFound("printingCentreAdresse.contains=" + DEFAULT_PRINTING_CENTRE_ADRESSE);

        // Get all the printingCentreList where printingCentreAdresse contains UPDATED_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldNotBeFound("printingCentreAdresse.contains=" + UPDATED_PRINTING_CENTRE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreAdresse does not contain DEFAULT_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldNotBeFound("printingCentreAdresse.doesNotContain=" + DEFAULT_PRINTING_CENTRE_ADRESSE);

        // Get all the printingCentreList where printingCentreAdresse does not contain UPDATED_PRINTING_CENTRE_ADRESSE
        defaultPrintingCentreShouldBeFound("printingCentreAdresse.doesNotContain=" + UPDATED_PRINTING_CENTRE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreEmail equals to DEFAULT_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldBeFound("printingCentreEmail.equals=" + DEFAULT_PRINTING_CENTRE_EMAIL);

        // Get all the printingCentreList where printingCentreEmail equals to UPDATED_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldNotBeFound("printingCentreEmail.equals=" + UPDATED_PRINTING_CENTRE_EMAIL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreEmailIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreEmail in DEFAULT_PRINTING_CENTRE_EMAIL or UPDATED_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldBeFound("printingCentreEmail.in=" + DEFAULT_PRINTING_CENTRE_EMAIL + "," + UPDATED_PRINTING_CENTRE_EMAIL);

        // Get all the printingCentreList where printingCentreEmail equals to UPDATED_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldNotBeFound("printingCentreEmail.in=" + UPDATED_PRINTING_CENTRE_EMAIL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreEmail is not null
        defaultPrintingCentreShouldBeFound("printingCentreEmail.specified=true");

        // Get all the printingCentreList where printingCentreEmail is null
        defaultPrintingCentreShouldNotBeFound("printingCentreEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreEmailContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreEmail contains DEFAULT_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldBeFound("printingCentreEmail.contains=" + DEFAULT_PRINTING_CENTRE_EMAIL);

        // Get all the printingCentreList where printingCentreEmail contains UPDATED_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldNotBeFound("printingCentreEmail.contains=" + UPDATED_PRINTING_CENTRE_EMAIL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreEmailNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreEmail does not contain DEFAULT_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldNotBeFound("printingCentreEmail.doesNotContain=" + DEFAULT_PRINTING_CENTRE_EMAIL);

        // Get all the printingCentreList where printingCentreEmail does not contain UPDATED_PRINTING_CENTRE_EMAIL
        defaultPrintingCentreShouldBeFound("printingCentreEmail.doesNotContain=" + UPDATED_PRINTING_CENTRE_EMAIL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreTelIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreTel equals to DEFAULT_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldBeFound("printingCentreTel.equals=" + DEFAULT_PRINTING_CENTRE_TEL);

        // Get all the printingCentreList where printingCentreTel equals to UPDATED_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldNotBeFound("printingCentreTel.equals=" + UPDATED_PRINTING_CENTRE_TEL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreTelIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreTel in DEFAULT_PRINTING_CENTRE_TEL or UPDATED_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldBeFound("printingCentreTel.in=" + DEFAULT_PRINTING_CENTRE_TEL + "," + UPDATED_PRINTING_CENTRE_TEL);

        // Get all the printingCentreList where printingCentreTel equals to UPDATED_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldNotBeFound("printingCentreTel.in=" + UPDATED_PRINTING_CENTRE_TEL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreTel is not null
        defaultPrintingCentreShouldBeFound("printingCentreTel.specified=true");

        // Get all the printingCentreList where printingCentreTel is null
        defaultPrintingCentreShouldNotBeFound("printingCentreTel.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreTelContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreTel contains DEFAULT_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldBeFound("printingCentreTel.contains=" + DEFAULT_PRINTING_CENTRE_TEL);

        // Get all the printingCentreList where printingCentreTel contains UPDATED_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldNotBeFound("printingCentreTel.contains=" + UPDATED_PRINTING_CENTRE_TEL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreTelNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreTel does not contain DEFAULT_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldNotBeFound("printingCentreTel.doesNotContain=" + DEFAULT_PRINTING_CENTRE_TEL);

        // Get all the printingCentreList where printingCentreTel does not contain UPDATED_PRINTING_CENTRE_TEL
        defaultPrintingCentreShouldBeFound("printingCentreTel.doesNotContain=" + UPDATED_PRINTING_CENTRE_TEL);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreFax equals to DEFAULT_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldBeFound("printingCentreFax.equals=" + DEFAULT_PRINTING_CENTRE_FAX);

        // Get all the printingCentreList where printingCentreFax equals to UPDATED_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldNotBeFound("printingCentreFax.equals=" + UPDATED_PRINTING_CENTRE_FAX);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreFaxIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreFax in DEFAULT_PRINTING_CENTRE_FAX or UPDATED_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldBeFound("printingCentreFax.in=" + DEFAULT_PRINTING_CENTRE_FAX + "," + UPDATED_PRINTING_CENTRE_FAX);

        // Get all the printingCentreList where printingCentreFax equals to UPDATED_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldNotBeFound("printingCentreFax.in=" + UPDATED_PRINTING_CENTRE_FAX);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreFax is not null
        defaultPrintingCentreShouldBeFound("printingCentreFax.specified=true");

        // Get all the printingCentreList where printingCentreFax is null
        defaultPrintingCentreShouldNotBeFound("printingCentreFax.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreFaxContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreFax contains DEFAULT_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldBeFound("printingCentreFax.contains=" + DEFAULT_PRINTING_CENTRE_FAX);

        // Get all the printingCentreList where printingCentreFax contains UPDATED_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldNotBeFound("printingCentreFax.contains=" + UPDATED_PRINTING_CENTRE_FAX);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreFaxNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreFax does not contain DEFAULT_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldNotBeFound("printingCentreFax.doesNotContain=" + DEFAULT_PRINTING_CENTRE_FAX);

        // Get all the printingCentreList where printingCentreFax does not contain UPDATED_PRINTING_CENTRE_FAX
        defaultPrintingCentreShouldBeFound("printingCentreFax.doesNotContain=" + UPDATED_PRINTING_CENTRE_FAX);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreResponsableNameIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreResponsableName equals to DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldBeFound("printingCentreResponsableName.equals=" + DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME);

        // Get all the printingCentreList where printingCentreResponsableName equals to UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreResponsableName.equals=" + UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreResponsableNameIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreResponsableName in DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME or UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldBeFound(
            "printingCentreResponsableName.in=" + DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME + "," + UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME
        );

        // Get all the printingCentreList where printingCentreResponsableName equals to UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreResponsableName.in=" + UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreResponsableNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreResponsableName is not null
        defaultPrintingCentreShouldBeFound("printingCentreResponsableName.specified=true");

        // Get all the printingCentreList where printingCentreResponsableName is null
        defaultPrintingCentreShouldNotBeFound("printingCentreResponsableName.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreResponsableNameContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreResponsableName contains DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldBeFound("printingCentreResponsableName.contains=" + DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME);

        // Get all the printingCentreList where printingCentreResponsableName contains UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreResponsableName.contains=" + UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreResponsableNameNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreResponsableName does not contain DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldNotBeFound("printingCentreResponsableName.doesNotContain=" + DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME);

        // Get all the printingCentreList where printingCentreResponsableName does not contain UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME
        defaultPrintingCentreShouldBeFound("printingCentreResponsableName.doesNotContain=" + UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingParams equals to DEFAULT_PRINTING_PARAMS
        defaultPrintingCentreShouldBeFound("printingParams.equals=" + DEFAULT_PRINTING_PARAMS);

        // Get all the printingCentreList where printingParams equals to UPDATED_PRINTING_PARAMS
        defaultPrintingCentreShouldNotBeFound("printingParams.equals=" + UPDATED_PRINTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingParamsIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingParams in DEFAULT_PRINTING_PARAMS or UPDATED_PRINTING_PARAMS
        defaultPrintingCentreShouldBeFound("printingParams.in=" + DEFAULT_PRINTING_PARAMS + "," + UPDATED_PRINTING_PARAMS);

        // Get all the printingCentreList where printingParams equals to UPDATED_PRINTING_PARAMS
        defaultPrintingCentreShouldNotBeFound("printingParams.in=" + UPDATED_PRINTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingParams is not null
        defaultPrintingCentreShouldBeFound("printingParams.specified=true");

        // Get all the printingCentreList where printingParams is null
        defaultPrintingCentreShouldNotBeFound("printingParams.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingParamsContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingParams contains DEFAULT_PRINTING_PARAMS
        defaultPrintingCentreShouldBeFound("printingParams.contains=" + DEFAULT_PRINTING_PARAMS);

        // Get all the printingCentreList where printingParams contains UPDATED_PRINTING_PARAMS
        defaultPrintingCentreShouldNotBeFound("printingParams.contains=" + UPDATED_PRINTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingParamsNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingParams does not contain DEFAULT_PRINTING_PARAMS
        defaultPrintingCentreShouldNotBeFound("printingParams.doesNotContain=" + DEFAULT_PRINTING_PARAMS);

        // Get all the printingCentreList where printingParams does not contain UPDATED_PRINTING_PARAMS
        defaultPrintingCentreShouldBeFound("printingParams.doesNotContain=" + UPDATED_PRINTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingAttributs equals to DEFAULT_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldBeFound("printingAttributs.equals=" + DEFAULT_PRINTING_ATTRIBUTS);

        // Get all the printingCentreList where printingAttributs equals to UPDATED_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldNotBeFound("printingAttributs.equals=" + UPDATED_PRINTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingAttributs in DEFAULT_PRINTING_ATTRIBUTS or UPDATED_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldBeFound("printingAttributs.in=" + DEFAULT_PRINTING_ATTRIBUTS + "," + UPDATED_PRINTING_ATTRIBUTS);

        // Get all the printingCentreList where printingAttributs equals to UPDATED_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldNotBeFound("printingAttributs.in=" + UPDATED_PRINTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingAttributs is not null
        defaultPrintingCentreShouldBeFound("printingAttributs.specified=true");

        // Get all the printingCentreList where printingAttributs is null
        defaultPrintingCentreShouldNotBeFound("printingAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingAttributsContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingAttributs contains DEFAULT_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldBeFound("printingAttributs.contains=" + DEFAULT_PRINTING_ATTRIBUTS);

        // Get all the printingCentreList where printingAttributs contains UPDATED_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldNotBeFound("printingAttributs.contains=" + UPDATED_PRINTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingAttributs does not contain DEFAULT_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldNotBeFound("printingAttributs.doesNotContain=" + DEFAULT_PRINTING_ATTRIBUTS);

        // Get all the printingCentreList where printingAttributs does not contain UPDATED_PRINTING_ATTRIBUTS
        defaultPrintingCentreShouldBeFound("printingAttributs.doesNotContain=" + UPDATED_PRINTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreStatIsEqualToSomething() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreStat equals to DEFAULT_PRINTING_CENTRE_STAT
        defaultPrintingCentreShouldBeFound("printingCentreStat.equals=" + DEFAULT_PRINTING_CENTRE_STAT);

        // Get all the printingCentreList where printingCentreStat equals to UPDATED_PRINTING_CENTRE_STAT
        defaultPrintingCentreShouldNotBeFound("printingCentreStat.equals=" + UPDATED_PRINTING_CENTRE_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreStatIsInShouldWork() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreStat in DEFAULT_PRINTING_CENTRE_STAT or UPDATED_PRINTING_CENTRE_STAT
        defaultPrintingCentreShouldBeFound("printingCentreStat.in=" + DEFAULT_PRINTING_CENTRE_STAT + "," + UPDATED_PRINTING_CENTRE_STAT);

        // Get all the printingCentreList where printingCentreStat equals to UPDATED_PRINTING_CENTRE_STAT
        defaultPrintingCentreShouldNotBeFound("printingCentreStat.in=" + UPDATED_PRINTING_CENTRE_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingCentreStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        // Get all the printingCentreList where printingCentreStat is not null
        defaultPrintingCentreShouldBeFound("printingCentreStat.specified=true");

        // Get all the printingCentreList where printingCentreStat is null
        defaultPrintingCentreShouldNotBeFound("printingCentreStat.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingCentresByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        printingCentre.setCity(city);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long cityId = city.getCityId();

        // Get all the printingCentreList where city equals to cityId
        defaultPrintingCentreShouldBeFound("cityId.equals=" + cityId);

        // Get all the printingCentreList where city equals to (cityId + 1)
        defaultPrintingCentreShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingCentresByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        printingCentre.setCountry(country);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long countryId = country.getCountryId();

        // Get all the printingCentreList where country equals to countryId
        defaultPrintingCentreShouldBeFound("countryId.equals=" + countryId);

        // Get all the printingCentreList where country equals to (countryId + 1)
        defaultPrintingCentreShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingCentresByOrganizIsEqualToSomething() throws Exception {
        Organiz organiz;
        if (TestUtil.findAll(em, Organiz.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            organiz = OrganizResourceIT.createEntity(em);
        } else {
            organiz = TestUtil.findAll(em, Organiz.class).get(0);
        }
        em.persist(organiz);
        em.flush();
        printingCentre.setOrganiz(organiz);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long organizId = organiz.getOrganizId();

        // Get all the printingCentreList where organiz equals to organizId
        defaultPrintingCentreShouldBeFound("organizId.equals=" + organizId);

        // Get all the printingCentreList where organiz equals to (organizId + 1)
        defaultPrintingCentreShouldNotBeFound("organizId.equals=" + (organizId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingTypeIsEqualToSomething() throws Exception {
        PrintingType printingType;
        if (TestUtil.findAll(em, PrintingType.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            printingType = PrintingTypeResourceIT.createEntity(em);
        } else {
            printingType = TestUtil.findAll(em, PrintingType.class).get(0);
        }
        em.persist(printingType);
        em.flush();
        printingCentre.setPrintingType(printingType);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long printingTypeId = printingType.getPrintingTypeId();

        // Get all the printingCentreList where printingType equals to printingTypeId
        defaultPrintingCentreShouldBeFound("printingTypeId.equals=" + printingTypeId);

        // Get all the printingCentreList where printingType equals to (printingTypeId + 1)
        defaultPrintingCentreShouldNotBeFound("printingTypeId.equals=" + (printingTypeId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingServerIsEqualToSomething() throws Exception {
        PrintingServer printingServer;
        if (TestUtil.findAll(em, PrintingServer.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            printingServer = PrintingServerResourceIT.createEntity(em);
        } else {
            printingServer = TestUtil.findAll(em, PrintingServer.class).get(0);
        }
        em.persist(printingServer);
        em.flush();
        printingCentre.setPrintingServer(printingServer);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long printingServerId = printingServer.getPrintingServerId();

        // Get all the printingCentreList where printingServer equals to printingServerId
        defaultPrintingCentreShouldBeFound("printingServerId.equals=" + printingServerId);

        // Get all the printingCentreList where printingServer equals to (printingServerId + 1)
        defaultPrintingCentreShouldNotBeFound("printingServerId.equals=" + (printingServerId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingCentresByPrintingModelIsEqualToSomething() throws Exception {
        PrintingModel printingModel;
        if (TestUtil.findAll(em, PrintingModel.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            printingModel = PrintingModelResourceIT.createEntity(em);
        } else {
            printingModel = TestUtil.findAll(em, PrintingModel.class).get(0);
        }
        em.persist(printingModel);
        em.flush();
        printingCentre.setPrintingModel(printingModel);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long printingModelId = printingModel.getPrintingModelId();

        // Get all the printingCentreList where printingModel equals to printingModelId
        defaultPrintingCentreShouldBeFound("printingModelId.equals=" + printingModelId);

        // Get all the printingCentreList where printingModel equals to (printingModelId + 1)
        defaultPrintingCentreShouldNotBeFound("printingModelId.equals=" + (printingModelId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingCentresByLanguageIsEqualToSomething() throws Exception {
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            language = LanguageResourceIT.createEntity(em);
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        em.persist(language);
        em.flush();
        printingCentre.setLanguage(language);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long languageId = language.getLanguageId();

        // Get all the printingCentreList where language equals to languageId
        defaultPrintingCentreShouldBeFound("languageId.equals=" + languageId);

        // Get all the printingCentreList where language equals to (languageId + 1)
        defaultPrintingCentreShouldNotBeFound("languageId.equals=" + (languageId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingCentresByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            printingCentreRepository.saveAndFlush(printingCentre);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        printingCentre.setEvent(event);
        printingCentreRepository.saveAndFlush(printingCentre);
        Long eventId = event.getEventId();

        // Get all the printingCentreList where event equals to eventId
        defaultPrintingCentreShouldBeFound("eventId.equals=" + eventId);

        // Get all the printingCentreList where event equals to (eventId + 1)
        defaultPrintingCentreShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrintingCentreShouldBeFound(String filter) throws Exception {
        restPrintingCentreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingCentreId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingCentreId").value(hasItem(printingCentre.getPrintingCentreId().intValue())))
            .andExpect(jsonPath("$.[*].printingCentreDescription").value(hasItem(DEFAULT_PRINTING_CENTRE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingCentreName").value(hasItem(DEFAULT_PRINTING_CENTRE_NAME)))
            .andExpect(jsonPath("$.[*].printingCentreLogoContentType").value(hasItem(DEFAULT_PRINTING_CENTRE_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].printingCentreLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRINTING_CENTRE_LOGO))))
            .andExpect(jsonPath("$.[*].printingCentreAdresse").value(hasItem(DEFAULT_PRINTING_CENTRE_ADRESSE)))
            .andExpect(jsonPath("$.[*].printingCentreEmail").value(hasItem(DEFAULT_PRINTING_CENTRE_EMAIL)))
            .andExpect(jsonPath("$.[*].printingCentreTel").value(hasItem(DEFAULT_PRINTING_CENTRE_TEL)))
            .andExpect(jsonPath("$.[*].printingCentreFax").value(hasItem(DEFAULT_PRINTING_CENTRE_FAX)))
            .andExpect(jsonPath("$.[*].printingCentreResponsableName").value(hasItem(DEFAULT_PRINTING_CENTRE_RESPONSABLE_NAME)))
            .andExpect(jsonPath("$.[*].printingParams").value(hasItem(DEFAULT_PRINTING_PARAMS)))
            .andExpect(jsonPath("$.[*].printingAttributs").value(hasItem(DEFAULT_PRINTING_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].printingCentreStat").value(hasItem(DEFAULT_PRINTING_CENTRE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restPrintingCentreMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingCentreId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrintingCentreShouldNotBeFound(String filter) throws Exception {
        restPrintingCentreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingCentreId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrintingCentreMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingCentreId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPrintingCentre() throws Exception {
        // Get the printingCentre
        restPrintingCentreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrintingCentre() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();

        // Update the printingCentre
        PrintingCentre updatedPrintingCentre = printingCentreRepository.findById(printingCentre.getPrintingCentreId()).get();
        // Disconnect from session so that the updates on updatedPrintingCentre are not directly saved in db
        em.detach(updatedPrintingCentre);
        updatedPrintingCentre
            .printingCentreDescription(UPDATED_PRINTING_CENTRE_DESCRIPTION)
            .printingCentreName(UPDATED_PRINTING_CENTRE_NAME)
            .printingCentreLogo(UPDATED_PRINTING_CENTRE_LOGO)
            .printingCentreLogoContentType(UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE)
            .printingCentreAdresse(UPDATED_PRINTING_CENTRE_ADRESSE)
            .printingCentreEmail(UPDATED_PRINTING_CENTRE_EMAIL)
            .printingCentreTel(UPDATED_PRINTING_CENTRE_TEL)
            .printingCentreFax(UPDATED_PRINTING_CENTRE_FAX)
            .printingCentreResponsableName(UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME)
            .printingParams(UPDATED_PRINTING_PARAMS)
            .printingAttributs(UPDATED_PRINTING_ATTRIBUTS)
            .printingCentreStat(UPDATED_PRINTING_CENTRE_STAT);
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(updatedPrintingCentre);

        restPrintingCentreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingCentreDTO.getPrintingCentreId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isOk());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
        PrintingCentre testPrintingCentre = printingCentreList.get(printingCentreList.size() - 1);
        assertThat(testPrintingCentre.getPrintingCentreDescription()).isEqualTo(UPDATED_PRINTING_CENTRE_DESCRIPTION);
        assertThat(testPrintingCentre.getPrintingCentreName()).isEqualTo(UPDATED_PRINTING_CENTRE_NAME);
        assertThat(testPrintingCentre.getPrintingCentreLogo()).isEqualTo(UPDATED_PRINTING_CENTRE_LOGO);
        assertThat(testPrintingCentre.getPrintingCentreLogoContentType()).isEqualTo(UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE);
        assertThat(testPrintingCentre.getPrintingCentreAdresse()).isEqualTo(UPDATED_PRINTING_CENTRE_ADRESSE);
        assertThat(testPrintingCentre.getPrintingCentreEmail()).isEqualTo(UPDATED_PRINTING_CENTRE_EMAIL);
        assertThat(testPrintingCentre.getPrintingCentreTel()).isEqualTo(UPDATED_PRINTING_CENTRE_TEL);
        assertThat(testPrintingCentre.getPrintingCentreFax()).isEqualTo(UPDATED_PRINTING_CENTRE_FAX);
        assertThat(testPrintingCentre.getPrintingCentreResponsableName()).isEqualTo(UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME);
        assertThat(testPrintingCentre.getPrintingParams()).isEqualTo(UPDATED_PRINTING_PARAMS);
        assertThat(testPrintingCentre.getPrintingAttributs()).isEqualTo(UPDATED_PRINTING_ATTRIBUTS);
        assertThat(testPrintingCentre.getPrintingCentreStat()).isEqualTo(UPDATED_PRINTING_CENTRE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingPrintingCentre() throws Exception {
        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();
        printingCentre.setPrintingCentreId(count.incrementAndGet());

        // Create the PrintingCentre
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingCentreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingCentreDTO.getPrintingCentreId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrintingCentre() throws Exception {
        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();
        printingCentre.setPrintingCentreId(count.incrementAndGet());

        // Create the PrintingCentre
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingCentreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrintingCentre() throws Exception {
        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();
        printingCentre.setPrintingCentreId(count.incrementAndGet());

        // Create the PrintingCentre
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingCentreMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrintingCentreWithPatch() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();

        // Update the printingCentre using partial update
        PrintingCentre partialUpdatedPrintingCentre = new PrintingCentre();
        partialUpdatedPrintingCentre.setPrintingCentreId(printingCentre.getPrintingCentreId());

        partialUpdatedPrintingCentre
            .printingCentreDescription(UPDATED_PRINTING_CENTRE_DESCRIPTION)
            .printingCentreLogo(UPDATED_PRINTING_CENTRE_LOGO)
            .printingCentreLogoContentType(UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE)
            .printingCentreEmail(UPDATED_PRINTING_CENTRE_EMAIL)
            .printingCentreTel(UPDATED_PRINTING_CENTRE_TEL)
            .printingCentreResponsableName(UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME)
            .printingParams(UPDATED_PRINTING_PARAMS)
            .printingAttributs(UPDATED_PRINTING_ATTRIBUTS);

        restPrintingCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingCentre.getPrintingCentreId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingCentre))
            )
            .andExpect(status().isOk());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
        PrintingCentre testPrintingCentre = printingCentreList.get(printingCentreList.size() - 1);
        assertThat(testPrintingCentre.getPrintingCentreDescription()).isEqualTo(UPDATED_PRINTING_CENTRE_DESCRIPTION);
        assertThat(testPrintingCentre.getPrintingCentreName()).isEqualTo(DEFAULT_PRINTING_CENTRE_NAME);
        assertThat(testPrintingCentre.getPrintingCentreLogo()).isEqualTo(UPDATED_PRINTING_CENTRE_LOGO);
        assertThat(testPrintingCentre.getPrintingCentreLogoContentType()).isEqualTo(UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE);
        assertThat(testPrintingCentre.getPrintingCentreAdresse()).isEqualTo(DEFAULT_PRINTING_CENTRE_ADRESSE);
        assertThat(testPrintingCentre.getPrintingCentreEmail()).isEqualTo(UPDATED_PRINTING_CENTRE_EMAIL);
        assertThat(testPrintingCentre.getPrintingCentreTel()).isEqualTo(UPDATED_PRINTING_CENTRE_TEL);
        assertThat(testPrintingCentre.getPrintingCentreFax()).isEqualTo(DEFAULT_PRINTING_CENTRE_FAX);
        assertThat(testPrintingCentre.getPrintingCentreResponsableName()).isEqualTo(UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME);
        assertThat(testPrintingCentre.getPrintingParams()).isEqualTo(UPDATED_PRINTING_PARAMS);
        assertThat(testPrintingCentre.getPrintingAttributs()).isEqualTo(UPDATED_PRINTING_ATTRIBUTS);
        assertThat(testPrintingCentre.getPrintingCentreStat()).isEqualTo(DEFAULT_PRINTING_CENTRE_STAT);
    }

    @Test
    @Transactional
    void fullUpdatePrintingCentreWithPatch() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();

        // Update the printingCentre using partial update
        PrintingCentre partialUpdatedPrintingCentre = new PrintingCentre();
        partialUpdatedPrintingCentre.setPrintingCentreId(printingCentre.getPrintingCentreId());

        partialUpdatedPrintingCentre
            .printingCentreDescription(UPDATED_PRINTING_CENTRE_DESCRIPTION)
            .printingCentreName(UPDATED_PRINTING_CENTRE_NAME)
            .printingCentreLogo(UPDATED_PRINTING_CENTRE_LOGO)
            .printingCentreLogoContentType(UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE)
            .printingCentreAdresse(UPDATED_PRINTING_CENTRE_ADRESSE)
            .printingCentreEmail(UPDATED_PRINTING_CENTRE_EMAIL)
            .printingCentreTel(UPDATED_PRINTING_CENTRE_TEL)
            .printingCentreFax(UPDATED_PRINTING_CENTRE_FAX)
            .printingCentreResponsableName(UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME)
            .printingParams(UPDATED_PRINTING_PARAMS)
            .printingAttributs(UPDATED_PRINTING_ATTRIBUTS)
            .printingCentreStat(UPDATED_PRINTING_CENTRE_STAT);

        restPrintingCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingCentre.getPrintingCentreId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingCentre))
            )
            .andExpect(status().isOk());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
        PrintingCentre testPrintingCentre = printingCentreList.get(printingCentreList.size() - 1);
        assertThat(testPrintingCentre.getPrintingCentreDescription()).isEqualTo(UPDATED_PRINTING_CENTRE_DESCRIPTION);
        assertThat(testPrintingCentre.getPrintingCentreName()).isEqualTo(UPDATED_PRINTING_CENTRE_NAME);
        assertThat(testPrintingCentre.getPrintingCentreLogo()).isEqualTo(UPDATED_PRINTING_CENTRE_LOGO);
        assertThat(testPrintingCentre.getPrintingCentreLogoContentType()).isEqualTo(UPDATED_PRINTING_CENTRE_LOGO_CONTENT_TYPE);
        assertThat(testPrintingCentre.getPrintingCentreAdresse()).isEqualTo(UPDATED_PRINTING_CENTRE_ADRESSE);
        assertThat(testPrintingCentre.getPrintingCentreEmail()).isEqualTo(UPDATED_PRINTING_CENTRE_EMAIL);
        assertThat(testPrintingCentre.getPrintingCentreTel()).isEqualTo(UPDATED_PRINTING_CENTRE_TEL);
        assertThat(testPrintingCentre.getPrintingCentreFax()).isEqualTo(UPDATED_PRINTING_CENTRE_FAX);
        assertThat(testPrintingCentre.getPrintingCentreResponsableName()).isEqualTo(UPDATED_PRINTING_CENTRE_RESPONSABLE_NAME);
        assertThat(testPrintingCentre.getPrintingParams()).isEqualTo(UPDATED_PRINTING_PARAMS);
        assertThat(testPrintingCentre.getPrintingAttributs()).isEqualTo(UPDATED_PRINTING_ATTRIBUTS);
        assertThat(testPrintingCentre.getPrintingCentreStat()).isEqualTo(UPDATED_PRINTING_CENTRE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingPrintingCentre() throws Exception {
        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();
        printingCentre.setPrintingCentreId(count.incrementAndGet());

        // Create the PrintingCentre
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, printingCentreDTO.getPrintingCentreId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrintingCentre() throws Exception {
        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();
        printingCentre.setPrintingCentreId(count.incrementAndGet());

        // Create the PrintingCentre
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingCentreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrintingCentre() throws Exception {
        int databaseSizeBeforeUpdate = printingCentreRepository.findAll().size();
        printingCentre.setPrintingCentreId(count.incrementAndGet());

        // Create the PrintingCentre
        PrintingCentreDTO printingCentreDTO = printingCentreMapper.toDto(printingCentre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingCentreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingCentreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingCentre in the database
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrintingCentre() throws Exception {
        // Initialize the database
        printingCentreRepository.saveAndFlush(printingCentre);

        int databaseSizeBeforeDelete = printingCentreRepository.findAll().size();

        // Delete the printingCentre
        restPrintingCentreMockMvc
            .perform(delete(ENTITY_API_URL_ID, printingCentre.getPrintingCentreId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrintingCentre> printingCentreList = printingCentreRepository.findAll();
        assertThat(printingCentreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
