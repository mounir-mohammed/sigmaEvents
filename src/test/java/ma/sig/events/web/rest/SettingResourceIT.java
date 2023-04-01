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
import ma.sig.events.domain.Language;
import ma.sig.events.domain.Setting;
import ma.sig.events.repository.SettingRepository;
import ma.sig.events.service.criteria.SettingCriteria;
import ma.sig.events.service.dto.SettingDTO;
import ma.sig.events.service.mapper.SettingMapper;
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
 * Integration tests for the {@link SettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SettingResourceIT {

    private static final Long DEFAULT_SETTING_PARENT_ID = 1L;
    private static final Long UPDATED_SETTING_PARENT_ID = 2L;
    private static final Long SMALLER_SETTING_PARENT_ID = 1L - 1L;

    private static final String DEFAULT_SETTING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SETTING_NAME_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_NAME_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_SETTING_DATA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_DATA_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SETTING_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SETTING_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_VALUE_STRING = "BBBBBBBBBB";

    private static final Long DEFAULT_SETTING_VALUE_LONG = 1L;
    private static final Long UPDATED_SETTING_VALUE_LONG = 2L;
    private static final Long SMALLER_SETTING_VALUE_LONG = 1L - 1L;

    private static final ZonedDateTime DEFAULT_SETTING_VALUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SETTING_VALUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_SETTING_VALUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_SETTING_VALUE_BOOLEAN = false;
    private static final Boolean UPDATED_SETTING_VALUE_BOOLEAN = true;

    private static final byte[] DEFAULT_SETTING_VALUE_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SETTING_VALUE_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SETTING_VALUE_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SETTING_VALUE_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SETTING_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_SETTING_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SETTING_STAT = false;
    private static final Boolean UPDATED_SETTING_STAT = true;

    private static final String ENTITY_API_URL = "/api/settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{settingId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSettingMockMvc;

    private Setting setting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createEntity(EntityManager em) {
        Setting setting = new Setting()
            .settingParentId(DEFAULT_SETTING_PARENT_ID)
            .settingType(DEFAULT_SETTING_TYPE)
            .settingNameClass(DEFAULT_SETTING_NAME_CLASS)
            .settingDataType(DEFAULT_SETTING_DATA_TYPE)
            .settingDescription(DEFAULT_SETTING_DESCRIPTION)
            .settingValueString(DEFAULT_SETTING_VALUE_STRING)
            .settingValueLong(DEFAULT_SETTING_VALUE_LONG)
            .settingValueDate(DEFAULT_SETTING_VALUE_DATE)
            .settingValueBoolean(DEFAULT_SETTING_VALUE_BOOLEAN)
            .settingValueBlob(DEFAULT_SETTING_VALUE_BLOB)
            .settingValueBlobContentType(DEFAULT_SETTING_VALUE_BLOB_CONTENT_TYPE)
            .settingParams(DEFAULT_SETTING_PARAMS)
            .settingAttributs(DEFAULT_SETTING_ATTRIBUTS)
            .settingStat(DEFAULT_SETTING_STAT);
        return setting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createUpdatedEntity(EntityManager em) {
        Setting setting = new Setting()
            .settingParentId(UPDATED_SETTING_PARENT_ID)
            .settingType(UPDATED_SETTING_TYPE)
            .settingNameClass(UPDATED_SETTING_NAME_CLASS)
            .settingDataType(UPDATED_SETTING_DATA_TYPE)
            .settingDescription(UPDATED_SETTING_DESCRIPTION)
            .settingValueString(UPDATED_SETTING_VALUE_STRING)
            .settingValueLong(UPDATED_SETTING_VALUE_LONG)
            .settingValueDate(UPDATED_SETTING_VALUE_DATE)
            .settingValueBoolean(UPDATED_SETTING_VALUE_BOOLEAN)
            .settingValueBlob(UPDATED_SETTING_VALUE_BLOB)
            .settingValueBlobContentType(UPDATED_SETTING_VALUE_BLOB_CONTENT_TYPE)
            .settingParams(UPDATED_SETTING_PARAMS)
            .settingAttributs(UPDATED_SETTING_ATTRIBUTS)
            .settingStat(UPDATED_SETTING_STAT);
        return setting;
    }

    @BeforeEach
    public void initTest() {
        setting = createEntity(em);
    }

    @Test
    @Transactional
    void createSetting() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();
        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);
        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isCreated());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate + 1);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getSettingParentId()).isEqualTo(DEFAULT_SETTING_PARENT_ID);
        assertThat(testSetting.getSettingType()).isEqualTo(DEFAULT_SETTING_TYPE);
        assertThat(testSetting.getSettingNameClass()).isEqualTo(DEFAULT_SETTING_NAME_CLASS);
        assertThat(testSetting.getSettingDataType()).isEqualTo(DEFAULT_SETTING_DATA_TYPE);
        assertThat(testSetting.getSettingDescription()).isEqualTo(DEFAULT_SETTING_DESCRIPTION);
        assertThat(testSetting.getSettingValueString()).isEqualTo(DEFAULT_SETTING_VALUE_STRING);
        assertThat(testSetting.getSettingValueLong()).isEqualTo(DEFAULT_SETTING_VALUE_LONG);
        assertThat(testSetting.getSettingValueDate()).isEqualTo(DEFAULT_SETTING_VALUE_DATE);
        assertThat(testSetting.getSettingValueBoolean()).isEqualTo(DEFAULT_SETTING_VALUE_BOOLEAN);
        assertThat(testSetting.getSettingValueBlob()).isEqualTo(DEFAULT_SETTING_VALUE_BLOB);
        assertThat(testSetting.getSettingValueBlobContentType()).isEqualTo(DEFAULT_SETTING_VALUE_BLOB_CONTENT_TYPE);
        assertThat(testSetting.getSettingParams()).isEqualTo(DEFAULT_SETTING_PARAMS);
        assertThat(testSetting.getSettingAttributs()).isEqualTo(DEFAULT_SETTING_ATTRIBUTS);
        assertThat(testSetting.getSettingStat()).isEqualTo(DEFAULT_SETTING_STAT);
    }

    @Test
    @Transactional
    void createSettingWithExistingId() throws Exception {
        // Create the Setting with an existing ID
        setting.setSettingId(1L);
        SettingDTO settingDTO = settingMapper.toDto(setting);

        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSettingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        setting.setSettingType(null);

        // Create the Setting, which fails.
        SettingDTO settingDTO = settingMapper.toDto(setting);

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettingNameClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        setting.setSettingNameClass(null);

        // Create the Setting, which fails.
        SettingDTO settingDTO = settingMapper.toDto(setting);

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettingDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        setting.setSettingDataType(null);

        // Create the Setting, which fails.
        SettingDTO settingDTO = settingMapper.toDto(setting);

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSettings() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=settingId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].settingId").value(hasItem(setting.getSettingId().intValue())))
            .andExpect(jsonPath("$.[*].settingParentId").value(hasItem(DEFAULT_SETTING_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].settingType").value(hasItem(DEFAULT_SETTING_TYPE)))
            .andExpect(jsonPath("$.[*].settingNameClass").value(hasItem(DEFAULT_SETTING_NAME_CLASS)))
            .andExpect(jsonPath("$.[*].settingDataType").value(hasItem(DEFAULT_SETTING_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].settingDescription").value(hasItem(DEFAULT_SETTING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].settingValueString").value(hasItem(DEFAULT_SETTING_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].settingValueLong").value(hasItem(DEFAULT_SETTING_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].settingValueDate").value(hasItem(sameInstant(DEFAULT_SETTING_VALUE_DATE))))
            .andExpect(jsonPath("$.[*].settingValueBoolean").value(hasItem(DEFAULT_SETTING_VALUE_BOOLEAN.booleanValue())))
            .andExpect(jsonPath("$.[*].settingValueBlobContentType").value(hasItem(DEFAULT_SETTING_VALUE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].settingValueBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_SETTING_VALUE_BLOB))))
            .andExpect(jsonPath("$.[*].settingParams").value(hasItem(DEFAULT_SETTING_PARAMS)))
            .andExpect(jsonPath("$.[*].settingAttributs").value(hasItem(DEFAULT_SETTING_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].settingStat").value(hasItem(DEFAULT_SETTING_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get the setting
        restSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, setting.getSettingId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.settingId").value(setting.getSettingId().intValue()))
            .andExpect(jsonPath("$.settingParentId").value(DEFAULT_SETTING_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.settingType").value(DEFAULT_SETTING_TYPE))
            .andExpect(jsonPath("$.settingNameClass").value(DEFAULT_SETTING_NAME_CLASS))
            .andExpect(jsonPath("$.settingDataType").value(DEFAULT_SETTING_DATA_TYPE))
            .andExpect(jsonPath("$.settingDescription").value(DEFAULT_SETTING_DESCRIPTION))
            .andExpect(jsonPath("$.settingValueString").value(DEFAULT_SETTING_VALUE_STRING))
            .andExpect(jsonPath("$.settingValueLong").value(DEFAULT_SETTING_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.settingValueDate").value(sameInstant(DEFAULT_SETTING_VALUE_DATE)))
            .andExpect(jsonPath("$.settingValueBoolean").value(DEFAULT_SETTING_VALUE_BOOLEAN.booleanValue()))
            .andExpect(jsonPath("$.settingValueBlobContentType").value(DEFAULT_SETTING_VALUE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.settingValueBlob").value(Base64Utils.encodeToString(DEFAULT_SETTING_VALUE_BLOB)))
            .andExpect(jsonPath("$.settingParams").value(DEFAULT_SETTING_PARAMS))
            .andExpect(jsonPath("$.settingAttributs").value(DEFAULT_SETTING_ATTRIBUTS))
            .andExpect(jsonPath("$.settingStat").value(DEFAULT_SETTING_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getSettingsByIdFiltering() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        Long id = setting.getSettingId();

        defaultSettingShouldBeFound("settingId.equals=" + id);
        defaultSettingShouldNotBeFound("settingId.notEquals=" + id);

        defaultSettingShouldBeFound("settingId.greaterThanOrEqual=" + id);
        defaultSettingShouldNotBeFound("settingId.greaterThan=" + id);

        defaultSettingShouldBeFound("settingId.lessThanOrEqual=" + id);
        defaultSettingShouldNotBeFound("settingId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParentId equals to DEFAULT_SETTING_PARENT_ID
        defaultSettingShouldBeFound("settingParentId.equals=" + DEFAULT_SETTING_PARENT_ID);

        // Get all the settingList where settingParentId equals to UPDATED_SETTING_PARENT_ID
        defaultSettingShouldNotBeFound("settingParentId.equals=" + UPDATED_SETTING_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParentIdIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParentId in DEFAULT_SETTING_PARENT_ID or UPDATED_SETTING_PARENT_ID
        defaultSettingShouldBeFound("settingParentId.in=" + DEFAULT_SETTING_PARENT_ID + "," + UPDATED_SETTING_PARENT_ID);

        // Get all the settingList where settingParentId equals to UPDATED_SETTING_PARENT_ID
        defaultSettingShouldNotBeFound("settingParentId.in=" + UPDATED_SETTING_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParentId is not null
        defaultSettingShouldBeFound("settingParentId.specified=true");

        // Get all the settingList where settingParentId is null
        defaultSettingShouldNotBeFound("settingParentId.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParentId is greater than or equal to DEFAULT_SETTING_PARENT_ID
        defaultSettingShouldBeFound("settingParentId.greaterThanOrEqual=" + DEFAULT_SETTING_PARENT_ID);

        // Get all the settingList where settingParentId is greater than or equal to UPDATED_SETTING_PARENT_ID
        defaultSettingShouldNotBeFound("settingParentId.greaterThanOrEqual=" + UPDATED_SETTING_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParentId is less than or equal to DEFAULT_SETTING_PARENT_ID
        defaultSettingShouldBeFound("settingParentId.lessThanOrEqual=" + DEFAULT_SETTING_PARENT_ID);

        // Get all the settingList where settingParentId is less than or equal to SMALLER_SETTING_PARENT_ID
        defaultSettingShouldNotBeFound("settingParentId.lessThanOrEqual=" + SMALLER_SETTING_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParentId is less than DEFAULT_SETTING_PARENT_ID
        defaultSettingShouldNotBeFound("settingParentId.lessThan=" + DEFAULT_SETTING_PARENT_ID);

        // Get all the settingList where settingParentId is less than UPDATED_SETTING_PARENT_ID
        defaultSettingShouldBeFound("settingParentId.lessThan=" + UPDATED_SETTING_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParentId is greater than DEFAULT_SETTING_PARENT_ID
        defaultSettingShouldNotBeFound("settingParentId.greaterThan=" + DEFAULT_SETTING_PARENT_ID);

        // Get all the settingList where settingParentId is greater than SMALLER_SETTING_PARENT_ID
        defaultSettingShouldBeFound("settingParentId.greaterThan=" + SMALLER_SETTING_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingType equals to DEFAULT_SETTING_TYPE
        defaultSettingShouldBeFound("settingType.equals=" + DEFAULT_SETTING_TYPE);

        // Get all the settingList where settingType equals to UPDATED_SETTING_TYPE
        defaultSettingShouldNotBeFound("settingType.equals=" + UPDATED_SETTING_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingType in DEFAULT_SETTING_TYPE or UPDATED_SETTING_TYPE
        defaultSettingShouldBeFound("settingType.in=" + DEFAULT_SETTING_TYPE + "," + UPDATED_SETTING_TYPE);

        // Get all the settingList where settingType equals to UPDATED_SETTING_TYPE
        defaultSettingShouldNotBeFound("settingType.in=" + UPDATED_SETTING_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingType is not null
        defaultSettingShouldBeFound("settingType.specified=true");

        // Get all the settingList where settingType is null
        defaultSettingShouldNotBeFound("settingType.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingTypeContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingType contains DEFAULT_SETTING_TYPE
        defaultSettingShouldBeFound("settingType.contains=" + DEFAULT_SETTING_TYPE);

        // Get all the settingList where settingType contains UPDATED_SETTING_TYPE
        defaultSettingShouldNotBeFound("settingType.contains=" + UPDATED_SETTING_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingTypeNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingType does not contain DEFAULT_SETTING_TYPE
        defaultSettingShouldNotBeFound("settingType.doesNotContain=" + DEFAULT_SETTING_TYPE);

        // Get all the settingList where settingType does not contain UPDATED_SETTING_TYPE
        defaultSettingShouldBeFound("settingType.doesNotContain=" + UPDATED_SETTING_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingNameClassIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingNameClass equals to DEFAULT_SETTING_NAME_CLASS
        defaultSettingShouldBeFound("settingNameClass.equals=" + DEFAULT_SETTING_NAME_CLASS);

        // Get all the settingList where settingNameClass equals to UPDATED_SETTING_NAME_CLASS
        defaultSettingShouldNotBeFound("settingNameClass.equals=" + UPDATED_SETTING_NAME_CLASS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingNameClassIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingNameClass in DEFAULT_SETTING_NAME_CLASS or UPDATED_SETTING_NAME_CLASS
        defaultSettingShouldBeFound("settingNameClass.in=" + DEFAULT_SETTING_NAME_CLASS + "," + UPDATED_SETTING_NAME_CLASS);

        // Get all the settingList where settingNameClass equals to UPDATED_SETTING_NAME_CLASS
        defaultSettingShouldNotBeFound("settingNameClass.in=" + UPDATED_SETTING_NAME_CLASS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingNameClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingNameClass is not null
        defaultSettingShouldBeFound("settingNameClass.specified=true");

        // Get all the settingList where settingNameClass is null
        defaultSettingShouldNotBeFound("settingNameClass.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingNameClassContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingNameClass contains DEFAULT_SETTING_NAME_CLASS
        defaultSettingShouldBeFound("settingNameClass.contains=" + DEFAULT_SETTING_NAME_CLASS);

        // Get all the settingList where settingNameClass contains UPDATED_SETTING_NAME_CLASS
        defaultSettingShouldNotBeFound("settingNameClass.contains=" + UPDATED_SETTING_NAME_CLASS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingNameClassNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingNameClass does not contain DEFAULT_SETTING_NAME_CLASS
        defaultSettingShouldNotBeFound("settingNameClass.doesNotContain=" + DEFAULT_SETTING_NAME_CLASS);

        // Get all the settingList where settingNameClass does not contain UPDATED_SETTING_NAME_CLASS
        defaultSettingShouldBeFound("settingNameClass.doesNotContain=" + UPDATED_SETTING_NAME_CLASS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDataType equals to DEFAULT_SETTING_DATA_TYPE
        defaultSettingShouldBeFound("settingDataType.equals=" + DEFAULT_SETTING_DATA_TYPE);

        // Get all the settingList where settingDataType equals to UPDATED_SETTING_DATA_TYPE
        defaultSettingShouldNotBeFound("settingDataType.equals=" + UPDATED_SETTING_DATA_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDataType in DEFAULT_SETTING_DATA_TYPE or UPDATED_SETTING_DATA_TYPE
        defaultSettingShouldBeFound("settingDataType.in=" + DEFAULT_SETTING_DATA_TYPE + "," + UPDATED_SETTING_DATA_TYPE);

        // Get all the settingList where settingDataType equals to UPDATED_SETTING_DATA_TYPE
        defaultSettingShouldNotBeFound("settingDataType.in=" + UPDATED_SETTING_DATA_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDataType is not null
        defaultSettingShouldBeFound("settingDataType.specified=true");

        // Get all the settingList where settingDataType is null
        defaultSettingShouldNotBeFound("settingDataType.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDataTypeContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDataType contains DEFAULT_SETTING_DATA_TYPE
        defaultSettingShouldBeFound("settingDataType.contains=" + DEFAULT_SETTING_DATA_TYPE);

        // Get all the settingList where settingDataType contains UPDATED_SETTING_DATA_TYPE
        defaultSettingShouldNotBeFound("settingDataType.contains=" + UPDATED_SETTING_DATA_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDataTypeNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDataType does not contain DEFAULT_SETTING_DATA_TYPE
        defaultSettingShouldNotBeFound("settingDataType.doesNotContain=" + DEFAULT_SETTING_DATA_TYPE);

        // Get all the settingList where settingDataType does not contain UPDATED_SETTING_DATA_TYPE
        defaultSettingShouldBeFound("settingDataType.doesNotContain=" + UPDATED_SETTING_DATA_TYPE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDescription equals to DEFAULT_SETTING_DESCRIPTION
        defaultSettingShouldBeFound("settingDescription.equals=" + DEFAULT_SETTING_DESCRIPTION);

        // Get all the settingList where settingDescription equals to UPDATED_SETTING_DESCRIPTION
        defaultSettingShouldNotBeFound("settingDescription.equals=" + UPDATED_SETTING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDescription in DEFAULT_SETTING_DESCRIPTION or UPDATED_SETTING_DESCRIPTION
        defaultSettingShouldBeFound("settingDescription.in=" + DEFAULT_SETTING_DESCRIPTION + "," + UPDATED_SETTING_DESCRIPTION);

        // Get all the settingList where settingDescription equals to UPDATED_SETTING_DESCRIPTION
        defaultSettingShouldNotBeFound("settingDescription.in=" + UPDATED_SETTING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDescription is not null
        defaultSettingShouldBeFound("settingDescription.specified=true");

        // Get all the settingList where settingDescription is null
        defaultSettingShouldNotBeFound("settingDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDescriptionContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDescription contains DEFAULT_SETTING_DESCRIPTION
        defaultSettingShouldBeFound("settingDescription.contains=" + DEFAULT_SETTING_DESCRIPTION);

        // Get all the settingList where settingDescription contains UPDATED_SETTING_DESCRIPTION
        defaultSettingShouldNotBeFound("settingDescription.contains=" + UPDATED_SETTING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingDescription does not contain DEFAULT_SETTING_DESCRIPTION
        defaultSettingShouldNotBeFound("settingDescription.doesNotContain=" + DEFAULT_SETTING_DESCRIPTION);

        // Get all the settingList where settingDescription does not contain UPDATED_SETTING_DESCRIPTION
        defaultSettingShouldBeFound("settingDescription.doesNotContain=" + UPDATED_SETTING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueStringIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueString equals to DEFAULT_SETTING_VALUE_STRING
        defaultSettingShouldBeFound("settingValueString.equals=" + DEFAULT_SETTING_VALUE_STRING);

        // Get all the settingList where settingValueString equals to UPDATED_SETTING_VALUE_STRING
        defaultSettingShouldNotBeFound("settingValueString.equals=" + UPDATED_SETTING_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueStringIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueString in DEFAULT_SETTING_VALUE_STRING or UPDATED_SETTING_VALUE_STRING
        defaultSettingShouldBeFound("settingValueString.in=" + DEFAULT_SETTING_VALUE_STRING + "," + UPDATED_SETTING_VALUE_STRING);

        // Get all the settingList where settingValueString equals to UPDATED_SETTING_VALUE_STRING
        defaultSettingShouldNotBeFound("settingValueString.in=" + UPDATED_SETTING_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueString is not null
        defaultSettingShouldBeFound("settingValueString.specified=true");

        // Get all the settingList where settingValueString is null
        defaultSettingShouldNotBeFound("settingValueString.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueStringContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueString contains DEFAULT_SETTING_VALUE_STRING
        defaultSettingShouldBeFound("settingValueString.contains=" + DEFAULT_SETTING_VALUE_STRING);

        // Get all the settingList where settingValueString contains UPDATED_SETTING_VALUE_STRING
        defaultSettingShouldNotBeFound("settingValueString.contains=" + UPDATED_SETTING_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueStringNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueString does not contain DEFAULT_SETTING_VALUE_STRING
        defaultSettingShouldNotBeFound("settingValueString.doesNotContain=" + DEFAULT_SETTING_VALUE_STRING);

        // Get all the settingList where settingValueString does not contain UPDATED_SETTING_VALUE_STRING
        defaultSettingShouldBeFound("settingValueString.doesNotContain=" + UPDATED_SETTING_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueLongIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueLong equals to DEFAULT_SETTING_VALUE_LONG
        defaultSettingShouldBeFound("settingValueLong.equals=" + DEFAULT_SETTING_VALUE_LONG);

        // Get all the settingList where settingValueLong equals to UPDATED_SETTING_VALUE_LONG
        defaultSettingShouldNotBeFound("settingValueLong.equals=" + UPDATED_SETTING_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueLongIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueLong in DEFAULT_SETTING_VALUE_LONG or UPDATED_SETTING_VALUE_LONG
        defaultSettingShouldBeFound("settingValueLong.in=" + DEFAULT_SETTING_VALUE_LONG + "," + UPDATED_SETTING_VALUE_LONG);

        // Get all the settingList where settingValueLong equals to UPDATED_SETTING_VALUE_LONG
        defaultSettingShouldNotBeFound("settingValueLong.in=" + UPDATED_SETTING_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueLongIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueLong is not null
        defaultSettingShouldBeFound("settingValueLong.specified=true");

        // Get all the settingList where settingValueLong is null
        defaultSettingShouldNotBeFound("settingValueLong.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueLongIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueLong is greater than or equal to DEFAULT_SETTING_VALUE_LONG
        defaultSettingShouldBeFound("settingValueLong.greaterThanOrEqual=" + DEFAULT_SETTING_VALUE_LONG);

        // Get all the settingList where settingValueLong is greater than or equal to UPDATED_SETTING_VALUE_LONG
        defaultSettingShouldNotBeFound("settingValueLong.greaterThanOrEqual=" + UPDATED_SETTING_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueLongIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueLong is less than or equal to DEFAULT_SETTING_VALUE_LONG
        defaultSettingShouldBeFound("settingValueLong.lessThanOrEqual=" + DEFAULT_SETTING_VALUE_LONG);

        // Get all the settingList where settingValueLong is less than or equal to SMALLER_SETTING_VALUE_LONG
        defaultSettingShouldNotBeFound("settingValueLong.lessThanOrEqual=" + SMALLER_SETTING_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueLongIsLessThanSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueLong is less than DEFAULT_SETTING_VALUE_LONG
        defaultSettingShouldNotBeFound("settingValueLong.lessThan=" + DEFAULT_SETTING_VALUE_LONG);

        // Get all the settingList where settingValueLong is less than UPDATED_SETTING_VALUE_LONG
        defaultSettingShouldBeFound("settingValueLong.lessThan=" + UPDATED_SETTING_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueLongIsGreaterThanSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueLong is greater than DEFAULT_SETTING_VALUE_LONG
        defaultSettingShouldNotBeFound("settingValueLong.greaterThan=" + DEFAULT_SETTING_VALUE_LONG);

        // Get all the settingList where settingValueLong is greater than SMALLER_SETTING_VALUE_LONG
        defaultSettingShouldBeFound("settingValueLong.greaterThan=" + SMALLER_SETTING_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueDate equals to DEFAULT_SETTING_VALUE_DATE
        defaultSettingShouldBeFound("settingValueDate.equals=" + DEFAULT_SETTING_VALUE_DATE);

        // Get all the settingList where settingValueDate equals to UPDATED_SETTING_VALUE_DATE
        defaultSettingShouldNotBeFound("settingValueDate.equals=" + UPDATED_SETTING_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueDateIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueDate in DEFAULT_SETTING_VALUE_DATE or UPDATED_SETTING_VALUE_DATE
        defaultSettingShouldBeFound("settingValueDate.in=" + DEFAULT_SETTING_VALUE_DATE + "," + UPDATED_SETTING_VALUE_DATE);

        // Get all the settingList where settingValueDate equals to UPDATED_SETTING_VALUE_DATE
        defaultSettingShouldNotBeFound("settingValueDate.in=" + UPDATED_SETTING_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueDate is not null
        defaultSettingShouldBeFound("settingValueDate.specified=true");

        // Get all the settingList where settingValueDate is null
        defaultSettingShouldNotBeFound("settingValueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueDate is greater than or equal to DEFAULT_SETTING_VALUE_DATE
        defaultSettingShouldBeFound("settingValueDate.greaterThanOrEqual=" + DEFAULT_SETTING_VALUE_DATE);

        // Get all the settingList where settingValueDate is greater than or equal to UPDATED_SETTING_VALUE_DATE
        defaultSettingShouldNotBeFound("settingValueDate.greaterThanOrEqual=" + UPDATED_SETTING_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueDate is less than or equal to DEFAULT_SETTING_VALUE_DATE
        defaultSettingShouldBeFound("settingValueDate.lessThanOrEqual=" + DEFAULT_SETTING_VALUE_DATE);

        // Get all the settingList where settingValueDate is less than or equal to SMALLER_SETTING_VALUE_DATE
        defaultSettingShouldNotBeFound("settingValueDate.lessThanOrEqual=" + SMALLER_SETTING_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueDate is less than DEFAULT_SETTING_VALUE_DATE
        defaultSettingShouldNotBeFound("settingValueDate.lessThan=" + DEFAULT_SETTING_VALUE_DATE);

        // Get all the settingList where settingValueDate is less than UPDATED_SETTING_VALUE_DATE
        defaultSettingShouldBeFound("settingValueDate.lessThan=" + UPDATED_SETTING_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueDate is greater than DEFAULT_SETTING_VALUE_DATE
        defaultSettingShouldNotBeFound("settingValueDate.greaterThan=" + DEFAULT_SETTING_VALUE_DATE);

        // Get all the settingList where settingValueDate is greater than SMALLER_SETTING_VALUE_DATE
        defaultSettingShouldBeFound("settingValueDate.greaterThan=" + SMALLER_SETTING_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueBooleanIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueBoolean equals to DEFAULT_SETTING_VALUE_BOOLEAN
        defaultSettingShouldBeFound("settingValueBoolean.equals=" + DEFAULT_SETTING_VALUE_BOOLEAN);

        // Get all the settingList where settingValueBoolean equals to UPDATED_SETTING_VALUE_BOOLEAN
        defaultSettingShouldNotBeFound("settingValueBoolean.equals=" + UPDATED_SETTING_VALUE_BOOLEAN);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueBooleanIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueBoolean in DEFAULT_SETTING_VALUE_BOOLEAN or UPDATED_SETTING_VALUE_BOOLEAN
        defaultSettingShouldBeFound("settingValueBoolean.in=" + DEFAULT_SETTING_VALUE_BOOLEAN + "," + UPDATED_SETTING_VALUE_BOOLEAN);

        // Get all the settingList where settingValueBoolean equals to UPDATED_SETTING_VALUE_BOOLEAN
        defaultSettingShouldNotBeFound("settingValueBoolean.in=" + UPDATED_SETTING_VALUE_BOOLEAN);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingValueBooleanIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingValueBoolean is not null
        defaultSettingShouldBeFound("settingValueBoolean.specified=true");

        // Get all the settingList where settingValueBoolean is null
        defaultSettingShouldNotBeFound("settingValueBoolean.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParams equals to DEFAULT_SETTING_PARAMS
        defaultSettingShouldBeFound("settingParams.equals=" + DEFAULT_SETTING_PARAMS);

        // Get all the settingList where settingParams equals to UPDATED_SETTING_PARAMS
        defaultSettingShouldNotBeFound("settingParams.equals=" + UPDATED_SETTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParamsIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParams in DEFAULT_SETTING_PARAMS or UPDATED_SETTING_PARAMS
        defaultSettingShouldBeFound("settingParams.in=" + DEFAULT_SETTING_PARAMS + "," + UPDATED_SETTING_PARAMS);

        // Get all the settingList where settingParams equals to UPDATED_SETTING_PARAMS
        defaultSettingShouldNotBeFound("settingParams.in=" + UPDATED_SETTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParams is not null
        defaultSettingShouldBeFound("settingParams.specified=true");

        // Get all the settingList where settingParams is null
        defaultSettingShouldNotBeFound("settingParams.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParamsContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParams contains DEFAULT_SETTING_PARAMS
        defaultSettingShouldBeFound("settingParams.contains=" + DEFAULT_SETTING_PARAMS);

        // Get all the settingList where settingParams contains UPDATED_SETTING_PARAMS
        defaultSettingShouldNotBeFound("settingParams.contains=" + UPDATED_SETTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingParamsNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingParams does not contain DEFAULT_SETTING_PARAMS
        defaultSettingShouldNotBeFound("settingParams.doesNotContain=" + DEFAULT_SETTING_PARAMS);

        // Get all the settingList where settingParams does not contain UPDATED_SETTING_PARAMS
        defaultSettingShouldBeFound("settingParams.doesNotContain=" + UPDATED_SETTING_PARAMS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingAttributs equals to DEFAULT_SETTING_ATTRIBUTS
        defaultSettingShouldBeFound("settingAttributs.equals=" + DEFAULT_SETTING_ATTRIBUTS);

        // Get all the settingList where settingAttributs equals to UPDATED_SETTING_ATTRIBUTS
        defaultSettingShouldNotBeFound("settingAttributs.equals=" + UPDATED_SETTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingAttributs in DEFAULT_SETTING_ATTRIBUTS or UPDATED_SETTING_ATTRIBUTS
        defaultSettingShouldBeFound("settingAttributs.in=" + DEFAULT_SETTING_ATTRIBUTS + "," + UPDATED_SETTING_ATTRIBUTS);

        // Get all the settingList where settingAttributs equals to UPDATED_SETTING_ATTRIBUTS
        defaultSettingShouldNotBeFound("settingAttributs.in=" + UPDATED_SETTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingAttributs is not null
        defaultSettingShouldBeFound("settingAttributs.specified=true");

        // Get all the settingList where settingAttributs is null
        defaultSettingShouldNotBeFound("settingAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsBySettingAttributsContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingAttributs contains DEFAULT_SETTING_ATTRIBUTS
        defaultSettingShouldBeFound("settingAttributs.contains=" + DEFAULT_SETTING_ATTRIBUTS);

        // Get all the settingList where settingAttributs contains UPDATED_SETTING_ATTRIBUTS
        defaultSettingShouldNotBeFound("settingAttributs.contains=" + UPDATED_SETTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingAttributs does not contain DEFAULT_SETTING_ATTRIBUTS
        defaultSettingShouldNotBeFound("settingAttributs.doesNotContain=" + DEFAULT_SETTING_ATTRIBUTS);

        // Get all the settingList where settingAttributs does not contain UPDATED_SETTING_ATTRIBUTS
        defaultSettingShouldBeFound("settingAttributs.doesNotContain=" + UPDATED_SETTING_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingStatIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingStat equals to DEFAULT_SETTING_STAT
        defaultSettingShouldBeFound("settingStat.equals=" + DEFAULT_SETTING_STAT);

        // Get all the settingList where settingStat equals to UPDATED_SETTING_STAT
        defaultSettingShouldNotBeFound("settingStat.equals=" + UPDATED_SETTING_STAT);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingStatIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingStat in DEFAULT_SETTING_STAT or UPDATED_SETTING_STAT
        defaultSettingShouldBeFound("settingStat.in=" + DEFAULT_SETTING_STAT + "," + UPDATED_SETTING_STAT);

        // Get all the settingList where settingStat equals to UPDATED_SETTING_STAT
        defaultSettingShouldNotBeFound("settingStat.in=" + UPDATED_SETTING_STAT);
    }

    @Test
    @Transactional
    void getAllSettingsBySettingStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where settingStat is not null
        defaultSettingShouldBeFound("settingStat.specified=true");

        // Get all the settingList where settingStat is null
        defaultSettingShouldNotBeFound("settingStat.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsByLanguageIsEqualToSomething() throws Exception {
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            settingRepository.saveAndFlush(setting);
            language = LanguageResourceIT.createEntity(em);
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        em.persist(language);
        em.flush();
        setting.setLanguage(language);
        settingRepository.saveAndFlush(setting);
        Long languageId = language.getLanguageId();

        // Get all the settingList where language equals to languageId
        defaultSettingShouldBeFound("languageId.equals=" + languageId);

        // Get all the settingList where language equals to (languageId + 1)
        defaultSettingShouldNotBeFound("languageId.equals=" + (languageId + 1));
    }

    @Test
    @Transactional
    void getAllSettingsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            settingRepository.saveAndFlush(setting);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        setting.setEvent(event);
        settingRepository.saveAndFlush(setting);
        Long eventId = event.getEventId();

        // Get all the settingList where event equals to eventId
        defaultSettingShouldBeFound("eventId.equals=" + eventId);

        // Get all the settingList where event equals to (eventId + 1)
        defaultSettingShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSettingShouldBeFound(String filter) throws Exception {
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=settingId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].settingId").value(hasItem(setting.getSettingId().intValue())))
            .andExpect(jsonPath("$.[*].settingParentId").value(hasItem(DEFAULT_SETTING_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].settingType").value(hasItem(DEFAULT_SETTING_TYPE)))
            .andExpect(jsonPath("$.[*].settingNameClass").value(hasItem(DEFAULT_SETTING_NAME_CLASS)))
            .andExpect(jsonPath("$.[*].settingDataType").value(hasItem(DEFAULT_SETTING_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].settingDescription").value(hasItem(DEFAULT_SETTING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].settingValueString").value(hasItem(DEFAULT_SETTING_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].settingValueLong").value(hasItem(DEFAULT_SETTING_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].settingValueDate").value(hasItem(sameInstant(DEFAULT_SETTING_VALUE_DATE))))
            .andExpect(jsonPath("$.[*].settingValueBoolean").value(hasItem(DEFAULT_SETTING_VALUE_BOOLEAN.booleanValue())))
            .andExpect(jsonPath("$.[*].settingValueBlobContentType").value(hasItem(DEFAULT_SETTING_VALUE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].settingValueBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_SETTING_VALUE_BLOB))))
            .andExpect(jsonPath("$.[*].settingParams").value(hasItem(DEFAULT_SETTING_PARAMS)))
            .andExpect(jsonPath("$.[*].settingAttributs").value(hasItem(DEFAULT_SETTING_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].settingStat").value(hasItem(DEFAULT_SETTING_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=settingId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSettingShouldNotBeFound(String filter) throws Exception {
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=settingId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=settingId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting
        Setting updatedSetting = settingRepository.findById(setting.getSettingId()).get();
        // Disconnect from session so that the updates on updatedSetting are not directly saved in db
        em.detach(updatedSetting);
        updatedSetting
            .settingParentId(UPDATED_SETTING_PARENT_ID)
            .settingType(UPDATED_SETTING_TYPE)
            .settingNameClass(UPDATED_SETTING_NAME_CLASS)
            .settingDataType(UPDATED_SETTING_DATA_TYPE)
            .settingDescription(UPDATED_SETTING_DESCRIPTION)
            .settingValueString(UPDATED_SETTING_VALUE_STRING)
            .settingValueLong(UPDATED_SETTING_VALUE_LONG)
            .settingValueDate(UPDATED_SETTING_VALUE_DATE)
            .settingValueBoolean(UPDATED_SETTING_VALUE_BOOLEAN)
            .settingValueBlob(UPDATED_SETTING_VALUE_BLOB)
            .settingValueBlobContentType(UPDATED_SETTING_VALUE_BLOB_CONTENT_TYPE)
            .settingParams(UPDATED_SETTING_PARAMS)
            .settingAttributs(UPDATED_SETTING_ATTRIBUTS)
            .settingStat(UPDATED_SETTING_STAT);
        SettingDTO settingDTO = settingMapper.toDto(updatedSetting);

        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingDTO.getSettingId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getSettingParentId()).isEqualTo(UPDATED_SETTING_PARENT_ID);
        assertThat(testSetting.getSettingType()).isEqualTo(UPDATED_SETTING_TYPE);
        assertThat(testSetting.getSettingNameClass()).isEqualTo(UPDATED_SETTING_NAME_CLASS);
        assertThat(testSetting.getSettingDataType()).isEqualTo(UPDATED_SETTING_DATA_TYPE);
        assertThat(testSetting.getSettingDescription()).isEqualTo(UPDATED_SETTING_DESCRIPTION);
        assertThat(testSetting.getSettingValueString()).isEqualTo(UPDATED_SETTING_VALUE_STRING);
        assertThat(testSetting.getSettingValueLong()).isEqualTo(UPDATED_SETTING_VALUE_LONG);
        assertThat(testSetting.getSettingValueDate()).isEqualTo(UPDATED_SETTING_VALUE_DATE);
        assertThat(testSetting.getSettingValueBoolean()).isEqualTo(UPDATED_SETTING_VALUE_BOOLEAN);
        assertThat(testSetting.getSettingValueBlob()).isEqualTo(UPDATED_SETTING_VALUE_BLOB);
        assertThat(testSetting.getSettingValueBlobContentType()).isEqualTo(UPDATED_SETTING_VALUE_BLOB_CONTENT_TYPE);
        assertThat(testSetting.getSettingParams()).isEqualTo(UPDATED_SETTING_PARAMS);
        assertThat(testSetting.getSettingAttributs()).isEqualTo(UPDATED_SETTING_ATTRIBUTS);
        assertThat(testSetting.getSettingStat()).isEqualTo(UPDATED_SETTING_STAT);
    }

    @Test
    @Transactional
    void putNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        setting.setSettingId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingDTO.getSettingId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        setting.setSettingId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        setting.setSettingId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSettingWithPatch() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting using partial update
        Setting partialUpdatedSetting = new Setting();
        partialUpdatedSetting.setSettingId(setting.getSettingId());

        partialUpdatedSetting
            .settingDescription(UPDATED_SETTING_DESCRIPTION)
            .settingValueLong(UPDATED_SETTING_VALUE_LONG)
            .settingValueDate(UPDATED_SETTING_VALUE_DATE)
            .settingParams(UPDATED_SETTING_PARAMS)
            .settingAttributs(UPDATED_SETTING_ATTRIBUTS);

        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetting.getSettingId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSetting))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getSettingParentId()).isEqualTo(DEFAULT_SETTING_PARENT_ID);
        assertThat(testSetting.getSettingType()).isEqualTo(DEFAULT_SETTING_TYPE);
        assertThat(testSetting.getSettingNameClass()).isEqualTo(DEFAULT_SETTING_NAME_CLASS);
        assertThat(testSetting.getSettingDataType()).isEqualTo(DEFAULT_SETTING_DATA_TYPE);
        assertThat(testSetting.getSettingDescription()).isEqualTo(UPDATED_SETTING_DESCRIPTION);
        assertThat(testSetting.getSettingValueString()).isEqualTo(DEFAULT_SETTING_VALUE_STRING);
        assertThat(testSetting.getSettingValueLong()).isEqualTo(UPDATED_SETTING_VALUE_LONG);
        assertThat(testSetting.getSettingValueDate()).isEqualTo(UPDATED_SETTING_VALUE_DATE);
        assertThat(testSetting.getSettingValueBoolean()).isEqualTo(DEFAULT_SETTING_VALUE_BOOLEAN);
        assertThat(testSetting.getSettingValueBlob()).isEqualTo(DEFAULT_SETTING_VALUE_BLOB);
        assertThat(testSetting.getSettingValueBlobContentType()).isEqualTo(DEFAULT_SETTING_VALUE_BLOB_CONTENT_TYPE);
        assertThat(testSetting.getSettingParams()).isEqualTo(UPDATED_SETTING_PARAMS);
        assertThat(testSetting.getSettingAttributs()).isEqualTo(UPDATED_SETTING_ATTRIBUTS);
        assertThat(testSetting.getSettingStat()).isEqualTo(DEFAULT_SETTING_STAT);
    }

    @Test
    @Transactional
    void fullUpdateSettingWithPatch() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting using partial update
        Setting partialUpdatedSetting = new Setting();
        partialUpdatedSetting.setSettingId(setting.getSettingId());

        partialUpdatedSetting
            .settingParentId(UPDATED_SETTING_PARENT_ID)
            .settingType(UPDATED_SETTING_TYPE)
            .settingNameClass(UPDATED_SETTING_NAME_CLASS)
            .settingDataType(UPDATED_SETTING_DATA_TYPE)
            .settingDescription(UPDATED_SETTING_DESCRIPTION)
            .settingValueString(UPDATED_SETTING_VALUE_STRING)
            .settingValueLong(UPDATED_SETTING_VALUE_LONG)
            .settingValueDate(UPDATED_SETTING_VALUE_DATE)
            .settingValueBoolean(UPDATED_SETTING_VALUE_BOOLEAN)
            .settingValueBlob(UPDATED_SETTING_VALUE_BLOB)
            .settingValueBlobContentType(UPDATED_SETTING_VALUE_BLOB_CONTENT_TYPE)
            .settingParams(UPDATED_SETTING_PARAMS)
            .settingAttributs(UPDATED_SETTING_ATTRIBUTS)
            .settingStat(UPDATED_SETTING_STAT);

        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetting.getSettingId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSetting))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getSettingParentId()).isEqualTo(UPDATED_SETTING_PARENT_ID);
        assertThat(testSetting.getSettingType()).isEqualTo(UPDATED_SETTING_TYPE);
        assertThat(testSetting.getSettingNameClass()).isEqualTo(UPDATED_SETTING_NAME_CLASS);
        assertThat(testSetting.getSettingDataType()).isEqualTo(UPDATED_SETTING_DATA_TYPE);
        assertThat(testSetting.getSettingDescription()).isEqualTo(UPDATED_SETTING_DESCRIPTION);
        assertThat(testSetting.getSettingValueString()).isEqualTo(UPDATED_SETTING_VALUE_STRING);
        assertThat(testSetting.getSettingValueLong()).isEqualTo(UPDATED_SETTING_VALUE_LONG);
        assertThat(testSetting.getSettingValueDate()).isEqualTo(UPDATED_SETTING_VALUE_DATE);
        assertThat(testSetting.getSettingValueBoolean()).isEqualTo(UPDATED_SETTING_VALUE_BOOLEAN);
        assertThat(testSetting.getSettingValueBlob()).isEqualTo(UPDATED_SETTING_VALUE_BLOB);
        assertThat(testSetting.getSettingValueBlobContentType()).isEqualTo(UPDATED_SETTING_VALUE_BLOB_CONTENT_TYPE);
        assertThat(testSetting.getSettingParams()).isEqualTo(UPDATED_SETTING_PARAMS);
        assertThat(testSetting.getSettingAttributs()).isEqualTo(UPDATED_SETTING_ATTRIBUTS);
        assertThat(testSetting.getSettingStat()).isEqualTo(UPDATED_SETTING_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        setting.setSettingId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, settingDTO.getSettingId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        setting.setSettingId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        setting.setSettingId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeDelete = settingRepository.findAll().size();

        // Delete the setting
        restSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, setting.getSettingId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
