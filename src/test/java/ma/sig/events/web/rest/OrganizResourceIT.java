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
import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.City;
import ma.sig.events.domain.Country;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Organiz;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.repository.OrganizRepository;
import ma.sig.events.service.criteria.OrganizCriteria;
import ma.sig.events.service.dto.OrganizDTO;
import ma.sig.events.service.mapper.OrganizMapper;
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
 * Integration tests for the {@link OrganizResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizResourceIT {

    private static final String DEFAULT_ORGANIZ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZ_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ORGANIZ_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ORGANIZ_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ORGANIZ_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ORGANIZ_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ORGANIZ_TEL = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZ_FAX = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZ_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZ_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZ_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZ_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZ_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ORGANIZ_STAT = false;
    private static final Boolean UPDATED_ORGANIZ_STAT = true;

    private static final String ENTITY_API_URL = "/api/organizs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizRepository organizRepository;

    @Autowired
    private OrganizMapper organizMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizMockMvc;

    private Organiz organiz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organiz createEntity(EntityManager em) {
        Organiz organiz = new Organiz()
            .organizName(DEFAULT_ORGANIZ_NAME)
            .organizDescription(DEFAULT_ORGANIZ_DESCRIPTION)
            .organizLogo(DEFAULT_ORGANIZ_LOGO)
            .organizLogoContentType(DEFAULT_ORGANIZ_LOGO_CONTENT_TYPE)
            .organizTel(DEFAULT_ORGANIZ_TEL)
            .organizFax(DEFAULT_ORGANIZ_FAX)
            .organizEmail(DEFAULT_ORGANIZ_EMAIL)
            .organizAdresse(DEFAULT_ORGANIZ_ADRESSE)
            .organizParams(DEFAULT_ORGANIZ_PARAMS)
            .organizAttributs(DEFAULT_ORGANIZ_ATTRIBUTS)
            .organizStat(DEFAULT_ORGANIZ_STAT);
        return organiz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organiz createUpdatedEntity(EntityManager em) {
        Organiz organiz = new Organiz()
            .organizName(UPDATED_ORGANIZ_NAME)
            .organizDescription(UPDATED_ORGANIZ_DESCRIPTION)
            .organizLogo(UPDATED_ORGANIZ_LOGO)
            .organizLogoContentType(UPDATED_ORGANIZ_LOGO_CONTENT_TYPE)
            .organizTel(UPDATED_ORGANIZ_TEL)
            .organizFax(UPDATED_ORGANIZ_FAX)
            .organizEmail(UPDATED_ORGANIZ_EMAIL)
            .organizAdresse(UPDATED_ORGANIZ_ADRESSE)
            .organizParams(UPDATED_ORGANIZ_PARAMS)
            .organizAttributs(UPDATED_ORGANIZ_ATTRIBUTS)
            .organizStat(UPDATED_ORGANIZ_STAT);
        return organiz;
    }

    @BeforeEach
    public void initTest() {
        organiz = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganiz() throws Exception {
        int databaseSizeBeforeCreate = organizRepository.findAll().size();
        // Create the Organiz
        OrganizDTO organizDTO = organizMapper.toDto(organiz);
        restOrganizMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizDTO)))
            .andExpect(status().isCreated());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeCreate + 1);
        Organiz testOrganiz = organizList.get(organizList.size() - 1);
        assertThat(testOrganiz.getOrganizName()).isEqualTo(DEFAULT_ORGANIZ_NAME);
        assertThat(testOrganiz.getOrganizDescription()).isEqualTo(DEFAULT_ORGANIZ_DESCRIPTION);
        assertThat(testOrganiz.getOrganizLogo()).isEqualTo(DEFAULT_ORGANIZ_LOGO);
        assertThat(testOrganiz.getOrganizLogoContentType()).isEqualTo(DEFAULT_ORGANIZ_LOGO_CONTENT_TYPE);
        assertThat(testOrganiz.getOrganizTel()).isEqualTo(DEFAULT_ORGANIZ_TEL);
        assertThat(testOrganiz.getOrganizFax()).isEqualTo(DEFAULT_ORGANIZ_FAX);
        assertThat(testOrganiz.getOrganizEmail()).isEqualTo(DEFAULT_ORGANIZ_EMAIL);
        assertThat(testOrganiz.getOrganizAdresse()).isEqualTo(DEFAULT_ORGANIZ_ADRESSE);
        assertThat(testOrganiz.getOrganizParams()).isEqualTo(DEFAULT_ORGANIZ_PARAMS);
        assertThat(testOrganiz.getOrganizAttributs()).isEqualTo(DEFAULT_ORGANIZ_ATTRIBUTS);
        assertThat(testOrganiz.getOrganizStat()).isEqualTo(DEFAULT_ORGANIZ_STAT);
    }

    @Test
    @Transactional
    void createOrganizWithExistingId() throws Exception {
        // Create the Organiz with an existing ID
        organiz.setOrganizId(1L);
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        int databaseSizeBeforeCreate = organizRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOrganizNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizRepository.findAll().size();
        // set the field null
        organiz.setOrganizName(null);

        // Create the Organiz, which fails.
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        restOrganizMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizDTO)))
            .andExpect(status().isBadRequest());

        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizs() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList
        restOrganizMockMvc
            .perform(get(ENTITY_API_URL + "?sort=organizId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizId").value(hasItem(organiz.getOrganizId().intValue())))
            .andExpect(jsonPath("$.[*].organizName").value(hasItem(DEFAULT_ORGANIZ_NAME)))
            .andExpect(jsonPath("$.[*].organizDescription").value(hasItem(DEFAULT_ORGANIZ_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].organizLogoContentType").value(hasItem(DEFAULT_ORGANIZ_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].organizLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ORGANIZ_LOGO))))
            .andExpect(jsonPath("$.[*].organizTel").value(hasItem(DEFAULT_ORGANIZ_TEL)))
            .andExpect(jsonPath("$.[*].organizFax").value(hasItem(DEFAULT_ORGANIZ_FAX)))
            .andExpect(jsonPath("$.[*].organizEmail").value(hasItem(DEFAULT_ORGANIZ_EMAIL)))
            .andExpect(jsonPath("$.[*].organizAdresse").value(hasItem(DEFAULT_ORGANIZ_ADRESSE)))
            .andExpect(jsonPath("$.[*].organizParams").value(hasItem(DEFAULT_ORGANIZ_PARAMS)))
            .andExpect(jsonPath("$.[*].organizAttributs").value(hasItem(DEFAULT_ORGANIZ_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].organizStat").value(hasItem(DEFAULT_ORGANIZ_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getOrganiz() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get the organiz
        restOrganizMockMvc
            .perform(get(ENTITY_API_URL_ID, organiz.getOrganizId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.organizId").value(organiz.getOrganizId().intValue()))
            .andExpect(jsonPath("$.organizName").value(DEFAULT_ORGANIZ_NAME))
            .andExpect(jsonPath("$.organizDescription").value(DEFAULT_ORGANIZ_DESCRIPTION))
            .andExpect(jsonPath("$.organizLogoContentType").value(DEFAULT_ORGANIZ_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.organizLogo").value(Base64Utils.encodeToString(DEFAULT_ORGANIZ_LOGO)))
            .andExpect(jsonPath("$.organizTel").value(DEFAULT_ORGANIZ_TEL))
            .andExpect(jsonPath("$.organizFax").value(DEFAULT_ORGANIZ_FAX))
            .andExpect(jsonPath("$.organizEmail").value(DEFAULT_ORGANIZ_EMAIL))
            .andExpect(jsonPath("$.organizAdresse").value(DEFAULT_ORGANIZ_ADRESSE))
            .andExpect(jsonPath("$.organizParams").value(DEFAULT_ORGANIZ_PARAMS))
            .andExpect(jsonPath("$.organizAttributs").value(DEFAULT_ORGANIZ_ATTRIBUTS))
            .andExpect(jsonPath("$.organizStat").value(DEFAULT_ORGANIZ_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getOrganizsByIdFiltering() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        Long id = organiz.getOrganizId();

        defaultOrganizShouldBeFound("organizId.equals=" + id);
        defaultOrganizShouldNotBeFound("organizId.notEquals=" + id);

        defaultOrganizShouldBeFound("organizId.greaterThanOrEqual=" + id);
        defaultOrganizShouldNotBeFound("organizId.greaterThan=" + id);

        defaultOrganizShouldBeFound("organizId.lessThanOrEqual=" + id);
        defaultOrganizShouldNotBeFound("organizId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizNameIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizName equals to DEFAULT_ORGANIZ_NAME
        defaultOrganizShouldBeFound("organizName.equals=" + DEFAULT_ORGANIZ_NAME);

        // Get all the organizList where organizName equals to UPDATED_ORGANIZ_NAME
        defaultOrganizShouldNotBeFound("organizName.equals=" + UPDATED_ORGANIZ_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizNameIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizName in DEFAULT_ORGANIZ_NAME or UPDATED_ORGANIZ_NAME
        defaultOrganizShouldBeFound("organizName.in=" + DEFAULT_ORGANIZ_NAME + "," + UPDATED_ORGANIZ_NAME);

        // Get all the organizList where organizName equals to UPDATED_ORGANIZ_NAME
        defaultOrganizShouldNotBeFound("organizName.in=" + UPDATED_ORGANIZ_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizName is not null
        defaultOrganizShouldBeFound("organizName.specified=true");

        // Get all the organizList where organizName is null
        defaultOrganizShouldNotBeFound("organizName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizNameContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizName contains DEFAULT_ORGANIZ_NAME
        defaultOrganizShouldBeFound("organizName.contains=" + DEFAULT_ORGANIZ_NAME);

        // Get all the organizList where organizName contains UPDATED_ORGANIZ_NAME
        defaultOrganizShouldNotBeFound("organizName.contains=" + UPDATED_ORGANIZ_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizNameNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizName does not contain DEFAULT_ORGANIZ_NAME
        defaultOrganizShouldNotBeFound("organizName.doesNotContain=" + DEFAULT_ORGANIZ_NAME);

        // Get all the organizList where organizName does not contain UPDATED_ORGANIZ_NAME
        defaultOrganizShouldBeFound("organizName.doesNotContain=" + UPDATED_ORGANIZ_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizDescription equals to DEFAULT_ORGANIZ_DESCRIPTION
        defaultOrganizShouldBeFound("organizDescription.equals=" + DEFAULT_ORGANIZ_DESCRIPTION);

        // Get all the organizList where organizDescription equals to UPDATED_ORGANIZ_DESCRIPTION
        defaultOrganizShouldNotBeFound("organizDescription.equals=" + UPDATED_ORGANIZ_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizDescription in DEFAULT_ORGANIZ_DESCRIPTION or UPDATED_ORGANIZ_DESCRIPTION
        defaultOrganizShouldBeFound("organizDescription.in=" + DEFAULT_ORGANIZ_DESCRIPTION + "," + UPDATED_ORGANIZ_DESCRIPTION);

        // Get all the organizList where organizDescription equals to UPDATED_ORGANIZ_DESCRIPTION
        defaultOrganizShouldNotBeFound("organizDescription.in=" + UPDATED_ORGANIZ_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizDescription is not null
        defaultOrganizShouldBeFound("organizDescription.specified=true");

        // Get all the organizList where organizDescription is null
        defaultOrganizShouldNotBeFound("organizDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizDescriptionContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizDescription contains DEFAULT_ORGANIZ_DESCRIPTION
        defaultOrganizShouldBeFound("organizDescription.contains=" + DEFAULT_ORGANIZ_DESCRIPTION);

        // Get all the organizList where organizDescription contains UPDATED_ORGANIZ_DESCRIPTION
        defaultOrganizShouldNotBeFound("organizDescription.contains=" + UPDATED_ORGANIZ_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizDescription does not contain DEFAULT_ORGANIZ_DESCRIPTION
        defaultOrganizShouldNotBeFound("organizDescription.doesNotContain=" + DEFAULT_ORGANIZ_DESCRIPTION);

        // Get all the organizList where organizDescription does not contain UPDATED_ORGANIZ_DESCRIPTION
        defaultOrganizShouldBeFound("organizDescription.doesNotContain=" + UPDATED_ORGANIZ_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizTelIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizTel equals to DEFAULT_ORGANIZ_TEL
        defaultOrganizShouldBeFound("organizTel.equals=" + DEFAULT_ORGANIZ_TEL);

        // Get all the organizList where organizTel equals to UPDATED_ORGANIZ_TEL
        defaultOrganizShouldNotBeFound("organizTel.equals=" + UPDATED_ORGANIZ_TEL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizTelIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizTel in DEFAULT_ORGANIZ_TEL or UPDATED_ORGANIZ_TEL
        defaultOrganizShouldBeFound("organizTel.in=" + DEFAULT_ORGANIZ_TEL + "," + UPDATED_ORGANIZ_TEL);

        // Get all the organizList where organizTel equals to UPDATED_ORGANIZ_TEL
        defaultOrganizShouldNotBeFound("organizTel.in=" + UPDATED_ORGANIZ_TEL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizTel is not null
        defaultOrganizShouldBeFound("organizTel.specified=true");

        // Get all the organizList where organizTel is null
        defaultOrganizShouldNotBeFound("organizTel.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizTelContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizTel contains DEFAULT_ORGANIZ_TEL
        defaultOrganizShouldBeFound("organizTel.contains=" + DEFAULT_ORGANIZ_TEL);

        // Get all the organizList where organizTel contains UPDATED_ORGANIZ_TEL
        defaultOrganizShouldNotBeFound("organizTel.contains=" + UPDATED_ORGANIZ_TEL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizTelNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizTel does not contain DEFAULT_ORGANIZ_TEL
        defaultOrganizShouldNotBeFound("organizTel.doesNotContain=" + DEFAULT_ORGANIZ_TEL);

        // Get all the organizList where organizTel does not contain UPDATED_ORGANIZ_TEL
        defaultOrganizShouldBeFound("organizTel.doesNotContain=" + UPDATED_ORGANIZ_TEL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizFax equals to DEFAULT_ORGANIZ_FAX
        defaultOrganizShouldBeFound("organizFax.equals=" + DEFAULT_ORGANIZ_FAX);

        // Get all the organizList where organizFax equals to UPDATED_ORGANIZ_FAX
        defaultOrganizShouldNotBeFound("organizFax.equals=" + UPDATED_ORGANIZ_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizFaxIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizFax in DEFAULT_ORGANIZ_FAX or UPDATED_ORGANIZ_FAX
        defaultOrganizShouldBeFound("organizFax.in=" + DEFAULT_ORGANIZ_FAX + "," + UPDATED_ORGANIZ_FAX);

        // Get all the organizList where organizFax equals to UPDATED_ORGANIZ_FAX
        defaultOrganizShouldNotBeFound("organizFax.in=" + UPDATED_ORGANIZ_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizFax is not null
        defaultOrganizShouldBeFound("organizFax.specified=true");

        // Get all the organizList where organizFax is null
        defaultOrganizShouldNotBeFound("organizFax.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizFaxContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizFax contains DEFAULT_ORGANIZ_FAX
        defaultOrganizShouldBeFound("organizFax.contains=" + DEFAULT_ORGANIZ_FAX);

        // Get all the organizList where organizFax contains UPDATED_ORGANIZ_FAX
        defaultOrganizShouldNotBeFound("organizFax.contains=" + UPDATED_ORGANIZ_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizFaxNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizFax does not contain DEFAULT_ORGANIZ_FAX
        defaultOrganizShouldNotBeFound("organizFax.doesNotContain=" + DEFAULT_ORGANIZ_FAX);

        // Get all the organizList where organizFax does not contain UPDATED_ORGANIZ_FAX
        defaultOrganizShouldBeFound("organizFax.doesNotContain=" + UPDATED_ORGANIZ_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizEmail equals to DEFAULT_ORGANIZ_EMAIL
        defaultOrganizShouldBeFound("organizEmail.equals=" + DEFAULT_ORGANIZ_EMAIL);

        // Get all the organizList where organizEmail equals to UPDATED_ORGANIZ_EMAIL
        defaultOrganizShouldNotBeFound("organizEmail.equals=" + UPDATED_ORGANIZ_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizEmailIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizEmail in DEFAULT_ORGANIZ_EMAIL or UPDATED_ORGANIZ_EMAIL
        defaultOrganizShouldBeFound("organizEmail.in=" + DEFAULT_ORGANIZ_EMAIL + "," + UPDATED_ORGANIZ_EMAIL);

        // Get all the organizList where organizEmail equals to UPDATED_ORGANIZ_EMAIL
        defaultOrganizShouldNotBeFound("organizEmail.in=" + UPDATED_ORGANIZ_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizEmail is not null
        defaultOrganizShouldBeFound("organizEmail.specified=true");

        // Get all the organizList where organizEmail is null
        defaultOrganizShouldNotBeFound("organizEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizEmailContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizEmail contains DEFAULT_ORGANIZ_EMAIL
        defaultOrganizShouldBeFound("organizEmail.contains=" + DEFAULT_ORGANIZ_EMAIL);

        // Get all the organizList where organizEmail contains UPDATED_ORGANIZ_EMAIL
        defaultOrganizShouldNotBeFound("organizEmail.contains=" + UPDATED_ORGANIZ_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizEmailNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizEmail does not contain DEFAULT_ORGANIZ_EMAIL
        defaultOrganizShouldNotBeFound("organizEmail.doesNotContain=" + DEFAULT_ORGANIZ_EMAIL);

        // Get all the organizList where organizEmail does not contain UPDATED_ORGANIZ_EMAIL
        defaultOrganizShouldBeFound("organizEmail.doesNotContain=" + UPDATED_ORGANIZ_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAdresse equals to DEFAULT_ORGANIZ_ADRESSE
        defaultOrganizShouldBeFound("organizAdresse.equals=" + DEFAULT_ORGANIZ_ADRESSE);

        // Get all the organizList where organizAdresse equals to UPDATED_ORGANIZ_ADRESSE
        defaultOrganizShouldNotBeFound("organizAdresse.equals=" + UPDATED_ORGANIZ_ADRESSE);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAdresse in DEFAULT_ORGANIZ_ADRESSE or UPDATED_ORGANIZ_ADRESSE
        defaultOrganizShouldBeFound("organizAdresse.in=" + DEFAULT_ORGANIZ_ADRESSE + "," + UPDATED_ORGANIZ_ADRESSE);

        // Get all the organizList where organizAdresse equals to UPDATED_ORGANIZ_ADRESSE
        defaultOrganizShouldNotBeFound("organizAdresse.in=" + UPDATED_ORGANIZ_ADRESSE);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAdresse is not null
        defaultOrganizShouldBeFound("organizAdresse.specified=true");

        // Get all the organizList where organizAdresse is null
        defaultOrganizShouldNotBeFound("organizAdresse.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAdresseContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAdresse contains DEFAULT_ORGANIZ_ADRESSE
        defaultOrganizShouldBeFound("organizAdresse.contains=" + DEFAULT_ORGANIZ_ADRESSE);

        // Get all the organizList where organizAdresse contains UPDATED_ORGANIZ_ADRESSE
        defaultOrganizShouldNotBeFound("organizAdresse.contains=" + UPDATED_ORGANIZ_ADRESSE);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAdresse does not contain DEFAULT_ORGANIZ_ADRESSE
        defaultOrganizShouldNotBeFound("organizAdresse.doesNotContain=" + DEFAULT_ORGANIZ_ADRESSE);

        // Get all the organizList where organizAdresse does not contain UPDATED_ORGANIZ_ADRESSE
        defaultOrganizShouldBeFound("organizAdresse.doesNotContain=" + UPDATED_ORGANIZ_ADRESSE);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizParams equals to DEFAULT_ORGANIZ_PARAMS
        defaultOrganizShouldBeFound("organizParams.equals=" + DEFAULT_ORGANIZ_PARAMS);

        // Get all the organizList where organizParams equals to UPDATED_ORGANIZ_PARAMS
        defaultOrganizShouldNotBeFound("organizParams.equals=" + UPDATED_ORGANIZ_PARAMS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizParamsIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizParams in DEFAULT_ORGANIZ_PARAMS or UPDATED_ORGANIZ_PARAMS
        defaultOrganizShouldBeFound("organizParams.in=" + DEFAULT_ORGANIZ_PARAMS + "," + UPDATED_ORGANIZ_PARAMS);

        // Get all the organizList where organizParams equals to UPDATED_ORGANIZ_PARAMS
        defaultOrganizShouldNotBeFound("organizParams.in=" + UPDATED_ORGANIZ_PARAMS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizParams is not null
        defaultOrganizShouldBeFound("organizParams.specified=true");

        // Get all the organizList where organizParams is null
        defaultOrganizShouldNotBeFound("organizParams.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizParamsContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizParams contains DEFAULT_ORGANIZ_PARAMS
        defaultOrganizShouldBeFound("organizParams.contains=" + DEFAULT_ORGANIZ_PARAMS);

        // Get all the organizList where organizParams contains UPDATED_ORGANIZ_PARAMS
        defaultOrganizShouldNotBeFound("organizParams.contains=" + UPDATED_ORGANIZ_PARAMS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizParamsNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizParams does not contain DEFAULT_ORGANIZ_PARAMS
        defaultOrganizShouldNotBeFound("organizParams.doesNotContain=" + DEFAULT_ORGANIZ_PARAMS);

        // Get all the organizList where organizParams does not contain UPDATED_ORGANIZ_PARAMS
        defaultOrganizShouldBeFound("organizParams.doesNotContain=" + UPDATED_ORGANIZ_PARAMS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAttributs equals to DEFAULT_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldBeFound("organizAttributs.equals=" + DEFAULT_ORGANIZ_ATTRIBUTS);

        // Get all the organizList where organizAttributs equals to UPDATED_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldNotBeFound("organizAttributs.equals=" + UPDATED_ORGANIZ_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAttributs in DEFAULT_ORGANIZ_ATTRIBUTS or UPDATED_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldBeFound("organizAttributs.in=" + DEFAULT_ORGANIZ_ATTRIBUTS + "," + UPDATED_ORGANIZ_ATTRIBUTS);

        // Get all the organizList where organizAttributs equals to UPDATED_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldNotBeFound("organizAttributs.in=" + UPDATED_ORGANIZ_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAttributs is not null
        defaultOrganizShouldBeFound("organizAttributs.specified=true");

        // Get all the organizList where organizAttributs is null
        defaultOrganizShouldNotBeFound("organizAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAttributsContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAttributs contains DEFAULT_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldBeFound("organizAttributs.contains=" + DEFAULT_ORGANIZ_ATTRIBUTS);

        // Get all the organizList where organizAttributs contains UPDATED_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldNotBeFound("organizAttributs.contains=" + UPDATED_ORGANIZ_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizAttributs does not contain DEFAULT_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldNotBeFound("organizAttributs.doesNotContain=" + DEFAULT_ORGANIZ_ATTRIBUTS);

        // Get all the organizList where organizAttributs does not contain UPDATED_ORGANIZ_ATTRIBUTS
        defaultOrganizShouldBeFound("organizAttributs.doesNotContain=" + UPDATED_ORGANIZ_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizStatIsEqualToSomething() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizStat equals to DEFAULT_ORGANIZ_STAT
        defaultOrganizShouldBeFound("organizStat.equals=" + DEFAULT_ORGANIZ_STAT);

        // Get all the organizList where organizStat equals to UPDATED_ORGANIZ_STAT
        defaultOrganizShouldNotBeFound("organizStat.equals=" + UPDATED_ORGANIZ_STAT);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizStatIsInShouldWork() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizStat in DEFAULT_ORGANIZ_STAT or UPDATED_ORGANIZ_STAT
        defaultOrganizShouldBeFound("organizStat.in=" + DEFAULT_ORGANIZ_STAT + "," + UPDATED_ORGANIZ_STAT);

        // Get all the organizList where organizStat equals to UPDATED_ORGANIZ_STAT
        defaultOrganizShouldNotBeFound("organizStat.in=" + UPDATED_ORGANIZ_STAT);
    }

    @Test
    @Transactional
    void getAllOrganizsByOrganizStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        // Get all the organizList where organizStat is not null
        defaultOrganizShouldBeFound("organizStat.specified=true");

        // Get all the organizList where organizStat is null
        defaultOrganizShouldNotBeFound("organizStat.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizsByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            organizRepository.saveAndFlush(organiz);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        organiz.addPrintingCentre(printingCentre);
        organizRepository.saveAndFlush(organiz);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the organizList where printingCentre equals to printingCentreId
        defaultOrganizShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the organizList where printingCentre equals to (printingCentreId + 1)
        defaultOrganizShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    @Test
    @Transactional
    void getAllOrganizsByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            organizRepository.saveAndFlush(organiz);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        organiz.addAccreditation(accreditation);
        organizRepository.saveAndFlush(organiz);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the organizList where accreditation equals to accreditationId
        defaultOrganizShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the organizList where accreditation equals to (accreditationId + 1)
        defaultOrganizShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllOrganizsByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            organizRepository.saveAndFlush(organiz);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        organiz.setCountry(country);
        organizRepository.saveAndFlush(organiz);
        Long countryId = country.getCountryId();

        // Get all the organizList where country equals to countryId
        defaultOrganizShouldBeFound("countryId.equals=" + countryId);

        // Get all the organizList where country equals to (countryId + 1)
        defaultOrganizShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllOrganizsByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            organizRepository.saveAndFlush(organiz);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        organiz.setCity(city);
        organizRepository.saveAndFlush(organiz);
        Long cityId = city.getCityId();

        // Get all the organizList where city equals to cityId
        defaultOrganizShouldBeFound("cityId.equals=" + cityId);

        // Get all the organizList where city equals to (cityId + 1)
        defaultOrganizShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllOrganizsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            organizRepository.saveAndFlush(organiz);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        organiz.setEvent(event);
        organizRepository.saveAndFlush(organiz);
        Long eventId = event.getEventId();

        // Get all the organizList where event equals to eventId
        defaultOrganizShouldBeFound("eventId.equals=" + eventId);

        // Get all the organizList where event equals to (eventId + 1)
        defaultOrganizShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganizShouldBeFound(String filter) throws Exception {
        restOrganizMockMvc
            .perform(get(ENTITY_API_URL + "?sort=organizId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizId").value(hasItem(organiz.getOrganizId().intValue())))
            .andExpect(jsonPath("$.[*].organizName").value(hasItem(DEFAULT_ORGANIZ_NAME)))
            .andExpect(jsonPath("$.[*].organizDescription").value(hasItem(DEFAULT_ORGANIZ_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].organizLogoContentType").value(hasItem(DEFAULT_ORGANIZ_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].organizLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ORGANIZ_LOGO))))
            .andExpect(jsonPath("$.[*].organizTel").value(hasItem(DEFAULT_ORGANIZ_TEL)))
            .andExpect(jsonPath("$.[*].organizFax").value(hasItem(DEFAULT_ORGANIZ_FAX)))
            .andExpect(jsonPath("$.[*].organizEmail").value(hasItem(DEFAULT_ORGANIZ_EMAIL)))
            .andExpect(jsonPath("$.[*].organizAdresse").value(hasItem(DEFAULT_ORGANIZ_ADRESSE)))
            .andExpect(jsonPath("$.[*].organizParams").value(hasItem(DEFAULT_ORGANIZ_PARAMS)))
            .andExpect(jsonPath("$.[*].organizAttributs").value(hasItem(DEFAULT_ORGANIZ_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].organizStat").value(hasItem(DEFAULT_ORGANIZ_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restOrganizMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=organizId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganizShouldNotBeFound(String filter) throws Exception {
        restOrganizMockMvc
            .perform(get(ENTITY_API_URL + "?sort=organizId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganizMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=organizId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrganiz() throws Exception {
        // Get the organiz
        restOrganizMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganiz() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        int databaseSizeBeforeUpdate = organizRepository.findAll().size();

        // Update the organiz
        Organiz updatedOrganiz = organizRepository.findById(organiz.getOrganizId()).get();
        // Disconnect from session so that the updates on updatedOrganiz are not directly saved in db
        em.detach(updatedOrganiz);
        updatedOrganiz
            .organizName(UPDATED_ORGANIZ_NAME)
            .organizDescription(UPDATED_ORGANIZ_DESCRIPTION)
            .organizLogo(UPDATED_ORGANIZ_LOGO)
            .organizLogoContentType(UPDATED_ORGANIZ_LOGO_CONTENT_TYPE)
            .organizTel(UPDATED_ORGANIZ_TEL)
            .organizFax(UPDATED_ORGANIZ_FAX)
            .organizEmail(UPDATED_ORGANIZ_EMAIL)
            .organizAdresse(UPDATED_ORGANIZ_ADRESSE)
            .organizParams(UPDATED_ORGANIZ_PARAMS)
            .organizAttributs(UPDATED_ORGANIZ_ATTRIBUTS)
            .organizStat(UPDATED_ORGANIZ_STAT);
        OrganizDTO organizDTO = organizMapper.toDto(updatedOrganiz);

        restOrganizMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizDTO.getOrganizId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
        Organiz testOrganiz = organizList.get(organizList.size() - 1);
        assertThat(testOrganiz.getOrganizName()).isEqualTo(UPDATED_ORGANIZ_NAME);
        assertThat(testOrganiz.getOrganizDescription()).isEqualTo(UPDATED_ORGANIZ_DESCRIPTION);
        assertThat(testOrganiz.getOrganizLogo()).isEqualTo(UPDATED_ORGANIZ_LOGO);
        assertThat(testOrganiz.getOrganizLogoContentType()).isEqualTo(UPDATED_ORGANIZ_LOGO_CONTENT_TYPE);
        assertThat(testOrganiz.getOrganizTel()).isEqualTo(UPDATED_ORGANIZ_TEL);
        assertThat(testOrganiz.getOrganizFax()).isEqualTo(UPDATED_ORGANIZ_FAX);
        assertThat(testOrganiz.getOrganizEmail()).isEqualTo(UPDATED_ORGANIZ_EMAIL);
        assertThat(testOrganiz.getOrganizAdresse()).isEqualTo(UPDATED_ORGANIZ_ADRESSE);
        assertThat(testOrganiz.getOrganizParams()).isEqualTo(UPDATED_ORGANIZ_PARAMS);
        assertThat(testOrganiz.getOrganizAttributs()).isEqualTo(UPDATED_ORGANIZ_ATTRIBUTS);
        assertThat(testOrganiz.getOrganizStat()).isEqualTo(UPDATED_ORGANIZ_STAT);
    }

    @Test
    @Transactional
    void putNonExistingOrganiz() throws Exception {
        int databaseSizeBeforeUpdate = organizRepository.findAll().size();
        organiz.setOrganizId(count.incrementAndGet());

        // Create the Organiz
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizDTO.getOrganizId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganiz() throws Exception {
        int databaseSizeBeforeUpdate = organizRepository.findAll().size();
        organiz.setOrganizId(count.incrementAndGet());

        // Create the Organiz
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganiz() throws Exception {
        int databaseSizeBeforeUpdate = organizRepository.findAll().size();
        organiz.setOrganizId(count.incrementAndGet());

        // Create the Organiz
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizWithPatch() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        int databaseSizeBeforeUpdate = organizRepository.findAll().size();

        // Update the organiz using partial update
        Organiz partialUpdatedOrganiz = new Organiz();
        partialUpdatedOrganiz.setOrganizId(organiz.getOrganizId());

        partialUpdatedOrganiz
            .organizName(UPDATED_ORGANIZ_NAME)
            .organizLogo(UPDATED_ORGANIZ_LOGO)
            .organizLogoContentType(UPDATED_ORGANIZ_LOGO_CONTENT_TYPE)
            .organizTel(UPDATED_ORGANIZ_TEL)
            .organizEmail(UPDATED_ORGANIZ_EMAIL)
            .organizAdresse(UPDATED_ORGANIZ_ADRESSE)
            .organizParams(UPDATED_ORGANIZ_PARAMS)
            .organizAttributs(UPDATED_ORGANIZ_ATTRIBUTS);

        restOrganizMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganiz.getOrganizId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganiz))
            )
            .andExpect(status().isOk());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
        Organiz testOrganiz = organizList.get(organizList.size() - 1);
        assertThat(testOrganiz.getOrganizName()).isEqualTo(UPDATED_ORGANIZ_NAME);
        assertThat(testOrganiz.getOrganizDescription()).isEqualTo(DEFAULT_ORGANIZ_DESCRIPTION);
        assertThat(testOrganiz.getOrganizLogo()).isEqualTo(UPDATED_ORGANIZ_LOGO);
        assertThat(testOrganiz.getOrganizLogoContentType()).isEqualTo(UPDATED_ORGANIZ_LOGO_CONTENT_TYPE);
        assertThat(testOrganiz.getOrganizTel()).isEqualTo(UPDATED_ORGANIZ_TEL);
        assertThat(testOrganiz.getOrganizFax()).isEqualTo(DEFAULT_ORGANIZ_FAX);
        assertThat(testOrganiz.getOrganizEmail()).isEqualTo(UPDATED_ORGANIZ_EMAIL);
        assertThat(testOrganiz.getOrganizAdresse()).isEqualTo(UPDATED_ORGANIZ_ADRESSE);
        assertThat(testOrganiz.getOrganizParams()).isEqualTo(UPDATED_ORGANIZ_PARAMS);
        assertThat(testOrganiz.getOrganizAttributs()).isEqualTo(UPDATED_ORGANIZ_ATTRIBUTS);
        assertThat(testOrganiz.getOrganizStat()).isEqualTo(DEFAULT_ORGANIZ_STAT);
    }

    @Test
    @Transactional
    void fullUpdateOrganizWithPatch() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        int databaseSizeBeforeUpdate = organizRepository.findAll().size();

        // Update the organiz using partial update
        Organiz partialUpdatedOrganiz = new Organiz();
        partialUpdatedOrganiz.setOrganizId(organiz.getOrganizId());

        partialUpdatedOrganiz
            .organizName(UPDATED_ORGANIZ_NAME)
            .organizDescription(UPDATED_ORGANIZ_DESCRIPTION)
            .organizLogo(UPDATED_ORGANIZ_LOGO)
            .organizLogoContentType(UPDATED_ORGANIZ_LOGO_CONTENT_TYPE)
            .organizTel(UPDATED_ORGANIZ_TEL)
            .organizFax(UPDATED_ORGANIZ_FAX)
            .organizEmail(UPDATED_ORGANIZ_EMAIL)
            .organizAdresse(UPDATED_ORGANIZ_ADRESSE)
            .organizParams(UPDATED_ORGANIZ_PARAMS)
            .organizAttributs(UPDATED_ORGANIZ_ATTRIBUTS)
            .organizStat(UPDATED_ORGANIZ_STAT);

        restOrganizMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganiz.getOrganizId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganiz))
            )
            .andExpect(status().isOk());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
        Organiz testOrganiz = organizList.get(organizList.size() - 1);
        assertThat(testOrganiz.getOrganizName()).isEqualTo(UPDATED_ORGANIZ_NAME);
        assertThat(testOrganiz.getOrganizDescription()).isEqualTo(UPDATED_ORGANIZ_DESCRIPTION);
        assertThat(testOrganiz.getOrganizLogo()).isEqualTo(UPDATED_ORGANIZ_LOGO);
        assertThat(testOrganiz.getOrganizLogoContentType()).isEqualTo(UPDATED_ORGANIZ_LOGO_CONTENT_TYPE);
        assertThat(testOrganiz.getOrganizTel()).isEqualTo(UPDATED_ORGANIZ_TEL);
        assertThat(testOrganiz.getOrganizFax()).isEqualTo(UPDATED_ORGANIZ_FAX);
        assertThat(testOrganiz.getOrganizEmail()).isEqualTo(UPDATED_ORGANIZ_EMAIL);
        assertThat(testOrganiz.getOrganizAdresse()).isEqualTo(UPDATED_ORGANIZ_ADRESSE);
        assertThat(testOrganiz.getOrganizParams()).isEqualTo(UPDATED_ORGANIZ_PARAMS);
        assertThat(testOrganiz.getOrganizAttributs()).isEqualTo(UPDATED_ORGANIZ_ATTRIBUTS);
        assertThat(testOrganiz.getOrganizStat()).isEqualTo(UPDATED_ORGANIZ_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingOrganiz() throws Exception {
        int databaseSizeBeforeUpdate = organizRepository.findAll().size();
        organiz.setOrganizId(count.incrementAndGet());

        // Create the Organiz
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizDTO.getOrganizId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganiz() throws Exception {
        int databaseSizeBeforeUpdate = organizRepository.findAll().size();
        organiz.setOrganizId(count.incrementAndGet());

        // Create the Organiz
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganiz() throws Exception {
        int databaseSizeBeforeUpdate = organizRepository.findAll().size();
        organiz.setOrganizId(count.incrementAndGet());

        // Create the Organiz
        OrganizDTO organizDTO = organizMapper.toDto(organiz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(organizDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organiz in the database
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganiz() throws Exception {
        // Initialize the database
        organizRepository.saveAndFlush(organiz);

        int databaseSizeBeforeDelete = organizRepository.findAll().size();

        // Delete the organiz
        restOrganizMockMvc
            .perform(delete(ENTITY_API_URL_ID, organiz.getOrganizId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organiz> organizList = organizRepository.findAll();
        assertThat(organizList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
