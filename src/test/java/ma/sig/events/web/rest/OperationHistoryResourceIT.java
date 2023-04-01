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
import ma.sig.events.domain.Event;
import ma.sig.events.domain.OperationHistory;
import ma.sig.events.domain.OperationType;
import ma.sig.events.repository.OperationHistoryRepository;
import ma.sig.events.service.criteria.OperationHistoryCriteria;
import ma.sig.events.service.dto.OperationHistoryDTO;
import ma.sig.events.service.mapper.OperationHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OperationHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperationHistoryResourceIT {

    private static final String DEFAULT_OPERATION_HISTORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HISTORY_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_OPERATION_HISTORY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OPERATION_HISTORY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_OPERATION_HISTORY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_OPERATION_HISTORY_USER_ID = 1L;
    private static final Long UPDATED_OPERATION_HISTORY_USER_ID = 2L;
    private static final Long SMALLER_OPERATION_HISTORY_USER_ID = 1L - 1L;

    private static final String DEFAULT_OPERATION_HISTORY_OLD_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HISTORY_OLD_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_HISTORY_NEW_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HISTORY_NEW_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_OPERATION_HISTORY_OLD_ID = 1L;
    private static final Long UPDATED_OPERATION_HISTORY_OLD_ID = 2L;
    private static final Long SMALLER_OPERATION_HISTORY_OLD_ID = 1L - 1L;

    private static final Long DEFAULT_OPERATION_HISTORY_NEW_ID = 1L;
    private static final Long UPDATED_OPERATION_HISTORY_NEW_ID = 2L;
    private static final Long SMALLER_OPERATION_HISTORY_NEW_ID = 1L - 1L;

    private static final String DEFAULT_OPERATION_HISTORY_IMPORTED_FILE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HISTORY_IMPORTED_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_HISTORY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HISTORY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_HISTORY_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HISTORY_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OPERATION_HISTORY_STAT = false;
    private static final Boolean UPDATED_OPERATION_HISTORY_STAT = true;

    private static final String ENTITY_API_URL = "/api/operation-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{operationHistoryId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperationHistoryRepository operationHistoryRepository;

    @Autowired
    private OperationHistoryMapper operationHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationHistoryMockMvc;

    private OperationHistory operationHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationHistory createEntity(EntityManager em) {
        OperationHistory operationHistory = new OperationHistory()
            .operationHistoryDescription(DEFAULT_OPERATION_HISTORY_DESCRIPTION)
            .operationHistoryDate(DEFAULT_OPERATION_HISTORY_DATE)
            .operationHistoryUserID(DEFAULT_OPERATION_HISTORY_USER_ID)
            .operationHistoryOldValue(DEFAULT_OPERATION_HISTORY_OLD_VALUE)
            .operationHistoryNewValue(DEFAULT_OPERATION_HISTORY_NEW_VALUE)
            .operationHistoryOldId(DEFAULT_OPERATION_HISTORY_OLD_ID)
            .operationHistoryNewId(DEFAULT_OPERATION_HISTORY_NEW_ID)
            .operationHistoryImportedFile(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE)
            .operationHistoryImportedFilePath(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH)
            .operationHistoryParams(DEFAULT_OPERATION_HISTORY_PARAMS)
            .operationHistoryAttributs(DEFAULT_OPERATION_HISTORY_ATTRIBUTS)
            .operationHistoryStat(DEFAULT_OPERATION_HISTORY_STAT);
        return operationHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationHistory createUpdatedEntity(EntityManager em) {
        OperationHistory operationHistory = new OperationHistory()
            .operationHistoryDescription(UPDATED_OPERATION_HISTORY_DESCRIPTION)
            .operationHistoryDate(UPDATED_OPERATION_HISTORY_DATE)
            .operationHistoryUserID(UPDATED_OPERATION_HISTORY_USER_ID)
            .operationHistoryOldValue(UPDATED_OPERATION_HISTORY_OLD_VALUE)
            .operationHistoryNewValue(UPDATED_OPERATION_HISTORY_NEW_VALUE)
            .operationHistoryOldId(UPDATED_OPERATION_HISTORY_OLD_ID)
            .operationHistoryNewId(UPDATED_OPERATION_HISTORY_NEW_ID)
            .operationHistoryImportedFile(UPDATED_OPERATION_HISTORY_IMPORTED_FILE)
            .operationHistoryImportedFilePath(UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH)
            .operationHistoryParams(UPDATED_OPERATION_HISTORY_PARAMS)
            .operationHistoryAttributs(UPDATED_OPERATION_HISTORY_ATTRIBUTS)
            .operationHistoryStat(UPDATED_OPERATION_HISTORY_STAT);
        return operationHistory;
    }

    @BeforeEach
    public void initTest() {
        operationHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createOperationHistory() throws Exception {
        int databaseSizeBeforeCreate = operationHistoryRepository.findAll().size();
        // Create the OperationHistory
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);
        restOperationHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        OperationHistory testOperationHistory = operationHistoryList.get(operationHistoryList.size() - 1);
        assertThat(testOperationHistory.getOperationHistoryDescription()).isEqualTo(DEFAULT_OPERATION_HISTORY_DESCRIPTION);
        assertThat(testOperationHistory.getOperationHistoryDate()).isEqualTo(DEFAULT_OPERATION_HISTORY_DATE);
        assertThat(testOperationHistory.getOperationHistoryUserID()).isEqualTo(DEFAULT_OPERATION_HISTORY_USER_ID);
        assertThat(testOperationHistory.getOperationHistoryOldValue()).isEqualTo(DEFAULT_OPERATION_HISTORY_OLD_VALUE);
        assertThat(testOperationHistory.getOperationHistoryNewValue()).isEqualTo(DEFAULT_OPERATION_HISTORY_NEW_VALUE);
        assertThat(testOperationHistory.getOperationHistoryOldId()).isEqualTo(DEFAULT_OPERATION_HISTORY_OLD_ID);
        assertThat(testOperationHistory.getOperationHistoryNewId()).isEqualTo(DEFAULT_OPERATION_HISTORY_NEW_ID);
        assertThat(testOperationHistory.getOperationHistoryImportedFile()).isEqualTo(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE);
        assertThat(testOperationHistory.getOperationHistoryImportedFilePath()).isEqualTo(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH);
        assertThat(testOperationHistory.getOperationHistoryParams()).isEqualTo(DEFAULT_OPERATION_HISTORY_PARAMS);
        assertThat(testOperationHistory.getOperationHistoryAttributs()).isEqualTo(DEFAULT_OPERATION_HISTORY_ATTRIBUTS);
        assertThat(testOperationHistory.getOperationHistoryStat()).isEqualTo(DEFAULT_OPERATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void createOperationHistoryWithExistingId() throws Exception {
        // Create the OperationHistory with an existing ID
        operationHistory.setOperationHistoryId(1L);
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);

        int databaseSizeBeforeCreate = operationHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperationHistories() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList
        restOperationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=operationHistoryId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].operationHistoryId").value(hasItem(operationHistory.getOperationHistoryId().intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryDescription").value(hasItem(DEFAULT_OPERATION_HISTORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].operationHistoryDate").value(hasItem(sameInstant(DEFAULT_OPERATION_HISTORY_DATE))))
            .andExpect(jsonPath("$.[*].operationHistoryUserID").value(hasItem(DEFAULT_OPERATION_HISTORY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryOldValue").value(hasItem(DEFAULT_OPERATION_HISTORY_OLD_VALUE)))
            .andExpect(jsonPath("$.[*].operationHistoryNewValue").value(hasItem(DEFAULT_OPERATION_HISTORY_NEW_VALUE)))
            .andExpect(jsonPath("$.[*].operationHistoryOldId").value(hasItem(DEFAULT_OPERATION_HISTORY_OLD_ID.intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryNewId").value(hasItem(DEFAULT_OPERATION_HISTORY_NEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryImportedFile").value(hasItem(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE)))
            .andExpect(jsonPath("$.[*].operationHistoryImportedFilePath").value(hasItem(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH)))
            .andExpect(jsonPath("$.[*].operationHistoryParams").value(hasItem(DEFAULT_OPERATION_HISTORY_PARAMS)))
            .andExpect(jsonPath("$.[*].operationHistoryAttributs").value(hasItem(DEFAULT_OPERATION_HISTORY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].operationHistoryStat").value(hasItem(DEFAULT_OPERATION_HISTORY_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getOperationHistory() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get the operationHistory
        restOperationHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, operationHistory.getOperationHistoryId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.operationHistoryId").value(operationHistory.getOperationHistoryId().intValue()))
            .andExpect(jsonPath("$.operationHistoryDescription").value(DEFAULT_OPERATION_HISTORY_DESCRIPTION))
            .andExpect(jsonPath("$.operationHistoryDate").value(sameInstant(DEFAULT_OPERATION_HISTORY_DATE)))
            .andExpect(jsonPath("$.operationHistoryUserID").value(DEFAULT_OPERATION_HISTORY_USER_ID.intValue()))
            .andExpect(jsonPath("$.operationHistoryOldValue").value(DEFAULT_OPERATION_HISTORY_OLD_VALUE))
            .andExpect(jsonPath("$.operationHistoryNewValue").value(DEFAULT_OPERATION_HISTORY_NEW_VALUE))
            .andExpect(jsonPath("$.operationHistoryOldId").value(DEFAULT_OPERATION_HISTORY_OLD_ID.intValue()))
            .andExpect(jsonPath("$.operationHistoryNewId").value(DEFAULT_OPERATION_HISTORY_NEW_ID.intValue()))
            .andExpect(jsonPath("$.operationHistoryImportedFile").value(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE))
            .andExpect(jsonPath("$.operationHistoryImportedFilePath").value(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH))
            .andExpect(jsonPath("$.operationHistoryParams").value(DEFAULT_OPERATION_HISTORY_PARAMS))
            .andExpect(jsonPath("$.operationHistoryAttributs").value(DEFAULT_OPERATION_HISTORY_ATTRIBUTS))
            .andExpect(jsonPath("$.operationHistoryStat").value(DEFAULT_OPERATION_HISTORY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getOperationHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        Long id = operationHistory.getOperationHistoryId();

        defaultOperationHistoryShouldBeFound("operationHistoryId.equals=" + id);
        defaultOperationHistoryShouldNotBeFound("operationHistoryId.notEquals=" + id);

        defaultOperationHistoryShouldBeFound("operationHistoryId.greaterThanOrEqual=" + id);
        defaultOperationHistoryShouldNotBeFound("operationHistoryId.greaterThan=" + id);

        defaultOperationHistoryShouldBeFound("operationHistoryId.lessThanOrEqual=" + id);
        defaultOperationHistoryShouldNotBeFound("operationHistoryId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDescription equals to DEFAULT_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldBeFound("operationHistoryDescription.equals=" + DEFAULT_OPERATION_HISTORY_DESCRIPTION);

        // Get all the operationHistoryList where operationHistoryDescription equals to UPDATED_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldNotBeFound("operationHistoryDescription.equals=" + UPDATED_OPERATION_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDescription in DEFAULT_OPERATION_HISTORY_DESCRIPTION or UPDATED_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldBeFound(
            "operationHistoryDescription.in=" + DEFAULT_OPERATION_HISTORY_DESCRIPTION + "," + UPDATED_OPERATION_HISTORY_DESCRIPTION
        );

        // Get all the operationHistoryList where operationHistoryDescription equals to UPDATED_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldNotBeFound("operationHistoryDescription.in=" + UPDATED_OPERATION_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDescription is not null
        defaultOperationHistoryShouldBeFound("operationHistoryDescription.specified=true");

        // Get all the operationHistoryList where operationHistoryDescription is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDescriptionContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDescription contains DEFAULT_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldBeFound("operationHistoryDescription.contains=" + DEFAULT_OPERATION_HISTORY_DESCRIPTION);

        // Get all the operationHistoryList where operationHistoryDescription contains UPDATED_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldNotBeFound("operationHistoryDescription.contains=" + UPDATED_OPERATION_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDescription does not contain DEFAULT_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldNotBeFound("operationHistoryDescription.doesNotContain=" + DEFAULT_OPERATION_HISTORY_DESCRIPTION);

        // Get all the operationHistoryList where operationHistoryDescription does not contain UPDATED_OPERATION_HISTORY_DESCRIPTION
        defaultOperationHistoryShouldBeFound("operationHistoryDescription.doesNotContain=" + UPDATED_OPERATION_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDate equals to DEFAULT_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldBeFound("operationHistoryDate.equals=" + DEFAULT_OPERATION_HISTORY_DATE);

        // Get all the operationHistoryList where operationHistoryDate equals to UPDATED_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldNotBeFound("operationHistoryDate.equals=" + UPDATED_OPERATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDateIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDate in DEFAULT_OPERATION_HISTORY_DATE or UPDATED_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldBeFound(
            "operationHistoryDate.in=" + DEFAULT_OPERATION_HISTORY_DATE + "," + UPDATED_OPERATION_HISTORY_DATE
        );

        // Get all the operationHistoryList where operationHistoryDate equals to UPDATED_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldNotBeFound("operationHistoryDate.in=" + UPDATED_OPERATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDate is not null
        defaultOperationHistoryShouldBeFound("operationHistoryDate.specified=true");

        // Get all the operationHistoryList where operationHistoryDate is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDate is greater than or equal to DEFAULT_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldBeFound("operationHistoryDate.greaterThanOrEqual=" + DEFAULT_OPERATION_HISTORY_DATE);

        // Get all the operationHistoryList where operationHistoryDate is greater than or equal to UPDATED_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldNotBeFound("operationHistoryDate.greaterThanOrEqual=" + UPDATED_OPERATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDate is less than or equal to DEFAULT_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldBeFound("operationHistoryDate.lessThanOrEqual=" + DEFAULT_OPERATION_HISTORY_DATE);

        // Get all the operationHistoryList where operationHistoryDate is less than or equal to SMALLER_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldNotBeFound("operationHistoryDate.lessThanOrEqual=" + SMALLER_OPERATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDate is less than DEFAULT_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldNotBeFound("operationHistoryDate.lessThan=" + DEFAULT_OPERATION_HISTORY_DATE);

        // Get all the operationHistoryList where operationHistoryDate is less than UPDATED_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldBeFound("operationHistoryDate.lessThan=" + UPDATED_OPERATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryDate is greater than DEFAULT_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldNotBeFound("operationHistoryDate.greaterThan=" + DEFAULT_OPERATION_HISTORY_DATE);

        // Get all the operationHistoryList where operationHistoryDate is greater than SMALLER_OPERATION_HISTORY_DATE
        defaultOperationHistoryShouldBeFound("operationHistoryDate.greaterThan=" + SMALLER_OPERATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryUserIDIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryUserID equals to DEFAULT_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldBeFound("operationHistoryUserID.equals=" + DEFAULT_OPERATION_HISTORY_USER_ID);

        // Get all the operationHistoryList where operationHistoryUserID equals to UPDATED_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryUserID.equals=" + UPDATED_OPERATION_HISTORY_USER_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryUserIDIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryUserID in DEFAULT_OPERATION_HISTORY_USER_ID or UPDATED_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldBeFound(
            "operationHistoryUserID.in=" + DEFAULT_OPERATION_HISTORY_USER_ID + "," + UPDATED_OPERATION_HISTORY_USER_ID
        );

        // Get all the operationHistoryList where operationHistoryUserID equals to UPDATED_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryUserID.in=" + UPDATED_OPERATION_HISTORY_USER_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryUserIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryUserID is not null
        defaultOperationHistoryShouldBeFound("operationHistoryUserID.specified=true");

        // Get all the operationHistoryList where operationHistoryUserID is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryUserID.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryUserIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryUserID is greater than or equal to DEFAULT_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldBeFound("operationHistoryUserID.greaterThanOrEqual=" + DEFAULT_OPERATION_HISTORY_USER_ID);

        // Get all the operationHistoryList where operationHistoryUserID is greater than or equal to UPDATED_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryUserID.greaterThanOrEqual=" + UPDATED_OPERATION_HISTORY_USER_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryUserIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryUserID is less than or equal to DEFAULT_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldBeFound("operationHistoryUserID.lessThanOrEqual=" + DEFAULT_OPERATION_HISTORY_USER_ID);

        // Get all the operationHistoryList where operationHistoryUserID is less than or equal to SMALLER_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryUserID.lessThanOrEqual=" + SMALLER_OPERATION_HISTORY_USER_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryUserIDIsLessThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryUserID is less than DEFAULT_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryUserID.lessThan=" + DEFAULT_OPERATION_HISTORY_USER_ID);

        // Get all the operationHistoryList where operationHistoryUserID is less than UPDATED_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldBeFound("operationHistoryUserID.lessThan=" + UPDATED_OPERATION_HISTORY_USER_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryUserIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryUserID is greater than DEFAULT_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryUserID.greaterThan=" + DEFAULT_OPERATION_HISTORY_USER_ID);

        // Get all the operationHistoryList where operationHistoryUserID is greater than SMALLER_OPERATION_HISTORY_USER_ID
        defaultOperationHistoryShouldBeFound("operationHistoryUserID.greaterThan=" + SMALLER_OPERATION_HISTORY_USER_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldValueIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldValue equals to DEFAULT_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldBeFound("operationHistoryOldValue.equals=" + DEFAULT_OPERATION_HISTORY_OLD_VALUE);

        // Get all the operationHistoryList where operationHistoryOldValue equals to UPDATED_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldValue.equals=" + UPDATED_OPERATION_HISTORY_OLD_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldValueIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldValue in DEFAULT_OPERATION_HISTORY_OLD_VALUE or UPDATED_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldBeFound(
            "operationHistoryOldValue.in=" + DEFAULT_OPERATION_HISTORY_OLD_VALUE + "," + UPDATED_OPERATION_HISTORY_OLD_VALUE
        );

        // Get all the operationHistoryList where operationHistoryOldValue equals to UPDATED_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldValue.in=" + UPDATED_OPERATION_HISTORY_OLD_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldValue is not null
        defaultOperationHistoryShouldBeFound("operationHistoryOldValue.specified=true");

        // Get all the operationHistoryList where operationHistoryOldValue is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldValue.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldValueContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldValue contains DEFAULT_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldBeFound("operationHistoryOldValue.contains=" + DEFAULT_OPERATION_HISTORY_OLD_VALUE);

        // Get all the operationHistoryList where operationHistoryOldValue contains UPDATED_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldValue.contains=" + UPDATED_OPERATION_HISTORY_OLD_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldValueNotContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldValue does not contain DEFAULT_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldValue.doesNotContain=" + DEFAULT_OPERATION_HISTORY_OLD_VALUE);

        // Get all the operationHistoryList where operationHistoryOldValue does not contain UPDATED_OPERATION_HISTORY_OLD_VALUE
        defaultOperationHistoryShouldBeFound("operationHistoryOldValue.doesNotContain=" + UPDATED_OPERATION_HISTORY_OLD_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewValueIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewValue equals to DEFAULT_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldBeFound("operationHistoryNewValue.equals=" + DEFAULT_OPERATION_HISTORY_NEW_VALUE);

        // Get all the operationHistoryList where operationHistoryNewValue equals to UPDATED_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewValue.equals=" + UPDATED_OPERATION_HISTORY_NEW_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewValueIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewValue in DEFAULT_OPERATION_HISTORY_NEW_VALUE or UPDATED_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldBeFound(
            "operationHistoryNewValue.in=" + DEFAULT_OPERATION_HISTORY_NEW_VALUE + "," + UPDATED_OPERATION_HISTORY_NEW_VALUE
        );

        // Get all the operationHistoryList where operationHistoryNewValue equals to UPDATED_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewValue.in=" + UPDATED_OPERATION_HISTORY_NEW_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewValue is not null
        defaultOperationHistoryShouldBeFound("operationHistoryNewValue.specified=true");

        // Get all the operationHistoryList where operationHistoryNewValue is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewValue.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewValueContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewValue contains DEFAULT_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldBeFound("operationHistoryNewValue.contains=" + DEFAULT_OPERATION_HISTORY_NEW_VALUE);

        // Get all the operationHistoryList where operationHistoryNewValue contains UPDATED_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewValue.contains=" + UPDATED_OPERATION_HISTORY_NEW_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewValueNotContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewValue does not contain DEFAULT_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewValue.doesNotContain=" + DEFAULT_OPERATION_HISTORY_NEW_VALUE);

        // Get all the operationHistoryList where operationHistoryNewValue does not contain UPDATED_OPERATION_HISTORY_NEW_VALUE
        defaultOperationHistoryShouldBeFound("operationHistoryNewValue.doesNotContain=" + UPDATED_OPERATION_HISTORY_NEW_VALUE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldIdIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldId equals to DEFAULT_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldBeFound("operationHistoryOldId.equals=" + DEFAULT_OPERATION_HISTORY_OLD_ID);

        // Get all the operationHistoryList where operationHistoryOldId equals to UPDATED_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldId.equals=" + UPDATED_OPERATION_HISTORY_OLD_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldIdIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldId in DEFAULT_OPERATION_HISTORY_OLD_ID or UPDATED_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldBeFound(
            "operationHistoryOldId.in=" + DEFAULT_OPERATION_HISTORY_OLD_ID + "," + UPDATED_OPERATION_HISTORY_OLD_ID
        );

        // Get all the operationHistoryList where operationHistoryOldId equals to UPDATED_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldId.in=" + UPDATED_OPERATION_HISTORY_OLD_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldId is not null
        defaultOperationHistoryShouldBeFound("operationHistoryOldId.specified=true");

        // Get all the operationHistoryList where operationHistoryOldId is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldId.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldId is greater than or equal to DEFAULT_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldBeFound("operationHistoryOldId.greaterThanOrEqual=" + DEFAULT_OPERATION_HISTORY_OLD_ID);

        // Get all the operationHistoryList where operationHistoryOldId is greater than or equal to UPDATED_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldId.greaterThanOrEqual=" + UPDATED_OPERATION_HISTORY_OLD_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldId is less than or equal to DEFAULT_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldBeFound("operationHistoryOldId.lessThanOrEqual=" + DEFAULT_OPERATION_HISTORY_OLD_ID);

        // Get all the operationHistoryList where operationHistoryOldId is less than or equal to SMALLER_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldId.lessThanOrEqual=" + SMALLER_OPERATION_HISTORY_OLD_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldIdIsLessThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldId is less than DEFAULT_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldId.lessThan=" + DEFAULT_OPERATION_HISTORY_OLD_ID);

        // Get all the operationHistoryList where operationHistoryOldId is less than UPDATED_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldBeFound("operationHistoryOldId.lessThan=" + UPDATED_OPERATION_HISTORY_OLD_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryOldIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryOldId is greater than DEFAULT_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryOldId.greaterThan=" + DEFAULT_OPERATION_HISTORY_OLD_ID);

        // Get all the operationHistoryList where operationHistoryOldId is greater than SMALLER_OPERATION_HISTORY_OLD_ID
        defaultOperationHistoryShouldBeFound("operationHistoryOldId.greaterThan=" + SMALLER_OPERATION_HISTORY_OLD_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewIdIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewId equals to DEFAULT_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldBeFound("operationHistoryNewId.equals=" + DEFAULT_OPERATION_HISTORY_NEW_ID);

        // Get all the operationHistoryList where operationHistoryNewId equals to UPDATED_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewId.equals=" + UPDATED_OPERATION_HISTORY_NEW_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewIdIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewId in DEFAULT_OPERATION_HISTORY_NEW_ID or UPDATED_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldBeFound(
            "operationHistoryNewId.in=" + DEFAULT_OPERATION_HISTORY_NEW_ID + "," + UPDATED_OPERATION_HISTORY_NEW_ID
        );

        // Get all the operationHistoryList where operationHistoryNewId equals to UPDATED_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewId.in=" + UPDATED_OPERATION_HISTORY_NEW_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewId is not null
        defaultOperationHistoryShouldBeFound("operationHistoryNewId.specified=true");

        // Get all the operationHistoryList where operationHistoryNewId is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewId.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewId is greater than or equal to DEFAULT_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldBeFound("operationHistoryNewId.greaterThanOrEqual=" + DEFAULT_OPERATION_HISTORY_NEW_ID);

        // Get all the operationHistoryList where operationHistoryNewId is greater than or equal to UPDATED_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewId.greaterThanOrEqual=" + UPDATED_OPERATION_HISTORY_NEW_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewId is less than or equal to DEFAULT_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldBeFound("operationHistoryNewId.lessThanOrEqual=" + DEFAULT_OPERATION_HISTORY_NEW_ID);

        // Get all the operationHistoryList where operationHistoryNewId is less than or equal to SMALLER_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewId.lessThanOrEqual=" + SMALLER_OPERATION_HISTORY_NEW_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewIdIsLessThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewId is less than DEFAULT_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewId.lessThan=" + DEFAULT_OPERATION_HISTORY_NEW_ID);

        // Get all the operationHistoryList where operationHistoryNewId is less than UPDATED_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldBeFound("operationHistoryNewId.lessThan=" + UPDATED_OPERATION_HISTORY_NEW_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryNewIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryNewId is greater than DEFAULT_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldNotBeFound("operationHistoryNewId.greaterThan=" + DEFAULT_OPERATION_HISTORY_NEW_ID);

        // Get all the operationHistoryList where operationHistoryNewId is greater than SMALLER_OPERATION_HISTORY_NEW_ID
        defaultOperationHistoryShouldBeFound("operationHistoryNewId.greaterThan=" + SMALLER_OPERATION_HISTORY_NEW_ID);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFileIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFile equals to DEFAULT_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldBeFound("operationHistoryImportedFile.equals=" + DEFAULT_OPERATION_HISTORY_IMPORTED_FILE);

        // Get all the operationHistoryList where operationHistoryImportedFile equals to UPDATED_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFile.equals=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFileIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFile in DEFAULT_OPERATION_HISTORY_IMPORTED_FILE or UPDATED_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldBeFound(
            "operationHistoryImportedFile.in=" + DEFAULT_OPERATION_HISTORY_IMPORTED_FILE + "," + UPDATED_OPERATION_HISTORY_IMPORTED_FILE
        );

        // Get all the operationHistoryList where operationHistoryImportedFile equals to UPDATED_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFile.in=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFile is not null
        defaultOperationHistoryShouldBeFound("operationHistoryImportedFile.specified=true");

        // Get all the operationHistoryList where operationHistoryImportedFile is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFile.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFileContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFile contains DEFAULT_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldBeFound("operationHistoryImportedFile.contains=" + DEFAULT_OPERATION_HISTORY_IMPORTED_FILE);

        // Get all the operationHistoryList where operationHistoryImportedFile contains UPDATED_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFile.contains=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFileNotContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFile does not contain DEFAULT_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFile.doesNotContain=" + DEFAULT_OPERATION_HISTORY_IMPORTED_FILE);

        // Get all the operationHistoryList where operationHistoryImportedFile does not contain UPDATED_OPERATION_HISTORY_IMPORTED_FILE
        defaultOperationHistoryShouldBeFound("operationHistoryImportedFile.doesNotContain=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFilePathIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFilePath equals to DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldBeFound("operationHistoryImportedFilePath.equals=" + DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH);

        // Get all the operationHistoryList where operationHistoryImportedFilePath equals to UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFilePath.equals=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFilePathIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFilePath in DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH or UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldBeFound(
            "operationHistoryImportedFilePath.in=" +
            DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH +
            "," +
            UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        );

        // Get all the operationHistoryList where operationHistoryImportedFilePath equals to UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFilePath.in=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFilePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFilePath is not null
        defaultOperationHistoryShouldBeFound("operationHistoryImportedFilePath.specified=true");

        // Get all the operationHistoryList where operationHistoryImportedFilePath is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryImportedFilePath.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFilePathContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFilePath contains DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldBeFound("operationHistoryImportedFilePath.contains=" + DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH);

        // Get all the operationHistoryList where operationHistoryImportedFilePath contains UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldNotBeFound(
            "operationHistoryImportedFilePath.contains=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        );
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryImportedFilePathNotContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryImportedFilePath does not contain DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldNotBeFound(
            "operationHistoryImportedFilePath.doesNotContain=" + DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH
        );

        // Get all the operationHistoryList where operationHistoryImportedFilePath does not contain UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        defaultOperationHistoryShouldBeFound(
            "operationHistoryImportedFilePath.doesNotContain=" + UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH
        );
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryParams equals to DEFAULT_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldBeFound("operationHistoryParams.equals=" + DEFAULT_OPERATION_HISTORY_PARAMS);

        // Get all the operationHistoryList where operationHistoryParams equals to UPDATED_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldNotBeFound("operationHistoryParams.equals=" + UPDATED_OPERATION_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryParamsIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryParams in DEFAULT_OPERATION_HISTORY_PARAMS or UPDATED_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldBeFound(
            "operationHistoryParams.in=" + DEFAULT_OPERATION_HISTORY_PARAMS + "," + UPDATED_OPERATION_HISTORY_PARAMS
        );

        // Get all the operationHistoryList where operationHistoryParams equals to UPDATED_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldNotBeFound("operationHistoryParams.in=" + UPDATED_OPERATION_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryParams is not null
        defaultOperationHistoryShouldBeFound("operationHistoryParams.specified=true");

        // Get all the operationHistoryList where operationHistoryParams is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryParams.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryParamsContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryParams contains DEFAULT_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldBeFound("operationHistoryParams.contains=" + DEFAULT_OPERATION_HISTORY_PARAMS);

        // Get all the operationHistoryList where operationHistoryParams contains UPDATED_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldNotBeFound("operationHistoryParams.contains=" + UPDATED_OPERATION_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryParamsNotContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryParams does not contain DEFAULT_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldNotBeFound("operationHistoryParams.doesNotContain=" + DEFAULT_OPERATION_HISTORY_PARAMS);

        // Get all the operationHistoryList where operationHistoryParams does not contain UPDATED_OPERATION_HISTORY_PARAMS
        defaultOperationHistoryShouldBeFound("operationHistoryParams.doesNotContain=" + UPDATED_OPERATION_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryAttributs equals to DEFAULT_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldBeFound("operationHistoryAttributs.equals=" + DEFAULT_OPERATION_HISTORY_ATTRIBUTS);

        // Get all the operationHistoryList where operationHistoryAttributs equals to UPDATED_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldNotBeFound("operationHistoryAttributs.equals=" + UPDATED_OPERATION_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryAttributs in DEFAULT_OPERATION_HISTORY_ATTRIBUTS or UPDATED_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldBeFound(
            "operationHistoryAttributs.in=" + DEFAULT_OPERATION_HISTORY_ATTRIBUTS + "," + UPDATED_OPERATION_HISTORY_ATTRIBUTS
        );

        // Get all the operationHistoryList where operationHistoryAttributs equals to UPDATED_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldNotBeFound("operationHistoryAttributs.in=" + UPDATED_OPERATION_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryAttributs is not null
        defaultOperationHistoryShouldBeFound("operationHistoryAttributs.specified=true");

        // Get all the operationHistoryList where operationHistoryAttributs is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryAttributsContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryAttributs contains DEFAULT_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldBeFound("operationHistoryAttributs.contains=" + DEFAULT_OPERATION_HISTORY_ATTRIBUTS);

        // Get all the operationHistoryList where operationHistoryAttributs contains UPDATED_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldNotBeFound("operationHistoryAttributs.contains=" + UPDATED_OPERATION_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryAttributs does not contain DEFAULT_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldNotBeFound("operationHistoryAttributs.doesNotContain=" + DEFAULT_OPERATION_HISTORY_ATTRIBUTS);

        // Get all the operationHistoryList where operationHistoryAttributs does not contain UPDATED_OPERATION_HISTORY_ATTRIBUTS
        defaultOperationHistoryShouldBeFound("operationHistoryAttributs.doesNotContain=" + UPDATED_OPERATION_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryStatIsEqualToSomething() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryStat equals to DEFAULT_OPERATION_HISTORY_STAT
        defaultOperationHistoryShouldBeFound("operationHistoryStat.equals=" + DEFAULT_OPERATION_HISTORY_STAT);

        // Get all the operationHistoryList where operationHistoryStat equals to UPDATED_OPERATION_HISTORY_STAT
        defaultOperationHistoryShouldNotBeFound("operationHistoryStat.equals=" + UPDATED_OPERATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryStatIsInShouldWork() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryStat in DEFAULT_OPERATION_HISTORY_STAT or UPDATED_OPERATION_HISTORY_STAT
        defaultOperationHistoryShouldBeFound(
            "operationHistoryStat.in=" + DEFAULT_OPERATION_HISTORY_STAT + "," + UPDATED_OPERATION_HISTORY_STAT
        );

        // Get all the operationHistoryList where operationHistoryStat equals to UPDATED_OPERATION_HISTORY_STAT
        defaultOperationHistoryShouldNotBeFound("operationHistoryStat.in=" + UPDATED_OPERATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByOperationHistoryStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        // Get all the operationHistoryList where operationHistoryStat is not null
        defaultOperationHistoryShouldBeFound("operationHistoryStat.specified=true");

        // Get all the operationHistoryList where operationHistoryStat is null
        defaultOperationHistoryShouldNotBeFound("operationHistoryStat.specified=false");
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByTypeoperationIsEqualToSomething() throws Exception {
        OperationType typeoperation;
        if (TestUtil.findAll(em, OperationType.class).isEmpty()) {
            operationHistoryRepository.saveAndFlush(operationHistory);
            typeoperation = OperationTypeResourceIT.createEntity(em);
        } else {
            typeoperation = TestUtil.findAll(em, OperationType.class).get(0);
        }
        em.persist(typeoperation);
        em.flush();
        operationHistory.setTypeoperation(typeoperation);
        operationHistoryRepository.saveAndFlush(operationHistory);
        Long typeoperationId = typeoperation.getOperationTypeId();

        // Get all the operationHistoryList where typeoperation equals to typeoperationId
        defaultOperationHistoryShouldBeFound("typeoperationId.equals=" + typeoperationId);

        // Get all the operationHistoryList where typeoperation equals to (typeoperationId + 1)
        defaultOperationHistoryShouldNotBeFound("typeoperationId.equals=" + (typeoperationId + 1));
    }

    @Test
    @Transactional
    void getAllOperationHistoriesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            operationHistoryRepository.saveAndFlush(operationHistory);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        operationHistory.setEvent(event);
        operationHistoryRepository.saveAndFlush(operationHistory);
        Long eventId = event.getEventId();

        // Get all the operationHistoryList where event equals to eventId
        defaultOperationHistoryShouldBeFound("eventId.equals=" + eventId);

        // Get all the operationHistoryList where event equals to (eventId + 1)
        defaultOperationHistoryShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOperationHistoryShouldBeFound(String filter) throws Exception {
        restOperationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=operationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].operationHistoryId").value(hasItem(operationHistory.getOperationHistoryId().intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryDescription").value(hasItem(DEFAULT_OPERATION_HISTORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].operationHistoryDate").value(hasItem(sameInstant(DEFAULT_OPERATION_HISTORY_DATE))))
            .andExpect(jsonPath("$.[*].operationHistoryUserID").value(hasItem(DEFAULT_OPERATION_HISTORY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryOldValue").value(hasItem(DEFAULT_OPERATION_HISTORY_OLD_VALUE)))
            .andExpect(jsonPath("$.[*].operationHistoryNewValue").value(hasItem(DEFAULT_OPERATION_HISTORY_NEW_VALUE)))
            .andExpect(jsonPath("$.[*].operationHistoryOldId").value(hasItem(DEFAULT_OPERATION_HISTORY_OLD_ID.intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryNewId").value(hasItem(DEFAULT_OPERATION_HISTORY_NEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].operationHistoryImportedFile").value(hasItem(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE)))
            .andExpect(jsonPath("$.[*].operationHistoryImportedFilePath").value(hasItem(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH)))
            .andExpect(jsonPath("$.[*].operationHistoryParams").value(hasItem(DEFAULT_OPERATION_HISTORY_PARAMS)))
            .andExpect(jsonPath("$.[*].operationHistoryAttributs").value(hasItem(DEFAULT_OPERATION_HISTORY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].operationHistoryStat").value(hasItem(DEFAULT_OPERATION_HISTORY_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restOperationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=operationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOperationHistoryShouldNotBeFound(String filter) throws Exception {
        restOperationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=operationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOperationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=operationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOperationHistory() throws Exception {
        // Get the operationHistory
        restOperationHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperationHistory() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();

        // Update the operationHistory
        OperationHistory updatedOperationHistory = operationHistoryRepository.findById(operationHistory.getOperationHistoryId()).get();
        // Disconnect from session so that the updates on updatedOperationHistory are not directly saved in db
        em.detach(updatedOperationHistory);
        updatedOperationHistory
            .operationHistoryDescription(UPDATED_OPERATION_HISTORY_DESCRIPTION)
            .operationHistoryDate(UPDATED_OPERATION_HISTORY_DATE)
            .operationHistoryUserID(UPDATED_OPERATION_HISTORY_USER_ID)
            .operationHistoryOldValue(UPDATED_OPERATION_HISTORY_OLD_VALUE)
            .operationHistoryNewValue(UPDATED_OPERATION_HISTORY_NEW_VALUE)
            .operationHistoryOldId(UPDATED_OPERATION_HISTORY_OLD_ID)
            .operationHistoryNewId(UPDATED_OPERATION_HISTORY_NEW_ID)
            .operationHistoryImportedFile(UPDATED_OPERATION_HISTORY_IMPORTED_FILE)
            .operationHistoryImportedFilePath(UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH)
            .operationHistoryParams(UPDATED_OPERATION_HISTORY_PARAMS)
            .operationHistoryAttributs(UPDATED_OPERATION_HISTORY_ATTRIBUTS)
            .operationHistoryStat(UPDATED_OPERATION_HISTORY_STAT);
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(updatedOperationHistory);

        restOperationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationHistoryDTO.getOperationHistoryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
        OperationHistory testOperationHistory = operationHistoryList.get(operationHistoryList.size() - 1);
        assertThat(testOperationHistory.getOperationHistoryDescription()).isEqualTo(UPDATED_OPERATION_HISTORY_DESCRIPTION);
        assertThat(testOperationHistory.getOperationHistoryDate()).isEqualTo(UPDATED_OPERATION_HISTORY_DATE);
        assertThat(testOperationHistory.getOperationHistoryUserID()).isEqualTo(UPDATED_OPERATION_HISTORY_USER_ID);
        assertThat(testOperationHistory.getOperationHistoryOldValue()).isEqualTo(UPDATED_OPERATION_HISTORY_OLD_VALUE);
        assertThat(testOperationHistory.getOperationHistoryNewValue()).isEqualTo(UPDATED_OPERATION_HISTORY_NEW_VALUE);
        assertThat(testOperationHistory.getOperationHistoryOldId()).isEqualTo(UPDATED_OPERATION_HISTORY_OLD_ID);
        assertThat(testOperationHistory.getOperationHistoryNewId()).isEqualTo(UPDATED_OPERATION_HISTORY_NEW_ID);
        assertThat(testOperationHistory.getOperationHistoryImportedFile()).isEqualTo(UPDATED_OPERATION_HISTORY_IMPORTED_FILE);
        assertThat(testOperationHistory.getOperationHistoryImportedFilePath()).isEqualTo(UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH);
        assertThat(testOperationHistory.getOperationHistoryParams()).isEqualTo(UPDATED_OPERATION_HISTORY_PARAMS);
        assertThat(testOperationHistory.getOperationHistoryAttributs()).isEqualTo(UPDATED_OPERATION_HISTORY_ATTRIBUTS);
        assertThat(testOperationHistory.getOperationHistoryStat()).isEqualTo(UPDATED_OPERATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingOperationHistory() throws Exception {
        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();
        operationHistory.setOperationHistoryId(count.incrementAndGet());

        // Create the OperationHistory
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationHistoryDTO.getOperationHistoryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperationHistory() throws Exception {
        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();
        operationHistory.setOperationHistoryId(count.incrementAndGet());

        // Create the OperationHistory
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperationHistory() throws Exception {
        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();
        operationHistory.setOperationHistoryId(count.incrementAndGet());

        // Create the OperationHistory
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperationHistoryWithPatch() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();

        // Update the operationHistory using partial update
        OperationHistory partialUpdatedOperationHistory = new OperationHistory();
        partialUpdatedOperationHistory.setOperationHistoryId(operationHistory.getOperationHistoryId());

        partialUpdatedOperationHistory
            .operationHistoryDescription(UPDATED_OPERATION_HISTORY_DESCRIPTION)
            .operationHistoryOldValue(UPDATED_OPERATION_HISTORY_OLD_VALUE)
            .operationHistoryOldId(UPDATED_OPERATION_HISTORY_OLD_ID)
            .operationHistoryNewId(UPDATED_OPERATION_HISTORY_NEW_ID)
            .operationHistoryImportedFile(UPDATED_OPERATION_HISTORY_IMPORTED_FILE)
            .operationHistoryParams(UPDATED_OPERATION_HISTORY_PARAMS)
            .operationHistoryAttributs(UPDATED_OPERATION_HISTORY_ATTRIBUTS);

        restOperationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationHistory.getOperationHistoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationHistory))
            )
            .andExpect(status().isOk());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
        OperationHistory testOperationHistory = operationHistoryList.get(operationHistoryList.size() - 1);
        assertThat(testOperationHistory.getOperationHistoryDescription()).isEqualTo(UPDATED_OPERATION_HISTORY_DESCRIPTION);
        assertThat(testOperationHistory.getOperationHistoryDate()).isEqualTo(DEFAULT_OPERATION_HISTORY_DATE);
        assertThat(testOperationHistory.getOperationHistoryUserID()).isEqualTo(DEFAULT_OPERATION_HISTORY_USER_ID);
        assertThat(testOperationHistory.getOperationHistoryOldValue()).isEqualTo(UPDATED_OPERATION_HISTORY_OLD_VALUE);
        assertThat(testOperationHistory.getOperationHistoryNewValue()).isEqualTo(DEFAULT_OPERATION_HISTORY_NEW_VALUE);
        assertThat(testOperationHistory.getOperationHistoryOldId()).isEqualTo(UPDATED_OPERATION_HISTORY_OLD_ID);
        assertThat(testOperationHistory.getOperationHistoryNewId()).isEqualTo(UPDATED_OPERATION_HISTORY_NEW_ID);
        assertThat(testOperationHistory.getOperationHistoryImportedFile()).isEqualTo(UPDATED_OPERATION_HISTORY_IMPORTED_FILE);
        assertThat(testOperationHistory.getOperationHistoryImportedFilePath()).isEqualTo(DEFAULT_OPERATION_HISTORY_IMPORTED_FILE_PATH);
        assertThat(testOperationHistory.getOperationHistoryParams()).isEqualTo(UPDATED_OPERATION_HISTORY_PARAMS);
        assertThat(testOperationHistory.getOperationHistoryAttributs()).isEqualTo(UPDATED_OPERATION_HISTORY_ATTRIBUTS);
        assertThat(testOperationHistory.getOperationHistoryStat()).isEqualTo(DEFAULT_OPERATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateOperationHistoryWithPatch() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();

        // Update the operationHistory using partial update
        OperationHistory partialUpdatedOperationHistory = new OperationHistory();
        partialUpdatedOperationHistory.setOperationHistoryId(operationHistory.getOperationHistoryId());

        partialUpdatedOperationHistory
            .operationHistoryDescription(UPDATED_OPERATION_HISTORY_DESCRIPTION)
            .operationHistoryDate(UPDATED_OPERATION_HISTORY_DATE)
            .operationHistoryUserID(UPDATED_OPERATION_HISTORY_USER_ID)
            .operationHistoryOldValue(UPDATED_OPERATION_HISTORY_OLD_VALUE)
            .operationHistoryNewValue(UPDATED_OPERATION_HISTORY_NEW_VALUE)
            .operationHistoryOldId(UPDATED_OPERATION_HISTORY_OLD_ID)
            .operationHistoryNewId(UPDATED_OPERATION_HISTORY_NEW_ID)
            .operationHistoryImportedFile(UPDATED_OPERATION_HISTORY_IMPORTED_FILE)
            .operationHistoryImportedFilePath(UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH)
            .operationHistoryParams(UPDATED_OPERATION_HISTORY_PARAMS)
            .operationHistoryAttributs(UPDATED_OPERATION_HISTORY_ATTRIBUTS)
            .operationHistoryStat(UPDATED_OPERATION_HISTORY_STAT);

        restOperationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationHistory.getOperationHistoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationHistory))
            )
            .andExpect(status().isOk());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
        OperationHistory testOperationHistory = operationHistoryList.get(operationHistoryList.size() - 1);
        assertThat(testOperationHistory.getOperationHistoryDescription()).isEqualTo(UPDATED_OPERATION_HISTORY_DESCRIPTION);
        assertThat(testOperationHistory.getOperationHistoryDate()).isEqualTo(UPDATED_OPERATION_HISTORY_DATE);
        assertThat(testOperationHistory.getOperationHistoryUserID()).isEqualTo(UPDATED_OPERATION_HISTORY_USER_ID);
        assertThat(testOperationHistory.getOperationHistoryOldValue()).isEqualTo(UPDATED_OPERATION_HISTORY_OLD_VALUE);
        assertThat(testOperationHistory.getOperationHistoryNewValue()).isEqualTo(UPDATED_OPERATION_HISTORY_NEW_VALUE);
        assertThat(testOperationHistory.getOperationHistoryOldId()).isEqualTo(UPDATED_OPERATION_HISTORY_OLD_ID);
        assertThat(testOperationHistory.getOperationHistoryNewId()).isEqualTo(UPDATED_OPERATION_HISTORY_NEW_ID);
        assertThat(testOperationHistory.getOperationHistoryImportedFile()).isEqualTo(UPDATED_OPERATION_HISTORY_IMPORTED_FILE);
        assertThat(testOperationHistory.getOperationHistoryImportedFilePath()).isEqualTo(UPDATED_OPERATION_HISTORY_IMPORTED_FILE_PATH);
        assertThat(testOperationHistory.getOperationHistoryParams()).isEqualTo(UPDATED_OPERATION_HISTORY_PARAMS);
        assertThat(testOperationHistory.getOperationHistoryAttributs()).isEqualTo(UPDATED_OPERATION_HISTORY_ATTRIBUTS);
        assertThat(testOperationHistory.getOperationHistoryStat()).isEqualTo(UPDATED_OPERATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingOperationHistory() throws Exception {
        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();
        operationHistory.setOperationHistoryId(count.incrementAndGet());

        // Create the OperationHistory
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operationHistoryDTO.getOperationHistoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperationHistory() throws Exception {
        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();
        operationHistory.setOperationHistoryId(count.incrementAndGet());

        // Create the OperationHistory
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperationHistory() throws Exception {
        int databaseSizeBeforeUpdate = operationHistoryRepository.findAll().size();
        operationHistory.setOperationHistoryId(count.incrementAndGet());

        // Create the OperationHistory
        OperationHistoryDTO operationHistoryDTO = operationHistoryMapper.toDto(operationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationHistory in the database
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperationHistory() throws Exception {
        // Initialize the database
        operationHistoryRepository.saveAndFlush(operationHistory);

        int databaseSizeBeforeDelete = operationHistoryRepository.findAll().size();

        // Delete the operationHistory
        restOperationHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, operationHistory.getOperationHistoryId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationHistory> operationHistoryList = operationHistoryRepository.findAll();
        assertThat(operationHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
