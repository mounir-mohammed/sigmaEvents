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
import ma.sig.events.domain.Organiz;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.repository.CountryRepository;
import ma.sig.events.service.criteria.CountryCriteria;
import ma.sig.events.service.dto.CountryDTO;
import ma.sig.events.service.mapper.CountryMapper;
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
 * Integration tests for the {@link CountryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountryResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE_ALPHA_2 = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE_ALPHA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE_ALPHA_3 = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE_ALPHA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_TEL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_TEL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COUNTRY_FLAG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COUNTRY_FLAG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COUNTRY_FLAG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COUNTRY_FLAG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COUNTRY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COUNTRY_STAT = false;
    private static final Boolean UPDATED_COUNTRY_STAT = true;

    private static final String ENTITY_API_URL = "/api/countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{countryId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryMockMvc;

    private Country country;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .countryName(DEFAULT_COUNTRY_NAME)
            .countryCodeAlpha2(DEFAULT_COUNTRY_CODE_ALPHA_2)
            .countryCodeAlpha3(DEFAULT_COUNTRY_CODE_ALPHA_3)
            .countryTelCode(DEFAULT_COUNTRY_TEL_CODE)
            .countryDescription(DEFAULT_COUNTRY_DESCRIPTION)
            .countryFlag(DEFAULT_COUNTRY_FLAG)
            .countryFlagContentType(DEFAULT_COUNTRY_FLAG_CONTENT_TYPE)
            .countryParams(DEFAULT_COUNTRY_PARAMS)
            .countryAttributs(DEFAULT_COUNTRY_ATTRIBUTS)
            .countryStat(DEFAULT_COUNTRY_STAT);
        return country;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity(EntityManager em) {
        Country country = new Country()
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCodeAlpha2(UPDATED_COUNTRY_CODE_ALPHA_2)
            .countryCodeAlpha3(UPDATED_COUNTRY_CODE_ALPHA_3)
            .countryTelCode(UPDATED_COUNTRY_TEL_CODE)
            .countryDescription(UPDATED_COUNTRY_DESCRIPTION)
            .countryFlag(UPDATED_COUNTRY_FLAG)
            .countryFlagContentType(UPDATED_COUNTRY_FLAG_CONTENT_TYPE)
            .countryParams(UPDATED_COUNTRY_PARAMS)
            .countryAttributs(UPDATED_COUNTRY_ATTRIBUTS)
            .countryStat(UPDATED_COUNTRY_STAT);
        return country;
    }

    @BeforeEach
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();
        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);
        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountry.getCountryCodeAlpha2()).isEqualTo(DEFAULT_COUNTRY_CODE_ALPHA_2);
        assertThat(testCountry.getCountryCodeAlpha3()).isEqualTo(DEFAULT_COUNTRY_CODE_ALPHA_3);
        assertThat(testCountry.getCountryTelCode()).isEqualTo(DEFAULT_COUNTRY_TEL_CODE);
        assertThat(testCountry.getCountryDescription()).isEqualTo(DEFAULT_COUNTRY_DESCRIPTION);
        assertThat(testCountry.getCountryFlag()).isEqualTo(DEFAULT_COUNTRY_FLAG);
        assertThat(testCountry.getCountryFlagContentType()).isEqualTo(DEFAULT_COUNTRY_FLAG_CONTENT_TYPE);
        assertThat(testCountry.getCountryParams()).isEqualTo(DEFAULT_COUNTRY_PARAMS);
        assertThat(testCountry.getCountryAttributs()).isEqualTo(DEFAULT_COUNTRY_ATTRIBUTS);
        assertThat(testCountry.getCountryStat()).isEqualTo(DEFAULT_COUNTRY_STAT);
    }

    @Test
    @Transactional
    void createCountryWithExistingId() throws Exception {
        // Create the Country with an existing ID
        country.setCountryId(1L);
        CountryDTO countryDTO = countryMapper.toDto(country);

        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCountryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCountryName(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryCodeAlpha2IsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCountryCodeAlpha2(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=countryId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(country.getCountryId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryCodeAlpha2").value(hasItem(DEFAULT_COUNTRY_CODE_ALPHA_2)))
            .andExpect(jsonPath("$.[*].countryCodeAlpha3").value(hasItem(DEFAULT_COUNTRY_CODE_ALPHA_3)))
            .andExpect(jsonPath("$.[*].countryTelCode").value(hasItem(DEFAULT_COUNTRY_TEL_CODE)))
            .andExpect(jsonPath("$.[*].countryDescription").value(hasItem(DEFAULT_COUNTRY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].countryFlagContentType").value(hasItem(DEFAULT_COUNTRY_FLAG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].countryFlag").value(hasItem(Base64Utils.encodeToString(DEFAULT_COUNTRY_FLAG))))
            .andExpect(jsonPath("$.[*].countryParams").value(hasItem(DEFAULT_COUNTRY_PARAMS)))
            .andExpect(jsonPath("$.[*].countryAttributs").value(hasItem(DEFAULT_COUNTRY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].countryStat").value(hasItem(DEFAULT_COUNTRY_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc
            .perform(get(ENTITY_API_URL_ID, country.getCountryId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.countryId").value(country.getCountryId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.countryCodeAlpha2").value(DEFAULT_COUNTRY_CODE_ALPHA_2))
            .andExpect(jsonPath("$.countryCodeAlpha3").value(DEFAULT_COUNTRY_CODE_ALPHA_3))
            .andExpect(jsonPath("$.countryTelCode").value(DEFAULT_COUNTRY_TEL_CODE))
            .andExpect(jsonPath("$.countryDescription").value(DEFAULT_COUNTRY_DESCRIPTION))
            .andExpect(jsonPath("$.countryFlagContentType").value(DEFAULT_COUNTRY_FLAG_CONTENT_TYPE))
            .andExpect(jsonPath("$.countryFlag").value(Base64Utils.encodeToString(DEFAULT_COUNTRY_FLAG)))
            .andExpect(jsonPath("$.countryParams").value(DEFAULT_COUNTRY_PARAMS))
            .andExpect(jsonPath("$.countryAttributs").value(DEFAULT_COUNTRY_ATTRIBUTS))
            .andExpect(jsonPath("$.countryStat").value(DEFAULT_COUNTRY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCountriesByIdFiltering() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        Long id = country.getCountryId();

        defaultCountryShouldBeFound("countryId.equals=" + id);
        defaultCountryShouldNotBeFound("countryId.notEquals=" + id);

        defaultCountryShouldBeFound("countryId.greaterThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("countryId.greaterThan=" + id);

        defaultCountryShouldBeFound("countryId.lessThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("countryId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName equals to DEFAULT_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.equals=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.equals=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName in DEFAULT_COUNTRY_NAME or UPDATED_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.in=" + DEFAULT_COUNTRY_NAME + "," + UPDATED_COUNTRY_NAME);

        // Get all the countryList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.in=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName is not null
        defaultCountryShouldBeFound("countryName.specified=true");

        // Get all the countryList where countryName is null
        defaultCountryShouldNotBeFound("countryName.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName contains DEFAULT_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.contains=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName contains UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.contains=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName does not contain DEFAULT_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.doesNotContain=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName does not contain UPDATED_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.doesNotContain=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha2IsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha2 equals to DEFAULT_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldBeFound("countryCodeAlpha2.equals=" + DEFAULT_COUNTRY_CODE_ALPHA_2);

        // Get all the countryList where countryCodeAlpha2 equals to UPDATED_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldNotBeFound("countryCodeAlpha2.equals=" + UPDATED_COUNTRY_CODE_ALPHA_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha2IsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha2 in DEFAULT_COUNTRY_CODE_ALPHA_2 or UPDATED_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldBeFound("countryCodeAlpha2.in=" + DEFAULT_COUNTRY_CODE_ALPHA_2 + "," + UPDATED_COUNTRY_CODE_ALPHA_2);

        // Get all the countryList where countryCodeAlpha2 equals to UPDATED_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldNotBeFound("countryCodeAlpha2.in=" + UPDATED_COUNTRY_CODE_ALPHA_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha2IsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha2 is not null
        defaultCountryShouldBeFound("countryCodeAlpha2.specified=true");

        // Get all the countryList where countryCodeAlpha2 is null
        defaultCountryShouldNotBeFound("countryCodeAlpha2.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha2ContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha2 contains DEFAULT_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldBeFound("countryCodeAlpha2.contains=" + DEFAULT_COUNTRY_CODE_ALPHA_2);

        // Get all the countryList where countryCodeAlpha2 contains UPDATED_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldNotBeFound("countryCodeAlpha2.contains=" + UPDATED_COUNTRY_CODE_ALPHA_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha2NotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha2 does not contain DEFAULT_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldNotBeFound("countryCodeAlpha2.doesNotContain=" + DEFAULT_COUNTRY_CODE_ALPHA_2);

        // Get all the countryList where countryCodeAlpha2 does not contain UPDATED_COUNTRY_CODE_ALPHA_2
        defaultCountryShouldBeFound("countryCodeAlpha2.doesNotContain=" + UPDATED_COUNTRY_CODE_ALPHA_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha3IsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha3 equals to DEFAULT_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldBeFound("countryCodeAlpha3.equals=" + DEFAULT_COUNTRY_CODE_ALPHA_3);

        // Get all the countryList where countryCodeAlpha3 equals to UPDATED_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldNotBeFound("countryCodeAlpha3.equals=" + UPDATED_COUNTRY_CODE_ALPHA_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha3IsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha3 in DEFAULT_COUNTRY_CODE_ALPHA_3 or UPDATED_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldBeFound("countryCodeAlpha3.in=" + DEFAULT_COUNTRY_CODE_ALPHA_3 + "," + UPDATED_COUNTRY_CODE_ALPHA_3);

        // Get all the countryList where countryCodeAlpha3 equals to UPDATED_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldNotBeFound("countryCodeAlpha3.in=" + UPDATED_COUNTRY_CODE_ALPHA_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha3IsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha3 is not null
        defaultCountryShouldBeFound("countryCodeAlpha3.specified=true");

        // Get all the countryList where countryCodeAlpha3 is null
        defaultCountryShouldNotBeFound("countryCodeAlpha3.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha3ContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha3 contains DEFAULT_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldBeFound("countryCodeAlpha3.contains=" + DEFAULT_COUNTRY_CODE_ALPHA_3);

        // Get all the countryList where countryCodeAlpha3 contains UPDATED_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldNotBeFound("countryCodeAlpha3.contains=" + UPDATED_COUNTRY_CODE_ALPHA_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCodeAlpha3NotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCodeAlpha3 does not contain DEFAULT_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldNotBeFound("countryCodeAlpha3.doesNotContain=" + DEFAULT_COUNTRY_CODE_ALPHA_3);

        // Get all the countryList where countryCodeAlpha3 does not contain UPDATED_COUNTRY_CODE_ALPHA_3
        defaultCountryShouldBeFound("countryCodeAlpha3.doesNotContain=" + UPDATED_COUNTRY_CODE_ALPHA_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryTelCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelCode equals to DEFAULT_COUNTRY_TEL_CODE
        defaultCountryShouldBeFound("countryTelCode.equals=" + DEFAULT_COUNTRY_TEL_CODE);

        // Get all the countryList where countryTelCode equals to UPDATED_COUNTRY_TEL_CODE
        defaultCountryShouldNotBeFound("countryTelCode.equals=" + UPDATED_COUNTRY_TEL_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryTelCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelCode in DEFAULT_COUNTRY_TEL_CODE or UPDATED_COUNTRY_TEL_CODE
        defaultCountryShouldBeFound("countryTelCode.in=" + DEFAULT_COUNTRY_TEL_CODE + "," + UPDATED_COUNTRY_TEL_CODE);

        // Get all the countryList where countryTelCode equals to UPDATED_COUNTRY_TEL_CODE
        defaultCountryShouldNotBeFound("countryTelCode.in=" + UPDATED_COUNTRY_TEL_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryTelCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelCode is not null
        defaultCountryShouldBeFound("countryTelCode.specified=true");

        // Get all the countryList where countryTelCode is null
        defaultCountryShouldNotBeFound("countryTelCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryTelCodeContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelCode contains DEFAULT_COUNTRY_TEL_CODE
        defaultCountryShouldBeFound("countryTelCode.contains=" + DEFAULT_COUNTRY_TEL_CODE);

        // Get all the countryList where countryTelCode contains UPDATED_COUNTRY_TEL_CODE
        defaultCountryShouldNotBeFound("countryTelCode.contains=" + UPDATED_COUNTRY_TEL_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryTelCodeNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryTelCode does not contain DEFAULT_COUNTRY_TEL_CODE
        defaultCountryShouldNotBeFound("countryTelCode.doesNotContain=" + DEFAULT_COUNTRY_TEL_CODE);

        // Get all the countryList where countryTelCode does not contain UPDATED_COUNTRY_TEL_CODE
        defaultCountryShouldBeFound("countryTelCode.doesNotContain=" + UPDATED_COUNTRY_TEL_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryDescription equals to DEFAULT_COUNTRY_DESCRIPTION
        defaultCountryShouldBeFound("countryDescription.equals=" + DEFAULT_COUNTRY_DESCRIPTION);

        // Get all the countryList where countryDescription equals to UPDATED_COUNTRY_DESCRIPTION
        defaultCountryShouldNotBeFound("countryDescription.equals=" + UPDATED_COUNTRY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryDescription in DEFAULT_COUNTRY_DESCRIPTION or UPDATED_COUNTRY_DESCRIPTION
        defaultCountryShouldBeFound("countryDescription.in=" + DEFAULT_COUNTRY_DESCRIPTION + "," + UPDATED_COUNTRY_DESCRIPTION);

        // Get all the countryList where countryDescription equals to UPDATED_COUNTRY_DESCRIPTION
        defaultCountryShouldNotBeFound("countryDescription.in=" + UPDATED_COUNTRY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryDescription is not null
        defaultCountryShouldBeFound("countryDescription.specified=true");

        // Get all the countryList where countryDescription is null
        defaultCountryShouldNotBeFound("countryDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryDescriptionContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryDescription contains DEFAULT_COUNTRY_DESCRIPTION
        defaultCountryShouldBeFound("countryDescription.contains=" + DEFAULT_COUNTRY_DESCRIPTION);

        // Get all the countryList where countryDescription contains UPDATED_COUNTRY_DESCRIPTION
        defaultCountryShouldNotBeFound("countryDescription.contains=" + UPDATED_COUNTRY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryDescription does not contain DEFAULT_COUNTRY_DESCRIPTION
        defaultCountryShouldNotBeFound("countryDescription.doesNotContain=" + DEFAULT_COUNTRY_DESCRIPTION);

        // Get all the countryList where countryDescription does not contain UPDATED_COUNTRY_DESCRIPTION
        defaultCountryShouldBeFound("countryDescription.doesNotContain=" + UPDATED_COUNTRY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryParams equals to DEFAULT_COUNTRY_PARAMS
        defaultCountryShouldBeFound("countryParams.equals=" + DEFAULT_COUNTRY_PARAMS);

        // Get all the countryList where countryParams equals to UPDATED_COUNTRY_PARAMS
        defaultCountryShouldNotBeFound("countryParams.equals=" + UPDATED_COUNTRY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryParamsIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryParams in DEFAULT_COUNTRY_PARAMS or UPDATED_COUNTRY_PARAMS
        defaultCountryShouldBeFound("countryParams.in=" + DEFAULT_COUNTRY_PARAMS + "," + UPDATED_COUNTRY_PARAMS);

        // Get all the countryList where countryParams equals to UPDATED_COUNTRY_PARAMS
        defaultCountryShouldNotBeFound("countryParams.in=" + UPDATED_COUNTRY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryParams is not null
        defaultCountryShouldBeFound("countryParams.specified=true");

        // Get all the countryList where countryParams is null
        defaultCountryShouldNotBeFound("countryParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryParamsContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryParams contains DEFAULT_COUNTRY_PARAMS
        defaultCountryShouldBeFound("countryParams.contains=" + DEFAULT_COUNTRY_PARAMS);

        // Get all the countryList where countryParams contains UPDATED_COUNTRY_PARAMS
        defaultCountryShouldNotBeFound("countryParams.contains=" + UPDATED_COUNTRY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryParamsNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryParams does not contain DEFAULT_COUNTRY_PARAMS
        defaultCountryShouldNotBeFound("countryParams.doesNotContain=" + DEFAULT_COUNTRY_PARAMS);

        // Get all the countryList where countryParams does not contain UPDATED_COUNTRY_PARAMS
        defaultCountryShouldBeFound("countryParams.doesNotContain=" + UPDATED_COUNTRY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryAttributs equals to DEFAULT_COUNTRY_ATTRIBUTS
        defaultCountryShouldBeFound("countryAttributs.equals=" + DEFAULT_COUNTRY_ATTRIBUTS);

        // Get all the countryList where countryAttributs equals to UPDATED_COUNTRY_ATTRIBUTS
        defaultCountryShouldNotBeFound("countryAttributs.equals=" + UPDATED_COUNTRY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryAttributs in DEFAULT_COUNTRY_ATTRIBUTS or UPDATED_COUNTRY_ATTRIBUTS
        defaultCountryShouldBeFound("countryAttributs.in=" + DEFAULT_COUNTRY_ATTRIBUTS + "," + UPDATED_COUNTRY_ATTRIBUTS);

        // Get all the countryList where countryAttributs equals to UPDATED_COUNTRY_ATTRIBUTS
        defaultCountryShouldNotBeFound("countryAttributs.in=" + UPDATED_COUNTRY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryAttributs is not null
        defaultCountryShouldBeFound("countryAttributs.specified=true");

        // Get all the countryList where countryAttributs is null
        defaultCountryShouldNotBeFound("countryAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryAttributsContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryAttributs contains DEFAULT_COUNTRY_ATTRIBUTS
        defaultCountryShouldBeFound("countryAttributs.contains=" + DEFAULT_COUNTRY_ATTRIBUTS);

        // Get all the countryList where countryAttributs contains UPDATED_COUNTRY_ATTRIBUTS
        defaultCountryShouldNotBeFound("countryAttributs.contains=" + UPDATED_COUNTRY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryAttributs does not contain DEFAULT_COUNTRY_ATTRIBUTS
        defaultCountryShouldNotBeFound("countryAttributs.doesNotContain=" + DEFAULT_COUNTRY_ATTRIBUTS);

        // Get all the countryList where countryAttributs does not contain UPDATED_COUNTRY_ATTRIBUTS
        defaultCountryShouldBeFound("countryAttributs.doesNotContain=" + UPDATED_COUNTRY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryStatIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryStat equals to DEFAULT_COUNTRY_STAT
        defaultCountryShouldBeFound("countryStat.equals=" + DEFAULT_COUNTRY_STAT);

        // Get all the countryList where countryStat equals to UPDATED_COUNTRY_STAT
        defaultCountryShouldNotBeFound("countryStat.equals=" + UPDATED_COUNTRY_STAT);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryStatIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryStat in DEFAULT_COUNTRY_STAT or UPDATED_COUNTRY_STAT
        defaultCountryShouldBeFound("countryStat.in=" + DEFAULT_COUNTRY_STAT + "," + UPDATED_COUNTRY_STAT);

        // Get all the countryList where countryStat equals to UPDATED_COUNTRY_STAT
        defaultCountryShouldNotBeFound("countryStat.in=" + UPDATED_COUNTRY_STAT);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryStat is not null
        defaultCountryShouldBeFound("countryStat.specified=true");

        // Get all the countryList where countryStat is null
        defaultCountryShouldNotBeFound("countryStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            countryRepository.saveAndFlush(country);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        country.addPrintingCentre(printingCentre);
        countryRepository.saveAndFlush(country);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the countryList where printingCentre equals to printingCentreId
        defaultCountryShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the countryList where printingCentre equals to (printingCentreId + 1)
        defaultCountryShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    @Test
    @Transactional
    void getAllCountriesByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            countryRepository.saveAndFlush(country);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        country.addCity(city);
        countryRepository.saveAndFlush(country);
        Long cityId = city.getCityId();

        // Get all the countryList where city equals to cityId
        defaultCountryShouldBeFound("cityId.equals=" + cityId);

        // Get all the countryList where city equals to (cityId + 1)
        defaultCountryShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllCountriesByOrganizIsEqualToSomething() throws Exception {
        Organiz organiz;
        if (TestUtil.findAll(em, Organiz.class).isEmpty()) {
            countryRepository.saveAndFlush(country);
            organiz = OrganizResourceIT.createEntity(em);
        } else {
            organiz = TestUtil.findAll(em, Organiz.class).get(0);
        }
        em.persist(organiz);
        em.flush();
        country.addOrganiz(organiz);
        countryRepository.saveAndFlush(country);
        Long organizId = organiz.getOrganizId();

        // Get all the countryList where organiz equals to organizId
        defaultCountryShouldBeFound("organizId.equals=" + organizId);

        // Get all the countryList where organiz equals to (organizId + 1)
        defaultCountryShouldNotBeFound("organizId.equals=" + (organizId + 1));
    }

    @Test
    @Transactional
    void getAllCountriesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            countryRepository.saveAndFlush(country);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        country.addAccreditation(accreditation);
        countryRepository.saveAndFlush(country);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the countryList where accreditation equals to accreditationId
        defaultCountryShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the countryList where accreditation equals to (accreditationId + 1)
        defaultCountryShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryShouldBeFound(String filter) throws Exception {
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=countryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(country.getCountryId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryCodeAlpha2").value(hasItem(DEFAULT_COUNTRY_CODE_ALPHA_2)))
            .andExpect(jsonPath("$.[*].countryCodeAlpha3").value(hasItem(DEFAULT_COUNTRY_CODE_ALPHA_3)))
            .andExpect(jsonPath("$.[*].countryTelCode").value(hasItem(DEFAULT_COUNTRY_TEL_CODE)))
            .andExpect(jsonPath("$.[*].countryDescription").value(hasItem(DEFAULT_COUNTRY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].countryFlagContentType").value(hasItem(DEFAULT_COUNTRY_FLAG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].countryFlag").value(hasItem(Base64Utils.encodeToString(DEFAULT_COUNTRY_FLAG))))
            .andExpect(jsonPath("$.[*].countryParams").value(hasItem(DEFAULT_COUNTRY_PARAMS)))
            .andExpect(jsonPath("$.[*].countryAttributs").value(hasItem(DEFAULT_COUNTRY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].countryStat").value(hasItem(DEFAULT_COUNTRY_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=countryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryShouldNotBeFound(String filter) throws Exception {
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=countryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=countryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getCountryId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCodeAlpha2(UPDATED_COUNTRY_CODE_ALPHA_2)
            .countryCodeAlpha3(UPDATED_COUNTRY_CODE_ALPHA_3)
            .countryTelCode(UPDATED_COUNTRY_TEL_CODE)
            .countryDescription(UPDATED_COUNTRY_DESCRIPTION)
            .countryFlag(UPDATED_COUNTRY_FLAG)
            .countryFlagContentType(UPDATED_COUNTRY_FLAG_CONTENT_TYPE)
            .countryParams(UPDATED_COUNTRY_PARAMS)
            .countryAttributs(UPDATED_COUNTRY_ATTRIBUTS)
            .countryStat(UPDATED_COUNTRY_STAT);
        CountryDTO countryDTO = countryMapper.toDto(updatedCountry);

        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryDTO.getCountryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountry.getCountryCodeAlpha2()).isEqualTo(UPDATED_COUNTRY_CODE_ALPHA_2);
        assertThat(testCountry.getCountryCodeAlpha3()).isEqualTo(UPDATED_COUNTRY_CODE_ALPHA_3);
        assertThat(testCountry.getCountryTelCode()).isEqualTo(UPDATED_COUNTRY_TEL_CODE);
        assertThat(testCountry.getCountryDescription()).isEqualTo(UPDATED_COUNTRY_DESCRIPTION);
        assertThat(testCountry.getCountryFlag()).isEqualTo(UPDATED_COUNTRY_FLAG);
        assertThat(testCountry.getCountryFlagContentType()).isEqualTo(UPDATED_COUNTRY_FLAG_CONTENT_TYPE);
        assertThat(testCountry.getCountryParams()).isEqualTo(UPDATED_COUNTRY_PARAMS);
        assertThat(testCountry.getCountryAttributs()).isEqualTo(UPDATED_COUNTRY_ATTRIBUTS);
        assertThat(testCountry.getCountryStat()).isEqualTo(UPDATED_COUNTRY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setCountryId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryDTO.getCountryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setCountryId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setCountryId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountryWithPatch() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country using partial update
        Country partialUpdatedCountry = new Country();
        partialUpdatedCountry.setCountryId(country.getCountryId());

        partialUpdatedCountry
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCodeAlpha2(UPDATED_COUNTRY_CODE_ALPHA_2)
            .countryCodeAlpha3(UPDATED_COUNTRY_CODE_ALPHA_3)
            .countryTelCode(UPDATED_COUNTRY_TEL_CODE)
            .countryParams(UPDATED_COUNTRY_PARAMS)
            .countryAttributs(UPDATED_COUNTRY_ATTRIBUTS)
            .countryStat(UPDATED_COUNTRY_STAT);

        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountry.getCountryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountry))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountry.getCountryCodeAlpha2()).isEqualTo(UPDATED_COUNTRY_CODE_ALPHA_2);
        assertThat(testCountry.getCountryCodeAlpha3()).isEqualTo(UPDATED_COUNTRY_CODE_ALPHA_3);
        assertThat(testCountry.getCountryTelCode()).isEqualTo(UPDATED_COUNTRY_TEL_CODE);
        assertThat(testCountry.getCountryDescription()).isEqualTo(DEFAULT_COUNTRY_DESCRIPTION);
        assertThat(testCountry.getCountryFlag()).isEqualTo(DEFAULT_COUNTRY_FLAG);
        assertThat(testCountry.getCountryFlagContentType()).isEqualTo(DEFAULT_COUNTRY_FLAG_CONTENT_TYPE);
        assertThat(testCountry.getCountryParams()).isEqualTo(UPDATED_COUNTRY_PARAMS);
        assertThat(testCountry.getCountryAttributs()).isEqualTo(UPDATED_COUNTRY_ATTRIBUTS);
        assertThat(testCountry.getCountryStat()).isEqualTo(UPDATED_COUNTRY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCountryWithPatch() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country using partial update
        Country partialUpdatedCountry = new Country();
        partialUpdatedCountry.setCountryId(country.getCountryId());

        partialUpdatedCountry
            .countryName(UPDATED_COUNTRY_NAME)
            .countryCodeAlpha2(UPDATED_COUNTRY_CODE_ALPHA_2)
            .countryCodeAlpha3(UPDATED_COUNTRY_CODE_ALPHA_3)
            .countryTelCode(UPDATED_COUNTRY_TEL_CODE)
            .countryDescription(UPDATED_COUNTRY_DESCRIPTION)
            .countryFlag(UPDATED_COUNTRY_FLAG)
            .countryFlagContentType(UPDATED_COUNTRY_FLAG_CONTENT_TYPE)
            .countryParams(UPDATED_COUNTRY_PARAMS)
            .countryAttributs(UPDATED_COUNTRY_ATTRIBUTS)
            .countryStat(UPDATED_COUNTRY_STAT);

        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountry.getCountryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountry))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountry.getCountryCodeAlpha2()).isEqualTo(UPDATED_COUNTRY_CODE_ALPHA_2);
        assertThat(testCountry.getCountryCodeAlpha3()).isEqualTo(UPDATED_COUNTRY_CODE_ALPHA_3);
        assertThat(testCountry.getCountryTelCode()).isEqualTo(UPDATED_COUNTRY_TEL_CODE);
        assertThat(testCountry.getCountryDescription()).isEqualTo(UPDATED_COUNTRY_DESCRIPTION);
        assertThat(testCountry.getCountryFlag()).isEqualTo(UPDATED_COUNTRY_FLAG);
        assertThat(testCountry.getCountryFlagContentType()).isEqualTo(UPDATED_COUNTRY_FLAG_CONTENT_TYPE);
        assertThat(testCountry.getCountryParams()).isEqualTo(UPDATED_COUNTRY_PARAMS);
        assertThat(testCountry.getCountryAttributs()).isEqualTo(UPDATED_COUNTRY_ATTRIBUTS);
        assertThat(testCountry.getCountryStat()).isEqualTo(UPDATED_COUNTRY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setCountryId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countryDTO.getCountryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setCountryId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setCountryId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc
            .perform(delete(ENTITY_API_URL_ID, country.getCountryId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
