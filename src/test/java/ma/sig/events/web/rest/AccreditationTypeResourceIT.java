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
import ma.sig.events.domain.AccreditationType;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.repository.AccreditationTypeRepository;
import ma.sig.events.service.criteria.AccreditationTypeCriteria;
import ma.sig.events.service.dto.AccreditationTypeDTO;
import ma.sig.events.service.mapper.AccreditationTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccreditationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccreditationTypeResourceIT {

    private static final String DEFAULT_ACCREDITATION_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_TYPE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_TYPE_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_TYPE_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCREDITATION_TYPE_STAT = false;
    private static final Boolean UPDATED_ACCREDITATION_TYPE_STAT = true;

    private static final String ENTITY_API_URL = "/api/accreditation-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{accreditationTypeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccreditationTypeRepository accreditationTypeRepository;

    @Autowired
    private AccreditationTypeMapper accreditationTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccreditationTypeMockMvc;

    private AccreditationType accreditationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccreditationType createEntity(EntityManager em) {
        AccreditationType accreditationType = new AccreditationType()
            .accreditationTypeValue(DEFAULT_ACCREDITATION_TYPE_VALUE)
            .accreditationTypeAbreviation(DEFAULT_ACCREDITATION_TYPE_ABREVIATION)
            .accreditationTypeDescription(DEFAULT_ACCREDITATION_TYPE_DESCRIPTION)
            .accreditationTypeParams(DEFAULT_ACCREDITATION_TYPE_PARAMS)
            .accreditationTypeAttributs(DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS)
            .accreditationTypeStat(DEFAULT_ACCREDITATION_TYPE_STAT);
        return accreditationType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccreditationType createUpdatedEntity(EntityManager em) {
        AccreditationType accreditationType = new AccreditationType()
            .accreditationTypeValue(UPDATED_ACCREDITATION_TYPE_VALUE)
            .accreditationTypeAbreviation(UPDATED_ACCREDITATION_TYPE_ABREVIATION)
            .accreditationTypeDescription(UPDATED_ACCREDITATION_TYPE_DESCRIPTION)
            .accreditationTypeParams(UPDATED_ACCREDITATION_TYPE_PARAMS)
            .accreditationTypeAttributs(UPDATED_ACCREDITATION_TYPE_ATTRIBUTS)
            .accreditationTypeStat(UPDATED_ACCREDITATION_TYPE_STAT);
        return accreditationType;
    }

    @BeforeEach
    public void initTest() {
        accreditationType = createEntity(em);
    }

    @Test
    @Transactional
    void createAccreditationType() throws Exception {
        int databaseSizeBeforeCreate = accreditationTypeRepository.findAll().size();
        // Create the AccreditationType
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);
        restAccreditationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AccreditationType testAccreditationType = accreditationTypeList.get(accreditationTypeList.size() - 1);
        assertThat(testAccreditationType.getAccreditationTypeValue()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_VALUE);
        assertThat(testAccreditationType.getAccreditationTypeAbreviation()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_ABREVIATION);
        assertThat(testAccreditationType.getAccreditationTypeDescription()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_DESCRIPTION);
        assertThat(testAccreditationType.getAccreditationTypeParams()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_PARAMS);
        assertThat(testAccreditationType.getAccreditationTypeAttributs()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS);
        assertThat(testAccreditationType.getAccreditationTypeStat()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void createAccreditationTypeWithExistingId() throws Exception {
        // Create the AccreditationType with an existing ID
        accreditationType.setAccreditationTypeId(1L);
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        int databaseSizeBeforeCreate = accreditationTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccreditationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccreditationTypeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationTypeRepository.findAll().size();
        // set the field null
        accreditationType.setAccreditationTypeValue(null);

        // Create the AccreditationType, which fails.
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        restAccreditationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccreditationTypes() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList
        restAccreditationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accreditationTypeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].accreditationTypeId").value(hasItem(accreditationType.getAccreditationTypeId().intValue())))
            .andExpect(jsonPath("$.[*].accreditationTypeValue").value(hasItem(DEFAULT_ACCREDITATION_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].accreditationTypeAbreviation").value(hasItem(DEFAULT_ACCREDITATION_TYPE_ABREVIATION)))
            .andExpect(jsonPath("$.[*].accreditationTypeDescription").value(hasItem(DEFAULT_ACCREDITATION_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].accreditationTypeParams").value(hasItem(DEFAULT_ACCREDITATION_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].accreditationTypeAttributs").value(hasItem(DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].accreditationTypeStat").value(hasItem(DEFAULT_ACCREDITATION_TYPE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getAccreditationType() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get the accreditationType
        restAccreditationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, accreditationType.getAccreditationTypeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.accreditationTypeId").value(accreditationType.getAccreditationTypeId().intValue()))
            .andExpect(jsonPath("$.accreditationTypeValue").value(DEFAULT_ACCREDITATION_TYPE_VALUE))
            .andExpect(jsonPath("$.accreditationTypeAbreviation").value(DEFAULT_ACCREDITATION_TYPE_ABREVIATION))
            .andExpect(jsonPath("$.accreditationTypeDescription").value(DEFAULT_ACCREDITATION_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.accreditationTypeParams").value(DEFAULT_ACCREDITATION_TYPE_PARAMS))
            .andExpect(jsonPath("$.accreditationTypeAttributs").value(DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.accreditationTypeStat").value(DEFAULT_ACCREDITATION_TYPE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getAccreditationTypesByIdFiltering() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        Long id = accreditationType.getAccreditationTypeId();

        defaultAccreditationTypeShouldBeFound("accreditationTypeId.equals=" + id);
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeId.notEquals=" + id);

        defaultAccreditationTypeShouldBeFound("accreditationTypeId.greaterThanOrEqual=" + id);
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeId.greaterThan=" + id);

        defaultAccreditationTypeShouldBeFound("accreditationTypeId.lessThanOrEqual=" + id);
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeValue equals to DEFAULT_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldBeFound("accreditationTypeValue.equals=" + DEFAULT_ACCREDITATION_TYPE_VALUE);

        // Get all the accreditationTypeList where accreditationTypeValue equals to UPDATED_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeValue.equals=" + UPDATED_ACCREDITATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeValueIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeValue in DEFAULT_ACCREDITATION_TYPE_VALUE or UPDATED_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldBeFound(
            "accreditationTypeValue.in=" + DEFAULT_ACCREDITATION_TYPE_VALUE + "," + UPDATED_ACCREDITATION_TYPE_VALUE
        );

        // Get all the accreditationTypeList where accreditationTypeValue equals to UPDATED_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeValue.in=" + UPDATED_ACCREDITATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeValue is not null
        defaultAccreditationTypeShouldBeFound("accreditationTypeValue.specified=true");

        // Get all the accreditationTypeList where accreditationTypeValue is null
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeValueContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeValue contains DEFAULT_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldBeFound("accreditationTypeValue.contains=" + DEFAULT_ACCREDITATION_TYPE_VALUE);

        // Get all the accreditationTypeList where accreditationTypeValue contains UPDATED_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeValue.contains=" + UPDATED_ACCREDITATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeValueNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeValue does not contain DEFAULT_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeValue.doesNotContain=" + DEFAULT_ACCREDITATION_TYPE_VALUE);

        // Get all the accreditationTypeList where accreditationTypeValue does not contain UPDATED_ACCREDITATION_TYPE_VALUE
        defaultAccreditationTypeShouldBeFound("accreditationTypeValue.doesNotContain=" + UPDATED_ACCREDITATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAbreviation equals to DEFAULT_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldBeFound("accreditationTypeAbreviation.equals=" + DEFAULT_ACCREDITATION_TYPE_ABREVIATION);

        // Get all the accreditationTypeList where accreditationTypeAbreviation equals to UPDATED_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAbreviation.equals=" + UPDATED_ACCREDITATION_TYPE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAbreviation in DEFAULT_ACCREDITATION_TYPE_ABREVIATION or UPDATED_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldBeFound(
            "accreditationTypeAbreviation.in=" + DEFAULT_ACCREDITATION_TYPE_ABREVIATION + "," + UPDATED_ACCREDITATION_TYPE_ABREVIATION
        );

        // Get all the accreditationTypeList where accreditationTypeAbreviation equals to UPDATED_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAbreviation.in=" + UPDATED_ACCREDITATION_TYPE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAbreviation is not null
        defaultAccreditationTypeShouldBeFound("accreditationTypeAbreviation.specified=true");

        // Get all the accreditationTypeList where accreditationTypeAbreviation is null
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAbreviationContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAbreviation contains DEFAULT_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldBeFound("accreditationTypeAbreviation.contains=" + DEFAULT_ACCREDITATION_TYPE_ABREVIATION);

        // Get all the accreditationTypeList where accreditationTypeAbreviation contains UPDATED_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAbreviation.contains=" + UPDATED_ACCREDITATION_TYPE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAbreviation does not contain DEFAULT_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAbreviation.doesNotContain=" + DEFAULT_ACCREDITATION_TYPE_ABREVIATION);

        // Get all the accreditationTypeList where accreditationTypeAbreviation does not contain UPDATED_ACCREDITATION_TYPE_ABREVIATION
        defaultAccreditationTypeShouldBeFound("accreditationTypeAbreviation.doesNotContain=" + UPDATED_ACCREDITATION_TYPE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeDescription equals to DEFAULT_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldBeFound("accreditationTypeDescription.equals=" + DEFAULT_ACCREDITATION_TYPE_DESCRIPTION);

        // Get all the accreditationTypeList where accreditationTypeDescription equals to UPDATED_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeDescription.equals=" + UPDATED_ACCREDITATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeDescription in DEFAULT_ACCREDITATION_TYPE_DESCRIPTION or UPDATED_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldBeFound(
            "accreditationTypeDescription.in=" + DEFAULT_ACCREDITATION_TYPE_DESCRIPTION + "," + UPDATED_ACCREDITATION_TYPE_DESCRIPTION
        );

        // Get all the accreditationTypeList where accreditationTypeDescription equals to UPDATED_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeDescription.in=" + UPDATED_ACCREDITATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeDescription is not null
        defaultAccreditationTypeShouldBeFound("accreditationTypeDescription.specified=true");

        // Get all the accreditationTypeList where accreditationTypeDescription is null
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeDescription contains DEFAULT_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldBeFound("accreditationTypeDescription.contains=" + DEFAULT_ACCREDITATION_TYPE_DESCRIPTION);

        // Get all the accreditationTypeList where accreditationTypeDescription contains UPDATED_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeDescription.contains=" + UPDATED_ACCREDITATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeDescription does not contain DEFAULT_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeDescription.doesNotContain=" + DEFAULT_ACCREDITATION_TYPE_DESCRIPTION);

        // Get all the accreditationTypeList where accreditationTypeDescription does not contain UPDATED_ACCREDITATION_TYPE_DESCRIPTION
        defaultAccreditationTypeShouldBeFound("accreditationTypeDescription.doesNotContain=" + UPDATED_ACCREDITATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeParams equals to DEFAULT_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldBeFound("accreditationTypeParams.equals=" + DEFAULT_ACCREDITATION_TYPE_PARAMS);

        // Get all the accreditationTypeList where accreditationTypeParams equals to UPDATED_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeParams.equals=" + UPDATED_ACCREDITATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeParams in DEFAULT_ACCREDITATION_TYPE_PARAMS or UPDATED_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldBeFound(
            "accreditationTypeParams.in=" + DEFAULT_ACCREDITATION_TYPE_PARAMS + "," + UPDATED_ACCREDITATION_TYPE_PARAMS
        );

        // Get all the accreditationTypeList where accreditationTypeParams equals to UPDATED_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeParams.in=" + UPDATED_ACCREDITATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeParams is not null
        defaultAccreditationTypeShouldBeFound("accreditationTypeParams.specified=true");

        // Get all the accreditationTypeList where accreditationTypeParams is null
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeParams contains DEFAULT_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldBeFound("accreditationTypeParams.contains=" + DEFAULT_ACCREDITATION_TYPE_PARAMS);

        // Get all the accreditationTypeList where accreditationTypeParams contains UPDATED_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeParams.contains=" + UPDATED_ACCREDITATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeParams does not contain DEFAULT_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeParams.doesNotContain=" + DEFAULT_ACCREDITATION_TYPE_PARAMS);

        // Get all the accreditationTypeList where accreditationTypeParams does not contain UPDATED_ACCREDITATION_TYPE_PARAMS
        defaultAccreditationTypeShouldBeFound("accreditationTypeParams.doesNotContain=" + UPDATED_ACCREDITATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAttributs equals to DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldBeFound("accreditationTypeAttributs.equals=" + DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS);

        // Get all the accreditationTypeList where accreditationTypeAttributs equals to UPDATED_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAttributs.equals=" + UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAttributs in DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS or UPDATED_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldBeFound(
            "accreditationTypeAttributs.in=" + DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS + "," + UPDATED_ACCREDITATION_TYPE_ATTRIBUTS
        );

        // Get all the accreditationTypeList where accreditationTypeAttributs equals to UPDATED_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAttributs.in=" + UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAttributs is not null
        defaultAccreditationTypeShouldBeFound("accreditationTypeAttributs.specified=true");

        // Get all the accreditationTypeList where accreditationTypeAttributs is null
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAttributs contains DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldBeFound("accreditationTypeAttributs.contains=" + DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS);

        // Get all the accreditationTypeList where accreditationTypeAttributs contains UPDATED_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAttributs.contains=" + UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeAttributs does not contain DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeAttributs.doesNotContain=" + DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS);

        // Get all the accreditationTypeList where accreditationTypeAttributs does not contain UPDATED_ACCREDITATION_TYPE_ATTRIBUTS
        defaultAccreditationTypeShouldBeFound("accreditationTypeAttributs.doesNotContain=" + UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeStat equals to DEFAULT_ACCREDITATION_TYPE_STAT
        defaultAccreditationTypeShouldBeFound("accreditationTypeStat.equals=" + DEFAULT_ACCREDITATION_TYPE_STAT);

        // Get all the accreditationTypeList where accreditationTypeStat equals to UPDATED_ACCREDITATION_TYPE_STAT
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeStat.equals=" + UPDATED_ACCREDITATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeStatIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeStat in DEFAULT_ACCREDITATION_TYPE_STAT or UPDATED_ACCREDITATION_TYPE_STAT
        defaultAccreditationTypeShouldBeFound(
            "accreditationTypeStat.in=" + DEFAULT_ACCREDITATION_TYPE_STAT + "," + UPDATED_ACCREDITATION_TYPE_STAT
        );

        // Get all the accreditationTypeList where accreditationTypeStat equals to UPDATED_ACCREDITATION_TYPE_STAT
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeStat.in=" + UPDATED_ACCREDITATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationTypeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        // Get all the accreditationTypeList where accreditationTypeStat is not null
        defaultAccreditationTypeShouldBeFound("accreditationTypeStat.specified=true");

        // Get all the accreditationTypeList where accreditationTypeStat is null
        defaultAccreditationTypeShouldNotBeFound("accreditationTypeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            accreditationTypeRepository.saveAndFlush(accreditationType);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        accreditationType.addAccreditation(accreditation);
        accreditationTypeRepository.saveAndFlush(accreditationType);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the accreditationTypeList where accreditation equals to accreditationId
        defaultAccreditationTypeShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the accreditationTypeList where accreditation equals to (accreditationId + 1)
        defaultAccreditationTypeShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByPrintingModelIsEqualToSomething() throws Exception {
        PrintingModel printingModel;
        if (TestUtil.findAll(em, PrintingModel.class).isEmpty()) {
            accreditationTypeRepository.saveAndFlush(accreditationType);
            printingModel = PrintingModelResourceIT.createEntity(em);
        } else {
            printingModel = TestUtil.findAll(em, PrintingModel.class).get(0);
        }
        em.persist(printingModel);
        em.flush();
        accreditationType.setPrintingModel(printingModel);
        accreditationTypeRepository.saveAndFlush(accreditationType);
        Long printingModelId = printingModel.getPrintingModelId();

        // Get all the accreditationTypeList where printingModel equals to printingModelId
        defaultAccreditationTypeShouldBeFound("printingModelId.equals=" + printingModelId);

        // Get all the accreditationTypeList where printingModel equals to (printingModelId + 1)
        defaultAccreditationTypeShouldNotBeFound("printingModelId.equals=" + (printingModelId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationTypesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            accreditationTypeRepository.saveAndFlush(accreditationType);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        accreditationType.setEvent(event);
        accreditationTypeRepository.saveAndFlush(accreditationType);
        Long eventId = event.getEventId();

        // Get all the accreditationTypeList where event equals to eventId
        defaultAccreditationTypeShouldBeFound("eventId.equals=" + eventId);

        // Get all the accreditationTypeList where event equals to (eventId + 1)
        defaultAccreditationTypeShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccreditationTypeShouldBeFound(String filter) throws Exception {
        restAccreditationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accreditationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].accreditationTypeId").value(hasItem(accreditationType.getAccreditationTypeId().intValue())))
            .andExpect(jsonPath("$.[*].accreditationTypeValue").value(hasItem(DEFAULT_ACCREDITATION_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].accreditationTypeAbreviation").value(hasItem(DEFAULT_ACCREDITATION_TYPE_ABREVIATION)))
            .andExpect(jsonPath("$.[*].accreditationTypeDescription").value(hasItem(DEFAULT_ACCREDITATION_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].accreditationTypeParams").value(hasItem(DEFAULT_ACCREDITATION_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].accreditationTypeAttributs").value(hasItem(DEFAULT_ACCREDITATION_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].accreditationTypeStat").value(hasItem(DEFAULT_ACCREDITATION_TYPE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restAccreditationTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=accreditationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccreditationTypeShouldNotBeFound(String filter) throws Exception {
        restAccreditationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accreditationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccreditationTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=accreditationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccreditationType() throws Exception {
        // Get the accreditationType
        restAccreditationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccreditationType() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();

        // Update the accreditationType
        AccreditationType updatedAccreditationType = accreditationTypeRepository.findById(accreditationType.getAccreditationTypeId()).get();
        // Disconnect from session so that the updates on updatedAccreditationType are not directly saved in db
        em.detach(updatedAccreditationType);
        updatedAccreditationType
            .accreditationTypeValue(UPDATED_ACCREDITATION_TYPE_VALUE)
            .accreditationTypeAbreviation(UPDATED_ACCREDITATION_TYPE_ABREVIATION)
            .accreditationTypeDescription(UPDATED_ACCREDITATION_TYPE_DESCRIPTION)
            .accreditationTypeParams(UPDATED_ACCREDITATION_TYPE_PARAMS)
            .accreditationTypeAttributs(UPDATED_ACCREDITATION_TYPE_ATTRIBUTS)
            .accreditationTypeStat(UPDATED_ACCREDITATION_TYPE_STAT);
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(updatedAccreditationType);

        restAccreditationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accreditationTypeDTO.getAccreditationTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
        AccreditationType testAccreditationType = accreditationTypeList.get(accreditationTypeList.size() - 1);
        assertThat(testAccreditationType.getAccreditationTypeValue()).isEqualTo(UPDATED_ACCREDITATION_TYPE_VALUE);
        assertThat(testAccreditationType.getAccreditationTypeAbreviation()).isEqualTo(UPDATED_ACCREDITATION_TYPE_ABREVIATION);
        assertThat(testAccreditationType.getAccreditationTypeDescription()).isEqualTo(UPDATED_ACCREDITATION_TYPE_DESCRIPTION);
        assertThat(testAccreditationType.getAccreditationTypeParams()).isEqualTo(UPDATED_ACCREDITATION_TYPE_PARAMS);
        assertThat(testAccreditationType.getAccreditationTypeAttributs()).isEqualTo(UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);
        assertThat(testAccreditationType.getAccreditationTypeStat()).isEqualTo(UPDATED_ACCREDITATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingAccreditationType() throws Exception {
        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();
        accreditationType.setAccreditationTypeId(count.incrementAndGet());

        // Create the AccreditationType
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accreditationTypeDTO.getAccreditationTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccreditationType() throws Exception {
        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();
        accreditationType.setAccreditationTypeId(count.incrementAndGet());

        // Create the AccreditationType
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccreditationType() throws Exception {
        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();
        accreditationType.setAccreditationTypeId(count.incrementAndGet());

        // Create the AccreditationType
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccreditationTypeWithPatch() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();

        // Update the accreditationType using partial update
        AccreditationType partialUpdatedAccreditationType = new AccreditationType();
        partialUpdatedAccreditationType.setAccreditationTypeId(accreditationType.getAccreditationTypeId());

        partialUpdatedAccreditationType
            .accreditationTypeValue(UPDATED_ACCREDITATION_TYPE_VALUE)
            .accreditationTypeDescription(UPDATED_ACCREDITATION_TYPE_DESCRIPTION)
            .accreditationTypeParams(UPDATED_ACCREDITATION_TYPE_PARAMS)
            .accreditationTypeAttributs(UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);

        restAccreditationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccreditationType.getAccreditationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccreditationType))
            )
            .andExpect(status().isOk());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
        AccreditationType testAccreditationType = accreditationTypeList.get(accreditationTypeList.size() - 1);
        assertThat(testAccreditationType.getAccreditationTypeValue()).isEqualTo(UPDATED_ACCREDITATION_TYPE_VALUE);
        assertThat(testAccreditationType.getAccreditationTypeAbreviation()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_ABREVIATION);
        assertThat(testAccreditationType.getAccreditationTypeDescription()).isEqualTo(UPDATED_ACCREDITATION_TYPE_DESCRIPTION);
        assertThat(testAccreditationType.getAccreditationTypeParams()).isEqualTo(UPDATED_ACCREDITATION_TYPE_PARAMS);
        assertThat(testAccreditationType.getAccreditationTypeAttributs()).isEqualTo(UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);
        assertThat(testAccreditationType.getAccreditationTypeStat()).isEqualTo(DEFAULT_ACCREDITATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateAccreditationTypeWithPatch() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();

        // Update the accreditationType using partial update
        AccreditationType partialUpdatedAccreditationType = new AccreditationType();
        partialUpdatedAccreditationType.setAccreditationTypeId(accreditationType.getAccreditationTypeId());

        partialUpdatedAccreditationType
            .accreditationTypeValue(UPDATED_ACCREDITATION_TYPE_VALUE)
            .accreditationTypeAbreviation(UPDATED_ACCREDITATION_TYPE_ABREVIATION)
            .accreditationTypeDescription(UPDATED_ACCREDITATION_TYPE_DESCRIPTION)
            .accreditationTypeParams(UPDATED_ACCREDITATION_TYPE_PARAMS)
            .accreditationTypeAttributs(UPDATED_ACCREDITATION_TYPE_ATTRIBUTS)
            .accreditationTypeStat(UPDATED_ACCREDITATION_TYPE_STAT);

        restAccreditationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccreditationType.getAccreditationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccreditationType))
            )
            .andExpect(status().isOk());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
        AccreditationType testAccreditationType = accreditationTypeList.get(accreditationTypeList.size() - 1);
        assertThat(testAccreditationType.getAccreditationTypeValue()).isEqualTo(UPDATED_ACCREDITATION_TYPE_VALUE);
        assertThat(testAccreditationType.getAccreditationTypeAbreviation()).isEqualTo(UPDATED_ACCREDITATION_TYPE_ABREVIATION);
        assertThat(testAccreditationType.getAccreditationTypeDescription()).isEqualTo(UPDATED_ACCREDITATION_TYPE_DESCRIPTION);
        assertThat(testAccreditationType.getAccreditationTypeParams()).isEqualTo(UPDATED_ACCREDITATION_TYPE_PARAMS);
        assertThat(testAccreditationType.getAccreditationTypeAttributs()).isEqualTo(UPDATED_ACCREDITATION_TYPE_ATTRIBUTS);
        assertThat(testAccreditationType.getAccreditationTypeStat()).isEqualTo(UPDATED_ACCREDITATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingAccreditationType() throws Exception {
        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();
        accreditationType.setAccreditationTypeId(count.incrementAndGet());

        // Create the AccreditationType
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accreditationTypeDTO.getAccreditationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccreditationType() throws Exception {
        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();
        accreditationType.setAccreditationTypeId(count.incrementAndGet());

        // Create the AccreditationType
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccreditationType() throws Exception {
        int databaseSizeBeforeUpdate = accreditationTypeRepository.findAll().size();
        accreditationType.setAccreditationTypeId(count.incrementAndGet());

        // Create the AccreditationType
        AccreditationTypeDTO accreditationTypeDTO = accreditationTypeMapper.toDto(accreditationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accreditationTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccreditationType in the database
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccreditationType() throws Exception {
        // Initialize the database
        accreditationTypeRepository.saveAndFlush(accreditationType);

        int databaseSizeBeforeDelete = accreditationTypeRepository.findAll().size();

        // Delete the accreditationType
        restAccreditationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, accreditationType.getAccreditationTypeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccreditationType> accreditationTypeList = accreditationTypeRepository.findAll();
        assertThat(accreditationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
