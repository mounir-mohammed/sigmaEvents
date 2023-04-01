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
import ma.sig.events.domain.Site;
import ma.sig.events.repository.CityRepository;
import ma.sig.events.service.criteria.CityCriteria;
import ma.sig.events.service.dto.CityDTO;
import ma.sig.events.service.mapper.CityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CityResourceIT {

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_CITY_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CITY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CITY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CITY_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CITY_STAT = false;
    private static final Boolean UPDATED_CITY_STAT = true;

    private static final String ENTITY_API_URL = "/api/cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{cityId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityMockMvc;

    private City city;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity(EntityManager em) {
        City city = new City()
            .cityName(DEFAULT_CITY_NAME)
            .cityZipCode(DEFAULT_CITY_ZIP_CODE)
            .cityAbreviation(DEFAULT_CITY_ABREVIATION)
            .cityDescription(DEFAULT_CITY_DESCRIPTION)
            .cityParams(DEFAULT_CITY_PARAMS)
            .cityAttributs(DEFAULT_CITY_ATTRIBUTS)
            .cityStat(DEFAULT_CITY_STAT);
        return city;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createUpdatedEntity(EntityManager em) {
        City city = new City()
            .cityName(UPDATED_CITY_NAME)
            .cityZipCode(UPDATED_CITY_ZIP_CODE)
            .cityAbreviation(UPDATED_CITY_ABREVIATION)
            .cityDescription(UPDATED_CITY_DESCRIPTION)
            .cityParams(UPDATED_CITY_PARAMS)
            .cityAttributs(UPDATED_CITY_ATTRIBUTS)
            .cityStat(UPDATED_CITY_STAT);
        return city;
    }

    @BeforeEach
    public void initTest() {
        city = createEntity(em);
    }

    @Test
    @Transactional
    void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();
        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);
        restCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCity.getCityZipCode()).isEqualTo(DEFAULT_CITY_ZIP_CODE);
        assertThat(testCity.getCityAbreviation()).isEqualTo(DEFAULT_CITY_ABREVIATION);
        assertThat(testCity.getCityDescription()).isEqualTo(DEFAULT_CITY_DESCRIPTION);
        assertThat(testCity.getCityParams()).isEqualTo(DEFAULT_CITY_PARAMS);
        assertThat(testCity.getCityAttributs()).isEqualTo(DEFAULT_CITY_ATTRIBUTS);
        assertThat(testCity.getCityStat()).isEqualTo(DEFAULT_CITY_STAT);
    }

    @Test
    @Transactional
    void createCityWithExistingId() throws Exception {
        // Create the City with an existing ID
        city.setCityId(1L);
        CityDTO cityDTO = cityMapper.toDto(city);

        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityRepository.findAll().size();
        // set the field null
        city.setCityName(null);

        // Create the City, which fails.
        CityDTO cityDTO = cityMapper.toDto(city);

        restCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityRepository.findAll().size();
        // set the field null
        city.setCityZipCode(null);

        // Create the City, which fails.
        CityDTO cityDTO = cityMapper.toDto(city);

        restCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList
        restCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=cityId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(city.getCityId().intValue())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].cityZipCode").value(hasItem(DEFAULT_CITY_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].cityAbreviation").value(hasItem(DEFAULT_CITY_ABREVIATION)))
            .andExpect(jsonPath("$.[*].cityDescription").value(hasItem(DEFAULT_CITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cityParams").value(hasItem(DEFAULT_CITY_PARAMS)))
            .andExpect(jsonPath("$.[*].cityAttributs").value(hasItem(DEFAULT_CITY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].cityStat").value(hasItem(DEFAULT_CITY_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get the city
        restCityMockMvc
            .perform(get(ENTITY_API_URL_ID, city.getCityId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.cityId").value(city.getCityId().intValue()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME))
            .andExpect(jsonPath("$.cityZipCode").value(DEFAULT_CITY_ZIP_CODE))
            .andExpect(jsonPath("$.cityAbreviation").value(DEFAULT_CITY_ABREVIATION))
            .andExpect(jsonPath("$.cityDescription").value(DEFAULT_CITY_DESCRIPTION))
            .andExpect(jsonPath("$.cityParams").value(DEFAULT_CITY_PARAMS))
            .andExpect(jsonPath("$.cityAttributs").value(DEFAULT_CITY_ATTRIBUTS))
            .andExpect(jsonPath("$.cityStat").value(DEFAULT_CITY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCitiesByIdFiltering() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        Long id = city.getCityId();

        defaultCityShouldBeFound("cityId.equals=" + id);
        defaultCityShouldNotBeFound("cityId.notEquals=" + id);

        defaultCityShouldBeFound("cityId.greaterThanOrEqual=" + id);
        defaultCityShouldNotBeFound("cityId.greaterThan=" + id);

        defaultCityShouldBeFound("cityId.lessThanOrEqual=" + id);
        defaultCityShouldNotBeFound("cityId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName equals to DEFAULT_CITY_NAME
        defaultCityShouldBeFound("cityName.equals=" + DEFAULT_CITY_NAME);

        // Get all the cityList where cityName equals to UPDATED_CITY_NAME
        defaultCityShouldNotBeFound("cityName.equals=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName in DEFAULT_CITY_NAME or UPDATED_CITY_NAME
        defaultCityShouldBeFound("cityName.in=" + DEFAULT_CITY_NAME + "," + UPDATED_CITY_NAME);

        // Get all the cityList where cityName equals to UPDATED_CITY_NAME
        defaultCityShouldNotBeFound("cityName.in=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName is not null
        defaultCityShouldBeFound("cityName.specified=true");

        // Get all the cityList where cityName is null
        defaultCityShouldNotBeFound("cityName.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName contains DEFAULT_CITY_NAME
        defaultCityShouldBeFound("cityName.contains=" + DEFAULT_CITY_NAME);

        // Get all the cityList where cityName contains UPDATED_CITY_NAME
        defaultCityShouldNotBeFound("cityName.contains=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameNotContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName does not contain DEFAULT_CITY_NAME
        defaultCityShouldNotBeFound("cityName.doesNotContain=" + DEFAULT_CITY_NAME);

        // Get all the cityList where cityName does not contain UPDATED_CITY_NAME
        defaultCityShouldBeFound("cityName.doesNotContain=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityZipCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityZipCode equals to DEFAULT_CITY_ZIP_CODE
        defaultCityShouldBeFound("cityZipCode.equals=" + DEFAULT_CITY_ZIP_CODE);

        // Get all the cityList where cityZipCode equals to UPDATED_CITY_ZIP_CODE
        defaultCityShouldNotBeFound("cityZipCode.equals=" + UPDATED_CITY_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllCitiesByCityZipCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityZipCode in DEFAULT_CITY_ZIP_CODE or UPDATED_CITY_ZIP_CODE
        defaultCityShouldBeFound("cityZipCode.in=" + DEFAULT_CITY_ZIP_CODE + "," + UPDATED_CITY_ZIP_CODE);

        // Get all the cityList where cityZipCode equals to UPDATED_CITY_ZIP_CODE
        defaultCityShouldNotBeFound("cityZipCode.in=" + UPDATED_CITY_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllCitiesByCityZipCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityZipCode is not null
        defaultCityShouldBeFound("cityZipCode.specified=true");

        // Get all the cityList where cityZipCode is null
        defaultCityShouldNotBeFound("cityZipCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByCityZipCodeContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityZipCode contains DEFAULT_CITY_ZIP_CODE
        defaultCityShouldBeFound("cityZipCode.contains=" + DEFAULT_CITY_ZIP_CODE);

        // Get all the cityList where cityZipCode contains UPDATED_CITY_ZIP_CODE
        defaultCityShouldNotBeFound("cityZipCode.contains=" + UPDATED_CITY_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllCitiesByCityZipCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityZipCode does not contain DEFAULT_CITY_ZIP_CODE
        defaultCityShouldNotBeFound("cityZipCode.doesNotContain=" + DEFAULT_CITY_ZIP_CODE);

        // Get all the cityList where cityZipCode does not contain UPDATED_CITY_ZIP_CODE
        defaultCityShouldBeFound("cityZipCode.doesNotContain=" + UPDATED_CITY_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAbreviation equals to DEFAULT_CITY_ABREVIATION
        defaultCityShouldBeFound("cityAbreviation.equals=" + DEFAULT_CITY_ABREVIATION);

        // Get all the cityList where cityAbreviation equals to UPDATED_CITY_ABREVIATION
        defaultCityShouldNotBeFound("cityAbreviation.equals=" + UPDATED_CITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAbreviation in DEFAULT_CITY_ABREVIATION or UPDATED_CITY_ABREVIATION
        defaultCityShouldBeFound("cityAbreviation.in=" + DEFAULT_CITY_ABREVIATION + "," + UPDATED_CITY_ABREVIATION);

        // Get all the cityList where cityAbreviation equals to UPDATED_CITY_ABREVIATION
        defaultCityShouldNotBeFound("cityAbreviation.in=" + UPDATED_CITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAbreviation is not null
        defaultCityShouldBeFound("cityAbreviation.specified=true");

        // Get all the cityList where cityAbreviation is null
        defaultCityShouldNotBeFound("cityAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByCityAbreviationContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAbreviation contains DEFAULT_CITY_ABREVIATION
        defaultCityShouldBeFound("cityAbreviation.contains=" + DEFAULT_CITY_ABREVIATION);

        // Get all the cityList where cityAbreviation contains UPDATED_CITY_ABREVIATION
        defaultCityShouldNotBeFound("cityAbreviation.contains=" + UPDATED_CITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAbreviation does not contain DEFAULT_CITY_ABREVIATION
        defaultCityShouldNotBeFound("cityAbreviation.doesNotContain=" + DEFAULT_CITY_ABREVIATION);

        // Get all the cityList where cityAbreviation does not contain UPDATED_CITY_ABREVIATION
        defaultCityShouldBeFound("cityAbreviation.doesNotContain=" + UPDATED_CITY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityDescription equals to DEFAULT_CITY_DESCRIPTION
        defaultCityShouldBeFound("cityDescription.equals=" + DEFAULT_CITY_DESCRIPTION);

        // Get all the cityList where cityDescription equals to UPDATED_CITY_DESCRIPTION
        defaultCityShouldNotBeFound("cityDescription.equals=" + UPDATED_CITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityDescription in DEFAULT_CITY_DESCRIPTION or UPDATED_CITY_DESCRIPTION
        defaultCityShouldBeFound("cityDescription.in=" + DEFAULT_CITY_DESCRIPTION + "," + UPDATED_CITY_DESCRIPTION);

        // Get all the cityList where cityDescription equals to UPDATED_CITY_DESCRIPTION
        defaultCityShouldNotBeFound("cityDescription.in=" + UPDATED_CITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityDescription is not null
        defaultCityShouldBeFound("cityDescription.specified=true");

        // Get all the cityList where cityDescription is null
        defaultCityShouldNotBeFound("cityDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByCityDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityDescription contains DEFAULT_CITY_DESCRIPTION
        defaultCityShouldBeFound("cityDescription.contains=" + DEFAULT_CITY_DESCRIPTION);

        // Get all the cityList where cityDescription contains UPDATED_CITY_DESCRIPTION
        defaultCityShouldNotBeFound("cityDescription.contains=" + UPDATED_CITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityDescription does not contain DEFAULT_CITY_DESCRIPTION
        defaultCityShouldNotBeFound("cityDescription.doesNotContain=" + DEFAULT_CITY_DESCRIPTION);

        // Get all the cityList where cityDescription does not contain UPDATED_CITY_DESCRIPTION
        defaultCityShouldBeFound("cityDescription.doesNotContain=" + UPDATED_CITY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitiesByCityParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityParams equals to DEFAULT_CITY_PARAMS
        defaultCityShouldBeFound("cityParams.equals=" + DEFAULT_CITY_PARAMS);

        // Get all the cityList where cityParams equals to UPDATED_CITY_PARAMS
        defaultCityShouldNotBeFound("cityParams.equals=" + UPDATED_CITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityParamsIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityParams in DEFAULT_CITY_PARAMS or UPDATED_CITY_PARAMS
        defaultCityShouldBeFound("cityParams.in=" + DEFAULT_CITY_PARAMS + "," + UPDATED_CITY_PARAMS);

        // Get all the cityList where cityParams equals to UPDATED_CITY_PARAMS
        defaultCityShouldNotBeFound("cityParams.in=" + UPDATED_CITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityParams is not null
        defaultCityShouldBeFound("cityParams.specified=true");

        // Get all the cityList where cityParams is null
        defaultCityShouldNotBeFound("cityParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByCityParamsContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityParams contains DEFAULT_CITY_PARAMS
        defaultCityShouldBeFound("cityParams.contains=" + DEFAULT_CITY_PARAMS);

        // Get all the cityList where cityParams contains UPDATED_CITY_PARAMS
        defaultCityShouldNotBeFound("cityParams.contains=" + UPDATED_CITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityParamsNotContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityParams does not contain DEFAULT_CITY_PARAMS
        defaultCityShouldNotBeFound("cityParams.doesNotContain=" + DEFAULT_CITY_PARAMS);

        // Get all the cityList where cityParams does not contain UPDATED_CITY_PARAMS
        defaultCityShouldBeFound("cityParams.doesNotContain=" + UPDATED_CITY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAttributs equals to DEFAULT_CITY_ATTRIBUTS
        defaultCityShouldBeFound("cityAttributs.equals=" + DEFAULT_CITY_ATTRIBUTS);

        // Get all the cityList where cityAttributs equals to UPDATED_CITY_ATTRIBUTS
        defaultCityShouldNotBeFound("cityAttributs.equals=" + UPDATED_CITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAttributs in DEFAULT_CITY_ATTRIBUTS or UPDATED_CITY_ATTRIBUTS
        defaultCityShouldBeFound("cityAttributs.in=" + DEFAULT_CITY_ATTRIBUTS + "," + UPDATED_CITY_ATTRIBUTS);

        // Get all the cityList where cityAttributs equals to UPDATED_CITY_ATTRIBUTS
        defaultCityShouldNotBeFound("cityAttributs.in=" + UPDATED_CITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAttributs is not null
        defaultCityShouldBeFound("cityAttributs.specified=true");

        // Get all the cityList where cityAttributs is null
        defaultCityShouldNotBeFound("cityAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByCityAttributsContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAttributs contains DEFAULT_CITY_ATTRIBUTS
        defaultCityShouldBeFound("cityAttributs.contains=" + DEFAULT_CITY_ATTRIBUTS);

        // Get all the cityList where cityAttributs contains UPDATED_CITY_ATTRIBUTS
        defaultCityShouldNotBeFound("cityAttributs.contains=" + UPDATED_CITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityAttributs does not contain DEFAULT_CITY_ATTRIBUTS
        defaultCityShouldNotBeFound("cityAttributs.doesNotContain=" + DEFAULT_CITY_ATTRIBUTS);

        // Get all the cityList where cityAttributs does not contain UPDATED_CITY_ATTRIBUTS
        defaultCityShouldBeFound("cityAttributs.doesNotContain=" + UPDATED_CITY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCitiesByCityStatIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityStat equals to DEFAULT_CITY_STAT
        defaultCityShouldBeFound("cityStat.equals=" + DEFAULT_CITY_STAT);

        // Get all the cityList where cityStat equals to UPDATED_CITY_STAT
        defaultCityShouldNotBeFound("cityStat.equals=" + UPDATED_CITY_STAT);
    }

    @Test
    @Transactional
    void getAllCitiesByCityStatIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityStat in DEFAULT_CITY_STAT or UPDATED_CITY_STAT
        defaultCityShouldBeFound("cityStat.in=" + DEFAULT_CITY_STAT + "," + UPDATED_CITY_STAT);

        // Get all the cityList where cityStat equals to UPDATED_CITY_STAT
        defaultCityShouldNotBeFound("cityStat.in=" + UPDATED_CITY_STAT);
    }

    @Test
    @Transactional
    void getAllCitiesByCityStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityStat is not null
        defaultCityShouldBeFound("cityStat.specified=true");

        // Get all the cityList where cityStat is null
        defaultCityShouldNotBeFound("cityStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            cityRepository.saveAndFlush(city);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        city.addPrintingCentre(printingCentre);
        cityRepository.saveAndFlush(city);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the cityList where printingCentre equals to printingCentreId
        defaultCityShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the cityList where printingCentre equals to (printingCentreId + 1)
        defaultCityShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesBySiteIsEqualToSomething() throws Exception {
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            cityRepository.saveAndFlush(city);
            site = SiteResourceIT.createEntity(em);
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(site);
        em.flush();
        city.addSite(site);
        cityRepository.saveAndFlush(city);
        Long siteId = site.getSiteId();

        // Get all the cityList where site equals to siteId
        defaultCityShouldBeFound("siteId.equals=" + siteId);

        // Get all the cityList where site equals to (siteId + 1)
        defaultCityShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByOrganizIsEqualToSomething() throws Exception {
        Organiz organiz;
        if (TestUtil.findAll(em, Organiz.class).isEmpty()) {
            cityRepository.saveAndFlush(city);
            organiz = OrganizResourceIT.createEntity(em);
        } else {
            organiz = TestUtil.findAll(em, Organiz.class).get(0);
        }
        em.persist(organiz);
        em.flush();
        city.addOrganiz(organiz);
        cityRepository.saveAndFlush(city);
        Long organizId = organiz.getOrganizId();

        // Get all the cityList where organiz equals to organizId
        defaultCityShouldBeFound("organizId.equals=" + organizId);

        // Get all the cityList where organiz equals to (organizId + 1)
        defaultCityShouldNotBeFound("organizId.equals=" + (organizId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            cityRepository.saveAndFlush(city);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        city.addAccreditation(accreditation);
        cityRepository.saveAndFlush(city);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the cityList where accreditation equals to accreditationId
        defaultCityShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the cityList where accreditation equals to (accreditationId + 1)
        defaultCityShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            cityRepository.saveAndFlush(city);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        city.setCountry(country);
        cityRepository.saveAndFlush(city);
        Long countryId = country.getCountryId();

        // Get all the cityList where country equals to countryId
        defaultCityShouldBeFound("countryId.equals=" + countryId);

        // Get all the cityList where country equals to (countryId + 1)
        defaultCityShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCityShouldBeFound(String filter) throws Exception {
        restCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=cityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(city.getCityId().intValue())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].cityZipCode").value(hasItem(DEFAULT_CITY_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].cityAbreviation").value(hasItem(DEFAULT_CITY_ABREVIATION)))
            .andExpect(jsonPath("$.[*].cityDescription").value(hasItem(DEFAULT_CITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cityParams").value(hasItem(DEFAULT_CITY_PARAMS)))
            .andExpect(jsonPath("$.[*].cityAttributs").value(hasItem(DEFAULT_CITY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].cityStat").value(hasItem(DEFAULT_CITY_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restCityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=cityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCityShouldNotBeFound(String filter) throws Exception {
        restCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=cityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=cityId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findById(city.getCityId()).get();
        // Disconnect from session so that the updates on updatedCity are not directly saved in db
        em.detach(updatedCity);
        updatedCity
            .cityName(UPDATED_CITY_NAME)
            .cityZipCode(UPDATED_CITY_ZIP_CODE)
            .cityAbreviation(UPDATED_CITY_ABREVIATION)
            .cityDescription(UPDATED_CITY_DESCRIPTION)
            .cityParams(UPDATED_CITY_PARAMS)
            .cityAttributs(UPDATED_CITY_ATTRIBUTS)
            .cityStat(UPDATED_CITY_STAT);
        CityDTO cityDTO = cityMapper.toDto(updatedCity);

        restCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityDTO.getCityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCity.getCityZipCode()).isEqualTo(UPDATED_CITY_ZIP_CODE);
        assertThat(testCity.getCityAbreviation()).isEqualTo(UPDATED_CITY_ABREVIATION);
        assertThat(testCity.getCityDescription()).isEqualTo(UPDATED_CITY_DESCRIPTION);
        assertThat(testCity.getCityParams()).isEqualTo(UPDATED_CITY_PARAMS);
        assertThat(testCity.getCityAttributs()).isEqualTo(UPDATED_CITY_ATTRIBUTS);
        assertThat(testCity.getCityStat()).isEqualTo(UPDATED_CITY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setCityId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityDTO.getCityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setCityId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setCityId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCityWithPatch() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city using partial update
        City partialUpdatedCity = new City();
        partialUpdatedCity.setCityId(city.getCityId());

        partialUpdatedCity.cityZipCode(UPDATED_CITY_ZIP_CODE).cityParams(UPDATED_CITY_PARAMS).cityAttributs(UPDATED_CITY_ATTRIBUTS);

        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCity.getCityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCity))
            )
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCity.getCityZipCode()).isEqualTo(UPDATED_CITY_ZIP_CODE);
        assertThat(testCity.getCityAbreviation()).isEqualTo(DEFAULT_CITY_ABREVIATION);
        assertThat(testCity.getCityDescription()).isEqualTo(DEFAULT_CITY_DESCRIPTION);
        assertThat(testCity.getCityParams()).isEqualTo(UPDATED_CITY_PARAMS);
        assertThat(testCity.getCityAttributs()).isEqualTo(UPDATED_CITY_ATTRIBUTS);
        assertThat(testCity.getCityStat()).isEqualTo(DEFAULT_CITY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCityWithPatch() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city using partial update
        City partialUpdatedCity = new City();
        partialUpdatedCity.setCityId(city.getCityId());

        partialUpdatedCity
            .cityName(UPDATED_CITY_NAME)
            .cityZipCode(UPDATED_CITY_ZIP_CODE)
            .cityAbreviation(UPDATED_CITY_ABREVIATION)
            .cityDescription(UPDATED_CITY_DESCRIPTION)
            .cityParams(UPDATED_CITY_PARAMS)
            .cityAttributs(UPDATED_CITY_ATTRIBUTS)
            .cityStat(UPDATED_CITY_STAT);

        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCity.getCityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCity))
            )
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCity.getCityZipCode()).isEqualTo(UPDATED_CITY_ZIP_CODE);
        assertThat(testCity.getCityAbreviation()).isEqualTo(UPDATED_CITY_ABREVIATION);
        assertThat(testCity.getCityDescription()).isEqualTo(UPDATED_CITY_DESCRIPTION);
        assertThat(testCity.getCityParams()).isEqualTo(UPDATED_CITY_PARAMS);
        assertThat(testCity.getCityAttributs()).isEqualTo(UPDATED_CITY_ATTRIBUTS);
        assertThat(testCity.getCityStat()).isEqualTo(UPDATED_CITY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setCityId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityDTO.getCityId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setCityId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setCityId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Delete the city
        restCityMockMvc
            .perform(delete(ENTITY_API_URL_ID, city.getCityId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
