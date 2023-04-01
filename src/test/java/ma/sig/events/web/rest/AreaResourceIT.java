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
import ma.sig.events.domain.Area;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Fonction;
import ma.sig.events.repository.AreaRepository;
import ma.sig.events.service.criteria.AreaCriteria;
import ma.sig.events.service.dto.AreaDTO;
import ma.sig.events.service.mapper.AreaMapper;
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
 * Integration tests for the {@link AreaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AreaResourceIT {

    private static final String DEFAULT_AREA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AREA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_AREA_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_AREA_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_AREA_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_AREA_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AREA_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AREA_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AREA_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_AREA_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_AREA_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_AREA_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AREA_STAT = false;
    private static final Boolean UPDATED_AREA_STAT = true;

    private static final String ENTITY_API_URL = "/api/areas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{areaId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaMockMvc;

    private Area area;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Area createEntity(EntityManager em) {
        Area area = new Area()
            .areaName(DEFAULT_AREA_NAME)
            .areaAbreviation(DEFAULT_AREA_ABREVIATION)
            .areaColor(DEFAULT_AREA_COLOR)
            .areaDescription(DEFAULT_AREA_DESCRIPTION)
            .areaLogo(DEFAULT_AREA_LOGO)
            .areaLogoContentType(DEFAULT_AREA_LOGO_CONTENT_TYPE)
            .areaParams(DEFAULT_AREA_PARAMS)
            .areaAttributs(DEFAULT_AREA_ATTRIBUTS)
            .areaStat(DEFAULT_AREA_STAT);
        return area;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Area createUpdatedEntity(EntityManager em) {
        Area area = new Area()
            .areaName(UPDATED_AREA_NAME)
            .areaAbreviation(UPDATED_AREA_ABREVIATION)
            .areaColor(UPDATED_AREA_COLOR)
            .areaDescription(UPDATED_AREA_DESCRIPTION)
            .areaLogo(UPDATED_AREA_LOGO)
            .areaLogoContentType(UPDATED_AREA_LOGO_CONTENT_TYPE)
            .areaParams(UPDATED_AREA_PARAMS)
            .areaAttributs(UPDATED_AREA_ATTRIBUTS)
            .areaStat(UPDATED_AREA_STAT);
        return area;
    }

    @BeforeEach
    public void initTest() {
        area = createEntity(em);
    }

    @Test
    @Transactional
    void createArea() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();
        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);
        restAreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate + 1);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getAreaName()).isEqualTo(DEFAULT_AREA_NAME);
        assertThat(testArea.getAreaAbreviation()).isEqualTo(DEFAULT_AREA_ABREVIATION);
        assertThat(testArea.getAreaColor()).isEqualTo(DEFAULT_AREA_COLOR);
        assertThat(testArea.getAreaDescription()).isEqualTo(DEFAULT_AREA_DESCRIPTION);
        assertThat(testArea.getAreaLogo()).isEqualTo(DEFAULT_AREA_LOGO);
        assertThat(testArea.getAreaLogoContentType()).isEqualTo(DEFAULT_AREA_LOGO_CONTENT_TYPE);
        assertThat(testArea.getAreaParams()).isEqualTo(DEFAULT_AREA_PARAMS);
        assertThat(testArea.getAreaAttributs()).isEqualTo(DEFAULT_AREA_ATTRIBUTS);
        assertThat(testArea.getAreaStat()).isEqualTo(DEFAULT_AREA_STAT);
    }

    @Test
    @Transactional
    void createAreaWithExistingId() throws Exception {
        // Create the Area with an existing ID
        area.setAreaId(1L);
        AreaDTO areaDTO = areaMapper.toDto(area);

        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAreaNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaRepository.findAll().size();
        // set the field null
        area.setAreaName(null);

        // Create the Area, which fails.
        AreaDTO areaDTO = areaMapper.toDto(area);

        restAreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isBadRequest());

        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAreas() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList
        restAreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=areaId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].areaId").value(hasItem(area.getAreaId().intValue())))
            .andExpect(jsonPath("$.[*].areaName").value(hasItem(DEFAULT_AREA_NAME)))
            .andExpect(jsonPath("$.[*].areaAbreviation").value(hasItem(DEFAULT_AREA_ABREVIATION)))
            .andExpect(jsonPath("$.[*].areaColor").value(hasItem(DEFAULT_AREA_COLOR)))
            .andExpect(jsonPath("$.[*].areaDescription").value(hasItem(DEFAULT_AREA_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].areaLogoContentType").value(hasItem(DEFAULT_AREA_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].areaLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_AREA_LOGO))))
            .andExpect(jsonPath("$.[*].areaParams").value(hasItem(DEFAULT_AREA_PARAMS)))
            .andExpect(jsonPath("$.[*].areaAttributs").value(hasItem(DEFAULT_AREA_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].areaStat").value(hasItem(DEFAULT_AREA_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get the area
        restAreaMockMvc
            .perform(get(ENTITY_API_URL_ID, area.getAreaId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.areaId").value(area.getAreaId().intValue()))
            .andExpect(jsonPath("$.areaName").value(DEFAULT_AREA_NAME))
            .andExpect(jsonPath("$.areaAbreviation").value(DEFAULT_AREA_ABREVIATION))
            .andExpect(jsonPath("$.areaColor").value(DEFAULT_AREA_COLOR))
            .andExpect(jsonPath("$.areaDescription").value(DEFAULT_AREA_DESCRIPTION))
            .andExpect(jsonPath("$.areaLogoContentType").value(DEFAULT_AREA_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.areaLogo").value(Base64Utils.encodeToString(DEFAULT_AREA_LOGO)))
            .andExpect(jsonPath("$.areaParams").value(DEFAULT_AREA_PARAMS))
            .andExpect(jsonPath("$.areaAttributs").value(DEFAULT_AREA_ATTRIBUTS))
            .andExpect(jsonPath("$.areaStat").value(DEFAULT_AREA_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getAreasByIdFiltering() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        Long id = area.getAreaId();

        defaultAreaShouldBeFound("areaId.equals=" + id);
        defaultAreaShouldNotBeFound("areaId.notEquals=" + id);

        defaultAreaShouldBeFound("areaId.greaterThanOrEqual=" + id);
        defaultAreaShouldNotBeFound("areaId.greaterThan=" + id);

        defaultAreaShouldBeFound("areaId.lessThanOrEqual=" + id);
        defaultAreaShouldNotBeFound("areaId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAreasByAreaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName equals to DEFAULT_AREA_NAME
        defaultAreaShouldBeFound("areaName.equals=" + DEFAULT_AREA_NAME);

        // Get all the areaList where areaName equals to UPDATED_AREA_NAME
        defaultAreaShouldNotBeFound("areaName.equals=" + UPDATED_AREA_NAME);
    }

    @Test
    @Transactional
    void getAllAreasByAreaNameIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName in DEFAULT_AREA_NAME or UPDATED_AREA_NAME
        defaultAreaShouldBeFound("areaName.in=" + DEFAULT_AREA_NAME + "," + UPDATED_AREA_NAME);

        // Get all the areaList where areaName equals to UPDATED_AREA_NAME
        defaultAreaShouldNotBeFound("areaName.in=" + UPDATED_AREA_NAME);
    }

    @Test
    @Transactional
    void getAllAreasByAreaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName is not null
        defaultAreaShouldBeFound("areaName.specified=true");

        // Get all the areaList where areaName is null
        defaultAreaShouldNotBeFound("areaName.specified=false");
    }

    @Test
    @Transactional
    void getAllAreasByAreaNameContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName contains DEFAULT_AREA_NAME
        defaultAreaShouldBeFound("areaName.contains=" + DEFAULT_AREA_NAME);

        // Get all the areaList where areaName contains UPDATED_AREA_NAME
        defaultAreaShouldNotBeFound("areaName.contains=" + UPDATED_AREA_NAME);
    }

    @Test
    @Transactional
    void getAllAreasByAreaNameNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName does not contain DEFAULT_AREA_NAME
        defaultAreaShouldNotBeFound("areaName.doesNotContain=" + DEFAULT_AREA_NAME);

        // Get all the areaList where areaName does not contain UPDATED_AREA_NAME
        defaultAreaShouldBeFound("areaName.doesNotContain=" + UPDATED_AREA_NAME);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAbreviation equals to DEFAULT_AREA_ABREVIATION
        defaultAreaShouldBeFound("areaAbreviation.equals=" + DEFAULT_AREA_ABREVIATION);

        // Get all the areaList where areaAbreviation equals to UPDATED_AREA_ABREVIATION
        defaultAreaShouldNotBeFound("areaAbreviation.equals=" + UPDATED_AREA_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAbreviation in DEFAULT_AREA_ABREVIATION or UPDATED_AREA_ABREVIATION
        defaultAreaShouldBeFound("areaAbreviation.in=" + DEFAULT_AREA_ABREVIATION + "," + UPDATED_AREA_ABREVIATION);

        // Get all the areaList where areaAbreviation equals to UPDATED_AREA_ABREVIATION
        defaultAreaShouldNotBeFound("areaAbreviation.in=" + UPDATED_AREA_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAbreviation is not null
        defaultAreaShouldBeFound("areaAbreviation.specified=true");

        // Get all the areaList where areaAbreviation is null
        defaultAreaShouldNotBeFound("areaAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllAreasByAreaAbreviationContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAbreviation contains DEFAULT_AREA_ABREVIATION
        defaultAreaShouldBeFound("areaAbreviation.contains=" + DEFAULT_AREA_ABREVIATION);

        // Get all the areaList where areaAbreviation contains UPDATED_AREA_ABREVIATION
        defaultAreaShouldNotBeFound("areaAbreviation.contains=" + UPDATED_AREA_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAbreviation does not contain DEFAULT_AREA_ABREVIATION
        defaultAreaShouldNotBeFound("areaAbreviation.doesNotContain=" + DEFAULT_AREA_ABREVIATION);

        // Get all the areaList where areaAbreviation does not contain UPDATED_AREA_ABREVIATION
        defaultAreaShouldBeFound("areaAbreviation.doesNotContain=" + UPDATED_AREA_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaColorIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaColor equals to DEFAULT_AREA_COLOR
        defaultAreaShouldBeFound("areaColor.equals=" + DEFAULT_AREA_COLOR);

        // Get all the areaList where areaColor equals to UPDATED_AREA_COLOR
        defaultAreaShouldNotBeFound("areaColor.equals=" + UPDATED_AREA_COLOR);
    }

    @Test
    @Transactional
    void getAllAreasByAreaColorIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaColor in DEFAULT_AREA_COLOR or UPDATED_AREA_COLOR
        defaultAreaShouldBeFound("areaColor.in=" + DEFAULT_AREA_COLOR + "," + UPDATED_AREA_COLOR);

        // Get all the areaList where areaColor equals to UPDATED_AREA_COLOR
        defaultAreaShouldNotBeFound("areaColor.in=" + UPDATED_AREA_COLOR);
    }

    @Test
    @Transactional
    void getAllAreasByAreaColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaColor is not null
        defaultAreaShouldBeFound("areaColor.specified=true");

        // Get all the areaList where areaColor is null
        defaultAreaShouldNotBeFound("areaColor.specified=false");
    }

    @Test
    @Transactional
    void getAllAreasByAreaColorContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaColor contains DEFAULT_AREA_COLOR
        defaultAreaShouldBeFound("areaColor.contains=" + DEFAULT_AREA_COLOR);

        // Get all the areaList where areaColor contains UPDATED_AREA_COLOR
        defaultAreaShouldNotBeFound("areaColor.contains=" + UPDATED_AREA_COLOR);
    }

    @Test
    @Transactional
    void getAllAreasByAreaColorNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaColor does not contain DEFAULT_AREA_COLOR
        defaultAreaShouldNotBeFound("areaColor.doesNotContain=" + DEFAULT_AREA_COLOR);

        // Get all the areaList where areaColor does not contain UPDATED_AREA_COLOR
        defaultAreaShouldBeFound("areaColor.doesNotContain=" + UPDATED_AREA_COLOR);
    }

    @Test
    @Transactional
    void getAllAreasByAreaDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaDescription equals to DEFAULT_AREA_DESCRIPTION
        defaultAreaShouldBeFound("areaDescription.equals=" + DEFAULT_AREA_DESCRIPTION);

        // Get all the areaList where areaDescription equals to UPDATED_AREA_DESCRIPTION
        defaultAreaShouldNotBeFound("areaDescription.equals=" + UPDATED_AREA_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaDescription in DEFAULT_AREA_DESCRIPTION or UPDATED_AREA_DESCRIPTION
        defaultAreaShouldBeFound("areaDescription.in=" + DEFAULT_AREA_DESCRIPTION + "," + UPDATED_AREA_DESCRIPTION);

        // Get all the areaList where areaDescription equals to UPDATED_AREA_DESCRIPTION
        defaultAreaShouldNotBeFound("areaDescription.in=" + UPDATED_AREA_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaDescription is not null
        defaultAreaShouldBeFound("areaDescription.specified=true");

        // Get all the areaList where areaDescription is null
        defaultAreaShouldNotBeFound("areaDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllAreasByAreaDescriptionContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaDescription contains DEFAULT_AREA_DESCRIPTION
        defaultAreaShouldBeFound("areaDescription.contains=" + DEFAULT_AREA_DESCRIPTION);

        // Get all the areaList where areaDescription contains UPDATED_AREA_DESCRIPTION
        defaultAreaShouldNotBeFound("areaDescription.contains=" + UPDATED_AREA_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaDescription does not contain DEFAULT_AREA_DESCRIPTION
        defaultAreaShouldNotBeFound("areaDescription.doesNotContain=" + DEFAULT_AREA_DESCRIPTION);

        // Get all the areaList where areaDescription does not contain UPDATED_AREA_DESCRIPTION
        defaultAreaShouldBeFound("areaDescription.doesNotContain=" + UPDATED_AREA_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAreasByAreaParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaParams equals to DEFAULT_AREA_PARAMS
        defaultAreaShouldBeFound("areaParams.equals=" + DEFAULT_AREA_PARAMS);

        // Get all the areaList where areaParams equals to UPDATED_AREA_PARAMS
        defaultAreaShouldNotBeFound("areaParams.equals=" + UPDATED_AREA_PARAMS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaParamsIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaParams in DEFAULT_AREA_PARAMS or UPDATED_AREA_PARAMS
        defaultAreaShouldBeFound("areaParams.in=" + DEFAULT_AREA_PARAMS + "," + UPDATED_AREA_PARAMS);

        // Get all the areaList where areaParams equals to UPDATED_AREA_PARAMS
        defaultAreaShouldNotBeFound("areaParams.in=" + UPDATED_AREA_PARAMS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaParams is not null
        defaultAreaShouldBeFound("areaParams.specified=true");

        // Get all the areaList where areaParams is null
        defaultAreaShouldNotBeFound("areaParams.specified=false");
    }

    @Test
    @Transactional
    void getAllAreasByAreaParamsContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaParams contains DEFAULT_AREA_PARAMS
        defaultAreaShouldBeFound("areaParams.contains=" + DEFAULT_AREA_PARAMS);

        // Get all the areaList where areaParams contains UPDATED_AREA_PARAMS
        defaultAreaShouldNotBeFound("areaParams.contains=" + UPDATED_AREA_PARAMS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaParamsNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaParams does not contain DEFAULT_AREA_PARAMS
        defaultAreaShouldNotBeFound("areaParams.doesNotContain=" + DEFAULT_AREA_PARAMS);

        // Get all the areaList where areaParams does not contain UPDATED_AREA_PARAMS
        defaultAreaShouldBeFound("areaParams.doesNotContain=" + UPDATED_AREA_PARAMS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAttributs equals to DEFAULT_AREA_ATTRIBUTS
        defaultAreaShouldBeFound("areaAttributs.equals=" + DEFAULT_AREA_ATTRIBUTS);

        // Get all the areaList where areaAttributs equals to UPDATED_AREA_ATTRIBUTS
        defaultAreaShouldNotBeFound("areaAttributs.equals=" + UPDATED_AREA_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAttributs in DEFAULT_AREA_ATTRIBUTS or UPDATED_AREA_ATTRIBUTS
        defaultAreaShouldBeFound("areaAttributs.in=" + DEFAULT_AREA_ATTRIBUTS + "," + UPDATED_AREA_ATTRIBUTS);

        // Get all the areaList where areaAttributs equals to UPDATED_AREA_ATTRIBUTS
        defaultAreaShouldNotBeFound("areaAttributs.in=" + UPDATED_AREA_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAttributs is not null
        defaultAreaShouldBeFound("areaAttributs.specified=true");

        // Get all the areaList where areaAttributs is null
        defaultAreaShouldNotBeFound("areaAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllAreasByAreaAttributsContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAttributs contains DEFAULT_AREA_ATTRIBUTS
        defaultAreaShouldBeFound("areaAttributs.contains=" + DEFAULT_AREA_ATTRIBUTS);

        // Get all the areaList where areaAttributs contains UPDATED_AREA_ATTRIBUTS
        defaultAreaShouldNotBeFound("areaAttributs.contains=" + UPDATED_AREA_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaAttributs does not contain DEFAULT_AREA_ATTRIBUTS
        defaultAreaShouldNotBeFound("areaAttributs.doesNotContain=" + DEFAULT_AREA_ATTRIBUTS);

        // Get all the areaList where areaAttributs does not contain UPDATED_AREA_ATTRIBUTS
        defaultAreaShouldBeFound("areaAttributs.doesNotContain=" + UPDATED_AREA_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAreasByAreaStatIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaStat equals to DEFAULT_AREA_STAT
        defaultAreaShouldBeFound("areaStat.equals=" + DEFAULT_AREA_STAT);

        // Get all the areaList where areaStat equals to UPDATED_AREA_STAT
        defaultAreaShouldNotBeFound("areaStat.equals=" + UPDATED_AREA_STAT);
    }

    @Test
    @Transactional
    void getAllAreasByAreaStatIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaStat in DEFAULT_AREA_STAT or UPDATED_AREA_STAT
        defaultAreaShouldBeFound("areaStat.in=" + DEFAULT_AREA_STAT + "," + UPDATED_AREA_STAT);

        // Get all the areaList where areaStat equals to UPDATED_AREA_STAT
        defaultAreaShouldNotBeFound("areaStat.in=" + UPDATED_AREA_STAT);
    }

    @Test
    @Transactional
    void getAllAreasByAreaStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaStat is not null
        defaultAreaShouldBeFound("areaStat.specified=true");

        // Get all the areaList where areaStat is null
        defaultAreaShouldNotBeFound("areaStat.specified=false");
    }

    @Test
    @Transactional
    void getAllAreasByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            areaRepository.saveAndFlush(area);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        area.setEvent(event);
        areaRepository.saveAndFlush(area);
        Long eventId = event.getEventId();

        // Get all the areaList where event equals to eventId
        defaultAreaShouldBeFound("eventId.equals=" + eventId);

        // Get all the areaList where event equals to (eventId + 1)
        defaultAreaShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllAreasByFonctionIsEqualToSomething() throws Exception {
        Fonction fonction;
        if (TestUtil.findAll(em, Fonction.class).isEmpty()) {
            areaRepository.saveAndFlush(area);
            fonction = FonctionResourceIT.createEntity(em);
        } else {
            fonction = TestUtil.findAll(em, Fonction.class).get(0);
        }
        em.persist(fonction);
        em.flush();
        area.addFonction(fonction);
        areaRepository.saveAndFlush(area);
        Long fonctionId = fonction.getFonctionId();

        // Get all the areaList where fonction equals to fonctionId
        defaultAreaShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the areaList where fonction equals to (fonctionId + 1)
        defaultAreaShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAreaShouldBeFound(String filter) throws Exception {
        restAreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=areaId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].areaId").value(hasItem(area.getAreaId().intValue())))
            .andExpect(jsonPath("$.[*].areaName").value(hasItem(DEFAULT_AREA_NAME)))
            .andExpect(jsonPath("$.[*].areaAbreviation").value(hasItem(DEFAULT_AREA_ABREVIATION)))
            .andExpect(jsonPath("$.[*].areaColor").value(hasItem(DEFAULT_AREA_COLOR)))
            .andExpect(jsonPath("$.[*].areaDescription").value(hasItem(DEFAULT_AREA_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].areaLogoContentType").value(hasItem(DEFAULT_AREA_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].areaLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_AREA_LOGO))))
            .andExpect(jsonPath("$.[*].areaParams").value(hasItem(DEFAULT_AREA_PARAMS)))
            .andExpect(jsonPath("$.[*].areaAttributs").value(hasItem(DEFAULT_AREA_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].areaStat").value(hasItem(DEFAULT_AREA_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restAreaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=areaId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAreaShouldNotBeFound(String filter) throws Exception {
        restAreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=areaId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAreaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=areaId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArea() throws Exception {
        // Get the area
        restAreaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area
        Area updatedArea = areaRepository.findById(area.getAreaId()).get();
        // Disconnect from session so that the updates on updatedArea are not directly saved in db
        em.detach(updatedArea);
        updatedArea
            .areaName(UPDATED_AREA_NAME)
            .areaAbreviation(UPDATED_AREA_ABREVIATION)
            .areaColor(UPDATED_AREA_COLOR)
            .areaDescription(UPDATED_AREA_DESCRIPTION)
            .areaLogo(UPDATED_AREA_LOGO)
            .areaLogoContentType(UPDATED_AREA_LOGO_CONTENT_TYPE)
            .areaParams(UPDATED_AREA_PARAMS)
            .areaAttributs(UPDATED_AREA_ATTRIBUTS)
            .areaStat(UPDATED_AREA_STAT);
        AreaDTO areaDTO = areaMapper.toDto(updatedArea);

        restAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaDTO.getAreaId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getAreaName()).isEqualTo(UPDATED_AREA_NAME);
        assertThat(testArea.getAreaAbreviation()).isEqualTo(UPDATED_AREA_ABREVIATION);
        assertThat(testArea.getAreaColor()).isEqualTo(UPDATED_AREA_COLOR);
        assertThat(testArea.getAreaDescription()).isEqualTo(UPDATED_AREA_DESCRIPTION);
        assertThat(testArea.getAreaLogo()).isEqualTo(UPDATED_AREA_LOGO);
        assertThat(testArea.getAreaLogoContentType()).isEqualTo(UPDATED_AREA_LOGO_CONTENT_TYPE);
        assertThat(testArea.getAreaParams()).isEqualTo(UPDATED_AREA_PARAMS);
        assertThat(testArea.getAreaAttributs()).isEqualTo(UPDATED_AREA_ATTRIBUTS);
        assertThat(testArea.getAreaStat()).isEqualTo(UPDATED_AREA_STAT);
    }

    @Test
    @Transactional
    void putNonExistingArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();
        area.setAreaId(count.incrementAndGet());

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaDTO.getAreaId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();
        area.setAreaId(count.incrementAndGet());

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();
        area.setAreaId(count.incrementAndGet());

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaWithPatch() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area using partial update
        Area partialUpdatedArea = new Area();
        partialUpdatedArea.setAreaId(area.getAreaId());

        partialUpdatedArea
            .areaName(UPDATED_AREA_NAME)
            .areaAbreviation(UPDATED_AREA_ABREVIATION)
            .areaDescription(UPDATED_AREA_DESCRIPTION)
            .areaLogo(UPDATED_AREA_LOGO)
            .areaLogoContentType(UPDATED_AREA_LOGO_CONTENT_TYPE)
            .areaParams(UPDATED_AREA_PARAMS)
            .areaAttributs(UPDATED_AREA_ATTRIBUTS);

        restAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArea.getAreaId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArea))
            )
            .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getAreaName()).isEqualTo(UPDATED_AREA_NAME);
        assertThat(testArea.getAreaAbreviation()).isEqualTo(UPDATED_AREA_ABREVIATION);
        assertThat(testArea.getAreaColor()).isEqualTo(DEFAULT_AREA_COLOR);
        assertThat(testArea.getAreaDescription()).isEqualTo(UPDATED_AREA_DESCRIPTION);
        assertThat(testArea.getAreaLogo()).isEqualTo(UPDATED_AREA_LOGO);
        assertThat(testArea.getAreaLogoContentType()).isEqualTo(UPDATED_AREA_LOGO_CONTENT_TYPE);
        assertThat(testArea.getAreaParams()).isEqualTo(UPDATED_AREA_PARAMS);
        assertThat(testArea.getAreaAttributs()).isEqualTo(UPDATED_AREA_ATTRIBUTS);
        assertThat(testArea.getAreaStat()).isEqualTo(DEFAULT_AREA_STAT);
    }

    @Test
    @Transactional
    void fullUpdateAreaWithPatch() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area using partial update
        Area partialUpdatedArea = new Area();
        partialUpdatedArea.setAreaId(area.getAreaId());

        partialUpdatedArea
            .areaName(UPDATED_AREA_NAME)
            .areaAbreviation(UPDATED_AREA_ABREVIATION)
            .areaColor(UPDATED_AREA_COLOR)
            .areaDescription(UPDATED_AREA_DESCRIPTION)
            .areaLogo(UPDATED_AREA_LOGO)
            .areaLogoContentType(UPDATED_AREA_LOGO_CONTENT_TYPE)
            .areaParams(UPDATED_AREA_PARAMS)
            .areaAttributs(UPDATED_AREA_ATTRIBUTS)
            .areaStat(UPDATED_AREA_STAT);

        restAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArea.getAreaId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArea))
            )
            .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getAreaName()).isEqualTo(UPDATED_AREA_NAME);
        assertThat(testArea.getAreaAbreviation()).isEqualTo(UPDATED_AREA_ABREVIATION);
        assertThat(testArea.getAreaColor()).isEqualTo(UPDATED_AREA_COLOR);
        assertThat(testArea.getAreaDescription()).isEqualTo(UPDATED_AREA_DESCRIPTION);
        assertThat(testArea.getAreaLogo()).isEqualTo(UPDATED_AREA_LOGO);
        assertThat(testArea.getAreaLogoContentType()).isEqualTo(UPDATED_AREA_LOGO_CONTENT_TYPE);
        assertThat(testArea.getAreaParams()).isEqualTo(UPDATED_AREA_PARAMS);
        assertThat(testArea.getAreaAttributs()).isEqualTo(UPDATED_AREA_ATTRIBUTS);
        assertThat(testArea.getAreaStat()).isEqualTo(UPDATED_AREA_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();
        area.setAreaId(count.incrementAndGet());

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaDTO.getAreaId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();
        area.setAreaId(count.incrementAndGet());

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();
        area.setAreaId(count.incrementAndGet());

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        int databaseSizeBeforeDelete = areaRepository.findAll().size();

        // Delete the area
        restAreaMockMvc
            .perform(delete(ENTITY_API_URL_ID, area.getAreaId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
