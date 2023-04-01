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
import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.domain.CheckAccreditationReport;
import ma.sig.events.domain.Event;
import ma.sig.events.repository.CheckAccreditationReportRepository;
import ma.sig.events.service.criteria.CheckAccreditationReportCriteria;
import ma.sig.events.service.dto.CheckAccreditationReportDTO;
import ma.sig.events.service.mapper.CheckAccreditationReportMapper;
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
 * Integration tests for the {@link CheckAccreditationReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CheckAccreditationReportResourceIT {

    private static final String DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECK_ACCREDITATION_REPORT_STAT = false;
    private static final Boolean UPDATED_CHECK_ACCREDITATION_REPORT_STAT = true;

    private static final String ENTITY_API_URL = "/api/check-accreditation-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{checkAccreditationReportId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckAccreditationReportRepository checkAccreditationReportRepository;

    @Autowired
    private CheckAccreditationReportMapper checkAccreditationReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckAccreditationReportMockMvc;

    private CheckAccreditationReport checkAccreditationReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckAccreditationReport createEntity(EntityManager em) {
        CheckAccreditationReport checkAccreditationReport = new CheckAccreditationReport()
            .checkAccreditationReportDescription(DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION)
            .checkAccreditationReportPersonPhoto(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO)
            .checkAccreditationReportPersonPhotoContentType(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportCINPhoto(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO)
            .checkAccreditationReportCINPhotoContentType(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportAttachment(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT)
            .checkAccreditationReportAttachmentContentType(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE)
            .checkAccreditationReportParams(DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS)
            .checkAccreditationReportAttributs(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS)
            .checkAccreditationReportStat(DEFAULT_CHECK_ACCREDITATION_REPORT_STAT);
        return checkAccreditationReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckAccreditationReport createUpdatedEntity(EntityManager em) {
        CheckAccreditationReport checkAccreditationReport = new CheckAccreditationReport()
            .checkAccreditationReportDescription(UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION)
            .checkAccreditationReportPersonPhoto(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO)
            .checkAccreditationReportPersonPhotoContentType(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportCINPhoto(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO)
            .checkAccreditationReportCINPhotoContentType(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportAttachment(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT)
            .checkAccreditationReportAttachmentContentType(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE)
            .checkAccreditationReportParams(UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS)
            .checkAccreditationReportAttributs(UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS)
            .checkAccreditationReportStat(UPDATED_CHECK_ACCREDITATION_REPORT_STAT);
        return checkAccreditationReport;
    }

    @BeforeEach
    public void initTest() {
        checkAccreditationReport = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckAccreditationReport() throws Exception {
        int databaseSizeBeforeCreate = checkAccreditationReportRepository.findAll().size();
        // Create the CheckAccreditationReport
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);
        restCheckAccreditationReportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeCreate + 1);
        CheckAccreditationReport testCheckAccreditationReport = checkAccreditationReportList.get(checkAccreditationReportList.size() - 1);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportDescription())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhoto())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhotoContentType())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhoto())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhotoContentType())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachment())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachmentContentType())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportParams()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttributs())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportStat()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_STAT);
    }

    @Test
    @Transactional
    void createCheckAccreditationReportWithExistingId() throws Exception {
        // Create the CheckAccreditationReport with an existing ID
        checkAccreditationReport.setCheckAccreditationReportId(1L);
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);

        int databaseSizeBeforeCreate = checkAccreditationReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckAccreditationReportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReports() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList
        restCheckAccreditationReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=checkAccreditationReportId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportId")
                    .value(hasItem(checkAccreditationReport.getCheckAccreditationReportId().intValue()))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationReportDescription").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportPersonPhotoContentType")
                    .value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportPersonPhoto")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO)))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportCINPhotoContentType")
                    .value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportCINPhoto")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO)))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportAttachmentContentType")
                    .value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportAttachment")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT)))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationReportParams").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS)))
            .andExpect(jsonPath("$.[*].checkAccreditationReportAttributs").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportStat").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_STAT.booleanValue()))
            );
    }

    @Test
    @Transactional
    void getCheckAccreditationReport() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get the checkAccreditationReport
        restCheckAccreditationReportMockMvc
            .perform(get(ENTITY_API_URL_ID, checkAccreditationReport.getCheckAccreditationReportId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.checkAccreditationReportId").value(checkAccreditationReport.getCheckAccreditationReportId().intValue()))
            .andExpect(jsonPath("$.checkAccreditationReportDescription").value(DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION))
            .andExpect(
                jsonPath("$.checkAccreditationReportPersonPhotoContentType")
                    .value(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE)
            )
            .andExpect(
                jsonPath("$.checkAccreditationReportPersonPhoto")
                    .value(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO))
            )
            .andExpect(
                jsonPath("$.checkAccreditationReportCINPhotoContentType").value(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE)
            )
            .andExpect(
                jsonPath("$.checkAccreditationReportCINPhoto")
                    .value(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO))
            )
            .andExpect(
                jsonPath("$.checkAccreditationReportAttachmentContentType")
                    .value(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE)
            )
            .andExpect(
                jsonPath("$.checkAccreditationReportAttachment")
                    .value(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT))
            )
            .andExpect(jsonPath("$.checkAccreditationReportParams").value(DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS))
            .andExpect(jsonPath("$.checkAccreditationReportAttributs").value(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS))
            .andExpect(jsonPath("$.checkAccreditationReportStat").value(DEFAULT_CHECK_ACCREDITATION_REPORT_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCheckAccreditationReportsByIdFiltering() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        Long id = checkAccreditationReport.getCheckAccreditationReportId();

        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportId.equals=" + id);
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportId.notEquals=" + id);

        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportId.greaterThanOrEqual=" + id);
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportId.greaterThan=" + id);

        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportId.lessThanOrEqual=" + id);
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription equals to DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportDescription.equals=" + DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription equals to UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportDescription.equals=" + UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription in DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION or UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportDescription.in=" +
            DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION +
            "," +
            UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription equals to UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportDescription.in=" + UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription is not null
        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportDescription.specified=true");

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription is null
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportDescriptionContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription contains DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportDescription.contains=" + DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription contains UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportDescription.contains=" + UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription does not contain DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportDescription.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportDescription does not contain UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportDescription.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportParams equals to DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportParams.equals=" + DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS);

        // Get all the checkAccreditationReportList where checkAccreditationReportParams equals to UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportParams.equals=" + UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportParamsIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportParams in DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS or UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportParams.in=" +
            DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS +
            "," +
            UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportParams equals to UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportParams.in=" + UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportParams is not null
        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportParams.specified=true");

        // Get all the checkAccreditationReportList where checkAccreditationReportParams is null
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportParamsContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportParams contains DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportParams.contains=" + DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportParams contains UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportParams.contains=" + UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportParamsNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportParams does not contain DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportParams.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportParams does not contain UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportParams.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs equals to DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportAttributs.equals=" + DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs equals to UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportAttributs.equals=" + UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs in DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS or UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportAttributs.in=" +
            DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS +
            "," +
            UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs equals to UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportAttributs.in=" + UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs is not null
        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportAttributs.specified=true");

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs is null
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportAttributsContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs contains DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportAttributs.contains=" + DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs contains UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportAttributs.contains=" + UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs does not contain DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldNotBeFound(
            "checkAccreditationReportAttributs.doesNotContain=" + DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportAttributs does not contain UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportAttributs.doesNotContain=" + UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS
        );
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportStatIsEqualToSomething() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportStat equals to DEFAULT_CHECK_ACCREDITATION_REPORT_STAT
        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportStat.equals=" + DEFAULT_CHECK_ACCREDITATION_REPORT_STAT);

        // Get all the checkAccreditationReportList where checkAccreditationReportStat equals to UPDATED_CHECK_ACCREDITATION_REPORT_STAT
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportStat.equals=" + UPDATED_CHECK_ACCREDITATION_REPORT_STAT);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportStatIsInShouldWork() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportStat in DEFAULT_CHECK_ACCREDITATION_REPORT_STAT or UPDATED_CHECK_ACCREDITATION_REPORT_STAT
        defaultCheckAccreditationReportShouldBeFound(
            "checkAccreditationReportStat.in=" + DEFAULT_CHECK_ACCREDITATION_REPORT_STAT + "," + UPDATED_CHECK_ACCREDITATION_REPORT_STAT
        );

        // Get all the checkAccreditationReportList where checkAccreditationReportStat equals to UPDATED_CHECK_ACCREDITATION_REPORT_STAT
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportStat.in=" + UPDATED_CHECK_ACCREDITATION_REPORT_STAT);
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationReportStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        // Get all the checkAccreditationReportList where checkAccreditationReportStat is not null
        defaultCheckAccreditationReportShouldBeFound("checkAccreditationReportStat.specified=true");

        // Get all the checkAccreditationReportList where checkAccreditationReportStat is null
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationReportStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        checkAccreditationReport.setEvent(event);
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);
        Long eventId = event.getEventId();

        // Get all the checkAccreditationReportList where event equals to eventId
        defaultCheckAccreditationReportShouldBeFound("eventId.equals=" + eventId);

        // Get all the checkAccreditationReportList where event equals to (eventId + 1)
        defaultCheckAccreditationReportShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllCheckAccreditationReportsByCheckAccreditationHistoryIsEqualToSomething() throws Exception {
        CheckAccreditationHistory checkAccreditationHistory;
        if (TestUtil.findAll(em, CheckAccreditationHistory.class).isEmpty()) {
            checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);
            checkAccreditationHistory = CheckAccreditationHistoryResourceIT.createEntity(em);
        } else {
            checkAccreditationHistory = TestUtil.findAll(em, CheckAccreditationHistory.class).get(0);
        }
        em.persist(checkAccreditationHistory);
        em.flush();
        checkAccreditationReport.setCheckAccreditationHistory(checkAccreditationHistory);
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);
        Long checkAccreditationHistoryId = checkAccreditationHistory.getCheckAccreditationHistoryId();

        // Get all the checkAccreditationReportList where checkAccreditationHistory equals to checkAccreditationHistoryId
        defaultCheckAccreditationReportShouldBeFound("checkAccreditationHistoryId.equals=" + checkAccreditationHistoryId);

        // Get all the checkAccreditationReportList where checkAccreditationHistory equals to (checkAccreditationHistoryId + 1)
        defaultCheckAccreditationReportShouldNotBeFound("checkAccreditationHistoryId.equals=" + (checkAccreditationHistoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckAccreditationReportShouldBeFound(String filter) throws Exception {
        restCheckAccreditationReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=checkAccreditationReportId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportId")
                    .value(hasItem(checkAccreditationReport.getCheckAccreditationReportId().intValue()))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationReportDescription").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_DESCRIPTION)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportPersonPhotoContentType")
                    .value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportPersonPhoto")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO)))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportCINPhotoContentType")
                    .value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportCINPhoto")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_CIN_PHOTO)))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportAttachmentContentType")
                    .value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportAttachment")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT)))
            )
            .andExpect(jsonPath("$.[*].checkAccreditationReportParams").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS)))
            .andExpect(jsonPath("$.[*].checkAccreditationReportAttributs").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTRIBUTS)))
            .andExpect(
                jsonPath("$.[*].checkAccreditationReportStat").value(hasItem(DEFAULT_CHECK_ACCREDITATION_REPORT_STAT.booleanValue()))
            );

        // Check, that the count call also returns 1
        restCheckAccreditationReportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=checkAccreditationReportId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckAccreditationReportShouldNotBeFound(String filter) throws Exception {
        restCheckAccreditationReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=checkAccreditationReportId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckAccreditationReportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=checkAccreditationReportId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckAccreditationReport() throws Exception {
        // Get the checkAccreditationReport
        restCheckAccreditationReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCheckAccreditationReport() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();

        // Update the checkAccreditationReport
        CheckAccreditationReport updatedCheckAccreditationReport = checkAccreditationReportRepository
            .findById(checkAccreditationReport.getCheckAccreditationReportId())
            .get();
        // Disconnect from session so that the updates on updatedCheckAccreditationReport are not directly saved in db
        em.detach(updatedCheckAccreditationReport);
        updatedCheckAccreditationReport
            .checkAccreditationReportDescription(UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION)
            .checkAccreditationReportPersonPhoto(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO)
            .checkAccreditationReportPersonPhotoContentType(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportCINPhoto(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO)
            .checkAccreditationReportCINPhotoContentType(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportAttachment(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT)
            .checkAccreditationReportAttachmentContentType(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE)
            .checkAccreditationReportParams(UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS)
            .checkAccreditationReportAttributs(UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS)
            .checkAccreditationReportStat(UPDATED_CHECK_ACCREDITATION_REPORT_STAT);
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(updatedCheckAccreditationReport);

        restCheckAccreditationReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkAccreditationReportDTO.getCheckAccreditationReportId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
        CheckAccreditationReport testCheckAccreditationReport = checkAccreditationReportList.get(checkAccreditationReportList.size() - 1);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportDescription())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhoto())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhotoContentType())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhoto())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhotoContentType())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachment())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachmentContentType())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportParams()).isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttributs())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportStat()).isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCheckAccreditationReport() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();
        checkAccreditationReport.setCheckAccreditationReportId(count.incrementAndGet());

        // Create the CheckAccreditationReport
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckAccreditationReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkAccreditationReportDTO.getCheckAccreditationReportId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckAccreditationReport() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();
        checkAccreditationReport.setCheckAccreditationReportId(count.incrementAndGet());

        // Create the CheckAccreditationReport
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckAccreditationReport() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();
        checkAccreditationReport.setCheckAccreditationReportId(count.incrementAndGet());

        // Create the CheckAccreditationReport
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationReportMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckAccreditationReportWithPatch() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();

        // Update the checkAccreditationReport using partial update
        CheckAccreditationReport partialUpdatedCheckAccreditationReport = new CheckAccreditationReport();
        partialUpdatedCheckAccreditationReport.setCheckAccreditationReportId(checkAccreditationReport.getCheckAccreditationReportId());

        partialUpdatedCheckAccreditationReport
            .checkAccreditationReportDescription(UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION)
            .checkAccreditationReportCINPhoto(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO)
            .checkAccreditationReportCINPhotoContentType(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportAttributs(UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS);

        restCheckAccreditationReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckAccreditationReport.getCheckAccreditationReportId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckAccreditationReport))
            )
            .andExpect(status().isOk());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
        CheckAccreditationReport testCheckAccreditationReport = checkAccreditationReportList.get(checkAccreditationReportList.size() - 1);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportDescription())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhoto())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhotoContentType())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhoto())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhotoContentType())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachment())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachmentContentType())
            .isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportParams()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_PARAMS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttributs())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportStat()).isEqualTo(DEFAULT_CHECK_ACCREDITATION_REPORT_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCheckAccreditationReportWithPatch() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();

        // Update the checkAccreditationReport using partial update
        CheckAccreditationReport partialUpdatedCheckAccreditationReport = new CheckAccreditationReport();
        partialUpdatedCheckAccreditationReport.setCheckAccreditationReportId(checkAccreditationReport.getCheckAccreditationReportId());

        partialUpdatedCheckAccreditationReport
            .checkAccreditationReportDescription(UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION)
            .checkAccreditationReportPersonPhoto(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO)
            .checkAccreditationReportPersonPhotoContentType(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportCINPhoto(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO)
            .checkAccreditationReportCINPhotoContentType(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE)
            .checkAccreditationReportAttachment(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT)
            .checkAccreditationReportAttachmentContentType(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE)
            .checkAccreditationReportParams(UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS)
            .checkAccreditationReportAttributs(UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS)
            .checkAccreditationReportStat(UPDATED_CHECK_ACCREDITATION_REPORT_STAT);

        restCheckAccreditationReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckAccreditationReport.getCheckAccreditationReportId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckAccreditationReport))
            )
            .andExpect(status().isOk());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
        CheckAccreditationReport testCheckAccreditationReport = checkAccreditationReportList.get(checkAccreditationReportList.size() - 1);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportDescription())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_DESCRIPTION);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhoto())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportPersonPhotoContentType())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_PERSON_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhoto())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportCINPhotoContentType())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_CIN_PHOTO_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachment())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttachmentContentType())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportParams()).isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_PARAMS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportAttributs())
            .isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_ATTRIBUTS);
        assertThat(testCheckAccreditationReport.getCheckAccreditationReportStat()).isEqualTo(UPDATED_CHECK_ACCREDITATION_REPORT_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCheckAccreditationReport() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();
        checkAccreditationReport.setCheckAccreditationReportId(count.incrementAndGet());

        // Create the CheckAccreditationReport
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckAccreditationReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkAccreditationReportDTO.getCheckAccreditationReportId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckAccreditationReport() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();
        checkAccreditationReport.setCheckAccreditationReportId(count.incrementAndGet());

        // Create the CheckAccreditationReport
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckAccreditationReport() throws Exception {
        int databaseSizeBeforeUpdate = checkAccreditationReportRepository.findAll().size();
        checkAccreditationReport.setCheckAccreditationReportId(count.incrementAndGet());

        // Create the CheckAccreditationReport
        CheckAccreditationReportDTO checkAccreditationReportDTO = checkAccreditationReportMapper.toDto(checkAccreditationReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckAccreditationReportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkAccreditationReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckAccreditationReport in the database
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckAccreditationReport() throws Exception {
        // Initialize the database
        checkAccreditationReportRepository.saveAndFlush(checkAccreditationReport);

        int databaseSizeBeforeDelete = checkAccreditationReportRepository.findAll().size();

        // Delete the checkAccreditationReport
        restCheckAccreditationReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkAccreditationReport.getCheckAccreditationReportId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckAccreditationReport> checkAccreditationReportList = checkAccreditationReportRepository.findAll();
        assertThat(checkAccreditationReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
