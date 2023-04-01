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
import ma.sig.events.domain.InfoSupp;
import ma.sig.events.domain.InfoSuppType;
import ma.sig.events.repository.InfoSuppRepository;
import ma.sig.events.service.criteria.InfoSuppCriteria;
import ma.sig.events.service.dto.InfoSuppDTO;
import ma.sig.events.service.mapper.InfoSuppMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InfoSuppResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InfoSuppResourceIT {

    private static final String DEFAULT_INFO_SUPP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_SUPP_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_SUPP_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_SUPP_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INFO_SUPP_STAT = false;
    private static final Boolean UPDATED_INFO_SUPP_STAT = true;

    private static final String ENTITY_API_URL = "/api/info-supps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{infoSuppId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfoSuppRepository infoSuppRepository;

    @Autowired
    private InfoSuppMapper infoSuppMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfoSuppMockMvc;

    private InfoSupp infoSupp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoSupp createEntity(EntityManager em) {
        InfoSupp infoSupp = new InfoSupp()
            .infoSuppName(DEFAULT_INFO_SUPP_NAME)
            .infoSuppDescription(DEFAULT_INFO_SUPP_DESCRIPTION)
            .infoSuppParams(DEFAULT_INFO_SUPP_PARAMS)
            .infoSuppAttributs(DEFAULT_INFO_SUPP_ATTRIBUTS)
            .infoSuppStat(DEFAULT_INFO_SUPP_STAT);
        return infoSupp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoSupp createUpdatedEntity(EntityManager em) {
        InfoSupp infoSupp = new InfoSupp()
            .infoSuppName(UPDATED_INFO_SUPP_NAME)
            .infoSuppDescription(UPDATED_INFO_SUPP_DESCRIPTION)
            .infoSuppParams(UPDATED_INFO_SUPP_PARAMS)
            .infoSuppAttributs(UPDATED_INFO_SUPP_ATTRIBUTS)
            .infoSuppStat(UPDATED_INFO_SUPP_STAT);
        return infoSupp;
    }

    @BeforeEach
    public void initTest() {
        infoSupp = createEntity(em);
    }

    @Test
    @Transactional
    void createInfoSupp() throws Exception {
        int databaseSizeBeforeCreate = infoSuppRepository.findAll().size();
        // Create the InfoSupp
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);
        restInfoSuppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppDTO)))
            .andExpect(status().isCreated());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeCreate + 1);
        InfoSupp testInfoSupp = infoSuppList.get(infoSuppList.size() - 1);
        assertThat(testInfoSupp.getInfoSuppName()).isEqualTo(DEFAULT_INFO_SUPP_NAME);
        assertThat(testInfoSupp.getInfoSuppDescription()).isEqualTo(DEFAULT_INFO_SUPP_DESCRIPTION);
        assertThat(testInfoSupp.getInfoSuppParams()).isEqualTo(DEFAULT_INFO_SUPP_PARAMS);
        assertThat(testInfoSupp.getInfoSuppAttributs()).isEqualTo(DEFAULT_INFO_SUPP_ATTRIBUTS);
        assertThat(testInfoSupp.getInfoSuppStat()).isEqualTo(DEFAULT_INFO_SUPP_STAT);
    }

    @Test
    @Transactional
    void createInfoSuppWithExistingId() throws Exception {
        // Create the InfoSupp with an existing ID
        infoSupp.setInfoSuppId(1L);
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        int databaseSizeBeforeCreate = infoSuppRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoSuppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInfoSuppNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSuppRepository.findAll().size();
        // set the field null
        infoSupp.setInfoSuppName(null);

        // Create the InfoSupp, which fails.
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        restInfoSuppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppDTO)))
            .andExpect(status().isBadRequest());

        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInfoSupps() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList
        restInfoSuppMockMvc
            .perform(get(ENTITY_API_URL + "?sort=infoSuppId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].infoSuppId").value(hasItem(infoSupp.getInfoSuppId().intValue())))
            .andExpect(jsonPath("$.[*].infoSuppName").value(hasItem(DEFAULT_INFO_SUPP_NAME)))
            .andExpect(jsonPath("$.[*].infoSuppDescription").value(hasItem(DEFAULT_INFO_SUPP_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].infoSuppParams").value(hasItem(DEFAULT_INFO_SUPP_PARAMS)))
            .andExpect(jsonPath("$.[*].infoSuppAttributs").value(hasItem(DEFAULT_INFO_SUPP_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].infoSuppStat").value(hasItem(DEFAULT_INFO_SUPP_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getInfoSupp() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get the infoSupp
        restInfoSuppMockMvc
            .perform(get(ENTITY_API_URL_ID, infoSupp.getInfoSuppId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.infoSuppId").value(infoSupp.getInfoSuppId().intValue()))
            .andExpect(jsonPath("$.infoSuppName").value(DEFAULT_INFO_SUPP_NAME))
            .andExpect(jsonPath("$.infoSuppDescription").value(DEFAULT_INFO_SUPP_DESCRIPTION))
            .andExpect(jsonPath("$.infoSuppParams").value(DEFAULT_INFO_SUPP_PARAMS))
            .andExpect(jsonPath("$.infoSuppAttributs").value(DEFAULT_INFO_SUPP_ATTRIBUTS))
            .andExpect(jsonPath("$.infoSuppStat").value(DEFAULT_INFO_SUPP_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getInfoSuppsByIdFiltering() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        Long id = infoSupp.getInfoSuppId();

        defaultInfoSuppShouldBeFound("infoSuppId.equals=" + id);
        defaultInfoSuppShouldNotBeFound("infoSuppId.notEquals=" + id);

        defaultInfoSuppShouldBeFound("infoSuppId.greaterThanOrEqual=" + id);
        defaultInfoSuppShouldNotBeFound("infoSuppId.greaterThan=" + id);

        defaultInfoSuppShouldBeFound("infoSuppId.lessThanOrEqual=" + id);
        defaultInfoSuppShouldNotBeFound("infoSuppId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppNameIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppName equals to DEFAULT_INFO_SUPP_NAME
        defaultInfoSuppShouldBeFound("infoSuppName.equals=" + DEFAULT_INFO_SUPP_NAME);

        // Get all the infoSuppList where infoSuppName equals to UPDATED_INFO_SUPP_NAME
        defaultInfoSuppShouldNotBeFound("infoSuppName.equals=" + UPDATED_INFO_SUPP_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppNameIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppName in DEFAULT_INFO_SUPP_NAME or UPDATED_INFO_SUPP_NAME
        defaultInfoSuppShouldBeFound("infoSuppName.in=" + DEFAULT_INFO_SUPP_NAME + "," + UPDATED_INFO_SUPP_NAME);

        // Get all the infoSuppList where infoSuppName equals to UPDATED_INFO_SUPP_NAME
        defaultInfoSuppShouldNotBeFound("infoSuppName.in=" + UPDATED_INFO_SUPP_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppName is not null
        defaultInfoSuppShouldBeFound("infoSuppName.specified=true");

        // Get all the infoSuppList where infoSuppName is null
        defaultInfoSuppShouldNotBeFound("infoSuppName.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppNameContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppName contains DEFAULT_INFO_SUPP_NAME
        defaultInfoSuppShouldBeFound("infoSuppName.contains=" + DEFAULT_INFO_SUPP_NAME);

        // Get all the infoSuppList where infoSuppName contains UPDATED_INFO_SUPP_NAME
        defaultInfoSuppShouldNotBeFound("infoSuppName.contains=" + UPDATED_INFO_SUPP_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppNameNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppName does not contain DEFAULT_INFO_SUPP_NAME
        defaultInfoSuppShouldNotBeFound("infoSuppName.doesNotContain=" + DEFAULT_INFO_SUPP_NAME);

        // Get all the infoSuppList where infoSuppName does not contain UPDATED_INFO_SUPP_NAME
        defaultInfoSuppShouldBeFound("infoSuppName.doesNotContain=" + UPDATED_INFO_SUPP_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppDescription equals to DEFAULT_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldBeFound("infoSuppDescription.equals=" + DEFAULT_INFO_SUPP_DESCRIPTION);

        // Get all the infoSuppList where infoSuppDescription equals to UPDATED_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldNotBeFound("infoSuppDescription.equals=" + UPDATED_INFO_SUPP_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppDescription in DEFAULT_INFO_SUPP_DESCRIPTION or UPDATED_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldBeFound("infoSuppDescription.in=" + DEFAULT_INFO_SUPP_DESCRIPTION + "," + UPDATED_INFO_SUPP_DESCRIPTION);

        // Get all the infoSuppList where infoSuppDescription equals to UPDATED_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldNotBeFound("infoSuppDescription.in=" + UPDATED_INFO_SUPP_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppDescription is not null
        defaultInfoSuppShouldBeFound("infoSuppDescription.specified=true");

        // Get all the infoSuppList where infoSuppDescription is null
        defaultInfoSuppShouldNotBeFound("infoSuppDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppDescriptionContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppDescription contains DEFAULT_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldBeFound("infoSuppDescription.contains=" + DEFAULT_INFO_SUPP_DESCRIPTION);

        // Get all the infoSuppList where infoSuppDescription contains UPDATED_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldNotBeFound("infoSuppDescription.contains=" + UPDATED_INFO_SUPP_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppDescription does not contain DEFAULT_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldNotBeFound("infoSuppDescription.doesNotContain=" + DEFAULT_INFO_SUPP_DESCRIPTION);

        // Get all the infoSuppList where infoSuppDescription does not contain UPDATED_INFO_SUPP_DESCRIPTION
        defaultInfoSuppShouldBeFound("infoSuppDescription.doesNotContain=" + UPDATED_INFO_SUPP_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppParams equals to DEFAULT_INFO_SUPP_PARAMS
        defaultInfoSuppShouldBeFound("infoSuppParams.equals=" + DEFAULT_INFO_SUPP_PARAMS);

        // Get all the infoSuppList where infoSuppParams equals to UPDATED_INFO_SUPP_PARAMS
        defaultInfoSuppShouldNotBeFound("infoSuppParams.equals=" + UPDATED_INFO_SUPP_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppParamsIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppParams in DEFAULT_INFO_SUPP_PARAMS or UPDATED_INFO_SUPP_PARAMS
        defaultInfoSuppShouldBeFound("infoSuppParams.in=" + DEFAULT_INFO_SUPP_PARAMS + "," + UPDATED_INFO_SUPP_PARAMS);

        // Get all the infoSuppList where infoSuppParams equals to UPDATED_INFO_SUPP_PARAMS
        defaultInfoSuppShouldNotBeFound("infoSuppParams.in=" + UPDATED_INFO_SUPP_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppParams is not null
        defaultInfoSuppShouldBeFound("infoSuppParams.specified=true");

        // Get all the infoSuppList where infoSuppParams is null
        defaultInfoSuppShouldNotBeFound("infoSuppParams.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppParamsContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppParams contains DEFAULT_INFO_SUPP_PARAMS
        defaultInfoSuppShouldBeFound("infoSuppParams.contains=" + DEFAULT_INFO_SUPP_PARAMS);

        // Get all the infoSuppList where infoSuppParams contains UPDATED_INFO_SUPP_PARAMS
        defaultInfoSuppShouldNotBeFound("infoSuppParams.contains=" + UPDATED_INFO_SUPP_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppParamsNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppParams does not contain DEFAULT_INFO_SUPP_PARAMS
        defaultInfoSuppShouldNotBeFound("infoSuppParams.doesNotContain=" + DEFAULT_INFO_SUPP_PARAMS);

        // Get all the infoSuppList where infoSuppParams does not contain UPDATED_INFO_SUPP_PARAMS
        defaultInfoSuppShouldBeFound("infoSuppParams.doesNotContain=" + UPDATED_INFO_SUPP_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppAttributs equals to DEFAULT_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldBeFound("infoSuppAttributs.equals=" + DEFAULT_INFO_SUPP_ATTRIBUTS);

        // Get all the infoSuppList where infoSuppAttributs equals to UPDATED_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldNotBeFound("infoSuppAttributs.equals=" + UPDATED_INFO_SUPP_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppAttributs in DEFAULT_INFO_SUPP_ATTRIBUTS or UPDATED_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldBeFound("infoSuppAttributs.in=" + DEFAULT_INFO_SUPP_ATTRIBUTS + "," + UPDATED_INFO_SUPP_ATTRIBUTS);

        // Get all the infoSuppList where infoSuppAttributs equals to UPDATED_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldNotBeFound("infoSuppAttributs.in=" + UPDATED_INFO_SUPP_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppAttributs is not null
        defaultInfoSuppShouldBeFound("infoSuppAttributs.specified=true");

        // Get all the infoSuppList where infoSuppAttributs is null
        defaultInfoSuppShouldNotBeFound("infoSuppAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppAttributsContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppAttributs contains DEFAULT_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldBeFound("infoSuppAttributs.contains=" + DEFAULT_INFO_SUPP_ATTRIBUTS);

        // Get all the infoSuppList where infoSuppAttributs contains UPDATED_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldNotBeFound("infoSuppAttributs.contains=" + UPDATED_INFO_SUPP_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppAttributs does not contain DEFAULT_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldNotBeFound("infoSuppAttributs.doesNotContain=" + DEFAULT_INFO_SUPP_ATTRIBUTS);

        // Get all the infoSuppList where infoSuppAttributs does not contain UPDATED_INFO_SUPP_ATTRIBUTS
        defaultInfoSuppShouldBeFound("infoSuppAttributs.doesNotContain=" + UPDATED_INFO_SUPP_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppStatIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppStat equals to DEFAULT_INFO_SUPP_STAT
        defaultInfoSuppShouldBeFound("infoSuppStat.equals=" + DEFAULT_INFO_SUPP_STAT);

        // Get all the infoSuppList where infoSuppStat equals to UPDATED_INFO_SUPP_STAT
        defaultInfoSuppShouldNotBeFound("infoSuppStat.equals=" + UPDATED_INFO_SUPP_STAT);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppStatIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppStat in DEFAULT_INFO_SUPP_STAT or UPDATED_INFO_SUPP_STAT
        defaultInfoSuppShouldBeFound("infoSuppStat.in=" + DEFAULT_INFO_SUPP_STAT + "," + UPDATED_INFO_SUPP_STAT);

        // Get all the infoSuppList where infoSuppStat equals to UPDATED_INFO_SUPP_STAT
        defaultInfoSuppShouldNotBeFound("infoSuppStat.in=" + UPDATED_INFO_SUPP_STAT);
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        // Get all the infoSuppList where infoSuppStat is not null
        defaultInfoSuppShouldBeFound("infoSuppStat.specified=true");

        // Get all the infoSuppList where infoSuppStat is null
        defaultInfoSuppShouldNotBeFound("infoSuppStat.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppsByInfoSuppTypeIsEqualToSomething() throws Exception {
        InfoSuppType infoSuppType;
        if (TestUtil.findAll(em, InfoSuppType.class).isEmpty()) {
            infoSuppRepository.saveAndFlush(infoSupp);
            infoSuppType = InfoSuppTypeResourceIT.createEntity(em);
        } else {
            infoSuppType = TestUtil.findAll(em, InfoSuppType.class).get(0);
        }
        em.persist(infoSuppType);
        em.flush();
        infoSupp.setInfoSuppType(infoSuppType);
        infoSuppRepository.saveAndFlush(infoSupp);
        Long infoSuppTypeId = infoSuppType.getInfoSuppTypeId();

        // Get all the infoSuppList where infoSuppType equals to infoSuppTypeId
        defaultInfoSuppShouldBeFound("infoSuppTypeId.equals=" + infoSuppTypeId);

        // Get all the infoSuppList where infoSuppType equals to (infoSuppTypeId + 1)
        defaultInfoSuppShouldNotBeFound("infoSuppTypeId.equals=" + (infoSuppTypeId + 1));
    }

    @Test
    @Transactional
    void getAllInfoSuppsByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            infoSuppRepository.saveAndFlush(infoSupp);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        infoSupp.setAccreditation(accreditation);
        infoSuppRepository.saveAndFlush(infoSupp);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the infoSuppList where accreditation equals to accreditationId
        defaultInfoSuppShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the infoSuppList where accreditation equals to (accreditationId + 1)
        defaultInfoSuppShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllInfoSuppsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            infoSuppRepository.saveAndFlush(infoSupp);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        infoSupp.setEvent(event);
        infoSuppRepository.saveAndFlush(infoSupp);
        Long eventId = event.getEventId();

        // Get all the infoSuppList where event equals to eventId
        defaultInfoSuppShouldBeFound("eventId.equals=" + eventId);

        // Get all the infoSuppList where event equals to (eventId + 1)
        defaultInfoSuppShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInfoSuppShouldBeFound(String filter) throws Exception {
        restInfoSuppMockMvc
            .perform(get(ENTITY_API_URL + "?sort=infoSuppId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].infoSuppId").value(hasItem(infoSupp.getInfoSuppId().intValue())))
            .andExpect(jsonPath("$.[*].infoSuppName").value(hasItem(DEFAULT_INFO_SUPP_NAME)))
            .andExpect(jsonPath("$.[*].infoSuppDescription").value(hasItem(DEFAULT_INFO_SUPP_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].infoSuppParams").value(hasItem(DEFAULT_INFO_SUPP_PARAMS)))
            .andExpect(jsonPath("$.[*].infoSuppAttributs").value(hasItem(DEFAULT_INFO_SUPP_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].infoSuppStat").value(hasItem(DEFAULT_INFO_SUPP_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restInfoSuppMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=infoSuppId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInfoSuppShouldNotBeFound(String filter) throws Exception {
        restInfoSuppMockMvc
            .perform(get(ENTITY_API_URL + "?sort=infoSuppId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInfoSuppMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=infoSuppId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInfoSupp() throws Exception {
        // Get the infoSupp
        restInfoSuppMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInfoSupp() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();

        // Update the infoSupp
        InfoSupp updatedInfoSupp = infoSuppRepository.findById(infoSupp.getInfoSuppId()).get();
        // Disconnect from session so that the updates on updatedInfoSupp are not directly saved in db
        em.detach(updatedInfoSupp);
        updatedInfoSupp
            .infoSuppName(UPDATED_INFO_SUPP_NAME)
            .infoSuppDescription(UPDATED_INFO_SUPP_DESCRIPTION)
            .infoSuppParams(UPDATED_INFO_SUPP_PARAMS)
            .infoSuppAttributs(UPDATED_INFO_SUPP_ATTRIBUTS)
            .infoSuppStat(UPDATED_INFO_SUPP_STAT);
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(updatedInfoSupp);

        restInfoSuppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infoSuppDTO.getInfoSuppId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppDTO))
            )
            .andExpect(status().isOk());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
        InfoSupp testInfoSupp = infoSuppList.get(infoSuppList.size() - 1);
        assertThat(testInfoSupp.getInfoSuppName()).isEqualTo(UPDATED_INFO_SUPP_NAME);
        assertThat(testInfoSupp.getInfoSuppDescription()).isEqualTo(UPDATED_INFO_SUPP_DESCRIPTION);
        assertThat(testInfoSupp.getInfoSuppParams()).isEqualTo(UPDATED_INFO_SUPP_PARAMS);
        assertThat(testInfoSupp.getInfoSuppAttributs()).isEqualTo(UPDATED_INFO_SUPP_ATTRIBUTS);
        assertThat(testInfoSupp.getInfoSuppStat()).isEqualTo(UPDATED_INFO_SUPP_STAT);
    }

    @Test
    @Transactional
    void putNonExistingInfoSupp() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();
        infoSupp.setInfoSuppId(count.incrementAndGet());

        // Create the InfoSupp
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoSuppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infoSuppDTO.getInfoSuppId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInfoSupp() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();
        infoSupp.setInfoSuppId(count.incrementAndGet());

        // Create the InfoSupp
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInfoSupp() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();
        infoSupp.setInfoSuppId(count.incrementAndGet());

        // Create the InfoSupp
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInfoSuppWithPatch() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();

        // Update the infoSupp using partial update
        InfoSupp partialUpdatedInfoSupp = new InfoSupp();
        partialUpdatedInfoSupp.setInfoSuppId(infoSupp.getInfoSuppId());

        partialUpdatedInfoSupp
            .infoSuppName(UPDATED_INFO_SUPP_NAME)
            .infoSuppDescription(UPDATED_INFO_SUPP_DESCRIPTION)
            .infoSuppAttributs(UPDATED_INFO_SUPP_ATTRIBUTS)
            .infoSuppStat(UPDATED_INFO_SUPP_STAT);

        restInfoSuppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoSupp.getInfoSuppId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoSupp))
            )
            .andExpect(status().isOk());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
        InfoSupp testInfoSupp = infoSuppList.get(infoSuppList.size() - 1);
        assertThat(testInfoSupp.getInfoSuppName()).isEqualTo(UPDATED_INFO_SUPP_NAME);
        assertThat(testInfoSupp.getInfoSuppDescription()).isEqualTo(UPDATED_INFO_SUPP_DESCRIPTION);
        assertThat(testInfoSupp.getInfoSuppParams()).isEqualTo(DEFAULT_INFO_SUPP_PARAMS);
        assertThat(testInfoSupp.getInfoSuppAttributs()).isEqualTo(UPDATED_INFO_SUPP_ATTRIBUTS);
        assertThat(testInfoSupp.getInfoSuppStat()).isEqualTo(UPDATED_INFO_SUPP_STAT);
    }

    @Test
    @Transactional
    void fullUpdateInfoSuppWithPatch() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();

        // Update the infoSupp using partial update
        InfoSupp partialUpdatedInfoSupp = new InfoSupp();
        partialUpdatedInfoSupp.setInfoSuppId(infoSupp.getInfoSuppId());

        partialUpdatedInfoSupp
            .infoSuppName(UPDATED_INFO_SUPP_NAME)
            .infoSuppDescription(UPDATED_INFO_SUPP_DESCRIPTION)
            .infoSuppParams(UPDATED_INFO_SUPP_PARAMS)
            .infoSuppAttributs(UPDATED_INFO_SUPP_ATTRIBUTS)
            .infoSuppStat(UPDATED_INFO_SUPP_STAT);

        restInfoSuppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoSupp.getInfoSuppId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoSupp))
            )
            .andExpect(status().isOk());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
        InfoSupp testInfoSupp = infoSuppList.get(infoSuppList.size() - 1);
        assertThat(testInfoSupp.getInfoSuppName()).isEqualTo(UPDATED_INFO_SUPP_NAME);
        assertThat(testInfoSupp.getInfoSuppDescription()).isEqualTo(UPDATED_INFO_SUPP_DESCRIPTION);
        assertThat(testInfoSupp.getInfoSuppParams()).isEqualTo(UPDATED_INFO_SUPP_PARAMS);
        assertThat(testInfoSupp.getInfoSuppAttributs()).isEqualTo(UPDATED_INFO_SUPP_ATTRIBUTS);
        assertThat(testInfoSupp.getInfoSuppStat()).isEqualTo(UPDATED_INFO_SUPP_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingInfoSupp() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();
        infoSupp.setInfoSuppId(count.incrementAndGet());

        // Create the InfoSupp
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoSuppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, infoSuppDTO.getInfoSuppId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInfoSupp() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();
        infoSupp.setInfoSuppId(count.incrementAndGet());

        // Create the InfoSupp
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInfoSupp() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppRepository.findAll().size();
        infoSupp.setInfoSuppId(count.incrementAndGet());

        // Create the InfoSupp
        InfoSuppDTO infoSuppDTO = infoSuppMapper.toDto(infoSupp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(infoSuppDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoSupp in the database
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInfoSupp() throws Exception {
        // Initialize the database
        infoSuppRepository.saveAndFlush(infoSupp);

        int databaseSizeBeforeDelete = infoSuppRepository.findAll().size();

        // Delete the infoSupp
        restInfoSuppMockMvc
            .perform(delete(ENTITY_API_URL_ID, infoSupp.getInfoSuppId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoSupp> infoSuppList = infoSuppRepository.findAll();
        assertThat(infoSuppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
