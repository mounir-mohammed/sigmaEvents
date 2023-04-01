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
import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.domain.CheckAccreditationReport;
import ma.sig.events.domain.Event;
import ma.sig.events.repository.CheckAccreditationHistoryRepository;
import ma.sig.events.service.criteria.CheckAccreditationHistoryCriteria;
import ma.sig.events.service.dto.CheckAccreditationHistoryDTO;
import ma.sig.events.service.mapper.CheckAccreditationHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CheckAccreditationHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CheckAccreditationHistoryResourceIT {

    private static final String DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID = 1L;
    private static final Long UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID = 2L;
    private static final Long SMALLER_CHECK_ACCREDITATION_HISTORY_USER_ID = 1L - 1L;

    private static final Boolean DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT = false;
    private static final Boolean UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT = true;

    private static final String DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_CHECK_ACCREDITATION_HISTORY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CHECK_ACCREDITATION_HISTORY_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final String DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_ACCREDITATION_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT = false;
    private static final Boolean UPDATED_CHECK_ACCREDITATION_HISTORY_STAT = true;

    private static final String ENTITY_API_URL = "/api/check-accreditation-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{checkAccreditationHistoryId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckAccreditationHistoryRepository checkAccreditationHistoryRepository;

    @Autowired
    private CheckAccreditationHistoryMapper checkAccreditationHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckAccreditationHistoryMockMvc;

    private CheckAccreditationHistory checkAccreditationHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckAccreditationHistory createEntity(EntityManager em) {
        CheckAccreditationHistory checkAccreditationHistory = new CheckAccreditationHistory()
            .checkAccreditationHistoryReadedCode(DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE)
            .checkAccreditationHistoryUserId(DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID)
            .checkAccreditationHistoryResult(DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT)
            .checkAccreditationHistoryError(DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR)
            .checkAccreditationHistoryDate(DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE)
            .checkAccreditationHistoryLocalisation(DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION)
            .checkAccreditationHistoryIpAdresse(DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE)
            .checkAccreditationParams(DEFAULT_CHECK_ACCREDITATION_PARAMS)
            .checkAccreditationAttributs(DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS)
            .checkAccreditationHistoryStat(DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT);
        return checkAccreditationHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckAccreditationHistory createUpdatedEntity(EntityManager em) {
        CheckAccreditationHistory checkAccreditationHistory = new CheckAccreditationHistory()
            .checkAccreditationHistoryReadedCode(UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE)
            .checkAccreditationHistoryUserId(UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID)
            .checkAccreditationHistoryResult(UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT)
            .checkAccreditationHistoryError(UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR)
            .checkAccreditationHistoryDate(UPDATED_CHECK_ACCREDITATION_HISTORY_DATE)
            .checkAccreditationHistoryLocalisation(UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION)
            .checkAccreditationHistoryIpAdresse(UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE)
            .checkAccreditationParams(UPDATED_CHECK_ACCREDITATION_PARAMS)
            .checkAccreditationAttributs(UPDATED_CHECK_ACCREDITATION_ATTRIBUTS)
            .checkAccreditationHistoryStat(UPDATED_CHECK_ACCREDITATION_HISTORY_STAT);
        return checkAccreditationHistory;
    }

    @BeforeEach
    public void initTest() {
        checkAccreditationHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckAccreditationHistory() throws Exception {
        int databaseSizeBeforeCreate = checkAccreditationHistoryRepository.findAll().size();
        // Create the CheckAccreditationHistory
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);
        restCheckAccreditationHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        CheckAccreditationHistory testCheckAccreditationHistory = checkAccreditationHistoryList.get(
            checkAccreditationHistoryList.size() - 1
        );
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryReadedCode())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryUserId())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryResult())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryError()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryDate()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryLocalisation())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryIpAdresse())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationParams()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_PARAMS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationAttributs()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryStat()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void createCheckAccreditationHistoryWithExistingId() throws Exception {
        // Create the CheckAccreditationHistory with an existing ID
        checkAccreditationHistory.setCheckAccreditationHistoryId(1L);
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);

        int databaseSizeBeforeCreate = checkAccreditationHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckAccreditationHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistories() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList
        restCheckAccreditationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=checkAccreditationHistoryId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryId")
                    .value(hasItem(checkAccreditationHistory.getCheckAccreditationHistoryId().intValue()))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryReadedCode").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryUserId").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID.intValue()))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryResult").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationHistoryError").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryDate").value(hasItem(sameInstant(DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE)))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryLocalisation").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationHistoryIpAdresse").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE)))
            .andExpect(jsonPath("$.[*].checkAccreditationParams").value(hasItem(DEFAULT_CHECK_ACCREDITATION_PARAMS)))
            .andExpect(jsonPath("$.[*].checkAccreditationAttributs").value(hasItem(DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryStat").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT.booleanValue()))
            );
    }

    @Test
    @Transactional
    void getCheckAccreditationHistory() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get the checkAccreditationHistory
        restCheckAccreditationHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, checkAccreditationHistory.getCheckAccreditationHistoryId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.checkAccreditationHistoryId").value(checkAccreditationHistory.getCheckAccreditationHistoryId().intValue())
            )
            .andExpect(jsonPath("$.checkAccreditationHistoryReadedCode").value(DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE))
            .andExpect(jsonPath("$.checkAccreditationHistoryUserId").value(DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID.intValue()))
            .andExpect(jsonPath("$.checkAccreditationHistoryResult").value(DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT.booleanValue()))
            .andExpect(jsonPath("$.checkAccreditationHistoryError").value(DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR))
            .andExpect(jsonPath("$.checkAccreditationHistoryDate").value(sameInstant(DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE)))
            .andExpect(jsonPath("$.checkAccreditationHistoryLocalisation").value(DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION))
            .andExpect(jsonPath("$.checkAccreditationHistoryIpAdresse").value(DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE))
            .andExpect(jsonPath("$.checkAccreditationParams").value(DEFAULT_CHECK_ACCREDITATION_PARAMS))
            .andExpect(jsonPath("$.checkAccreditationAttributs").value(DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS))
            .andExpect(jsonPath("$.checkAccreditationHistoryStat").value(DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCheckAccreditationHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        Long id = checkAccreditationHistory.getCheckAccreditationHistoryId();

        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryId.equals=" + id);
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryId.notEquals=" + id);

        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryId.greaterThanOrEqual=" + id);
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryId.greaterThan=" + id);

        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryId.lessThanOrEqual=" + id);
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryReadedCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryReadedCode.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode equals to UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryReadedCode.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryReadedCodeIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode in DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE or UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryReadedCode.in=" +
            DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE +
            "," +
            UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode equals to UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryReadedCode.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryReadedCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryReadedCode.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryReadedCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryReadedCodeContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode contains DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryReadedCode.contains=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode contains UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryReadedCode.contains=" + UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryReadedCodeNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode does not contain DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryReadedCode.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryReadedCode does not contain UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryReadedCode.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryUserId.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId equals to UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryUserId.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId in DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID or UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryUserId.in=" +
            DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID +
            "," +
            UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId equals to UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryUserId.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryUserId.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryUserId.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is greater than or equal to DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryUserId.greaterThanOrEqual=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is greater than or equal to UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryUserId.greaterThanOrEqual=" + UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is less than or equal to DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryUserId.lessThanOrEqual=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is less than or equal to SMALLER_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryUserId.lessThanOrEqual=" + SMALLER_CHECK_ACCREDITATION_HISTORY_USER_ID
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is less than DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryUserId.lessThan=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is less than UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryUserId.lessThan=" + UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is greater than DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryUserId.greaterThan=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryUserId is greater than SMALLER_CHECK_ACCREDITATION_HISTORY_USER_ID
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryUserId.greaterThan=" + SMALLER_CHECK_ACCREDITATION_HISTORY_USER_ID
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryResultIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryResult equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryResult.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryResult equals to UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryResult.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryResultIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryResult in DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT or UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryResult.in=" +
            DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT +
            "," +
            UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryResult equals to UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryResult.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryResult is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryResult.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryResult is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryResult.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryErrorIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryError.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError equals to UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryError.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryErrorIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError in DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR or UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryError.in=" +
            DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR +
            "," +
            UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError equals to UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryError.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryErrorIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryError.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryError.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryErrorContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError contains DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryError.contains=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError contains UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryError.contains=" + UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryErrorNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError does not contain DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryError.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryError does not contain UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryError.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryDate.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate equals to UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryDate.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryDateIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate in DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE or UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryDate.in=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE + "," + UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate equals to UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryDate.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryDate.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is greater than or equal to DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryDate.greaterThanOrEqual=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is greater than or equal to UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryDate.greaterThanOrEqual=" + UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is less than or equal to DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryDate.lessThanOrEqual=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is less than or equal to SMALLER_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryDate.lessThanOrEqual=" + SMALLER_CHECK_ACCREDITATION_HISTORY_DATE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is less than DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryDate.lessThan=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is less than UPDATED_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryDate.lessThan=" + UPDATED_CHECK_ACCREDITATION_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is greater than DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryDate.greaterThan=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryDate is greater than SMALLER_CHECK_ACCREDITATION_HISTORY_DATE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryDate.greaterThan=" + SMALLER_CHECK_ACCREDITATION_HISTORY_DATE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryLocalisationIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryLocalisation.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation equals to UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryLocalisation.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryLocalisationIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation in DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION or UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryLocalisation.in=" +
            DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION +
            "," +
            UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation equals to UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryLocalisation.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryLocalisationIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryLocalisation.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryLocalisation.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryLocalisationContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation contains DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryLocalisation.contains=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation contains UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryLocalisation.contains=" + UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryLocalisationNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation does not contain DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryLocalisation.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryLocalisation does not contain UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryLocalisation.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryIpAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryIpAdresse.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse equals to UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryIpAdresse.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryIpAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse in DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE or UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryIpAdresse.in=" +
            DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE +
            "," +
            UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse equals to UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryIpAdresse.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryIpAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryIpAdresse.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryIpAdresse.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryIpAdresseContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse contains DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryIpAdresse.contains=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse contains UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryIpAdresse.contains=" + UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryIpAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse does not contain DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryIpAdresse.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryIpAdresse does not contain UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryIpAdresse.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams equals to DEFAULT_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationParams.equals=" + DEFAULT_CHECK_ACCREDITATION_PARAMS);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams equals to UPDATED_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationParams.equals=" + UPDATED_CHECK_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationParamsIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams in DEFAULT_CHECK_ACCREDITATION_PARAMS or UPDATED_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationParams.in=" + DEFAULT_CHECK_ACCREDITATION_PARAMS + "," + UPDATED_CHECK_ACCREDITATION_PARAMS
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationParams equals to UPDATED_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationParams.in=" + UPDATED_CHECK_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationParams.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationParams is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationParamsContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams contains DEFAULT_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationParams.contains=" + DEFAULT_CHECK_ACCREDITATION_PARAMS);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams contains UPDATED_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationParams.contains=" + UPDATED_CHECK_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationParamsNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams does not contain DEFAULT_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationParams.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_PARAMS);

        // Get all the checkAccreditationHistoryList where checkAccreditationParams does not contain UPDATED_CHECK_ACCREDITATION_PARAMS
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationParams.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs equals to DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationAttributs.equals=" + DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS);

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs equals to UPDATED_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationAttributs.equals=" + UPDATED_CHECK_ACCREDITATION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs in DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS or UPDATED_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationAttributs.in=" + DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS + "," + UPDATED_CHECK_ACCREDITATION_ATTRIBUTS
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs equals to UPDATED_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationAttributs.in=" + UPDATED_CHECK_ACCREDITATION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationAttributs.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationAttributsContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs contains DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationAttributs.contains=" + DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS);

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs contains UPDATED_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationAttributs.contains=" + UPDATED_CHECK_ACCREDITATION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs does not contain DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationAttributs.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationAttributs does not contain UPDATED_CHECK_ACCREDITATION_ATTRIBUTS
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationAttributs.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_ATTRIBUTS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryStatIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryStat equals to DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryStat.equals=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryStat equals to UPDATED_CHECK_ACCREDITATION_HISTORY_STAT
        defaultCheckAccreditationHistoryShouldNotBeFound(
            "checkAccreditationHistoryStat.equals=" + UPDATED_CHECK_ACCREDITATION_HISTORY_STAT
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryStatIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryStat in DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT or UPDATED_CHECK_ACCREDITATION_HISTORY_STAT
        defaultCheckAccreditationHistoryShouldBeFound(
            "checkAccreditationHistoryStat.in=" + DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT + "," + UPDATED_CHECK_ACCREDITATION_HISTORY_STAT
        );

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryStat equals to UPDATED_CHECK_ACCREDITATION_HISTORY_STAT
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryStat.in=" + UPDATED_CHECK_ACCREDITATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationHistoryStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryStat is not null
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationHistoryStat.specified=true");

        // Get all the checkAccreditationHistoryList where checkAccreditationHistoryStat is null
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationHistoryStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByCheckAccreditationReportIsEqualToSomething() throws Exception {
        CheckAccreditationReport checkAccreditationReport;
        if (TestUtil.findAll(em, CheckAccreditationReport.class).isEmpty()) {
            checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);
            checkAccreditationReport = CheckAccreditationReportResourceIT.createEntity(em);
        } else {
            checkAccreditationReport = TestUtil.findAll(em, CheckAccreditationReport.class).get(0);
        }
        em.persist(checkAccreditationReport);
        em.flush();
        checkAccreditationHistory.addCheckAccreditationReport(checkAccreditationReport);
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);
        Long checkAccreditationReportId = checkAccreditationReport.getCheckAccreditationReportId();

        // Get all the checkAccreditationHistoryList where checkAccreditationReport equals to checkAccreditationReportId
        defaultCheckAccreditationHistoryShouldBeFound("checkAccreditationReportId.equals=" + checkAccreditationReportId);

        // Get all the checkAccreditationHistoryList where checkAccreditationReport equals to (checkAccreditationReportId + 1)
        defaultCheckAccreditationHistoryShouldNotBeFound("checkAccreditationReportId.equals=" + (checkAccreditationReportId + 1));
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        checkAccreditationHistory.setEvent(event);
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);
        Long eventId = event.getEventId();

        // Get all the checkAccreditationHistoryList where event equals to eventId
        defaultCheckAccreditationHistoryShouldBeFound("eventId.equals=" + eventId);

        // Get all the checkAccreditationHistoryList where event equals to (eventId + 1)
        defaultCheckAccreditationHistoryShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllCheckAccreditationHistoriesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        checkAccreditationHistory.setAccreditation(accreditation);
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the checkAccreditationHistoryList where accreditation equals to accreditationId
        defaultCheckAccreditationHistoryShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the checkAccreditationHistoryList where accreditation equals to (accreditationId + 1)
        defaultCheckAccreditationHistoryShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckAccreditationHistoryShouldBeFound(String filter) throws Exception {
        restCheckAccreditationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=checkAccreditationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryId")
                    .value(hasItem(checkAccreditationHistory.getCheckAccreditationHistoryId().intValue()))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryReadedCode").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_READED_CODE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryUserId").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_USER_ID.intValue()))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryResult").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationHistoryError").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_ERROR)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryDate").value(hasItem(sameInstant(DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE)))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryLocalisation").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_LOCALISATION))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationHistoryIpAdresse").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE)))
            .andExpect(jsonPath("$.[*].checkAccreditationParams").value(hasItem(DEFAULT_CHECK_ACCREDITATION_PARAMS)))
            .andExpect(jsonPath("$.[*].checkAccreditationAttributs").value(hasItem(DEFAULT_CHECK_ACCREDITATION_ATTRIBUTS)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationHistoryStat").value(hasItem(DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT.booleanValue()))
            );

        // Check, that the count call also returns 1
        restCheckAccreditationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=checkAccreditationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckAccreditationHistoryShouldNotBeFound(String filter) throws Exception {
        restCheckAccreditationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=checkAccreditationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckAccreditationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=checkAccreditationHistoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckAccreditationHistory() throws Exception {
        // Get the checkAccreditationHistory
        restCheckAccreditationHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCheckAccreditationHistory() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();

        // Update the checkAccreditationHistory
        CheckAccreditationHistory updatedCheckAccreditationHistory = checkAccreditationHistoryRepository
            .findById(checkAccreditationHistory.getCheckAccreditationHistoryId())
            .get();
        // Disconnect from session so that the updates on updatedCheckAccreditationHistory are not directly saved in db
        em.detach(updatedCheckAccreditationHistory);
        updatedCheckAccreditationHistory
            .checkAccreditationHistoryReadedCode(UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE)
            .checkAccreditationHistoryUserId(UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID)
            .checkAccreditationHistoryResult(UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT)
            .checkAccreditationHistoryError(UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR)
            .checkAccreditationHistoryDate(UPDATED_CHECK_ACCREDITATION_HISTORY_DATE)
            .checkAccreditationHistoryLocalisation(UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION)
            .checkAccreditationHistoryIpAdresse(UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE)
            .checkAccreditationParams(UPDATED_CHECK_ACCREDITATION_PARAMS)
            .checkAccreditationAttributs(UPDATED_CHECK_ACCREDITATION_ATTRIBUTS)
            .checkAccreditationHistoryStat(UPDATED_CHECK_ACCREDITATION_HISTORY_STAT);
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(updatedCheckAccreditationHistory);

        restCheckAccreditationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkAccreditationHistoryDTO.getCheckAccreditationHistoryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
        CheckAccreditationHistory testCheckAccreditationHistory = checkAccreditationHistoryList.get(
            checkAccreditationHistoryList.size() - 1
        );
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryReadedCode())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryUserId())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryResult())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryError()).isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryDate()).isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_DATE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryLocalisation())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryIpAdresse())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationParams()).isEqualTo(UPDATED_CHECK_ACCREDITATION_PARAMS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationAttributs()).isEqualTo(UPDATED_CHECK_ACCREDITATION_ATTRIBUTS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryStat()).isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCheckAccreditationHistory() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();
        checkAccreditationHistory.setCheckAccreditationHistoryId(count.incrementAndGet());

        // Create the CheckAccreditationHistory
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckAccreditationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkAccreditationHistoryDTO.getCheckAccreditationHistoryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckAccreditationHistory() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();
        checkAccreditationHistory.setCheckAccreditationHistoryId(count.incrementAndGet());

        // Create the CheckAccreditationHistory
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckAccreditationHistory() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();
        checkAccreditationHistory.setCheckAccreditationHistoryId(count.incrementAndGet());

        // Create the CheckAccreditationHistory
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckAccreditationHistoryWithPatch() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();

        // Update the checkAccreditationHistory using partial update
        CheckAccreditationHistory partialUpdatedCheckAccreditationHistory = new CheckAccreditationHistory();
        partialUpdatedCheckAccreditationHistory.setCheckAccreditationHistoryId(checkAccreditationHistory.getCheckAccreditationHistoryId());

        partialUpdatedCheckAccreditationHistory
            .checkAccreditationHistoryReadedCode(UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE)
            .checkAccreditationHistoryUserId(UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID)
            .checkAccreditationHistoryError(UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR)
            .checkAccreditationHistoryLocalisation(UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION)
            .checkAccreditationHistoryIpAdresse(UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE)
            .checkAccreditationParams(UPDATED_CHECK_ACCREDITATION_PARAMS)
            .checkAccreditationAttributs(UPDATED_CHECK_ACCREDITATION_ATTRIBUTS);

        restCheckAccreditationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckAccreditationHistory.getCheckAccreditationHistoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckAccreditationHistory))
            )
            .andExpect(status().isOk());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
        CheckAccreditationHistory testCheckAccreditationHistory = checkAccreditationHistoryList.get(
            checkAccreditationHistoryList.size() - 1
        );
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryReadedCode())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryUserId())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryResult())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_RESULT);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryError()).isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryDate()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_DATE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryLocalisation())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryIpAdresse())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationParams()).isEqualTo(UPDATED_CHECK_ACCREDITATION_PARAMS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationAttributs()).isEqualTo(UPDATED_CHECK_ACCREDITATION_ATTRIBUTS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryStat()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCheckAccreditationHistoryWithPatch() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();

        // Update the checkAccreditationHistory using partial update
        CheckAccreditationHistory partialUpdatedCheckAccreditationHistory = new CheckAccreditationHistory();
        partialUpdatedCheckAccreditationHistory.setCheckAccreditationHistoryId(checkAccreditationHistory.getCheckAccreditationHistoryId());

        partialUpdatedCheckAccreditationHistory
            .checkAccreditationHistoryReadedCode(UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE)
            .checkAccreditationHistoryUserId(UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID)
            .checkAccreditationHistoryResult(UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT)
            .checkAccreditationHistoryError(UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR)
            .checkAccreditationHistoryDate(UPDATED_CHECK_ACCREDITATION_HISTORY_DATE)
            .checkAccreditationHistoryLocalisation(UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION)
            .checkAccreditationHistoryIpAdresse(UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE)
            .checkAccreditationParams(UPDATED_CHECK_ACCREDITATION_PARAMS)
            .checkAccreditationAttributs(UPDATED_CHECK_ACCREDITATION_ATTRIBUTS)
            .checkAccreditationHistoryStat(UPDATED_CHECK_ACCREDITATION_HISTORY_STAT);

        restCheckAccreditationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckAccreditationHistory.getCheckAccreditationHistoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckAccreditationHistory))
            )
            .andExpect(status().isOk());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
        CheckAccreditationHistory testCheckAccreditationHistory = checkAccreditationHistoryList.get(
            checkAccreditationHistoryList.size() - 1
        );
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryReadedCode())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_READED_CODE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryUserId())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_USER_ID);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryResult())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_RESULT);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryError()).isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_ERROR);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryDate()).isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_DATE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryLocalisation())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_LOCALISATION);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryIpAdresse())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_IP_ADRESSE);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationParams()).isEqualTo(UPDATED_CHECK_ACCREDITATION_PARAMS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationAttributs()).isEqualTo(UPDATED_CHECK_ACCREDITATION_ATTRIBUTS);
        assertThat(testCheckAccreditationHistory.getCheckAccreditationHistoryStat()).isEqualTo(UPDATED_CHECK_ACCREDITATION_HISTORY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCheckAccreditationHistory() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();
        checkAccreditationHistory.setCheckAccreditationHistoryId(count.incrementAndGet());

        // Create the CheckAccreditationHistory
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckAccreditationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkAccreditationHistoryDTO.getCheckAccreditationHistoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckAccreditationHistory() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();
        checkAccreditationHistory.setCheckAccreditationHistoryId(count.incrementAndGet());

        // Create the CheckAccreditationHistory
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckAccreditationHistory() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationHistoryRepository.findAll().size();
        checkAccreditationHistory.setCheckAccreditationHistoryId(count.incrementAndGet());

        // Create the CheckAccreditationHistory
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = checkAccreditationHistoryMapper.toDto(checkAccreditationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckAccreditationHistory in the database
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckAccreditationHistory() throws Exception {
        // Initialize the database
        checkAccreditationHistoryRepository.saveAndFlush(checkAccreditationHistory);

        int databaseSizeBeforeDelete = checkAccreditationHistoryRepository.findAll().size();

        // Delete the checkAccreditationHistory
        restCheckAccreditationHistoryMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, checkAccreditationHistory.getCheckAccreditationHistoryId()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckAccreditationHistory> checkAccreditationHistoryList = checkAccreditationHistoryRepository.findAll();
        assertThat(checkAccreditationHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
