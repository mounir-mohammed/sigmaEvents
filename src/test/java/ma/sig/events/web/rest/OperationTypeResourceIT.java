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
import ma.sig.events.domain.OperationHistory;
import ma.sig.events.domain.OperationType;
import ma.sig.events.repository.OperationTypeRepository;
import ma.sig.events.service.criteria.OperationTypeCriteria;
import ma.sig.events.service.dto.OperationTypeDTO;
import ma.sig.events.service.mapper.OperationTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OperationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperationTypeResourceIT {

    private static final String DEFAULT_OPERATION_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_TYPE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OPERATION_TYPE_STAT = false;
    private static final Boolean UPDATED_OPERATION_TYPE_STAT = true;

    private static final String ENTITY_API_URL = "/api/operation-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{operationTypeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private OperationTypeMapper operationTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationTypeMockMvc;

    private OperationType operationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationType createEntity(EntityManager em) {
        OperationType operationType = new OperationType()
            .operationTypeValue(DEFAULT_OPERATION_TYPE_VALUE)
            .operationTypeDescription(DEFAULT_OPERATION_TYPE_DESCRIPTION)
            .operationTypeParams(DEFAULT_OPERATION_TYPE_PARAMS)
            .operationTypeAttributs(DEFAULT_OPERATION_TYPE_ATTRIBUTS)
            .operationTypeStat(DEFAULT_OPERATION_TYPE_STAT);
        return operationType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationType createUpdatedEntity(EntityManager em) {
        OperationType operationType = new OperationType()
            .operationTypeValue(UPDATED_OPERATION_TYPE_VALUE)
            .operationTypeDescription(UPDATED_OPERATION_TYPE_DESCRIPTION)
            .operationTypeParams(UPDATED_OPERATION_TYPE_PARAMS)
            .operationTypeAttributs(UPDATED_OPERATION_TYPE_ATTRIBUTS)
            .operationTypeStat(UPDATED_OPERATION_TYPE_STAT);
        return operationType;
    }

    @BeforeEach
    public void initTest() {
        operationType = createEntity(em);
    }

    @Test
    @Transactional
    void createOperationType() throws Exception {
        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();
        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);
        restOperationTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getOperationTypeValue()).isEqualTo(DEFAULT_OPERATION_TYPE_VALUE);
        assertThat(testOperationType.getOperationTypeDescription()).isEqualTo(DEFAULT_OPERATION_TYPE_DESCRIPTION);
        assertThat(testOperationType.getOperationTypeParams()).isEqualTo(DEFAULT_OPERATION_TYPE_PARAMS);
        assertThat(testOperationType.getOperationTypeAttributs()).isEqualTo(DEFAULT_OPERATION_TYPE_ATTRIBUTS);
        assertThat(testOperationType.getOperationTypeStat()).isEqualTo(DEFAULT_OPERATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void createOperationTypeWithExistingId() throws Exception {
        // Create the OperationType with an existing ID
        operationType.setOperationTypeId(1L);
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationTypeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationTypeRepository.findAll().size();
        // set the field null
        operationType.setOperationTypeValue(null);

        // Create the OperationType, which fails.
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        restOperationTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOperationTypes() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList
        restOperationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=operationTypeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].operationTypeId").value(hasItem(operationType.getOperationTypeId().intValue())))
            .andExpect(jsonPath("$.[*].operationTypeValue").value(hasItem(DEFAULT_OPERATION_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].operationTypeDescription").value(hasItem(DEFAULT_OPERATION_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].operationTypeParams").value(hasItem(DEFAULT_OPERATION_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].operationTypeAttributs").value(hasItem(DEFAULT_OPERATION_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].operationTypeStat").value(hasItem(DEFAULT_OPERATION_TYPE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get the operationType
        restOperationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, operationType.getOperationTypeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.operationTypeId").value(operationType.getOperationTypeId().intValue()))
            .andExpect(jsonPath("$.operationTypeValue").value(DEFAULT_OPERATION_TYPE_VALUE))
            .andExpect(jsonPath("$.operationTypeDescription").value(DEFAULT_OPERATION_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.operationTypeParams").value(DEFAULT_OPERATION_TYPE_PARAMS))
            .andExpect(jsonPath("$.operationTypeAttributs").value(DEFAULT_OPERATION_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.operationTypeStat").value(DEFAULT_OPERATION_TYPE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getOperationTypesByIdFiltering() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        Long id = operationType.getOperationTypeId();

        defaultOperationTypeShouldBeFound("operationTypeId.equals=" + id);
        defaultOperationTypeShouldNotBeFound("operationTypeId.notEquals=" + id);

        defaultOperationTypeShouldBeFound("operationTypeId.greaterThanOrEqual=" + id);
        defaultOperationTypeShouldNotBeFound("operationTypeId.greaterThan=" + id);

        defaultOperationTypeShouldBeFound("operationTypeId.lessThanOrEqual=" + id);
        defaultOperationTypeShouldNotBeFound("operationTypeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeValue equals to DEFAULT_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldBeFound("operationTypeValue.equals=" + DEFAULT_OPERATION_TYPE_VALUE);

        // Get all the operationTypeList where operationTypeValue equals to UPDATED_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldNotBeFound("operationTypeValue.equals=" + UPDATED_OPERATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeValueIsInShouldWork() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeValue in DEFAULT_OPERATION_TYPE_VALUE or UPDATED_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldBeFound("operationTypeValue.in=" + DEFAULT_OPERATION_TYPE_VALUE + "," + UPDATED_OPERATION_TYPE_VALUE);

        // Get all the operationTypeList where operationTypeValue equals to UPDATED_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldNotBeFound("operationTypeValue.in=" + UPDATED_OPERATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeValue is not null
        defaultOperationTypeShouldBeFound("operationTypeValue.specified=true");

        // Get all the operationTypeList where operationTypeValue is null
        defaultOperationTypeShouldNotBeFound("operationTypeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeValueContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeValue contains DEFAULT_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldBeFound("operationTypeValue.contains=" + DEFAULT_OPERATION_TYPE_VALUE);

        // Get all the operationTypeList where operationTypeValue contains UPDATED_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldNotBeFound("operationTypeValue.contains=" + UPDATED_OPERATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeValueNotContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeValue does not contain DEFAULT_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldNotBeFound("operationTypeValue.doesNotContain=" + DEFAULT_OPERATION_TYPE_VALUE);

        // Get all the operationTypeList where operationTypeValue does not contain UPDATED_OPERATION_TYPE_VALUE
        defaultOperationTypeShouldBeFound("operationTypeValue.doesNotContain=" + UPDATED_OPERATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeDescription equals to DEFAULT_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldBeFound("operationTypeDescription.equals=" + DEFAULT_OPERATION_TYPE_DESCRIPTION);

        // Get all the operationTypeList where operationTypeDescription equals to UPDATED_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldNotBeFound("operationTypeDescription.equals=" + UPDATED_OPERATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeDescription in DEFAULT_OPERATION_TYPE_DESCRIPTION or UPDATED_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldBeFound(
            "operationTypeDescription.in=" + DEFAULT_OPERATION_TYPE_DESCRIPTION + "," + UPDATED_OPERATION_TYPE_DESCRIPTION
        );

        // Get all the operationTypeList where operationTypeDescription equals to UPDATED_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldNotBeFound("operationTypeDescription.in=" + UPDATED_OPERATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeDescription is not null
        defaultOperationTypeShouldBeFound("operationTypeDescription.specified=true");

        // Get all the operationTypeList where operationTypeDescription is null
        defaultOperationTypeShouldNotBeFound("operationTypeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeDescription contains DEFAULT_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldBeFound("operationTypeDescription.contains=" + DEFAULT_OPERATION_TYPE_DESCRIPTION);

        // Get all the operationTypeList where operationTypeDescription contains UPDATED_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldNotBeFound("operationTypeDescription.contains=" + UPDATED_OPERATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeDescription does not contain DEFAULT_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldNotBeFound("operationTypeDescription.doesNotContain=" + DEFAULT_OPERATION_TYPE_DESCRIPTION);

        // Get all the operationTypeList where operationTypeDescription does not contain UPDATED_OPERATION_TYPE_DESCRIPTION
        defaultOperationTypeShouldBeFound("operationTypeDescription.doesNotContain=" + UPDATED_OPERATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeParams equals to DEFAULT_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldBeFound("operationTypeParams.equals=" + DEFAULT_OPERATION_TYPE_PARAMS);

        // Get all the operationTypeList where operationTypeParams equals to UPDATED_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldNotBeFound("operationTypeParams.equals=" + UPDATED_OPERATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeParams in DEFAULT_OPERATION_TYPE_PARAMS or UPDATED_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldBeFound("operationTypeParams.in=" + DEFAULT_OPERATION_TYPE_PARAMS + "," + UPDATED_OPERATION_TYPE_PARAMS);

        // Get all the operationTypeList where operationTypeParams equals to UPDATED_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldNotBeFound("operationTypeParams.in=" + UPDATED_OPERATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeParams is not null
        defaultOperationTypeShouldBeFound("operationTypeParams.specified=true");

        // Get all the operationTypeList where operationTypeParams is null
        defaultOperationTypeShouldNotBeFound("operationTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeParams contains DEFAULT_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldBeFound("operationTypeParams.contains=" + DEFAULT_OPERATION_TYPE_PARAMS);

        // Get all the operationTypeList where operationTypeParams contains UPDATED_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldNotBeFound("operationTypeParams.contains=" + UPDATED_OPERATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeParams does not contain DEFAULT_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldNotBeFound("operationTypeParams.doesNotContain=" + DEFAULT_OPERATION_TYPE_PARAMS);

        // Get all the operationTypeList where operationTypeParams does not contain UPDATED_OPERATION_TYPE_PARAMS
        defaultOperationTypeShouldBeFound("operationTypeParams.doesNotContain=" + UPDATED_OPERATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeAttributs equals to DEFAULT_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldBeFound("operationTypeAttributs.equals=" + DEFAULT_OPERATION_TYPE_ATTRIBUTS);

        // Get all the operationTypeList where operationTypeAttributs equals to UPDATED_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldNotBeFound("operationTypeAttributs.equals=" + UPDATED_OPERATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeAttributs in DEFAULT_OPERATION_TYPE_ATTRIBUTS or UPDATED_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldBeFound(
            "operationTypeAttributs.in=" + DEFAULT_OPERATION_TYPE_ATTRIBUTS + "," + UPDATED_OPERATION_TYPE_ATTRIBUTS
        );

        // Get all the operationTypeList where operationTypeAttributs equals to UPDATED_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldNotBeFound("operationTypeAttributs.in=" + UPDATED_OPERATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeAttributs is not null
        defaultOperationTypeShouldBeFound("operationTypeAttributs.specified=true");

        // Get all the operationTypeList where operationTypeAttributs is null
        defaultOperationTypeShouldNotBeFound("operationTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeAttributs contains DEFAULT_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldBeFound("operationTypeAttributs.contains=" + DEFAULT_OPERATION_TYPE_ATTRIBUTS);

        // Get all the operationTypeList where operationTypeAttributs contains UPDATED_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldNotBeFound("operationTypeAttributs.contains=" + UPDATED_OPERATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeAttributs does not contain DEFAULT_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldNotBeFound("operationTypeAttributs.doesNotContain=" + DEFAULT_OPERATION_TYPE_ATTRIBUTS);

        // Get all the operationTypeList where operationTypeAttributs does not contain UPDATED_OPERATION_TYPE_ATTRIBUTS
        defaultOperationTypeShouldBeFound("operationTypeAttributs.doesNotContain=" + UPDATED_OPERATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeStat equals to DEFAULT_OPERATION_TYPE_STAT
        defaultOperationTypeShouldBeFound("operationTypeStat.equals=" + DEFAULT_OPERATION_TYPE_STAT);

        // Get all the operationTypeList where operationTypeStat equals to UPDATED_OPERATION_TYPE_STAT
        defaultOperationTypeShouldNotBeFound("operationTypeStat.equals=" + UPDATED_OPERATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeStatIsInShouldWork() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeStat in DEFAULT_OPERATION_TYPE_STAT or UPDATED_OPERATION_TYPE_STAT
        defaultOperationTypeShouldBeFound("operationTypeStat.in=" + DEFAULT_OPERATION_TYPE_STAT + "," + UPDATED_OPERATION_TYPE_STAT);

        // Get all the operationTypeList where operationTypeStat equals to UPDATED_OPERATION_TYPE_STAT
        defaultOperationTypeShouldNotBeFound("operationTypeStat.in=" + UPDATED_OPERATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationTypeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList where operationTypeStat is not null
        defaultOperationTypeShouldBeFound("operationTypeStat.specified=true");

        // Get all the operationTypeList where operationTypeStat is null
        defaultOperationTypeShouldNotBeFound("operationTypeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationTypesByOperationHistoryIsEqualToSomething() throws Exception {
        OperationHistory operationHistory;
        if (TestUtil.findAll(em, OperationHistory.class).isEmpty()) {
            operationTypeRepository.saveAndFlush(operationType);
            operationHistory = OperationHistoryResourceIT.createEntity(em);
        } else {
            operationHistory = TestUtil.findAll(em, OperationHistory.class).get(0);
        }
        em.persist(operationHistory);
        em.flush();
        operationType.addOperationHistory(operationHistory);
        operationTypeRepository.saveAndFlush(operationType);
        Long operationHistoryId = operationHistory.getOperationHistoryId();

        // Get all the operationTypeList where operationHistory equals to operationHistoryId
        defaultOperationTypeShouldBeFound("operationHistoryId.equals=" + operationHistoryId);

        // Get all the operationTypeList where operationHistory equals to (operationHistoryId + 1)
        defaultOperationTypeShouldNotBeFound("operationHistoryId.equals=" + (operationHistoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOperationTypeShouldBeFound(String filter) throws Exception {
        restOperationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=operationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].operationTypeId").value(hasItem(operationType.getOperationTypeId().intValue())))
            .andExpect(jsonPath("$.[*].operationTypeValue").value(hasItem(DEFAULT_OPERATION_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].operationTypeDescription").value(hasItem(DEFAULT_OPERATION_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].operationTypeParams").value(hasItem(DEFAULT_OPERATION_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].operationTypeAttributs").value(hasItem(DEFAULT_OPERATION_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].operationTypeStat").value(hasItem(DEFAULT_OPERATION_TYPE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restOperationTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=operationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOperationTypeShouldNotBeFound(String filter) throws Exception {
        restOperationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=operationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOperationTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=operationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOperationType() throws Exception {
        // Get the operationType
        restOperationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Update the operationType
        OperationType updatedOperationType = operationTypeRepository.findById(operationType.getOperationTypeId()).get();
        // Disconnect from session so that the updates on updatedOperationType are not directly saved in db
        em.detach(updatedOperationType);
        updatedOperationType
            .operationTypeValue(UPDATED_OPERATION_TYPE_VALUE)
            .operationTypeDescription(UPDATED_OPERATION_TYPE_DESCRIPTION)
            .operationTypeParams(UPDATED_OPERATION_TYPE_PARAMS)
            .operationTypeAttributs(UPDATED_OPERATION_TYPE_ATTRIBUTS)
            .operationTypeStat(UPDATED_OPERATION_TYPE_STAT);
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(updatedOperationType);

        restOperationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationTypeDTO.getOperationTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getOperationTypeValue()).isEqualTo(UPDATED_OPERATION_TYPE_VALUE);
        assertThat(testOperationType.getOperationTypeDescription()).isEqualTo(UPDATED_OPERATION_TYPE_DESCRIPTION);
        assertThat(testOperationType.getOperationTypeParams()).isEqualTo(UPDATED_OPERATION_TYPE_PARAMS);
        assertThat(testOperationType.getOperationTypeAttributs()).isEqualTo(UPDATED_OPERATION_TYPE_ATTRIBUTS);
        assertThat(testOperationType.getOperationTypeStat()).isEqualTo(UPDATED_OPERATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();
        operationType.setOperationTypeId(count.incrementAndGet());

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationTypeDTO.getOperationTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();
        operationType.setOperationTypeId(count.incrementAndGet());

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();
        operationType.setOperationTypeId(count.incrementAndGet());

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperationTypeWithPatch() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Update the operationType using partial update
        OperationType partialUpdatedOperationType = new OperationType();
        partialUpdatedOperationType.setOperationTypeId(operationType.getOperationTypeId());

        partialUpdatedOperationType
            .operationTypeDescription(UPDATED_OPERATION_TYPE_DESCRIPTION)
            .operationTypeParams(UPDATED_OPERATION_TYPE_PARAMS);

        restOperationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationType.getOperationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationType))
            )
            .andExpect(status().isOk());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getOperationTypeValue()).isEqualTo(DEFAULT_OPERATION_TYPE_VALUE);
        assertThat(testOperationType.getOperationTypeDescription()).isEqualTo(UPDATED_OPERATION_TYPE_DESCRIPTION);
        assertThat(testOperationType.getOperationTypeParams()).isEqualTo(UPDATED_OPERATION_TYPE_PARAMS);
        assertThat(testOperationType.getOperationTypeAttributs()).isEqualTo(DEFAULT_OPERATION_TYPE_ATTRIBUTS);
        assertThat(testOperationType.getOperationTypeStat()).isEqualTo(DEFAULT_OPERATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateOperationTypeWithPatch() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Update the operationType using partial update
        OperationType partialUpdatedOperationType = new OperationType();
        partialUpdatedOperationType.setOperationTypeId(operationType.getOperationTypeId());

        partialUpdatedOperationType
            .operationTypeValue(UPDATED_OPERATION_TYPE_VALUE)
            .operationTypeDescription(UPDATED_OPERATION_TYPE_DESCRIPTION)
            .operationTypeParams(UPDATED_OPERATION_TYPE_PARAMS)
            .operationTypeAttributs(UPDATED_OPERATION_TYPE_ATTRIBUTS)
            .operationTypeStat(UPDATED_OPERATION_TYPE_STAT);

        restOperationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationType.getOperationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationType))
            )
            .andExpect(status().isOk());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getOperationTypeValue()).isEqualTo(UPDATED_OPERATION_TYPE_VALUE);
        assertThat(testOperationType.getOperationTypeDescription()).isEqualTo(UPDATED_OPERATION_TYPE_DESCRIPTION);
        assertThat(testOperationType.getOperationTypeParams()).isEqualTo(UPDATED_OPERATION_TYPE_PARAMS);
        assertThat(testOperationType.getOperationTypeAttributs()).isEqualTo(UPDATED_OPERATION_TYPE_ATTRIBUTS);
        assertThat(testOperationType.getOperationTypeStat()).isEqualTo(UPDATED_OPERATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();
        operationType.setOperationTypeId(count.incrementAndGet());

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operationTypeDTO.getOperationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();
        operationType.setOperationTypeId(count.incrementAndGet());

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();
        operationType.setOperationTypeId(count.incrementAndGet());

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        int databaseSizeBeforeDelete = operationTypeRepository.findAll().size();

        // Delete the operationType
        restOperationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, operationType.getOperationTypeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
