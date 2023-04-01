package ma.sig.events.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ma.sig.events.IntegrationTest;
import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.Area;
import ma.sig.events.domain.Category;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Fonction;
import ma.sig.events.repository.FonctionRepository;
import ma.sig.events.service.FonctionService;
import ma.sig.events.service.criteria.FonctionCriteria;
import ma.sig.events.service.dto.FonctionDTO;
import ma.sig.events.service.mapper.FonctionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FonctionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FonctionResourceIT {

    private static final String DEFAULT_FONCTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FONCTION_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FONCTION_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FONCTION_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FONCTION_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FONCTION_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FONCTION_STAT = false;
    private static final Boolean UPDATED_FONCTION_STAT = true;

    private static final String ENTITY_API_URL = "/api/fonctions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{fonctionId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FonctionRepository fonctionRepository;

    @Mock
    private FonctionRepository fonctionRepositoryMock;

    @Autowired
    private FonctionMapper fonctionMapper;

    @Mock
    private FonctionService fonctionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFonctionMockMvc;

    private Fonction fonction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fonction createEntity(EntityManager em) {
        Fonction fonction = new Fonction()
            .fonctionName(DEFAULT_FONCTION_NAME)
            .fonctionAbreviation(DEFAULT_FONCTION_ABREVIATION)
            .fonctionColor(DEFAULT_FONCTION_COLOR)
            .fonctionDescription(DEFAULT_FONCTION_DESCRIPTION)
            .fonctionLogo(DEFAULT_FONCTION_LOGO)
            .fonctionLogoContentType(DEFAULT_FONCTION_LOGO_CONTENT_TYPE)
            .fonctionParams(DEFAULT_FONCTION_PARAMS)
            .fonctionAttributs(DEFAULT_FONCTION_ATTRIBUTS)
            .fonctionStat(DEFAULT_FONCTION_STAT);
        return fonction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fonction createUpdatedEntity(EntityManager em) {
        Fonction fonction = new Fonction()
            .fonctionName(UPDATED_FONCTION_NAME)
            .fonctionAbreviation(UPDATED_FONCTION_ABREVIATION)
            .fonctionColor(UPDATED_FONCTION_COLOR)
            .fonctionDescription(UPDATED_FONCTION_DESCRIPTION)
            .fonctionLogo(UPDATED_FONCTION_LOGO)
            .fonctionLogoContentType(UPDATED_FONCTION_LOGO_CONTENT_TYPE)
            .fonctionParams(UPDATED_FONCTION_PARAMS)
            .fonctionAttributs(UPDATED_FONCTION_ATTRIBUTS)
            .fonctionStat(UPDATED_FONCTION_STAT);
        return fonction;
    }

    @BeforeEach
    public void initTest() {
        fonction = createEntity(em);
    }

    @Test
    @Transactional
    void createFonction() throws Exception {
        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();
        // Create the Fonction
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);
        restFonctionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionDTO)))
            .andExpect(status().isCreated());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate + 1);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getFonctionName()).isEqualTo(DEFAULT_FONCTION_NAME);
        assertThat(testFonction.getFonctionAbreviation()).isEqualTo(DEFAULT_FONCTION_ABREVIATION);
        assertThat(testFonction.getFonctionColor()).isEqualTo(DEFAULT_FONCTION_COLOR);
        assertThat(testFonction.getFonctionDescription()).isEqualTo(DEFAULT_FONCTION_DESCRIPTION);
        assertThat(testFonction.getFonctionLogo()).isEqualTo(DEFAULT_FONCTION_LOGO);
        assertThat(testFonction.getFonctionLogoContentType()).isEqualTo(DEFAULT_FONCTION_LOGO_CONTENT_TYPE);
        assertThat(testFonction.getFonctionParams()).isEqualTo(DEFAULT_FONCTION_PARAMS);
        assertThat(testFonction.getFonctionAttributs()).isEqualTo(DEFAULT_FONCTION_ATTRIBUTS);
        assertThat(testFonction.getFonctionStat()).isEqualTo(DEFAULT_FONCTION_STAT);
    }

    @Test
    @Transactional
    void createFonctionWithExistingId() throws Exception {
        // Create the Fonction with an existing ID
        fonction.setFonctionId(1L);
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFonctionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fonctionRepository.findAll().size();
        // set the field null
        fonction.setFonctionName(null);

        // Create the Fonction, which fails.
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        restFonctionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionDTO)))
            .andExpect(status().isBadRequest());

        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFonctions() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList
        restFonctionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=fonctionId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].fonctionId").value(hasItem(fonction.getFonctionId().intValue())))
            .andExpect(jsonPath("$.[*].fonctionName").value(hasItem(DEFAULT_FONCTION_NAME)))
            .andExpect(jsonPath("$.[*].fonctionAbreviation").value(hasItem(DEFAULT_FONCTION_ABREVIATION)))
            .andExpect(jsonPath("$.[*].fonctionColor").value(hasItem(DEFAULT_FONCTION_COLOR)))
            .andExpect(jsonPath("$.[*].fonctionDescription").value(hasItem(DEFAULT_FONCTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fonctionLogoContentType").value(hasItem(DEFAULT_FONCTION_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fonctionLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_FONCTION_LOGO))))
            .andExpect(jsonPath("$.[*].fonctionParams").value(hasItem(DEFAULT_FONCTION_PARAMS)))
            .andExpect(jsonPath("$.[*].fonctionAttributs").value(hasItem(DEFAULT_FONCTION_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].fonctionStat").value(hasItem(DEFAULT_FONCTION_STAT.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFonctionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(fonctionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFonctionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fonctionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFonctionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fonctionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFonctionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fonctionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFonction() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get the fonction
        restFonctionMockMvc
            .perform(get(ENTITY_API_URL_ID, fonction.getFonctionId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.fonctionId").value(fonction.getFonctionId().intValue()))
            .andExpect(jsonPath("$.fonctionName").value(DEFAULT_FONCTION_NAME))
            .andExpect(jsonPath("$.fonctionAbreviation").value(DEFAULT_FONCTION_ABREVIATION))
            .andExpect(jsonPath("$.fonctionColor").value(DEFAULT_FONCTION_COLOR))
            .andExpect(jsonPath("$.fonctionDescription").value(DEFAULT_FONCTION_DESCRIPTION))
            .andExpect(jsonPath("$.fonctionLogoContentType").value(DEFAULT_FONCTION_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.fonctionLogo").value(Base64Utils.encodeToString(DEFAULT_FONCTION_LOGO)))
            .andExpect(jsonPath("$.fonctionParams").value(DEFAULT_FONCTION_PARAMS))
            .andExpect(jsonPath("$.fonctionAttributs").value(DEFAULT_FONCTION_ATTRIBUTS))
            .andExpect(jsonPath("$.fonctionStat").value(DEFAULT_FONCTION_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getFonctionsByIdFiltering() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        Long id = fonction.getFonctionId();

        defaultFonctionShouldBeFound("fonctionId.equals=" + id);
        defaultFonctionShouldNotBeFound("fonctionId.notEquals=" + id);

        defaultFonctionShouldBeFound("fonctionId.greaterThanOrEqual=" + id);
        defaultFonctionShouldNotBeFound("fonctionId.greaterThan=" + id);

        defaultFonctionShouldBeFound("fonctionId.lessThanOrEqual=" + id);
        defaultFonctionShouldNotBeFound("fonctionId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionName equals to DEFAULT_FONCTION_NAME
        defaultFonctionShouldBeFound("fonctionName.equals=" + DEFAULT_FONCTION_NAME);

        // Get all the fonctionList where fonctionName equals to UPDATED_FONCTION_NAME
        defaultFonctionShouldNotBeFound("fonctionName.equals=" + UPDATED_FONCTION_NAME);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionNameIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionName in DEFAULT_FONCTION_NAME or UPDATED_FONCTION_NAME
        defaultFonctionShouldBeFound("fonctionName.in=" + DEFAULT_FONCTION_NAME + "," + UPDATED_FONCTION_NAME);

        // Get all the fonctionList where fonctionName equals to UPDATED_FONCTION_NAME
        defaultFonctionShouldNotBeFound("fonctionName.in=" + UPDATED_FONCTION_NAME);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionName is not null
        defaultFonctionShouldBeFound("fonctionName.specified=true");

        // Get all the fonctionList where fonctionName is null
        defaultFonctionShouldNotBeFound("fonctionName.specified=false");
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionNameContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionName contains DEFAULT_FONCTION_NAME
        defaultFonctionShouldBeFound("fonctionName.contains=" + DEFAULT_FONCTION_NAME);

        // Get all the fonctionList where fonctionName contains UPDATED_FONCTION_NAME
        defaultFonctionShouldNotBeFound("fonctionName.contains=" + UPDATED_FONCTION_NAME);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionNameNotContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionName does not contain DEFAULT_FONCTION_NAME
        defaultFonctionShouldNotBeFound("fonctionName.doesNotContain=" + DEFAULT_FONCTION_NAME);

        // Get all the fonctionList where fonctionName does not contain UPDATED_FONCTION_NAME
        defaultFonctionShouldBeFound("fonctionName.doesNotContain=" + UPDATED_FONCTION_NAME);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAbreviation equals to DEFAULT_FONCTION_ABREVIATION
        defaultFonctionShouldBeFound("fonctionAbreviation.equals=" + DEFAULT_FONCTION_ABREVIATION);

        // Get all the fonctionList where fonctionAbreviation equals to UPDATED_FONCTION_ABREVIATION
        defaultFonctionShouldNotBeFound("fonctionAbreviation.equals=" + UPDATED_FONCTION_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAbreviation in DEFAULT_FONCTION_ABREVIATION or UPDATED_FONCTION_ABREVIATION
        defaultFonctionShouldBeFound("fonctionAbreviation.in=" + DEFAULT_FONCTION_ABREVIATION + "," + UPDATED_FONCTION_ABREVIATION);

        // Get all the fonctionList where fonctionAbreviation equals to UPDATED_FONCTION_ABREVIATION
        defaultFonctionShouldNotBeFound("fonctionAbreviation.in=" + UPDATED_FONCTION_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAbreviation is not null
        defaultFonctionShouldBeFound("fonctionAbreviation.specified=true");

        // Get all the fonctionList where fonctionAbreviation is null
        defaultFonctionShouldNotBeFound("fonctionAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAbreviationContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAbreviation contains DEFAULT_FONCTION_ABREVIATION
        defaultFonctionShouldBeFound("fonctionAbreviation.contains=" + DEFAULT_FONCTION_ABREVIATION);

        // Get all the fonctionList where fonctionAbreviation contains UPDATED_FONCTION_ABREVIATION
        defaultFonctionShouldNotBeFound("fonctionAbreviation.contains=" + UPDATED_FONCTION_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAbreviation does not contain DEFAULT_FONCTION_ABREVIATION
        defaultFonctionShouldNotBeFound("fonctionAbreviation.doesNotContain=" + DEFAULT_FONCTION_ABREVIATION);

        // Get all the fonctionList where fonctionAbreviation does not contain UPDATED_FONCTION_ABREVIATION
        defaultFonctionShouldBeFound("fonctionAbreviation.doesNotContain=" + UPDATED_FONCTION_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionColorIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionColor equals to DEFAULT_FONCTION_COLOR
        defaultFonctionShouldBeFound("fonctionColor.equals=" + DEFAULT_FONCTION_COLOR);

        // Get all the fonctionList where fonctionColor equals to UPDATED_FONCTION_COLOR
        defaultFonctionShouldNotBeFound("fonctionColor.equals=" + UPDATED_FONCTION_COLOR);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionColorIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionColor in DEFAULT_FONCTION_COLOR or UPDATED_FONCTION_COLOR
        defaultFonctionShouldBeFound("fonctionColor.in=" + DEFAULT_FONCTION_COLOR + "," + UPDATED_FONCTION_COLOR);

        // Get all the fonctionList where fonctionColor equals to UPDATED_FONCTION_COLOR
        defaultFonctionShouldNotBeFound("fonctionColor.in=" + UPDATED_FONCTION_COLOR);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionColor is not null
        defaultFonctionShouldBeFound("fonctionColor.specified=true");

        // Get all the fonctionList where fonctionColor is null
        defaultFonctionShouldNotBeFound("fonctionColor.specified=false");
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionColorContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionColor contains DEFAULT_FONCTION_COLOR
        defaultFonctionShouldBeFound("fonctionColor.contains=" + DEFAULT_FONCTION_COLOR);

        // Get all the fonctionList where fonctionColor contains UPDATED_FONCTION_COLOR
        defaultFonctionShouldNotBeFound("fonctionColor.contains=" + UPDATED_FONCTION_COLOR);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionColorNotContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionColor does not contain DEFAULT_FONCTION_COLOR
        defaultFonctionShouldNotBeFound("fonctionColor.doesNotContain=" + DEFAULT_FONCTION_COLOR);

        // Get all the fonctionList where fonctionColor does not contain UPDATED_FONCTION_COLOR
        defaultFonctionShouldBeFound("fonctionColor.doesNotContain=" + UPDATED_FONCTION_COLOR);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionDescription equals to DEFAULT_FONCTION_DESCRIPTION
        defaultFonctionShouldBeFound("fonctionDescription.equals=" + DEFAULT_FONCTION_DESCRIPTION);

        // Get all the fonctionList where fonctionDescription equals to UPDATED_FONCTION_DESCRIPTION
        defaultFonctionShouldNotBeFound("fonctionDescription.equals=" + UPDATED_FONCTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionDescription in DEFAULT_FONCTION_DESCRIPTION or UPDATED_FONCTION_DESCRIPTION
        defaultFonctionShouldBeFound("fonctionDescription.in=" + DEFAULT_FONCTION_DESCRIPTION + "," + UPDATED_FONCTION_DESCRIPTION);

        // Get all the fonctionList where fonctionDescription equals to UPDATED_FONCTION_DESCRIPTION
        defaultFonctionShouldNotBeFound("fonctionDescription.in=" + UPDATED_FONCTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionDescription is not null
        defaultFonctionShouldBeFound("fonctionDescription.specified=true");

        // Get all the fonctionList where fonctionDescription is null
        defaultFonctionShouldNotBeFound("fonctionDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionDescriptionContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionDescription contains DEFAULT_FONCTION_DESCRIPTION
        defaultFonctionShouldBeFound("fonctionDescription.contains=" + DEFAULT_FONCTION_DESCRIPTION);

        // Get all the fonctionList where fonctionDescription contains UPDATED_FONCTION_DESCRIPTION
        defaultFonctionShouldNotBeFound("fonctionDescription.contains=" + UPDATED_FONCTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionDescription does not contain DEFAULT_FONCTION_DESCRIPTION
        defaultFonctionShouldNotBeFound("fonctionDescription.doesNotContain=" + DEFAULT_FONCTION_DESCRIPTION);

        // Get all the fonctionList where fonctionDescription does not contain UPDATED_FONCTION_DESCRIPTION
        defaultFonctionShouldBeFound("fonctionDescription.doesNotContain=" + UPDATED_FONCTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionParams equals to DEFAULT_FONCTION_PARAMS
        defaultFonctionShouldBeFound("fonctionParams.equals=" + DEFAULT_FONCTION_PARAMS);

        // Get all the fonctionList where fonctionParams equals to UPDATED_FONCTION_PARAMS
        defaultFonctionShouldNotBeFound("fonctionParams.equals=" + UPDATED_FONCTION_PARAMS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionParamsIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionParams in DEFAULT_FONCTION_PARAMS or UPDATED_FONCTION_PARAMS
        defaultFonctionShouldBeFound("fonctionParams.in=" + DEFAULT_FONCTION_PARAMS + "," + UPDATED_FONCTION_PARAMS);

        // Get all the fonctionList where fonctionParams equals to UPDATED_FONCTION_PARAMS
        defaultFonctionShouldNotBeFound("fonctionParams.in=" + UPDATED_FONCTION_PARAMS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionParams is not null
        defaultFonctionShouldBeFound("fonctionParams.specified=true");

        // Get all the fonctionList where fonctionParams is null
        defaultFonctionShouldNotBeFound("fonctionParams.specified=false");
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionParamsContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionParams contains DEFAULT_FONCTION_PARAMS
        defaultFonctionShouldBeFound("fonctionParams.contains=" + DEFAULT_FONCTION_PARAMS);

        // Get all the fonctionList where fonctionParams contains UPDATED_FONCTION_PARAMS
        defaultFonctionShouldNotBeFound("fonctionParams.contains=" + UPDATED_FONCTION_PARAMS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionParamsNotContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionParams does not contain DEFAULT_FONCTION_PARAMS
        defaultFonctionShouldNotBeFound("fonctionParams.doesNotContain=" + DEFAULT_FONCTION_PARAMS);

        // Get all the fonctionList where fonctionParams does not contain UPDATED_FONCTION_PARAMS
        defaultFonctionShouldBeFound("fonctionParams.doesNotContain=" + UPDATED_FONCTION_PARAMS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAttributs equals to DEFAULT_FONCTION_ATTRIBUTS
        defaultFonctionShouldBeFound("fonctionAttributs.equals=" + DEFAULT_FONCTION_ATTRIBUTS);

        // Get all the fonctionList where fonctionAttributs equals to UPDATED_FONCTION_ATTRIBUTS
        defaultFonctionShouldNotBeFound("fonctionAttributs.equals=" + UPDATED_FONCTION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAttributs in DEFAULT_FONCTION_ATTRIBUTS or UPDATED_FONCTION_ATTRIBUTS
        defaultFonctionShouldBeFound("fonctionAttributs.in=" + DEFAULT_FONCTION_ATTRIBUTS + "," + UPDATED_FONCTION_ATTRIBUTS);

        // Get all the fonctionList where fonctionAttributs equals to UPDATED_FONCTION_ATTRIBUTS
        defaultFonctionShouldNotBeFound("fonctionAttributs.in=" + UPDATED_FONCTION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAttributs is not null
        defaultFonctionShouldBeFound("fonctionAttributs.specified=true");

        // Get all the fonctionList where fonctionAttributs is null
        defaultFonctionShouldNotBeFound("fonctionAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAttributsContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAttributs contains DEFAULT_FONCTION_ATTRIBUTS
        defaultFonctionShouldBeFound("fonctionAttributs.contains=" + DEFAULT_FONCTION_ATTRIBUTS);

        // Get all the fonctionList where fonctionAttributs contains UPDATED_FONCTION_ATTRIBUTS
        defaultFonctionShouldNotBeFound("fonctionAttributs.contains=" + UPDATED_FONCTION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionAttributs does not contain DEFAULT_FONCTION_ATTRIBUTS
        defaultFonctionShouldNotBeFound("fonctionAttributs.doesNotContain=" + DEFAULT_FONCTION_ATTRIBUTS);

        // Get all the fonctionList where fonctionAttributs does not contain UPDATED_FONCTION_ATTRIBUTS
        defaultFonctionShouldBeFound("fonctionAttributs.doesNotContain=" + UPDATED_FONCTION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionStatIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionStat equals to DEFAULT_FONCTION_STAT
        defaultFonctionShouldBeFound("fonctionStat.equals=" + DEFAULT_FONCTION_STAT);

        // Get all the fonctionList where fonctionStat equals to UPDATED_FONCTION_STAT
        defaultFonctionShouldNotBeFound("fonctionStat.equals=" + UPDATED_FONCTION_STAT);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionStatIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionStat in DEFAULT_FONCTION_STAT or UPDATED_FONCTION_STAT
        defaultFonctionShouldBeFound("fonctionStat.in=" + DEFAULT_FONCTION_STAT + "," + UPDATED_FONCTION_STAT);

        // Get all the fonctionList where fonctionStat equals to UPDATED_FONCTION_STAT
        defaultFonctionShouldNotBeFound("fonctionStat.in=" + UPDATED_FONCTION_STAT);
    }

    @Test
    @Transactional
    void getAllFonctionsByFonctionStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where fonctionStat is not null
        defaultFonctionShouldBeFound("fonctionStat.specified=true");

        // Get all the fonctionList where fonctionStat is null
        defaultFonctionShouldNotBeFound("fonctionStat.specified=false");
    }

    @Test
    @Transactional
    void getAllFonctionsByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            fonctionRepository.saveAndFlush(fonction);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        fonction.addAccreditation(accreditation);
        fonctionRepository.saveAndFlush(fonction);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the fonctionList where accreditation equals to accreditationId
        defaultFonctionShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the fonctionList where accreditation equals to (accreditationId + 1)
        defaultFonctionShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllFonctionsByAreaIsEqualToSomething() throws Exception {
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            fonctionRepository.saveAndFlush(fonction);
            area = AreaResourceIT.createEntity(em);
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        em.persist(area);
        em.flush();
        fonction.addArea(area);
        fonctionRepository.saveAndFlush(fonction);
        Long areaId = area.getAreaId();

        // Get all the fonctionList where area equals to areaId
        defaultFonctionShouldBeFound("areaId.equals=" + areaId);

        // Get all the fonctionList where area equals to (areaId + 1)
        defaultFonctionShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }

    @Test
    @Transactional
    void getAllFonctionsByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            fonctionRepository.saveAndFlush(fonction);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        fonction.setCategory(category);
        fonctionRepository.saveAndFlush(fonction);
        Long categoryId = category.getCategoryId();

        // Get all the fonctionList where category equals to categoryId
        defaultFonctionShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the fonctionList where category equals to (categoryId + 1)
        defaultFonctionShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllFonctionsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            fonctionRepository.saveAndFlush(fonction);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        fonction.setEvent(event);
        fonctionRepository.saveAndFlush(fonction);
        Long eventId = event.getEventId();

        // Get all the fonctionList where event equals to eventId
        defaultFonctionShouldBeFound("eventId.equals=" + eventId);

        // Get all the fonctionList where event equals to (eventId + 1)
        defaultFonctionShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFonctionShouldBeFound(String filter) throws Exception {
        restFonctionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=fonctionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].fonctionId").value(hasItem(fonction.getFonctionId().intValue())))
            .andExpect(jsonPath("$.[*].fonctionName").value(hasItem(DEFAULT_FONCTION_NAME)))
            .andExpect(jsonPath("$.[*].fonctionAbreviation").value(hasItem(DEFAULT_FONCTION_ABREVIATION)))
            .andExpect(jsonPath("$.[*].fonctionColor").value(hasItem(DEFAULT_FONCTION_COLOR)))
            .andExpect(jsonPath("$.[*].fonctionDescription").value(hasItem(DEFAULT_FONCTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fonctionLogoContentType").value(hasItem(DEFAULT_FONCTION_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fonctionLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_FONCTION_LOGO))))
            .andExpect(jsonPath("$.[*].fonctionParams").value(hasItem(DEFAULT_FONCTION_PARAMS)))
            .andExpect(jsonPath("$.[*].fonctionAttributs").value(hasItem(DEFAULT_FONCTION_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].fonctionStat").value(hasItem(DEFAULT_FONCTION_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restFonctionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=fonctionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFonctionShouldNotBeFound(String filter) throws Exception {
        restFonctionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=fonctionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFonctionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=fonctionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFonction() throws Exception {
        // Get the fonction
        restFonctionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFonction() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Update the fonction
        Fonction updatedFonction = fonctionRepository.findById(fonction.getFonctionId()).get();
        // Disconnect from session so that the updates on updatedFonction are not directly saved in db
        em.detach(updatedFonction);
        updatedFonction
            .fonctionName(UPDATED_FONCTION_NAME)
            .fonctionAbreviation(UPDATED_FONCTION_ABREVIATION)
            .fonctionColor(UPDATED_FONCTION_COLOR)
            .fonctionDescription(UPDATED_FONCTION_DESCRIPTION)
            .fonctionLogo(UPDATED_FONCTION_LOGO)
            .fonctionLogoContentType(UPDATED_FONCTION_LOGO_CONTENT_TYPE)
            .fonctionParams(UPDATED_FONCTION_PARAMS)
            .fonctionAttributs(UPDATED_FONCTION_ATTRIBUTS)
            .fonctionStat(UPDATED_FONCTION_STAT);
        FonctionDTO fonctionDTO = fonctionMapper.toDto(updatedFonction);

        restFonctionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fonctionDTO.getFonctionId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fonctionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getFonctionName()).isEqualTo(UPDATED_FONCTION_NAME);
        assertThat(testFonction.getFonctionAbreviation()).isEqualTo(UPDATED_FONCTION_ABREVIATION);
        assertThat(testFonction.getFonctionColor()).isEqualTo(UPDATED_FONCTION_COLOR);
        assertThat(testFonction.getFonctionDescription()).isEqualTo(UPDATED_FONCTION_DESCRIPTION);
        assertThat(testFonction.getFonctionLogo()).isEqualTo(UPDATED_FONCTION_LOGO);
        assertThat(testFonction.getFonctionLogoContentType()).isEqualTo(UPDATED_FONCTION_LOGO_CONTENT_TYPE);
        assertThat(testFonction.getFonctionParams()).isEqualTo(UPDATED_FONCTION_PARAMS);
        assertThat(testFonction.getFonctionAttributs()).isEqualTo(UPDATED_FONCTION_ATTRIBUTS);
        assertThat(testFonction.getFonctionStat()).isEqualTo(UPDATED_FONCTION_STAT);
    }

    @Test
    @Transactional
    void putNonExistingFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();
        fonction.setFonctionId(count.incrementAndGet());

        // Create the Fonction
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fonctionDTO.getFonctionId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fonctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();
        fonction.setFonctionId(count.incrementAndGet());

        // Create the Fonction
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fonctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();
        fonction.setFonctionId(count.incrementAndGet());

        // Create the Fonction
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFonctionWithPatch() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Update the fonction using partial update
        Fonction partialUpdatedFonction = new Fonction();
        partialUpdatedFonction.setFonctionId(fonction.getFonctionId());

        partialUpdatedFonction
            .fonctionColor(UPDATED_FONCTION_COLOR)
            .fonctionDescription(UPDATED_FONCTION_DESCRIPTION)
            .fonctionStat(UPDATED_FONCTION_STAT);

        restFonctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFonction.getFonctionId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFonction))
            )
            .andExpect(status().isOk());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getFonctionName()).isEqualTo(DEFAULT_FONCTION_NAME);
        assertThat(testFonction.getFonctionAbreviation()).isEqualTo(DEFAULT_FONCTION_ABREVIATION);
        assertThat(testFonction.getFonctionColor()).isEqualTo(UPDATED_FONCTION_COLOR);
        assertThat(testFonction.getFonctionDescription()).isEqualTo(UPDATED_FONCTION_DESCRIPTION);
        assertThat(testFonction.getFonctionLogo()).isEqualTo(DEFAULT_FONCTION_LOGO);
        assertThat(testFonction.getFonctionLogoContentType()).isEqualTo(DEFAULT_FONCTION_LOGO_CONTENT_TYPE);
        assertThat(testFonction.getFonctionParams()).isEqualTo(DEFAULT_FONCTION_PARAMS);
        assertThat(testFonction.getFonctionAttributs()).isEqualTo(DEFAULT_FONCTION_ATTRIBUTS);
        assertThat(testFonction.getFonctionStat()).isEqualTo(UPDATED_FONCTION_STAT);
    }

    @Test
    @Transactional
    void fullUpdateFonctionWithPatch() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Update the fonction using partial update
        Fonction partialUpdatedFonction = new Fonction();
        partialUpdatedFonction.setFonctionId(fonction.getFonctionId());

        partialUpdatedFonction
            .fonctionName(UPDATED_FONCTION_NAME)
            .fonctionAbreviation(UPDATED_FONCTION_ABREVIATION)
            .fonctionColor(UPDATED_FONCTION_COLOR)
            .fonctionDescription(UPDATED_FONCTION_DESCRIPTION)
            .fonctionLogo(UPDATED_FONCTION_LOGO)
            .fonctionLogoContentType(UPDATED_FONCTION_LOGO_CONTENT_TYPE)
            .fonctionParams(UPDATED_FONCTION_PARAMS)
            .fonctionAttributs(UPDATED_FONCTION_ATTRIBUTS)
            .fonctionStat(UPDATED_FONCTION_STAT);

        restFonctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFonction.getFonctionId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFonction))
            )
            .andExpect(status().isOk());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getFonctionName()).isEqualTo(UPDATED_FONCTION_NAME);
        assertThat(testFonction.getFonctionAbreviation()).isEqualTo(UPDATED_FONCTION_ABREVIATION);
        assertThat(testFonction.getFonctionColor()).isEqualTo(UPDATED_FONCTION_COLOR);
        assertThat(testFonction.getFonctionDescription()).isEqualTo(UPDATED_FONCTION_DESCRIPTION);
        assertThat(testFonction.getFonctionLogo()).isEqualTo(UPDATED_FONCTION_LOGO);
        assertThat(testFonction.getFonctionLogoContentType()).isEqualTo(UPDATED_FONCTION_LOGO_CONTENT_TYPE);
        assertThat(testFonction.getFonctionParams()).isEqualTo(UPDATED_FONCTION_PARAMS);
        assertThat(testFonction.getFonctionAttributs()).isEqualTo(UPDATED_FONCTION_ATTRIBUTS);
        assertThat(testFonction.getFonctionStat()).isEqualTo(UPDATED_FONCTION_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();
        fonction.setFonctionId(count.incrementAndGet());

        // Create the Fonction
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fonctionDTO.getFonctionId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fonctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();
        fonction.setFonctionId(count.incrementAndGet());

        // Create the Fonction
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fonctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();
        fonction.setFonctionId(count.incrementAndGet());

        // Create the Fonction
        FonctionDTO fonctionDTO = fonctionMapper.toDto(fonction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fonctionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFonction() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        int databaseSizeBeforeDelete = fonctionRepository.findAll().size();

        // Delete the fonction
        restFonctionMockMvc
            .perform(delete(ENTITY_API_URL_ID, fonction.getFonctionId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
