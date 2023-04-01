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
import ma.sig.events.domain.Category;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Fonction;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.repository.CategoryRepository;
import ma.sig.events.service.criteria.CategoryCriteria;
import ma.sig.events.service.dto.CategoryDTO;
import ma.sig.events.service.mapper.CategoryMapper;
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
 * Integration tests for the {@link CategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoryResourceIT {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CATEGORY_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CATEGORY_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CATEGORY_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CATEGORY_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CATEGORY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CATEGORY_STAT = false;
    private static final Boolean UPDATED_CATEGORY_STAT = true;

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{categoryId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryMockMvc;

    private Category category;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .categoryName(DEFAULT_CATEGORY_NAME)
            .categoryAbreviation(DEFAULT_CATEGORY_ABREVIATION)
            .categoryColor(DEFAULT_CATEGORY_COLOR)
            .categoryDescription(DEFAULT_CATEGORY_DESCRIPTION)
            .categoryLogo(DEFAULT_CATEGORY_LOGO)
            .categoryLogoContentType(DEFAULT_CATEGORY_LOGO_CONTENT_TYPE)
            .categoryParams(DEFAULT_CATEGORY_PARAMS)
            .categoryAttributs(DEFAULT_CATEGORY_ATTRIBUTS)
            .categoryStat(DEFAULT_CATEGORY_STAT);
        return category;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createUpdatedEntity(EntityManager em) {
        Category category = new Category()
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryAbreviation(UPDATED_CATEGORY_ABREVIATION)
            .categoryColor(UPDATED_CATEGORY_COLOR)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .categoryLogo(UPDATED_CATEGORY_LOGO)
            .categoryLogoContentType(UPDATED_CATEGORY_LOGO_CONTENT_TYPE)
            .categoryParams(UPDATED_CATEGORY_PARAMS)
            .categoryAttributs(UPDATED_CATEGORY_ATTRIBUTS)
            .categoryStat(UPDATED_CATEGORY_STAT);
        return category;
    }

    @BeforeEach
    public void initTest() {
        category = createEntity(em);
    }

    @Test
    @Transactional
    void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testCategory.getCategoryAbreviation()).isEqualTo(DEFAULT_CATEGORY_ABREVIATION);
        assertThat(testCategory.getCategoryColor()).isEqualTo(DEFAULT_CATEGORY_COLOR);
        assertThat(testCategory.getCategoryDescription()).isEqualTo(DEFAULT_CATEGORY_DESCRIPTION);
        assertThat(testCategory.getCategoryLogo()).isEqualTo(DEFAULT_CATEGORY_LOGO);
        assertThat(testCategory.getCategoryLogoContentType()).isEqualTo(DEFAULT_CATEGORY_LOGO_CONTENT_TYPE);
        assertThat(testCategory.getCategoryParams()).isEqualTo(DEFAULT_CATEGORY_PARAMS);
        assertThat(testCategory.getCategoryAttributs()).isEqualTo(DEFAULT_CATEGORY_ATTRIBUTS);
        assertThat(testCategory.getCategoryStat()).isEqualTo(DEFAULT_CATEGORY_STAT);
    }

    @Test
    @Transactional
    void createCategoryWithExistingId() throws Exception {
        // Create the Category with an existing ID
        category.setCategoryId(1L);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setCategoryName(null);

        // Create the Category, which fails.
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=categoryId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(category.getCategoryId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].categoryAbreviation").value(hasItem(DEFAULT_CATEGORY_ABREVIATION)))
            .andExpect(jsonPath("$.[*].categoryColor").value(hasItem(DEFAULT_CATEGORY_COLOR)))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].categoryLogoContentType").value(hasItem(DEFAULT_CATEGORY_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].categoryLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CATEGORY_LOGO))))
            .andExpect(jsonPath("$.[*].categoryParams").value(hasItem(DEFAULT_CATEGORY_PARAMS)))
            .andExpect(jsonPath("$.[*].categoryAttributs").value(hasItem(DEFAULT_CATEGORY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].categoryStat").value(hasItem(DEFAULT_CATEGORY_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, category.getCategoryId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.categoryId").value(category.getCategoryId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.categoryAbreviation").value(DEFAULT_CATEGORY_ABREVIATION))
            .andExpect(jsonPath("$.categoryColor").value(DEFAULT_CATEGORY_COLOR))
            .andExpect(jsonPath("$.categoryDescription").value(DEFAULT_CATEGORY_DESCRIPTION))
            .andExpect(jsonPath("$.categoryLogoContentType").value(DEFAULT_CATEGORY_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.categoryLogo").value(Base64Utils.encodeToString(DEFAULT_CATEGORY_LOGO)))
            .andExpect(jsonPath("$.categoryParams").value(DEFAULT_CATEGORY_PARAMS))
            .andExpect(jsonPath("$.categoryAttributs").value(DEFAULT_CATEGORY_ATTRIBUTS))
            .andExpect(jsonPath("$.categoryStat").value(DEFAULT_CATEGORY_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        Long id = category.getCategoryId();

        defaultCategoryShouldBeFound("categoryId.equals=" + id);
        defaultCategoryShouldNotBeFound("categoryId.notEquals=" + id);

        defaultCategoryShouldBeFound("categoryId.greaterThanOrEqual=" + id);
        defaultCategoryShouldNotBeFound("categoryId.greaterThan=" + id);

        defaultCategoryShouldBeFound("categoryId.lessThanOrEqual=" + id);
        defaultCategoryShouldNotBeFound("categoryId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryName equals to DEFAULT_CATEGORY_NAME
        defaultCategoryShouldBeFound("categoryName.equals=" + DEFAULT_CATEGORY_NAME);

        // Get all the categoryList where categoryName equals to UPDATED_CATEGORY_NAME
        defaultCategoryShouldNotBeFound("categoryName.equals=" + UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryName in DEFAULT_CATEGORY_NAME or UPDATED_CATEGORY_NAME
        defaultCategoryShouldBeFound("categoryName.in=" + DEFAULT_CATEGORY_NAME + "," + UPDATED_CATEGORY_NAME);

        // Get all the categoryList where categoryName equals to UPDATED_CATEGORY_NAME
        defaultCategoryShouldNotBeFound("categoryName.in=" + UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryName is not null
        defaultCategoryShouldBeFound("categoryName.specified=true");

        // Get all the categoryList where categoryName is null
        defaultCategoryShouldNotBeFound("categoryName.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryNameContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryName contains DEFAULT_CATEGORY_NAME
        defaultCategoryShouldBeFound("categoryName.contains=" + DEFAULT_CATEGORY_NAME);

        // Get all the categoryList where categoryName contains UPDATED_CATEGORY_NAME
        defaultCategoryShouldNotBeFound("categoryName.contains=" + UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryNameNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryName does not contain DEFAULT_CATEGORY_NAME
        defaultCategoryShouldNotBeFound("categoryName.doesNotContain=" + DEFAULT_CATEGORY_NAME);

        // Get all the categoryList where categoryName does not contain UPDATED_CATEGORY_NAME
        defaultCategoryShouldBeFound("categoryName.doesNotContain=" + UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAbreviation equals to DEFAULT_CATEGORY_ABREVIATION
        defaultCategoryShouldBeFound("categoryAbreviation.equals=" + DEFAULT_CATEGORY_ABREVIATION);

        // Get all the categoryList where categoryAbreviation equals to UPDATED_CATEGORY_ABREVIATION
        defaultCategoryShouldNotBeFound("categoryAbreviation.equals=" + UPDATED_CATEGORY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAbreviation in DEFAULT_CATEGORY_ABREVIATION or UPDATED_CATEGORY_ABREVIATION
        defaultCategoryShouldBeFound("categoryAbreviation.in=" + DEFAULT_CATEGORY_ABREVIATION + "," + UPDATED_CATEGORY_ABREVIATION);

        // Get all the categoryList where categoryAbreviation equals to UPDATED_CATEGORY_ABREVIATION
        defaultCategoryShouldNotBeFound("categoryAbreviation.in=" + UPDATED_CATEGORY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAbreviation is not null
        defaultCategoryShouldBeFound("categoryAbreviation.specified=true");

        // Get all the categoryList where categoryAbreviation is null
        defaultCategoryShouldNotBeFound("categoryAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAbreviationContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAbreviation contains DEFAULT_CATEGORY_ABREVIATION
        defaultCategoryShouldBeFound("categoryAbreviation.contains=" + DEFAULT_CATEGORY_ABREVIATION);

        // Get all the categoryList where categoryAbreviation contains UPDATED_CATEGORY_ABREVIATION
        defaultCategoryShouldNotBeFound("categoryAbreviation.contains=" + UPDATED_CATEGORY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAbreviation does not contain DEFAULT_CATEGORY_ABREVIATION
        defaultCategoryShouldNotBeFound("categoryAbreviation.doesNotContain=" + DEFAULT_CATEGORY_ABREVIATION);

        // Get all the categoryList where categoryAbreviation does not contain UPDATED_CATEGORY_ABREVIATION
        defaultCategoryShouldBeFound("categoryAbreviation.doesNotContain=" + UPDATED_CATEGORY_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryColorIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryColor equals to DEFAULT_CATEGORY_COLOR
        defaultCategoryShouldBeFound("categoryColor.equals=" + DEFAULT_CATEGORY_COLOR);

        // Get all the categoryList where categoryColor equals to UPDATED_CATEGORY_COLOR
        defaultCategoryShouldNotBeFound("categoryColor.equals=" + UPDATED_CATEGORY_COLOR);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryColorIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryColor in DEFAULT_CATEGORY_COLOR or UPDATED_CATEGORY_COLOR
        defaultCategoryShouldBeFound("categoryColor.in=" + DEFAULT_CATEGORY_COLOR + "," + UPDATED_CATEGORY_COLOR);

        // Get all the categoryList where categoryColor equals to UPDATED_CATEGORY_COLOR
        defaultCategoryShouldNotBeFound("categoryColor.in=" + UPDATED_CATEGORY_COLOR);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryColor is not null
        defaultCategoryShouldBeFound("categoryColor.specified=true");

        // Get all the categoryList where categoryColor is null
        defaultCategoryShouldNotBeFound("categoryColor.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryColorContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryColor contains DEFAULT_CATEGORY_COLOR
        defaultCategoryShouldBeFound("categoryColor.contains=" + DEFAULT_CATEGORY_COLOR);

        // Get all the categoryList where categoryColor contains UPDATED_CATEGORY_COLOR
        defaultCategoryShouldNotBeFound("categoryColor.contains=" + UPDATED_CATEGORY_COLOR);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryColorNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryColor does not contain DEFAULT_CATEGORY_COLOR
        defaultCategoryShouldNotBeFound("categoryColor.doesNotContain=" + DEFAULT_CATEGORY_COLOR);

        // Get all the categoryList where categoryColor does not contain UPDATED_CATEGORY_COLOR
        defaultCategoryShouldBeFound("categoryColor.doesNotContain=" + UPDATED_CATEGORY_COLOR);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription equals to DEFAULT_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.equals=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription equals to UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.equals=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription in DEFAULT_CATEGORY_DESCRIPTION or UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.in=" + DEFAULT_CATEGORY_DESCRIPTION + "," + UPDATED_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription equals to UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.in=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription is not null
        defaultCategoryShouldBeFound("categoryDescription.specified=true");

        // Get all the categoryList where categoryDescription is null
        defaultCategoryShouldNotBeFound("categoryDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryDescriptionContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription contains DEFAULT_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.contains=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription contains UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.contains=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription does not contain DEFAULT_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.doesNotContain=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription does not contain UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.doesNotContain=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryParams equals to DEFAULT_CATEGORY_PARAMS
        defaultCategoryShouldBeFound("categoryParams.equals=" + DEFAULT_CATEGORY_PARAMS);

        // Get all the categoryList where categoryParams equals to UPDATED_CATEGORY_PARAMS
        defaultCategoryShouldNotBeFound("categoryParams.equals=" + UPDATED_CATEGORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryParamsIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryParams in DEFAULT_CATEGORY_PARAMS or UPDATED_CATEGORY_PARAMS
        defaultCategoryShouldBeFound("categoryParams.in=" + DEFAULT_CATEGORY_PARAMS + "," + UPDATED_CATEGORY_PARAMS);

        // Get all the categoryList where categoryParams equals to UPDATED_CATEGORY_PARAMS
        defaultCategoryShouldNotBeFound("categoryParams.in=" + UPDATED_CATEGORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryParams is not null
        defaultCategoryShouldBeFound("categoryParams.specified=true");

        // Get all the categoryList where categoryParams is null
        defaultCategoryShouldNotBeFound("categoryParams.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryParamsContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryParams contains DEFAULT_CATEGORY_PARAMS
        defaultCategoryShouldBeFound("categoryParams.contains=" + DEFAULT_CATEGORY_PARAMS);

        // Get all the categoryList where categoryParams contains UPDATED_CATEGORY_PARAMS
        defaultCategoryShouldNotBeFound("categoryParams.contains=" + UPDATED_CATEGORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryParamsNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryParams does not contain DEFAULT_CATEGORY_PARAMS
        defaultCategoryShouldNotBeFound("categoryParams.doesNotContain=" + DEFAULT_CATEGORY_PARAMS);

        // Get all the categoryList where categoryParams does not contain UPDATED_CATEGORY_PARAMS
        defaultCategoryShouldBeFound("categoryParams.doesNotContain=" + UPDATED_CATEGORY_PARAMS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAttributs equals to DEFAULT_CATEGORY_ATTRIBUTS
        defaultCategoryShouldBeFound("categoryAttributs.equals=" + DEFAULT_CATEGORY_ATTRIBUTS);

        // Get all the categoryList where categoryAttributs equals to UPDATED_CATEGORY_ATTRIBUTS
        defaultCategoryShouldNotBeFound("categoryAttributs.equals=" + UPDATED_CATEGORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAttributs in DEFAULT_CATEGORY_ATTRIBUTS or UPDATED_CATEGORY_ATTRIBUTS
        defaultCategoryShouldBeFound("categoryAttributs.in=" + DEFAULT_CATEGORY_ATTRIBUTS + "," + UPDATED_CATEGORY_ATTRIBUTS);

        // Get all the categoryList where categoryAttributs equals to UPDATED_CATEGORY_ATTRIBUTS
        defaultCategoryShouldNotBeFound("categoryAttributs.in=" + UPDATED_CATEGORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAttributs is not null
        defaultCategoryShouldBeFound("categoryAttributs.specified=true");

        // Get all the categoryList where categoryAttributs is null
        defaultCategoryShouldNotBeFound("categoryAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAttributsContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAttributs contains DEFAULT_CATEGORY_ATTRIBUTS
        defaultCategoryShouldBeFound("categoryAttributs.contains=" + DEFAULT_CATEGORY_ATTRIBUTS);

        // Get all the categoryList where categoryAttributs contains UPDATED_CATEGORY_ATTRIBUTS
        defaultCategoryShouldNotBeFound("categoryAttributs.contains=" + UPDATED_CATEGORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryAttributs does not contain DEFAULT_CATEGORY_ATTRIBUTS
        defaultCategoryShouldNotBeFound("categoryAttributs.doesNotContain=" + DEFAULT_CATEGORY_ATTRIBUTS);

        // Get all the categoryList where categoryAttributs does not contain UPDATED_CATEGORY_ATTRIBUTS
        defaultCategoryShouldBeFound("categoryAttributs.doesNotContain=" + UPDATED_CATEGORY_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryStatIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryStat equals to DEFAULT_CATEGORY_STAT
        defaultCategoryShouldBeFound("categoryStat.equals=" + DEFAULT_CATEGORY_STAT);

        // Get all the categoryList where categoryStat equals to UPDATED_CATEGORY_STAT
        defaultCategoryShouldNotBeFound("categoryStat.equals=" + UPDATED_CATEGORY_STAT);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryStatIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryStat in DEFAULT_CATEGORY_STAT or UPDATED_CATEGORY_STAT
        defaultCategoryShouldBeFound("categoryStat.in=" + DEFAULT_CATEGORY_STAT + "," + UPDATED_CATEGORY_STAT);

        // Get all the categoryList where categoryStat equals to UPDATED_CATEGORY_STAT
        defaultCategoryShouldNotBeFound("categoryStat.in=" + UPDATED_CATEGORY_STAT);
    }

    @Test
    @Transactional
    void getAllCategoriesByCategoryStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryStat is not null
        defaultCategoryShouldBeFound("categoryStat.specified=true");

        // Get all the categoryList where categoryStat is null
        defaultCategoryShouldNotBeFound("categoryStat.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByFonctionIsEqualToSomething() throws Exception {
        Fonction fonction;
        if (TestUtil.findAll(em, Fonction.class).isEmpty()) {
            categoryRepository.saveAndFlush(category);
            fonction = FonctionResourceIT.createEntity(em);
        } else {
            fonction = TestUtil.findAll(em, Fonction.class).get(0);
        }
        em.persist(fonction);
        em.flush();
        category.addFonction(fonction);
        categoryRepository.saveAndFlush(category);
        Long fonctionId = fonction.getFonctionId();

        // Get all the categoryList where fonction equals to fonctionId
        defaultCategoryShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the categoryList where fonction equals to (fonctionId + 1)
        defaultCategoryShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            categoryRepository.saveAndFlush(category);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        category.addAccreditation(accreditation);
        categoryRepository.saveAndFlush(category);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the categoryList where accreditation equals to accreditationId
        defaultCategoryShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the categoryList where accreditation equals to (accreditationId + 1)
        defaultCategoryShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriesByPrintingModelIsEqualToSomething() throws Exception {
        PrintingModel printingModel;
        if (TestUtil.findAll(em, PrintingModel.class).isEmpty()) {
            categoryRepository.saveAndFlush(category);
            printingModel = PrintingModelResourceIT.createEntity(em);
        } else {
            printingModel = TestUtil.findAll(em, PrintingModel.class).get(0);
        }
        em.persist(printingModel);
        em.flush();
        category.setPrintingModel(printingModel);
        categoryRepository.saveAndFlush(category);
        Long printingModelId = printingModel.getPrintingModelId();

        // Get all the categoryList where printingModel equals to printingModelId
        defaultCategoryShouldBeFound("printingModelId.equals=" + printingModelId);

        // Get all the categoryList where printingModel equals to (printingModelId + 1)
        defaultCategoryShouldNotBeFound("printingModelId.equals=" + (printingModelId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            categoryRepository.saveAndFlush(category);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        category.setEvent(event);
        categoryRepository.saveAndFlush(category);
        Long eventId = event.getEventId();

        // Get all the categoryList where event equals to eventId
        defaultCategoryShouldBeFound("eventId.equals=" + eventId);

        // Get all the categoryList where event equals to (eventId + 1)
        defaultCategoryShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoryShouldBeFound(String filter) throws Exception {
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=categoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(category.getCategoryId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].categoryAbreviation").value(hasItem(DEFAULT_CATEGORY_ABREVIATION)))
            .andExpect(jsonPath("$.[*].categoryColor").value(hasItem(DEFAULT_CATEGORY_COLOR)))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].categoryLogoContentType").value(hasItem(DEFAULT_CATEGORY_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].categoryLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CATEGORY_LOGO))))
            .andExpect(jsonPath("$.[*].categoryParams").value(hasItem(DEFAULT_CATEGORY_PARAMS)))
            .andExpect(jsonPath("$.[*].categoryAttributs").value(hasItem(DEFAULT_CATEGORY_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].categoryStat").value(hasItem(DEFAULT_CATEGORY_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=categoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoryShouldNotBeFound(String filter) throws Exception {
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=categoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=categoryId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getCategoryId()).get();
        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
        em.detach(updatedCategory);
        updatedCategory
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryAbreviation(UPDATED_CATEGORY_ABREVIATION)
            .categoryColor(UPDATED_CATEGORY_COLOR)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .categoryLogo(UPDATED_CATEGORY_LOGO)
            .categoryLogoContentType(UPDATED_CATEGORY_LOGO_CONTENT_TYPE)
            .categoryParams(UPDATED_CATEGORY_PARAMS)
            .categoryAttributs(UPDATED_CATEGORY_ATTRIBUTS)
            .categoryStat(UPDATED_CATEGORY_STAT);
        CategoryDTO categoryDTO = categoryMapper.toDto(updatedCategory);

        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryDTO.getCategoryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testCategory.getCategoryAbreviation()).isEqualTo(UPDATED_CATEGORY_ABREVIATION);
        assertThat(testCategory.getCategoryColor()).isEqualTo(UPDATED_CATEGORY_COLOR);
        assertThat(testCategory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
        assertThat(testCategory.getCategoryLogo()).isEqualTo(UPDATED_CATEGORY_LOGO);
        assertThat(testCategory.getCategoryLogoContentType()).isEqualTo(UPDATED_CATEGORY_LOGO_CONTENT_TYPE);
        assertThat(testCategory.getCategoryParams()).isEqualTo(UPDATED_CATEGORY_PARAMS);
        assertThat(testCategory.getCategoryAttributs()).isEqualTo(UPDATED_CATEGORY_ATTRIBUTS);
        assertThat(testCategory.getCategoryStat()).isEqualTo(UPDATED_CATEGORY_STAT);
    }

    @Test
    @Transactional
    void putNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setCategoryId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryDTO.getCategoryId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setCategoryId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setCategoryId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoryWithPatch() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category using partial update
        Category partialUpdatedCategory = new Category();
        partialUpdatedCategory.setCategoryId(category.getCategoryId());

        partialUpdatedCategory
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .categoryLogo(UPDATED_CATEGORY_LOGO)
            .categoryLogoContentType(UPDATED_CATEGORY_LOGO_CONTENT_TYPE)
            .categoryParams(UPDATED_CATEGORY_PARAMS);

        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategory.getCategoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testCategory.getCategoryAbreviation()).isEqualTo(DEFAULT_CATEGORY_ABREVIATION);
        assertThat(testCategory.getCategoryColor()).isEqualTo(DEFAULT_CATEGORY_COLOR);
        assertThat(testCategory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
        assertThat(testCategory.getCategoryLogo()).isEqualTo(UPDATED_CATEGORY_LOGO);
        assertThat(testCategory.getCategoryLogoContentType()).isEqualTo(UPDATED_CATEGORY_LOGO_CONTENT_TYPE);
        assertThat(testCategory.getCategoryParams()).isEqualTo(UPDATED_CATEGORY_PARAMS);
        assertThat(testCategory.getCategoryAttributs()).isEqualTo(DEFAULT_CATEGORY_ATTRIBUTS);
        assertThat(testCategory.getCategoryStat()).isEqualTo(DEFAULT_CATEGORY_STAT);
    }

    @Test
    @Transactional
    void fullUpdateCategoryWithPatch() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category using partial update
        Category partialUpdatedCategory = new Category();
        partialUpdatedCategory.setCategoryId(category.getCategoryId());

        partialUpdatedCategory
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryAbreviation(UPDATED_CATEGORY_ABREVIATION)
            .categoryColor(UPDATED_CATEGORY_COLOR)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .categoryLogo(UPDATED_CATEGORY_LOGO)
            .categoryLogoContentType(UPDATED_CATEGORY_LOGO_CONTENT_TYPE)
            .categoryParams(UPDATED_CATEGORY_PARAMS)
            .categoryAttributs(UPDATED_CATEGORY_ATTRIBUTS)
            .categoryStat(UPDATED_CATEGORY_STAT);

        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategory.getCategoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testCategory.getCategoryAbreviation()).isEqualTo(UPDATED_CATEGORY_ABREVIATION);
        assertThat(testCategory.getCategoryColor()).isEqualTo(UPDATED_CATEGORY_COLOR);
        assertThat(testCategory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
        assertThat(testCategory.getCategoryLogo()).isEqualTo(UPDATED_CATEGORY_LOGO);
        assertThat(testCategory.getCategoryLogoContentType()).isEqualTo(UPDATED_CATEGORY_LOGO_CONTENT_TYPE);
        assertThat(testCategory.getCategoryParams()).isEqualTo(UPDATED_CATEGORY_PARAMS);
        assertThat(testCategory.getCategoryAttributs()).isEqualTo(UPDATED_CATEGORY_ATTRIBUTS);
        assertThat(testCategory.getCategoryStat()).isEqualTo(UPDATED_CATEGORY_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setCategoryId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoryDTO.getCategoryId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setCategoryId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setCategoryId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Delete the category
        restCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, category.getCategoryId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
