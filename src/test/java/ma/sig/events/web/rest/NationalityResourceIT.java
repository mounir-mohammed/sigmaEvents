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
import ma.sig.events.domain.Nationality;
import ma.sig.events.repository.NationalityRepository;
import ma.sig.events.service.criteria.NationalityCriteria;
import ma.sig.events.service.dto.NationalityDTO;
import ma.sig.events.service.mapper.NationalityMapper;
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
 * Integration tests for the {@link NationalityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NationalityResourceIT {

    private static final String DEFAULT_NATIONALITY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_NATIONALITY_FLAG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NATIONALITY_FLAG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NATIONALITY_FLAG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NATIONALITY_FLAG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NATIONALITY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NATIONALITY_STAT = false;
    private static final Boolean UPDATED_NATIONALITY_STAT = true;

    private static final String ENTITY_API_URL = "/api/nationalities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{nationalityId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NationalityRepository nationalityRepository;

    @Autowired
    private NationalityMapper nationalityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNationalityMockMvc;

    private Nationality nationality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationality createEntity(EntityManager em) {
        Nationality nationality = new Nationality()
            .nationalityValue(DEFAULT_NATIONALITY_VALUE)
            .nationalityAbreviation(DEFAULT_NATIONALITY_ABREVIATION)
            .nationalityDescription(DEFAULT_NATIONALITY_DESCRIPTION)
            .nationalityFlag(DEFAULT_NATIONALITY_FLAG)
            .nationalityFlagContentType(DEFAULT_NATIONALITY_FLAG_CONTENT_TYPE)
            .nationalityParams(DEFAULT_NATIONALITY_PARAMS)
            .nationalityAttributs(DEFAULT_NATIONALITY_ATTRIBUTS)
            .nationalityStat(DEFAULT_NATIONALITY_STAT);
        return nationality;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationality createUpdatedEntity(EntityManager em) {
        Nationality nationality = new Nationality()
            .nationalityValue(UPDATED_NATIONALITY_VALUE)
            .nationalityAbreviation(UPDATED_NATIONALITY_ABREVIATION)
            .nationalityDescription(UPDATED_NATIONALITY_DESCRIPTION)
            .nationalityFlag(UPDATED_NATIONALITY_FLAG)
            .nationalityFlagContentType(UPDATED_NATIONALITY_FLAG_CONTENT_TYPE)
            .nationalityParams(UPDATED_NATIONALITY_PARAMS)
            .nationalityAttributs(UPDATED_NATIONALITY_ATTRIBUTS)
            .nationalityStat(UPDATED_NATIONALITY_STAT);
        return nationality;
    }

    @BeforeEach
    public void initTest() {
        nationality = createEntity(em);
    }

    @Test
    @Transactional
    void createNationality() throws Exception {
        int databaseSizeBeforeCreate = nationalityRepository.findAll().size();
        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);
        restNationalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeCreate + 1);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getNationalityValue()).isEqualTo(DEFAULT_NATIONALITY_VALUE);
        assertThat(testNationality.getNationalityAbreviation()).isEqualTo(DEFAULT_NATIONALITY_ABREVIATION);
        assertThat(testNationality.getNationalityDescription()).isEqualTo(DEFAULT_NATIONALITY_DESCRIPTION);
        assertThat(testNationality.getNationalityFlag()).isEqualTo(DEFAULT_NATIONALITY_FLAG);
        assertThat(testNationality.getNationalityFlagContentType()).isEqualTo(DEFAULT_NATIONALITY_FLAG_CONTENT_TYPE);
        assertThat(testNationality.getNationalityParams()).isEqualTo(DEFAULT_NATIONALITY_PARAMS);
        assertThat(testNationality.getNationalityAttributs()).isEqualTo(DEFAULT_NATIONALITY_ATTRIBUTS);
        assertThat(testNationality.getNationalityStat()).isEqualTo(DEFAULT_NATIONALITY_STAT);
    }

    @Test
    @Transactional
    void createNationalityWithExistingId() throws Exception {
        // Create the Nationality with an existing ID
        nationality.setNationalityId(1L);
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        int databaseSizeBeforeCreate = nationalityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNationalityValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = nationalityRepository.findAll().size();
        // set the field null
        nationality.setNationalityValue(null);

        // Create the Nationality, which fails.
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        restNationalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNationalities() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=nationalityId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].nationalityId").value(hasItem(nationality.getNationalityId().intValue())))
            .andExpect(jsonPath("$.[*].nationalityValue").value(hasItem(DEFAULT_NATIONALITY_VALUE)))
            .andExpect(jsonPath("$.[*].nationalityAbreviation").value(hasItem(DEFAULT_NATIONALITY_ABREVIATION)))
            .andExpect(jsonPath("$.[*].nationalityDescription").value(hasItem(DEFAULT_NATIONALITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nationalityFlagContentType").value(hasItem(DEFAULT_NATIONALITY_FLAG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nationalityFlag").value(hasItem(Base64Utils.encodeToString(DEFAULT_NATIONALITY_FLAG))))
            .andExpect(jsonPath("$.[*].nationalityParams").value(hasItem(DEFAULT_NATIONALITY_PARAMS)))
            .andExpect(jsonPath("$.[*].nationalityAttributs").value(hasItem(DEFAULT_NATIONALITY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].nationalityStat").value(hasItem(DEFAULT_NATIONALITY_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get the nationality
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL_ID, nationality.getNationalityId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.nationalityId").value(nationality.getNationalityId().intValue()))
            .andExpect(jsonPath("$.nationalityValue").value(DEFAULT_NATIONALITY_VALUE))
            .andExpect(jsonPath("$.nationalityAbreviation").value(DEFAULT_NATIONALITY_ABREVIATION))
            .andExpect(jsonPath("$.nationalityDescription").value(DEFAULT_NATIONALITY_DESCRIPTION))
            .andExpect(jsonPath("$.nationalityFlagContentType").value(DEFAULT_NATIONALITY_FLAG_CONTENT_TYPE))
            .andExpect(jsonPath("$.nationalityFlag").value(Base64Utils.encodeToString(DEFAULT_NATIONALITY_FLAG)))
            .andExpect(jsonPath("$.nationalityParams").value(DEFAULT_NATIONALITY_PARAMS))
            .andExpect(jsonPath("$.nationalityAttributs").value(DEFAULT_NATIONALITY_ATTRIBUTS))
            .andExpect(jsonPath("$.nationalityStat").value(DEFAULT_NATIONALITY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getNationalitiesByIdFiltering() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        Long id = nationality.getNationalityId();

        defaultNationalityShouldBeFound("nationalityId.equals=" + id);
        defaultNationalityShouldNotBeFound("nationalityId.notEquals=" + id);

        defaultNationalityShouldBeFound("nationalityId.greaterThanOrEqual=" + id);
        defaultNationalityShouldNotBeFound("nationalityId.greaterThan=" + id);

        defaultNationalityShouldBeFound("nationalityId.lessThanOrEqual=" + id);
        defaultNationalityShouldNotBeFound("nationalityId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityValueIsEqualToSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityValue equals to DEFAULT_NATIONALITY_VALUE
        defaultNationalityShouldBeFound("nationalityValue.equals=" + DEFAULT_NATIONALITY_VALUE);

        // Get all the nationalityList where nationalityValue equals to UPDATED_NATIONALITY_VALUE
        defaultNationalityShouldNotBeFound("nationalityValue.equals=" + UPDATED_NATIONALITY_VALUE);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityValueIsInShouldWork() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityValue in DEFAULT_NATIONALITY_VALUE or UPDATED_NATIONALITY_VALUE
        defaultNationalityShouldBeFound("nationalityValue.in=" + DEFAULT_NATIONALITY_VALUE + "," + UPDATED_NATIONALITY_VALUE);

        // Get all the nationalityList where nationalityValue equals to UPDATED_NATIONALITY_VALUE
        defaultNationalityShouldNotBeFound("nationalityValue.in=" + UPDATED_NATIONALITY_VALUE);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityValue is not null
        defaultNationalityShouldBeFound("nationalityValue.specified=true");

        // Get all the nationalityList where nationalityValue is null
        defaultNationalityShouldNotBeFound("nationalityValue.specified=false");
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityValueContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityValue contains DEFAULT_NATIONALITY_VALUE
        defaultNationalityShouldBeFound("nationalityValue.contains=" + DEFAULT_NATIONALITY_VALUE);

        // Get all the nationalityList where nationalityValue contains UPDATED_NATIONALITY_VALUE
        defaultNationalityShouldNotBeFound("nationalityValue.contains=" + UPDATED_NATIONALITY_VALUE);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityValueNotContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityValue does not contain DEFAULT_NATIONALITY_VALUE
        defaultNationalityShouldNotBeFound("nationalityValue.doesNotContain=" + DEFAULT_NATIONALITY_VALUE);

        // Get all the nationalityList where nationalityValue does not contain UPDATED_NATIONALITY_VALUE
        defaultNationalityShouldBeFound("nationalityValue.doesNotContain=" + UPDATED_NATIONALITY_VALUE);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAbreviation equals to DEFAULT_NATIONALITY_ABREVIATION
        defaultNationalityShouldBeFound("nationalityAbreviation.equals=" + DEFAULT_NATIONALITY_ABREVIATION);

        // Get all the nationalityList where nationalityAbreviation equals to UPDATED_NATIONALITY_ABREVIATION
        defaultNationalityShouldNotBeFound("nationalityAbreviation.equals=" + UPDATED_NATIONALITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAbreviation in DEFAULT_NATIONALITY_ABREVIATION or UPDATED_NATIONALITY_ABREVIATION
        defaultNationalityShouldBeFound(
            "nationalityAbreviation.in=" + DEFAULT_NATIONALITY_ABREVIATION + "," + UPDATED_NATIONALITY_ABREVIATION
        );

        // Get all the nationalityList where nationalityAbreviation equals to UPDATED_NATIONALITY_ABREVIATION
        defaultNationalityShouldNotBeFound("nationalityAbreviation.in=" + UPDATED_NATIONALITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAbreviation is not null
        defaultNationalityShouldBeFound("nationalityAbreviation.specified=true");

        // Get all the nationalityList where nationalityAbreviation is null
        defaultNationalityShouldNotBeFound("nationalityAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAbreviationContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAbreviation contains DEFAULT_NATIONALITY_ABREVIATION
        defaultNationalityShouldBeFound("nationalityAbreviation.contains=" + DEFAULT_NATIONALITY_ABREVIATION);

        // Get all the nationalityList where nationalityAbreviation contains UPDATED_NATIONALITY_ABREVIATION
        defaultNationalityShouldNotBeFound("nationalityAbreviation.contains=" + UPDATED_NATIONALITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAbreviation does not contain DEFAULT_NATIONALITY_ABREVIATION
        defaultNationalityShouldNotBeFound("nationalityAbreviation.doesNotContain=" + DEFAULT_NATIONALITY_ABREVIATION);

        // Get all the nationalityList where nationalityAbreviation does not contain UPDATED_NATIONALITY_ABREVIATION
        defaultNationalityShouldBeFound("nationalityAbreviation.doesNotContain=" + UPDATED_NATIONALITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityDescription equals to DEFAULT_NATIONALITY_DESCRIPTION
        defaultNationalityShouldBeFound("nationalityDescription.equals=" + DEFAULT_NATIONALITY_DESCRIPTION);

        // Get all the nationalityList where nationalityDescription equals to UPDATED_NATIONALITY_DESCRIPTION
        defaultNationalityShouldNotBeFound("nationalityDescription.equals=" + UPDATED_NATIONALITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityDescription in DEFAULT_NATIONALITY_DESCRIPTION or UPDATED_NATIONALITY_DESCRIPTION
        defaultNationalityShouldBeFound(
            "nationalityDescription.in=" + DEFAULT_NATIONALITY_DESCRIPTION + "," + UPDATED_NATIONALITY_DESCRIPTION
        );

        // Get all the nationalityList where nationalityDescription equals to UPDATED_NATIONALITY_DESCRIPTION
        defaultNationalityShouldNotBeFound("nationalityDescription.in=" + UPDATED_NATIONALITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityDescription is not null
        defaultNationalityShouldBeFound("nationalityDescription.specified=true");

        // Get all the nationalityList where nationalityDescription is null
        defaultNationalityShouldNotBeFound("nationalityDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityDescriptionContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityDescription contains DEFAULT_NATIONALITY_DESCRIPTION
        defaultNationalityShouldBeFound("nationalityDescription.contains=" + DEFAULT_NATIONALITY_DESCRIPTION);

        // Get all the nationalityList where nationalityDescription contains UPDATED_NATIONALITY_DESCRIPTION
        defaultNationalityShouldNotBeFound("nationalityDescription.contains=" + UPDATED_NATIONALITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityDescription does not contain DEFAULT_NATIONALITY_DESCRIPTION
        defaultNationalityShouldNotBeFound("nationalityDescription.doesNotContain=" + DEFAULT_NATIONALITY_DESCRIPTION);

        // Get all the nationalityList where nationalityDescription does not contain UPDATED_NATIONALITY_DESCRIPTION
        defaultNationalityShouldBeFound("nationalityDescription.doesNotContain=" + UPDATED_NATIONALITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityParams equals to DEFAULT_NATIONALITY_PARAMS
        defaultNationalityShouldBeFound("nationalityParams.equals=" + DEFAULT_NATIONALITY_PARAMS);

        // Get all the nationalityList where nationalityParams equals to UPDATED_NATIONALITY_PARAMS
        defaultNationalityShouldNotBeFound("nationalityParams.equals=" + UPDATED_NATIONALITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityParamsIsInShouldWork() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityParams in DEFAULT_NATIONALITY_PARAMS or UPDATED_NATIONALITY_PARAMS
        defaultNationalityShouldBeFound("nationalityParams.in=" + DEFAULT_NATIONALITY_PARAMS + "," + UPDATED_NATIONALITY_PARAMS);

        // Get all the nationalityList where nationalityParams equals to UPDATED_NATIONALITY_PARAMS
        defaultNationalityShouldNotBeFound("nationalityParams.in=" + UPDATED_NATIONALITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityParams is not null
        defaultNationalityShouldBeFound("nationalityParams.specified=true");

        // Get all the nationalityList where nationalityParams is null
        defaultNationalityShouldNotBeFound("nationalityParams.specified=false");
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityParamsContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityParams contains DEFAULT_NATIONALITY_PARAMS
        defaultNationalityShouldBeFound("nationalityParams.contains=" + DEFAULT_NATIONALITY_PARAMS);

        // Get all the nationalityList where nationalityParams contains UPDATED_NATIONALITY_PARAMS
        defaultNationalityShouldNotBeFound("nationalityParams.contains=" + UPDATED_NATIONALITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityParamsNotContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityParams does not contain DEFAULT_NATIONALITY_PARAMS
        defaultNationalityShouldNotBeFound("nationalityParams.doesNotContain=" + DEFAULT_NATIONALITY_PARAMS);

        // Get all the nationalityList where nationalityParams does not contain UPDATED_NATIONALITY_PARAMS
        defaultNationalityShouldBeFound("nationalityParams.doesNotContain=" + UPDATED_NATIONALITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAttributs equals to DEFAULT_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldBeFound("nationalityAttributs.equals=" + DEFAULT_NATIONALITY_ATTRIBUTS);

        // Get all the nationalityList where nationalityAttributs equals to UPDATED_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldNotBeFound("nationalityAttributs.equals=" + UPDATED_NATIONALITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAttributs in DEFAULT_NATIONALITY_ATTRIBUTS or UPDATED_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldBeFound("nationalityAttributs.in=" + DEFAULT_NATIONALITY_ATTRIBUTS + "," + UPDATED_NATIONALITY_ATTRIBUTS);

        // Get all the nationalityList where nationalityAttributs equals to UPDATED_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldNotBeFound("nationalityAttributs.in=" + UPDATED_NATIONALITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAttributs is not null
        defaultNationalityShouldBeFound("nationalityAttributs.specified=true");

        // Get all the nationalityList where nationalityAttributs is null
        defaultNationalityShouldNotBeFound("nationalityAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAttributsContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAttributs contains DEFAULT_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldBeFound("nationalityAttributs.contains=" + DEFAULT_NATIONALITY_ATTRIBUTS);

        // Get all the nationalityList where nationalityAttributs contains UPDATED_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldNotBeFound("nationalityAttributs.contains=" + UPDATED_NATIONALITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityAttributs does not contain DEFAULT_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldNotBeFound("nationalityAttributs.doesNotContain=" + DEFAULT_NATIONALITY_ATTRIBUTS);

        // Get all the nationalityList where nationalityAttributs does not contain UPDATED_NATIONALITY_ATTRIBUTS
        defaultNationalityShouldBeFound("nationalityAttributs.doesNotContain=" + UPDATED_NATIONALITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityStatIsEqualToSomething() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityStat equals to DEFAULT_NATIONALITY_STAT
        defaultNationalityShouldBeFound("nationalityStat.equals=" + DEFAULT_NATIONALITY_STAT);

        // Get all the nationalityList where nationalityStat equals to UPDATED_NATIONALITY_STAT
        defaultNationalityShouldNotBeFound("nationalityStat.equals=" + UPDATED_NATIONALITY_STAT);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityStatIsInShouldWork() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityStat in DEFAULT_NATIONALITY_STAT or UPDATED_NATIONALITY_STAT
        defaultNationalityShouldBeFound("nationalityStat.in=" + DEFAULT_NATIONALITY_STAT + "," + UPDATED_NATIONALITY_STAT);

        // Get all the nationalityList where nationalityStat equals to UPDATED_NATIONALITY_STAT
        defaultNationalityShouldNotBeFound("nationalityStat.in=" + UPDATED_NATIONALITY_STAT);
    }

    @Test
    @Transactional
    void getAllNationalitiesByNationalityStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList where nationalityStat is not null
        defaultNationalityShouldBeFound("nationalityStat.specified=true");

        // Get all the nationalityList where nationalityStat is null
        defaultNationalityShouldNotBeFound("nationalityStat.specified=false");
    }

    @Test
    @Transactional
    void getAllNationalitiesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            nationalityRepository.saveAndFlush(nationality);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        nationality.addAccreditation(accreditation);
        nationalityRepository.saveAndFlush(nationality);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the nationalityList where accreditation equals to accreditationId
        defaultNationalityShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the nationalityList where accreditation equals to (accreditationId + 1)
        defaultNationalityShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNationalityShouldBeFound(String filter) throws Exception {
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=nationalityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].nationalityId").value(hasItem(nationality.getNationalityId().intValue())))
            .andExpect(jsonPath("$.[*].nationalityValue").value(hasItem(DEFAULT_NATIONALITY_VALUE)))
            .andExpect(jsonPath("$.[*].nationalityAbreviation").value(hasItem(DEFAULT_NATIONALITY_ABREVIATION)))
            .andExpect(jsonPath("$.[*].nationalityDescription").value(hasItem(DEFAULT_NATIONALITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nationalityFlagContentType").value(hasItem(DEFAULT_NATIONALITY_FLAG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nationalityFlag").value(hasItem(Base64Utils.encodeToString(DEFAULT_NATIONALITY_FLAG))))
            .andExpect(jsonPath("$.[*].nationalityParams").value(hasItem(DEFAULT_NATIONALITY_PARAMS)))
            .andExpect(jsonPath("$.[*].nationalityAttributs").value(hasItem(DEFAULT_NATIONALITY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].nationalityStat").value(hasItem(DEFAULT_NATIONALITY_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=nationalityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNationalityShouldNotBeFound(String filter) throws Exception {
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=nationalityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNationalityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=nationalityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNationality() throws Exception {
        // Get the nationality
        restNationalityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Update the nationality
        Nationality updatedNationality = nationalityRepository.findById(nationality.getNationalityId()).get();
        // Disconnect from session so that the updates on updatedNationality are not directly saved in db
        em.detach(updatedNationality);
        updatedNationality
            .nationalityValue(UPDATED_NATIONALITY_VALUE)
            .nationalityAbreviation(UPDATED_NATIONALITY_ABREVIATION)
            .nationalityDescription(UPDATED_NATIONALITY_DESCRIPTION)
            .nationalityFlag(UPDATED_NATIONALITY_FLAG)
            .nationalityFlagContentType(UPDATED_NATIONALITY_FLAG_CONTENT_TYPE)
            .nationalityParams(UPDATED_NATIONALITY_PARAMS)
            .nationalityAttributs(UPDATED_NATIONALITY_ATTRIBUTS)
            .nationalityStat(UPDATED_NATIONALITY_STAT);
        NationalityDTO nationalityDTO = nationalityMapper.toDto(updatedNationality);

        restNationalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nationalityDTO.getNationalityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getNationalityValue()).isEqualTo(UPDATED_NATIONALITY_VALUE);
        assertThat(testNationality.getNationalityAbreviation()).isEqualTo(UPDATED_NATIONALITY_ABREVIATION);
        assertThat(testNationality.getNationalityDescription()).isEqualTo(UPDATED_NATIONALITY_DESCRIPTION);
        assertThat(testNationality.getNationalityFlag()).isEqualTo(UPDATED_NATIONALITY_FLAG);
        assertThat(testNationality.getNationalityFlagContentType()).isEqualTo(UPDATED_NATIONALITY_FLAG_CONTENT_TYPE);
        assertThat(testNationality.getNationalityParams()).isEqualTo(UPDATED_NATIONALITY_PARAMS);
        assertThat(testNationality.getNationalityAttributs()).isEqualTo(UPDATED_NATIONALITY_ATTRIBUTS);
        assertThat(testNationality.getNationalityStat()).isEqualTo(UPDATED_NATIONALITY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setNationalityId(count.incrementAndGet());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nationalityDTO.getNationalityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setNationalityId(count.incrementAndGet());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setNationalityId(count.incrementAndGet());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nationalityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNationalityWithPatch() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Update the nationality using partial update
        Nationality partialUpdatedNationality = new Nationality();
        partialUpdatedNationality.setNationalityId(nationality.getNationalityId());

        partialUpdatedNationality
            .nationalityValue(UPDATED_NATIONALITY_VALUE)
            .nationalityAbreviation(UPDATED_NATIONALITY_ABREVIATION)
            .nationalityStat(UPDATED_NATIONALITY_STAT);

        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationality.getNationalityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNationality))
            )
            .andExpect(status().isOk());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getNationalityValue()).isEqualTo(UPDATED_NATIONALITY_VALUE);
        assertThat(testNationality.getNationalityAbreviation()).isEqualTo(UPDATED_NATIONALITY_ABREVIATION);
        assertThat(testNationality.getNationalityDescription()).isEqualTo(DEFAULT_NATIONALITY_DESCRIPTION);
        assertThat(testNationality.getNationalityFlag()).isEqualTo(DEFAULT_NATIONALITY_FLAG);
        assertThat(testNationality.getNationalityFlagContentType()).isEqualTo(DEFAULT_NATIONALITY_FLAG_CONTENT_TYPE);
        assertThat(testNationality.getNationalityParams()).isEqualTo(DEFAULT_NATIONALITY_PARAMS);
        assertThat(testNationality.getNationalityAttributs()).isEqualTo(DEFAULT_NATIONALITY_ATTRIBUTS);
        assertThat(testNationality.getNationalityStat()).isEqualTo(UPDATED_NATIONALITY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateNationalityWithPatch() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Update the nationality using partial update
        Nationality partialUpdatedNationality = new Nationality();
        partialUpdatedNationality.setNationalityId(nationality.getNationalityId());

        partialUpdatedNationality
            .nationalityValue(UPDATED_NATIONALITY_VALUE)
            .nationalityAbreviation(UPDATED_NATIONALITY_ABREVIATION)
            .nationalityDescription(UPDATED_NATIONALITY_DESCRIPTION)
            .nationalityFlag(UPDATED_NATIONALITY_FLAG)
            .nationalityFlagContentType(UPDATED_NATIONALITY_FLAG_CONTENT_TYPE)
            .nationalityParams(UPDATED_NATIONALITY_PARAMS)
            .nationalityAttributs(UPDATED_NATIONALITY_ATTRIBUTS)
            .nationalityStat(UPDATED_NATIONALITY_STAT);

        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationality.getNationalityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNationality))
            )
            .andExpect(status().isOk());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getNationalityValue()).isEqualTo(UPDATED_NATIONALITY_VALUE);
        assertThat(testNationality.getNationalityAbreviation()).isEqualTo(UPDATED_NATIONALITY_ABREVIATION);
        assertThat(testNationality.getNationalityDescription()).isEqualTo(UPDATED_NATIONALITY_DESCRIPTION);
        assertThat(testNationality.getNationalityFlag()).isEqualTo(UPDATED_NATIONALITY_FLAG);
        assertThat(testNationality.getNationalityFlagContentType()).isEqualTo(UPDATED_NATIONALITY_FLAG_CONTENT_TYPE);
        assertThat(testNationality.getNationalityParams()).isEqualTo(UPDATED_NATIONALITY_PARAMS);
        assertThat(testNationality.getNationalityAttributs()).isEqualTo(UPDATED_NATIONALITY_ATTRIBUTS);
        assertThat(testNationality.getNationalityStat()).isEqualTo(UPDATED_NATIONALITY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setNationalityId(count.incrementAndGet());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nationalityDTO.getNationalityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setNationalityId(count.incrementAndGet());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();
        nationality.setNationalityId(count.incrementAndGet());

        // Create the Nationality
        NationalityDTO nationalityDTO = nationalityMapper.toDto(nationality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationalityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nationalityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeDelete = nationalityRepository.findAll().size();

        // Delete the nationality
        restNationalityMockMvc
            .perform(delete(ENTITY_API_URL_ID, nationality.getNationalityId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
