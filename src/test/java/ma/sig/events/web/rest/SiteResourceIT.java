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
import ma.sig.events.domain.City;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Site;
import ma.sig.events.repository.SiteRepository;
import ma.sig.events.service.criteria.SiteCriteria;
import ma.sig.events.service.dto.SiteDTO;
import ma.sig.events.service.mapper.SiteMapper;
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
 * Integration tests for the {@link SiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteResourceIT {

    private static final String DEFAULT_SITE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SITE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_SITE_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_SITE_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SITE_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SITE_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SITE_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SITE_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SITE_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SITE_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_SITE_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_TEL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_FAX = "AAAAAAAAAA";
    private static final String UPDATED_SITE_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_RESPONSABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SITE_RESPONSABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_SITE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_SITE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SITE_STAT = false;
    private static final Boolean UPDATED_SITE_STAT = true;

    private static final String ENTITY_API_URL = "/api/sites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{siteId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteMockMvc;

    private Site site;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createEntity(EntityManager em) {
        Site site = new Site()
            .siteName(DEFAULT_SITE_NAME)
            .siteColor(DEFAULT_SITE_COLOR)
            .siteAbreviation(DEFAULT_SITE_ABREVIATION)
            .siteDescription(DEFAULT_SITE_DESCRIPTION)
            .siteLogo(DEFAULT_SITE_LOGO)
            .siteLogoContentType(DEFAULT_SITE_LOGO_CONTENT_TYPE)
            .siteAdresse(DEFAULT_SITE_ADRESSE)
            .siteEmail(DEFAULT_SITE_EMAIL)
            .siteTel(DEFAULT_SITE_TEL)
            .siteFax(DEFAULT_SITE_FAX)
            .siteResponsableName(DEFAULT_SITE_RESPONSABLE_NAME)
            .siteParams(DEFAULT_SITE_PARAMS)
            .siteAttributs(DEFAULT_SITE_ATTRIBUTS)
            .siteStat(DEFAULT_SITE_STAT);
        return site;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createUpdatedEntity(EntityManager em) {
        Site site = new Site()
            .siteName(UPDATED_SITE_NAME)
            .siteColor(UPDATED_SITE_COLOR)
            .siteAbreviation(UPDATED_SITE_ABREVIATION)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .siteLogo(UPDATED_SITE_LOGO)
            .siteLogoContentType(UPDATED_SITE_LOGO_CONTENT_TYPE)
            .siteAdresse(UPDATED_SITE_ADRESSE)
            .siteEmail(UPDATED_SITE_EMAIL)
            .siteTel(UPDATED_SITE_TEL)
            .siteFax(UPDATED_SITE_FAX)
            .siteResponsableName(UPDATED_SITE_RESPONSABLE_NAME)
            .siteParams(UPDATED_SITE_PARAMS)
            .siteAttributs(UPDATED_SITE_ATTRIBUTS)
            .siteStat(UPDATED_SITE_STAT);
        return site;
    }

    @BeforeEach
    public void initTest() {
        site = createEntity(em);
    }

    @Test
    @Transactional
    void createSite() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();
        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);
        restSiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isCreated());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate + 1);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getSiteName()).isEqualTo(DEFAULT_SITE_NAME);
        assertThat(testSite.getSiteColor()).isEqualTo(DEFAULT_SITE_COLOR);
        assertThat(testSite.getSiteAbreviation()).isEqualTo(DEFAULT_SITE_ABREVIATION);
        assertThat(testSite.getSiteDescription()).isEqualTo(DEFAULT_SITE_DESCRIPTION);
        assertThat(testSite.getSiteLogo()).isEqualTo(DEFAULT_SITE_LOGO);
        assertThat(testSite.getSiteLogoContentType()).isEqualTo(DEFAULT_SITE_LOGO_CONTENT_TYPE);
        assertThat(testSite.getSiteAdresse()).isEqualTo(DEFAULT_SITE_ADRESSE);
        assertThat(testSite.getSiteEmail()).isEqualTo(DEFAULT_SITE_EMAIL);
        assertThat(testSite.getSiteTel()).isEqualTo(DEFAULT_SITE_TEL);
        assertThat(testSite.getSiteFax()).isEqualTo(DEFAULT_SITE_FAX);
        assertThat(testSite.getSiteResponsableName()).isEqualTo(DEFAULT_SITE_RESPONSABLE_NAME);
        assertThat(testSite.getSiteParams()).isEqualTo(DEFAULT_SITE_PARAMS);
        assertThat(testSite.getSiteAttributs()).isEqualTo(DEFAULT_SITE_ATTRIBUTS);
        assertThat(testSite.getSiteStat()).isEqualTo(DEFAULT_SITE_STAT);
    }

    @Test
    @Transactional
    void createSiteWithExistingId() throws Exception {
        // Create the Site with an existing ID
        site.setSiteId(1L);
        SiteDTO siteDTO = siteMapper.toDto(site);

        int databaseSizeBeforeCreate = siteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSiteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteRepository.findAll().size();
        // set the field null
        site.setSiteName(null);

        // Create the Site, which fails.
        SiteDTO siteDTO = siteMapper.toDto(site);

        restSiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSites() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=siteId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(site.getSiteId().intValue())))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME)))
            .andExpect(jsonPath("$.[*].siteColor").value(hasItem(DEFAULT_SITE_COLOR)))
            .andExpect(jsonPath("$.[*].siteAbreviation").value(hasItem(DEFAULT_SITE_ABREVIATION)))
            .andExpect(jsonPath("$.[*].siteDescription").value(hasItem(DEFAULT_SITE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].siteLogoContentType").value(hasItem(DEFAULT_SITE_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].siteLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_SITE_LOGO))))
            .andExpect(jsonPath("$.[*].siteAdresse").value(hasItem(DEFAULT_SITE_ADRESSE)))
            .andExpect(jsonPath("$.[*].siteEmail").value(hasItem(DEFAULT_SITE_EMAIL)))
            .andExpect(jsonPath("$.[*].siteTel").value(hasItem(DEFAULT_SITE_TEL)))
            .andExpect(jsonPath("$.[*].siteFax").value(hasItem(DEFAULT_SITE_FAX)))
            .andExpect(jsonPath("$.[*].siteResponsableName").value(hasItem(DEFAULT_SITE_RESPONSABLE_NAME)))
            .andExpect(jsonPath("$.[*].siteParams").value(hasItem(DEFAULT_SITE_PARAMS)))
            .andExpect(jsonPath("$.[*].siteAttributs").value(hasItem(DEFAULT_SITE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].siteStat").value(hasItem(DEFAULT_SITE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get the site
        restSiteMockMvc
            .perform(get(ENTITY_API_URL_ID, site.getSiteId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.siteId").value(site.getSiteId().intValue()))
            .andExpect(jsonPath("$.siteName").value(DEFAULT_SITE_NAME))
            .andExpect(jsonPath("$.siteColor").value(DEFAULT_SITE_COLOR))
            .andExpect(jsonPath("$.siteAbreviation").value(DEFAULT_SITE_ABREVIATION))
            .andExpect(jsonPath("$.siteDescription").value(DEFAULT_SITE_DESCRIPTION))
            .andExpect(jsonPath("$.siteLogoContentType").value(DEFAULT_SITE_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.siteLogo").value(Base64Utils.encodeToString(DEFAULT_SITE_LOGO)))
            .andExpect(jsonPath("$.siteAdresse").value(DEFAULT_SITE_ADRESSE))
            .andExpect(jsonPath("$.siteEmail").value(DEFAULT_SITE_EMAIL))
            .andExpect(jsonPath("$.siteTel").value(DEFAULT_SITE_TEL))
            .andExpect(jsonPath("$.siteFax").value(DEFAULT_SITE_FAX))
            .andExpect(jsonPath("$.siteResponsableName").value(DEFAULT_SITE_RESPONSABLE_NAME))
            .andExpect(jsonPath("$.siteParams").value(DEFAULT_SITE_PARAMS))
            .andExpect(jsonPath("$.siteAttributs").value(DEFAULT_SITE_ATTRIBUTS))
            .andExpect(jsonPath("$.siteStat").value(DEFAULT_SITE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getSitesByIdFiltering() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        Long id = site.getSiteId();

        defaultSiteShouldBeFound("siteId.equals=" + id);
        defaultSiteShouldNotBeFound("siteId.notEquals=" + id);

        defaultSiteShouldBeFound("siteId.greaterThanOrEqual=" + id);
        defaultSiteShouldNotBeFound("siteId.greaterThan=" + id);

        defaultSiteShouldBeFound("siteId.lessThanOrEqual=" + id);
        defaultSiteShouldNotBeFound("siteId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSitesBySiteNameIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteName equals to DEFAULT_SITE_NAME
        defaultSiteShouldBeFound("siteName.equals=" + DEFAULT_SITE_NAME);

        // Get all the siteList where siteName equals to UPDATED_SITE_NAME
        defaultSiteShouldNotBeFound("siteName.equals=" + UPDATED_SITE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteNameIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteName in DEFAULT_SITE_NAME or UPDATED_SITE_NAME
        defaultSiteShouldBeFound("siteName.in=" + DEFAULT_SITE_NAME + "," + UPDATED_SITE_NAME);

        // Get all the siteList where siteName equals to UPDATED_SITE_NAME
        defaultSiteShouldNotBeFound("siteName.in=" + UPDATED_SITE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteName is not null
        defaultSiteShouldBeFound("siteName.specified=true");

        // Get all the siteList where siteName is null
        defaultSiteShouldNotBeFound("siteName.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteNameContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteName contains DEFAULT_SITE_NAME
        defaultSiteShouldBeFound("siteName.contains=" + DEFAULT_SITE_NAME);

        // Get all the siteList where siteName contains UPDATED_SITE_NAME
        defaultSiteShouldNotBeFound("siteName.contains=" + UPDATED_SITE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteNameNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteName does not contain DEFAULT_SITE_NAME
        defaultSiteShouldNotBeFound("siteName.doesNotContain=" + DEFAULT_SITE_NAME);

        // Get all the siteList where siteName does not contain UPDATED_SITE_NAME
        defaultSiteShouldBeFound("siteName.doesNotContain=" + UPDATED_SITE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteColorIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteColor equals to DEFAULT_SITE_COLOR
        defaultSiteShouldBeFound("siteColor.equals=" + DEFAULT_SITE_COLOR);

        // Get all the siteList where siteColor equals to UPDATED_SITE_COLOR
        defaultSiteShouldNotBeFound("siteColor.equals=" + UPDATED_SITE_COLOR);
    }

    @Test
    @Transactional
    void getAllSitesBySiteColorIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteColor in DEFAULT_SITE_COLOR or UPDATED_SITE_COLOR
        defaultSiteShouldBeFound("siteColor.in=" + DEFAULT_SITE_COLOR + "," + UPDATED_SITE_COLOR);

        // Get all the siteList where siteColor equals to UPDATED_SITE_COLOR
        defaultSiteShouldNotBeFound("siteColor.in=" + UPDATED_SITE_COLOR);
    }

    @Test
    @Transactional
    void getAllSitesBySiteColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteColor is not null
        defaultSiteShouldBeFound("siteColor.specified=true");

        // Get all the siteList where siteColor is null
        defaultSiteShouldNotBeFound("siteColor.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteColorContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteColor contains DEFAULT_SITE_COLOR
        defaultSiteShouldBeFound("siteColor.contains=" + DEFAULT_SITE_COLOR);

        // Get all the siteList where siteColor contains UPDATED_SITE_COLOR
        defaultSiteShouldNotBeFound("siteColor.contains=" + UPDATED_SITE_COLOR);
    }

    @Test
    @Transactional
    void getAllSitesBySiteColorNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteColor does not contain DEFAULT_SITE_COLOR
        defaultSiteShouldNotBeFound("siteColor.doesNotContain=" + DEFAULT_SITE_COLOR);

        // Get all the siteList where siteColor does not contain UPDATED_SITE_COLOR
        defaultSiteShouldBeFound("siteColor.doesNotContain=" + UPDATED_SITE_COLOR);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAbreviation equals to DEFAULT_SITE_ABREVIATION
        defaultSiteShouldBeFound("siteAbreviation.equals=" + DEFAULT_SITE_ABREVIATION);

        // Get all the siteList where siteAbreviation equals to UPDATED_SITE_ABREVIATION
        defaultSiteShouldNotBeFound("siteAbreviation.equals=" + UPDATED_SITE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAbreviation in DEFAULT_SITE_ABREVIATION or UPDATED_SITE_ABREVIATION
        defaultSiteShouldBeFound("siteAbreviation.in=" + DEFAULT_SITE_ABREVIATION + "," + UPDATED_SITE_ABREVIATION);

        // Get all the siteList where siteAbreviation equals to UPDATED_SITE_ABREVIATION
        defaultSiteShouldNotBeFound("siteAbreviation.in=" + UPDATED_SITE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAbreviation is not null
        defaultSiteShouldBeFound("siteAbreviation.specified=true");

        // Get all the siteList where siteAbreviation is null
        defaultSiteShouldNotBeFound("siteAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteAbreviationContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAbreviation contains DEFAULT_SITE_ABREVIATION
        defaultSiteShouldBeFound("siteAbreviation.contains=" + DEFAULT_SITE_ABREVIATION);

        // Get all the siteList where siteAbreviation contains UPDATED_SITE_ABREVIATION
        defaultSiteShouldNotBeFound("siteAbreviation.contains=" + UPDATED_SITE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAbreviation does not contain DEFAULT_SITE_ABREVIATION
        defaultSiteShouldNotBeFound("siteAbreviation.doesNotContain=" + DEFAULT_SITE_ABREVIATION);

        // Get all the siteList where siteAbreviation does not contain UPDATED_SITE_ABREVIATION
        defaultSiteShouldBeFound("siteAbreviation.doesNotContain=" + UPDATED_SITE_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteDescription equals to DEFAULT_SITE_DESCRIPTION
        defaultSiteShouldBeFound("siteDescription.equals=" + DEFAULT_SITE_DESCRIPTION);

        // Get all the siteList where siteDescription equals to UPDATED_SITE_DESCRIPTION
        defaultSiteShouldNotBeFound("siteDescription.equals=" + UPDATED_SITE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteDescription in DEFAULT_SITE_DESCRIPTION or UPDATED_SITE_DESCRIPTION
        defaultSiteShouldBeFound("siteDescription.in=" + DEFAULT_SITE_DESCRIPTION + "," + UPDATED_SITE_DESCRIPTION);

        // Get all the siteList where siteDescription equals to UPDATED_SITE_DESCRIPTION
        defaultSiteShouldNotBeFound("siteDescription.in=" + UPDATED_SITE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteDescription is not null
        defaultSiteShouldBeFound("siteDescription.specified=true");

        // Get all the siteList where siteDescription is null
        defaultSiteShouldNotBeFound("siteDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteDescriptionContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteDescription contains DEFAULT_SITE_DESCRIPTION
        defaultSiteShouldBeFound("siteDescription.contains=" + DEFAULT_SITE_DESCRIPTION);

        // Get all the siteList where siteDescription contains UPDATED_SITE_DESCRIPTION
        defaultSiteShouldNotBeFound("siteDescription.contains=" + UPDATED_SITE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteDescription does not contain DEFAULT_SITE_DESCRIPTION
        defaultSiteShouldNotBeFound("siteDescription.doesNotContain=" + DEFAULT_SITE_DESCRIPTION);

        // Get all the siteList where siteDescription does not contain UPDATED_SITE_DESCRIPTION
        defaultSiteShouldBeFound("siteDescription.doesNotContain=" + UPDATED_SITE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAdresse equals to DEFAULT_SITE_ADRESSE
        defaultSiteShouldBeFound("siteAdresse.equals=" + DEFAULT_SITE_ADRESSE);

        // Get all the siteList where siteAdresse equals to UPDATED_SITE_ADRESSE
        defaultSiteShouldNotBeFound("siteAdresse.equals=" + UPDATED_SITE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAdresse in DEFAULT_SITE_ADRESSE or UPDATED_SITE_ADRESSE
        defaultSiteShouldBeFound("siteAdresse.in=" + DEFAULT_SITE_ADRESSE + "," + UPDATED_SITE_ADRESSE);

        // Get all the siteList where siteAdresse equals to UPDATED_SITE_ADRESSE
        defaultSiteShouldNotBeFound("siteAdresse.in=" + UPDATED_SITE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAdresse is not null
        defaultSiteShouldBeFound("siteAdresse.specified=true");

        // Get all the siteList where siteAdresse is null
        defaultSiteShouldNotBeFound("siteAdresse.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteAdresseContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAdresse contains DEFAULT_SITE_ADRESSE
        defaultSiteShouldBeFound("siteAdresse.contains=" + DEFAULT_SITE_ADRESSE);

        // Get all the siteList where siteAdresse contains UPDATED_SITE_ADRESSE
        defaultSiteShouldNotBeFound("siteAdresse.contains=" + UPDATED_SITE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAdresse does not contain DEFAULT_SITE_ADRESSE
        defaultSiteShouldNotBeFound("siteAdresse.doesNotContain=" + DEFAULT_SITE_ADRESSE);

        // Get all the siteList where siteAdresse does not contain UPDATED_SITE_ADRESSE
        defaultSiteShouldBeFound("siteAdresse.doesNotContain=" + UPDATED_SITE_ADRESSE);
    }

    @Test
    @Transactional
    void getAllSitesBySiteEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteEmail equals to DEFAULT_SITE_EMAIL
        defaultSiteShouldBeFound("siteEmail.equals=" + DEFAULT_SITE_EMAIL);

        // Get all the siteList where siteEmail equals to UPDATED_SITE_EMAIL
        defaultSiteShouldNotBeFound("siteEmail.equals=" + UPDATED_SITE_EMAIL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteEmailIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteEmail in DEFAULT_SITE_EMAIL or UPDATED_SITE_EMAIL
        defaultSiteShouldBeFound("siteEmail.in=" + DEFAULT_SITE_EMAIL + "," + UPDATED_SITE_EMAIL);

        // Get all the siteList where siteEmail equals to UPDATED_SITE_EMAIL
        defaultSiteShouldNotBeFound("siteEmail.in=" + UPDATED_SITE_EMAIL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteEmail is not null
        defaultSiteShouldBeFound("siteEmail.specified=true");

        // Get all the siteList where siteEmail is null
        defaultSiteShouldNotBeFound("siteEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteEmailContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteEmail contains DEFAULT_SITE_EMAIL
        defaultSiteShouldBeFound("siteEmail.contains=" + DEFAULT_SITE_EMAIL);

        // Get all the siteList where siteEmail contains UPDATED_SITE_EMAIL
        defaultSiteShouldNotBeFound("siteEmail.contains=" + UPDATED_SITE_EMAIL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteEmailNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteEmail does not contain DEFAULT_SITE_EMAIL
        defaultSiteShouldNotBeFound("siteEmail.doesNotContain=" + DEFAULT_SITE_EMAIL);

        // Get all the siteList where siteEmail does not contain UPDATED_SITE_EMAIL
        defaultSiteShouldBeFound("siteEmail.doesNotContain=" + UPDATED_SITE_EMAIL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteTelIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteTel equals to DEFAULT_SITE_TEL
        defaultSiteShouldBeFound("siteTel.equals=" + DEFAULT_SITE_TEL);

        // Get all the siteList where siteTel equals to UPDATED_SITE_TEL
        defaultSiteShouldNotBeFound("siteTel.equals=" + UPDATED_SITE_TEL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteTelIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteTel in DEFAULT_SITE_TEL or UPDATED_SITE_TEL
        defaultSiteShouldBeFound("siteTel.in=" + DEFAULT_SITE_TEL + "," + UPDATED_SITE_TEL);

        // Get all the siteList where siteTel equals to UPDATED_SITE_TEL
        defaultSiteShouldNotBeFound("siteTel.in=" + UPDATED_SITE_TEL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteTel is not null
        defaultSiteShouldBeFound("siteTel.specified=true");

        // Get all the siteList where siteTel is null
        defaultSiteShouldNotBeFound("siteTel.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteTelContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteTel contains DEFAULT_SITE_TEL
        defaultSiteShouldBeFound("siteTel.contains=" + DEFAULT_SITE_TEL);

        // Get all the siteList where siteTel contains UPDATED_SITE_TEL
        defaultSiteShouldNotBeFound("siteTel.contains=" + UPDATED_SITE_TEL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteTelNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteTel does not contain DEFAULT_SITE_TEL
        defaultSiteShouldNotBeFound("siteTel.doesNotContain=" + DEFAULT_SITE_TEL);

        // Get all the siteList where siteTel does not contain UPDATED_SITE_TEL
        defaultSiteShouldBeFound("siteTel.doesNotContain=" + UPDATED_SITE_TEL);
    }

    @Test
    @Transactional
    void getAllSitesBySiteFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteFax equals to DEFAULT_SITE_FAX
        defaultSiteShouldBeFound("siteFax.equals=" + DEFAULT_SITE_FAX);

        // Get all the siteList where siteFax equals to UPDATED_SITE_FAX
        defaultSiteShouldNotBeFound("siteFax.equals=" + UPDATED_SITE_FAX);
    }

    @Test
    @Transactional
    void getAllSitesBySiteFaxIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteFax in DEFAULT_SITE_FAX or UPDATED_SITE_FAX
        defaultSiteShouldBeFound("siteFax.in=" + DEFAULT_SITE_FAX + "," + UPDATED_SITE_FAX);

        // Get all the siteList where siteFax equals to UPDATED_SITE_FAX
        defaultSiteShouldNotBeFound("siteFax.in=" + UPDATED_SITE_FAX);
    }

    @Test
    @Transactional
    void getAllSitesBySiteFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteFax is not null
        defaultSiteShouldBeFound("siteFax.specified=true");

        // Get all the siteList where siteFax is null
        defaultSiteShouldNotBeFound("siteFax.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteFaxContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteFax contains DEFAULT_SITE_FAX
        defaultSiteShouldBeFound("siteFax.contains=" + DEFAULT_SITE_FAX);

        // Get all the siteList where siteFax contains UPDATED_SITE_FAX
        defaultSiteShouldNotBeFound("siteFax.contains=" + UPDATED_SITE_FAX);
    }

    @Test
    @Transactional
    void getAllSitesBySiteFaxNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteFax does not contain DEFAULT_SITE_FAX
        defaultSiteShouldNotBeFound("siteFax.doesNotContain=" + DEFAULT_SITE_FAX);

        // Get all the siteList where siteFax does not contain UPDATED_SITE_FAX
        defaultSiteShouldBeFound("siteFax.doesNotContain=" + UPDATED_SITE_FAX);
    }

    @Test
    @Transactional
    void getAllSitesBySiteResponsableNameIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteResponsableName equals to DEFAULT_SITE_RESPONSABLE_NAME
        defaultSiteShouldBeFound("siteResponsableName.equals=" + DEFAULT_SITE_RESPONSABLE_NAME);

        // Get all the siteList where siteResponsableName equals to UPDATED_SITE_RESPONSABLE_NAME
        defaultSiteShouldNotBeFound("siteResponsableName.equals=" + UPDATED_SITE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteResponsableNameIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteResponsableName in DEFAULT_SITE_RESPONSABLE_NAME or UPDATED_SITE_RESPONSABLE_NAME
        defaultSiteShouldBeFound("siteResponsableName.in=" + DEFAULT_SITE_RESPONSABLE_NAME + "," + UPDATED_SITE_RESPONSABLE_NAME);

        // Get all the siteList where siteResponsableName equals to UPDATED_SITE_RESPONSABLE_NAME
        defaultSiteShouldNotBeFound("siteResponsableName.in=" + UPDATED_SITE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteResponsableNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteResponsableName is not null
        defaultSiteShouldBeFound("siteResponsableName.specified=true");

        // Get all the siteList where siteResponsableName is null
        defaultSiteShouldNotBeFound("siteResponsableName.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteResponsableNameContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteResponsableName contains DEFAULT_SITE_RESPONSABLE_NAME
        defaultSiteShouldBeFound("siteResponsableName.contains=" + DEFAULT_SITE_RESPONSABLE_NAME);

        // Get all the siteList where siteResponsableName contains UPDATED_SITE_RESPONSABLE_NAME
        defaultSiteShouldNotBeFound("siteResponsableName.contains=" + UPDATED_SITE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteResponsableNameNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteResponsableName does not contain DEFAULT_SITE_RESPONSABLE_NAME
        defaultSiteShouldNotBeFound("siteResponsableName.doesNotContain=" + DEFAULT_SITE_RESPONSABLE_NAME);

        // Get all the siteList where siteResponsableName does not contain UPDATED_SITE_RESPONSABLE_NAME
        defaultSiteShouldBeFound("siteResponsableName.doesNotContain=" + UPDATED_SITE_RESPONSABLE_NAME);
    }

    @Test
    @Transactional
    void getAllSitesBySiteParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteParams equals to DEFAULT_SITE_PARAMS
        defaultSiteShouldBeFound("siteParams.equals=" + DEFAULT_SITE_PARAMS);

        // Get all the siteList where siteParams equals to UPDATED_SITE_PARAMS
        defaultSiteShouldNotBeFound("siteParams.equals=" + UPDATED_SITE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteParamsIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteParams in DEFAULT_SITE_PARAMS or UPDATED_SITE_PARAMS
        defaultSiteShouldBeFound("siteParams.in=" + DEFAULT_SITE_PARAMS + "," + UPDATED_SITE_PARAMS);

        // Get all the siteList where siteParams equals to UPDATED_SITE_PARAMS
        defaultSiteShouldNotBeFound("siteParams.in=" + UPDATED_SITE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteParams is not null
        defaultSiteShouldBeFound("siteParams.specified=true");

        // Get all the siteList where siteParams is null
        defaultSiteShouldNotBeFound("siteParams.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteParamsContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteParams contains DEFAULT_SITE_PARAMS
        defaultSiteShouldBeFound("siteParams.contains=" + DEFAULT_SITE_PARAMS);

        // Get all the siteList where siteParams contains UPDATED_SITE_PARAMS
        defaultSiteShouldNotBeFound("siteParams.contains=" + UPDATED_SITE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteParamsNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteParams does not contain DEFAULT_SITE_PARAMS
        defaultSiteShouldNotBeFound("siteParams.doesNotContain=" + DEFAULT_SITE_PARAMS);

        // Get all the siteList where siteParams does not contain UPDATED_SITE_PARAMS
        defaultSiteShouldBeFound("siteParams.doesNotContain=" + UPDATED_SITE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAttributs equals to DEFAULT_SITE_ATTRIBUTS
        defaultSiteShouldBeFound("siteAttributs.equals=" + DEFAULT_SITE_ATTRIBUTS);

        // Get all the siteList where siteAttributs equals to UPDATED_SITE_ATTRIBUTS
        defaultSiteShouldNotBeFound("siteAttributs.equals=" + UPDATED_SITE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAttributs in DEFAULT_SITE_ATTRIBUTS or UPDATED_SITE_ATTRIBUTS
        defaultSiteShouldBeFound("siteAttributs.in=" + DEFAULT_SITE_ATTRIBUTS + "," + UPDATED_SITE_ATTRIBUTS);

        // Get all the siteList where siteAttributs equals to UPDATED_SITE_ATTRIBUTS
        defaultSiteShouldNotBeFound("siteAttributs.in=" + UPDATED_SITE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAttributs is not null
        defaultSiteShouldBeFound("siteAttributs.specified=true");

        // Get all the siteList where siteAttributs is null
        defaultSiteShouldNotBeFound("siteAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesBySiteAttributsContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAttributs contains DEFAULT_SITE_ATTRIBUTS
        defaultSiteShouldBeFound("siteAttributs.contains=" + DEFAULT_SITE_ATTRIBUTS);

        // Get all the siteList where siteAttributs contains UPDATED_SITE_ATTRIBUTS
        defaultSiteShouldNotBeFound("siteAttributs.contains=" + UPDATED_SITE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteAttributs does not contain DEFAULT_SITE_ATTRIBUTS
        defaultSiteShouldNotBeFound("siteAttributs.doesNotContain=" + DEFAULT_SITE_ATTRIBUTS);

        // Get all the siteList where siteAttributs does not contain UPDATED_SITE_ATTRIBUTS
        defaultSiteShouldBeFound("siteAttributs.doesNotContain=" + UPDATED_SITE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSitesBySiteStatIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteStat equals to DEFAULT_SITE_STAT
        defaultSiteShouldBeFound("siteStat.equals=" + DEFAULT_SITE_STAT);

        // Get all the siteList where siteStat equals to UPDATED_SITE_STAT
        defaultSiteShouldNotBeFound("siteStat.equals=" + UPDATED_SITE_STAT);
    }

    @Test
    @Transactional
    void getAllSitesBySiteStatIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteStat in DEFAULT_SITE_STAT or UPDATED_SITE_STAT
        defaultSiteShouldBeFound("siteStat.in=" + DEFAULT_SITE_STAT + "," + UPDATED_SITE_STAT);

        // Get all the siteList where siteStat equals to UPDATED_SITE_STAT
        defaultSiteShouldNotBeFound("siteStat.in=" + UPDATED_SITE_STAT);
    }

    @Test
    @Transactional
    void getAllSitesBySiteStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where siteStat is not null
        defaultSiteShouldBeFound("siteStat.specified=true");

        // Get all the siteList where siteStat is null
        defaultSiteShouldNotBeFound("siteStat.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            siteRepository.saveAndFlush(site);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        site.setCity(city);
        siteRepository.saveAndFlush(site);
        Long cityId = city.getCityId();

        // Get all the siteList where city equals to cityId
        defaultSiteShouldBeFound("cityId.equals=" + cityId);

        // Get all the siteList where city equals to (cityId + 1)
        defaultSiteShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllSitesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            siteRepository.saveAndFlush(site);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        site.setEvent(event);
        siteRepository.saveAndFlush(site);
        Long eventId = event.getEventId();

        // Get all the siteList where event equals to eventId
        defaultSiteShouldBeFound("eventId.equals=" + eventId);

        // Get all the siteList where event equals to (eventId + 1)
        defaultSiteShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllSitesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            siteRepository.saveAndFlush(site);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        site.addAccreditation(accreditation);
        siteRepository.saveAndFlush(site);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the siteList where accreditation equals to accreditationId
        defaultSiteShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the siteList where accreditation equals to (accreditationId + 1)
        defaultSiteShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSiteShouldBeFound(String filter) throws Exception {
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=siteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(site.getSiteId().intValue())))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME)))
            .andExpect(jsonPath("$.[*].siteColor").value(hasItem(DEFAULT_SITE_COLOR)))
            .andExpect(jsonPath("$.[*].siteAbreviation").value(hasItem(DEFAULT_SITE_ABREVIATION)))
            .andExpect(jsonPath("$.[*].siteDescription").value(hasItem(DEFAULT_SITE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].siteLogoContentType").value(hasItem(DEFAULT_SITE_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].siteLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_SITE_LOGO))))
            .andExpect(jsonPath("$.[*].siteAdresse").value(hasItem(DEFAULT_SITE_ADRESSE)))
            .andExpect(jsonPath("$.[*].siteEmail").value(hasItem(DEFAULT_SITE_EMAIL)))
            .andExpect(jsonPath("$.[*].siteTel").value(hasItem(DEFAULT_SITE_TEL)))
            .andExpect(jsonPath("$.[*].siteFax").value(hasItem(DEFAULT_SITE_FAX)))
            .andExpect(jsonPath("$.[*].siteResponsableName").value(hasItem(DEFAULT_SITE_RESPONSABLE_NAME)))
            .andExpect(jsonPath("$.[*].siteParams").value(hasItem(DEFAULT_SITE_PARAMS)))
            .andExpect(jsonPath("$.[*].siteAttributs").value(hasItem(DEFAULT_SITE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].siteStat").value(hasItem(DEFAULT_SITE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=siteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSiteShouldNotBeFound(String filter) throws Exception {
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=siteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=siteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSite() throws Exception {
        // Get the site
        restSiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Update the site
        Site updatedSite = siteRepository.findById(site.getSiteId()).get();
        // Disconnect from session so that the updates on updatedSite are not directly saved in db
        em.detach(updatedSite);
        updatedSite
            .siteName(UPDATED_SITE_NAME)
            .siteColor(UPDATED_SITE_COLOR)
            .siteAbreviation(UPDATED_SITE_ABREVIATION)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .siteLogo(UPDATED_SITE_LOGO)
            .siteLogoContentType(UPDATED_SITE_LOGO_CONTENT_TYPE)
            .siteAdresse(UPDATED_SITE_ADRESSE)
            .siteEmail(UPDATED_SITE_EMAIL)
            .siteTel(UPDATED_SITE_TEL)
            .siteFax(UPDATED_SITE_FAX)
            .siteResponsableName(UPDATED_SITE_RESPONSABLE_NAME)
            .siteParams(UPDATED_SITE_PARAMS)
            .siteAttributs(UPDATED_SITE_ATTRIBUTS)
            .siteStat(UPDATED_SITE_STAT);
        SiteDTO siteDTO = siteMapper.toDto(updatedSite);

        restSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteDTO.getSiteId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testSite.getSiteColor()).isEqualTo(UPDATED_SITE_COLOR);
        assertThat(testSite.getSiteAbreviation()).isEqualTo(UPDATED_SITE_ABREVIATION);
        assertThat(testSite.getSiteDescription()).isEqualTo(UPDATED_SITE_DESCRIPTION);
        assertThat(testSite.getSiteLogo()).isEqualTo(UPDATED_SITE_LOGO);
        assertThat(testSite.getSiteLogoContentType()).isEqualTo(UPDATED_SITE_LOGO_CONTENT_TYPE);
        assertThat(testSite.getSiteAdresse()).isEqualTo(UPDATED_SITE_ADRESSE);
        assertThat(testSite.getSiteEmail()).isEqualTo(UPDATED_SITE_EMAIL);
        assertThat(testSite.getSiteTel()).isEqualTo(UPDATED_SITE_TEL);
        assertThat(testSite.getSiteFax()).isEqualTo(UPDATED_SITE_FAX);
        assertThat(testSite.getSiteResponsableName()).isEqualTo(UPDATED_SITE_RESPONSABLE_NAME);
        assertThat(testSite.getSiteParams()).isEqualTo(UPDATED_SITE_PARAMS);
        assertThat(testSite.getSiteAttributs()).isEqualTo(UPDATED_SITE_ATTRIBUTS);
        assertThat(testSite.getSiteStat()).isEqualTo(UPDATED_SITE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();
        site.setSiteId(count.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteDTO.getSiteId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();
        site.setSiteId(count.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();
        site.setSiteId(count.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteWithPatch() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Update the site using partial update
        Site partialUpdatedSite = new Site();
        partialUpdatedSite.setSiteId(site.getSiteId());

        partialUpdatedSite
            .siteColor(UPDATED_SITE_COLOR)
            .siteAbreviation(UPDATED_SITE_ABREVIATION)
            .siteLogo(UPDATED_SITE_LOGO)
            .siteLogoContentType(UPDATED_SITE_LOGO_CONTENT_TYPE)
            .siteAdresse(UPDATED_SITE_ADRESSE)
            .siteEmail(UPDATED_SITE_EMAIL)
            .siteTel(UPDATED_SITE_TEL)
            .siteFax(UPDATED_SITE_FAX)
            .siteResponsableName(UPDATED_SITE_RESPONSABLE_NAME)
            .siteAttributs(UPDATED_SITE_ATTRIBUTS);

        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSite.getSiteId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSite))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getSiteName()).isEqualTo(DEFAULT_SITE_NAME);
        assertThat(testSite.getSiteColor()).isEqualTo(UPDATED_SITE_COLOR);
        assertThat(testSite.getSiteAbreviation()).isEqualTo(UPDATED_SITE_ABREVIATION);
        assertThat(testSite.getSiteDescription()).isEqualTo(DEFAULT_SITE_DESCRIPTION);
        assertThat(testSite.getSiteLogo()).isEqualTo(UPDATED_SITE_LOGO);
        assertThat(testSite.getSiteLogoContentType()).isEqualTo(UPDATED_SITE_LOGO_CONTENT_TYPE);
        assertThat(testSite.getSiteAdresse()).isEqualTo(UPDATED_SITE_ADRESSE);
        assertThat(testSite.getSiteEmail()).isEqualTo(UPDATED_SITE_EMAIL);
        assertThat(testSite.getSiteTel()).isEqualTo(UPDATED_SITE_TEL);
        assertThat(testSite.getSiteFax()).isEqualTo(UPDATED_SITE_FAX);
        assertThat(testSite.getSiteResponsableName()).isEqualTo(UPDATED_SITE_RESPONSABLE_NAME);
        assertThat(testSite.getSiteParams()).isEqualTo(DEFAULT_SITE_PARAMS);
        assertThat(testSite.getSiteAttributs()).isEqualTo(UPDATED_SITE_ATTRIBUTS);
        assertThat(testSite.getSiteStat()).isEqualTo(DEFAULT_SITE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateSiteWithPatch() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Update the site using partial update
        Site partialUpdatedSite = new Site();
        partialUpdatedSite.setSiteId(site.getSiteId());

        partialUpdatedSite
            .siteName(UPDATED_SITE_NAME)
            .siteColor(UPDATED_SITE_COLOR)
            .siteAbreviation(UPDATED_SITE_ABREVIATION)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .siteLogo(UPDATED_SITE_LOGO)
            .siteLogoContentType(UPDATED_SITE_LOGO_CONTENT_TYPE)
            .siteAdresse(UPDATED_SITE_ADRESSE)
            .siteEmail(UPDATED_SITE_EMAIL)
            .siteTel(UPDATED_SITE_TEL)
            .siteFax(UPDATED_SITE_FAX)
            .siteResponsableName(UPDATED_SITE_RESPONSABLE_NAME)
            .siteParams(UPDATED_SITE_PARAMS)
            .siteAttributs(UPDATED_SITE_ATTRIBUTS)
            .siteStat(UPDATED_SITE_STAT);

        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSite.getSiteId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSite))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testSite.getSiteColor()).isEqualTo(UPDATED_SITE_COLOR);
        assertThat(testSite.getSiteAbreviation()).isEqualTo(UPDATED_SITE_ABREVIATION);
        assertThat(testSite.getSiteDescription()).isEqualTo(UPDATED_SITE_DESCRIPTION);
        assertThat(testSite.getSiteLogo()).isEqualTo(UPDATED_SITE_LOGO);
        assertThat(testSite.getSiteLogoContentType()).isEqualTo(UPDATED_SITE_LOGO_CONTENT_TYPE);
        assertThat(testSite.getSiteAdresse()).isEqualTo(UPDATED_SITE_ADRESSE);
        assertThat(testSite.getSiteEmail()).isEqualTo(UPDATED_SITE_EMAIL);
        assertThat(testSite.getSiteTel()).isEqualTo(UPDATED_SITE_TEL);
        assertThat(testSite.getSiteFax()).isEqualTo(UPDATED_SITE_FAX);
        assertThat(testSite.getSiteResponsableName()).isEqualTo(UPDATED_SITE_RESPONSABLE_NAME);
        assertThat(testSite.getSiteParams()).isEqualTo(UPDATED_SITE_PARAMS);
        assertThat(testSite.getSiteAttributs()).isEqualTo(UPDATED_SITE_ATTRIBUTS);
        assertThat(testSite.getSiteStat()).isEqualTo(UPDATED_SITE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();
        site.setSiteId(count.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteDTO.getSiteId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();
        site.setSiteId(count.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();
        site.setSiteId(count.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        int databaseSizeBeforeDelete = siteRepository.findAll().size();

        // Delete the site
        restSiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, site.getSiteId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
