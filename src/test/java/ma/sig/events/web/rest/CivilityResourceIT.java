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
import ma.sig.events.domain.Civility;
import ma.sig.events.repository.CivilityRepository;
import ma.sig.events.service.criteria.CivilityCriteria;
import ma.sig.events.service.dto.CivilityDTO;
import ma.sig.events.service.mapper.CivilityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CivilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CivilityResourceIT {

    private static final String DEFAULT_CIVILITY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITY_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CIVILITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CIVILITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CIVILITY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CIVILITY_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITY_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CIVILITY_STAT = false;
    private static final Boolean UPDATED_CIVILITY_STAT = true;

    private static final String ENTITY_API_URL = "/api/civilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{civilityId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CivilityRepository civilityRepository;

    @Autowired
    private CivilityMapper civilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCivilityMockMvc;

    private Civility civility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Civility createEntity(EntityManager em) {
        Civility civility = new Civility()
            .civilityValue(DEFAULT_CIVILITY_VALUE)
            .civilityDescription(DEFAULT_CIVILITY_DESCRIPTION)
            .civilityCode(DEFAULT_CIVILITY_CODE)
            .civilityParams(DEFAULT_CIVILITY_PARAMS)
            .civilityAttributs(DEFAULT_CIVILITY_ATTRIBUTS)
            .civilityStat(DEFAULT_CIVILITY_STAT);
        return civility;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Civility createUpdatedEntity(EntityManager em) {
        Civility civility = new Civility()
            .civilityValue(UPDATED_CIVILITY_VALUE)
            .civilityDescription(UPDATED_CIVILITY_DESCRIPTION)
            .civilityCode(UPDATED_CIVILITY_CODE)
            .civilityParams(UPDATED_CIVILITY_PARAMS)
            .civilityAttributs(UPDATED_CIVILITY_ATTRIBUTS)
            .civilityStat(UPDATED_CIVILITY_STAT);
        return civility;
    }

    @BeforeEach
    public void initTest() {
        civility = createEntity(em);
    }

    @Test
    @Transactional
    void createCivility() throws Exception {
        int databaseSizeBeforeCreate = civilityRepository.findAll().size();
        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);
        restCivilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeCreate + 1);
        Civility testCivility = civilityList.get(civilityList.size() - 1);
        assertThat(testCivility.getCivilityValue()).isEqualTo(DEFAULT_CIVILITY_VALUE);
        assertThat(testCivility.getCivilityDescription()).isEqualTo(DEFAULT_CIVILITY_DESCRIPTION);
        assertThat(testCivility.getCivilityCode()).isEqualTo(DEFAULT_CIVILITY_CODE);
        assertThat(testCivility.getCivilityParams()).isEqualTo(DEFAULT_CIVILITY_PARAMS);
        assertThat(testCivility.getCivilityAttributs()).isEqualTo(DEFAULT_CIVILITY_ATTRIBUTS);
        assertThat(testCivility.getCivilityStat()).isEqualTo(DEFAULT_CIVILITY_STAT);
    }

    @Test
    @Transactional
    void createCivilityWithExistingId() throws Exception {
        // Create the Civility with an existing ID
        civility.setCivilityId(1L);
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        int databaseSizeBeforeCreate = civilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCivilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCivilityValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = civilityRepository.findAll().size();
        // set the field null
        civility.setCivilityValue(null);

        // Create the Civility, which fails.
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        restCivilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isBadRequest());

        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCivilities() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=civilityId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].civilityId").value(hasItem(civility.getCivilityId().intValue())))
            .andExpect(jsonPath("$.[*].civilityValue").value(hasItem(DEFAULT_CIVILITY_VALUE)))
            .andExpect(jsonPath("$.[*].civilityDescription").value(hasItem(DEFAULT_CIVILITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].civilityCode").value(hasItem(DEFAULT_CIVILITY_CODE)))
            .andExpect(jsonPath("$.[*].civilityParams").value(hasItem(DEFAULT_CIVILITY_PARAMS)))
            .andExpect(jsonPath("$.[*].civilityAttributs").value(hasItem(DEFAULT_CIVILITY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].civilityStat").value(hasItem(DEFAULT_CIVILITY_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getCivility() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get the civility
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL_ID, civility.getCivilityId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.civilityId").value(civility.getCivilityId().intValue()))
            .andExpect(jsonPath("$.civilityValue").value(DEFAULT_CIVILITY_VALUE))
            .andExpect(jsonPath("$.civilityDescription").value(DEFAULT_CIVILITY_DESCRIPTION))
            .andExpect(jsonPath("$.civilityCode").value(DEFAULT_CIVILITY_CODE))
            .andExpect(jsonPath("$.civilityParams").value(DEFAULT_CIVILITY_PARAMS))
            .andExpect(jsonPath("$.civilityAttributs").value(DEFAULT_CIVILITY_ATTRIBUTS))
            .andExpect(jsonPath("$.civilityStat").value(DEFAULT_CIVILITY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCivilitiesByIdFiltering() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        Long id = civility.getCivilityId();

        defaultCivilityShouldBeFound("civilityId.equals=" + id);
        defaultCivilityShouldNotBeFound("civilityId.notEquals=" + id);

        defaultCivilityShouldBeFound("civilityId.greaterThanOrEqual=" + id);
        defaultCivilityShouldNotBeFound("civilityId.greaterThan=" + id);

        defaultCivilityShouldBeFound("civilityId.lessThanOrEqual=" + id);
        defaultCivilityShouldNotBeFound("civilityId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityValueIsEqualToSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityValue equals to DEFAULT_CIVILITY_VALUE
        defaultCivilityShouldBeFound("civilityValue.equals=" + DEFAULT_CIVILITY_VALUE);

        // Get all the civilityList where civilityValue equals to UPDATED_CIVILITY_VALUE
        defaultCivilityShouldNotBeFound("civilityValue.equals=" + UPDATED_CIVILITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityValueIsInShouldWork() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityValue in DEFAULT_CIVILITY_VALUE or UPDATED_CIVILITY_VALUE
        defaultCivilityShouldBeFound("civilityValue.in=" + DEFAULT_CIVILITY_VALUE + "," + UPDATED_CIVILITY_VALUE);

        // Get all the civilityList where civilityValue equals to UPDATED_CIVILITY_VALUE
        defaultCivilityShouldNotBeFound("civilityValue.in=" + UPDATED_CIVILITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityValue is not null
        defaultCivilityShouldBeFound("civilityValue.specified=true");

        // Get all the civilityList where civilityValue is null
        defaultCivilityShouldNotBeFound("civilityValue.specified=false");
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityValueContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityValue contains DEFAULT_CIVILITY_VALUE
        defaultCivilityShouldBeFound("civilityValue.contains=" + DEFAULT_CIVILITY_VALUE);

        // Get all the civilityList where civilityValue contains UPDATED_CIVILITY_VALUE
        defaultCivilityShouldNotBeFound("civilityValue.contains=" + UPDATED_CIVILITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityValueNotContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityValue does not contain DEFAULT_CIVILITY_VALUE
        defaultCivilityShouldNotBeFound("civilityValue.doesNotContain=" + DEFAULT_CIVILITY_VALUE);

        // Get all the civilityList where civilityValue does not contain UPDATED_CIVILITY_VALUE
        defaultCivilityShouldBeFound("civilityValue.doesNotContain=" + UPDATED_CIVILITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityDescription equals to DEFAULT_CIVILITY_DESCRIPTION
        defaultCivilityShouldBeFound("civilityDescription.equals=" + DEFAULT_CIVILITY_DESCRIPTION);

        // Get all the civilityList where civilityDescription equals to UPDATED_CIVILITY_DESCRIPTION
        defaultCivilityShouldNotBeFound("civilityDescription.equals=" + UPDATED_CIVILITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityDescription in DEFAULT_CIVILITY_DESCRIPTION or UPDATED_CIVILITY_DESCRIPTION
        defaultCivilityShouldBeFound("civilityDescription.in=" + DEFAULT_CIVILITY_DESCRIPTION + "," + UPDATED_CIVILITY_DESCRIPTION);

        // Get all the civilityList where civilityDescription equals to UPDATED_CIVILITY_DESCRIPTION
        defaultCivilityShouldNotBeFound("civilityDescription.in=" + UPDATED_CIVILITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityDescription is not null
        defaultCivilityShouldBeFound("civilityDescription.specified=true");

        // Get all the civilityList where civilityDescription is null
        defaultCivilityShouldNotBeFound("civilityDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityDescriptionContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityDescription contains DEFAULT_CIVILITY_DESCRIPTION
        defaultCivilityShouldBeFound("civilityDescription.contains=" + DEFAULT_CIVILITY_DESCRIPTION);

        // Get all the civilityList where civilityDescription contains UPDATED_CIVILITY_DESCRIPTION
        defaultCivilityShouldNotBeFound("civilityDescription.contains=" + UPDATED_CIVILITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityDescription does not contain DEFAULT_CIVILITY_DESCRIPTION
        defaultCivilityShouldNotBeFound("civilityDescription.doesNotContain=" + DEFAULT_CIVILITY_DESCRIPTION);

        // Get all the civilityList where civilityDescription does not contain UPDATED_CIVILITY_DESCRIPTION
        defaultCivilityShouldBeFound("civilityDescription.doesNotContain=" + UPDATED_CIVILITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityCode equals to DEFAULT_CIVILITY_CODE
        defaultCivilityShouldBeFound("civilityCode.equals=" + DEFAULT_CIVILITY_CODE);

        // Get all the civilityList where civilityCode equals to UPDATED_CIVILITY_CODE
        defaultCivilityShouldNotBeFound("civilityCode.equals=" + UPDATED_CIVILITY_CODE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityCode in DEFAULT_CIVILITY_CODE or UPDATED_CIVILITY_CODE
        defaultCivilityShouldBeFound("civilityCode.in=" + DEFAULT_CIVILITY_CODE + "," + UPDATED_CIVILITY_CODE);

        // Get all the civilityList where civilityCode equals to UPDATED_CIVILITY_CODE
        defaultCivilityShouldNotBeFound("civilityCode.in=" + UPDATED_CIVILITY_CODE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityCode is not null
        defaultCivilityShouldBeFound("civilityCode.specified=true");

        // Get all the civilityList where civilityCode is null
        defaultCivilityShouldNotBeFound("civilityCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityCodeContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityCode contains DEFAULT_CIVILITY_CODE
        defaultCivilityShouldBeFound("civilityCode.contains=" + DEFAULT_CIVILITY_CODE);

        // Get all the civilityList where civilityCode contains UPDATED_CIVILITY_CODE
        defaultCivilityShouldNotBeFound("civilityCode.contains=" + UPDATED_CIVILITY_CODE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityCodeNotContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityCode does not contain DEFAULT_CIVILITY_CODE
        defaultCivilityShouldNotBeFound("civilityCode.doesNotContain=" + DEFAULT_CIVILITY_CODE);

        // Get all the civilityList where civilityCode does not contain UPDATED_CIVILITY_CODE
        defaultCivilityShouldBeFound("civilityCode.doesNotContain=" + UPDATED_CIVILITY_CODE);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityParams equals to DEFAULT_CIVILITY_PARAMS
        defaultCivilityShouldBeFound("civilityParams.equals=" + DEFAULT_CIVILITY_PARAMS);

        // Get all the civilityList where civilityParams equals to UPDATED_CIVILITY_PARAMS
        defaultCivilityShouldNotBeFound("civilityParams.equals=" + UPDATED_CIVILITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityParamsIsInShouldWork() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityParams in DEFAULT_CIVILITY_PARAMS or UPDATED_CIVILITY_PARAMS
        defaultCivilityShouldBeFound("civilityParams.in=" + DEFAULT_CIVILITY_PARAMS + "," + UPDATED_CIVILITY_PARAMS);

        // Get all the civilityList where civilityParams equals to UPDATED_CIVILITY_PARAMS
        defaultCivilityShouldNotBeFound("civilityParams.in=" + UPDATED_CIVILITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityParams is not null
        defaultCivilityShouldBeFound("civilityParams.specified=true");

        // Get all the civilityList where civilityParams is null
        defaultCivilityShouldNotBeFound("civilityParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityParamsContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityParams contains DEFAULT_CIVILITY_PARAMS
        defaultCivilityShouldBeFound("civilityParams.contains=" + DEFAULT_CIVILITY_PARAMS);

        // Get all the civilityList where civilityParams contains UPDATED_CIVILITY_PARAMS
        defaultCivilityShouldNotBeFound("civilityParams.contains=" + UPDATED_CIVILITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityParamsNotContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityParams does not contain DEFAULT_CIVILITY_PARAMS
        defaultCivilityShouldNotBeFound("civilityParams.doesNotContain=" + DEFAULT_CIVILITY_PARAMS);

        // Get all the civilityList where civilityParams does not contain UPDATED_CIVILITY_PARAMS
        defaultCivilityShouldBeFound("civilityParams.doesNotContain=" + UPDATED_CIVILITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityAttributs equals to DEFAULT_CIVILITY_ATTRIBUTS
        defaultCivilityShouldBeFound("civilityAttributs.equals=" + DEFAULT_CIVILITY_ATTRIBUTS);

        // Get all the civilityList where civilityAttributs equals to UPDATED_CIVILITY_ATTRIBUTS
        defaultCivilityShouldNotBeFound("civilityAttributs.equals=" + UPDATED_CIVILITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityAttributs in DEFAULT_CIVILITY_ATTRIBUTS or UPDATED_CIVILITY_ATTRIBUTS
        defaultCivilityShouldBeFound("civilityAttributs.in=" + DEFAULT_CIVILITY_ATTRIBUTS + "," + UPDATED_CIVILITY_ATTRIBUTS);

        // Get all the civilityList where civilityAttributs equals to UPDATED_CIVILITY_ATTRIBUTS
        defaultCivilityShouldNotBeFound("civilityAttributs.in=" + UPDATED_CIVILITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityAttributs is not null
        defaultCivilityShouldBeFound("civilityAttributs.specified=true");

        // Get all the civilityList where civilityAttributs is null
        defaultCivilityShouldNotBeFound("civilityAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityAttributsContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityAttributs contains DEFAULT_CIVILITY_ATTRIBUTS
        defaultCivilityShouldBeFound("civilityAttributs.contains=" + DEFAULT_CIVILITY_ATTRIBUTS);

        // Get all the civilityList where civilityAttributs contains UPDATED_CIVILITY_ATTRIBUTS
        defaultCivilityShouldNotBeFound("civilityAttributs.contains=" + UPDATED_CIVILITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityAttributs does not contain DEFAULT_CIVILITY_ATTRIBUTS
        defaultCivilityShouldNotBeFound("civilityAttributs.doesNotContain=" + DEFAULT_CIVILITY_ATTRIBUTS);

        // Get all the civilityList where civilityAttributs does not contain UPDATED_CIVILITY_ATTRIBUTS
        defaultCivilityShouldBeFound("civilityAttributs.doesNotContain=" + UPDATED_CIVILITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityStatIsEqualToSomething() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityStat equals to DEFAULT_CIVILITY_STAT
        defaultCivilityShouldBeFound("civilityStat.equals=" + DEFAULT_CIVILITY_STAT);

        // Get all the civilityList where civilityStat equals to UPDATED_CIVILITY_STAT
        defaultCivilityShouldNotBeFound("civilityStat.equals=" + UPDATED_CIVILITY_STAT);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityStatIsInShouldWork() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityStat in DEFAULT_CIVILITY_STAT or UPDATED_CIVILITY_STAT
        defaultCivilityShouldBeFound("civilityStat.in=" + DEFAULT_CIVILITY_STAT + "," + UPDATED_CIVILITY_STAT);

        // Get all the civilityList where civilityStat equals to UPDATED_CIVILITY_STAT
        defaultCivilityShouldNotBeFound("civilityStat.in=" + UPDATED_CIVILITY_STAT);
    }

    @Test
    @Transactional
    void getAllCivilitiesByCivilityStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList where civilityStat is not null
        defaultCivilityShouldBeFound("civilityStat.specified=true");

        // Get all the civilityList where civilityStat is null
        defaultCivilityShouldNotBeFound("civilityStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCivilitiesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            civilityRepository.saveAndFlush(civility);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        civility.addAccreditation(accreditation);
        civilityRepository.saveAndFlush(civility);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the civilityList where accreditation equals to accreditationId
        defaultCivilityShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the civilityList where accreditation equals to (accreditationId + 1)
        defaultCivilityShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCivilityShouldBeFound(String filter) throws Exception {
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=civilityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].civilityId").value(hasItem(civility.getCivilityId().intValue())))
            .andExpect(jsonPath("$.[*].civilityValue").value(hasItem(DEFAULT_CIVILITY_VALUE)))
            .andExpect(jsonPath("$.[*].civilityDescription").value(hasItem(DEFAULT_CIVILITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].civilityCode").value(hasItem(DEFAULT_CIVILITY_CODE)))
            .andExpect(jsonPath("$.[*].civilityParams").value(hasItem(DEFAULT_CIVILITY_PARAMS)))
            .andExpect(jsonPath("$.[*].civilityAttributs").value(hasItem(DEFAULT_CIVILITY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].civilityStat").value(hasItem(DEFAULT_CIVILITY_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=civilityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCivilityShouldNotBeFound(String filter) throws Exception {
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=civilityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=civilityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCivility() throws Exception {
        // Get the civility
        restCivilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCivility() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();

        // Update the civility
        Civility updatedCivility = civilityRepository.findById(civility.getCivilityId()).get();
        // Disconnect from session so that the updates on updatedCivility are not directly saved in db
        em.detach(updatedCivility);
        updatedCivility
            .civilityValue(UPDATED_CIVILITY_VALUE)
            .civilityDescription(UPDATED_CIVILITY_DESCRIPTION)
            .civilityCode(UPDATED_CIVILITY_CODE)
            .civilityParams(UPDATED_CIVILITY_PARAMS)
            .civilityAttributs(UPDATED_CIVILITY_ATTRIBUTS)
            .civilityStat(UPDATED_CIVILITY_STAT);
        CivilityDTO civilityDTO = civilityMapper.toDto(updatedCivility);

        restCivilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, civilityDTO.getCivilityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(civilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
        Civility testCivility = civilityList.get(civilityList.size() - 1);
        assertThat(testCivility.getCivilityValue()).isEqualTo(UPDATED_CIVILITY_VALUE);
        assertThat(testCivility.getCivilityDescription()).isEqualTo(UPDATED_CIVILITY_DESCRIPTION);
        assertThat(testCivility.getCivilityCode()).isEqualTo(UPDATED_CIVILITY_CODE);
        assertThat(testCivility.getCivilityParams()).isEqualTo(UPDATED_CIVILITY_PARAMS);
        assertThat(testCivility.getCivilityAttributs()).isEqualTo(UPDATED_CIVILITY_ATTRIBUTS);
        assertThat(testCivility.getCivilityStat()).isEqualTo(UPDATED_CIVILITY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCivility() throws Exception {
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();
        civility.setCivilityId(count.incrementAndGet());

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, civilityDTO.getCivilityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(civilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCivility() throws Exception {
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();
        civility.setCivilityId(count.incrementAndGet());

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(civilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCivility() throws Exception {
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();
        civility.setCivilityId(count.incrementAndGet());

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCivilityWithPatch() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();

        // Update the civility using partial update
        Civility partialUpdatedCivility = new Civility();
        partialUpdatedCivility.setCivilityId(civility.getCivilityId());

        partialUpdatedCivility
            .civilityValue(UPDATED_CIVILITY_VALUE)
            .civilityDescription(UPDATED_CIVILITY_DESCRIPTION)
            .civilityCode(UPDATED_CIVILITY_CODE)
            .civilityParams(UPDATED_CIVILITY_PARAMS)
            .civilityAttributs(UPDATED_CIVILITY_ATTRIBUTS);

        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCivility.getCivilityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCivility))
            )
            .andExpect(status().isOk());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
        Civility testCivility = civilityList.get(civilityList.size() - 1);
        assertThat(testCivility.getCivilityValue()).isEqualTo(UPDATED_CIVILITY_VALUE);
        assertThat(testCivility.getCivilityDescription()).isEqualTo(UPDATED_CIVILITY_DESCRIPTION);
        assertThat(testCivility.getCivilityCode()).isEqualTo(UPDATED_CIVILITY_CODE);
        assertThat(testCivility.getCivilityParams()).isEqualTo(UPDATED_CIVILITY_PARAMS);
        assertThat(testCivility.getCivilityAttributs()).isEqualTo(UPDATED_CIVILITY_ATTRIBUTS);
        assertThat(testCivility.getCivilityStat()).isEqualTo(DEFAULT_CIVILITY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCivilityWithPatch() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();

        // Update the civility using partial update
        Civility partialUpdatedCivility = new Civility();
        partialUpdatedCivility.setCivilityId(civility.getCivilityId());

        partialUpdatedCivility
            .civilityValue(UPDATED_CIVILITY_VALUE)
            .civilityDescription(UPDATED_CIVILITY_DESCRIPTION)
            .civilityCode(UPDATED_CIVILITY_CODE)
            .civilityParams(UPDATED_CIVILITY_PARAMS)
            .civilityAttributs(UPDATED_CIVILITY_ATTRIBUTS)
            .civilityStat(UPDATED_CIVILITY_STAT);

        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCivility.getCivilityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCivility))
            )
            .andExpect(status().isOk());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
        Civility testCivility = civilityList.get(civilityList.size() - 1);
        assertThat(testCivility.getCivilityValue()).isEqualTo(UPDATED_CIVILITY_VALUE);
        assertThat(testCivility.getCivilityDescription()).isEqualTo(UPDATED_CIVILITY_DESCRIPTION);
        assertThat(testCivility.getCivilityCode()).isEqualTo(UPDATED_CIVILITY_CODE);
        assertThat(testCivility.getCivilityParams()).isEqualTo(UPDATED_CIVILITY_PARAMS);
        assertThat(testCivility.getCivilityAttributs()).isEqualTo(UPDATED_CIVILITY_ATTRIBUTS);
        assertThat(testCivility.getCivilityStat()).isEqualTo(UPDATED_CIVILITY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCivility() throws Exception {
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();
        civility.setCivilityId(count.incrementAndGet());

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, civilityDTO.getCivilityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(civilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCivility() throws Exception {
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();
        civility.setCivilityId(count.incrementAndGet());

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(civilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCivility() throws Exception {
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();
        civility.setCivilityId(count.incrementAndGet());

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(civilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCivility() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        int databaseSizeBeforeDelete = civilityRepository.findAll().size();

        // Delete the civility
        restCivilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, civility.getCivilityId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
