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
import ma.sig.events.domain.Code;
import ma.sig.events.domain.CodeType;
import ma.sig.events.domain.Event;
import ma.sig.events.repository.CodeRepository;
import ma.sig.events.service.criteria.CodeCriteria;
import ma.sig.events.service.dto.CodeDTO;
import ma.sig.events.service.mapper.CodeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodeResourceIT {

    private static final String DEFAULT_CODE_FOR_ENTITY = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FOR_ENTITY = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_ENTITY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ENTITY_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CODE_USED = false;
    private static final Boolean UPDATED_CODE_USED = true;

    private static final String DEFAULT_CODE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CODE_STAT = false;
    private static final Boolean UPDATED_CODE_STAT = true;

    private static final String ENTITY_API_URL = "/api/codes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{codeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodeMockMvc;

    private Code code;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Code createEntity(EntityManager em) {
        Code code = new Code()
            .codeForEntity(DEFAULT_CODE_FOR_ENTITY)
            .codeEntityValue(DEFAULT_CODE_ENTITY_VALUE)
            .codeValue(DEFAULT_CODE_VALUE)
            .codeUsed(DEFAULT_CODE_USED)
            .codeParams(DEFAULT_CODE_PARAMS)
            .codeAttributs(DEFAULT_CODE_ATTRIBUTS)
            .codeStat(DEFAULT_CODE_STAT);
        return code;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Code createUpdatedEntity(EntityManager em) {
        Code code = new Code()
            .codeForEntity(UPDATED_CODE_FOR_ENTITY)
            .codeEntityValue(UPDATED_CODE_ENTITY_VALUE)
            .codeValue(UPDATED_CODE_VALUE)
            .codeUsed(UPDATED_CODE_USED)
            .codeParams(UPDATED_CODE_PARAMS)
            .codeAttributs(UPDATED_CODE_ATTRIBUTS)
            .codeStat(UPDATED_CODE_STAT);
        return code;
    }

    @BeforeEach
    public void initTest() {
        code = createEntity(em);
    }

    @Test
    @Transactional
    void createCode() throws Exception {
        int databaseSizeBeforeCreate = codeRepository.findAll().size();
        // Create the Code
        CodeDTO codeDTO = codeMapper.toDto(code);
        restCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeDTO)))
            .andExpect(status().isCreated());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeCreate + 1);
        Code testCode = codeList.get(codeList.size() - 1);
        assertThat(testCode.getCodeForEntity()).isEqualTo(DEFAULT_CODE_FOR_ENTITY);
        assertThat(testCode.getCodeEntityValue()).isEqualTo(DEFAULT_CODE_ENTITY_VALUE);
        assertThat(testCode.getCodeValue()).isEqualTo(DEFAULT_CODE_VALUE);
        assertThat(testCode.getCodeUsed()).isEqualTo(DEFAULT_CODE_USED);
        assertThat(testCode.getCodeParams()).isEqualTo(DEFAULT_CODE_PARAMS);
        assertThat(testCode.getCodeAttributs()).isEqualTo(DEFAULT_CODE_ATTRIBUTS);
        assertThat(testCode.getCodeStat()).isEqualTo(DEFAULT_CODE_STAT);
    }

    @Test
    @Transactional
    void createCodeWithExistingId() throws Exception {
        // Create the Code with an existing ID
        code.setCodeId(1L);
        CodeDTO codeDTO = codeMapper.toDto(code);

        int databaseSizeBeforeCreate = codeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeRepository.findAll().size();
        // set the field null
        code.setCodeValue(null);

        // Create the Code, which fails.
        CodeDTO codeDTO = codeMapper.toDto(code);

        restCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeDTO)))
            .andExpect(status().isBadRequest());

        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCodes() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList
        restCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].codeId").value(hasItem(code.getCodeId().intValue())))
            .andExpect(jsonPath("$.[*].codeForEntity").value(hasItem(DEFAULT_CODE_FOR_ENTITY)))
            .andExpect(jsonPath("$.[*].codeEntityValue").value(hasItem(DEFAULT_CODE_ENTITY_VALUE)))
            .andExpect(jsonPath("$.[*].codeValue").value(hasItem(DEFAULT_CODE_VALUE)))
            .andExpect(jsonPath("$.[*].codeUsed").value(hasItem(DEFAULT_CODE_USED.booleanValue())))
            .andExpect(jsonPath("$.[*].codeParams").value(hasItem(DEFAULT_CODE_PARAMS)))
            .andExpect(jsonPath("$.[*].codeAttributs").value(hasItem(DEFAULT_CODE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].codeStat").value(hasItem(DEFAULT_CODE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getCode() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get the code
        restCodeMockMvc
            .perform(get(ENTITY_API_URL_ID, code.getCodeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.codeId").value(code.getCodeId().intValue()))
            .andExpect(jsonPath("$.codeForEntity").value(DEFAULT_CODE_FOR_ENTITY))
            .andExpect(jsonPath("$.codeEntityValue").value(DEFAULT_CODE_ENTITY_VALUE))
            .andExpect(jsonPath("$.codeValue").value(DEFAULT_CODE_VALUE))
            .andExpect(jsonPath("$.codeUsed").value(DEFAULT_CODE_USED.booleanValue()))
            .andExpect(jsonPath("$.codeParams").value(DEFAULT_CODE_PARAMS))
            .andExpect(jsonPath("$.codeAttributs").value(DEFAULT_CODE_ATTRIBUTS))
            .andExpect(jsonPath("$.codeStat").value(DEFAULT_CODE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCodesByIdFiltering() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        Long id = code.getCodeId();

        defaultCodeShouldBeFound("codeId.equals=" + id);
        defaultCodeShouldNotBeFound("codeId.notEquals=" + id);

        defaultCodeShouldBeFound("codeId.greaterThanOrEqual=" + id);
        defaultCodeShouldNotBeFound("codeId.greaterThan=" + id);

        defaultCodeShouldBeFound("codeId.lessThanOrEqual=" + id);
        defaultCodeShouldNotBeFound("codeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCodesByCodeForEntityIsEqualToSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeForEntity equals to DEFAULT_CODE_FOR_ENTITY
        defaultCodeShouldBeFound("codeForEntity.equals=" + DEFAULT_CODE_FOR_ENTITY);

        // Get all the codeList where codeForEntity equals to UPDATED_CODE_FOR_ENTITY
        defaultCodeShouldNotBeFound("codeForEntity.equals=" + UPDATED_CODE_FOR_ENTITY);
    }

    @Test
    @Transactional
    void getAllCodesByCodeForEntityIsInShouldWork() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeForEntity in DEFAULT_CODE_FOR_ENTITY or UPDATED_CODE_FOR_ENTITY
        defaultCodeShouldBeFound("codeForEntity.in=" + DEFAULT_CODE_FOR_ENTITY + "," + UPDATED_CODE_FOR_ENTITY);

        // Get all the codeList where codeForEntity equals to UPDATED_CODE_FOR_ENTITY
        defaultCodeShouldNotBeFound("codeForEntity.in=" + UPDATED_CODE_FOR_ENTITY);
    }

    @Test
    @Transactional
    void getAllCodesByCodeForEntityIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeForEntity is not null
        defaultCodeShouldBeFound("codeForEntity.specified=true");

        // Get all the codeList where codeForEntity is null
        defaultCodeShouldNotBeFound("codeForEntity.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByCodeForEntityContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeForEntity contains DEFAULT_CODE_FOR_ENTITY
        defaultCodeShouldBeFound("codeForEntity.contains=" + DEFAULT_CODE_FOR_ENTITY);

        // Get all the codeList where codeForEntity contains UPDATED_CODE_FOR_ENTITY
        defaultCodeShouldNotBeFound("codeForEntity.contains=" + UPDATED_CODE_FOR_ENTITY);
    }

    @Test
    @Transactional
    void getAllCodesByCodeForEntityNotContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeForEntity does not contain DEFAULT_CODE_FOR_ENTITY
        defaultCodeShouldNotBeFound("codeForEntity.doesNotContain=" + DEFAULT_CODE_FOR_ENTITY);

        // Get all the codeList where codeForEntity does not contain UPDATED_CODE_FOR_ENTITY
        defaultCodeShouldBeFound("codeForEntity.doesNotContain=" + UPDATED_CODE_FOR_ENTITY);
    }

    @Test
    @Transactional
    void getAllCodesByCodeEntityValueIsEqualToSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeEntityValue equals to DEFAULT_CODE_ENTITY_VALUE
        defaultCodeShouldBeFound("codeEntityValue.equals=" + DEFAULT_CODE_ENTITY_VALUE);

        // Get all the codeList where codeEntityValue equals to UPDATED_CODE_ENTITY_VALUE
        defaultCodeShouldNotBeFound("codeEntityValue.equals=" + UPDATED_CODE_ENTITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeEntityValueIsInShouldWork() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeEntityValue in DEFAULT_CODE_ENTITY_VALUE or UPDATED_CODE_ENTITY_VALUE
        defaultCodeShouldBeFound("codeEntityValue.in=" + DEFAULT_CODE_ENTITY_VALUE + "," + UPDATED_CODE_ENTITY_VALUE);

        // Get all the codeList where codeEntityValue equals to UPDATED_CODE_ENTITY_VALUE
        defaultCodeShouldNotBeFound("codeEntityValue.in=" + UPDATED_CODE_ENTITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeEntityValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeEntityValue is not null
        defaultCodeShouldBeFound("codeEntityValue.specified=true");

        // Get all the codeList where codeEntityValue is null
        defaultCodeShouldNotBeFound("codeEntityValue.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByCodeEntityValueContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeEntityValue contains DEFAULT_CODE_ENTITY_VALUE
        defaultCodeShouldBeFound("codeEntityValue.contains=" + DEFAULT_CODE_ENTITY_VALUE);

        // Get all the codeList where codeEntityValue contains UPDATED_CODE_ENTITY_VALUE
        defaultCodeShouldNotBeFound("codeEntityValue.contains=" + UPDATED_CODE_ENTITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeEntityValueNotContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeEntityValue does not contain DEFAULT_CODE_ENTITY_VALUE
        defaultCodeShouldNotBeFound("codeEntityValue.doesNotContain=" + DEFAULT_CODE_ENTITY_VALUE);

        // Get all the codeList where codeEntityValue does not contain UPDATED_CODE_ENTITY_VALUE
        defaultCodeShouldBeFound("codeEntityValue.doesNotContain=" + UPDATED_CODE_ENTITY_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeValue equals to DEFAULT_CODE_VALUE
        defaultCodeShouldBeFound("codeValue.equals=" + DEFAULT_CODE_VALUE);

        // Get all the codeList where codeValue equals to UPDATED_CODE_VALUE
        defaultCodeShouldNotBeFound("codeValue.equals=" + UPDATED_CODE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeValueIsInShouldWork() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeValue in DEFAULT_CODE_VALUE or UPDATED_CODE_VALUE
        defaultCodeShouldBeFound("codeValue.in=" + DEFAULT_CODE_VALUE + "," + UPDATED_CODE_VALUE);

        // Get all the codeList where codeValue equals to UPDATED_CODE_VALUE
        defaultCodeShouldNotBeFound("codeValue.in=" + UPDATED_CODE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeValue is not null
        defaultCodeShouldBeFound("codeValue.specified=true");

        // Get all the codeList where codeValue is null
        defaultCodeShouldNotBeFound("codeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByCodeValueContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeValue contains DEFAULT_CODE_VALUE
        defaultCodeShouldBeFound("codeValue.contains=" + DEFAULT_CODE_VALUE);

        // Get all the codeList where codeValue contains UPDATED_CODE_VALUE
        defaultCodeShouldNotBeFound("codeValue.contains=" + UPDATED_CODE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeValueNotContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeValue does not contain DEFAULT_CODE_VALUE
        defaultCodeShouldNotBeFound("codeValue.doesNotContain=" + DEFAULT_CODE_VALUE);

        // Get all the codeList where codeValue does not contain UPDATED_CODE_VALUE
        defaultCodeShouldBeFound("codeValue.doesNotContain=" + UPDATED_CODE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodesByCodeUsedIsEqualToSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeUsed equals to DEFAULT_CODE_USED
        defaultCodeShouldBeFound("codeUsed.equals=" + DEFAULT_CODE_USED);

        // Get all the codeList where codeUsed equals to UPDATED_CODE_USED
        defaultCodeShouldNotBeFound("codeUsed.equals=" + UPDATED_CODE_USED);
    }

    @Test
    @Transactional
    void getAllCodesByCodeUsedIsInShouldWork() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeUsed in DEFAULT_CODE_USED or UPDATED_CODE_USED
        defaultCodeShouldBeFound("codeUsed.in=" + DEFAULT_CODE_USED + "," + UPDATED_CODE_USED);

        // Get all the codeList where codeUsed equals to UPDATED_CODE_USED
        defaultCodeShouldNotBeFound("codeUsed.in=" + UPDATED_CODE_USED);
    }

    @Test
    @Transactional
    void getAllCodesByCodeUsedIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeUsed is not null
        defaultCodeShouldBeFound("codeUsed.specified=true");

        // Get all the codeList where codeUsed is null
        defaultCodeShouldNotBeFound("codeUsed.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByCodeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeParams equals to DEFAULT_CODE_PARAMS
        defaultCodeShouldBeFound("codeParams.equals=" + DEFAULT_CODE_PARAMS);

        // Get all the codeList where codeParams equals to UPDATED_CODE_PARAMS
        defaultCodeShouldNotBeFound("codeParams.equals=" + UPDATED_CODE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeParams in DEFAULT_CODE_PARAMS or UPDATED_CODE_PARAMS
        defaultCodeShouldBeFound("codeParams.in=" + DEFAULT_CODE_PARAMS + "," + UPDATED_CODE_PARAMS);

        // Get all the codeList where codeParams equals to UPDATED_CODE_PARAMS
        defaultCodeShouldNotBeFound("codeParams.in=" + UPDATED_CODE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeParams is not null
        defaultCodeShouldBeFound("codeParams.specified=true");

        // Get all the codeList where codeParams is null
        defaultCodeShouldNotBeFound("codeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByCodeParamsContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeParams contains DEFAULT_CODE_PARAMS
        defaultCodeShouldBeFound("codeParams.contains=" + DEFAULT_CODE_PARAMS);

        // Get all the codeList where codeParams contains UPDATED_CODE_PARAMS
        defaultCodeShouldNotBeFound("codeParams.contains=" + UPDATED_CODE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeParams does not contain DEFAULT_CODE_PARAMS
        defaultCodeShouldNotBeFound("codeParams.doesNotContain=" + DEFAULT_CODE_PARAMS);

        // Get all the codeList where codeParams does not contain UPDATED_CODE_PARAMS
        defaultCodeShouldBeFound("codeParams.doesNotContain=" + UPDATED_CODE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeAttributs equals to DEFAULT_CODE_ATTRIBUTS
        defaultCodeShouldBeFound("codeAttributs.equals=" + DEFAULT_CODE_ATTRIBUTS);

        // Get all the codeList where codeAttributs equals to UPDATED_CODE_ATTRIBUTS
        defaultCodeShouldNotBeFound("codeAttributs.equals=" + UPDATED_CODE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeAttributs in DEFAULT_CODE_ATTRIBUTS or UPDATED_CODE_ATTRIBUTS
        defaultCodeShouldBeFound("codeAttributs.in=" + DEFAULT_CODE_ATTRIBUTS + "," + UPDATED_CODE_ATTRIBUTS);

        // Get all the codeList where codeAttributs equals to UPDATED_CODE_ATTRIBUTS
        defaultCodeShouldNotBeFound("codeAttributs.in=" + UPDATED_CODE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeAttributs is not null
        defaultCodeShouldBeFound("codeAttributs.specified=true");

        // Get all the codeList where codeAttributs is null
        defaultCodeShouldNotBeFound("codeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByCodeAttributsContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeAttributs contains DEFAULT_CODE_ATTRIBUTS
        defaultCodeShouldBeFound("codeAttributs.contains=" + DEFAULT_CODE_ATTRIBUTS);

        // Get all the codeList where codeAttributs contains UPDATED_CODE_ATTRIBUTS
        defaultCodeShouldNotBeFound("codeAttributs.contains=" + UPDATED_CODE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeAttributs does not contain DEFAULT_CODE_ATTRIBUTS
        defaultCodeShouldNotBeFound("codeAttributs.doesNotContain=" + DEFAULT_CODE_ATTRIBUTS);

        // Get all the codeList where codeAttributs does not contain UPDATED_CODE_ATTRIBUTS
        defaultCodeShouldBeFound("codeAttributs.doesNotContain=" + UPDATED_CODE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodesByCodeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeStat equals to DEFAULT_CODE_STAT
        defaultCodeShouldBeFound("codeStat.equals=" + DEFAULT_CODE_STAT);

        // Get all the codeList where codeStat equals to UPDATED_CODE_STAT
        defaultCodeShouldNotBeFound("codeStat.equals=" + UPDATED_CODE_STAT);
    }

    @Test
    @Transactional
    void getAllCodesByCodeStatIsInShouldWork() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeStat in DEFAULT_CODE_STAT or UPDATED_CODE_STAT
        defaultCodeShouldBeFound("codeStat.in=" + DEFAULT_CODE_STAT + "," + UPDATED_CODE_STAT);

        // Get all the codeList where codeStat equals to UPDATED_CODE_STAT
        defaultCodeShouldNotBeFound("codeStat.in=" + UPDATED_CODE_STAT);
    }

    @Test
    @Transactional
    void getAllCodesByCodeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList where codeStat is not null
        defaultCodeShouldBeFound("codeStat.specified=true");

        // Get all the codeList where codeStat is null
        defaultCodeShouldNotBeFound("codeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            codeRepository.saveAndFlush(code);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        code.addAccreditation(accreditation);
        codeRepository.saveAndFlush(code);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the codeList where accreditation equals to accreditationId
        defaultCodeShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the codeList where accreditation equals to (accreditationId + 1)
        defaultCodeShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllCodesByCodeTypeIsEqualToSomething() throws Exception {
        CodeType codeType;
        if (TestUtil.findAll(em, CodeType.class).isEmpty()) {
            codeRepository.saveAndFlush(code);
            codeType = CodeTypeResourceIT.createEntity(em);
        } else {
            codeType = TestUtil.findAll(em, CodeType.class).get(0);
        }
        em.persist(codeType);
        em.flush();
        code.setCodeType(codeType);
        codeRepository.saveAndFlush(code);
        Long codeTypeId = codeType.getCodeTypeId();

        // Get all the codeList where codeType equals to codeTypeId
        defaultCodeShouldBeFound("codeTypeId.equals=" + codeTypeId);

        // Get all the codeList where codeType equals to (codeTypeId + 1)
        defaultCodeShouldNotBeFound("codeTypeId.equals=" + (codeTypeId + 1));
    }

    @Test
    @Transactional
    void getAllCodesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            codeRepository.saveAndFlush(code);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        code.setEvent(event);
        codeRepository.saveAndFlush(code);
        Long eventId = event.getEventId();

        // Get all the codeList where event equals to eventId
        defaultCodeShouldBeFound("eventId.equals=" + eventId);

        // Get all the codeList where event equals to (eventId + 1)
        defaultCodeShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCodeShouldBeFound(String filter) throws Exception {
        restCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].codeId").value(hasItem(code.getCodeId().intValue())))
            .andExpect(jsonPath("$.[*].codeForEntity").value(hasItem(DEFAULT_CODE_FOR_ENTITY)))
            .andExpect(jsonPath("$.[*].codeEntityValue").value(hasItem(DEFAULT_CODE_ENTITY_VALUE)))
            .andExpect(jsonPath("$.[*].codeValue").value(hasItem(DEFAULT_CODE_VALUE)))
            .andExpect(jsonPath("$.[*].codeUsed").value(hasItem(DEFAULT_CODE_USED.booleanValue())))
            .andExpect(jsonPath("$.[*].codeParams").value(hasItem(DEFAULT_CODE_PARAMS)))
            .andExpect(jsonPath("$.[*].codeAttributs").value(hasItem(DEFAULT_CODE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].codeStat").value(hasItem(DEFAULT_CODE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restCodeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=codeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCodeShouldNotBeFound(String filter) throws Exception {
        restCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCodeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=codeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCode() throws Exception {
        // Get the code
        restCodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCode() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        int databaseSizeBeforeUpdate = codeRepository.findAll().size();

        // Update the code
        Code updatedCode = codeRepository.findById(code.getCodeId()).get();
        // Disconnect from session so that the updates on updatedCode are not directly saved in db
        em.detach(updatedCode);
        updatedCode
            .codeForEntity(UPDATED_CODE_FOR_ENTITY)
            .codeEntityValue(UPDATED_CODE_ENTITY_VALUE)
            .codeValue(UPDATED_CODE_VALUE)
            .codeUsed(UPDATED_CODE_USED)
            .codeParams(UPDATED_CODE_PARAMS)
            .codeAttributs(UPDATED_CODE_ATTRIBUTS)
            .codeStat(UPDATED_CODE_STAT);
        CodeDTO codeDTO = codeMapper.toDto(updatedCode);

        restCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeDTO.getCodeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
        Code testCode = codeList.get(codeList.size() - 1);
        assertThat(testCode.getCodeForEntity()).isEqualTo(UPDATED_CODE_FOR_ENTITY);
        assertThat(testCode.getCodeEntityValue()).isEqualTo(UPDATED_CODE_ENTITY_VALUE);
        assertThat(testCode.getCodeValue()).isEqualTo(UPDATED_CODE_VALUE);
        assertThat(testCode.getCodeUsed()).isEqualTo(UPDATED_CODE_USED);
        assertThat(testCode.getCodeParams()).isEqualTo(UPDATED_CODE_PARAMS);
        assertThat(testCode.getCodeAttributs()).isEqualTo(UPDATED_CODE_ATTRIBUTS);
        assertThat(testCode.getCodeStat()).isEqualTo(UPDATED_CODE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCode() throws Exception {
        int databaseSizeBeforeUpdate = codeRepository.findAll().size();
        code.setCodeId(count.incrementAndGet());

        // Create the Code
        CodeDTO codeDTO = codeMapper.toDto(code);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeDTO.getCodeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCode() throws Exception {
        int databaseSizeBeforeUpdate = codeRepository.findAll().size();
        code.setCodeId(count.incrementAndGet());

        // Create the Code
        CodeDTO codeDTO = codeMapper.toDto(code);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCode() throws Exception {
        int databaseSizeBeforeUpdate = codeRepository.findAll().size();
        code.setCodeId(count.incrementAndGet());

        // Create the Code
        CodeDTO codeDTO = codeMapper.toDto(code);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodeWithPatch() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        int databaseSizeBeforeUpdate = codeRepository.findAll().size();

        // Update the code using partial update
        Code partialUpdatedCode = new Code();
        partialUpdatedCode.setCodeId(code.getCodeId());

        partialUpdatedCode
            .codeForEntity(UPDATED_CODE_FOR_ENTITY)
            .codeEntityValue(UPDATED_CODE_ENTITY_VALUE)
            .codeUsed(UPDATED_CODE_USED)
            .codeParams(UPDATED_CODE_PARAMS)
            .codeStat(UPDATED_CODE_STAT);

        restCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCode.getCodeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCode))
            )
            .andExpect(status().isOk());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
        Code testCode = codeList.get(codeList.size() - 1);
        assertThat(testCode.getCodeForEntity()).isEqualTo(UPDATED_CODE_FOR_ENTITY);
        assertThat(testCode.getCodeEntityValue()).isEqualTo(UPDATED_CODE_ENTITY_VALUE);
        assertThat(testCode.getCodeValue()).isEqualTo(DEFAULT_CODE_VALUE);
        assertThat(testCode.getCodeUsed()).isEqualTo(UPDATED_CODE_USED);
        assertThat(testCode.getCodeParams()).isEqualTo(UPDATED_CODE_PARAMS);
        assertThat(testCode.getCodeAttributs()).isEqualTo(DEFAULT_CODE_ATTRIBUTS);
        assertThat(testCode.getCodeStat()).isEqualTo(UPDATED_CODE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCodeWithPatch() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        int databaseSizeBeforeUpdate = codeRepository.findAll().size();

        // Update the code using partial update
        Code partialUpdatedCode = new Code();
        partialUpdatedCode.setCodeId(code.getCodeId());

        partialUpdatedCode
            .codeForEntity(UPDATED_CODE_FOR_ENTITY)
            .codeEntityValue(UPDATED_CODE_ENTITY_VALUE)
            .codeValue(UPDATED_CODE_VALUE)
            .codeUsed(UPDATED_CODE_USED)
            .codeParams(UPDATED_CODE_PARAMS)
            .codeAttributs(UPDATED_CODE_ATTRIBUTS)
            .codeStat(UPDATED_CODE_STAT);

        restCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCode.getCodeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCode))
            )
            .andExpect(status().isOk());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
        Code testCode = codeList.get(codeList.size() - 1);
        assertThat(testCode.getCodeForEntity()).isEqualTo(UPDATED_CODE_FOR_ENTITY);
        assertThat(testCode.getCodeEntityValue()).isEqualTo(UPDATED_CODE_ENTITY_VALUE);
        assertThat(testCode.getCodeValue()).isEqualTo(UPDATED_CODE_VALUE);
        assertThat(testCode.getCodeUsed()).isEqualTo(UPDATED_CODE_USED);
        assertThat(testCode.getCodeParams()).isEqualTo(UPDATED_CODE_PARAMS);
        assertThat(testCode.getCodeAttributs()).isEqualTo(UPDATED_CODE_ATTRIBUTS);
        assertThat(testCode.getCodeStat()).isEqualTo(UPDATED_CODE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCode() throws Exception {
        int databaseSizeBeforeUpdate = codeRepository.findAll().size();
        code.setCodeId(count.incrementAndGet());

        // Create the Code
        CodeDTO codeDTO = codeMapper.toDto(code);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codeDTO.getCodeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCode() throws Exception {
        int databaseSizeBeforeUpdate = codeRepository.findAll().size();
        code.setCodeId(count.incrementAndGet());

        // Create the Code
        CodeDTO codeDTO = codeMapper.toDto(code);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCode() throws Exception {
        int databaseSizeBeforeUpdate = codeRepository.findAll().size();
        code.setCodeId(count.incrementAndGet());

        // Create the Code
        CodeDTO codeDTO = codeMapper.toDto(code);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(codeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCode() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        int databaseSizeBeforeDelete = codeRepository.findAll().size();

        // Delete the code
        restCodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, code.getCodeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
