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
import ma.sig.events.domain.Attachement;
import ma.sig.events.domain.AttachementType;
import ma.sig.events.domain.Event;
import ma.sig.events.repository.AttachementRepository;
import ma.sig.events.service.criteria.AttachementCriteria;
import ma.sig.events.service.dto.AttachementDTO;
import ma.sig.events.service.mapper.AttachementMapper;
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
 * Integration tests for the {@link AttachementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttachementResourceIT {

    private static final String DEFAULT_ATTACHEMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACHEMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_PATH = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHEMENT_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHEMENT_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHEMENT_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHEMENT_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHEMENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHEMENT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHEMENT_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHEMENT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHEMENT_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHEMENT_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACHEMENT_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATTACHEMENT_STAT = false;
    private static final Boolean UPDATED_ATTACHEMENT_STAT = true;

    private static final String ENTITY_API_URL = "/api/attachements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{attachementId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttachementRepository attachementRepository;

    @Autowired
    private AttachementMapper attachementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttachementMockMvc;

    private Attachement attachement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachement createEntity(EntityManager em) {
        Attachement attachement = new Attachement()
            .attachementName(DEFAULT_ATTACHEMENT_NAME)
            .attachementPath(DEFAULT_ATTACHEMENT_PATH)
            .attachementBlob(DEFAULT_ATTACHEMENT_BLOB)
            .attachementBlobContentType(DEFAULT_ATTACHEMENT_BLOB_CONTENT_TYPE)
            .attachementDescription(DEFAULT_ATTACHEMENT_DESCRIPTION)
            .attachementPhoto(DEFAULT_ATTACHEMENT_PHOTO)
            .attachementPhotoContentType(DEFAULT_ATTACHEMENT_PHOTO_CONTENT_TYPE)
            .attachementParams(DEFAULT_ATTACHEMENT_PARAMS)
            .attachementAttributs(DEFAULT_ATTACHEMENT_ATTRIBUTS)
            .attachementStat(DEFAULT_ATTACHEMENT_STAT);
        return attachement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachement createUpdatedEntity(EntityManager em) {
        Attachement attachement = new Attachement()
            .attachementName(UPDATED_ATTACHEMENT_NAME)
            .attachementPath(UPDATED_ATTACHEMENT_PATH)
            .attachementBlob(UPDATED_ATTACHEMENT_BLOB)
            .attachementBlobContentType(UPDATED_ATTACHEMENT_BLOB_CONTENT_TYPE)
            .attachementDescription(UPDATED_ATTACHEMENT_DESCRIPTION)
            .attachementPhoto(UPDATED_ATTACHEMENT_PHOTO)
            .attachementPhotoContentType(UPDATED_ATTACHEMENT_PHOTO_CONTENT_TYPE)
            .attachementParams(UPDATED_ATTACHEMENT_PARAMS)
            .attachementAttributs(UPDATED_ATTACHEMENT_ATTRIBUTS)
            .attachementStat(UPDATED_ATTACHEMENT_STAT);
        return attachement;
    }

    @BeforeEach
    public void initTest() {
        attachement = createEntity(em);
    }

    @Test
    @Transactional
    void createAttachement() throws Exception {
        int databaseSizeBeforeCreate = attachementRepository.findAll().size();
        // Create the Attachement
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);
        restAttachementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeCreate + 1);
        Attachement testAttachement = attachementList.get(attachementList.size() - 1);
        assertThat(testAttachement.getAttachementName()).isEqualTo(DEFAULT_ATTACHEMENT_NAME);
        assertThat(testAttachement.getAttachementPath()).isEqualTo(DEFAULT_ATTACHEMENT_PATH);
        assertThat(testAttachement.getAttachementBlob()).isEqualTo(DEFAULT_ATTACHEMENT_BLOB);
        assertThat(testAttachement.getAttachementBlobContentType()).isEqualTo(DEFAULT_ATTACHEMENT_BLOB_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementDescription()).isEqualTo(DEFAULT_ATTACHEMENT_DESCRIPTION);
        assertThat(testAttachement.getAttachementPhoto()).isEqualTo(DEFAULT_ATTACHEMENT_PHOTO);
        assertThat(testAttachement.getAttachementPhotoContentType()).isEqualTo(DEFAULT_ATTACHEMENT_PHOTO_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementParams()).isEqualTo(DEFAULT_ATTACHEMENT_PARAMS);
        assertThat(testAttachement.getAttachementAttributs()).isEqualTo(DEFAULT_ATTACHEMENT_ATTRIBUTS);
        assertThat(testAttachement.getAttachementStat()).isEqualTo(DEFAULT_ATTACHEMENT_STAT);
    }

    @Test
    @Transactional
    void createAttachementWithExistingId() throws Exception {
        // Create the Attachement with an existing ID
        attachement.setAttachementId(1L);
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        int databaseSizeBeforeCreate = attachementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttachementNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachementRepository.findAll().size();
        // set the field null
        attachement.setAttachementName(null);

        // Create the Attachement, which fails.
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        restAttachementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttachements() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList
        restAttachementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=attachementId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].attachementId").value(hasItem(attachement.getAttachementId().intValue())))
            .andExpect(jsonPath("$.[*].attachementName").value(hasItem(DEFAULT_ATTACHEMENT_NAME)))
            .andExpect(jsonPath("$.[*].attachementPath").value(hasItem(DEFAULT_ATTACHEMENT_PATH)))
            .andExpect(jsonPath("$.[*].attachementBlobContentType").value(hasItem(DEFAULT_ATTACHEMENT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachementBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHEMENT_BLOB))))
            .andExpect(jsonPath("$.[*].attachementDescription").value(hasItem(DEFAULT_ATTACHEMENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachementPhotoContentType").value(hasItem(DEFAULT_ATTACHEMENT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachementPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHEMENT_PHOTO))))
            .andExpect(jsonPath("$.[*].attachementParams").value(hasItem(DEFAULT_ATTACHEMENT_PARAMS)))
            .andExpect(jsonPath("$.[*].attachementAttributs").value(hasItem(DEFAULT_ATTACHEMENT_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].attachementStat").value(hasItem(DEFAULT_ATTACHEMENT_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getAttachement() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get the attachement
        restAttachementMockMvc
            .perform(get(ENTITY_API_URL_ID, attachement.getAttachementId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.attachementId").value(attachement.getAttachementId().intValue()))
            .andExpect(jsonPath("$.attachementName").value(DEFAULT_ATTACHEMENT_NAME))
            .andExpect(jsonPath("$.attachementPath").value(DEFAULT_ATTACHEMENT_PATH))
            .andExpect(jsonPath("$.attachementBlobContentType").value(DEFAULT_ATTACHEMENT_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachementBlob").value(Base64Utils.encodeToString(DEFAULT_ATTACHEMENT_BLOB)))
            .andExpect(jsonPath("$.attachementDescription").value(DEFAULT_ATTACHEMENT_DESCRIPTION))
            .andExpect(jsonPath("$.attachementPhotoContentType").value(DEFAULT_ATTACHEMENT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachementPhoto").value(Base64Utils.encodeToString(DEFAULT_ATTACHEMENT_PHOTO)))
            .andExpect(jsonPath("$.attachementParams").value(DEFAULT_ATTACHEMENT_PARAMS))
            .andExpect(jsonPath("$.attachementAttributs").value(DEFAULT_ATTACHEMENT_ATTRIBUTS))
            .andExpect(jsonPath("$.attachementStat").value(DEFAULT_ATTACHEMENT_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getAttachementsByIdFiltering() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        Long id = attachement.getAttachementId();

        defaultAttachementShouldBeFound("attachementId.equals=" + id);
        defaultAttachementShouldNotBeFound("attachementId.notEquals=" + id);

        defaultAttachementShouldBeFound("attachementId.greaterThanOrEqual=" + id);
        defaultAttachementShouldNotBeFound("attachementId.greaterThan=" + id);

        defaultAttachementShouldBeFound("attachementId.lessThanOrEqual=" + id);
        defaultAttachementShouldNotBeFound("attachementId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementNameIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementName equals to DEFAULT_ATTACHEMENT_NAME
        defaultAttachementShouldBeFound("attachementName.equals=" + DEFAULT_ATTACHEMENT_NAME);

        // Get all the attachementList where attachementName equals to UPDATED_ATTACHEMENT_NAME
        defaultAttachementShouldNotBeFound("attachementName.equals=" + UPDATED_ATTACHEMENT_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementNameIsInShouldWork() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementName in DEFAULT_ATTACHEMENT_NAME or UPDATED_ATTACHEMENT_NAME
        defaultAttachementShouldBeFound("attachementName.in=" + DEFAULT_ATTACHEMENT_NAME + "," + UPDATED_ATTACHEMENT_NAME);

        // Get all the attachementList where attachementName equals to UPDATED_ATTACHEMENT_NAME
        defaultAttachementShouldNotBeFound("attachementName.in=" + UPDATED_ATTACHEMENT_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementName is not null
        defaultAttachementShouldBeFound("attachementName.specified=true");

        // Get all the attachementList where attachementName is null
        defaultAttachementShouldNotBeFound("attachementName.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementNameContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementName contains DEFAULT_ATTACHEMENT_NAME
        defaultAttachementShouldBeFound("attachementName.contains=" + DEFAULT_ATTACHEMENT_NAME);

        // Get all the attachementList where attachementName contains UPDATED_ATTACHEMENT_NAME
        defaultAttachementShouldNotBeFound("attachementName.contains=" + UPDATED_ATTACHEMENT_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementNameNotContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementName does not contain DEFAULT_ATTACHEMENT_NAME
        defaultAttachementShouldNotBeFound("attachementName.doesNotContain=" + DEFAULT_ATTACHEMENT_NAME);

        // Get all the attachementList where attachementName does not contain UPDATED_ATTACHEMENT_NAME
        defaultAttachementShouldBeFound("attachementName.doesNotContain=" + UPDATED_ATTACHEMENT_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementPathIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementPath equals to DEFAULT_ATTACHEMENT_PATH
        defaultAttachementShouldBeFound("attachementPath.equals=" + DEFAULT_ATTACHEMENT_PATH);

        // Get all the attachementList where attachementPath equals to UPDATED_ATTACHEMENT_PATH
        defaultAttachementShouldNotBeFound("attachementPath.equals=" + UPDATED_ATTACHEMENT_PATH);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementPathIsInShouldWork() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementPath in DEFAULT_ATTACHEMENT_PATH or UPDATED_ATTACHEMENT_PATH
        defaultAttachementShouldBeFound("attachementPath.in=" + DEFAULT_ATTACHEMENT_PATH + "," + UPDATED_ATTACHEMENT_PATH);

        // Get all the attachementList where attachementPath equals to UPDATED_ATTACHEMENT_PATH
        defaultAttachementShouldNotBeFound("attachementPath.in=" + UPDATED_ATTACHEMENT_PATH);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementPath is not null
        defaultAttachementShouldBeFound("attachementPath.specified=true");

        // Get all the attachementList where attachementPath is null
        defaultAttachementShouldNotBeFound("attachementPath.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementPathContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementPath contains DEFAULT_ATTACHEMENT_PATH
        defaultAttachementShouldBeFound("attachementPath.contains=" + DEFAULT_ATTACHEMENT_PATH);

        // Get all the attachementList where attachementPath contains UPDATED_ATTACHEMENT_PATH
        defaultAttachementShouldNotBeFound("attachementPath.contains=" + UPDATED_ATTACHEMENT_PATH);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementPathNotContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementPath does not contain DEFAULT_ATTACHEMENT_PATH
        defaultAttachementShouldNotBeFound("attachementPath.doesNotContain=" + DEFAULT_ATTACHEMENT_PATH);

        // Get all the attachementList where attachementPath does not contain UPDATED_ATTACHEMENT_PATH
        defaultAttachementShouldBeFound("attachementPath.doesNotContain=" + UPDATED_ATTACHEMENT_PATH);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementDescription equals to DEFAULT_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldBeFound("attachementDescription.equals=" + DEFAULT_ATTACHEMENT_DESCRIPTION);

        // Get all the attachementList where attachementDescription equals to UPDATED_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldNotBeFound("attachementDescription.equals=" + UPDATED_ATTACHEMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementDescription in DEFAULT_ATTACHEMENT_DESCRIPTION or UPDATED_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldBeFound(
            "attachementDescription.in=" + DEFAULT_ATTACHEMENT_DESCRIPTION + "," + UPDATED_ATTACHEMENT_DESCRIPTION
        );

        // Get all the attachementList where attachementDescription equals to UPDATED_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldNotBeFound("attachementDescription.in=" + UPDATED_ATTACHEMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementDescription is not null
        defaultAttachementShouldBeFound("attachementDescription.specified=true");

        // Get all the attachementList where attachementDescription is null
        defaultAttachementShouldNotBeFound("attachementDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementDescriptionContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementDescription contains DEFAULT_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldBeFound("attachementDescription.contains=" + DEFAULT_ATTACHEMENT_DESCRIPTION);

        // Get all the attachementList where attachementDescription contains UPDATED_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldNotBeFound("attachementDescription.contains=" + UPDATED_ATTACHEMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementDescription does not contain DEFAULT_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldNotBeFound("attachementDescription.doesNotContain=" + DEFAULT_ATTACHEMENT_DESCRIPTION);

        // Get all the attachementList where attachementDescription does not contain UPDATED_ATTACHEMENT_DESCRIPTION
        defaultAttachementShouldBeFound("attachementDescription.doesNotContain=" + UPDATED_ATTACHEMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementParams equals to DEFAULT_ATTACHEMENT_PARAMS
        defaultAttachementShouldBeFound("attachementParams.equals=" + DEFAULT_ATTACHEMENT_PARAMS);

        // Get all the attachementList where attachementParams equals to UPDATED_ATTACHEMENT_PARAMS
        defaultAttachementShouldNotBeFound("attachementParams.equals=" + UPDATED_ATTACHEMENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementParamsIsInShouldWork() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementParams in DEFAULT_ATTACHEMENT_PARAMS or UPDATED_ATTACHEMENT_PARAMS
        defaultAttachementShouldBeFound("attachementParams.in=" + DEFAULT_ATTACHEMENT_PARAMS + "," + UPDATED_ATTACHEMENT_PARAMS);

        // Get all the attachementList where attachementParams equals to UPDATED_ATTACHEMENT_PARAMS
        defaultAttachementShouldNotBeFound("attachementParams.in=" + UPDATED_ATTACHEMENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementParams is not null
        defaultAttachementShouldBeFound("attachementParams.specified=true");

        // Get all the attachementList where attachementParams is null
        defaultAttachementShouldNotBeFound("attachementParams.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementParamsContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementParams contains DEFAULT_ATTACHEMENT_PARAMS
        defaultAttachementShouldBeFound("attachementParams.contains=" + DEFAULT_ATTACHEMENT_PARAMS);

        // Get all the attachementList where attachementParams contains UPDATED_ATTACHEMENT_PARAMS
        defaultAttachementShouldNotBeFound("attachementParams.contains=" + UPDATED_ATTACHEMENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementParamsNotContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementParams does not contain DEFAULT_ATTACHEMENT_PARAMS
        defaultAttachementShouldNotBeFound("attachementParams.doesNotContain=" + DEFAULT_ATTACHEMENT_PARAMS);

        // Get all the attachementList where attachementParams does not contain UPDATED_ATTACHEMENT_PARAMS
        defaultAttachementShouldBeFound("attachementParams.doesNotContain=" + UPDATED_ATTACHEMENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementAttributs equals to DEFAULT_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldBeFound("attachementAttributs.equals=" + DEFAULT_ATTACHEMENT_ATTRIBUTS);

        // Get all the attachementList where attachementAttributs equals to UPDATED_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldNotBeFound("attachementAttributs.equals=" + UPDATED_ATTACHEMENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementAttributs in DEFAULT_ATTACHEMENT_ATTRIBUTS or UPDATED_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldBeFound("attachementAttributs.in=" + DEFAULT_ATTACHEMENT_ATTRIBUTS + "," + UPDATED_ATTACHEMENT_ATTRIBUTS);

        // Get all the attachementList where attachementAttributs equals to UPDATED_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldNotBeFound("attachementAttributs.in=" + UPDATED_ATTACHEMENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementAttributs is not null
        defaultAttachementShouldBeFound("attachementAttributs.specified=true");

        // Get all the attachementList where attachementAttributs is null
        defaultAttachementShouldNotBeFound("attachementAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementAttributsContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementAttributs contains DEFAULT_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldBeFound("attachementAttributs.contains=" + DEFAULT_ATTACHEMENT_ATTRIBUTS);

        // Get all the attachementList where attachementAttributs contains UPDATED_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldNotBeFound("attachementAttributs.contains=" + UPDATED_ATTACHEMENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementAttributs does not contain DEFAULT_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldNotBeFound("attachementAttributs.doesNotContain=" + DEFAULT_ATTACHEMENT_ATTRIBUTS);

        // Get all the attachementList where attachementAttributs does not contain UPDATED_ATTACHEMENT_ATTRIBUTS
        defaultAttachementShouldBeFound("attachementAttributs.doesNotContain=" + UPDATED_ATTACHEMENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementStatIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementStat equals to DEFAULT_ATTACHEMENT_STAT
        defaultAttachementShouldBeFound("attachementStat.equals=" + DEFAULT_ATTACHEMENT_STAT);

        // Get all the attachementList where attachementStat equals to UPDATED_ATTACHEMENT_STAT
        defaultAttachementShouldNotBeFound("attachementStat.equals=" + UPDATED_ATTACHEMENT_STAT);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementStatIsInShouldWork() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementStat in DEFAULT_ATTACHEMENT_STAT or UPDATED_ATTACHEMENT_STAT
        defaultAttachementShouldBeFound("attachementStat.in=" + DEFAULT_ATTACHEMENT_STAT + "," + UPDATED_ATTACHEMENT_STAT);

        // Get all the attachementList where attachementStat equals to UPDATED_ATTACHEMENT_STAT
        defaultAttachementShouldNotBeFound("attachementStat.in=" + UPDATED_ATTACHEMENT_STAT);
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        // Get all the attachementList where attachementStat is not null
        defaultAttachementShouldBeFound("attachementStat.specified=true");

        // Get all the attachementList where attachementStat is null
        defaultAttachementShouldNotBeFound("attachementStat.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementsByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            attachementRepository.saveAndFlush(attachement);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        attachement.addAccreditation(accreditation);
        attachementRepository.saveAndFlush(attachement);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the attachementList where accreditation equals to accreditationId
        defaultAttachementShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the attachementList where accreditation equals to (accreditationId + 1)
        defaultAttachementShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllAttachementsByAttachementTypeIsEqualToSomething() throws Exception {
        AttachementType attachementType;
        if (TestUtil.findAll(em, AttachementType.class).isEmpty()) {
            attachementRepository.saveAndFlush(attachement);
            attachementType = AttachementTypeResourceIT.createEntity(em);
        } else {
            attachementType = TestUtil.findAll(em, AttachementType.class).get(0);
        }
        em.persist(attachementType);
        em.flush();
        attachement.setAttachementType(attachementType);
        attachementRepository.saveAndFlush(attachement);
        Long attachementTypeId = attachementType.getAttachementTypeId();

        // Get all the attachementList where attachementType equals to attachementTypeId
        defaultAttachementShouldBeFound("attachementTypeId.equals=" + attachementTypeId);

        // Get all the attachementList where attachementType equals to (attachementTypeId + 1)
        defaultAttachementShouldNotBeFound("attachementTypeId.equals=" + (attachementTypeId + 1));
    }

    @Test
    @Transactional
    void getAllAttachementsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            attachementRepository.saveAndFlush(attachement);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        attachement.setEvent(event);
        attachementRepository.saveAndFlush(attachement);
        Long eventId = event.getEventId();

        // Get all the attachementList where event equals to eventId
        defaultAttachementShouldBeFound("eventId.equals=" + eventId);

        // Get all the attachementList where event equals to (eventId + 1)
        defaultAttachementShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttachementShouldBeFound(String filter) throws Exception {
        restAttachementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=attachementId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].attachementId").value(hasItem(attachement.getAttachementId().intValue())))
            .andExpect(jsonPath("$.[*].attachementName").value(hasItem(DEFAULT_ATTACHEMENT_NAME)))
            .andExpect(jsonPath("$.[*].attachementPath").value(hasItem(DEFAULT_ATTACHEMENT_PATH)))
            .andExpect(jsonPath("$.[*].attachementBlobContentType").value(hasItem(DEFAULT_ATTACHEMENT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachementBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHEMENT_BLOB))))
            .andExpect(jsonPath("$.[*].attachementDescription").value(hasItem(DEFAULT_ATTACHEMENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachementPhotoContentType").value(hasItem(DEFAULT_ATTACHEMENT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachementPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHEMENT_PHOTO))))
            .andExpect(jsonPath("$.[*].attachementParams").value(hasItem(DEFAULT_ATTACHEMENT_PARAMS)))
            .andExpect(jsonPath("$.[*].attachementAttributs").value(hasItem(DEFAULT_ATTACHEMENT_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].attachementStat").value(hasItem(DEFAULT_ATTACHEMENT_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restAttachementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=attachementId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttachementShouldNotBeFound(String filter) throws Exception {
        restAttachementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=attachementId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttachementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=attachementId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAttachement() throws Exception {
        // Get the attachement
        restAttachementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttachement() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();

        // Update the attachement
        Attachement updatedAttachement = attachementRepository.findById(attachement.getAttachementId()).get();
        // Disconnect from session so that the updates on updatedAttachement are not directly saved in db
        em.detach(updatedAttachement);
        updatedAttachement
            .attachementName(UPDATED_ATTACHEMENT_NAME)
            .attachementPath(UPDATED_ATTACHEMENT_PATH)
            .attachementBlob(UPDATED_ATTACHEMENT_BLOB)
            .attachementBlobContentType(UPDATED_ATTACHEMENT_BLOB_CONTENT_TYPE)
            .attachementDescription(UPDATED_ATTACHEMENT_DESCRIPTION)
            .attachementPhoto(UPDATED_ATTACHEMENT_PHOTO)
            .attachementPhotoContentType(UPDATED_ATTACHEMENT_PHOTO_CONTENT_TYPE)
            .attachementParams(UPDATED_ATTACHEMENT_PARAMS)
            .attachementAttributs(UPDATED_ATTACHEMENT_ATTRIBUTS)
            .attachementStat(UPDATED_ATTACHEMENT_STAT);
        AttachementDTO attachementDTO = attachementMapper.toDto(updatedAttachement);

        restAttachementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachementDTO.getAttachementId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
        Attachement testAttachement = attachementList.get(attachementList.size() - 1);
        assertThat(testAttachement.getAttachementName()).isEqualTo(UPDATED_ATTACHEMENT_NAME);
        assertThat(testAttachement.getAttachementPath()).isEqualTo(UPDATED_ATTACHEMENT_PATH);
        assertThat(testAttachement.getAttachementBlob()).isEqualTo(UPDATED_ATTACHEMENT_BLOB);
        assertThat(testAttachement.getAttachementBlobContentType()).isEqualTo(UPDATED_ATTACHEMENT_BLOB_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementDescription()).isEqualTo(UPDATED_ATTACHEMENT_DESCRIPTION);
        assertThat(testAttachement.getAttachementPhoto()).isEqualTo(UPDATED_ATTACHEMENT_PHOTO);
        assertThat(testAttachement.getAttachementPhotoContentType()).isEqualTo(UPDATED_ATTACHEMENT_PHOTO_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementParams()).isEqualTo(UPDATED_ATTACHEMENT_PARAMS);
        assertThat(testAttachement.getAttachementAttributs()).isEqualTo(UPDATED_ATTACHEMENT_ATTRIBUTS);
        assertThat(testAttachement.getAttachementStat()).isEqualTo(UPDATED_ATTACHEMENT_STAT);
    }

    @Test
    @Transactional
    void putNonExistingAttachement() throws Exception {
        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();
        attachement.setAttachementId(count.incrementAndGet());

        // Create the Attachement
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachementDTO.getAttachementId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttachement() throws Exception {
        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();
        attachement.setAttachementId(count.incrementAndGet());

        // Create the Attachement
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttachement() throws Exception {
        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();
        attachement.setAttachementId(count.incrementAndGet());

        // Create the Attachement
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttachementWithPatch() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();

        // Update the attachement using partial update
        Attachement partialUpdatedAttachement = new Attachement();
        partialUpdatedAttachement.setAttachementId(attachement.getAttachementId());

        partialUpdatedAttachement
            .attachementName(UPDATED_ATTACHEMENT_NAME)
            .attachementPath(UPDATED_ATTACHEMENT_PATH)
            .attachementAttributs(UPDATED_ATTACHEMENT_ATTRIBUTS)
            .attachementStat(UPDATED_ATTACHEMENT_STAT);

        restAttachementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachement.getAttachementId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachement))
            )
            .andExpect(status().isOk());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
        Attachement testAttachement = attachementList.get(attachementList.size() - 1);
        assertThat(testAttachement.getAttachementName()).isEqualTo(UPDATED_ATTACHEMENT_NAME);
        assertThat(testAttachement.getAttachementPath()).isEqualTo(UPDATED_ATTACHEMENT_PATH);
        assertThat(testAttachement.getAttachementBlob()).isEqualTo(DEFAULT_ATTACHEMENT_BLOB);
        assertThat(testAttachement.getAttachementBlobContentType()).isEqualTo(DEFAULT_ATTACHEMENT_BLOB_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementDescription()).isEqualTo(DEFAULT_ATTACHEMENT_DESCRIPTION);
        assertThat(testAttachement.getAttachementPhoto()).isEqualTo(DEFAULT_ATTACHEMENT_PHOTO);
        assertThat(testAttachement.getAttachementPhotoContentType()).isEqualTo(DEFAULT_ATTACHEMENT_PHOTO_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementParams()).isEqualTo(DEFAULT_ATTACHEMENT_PARAMS);
        assertThat(testAttachement.getAttachementAttributs()).isEqualTo(UPDATED_ATTACHEMENT_ATTRIBUTS);
        assertThat(testAttachement.getAttachementStat()).isEqualTo(UPDATED_ATTACHEMENT_STAT);
    }

    @Test
    @Transactional
    void fullUpdateAttachementWithPatch() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();

        // Update the attachement using partial update
        Attachement partialUpdatedAttachement = new Attachement();
        partialUpdatedAttachement.setAttachementId(attachement.getAttachementId());

        partialUpdatedAttachement
            .attachementName(UPDATED_ATTACHEMENT_NAME)
            .attachementPath(UPDATED_ATTACHEMENT_PATH)
            .attachementBlob(UPDATED_ATTACHEMENT_BLOB)
            .attachementBlobContentType(UPDATED_ATTACHEMENT_BLOB_CONTENT_TYPE)
            .attachementDescription(UPDATED_ATTACHEMENT_DESCRIPTION)
            .attachementPhoto(UPDATED_ATTACHEMENT_PHOTO)
            .attachementPhotoContentType(UPDATED_ATTACHEMENT_PHOTO_CONTENT_TYPE)
            .attachementParams(UPDATED_ATTACHEMENT_PARAMS)
            .attachementAttributs(UPDATED_ATTACHEMENT_ATTRIBUTS)
            .attachementStat(UPDATED_ATTACHEMENT_STAT);

        restAttachementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachement.getAttachementId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachement))
            )
            .andExpect(status().isOk());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
        Attachement testAttachement = attachementList.get(attachementList.size() - 1);
        assertThat(testAttachement.getAttachementName()).isEqualTo(UPDATED_ATTACHEMENT_NAME);
        assertThat(testAttachement.getAttachementPath()).isEqualTo(UPDATED_ATTACHEMENT_PATH);
        assertThat(testAttachement.getAttachementBlob()).isEqualTo(UPDATED_ATTACHEMENT_BLOB);
        assertThat(testAttachement.getAttachementBlobContentType()).isEqualTo(UPDATED_ATTACHEMENT_BLOB_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementDescription()).isEqualTo(UPDATED_ATTACHEMENT_DESCRIPTION);
        assertThat(testAttachement.getAttachementPhoto()).isEqualTo(UPDATED_ATTACHEMENT_PHOTO);
        assertThat(testAttachement.getAttachementPhotoContentType()).isEqualTo(UPDATED_ATTACHEMENT_PHOTO_CONTENT_TYPE);
        assertThat(testAttachement.getAttachementParams()).isEqualTo(UPDATED_ATTACHEMENT_PARAMS);
        assertThat(testAttachement.getAttachementAttributs()).isEqualTo(UPDATED_ATTACHEMENT_ATTRIBUTS);
        assertThat(testAttachement.getAttachementStat()).isEqualTo(UPDATED_ATTACHEMENT_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingAttachement() throws Exception {
        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();
        attachement.setAttachementId(count.incrementAndGet());

        // Create the Attachement
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attachementDTO.getAttachementId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttachement() throws Exception {
        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();
        attachement.setAttachementId(count.incrementAndGet());

        // Create the Attachement
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttachement() throws Exception {
        int databaseSizeBeforeUpdate = attachementRepository.findAll().size();
        attachement.setAttachementId(count.incrementAndGet());

        // Create the Attachement
        AttachementDTO attachementDTO = attachementMapper.toDto(attachement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attachementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attachement in the database
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttachement() throws Exception {
        // Initialize the database
        attachementRepository.saveAndFlush(attachement);

        int databaseSizeBeforeDelete = attachementRepository.findAll().size();

        // Delete the attachement
        restAttachementMockMvc
            .perform(delete(ENTITY_API_URL_ID, attachement.getAttachementId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attachement> attachementList = attachementRepository.findAll();
        assertThat(attachementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
