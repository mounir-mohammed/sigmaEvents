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
import ma.sig.events.domain.DayPassInfo;
import ma.sig.events.domain.Event;
import ma.sig.events.repository.DayPassInfoRepository;
import ma.sig.events.service.criteria.DayPassInfoCriteria;
import ma.sig.events.service.dto.DayPassInfoDTO;
import ma.sig.events.service.mapper.DayPassInfoMapper;
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
 * Integration tests for the {@link DayPassInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DayPassInfoResourceIT {

    private static final String DEFAULT_DAY_PASS_INFO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DAY_PASS_INFO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DAY_PASS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DAY_PASS_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DAY_PASS_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DAY_PASS_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DAY_PASS_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DAY_PASS_LOGO_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_DAY_PASS_INFO_CREATION_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_DAY_PASS_INFO_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DAY_PASS_INFO_CREATION_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final ZonedDateTime DEFAULT_DAY_PASS_INFO_UPDATE_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_DAY_PASS_INFO_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DAY_PASS_INFO_UPDATE_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final String DEFAULT_DAY_PASS_INFO_CREATED_BYUSER = "AAAAAAAAAA";
    private static final String UPDATED_DAY_PASS_INFO_CREATED_BYUSER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DAY_PASS_INFO_DATE_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAY_PASS_INFO_DATE_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DAY_PASS_INFO_DATE_START = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final ZonedDateTime DEFAULT_DAY_PASS_INFO_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAY_PASS_INFO_DATE_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DAY_PASS_INFO_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_DAY_PASS_INFO_NUMBER = 1L;
    private static final Long UPDATED_DAY_PASS_INFO_NUMBER = 2L;
    private static final Long SMALLER_DAY_PASS_INFO_NUMBER = 1L - 1L;

    private static final String DEFAULT_DAY_PASS_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_DAY_PASS_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_DAY_PASS_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_DAY_PASS_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DAY_PASS_INFO_STAT = false;
    private static final Boolean UPDATED_DAY_PASS_INFO_STAT = true;

    private static final String ENTITY_API_URL = "/api/day-pass-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{dayPassInfoId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DayPassInfoRepository dayPassInfoRepository;

    @Autowired
    private DayPassInfoMapper dayPassInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDayPassInfoMockMvc;

    private DayPassInfo dayPassInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayPassInfo createEntity(EntityManager em) {
        DayPassInfo dayPassInfo = new DayPassInfo()
            .dayPassInfoName(DEFAULT_DAY_PASS_INFO_NAME)
            .dayPassDescription(DEFAULT_DAY_PASS_DESCRIPTION)
            .dayPassLogo(DEFAULT_DAY_PASS_LOGO)
            .dayPassLogoContentType(DEFAULT_DAY_PASS_LOGO_CONTENT_TYPE)
            .dayPassInfoCreationDate(DEFAULT_DAY_PASS_INFO_CREATION_DATE)
            .dayPassInfoUpdateDate(DEFAULT_DAY_PASS_INFO_UPDATE_DATE)
            .dayPassInfoCreatedByuser(DEFAULT_DAY_PASS_INFO_CREATED_BYUSER)
            .dayPassInfoDateStart(DEFAULT_DAY_PASS_INFO_DATE_START)
            .dayPassInfoDateEnd(DEFAULT_DAY_PASS_INFO_DATE_END)
            .dayPassInfoNumber(DEFAULT_DAY_PASS_INFO_NUMBER)
            .dayPassParams(DEFAULT_DAY_PASS_PARAMS)
            .dayPassAttributs(DEFAULT_DAY_PASS_ATTRIBUTS)
            .dayPassInfoStat(DEFAULT_DAY_PASS_INFO_STAT);
        return dayPassInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayPassInfo createUpdatedEntity(EntityManager em) {
        DayPassInfo dayPassInfo = new DayPassInfo()
            .dayPassInfoName(UPDATED_DAY_PASS_INFO_NAME)
            .dayPassDescription(UPDATED_DAY_PASS_DESCRIPTION)
            .dayPassLogo(UPDATED_DAY_PASS_LOGO)
            .dayPassLogoContentType(UPDATED_DAY_PASS_LOGO_CONTENT_TYPE)
            .dayPassInfoCreationDate(UPDATED_DAY_PASS_INFO_CREATION_DATE)
            .dayPassInfoUpdateDate(UPDATED_DAY_PASS_INFO_UPDATE_DATE)
            .dayPassInfoCreatedByuser(UPDATED_DAY_PASS_INFO_CREATED_BYUSER)
            .dayPassInfoDateStart(UPDATED_DAY_PASS_INFO_DATE_START)
            .dayPassInfoDateEnd(UPDATED_DAY_PASS_INFO_DATE_END)
            .dayPassInfoNumber(UPDATED_DAY_PASS_INFO_NUMBER)
            .dayPassParams(UPDATED_DAY_PASS_PARAMS)
            .dayPassAttributs(UPDATED_DAY_PASS_ATTRIBUTS)
            .dayPassInfoStat(UPDATED_DAY_PASS_INFO_STAT);
        return dayPassInfo;
    }

    @BeforeEach
    public void initTest() {
        dayPassInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createDayPassInfo() throws Exception {
        int databaseSizeBeforeCreate = dayPassInfoRepository.findAll().size();
        // Create the DayPassInfo
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);
        restDayPassInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeCreate + 1);
        DayPassInfo testDayPassInfo = dayPassInfoList.get(dayPassInfoList.size() - 1);
        assertThat(testDayPassInfo.getDayPassInfoName()).isEqualTo(DEFAULT_DAY_PASS_INFO_NAME);
        assertThat(testDayPassInfo.getDayPassDescription()).isEqualTo(DEFAULT_DAY_PASS_DESCRIPTION);
        assertThat(testDayPassInfo.getDayPassLogo()).isEqualTo(DEFAULT_DAY_PASS_LOGO);
        assertThat(testDayPassInfo.getDayPassLogoContentType()).isEqualTo(DEFAULT_DAY_PASS_LOGO_CONTENT_TYPE);
        assertThat(testDayPassInfo.getDayPassInfoCreationDate()).isEqualTo(DEFAULT_DAY_PASS_INFO_CREATION_DATE);
        assertThat(testDayPassInfo.getDayPassInfoUpdateDate()).isEqualTo(DEFAULT_DAY_PASS_INFO_UPDATE_DATE);
        assertThat(testDayPassInfo.getDayPassInfoCreatedByuser()).isEqualTo(DEFAULT_DAY_PASS_INFO_CREATED_BYUSER);
        assertThat(testDayPassInfo.getDayPassInfoDateStart()).isEqualTo(DEFAULT_DAY_PASS_INFO_DATE_START);
        assertThat(testDayPassInfo.getDayPassInfoDateEnd()).isEqualTo(DEFAULT_DAY_PASS_INFO_DATE_END);
        assertThat(testDayPassInfo.getDayPassInfoNumber()).isEqualTo(DEFAULT_DAY_PASS_INFO_NUMBER);
        assertThat(testDayPassInfo.getDayPassParams()).isEqualTo(DEFAULT_DAY_PASS_PARAMS);
        assertThat(testDayPassInfo.getDayPassAttributs()).isEqualTo(DEFAULT_DAY_PASS_ATTRIBUTS);
        assertThat(testDayPassInfo.getDayPassInfoStat()).isEqualTo(DEFAULT_DAY_PASS_INFO_STAT);
    }

    @Test
    @Transactional
    void createDayPassInfoWithExistingId() throws Exception {
        // Create the DayPassInfo with an existing ID
        dayPassInfo.setDayPassInfoId(1L);
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);

        int databaseSizeBeforeCreate = dayPassInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayPassInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDayPassInfos() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList
        restDayPassInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=dayPassInfoId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].dayPassInfoId").value(hasItem(dayPassInfo.getDayPassInfoId().intValue())))
            .andExpect(jsonPath("$.[*].dayPassInfoName").value(hasItem(DEFAULT_DAY_PASS_INFO_NAME)))
            .andExpect(jsonPath("$.[*].dayPassDescription").value(hasItem(DEFAULT_DAY_PASS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dayPassLogoContentType").value(hasItem(DEFAULT_DAY_PASS_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dayPassLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_DAY_PASS_LOGO))))
            .andExpect(jsonPath("$.[*].dayPassInfoCreationDate").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].dayPassInfoUpdateDate").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].dayPassInfoCreatedByuser").value(hasItem(DEFAULT_DAY_PASS_INFO_CREATED_BYUSER)))
            .andExpect(jsonPath("$.[*].dayPassInfoDateStart").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_DATE_START))))
            .andExpect(jsonPath("$.[*].dayPassInfoDateEnd").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_DATE_END))))
            .andExpect(jsonPath("$.[*].dayPassInfoNumber").value(hasItem(DEFAULT_DAY_PASS_INFO_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].dayPassParams").value(hasItem(DEFAULT_DAY_PASS_PARAMS)))
            .andExpect(jsonPath("$.[*].dayPassAttributs").value(hasItem(DEFAULT_DAY_PASS_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].dayPassInfoStat").value(hasItem(DEFAULT_DAY_PASS_INFO_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getDayPassInfo() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get the dayPassInfo
        restDayPassInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, dayPassInfo.getDayPassInfoId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.dayPassInfoId").value(dayPassInfo.getDayPassInfoId().intValue()))
            .andExpect(jsonPath("$.dayPassInfoName").value(DEFAULT_DAY_PASS_INFO_NAME))
            .andExpect(jsonPath("$.dayPassDescription").value(DEFAULT_DAY_PASS_DESCRIPTION))
            .andExpect(jsonPath("$.dayPassLogoContentType").value(DEFAULT_DAY_PASS_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.dayPassLogo").value(Base64Utils.encodeToString(DEFAULT_DAY_PASS_LOGO)))
            .andExpect(jsonPath("$.dayPassInfoCreationDate").value(sameInstant(DEFAULT_DAY_PASS_INFO_CREATION_DATE)))
            .andExpect(jsonPath("$.dayPassInfoUpdateDate").value(sameInstant(DEFAULT_DAY_PASS_INFO_UPDATE_DATE)))
            .andExpect(jsonPath("$.dayPassInfoCreatedByuser").value(DEFAULT_DAY_PASS_INFO_CREATED_BYUSER))
            .andExpect(jsonPath("$.dayPassInfoDateStart").value(sameInstant(DEFAULT_DAY_PASS_INFO_DATE_START)))
            .andExpect(jsonPath("$.dayPassInfoDateEnd").value(sameInstant(DEFAULT_DAY_PASS_INFO_DATE_END)))
            .andExpect(jsonPath("$.dayPassInfoNumber").value(DEFAULT_DAY_PASS_INFO_NUMBER.intValue()))
            .andExpect(jsonPath("$.dayPassParams").value(DEFAULT_DAY_PASS_PARAMS))
            .andExpect(jsonPath("$.dayPassAttributs").value(DEFAULT_DAY_PASS_ATTRIBUTS))
            .andExpect(jsonPath("$.dayPassInfoStat").value(DEFAULT_DAY_PASS_INFO_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getDayPassInfosByIdFiltering() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        Long id = dayPassInfo.getDayPassInfoId();

        defaultDayPassInfoShouldBeFound("dayPassInfoId.equals=" + id);
        defaultDayPassInfoShouldNotBeFound("dayPassInfoId.notEquals=" + id);

        defaultDayPassInfoShouldBeFound("dayPassInfoId.greaterThanOrEqual=" + id);
        defaultDayPassInfoShouldNotBeFound("dayPassInfoId.greaterThan=" + id);

        defaultDayPassInfoShouldBeFound("dayPassInfoId.lessThanOrEqual=" + id);
        defaultDayPassInfoShouldNotBeFound("dayPassInfoId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoName equals to DEFAULT_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldBeFound("dayPassInfoName.equals=" + DEFAULT_DAY_PASS_INFO_NAME);

        // Get all the dayPassInfoList where dayPassInfoName equals to UPDATED_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldNotBeFound("dayPassInfoName.equals=" + UPDATED_DAY_PASS_INFO_NAME);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNameIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoName in DEFAULT_DAY_PASS_INFO_NAME or UPDATED_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldBeFound("dayPassInfoName.in=" + DEFAULT_DAY_PASS_INFO_NAME + "," + UPDATED_DAY_PASS_INFO_NAME);

        // Get all the dayPassInfoList where dayPassInfoName equals to UPDATED_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldNotBeFound("dayPassInfoName.in=" + UPDATED_DAY_PASS_INFO_NAME);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoName is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoName.specified=true");

        // Get all the dayPassInfoList where dayPassInfoName is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoName.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNameContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoName contains DEFAULT_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldBeFound("dayPassInfoName.contains=" + DEFAULT_DAY_PASS_INFO_NAME);

        // Get all the dayPassInfoList where dayPassInfoName contains UPDATED_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldNotBeFound("dayPassInfoName.contains=" + UPDATED_DAY_PASS_INFO_NAME);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNameNotContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoName does not contain DEFAULT_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldNotBeFound("dayPassInfoName.doesNotContain=" + DEFAULT_DAY_PASS_INFO_NAME);

        // Get all the dayPassInfoList where dayPassInfoName does not contain UPDATED_DAY_PASS_INFO_NAME
        defaultDayPassInfoShouldBeFound("dayPassInfoName.doesNotContain=" + UPDATED_DAY_PASS_INFO_NAME);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassDescription equals to DEFAULT_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldBeFound("dayPassDescription.equals=" + DEFAULT_DAY_PASS_DESCRIPTION);

        // Get all the dayPassInfoList where dayPassDescription equals to UPDATED_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldNotBeFound("dayPassDescription.equals=" + UPDATED_DAY_PASS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassDescription in DEFAULT_DAY_PASS_DESCRIPTION or UPDATED_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldBeFound("dayPassDescription.in=" + DEFAULT_DAY_PASS_DESCRIPTION + "," + UPDATED_DAY_PASS_DESCRIPTION);

        // Get all the dayPassInfoList where dayPassDescription equals to UPDATED_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldNotBeFound("dayPassDescription.in=" + UPDATED_DAY_PASS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassDescription is not null
        defaultDayPassInfoShouldBeFound("dayPassDescription.specified=true");

        // Get all the dayPassInfoList where dayPassDescription is null
        defaultDayPassInfoShouldNotBeFound("dayPassDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassDescriptionContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassDescription contains DEFAULT_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldBeFound("dayPassDescription.contains=" + DEFAULT_DAY_PASS_DESCRIPTION);

        // Get all the dayPassInfoList where dayPassDescription contains UPDATED_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldNotBeFound("dayPassDescription.contains=" + UPDATED_DAY_PASS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassDescription does not contain DEFAULT_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldNotBeFound("dayPassDescription.doesNotContain=" + DEFAULT_DAY_PASS_DESCRIPTION);

        // Get all the dayPassInfoList where dayPassDescription does not contain UPDATED_DAY_PASS_DESCRIPTION
        defaultDayPassInfoShouldBeFound("dayPassDescription.doesNotContain=" + UPDATED_DAY_PASS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreationDate equals to DEFAULT_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoCreationDate.equals=" + DEFAULT_DAY_PASS_INFO_CREATION_DATE);

        // Get all the dayPassInfoList where dayPassInfoCreationDate equals to UPDATED_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreationDate.equals=" + UPDATED_DAY_PASS_INFO_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreationDate in DEFAULT_DAY_PASS_INFO_CREATION_DATE or UPDATED_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldBeFound(
            "dayPassInfoCreationDate.in=" + DEFAULT_DAY_PASS_INFO_CREATION_DATE + "," + UPDATED_DAY_PASS_INFO_CREATION_DATE
        );

        // Get all the dayPassInfoList where dayPassInfoCreationDate equals to UPDATED_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreationDate.in=" + UPDATED_DAY_PASS_INFO_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoCreationDate.specified=true");

        // Get all the dayPassInfoList where dayPassInfoCreationDate is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is greater than or equal to DEFAULT_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoCreationDate.greaterThanOrEqual=" + DEFAULT_DAY_PASS_INFO_CREATION_DATE);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is greater than or equal to UPDATED_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreationDate.greaterThanOrEqual=" + UPDATED_DAY_PASS_INFO_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is less than or equal to DEFAULT_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoCreationDate.lessThanOrEqual=" + DEFAULT_DAY_PASS_INFO_CREATION_DATE);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is less than or equal to SMALLER_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreationDate.lessThanOrEqual=" + SMALLER_DAY_PASS_INFO_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is less than DEFAULT_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreationDate.lessThan=" + DEFAULT_DAY_PASS_INFO_CREATION_DATE);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is less than UPDATED_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoCreationDate.lessThan=" + UPDATED_DAY_PASS_INFO_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is greater than DEFAULT_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreationDate.greaterThan=" + DEFAULT_DAY_PASS_INFO_CREATION_DATE);

        // Get all the dayPassInfoList where dayPassInfoCreationDate is greater than SMALLER_DAY_PASS_INFO_CREATION_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoCreationDate.greaterThan=" + SMALLER_DAY_PASS_INFO_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate equals to DEFAULT_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoUpdateDate.equals=" + DEFAULT_DAY_PASS_INFO_UPDATE_DATE);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate equals to UPDATED_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoUpdateDate.equals=" + UPDATED_DAY_PASS_INFO_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate in DEFAULT_DAY_PASS_INFO_UPDATE_DATE or UPDATED_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldBeFound(
            "dayPassInfoUpdateDate.in=" + DEFAULT_DAY_PASS_INFO_UPDATE_DATE + "," + UPDATED_DAY_PASS_INFO_UPDATE_DATE
        );

        // Get all the dayPassInfoList where dayPassInfoUpdateDate equals to UPDATED_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoUpdateDate.in=" + UPDATED_DAY_PASS_INFO_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoUpdateDate.specified=true");

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoUpdateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoUpdateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is greater than or equal to DEFAULT_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoUpdateDate.greaterThanOrEqual=" + DEFAULT_DAY_PASS_INFO_UPDATE_DATE);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is greater than or equal to UPDATED_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoUpdateDate.greaterThanOrEqual=" + UPDATED_DAY_PASS_INFO_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoUpdateDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is less than or equal to DEFAULT_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoUpdateDate.lessThanOrEqual=" + DEFAULT_DAY_PASS_INFO_UPDATE_DATE);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is less than or equal to SMALLER_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoUpdateDate.lessThanOrEqual=" + SMALLER_DAY_PASS_INFO_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoUpdateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is less than DEFAULT_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoUpdateDate.lessThan=" + DEFAULT_DAY_PASS_INFO_UPDATE_DATE);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is less than UPDATED_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoUpdateDate.lessThan=" + UPDATED_DAY_PASS_INFO_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoUpdateDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is greater than DEFAULT_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldNotBeFound("dayPassInfoUpdateDate.greaterThan=" + DEFAULT_DAY_PASS_INFO_UPDATE_DATE);

        // Get all the dayPassInfoList where dayPassInfoUpdateDate is greater than SMALLER_DAY_PASS_INFO_UPDATE_DATE
        defaultDayPassInfoShouldBeFound("dayPassInfoUpdateDate.greaterThan=" + SMALLER_DAY_PASS_INFO_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreatedByuserIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser equals to DEFAULT_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldBeFound("dayPassInfoCreatedByuser.equals=" + DEFAULT_DAY_PASS_INFO_CREATED_BYUSER);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser equals to UPDATED_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreatedByuser.equals=" + UPDATED_DAY_PASS_INFO_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreatedByuserIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser in DEFAULT_DAY_PASS_INFO_CREATED_BYUSER or UPDATED_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldBeFound(
            "dayPassInfoCreatedByuser.in=" + DEFAULT_DAY_PASS_INFO_CREATED_BYUSER + "," + UPDATED_DAY_PASS_INFO_CREATED_BYUSER
        );

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser equals to UPDATED_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreatedByuser.in=" + UPDATED_DAY_PASS_INFO_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreatedByuserIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoCreatedByuser.specified=true");

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreatedByuser.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreatedByuserContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser contains DEFAULT_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldBeFound("dayPassInfoCreatedByuser.contains=" + DEFAULT_DAY_PASS_INFO_CREATED_BYUSER);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser contains UPDATED_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreatedByuser.contains=" + UPDATED_DAY_PASS_INFO_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoCreatedByuserNotContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser does not contain DEFAULT_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoCreatedByuser.doesNotContain=" + DEFAULT_DAY_PASS_INFO_CREATED_BYUSER);

        // Get all the dayPassInfoList where dayPassInfoCreatedByuser does not contain UPDATED_DAY_PASS_INFO_CREATED_BYUSER
        defaultDayPassInfoShouldBeFound("dayPassInfoCreatedByuser.doesNotContain=" + UPDATED_DAY_PASS_INFO_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateStartIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateStart equals to DEFAULT_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldBeFound("dayPassInfoDateStart.equals=" + DEFAULT_DAY_PASS_INFO_DATE_START);

        // Get all the dayPassInfoList where dayPassInfoDateStart equals to UPDATED_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateStart.equals=" + UPDATED_DAY_PASS_INFO_DATE_START);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateStartIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateStart in DEFAULT_DAY_PASS_INFO_DATE_START or UPDATED_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldBeFound(
            "dayPassInfoDateStart.in=" + DEFAULT_DAY_PASS_INFO_DATE_START + "," + UPDATED_DAY_PASS_INFO_DATE_START
        );

        // Get all the dayPassInfoList where dayPassInfoDateStart equals to UPDATED_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateStart.in=" + UPDATED_DAY_PASS_INFO_DATE_START);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateStart is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoDateStart.specified=true");

        // Get all the dayPassInfoList where dayPassInfoDateStart is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateStart.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateStart is greater than or equal to DEFAULT_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldBeFound("dayPassInfoDateStart.greaterThanOrEqual=" + DEFAULT_DAY_PASS_INFO_DATE_START);

        // Get all the dayPassInfoList where dayPassInfoDateStart is greater than or equal to UPDATED_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateStart.greaterThanOrEqual=" + UPDATED_DAY_PASS_INFO_DATE_START);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateStart is less than or equal to DEFAULT_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldBeFound("dayPassInfoDateStart.lessThanOrEqual=" + DEFAULT_DAY_PASS_INFO_DATE_START);

        // Get all the dayPassInfoList where dayPassInfoDateStart is less than or equal to SMALLER_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateStart.lessThanOrEqual=" + SMALLER_DAY_PASS_INFO_DATE_START);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateStartIsLessThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateStart is less than DEFAULT_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateStart.lessThan=" + DEFAULT_DAY_PASS_INFO_DATE_START);

        // Get all the dayPassInfoList where dayPassInfoDateStart is less than UPDATED_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldBeFound("dayPassInfoDateStart.lessThan=" + UPDATED_DAY_PASS_INFO_DATE_START);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateStart is greater than DEFAULT_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateStart.greaterThan=" + DEFAULT_DAY_PASS_INFO_DATE_START);

        // Get all the dayPassInfoList where dayPassInfoDateStart is greater than SMALLER_DAY_PASS_INFO_DATE_START
        defaultDayPassInfoShouldBeFound("dayPassInfoDateStart.greaterThan=" + SMALLER_DAY_PASS_INFO_DATE_START);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateEndIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateEnd equals to DEFAULT_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldBeFound("dayPassInfoDateEnd.equals=" + DEFAULT_DAY_PASS_INFO_DATE_END);

        // Get all the dayPassInfoList where dayPassInfoDateEnd equals to UPDATED_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateEnd.equals=" + UPDATED_DAY_PASS_INFO_DATE_END);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateEndIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateEnd in DEFAULT_DAY_PASS_INFO_DATE_END or UPDATED_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldBeFound("dayPassInfoDateEnd.in=" + DEFAULT_DAY_PASS_INFO_DATE_END + "," + UPDATED_DAY_PASS_INFO_DATE_END);

        // Get all the dayPassInfoList where dayPassInfoDateEnd equals to UPDATED_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateEnd.in=" + UPDATED_DAY_PASS_INFO_DATE_END);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoDateEnd.specified=true");

        // Get all the dayPassInfoList where dayPassInfoDateEnd is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateEnd.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is greater than or equal to DEFAULT_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldBeFound("dayPassInfoDateEnd.greaterThanOrEqual=" + DEFAULT_DAY_PASS_INFO_DATE_END);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is greater than or equal to UPDATED_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateEnd.greaterThanOrEqual=" + UPDATED_DAY_PASS_INFO_DATE_END);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is less than or equal to DEFAULT_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldBeFound("dayPassInfoDateEnd.lessThanOrEqual=" + DEFAULT_DAY_PASS_INFO_DATE_END);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is less than or equal to SMALLER_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateEnd.lessThanOrEqual=" + SMALLER_DAY_PASS_INFO_DATE_END);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateEndIsLessThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is less than DEFAULT_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateEnd.lessThan=" + DEFAULT_DAY_PASS_INFO_DATE_END);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is less than UPDATED_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldBeFound("dayPassInfoDateEnd.lessThan=" + UPDATED_DAY_PASS_INFO_DATE_END);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoDateEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is greater than DEFAULT_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldNotBeFound("dayPassInfoDateEnd.greaterThan=" + DEFAULT_DAY_PASS_INFO_DATE_END);

        // Get all the dayPassInfoList where dayPassInfoDateEnd is greater than SMALLER_DAY_PASS_INFO_DATE_END
        defaultDayPassInfoShouldBeFound("dayPassInfoDateEnd.greaterThan=" + SMALLER_DAY_PASS_INFO_DATE_END);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoNumber equals to DEFAULT_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldBeFound("dayPassInfoNumber.equals=" + DEFAULT_DAY_PASS_INFO_NUMBER);

        // Get all the dayPassInfoList where dayPassInfoNumber equals to UPDATED_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoNumber.equals=" + UPDATED_DAY_PASS_INFO_NUMBER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNumberIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoNumber in DEFAULT_DAY_PASS_INFO_NUMBER or UPDATED_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldBeFound("dayPassInfoNumber.in=" + DEFAULT_DAY_PASS_INFO_NUMBER + "," + UPDATED_DAY_PASS_INFO_NUMBER);

        // Get all the dayPassInfoList where dayPassInfoNumber equals to UPDATED_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoNumber.in=" + UPDATED_DAY_PASS_INFO_NUMBER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoNumber is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoNumber.specified=true");

        // Get all the dayPassInfoList where dayPassInfoNumber is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoNumber is greater than or equal to DEFAULT_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldBeFound("dayPassInfoNumber.greaterThanOrEqual=" + DEFAULT_DAY_PASS_INFO_NUMBER);

        // Get all the dayPassInfoList where dayPassInfoNumber is greater than or equal to UPDATED_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoNumber.greaterThanOrEqual=" + UPDATED_DAY_PASS_INFO_NUMBER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoNumber is less than or equal to DEFAULT_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldBeFound("dayPassInfoNumber.lessThanOrEqual=" + DEFAULT_DAY_PASS_INFO_NUMBER);

        // Get all the dayPassInfoList where dayPassInfoNumber is less than or equal to SMALLER_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoNumber.lessThanOrEqual=" + SMALLER_DAY_PASS_INFO_NUMBER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoNumber is less than DEFAULT_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoNumber.lessThan=" + DEFAULT_DAY_PASS_INFO_NUMBER);

        // Get all the dayPassInfoList where dayPassInfoNumber is less than UPDATED_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldBeFound("dayPassInfoNumber.lessThan=" + UPDATED_DAY_PASS_INFO_NUMBER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoNumber is greater than DEFAULT_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldNotBeFound("dayPassInfoNumber.greaterThan=" + DEFAULT_DAY_PASS_INFO_NUMBER);

        // Get all the dayPassInfoList where dayPassInfoNumber is greater than SMALLER_DAY_PASS_INFO_NUMBER
        defaultDayPassInfoShouldBeFound("dayPassInfoNumber.greaterThan=" + SMALLER_DAY_PASS_INFO_NUMBER);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassParams equals to DEFAULT_DAY_PASS_PARAMS
        defaultDayPassInfoShouldBeFound("dayPassParams.equals=" + DEFAULT_DAY_PASS_PARAMS);

        // Get all the dayPassInfoList where dayPassParams equals to UPDATED_DAY_PASS_PARAMS
        defaultDayPassInfoShouldNotBeFound("dayPassParams.equals=" + UPDATED_DAY_PASS_PARAMS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassParamsIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassParams in DEFAULT_DAY_PASS_PARAMS or UPDATED_DAY_PASS_PARAMS
        defaultDayPassInfoShouldBeFound("dayPassParams.in=" + DEFAULT_DAY_PASS_PARAMS + "," + UPDATED_DAY_PASS_PARAMS);

        // Get all the dayPassInfoList where dayPassParams equals to UPDATED_DAY_PASS_PARAMS
        defaultDayPassInfoShouldNotBeFound("dayPassParams.in=" + UPDATED_DAY_PASS_PARAMS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassParams is not null
        defaultDayPassInfoShouldBeFound("dayPassParams.specified=true");

        // Get all the dayPassInfoList where dayPassParams is null
        defaultDayPassInfoShouldNotBeFound("dayPassParams.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassParamsContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassParams contains DEFAULT_DAY_PASS_PARAMS
        defaultDayPassInfoShouldBeFound("dayPassParams.contains=" + DEFAULT_DAY_PASS_PARAMS);

        // Get all the dayPassInfoList where dayPassParams contains UPDATED_DAY_PASS_PARAMS
        defaultDayPassInfoShouldNotBeFound("dayPassParams.contains=" + UPDATED_DAY_PASS_PARAMS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassParamsNotContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassParams does not contain DEFAULT_DAY_PASS_PARAMS
        defaultDayPassInfoShouldNotBeFound("dayPassParams.doesNotContain=" + DEFAULT_DAY_PASS_PARAMS);

        // Get all the dayPassInfoList where dayPassParams does not contain UPDATED_DAY_PASS_PARAMS
        defaultDayPassInfoShouldBeFound("dayPassParams.doesNotContain=" + UPDATED_DAY_PASS_PARAMS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassAttributs equals to DEFAULT_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldBeFound("dayPassAttributs.equals=" + DEFAULT_DAY_PASS_ATTRIBUTS);

        // Get all the dayPassInfoList where dayPassAttributs equals to UPDATED_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldNotBeFound("dayPassAttributs.equals=" + UPDATED_DAY_PASS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassAttributs in DEFAULT_DAY_PASS_ATTRIBUTS or UPDATED_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldBeFound("dayPassAttributs.in=" + DEFAULT_DAY_PASS_ATTRIBUTS + "," + UPDATED_DAY_PASS_ATTRIBUTS);

        // Get all the dayPassInfoList where dayPassAttributs equals to UPDATED_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldNotBeFound("dayPassAttributs.in=" + UPDATED_DAY_PASS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassAttributs is not null
        defaultDayPassInfoShouldBeFound("dayPassAttributs.specified=true");

        // Get all the dayPassInfoList where dayPassAttributs is null
        defaultDayPassInfoShouldNotBeFound("dayPassAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassAttributsContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassAttributs contains DEFAULT_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldBeFound("dayPassAttributs.contains=" + DEFAULT_DAY_PASS_ATTRIBUTS);

        // Get all the dayPassInfoList where dayPassAttributs contains UPDATED_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldNotBeFound("dayPassAttributs.contains=" + UPDATED_DAY_PASS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassAttributs does not contain DEFAULT_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldNotBeFound("dayPassAttributs.doesNotContain=" + DEFAULT_DAY_PASS_ATTRIBUTS);

        // Get all the dayPassInfoList where dayPassAttributs does not contain UPDATED_DAY_PASS_ATTRIBUTS
        defaultDayPassInfoShouldBeFound("dayPassAttributs.doesNotContain=" + UPDATED_DAY_PASS_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoStatIsEqualToSomething() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoStat equals to DEFAULT_DAY_PASS_INFO_STAT
        defaultDayPassInfoShouldBeFound("dayPassInfoStat.equals=" + DEFAULT_DAY_PASS_INFO_STAT);

        // Get all the dayPassInfoList where dayPassInfoStat equals to UPDATED_DAY_PASS_INFO_STAT
        defaultDayPassInfoShouldNotBeFound("dayPassInfoStat.equals=" + UPDATED_DAY_PASS_INFO_STAT);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoStatIsInShouldWork() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoStat in DEFAULT_DAY_PASS_INFO_STAT or UPDATED_DAY_PASS_INFO_STAT
        defaultDayPassInfoShouldBeFound("dayPassInfoStat.in=" + DEFAULT_DAY_PASS_INFO_STAT + "," + UPDATED_DAY_PASS_INFO_STAT);

        // Get all the dayPassInfoList where dayPassInfoStat equals to UPDATED_DAY_PASS_INFO_STAT
        defaultDayPassInfoShouldNotBeFound("dayPassInfoStat.in=" + UPDATED_DAY_PASS_INFO_STAT);
    }

    @Test
    @Transactional
    void getAllDayPassInfosByDayPassInfoStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        // Get all the dayPassInfoList where dayPassInfoStat is not null
        defaultDayPassInfoShouldBeFound("dayPassInfoStat.specified=true");

        // Get all the dayPassInfoList where dayPassInfoStat is null
        defaultDayPassInfoShouldNotBeFound("dayPassInfoStat.specified=false");
    }

    @Test
    @Transactional
    void getAllDayPassInfosByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            dayPassInfoRepository.saveAndFlush(dayPassInfo);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        dayPassInfo.addAccreditation(accreditation);
        dayPassInfoRepository.saveAndFlush(dayPassInfo);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the dayPassInfoList where accreditation equals to accreditationId
        defaultDayPassInfoShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the dayPassInfoList where accreditation equals to (accreditationId + 1)
        defaultDayPassInfoShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllDayPassInfosByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            dayPassInfoRepository.saveAndFlush(dayPassInfo);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        dayPassInfo.setEvent(event);
        dayPassInfoRepository.saveAndFlush(dayPassInfo);
        Long eventId = event.getEventId();

        // Get all the dayPassInfoList where event equals to eventId
        defaultDayPassInfoShouldBeFound("eventId.equals=" + eventId);

        // Get all the dayPassInfoList where event equals to (eventId + 1)
        defaultDayPassInfoShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDayPassInfoShouldBeFound(String filter) throws Exception {
        restDayPassInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=dayPassInfoId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].dayPassInfoId").value(hasItem(dayPassInfo.getDayPassInfoId().intValue())))
            .andExpect(jsonPath("$.[*].dayPassInfoName").value(hasItem(DEFAULT_DAY_PASS_INFO_NAME)))
            .andExpect(jsonPath("$.[*].dayPassDescription").value(hasItem(DEFAULT_DAY_PASS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dayPassLogoContentType").value(hasItem(DEFAULT_DAY_PASS_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dayPassLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_DAY_PASS_LOGO))))
            .andExpect(jsonPath("$.[*].dayPassInfoCreationDate").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].dayPassInfoUpdateDate").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].dayPassInfoCreatedByuser").value(hasItem(DEFAULT_DAY_PASS_INFO_CREATED_BYUSER)))
            .andExpect(jsonPath("$.[*].dayPassInfoDateStart").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_DATE_START))))
            .andExpect(jsonPath("$.[*].dayPassInfoDateEnd").value(hasItem(sameInstant(DEFAULT_DAY_PASS_INFO_DATE_END))))
            .andExpect(jsonPath("$.[*].dayPassInfoNumber").value(hasItem(DEFAULT_DAY_PASS_INFO_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].dayPassParams").value(hasItem(DEFAULT_DAY_PASS_PARAMS)))
            .andExpect(jsonPath("$.[*].dayPassAttributs").value(hasItem(DEFAULT_DAY_PASS_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].dayPassInfoStat").value(hasItem(DEFAULT_DAY_PASS_INFO_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restDayPassInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=dayPassInfoId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDayPassInfoShouldNotBeFound(String filter) throws Exception {
        restDayPassInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=dayPassInfoId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDayPassInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=dayPassInfoId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDayPassInfo() throws Exception {
        // Get the dayPassInfo
        restDayPassInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDayPassInfo() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();

        // Update the dayPassInfo
        DayPassInfo updatedDayPassInfo = dayPassInfoRepository.findById(dayPassInfo.getDayPassInfoId()).get();
        // Disconnect from session so that the updates on updatedDayPassInfo are not directly saved in db
        em.detach(updatedDayPassInfo);
        updatedDayPassInfo
            .dayPassInfoName(UPDATED_DAY_PASS_INFO_NAME)
            .dayPassDescription(UPDATED_DAY_PASS_DESCRIPTION)
            .dayPassLogo(UPDATED_DAY_PASS_LOGO)
            .dayPassLogoContentType(UPDATED_DAY_PASS_LOGO_CONTENT_TYPE)
            .dayPassInfoCreationDate(UPDATED_DAY_PASS_INFO_CREATION_DATE)
            .dayPassInfoUpdateDate(UPDATED_DAY_PASS_INFO_UPDATE_DATE)
            .dayPassInfoCreatedByuser(UPDATED_DAY_PASS_INFO_CREATED_BYUSER)
            .dayPassInfoDateStart(UPDATED_DAY_PASS_INFO_DATE_START)
            .dayPassInfoDateEnd(UPDATED_DAY_PASS_INFO_DATE_END)
            .dayPassInfoNumber(UPDATED_DAY_PASS_INFO_NUMBER)
            .dayPassParams(UPDATED_DAY_PASS_PARAMS)
            .dayPassAttributs(UPDATED_DAY_PASS_ATTRIBUTS)
            .dayPassInfoStat(UPDATED_DAY_PASS_INFO_STAT);
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(updatedDayPassInfo);

        restDayPassInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dayPassInfoDTO.getDayPassInfoId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
        DayPassInfo testDayPassInfo = dayPassInfoList.get(dayPassInfoList.size() - 1);
        assertThat(testDayPassInfo.getDayPassInfoName()).isEqualTo(UPDATED_DAY_PASS_INFO_NAME);
        assertThat(testDayPassInfo.getDayPassDescription()).isEqualTo(UPDATED_DAY_PASS_DESCRIPTION);
        assertThat(testDayPassInfo.getDayPassLogo()).isEqualTo(UPDATED_DAY_PASS_LOGO);
        assertThat(testDayPassInfo.getDayPassLogoContentType()).isEqualTo(UPDATED_DAY_PASS_LOGO_CONTENT_TYPE);
        assertThat(testDayPassInfo.getDayPassInfoCreationDate()).isEqualTo(UPDATED_DAY_PASS_INFO_CREATION_DATE);
        assertThat(testDayPassInfo.getDayPassInfoUpdateDate()).isEqualTo(UPDATED_DAY_PASS_INFO_UPDATE_DATE);
        assertThat(testDayPassInfo.getDayPassInfoCreatedByuser()).isEqualTo(UPDATED_DAY_PASS_INFO_CREATED_BYUSER);
        assertThat(testDayPassInfo.getDayPassInfoDateStart()).isEqualTo(UPDATED_DAY_PASS_INFO_DATE_START);
        assertThat(testDayPassInfo.getDayPassInfoDateEnd()).isEqualTo(UPDATED_DAY_PASS_INFO_DATE_END);
        assertThat(testDayPassInfo.getDayPassInfoNumber()).isEqualTo(UPDATED_DAY_PASS_INFO_NUMBER);
        assertThat(testDayPassInfo.getDayPassParams()).isEqualTo(UPDATED_DAY_PASS_PARAMS);
        assertThat(testDayPassInfo.getDayPassAttributs()).isEqualTo(UPDATED_DAY_PASS_ATTRIBUTS);
        assertThat(testDayPassInfo.getDayPassInfoStat()).isEqualTo(UPDATED_DAY_PASS_INFO_STAT);
    }

    @Test
    @Transactional
    void putNonExistingDayPassInfo() throws Exception {
        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();
        dayPassInfo.setDayPassInfoId(count.incrementAndGet());

        // Create the DayPassInfo
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayPassInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dayPassInfoDTO.getDayPassInfoId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDayPassInfo() throws Exception {
        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();
        dayPassInfo.setDayPassInfoId(count.incrementAndGet());

        // Create the DayPassInfo
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayPassInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDayPassInfo() throws Exception {
        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();
        dayPassInfo.setDayPassInfoId(count.incrementAndGet());

        // Create the DayPassInfo
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayPassInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDayPassInfoWithPatch() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();

        // Update the dayPassInfo using partial update
        DayPassInfo partialUpdatedDayPassInfo = new DayPassInfo();
        partialUpdatedDayPassInfo.setDayPassInfoId(dayPassInfo.getDayPassInfoId());

        partialUpdatedDayPassInfo
            .dayPassLogo(UPDATED_DAY_PASS_LOGO)
            .dayPassLogoContentType(UPDATED_DAY_PASS_LOGO_CONTENT_TYPE)
            .dayPassInfoCreationDate(UPDATED_DAY_PASS_INFO_CREATION_DATE)
            .dayPassInfoDateStart(UPDATED_DAY_PASS_INFO_DATE_START)
            .dayPassInfoNumber(UPDATED_DAY_PASS_INFO_NUMBER)
            .dayPassParams(UPDATED_DAY_PASS_PARAMS)
            .dayPassAttributs(UPDATED_DAY_PASS_ATTRIBUTS);

        restDayPassInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDayPassInfo.getDayPassInfoId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDayPassInfo))
            )
            .andExpect(status().isOk());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
        DayPassInfo testDayPassInfo = dayPassInfoList.get(dayPassInfoList.size() - 1);
        assertThat(testDayPassInfo.getDayPassInfoName()).isEqualTo(DEFAULT_DAY_PASS_INFO_NAME);
        assertThat(testDayPassInfo.getDayPassDescription()).isEqualTo(DEFAULT_DAY_PASS_DESCRIPTION);
        assertThat(testDayPassInfo.getDayPassLogo()).isEqualTo(UPDATED_DAY_PASS_LOGO);
        assertThat(testDayPassInfo.getDayPassLogoContentType()).isEqualTo(UPDATED_DAY_PASS_LOGO_CONTENT_TYPE);
        assertThat(testDayPassInfo.getDayPassInfoCreationDate()).isEqualTo(UPDATED_DAY_PASS_INFO_CREATION_DATE);
        assertThat(testDayPassInfo.getDayPassInfoUpdateDate()).isEqualTo(DEFAULT_DAY_PASS_INFO_UPDATE_DATE);
        assertThat(testDayPassInfo.getDayPassInfoCreatedByuser()).isEqualTo(DEFAULT_DAY_PASS_INFO_CREATED_BYUSER);
        assertThat(testDayPassInfo.getDayPassInfoDateStart()).isEqualTo(UPDATED_DAY_PASS_INFO_DATE_START);
        assertThat(testDayPassInfo.getDayPassInfoDateEnd()).isEqualTo(DEFAULT_DAY_PASS_INFO_DATE_END);
        assertThat(testDayPassInfo.getDayPassInfoNumber()).isEqualTo(UPDATED_DAY_PASS_INFO_NUMBER);
        assertThat(testDayPassInfo.getDayPassParams()).isEqualTo(UPDATED_DAY_PASS_PARAMS);
        assertThat(testDayPassInfo.getDayPassAttributs()).isEqualTo(UPDATED_DAY_PASS_ATTRIBUTS);
        assertThat(testDayPassInfo.getDayPassInfoStat()).isEqualTo(DEFAULT_DAY_PASS_INFO_STAT);
    }

    @Test
    @Transactional
    void fullUpdateDayPassInfoWithPatch() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();

        // Update the dayPassInfo using partial update
        DayPassInfo partialUpdatedDayPassInfo = new DayPassInfo();
        partialUpdatedDayPassInfo.setDayPassInfoId(dayPassInfo.getDayPassInfoId());

        partialUpdatedDayPassInfo
            .dayPassInfoName(UPDATED_DAY_PASS_INFO_NAME)
            .dayPassDescription(UPDATED_DAY_PASS_DESCRIPTION)
            .dayPassLogo(UPDATED_DAY_PASS_LOGO)
            .dayPassLogoContentType(UPDATED_DAY_PASS_LOGO_CONTENT_TYPE)
            .dayPassInfoCreationDate(UPDATED_DAY_PASS_INFO_CREATION_DATE)
            .dayPassInfoUpdateDate(UPDATED_DAY_PASS_INFO_UPDATE_DATE)
            .dayPassInfoCreatedByuser(UPDATED_DAY_PASS_INFO_CREATED_BYUSER)
            .dayPassInfoDateStart(UPDATED_DAY_PASS_INFO_DATE_START)
            .dayPassInfoDateEnd(UPDATED_DAY_PASS_INFO_DATE_END)
            .dayPassInfoNumber(UPDATED_DAY_PASS_INFO_NUMBER)
            .dayPassParams(UPDATED_DAY_PASS_PARAMS)
            .dayPassAttributs(UPDATED_DAY_PASS_ATTRIBUTS)
            .dayPassInfoStat(UPDATED_DAY_PASS_INFO_STAT);

        restDayPassInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDayPassInfo.getDayPassInfoId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDayPassInfo))
            )
            .andExpect(status().isOk());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
        DayPassInfo testDayPassInfo = dayPassInfoList.get(dayPassInfoList.size() - 1);
        assertThat(testDayPassInfo.getDayPassInfoName()).isEqualTo(UPDATED_DAY_PASS_INFO_NAME);
        assertThat(testDayPassInfo.getDayPassDescription()).isEqualTo(UPDATED_DAY_PASS_DESCRIPTION);
        assertThat(testDayPassInfo.getDayPassLogo()).isEqualTo(UPDATED_DAY_PASS_LOGO);
        assertThat(testDayPassInfo.getDayPassLogoContentType()).isEqualTo(UPDATED_DAY_PASS_LOGO_CONTENT_TYPE);
        assertThat(testDayPassInfo.getDayPassInfoCreationDate()).isEqualTo(UPDATED_DAY_PASS_INFO_CREATION_DATE);
        assertThat(testDayPassInfo.getDayPassInfoUpdateDate()).isEqualTo(UPDATED_DAY_PASS_INFO_UPDATE_DATE);
        assertThat(testDayPassInfo.getDayPassInfoCreatedByuser()).isEqualTo(UPDATED_DAY_PASS_INFO_CREATED_BYUSER);
        assertThat(testDayPassInfo.getDayPassInfoDateStart()).isEqualTo(UPDATED_DAY_PASS_INFO_DATE_START);
        assertThat(testDayPassInfo.getDayPassInfoDateEnd()).isEqualTo(UPDATED_DAY_PASS_INFO_DATE_END);
        assertThat(testDayPassInfo.getDayPassInfoNumber()).isEqualTo(UPDATED_DAY_PASS_INFO_NUMBER);
        assertThat(testDayPassInfo.getDayPassParams()).isEqualTo(UPDATED_DAY_PASS_PARAMS);
        assertThat(testDayPassInfo.getDayPassAttributs()).isEqualTo(UPDATED_DAY_PASS_ATTRIBUTS);
        assertThat(testDayPassInfo.getDayPassInfoStat()).isEqualTo(UPDATED_DAY_PASS_INFO_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingDayPassInfo() throws Exception {
        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();
        dayPassInfo.setDayPassInfoId(count.incrementAndGet());

        // Create the DayPassInfo
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayPassInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dayPassInfoDTO.getDayPassInfoId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDayPassInfo() throws Exception {
        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();
        dayPassInfo.setDayPassInfoId(count.incrementAndGet());

        // Create the DayPassInfo
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayPassInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDayPassInfo() throws Exception {
        int databaseSizeBeforeUpdate = dayPassInfoRepository.findAll().size();
        dayPassInfo.setDayPassInfoId(count.incrementAndGet());

        // Create the DayPassInfo
        DayPassInfoDTO dayPassInfoDTO = dayPassInfoMapper.toDto(dayPassInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayPassInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dayPassInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DayPassInfo in the database
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDayPassInfo() throws Exception {
        // Initialize the database
        dayPassInfoRepository.saveAndFlush(dayPassInfo);

        int databaseSizeBeforeDelete = dayPassInfoRepository.findAll().size();

        // Delete the dayPassInfo
        restDayPassInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, dayPassInfo.getDayPassInfoId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DayPassInfo> dayPassInfoList = dayPassInfoRepository.findAll();
        assertThat(dayPassInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
