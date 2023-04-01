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
import ma.sig.events.domain.Attachement;
import ma.sig.events.domain.AttachementType;
import ma.sig.events.repository.AttachementTypeRepository;
import ma.sig.events.service.criteria.AttachementTypeCriteria;
import ma.sig.events.service.dto.AttachementTypeDTO;
import ma.sig.events.service.mapper.AttachementTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AttachementTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttachementTypeResourceIT {

    private static final String DEFAULT_ATTACHEMENT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACHEMENT_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATTACHEMENT_TYPE_STAT = false;
    private static final Boolean UPDATED_ATTACHEMENT_TYPE_STAT = true;

    private static final String ENTITY_API_URL = "/api/attachement-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{attachementTypeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttachementTypeRepository attachementTypeRepository;

    @Autowired
    private AttachementTypeMapper attachementTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttachementTypeMockMvc;

    private AttachementType attachementType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachementType createEntity(EntityManager em) {
        AttachementType attachementType = new AttachementType()
            .attachementTypeName(DEFAULT_ATTACHEMENT_TYPE_NAME)
            .attachementTypeDescription(DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION)
            .attachementTypeParams(DEFAULT_ATTACHEMENT_TYPE_PARAMS)
            .attachementTypeAttributs(DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS)
            .attachementTypeStat(DEFAULT_ATTACHEMENT_TYPE_STAT);
        return attachementType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachementType createUpdatedEntity(EntityManager em) {
        AttachementType attachementType = new AttachementType()
            .attachementTypeName(UPDATED_ATTACHEMENT_TYPE_NAME)
            .attachementTypeDescription(UPDATED_ATTACHEMENT_TYPE_DESCRIPTION)
            .attachementTypeParams(UPDATED_ATTACHEMENT_TYPE_PARAMS)
            .attachementTypeAttributs(UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS)
            .attachementTypeStat(UPDATED_ATTACHEMENT_TYPE_STAT);
        return attachementType;
    }

    @BeforeEach
    public void initTest() {
        attachementType = createEntity(em);
    }

    @Test
    @Transactional
    void createAttachementType() throws Exception {
        int databaseSizeBeforeCreate = attachementTypeRepository.findAll().size();
        // Create the AttachementType
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);
        restAttachementTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AttachementType testAttachementType = attachementTypeList.get(attachementTypeList.size() - 1);
        assertThat(testAttachementType.getAttachementTypeName()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_NAME);
        assertThat(testAttachementType.getAttachementTypeDescription()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION);
        assertThat(testAttachementType.getAttachementTypeParams()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_PARAMS);
        assertThat(testAttachementType.getAttachementTypeAttributs()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS);
        assertThat(testAttachementType.getAttachementTypeStat()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_STAT);
    }

    @Test
    @Transactional
    void createAttachementTypeWithExistingId() throws Exception {
        // Create the AttachementType with an existing ID
        attachementType.setAttachementTypeId(1L);
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        int databaseSizeBeforeCreate = attachementTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachementTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttachementTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachementTypeRepository.findAll().size();
        // set the field null
        attachementType.setAttachementTypeName(null);

        // Create the AttachementType, which fails.
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        restAttachementTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttachementTypes() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList
        restAttachementTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=attachementTypeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].attachementTypeId").value(hasItem(attachementType.getAttachementTypeId().intValue())))
            .andExpect(jsonPath("$.[*].attachementTypeName").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].attachementTypeDescription").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachementTypeParams").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].attachementTypeAttributs").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].attachementTypeStat").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getAttachementType() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get the attachementType
        restAttachementTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, attachementType.getAttachementTypeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.attachementTypeId").value(attachementType.getAttachementTypeId().intValue()))
            .andExpect(jsonPath("$.attachementTypeName").value(DEFAULT_ATTACHEMENT_TYPE_NAME))
            .andExpect(jsonPath("$.attachementTypeDescription").value(DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.attachementTypeParams").value(DEFAULT_ATTACHEMENT_TYPE_PARAMS))
            .andExpect(jsonPath("$.attachementTypeAttributs").value(DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.attachementTypeStat").value(DEFAULT_ATTACHEMENT_TYPE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getAttachementTypesByIdFiltering() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        Long id = attachementType.getAttachementTypeId();

        defaultAttachementTypeShouldBeFound("attachementTypeId.equals=" + id);
        defaultAttachementTypeShouldNotBeFound("attachementTypeId.notEquals=" + id);

        defaultAttachementTypeShouldBeFound("attachementTypeId.greaterThanOrEqual=" + id);
        defaultAttachementTypeShouldNotBeFound("attachementTypeId.greaterThan=" + id);

        defaultAttachementTypeShouldBeFound("attachementTypeId.lessThanOrEqual=" + id);
        defaultAttachementTypeShouldNotBeFound("attachementTypeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeName equals to DEFAULT_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldBeFound("attachementTypeName.equals=" + DEFAULT_ATTACHEMENT_TYPE_NAME);

        // Get all the attachementTypeList where attachementTypeName equals to UPDATED_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldNotBeFound("attachementTypeName.equals=" + UPDATED_ATTACHEMENT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeName in DEFAULT_ATTACHEMENT_TYPE_NAME or UPDATED_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldBeFound(
            "attachementTypeName.in=" + DEFAULT_ATTACHEMENT_TYPE_NAME + "," + UPDATED_ATTACHEMENT_TYPE_NAME
        );

        // Get all the attachementTypeList where attachementTypeName equals to UPDATED_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldNotBeFound("attachementTypeName.in=" + UPDATED_ATTACHEMENT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeName is not null
        defaultAttachementTypeShouldBeFound("attachementTypeName.specified=true");

        // Get all the attachementTypeList where attachementTypeName is null
        defaultAttachementTypeShouldNotBeFound("attachementTypeName.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeNameContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeName contains DEFAULT_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldBeFound("attachementTypeName.contains=" + DEFAULT_ATTACHEMENT_TYPE_NAME);

        // Get all the attachementTypeList where attachementTypeName contains UPDATED_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldNotBeFound("attachementTypeName.contains=" + UPDATED_ATTACHEMENT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeName does not contain DEFAULT_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldNotBeFound("attachementTypeName.doesNotContain=" + DEFAULT_ATTACHEMENT_TYPE_NAME);

        // Get all the attachementTypeList where attachementTypeName does not contain UPDATED_ATTACHEMENT_TYPE_NAME
        defaultAttachementTypeShouldBeFound("attachementTypeName.doesNotContain=" + UPDATED_ATTACHEMENT_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeDescription equals to DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldBeFound("attachementTypeDescription.equals=" + DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION);

        // Get all the attachementTypeList where attachementTypeDescription equals to UPDATED_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldNotBeFound("attachementTypeDescription.equals=" + UPDATED_ATTACHEMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeDescription in DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION or UPDATED_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldBeFound(
            "attachementTypeDescription.in=" + DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION + "," + UPDATED_ATTACHEMENT_TYPE_DESCRIPTION
        );

        // Get all the attachementTypeList where attachementTypeDescription equals to UPDATED_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldNotBeFound("attachementTypeDescription.in=" + UPDATED_ATTACHEMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeDescription is not null
        defaultAttachementTypeShouldBeFound("attachementTypeDescription.specified=true");

        // Get all the attachementTypeList where attachementTypeDescription is null
        defaultAttachementTypeShouldNotBeFound("attachementTypeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeDescription contains DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldBeFound("attachementTypeDescription.contains=" + DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION);

        // Get all the attachementTypeList where attachementTypeDescription contains UPDATED_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldNotBeFound("attachementTypeDescription.contains=" + UPDATED_ATTACHEMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeDescription does not contain DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldNotBeFound("attachementTypeDescription.doesNotContain=" + DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION);

        // Get all the attachementTypeList where attachementTypeDescription does not contain UPDATED_ATTACHEMENT_TYPE_DESCRIPTION
        defaultAttachementTypeShouldBeFound("attachementTypeDescription.doesNotContain=" + UPDATED_ATTACHEMENT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeParams equals to DEFAULT_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldBeFound("attachementTypeParams.equals=" + DEFAULT_ATTACHEMENT_TYPE_PARAMS);

        // Get all the attachementTypeList where attachementTypeParams equals to UPDATED_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldNotBeFound("attachementTypeParams.equals=" + UPDATED_ATTACHEMENT_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeParams in DEFAULT_ATTACHEMENT_TYPE_PARAMS or UPDATED_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldBeFound(
            "attachementTypeParams.in=" + DEFAULT_ATTACHEMENT_TYPE_PARAMS + "," + UPDATED_ATTACHEMENT_TYPE_PARAMS
        );

        // Get all the attachementTypeList where attachementTypeParams equals to UPDATED_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldNotBeFound("attachementTypeParams.in=" + UPDATED_ATTACHEMENT_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeParams is not null
        defaultAttachementTypeShouldBeFound("attachementTypeParams.specified=true");

        // Get all the attachementTypeList where attachementTypeParams is null
        defaultAttachementTypeShouldNotBeFound("attachementTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeParams contains DEFAULT_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldBeFound("attachementTypeParams.contains=" + DEFAULT_ATTACHEMENT_TYPE_PARAMS);

        // Get all the attachementTypeList where attachementTypeParams contains UPDATED_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldNotBeFound("attachementTypeParams.contains=" + UPDATED_ATTACHEMENT_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeParams does not contain DEFAULT_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldNotBeFound("attachementTypeParams.doesNotContain=" + DEFAULT_ATTACHEMENT_TYPE_PARAMS);

        // Get all the attachementTypeList where attachementTypeParams does not contain UPDATED_ATTACHEMENT_TYPE_PARAMS
        defaultAttachementTypeShouldBeFound("attachementTypeParams.doesNotContain=" + UPDATED_ATTACHEMENT_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeAttributs equals to DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldBeFound("attachementTypeAttributs.equals=" + DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS);

        // Get all the attachementTypeList where attachementTypeAttributs equals to UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldNotBeFound("attachementTypeAttributs.equals=" + UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeAttributs in DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS or UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldBeFound(
            "attachementTypeAttributs.in=" + DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS + "," + UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS
        );

        // Get all the attachementTypeList where attachementTypeAttributs equals to UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldNotBeFound("attachementTypeAttributs.in=" + UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeAttributs is not null
        defaultAttachementTypeShouldBeFound("attachementTypeAttributs.specified=true");

        // Get all the attachementTypeList where attachementTypeAttributs is null
        defaultAttachementTypeShouldNotBeFound("attachementTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeAttributs contains DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldBeFound("attachementTypeAttributs.contains=" + DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS);

        // Get all the attachementTypeList where attachementTypeAttributs contains UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldNotBeFound("attachementTypeAttributs.contains=" + UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeAttributs does not contain DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldNotBeFound("attachementTypeAttributs.doesNotContain=" + DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS);

        // Get all the attachementTypeList where attachementTypeAttributs does not contain UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS
        defaultAttachementTypeShouldBeFound("attachementTypeAttributs.doesNotContain=" + UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeStat equals to DEFAULT_ATTACHEMENT_TYPE_STAT
        defaultAttachementTypeShouldBeFound("attachementTypeStat.equals=" + DEFAULT_ATTACHEMENT_TYPE_STAT);

        // Get all the attachementTypeList where attachementTypeStat equals to UPDATED_ATTACHEMENT_TYPE_STAT
        defaultAttachementTypeShouldNotBeFound("attachementTypeStat.equals=" + UPDATED_ATTACHEMENT_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeStatIsInShouldWork() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeStat in DEFAULT_ATTACHEMENT_TYPE_STAT or UPDATED_ATTACHEMENT_TYPE_STAT
        defaultAttachementTypeShouldBeFound(
            "attachementTypeStat.in=" + DEFAULT_ATTACHEMENT_TYPE_STAT + "," + UPDATED_ATTACHEMENT_TYPE_STAT
        );

        // Get all the attachementTypeList where attachementTypeStat equals to UPDATED_ATTACHEMENT_TYPE_STAT
        defaultAttachementTypeShouldNotBeFound("attachementTypeStat.in=" + UPDATED_ATTACHEMENT_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementTypeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        // Get all the attachementTypeList where attachementTypeStat is not null
        defaultAttachementTypeShouldBeFound("attachementTypeStat.specified=true");

        // Get all the attachementTypeList where attachementTypeStat is null
        defaultAttachementTypeShouldNotBeFound("attachementTypeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllAttachementTypesByAttachementIsEqualToSomething() throws Exception {
        Attachement attachement;
        if (TestUtil.findAll(em, Attachement.class).isEmpty()) {
            attachementTypeRepository.saveAndFlush(attachementType);
            attachement = AttachementResourceIT.createEntity(em);
        } else {
            attachement = TestUtil.findAll(em, Attachement.class).get(0);
        }
        em.persist(attachement);
        em.flush();
        attachementType.addAttachement(attachement);
        attachementTypeRepository.saveAndFlush(attachementType);
        Long attachementId = attachement.getAttachementId();

        // Get all the attachementTypeList where attachement equals to attachementId
        defaultAttachementTypeShouldBeFound("attachementId.equals=" + attachementId);

        // Get all the attachementTypeList where attachement equals to (attachementId + 1)
        defaultAttachementTypeShouldNotBeFound("attachementId.equals=" + (attachementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttachementTypeShouldBeFound(String filter) throws Exception {
        restAttachementTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=attachementTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].attachementTypeId").value(hasItem(attachementType.getAttachementTypeId().intValue())))
            .andExpect(jsonPath("$.[*].attachementTypeName").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].attachementTypeDescription").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachementTypeParams").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].attachementTypeAttributs").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].attachementTypeStat").value(hasItem(DEFAULT_ATTACHEMENT_TYPE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restAttachementTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=attachementTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttachementTypeShouldNotBeFound(String filter) throws Exception {
        restAttachementTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=attachementTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttachementTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=attachementTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAttachementType() throws Exception {
        // Get the attachementType
        restAttachementTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttachementType() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();

        // Update the attachementType
        AttachementType updatedAttachementType = attachementTypeRepository.findById(attachementType.getAttachementTypeId()).get();
        // Disconnect from session so that the updates on updatedAttachementType are not directly saved in db
        em.detach(updatedAttachementType);
        updatedAttachementType
            .attachementTypeName(UPDATED_ATTACHEMENT_TYPE_NAME)
            .attachementTypeDescription(UPDATED_ATTACHEMENT_TYPE_DESCRIPTION)
            .attachementTypeParams(UPDATED_ATTACHEMENT_TYPE_PARAMS)
            .attachementTypeAttributs(UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS)
            .attachementTypeStat(UPDATED_ATTACHEMENT_TYPE_STAT);
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(updatedAttachementType);

        restAttachementTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachementTypeDTO.getAttachementTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
        AttachementType testAttachementType = attachementTypeList.get(attachementTypeList.size() - 1);
        assertThat(testAttachementType.getAttachementTypeName()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_NAME);
        assertThat(testAttachementType.getAttachementTypeDescription()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_DESCRIPTION);
        assertThat(testAttachementType.getAttachementTypeParams()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_PARAMS);
        assertThat(testAttachementType.getAttachementTypeAttributs()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS);
        assertThat(testAttachementType.getAttachementTypeStat()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingAttachementType() throws Exception {
        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();
        attachementType.setAttachementTypeId(count.incrementAndGet());

        // Create the AttachementType
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachementTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachementTypeDTO.getAttachementTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttachementType() throws Exception {
        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();
        attachementType.setAttachementTypeId(count.incrementAndGet());

        // Create the AttachementType
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttachementType() throws Exception {
        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();
        attachementType.setAttachementTypeId(count.incrementAndGet());

        // Create the AttachementType
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttachementTypeWithPatch() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();

        // Update the attachementType using partial update
        AttachementType partialUpdatedAttachementType = new AttachementType();
        partialUpdatedAttachementType.setAttachementTypeId(attachementType.getAttachementTypeId());

        partialUpdatedAttachementType
            .attachementTypeParams(UPDATED_ATTACHEMENT_TYPE_PARAMS)
            .attachementTypeStat(UPDATED_ATTACHEMENT_TYPE_STAT);

        restAttachementTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachementType.getAttachementTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachementType))
            )
            .andExpect(status().isOk());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
        AttachementType testAttachementType = attachementTypeList.get(attachementTypeList.size() - 1);
        assertThat(testAttachementType.getAttachementTypeName()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_NAME);
        assertThat(testAttachementType.getAttachementTypeDescription()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_DESCRIPTION);
        assertThat(testAttachementType.getAttachementTypeParams()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_PARAMS);
        assertThat(testAttachementType.getAttachementTypeAttributs()).isEqualTo(DEFAULT_ATTACHEMENT_TYPE_ATTRIBUTS);
        assertThat(testAttachementType.getAttachementTypeStat()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateAttachementTypeWithPatch() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();

        // Update the attachementType using partial update
        AttachementType partialUpdatedAttachementType = new AttachementType();
        partialUpdatedAttachementType.setAttachementTypeId(attachementType.getAttachementTypeId());

        partialUpdatedAttachementType
            .attachementTypeName(UPDATED_ATTACHEMENT_TYPE_NAME)
            .attachementTypeDescription(UPDATED_ATTACHEMENT_TYPE_DESCRIPTION)
            .attachementTypeParams(UPDATED_ATTACHEMENT_TYPE_PARAMS)
            .attachementTypeAttributs(UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS)
            .attachementTypeStat(UPDATED_ATTACHEMENT_TYPE_STAT);

        restAttachementTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachementType.getAttachementTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachementType))
            )
            .andExpect(status().isOk());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
        AttachementType testAttachementType = attachementTypeList.get(attachementTypeList.size() - 1);
        assertThat(testAttachementType.getAttachementTypeName()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_NAME);
        assertThat(testAttachementType.getAttachementTypeDescription()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_DESCRIPTION);
        assertThat(testAttachementType.getAttachementTypeParams()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_PARAMS);
        assertThat(testAttachementType.getAttachementTypeAttributs()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_ATTRIBUTS);
        assertThat(testAttachementType.getAttachementTypeStat()).isEqualTo(UPDATED_ATTACHEMENT_TYPE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingAttachementType() throws Exception {
        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();
        attachementType.setAttachementTypeId(count.incrementAndGet());

        // Create the AttachementType
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachementTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attachementTypeDTO.getAttachementTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttachementType() throws Exception {
        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();
        attachementType.setAttachementTypeId(count.incrementAndGet());

        // Create the AttachementType
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttachementType() throws Exception {
        int databaseSizeBeforeUpdate = attachementTypeRepository.findAll().size();
        attachementType.setAttachementTypeId(count.incrementAndGet());

        // Create the AttachementType
        AttachementTypeDTO attachementTypeDTO = attachementTypeMapper.toDto(attachementType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachementTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachementTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttachementType in the database
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttachementType() throws Exception {
        // Initialize the database
        attachementTypeRepository.saveAndFlush(attachementType);

        int databaseSizeBeforeDelete = attachementTypeRepository.findAll().size();

        // Delete the attachementType
        restAttachementTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, attachementType.getAttachementTypeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttachementType> attachementTypeList = attachementTypeRepository.findAll();
        assertThat(attachementTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
