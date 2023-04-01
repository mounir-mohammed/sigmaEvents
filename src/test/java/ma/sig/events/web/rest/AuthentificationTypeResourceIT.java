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
import ma.sig.events.domain.AuthentificationType;
import ma.sig.events.repository.AuthentificationTypeRepository;
import ma.sig.events.service.criteria.AuthentificationTypeCriteria;
import ma.sig.events.service.dto.AuthentificationTypeDTO;
import ma.sig.events.service.mapper.AuthentificationTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AuthentificationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthentificationTypeResourceIT {

    private static final String DEFAULT_AUTHENTIFICATION_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHENTIFICATION_TYPE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHENTIFICATION_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_AUTHENTIFICATION_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTHENTIFICATION_TYPE_STAT = false;
    private static final Boolean UPDATED_AUTHENTIFICATION_TYPE_STAT = true;

    private static final String ENTITY_API_URL = "/api/authentification-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{authentificationTypeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthentificationTypeRepository authentificationTypeRepository;

    @Autowired
    private AuthentificationTypeMapper authentificationTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthentificationTypeMockMvc;

    private AuthentificationType authentificationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthentificationType createEntity(EntityManager em) {
        AuthentificationType authentificationType = new AuthentificationType()
            .authentificationTypeValue(DEFAULT_AUTHENTIFICATION_TYPE_VALUE)
            .authentificationTypeDescription(DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION)
            .authentificationTypeParams(DEFAULT_AUTHENTIFICATION_TYPE_PARAMS)
            .authentificationTypeAttributs(DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS)
            .authentificationTypeStat(DEFAULT_AUTHENTIFICATION_TYPE_STAT);
        return authentificationType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthentificationType createUpdatedEntity(EntityManager em) {
        AuthentificationType authentificationType = new AuthentificationType()
            .authentificationTypeValue(UPDATED_AUTHENTIFICATION_TYPE_VALUE)
            .authentificationTypeDescription(UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION)
            .authentificationTypeParams(UPDATED_AUTHENTIFICATION_TYPE_PARAMS)
            .authentificationTypeAttributs(UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS)
            .authentificationTypeStat(UPDATED_AUTHENTIFICATION_TYPE_STAT);
        return authentificationType;
    }

    @BeforeEach
    public void initTest() {
        authentificationType = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthentificationType() throws Exception {
        int databaseSizeBeforeCreate = authentificationTypeRepository.findAll().size();
        // Create the AuthentificationType
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);
        restAuthentificationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AuthentificationType testAuthentificationType = authentificationTypeList.get(authentificationTypeList.size() - 1);
        assertThat(testAuthentificationType.getAuthentificationTypeValue()).isEqualTo(DEFAULT_AUTHENTIFICATION_TYPE_VALUE);
        assertThat(testAuthentificationType.getAuthentificationTypeDescription()).isEqualTo(DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION);
        assertThat(testAuthentificationType.getAuthentificationTypeParams()).isEqualTo(DEFAULT_AUTHENTIFICATION_TYPE_PARAMS);
        assertThat(testAuthentificationType.getAuthentificationTypeAttributs()).isEqualTo(DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS);
        assertThat(testAuthentificationType.getAuthentificationTypeStat()).isEqualTo(DEFAULT_AUTHENTIFICATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void createAuthentificationTypeWithExistingId() throws Exception {
        // Create the AuthentificationType with an existing ID
        authentificationType.setAuthentificationTypeId(1L);
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        int databaseSizeBeforeCreate = authentificationTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthentificationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAuthentificationTypeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = authentificationTypeRepository.findAll().size();
        // set the field null
        authentificationType.setAuthentificationTypeValue(null);

        // Create the AuthentificationType, which fails.
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        restAuthentificationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypes() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList
        restAuthentificationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=authentificationTypeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].authentificationTypeId").value(hasItem(authentificationType.getAuthentificationTypeId().intValue())))
            .andExpect(jsonPath("$.[*].authentificationTypeValue").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].authentificationTypeDescription").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].authentificationTypeParams").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].authentificationTypeAttributs").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].authentificationTypeStat").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getAuthentificationType() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get the authentificationType
        restAuthentificationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, authentificationType.getAuthentificationTypeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.authentificationTypeId").value(authentificationType.getAuthentificationTypeId().intValue()))
            .andExpect(jsonPath("$.authentificationTypeValue").value(DEFAULT_AUTHENTIFICATION_TYPE_VALUE))
            .andExpect(jsonPath("$.authentificationTypeDescription").value(DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.authentificationTypeParams").value(DEFAULT_AUTHENTIFICATION_TYPE_PARAMS))
            .andExpect(jsonPath("$.authentificationTypeAttributs").value(DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.authentificationTypeStat").value(DEFAULT_AUTHENTIFICATION_TYPE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getAuthentificationTypesByIdFiltering() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        Long id = authentificationType.getAuthentificationTypeId();

        defaultAuthentificationTypeShouldBeFound("authentificationTypeId.equals=" + id);
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeId.notEquals=" + id);

        defaultAuthentificationTypeShouldBeFound("authentificationTypeId.greaterThanOrEqual=" + id);
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeId.greaterThan=" + id);

        defaultAuthentificationTypeShouldBeFound("authentificationTypeId.lessThanOrEqual=" + id);
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeValue equals to DEFAULT_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldBeFound("authentificationTypeValue.equals=" + DEFAULT_AUTHENTIFICATION_TYPE_VALUE);

        // Get all the authentificationTypeList where authentificationTypeValue equals to UPDATED_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeValue.equals=" + UPDATED_AUTHENTIFICATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeValueIsInShouldWork() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeValue in DEFAULT_AUTHENTIFICATION_TYPE_VALUE or UPDATED_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldBeFound(
            "authentificationTypeValue.in=" + DEFAULT_AUTHENTIFICATION_TYPE_VALUE + "," + UPDATED_AUTHENTIFICATION_TYPE_VALUE
        );

        // Get all the authentificationTypeList where authentificationTypeValue equals to UPDATED_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeValue.in=" + UPDATED_AUTHENTIFICATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeValue is not null
        defaultAuthentificationTypeShouldBeFound("authentificationTypeValue.specified=true");

        // Get all the authentificationTypeList where authentificationTypeValue is null
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeValueContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeValue contains DEFAULT_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldBeFound("authentificationTypeValue.contains=" + DEFAULT_AUTHENTIFICATION_TYPE_VALUE);

        // Get all the authentificationTypeList where authentificationTypeValue contains UPDATED_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeValue.contains=" + UPDATED_AUTHENTIFICATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeValueNotContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeValue does not contain DEFAULT_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeValue.doesNotContain=" + DEFAULT_AUTHENTIFICATION_TYPE_VALUE);

        // Get all the authentificationTypeList where authentificationTypeValue does not contain UPDATED_AUTHENTIFICATION_TYPE_VALUE
        defaultAuthentificationTypeShouldBeFound("authentificationTypeValue.doesNotContain=" + UPDATED_AUTHENTIFICATION_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeDescription equals to DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldBeFound("authentificationTypeDescription.equals=" + DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION);

        // Get all the authentificationTypeList where authentificationTypeDescription equals to UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeDescription.equals=" + UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeDescription in DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION or UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldBeFound(
            "authentificationTypeDescription.in=" +
            DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION +
            "," +
            UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        );

        // Get all the authentificationTypeList where authentificationTypeDescription equals to UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeDescription.in=" + UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeDescription is not null
        defaultAuthentificationTypeShouldBeFound("authentificationTypeDescription.specified=true");

        // Get all the authentificationTypeList where authentificationTypeDescription is null
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeDescription contains DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldBeFound("authentificationTypeDescription.contains=" + DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION);

        // Get all the authentificationTypeList where authentificationTypeDescription contains UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldNotBeFound(
            "authentificationTypeDescription.contains=" + UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeDescription does not contain DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldNotBeFound(
            "authentificationTypeDescription.doesNotContain=" + DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION
        );

        // Get all the authentificationTypeList where authentificationTypeDescription does not contain UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        defaultAuthentificationTypeShouldBeFound(
            "authentificationTypeDescription.doesNotContain=" + UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeParams equals to DEFAULT_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldBeFound("authentificationTypeParams.equals=" + DEFAULT_AUTHENTIFICATION_TYPE_PARAMS);

        // Get all the authentificationTypeList where authentificationTypeParams equals to UPDATED_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeParams.equals=" + UPDATED_AUTHENTIFICATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeParams in DEFAULT_AUTHENTIFICATION_TYPE_PARAMS or UPDATED_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldBeFound(
            "authentificationTypeParams.in=" + DEFAULT_AUTHENTIFICATION_TYPE_PARAMS + "," + UPDATED_AUTHENTIFICATION_TYPE_PARAMS
        );

        // Get all the authentificationTypeList where authentificationTypeParams equals to UPDATED_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeParams.in=" + UPDATED_AUTHENTIFICATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeParams is not null
        defaultAuthentificationTypeShouldBeFound("authentificationTypeParams.specified=true");

        // Get all the authentificationTypeList where authentificationTypeParams is null
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeParams contains DEFAULT_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldBeFound("authentificationTypeParams.contains=" + DEFAULT_AUTHENTIFICATION_TYPE_PARAMS);

        // Get all the authentificationTypeList where authentificationTypeParams contains UPDATED_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeParams.contains=" + UPDATED_AUTHENTIFICATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeParams does not contain DEFAULT_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeParams.doesNotContain=" + DEFAULT_AUTHENTIFICATION_TYPE_PARAMS);

        // Get all the authentificationTypeList where authentificationTypeParams does not contain UPDATED_AUTHENTIFICATION_TYPE_PARAMS
        defaultAuthentificationTypeShouldBeFound("authentificationTypeParams.doesNotContain=" + UPDATED_AUTHENTIFICATION_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeAttributs equals to DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldBeFound("authentificationTypeAttributs.equals=" + DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS);

        // Get all the authentificationTypeList where authentificationTypeAttributs equals to UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeAttributs.equals=" + UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeAttributs in DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS or UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldBeFound(
            "authentificationTypeAttributs.in=" + DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS + "," + UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS
        );

        // Get all the authentificationTypeList where authentificationTypeAttributs equals to UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeAttributs.in=" + UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeAttributs is not null
        defaultAuthentificationTypeShouldBeFound("authentificationTypeAttributs.specified=true");

        // Get all the authentificationTypeList where authentificationTypeAttributs is null
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeAttributs contains DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldBeFound("authentificationTypeAttributs.contains=" + DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS);

        // Get all the authentificationTypeList where authentificationTypeAttributs contains UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeAttributs.contains=" + UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeAttributs does not contain DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldNotBeFound(
            "authentificationTypeAttributs.doesNotContain=" + DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS
        );

        // Get all the authentificationTypeList where authentificationTypeAttributs does not contain UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS
        defaultAuthentificationTypeShouldBeFound("authentificationTypeAttributs.doesNotContain=" + UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeStat equals to DEFAULT_AUTHENTIFICATION_TYPE_STAT
        defaultAuthentificationTypeShouldBeFound("authentificationTypeStat.equals=" + DEFAULT_AUTHENTIFICATION_TYPE_STAT);

        // Get all the authentificationTypeList where authentificationTypeStat equals to UPDATED_AUTHENTIFICATION_TYPE_STAT
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeStat.equals=" + UPDATED_AUTHENTIFICATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeStatIsInShouldWork() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeStat in DEFAULT_AUTHENTIFICATION_TYPE_STAT or UPDATED_AUTHENTIFICATION_TYPE_STAT
        defaultAuthentificationTypeShouldBeFound(
            "authentificationTypeStat.in=" + DEFAULT_AUTHENTIFICATION_TYPE_STAT + "," + UPDATED_AUTHENTIFICATION_TYPE_STAT
        );

        // Get all the authentificationTypeList where authentificationTypeStat equals to UPDATED_AUTHENTIFICATION_TYPE_STAT
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeStat.in=" + UPDATED_AUTHENTIFICATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllAuthentificationTypesByAuthentificationTypeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        // Get all the authentificationTypeList where authentificationTypeStat is not null
        defaultAuthentificationTypeShouldBeFound("authentificationTypeStat.specified=true");

        // Get all the authentificationTypeList where authentificationTypeStat is null
        defaultAuthentificationTypeShouldNotBeFound("authentificationTypeStat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuthentificationTypeShouldBeFound(String filter) throws Exception {
        restAuthentificationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=authentificationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].authentificationTypeId").value(hasItem(authentificationType.getAuthentificationTypeId().intValue())))
            .andExpect(jsonPath("$.[*].authentificationTypeValue").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].authentificationTypeDescription").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].authentificationTypeParams").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].authentificationTypeAttributs").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].authentificationTypeStat").value(hasItem(DEFAULT_AUTHENTIFICATION_TYPE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restAuthentificationTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=authentificationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuthentificationTypeShouldNotBeFound(String filter) throws Exception {
        restAuthentificationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=authentificationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuthentificationTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=authentificationTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAuthentificationType() throws Exception {
        // Get the authentificationType
        restAuthentificationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuthentificationType() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();

        // Update the authentificationType
        AuthentificationType updatedAuthentificationType = authentificationTypeRepository
            .findById(authentificationType.getAuthentificationTypeId())
            .get();
        // Disconnect from session so that the updates on updatedAuthentificationType are not directly saved in db
        em.detach(updatedAuthentificationType);
        updatedAuthentificationType
            .authentificationTypeValue(UPDATED_AUTHENTIFICATION_TYPE_VALUE)
            .authentificationTypeDescription(UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION)
            .authentificationTypeParams(UPDATED_AUTHENTIFICATION_TYPE_PARAMS)
            .authentificationTypeAttributs(UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS)
            .authentificationTypeStat(UPDATED_AUTHENTIFICATION_TYPE_STAT);
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(updatedAuthentificationType);

        restAuthentificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authentificationTypeDTO.getAuthentificationTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
        AuthentificationType testAuthentificationType = authentificationTypeList.get(authentificationTypeList.size() - 1);
        assertThat(testAuthentificationType.getAuthentificationTypeValue()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_VALUE);
        assertThat(testAuthentificationType.getAuthentificationTypeDescription()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION);
        assertThat(testAuthentificationType.getAuthentificationTypeParams()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_PARAMS);
        assertThat(testAuthentificationType.getAuthentificationTypeAttributs()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);
        assertThat(testAuthentificationType.getAuthentificationTypeStat()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingAuthentificationType() throws Exception {
        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();
        authentificationType.setAuthentificationTypeId(count.incrementAndGet());

        // Create the AuthentificationType
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthentificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authentificationTypeDTO.getAuthentificationTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthentificationType() throws Exception {
        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();
        authentificationType.setAuthentificationTypeId(count.incrementAndGet());

        // Create the AuthentificationType
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthentificationType() throws Exception {
        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();
        authentificationType.setAuthentificationTypeId(count.incrementAndGet());

        // Create the AuthentificationType
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthentificationTypeWithPatch() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();

        // Update the authentificationType using partial update
        AuthentificationType partialUpdatedAuthentificationType = new AuthentificationType();
        partialUpdatedAuthentificationType.setAuthentificationTypeId(authentificationType.getAuthentificationTypeId());

        partialUpdatedAuthentificationType
            .authentificationTypeValue(UPDATED_AUTHENTIFICATION_TYPE_VALUE)
            .authentificationTypeDescription(UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION)
            .authentificationTypeAttributs(UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);

        restAuthentificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthentificationType.getAuthentificationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthentificationType))
            )
            .andExpect(status().isOk());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
        AuthentificationType testAuthentificationType = authentificationTypeList.get(authentificationTypeList.size() - 1);
        assertThat(testAuthentificationType.getAuthentificationTypeValue()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_VALUE);
        assertThat(testAuthentificationType.getAuthentificationTypeDescription()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION);
        assertThat(testAuthentificationType.getAuthentificationTypeParams()).isEqualTo(DEFAULT_AUTHENTIFICATION_TYPE_PARAMS);
        assertThat(testAuthentificationType.getAuthentificationTypeAttributs()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);
        assertThat(testAuthentificationType.getAuthentificationTypeStat()).isEqualTo(DEFAULT_AUTHENTIFICATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateAuthentificationTypeWithPatch() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();

        // Update the authentificationType using partial update
        AuthentificationType partialUpdatedAuthentificationType = new AuthentificationType();
        partialUpdatedAuthentificationType.setAuthentificationTypeId(authentificationType.getAuthentificationTypeId());

        partialUpdatedAuthentificationType
            .authentificationTypeValue(UPDATED_AUTHENTIFICATION_TYPE_VALUE)
            .authentificationTypeDescription(UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION)
            .authentificationTypeParams(UPDATED_AUTHENTIFICATION_TYPE_PARAMS)
            .authentificationTypeAttributs(UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS)
            .authentificationTypeStat(UPDATED_AUTHENTIFICATION_TYPE_STAT);

        restAuthentificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthentificationType.getAuthentificationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthentificationType))
            )
            .andExpect(status().isOk());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
        AuthentificationType testAuthentificationType = authentificationTypeList.get(authentificationTypeList.size() - 1);
        assertThat(testAuthentificationType.getAuthentificationTypeValue()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_VALUE);
        assertThat(testAuthentificationType.getAuthentificationTypeDescription()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_DESCRIPTION);
        assertThat(testAuthentificationType.getAuthentificationTypeParams()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_PARAMS);
        assertThat(testAuthentificationType.getAuthentificationTypeAttributs()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_ATTRIBUTS);
        assertThat(testAuthentificationType.getAuthentificationTypeStat()).isEqualTo(UPDATED_AUTHENTIFICATION_TYPE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingAuthentificationType() throws Exception {
        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();
        authentificationType.setAuthentificationTypeId(count.incrementAndGet());

        // Create the AuthentificationType
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthentificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authentificationTypeDTO.getAuthentificationTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthentificationType() throws Exception {
        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();
        authentificationType.setAuthentificationTypeId(count.incrementAndGet());

        // Create the AuthentificationType
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthentificationType() throws Exception {
        int databaseSizeBeforeUpdate = authentificationTypeRepository.findAll().size();
        authentificationType.setAuthentificationTypeId(count.incrementAndGet());

        // Create the AuthentificationType
        AuthentificationTypeDTO authentificationTypeDTO = authentificationTypeMapper.toDto(authentificationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authentificationTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthentificationType in the database
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthentificationType() throws Exception {
        // Initialize the database
        authentificationTypeRepository.saveAndFlush(authentificationType);

        int databaseSizeBeforeDelete = authentificationTypeRepository.findAll().size();

        // Delete the authentificationType
        restAuthentificationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, authentificationType.getAuthentificationTypeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuthentificationType> authentificationTypeList = authentificationTypeRepository.findAll();
        assertThat(authentificationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
