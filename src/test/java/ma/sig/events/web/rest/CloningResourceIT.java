package ma.sig.events.web.rest;

import static ma.sig.events.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ma.sig.events.IntegrationTest;
import ma.sig.events.domain.Cloning;
import ma.sig.events.repository.CloningRepository;
import ma.sig.events.service.criteria.CloningCriteria;
import ma.sig.events.service.dto.CloningDTO;
import ma.sig.events.service.mapper.CloningMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CloningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CloningResourceIT {

    private static final String DEFAULT_CLONING_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CLONING_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CLONING_OLD_EVENT_ID = 1L;
    private static final Long UPDATED_CLONING_OLD_EVENT_ID = 2L;
    private static final Long SMALLER_CLONING_OLD_EVENT_ID = 1L - 1L;

    private static final Long DEFAULT_CLONING_NEW_EVENT_ID = 1L;
    private static final Long UPDATED_CLONING_NEW_EVENT_ID = 2L;
    private static final Long SMALLER_CLONING_NEW_EVENT_ID = 1L - 1L;

    private static final Long DEFAULT_CLONING_USER_ID = 1L;
    private static final Long UPDATED_CLONING_USER_ID = 2L;
    private static final Long SMALLER_CLONING_USER_ID = 1L - 1L;

    private static final ZonedDateTime DEFAULT_CLONING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CLONING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CLONING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_CLONED_ENTITYS = "AAAAAAAAAA";
    private static final String UPDATED_CLONED_ENTITYS = "BBBBBBBBBB";

    private static final String DEFAULT_CLONED_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CLONED_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CLONED_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CLONED_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CLONED_STAT = false;
    private static final Boolean UPDATED_CLONED_STAT = true;

    private static final String ENTITY_API_URL = "/api/clonings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{cloningId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CloningRepository cloningRepository;

    @Autowired
    private CloningMapper cloningMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCloningMockMvc;

    private Cloning cloning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cloning createEntity(EntityManager em) {
        Cloning cloning = new Cloning()
            .cloningDescription(DEFAULT_CLONING_DESCRIPTION)
            .cloningOldEventId(DEFAULT_CLONING_OLD_EVENT_ID)
            .cloningNewEventId(DEFAULT_CLONING_NEW_EVENT_ID)
            .cloningUserId(DEFAULT_CLONING_USER_ID)
            .cloningDate(DEFAULT_CLONING_DATE)
            .clonedEntitys(DEFAULT_CLONED_ENTITYS)
            .clonedParams(DEFAULT_CLONED_PARAMS)
            .clonedAttributs(DEFAULT_CLONED_ATTRIBUTS)
            .clonedStat(DEFAULT_CLONED_STAT);
        return cloning;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cloning createUpdatedEntity(EntityManager em) {
        Cloning cloning = new Cloning()
            .cloningDescription(UPDATED_CLONING_DESCRIPTION)
            .cloningOldEventId(UPDATED_CLONING_OLD_EVENT_ID)
            .cloningNewEventId(UPDATED_CLONING_NEW_EVENT_ID)
            .cloningUserId(UPDATED_CLONING_USER_ID)
            .cloningDate(UPDATED_CLONING_DATE)
            .clonedEntitys(UPDATED_CLONED_ENTITYS)
            .clonedParams(UPDATED_CLONED_PARAMS)
            .clonedAttributs(UPDATED_CLONED_ATTRIBUTS)
            .clonedStat(UPDATED_CLONED_STAT);
        return cloning;
    }

    @BeforeEach
    public void initTest() {
        cloning = createEntity(em);
    }

    @Test
    @Transactional
    void createCloning() throws Exception {
        int databaseSizeBeforeCreate = cloningRepository.findAll().size();
        // Create the Cloning
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);
        restCloningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cloningDTO)))
            .andExpect(status().isCreated());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeCreate + 1);
        Cloning testCloning = cloningList.get(cloningList.size() - 1);
        assertThat(testCloning.getCloningDescription()).isEqualTo(DEFAULT_CLONING_DESCRIPTION);
        assertThat(testCloning.getCloningOldEventId()).isEqualTo(DEFAULT_CLONING_OLD_EVENT_ID);
        assertThat(testCloning.getCloningNewEventId()).isEqualTo(DEFAULT_CLONING_NEW_EVENT_ID);
        assertThat(testCloning.getCloningUserId()).isEqualTo(DEFAULT_CLONING_USER_ID);
        assertThat(testCloning.getCloningDate()).isEqualTo(DEFAULT_CLONING_DATE);
        assertThat(testCloning.getClonedEntitys()).isEqualTo(DEFAULT_CLONED_ENTITYS);
        assertThat(testCloning.getClonedParams()).isEqualTo(DEFAULT_CLONED_PARAMS);
        assertThat(testCloning.getClonedAttributs()).isEqualTo(DEFAULT_CLONED_ATTRIBUTS);
        assertThat(testCloning.getClonedStat()).isEqualTo(DEFAULT_CLONED_STAT);
    }

    @Test
    @Transactional
    void createCloningWithExistingId() throws Exception {
        // Create the Cloning with an existing ID
        cloning.setCloningId(1L);
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);

        int databaseSizeBeforeCreate = cloningRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCloningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cloningDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClonings() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList
        restCloningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=cloningId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].cloningId").value(hasItem(cloning.getCloningId().intValue())))
            .andExpect(jsonPath("$.[*].cloningDescription").value(hasItem(DEFAULT_CLONING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cloningOldEventId").value(hasItem(DEFAULT_CLONING_OLD_EVENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].cloningNewEventId").value(hasItem(DEFAULT_CLONING_NEW_EVENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].cloningUserId").value(hasItem(DEFAULT_CLONING_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].cloningDate").value(hasItem(sameInstant(DEFAULT_CLONING_DATE))))
            .andExpect(jsonPath("$.[*].clonedEntitys").value(hasItem(DEFAULT_CLONED_ENTITYS)))
            .andExpect(jsonPath("$.[*].clonedParams").value(hasItem(DEFAULT_CLONED_PARAMS)))
            .andExpect(jsonPath("$.[*].clonedAttributs").value(hasItem(DEFAULT_CLONED_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].clonedStat").value(hasItem(DEFAULT_CLONED_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getCloning() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get the cloning
        restCloningMockMvc
            .perform(get(ENTITY_API_URL_ID, cloning.getCloningId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.cloningId").value(cloning.getCloningId().intValue()))
            .andExpect(jsonPath("$.cloningDescription").value(DEFAULT_CLONING_DESCRIPTION))
            .andExpect(jsonPath("$.cloningOldEventId").value(DEFAULT_CLONING_OLD_EVENT_ID.intValue()))
            .andExpect(jsonPath("$.cloningNewEventId").value(DEFAULT_CLONING_NEW_EVENT_ID.intValue()))
            .andExpect(jsonPath("$.cloningUserId").value(DEFAULT_CLONING_USER_ID.intValue()))
            .andExpect(jsonPath("$.cloningDate").value(sameInstant(DEFAULT_CLONING_DATE)))
            .andExpect(jsonPath("$.clonedEntitys").value(DEFAULT_CLONED_ENTITYS))
            .andExpect(jsonPath("$.clonedParams").value(DEFAULT_CLONED_PARAMS))
            .andExpect(jsonPath("$.clonedAttributs").value(DEFAULT_CLONED_ATTRIBUTS))
            .andExpect(jsonPath("$.clonedStat").value(DEFAULT_CLONED_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCloningsByIdFiltering() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        Long id = cloning.getCloningId();

        defaultCloningShouldBeFound("cloningId.equals=" + id);
        defaultCloningShouldNotBeFound("cloningId.notEquals=" + id);

        defaultCloningShouldBeFound("cloningId.greaterThanOrEqual=" + id);
        defaultCloningShouldNotBeFound("cloningId.greaterThan=" + id);

        defaultCloningShouldBeFound("cloningId.lessThanOrEqual=" + id);
        defaultCloningShouldNotBeFound("cloningId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDescription equals to DEFAULT_CLONING_DESCRIPTION
        defaultCloningShouldBeFound("cloningDescription.equals=" + DEFAULT_CLONING_DESCRIPTION);

        // Get all the cloningList where cloningDescription equals to UPDATED_CLONING_DESCRIPTION
        defaultCloningShouldNotBeFound("cloningDescription.equals=" + UPDATED_CLONING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDescription in DEFAULT_CLONING_DESCRIPTION or UPDATED_CLONING_DESCRIPTION
        defaultCloningShouldBeFound("cloningDescription.in=" + DEFAULT_CLONING_DESCRIPTION + "," + UPDATED_CLONING_DESCRIPTION);

        // Get all the cloningList where cloningDescription equals to UPDATED_CLONING_DESCRIPTION
        defaultCloningShouldNotBeFound("cloningDescription.in=" + UPDATED_CLONING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDescription is not null
        defaultCloningShouldBeFound("cloningDescription.specified=true");

        // Get all the cloningList where cloningDescription is null
        defaultCloningShouldNotBeFound("cloningDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDescription contains DEFAULT_CLONING_DESCRIPTION
        defaultCloningShouldBeFound("cloningDescription.contains=" + DEFAULT_CLONING_DESCRIPTION);

        // Get all the cloningList where cloningDescription contains UPDATED_CLONING_DESCRIPTION
        defaultCloningShouldNotBeFound("cloningDescription.contains=" + UPDATED_CLONING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDescription does not contain DEFAULT_CLONING_DESCRIPTION
        defaultCloningShouldNotBeFound("cloningDescription.doesNotContain=" + DEFAULT_CLONING_DESCRIPTION);

        // Get all the cloningList where cloningDescription does not contain UPDATED_CLONING_DESCRIPTION
        defaultCloningShouldBeFound("cloningDescription.doesNotContain=" + UPDATED_CLONING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningOldEventIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningOldEventId equals to DEFAULT_CLONING_OLD_EVENT_ID
        defaultCloningShouldBeFound("cloningOldEventId.equals=" + DEFAULT_CLONING_OLD_EVENT_ID);

        // Get all the cloningList where cloningOldEventId equals to UPDATED_CLONING_OLD_EVENT_ID
        defaultCloningShouldNotBeFound("cloningOldEventId.equals=" + UPDATED_CLONING_OLD_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningOldEventIdIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningOldEventId in DEFAULT_CLONING_OLD_EVENT_ID or UPDATED_CLONING_OLD_EVENT_ID
        defaultCloningShouldBeFound("cloningOldEventId.in=" + DEFAULT_CLONING_OLD_EVENT_ID + "," + UPDATED_CLONING_OLD_EVENT_ID);

        // Get all the cloningList where cloningOldEventId equals to UPDATED_CLONING_OLD_EVENT_ID
        defaultCloningShouldNotBeFound("cloningOldEventId.in=" + UPDATED_CLONING_OLD_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningOldEventIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningOldEventId is not null
        defaultCloningShouldBeFound("cloningOldEventId.specified=true");

        // Get all the cloningList where cloningOldEventId is null
        defaultCloningShouldNotBeFound("cloningOldEventId.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByCloningOldEventIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningOldEventId is greater than or equal to DEFAULT_CLONING_OLD_EVENT_ID
        defaultCloningShouldBeFound("cloningOldEventId.greaterThanOrEqual=" + DEFAULT_CLONING_OLD_EVENT_ID);

        // Get all the cloningList where cloningOldEventId is greater than or equal to UPDATED_CLONING_OLD_EVENT_ID
        defaultCloningShouldNotBeFound("cloningOldEventId.greaterThanOrEqual=" + UPDATED_CLONING_OLD_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningOldEventIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningOldEventId is less than or equal to DEFAULT_CLONING_OLD_EVENT_ID
        defaultCloningShouldBeFound("cloningOldEventId.lessThanOrEqual=" + DEFAULT_CLONING_OLD_EVENT_ID);

        // Get all the cloningList where cloningOldEventId is less than or equal to SMALLER_CLONING_OLD_EVENT_ID
        defaultCloningShouldNotBeFound("cloningOldEventId.lessThanOrEqual=" + SMALLER_CLONING_OLD_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningOldEventIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningOldEventId is less than DEFAULT_CLONING_OLD_EVENT_ID
        defaultCloningShouldNotBeFound("cloningOldEventId.lessThan=" + DEFAULT_CLONING_OLD_EVENT_ID);

        // Get all the cloningList where cloningOldEventId is less than UPDATED_CLONING_OLD_EVENT_ID
        defaultCloningShouldBeFound("cloningOldEventId.lessThan=" + UPDATED_CLONING_OLD_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningOldEventIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningOldEventId is greater than DEFAULT_CLONING_OLD_EVENT_ID
        defaultCloningShouldNotBeFound("cloningOldEventId.greaterThan=" + DEFAULT_CLONING_OLD_EVENT_ID);

        // Get all the cloningList where cloningOldEventId is greater than SMALLER_CLONING_OLD_EVENT_ID
        defaultCloningShouldBeFound("cloningOldEventId.greaterThan=" + SMALLER_CLONING_OLD_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningNewEventIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningNewEventId equals to DEFAULT_CLONING_NEW_EVENT_ID
        defaultCloningShouldBeFound("cloningNewEventId.equals=" + DEFAULT_CLONING_NEW_EVENT_ID);

        // Get all the cloningList where cloningNewEventId equals to UPDATED_CLONING_NEW_EVENT_ID
        defaultCloningShouldNotBeFound("cloningNewEventId.equals=" + UPDATED_CLONING_NEW_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningNewEventIdIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningNewEventId in DEFAULT_CLONING_NEW_EVENT_ID or UPDATED_CLONING_NEW_EVENT_ID
        defaultCloningShouldBeFound("cloningNewEventId.in=" + DEFAULT_CLONING_NEW_EVENT_ID + "," + UPDATED_CLONING_NEW_EVENT_ID);

        // Get all the cloningList where cloningNewEventId equals to UPDATED_CLONING_NEW_EVENT_ID
        defaultCloningShouldNotBeFound("cloningNewEventId.in=" + UPDATED_CLONING_NEW_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningNewEventIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningNewEventId is not null
        defaultCloningShouldBeFound("cloningNewEventId.specified=true");

        // Get all the cloningList where cloningNewEventId is null
        defaultCloningShouldNotBeFound("cloningNewEventId.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByCloningNewEventIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningNewEventId is greater than or equal to DEFAULT_CLONING_NEW_EVENT_ID
        defaultCloningShouldBeFound("cloningNewEventId.greaterThanOrEqual=" + DEFAULT_CLONING_NEW_EVENT_ID);

        // Get all the cloningList where cloningNewEventId is greater than or equal to UPDATED_CLONING_NEW_EVENT_ID
        defaultCloningShouldNotBeFound("cloningNewEventId.greaterThanOrEqual=" + UPDATED_CLONING_NEW_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningNewEventIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningNewEventId is less than or equal to DEFAULT_CLONING_NEW_EVENT_ID
        defaultCloningShouldBeFound("cloningNewEventId.lessThanOrEqual=" + DEFAULT_CLONING_NEW_EVENT_ID);

        // Get all the cloningList where cloningNewEventId is less than or equal to SMALLER_CLONING_NEW_EVENT_ID
        defaultCloningShouldNotBeFound("cloningNewEventId.lessThanOrEqual=" + SMALLER_CLONING_NEW_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningNewEventIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningNewEventId is less than DEFAULT_CLONING_NEW_EVENT_ID
        defaultCloningShouldNotBeFound("cloningNewEventId.lessThan=" + DEFAULT_CLONING_NEW_EVENT_ID);

        // Get all the cloningList where cloningNewEventId is less than UPDATED_CLONING_NEW_EVENT_ID
        defaultCloningShouldBeFound("cloningNewEventId.lessThan=" + UPDATED_CLONING_NEW_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningNewEventIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningNewEventId is greater than DEFAULT_CLONING_NEW_EVENT_ID
        defaultCloningShouldNotBeFound("cloningNewEventId.greaterThan=" + DEFAULT_CLONING_NEW_EVENT_ID);

        // Get all the cloningList where cloningNewEventId is greater than SMALLER_CLONING_NEW_EVENT_ID
        defaultCloningShouldBeFound("cloningNewEventId.greaterThan=" + SMALLER_CLONING_NEW_EVENT_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningUserId equals to DEFAULT_CLONING_USER_ID
        defaultCloningShouldBeFound("cloningUserId.equals=" + DEFAULT_CLONING_USER_ID);

        // Get all the cloningList where cloningUserId equals to UPDATED_CLONING_USER_ID
        defaultCloningShouldNotBeFound("cloningUserId.equals=" + UPDATED_CLONING_USER_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningUserId in DEFAULT_CLONING_USER_ID or UPDATED_CLONING_USER_ID
        defaultCloningShouldBeFound("cloningUserId.in=" + DEFAULT_CLONING_USER_ID + "," + UPDATED_CLONING_USER_ID);

        // Get all the cloningList where cloningUserId equals to UPDATED_CLONING_USER_ID
        defaultCloningShouldNotBeFound("cloningUserId.in=" + UPDATED_CLONING_USER_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningUserId is not null
        defaultCloningShouldBeFound("cloningUserId.specified=true");

        // Get all the cloningList where cloningUserId is null
        defaultCloningShouldNotBeFound("cloningUserId.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByCloningUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningUserId is greater than or equal to DEFAULT_CLONING_USER_ID
        defaultCloningShouldBeFound("cloningUserId.greaterThanOrEqual=" + DEFAULT_CLONING_USER_ID);

        // Get all the cloningList where cloningUserId is greater than or equal to UPDATED_CLONING_USER_ID
        defaultCloningShouldNotBeFound("cloningUserId.greaterThanOrEqual=" + UPDATED_CLONING_USER_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningUserId is less than or equal to DEFAULT_CLONING_USER_ID
        defaultCloningShouldBeFound("cloningUserId.lessThanOrEqual=" + DEFAULT_CLONING_USER_ID);

        // Get all the cloningList where cloningUserId is less than or equal to SMALLER_CLONING_USER_ID
        defaultCloningShouldNotBeFound("cloningUserId.lessThanOrEqual=" + SMALLER_CLONING_USER_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningUserId is less than DEFAULT_CLONING_USER_ID
        defaultCloningShouldNotBeFound("cloningUserId.lessThan=" + DEFAULT_CLONING_USER_ID);

        // Get all the cloningList where cloningUserId is less than UPDATED_CLONING_USER_ID
        defaultCloningShouldBeFound("cloningUserId.lessThan=" + UPDATED_CLONING_USER_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningUserId is greater than DEFAULT_CLONING_USER_ID
        defaultCloningShouldNotBeFound("cloningUserId.greaterThan=" + DEFAULT_CLONING_USER_ID);

        // Get all the cloningList where cloningUserId is greater than SMALLER_CLONING_USER_ID
        defaultCloningShouldBeFound("cloningUserId.greaterThan=" + SMALLER_CLONING_USER_ID);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDate equals to DEFAULT_CLONING_DATE
        defaultCloningShouldBeFound("cloningDate.equals=" + DEFAULT_CLONING_DATE);

        // Get all the cloningList where cloningDate equals to UPDATED_CLONING_DATE
        defaultCloningShouldNotBeFound("cloningDate.equals=" + UPDATED_CLONING_DATE);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDateIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDate in DEFAULT_CLONING_DATE or UPDATED_CLONING_DATE
        defaultCloningShouldBeFound("cloningDate.in=" + DEFAULT_CLONING_DATE + "," + UPDATED_CLONING_DATE);

        // Get all the cloningList where cloningDate equals to UPDATED_CLONING_DATE
        defaultCloningShouldNotBeFound("cloningDate.in=" + UPDATED_CLONING_DATE);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDate is not null
        defaultCloningShouldBeFound("cloningDate.specified=true");

        // Get all the cloningList where cloningDate is null
        defaultCloningShouldNotBeFound("cloningDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDate is greater than or equal to DEFAULT_CLONING_DATE
        defaultCloningShouldBeFound("cloningDate.greaterThanOrEqual=" + DEFAULT_CLONING_DATE);

        // Get all the cloningList where cloningDate is greater than or equal to UPDATED_CLONING_DATE
        defaultCloningShouldNotBeFound("cloningDate.greaterThanOrEqual=" + UPDATED_CLONING_DATE);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDate is less than or equal to DEFAULT_CLONING_DATE
        defaultCloningShouldBeFound("cloningDate.lessThanOrEqual=" + DEFAULT_CLONING_DATE);

        // Get all the cloningList where cloningDate is less than or equal to SMALLER_CLONING_DATE
        defaultCloningShouldNotBeFound("cloningDate.lessThanOrEqual=" + SMALLER_CLONING_DATE);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDate is less than DEFAULT_CLONING_DATE
        defaultCloningShouldNotBeFound("cloningDate.lessThan=" + DEFAULT_CLONING_DATE);

        // Get all the cloningList where cloningDate is less than UPDATED_CLONING_DATE
        defaultCloningShouldBeFound("cloningDate.lessThan=" + UPDATED_CLONING_DATE);
    }

    @Test
    @Transactional
    void getAllCloningsByCloningDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where cloningDate is greater than DEFAULT_CLONING_DATE
        defaultCloningShouldNotBeFound("cloningDate.greaterThan=" + DEFAULT_CLONING_DATE);

        // Get all the cloningList where cloningDate is greater than SMALLER_CLONING_DATE
        defaultCloningShouldBeFound("cloningDate.greaterThan=" + SMALLER_CLONING_DATE);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedEntitysIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedEntitys equals to DEFAULT_CLONED_ENTITYS
        defaultCloningShouldBeFound("clonedEntitys.equals=" + DEFAULT_CLONED_ENTITYS);

        // Get all the cloningList where clonedEntitys equals to UPDATED_CLONED_ENTITYS
        defaultCloningShouldNotBeFound("clonedEntitys.equals=" + UPDATED_CLONED_ENTITYS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedEntitysIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedEntitys in DEFAULT_CLONED_ENTITYS or UPDATED_CLONED_ENTITYS
        defaultCloningShouldBeFound("clonedEntitys.in=" + DEFAULT_CLONED_ENTITYS + "," + UPDATED_CLONED_ENTITYS);

        // Get all the cloningList where clonedEntitys equals to UPDATED_CLONED_ENTITYS
        defaultCloningShouldNotBeFound("clonedEntitys.in=" + UPDATED_CLONED_ENTITYS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedEntitysIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedEntitys is not null
        defaultCloningShouldBeFound("clonedEntitys.specified=true");

        // Get all the cloningList where clonedEntitys is null
        defaultCloningShouldNotBeFound("clonedEntitys.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByClonedEntitysContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedEntitys contains DEFAULT_CLONED_ENTITYS
        defaultCloningShouldBeFound("clonedEntitys.contains=" + DEFAULT_CLONED_ENTITYS);

        // Get all the cloningList where clonedEntitys contains UPDATED_CLONED_ENTITYS
        defaultCloningShouldNotBeFound("clonedEntitys.contains=" + UPDATED_CLONED_ENTITYS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedEntitysNotContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedEntitys does not contain DEFAULT_CLONED_ENTITYS
        defaultCloningShouldNotBeFound("clonedEntitys.doesNotContain=" + DEFAULT_CLONED_ENTITYS);

        // Get all the cloningList where clonedEntitys does not contain UPDATED_CLONED_ENTITYS
        defaultCloningShouldBeFound("clonedEntitys.doesNotContain=" + UPDATED_CLONED_ENTITYS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedParams equals to DEFAULT_CLONED_PARAMS
        defaultCloningShouldBeFound("clonedParams.equals=" + DEFAULT_CLONED_PARAMS);

        // Get all the cloningList where clonedParams equals to UPDATED_CLONED_PARAMS
        defaultCloningShouldNotBeFound("clonedParams.equals=" + UPDATED_CLONED_PARAMS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedParamsIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedParams in DEFAULT_CLONED_PARAMS or UPDATED_CLONED_PARAMS
        defaultCloningShouldBeFound("clonedParams.in=" + DEFAULT_CLONED_PARAMS + "," + UPDATED_CLONED_PARAMS);

        // Get all the cloningList where clonedParams equals to UPDATED_CLONED_PARAMS
        defaultCloningShouldNotBeFound("clonedParams.in=" + UPDATED_CLONED_PARAMS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedParams is not null
        defaultCloningShouldBeFound("clonedParams.specified=true");

        // Get all the cloningList where clonedParams is null
        defaultCloningShouldNotBeFound("clonedParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByClonedParamsContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedParams contains DEFAULT_CLONED_PARAMS
        defaultCloningShouldBeFound("clonedParams.contains=" + DEFAULT_CLONED_PARAMS);

        // Get all the cloningList where clonedParams contains UPDATED_CLONED_PARAMS
        defaultCloningShouldNotBeFound("clonedParams.contains=" + UPDATED_CLONED_PARAMS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedParamsNotContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedParams does not contain DEFAULT_CLONED_PARAMS
        defaultCloningShouldNotBeFound("clonedParams.doesNotContain=" + DEFAULT_CLONED_PARAMS);

        // Get all the cloningList where clonedParams does not contain UPDATED_CLONED_PARAMS
        defaultCloningShouldBeFound("clonedParams.doesNotContain=" + UPDATED_CLONED_PARAMS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedAttributs equals to DEFAULT_CLONED_ATTRIBUTS
        defaultCloningShouldBeFound("clonedAttributs.equals=" + DEFAULT_CLONED_ATTRIBUTS);

        // Get all the cloningList where clonedAttributs equals to UPDATED_CLONED_ATTRIBUTS
        defaultCloningShouldNotBeFound("clonedAttributs.equals=" + UPDATED_CLONED_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedAttributs in DEFAULT_CLONED_ATTRIBUTS or UPDATED_CLONED_ATTRIBUTS
        defaultCloningShouldBeFound("clonedAttributs.in=" + DEFAULT_CLONED_ATTRIBUTS + "," + UPDATED_CLONED_ATTRIBUTS);

        // Get all the cloningList where clonedAttributs equals to UPDATED_CLONED_ATTRIBUTS
        defaultCloningShouldNotBeFound("clonedAttributs.in=" + UPDATED_CLONED_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedAttributs is not null
        defaultCloningShouldBeFound("clonedAttributs.specified=true");

        // Get all the cloningList where clonedAttributs is null
        defaultCloningShouldNotBeFound("clonedAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCloningsByClonedAttributsContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedAttributs contains DEFAULT_CLONED_ATTRIBUTS
        defaultCloningShouldBeFound("clonedAttributs.contains=" + DEFAULT_CLONED_ATTRIBUTS);

        // Get all the cloningList where clonedAttributs contains UPDATED_CLONED_ATTRIBUTS
        defaultCloningShouldNotBeFound("clonedAttributs.contains=" + UPDATED_CLONED_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedAttributs does not contain DEFAULT_CLONED_ATTRIBUTS
        defaultCloningShouldNotBeFound("clonedAttributs.doesNotContain=" + DEFAULT_CLONED_ATTRIBUTS);

        // Get all the cloningList where clonedAttributs does not contain UPDATED_CLONED_ATTRIBUTS
        defaultCloningShouldBeFound("clonedAttributs.doesNotContain=" + UPDATED_CLONED_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedStatIsEqualToSomething() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedStat equals to DEFAULT_CLONED_STAT
        defaultCloningShouldBeFound("clonedStat.equals=" + DEFAULT_CLONED_STAT);

        // Get all the cloningList where clonedStat equals to UPDATED_CLONED_STAT
        defaultCloningShouldNotBeFound("clonedStat.equals=" + UPDATED_CLONED_STAT);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedStatIsInShouldWork() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedStat in DEFAULT_CLONED_STAT or UPDATED_CLONED_STAT
        defaultCloningShouldBeFound("clonedStat.in=" + DEFAULT_CLONED_STAT + "," + UPDATED_CLONED_STAT);

        // Get all the cloningList where clonedStat equals to UPDATED_CLONED_STAT
        defaultCloningShouldNotBeFound("clonedStat.in=" + UPDATED_CLONED_STAT);
    }

    @Test
    @Transactional
    void getAllCloningsByClonedStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        // Get all the cloningList where clonedStat is not null
        defaultCloningShouldBeFound("clonedStat.specified=true");

        // Get all the cloningList where clonedStat is null
        defaultCloningShouldNotBeFound("clonedStat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCloningShouldBeFound(String filter) throws Exception {
        restCloningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=cloningId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].cloningId").value(hasItem(cloning.getCloningId().intValue())))
            .andExpect(jsonPath("$.[*].cloningDescription").value(hasItem(DEFAULT_CLONING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cloningOldEventId").value(hasItem(DEFAULT_CLONING_OLD_EVENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].cloningNewEventId").value(hasItem(DEFAULT_CLONING_NEW_EVENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].cloningUserId").value(hasItem(DEFAULT_CLONING_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].cloningDate").value(hasItem(sameInstant(DEFAULT_CLONING_DATE))))
            .andExpect(jsonPath("$.[*].clonedEntitys").value(hasItem(DEFAULT_CLONED_ENTITYS)))
            .andExpect(jsonPath("$.[*].clonedParams").value(hasItem(DEFAULT_CLONED_PARAMS)))
            .andExpect(jsonPath("$.[*].clonedAttributs").value(hasItem(DEFAULT_CLONED_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].clonedStat").value(hasItem(DEFAULT_CLONED_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restCloningMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=cloningId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCloningShouldNotBeFound(String filter) throws Exception {
        restCloningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=cloningId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCloningMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=cloningId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCloning() throws Exception {
        // Get the cloning
        restCloningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCloning() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();

        // Update the cloning
        Cloning updatedCloning = cloningRepository.findById(cloning.getCloningId()).get();
        // Disconnect from session so that the updates on updatedCloning are not directly saved in db
        em.detach(updatedCloning);
        updatedCloning
            .cloningDescription(UPDATED_CLONING_DESCRIPTION)
            .cloningOldEventId(UPDATED_CLONING_OLD_EVENT_ID)
            .cloningNewEventId(UPDATED_CLONING_NEW_EVENT_ID)
            .cloningUserId(UPDATED_CLONING_USER_ID)
            .cloningDate(UPDATED_CLONING_DATE)
            .clonedEntitys(UPDATED_CLONED_ENTITYS)
            .clonedParams(UPDATED_CLONED_PARAMS)
            .clonedAttributs(UPDATED_CLONED_ATTRIBUTS)
            .clonedStat(UPDATED_CLONED_STAT);
        CloningDTO cloningDTO = cloningMapper.toDto(updatedCloning);

        restCloningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cloningDTO.getCloningId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cloningDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
        Cloning testCloning = cloningList.get(cloningList.size() - 1);
        assertThat(testCloning.getCloningDescription()).isEqualTo(UPDATED_CLONING_DESCRIPTION);
        assertThat(testCloning.getCloningOldEventId()).isEqualTo(UPDATED_CLONING_OLD_EVENT_ID);
        assertThat(testCloning.getCloningNewEventId()).isEqualTo(UPDATED_CLONING_NEW_EVENT_ID);
        assertThat(testCloning.getCloningUserId()).isEqualTo(UPDATED_CLONING_USER_ID);
        assertThat(testCloning.getCloningDate()).isEqualTo(UPDATED_CLONING_DATE);
        assertThat(testCloning.getClonedEntitys()).isEqualTo(UPDATED_CLONED_ENTITYS);
        assertThat(testCloning.getClonedParams()).isEqualTo(UPDATED_CLONED_PARAMS);
        assertThat(testCloning.getClonedAttributs()).isEqualTo(UPDATED_CLONED_ATTRIBUTS);
        assertThat(testCloning.getClonedStat()).isEqualTo(UPDATED_CLONED_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCloning() throws Exception {
        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();
        cloning.setCloningId(count.incrementAndGet());

        // Create the Cloning
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCloningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cloningDTO.getCloningId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cloningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCloning() throws Exception {
        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();
        cloning.setCloningId(count.incrementAndGet());

        // Create the Cloning
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCloningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cloningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCloning() throws Exception {
        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();
        cloning.setCloningId(count.incrementAndGet());

        // Create the Cloning
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCloningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cloningDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCloningWithPatch() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();

        // Update the cloning using partial update
        Cloning partialUpdatedCloning = new Cloning();
        partialUpdatedCloning.setCloningId(cloning.getCloningId());

        partialUpdatedCloning.cloningDate(UPDATED_CLONING_DATE).clonedEntitys(UPDATED_CLONED_ENTITYS).clonedParams(UPDATED_CLONED_PARAMS);

        restCloningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCloning.getCloningId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCloning))
            )
            .andExpect(status().isOk());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
        Cloning testCloning = cloningList.get(cloningList.size() - 1);
        assertThat(testCloning.getCloningDescription()).isEqualTo(DEFAULT_CLONING_DESCRIPTION);
        assertThat(testCloning.getCloningOldEventId()).isEqualTo(DEFAULT_CLONING_OLD_EVENT_ID);
        assertThat(testCloning.getCloningNewEventId()).isEqualTo(DEFAULT_CLONING_NEW_EVENT_ID);
        assertThat(testCloning.getCloningUserId()).isEqualTo(DEFAULT_CLONING_USER_ID);
        assertThat(testCloning.getCloningDate()).isEqualTo(UPDATED_CLONING_DATE);
        assertThat(testCloning.getClonedEntitys()).isEqualTo(UPDATED_CLONED_ENTITYS);
        assertThat(testCloning.getClonedParams()).isEqualTo(UPDATED_CLONED_PARAMS);
        assertThat(testCloning.getClonedAttributs()).isEqualTo(DEFAULT_CLONED_ATTRIBUTS);
        assertThat(testCloning.getClonedStat()).isEqualTo(DEFAULT_CLONED_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCloningWithPatch() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();

        // Update the cloning using partial update
        Cloning partialUpdatedCloning = new Cloning();
        partialUpdatedCloning.setCloningId(cloning.getCloningId());

        partialUpdatedCloning
            .cloningDescription(UPDATED_CLONING_DESCRIPTION)
            .cloningOldEventId(UPDATED_CLONING_OLD_EVENT_ID)
            .cloningNewEventId(UPDATED_CLONING_NEW_EVENT_ID)
            .cloningUserId(UPDATED_CLONING_USER_ID)
            .cloningDate(UPDATED_CLONING_DATE)
            .clonedEntitys(UPDATED_CLONED_ENTITYS)
            .clonedParams(UPDATED_CLONED_PARAMS)
            .clonedAttributs(UPDATED_CLONED_ATTRIBUTS)
            .clonedStat(UPDATED_CLONED_STAT);

        restCloningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCloning.getCloningId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCloning))
            )
            .andExpect(status().isOk());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
        Cloning testCloning = cloningList.get(cloningList.size() - 1);
        assertThat(testCloning.getCloningDescription()).isEqualTo(UPDATED_CLONING_DESCRIPTION);
        assertThat(testCloning.getCloningOldEventId()).isEqualTo(UPDATED_CLONING_OLD_EVENT_ID);
        assertThat(testCloning.getCloningNewEventId()).isEqualTo(UPDATED_CLONING_NEW_EVENT_ID);
        assertThat(testCloning.getCloningUserId()).isEqualTo(UPDATED_CLONING_USER_ID);
        assertThat(testCloning.getCloningDate()).isEqualTo(UPDATED_CLONING_DATE);
        assertThat(testCloning.getClonedEntitys()).isEqualTo(UPDATED_CLONED_ENTITYS);
        assertThat(testCloning.getClonedParams()).isEqualTo(UPDATED_CLONED_PARAMS);
        assertThat(testCloning.getClonedAttributs()).isEqualTo(UPDATED_CLONED_ATTRIBUTS);
        assertThat(testCloning.getClonedStat()).isEqualTo(UPDATED_CLONED_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCloning() throws Exception {
        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();
        cloning.setCloningId(count.incrementAndGet());

        // Create the Cloning
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCloningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cloningDTO.getCloningId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cloningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCloning() throws Exception {
        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();
        cloning.setCloningId(count.incrementAndGet());

        // Create the Cloning
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCloningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cloningDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCloning() throws Exception {
        int databaseSizeBeforeUpdate = cloningRepository.findAll().size();
        cloning.setCloningId(count.incrementAndGet());

        // Create the Cloning
        CloningDTO cloningDTO = cloningMapper.toDto(cloning);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCloningMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cloningDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cloning in the database
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCloning() throws Exception {
        // Initialize the database
        cloningRepository.saveAndFlush(cloning);

        int databaseSizeBeforeDelete = cloningRepository.findAll().size();

        // Delete the cloning
        restCloningMockMvc
            .perform(delete(ENTITY_API_URL_ID, cloning.getCloningId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cloning> cloningList = cloningRepository.findAll();
        assertThat(cloningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
