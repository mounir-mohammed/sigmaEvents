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
import ma.sig.events.domain.InfoSupp;
import ma.sig.events.domain.InfoSuppType;
import ma.sig.events.repository.InfoSuppTypeRepository;
import ma.sig.events.service.criteria.InfoSuppTypeCriteria;
import ma.sig.events.service.dto.InfoSuppTypeDTO;
import ma.sig.events.service.mapper.InfoSuppTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InfoSuppTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InfoSuppTypeResourceIT {

    private static final String DEFAULT_INFO_SUPP_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_SUPP_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_SUPP_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SUPP_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INFO_SUPP_TYPE_STAT = false;
    private static final Boolean UPDATED_INFO_SUPP_TYPE_STAT = true;

    private static final String ENTITY_API_URL = "/api/info-supp-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{infoSuppTypeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfoSuppTypeRepository infoSuppTypeRepository;

    @Autowired
    private InfoSuppTypeMapper infoSuppTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfoSuppTypeMockMvc;

    private InfoSuppType infoSuppType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoSuppType createEntity(EntityManager em) {
        InfoSuppType infoSuppType = new InfoSuppType()
            .infoSuppTypeName(DEFAULT_INFO_SUPP_TYPE_NAME)
            .infoSuppTypeDescription(DEFAULT_INFO_SUPP_TYPE_DESCRIPTION)
            .infoSuppTypeParams(DEFAULT_INFO_SUPP_TYPE_PARAMS)
            .infoSuppTypeAttributs(DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS)
            .infoSuppTypeStat(DEFAULT_INFO_SUPP_TYPE_STAT);
        return infoSuppType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoSuppType createUpdatedEntity(EntityManager em) {
        InfoSuppType infoSuppType = new InfoSuppType()
            .infoSuppTypeName(UPDATED_INFO_SUPP_TYPE_NAME)
            .infoSuppTypeDescription(UPDATED_INFO_SUPP_TYPE_DESCRIPTION)
            .infoSuppTypeParams(UPDATED_INFO_SUPP_TYPE_PARAMS)
            .infoSuppTypeAttributs(UPDATED_INFO_SUPP_TYPE_ATTRIBUTS)
            .infoSuppTypeStat(UPDATED_INFO_SUPP_TYPE_STAT);
        return infoSuppType;
    }

    @BeforeEach
    public void initTest() {
        infoSuppType = createEntity(em);
    }

    @Test
    @Transactional
    void createInfoSuppType() throws Exception {
        int databaseSizeBeforeCreate = infoSuppTypeRepository.findAll().size();
        // Create the InfoSuppType
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);
        restInfoSuppTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InfoSuppType testInfoSuppType = infoSuppTypeList.get(infoSuppTypeList.size() - 1);
        assertThat(testInfoSuppType.getInfoSuppTypeName()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_NAME);
        assertThat(testInfoSuppType.getInfoSuppTypeDescription()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_DESCRIPTION);
        assertThat(testInfoSuppType.getInfoSuppTypeParams()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_PARAMS);
        assertThat(testInfoSuppType.getInfoSuppTypeAttributs()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS);
        assertThat(testInfoSuppType.getInfoSuppTypeStat()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_STAT);
    }

    @Test
    @Transactional
    void createInfoSuppTypeWithExistingId() throws Exception {
        // Create the InfoSuppType with an existing ID
        infoSuppType.setInfoSuppTypeId(1L);
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        int databaseSizeBeforeCreate = infoSuppTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoSuppTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInfoSuppTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoSuppTypeRepository.findAll().size();
        // set the field null
        infoSuppType.setInfoSuppTypeName(null);

        // Create the InfoSuppType, which fails.
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        restInfoSuppTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypes() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList
        restInfoSuppTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=infoSuppTypeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].infoSuppTypeId").value(hasItem(infoSuppType.getInfoSuppTypeId().intValue())))
            .andExpect(jsonPath("$.[*].infoSuppTypeName").value(hasItem(DEFAULT_INFO_SUPP_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].infoSuppTypeDescription").value(hasItem(DEFAULT_INFO_SUPP_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].infoSuppTypeParams").value(hasItem(DEFAULT_INFO_SUPP_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].infoSuppTypeAttributs").value(hasItem(DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].infoSuppTypeStat").value(hasItem(DEFAULT_INFO_SUPP_TYPE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getInfoSuppType() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get the infoSuppType
        restInfoSuppTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, infoSuppType.getInfoSuppTypeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.infoSuppTypeId").value(infoSuppType.getInfoSuppTypeId().intValue()))
            .andExpect(jsonPath("$.infoSuppTypeName").value(DEFAULT_INFO_SUPP_TYPE_NAME))
            .andExpect(jsonPath("$.infoSuppTypeDescription").value(DEFAULT_INFO_SUPP_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.infoSuppTypeParams").value(DEFAULT_INFO_SUPP_TYPE_PARAMS))
            .andExpect(jsonPath("$.infoSuppTypeAttributs").value(DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.infoSuppTypeStat").value(DEFAULT_INFO_SUPP_TYPE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getInfoSuppTypesByIdFiltering() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        Long id = infoSuppType.getInfoSuppTypeId();

        defaultInfoSuppTypeShouldBeFound("infoSuppTypeId.equals=" + id);
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeId.notEquals=" + id);

        defaultInfoSuppTypeShouldBeFound("infoSuppTypeId.greaterThanOrEqual=" + id);
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeId.greaterThan=" + id);

        defaultInfoSuppTypeShouldBeFound("infoSuppTypeId.lessThanOrEqual=" + id);
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeName equals to DEFAULT_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeName.equals=" + DEFAULT_INFO_SUPP_TYPE_NAME);

        // Get all the infoSuppTypeList where infoSuppTypeName equals to UPDATED_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeName.equals=" + UPDATED_INFO_SUPP_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeName in DEFAULT_INFO_SUPP_TYPE_NAME or UPDATED_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeName.in=" + DEFAULT_INFO_SUPP_TYPE_NAME + "," + UPDATED_INFO_SUPP_TYPE_NAME);

        // Get all the infoSuppTypeList where infoSuppTypeName equals to UPDATED_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeName.in=" + UPDATED_INFO_SUPP_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeName is not null
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeName.specified=true");

        // Get all the infoSuppTypeList where infoSuppTypeName is null
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeName.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeNameContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeName contains DEFAULT_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeName.contains=" + DEFAULT_INFO_SUPP_TYPE_NAME);

        // Get all the infoSuppTypeList where infoSuppTypeName contains UPDATED_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeName.contains=" + UPDATED_INFO_SUPP_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeName does not contain DEFAULT_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeName.doesNotContain=" + DEFAULT_INFO_SUPP_TYPE_NAME);

        // Get all the infoSuppTypeList where infoSuppTypeName does not contain UPDATED_INFO_SUPP_TYPE_NAME
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeName.doesNotContain=" + UPDATED_INFO_SUPP_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeDescription equals to DEFAULT_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeDescription.equals=" + DEFAULT_INFO_SUPP_TYPE_DESCRIPTION);

        // Get all the infoSuppTypeList where infoSuppTypeDescription equals to UPDATED_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeDescription.equals=" + UPDATED_INFO_SUPP_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeDescription in DEFAULT_INFO_SUPP_TYPE_DESCRIPTION or UPDATED_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldBeFound(
            "infoSuppTypeDescription.in=" + DEFAULT_INFO_SUPP_TYPE_DESCRIPTION + "," + UPDATED_INFO_SUPP_TYPE_DESCRIPTION
        );

        // Get all the infoSuppTypeList where infoSuppTypeDescription equals to UPDATED_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeDescription.in=" + UPDATED_INFO_SUPP_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeDescription is not null
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeDescription.specified=true");

        // Get all the infoSuppTypeList where infoSuppTypeDescription is null
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeDescription contains DEFAULT_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeDescription.contains=" + DEFAULT_INFO_SUPP_TYPE_DESCRIPTION);

        // Get all the infoSuppTypeList where infoSuppTypeDescription contains UPDATED_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeDescription.contains=" + UPDATED_INFO_SUPP_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeDescription does not contain DEFAULT_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeDescription.doesNotContain=" + DEFAULT_INFO_SUPP_TYPE_DESCRIPTION);

        // Get all the infoSuppTypeList where infoSuppTypeDescription does not contain UPDATED_INFO_SUPP_TYPE_DESCRIPTION
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeDescription.doesNotContain=" + UPDATED_INFO_SUPP_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeParams equals to DEFAULT_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeParams.equals=" + DEFAULT_INFO_SUPP_TYPE_PARAMS);

        // Get all the infoSuppTypeList where infoSuppTypeParams equals to UPDATED_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeParams.equals=" + UPDATED_INFO_SUPP_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeParams in DEFAULT_INFO_SUPP_TYPE_PARAMS or UPDATED_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeParams.in=" + DEFAULT_INFO_SUPP_TYPE_PARAMS + "," + UPDATED_INFO_SUPP_TYPE_PARAMS);

        // Get all the infoSuppTypeList where infoSuppTypeParams equals to UPDATED_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeParams.in=" + UPDATED_INFO_SUPP_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeParams is not null
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeParams.specified=true");

        // Get all the infoSuppTypeList where infoSuppTypeParams is null
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeParams contains DEFAULT_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeParams.contains=" + DEFAULT_INFO_SUPP_TYPE_PARAMS);

        // Get all the infoSuppTypeList where infoSuppTypeParams contains UPDATED_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeParams.contains=" + UPDATED_INFO_SUPP_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeParams does not contain DEFAULT_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeParams.doesNotContain=" + DEFAULT_INFO_SUPP_TYPE_PARAMS);

        // Get all the infoSuppTypeList where infoSuppTypeParams does not contain UPDATED_INFO_SUPP_TYPE_PARAMS
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeParams.doesNotContain=" + UPDATED_INFO_SUPP_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs equals to DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeAttributs.equals=" + DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs equals to UPDATED_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeAttributs.equals=" + UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs in DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS or UPDATED_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldBeFound(
            "infoSuppTypeAttributs.in=" + DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS + "," + UPDATED_INFO_SUPP_TYPE_ATTRIBUTS
        );

        // Get all the infoSuppTypeList where infoSuppTypeAttributs equals to UPDATED_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeAttributs.in=" + UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs is not null
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeAttributs.specified=true");

        // Get all the infoSuppTypeList where infoSuppTypeAttributs is null
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs contains DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeAttributs.contains=" + DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs contains UPDATED_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeAttributs.contains=" + UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs does not contain DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeAttributs.doesNotContain=" + DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS);

        // Get all the infoSuppTypeList where infoSuppTypeAttributs does not contain UPDATED_INFO_SUPP_TYPE_ATTRIBUTS
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeAttributs.doesNotContain=" + UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeStat equals to DEFAULT_INFO_SUPP_TYPE_STAT
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeStat.equals=" + DEFAULT_INFO_SUPP_TYPE_STAT);

        // Get all the infoSuppTypeList where infoSuppTypeStat equals to UPDATED_INFO_SUPP_TYPE_STAT
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeStat.equals=" + UPDATED_INFO_SUPP_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeStatIsInShouldWork() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeStat in DEFAULT_INFO_SUPP_TYPE_STAT or UPDATED_INFO_SUPP_TYPE_STAT
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeStat.in=" + DEFAULT_INFO_SUPP_TYPE_STAT + "," + UPDATED_INFO_SUPP_TYPE_STAT);

        // Get all the infoSuppTypeList where infoSuppTypeStat equals to UPDATED_INFO_SUPP_TYPE_STAT
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeStat.in=" + UPDATED_INFO_SUPP_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppTypeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        // Get all the infoSuppTypeList where infoSuppTypeStat is not null
        defaultInfoSuppTypeShouldBeFound("infoSuppTypeStat.specified=true");

        // Get all the infoSuppTypeList where infoSuppTypeStat is null
        defaultInfoSuppTypeShouldNotBeFound("infoSuppTypeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllInfoSuppTypesByInfoSuppIsEqualToSomething() throws Exception {
        InfoSupp infoSupp;
        if (TestUtil.findAll(em, InfoSupp.class).isEmpty()) {
            infoSuppTypeRepository.saveAndFlush(infoSuppType);
            infoSupp = InfoSuppResourceIT.createEntity(em);
        } else {
            infoSupp = TestUtil.findAll(em, InfoSupp.class).get(0);
        }
        em.persist(infoSupp);
        em.flush();
        infoSuppType.addInfoSupp(infoSupp);
        infoSuppTypeRepository.saveAndFlush(infoSuppType);
        Long infoSuppId = infoSupp.getInfoSuppId();

        // Get all the infoSuppTypeList where infoSupp equals to infoSuppId
        defaultInfoSuppTypeShouldBeFound("infoSuppId.equals=" + infoSuppId);

        // Get all the infoSuppTypeList where infoSupp equals to (infoSuppId + 1)
        defaultInfoSuppTypeShouldNotBeFound("infoSuppId.equals=" + (infoSuppId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInfoSuppTypeShouldBeFound(String filter) throws Exception {
        restInfoSuppTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=infoSuppTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].infoSuppTypeId").value(hasItem(infoSuppType.getInfoSuppTypeId().intValue())))
            .andExpect(jsonPath("$.[*].infoSuppTypeName").value(hasItem(DEFAULT_INFO_SUPP_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].infoSuppTypeDescription").value(hasItem(DEFAULT_INFO_SUPP_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].infoSuppTypeParams").value(hasItem(DEFAULT_INFO_SUPP_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].infoSuppTypeAttributs").value(hasItem(DEFAULT_INFO_SUPP_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].infoSuppTypeStat").value(hasItem(DEFAULT_INFO_SUPP_TYPE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restInfoSuppTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=infoSuppTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInfoSuppTypeShouldNotBeFound(String filter) throws Exception {
        restInfoSuppTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=infoSuppTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInfoSuppTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=infoSuppTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInfoSuppType() throws Exception {
        // Get the infoSuppType
        restInfoSuppTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInfoSuppType() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();

        // Update the infoSuppType
        InfoSuppType updatedInfoSuppType = infoSuppTypeRepository.findById(infoSuppType.getInfoSuppTypeId()).get();
        // Disconnect from session so that the updates on updatedInfoSuppType are not directly saved in db
        em.detach(updatedInfoSuppType);
        updatedInfoSuppType
            .infoSuppTypeName(UPDATED_INFO_SUPP_TYPE_NAME)
            .infoSuppTypeDescription(UPDATED_INFO_SUPP_TYPE_DESCRIPTION)
            .infoSuppTypeParams(UPDATED_INFO_SUPP_TYPE_PARAMS)
            .infoSuppTypeAttributs(UPDATED_INFO_SUPP_TYPE_ATTRIBUTS)
            .infoSuppTypeStat(UPDATED_INFO_SUPP_TYPE_STAT);
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(updatedInfoSuppType);

        restInfoSuppTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infoSuppTypeDTO.getInfoSuppTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
        InfoSuppType testInfoSuppType = infoSuppTypeList.get(infoSuppTypeList.size() - 1);
        assertThat(testInfoSuppType.getInfoSuppTypeName()).isEqualTo(UPDATED_INFO_SUPP_TYPE_NAME);
        assertThat(testInfoSuppType.getInfoSuppTypeDescription()).isEqualTo(UPDATED_INFO_SUPP_TYPE_DESCRIPTION);
        assertThat(testInfoSuppType.getInfoSuppTypeParams()).isEqualTo(UPDATED_INFO_SUPP_TYPE_PARAMS);
        assertThat(testInfoSuppType.getInfoSuppTypeAttributs()).isEqualTo(UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);
        assertThat(testInfoSuppType.getInfoSuppTypeStat()).isEqualTo(UPDATED_INFO_SUPP_TYPE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingInfoSuppType() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();
        infoSuppType.setInfoSuppTypeId(count.incrementAndGet());

        // Create the InfoSuppType
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoSuppTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infoSuppTypeDTO.getInfoSuppTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInfoSuppType() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();
        infoSuppType.setInfoSuppTypeId(count.incrementAndGet());

        // Create the InfoSuppType
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInfoSuppType() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();
        infoSuppType.setInfoSuppTypeId(count.incrementAndGet());

        // Create the InfoSuppType
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInfoSuppTypeWithPatch() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();

        // Update the infoSuppType using partial update
        InfoSuppType partialUpdatedInfoSuppType = new InfoSuppType();
        partialUpdatedInfoSuppType.setInfoSuppTypeId(infoSuppType.getInfoSuppTypeId());

        partialUpdatedInfoSuppType.infoSuppTypeName(UPDATED_INFO_SUPP_TYPE_NAME).infoSuppTypeAttributs(UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);

        restInfoSuppTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoSuppType.getInfoSuppTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoSuppType))
            )
            .andExpect(status().isOk());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
        InfoSuppType testInfoSuppType = infoSuppTypeList.get(infoSuppTypeList.size() - 1);
        assertThat(testInfoSuppType.getInfoSuppTypeName()).isEqualTo(UPDATED_INFO_SUPP_TYPE_NAME);
        assertThat(testInfoSuppType.getInfoSuppTypeDescription()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_DESCRIPTION);
        assertThat(testInfoSuppType.getInfoSuppTypeParams()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_PARAMS);
        assertThat(testInfoSuppType.getInfoSuppTypeAttributs()).isEqualTo(UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);
        assertThat(testInfoSuppType.getInfoSuppTypeStat()).isEqualTo(DEFAULT_INFO_SUPP_TYPE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateInfoSuppTypeWithPatch() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();

        // Update the infoSuppType using partial update
        InfoSuppType partialUpdatedInfoSuppType = new InfoSuppType();
        partialUpdatedInfoSuppType.setInfoSuppTypeId(infoSuppType.getInfoSuppTypeId());

        partialUpdatedInfoSuppType
            .infoSuppTypeName(UPDATED_INFO_SUPP_TYPE_NAME)
            .infoSuppTypeDescription(UPDATED_INFO_SUPP_TYPE_DESCRIPTION)
            .infoSuppTypeParams(UPDATED_INFO_SUPP_TYPE_PARAMS)
            .infoSuppTypeAttributs(UPDATED_INFO_SUPP_TYPE_ATTRIBUTS)
            .infoSuppTypeStat(UPDATED_INFO_SUPP_TYPE_STAT);

        restInfoSuppTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoSuppType.getInfoSuppTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoSuppType))
            )
            .andExpect(status().isOk());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
        InfoSuppType testInfoSuppType = infoSuppTypeList.get(infoSuppTypeList.size() - 1);
        assertThat(testInfoSuppType.getInfoSuppTypeName()).isEqualTo(UPDATED_INFO_SUPP_TYPE_NAME);
        assertThat(testInfoSuppType.getInfoSuppTypeDescription()).isEqualTo(UPDATED_INFO_SUPP_TYPE_DESCRIPTION);
        assertThat(testInfoSuppType.getInfoSuppTypeParams()).isEqualTo(UPDATED_INFO_SUPP_TYPE_PARAMS);
        assertThat(testInfoSuppType.getInfoSuppTypeAttributs()).isEqualTo(UPDATED_INFO_SUPP_TYPE_ATTRIBUTS);
        assertThat(testInfoSuppType.getInfoSuppTypeStat()).isEqualTo(UPDATED_INFO_SUPP_TYPE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingInfoSuppType() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();
        infoSuppType.setInfoSuppTypeId(count.incrementAndGet());

        // Create the InfoSuppType
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoSuppTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, infoSuppTypeDTO.getInfoSuppTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInfoSuppType() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();
        infoSuppType.setInfoSuppTypeId(count.incrementAndGet());

        // Create the InfoSuppType
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInfoSuppType() throws Exception {
        int databaseSizeBeforeUpdate = infoSuppTypeRepository.findAll().size();
        infoSuppType.setInfoSuppTypeId(count.incrementAndGet());

        // Create the InfoSuppType
        InfoSuppTypeDTO infoSuppTypeDTO = infoSuppTypeMapper.toDto(infoSuppType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoSuppTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoSuppTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoSuppType in the database
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInfoSuppType() throws Exception {
        // Initialize the database
        infoSuppTypeRepository.saveAndFlush(infoSuppType);

        int databaseSizeBeforeDelete = infoSuppTypeRepository.findAll().size();

        // Delete the infoSuppType
        restInfoSuppTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, infoSuppType.getInfoSuppTypeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoSuppType> infoSuppTypeList = infoSuppTypeRepository.findAll();
        assertThat(infoSuppTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
