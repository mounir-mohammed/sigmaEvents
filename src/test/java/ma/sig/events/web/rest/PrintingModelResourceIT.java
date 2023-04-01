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
import ma.sig.events.domain.AccreditationType;
import ma.sig.events.domain.Category;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.repository.PrintingModelRepository;
import ma.sig.events.service.criteria.PrintingModelCriteria;
import ma.sig.events.service.dto.PrintingModelDTO;
import ma.sig.events.service.mapper.PrintingModelMapper;
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
 * Integration tests for the {@link PrintingModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrintingModelResourceIT {

    private static final String DEFAULT_PRINTING_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_MODEL_FILE = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_MODEL_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_MODEL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_MODEL_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_MODEL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_MODEL_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PRINTING_MODEL_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRINTING_MODEL_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRINTING_MODEL_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRINTING_MODEL_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PRINTING_MODEL_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_MODEL_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_MODEL_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_MODEL_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINTING_MODEL_STAT = false;
    private static final Boolean UPDATED_PRINTING_MODEL_STAT = true;

    private static final String ENTITY_API_URL = "/api/printing-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{printingModelId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrintingModelRepository printingModelRepository;

    @Autowired
    private PrintingModelMapper printingModelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrintingModelMockMvc;

    private PrintingModel printingModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingModel createEntity(EntityManager em) {
        PrintingModel printingModel = new PrintingModel()
            .printingModelName(DEFAULT_PRINTING_MODEL_NAME)
            .printingModelFile(DEFAULT_PRINTING_MODEL_FILE)
            .printingModelPath(DEFAULT_PRINTING_MODEL_PATH)
            .printingModelDescription(DEFAULT_PRINTING_MODEL_DESCRIPTION)
            .printingModelData(DEFAULT_PRINTING_MODEL_DATA)
            .printingModelDataContentType(DEFAULT_PRINTING_MODEL_DATA_CONTENT_TYPE)
            .printingModelParams(DEFAULT_PRINTING_MODEL_PARAMS)
            .printingModelAttributs(DEFAULT_PRINTING_MODEL_ATTRIBUTS)
            .printingModelStat(DEFAULT_PRINTING_MODEL_STAT);
        return printingModel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingModel createUpdatedEntity(EntityManager em) {
        PrintingModel printingModel = new PrintingModel()
            .printingModelName(UPDATED_PRINTING_MODEL_NAME)
            .printingModelFile(UPDATED_PRINTING_MODEL_FILE)
            .printingModelPath(UPDATED_PRINTING_MODEL_PATH)
            .printingModelDescription(UPDATED_PRINTING_MODEL_DESCRIPTION)
            .printingModelData(UPDATED_PRINTING_MODEL_DATA)
            .printingModelDataContentType(UPDATED_PRINTING_MODEL_DATA_CONTENT_TYPE)
            .printingModelParams(UPDATED_PRINTING_MODEL_PARAMS)
            .printingModelAttributs(UPDATED_PRINTING_MODEL_ATTRIBUTS)
            .printingModelStat(UPDATED_PRINTING_MODEL_STAT);
        return printingModel;
    }

    @BeforeEach
    public void initTest() {
        printingModel = createEntity(em);
    }

    @Test
    @Transactional
    void createPrintingModel() throws Exception {
        int databaseSizeBeforeCreate = printingModelRepository.findAll().size();
        // Create the PrintingModel
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);
        restPrintingModelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeCreate + 1);
        PrintingModel testPrintingModel = printingModelList.get(printingModelList.size() - 1);
        assertThat(testPrintingModel.getPrintingModelName()).isEqualTo(DEFAULT_PRINTING_MODEL_NAME);
        assertThat(testPrintingModel.getPrintingModelFile()).isEqualTo(DEFAULT_PRINTING_MODEL_FILE);
        assertThat(testPrintingModel.getPrintingModelPath()).isEqualTo(DEFAULT_PRINTING_MODEL_PATH);
        assertThat(testPrintingModel.getPrintingModelDescription()).isEqualTo(DEFAULT_PRINTING_MODEL_DESCRIPTION);
        assertThat(testPrintingModel.getPrintingModelData()).isEqualTo(DEFAULT_PRINTING_MODEL_DATA);
        assertThat(testPrintingModel.getPrintingModelDataContentType()).isEqualTo(DEFAULT_PRINTING_MODEL_DATA_CONTENT_TYPE);
        assertThat(testPrintingModel.getPrintingModelParams()).isEqualTo(DEFAULT_PRINTING_MODEL_PARAMS);
        assertThat(testPrintingModel.getPrintingModelAttributs()).isEqualTo(DEFAULT_PRINTING_MODEL_ATTRIBUTS);
        assertThat(testPrintingModel.getPrintingModelStat()).isEqualTo(DEFAULT_PRINTING_MODEL_STAT);
    }

    @Test
    @Transactional
    void createPrintingModelWithExistingId() throws Exception {
        // Create the PrintingModel with an existing ID
        printingModel.setPrintingModelId(1L);
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        int databaseSizeBeforeCreate = printingModelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrintingModelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrintingModelNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = printingModelRepository.findAll().size();
        // set the field null
        printingModel.setPrintingModelName(null);

        // Create the PrintingModel, which fails.
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        restPrintingModelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrintingModelFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = printingModelRepository.findAll().size();
        // set the field null
        printingModel.setPrintingModelFile(null);

        // Create the PrintingModel, which fails.
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        restPrintingModelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrintingModelPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = printingModelRepository.findAll().size();
        // set the field null
        printingModel.setPrintingModelPath(null);

        // Create the PrintingModel, which fails.
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        restPrintingModelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrintingModels() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList
        restPrintingModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingModelId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingModelId").value(hasItem(printingModel.getPrintingModelId().intValue())))
            .andExpect(jsonPath("$.[*].printingModelName").value(hasItem(DEFAULT_PRINTING_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].printingModelFile").value(hasItem(DEFAULT_PRINTING_MODEL_FILE)))
            .andExpect(jsonPath("$.[*].printingModelPath").value(hasItem(DEFAULT_PRINTING_MODEL_PATH)))
            .andExpect(jsonPath("$.[*].printingModelDescription").value(hasItem(DEFAULT_PRINTING_MODEL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingModelDataContentType").value(hasItem(DEFAULT_PRINTING_MODEL_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].printingModelData").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRINTING_MODEL_DATA))))
            .andExpect(jsonPath("$.[*].printingModelParams").value(hasItem(DEFAULT_PRINTING_MODEL_PARAMS)))
            .andExpect(jsonPath("$.[*].printingModelAttributs").value(hasItem(DEFAULT_PRINTING_MODEL_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].printingModelStat").value(hasItem(DEFAULT_PRINTING_MODEL_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getPrintingModel() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get the printingModel
        restPrintingModelMockMvc
            .perform(get(ENTITY_API_URL_ID, printingModel.getPrintingModelId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.printingModelId").value(printingModel.getPrintingModelId().intValue()))
            .andExpect(jsonPath("$.printingModelName").value(DEFAULT_PRINTING_MODEL_NAME))
            .andExpect(jsonPath("$.printingModelFile").value(DEFAULT_PRINTING_MODEL_FILE))
            .andExpect(jsonPath("$.printingModelPath").value(DEFAULT_PRINTING_MODEL_PATH))
            .andExpect(jsonPath("$.printingModelDescription").value(DEFAULT_PRINTING_MODEL_DESCRIPTION))
            .andExpect(jsonPath("$.printingModelDataContentType").value(DEFAULT_PRINTING_MODEL_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.printingModelData").value(Base64Utils.encodeToString(DEFAULT_PRINTING_MODEL_DATA)))
            .andExpect(jsonPath("$.printingModelParams").value(DEFAULT_PRINTING_MODEL_PARAMS))
            .andExpect(jsonPath("$.printingModelAttributs").value(DEFAULT_PRINTING_MODEL_ATTRIBUTS))
            .andExpect(jsonPath("$.printingModelStat").value(DEFAULT_PRINTING_MODEL_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getPrintingModelsByIdFiltering() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        Long id = printingModel.getPrintingModelId();

        defaultPrintingModelShouldBeFound("printingModelId.equals=" + id);
        defaultPrintingModelShouldNotBeFound("printingModelId.notEquals=" + id);

        defaultPrintingModelShouldBeFound("printingModelId.greaterThanOrEqual=" + id);
        defaultPrintingModelShouldNotBeFound("printingModelId.greaterThan=" + id);

        defaultPrintingModelShouldBeFound("printingModelId.lessThanOrEqual=" + id);
        defaultPrintingModelShouldNotBeFound("printingModelId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelName equals to DEFAULT_PRINTING_MODEL_NAME
        defaultPrintingModelShouldBeFound("printingModelName.equals=" + DEFAULT_PRINTING_MODEL_NAME);

        // Get all the printingModelList where printingModelName equals to UPDATED_PRINTING_MODEL_NAME
        defaultPrintingModelShouldNotBeFound("printingModelName.equals=" + UPDATED_PRINTING_MODEL_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelNameIsInShouldWork() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelName in DEFAULT_PRINTING_MODEL_NAME or UPDATED_PRINTING_MODEL_NAME
        defaultPrintingModelShouldBeFound("printingModelName.in=" + DEFAULT_PRINTING_MODEL_NAME + "," + UPDATED_PRINTING_MODEL_NAME);

        // Get all the printingModelList where printingModelName equals to UPDATED_PRINTING_MODEL_NAME
        defaultPrintingModelShouldNotBeFound("printingModelName.in=" + UPDATED_PRINTING_MODEL_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelName is not null
        defaultPrintingModelShouldBeFound("printingModelName.specified=true");

        // Get all the printingModelList where printingModelName is null
        defaultPrintingModelShouldNotBeFound("printingModelName.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelNameContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelName contains DEFAULT_PRINTING_MODEL_NAME
        defaultPrintingModelShouldBeFound("printingModelName.contains=" + DEFAULT_PRINTING_MODEL_NAME);

        // Get all the printingModelList where printingModelName contains UPDATED_PRINTING_MODEL_NAME
        defaultPrintingModelShouldNotBeFound("printingModelName.contains=" + UPDATED_PRINTING_MODEL_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelNameNotContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelName does not contain DEFAULT_PRINTING_MODEL_NAME
        defaultPrintingModelShouldNotBeFound("printingModelName.doesNotContain=" + DEFAULT_PRINTING_MODEL_NAME);

        // Get all the printingModelList where printingModelName does not contain UPDATED_PRINTING_MODEL_NAME
        defaultPrintingModelShouldBeFound("printingModelName.doesNotContain=" + UPDATED_PRINTING_MODEL_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelFileIsEqualToSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelFile equals to DEFAULT_PRINTING_MODEL_FILE
        defaultPrintingModelShouldBeFound("printingModelFile.equals=" + DEFAULT_PRINTING_MODEL_FILE);

        // Get all the printingModelList where printingModelFile equals to UPDATED_PRINTING_MODEL_FILE
        defaultPrintingModelShouldNotBeFound("printingModelFile.equals=" + UPDATED_PRINTING_MODEL_FILE);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelFileIsInShouldWork() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelFile in DEFAULT_PRINTING_MODEL_FILE or UPDATED_PRINTING_MODEL_FILE
        defaultPrintingModelShouldBeFound("printingModelFile.in=" + DEFAULT_PRINTING_MODEL_FILE + "," + UPDATED_PRINTING_MODEL_FILE);

        // Get all the printingModelList where printingModelFile equals to UPDATED_PRINTING_MODEL_FILE
        defaultPrintingModelShouldNotBeFound("printingModelFile.in=" + UPDATED_PRINTING_MODEL_FILE);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelFile is not null
        defaultPrintingModelShouldBeFound("printingModelFile.specified=true");

        // Get all the printingModelList where printingModelFile is null
        defaultPrintingModelShouldNotBeFound("printingModelFile.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelFileContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelFile contains DEFAULT_PRINTING_MODEL_FILE
        defaultPrintingModelShouldBeFound("printingModelFile.contains=" + DEFAULT_PRINTING_MODEL_FILE);

        // Get all the printingModelList where printingModelFile contains UPDATED_PRINTING_MODEL_FILE
        defaultPrintingModelShouldNotBeFound("printingModelFile.contains=" + UPDATED_PRINTING_MODEL_FILE);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelFileNotContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelFile does not contain DEFAULT_PRINTING_MODEL_FILE
        defaultPrintingModelShouldNotBeFound("printingModelFile.doesNotContain=" + DEFAULT_PRINTING_MODEL_FILE);

        // Get all the printingModelList where printingModelFile does not contain UPDATED_PRINTING_MODEL_FILE
        defaultPrintingModelShouldBeFound("printingModelFile.doesNotContain=" + UPDATED_PRINTING_MODEL_FILE);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelPathIsEqualToSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelPath equals to DEFAULT_PRINTING_MODEL_PATH
        defaultPrintingModelShouldBeFound("printingModelPath.equals=" + DEFAULT_PRINTING_MODEL_PATH);

        // Get all the printingModelList where printingModelPath equals to UPDATED_PRINTING_MODEL_PATH
        defaultPrintingModelShouldNotBeFound("printingModelPath.equals=" + UPDATED_PRINTING_MODEL_PATH);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelPathIsInShouldWork() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelPath in DEFAULT_PRINTING_MODEL_PATH or UPDATED_PRINTING_MODEL_PATH
        defaultPrintingModelShouldBeFound("printingModelPath.in=" + DEFAULT_PRINTING_MODEL_PATH + "," + UPDATED_PRINTING_MODEL_PATH);

        // Get all the printingModelList where printingModelPath equals to UPDATED_PRINTING_MODEL_PATH
        defaultPrintingModelShouldNotBeFound("printingModelPath.in=" + UPDATED_PRINTING_MODEL_PATH);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelPath is not null
        defaultPrintingModelShouldBeFound("printingModelPath.specified=true");

        // Get all the printingModelList where printingModelPath is null
        defaultPrintingModelShouldNotBeFound("printingModelPath.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelPathContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelPath contains DEFAULT_PRINTING_MODEL_PATH
        defaultPrintingModelShouldBeFound("printingModelPath.contains=" + DEFAULT_PRINTING_MODEL_PATH);

        // Get all the printingModelList where printingModelPath contains UPDATED_PRINTING_MODEL_PATH
        defaultPrintingModelShouldNotBeFound("printingModelPath.contains=" + UPDATED_PRINTING_MODEL_PATH);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelPathNotContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelPath does not contain DEFAULT_PRINTING_MODEL_PATH
        defaultPrintingModelShouldNotBeFound("printingModelPath.doesNotContain=" + DEFAULT_PRINTING_MODEL_PATH);

        // Get all the printingModelList where printingModelPath does not contain UPDATED_PRINTING_MODEL_PATH
        defaultPrintingModelShouldBeFound("printingModelPath.doesNotContain=" + UPDATED_PRINTING_MODEL_PATH);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelDescription equals to DEFAULT_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldBeFound("printingModelDescription.equals=" + DEFAULT_PRINTING_MODEL_DESCRIPTION);

        // Get all the printingModelList where printingModelDescription equals to UPDATED_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldNotBeFound("printingModelDescription.equals=" + UPDATED_PRINTING_MODEL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelDescription in DEFAULT_PRINTING_MODEL_DESCRIPTION or UPDATED_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldBeFound(
            "printingModelDescription.in=" + DEFAULT_PRINTING_MODEL_DESCRIPTION + "," + UPDATED_PRINTING_MODEL_DESCRIPTION
        );

        // Get all the printingModelList where printingModelDescription equals to UPDATED_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldNotBeFound("printingModelDescription.in=" + UPDATED_PRINTING_MODEL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelDescription is not null
        defaultPrintingModelShouldBeFound("printingModelDescription.specified=true");

        // Get all the printingModelList where printingModelDescription is null
        defaultPrintingModelShouldNotBeFound("printingModelDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelDescriptionContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelDescription contains DEFAULT_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldBeFound("printingModelDescription.contains=" + DEFAULT_PRINTING_MODEL_DESCRIPTION);

        // Get all the printingModelList where printingModelDescription contains UPDATED_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldNotBeFound("printingModelDescription.contains=" + UPDATED_PRINTING_MODEL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelDescription does not contain DEFAULT_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldNotBeFound("printingModelDescription.doesNotContain=" + DEFAULT_PRINTING_MODEL_DESCRIPTION);

        // Get all the printingModelList where printingModelDescription does not contain UPDATED_PRINTING_MODEL_DESCRIPTION
        defaultPrintingModelShouldBeFound("printingModelDescription.doesNotContain=" + UPDATED_PRINTING_MODEL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelParams equals to DEFAULT_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldBeFound("printingModelParams.equals=" + DEFAULT_PRINTING_MODEL_PARAMS);

        // Get all the printingModelList where printingModelParams equals to UPDATED_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldNotBeFound("printingModelParams.equals=" + UPDATED_PRINTING_MODEL_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelParamsIsInShouldWork() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelParams in DEFAULT_PRINTING_MODEL_PARAMS or UPDATED_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldBeFound("printingModelParams.in=" + DEFAULT_PRINTING_MODEL_PARAMS + "," + UPDATED_PRINTING_MODEL_PARAMS);

        // Get all the printingModelList where printingModelParams equals to UPDATED_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldNotBeFound("printingModelParams.in=" + UPDATED_PRINTING_MODEL_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelParams is not null
        defaultPrintingModelShouldBeFound("printingModelParams.specified=true");

        // Get all the printingModelList where printingModelParams is null
        defaultPrintingModelShouldNotBeFound("printingModelParams.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelParamsContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelParams contains DEFAULT_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldBeFound("printingModelParams.contains=" + DEFAULT_PRINTING_MODEL_PARAMS);

        // Get all the printingModelList where printingModelParams contains UPDATED_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldNotBeFound("printingModelParams.contains=" + UPDATED_PRINTING_MODEL_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelParamsNotContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelParams does not contain DEFAULT_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldNotBeFound("printingModelParams.doesNotContain=" + DEFAULT_PRINTING_MODEL_PARAMS);

        // Get all the printingModelList where printingModelParams does not contain UPDATED_PRINTING_MODEL_PARAMS
        defaultPrintingModelShouldBeFound("printingModelParams.doesNotContain=" + UPDATED_PRINTING_MODEL_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelAttributs equals to DEFAULT_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldBeFound("printingModelAttributs.equals=" + DEFAULT_PRINTING_MODEL_ATTRIBUTS);

        // Get all the printingModelList where printingModelAttributs equals to UPDATED_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldNotBeFound("printingModelAttributs.equals=" + UPDATED_PRINTING_MODEL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelAttributs in DEFAULT_PRINTING_MODEL_ATTRIBUTS or UPDATED_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldBeFound(
            "printingModelAttributs.in=" + DEFAULT_PRINTING_MODEL_ATTRIBUTS + "," + UPDATED_PRINTING_MODEL_ATTRIBUTS
        );

        // Get all the printingModelList where printingModelAttributs equals to UPDATED_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldNotBeFound("printingModelAttributs.in=" + UPDATED_PRINTING_MODEL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelAttributs is not null
        defaultPrintingModelShouldBeFound("printingModelAttributs.specified=true");

        // Get all the printingModelList where printingModelAttributs is null
        defaultPrintingModelShouldNotBeFound("printingModelAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelAttributsContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelAttributs contains DEFAULT_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldBeFound("printingModelAttributs.contains=" + DEFAULT_PRINTING_MODEL_ATTRIBUTS);

        // Get all the printingModelList where printingModelAttributs contains UPDATED_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldNotBeFound("printingModelAttributs.contains=" + UPDATED_PRINTING_MODEL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelAttributs does not contain DEFAULT_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldNotBeFound("printingModelAttributs.doesNotContain=" + DEFAULT_PRINTING_MODEL_ATTRIBUTS);

        // Get all the printingModelList where printingModelAttributs does not contain UPDATED_PRINTING_MODEL_ATTRIBUTS
        defaultPrintingModelShouldBeFound("printingModelAttributs.doesNotContain=" + UPDATED_PRINTING_MODEL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelStatIsEqualToSomething() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelStat equals to DEFAULT_PRINTING_MODEL_STAT
        defaultPrintingModelShouldBeFound("printingModelStat.equals=" + DEFAULT_PRINTING_MODEL_STAT);

        // Get all the printingModelList where printingModelStat equals to UPDATED_PRINTING_MODEL_STAT
        defaultPrintingModelShouldNotBeFound("printingModelStat.equals=" + UPDATED_PRINTING_MODEL_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelStatIsInShouldWork() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelStat in DEFAULT_PRINTING_MODEL_STAT or UPDATED_PRINTING_MODEL_STAT
        defaultPrintingModelShouldBeFound("printingModelStat.in=" + DEFAULT_PRINTING_MODEL_STAT + "," + UPDATED_PRINTING_MODEL_STAT);

        // Get all the printingModelList where printingModelStat equals to UPDATED_PRINTING_MODEL_STAT
        defaultPrintingModelShouldNotBeFound("printingModelStat.in=" + UPDATED_PRINTING_MODEL_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingModelStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        // Get all the printingModelList where printingModelStat is not null
        defaultPrintingModelShouldBeFound("printingModelStat.specified=true");

        // Get all the printingModelList where printingModelStat is null
        defaultPrintingModelShouldNotBeFound("printingModelStat.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingModelsByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            printingModelRepository.saveAndFlush(printingModel);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        printingModel.addPrintingCentre(printingCentre);
        printingModelRepository.saveAndFlush(printingModel);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the printingModelList where printingCentre equals to printingCentreId
        defaultPrintingModelShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the printingModelList where printingCentre equals to (printingCentreId + 1)
        defaultPrintingModelShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingModelsByAccreditationTypeIsEqualToSomething() throws Exception {
        AccreditationType accreditationType;
        if (TestUtil.findAll(em, AccreditationType.class).isEmpty()) {
            printingModelRepository.saveAndFlush(printingModel);
            accreditationType = AccreditationTypeResourceIT.createEntity(em);
        } else {
            accreditationType = TestUtil.findAll(em, AccreditationType.class).get(0);
        }
        em.persist(accreditationType);
        em.flush();
        printingModel.addAccreditationType(accreditationType);
        printingModelRepository.saveAndFlush(printingModel);
        Long accreditationTypeId = accreditationType.getAccreditationTypeId();

        // Get all the printingModelList where accreditationType equals to accreditationTypeId
        defaultPrintingModelShouldBeFound("accreditationTypeId.equals=" + accreditationTypeId);

        // Get all the printingModelList where accreditationType equals to (accreditationTypeId + 1)
        defaultPrintingModelShouldNotBeFound("accreditationTypeId.equals=" + (accreditationTypeId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingModelsByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            printingModelRepository.saveAndFlush(printingModel);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        printingModel.addCategory(category);
        printingModelRepository.saveAndFlush(printingModel);
        Long categoryId = category.getCategoryId();

        // Get all the printingModelList where category equals to categoryId
        defaultPrintingModelShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the printingModelList where category equals to (categoryId + 1)
        defaultPrintingModelShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingModelsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            printingModelRepository.saveAndFlush(printingModel);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        printingModel.setEvent(event);
        printingModelRepository.saveAndFlush(printingModel);
        Long eventId = event.getEventId();

        // Get all the printingModelList where event equals to eventId
        defaultPrintingModelShouldBeFound("eventId.equals=" + eventId);

        // Get all the printingModelList where event equals to (eventId + 1)
        defaultPrintingModelShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrintingModelShouldBeFound(String filter) throws Exception {
        restPrintingModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingModelId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingModelId").value(hasItem(printingModel.getPrintingModelId().intValue())))
            .andExpect(jsonPath("$.[*].printingModelName").value(hasItem(DEFAULT_PRINTING_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].printingModelFile").value(hasItem(DEFAULT_PRINTING_MODEL_FILE)))
            .andExpect(jsonPath("$.[*].printingModelPath").value(hasItem(DEFAULT_PRINTING_MODEL_PATH)))
            .andExpect(jsonPath("$.[*].printingModelDescription").value(hasItem(DEFAULT_PRINTING_MODEL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingModelDataContentType").value(hasItem(DEFAULT_PRINTING_MODEL_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].printingModelData").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRINTING_MODEL_DATA))))
            .andExpect(jsonPath("$.[*].printingModelParams").value(hasItem(DEFAULT_PRINTING_MODEL_PARAMS)))
            .andExpect(jsonPath("$.[*].printingModelAttributs").value(hasItem(DEFAULT_PRINTING_MODEL_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].printingModelStat").value(hasItem(DEFAULT_PRINTING_MODEL_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restPrintingModelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingModelId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrintingModelShouldNotBeFound(String filter) throws Exception {
        restPrintingModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingModelId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrintingModelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingModelId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPrintingModel() throws Exception {
        // Get the printingModel
        restPrintingModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrintingModel() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();

        // Update the printingModel
        PrintingModel updatedPrintingModel = printingModelRepository.findById(printingModel.getPrintingModelId()).get();
        // Disconnect from session so that the updates on updatedPrintingModel are not directly saved in db
        em.detach(updatedPrintingModel);
        updatedPrintingModel
            .printingModelName(UPDATED_PRINTING_MODEL_NAME)
            .printingModelFile(UPDATED_PRINTING_MODEL_FILE)
            .printingModelPath(UPDATED_PRINTING_MODEL_PATH)
            .printingModelDescription(UPDATED_PRINTING_MODEL_DESCRIPTION)
            .printingModelData(UPDATED_PRINTING_MODEL_DATA)
            .printingModelDataContentType(UPDATED_PRINTING_MODEL_DATA_CONTENT_TYPE)
            .printingModelParams(UPDATED_PRINTING_MODEL_PARAMS)
            .printingModelAttributs(UPDATED_PRINTING_MODEL_ATTRIBUTS)
            .printingModelStat(UPDATED_PRINTING_MODEL_STAT);
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(updatedPrintingModel);

        restPrintingModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingModelDTO.getPrintingModelId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isOk());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
        PrintingModel testPrintingModel = printingModelList.get(printingModelList.size() - 1);
        assertThat(testPrintingModel.getPrintingModelName()).isEqualTo(UPDATED_PRINTING_MODEL_NAME);
        assertThat(testPrintingModel.getPrintingModelFile()).isEqualTo(UPDATED_PRINTING_MODEL_FILE);
        assertThat(testPrintingModel.getPrintingModelPath()).isEqualTo(UPDATED_PRINTING_MODEL_PATH);
        assertThat(testPrintingModel.getPrintingModelDescription()).isEqualTo(UPDATED_PRINTING_MODEL_DESCRIPTION);
        assertThat(testPrintingModel.getPrintingModelData()).isEqualTo(UPDATED_PRINTING_MODEL_DATA);
        assertThat(testPrintingModel.getPrintingModelDataContentType()).isEqualTo(UPDATED_PRINTING_MODEL_DATA_CONTENT_TYPE);
        assertThat(testPrintingModel.getPrintingModelParams()).isEqualTo(UPDATED_PRINTING_MODEL_PARAMS);
        assertThat(testPrintingModel.getPrintingModelAttributs()).isEqualTo(UPDATED_PRINTING_MODEL_ATTRIBUTS);
        assertThat(testPrintingModel.getPrintingModelStat()).isEqualTo(UPDATED_PRINTING_MODEL_STAT);
    }

    @Test
    @Transactional
    void putNonExistingPrintingModel() throws Exception {
        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();
        printingModel.setPrintingModelId(count.incrementAndGet());

        // Create the PrintingModel
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingModelDTO.getPrintingModelId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrintingModel() throws Exception {
        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();
        printingModel.setPrintingModelId(count.incrementAndGet());

        // Create the PrintingModel
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrintingModel() throws Exception {
        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();
        printingModel.setPrintingModelId(count.incrementAndGet());

        // Create the PrintingModel
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingModelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrintingModelWithPatch() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();

        // Update the printingModel using partial update
        PrintingModel partialUpdatedPrintingModel = new PrintingModel();
        partialUpdatedPrintingModel.setPrintingModelId(printingModel.getPrintingModelId());

        partialUpdatedPrintingModel.printingModelStat(UPDATED_PRINTING_MODEL_STAT);

        restPrintingModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingModel.getPrintingModelId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingModel))
            )
            .andExpect(status().isOk());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
        PrintingModel testPrintingModel = printingModelList.get(printingModelList.size() - 1);
        assertThat(testPrintingModel.getPrintingModelName()).isEqualTo(DEFAULT_PRINTING_MODEL_NAME);
        assertThat(testPrintingModel.getPrintingModelFile()).isEqualTo(DEFAULT_PRINTING_MODEL_FILE);
        assertThat(testPrintingModel.getPrintingModelPath()).isEqualTo(DEFAULT_PRINTING_MODEL_PATH);
        assertThat(testPrintingModel.getPrintingModelDescription()).isEqualTo(DEFAULT_PRINTING_MODEL_DESCRIPTION);
        assertThat(testPrintingModel.getPrintingModelData()).isEqualTo(DEFAULT_PRINTING_MODEL_DATA);
        assertThat(testPrintingModel.getPrintingModelDataContentType()).isEqualTo(DEFAULT_PRINTING_MODEL_DATA_CONTENT_TYPE);
        assertThat(testPrintingModel.getPrintingModelParams()).isEqualTo(DEFAULT_PRINTING_MODEL_PARAMS);
        assertThat(testPrintingModel.getPrintingModelAttributs()).isEqualTo(DEFAULT_PRINTING_MODEL_ATTRIBUTS);
        assertThat(testPrintingModel.getPrintingModelStat()).isEqualTo(UPDATED_PRINTING_MODEL_STAT);
    }

    @Test
    @Transactional
    void fullUpdatePrintingModelWithPatch() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();

        // Update the printingModel using partial update
        PrintingModel partialUpdatedPrintingModel = new PrintingModel();
        partialUpdatedPrintingModel.setPrintingModelId(printingModel.getPrintingModelId());

        partialUpdatedPrintingModel
            .printingModelName(UPDATED_PRINTING_MODEL_NAME)
            .printingModelFile(UPDATED_PRINTING_MODEL_FILE)
            .printingModelPath(UPDATED_PRINTING_MODEL_PATH)
            .printingModelDescription(UPDATED_PRINTING_MODEL_DESCRIPTION)
            .printingModelData(UPDATED_PRINTING_MODEL_DATA)
            .printingModelDataContentType(UPDATED_PRINTING_MODEL_DATA_CONTENT_TYPE)
            .printingModelParams(UPDATED_PRINTING_MODEL_PARAMS)
            .printingModelAttributs(UPDATED_PRINTING_MODEL_ATTRIBUTS)
            .printingModelStat(UPDATED_PRINTING_MODEL_STAT);

        restPrintingModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingModel.getPrintingModelId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingModel))
            )
            .andExpect(status().isOk());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
        PrintingModel testPrintingModel = printingModelList.get(printingModelList.size() - 1);
        assertThat(testPrintingModel.getPrintingModelName()).isEqualTo(UPDATED_PRINTING_MODEL_NAME);
        assertThat(testPrintingModel.getPrintingModelFile()).isEqualTo(UPDATED_PRINTING_MODEL_FILE);
        assertThat(testPrintingModel.getPrintingModelPath()).isEqualTo(UPDATED_PRINTING_MODEL_PATH);
        assertThat(testPrintingModel.getPrintingModelDescription()).isEqualTo(UPDATED_PRINTING_MODEL_DESCRIPTION);
        assertThat(testPrintingModel.getPrintingModelData()).isEqualTo(UPDATED_PRINTING_MODEL_DATA);
        assertThat(testPrintingModel.getPrintingModelDataContentType()).isEqualTo(UPDATED_PRINTING_MODEL_DATA_CONTENT_TYPE);
        assertThat(testPrintingModel.getPrintingModelParams()).isEqualTo(UPDATED_PRINTING_MODEL_PARAMS);
        assertThat(testPrintingModel.getPrintingModelAttributs()).isEqualTo(UPDATED_PRINTING_MODEL_ATTRIBUTS);
        assertThat(testPrintingModel.getPrintingModelStat()).isEqualTo(UPDATED_PRINTING_MODEL_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingPrintingModel() throws Exception {
        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();
        printingModel.setPrintingModelId(count.incrementAndGet());

        // Create the PrintingModel
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, printingModelDTO.getPrintingModelId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrintingModel() throws Exception {
        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();
        printingModel.setPrintingModelId(count.incrementAndGet());

        // Create the PrintingModel
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrintingModel() throws Exception {
        int databaseSizeBeforeUpdate = printingModelRepository.findAll().size();
        printingModel.setPrintingModelId(count.incrementAndGet());

        // Create the PrintingModel
        PrintingModelDTO printingModelDTO = printingModelMapper.toDto(printingModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingModelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingModelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingModel in the database
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrintingModel() throws Exception {
        // Initialize the database
        printingModelRepository.saveAndFlush(printingModel);

        int databaseSizeBeforeDelete = printingModelRepository.findAll().size();

        // Delete the printingModel
        restPrintingModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, printingModel.getPrintingModelId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrintingModel> printingModelList = printingModelRepository.findAll();
        assertThat(printingModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
