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
import ma.sig.events.domain.Event;
import ma.sig.events.domain.PhotoArchive;
import ma.sig.events.repository.PhotoArchiveRepository;
import ma.sig.events.service.criteria.PhotoArchiveCriteria;
import ma.sig.events.service.dto.PhotoArchiveDTO;
import ma.sig.events.service.mapper.PhotoArchiveMapper;
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
 * Integration tests for the {@link PhotoArchiveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhotoArchiveResourceIT {

    private static final String DEFAULT_PHOTO_ARCHIVE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_ARCHIVE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_ARCHIVE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_ARCHIVE_PATH = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO_ARCHIVE_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_ARCHIVE_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PHOTO_ARCHIVE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_ARCHIVE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_ARCHIVE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_ARCHIVE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_ARCHIVE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PHOTO_ARCHIVE_STAT = false;
    private static final Boolean UPDATED_PHOTO_ARCHIVE_STAT = true;

    private static final String ENTITY_API_URL = "/api/photo-archives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{photoArchiveId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhotoArchiveRepository photoArchiveRepository;

    @Autowired
    private PhotoArchiveMapper photoArchiveMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhotoArchiveMockMvc;

    private PhotoArchive photoArchive;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoArchive createEntity(EntityManager em) {
        PhotoArchive photoArchive = new PhotoArchive()
            .photoArchiveName(DEFAULT_PHOTO_ARCHIVE_NAME)
            .photoArchivePath(DEFAULT_PHOTO_ARCHIVE_PATH)
            .photoArchivePhoto(DEFAULT_PHOTO_ARCHIVE_PHOTO)
            .photoArchivePhotoContentType(DEFAULT_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE)
            .photoArchiveDescription(DEFAULT_PHOTO_ARCHIVE_DESCRIPTION)
            .photoArchiveParams(DEFAULT_PHOTO_ARCHIVE_PARAMS)
            .photoArchiveAttributs(DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS)
            .photoArchiveStat(DEFAULT_PHOTO_ARCHIVE_STAT);
        return photoArchive;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoArchive createUpdatedEntity(EntityManager em) {
        PhotoArchive photoArchive = new PhotoArchive()
            .photoArchiveName(UPDATED_PHOTO_ARCHIVE_NAME)
            .photoArchivePath(UPDATED_PHOTO_ARCHIVE_PATH)
            .photoArchivePhoto(UPDATED_PHOTO_ARCHIVE_PHOTO)
            .photoArchivePhotoContentType(UPDATED_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE)
            .photoArchiveDescription(UPDATED_PHOTO_ARCHIVE_DESCRIPTION)
            .photoArchiveParams(UPDATED_PHOTO_ARCHIVE_PARAMS)
            .photoArchiveAttributs(UPDATED_PHOTO_ARCHIVE_ATTRIBUTS)
            .photoArchiveStat(UPDATED_PHOTO_ARCHIVE_STAT);
        return photoArchive;
    }

    @BeforeEach
    public void initTest() {
        photoArchive = createEntity(em);
    }

    @Test
    @Transactional
    void createPhotoArchive() throws Exception {
        int databaseSizeBeforeCreate = photoArchiveRepository.findAll().size();
        // Create the PhotoArchive
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);
        restPhotoArchiveMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeCreate + 1);
        PhotoArchive testPhotoArchive = photoArchiveList.get(photoArchiveList.size() - 1);
        assertThat(testPhotoArchive.getPhotoArchiveName()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_NAME);
        assertThat(testPhotoArchive.getPhotoArchivePath()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_PATH);
        assertThat(testPhotoArchive.getPhotoArchivePhoto()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_PHOTO);
        assertThat(testPhotoArchive.getPhotoArchivePhotoContentType()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE);
        assertThat(testPhotoArchive.getPhotoArchiveDescription()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_DESCRIPTION);
        assertThat(testPhotoArchive.getPhotoArchiveParams()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_PARAMS);
        assertThat(testPhotoArchive.getPhotoArchiveAttributs()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS);
        assertThat(testPhotoArchive.getPhotoArchiveStat()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_STAT);
    }

    @Test
    @Transactional
    void createPhotoArchiveWithExistingId() throws Exception {
        // Create the PhotoArchive with an existing ID
        photoArchive.setPhotoArchiveId(1L);
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        int databaseSizeBeforeCreate = photoArchiveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotoArchiveMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhotoArchiveNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = photoArchiveRepository.findAll().size();
        // set the field null
        photoArchive.setPhotoArchiveName(null);

        // Create the PhotoArchive, which fails.
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        restPhotoArchiveMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhotoArchives() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList
        restPhotoArchiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=photoArchiveId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].photoArchiveId").value(hasItem(photoArchive.getPhotoArchiveId().intValue())))
            .andExpect(jsonPath("$.[*].photoArchiveName").value(hasItem(DEFAULT_PHOTO_ARCHIVE_NAME)))
            .andExpect(jsonPath("$.[*].photoArchivePath").value(hasItem(DEFAULT_PHOTO_ARCHIVE_PATH)))
            .andExpect(jsonPath("$.[*].photoArchivePhotoContentType").value(hasItem(DEFAULT_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoArchivePhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_ARCHIVE_PHOTO))))
            .andExpect(jsonPath("$.[*].photoArchiveDescription").value(hasItem(DEFAULT_PHOTO_ARCHIVE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].photoArchiveParams").value(hasItem(DEFAULT_PHOTO_ARCHIVE_PARAMS)))
            .andExpect(jsonPath("$.[*].photoArchiveAttributs").value(hasItem(DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].photoArchiveStat").value(hasItem(DEFAULT_PHOTO_ARCHIVE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getPhotoArchive() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get the photoArchive
        restPhotoArchiveMockMvc
            .perform(get(ENTITY_API_URL_ID, photoArchive.getPhotoArchiveId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.photoArchiveId").value(photoArchive.getPhotoArchiveId().intValue()))
            .andExpect(jsonPath("$.photoArchiveName").value(DEFAULT_PHOTO_ARCHIVE_NAME))
            .andExpect(jsonPath("$.photoArchivePath").value(DEFAULT_PHOTO_ARCHIVE_PATH))
            .andExpect(jsonPath("$.photoArchivePhotoContentType").value(DEFAULT_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoArchivePhoto").value(Base64Utils.encodeToString(DEFAULT_PHOTO_ARCHIVE_PHOTO)))
            .andExpect(jsonPath("$.photoArchiveDescription").value(DEFAULT_PHOTO_ARCHIVE_DESCRIPTION))
            .andExpect(jsonPath("$.photoArchiveParams").value(DEFAULT_PHOTO_ARCHIVE_PARAMS))
            .andExpect(jsonPath("$.photoArchiveAttributs").value(DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS))
            .andExpect(jsonPath("$.photoArchiveStat").value(DEFAULT_PHOTO_ARCHIVE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getPhotoArchivesByIdFiltering() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        Long id = photoArchive.getPhotoArchiveId();

        defaultPhotoArchiveShouldBeFound("photoArchiveId.equals=" + id);
        defaultPhotoArchiveShouldNotBeFound("photoArchiveId.notEquals=" + id);

        defaultPhotoArchiveShouldBeFound("photoArchiveId.greaterThanOrEqual=" + id);
        defaultPhotoArchiveShouldNotBeFound("photoArchiveId.greaterThan=" + id);

        defaultPhotoArchiveShouldBeFound("photoArchiveId.lessThanOrEqual=" + id);
        defaultPhotoArchiveShouldNotBeFound("photoArchiveId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveNameIsEqualToSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveName equals to DEFAULT_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldBeFound("photoArchiveName.equals=" + DEFAULT_PHOTO_ARCHIVE_NAME);

        // Get all the photoArchiveList where photoArchiveName equals to UPDATED_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldNotBeFound("photoArchiveName.equals=" + UPDATED_PHOTO_ARCHIVE_NAME);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveNameIsInShouldWork() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveName in DEFAULT_PHOTO_ARCHIVE_NAME or UPDATED_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldBeFound("photoArchiveName.in=" + DEFAULT_PHOTO_ARCHIVE_NAME + "," + UPDATED_PHOTO_ARCHIVE_NAME);

        // Get all the photoArchiveList where photoArchiveName equals to UPDATED_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldNotBeFound("photoArchiveName.in=" + UPDATED_PHOTO_ARCHIVE_NAME);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveName is not null
        defaultPhotoArchiveShouldBeFound("photoArchiveName.specified=true");

        // Get all the photoArchiveList where photoArchiveName is null
        defaultPhotoArchiveShouldNotBeFound("photoArchiveName.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveNameContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveName contains DEFAULT_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldBeFound("photoArchiveName.contains=" + DEFAULT_PHOTO_ARCHIVE_NAME);

        // Get all the photoArchiveList where photoArchiveName contains UPDATED_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldNotBeFound("photoArchiveName.contains=" + UPDATED_PHOTO_ARCHIVE_NAME);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveNameNotContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveName does not contain DEFAULT_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldNotBeFound("photoArchiveName.doesNotContain=" + DEFAULT_PHOTO_ARCHIVE_NAME);

        // Get all the photoArchiveList where photoArchiveName does not contain UPDATED_PHOTO_ARCHIVE_NAME
        defaultPhotoArchiveShouldBeFound("photoArchiveName.doesNotContain=" + UPDATED_PHOTO_ARCHIVE_NAME);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchivePathIsEqualToSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchivePath equals to DEFAULT_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldBeFound("photoArchivePath.equals=" + DEFAULT_PHOTO_ARCHIVE_PATH);

        // Get all the photoArchiveList where photoArchivePath equals to UPDATED_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldNotBeFound("photoArchivePath.equals=" + UPDATED_PHOTO_ARCHIVE_PATH);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchivePathIsInShouldWork() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchivePath in DEFAULT_PHOTO_ARCHIVE_PATH or UPDATED_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldBeFound("photoArchivePath.in=" + DEFAULT_PHOTO_ARCHIVE_PATH + "," + UPDATED_PHOTO_ARCHIVE_PATH);

        // Get all the photoArchiveList where photoArchivePath equals to UPDATED_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldNotBeFound("photoArchivePath.in=" + UPDATED_PHOTO_ARCHIVE_PATH);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchivePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchivePath is not null
        defaultPhotoArchiveShouldBeFound("photoArchivePath.specified=true");

        // Get all the photoArchiveList where photoArchivePath is null
        defaultPhotoArchiveShouldNotBeFound("photoArchivePath.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchivePathContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchivePath contains DEFAULT_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldBeFound("photoArchivePath.contains=" + DEFAULT_PHOTO_ARCHIVE_PATH);

        // Get all the photoArchiveList where photoArchivePath contains UPDATED_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldNotBeFound("photoArchivePath.contains=" + UPDATED_PHOTO_ARCHIVE_PATH);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchivePathNotContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchivePath does not contain DEFAULT_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldNotBeFound("photoArchivePath.doesNotContain=" + DEFAULT_PHOTO_ARCHIVE_PATH);

        // Get all the photoArchiveList where photoArchivePath does not contain UPDATED_PHOTO_ARCHIVE_PATH
        defaultPhotoArchiveShouldBeFound("photoArchivePath.doesNotContain=" + UPDATED_PHOTO_ARCHIVE_PATH);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveDescription equals to DEFAULT_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldBeFound("photoArchiveDescription.equals=" + DEFAULT_PHOTO_ARCHIVE_DESCRIPTION);

        // Get all the photoArchiveList where photoArchiveDescription equals to UPDATED_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldNotBeFound("photoArchiveDescription.equals=" + UPDATED_PHOTO_ARCHIVE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveDescription in DEFAULT_PHOTO_ARCHIVE_DESCRIPTION or UPDATED_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldBeFound(
            "photoArchiveDescription.in=" + DEFAULT_PHOTO_ARCHIVE_DESCRIPTION + "," + UPDATED_PHOTO_ARCHIVE_DESCRIPTION
        );

        // Get all the photoArchiveList where photoArchiveDescription equals to UPDATED_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldNotBeFound("photoArchiveDescription.in=" + UPDATED_PHOTO_ARCHIVE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveDescription is not null
        defaultPhotoArchiveShouldBeFound("photoArchiveDescription.specified=true");

        // Get all the photoArchiveList where photoArchiveDescription is null
        defaultPhotoArchiveShouldNotBeFound("photoArchiveDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveDescriptionContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveDescription contains DEFAULT_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldBeFound("photoArchiveDescription.contains=" + DEFAULT_PHOTO_ARCHIVE_DESCRIPTION);

        // Get all the photoArchiveList where photoArchiveDescription contains UPDATED_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldNotBeFound("photoArchiveDescription.contains=" + UPDATED_PHOTO_ARCHIVE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveDescription does not contain DEFAULT_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldNotBeFound("photoArchiveDescription.doesNotContain=" + DEFAULT_PHOTO_ARCHIVE_DESCRIPTION);

        // Get all the photoArchiveList where photoArchiveDescription does not contain UPDATED_PHOTO_ARCHIVE_DESCRIPTION
        defaultPhotoArchiveShouldBeFound("photoArchiveDescription.doesNotContain=" + UPDATED_PHOTO_ARCHIVE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveParams equals to DEFAULT_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldBeFound("photoArchiveParams.equals=" + DEFAULT_PHOTO_ARCHIVE_PARAMS);

        // Get all the photoArchiveList where photoArchiveParams equals to UPDATED_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveParams.equals=" + UPDATED_PHOTO_ARCHIVE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveParamsIsInShouldWork() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveParams in DEFAULT_PHOTO_ARCHIVE_PARAMS or UPDATED_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldBeFound("photoArchiveParams.in=" + DEFAULT_PHOTO_ARCHIVE_PARAMS + "," + UPDATED_PHOTO_ARCHIVE_PARAMS);

        // Get all the photoArchiveList where photoArchiveParams equals to UPDATED_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveParams.in=" + UPDATED_PHOTO_ARCHIVE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveParams is not null
        defaultPhotoArchiveShouldBeFound("photoArchiveParams.specified=true");

        // Get all the photoArchiveList where photoArchiveParams is null
        defaultPhotoArchiveShouldNotBeFound("photoArchiveParams.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveParamsContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveParams contains DEFAULT_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldBeFound("photoArchiveParams.contains=" + DEFAULT_PHOTO_ARCHIVE_PARAMS);

        // Get all the photoArchiveList where photoArchiveParams contains UPDATED_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveParams.contains=" + UPDATED_PHOTO_ARCHIVE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveParamsNotContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveParams does not contain DEFAULT_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveParams.doesNotContain=" + DEFAULT_PHOTO_ARCHIVE_PARAMS);

        // Get all the photoArchiveList where photoArchiveParams does not contain UPDATED_PHOTO_ARCHIVE_PARAMS
        defaultPhotoArchiveShouldBeFound("photoArchiveParams.doesNotContain=" + UPDATED_PHOTO_ARCHIVE_PARAMS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveAttributs equals to DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldBeFound("photoArchiveAttributs.equals=" + DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS);

        // Get all the photoArchiveList where photoArchiveAttributs equals to UPDATED_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveAttributs.equals=" + UPDATED_PHOTO_ARCHIVE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveAttributs in DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS or UPDATED_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldBeFound(
            "photoArchiveAttributs.in=" + DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS + "," + UPDATED_PHOTO_ARCHIVE_ATTRIBUTS
        );

        // Get all the photoArchiveList where photoArchiveAttributs equals to UPDATED_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveAttributs.in=" + UPDATED_PHOTO_ARCHIVE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveAttributs is not null
        defaultPhotoArchiveShouldBeFound("photoArchiveAttributs.specified=true");

        // Get all the photoArchiveList where photoArchiveAttributs is null
        defaultPhotoArchiveShouldNotBeFound("photoArchiveAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveAttributsContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveAttributs contains DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldBeFound("photoArchiveAttributs.contains=" + DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS);

        // Get all the photoArchiveList where photoArchiveAttributs contains UPDATED_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveAttributs.contains=" + UPDATED_PHOTO_ARCHIVE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveAttributs does not contain DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldNotBeFound("photoArchiveAttributs.doesNotContain=" + DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS);

        // Get all the photoArchiveList where photoArchiveAttributs does not contain UPDATED_PHOTO_ARCHIVE_ATTRIBUTS
        defaultPhotoArchiveShouldBeFound("photoArchiveAttributs.doesNotContain=" + UPDATED_PHOTO_ARCHIVE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveStatIsEqualToSomething() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveStat equals to DEFAULT_PHOTO_ARCHIVE_STAT
        defaultPhotoArchiveShouldBeFound("photoArchiveStat.equals=" + DEFAULT_PHOTO_ARCHIVE_STAT);

        // Get all the photoArchiveList where photoArchiveStat equals to UPDATED_PHOTO_ARCHIVE_STAT
        defaultPhotoArchiveShouldNotBeFound("photoArchiveStat.equals=" + UPDATED_PHOTO_ARCHIVE_STAT);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveStatIsInShouldWork() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveStat in DEFAULT_PHOTO_ARCHIVE_STAT or UPDATED_PHOTO_ARCHIVE_STAT
        defaultPhotoArchiveShouldBeFound("photoArchiveStat.in=" + DEFAULT_PHOTO_ARCHIVE_STAT + "," + UPDATED_PHOTO_ARCHIVE_STAT);

        // Get all the photoArchiveList where photoArchiveStat equals to UPDATED_PHOTO_ARCHIVE_STAT
        defaultPhotoArchiveShouldNotBeFound("photoArchiveStat.in=" + UPDATED_PHOTO_ARCHIVE_STAT);
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByPhotoArchiveStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        // Get all the photoArchiveList where photoArchiveStat is not null
        defaultPhotoArchiveShouldBeFound("photoArchiveStat.specified=true");

        // Get all the photoArchiveList where photoArchiveStat is null
        defaultPhotoArchiveShouldNotBeFound("photoArchiveStat.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            photoArchiveRepository.saveAndFlush(photoArchive);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        photoArchive.setAccreditation(accreditation);
        photoArchiveRepository.saveAndFlush(photoArchive);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the photoArchiveList where accreditation equals to accreditationId
        defaultPhotoArchiveShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the photoArchiveList where accreditation equals to (accreditationId + 1)
        defaultPhotoArchiveShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllPhotoArchivesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            photoArchiveRepository.saveAndFlush(photoArchive);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        photoArchive.setEvent(event);
        photoArchiveRepository.saveAndFlush(photoArchive);
        Long eventId = event.getEventId();

        // Get all the photoArchiveList where event equals to eventId
        defaultPhotoArchiveShouldBeFound("eventId.equals=" + eventId);

        // Get all the photoArchiveList where event equals to (eventId + 1)
        defaultPhotoArchiveShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhotoArchiveShouldBeFound(String filter) throws Exception {
        restPhotoArchiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=photoArchiveId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].photoArchiveId").value(hasItem(photoArchive.getPhotoArchiveId().intValue())))
            .andExpect(jsonPath("$.[*].photoArchiveName").value(hasItem(DEFAULT_PHOTO_ARCHIVE_NAME)))
            .andExpect(jsonPath("$.[*].photoArchivePath").value(hasItem(DEFAULT_PHOTO_ARCHIVE_PATH)))
            .andExpect(jsonPath("$.[*].photoArchivePhotoContentType").value(hasItem(DEFAULT_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoArchivePhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_ARCHIVE_PHOTO))))
            .andExpect(jsonPath("$.[*].photoArchiveDescription").value(hasItem(DEFAULT_PHOTO_ARCHIVE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].photoArchiveParams").value(hasItem(DEFAULT_PHOTO_ARCHIVE_PARAMS)))
            .andExpect(jsonPath("$.[*].photoArchiveAttributs").value(hasItem(DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].photoArchiveStat").value(hasItem(DEFAULT_PHOTO_ARCHIVE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restPhotoArchiveMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=photoArchiveId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhotoArchiveShouldNotBeFound(String filter) throws Exception {
        restPhotoArchiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=photoArchiveId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhotoArchiveMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=photoArchiveId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPhotoArchive() throws Exception {
        // Get the photoArchive
        restPhotoArchiveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPhotoArchive() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();

        // Update the photoArchive
        PhotoArchive updatedPhotoArchive = photoArchiveRepository.findById(photoArchive.getPhotoArchiveId()).get();
        // Disconnect from session so that the updates on updatedPhotoArchive are not directly saved in db
        em.detach(updatedPhotoArchive);
        updatedPhotoArchive
            .photoArchiveName(UPDATED_PHOTO_ARCHIVE_NAME)
            .photoArchivePath(UPDATED_PHOTO_ARCHIVE_PATH)
            .photoArchivePhoto(UPDATED_PHOTO_ARCHIVE_PHOTO)
            .photoArchivePhotoContentType(UPDATED_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE)
            .photoArchiveDescription(UPDATED_PHOTO_ARCHIVE_DESCRIPTION)
            .photoArchiveParams(UPDATED_PHOTO_ARCHIVE_PARAMS)
            .photoArchiveAttributs(UPDATED_PHOTO_ARCHIVE_ATTRIBUTS)
            .photoArchiveStat(UPDATED_PHOTO_ARCHIVE_STAT);
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(updatedPhotoArchive);

        restPhotoArchiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoArchiveDTO.getPhotoArchiveId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
        PhotoArchive testPhotoArchive = photoArchiveList.get(photoArchiveList.size() - 1);
        assertThat(testPhotoArchive.getPhotoArchiveName()).isEqualTo(UPDATED_PHOTO_ARCHIVE_NAME);
        assertThat(testPhotoArchive.getPhotoArchivePath()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PATH);
        assertThat(testPhotoArchive.getPhotoArchivePhoto()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PHOTO);
        assertThat(testPhotoArchive.getPhotoArchivePhotoContentType()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE);
        assertThat(testPhotoArchive.getPhotoArchiveDescription()).isEqualTo(UPDATED_PHOTO_ARCHIVE_DESCRIPTION);
        assertThat(testPhotoArchive.getPhotoArchiveParams()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PARAMS);
        assertThat(testPhotoArchive.getPhotoArchiveAttributs()).isEqualTo(UPDATED_PHOTO_ARCHIVE_ATTRIBUTS);
        assertThat(testPhotoArchive.getPhotoArchiveStat()).isEqualTo(UPDATED_PHOTO_ARCHIVE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingPhotoArchive() throws Exception {
        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();
        photoArchive.setPhotoArchiveId(count.incrementAndGet());

        // Create the PhotoArchive
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoArchiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoArchiveDTO.getPhotoArchiveId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhotoArchive() throws Exception {
        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();
        photoArchive.setPhotoArchiveId(count.incrementAndGet());

        // Create the PhotoArchive
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoArchiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhotoArchive() throws Exception {
        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();
        photoArchive.setPhotoArchiveId(count.incrementAndGet());

        // Create the PhotoArchive
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoArchiveMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhotoArchiveWithPatch() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();

        // Update the photoArchive using partial update
        PhotoArchive partialUpdatedPhotoArchive = new PhotoArchive();
        partialUpdatedPhotoArchive.setPhotoArchiveId(photoArchive.getPhotoArchiveId());

        partialUpdatedPhotoArchive
            .photoArchiveName(UPDATED_PHOTO_ARCHIVE_NAME)
            .photoArchivePath(UPDATED_PHOTO_ARCHIVE_PATH)
            .photoArchiveStat(UPDATED_PHOTO_ARCHIVE_STAT);

        restPhotoArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotoArchive.getPhotoArchiveId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhotoArchive))
            )
            .andExpect(status().isOk());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
        PhotoArchive testPhotoArchive = photoArchiveList.get(photoArchiveList.size() - 1);
        assertThat(testPhotoArchive.getPhotoArchiveName()).isEqualTo(UPDATED_PHOTO_ARCHIVE_NAME);
        assertThat(testPhotoArchive.getPhotoArchivePath()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PATH);
        assertThat(testPhotoArchive.getPhotoArchivePhoto()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_PHOTO);
        assertThat(testPhotoArchive.getPhotoArchivePhotoContentType()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE);
        assertThat(testPhotoArchive.getPhotoArchiveDescription()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_DESCRIPTION);
        assertThat(testPhotoArchive.getPhotoArchiveParams()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_PARAMS);
        assertThat(testPhotoArchive.getPhotoArchiveAttributs()).isEqualTo(DEFAULT_PHOTO_ARCHIVE_ATTRIBUTS);
        assertThat(testPhotoArchive.getPhotoArchiveStat()).isEqualTo(UPDATED_PHOTO_ARCHIVE_STAT);
    }

    @Test
    @Transactional
    void fullUpdatePhotoArchiveWithPatch() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();

        // Update the photoArchive using partial update
        PhotoArchive partialUpdatedPhotoArchive = new PhotoArchive();
        partialUpdatedPhotoArchive.setPhotoArchiveId(photoArchive.getPhotoArchiveId());

        partialUpdatedPhotoArchive
            .photoArchiveName(UPDATED_PHOTO_ARCHIVE_NAME)
            .photoArchivePath(UPDATED_PHOTO_ARCHIVE_PATH)
            .photoArchivePhoto(UPDATED_PHOTO_ARCHIVE_PHOTO)
            .photoArchivePhotoContentType(UPDATED_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE)
            .photoArchiveDescription(UPDATED_PHOTO_ARCHIVE_DESCRIPTION)
            .photoArchiveParams(UPDATED_PHOTO_ARCHIVE_PARAMS)
            .photoArchiveAttributs(UPDATED_PHOTO_ARCHIVE_ATTRIBUTS)
            .photoArchiveStat(UPDATED_PHOTO_ARCHIVE_STAT);

        restPhotoArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotoArchive.getPhotoArchiveId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhotoArchive))
            )
            .andExpect(status().isOk());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
        PhotoArchive testPhotoArchive = photoArchiveList.get(photoArchiveList.size() - 1);
        assertThat(testPhotoArchive.getPhotoArchiveName()).isEqualTo(UPDATED_PHOTO_ARCHIVE_NAME);
        assertThat(testPhotoArchive.getPhotoArchivePath()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PATH);
        assertThat(testPhotoArchive.getPhotoArchivePhoto()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PHOTO);
        assertThat(testPhotoArchive.getPhotoArchivePhotoContentType()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PHOTO_CONTENT_TYPE);
        assertThat(testPhotoArchive.getPhotoArchiveDescription()).isEqualTo(UPDATED_PHOTO_ARCHIVE_DESCRIPTION);
        assertThat(testPhotoArchive.getPhotoArchiveParams()).isEqualTo(UPDATED_PHOTO_ARCHIVE_PARAMS);
        assertThat(testPhotoArchive.getPhotoArchiveAttributs()).isEqualTo(UPDATED_PHOTO_ARCHIVE_ATTRIBUTS);
        assertThat(testPhotoArchive.getPhotoArchiveStat()).isEqualTo(UPDATED_PHOTO_ARCHIVE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingPhotoArchive() throws Exception {
        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();
        photoArchive.setPhotoArchiveId(count.incrementAndGet());

        // Create the PhotoArchive
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, photoArchiveDTO.getPhotoArchiveId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhotoArchive() throws Exception {
        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();
        photoArchive.setPhotoArchiveId(count.incrementAndGet());

        // Create the PhotoArchive
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhotoArchive() throws Exception {
        int databaseSizeBeforeUpdate = photoArchiveRepository.findAll().size();
        photoArchive.setPhotoArchiveId(count.incrementAndGet());

        // Create the PhotoArchive
        PhotoArchiveDTO photoArchiveDTO = photoArchiveMapper.toDto(photoArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photoArchiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhotoArchive in the database
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhotoArchive() throws Exception {
        // Initialize the database
        photoArchiveRepository.saveAndFlush(photoArchive);

        int databaseSizeBeforeDelete = photoArchiveRepository.findAll().size();

        // Delete the photoArchive
        restPhotoArchiveMockMvc
            .perform(delete(ENTITY_API_URL_ID, photoArchive.getPhotoArchiveId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhotoArchive> photoArchiveList = photoArchiveRepository.findAll();
        assertThat(photoArchiveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
