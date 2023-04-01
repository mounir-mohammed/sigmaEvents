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
import ma.sig.events.domain.Sexe;
import ma.sig.events.repository.SexeRepository;
import ma.sig.events.service.criteria.SexeCriteria;
import ma.sig.events.service.dto.SexeDTO;
import ma.sig.events.service.mapper.SexeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SexeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SexeResourceIT {

    private static final String DEFAULT_SEXE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_SEXE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SEXE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SEXE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SEXE_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_SEXE_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_SEXE_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_SEXE_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEXE_STAT = false;
    private static final Boolean UPDATED_SEXE_STAT = true;

    private static final String ENTITY_API_URL = "/api/sexes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{sexeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SexeRepository sexeRepository;

    @Autowired
    private SexeMapper sexeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSexeMockMvc;

    private Sexe sexe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createEntity(EntityManager em) {
        Sexe sexe = new Sexe()
            .sexeValue(DEFAULT_SEXE_VALUE)
            .sexeDescription(DEFAULT_SEXE_DESCRIPTION)
            .sexeTypeParams(DEFAULT_SEXE_TYPE_PARAMS)
            .sexeTypeAttributs(DEFAULT_SEXE_TYPE_ATTRIBUTS)
            .sexeStat(DEFAULT_SEXE_STAT);
        return sexe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createUpdatedEntity(EntityManager em) {
        Sexe sexe = new Sexe()
            .sexeValue(UPDATED_SEXE_VALUE)
            .sexeDescription(UPDATED_SEXE_DESCRIPTION)
            .sexeTypeParams(UPDATED_SEXE_TYPE_PARAMS)
            .sexeTypeAttributs(UPDATED_SEXE_TYPE_ATTRIBUTS)
            .sexeStat(UPDATED_SEXE_STAT);
        return sexe;
    }

    @BeforeEach
    public void initTest() {
        sexe = createEntity(em);
    }

    @Test
    @Transactional
    void createSexe() throws Exception {
        int databaseSizeBeforeCreate = sexeRepository.findAll().size();
        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);
        restSexeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isCreated());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate + 1);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexeValue()).isEqualTo(DEFAULT_SEXE_VALUE);
        assertThat(testSexe.getSexeDescription()).isEqualTo(DEFAULT_SEXE_DESCRIPTION);
        assertThat(testSexe.getSexeTypeParams()).isEqualTo(DEFAULT_SEXE_TYPE_PARAMS);
        assertThat(testSexe.getSexeTypeAttributs()).isEqualTo(DEFAULT_SEXE_TYPE_ATTRIBUTS);
        assertThat(testSexe.getSexeStat()).isEqualTo(DEFAULT_SEXE_STAT);
    }

    @Test
    @Transactional
    void createSexeWithExistingId() throws Exception {
        // Create the Sexe with an existing ID
        sexe.setSexeId(1L);
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        int databaseSizeBeforeCreate = sexeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSexeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSexeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = sexeRepository.findAll().size();
        // set the field null
        sexe.setSexeValue(null);

        // Create the Sexe, which fails.
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        restSexeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isBadRequest());

        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSexes() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList
        restSexeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=sexeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].sexeId").value(hasItem(sexe.getSexeId().intValue())))
            .andExpect(jsonPath("$.[*].sexeValue").value(hasItem(DEFAULT_SEXE_VALUE)))
            .andExpect(jsonPath("$.[*].sexeDescription").value(hasItem(DEFAULT_SEXE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sexeTypeParams").value(hasItem(DEFAULT_SEXE_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].sexeTypeAttributs").value(hasItem(DEFAULT_SEXE_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].sexeStat").value(hasItem(DEFAULT_SEXE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get the sexe
        restSexeMockMvc
            .perform(get(ENTITY_API_URL_ID, sexe.getSexeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.sexeId").value(sexe.getSexeId().intValue()))
            .andExpect(jsonPath("$.sexeValue").value(DEFAULT_SEXE_VALUE))
            .andExpect(jsonPath("$.sexeDescription").value(DEFAULT_SEXE_DESCRIPTION))
            .andExpect(jsonPath("$.sexeTypeParams").value(DEFAULT_SEXE_TYPE_PARAMS))
            .andExpect(jsonPath("$.sexeTypeAttributs").value(DEFAULT_SEXE_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.sexeStat").value(DEFAULT_SEXE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getSexesByIdFiltering() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        Long id = sexe.getSexeId();

        defaultSexeShouldBeFound("sexeId.equals=" + id);
        defaultSexeShouldNotBeFound("sexeId.notEquals=" + id);

        defaultSexeShouldBeFound("sexeId.greaterThanOrEqual=" + id);
        defaultSexeShouldNotBeFound("sexeId.greaterThan=" + id);

        defaultSexeShouldBeFound("sexeId.lessThanOrEqual=" + id);
        defaultSexeShouldNotBeFound("sexeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSexesBySexeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeValue equals to DEFAULT_SEXE_VALUE
        defaultSexeShouldBeFound("sexeValue.equals=" + DEFAULT_SEXE_VALUE);

        // Get all the sexeList where sexeValue equals to UPDATED_SEXE_VALUE
        defaultSexeShouldNotBeFound("sexeValue.equals=" + UPDATED_SEXE_VALUE);
    }

    @Test
    @Transactional
    void getAllSexesBySexeValueIsInShouldWork() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeValue in DEFAULT_SEXE_VALUE or UPDATED_SEXE_VALUE
        defaultSexeShouldBeFound("sexeValue.in=" + DEFAULT_SEXE_VALUE + "," + UPDATED_SEXE_VALUE);

        // Get all the sexeList where sexeValue equals to UPDATED_SEXE_VALUE
        defaultSexeShouldNotBeFound("sexeValue.in=" + UPDATED_SEXE_VALUE);
    }

    @Test
    @Transactional
    void getAllSexesBySexeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeValue is not null
        defaultSexeShouldBeFound("sexeValue.specified=true");

        // Get all the sexeList where sexeValue is null
        defaultSexeShouldNotBeFound("sexeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSexesBySexeValueContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeValue contains DEFAULT_SEXE_VALUE
        defaultSexeShouldBeFound("sexeValue.contains=" + DEFAULT_SEXE_VALUE);

        // Get all the sexeList where sexeValue contains UPDATED_SEXE_VALUE
        defaultSexeShouldNotBeFound("sexeValue.contains=" + UPDATED_SEXE_VALUE);
    }

    @Test
    @Transactional
    void getAllSexesBySexeValueNotContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeValue does not contain DEFAULT_SEXE_VALUE
        defaultSexeShouldNotBeFound("sexeValue.doesNotContain=" + DEFAULT_SEXE_VALUE);

        // Get all the sexeList where sexeValue does not contain UPDATED_SEXE_VALUE
        defaultSexeShouldBeFound("sexeValue.doesNotContain=" + UPDATED_SEXE_VALUE);
    }

    @Test
    @Transactional
    void getAllSexesBySexeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeDescription equals to DEFAULT_SEXE_DESCRIPTION
        defaultSexeShouldBeFound("sexeDescription.equals=" + DEFAULT_SEXE_DESCRIPTION);

        // Get all the sexeList where sexeDescription equals to UPDATED_SEXE_DESCRIPTION
        defaultSexeShouldNotBeFound("sexeDescription.equals=" + UPDATED_SEXE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSexesBySexeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeDescription in DEFAULT_SEXE_DESCRIPTION or UPDATED_SEXE_DESCRIPTION
        defaultSexeShouldBeFound("sexeDescription.in=" + DEFAULT_SEXE_DESCRIPTION + "," + UPDATED_SEXE_DESCRIPTION);

        // Get all the sexeList where sexeDescription equals to UPDATED_SEXE_DESCRIPTION
        defaultSexeShouldNotBeFound("sexeDescription.in=" + UPDATED_SEXE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSexesBySexeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeDescription is not null
        defaultSexeShouldBeFound("sexeDescription.specified=true");

        // Get all the sexeList where sexeDescription is null
        defaultSexeShouldNotBeFound("sexeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllSexesBySexeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeDescription contains DEFAULT_SEXE_DESCRIPTION
        defaultSexeShouldBeFound("sexeDescription.contains=" + DEFAULT_SEXE_DESCRIPTION);

        // Get all the sexeList where sexeDescription contains UPDATED_SEXE_DESCRIPTION
        defaultSexeShouldNotBeFound("sexeDescription.contains=" + UPDATED_SEXE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSexesBySexeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeDescription does not contain DEFAULT_SEXE_DESCRIPTION
        defaultSexeShouldNotBeFound("sexeDescription.doesNotContain=" + DEFAULT_SEXE_DESCRIPTION);

        // Get all the sexeList where sexeDescription does not contain UPDATED_SEXE_DESCRIPTION
        defaultSexeShouldBeFound("sexeDescription.doesNotContain=" + UPDATED_SEXE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeParams equals to DEFAULT_SEXE_TYPE_PARAMS
        defaultSexeShouldBeFound("sexeTypeParams.equals=" + DEFAULT_SEXE_TYPE_PARAMS);

        // Get all the sexeList where sexeTypeParams equals to UPDATED_SEXE_TYPE_PARAMS
        defaultSexeShouldNotBeFound("sexeTypeParams.equals=" + UPDATED_SEXE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeParams in DEFAULT_SEXE_TYPE_PARAMS or UPDATED_SEXE_TYPE_PARAMS
        defaultSexeShouldBeFound("sexeTypeParams.in=" + DEFAULT_SEXE_TYPE_PARAMS + "," + UPDATED_SEXE_TYPE_PARAMS);

        // Get all the sexeList where sexeTypeParams equals to UPDATED_SEXE_TYPE_PARAMS
        defaultSexeShouldNotBeFound("sexeTypeParams.in=" + UPDATED_SEXE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeParams is not null
        defaultSexeShouldBeFound("sexeTypeParams.specified=true");

        // Get all the sexeList where sexeTypeParams is null
        defaultSexeShouldNotBeFound("sexeTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeParams contains DEFAULT_SEXE_TYPE_PARAMS
        defaultSexeShouldBeFound("sexeTypeParams.contains=" + DEFAULT_SEXE_TYPE_PARAMS);

        // Get all the sexeList where sexeTypeParams contains UPDATED_SEXE_TYPE_PARAMS
        defaultSexeShouldNotBeFound("sexeTypeParams.contains=" + UPDATED_SEXE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeParams does not contain DEFAULT_SEXE_TYPE_PARAMS
        defaultSexeShouldNotBeFound("sexeTypeParams.doesNotContain=" + DEFAULT_SEXE_TYPE_PARAMS);

        // Get all the sexeList where sexeTypeParams does not contain UPDATED_SEXE_TYPE_PARAMS
        defaultSexeShouldBeFound("sexeTypeParams.doesNotContain=" + UPDATED_SEXE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeAttributs equals to DEFAULT_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldBeFound("sexeTypeAttributs.equals=" + DEFAULT_SEXE_TYPE_ATTRIBUTS);

        // Get all the sexeList where sexeTypeAttributs equals to UPDATED_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldNotBeFound("sexeTypeAttributs.equals=" + UPDATED_SEXE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeAttributs in DEFAULT_SEXE_TYPE_ATTRIBUTS or UPDATED_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldBeFound("sexeTypeAttributs.in=" + DEFAULT_SEXE_TYPE_ATTRIBUTS + "," + UPDATED_SEXE_TYPE_ATTRIBUTS);

        // Get all the sexeList where sexeTypeAttributs equals to UPDATED_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldNotBeFound("sexeTypeAttributs.in=" + UPDATED_SEXE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeAttributs is not null
        defaultSexeShouldBeFound("sexeTypeAttributs.specified=true");

        // Get all the sexeList where sexeTypeAttributs is null
        defaultSexeShouldNotBeFound("sexeTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeAttributs contains DEFAULT_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldBeFound("sexeTypeAttributs.contains=" + DEFAULT_SEXE_TYPE_ATTRIBUTS);

        // Get all the sexeList where sexeTypeAttributs contains UPDATED_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldNotBeFound("sexeTypeAttributs.contains=" + UPDATED_SEXE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeTypeAttributs does not contain DEFAULT_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldNotBeFound("sexeTypeAttributs.doesNotContain=" + DEFAULT_SEXE_TYPE_ATTRIBUTS);

        // Get all the sexeList where sexeTypeAttributs does not contain UPDATED_SEXE_TYPE_ATTRIBUTS
        defaultSexeShouldBeFound("sexeTypeAttributs.doesNotContain=" + UPDATED_SEXE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllSexesBySexeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeStat equals to DEFAULT_SEXE_STAT
        defaultSexeShouldBeFound("sexeStat.equals=" + DEFAULT_SEXE_STAT);

        // Get all the sexeList where sexeStat equals to UPDATED_SEXE_STAT
        defaultSexeShouldNotBeFound("sexeStat.equals=" + UPDATED_SEXE_STAT);
    }

    @Test
    @Transactional
    void getAllSexesBySexeStatIsInShouldWork() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeStat in DEFAULT_SEXE_STAT or UPDATED_SEXE_STAT
        defaultSexeShouldBeFound("sexeStat.in=" + DEFAULT_SEXE_STAT + "," + UPDATED_SEXE_STAT);

        // Get all the sexeList where sexeStat equals to UPDATED_SEXE_STAT
        defaultSexeShouldNotBeFound("sexeStat.in=" + UPDATED_SEXE_STAT);
    }

    @Test
    @Transactional
    void getAllSexesBySexeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList where sexeStat is not null
        defaultSexeShouldBeFound("sexeStat.specified=true");

        // Get all the sexeList where sexeStat is null
        defaultSexeShouldNotBeFound("sexeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllSexesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            sexeRepository.saveAndFlush(sexe);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        sexe.addAccreditation(accreditation);
        sexeRepository.saveAndFlush(sexe);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the sexeList where accreditation equals to accreditationId
        defaultSexeShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the sexeList where accreditation equals to (accreditationId + 1)
        defaultSexeShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSexeShouldBeFound(String filter) throws Exception {
        restSexeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=sexeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].sexeId").value(hasItem(sexe.getSexeId().intValue())))
            .andExpect(jsonPath("$.[*].sexeValue").value(hasItem(DEFAULT_SEXE_VALUE)))
            .andExpect(jsonPath("$.[*].sexeDescription").value(hasItem(DEFAULT_SEXE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sexeTypeParams").value(hasItem(DEFAULT_SEXE_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].sexeTypeAttributs").value(hasItem(DEFAULT_SEXE_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].sexeStat").value(hasItem(DEFAULT_SEXE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restSexeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=sexeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSexeShouldNotBeFound(String filter) throws Exception {
        restSexeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=sexeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSexeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=sexeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSexe() throws Exception {
        // Get the sexe
        restSexeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe
        Sexe updatedSexe = sexeRepository.findById(sexe.getSexeId()).get();
        // Disconnect from session so that the updates on updatedSexe are not directly saved in db
        em.detach(updatedSexe);
        updatedSexe
            .sexeValue(UPDATED_SEXE_VALUE)
            .sexeDescription(UPDATED_SEXE_DESCRIPTION)
            .sexeTypeParams(UPDATED_SEXE_TYPE_PARAMS)
            .sexeTypeAttributs(UPDATED_SEXE_TYPE_ATTRIBUTS)
            .sexeStat(UPDATED_SEXE_STAT);
        SexeDTO sexeDTO = sexeMapper.toDto(updatedSexe);

        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sexeDTO.getSexeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexeValue()).isEqualTo(UPDATED_SEXE_VALUE);
        assertThat(testSexe.getSexeDescription()).isEqualTo(UPDATED_SEXE_DESCRIPTION);
        assertThat(testSexe.getSexeTypeParams()).isEqualTo(UPDATED_SEXE_TYPE_PARAMS);
        assertThat(testSexe.getSexeTypeAttributs()).isEqualTo(UPDATED_SEXE_TYPE_ATTRIBUTS);
        assertThat(testSexe.getSexeStat()).isEqualTo(UPDATED_SEXE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setSexeId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sexeDTO.getSexeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setSexeId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setSexeId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSexeWithPatch() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe using partial update
        Sexe partialUpdatedSexe = new Sexe();
        partialUpdatedSexe.setSexeId(sexe.getSexeId());

        partialUpdatedSexe.sexeValue(UPDATED_SEXE_VALUE).sexeStat(UPDATED_SEXE_STAT);

        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSexe.getSexeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSexe))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexeValue()).isEqualTo(UPDATED_SEXE_VALUE);
        assertThat(testSexe.getSexeDescription()).isEqualTo(DEFAULT_SEXE_DESCRIPTION);
        assertThat(testSexe.getSexeTypeParams()).isEqualTo(DEFAULT_SEXE_TYPE_PARAMS);
        assertThat(testSexe.getSexeTypeAttributs()).isEqualTo(DEFAULT_SEXE_TYPE_ATTRIBUTS);
        assertThat(testSexe.getSexeStat()).isEqualTo(UPDATED_SEXE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateSexeWithPatch() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe using partial update
        Sexe partialUpdatedSexe = new Sexe();
        partialUpdatedSexe.setSexeId(sexe.getSexeId());

        partialUpdatedSexe
            .sexeValue(UPDATED_SEXE_VALUE)
            .sexeDescription(UPDATED_SEXE_DESCRIPTION)
            .sexeTypeParams(UPDATED_SEXE_TYPE_PARAMS)
            .sexeTypeAttributs(UPDATED_SEXE_TYPE_ATTRIBUTS)
            .sexeStat(UPDATED_SEXE_STAT);

        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSexe.getSexeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSexe))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexeValue()).isEqualTo(UPDATED_SEXE_VALUE);
        assertThat(testSexe.getSexeDescription()).isEqualTo(UPDATED_SEXE_DESCRIPTION);
        assertThat(testSexe.getSexeTypeParams()).isEqualTo(UPDATED_SEXE_TYPE_PARAMS);
        assertThat(testSexe.getSexeTypeAttributs()).isEqualTo(UPDATED_SEXE_TYPE_ATTRIBUTS);
        assertThat(testSexe.getSexeStat()).isEqualTo(UPDATED_SEXE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setSexeId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sexeDTO.getSexeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setSexeId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setSexeId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeDelete = sexeRepository.findAll().size();

        // Delete the sexe
        restSexeMockMvc
            .perform(delete(ENTITY_API_URL_ID, sexe.getSexeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
