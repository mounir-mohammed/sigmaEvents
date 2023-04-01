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
import ma.sig.events.domain.Code;
import ma.sig.events.domain.CodeType;
import ma.sig.events.repository.CodeTypeRepository;
import ma.sig.events.service.criteria.CodeTypeCriteria;
import ma.sig.events.service.dto.CodeTypeDTO;
import ma.sig.events.service.mapper.CodeTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CodeTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodeTypeResourceIT {

    private static final String DEFAULT_CODE_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CODE_TYPE_STAT = false;
    private static final Boolean UPDATED_CODE_TYPE_STAT = true;

    private static final String ENTITY_API_URL = "/api/code-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{codeTypeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CodeTypeRepository codeTypeRepository;

    @Autowired
    private CodeTypeMapper codeTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodeTypeMockMvc;

    private CodeType codeType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeType createEntity(EntityManager em) {
        CodeType codeType = new CodeType()
            .codeTypeValue(DEFAULT_CODE_TYPE_VALUE)
            .codeTypeDescription(DEFAULT_CODE_TYPE_DESCRIPTION)
            .codeTypeParams(DEFAULT_CODE_TYPE_PARAMS)
            .codeTypeAttributs(DEFAULT_CODE_TYPE_ATTRIBUTS)
            .codeTypeStat(DEFAULT_CODE_TYPE_STAT);
        return codeType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeType createUpdatedEntity(EntityManager em) {
        CodeType codeType = new CodeType()
            .codeTypeValue(UPDATED_CODE_TYPE_VALUE)
            .codeTypeDescription(UPDATED_CODE_TYPE_DESCRIPTION)
            .codeTypeParams(UPDATED_CODE_TYPE_PARAMS)
            .codeTypeAttributs(UPDATED_CODE_TYPE_ATTRIBUTS)
            .codeTypeStat(UPDATED_CODE_TYPE_STAT);
        return codeType;
    }

    @BeforeEach
    public void initTest() {
        codeType = createEntity(em);
    }

    @Test
    @Transactional
    void createCodeType() throws Exception {
        int databaseSizeBeforeCreate = codeTypeRepository.findAll().size();
        // Create the CodeType
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);
        restCodeTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CodeType testCodeType = codeTypeList.get(codeTypeList.size() - 1);
        assertThat(testCodeType.getCodeTypeValue()).isEqualTo(DEFAULT_CODE_TYPE_VALUE);
        assertThat(testCodeType.getCodeTypeDescription()).isEqualTo(DEFAULT_CODE_TYPE_DESCRIPTION);
        assertThat(testCodeType.getCodeTypeParams()).isEqualTo(DEFAULT_CODE_TYPE_PARAMS);
        assertThat(testCodeType.getCodeTypeAttributs()).isEqualTo(DEFAULT_CODE_TYPE_ATTRIBUTS);
        assertThat(testCodeType.getCodeTypeStat()).isEqualTo(DEFAULT_CODE_TYPE_STAT);
    }

    @Test
    @Transactional
    void createCodeTypeWithExistingId() throws Exception {
        // Create the CodeType with an existing ID
        codeType.setCodeTypeId(1L);
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        int databaseSizeBeforeCreate = codeTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeTypeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeTypeRepository.findAll().size();
        // set the field null
        codeType.setCodeTypeValue(null);

        // Create the CodeType, which fails.
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        restCodeTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCodeTypes() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList
        restCodeTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codeTypeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].codeTypeId").value(hasItem(codeType.getCodeTypeId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeValue").value(hasItem(DEFAULT_CODE_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].codeTypeDescription").value(hasItem(DEFAULT_CODE_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].codeTypeParams").value(hasItem(DEFAULT_CODE_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].codeTypeAttributs").value(hasItem(DEFAULT_CODE_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].codeTypeStat").value(hasItem(DEFAULT_CODE_TYPE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getCodeType() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get the codeType
        restCodeTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, codeType.getCodeTypeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.codeTypeId").value(codeType.getCodeTypeId().intValue()))
            .andExpect(jsonPath("$.codeTypeValue").value(DEFAULT_CODE_TYPE_VALUE))
            .andExpect(jsonPath("$.codeTypeDescription").value(DEFAULT_CODE_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.codeTypeParams").value(DEFAULT_CODE_TYPE_PARAMS))
            .andExpect(jsonPath("$.codeTypeAttributs").value(DEFAULT_CODE_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.codeTypeStat").value(DEFAULT_CODE_TYPE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCodeTypesByIdFiltering() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        Long id = codeType.getCodeTypeId();

        defaultCodeTypeShouldBeFound("codeTypeId.equals=" + id);
        defaultCodeTypeShouldNotBeFound("codeTypeId.notEquals=" + id);

        defaultCodeTypeShouldBeFound("codeTypeId.greaterThanOrEqual=" + id);
        defaultCodeTypeShouldNotBeFound("codeTypeId.greaterThan=" + id);

        defaultCodeTypeShouldBeFound("codeTypeId.lessThanOrEqual=" + id);
        defaultCodeTypeShouldNotBeFound("codeTypeId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeValue equals to DEFAULT_CODE_TYPE_VALUE
        defaultCodeTypeShouldBeFound("codeTypeValue.equals=" + DEFAULT_CODE_TYPE_VALUE);

        // Get all the codeTypeList where codeTypeValue equals to UPDATED_CODE_TYPE_VALUE
        defaultCodeTypeShouldNotBeFound("codeTypeValue.equals=" + UPDATED_CODE_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeValueIsInShouldWork() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeValue in DEFAULT_CODE_TYPE_VALUE or UPDATED_CODE_TYPE_VALUE
        defaultCodeTypeShouldBeFound("codeTypeValue.in=" + DEFAULT_CODE_TYPE_VALUE + "," + UPDATED_CODE_TYPE_VALUE);

        // Get all the codeTypeList where codeTypeValue equals to UPDATED_CODE_TYPE_VALUE
        defaultCodeTypeShouldNotBeFound("codeTypeValue.in=" + UPDATED_CODE_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeValue is not null
        defaultCodeTypeShouldBeFound("codeTypeValue.specified=true");

        // Get all the codeTypeList where codeTypeValue is null
        defaultCodeTypeShouldNotBeFound("codeTypeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeValueContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeValue contains DEFAULT_CODE_TYPE_VALUE
        defaultCodeTypeShouldBeFound("codeTypeValue.contains=" + DEFAULT_CODE_TYPE_VALUE);

        // Get all the codeTypeList where codeTypeValue contains UPDATED_CODE_TYPE_VALUE
        defaultCodeTypeShouldNotBeFound("codeTypeValue.contains=" + UPDATED_CODE_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeValueNotContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeValue does not contain DEFAULT_CODE_TYPE_VALUE
        defaultCodeTypeShouldNotBeFound("codeTypeValue.doesNotContain=" + DEFAULT_CODE_TYPE_VALUE);

        // Get all the codeTypeList where codeTypeValue does not contain UPDATED_CODE_TYPE_VALUE
        defaultCodeTypeShouldBeFound("codeTypeValue.doesNotContain=" + UPDATED_CODE_TYPE_VALUE);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeDescription equals to DEFAULT_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldBeFound("codeTypeDescription.equals=" + DEFAULT_CODE_TYPE_DESCRIPTION);

        // Get all the codeTypeList where codeTypeDescription equals to UPDATED_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldNotBeFound("codeTypeDescription.equals=" + UPDATED_CODE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeDescription in DEFAULT_CODE_TYPE_DESCRIPTION or UPDATED_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldBeFound("codeTypeDescription.in=" + DEFAULT_CODE_TYPE_DESCRIPTION + "," + UPDATED_CODE_TYPE_DESCRIPTION);

        // Get all the codeTypeList where codeTypeDescription equals to UPDATED_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldNotBeFound("codeTypeDescription.in=" + UPDATED_CODE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeDescription is not null
        defaultCodeTypeShouldBeFound("codeTypeDescription.specified=true");

        // Get all the codeTypeList where codeTypeDescription is null
        defaultCodeTypeShouldNotBeFound("codeTypeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeDescription contains DEFAULT_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldBeFound("codeTypeDescription.contains=" + DEFAULT_CODE_TYPE_DESCRIPTION);

        // Get all the codeTypeList where codeTypeDescription contains UPDATED_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldNotBeFound("codeTypeDescription.contains=" + UPDATED_CODE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeDescription does not contain DEFAULT_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldNotBeFound("codeTypeDescription.doesNotContain=" + DEFAULT_CODE_TYPE_DESCRIPTION);

        // Get all the codeTypeList where codeTypeDescription does not contain UPDATED_CODE_TYPE_DESCRIPTION
        defaultCodeTypeShouldBeFound("codeTypeDescription.doesNotContain=" + UPDATED_CODE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeParams equals to DEFAULT_CODE_TYPE_PARAMS
        defaultCodeTypeShouldBeFound("codeTypeParams.equals=" + DEFAULT_CODE_TYPE_PARAMS);

        // Get all the codeTypeList where codeTypeParams equals to UPDATED_CODE_TYPE_PARAMS
        defaultCodeTypeShouldNotBeFound("codeTypeParams.equals=" + UPDATED_CODE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeParams in DEFAULT_CODE_TYPE_PARAMS or UPDATED_CODE_TYPE_PARAMS
        defaultCodeTypeShouldBeFound("codeTypeParams.in=" + DEFAULT_CODE_TYPE_PARAMS + "," + UPDATED_CODE_TYPE_PARAMS);

        // Get all the codeTypeList where codeTypeParams equals to UPDATED_CODE_TYPE_PARAMS
        defaultCodeTypeShouldNotBeFound("codeTypeParams.in=" + UPDATED_CODE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeParams is not null
        defaultCodeTypeShouldBeFound("codeTypeParams.specified=true");

        // Get all the codeTypeList where codeTypeParams is null
        defaultCodeTypeShouldNotBeFound("codeTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeParams contains DEFAULT_CODE_TYPE_PARAMS
        defaultCodeTypeShouldBeFound("codeTypeParams.contains=" + DEFAULT_CODE_TYPE_PARAMS);

        // Get all the codeTypeList where codeTypeParams contains UPDATED_CODE_TYPE_PARAMS
        defaultCodeTypeShouldNotBeFound("codeTypeParams.contains=" + UPDATED_CODE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeParams does not contain DEFAULT_CODE_TYPE_PARAMS
        defaultCodeTypeShouldNotBeFound("codeTypeParams.doesNotContain=" + DEFAULT_CODE_TYPE_PARAMS);

        // Get all the codeTypeList where codeTypeParams does not contain UPDATED_CODE_TYPE_PARAMS
        defaultCodeTypeShouldBeFound("codeTypeParams.doesNotContain=" + UPDATED_CODE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeAttributs equals to DEFAULT_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldBeFound("codeTypeAttributs.equals=" + DEFAULT_CODE_TYPE_ATTRIBUTS);

        // Get all the codeTypeList where codeTypeAttributs equals to UPDATED_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldNotBeFound("codeTypeAttributs.equals=" + UPDATED_CODE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeAttributs in DEFAULT_CODE_TYPE_ATTRIBUTS or UPDATED_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldBeFound("codeTypeAttributs.in=" + DEFAULT_CODE_TYPE_ATTRIBUTS + "," + UPDATED_CODE_TYPE_ATTRIBUTS);

        // Get all the codeTypeList where codeTypeAttributs equals to UPDATED_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldNotBeFound("codeTypeAttributs.in=" + UPDATED_CODE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeAttributs is not null
        defaultCodeTypeShouldBeFound("codeTypeAttributs.specified=true");

        // Get all the codeTypeList where codeTypeAttributs is null
        defaultCodeTypeShouldNotBeFound("codeTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeAttributs contains DEFAULT_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldBeFound("codeTypeAttributs.contains=" + DEFAULT_CODE_TYPE_ATTRIBUTS);

        // Get all the codeTypeList where codeTypeAttributs contains UPDATED_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldNotBeFound("codeTypeAttributs.contains=" + UPDATED_CODE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeAttributs does not contain DEFAULT_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldNotBeFound("codeTypeAttributs.doesNotContain=" + DEFAULT_CODE_TYPE_ATTRIBUTS);

        // Get all the codeTypeList where codeTypeAttributs does not contain UPDATED_CODE_TYPE_ATTRIBUTS
        defaultCodeTypeShouldBeFound("codeTypeAttributs.doesNotContain=" + UPDATED_CODE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeStatIsEqualToSomething() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeStat equals to DEFAULT_CODE_TYPE_STAT
        defaultCodeTypeShouldBeFound("codeTypeStat.equals=" + DEFAULT_CODE_TYPE_STAT);

        // Get all the codeTypeList where codeTypeStat equals to UPDATED_CODE_TYPE_STAT
        defaultCodeTypeShouldNotBeFound("codeTypeStat.equals=" + UPDATED_CODE_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeStatIsInShouldWork() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeStat in DEFAULT_CODE_TYPE_STAT or UPDATED_CODE_TYPE_STAT
        defaultCodeTypeShouldBeFound("codeTypeStat.in=" + DEFAULT_CODE_TYPE_STAT + "," + UPDATED_CODE_TYPE_STAT);

        // Get all the codeTypeList where codeTypeStat equals to UPDATED_CODE_TYPE_STAT
        defaultCodeTypeShouldNotBeFound("codeTypeStat.in=" + UPDATED_CODE_TYPE_STAT);
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeTypeStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        // Get all the codeTypeList where codeTypeStat is not null
        defaultCodeTypeShouldBeFound("codeTypeStat.specified=true");

        // Get all the codeTypeList where codeTypeStat is null
        defaultCodeTypeShouldNotBeFound("codeTypeStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCodeTypesByCodeIsEqualToSomething() throws Exception {
        Code code;
        if (TestUtil.findAll(em, Code.class).isEmpty()) {
            codeTypeRepository.saveAndFlush(codeType);
            code = CodeResourceIT.createEntity(em);
        } else {
            code = TestUtil.findAll(em, Code.class).get(0);
        }
        em.persist(code);
        em.flush();
        codeType.addCode(code);
        codeTypeRepository.saveAndFlush(codeType);
        Long codeId = code.getCodeId();

        // Get all the codeTypeList where code equals to codeId
        defaultCodeTypeShouldBeFound("codeId.equals=" + codeId);

        // Get all the codeTypeList where code equals to (codeId + 1)
        defaultCodeTypeShouldNotBeFound("codeId.equals=" + (codeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCodeTypeShouldBeFound(String filter) throws Exception {
        restCodeTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codeTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].codeTypeId").value(hasItem(codeType.getCodeTypeId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeValue").value(hasItem(DEFAULT_CODE_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].codeTypeDescription").value(hasItem(DEFAULT_CODE_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].codeTypeParams").value(hasItem(DEFAULT_CODE_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].codeTypeAttributs").value(hasItem(DEFAULT_CODE_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].codeTypeStat").value(hasItem(DEFAULT_CODE_TYPE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restCodeTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=codeTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCodeTypeShouldNotBeFound(String filter) throws Exception {
        restCodeTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codeTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCodeTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=codeTypeId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCodeType() throws Exception {
        // Get the codeType
        restCodeTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCodeType() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();

        // Update the codeType
        CodeType updatedCodeType = codeTypeRepository.findById(codeType.getCodeTypeId()).get();
        // Disconnect from session so that the updates on updatedCodeType are not directly saved in db
        em.detach(updatedCodeType);
        updatedCodeType
            .codeTypeValue(UPDATED_CODE_TYPE_VALUE)
            .codeTypeDescription(UPDATED_CODE_TYPE_DESCRIPTION)
            .codeTypeParams(UPDATED_CODE_TYPE_PARAMS)
            .codeTypeAttributs(UPDATED_CODE_TYPE_ATTRIBUTS)
            .codeTypeStat(UPDATED_CODE_TYPE_STAT);
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(updatedCodeType);

        restCodeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeTypeDTO.getCodeTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
        CodeType testCodeType = codeTypeList.get(codeTypeList.size() - 1);
        assertThat(testCodeType.getCodeTypeValue()).isEqualTo(UPDATED_CODE_TYPE_VALUE);
        assertThat(testCodeType.getCodeTypeDescription()).isEqualTo(UPDATED_CODE_TYPE_DESCRIPTION);
        assertThat(testCodeType.getCodeTypeParams()).isEqualTo(UPDATED_CODE_TYPE_PARAMS);
        assertThat(testCodeType.getCodeTypeAttributs()).isEqualTo(UPDATED_CODE_TYPE_ATTRIBUTS);
        assertThat(testCodeType.getCodeTypeStat()).isEqualTo(UPDATED_CODE_TYPE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCodeType() throws Exception {
        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();
        codeType.setCodeTypeId(count.incrementAndGet());

        // Create the CodeType
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeTypeDTO.getCodeTypeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCodeType() throws Exception {
        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();
        codeType.setCodeTypeId(count.incrementAndGet());

        // Create the CodeType
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCodeType() throws Exception {
        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();
        codeType.setCodeTypeId(count.incrementAndGet());

        // Create the CodeType
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodeTypeWithPatch() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();

        // Update the codeType using partial update
        CodeType partialUpdatedCodeType = new CodeType();
        partialUpdatedCodeType.setCodeTypeId(codeType.getCodeTypeId());

        partialUpdatedCodeType.codeTypeValue(UPDATED_CODE_TYPE_VALUE).codeTypeDescription(UPDATED_CODE_TYPE_DESCRIPTION);

        restCodeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeType.getCodeTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodeType))
            )
            .andExpect(status().isOk());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
        CodeType testCodeType = codeTypeList.get(codeTypeList.size() - 1);
        assertThat(testCodeType.getCodeTypeValue()).isEqualTo(UPDATED_CODE_TYPE_VALUE);
        assertThat(testCodeType.getCodeTypeDescription()).isEqualTo(UPDATED_CODE_TYPE_DESCRIPTION);
        assertThat(testCodeType.getCodeTypeParams()).isEqualTo(DEFAULT_CODE_TYPE_PARAMS);
        assertThat(testCodeType.getCodeTypeAttributs()).isEqualTo(DEFAULT_CODE_TYPE_ATTRIBUTS);
        assertThat(testCodeType.getCodeTypeStat()).isEqualTo(DEFAULT_CODE_TYPE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCodeTypeWithPatch() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();

        // Update the codeType using partial update
        CodeType partialUpdatedCodeType = new CodeType();
        partialUpdatedCodeType.setCodeTypeId(codeType.getCodeTypeId());

        partialUpdatedCodeType
            .codeTypeValue(UPDATED_CODE_TYPE_VALUE)
            .codeTypeDescription(UPDATED_CODE_TYPE_DESCRIPTION)
            .codeTypeParams(UPDATED_CODE_TYPE_PARAMS)
            .codeTypeAttributs(UPDATED_CODE_TYPE_ATTRIBUTS)
            .codeTypeStat(UPDATED_CODE_TYPE_STAT);

        restCodeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeType.getCodeTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodeType))
            )
            .andExpect(status().isOk());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
        CodeType testCodeType = codeTypeList.get(codeTypeList.size() - 1);
        assertThat(testCodeType.getCodeTypeValue()).isEqualTo(UPDATED_CODE_TYPE_VALUE);
        assertThat(testCodeType.getCodeTypeDescription()).isEqualTo(UPDATED_CODE_TYPE_DESCRIPTION);
        assertThat(testCodeType.getCodeTypeParams()).isEqualTo(UPDATED_CODE_TYPE_PARAMS);
        assertThat(testCodeType.getCodeTypeAttributs()).isEqualTo(UPDATED_CODE_TYPE_ATTRIBUTS);
        assertThat(testCodeType.getCodeTypeStat()).isEqualTo(UPDATED_CODE_TYPE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCodeType() throws Exception {
        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();
        codeType.setCodeTypeId(count.incrementAndGet());

        // Create the CodeType
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codeTypeDTO.getCodeTypeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCodeType() throws Exception {
        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();
        codeType.setCodeTypeId(count.incrementAndGet());

        // Create the CodeType
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCodeType() throws Exception {
        int databaseSizeBeforeUpdate = codeTypeRepository.findAll().size();
        codeType.setCodeTypeId(count.incrementAndGet());

        // Create the CodeType
        CodeTypeDTO codeTypeDTO = codeTypeMapper.toDto(codeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(codeTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeType in the database
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCodeType() throws Exception {
        // Initialize the database
        codeTypeRepository.saveAndFlush(codeType);

        int databaseSizeBeforeDelete = codeTypeRepository.findAll().size();

        // Delete the codeType
        restCodeTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, codeType.getCodeTypeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodeType> codeTypeList = codeTypeRepository.findAll();
        assertThat(codeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
