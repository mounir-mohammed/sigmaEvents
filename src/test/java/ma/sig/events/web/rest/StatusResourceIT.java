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
import ma.sig.events.domain.Status;
import ma.sig.events.repository.StatusRepository;
import ma.sig.events.service.criteria.StatusCriteria;
import ma.sig.events.service.dto.StatusDTO;
import ma.sig.events.service.mapper.StatusMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatusResourceIT {

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS_USER_CAN_PRINT = false;
    private static final Boolean UPDATED_STATUS_USER_CAN_PRINT = true;

    private static final Boolean DEFAULT_STATUS_USER_CAN_UPDATE = false;
    private static final Boolean UPDATED_STATUS_USER_CAN_UPDATE = true;

    private static final Boolean DEFAULT_STATUS_USER_CAN_VALIDATE = false;
    private static final Boolean UPDATED_STATUS_USER_CAN_VALIDATE = true;

    private static final String DEFAULT_STATUS_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS_STAT = false;
    private static final Boolean UPDATED_STATUS_STAT = true;

    private static final String ENTITY_API_URL = "/api/statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{statusId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusMockMvc;

    private Status status;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createEntity(EntityManager em) {
        Status status = new Status()
            .statusName(DEFAULT_STATUS_NAME)
            .statusAbreviation(DEFAULT_STATUS_ABREVIATION)
            .statusColor(DEFAULT_STATUS_COLOR)
            .statusDescription(DEFAULT_STATUS_DESCRIPTION)
            .statusUserCanPrint(DEFAULT_STATUS_USER_CAN_PRINT)
            .statusUserCanUpdate(DEFAULT_STATUS_USER_CAN_UPDATE)
            .statusUserCanValidate(DEFAULT_STATUS_USER_CAN_VALIDATE)
            .statusParams(DEFAULT_STATUS_PARAMS)
            .statusAttributs(DEFAULT_STATUS_ATTRIBUTS)
            .statusStat(DEFAULT_STATUS_STAT);
        return status;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createUpdatedEntity(EntityManager em) {
        Status status = new Status()
            .statusName(UPDATED_STATUS_NAME)
            .statusAbreviation(UPDATED_STATUS_ABREVIATION)
            .statusColor(UPDATED_STATUS_COLOR)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusUserCanPrint(UPDATED_STATUS_USER_CAN_PRINT)
            .statusUserCanUpdate(UPDATED_STATUS_USER_CAN_UPDATE)
            .statusUserCanValidate(UPDATED_STATUS_USER_CAN_VALIDATE)
            .statusParams(UPDATED_STATUS_PARAMS)
            .statusAttributs(UPDATED_STATUS_ATTRIBUTS)
            .statusStat(UPDATED_STATUS_STAT);
        return status;
    }

    @BeforeEach
    public void initTest() {
        status = createEntity(em);
    }

    @Test
    @Transactional
    void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();
        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);
        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testStatus.getStatusAbreviation()).isEqualTo(DEFAULT_STATUS_ABREVIATION);
        assertThat(testStatus.getStatusColor()).isEqualTo(DEFAULT_STATUS_COLOR);
        assertThat(testStatus.getStatusDescription()).isEqualTo(DEFAULT_STATUS_DESCRIPTION);
        assertThat(testStatus.getStatusUserCanPrint()).isEqualTo(DEFAULT_STATUS_USER_CAN_PRINT);
        assertThat(testStatus.getStatusUserCanUpdate()).isEqualTo(DEFAULT_STATUS_USER_CAN_UPDATE);
        assertThat(testStatus.getStatusUserCanValidate()).isEqualTo(DEFAULT_STATUS_USER_CAN_VALIDATE);
        assertThat(testStatus.getStatusParams()).isEqualTo(DEFAULT_STATUS_PARAMS);
        assertThat(testStatus.getStatusAttributs()).isEqualTo(DEFAULT_STATUS_ATTRIBUTS);
        assertThat(testStatus.getStatusStat()).isEqualTo(DEFAULT_STATUS_STAT);
    }

    @Test
    @Transactional
    void createStatusWithExistingId() throws Exception {
        // Create the Status with an existing ID
        status.setStatusId(1L);
        StatusDTO statusDTO = statusMapper.toDto(status);

        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setStatusName(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=statusId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].statusId").value(hasItem(status.getStatusId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].statusAbreviation").value(hasItem(DEFAULT_STATUS_ABREVIATION)))
            .andExpect(jsonPath("$.[*].statusColor").value(hasItem(DEFAULT_STATUS_COLOR)))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusUserCanPrint").value(hasItem(DEFAULT_STATUS_USER_CAN_PRINT.booleanValue())))
            .andExpect(jsonPath("$.[*].statusUserCanUpdate").value(hasItem(DEFAULT_STATUS_USER_CAN_UPDATE.booleanValue())))
            .andExpect(jsonPath("$.[*].statusUserCanValidate").value(hasItem(DEFAULT_STATUS_USER_CAN_VALIDATE.booleanValue())))
            .andExpect(jsonPath("$.[*].statusParams").value(hasItem(DEFAULT_STATUS_PARAMS)))
            .andExpect(jsonPath("$.[*].statusAttributs").value(hasItem(DEFAULT_STATUS_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].statusStat").value(hasItem(DEFAULT_STATUS_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get the status
        restStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, status.getStatusId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.statusId").value(status.getStatusId().intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME))
            .andExpect(jsonPath("$.statusAbreviation").value(DEFAULT_STATUS_ABREVIATION))
            .andExpect(jsonPath("$.statusColor").value(DEFAULT_STATUS_COLOR))
            .andExpect(jsonPath("$.statusDescription").value(DEFAULT_STATUS_DESCRIPTION))
            .andExpect(jsonPath("$.statusUserCanPrint").value(DEFAULT_STATUS_USER_CAN_PRINT.booleanValue()))
            .andExpect(jsonPath("$.statusUserCanUpdate").value(DEFAULT_STATUS_USER_CAN_UPDATE.booleanValue()))
            .andExpect(jsonPath("$.statusUserCanValidate").value(DEFAULT_STATUS_USER_CAN_VALIDATE.booleanValue()))
            .andExpect(jsonPath("$.statusParams").value(DEFAULT_STATUS_PARAMS))
            .andExpect(jsonPath("$.statusAttributs").value(DEFAULT_STATUS_ATTRIBUTS))
            .andExpect(jsonPath("$.statusStat").value(DEFAULT_STATUS_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getStatusesByIdFiltering() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        Long id = status.getStatusId();

        defaultStatusShouldBeFound("statusId.equals=" + id);
        defaultStatusShouldNotBeFound("statusId.notEquals=" + id);

        defaultStatusShouldBeFound("statusId.greaterThanOrEqual=" + id);
        defaultStatusShouldNotBeFound("statusId.greaterThan=" + id);

        defaultStatusShouldBeFound("statusId.lessThanOrEqual=" + id);
        defaultStatusShouldNotBeFound("statusId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName equals to DEFAULT_STATUS_NAME
        defaultStatusShouldBeFound("statusName.equals=" + DEFAULT_STATUS_NAME);

        // Get all the statusList where statusName equals to UPDATED_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.equals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName in DEFAULT_STATUS_NAME or UPDATED_STATUS_NAME
        defaultStatusShouldBeFound("statusName.in=" + DEFAULT_STATUS_NAME + "," + UPDATED_STATUS_NAME);

        // Get all the statusList where statusName equals to UPDATED_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.in=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName is not null
        defaultStatusShouldBeFound("statusName.specified=true");

        // Get all the statusList where statusName is null
        defaultStatusShouldNotBeFound("statusName.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName contains DEFAULT_STATUS_NAME
        defaultStatusShouldBeFound("statusName.contains=" + DEFAULT_STATUS_NAME);

        // Get all the statusList where statusName contains UPDATED_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.contains=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName does not contain DEFAULT_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.doesNotContain=" + DEFAULT_STATUS_NAME);

        // Get all the statusList where statusName does not contain UPDATED_STATUS_NAME
        defaultStatusShouldBeFound("statusName.doesNotContain=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAbreviation equals to DEFAULT_STATUS_ABREVIATION
        defaultStatusShouldBeFound("statusAbreviation.equals=" + DEFAULT_STATUS_ABREVIATION);

        // Get all the statusList where statusAbreviation equals to UPDATED_STATUS_ABREVIATION
        defaultStatusShouldNotBeFound("statusAbreviation.equals=" + UPDATED_STATUS_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAbreviation in DEFAULT_STATUS_ABREVIATION or UPDATED_STATUS_ABREVIATION
        defaultStatusShouldBeFound("statusAbreviation.in=" + DEFAULT_STATUS_ABREVIATION + "," + UPDATED_STATUS_ABREVIATION);

        // Get all the statusList where statusAbreviation equals to UPDATED_STATUS_ABREVIATION
        defaultStatusShouldNotBeFound("statusAbreviation.in=" + UPDATED_STATUS_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAbreviation is not null
        defaultStatusShouldBeFound("statusAbreviation.specified=true");

        // Get all the statusList where statusAbreviation is null
        defaultStatusShouldNotBeFound("statusAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAbreviationContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAbreviation contains DEFAULT_STATUS_ABREVIATION
        defaultStatusShouldBeFound("statusAbreviation.contains=" + DEFAULT_STATUS_ABREVIATION);

        // Get all the statusList where statusAbreviation contains UPDATED_STATUS_ABREVIATION
        defaultStatusShouldNotBeFound("statusAbreviation.contains=" + UPDATED_STATUS_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAbreviation does not contain DEFAULT_STATUS_ABREVIATION
        defaultStatusShouldNotBeFound("statusAbreviation.doesNotContain=" + DEFAULT_STATUS_ABREVIATION);

        // Get all the statusList where statusAbreviation does not contain UPDATED_STATUS_ABREVIATION
        defaultStatusShouldBeFound("statusAbreviation.doesNotContain=" + UPDATED_STATUS_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusColorIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusColor equals to DEFAULT_STATUS_COLOR
        defaultStatusShouldBeFound("statusColor.equals=" + DEFAULT_STATUS_COLOR);

        // Get all the statusList where statusColor equals to UPDATED_STATUS_COLOR
        defaultStatusShouldNotBeFound("statusColor.equals=" + UPDATED_STATUS_COLOR);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusColorIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusColor in DEFAULT_STATUS_COLOR or UPDATED_STATUS_COLOR
        defaultStatusShouldBeFound("statusColor.in=" + DEFAULT_STATUS_COLOR + "," + UPDATED_STATUS_COLOR);

        // Get all the statusList where statusColor equals to UPDATED_STATUS_COLOR
        defaultStatusShouldNotBeFound("statusColor.in=" + UPDATED_STATUS_COLOR);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusColor is not null
        defaultStatusShouldBeFound("statusColor.specified=true");

        // Get all the statusList where statusColor is null
        defaultStatusShouldNotBeFound("statusColor.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusColorContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusColor contains DEFAULT_STATUS_COLOR
        defaultStatusShouldBeFound("statusColor.contains=" + DEFAULT_STATUS_COLOR);

        // Get all the statusList where statusColor contains UPDATED_STATUS_COLOR
        defaultStatusShouldNotBeFound("statusColor.contains=" + UPDATED_STATUS_COLOR);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusColorNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusColor does not contain DEFAULT_STATUS_COLOR
        defaultStatusShouldNotBeFound("statusColor.doesNotContain=" + DEFAULT_STATUS_COLOR);

        // Get all the statusList where statusColor does not contain UPDATED_STATUS_COLOR
        defaultStatusShouldBeFound("statusColor.doesNotContain=" + UPDATED_STATUS_COLOR);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescription equals to DEFAULT_STATUS_DESCRIPTION
        defaultStatusShouldBeFound("statusDescription.equals=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the statusList where statusDescription equals to UPDATED_STATUS_DESCRIPTION
        defaultStatusShouldNotBeFound("statusDescription.equals=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescription in DEFAULT_STATUS_DESCRIPTION or UPDATED_STATUS_DESCRIPTION
        defaultStatusShouldBeFound("statusDescription.in=" + DEFAULT_STATUS_DESCRIPTION + "," + UPDATED_STATUS_DESCRIPTION);

        // Get all the statusList where statusDescription equals to UPDATED_STATUS_DESCRIPTION
        defaultStatusShouldNotBeFound("statusDescription.in=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescription is not null
        defaultStatusShouldBeFound("statusDescription.specified=true");

        // Get all the statusList where statusDescription is null
        defaultStatusShouldNotBeFound("statusDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescription contains DEFAULT_STATUS_DESCRIPTION
        defaultStatusShouldBeFound("statusDescription.contains=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the statusList where statusDescription contains UPDATED_STATUS_DESCRIPTION
        defaultStatusShouldNotBeFound("statusDescription.contains=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescription does not contain DEFAULT_STATUS_DESCRIPTION
        defaultStatusShouldNotBeFound("statusDescription.doesNotContain=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the statusList where statusDescription does not contain UPDATED_STATUS_DESCRIPTION
        defaultStatusShouldBeFound("statusDescription.doesNotContain=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanPrintIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanPrint equals to DEFAULT_STATUS_USER_CAN_PRINT
        defaultStatusShouldBeFound("statusUserCanPrint.equals=" + DEFAULT_STATUS_USER_CAN_PRINT);

        // Get all the statusList where statusUserCanPrint equals to UPDATED_STATUS_USER_CAN_PRINT
        defaultStatusShouldNotBeFound("statusUserCanPrint.equals=" + UPDATED_STATUS_USER_CAN_PRINT);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanPrintIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanPrint in DEFAULT_STATUS_USER_CAN_PRINT or UPDATED_STATUS_USER_CAN_PRINT
        defaultStatusShouldBeFound("statusUserCanPrint.in=" + DEFAULT_STATUS_USER_CAN_PRINT + "," + UPDATED_STATUS_USER_CAN_PRINT);

        // Get all the statusList where statusUserCanPrint equals to UPDATED_STATUS_USER_CAN_PRINT
        defaultStatusShouldNotBeFound("statusUserCanPrint.in=" + UPDATED_STATUS_USER_CAN_PRINT);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanPrintIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanPrint is not null
        defaultStatusShouldBeFound("statusUserCanPrint.specified=true");

        // Get all the statusList where statusUserCanPrint is null
        defaultStatusShouldNotBeFound("statusUserCanPrint.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanUpdate equals to DEFAULT_STATUS_USER_CAN_UPDATE
        defaultStatusShouldBeFound("statusUserCanUpdate.equals=" + DEFAULT_STATUS_USER_CAN_UPDATE);

        // Get all the statusList where statusUserCanUpdate equals to UPDATED_STATUS_USER_CAN_UPDATE
        defaultStatusShouldNotBeFound("statusUserCanUpdate.equals=" + UPDATED_STATUS_USER_CAN_UPDATE);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanUpdate in DEFAULT_STATUS_USER_CAN_UPDATE or UPDATED_STATUS_USER_CAN_UPDATE
        defaultStatusShouldBeFound("statusUserCanUpdate.in=" + DEFAULT_STATUS_USER_CAN_UPDATE + "," + UPDATED_STATUS_USER_CAN_UPDATE);

        // Get all the statusList where statusUserCanUpdate equals to UPDATED_STATUS_USER_CAN_UPDATE
        defaultStatusShouldNotBeFound("statusUserCanUpdate.in=" + UPDATED_STATUS_USER_CAN_UPDATE);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanUpdate is not null
        defaultStatusShouldBeFound("statusUserCanUpdate.specified=true");

        // Get all the statusList where statusUserCanUpdate is null
        defaultStatusShouldNotBeFound("statusUserCanUpdate.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanValidateIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanValidate equals to DEFAULT_STATUS_USER_CAN_VALIDATE
        defaultStatusShouldBeFound("statusUserCanValidate.equals=" + DEFAULT_STATUS_USER_CAN_VALIDATE);

        // Get all the statusList where statusUserCanValidate equals to UPDATED_STATUS_USER_CAN_VALIDATE
        defaultStatusShouldNotBeFound("statusUserCanValidate.equals=" + UPDATED_STATUS_USER_CAN_VALIDATE);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanValidateIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanValidate in DEFAULT_STATUS_USER_CAN_VALIDATE or UPDATED_STATUS_USER_CAN_VALIDATE
        defaultStatusShouldBeFound("statusUserCanValidate.in=" + DEFAULT_STATUS_USER_CAN_VALIDATE + "," + UPDATED_STATUS_USER_CAN_VALIDATE);

        // Get all the statusList where statusUserCanValidate equals to UPDATED_STATUS_USER_CAN_VALIDATE
        defaultStatusShouldNotBeFound("statusUserCanValidate.in=" + UPDATED_STATUS_USER_CAN_VALIDATE);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusUserCanValidateIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusUserCanValidate is not null
        defaultStatusShouldBeFound("statusUserCanValidate.specified=true");

        // Get all the statusList where statusUserCanValidate is null
        defaultStatusShouldNotBeFound("statusUserCanValidate.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusParams equals to DEFAULT_STATUS_PARAMS
        defaultStatusShouldBeFound("statusParams.equals=" + DEFAULT_STATUS_PARAMS);

        // Get all the statusList where statusParams equals to UPDATED_STATUS_PARAMS
        defaultStatusShouldNotBeFound("statusParams.equals=" + UPDATED_STATUS_PARAMS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusParamsIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusParams in DEFAULT_STATUS_PARAMS or UPDATED_STATUS_PARAMS
        defaultStatusShouldBeFound("statusParams.in=" + DEFAULT_STATUS_PARAMS + "," + UPDATED_STATUS_PARAMS);

        // Get all the statusList where statusParams equals to UPDATED_STATUS_PARAMS
        defaultStatusShouldNotBeFound("statusParams.in=" + UPDATED_STATUS_PARAMS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusParams is not null
        defaultStatusShouldBeFound("statusParams.specified=true");

        // Get all the statusList where statusParams is null
        defaultStatusShouldNotBeFound("statusParams.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusParamsContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusParams contains DEFAULT_STATUS_PARAMS
        defaultStatusShouldBeFound("statusParams.contains=" + DEFAULT_STATUS_PARAMS);

        // Get all the statusList where statusParams contains UPDATED_STATUS_PARAMS
        defaultStatusShouldNotBeFound("statusParams.contains=" + UPDATED_STATUS_PARAMS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusParamsNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusParams does not contain DEFAULT_STATUS_PARAMS
        defaultStatusShouldNotBeFound("statusParams.doesNotContain=" + DEFAULT_STATUS_PARAMS);

        // Get all the statusList where statusParams does not contain UPDATED_STATUS_PARAMS
        defaultStatusShouldBeFound("statusParams.doesNotContain=" + UPDATED_STATUS_PARAMS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAttributs equals to DEFAULT_STATUS_ATTRIBUTS
        defaultStatusShouldBeFound("statusAttributs.equals=" + DEFAULT_STATUS_ATTRIBUTS);

        // Get all the statusList where statusAttributs equals to UPDATED_STATUS_ATTRIBUTS
        defaultStatusShouldNotBeFound("statusAttributs.equals=" + UPDATED_STATUS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAttributs in DEFAULT_STATUS_ATTRIBUTS or UPDATED_STATUS_ATTRIBUTS
        defaultStatusShouldBeFound("statusAttributs.in=" + DEFAULT_STATUS_ATTRIBUTS + "," + UPDATED_STATUS_ATTRIBUTS);

        // Get all the statusList where statusAttributs equals to UPDATED_STATUS_ATTRIBUTS
        defaultStatusShouldNotBeFound("statusAttributs.in=" + UPDATED_STATUS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAttributs is not null
        defaultStatusShouldBeFound("statusAttributs.specified=true");

        // Get all the statusList where statusAttributs is null
        defaultStatusShouldNotBeFound("statusAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAttributsContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAttributs contains DEFAULT_STATUS_ATTRIBUTS
        defaultStatusShouldBeFound("statusAttributs.contains=" + DEFAULT_STATUS_ATTRIBUTS);

        // Get all the statusList where statusAttributs contains UPDATED_STATUS_ATTRIBUTS
        defaultStatusShouldNotBeFound("statusAttributs.contains=" + UPDATED_STATUS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusAttributs does not contain DEFAULT_STATUS_ATTRIBUTS
        defaultStatusShouldNotBeFound("statusAttributs.doesNotContain=" + DEFAULT_STATUS_ATTRIBUTS);

        // Get all the statusList where statusAttributs does not contain UPDATED_STATUS_ATTRIBUTS
        defaultStatusShouldBeFound("statusAttributs.doesNotContain=" + UPDATED_STATUS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusStatIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusStat equals to DEFAULT_STATUS_STAT
        defaultStatusShouldBeFound("statusStat.equals=" + DEFAULT_STATUS_STAT);

        // Get all the statusList where statusStat equals to UPDATED_STATUS_STAT
        defaultStatusShouldNotBeFound("statusStat.equals=" + UPDATED_STATUS_STAT);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusStatIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusStat in DEFAULT_STATUS_STAT or UPDATED_STATUS_STAT
        defaultStatusShouldBeFound("statusStat.in=" + DEFAULT_STATUS_STAT + "," + UPDATED_STATUS_STAT);

        // Get all the statusList where statusStat equals to UPDATED_STATUS_STAT
        defaultStatusShouldNotBeFound("statusStat.in=" + UPDATED_STATUS_STAT);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusStat is not null
        defaultStatusShouldBeFound("statusStat.specified=true");

        // Get all the statusList where statusStat is null
        defaultStatusShouldNotBeFound("statusStat.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            statusRepository.saveAndFlush(status);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        status.addAccreditation(accreditation);
        statusRepository.saveAndFlush(status);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the statusList where accreditation equals to accreditationId
        defaultStatusShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the statusList where accreditation equals to (accreditationId + 1)
        defaultStatusShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusShouldBeFound(String filter) throws Exception {
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=statusId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].statusId").value(hasItem(status.getStatusId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].statusAbreviation").value(hasItem(DEFAULT_STATUS_ABREVIATION)))
            .andExpect(jsonPath("$.[*].statusColor").value(hasItem(DEFAULT_STATUS_COLOR)))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusUserCanPrint").value(hasItem(DEFAULT_STATUS_USER_CAN_PRINT.booleanValue())))
            .andExpect(jsonPath("$.[*].statusUserCanUpdate").value(hasItem(DEFAULT_STATUS_USER_CAN_UPDATE.booleanValue())))
            .andExpect(jsonPath("$.[*].statusUserCanValidate").value(hasItem(DEFAULT_STATUS_USER_CAN_VALIDATE.booleanValue())))
            .andExpect(jsonPath("$.[*].statusParams").value(hasItem(DEFAULT_STATUS_PARAMS)))
            .andExpect(jsonPath("$.[*].statusAttributs").value(hasItem(DEFAULT_STATUS_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].statusStat").value(hasItem(DEFAULT_STATUS_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=statusId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusShouldNotBeFound(String filter) throws Exception {
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=statusId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=statusId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findById(status.getStatusId()).get();
        // Disconnect from session so that the updates on updatedStatus are not directly saved in db
        em.detach(updatedStatus);
        updatedStatus
            .statusName(UPDATED_STATUS_NAME)
            .statusAbreviation(UPDATED_STATUS_ABREVIATION)
            .statusColor(UPDATED_STATUS_COLOR)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusUserCanPrint(UPDATED_STATUS_USER_CAN_PRINT)
            .statusUserCanUpdate(UPDATED_STATUS_USER_CAN_UPDATE)
            .statusUserCanValidate(UPDATED_STATUS_USER_CAN_VALIDATE)
            .statusParams(UPDATED_STATUS_PARAMS)
            .statusAttributs(UPDATED_STATUS_ATTRIBUTS)
            .statusStat(UPDATED_STATUS_STAT);
        StatusDTO statusDTO = statusMapper.toDto(updatedStatus);

        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusDTO.getStatusId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testStatus.getStatusAbreviation()).isEqualTo(UPDATED_STATUS_ABREVIATION);
        assertThat(testStatus.getStatusColor()).isEqualTo(UPDATED_STATUS_COLOR);
        assertThat(testStatus.getStatusDescription()).isEqualTo(UPDATED_STATUS_DESCRIPTION);
        assertThat(testStatus.getStatusUserCanPrint()).isEqualTo(UPDATED_STATUS_USER_CAN_PRINT);
        assertThat(testStatus.getStatusUserCanUpdate()).isEqualTo(UPDATED_STATUS_USER_CAN_UPDATE);
        assertThat(testStatus.getStatusUserCanValidate()).isEqualTo(UPDATED_STATUS_USER_CAN_VALIDATE);
        assertThat(testStatus.getStatusParams()).isEqualTo(UPDATED_STATUS_PARAMS);
        assertThat(testStatus.getStatusAttributs()).isEqualTo(UPDATED_STATUS_ATTRIBUTS);
        assertThat(testStatus.getStatusStat()).isEqualTo(UPDATED_STATUS_STAT);
    }

    @Test
    @Transactional
    void putNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setStatusId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusDTO.getStatusId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setStatusId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setStatusId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusWithPatch() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status using partial update
        Status partialUpdatedStatus = new Status();
        partialUpdatedStatus.setStatusId(status.getStatusId());

        partialUpdatedStatus
            .statusColor(UPDATED_STATUS_COLOR)
            .statusUserCanUpdate(UPDATED_STATUS_USER_CAN_UPDATE)
            .statusAttributs(UPDATED_STATUS_ATTRIBUTS);

        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatus.getStatusId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatus))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testStatus.getStatusAbreviation()).isEqualTo(DEFAULT_STATUS_ABREVIATION);
        assertThat(testStatus.getStatusColor()).isEqualTo(UPDATED_STATUS_COLOR);
        assertThat(testStatus.getStatusDescription()).isEqualTo(DEFAULT_STATUS_DESCRIPTION);
        assertThat(testStatus.getStatusUserCanPrint()).isEqualTo(DEFAULT_STATUS_USER_CAN_PRINT);
        assertThat(testStatus.getStatusUserCanUpdate()).isEqualTo(UPDATED_STATUS_USER_CAN_UPDATE);
        assertThat(testStatus.getStatusUserCanValidate()).isEqualTo(DEFAULT_STATUS_USER_CAN_VALIDATE);
        assertThat(testStatus.getStatusParams()).isEqualTo(DEFAULT_STATUS_PARAMS);
        assertThat(testStatus.getStatusAttributs()).isEqualTo(UPDATED_STATUS_ATTRIBUTS);
        assertThat(testStatus.getStatusStat()).isEqualTo(DEFAULT_STATUS_STAT);
    }

    @Test
    @Transactional
    void fullUpdateStatusWithPatch() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status using partial update
        Status partialUpdatedStatus = new Status();
        partialUpdatedStatus.setStatusId(status.getStatusId());

        partialUpdatedStatus
            .statusName(UPDATED_STATUS_NAME)
            .statusAbreviation(UPDATED_STATUS_ABREVIATION)
            .statusColor(UPDATED_STATUS_COLOR)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusUserCanPrint(UPDATED_STATUS_USER_CAN_PRINT)
            .statusUserCanUpdate(UPDATED_STATUS_USER_CAN_UPDATE)
            .statusUserCanValidate(UPDATED_STATUS_USER_CAN_VALIDATE)
            .statusParams(UPDATED_STATUS_PARAMS)
            .statusAttributs(UPDATED_STATUS_ATTRIBUTS)
            .statusStat(UPDATED_STATUS_STAT);

        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatus.getStatusId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatus))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testStatus.getStatusAbreviation()).isEqualTo(UPDATED_STATUS_ABREVIATION);
        assertThat(testStatus.getStatusColor()).isEqualTo(UPDATED_STATUS_COLOR);
        assertThat(testStatus.getStatusDescription()).isEqualTo(UPDATED_STATUS_DESCRIPTION);
        assertThat(testStatus.getStatusUserCanPrint()).isEqualTo(UPDATED_STATUS_USER_CAN_PRINT);
        assertThat(testStatus.getStatusUserCanUpdate()).isEqualTo(UPDATED_STATUS_USER_CAN_UPDATE);
        assertThat(testStatus.getStatusUserCanValidate()).isEqualTo(UPDATED_STATUS_USER_CAN_VALIDATE);
        assertThat(testStatus.getStatusParams()).isEqualTo(UPDATED_STATUS_PARAMS);
        assertThat(testStatus.getStatusAttributs()).isEqualTo(UPDATED_STATUS_ATTRIBUTS);
        assertThat(testStatus.getStatusStat()).isEqualTo(UPDATED_STATUS_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setStatusId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusDTO.getStatusId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setStatusId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setStatusId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Delete the status
        restStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, status.getStatusId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
