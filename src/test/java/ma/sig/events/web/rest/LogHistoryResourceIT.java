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
import ma.sig.events.domain.LogHistory;
import ma.sig.events.repository.LogHistoryRepository;
import ma.sig.events.service.criteria.LogHistoryCriteria;
import ma.sig.events.service.dto.LogHistoryDTO;
import ma.sig.events.service.mapper.LogHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LogHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LogHistoryResourceIT {

    private static final String DEFAULT_LOG_HISTORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LOG_HISTORY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOG_HISTORY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_LOG_HISTORY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_LOG_HISTORY_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_LOG_HISTORY_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LOG_HISTORY_STAT = false;
    private static final Boolean UPDATED_LOG_HISTORY_STAT = true;

    private static final String ENTITY_API_URL = "/api/log-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{logHistory}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LogHistoryRepository logHistoryRepository;

    @Autowired
    private LogHistoryMapper logHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogHistoryMockMvc;

    private LogHistory logHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogHistory createEntity(EntityManager em) {
        LogHistory logHistory = new LogHistory()
            .logHistoryDescription(DEFAULT_LOG_HISTORY_DESCRIPTION)
            .logHistoryParams(DEFAULT_LOG_HISTORY_PARAMS)
            .logHistoryAttributs(DEFAULT_LOG_HISTORY_ATTRIBUTS)
            .logHistoryStat(DEFAULT_LOG_HISTORY_STAT);
        return logHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogHistory createUpdatedEntity(EntityManager em) {
        LogHistory logHistory = new LogHistory()
            .logHistoryDescription(UPDATED_LOG_HISTORY_DESCRIPTION)
            .logHistoryParams(UPDATED_LOG_HISTORY_PARAMS)
            .logHistoryAttributs(UPDATED_LOG_HISTORY_ATTRIBUTS)
            .logHistoryStat(UPDATED_LOG_HISTORY_STAT);
        return logHistory;
    }

    @BeforeEach
    public void initTest() {
        logHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createLogHistory() throws Exception {
        int databaseSizeBeforeCreate = logHistoryRepository.findAll().size();
        // Create the LogHistory
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);
        restLogHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        LogHistory testLogHistory = logHistoryList.get(logHistoryList.size() - 1);
        assertThat(testLogHistory.getLogHistoryDescription()).isEqualTo(DEFAULT_LOG_HISTORY_DESCRIPTION);
        assertThat(testLogHistory.getLogHistoryParams()).isEqualTo(DEFAULT_LOG_HISTORY_PARAMS);
        assertThat(testLogHistory.getLogHistoryAttributs()).isEqualTo(DEFAULT_LOG_HISTORY_ATTRIBUTS);
        assertThat(testLogHistory.getLogHistoryStat()).isEqualTo(DEFAULT_LOG_HISTORY_STAT);
    }

    @Test
    @Transactional
    void createLogHistoryWithExistingId() throws Exception {
        // Create the LogHistory with an existing ID
        logHistory.setLogHistory(1L);
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);

        int databaseSizeBeforeCreate = logHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLogHistories() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList
        restLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=logHistory,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].logHistory").value(hasItem(logHistory.getLogHistory().intValue())))
            .andExpect(jsonPath("$.[*].logHistoryDescription").value(hasItem(DEFAULT_LOG_HISTORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].logHistoryParams").value(hasItem(DEFAULT_LOG_HISTORY_PARAMS)))
            .andExpect(jsonPath("$.[*].logHistoryAttributs").value(hasItem(DEFAULT_LOG_HISTORY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].logHistoryStat").value(hasItem(DEFAULT_LOG_HISTORY_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getLogHistory() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get the logHistory
        restLogHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, logHistory.getLogHistory()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.logHistory").value(logHistory.getLogHistory().intValue()))
            .andExpect(jsonPath("$.logHistoryDescription").value(DEFAULT_LOG_HISTORY_DESCRIPTION))
            .andExpect(jsonPath("$.logHistoryParams").value(DEFAULT_LOG_HISTORY_PARAMS))
            .andExpect(jsonPath("$.logHistoryAttributs").value(DEFAULT_LOG_HISTORY_ATTRIBUTS))
            .andExpect(jsonPath("$.logHistoryStat").value(DEFAULT_LOG_HISTORY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getLogHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        Long id = logHistory.getLogHistory();

        defaultLogHistoryShouldBeFound("logHistory.equals=" + id);
        defaultLogHistoryShouldNotBeFound("logHistory.notEquals=" + id);

        defaultLogHistoryShouldBeFound("logHistory.greaterThanOrEqual=" + id);
        defaultLogHistoryShouldNotBeFound("logHistory.greaterThan=" + id);

        defaultLogHistoryShouldBeFound("logHistory.lessThanOrEqual=" + id);
        defaultLogHistoryShouldNotBeFound("logHistory.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryDescription equals to DEFAULT_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldBeFound("logHistoryDescription.equals=" + DEFAULT_LOG_HISTORY_DESCRIPTION);

        // Get all the logHistoryList where logHistoryDescription equals to UPDATED_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldNotBeFound("logHistoryDescription.equals=" + UPDATED_LOG_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryDescription in DEFAULT_LOG_HISTORY_DESCRIPTION or UPDATED_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldBeFound(
            "logHistoryDescription.in=" + DEFAULT_LOG_HISTORY_DESCRIPTION + "," + UPDATED_LOG_HISTORY_DESCRIPTION
        );

        // Get all the logHistoryList where logHistoryDescription equals to UPDATED_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldNotBeFound("logHistoryDescription.in=" + UPDATED_LOG_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryDescription is not null
        defaultLogHistoryShouldBeFound("logHistoryDescription.specified=true");

        // Get all the logHistoryList where logHistoryDescription is null
        defaultLogHistoryShouldNotBeFound("logHistoryDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryDescriptionContainsSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryDescription contains DEFAULT_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldBeFound("logHistoryDescription.contains=" + DEFAULT_LOG_HISTORY_DESCRIPTION);

        // Get all the logHistoryList where logHistoryDescription contains UPDATED_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldNotBeFound("logHistoryDescription.contains=" + UPDATED_LOG_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryDescription does not contain DEFAULT_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldNotBeFound("logHistoryDescription.doesNotContain=" + DEFAULT_LOG_HISTORY_DESCRIPTION);

        // Get all the logHistoryList where logHistoryDescription does not contain UPDATED_LOG_HISTORY_DESCRIPTION
        defaultLogHistoryShouldBeFound("logHistoryDescription.doesNotContain=" + UPDATED_LOG_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryParams equals to DEFAULT_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldBeFound("logHistoryParams.equals=" + DEFAULT_LOG_HISTORY_PARAMS);

        // Get all the logHistoryList where logHistoryParams equals to UPDATED_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldNotBeFound("logHistoryParams.equals=" + UPDATED_LOG_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryParamsIsInShouldWork() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryParams in DEFAULT_LOG_HISTORY_PARAMS or UPDATED_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldBeFound("logHistoryParams.in=" + DEFAULT_LOG_HISTORY_PARAMS + "," + UPDATED_LOG_HISTORY_PARAMS);

        // Get all the logHistoryList where logHistoryParams equals to UPDATED_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldNotBeFound("logHistoryParams.in=" + UPDATED_LOG_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryParams is not null
        defaultLogHistoryShouldBeFound("logHistoryParams.specified=true");

        // Get all the logHistoryList where logHistoryParams is null
        defaultLogHistoryShouldNotBeFound("logHistoryParams.specified=false");
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryParamsContainsSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryParams contains DEFAULT_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldBeFound("logHistoryParams.contains=" + DEFAULT_LOG_HISTORY_PARAMS);

        // Get all the logHistoryList where logHistoryParams contains UPDATED_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldNotBeFound("logHistoryParams.contains=" + UPDATED_LOG_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryParamsNotContainsSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryParams does not contain DEFAULT_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldNotBeFound("logHistoryParams.doesNotContain=" + DEFAULT_LOG_HISTORY_PARAMS);

        // Get all the logHistoryList where logHistoryParams does not contain UPDATED_LOG_HISTORY_PARAMS
        defaultLogHistoryShouldBeFound("logHistoryParams.doesNotContain=" + UPDATED_LOG_HISTORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryAttributs equals to DEFAULT_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldBeFound("logHistoryAttributs.equals=" + DEFAULT_LOG_HISTORY_ATTRIBUTS);

        // Get all the logHistoryList where logHistoryAttributs equals to UPDATED_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldNotBeFound("logHistoryAttributs.equals=" + UPDATED_LOG_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryAttributs in DEFAULT_LOG_HISTORY_ATTRIBUTS or UPDATED_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldBeFound("logHistoryAttributs.in=" + DEFAULT_LOG_HISTORY_ATTRIBUTS + "," + UPDATED_LOG_HISTORY_ATTRIBUTS);

        // Get all the logHistoryList where logHistoryAttributs equals to UPDATED_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldNotBeFound("logHistoryAttributs.in=" + UPDATED_LOG_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryAttributs is not null
        defaultLogHistoryShouldBeFound("logHistoryAttributs.specified=true");

        // Get all the logHistoryList where logHistoryAttributs is null
        defaultLogHistoryShouldNotBeFound("logHistoryAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryAttributsContainsSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryAttributs contains DEFAULT_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldBeFound("logHistoryAttributs.contains=" + DEFAULT_LOG_HISTORY_ATTRIBUTS);

        // Get all the logHistoryList where logHistoryAttributs contains UPDATED_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldNotBeFound("logHistoryAttributs.contains=" + UPDATED_LOG_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryAttributs does not contain DEFAULT_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldNotBeFound("logHistoryAttributs.doesNotContain=" + DEFAULT_LOG_HISTORY_ATTRIBUTS);

        // Get all the logHistoryList where logHistoryAttributs does not contain UPDATED_LOG_HISTORY_ATTRIBUTS
        defaultLogHistoryShouldBeFound("logHistoryAttributs.doesNotContain=" + UPDATED_LOG_HISTORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryStatIsEqualToSomething() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryStat equals to DEFAULT_LOG_HISTORY_STAT
        defaultLogHistoryShouldBeFound("logHistoryStat.equals=" + DEFAULT_LOG_HISTORY_STAT);

        // Get all the logHistoryList where logHistoryStat equals to UPDATED_LOG_HISTORY_STAT
        defaultLogHistoryShouldNotBeFound("logHistoryStat.equals=" + UPDATED_LOG_HISTORY_STAT);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryStatIsInShouldWork() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryStat in DEFAULT_LOG_HISTORY_STAT or UPDATED_LOG_HISTORY_STAT
        defaultLogHistoryShouldBeFound("logHistoryStat.in=" + DEFAULT_LOG_HISTORY_STAT + "," + UPDATED_LOG_HISTORY_STAT);

        // Get all the logHistoryList where logHistoryStat equals to UPDATED_LOG_HISTORY_STAT
        defaultLogHistoryShouldNotBeFound("logHistoryStat.in=" + UPDATED_LOG_HISTORY_STAT);
    }

    @Test
    @Transactional
    void getAllLogHistoriesByLogHistoryStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        // Get all the logHistoryList where logHistoryStat is not null
        defaultLogHistoryShouldBeFound("logHistoryStat.specified=true");

        // Get all the logHistoryList where logHistoryStat is null
        defaultLogHistoryShouldNotBeFound("logHistoryStat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLogHistoryShouldBeFound(String filter) throws Exception {
        restLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=logHistory,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].logHistory").value(hasItem(logHistory.getLogHistory().intValue())))
            .andExpect(jsonPath("$.[*].logHistoryDescription").value(hasItem(DEFAULT_LOG_HISTORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].logHistoryParams").value(hasItem(DEFAULT_LOG_HISTORY_PARAMS)))
            .andExpect(jsonPath("$.[*].logHistoryAttributs").value(hasItem(DEFAULT_LOG_HISTORY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].logHistoryStat").value(hasItem(DEFAULT_LOG_HISTORY_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=logHistory,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLogHistoryShouldNotBeFound(String filter) throws Exception {
        restLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=logHistory,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=logHistory,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLogHistory() throws Exception {
        // Get the logHistory
        restLogHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLogHistory() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();

        // Update the logHistory
        LogHistory updatedLogHistory = logHistoryRepository.findById(logHistory.getLogHistory()).get();
        // Disconnect from session so that the updates on updatedLogHistory are not directly saved in db
        em.detach(updatedLogHistory);
        updatedLogHistory
            .logHistoryDescription(UPDATED_LOG_HISTORY_DESCRIPTION)
            .logHistoryParams(UPDATED_LOG_HISTORY_PARAMS)
            .logHistoryAttributs(UPDATED_LOG_HISTORY_ATTRIBUTS)
            .logHistoryStat(UPDATED_LOG_HISTORY_STAT);
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(updatedLogHistory);

        restLogHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logHistoryDTO.getLogHistory())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
        LogHistory testLogHistory = logHistoryList.get(logHistoryList.size() - 1);
        assertThat(testLogHistory.getLogHistoryDescription()).isEqualTo(UPDATED_LOG_HISTORY_DESCRIPTION);
        assertThat(testLogHistory.getLogHistoryParams()).isEqualTo(UPDATED_LOG_HISTORY_PARAMS);
        assertThat(testLogHistory.getLogHistoryAttributs()).isEqualTo(UPDATED_LOG_HISTORY_ATTRIBUTS);
        assertThat(testLogHistory.getLogHistoryStat()).isEqualTo(UPDATED_LOG_HISTORY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();
        logHistory.setLogHistory(count.incrementAndGet());

        // Create the LogHistory
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logHistoryDTO.getLogHistory())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();
        logHistory.setLogHistory(count.incrementAndGet());

        // Create the LogHistory
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();
        logHistory.setLogHistory(count.incrementAndGet());

        // Create the LogHistory
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLogHistoryWithPatch() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();

        // Update the logHistory using partial update
        LogHistory partialUpdatedLogHistory = new LogHistory();
        partialUpdatedLogHistory.setLogHistory(logHistory.getLogHistory());

        partialUpdatedLogHistory.logHistoryParams(UPDATED_LOG_HISTORY_PARAMS);

        restLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogHistory.getLogHistory())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogHistory))
            )
            .andExpect(status().isOk());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
        LogHistory testLogHistory = logHistoryList.get(logHistoryList.size() - 1);
        assertThat(testLogHistory.getLogHistoryDescription()).isEqualTo(DEFAULT_LOG_HISTORY_DESCRIPTION);
        assertThat(testLogHistory.getLogHistoryParams()).isEqualTo(UPDATED_LOG_HISTORY_PARAMS);
        assertThat(testLogHistory.getLogHistoryAttributs()).isEqualTo(DEFAULT_LOG_HISTORY_ATTRIBUTS);
        assertThat(testLogHistory.getLogHistoryStat()).isEqualTo(DEFAULT_LOG_HISTORY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateLogHistoryWithPatch() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();

        // Update the logHistory using partial update
        LogHistory partialUpdatedLogHistory = new LogHistory();
        partialUpdatedLogHistory.setLogHistory(logHistory.getLogHistory());

        partialUpdatedLogHistory
            .logHistoryDescription(UPDATED_LOG_HISTORY_DESCRIPTION)
            .logHistoryParams(UPDATED_LOG_HISTORY_PARAMS)
            .logHistoryAttributs(UPDATED_LOG_HISTORY_ATTRIBUTS)
            .logHistoryStat(UPDATED_LOG_HISTORY_STAT);

        restLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogHistory.getLogHistory())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogHistory))
            )
            .andExpect(status().isOk());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
        LogHistory testLogHistory = logHistoryList.get(logHistoryList.size() - 1);
        assertThat(testLogHistory.getLogHistoryDescription()).isEqualTo(UPDATED_LOG_HISTORY_DESCRIPTION);
        assertThat(testLogHistory.getLogHistoryParams()).isEqualTo(UPDATED_LOG_HISTORY_PARAMS);
        assertThat(testLogHistory.getLogHistoryAttributs()).isEqualTo(UPDATED_LOG_HISTORY_ATTRIBUTS);
        assertThat(testLogHistory.getLogHistoryStat()).isEqualTo(UPDATED_LOG_HISTORY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();
        logHistory.setLogHistory(count.incrementAndGet());

        // Create the LogHistory
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, logHistoryDTO.getLogHistory())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();
        logHistory.setLogHistory(count.incrementAndGet());

        // Create the LogHistory
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = logHistoryRepository.findAll().size();
        logHistory.setLogHistory(count.incrementAndGet());

        // Create the LogHistory
        LogHistoryDTO logHistoryDTO = logHistoryMapper.toDto(logHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(logHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogHistory in the database
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLogHistory() throws Exception {
        // Initialize the database
        logHistoryRepository.saveAndFlush(logHistory);

        int databaseSizeBeforeDelete = logHistoryRepository.findAll().size();

        // Delete the logHistory
        restLogHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, logHistory.getLogHistory()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogHistory> logHistoryList = logHistoryRepository.findAll();
        assertThat(logHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
