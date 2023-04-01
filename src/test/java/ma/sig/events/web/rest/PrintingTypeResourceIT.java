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
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.PrintingType;
import ma.sig.events.repository.PrintingTypeRepository;
import ma.sig.events.service.criteria.PrintingTypeCriteria;
import ma.sig.events.service.dto.PrintingTypeDTO;
import ma.sig.events.service.mapper.PrintingTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrintingTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrintingTypeResourceIT {

    private static final String DEFAULT_PRINTING_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_TYPE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINTING_TYPE_STAT = false;
    private static final Boolean UPDATED_PRINTING_TYPE_STAT = true;

    private static final String ENTITY_API_URL = "/api/printing-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{printingTypeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrintingTypeRepository printingTypeRepository;

    @Autowired
    private PrintingTypeMapper printingTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrintingTypeMockMvc;

    private PrintingType printingType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingType createEntity(EntityManager em) {
        PrintingType printingType = new PrintingType()
            .printingTypeValue(DEFAULT_PRINTING_TYPE_VALUE)
            .printingTypeDescription(DEFAULT_PRINTING_TYPE_DESCRIPTION)
            .printingTypeParams(DEFAULT_PRINTING_TYPE_PARAMS)
            .printingTypeAttributs(DEFAULT_PRINTING_TYPE_ATTRIBUTS)
            .printingTypeStat(DEFAULT_PRINTING_TYPE_STAT);
        return printingType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingType createUpdatedEntity(EntityManager em) {
        PrintingType printingType = new PrintingType()
            .printingTypeValue(UPDATED_PRINTING_TYPE_VALUE)
            .printingTypeDescription(UPDATED_PRINTING_TYPE_DESCRIPTION)
            .printingTypeParams(UPDATED_PRINTING_TYPE_PARAMS)
            .printingTypeAttributs(UPDATED_PRINTING_TYPE_ATTRIBUTS)
            .printingTypeStat(UPDATED_PRINTING_TYPE_STAT);
        return printingType;
    }

    @BeforeEach
    public void initTest() {
        printingType = createEntity(em);
    }

    @Test
    @Transactional
    void createPrintingType() throws Exception {
        int databaseSizeBeforeCreate = printingTypeRepository.findAll().size();
        // Create the PrintingType
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);
        restPrintingTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PrintingType testPrintingType = printingTypeList.get(printingTypeList.size() - 1);
        assertThat(testPrintingType.getPrintingTypeValue()).isEqualTo(DEFAULT_PRINTING_TYPE_VALUE);
        assertThat(testPrintingType.getPrintingTypeDescription()).isEqualTo(DEFAULT_PRINTING_TYPE_DESCRIPTION);
        assertThat(testPrintingType.getPrintingTypeParams()).isEqualTo(DEFAULT_PRINTING_TYPE_PARAMS);
        assertThat(testPrintingType.getPrintingTypeAttributs()).isEqualTo(DEFAULT_PRINTING_TYPE_ATTRIBUTS);
        assertThat(testPrintingType.getPrintingTypeStat()).isEqualTo(DEFAULT_PRINTING_TYPE_STAT);
    }

    @Test
    @Transactional
    void createPrintingTypeWithExistingId() throws Exception {
        // Create the PrintingType with an existing ID
        printingType.setPrintingTypeId(1L);
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        int databaseSizeBeforeCreate = printingTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrintingTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrintingTypeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = printingTypeRepository.findAll().size();
        // set the field null
        printingType.setPrintingTypeValue(null);

        // Create the PrintingType, which fails.
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        restPrintingTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrintingTypes() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList
        restPrintingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingTypeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingTypeId").value(hasItem(printingType.getPrintingTypeId().intValue())))
            .andExpect(jsonPath("$.[*].printingTypeValue").value(hasItem(DEFAULT_PRINTING_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].printingTypeDescription").value(hasItem(DEFAULT_PRINTING_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingTypeParams").value(hasItem(DEFAULT_PRINTING_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].printingTypeAttributs").value(hasItem(DEFAULT_PRINTING_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].printingTypeStat").value(hasItem(DEFAULT_PRINTING_TYPE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getPrintingType() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get the printingType
        restPrintingTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, printingType.getPrintingTypeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.printingTypeId").value(printingType.getPrintingTypeId().intValue()))
            .andExpect(jsonPath("$.printingTypeValue").value(DEFAULT_PRINTING_TYPE_VALUE))
            .andExpect(jsonPath("$.printingTypeDescription").value(DEFAULT_PRINTING_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.printingTypeParams").value(DEFAULT_PRINTING_TYPE_PARAMS))
            .andExpect(jsonPath("$.printingTypeAttributs").value(DEFAULT_PRINTING_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.printingTypeStat").value(DEFAULT_PRINTING_TYPE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getPrintingTypesByIdFiltering() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        Long id = printingType.getPrintingTypeId();

        defaultPrintingTypeShouldBeFound("printingTypeId.equals=" + id);
        defaultPrintingTypeShouldNotBeFound("printingTypeId.notEquals=" + id);

        defaultPrintingTypeShouldBeFound("printingTypeId.greaterThanOrEqual=" + id);
        defaultPrintingTypeShouldNotBeFound("printingTypeId.greaterThan=" + id);

        defaultPrintingTypeShouldBeFound("printingTypeId.lessThanOrEqual=" + id);
        defaultPrintingTypeShouldNotBeFound("printingTypeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeValue equals to DEFAULT_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldBeFound("printingTypeValue.equals=" + DEFAULT_PRINTING_TYPE_VALUE);

        // Get all the printingTypeList where printingTypeValue equals to UPDATED_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldNotBeFound("printingTypeValue.equals=" + UPDATED_PRINTING_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeValueIsInShouldWork() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeValue in DEFAULT_PRINTING_TYPE_VALUE or UPDATED_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldBeFound("printingTypeValue.in=" + DEFAULT_PRINTING_TYPE_VALUE + "," + UPDATED_PRINTING_TYPE_VALUE);

        // Get all the printingTypeList where printingTypeValue equals to UPDATED_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldNotBeFound("printingTypeValue.in=" + UPDATED_PRINTING_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeValue is not null
        defaultPrintingTypeShouldBeFound("printingTypeValue.specified=true");

        // Get all the printingTypeList where printingTypeValue is null
        defaultPrintingTypeShouldNotBeFound("printingTypeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeValueContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeValue contains DEFAULT_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldBeFound("printingTypeValue.contains=" + DEFAULT_PRINTING_TYPE_VALUE);

        // Get all the printingTypeList where printingTypeValue contains UPDATED_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldNotBeFound("printingTypeValue.contains=" + UPDATED_PRINTING_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeValueNotContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeValue does not contain DEFAULT_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldNotBeFound("printingTypeValue.doesNotContain=" + DEFAULT_PRINTING_TYPE_VALUE);

        // Get all the printingTypeList where printingTypeValue does not contain UPDATED_PRINTING_TYPE_VALUE
        defaultPrintingTypeShouldBeFound("printingTypeValue.doesNotContain=" + UPDATED_PRINTING_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeDescription equals to DEFAULT_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldBeFound("printingTypeDescription.equals=" + DEFAULT_PRINTING_TYPE_DESCRIPTION);

        // Get all the printingTypeList where printingTypeDescription equals to UPDATED_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldNotBeFound("printingTypeDescription.equals=" + UPDATED_PRINTING_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeDescription in DEFAULT_PRINTING_TYPE_DESCRIPTION or UPDATED_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldBeFound(
            "printingTypeDescription.in=" + DEFAULT_PRINTING_TYPE_DESCRIPTION + "," + UPDATED_PRINTING_TYPE_DESCRIPTION
        );

        // Get all the printingTypeList where printingTypeDescription equals to UPDATED_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldNotBeFound("printingTypeDescription.in=" + UPDATED_PRINTING_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeDescription is not null
        defaultPrintingTypeShouldBeFound("printingTypeDescription.specified=true");

        // Get all the printingTypeList where printingTypeDescription is null
        defaultPrintingTypeShouldNotBeFound("printingTypeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeDescription contains DEFAULT_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldBeFound("printingTypeDescription.contains=" + DEFAULT_PRINTING_TYPE_DESCRIPTION);

        // Get all the printingTypeList where printingTypeDescription contains UPDATED_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldNotBeFound("printingTypeDescription.contains=" + UPDATED_PRINTING_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeDescription does not contain DEFAULT_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldNotBeFound("printingTypeDescription.doesNotContain=" + DEFAULT_PRINTING_TYPE_DESCRIPTION);

        // Get all the printingTypeList where printingTypeDescription does not contain UPDATED_PRINTING_TYPE_DESCRIPTION
        defaultPrintingTypeShouldBeFound("printingTypeDescription.doesNotContain=" + UPDATED_PRINTING_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeParams equals to DEFAULT_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldBeFound("printingTypeParams.equals=" + DEFAULT_PRINTING_TYPE_PARAMS);

        // Get all the printingTypeList where printingTypeParams equals to UPDATED_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldNotBeFound("printingTypeParams.equals=" + UPDATED_PRINTING_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeParams in DEFAULT_PRINTING_TYPE_PARAMS or UPDATED_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldBeFound("printingTypeParams.in=" + DEFAULT_PRINTING_TYPE_PARAMS + "," + UPDATED_PRINTING_TYPE_PARAMS);

        // Get all the printingTypeList where printingTypeParams equals to UPDATED_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldNotBeFound("printingTypeParams.in=" + UPDATED_PRINTING_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeParams is not null
        defaultPrintingTypeShouldBeFound("printingTypeParams.specified=true");

        // Get all the printingTypeList where printingTypeParams is null
        defaultPrintingTypeShouldNotBeFound("printingTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeParams contains DEFAULT_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldBeFound("printingTypeParams.contains=" + DEFAULT_PRINTING_TYPE_PARAMS);

        // Get all the printingTypeList where printingTypeParams contains UPDATED_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldNotBeFound("printingTypeParams.contains=" + UPDATED_PRINTING_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeParams does not contain DEFAULT_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldNotBeFound("printingTypeParams.doesNotContain=" + DEFAULT_PRINTING_TYPE_PARAMS);

        // Get all the printingTypeList where printingTypeParams does not contain UPDATED_PRINTING_TYPE_PARAMS
        defaultPrintingTypeShouldBeFound("printingTypeParams.doesNotContain=" + UPDATED_PRINTING_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeAttributs equals to DEFAULT_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldBeFound("printingTypeAttributs.equals=" + DEFAULT_PRINTING_TYPE_ATTRIBUTS);

        // Get all the printingTypeList where printingTypeAttributs equals to UPDATED_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldNotBeFound("printingTypeAttributs.equals=" + UPDATED_PRINTING_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeAttributs in DEFAULT_PRINTING_TYPE_ATTRIBUTS or UPDATED_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldBeFound(
            "printingTypeAttributs.in=" + DEFAULT_PRINTING_TYPE_ATTRIBUTS + "," + UPDATED_PRINTING_TYPE_ATTRIBUTS
        );

        // Get all the printingTypeList where printingTypeAttributs equals to UPDATED_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldNotBeFound("printingTypeAttributs.in=" + UPDATED_PRINTING_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeAttributs is not null
        defaultPrintingTypeShouldBeFound("printingTypeAttributs.specified=true");

        // Get all the printingTypeList where printingTypeAttributs is null
        defaultPrintingTypeShouldNotBeFound("printingTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeAttributs contains DEFAULT_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldBeFound("printingTypeAttributs.contains=" + DEFAULT_PRINTING_TYPE_ATTRIBUTS);

        // Get all the printingTypeList where printingTypeAttributs contains UPDATED_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldNotBeFound("printingTypeAttributs.contains=" + UPDATED_PRINTING_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeAttributs does not contain DEFAULT_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldNotBeFound("printingTypeAttributs.doesNotContain=" + DEFAULT_PRINTING_TYPE_ATTRIBUTS);

        // Get all the printingTypeList where printingTypeAttributs does not contain UPDATED_PRINTING_TYPE_ATTRIBUTS
        defaultPrintingTypeShouldBeFound("printingTypeAttributs.doesNotContain=" + UPDATED_PRINTING_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeStat equals to DEFAULT_PRINTING_TYPE_STAT
        defaultPrintingTypeShouldBeFound("printingTypeStat.equals=" + DEFAULT_PRINTING_TYPE_STAT);

        // Get all the printingTypeList where printingTypeStat equals to UPDATED_PRINTING_TYPE_STAT
        defaultPrintingTypeShouldNotBeFound("printingTypeStat.equals=" + UPDATED_PRINTING_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeStatIsInShouldWork() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeStat in DEFAULT_PRINTING_TYPE_STAT or UPDATED_PRINTING_TYPE_STAT
        defaultPrintingTypeShouldBeFound("printingTypeStat.in=" + DEFAULT_PRINTING_TYPE_STAT + "," + UPDATED_PRINTING_TYPE_STAT);

        // Get all the printingTypeList where printingTypeStat equals to UPDATED_PRINTING_TYPE_STAT
        defaultPrintingTypeShouldNotBeFound("printingTypeStat.in=" + UPDATED_PRINTING_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingTypeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        // Get all the printingTypeList where printingTypeStat is not null
        defaultPrintingTypeShouldBeFound("printingTypeStat.specified=true");

        // Get all the printingTypeList where printingTypeStat is null
        defaultPrintingTypeShouldNotBeFound("printingTypeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingTypesByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            printingTypeRepository.saveAndFlush(printingType);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        printingType.addPrintingCentre(printingCentre);
        printingTypeRepository.saveAndFlush(printingType);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the printingTypeList where printingCentre equals to printingCentreId
        defaultPrintingTypeShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the printingTypeList where printingCentre equals to (printingCentreId + 1)
        defaultPrintingTypeShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrintingTypeShouldBeFound(String filter) throws Exception {
        restPrintingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingTypeId").value(hasItem(printingType.getPrintingTypeId().intValue())))
            .andExpect(jsonPath("$.[*].printingTypeValue").value(hasItem(DEFAULT_PRINTING_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].printingTypeDescription").value(hasItem(DEFAULT_PRINTING_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingTypeParams").value(hasItem(DEFAULT_PRINTING_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].printingTypeAttributs").value(hasItem(DEFAULT_PRINTING_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].printingTypeStat").value(hasItem(DEFAULT_PRINTING_TYPE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restPrintingTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrintingTypeShouldNotBeFound(String filter) throws Exception {
        restPrintingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrintingTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPrintingType() throws Exception {
        // Get the printingType
        restPrintingTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrintingType() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();

        // Update the printingType
        PrintingType updatedPrintingType = printingTypeRepository.findById(printingType.getPrintingTypeId()).get();
        // Disconnect from session so that the updates on updatedPrintingType are not directly saved in db
        em.detach(updatedPrintingType);
        updatedPrintingType
            .printingTypeValue(UPDATED_PRINTING_TYPE_VALUE)
            .printingTypeDescription(UPDATED_PRINTING_TYPE_DESCRIPTION)
            .printingTypeParams(UPDATED_PRINTING_TYPE_PARAMS)
            .printingTypeAttributs(UPDATED_PRINTING_TYPE_ATTRIBUTS)
            .printingTypeStat(UPDATED_PRINTING_TYPE_STAT);
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(updatedPrintingType);

        restPrintingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingTypeDTO.getPrintingTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
        PrintingType testPrintingType = printingTypeList.get(printingTypeList.size() - 1);
        assertThat(testPrintingType.getPrintingTypeValue()).isEqualTo(UPDATED_PRINTING_TYPE_VALUE);
        assertThat(testPrintingType.getPrintingTypeDescription()).isEqualTo(UPDATED_PRINTING_TYPE_DESCRIPTION);
        assertThat(testPrintingType.getPrintingTypeParams()).isEqualTo(UPDATED_PRINTING_TYPE_PARAMS);
        assertThat(testPrintingType.getPrintingTypeAttributs()).isEqualTo(UPDATED_PRINTING_TYPE_ATTRIBUTS);
        assertThat(testPrintingType.getPrintingTypeStat()).isEqualTo(UPDATED_PRINTING_TYPE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingPrintingType() throws Exception {
        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();
        printingType.setPrintingTypeId(count.incrementAndGet());

        // Create the PrintingType
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingTypeDTO.getPrintingTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrintingType() throws Exception {
        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();
        printingType.setPrintingTypeId(count.incrementAndGet());

        // Create the PrintingType
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrintingType() throws Exception {
        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();
        printingType.setPrintingTypeId(count.incrementAndGet());

        // Create the PrintingType
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrintingTypeWithPatch() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();

        // Update the printingType using partial update
        PrintingType partialUpdatedPrintingType = new PrintingType();
        partialUpdatedPrintingType.setPrintingTypeId(printingType.getPrintingTypeId());

        partialUpdatedPrintingType.printingTypeParams(UPDATED_PRINTING_TYPE_PARAMS).printingTypeStat(UPDATED_PRINTING_TYPE_STAT);

        restPrintingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingType.getPrintingTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingType))
            )
            .andExpect(status().isOk());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
        PrintingType testPrintingType = printingTypeList.get(printingTypeList.size() - 1);
        assertThat(testPrintingType.getPrintingTypeValue()).isEqualTo(DEFAULT_PRINTING_TYPE_VALUE);
        assertThat(testPrintingType.getPrintingTypeDescription()).isEqualTo(DEFAULT_PRINTING_TYPE_DESCRIPTION);
        assertThat(testPrintingType.getPrintingTypeParams()).isEqualTo(UPDATED_PRINTING_TYPE_PARAMS);
        assertThat(testPrintingType.getPrintingTypeAttributs()).isEqualTo(DEFAULT_PRINTING_TYPE_ATTRIBUTS);
        assertThat(testPrintingType.getPrintingTypeStat()).isEqualTo(UPDATED_PRINTING_TYPE_STAT);
    }

    @Test
    @Transactional
    void fullUpdatePrintingTypeWithPatch() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();

        // Update the printingType using partial update
        PrintingType partialUpdatedPrintingType = new PrintingType();
        partialUpdatedPrintingType.setPrintingTypeId(printingType.getPrintingTypeId());

        partialUpdatedPrintingType
            .printingTypeValue(UPDATED_PRINTING_TYPE_VALUE)
            .printingTypeDescription(UPDATED_PRINTING_TYPE_DESCRIPTION)
            .printingTypeParams(UPDATED_PRINTING_TYPE_PARAMS)
            .printingTypeAttributs(UPDATED_PRINTING_TYPE_ATTRIBUTS)
            .printingTypeStat(UPDATED_PRINTING_TYPE_STAT);

        restPrintingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingType.getPrintingTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingType))
            )
            .andExpect(status().isOk());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
        PrintingType testPrintingType = printingTypeList.get(printingTypeList.size() - 1);
        assertThat(testPrintingType.getPrintingTypeValue()).isEqualTo(UPDATED_PRINTING_TYPE_VALUE);
        assertThat(testPrintingType.getPrintingTypeDescription()).isEqualTo(UPDATED_PRINTING_TYPE_DESCRIPTION);
        assertThat(testPrintingType.getPrintingTypeParams()).isEqualTo(UPDATED_PRINTING_TYPE_PARAMS);
        assertThat(testPrintingType.getPrintingTypeAttributs()).isEqualTo(UPDATED_PRINTING_TYPE_ATTRIBUTS);
        assertThat(testPrintingType.getPrintingTypeStat()).isEqualTo(UPDATED_PRINTING_TYPE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingPrintingType() throws Exception {
        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();
        printingType.setPrintingTypeId(count.incrementAndGet());

        // Create the PrintingType
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, printingTypeDTO.getPrintingTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrintingType() throws Exception {
        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();
        printingType.setPrintingTypeId(count.incrementAndGet());

        // Create the PrintingType
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrintingType() throws Exception {
        int databaseSizeBeforeUpdate = printingTypeRepository.findAll().size();
        printingType.setPrintingTypeId(count.incrementAndGet());

        // Create the PrintingType
        PrintingTypeDTO printingTypeDTO = printingTypeMapper.toDto(printingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingType in the database
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrintingType() throws Exception {
        // Initialize the database
        printingTypeRepository.saveAndFlush(printingType);

        int databaseSizeBeforeDelete = printingTypeRepository.findAll().size();

        // Delete the printingType
        restPrintingTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, printingType.getPrintingTypeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrintingType> printingTypeList = printingTypeRepository.findAll();
        assertThat(printingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
