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
import ma.sig.events.domain.Event;
import ma.sig.events.domain.EventControl;
import ma.sig.events.domain.EventField;
import ma.sig.events.domain.EventForm;
import ma.sig.events.repository.EventFieldRepository;
import ma.sig.events.service.criteria.EventFieldCriteria;
import ma.sig.events.service.dto.EventFieldDTO;
import ma.sig.events.service.mapper.EventFieldMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventFieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventFieldResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FIELD_STAT = false;
    private static final Boolean UPDATED_FIELD_STAT = true;

    private static final String ENTITY_API_URL = "/api/event-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{fieldId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventFieldRepository eventFieldRepository;

    @Autowired
    private EventFieldMapper eventFieldMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventFieldMockMvc;

    private EventField eventField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventField createEntity(EntityManager em) {
        EventField eventField = new EventField()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldCategorie(DEFAULT_FIELD_CATEGORIE)
            .fieldDescription(DEFAULT_FIELD_DESCRIPTION)
            .fieldType(DEFAULT_FIELD_TYPE)
            .fieldParams(DEFAULT_FIELD_PARAMS)
            .fieldAttributs(DEFAULT_FIELD_ATTRIBUTS)
            .fieldStat(DEFAULT_FIELD_STAT);
        return eventField;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventField createUpdatedEntity(EntityManager em) {
        EventField eventField = new EventField()
            .fieldName(UPDATED_FIELD_NAME)
            .fieldCategorie(UPDATED_FIELD_CATEGORIE)
            .fieldDescription(UPDATED_FIELD_DESCRIPTION)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldParams(UPDATED_FIELD_PARAMS)
            .fieldAttributs(UPDATED_FIELD_ATTRIBUTS)
            .fieldStat(UPDATED_FIELD_STAT);
        return eventField;
    }

    @BeforeEach
    public void initTest() {
        eventField = createEntity(em);
    }

    @Test
    @Transactional
    void createEventField() throws Exception {
        int databaseSizeBeforeCreate = eventFieldRepository.findAll().size();
        // Create the EventField
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);
        restEventFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventFieldDTO)))
            .andExpect(status().isCreated());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeCreate + 1);
        EventField testEventField = eventFieldList.get(eventFieldList.size() - 1);
        assertThat(testEventField.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testEventField.getFieldCategorie()).isEqualTo(DEFAULT_FIELD_CATEGORIE);
        assertThat(testEventField.getFieldDescription()).isEqualTo(DEFAULT_FIELD_DESCRIPTION);
        assertThat(testEventField.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testEventField.getFieldParams()).isEqualTo(DEFAULT_FIELD_PARAMS);
        assertThat(testEventField.getFieldAttributs()).isEqualTo(DEFAULT_FIELD_ATTRIBUTS);
        assertThat(testEventField.getFieldStat()).isEqualTo(DEFAULT_FIELD_STAT);
    }

    @Test
    @Transactional
    void createEventFieldWithExistingId() throws Exception {
        // Create the EventField with an existing ID
        eventField.setFieldId(1L);
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);

        int databaseSizeBeforeCreate = eventFieldRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventFieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventFields() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList
        restEventFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=fieldId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].fieldId").value(hasItem(eventField.getFieldId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldCategorie").value(hasItem(DEFAULT_FIELD_CATEGORIE)))
            .andExpect(jsonPath("$.[*].fieldDescription").value(hasItem(DEFAULT_FIELD_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].fieldParams").value(hasItem(DEFAULT_FIELD_PARAMS)))
            .andExpect(jsonPath("$.[*].fieldAttributs").value(hasItem(DEFAULT_FIELD_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].fieldStat").value(hasItem(DEFAULT_FIELD_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getEventField() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get the eventField
        restEventFieldMockMvc
            .perform(get(ENTITY_API_URL_ID, eventField.getFieldId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.fieldId").value(eventField.getFieldId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldCategorie").value(DEFAULT_FIELD_CATEGORIE))
            .andExpect(jsonPath("$.fieldDescription").value(DEFAULT_FIELD_DESCRIPTION))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE))
            .andExpect(jsonPath("$.fieldParams").value(DEFAULT_FIELD_PARAMS))
            .andExpect(jsonPath("$.fieldAttributs").value(DEFAULT_FIELD_ATTRIBUTS))
            .andExpect(jsonPath("$.fieldStat").value(DEFAULT_FIELD_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getEventFieldsByIdFiltering() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        Long id = eventField.getFieldId();

        defaultEventFieldShouldBeFound("fieldId.equals=" + id);
        defaultEventFieldShouldNotBeFound("fieldId.notEquals=" + id);

        defaultEventFieldShouldBeFound("fieldId.greaterThanOrEqual=" + id);
        defaultEventFieldShouldNotBeFound("fieldId.greaterThan=" + id);

        defaultEventFieldShouldBeFound("fieldId.lessThanOrEqual=" + id);
        defaultEventFieldShouldNotBeFound("fieldId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldNameIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldName equals to DEFAULT_FIELD_NAME
        defaultEventFieldShouldBeFound("fieldName.equals=" + DEFAULT_FIELD_NAME);

        // Get all the eventFieldList where fieldName equals to UPDATED_FIELD_NAME
        defaultEventFieldShouldNotBeFound("fieldName.equals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldNameIsInShouldWork() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldName in DEFAULT_FIELD_NAME or UPDATED_FIELD_NAME
        defaultEventFieldShouldBeFound("fieldName.in=" + DEFAULT_FIELD_NAME + "," + UPDATED_FIELD_NAME);

        // Get all the eventFieldList where fieldName equals to UPDATED_FIELD_NAME
        defaultEventFieldShouldNotBeFound("fieldName.in=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldName is not null
        defaultEventFieldShouldBeFound("fieldName.specified=true");

        // Get all the eventFieldList where fieldName is null
        defaultEventFieldShouldNotBeFound("fieldName.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldNameContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldName contains DEFAULT_FIELD_NAME
        defaultEventFieldShouldBeFound("fieldName.contains=" + DEFAULT_FIELD_NAME);

        // Get all the eventFieldList where fieldName contains UPDATED_FIELD_NAME
        defaultEventFieldShouldNotBeFound("fieldName.contains=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldNameNotContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldName does not contain DEFAULT_FIELD_NAME
        defaultEventFieldShouldNotBeFound("fieldName.doesNotContain=" + DEFAULT_FIELD_NAME);

        // Get all the eventFieldList where fieldName does not contain UPDATED_FIELD_NAME
        defaultEventFieldShouldBeFound("fieldName.doesNotContain=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldCategorie equals to DEFAULT_FIELD_CATEGORIE
        defaultEventFieldShouldBeFound("fieldCategorie.equals=" + DEFAULT_FIELD_CATEGORIE);

        // Get all the eventFieldList where fieldCategorie equals to UPDATED_FIELD_CATEGORIE
        defaultEventFieldShouldNotBeFound("fieldCategorie.equals=" + UPDATED_FIELD_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldCategorieIsInShouldWork() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldCategorie in DEFAULT_FIELD_CATEGORIE or UPDATED_FIELD_CATEGORIE
        defaultEventFieldShouldBeFound("fieldCategorie.in=" + DEFAULT_FIELD_CATEGORIE + "," + UPDATED_FIELD_CATEGORIE);

        // Get all the eventFieldList where fieldCategorie equals to UPDATED_FIELD_CATEGORIE
        defaultEventFieldShouldNotBeFound("fieldCategorie.in=" + UPDATED_FIELD_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldCategorieIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldCategorie is not null
        defaultEventFieldShouldBeFound("fieldCategorie.specified=true");

        // Get all the eventFieldList where fieldCategorie is null
        defaultEventFieldShouldNotBeFound("fieldCategorie.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldCategorieContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldCategorie contains DEFAULT_FIELD_CATEGORIE
        defaultEventFieldShouldBeFound("fieldCategorie.contains=" + DEFAULT_FIELD_CATEGORIE);

        // Get all the eventFieldList where fieldCategorie contains UPDATED_FIELD_CATEGORIE
        defaultEventFieldShouldNotBeFound("fieldCategorie.contains=" + UPDATED_FIELD_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldCategorieNotContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldCategorie does not contain DEFAULT_FIELD_CATEGORIE
        defaultEventFieldShouldNotBeFound("fieldCategorie.doesNotContain=" + DEFAULT_FIELD_CATEGORIE);

        // Get all the eventFieldList where fieldCategorie does not contain UPDATED_FIELD_CATEGORIE
        defaultEventFieldShouldBeFound("fieldCategorie.doesNotContain=" + UPDATED_FIELD_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldDescription equals to DEFAULT_FIELD_DESCRIPTION
        defaultEventFieldShouldBeFound("fieldDescription.equals=" + DEFAULT_FIELD_DESCRIPTION);

        // Get all the eventFieldList where fieldDescription equals to UPDATED_FIELD_DESCRIPTION
        defaultEventFieldShouldNotBeFound("fieldDescription.equals=" + UPDATED_FIELD_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldDescription in DEFAULT_FIELD_DESCRIPTION or UPDATED_FIELD_DESCRIPTION
        defaultEventFieldShouldBeFound("fieldDescription.in=" + DEFAULT_FIELD_DESCRIPTION + "," + UPDATED_FIELD_DESCRIPTION);

        // Get all the eventFieldList where fieldDescription equals to UPDATED_FIELD_DESCRIPTION
        defaultEventFieldShouldNotBeFound("fieldDescription.in=" + UPDATED_FIELD_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldDescription is not null
        defaultEventFieldShouldBeFound("fieldDescription.specified=true");

        // Get all the eventFieldList where fieldDescription is null
        defaultEventFieldShouldNotBeFound("fieldDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldDescription contains DEFAULT_FIELD_DESCRIPTION
        defaultEventFieldShouldBeFound("fieldDescription.contains=" + DEFAULT_FIELD_DESCRIPTION);

        // Get all the eventFieldList where fieldDescription contains UPDATED_FIELD_DESCRIPTION
        defaultEventFieldShouldNotBeFound("fieldDescription.contains=" + UPDATED_FIELD_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldDescription does not contain DEFAULT_FIELD_DESCRIPTION
        defaultEventFieldShouldNotBeFound("fieldDescription.doesNotContain=" + DEFAULT_FIELD_DESCRIPTION);

        // Get all the eventFieldList where fieldDescription does not contain UPDATED_FIELD_DESCRIPTION
        defaultEventFieldShouldBeFound("fieldDescription.doesNotContain=" + UPDATED_FIELD_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldType equals to DEFAULT_FIELD_TYPE
        defaultEventFieldShouldBeFound("fieldType.equals=" + DEFAULT_FIELD_TYPE);

        // Get all the eventFieldList where fieldType equals to UPDATED_FIELD_TYPE
        defaultEventFieldShouldNotBeFound("fieldType.equals=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldTypeIsInShouldWork() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldType in DEFAULT_FIELD_TYPE or UPDATED_FIELD_TYPE
        defaultEventFieldShouldBeFound("fieldType.in=" + DEFAULT_FIELD_TYPE + "," + UPDATED_FIELD_TYPE);

        // Get all the eventFieldList where fieldType equals to UPDATED_FIELD_TYPE
        defaultEventFieldShouldNotBeFound("fieldType.in=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldType is not null
        defaultEventFieldShouldBeFound("fieldType.specified=true");

        // Get all the eventFieldList where fieldType is null
        defaultEventFieldShouldNotBeFound("fieldType.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldTypeContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldType contains DEFAULT_FIELD_TYPE
        defaultEventFieldShouldBeFound("fieldType.contains=" + DEFAULT_FIELD_TYPE);

        // Get all the eventFieldList where fieldType contains UPDATED_FIELD_TYPE
        defaultEventFieldShouldNotBeFound("fieldType.contains=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldTypeNotContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldType does not contain DEFAULT_FIELD_TYPE
        defaultEventFieldShouldNotBeFound("fieldType.doesNotContain=" + DEFAULT_FIELD_TYPE);

        // Get all the eventFieldList where fieldType does not contain UPDATED_FIELD_TYPE
        defaultEventFieldShouldBeFound("fieldType.doesNotContain=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldParams equals to DEFAULT_FIELD_PARAMS
        defaultEventFieldShouldBeFound("fieldParams.equals=" + DEFAULT_FIELD_PARAMS);

        // Get all the eventFieldList where fieldParams equals to UPDATED_FIELD_PARAMS
        defaultEventFieldShouldNotBeFound("fieldParams.equals=" + UPDATED_FIELD_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldParamsIsInShouldWork() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldParams in DEFAULT_FIELD_PARAMS or UPDATED_FIELD_PARAMS
        defaultEventFieldShouldBeFound("fieldParams.in=" + DEFAULT_FIELD_PARAMS + "," + UPDATED_FIELD_PARAMS);

        // Get all the eventFieldList where fieldParams equals to UPDATED_FIELD_PARAMS
        defaultEventFieldShouldNotBeFound("fieldParams.in=" + UPDATED_FIELD_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldParams is not null
        defaultEventFieldShouldBeFound("fieldParams.specified=true");

        // Get all the eventFieldList where fieldParams is null
        defaultEventFieldShouldNotBeFound("fieldParams.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldParamsContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldParams contains DEFAULT_FIELD_PARAMS
        defaultEventFieldShouldBeFound("fieldParams.contains=" + DEFAULT_FIELD_PARAMS);

        // Get all the eventFieldList where fieldParams contains UPDATED_FIELD_PARAMS
        defaultEventFieldShouldNotBeFound("fieldParams.contains=" + UPDATED_FIELD_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldParamsNotContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldParams does not contain DEFAULT_FIELD_PARAMS
        defaultEventFieldShouldNotBeFound("fieldParams.doesNotContain=" + DEFAULT_FIELD_PARAMS);

        // Get all the eventFieldList where fieldParams does not contain UPDATED_FIELD_PARAMS
        defaultEventFieldShouldBeFound("fieldParams.doesNotContain=" + UPDATED_FIELD_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldAttributs equals to DEFAULT_FIELD_ATTRIBUTS
        defaultEventFieldShouldBeFound("fieldAttributs.equals=" + DEFAULT_FIELD_ATTRIBUTS);

        // Get all the eventFieldList where fieldAttributs equals to UPDATED_FIELD_ATTRIBUTS
        defaultEventFieldShouldNotBeFound("fieldAttributs.equals=" + UPDATED_FIELD_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldAttributs in DEFAULT_FIELD_ATTRIBUTS or UPDATED_FIELD_ATTRIBUTS
        defaultEventFieldShouldBeFound("fieldAttributs.in=" + DEFAULT_FIELD_ATTRIBUTS + "," + UPDATED_FIELD_ATTRIBUTS);

        // Get all the eventFieldList where fieldAttributs equals to UPDATED_FIELD_ATTRIBUTS
        defaultEventFieldShouldNotBeFound("fieldAttributs.in=" + UPDATED_FIELD_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldAttributs is not null
        defaultEventFieldShouldBeFound("fieldAttributs.specified=true");

        // Get all the eventFieldList where fieldAttributs is null
        defaultEventFieldShouldNotBeFound("fieldAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldAttributsContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldAttributs contains DEFAULT_FIELD_ATTRIBUTS
        defaultEventFieldShouldBeFound("fieldAttributs.contains=" + DEFAULT_FIELD_ATTRIBUTS);

        // Get all the eventFieldList where fieldAttributs contains UPDATED_FIELD_ATTRIBUTS
        defaultEventFieldShouldNotBeFound("fieldAttributs.contains=" + UPDATED_FIELD_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldAttributs does not contain DEFAULT_FIELD_ATTRIBUTS
        defaultEventFieldShouldNotBeFound("fieldAttributs.doesNotContain=" + DEFAULT_FIELD_ATTRIBUTS);

        // Get all the eventFieldList where fieldAttributs does not contain UPDATED_FIELD_ATTRIBUTS
        defaultEventFieldShouldBeFound("fieldAttributs.doesNotContain=" + UPDATED_FIELD_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldStatIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldStat equals to DEFAULT_FIELD_STAT
        defaultEventFieldShouldBeFound("fieldStat.equals=" + DEFAULT_FIELD_STAT);

        // Get all the eventFieldList where fieldStat equals to UPDATED_FIELD_STAT
        defaultEventFieldShouldNotBeFound("fieldStat.equals=" + UPDATED_FIELD_STAT);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldStatIsInShouldWork() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldStat in DEFAULT_FIELD_STAT or UPDATED_FIELD_STAT
        defaultEventFieldShouldBeFound("fieldStat.in=" + DEFAULT_FIELD_STAT + "," + UPDATED_FIELD_STAT);

        // Get all the eventFieldList where fieldStat equals to UPDATED_FIELD_STAT
        defaultEventFieldShouldNotBeFound("fieldStat.in=" + UPDATED_FIELD_STAT);
    }

    @Test
    @Transactional
    void getAllEventFieldsByFieldStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        // Get all the eventFieldList where fieldStat is not null
        defaultEventFieldShouldBeFound("fieldStat.specified=true");

        // Get all the eventFieldList where fieldStat is null
        defaultEventFieldShouldNotBeFound("fieldStat.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFieldsByEventControlIsEqualToSomething() throws Exception {
        EventControl eventControl;
        if (TestUtil.findAll(em, EventControl.class).isEmpty()) {
            eventFieldRepository.saveAndFlush(eventField);
            eventControl = EventControlResourceIT.createEntity(em);
        } else {
            eventControl = TestUtil.findAll(em, EventControl.class).get(0);
        }
        em.persist(eventControl);
        em.flush();
        eventField.addEventControl(eventControl);
        eventFieldRepository.saveAndFlush(eventField);
        Long eventControlId = eventControl.getControlId();

        // Get all the eventFieldList where eventControl equals to eventControlId
        defaultEventFieldShouldBeFound("eventControlId.equals=" + eventControlId);

        // Get all the eventFieldList where eventControl equals to (eventControlId + 1)
        defaultEventFieldShouldNotBeFound("eventControlId.equals=" + (eventControlId + 1));
    }

    @Test
    @Transactional
    void getAllEventFieldsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            eventFieldRepository.saveAndFlush(eventField);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        eventField.setEvent(event);
        eventFieldRepository.saveAndFlush(eventField);
        Long eventId = event.getEventId();

        // Get all the eventFieldList where event equals to eventId
        defaultEventFieldShouldBeFound("eventId.equals=" + eventId);

        // Get all the eventFieldList where event equals to (eventId + 1)
        defaultEventFieldShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllEventFieldsByEventFormIsEqualToSomething() throws Exception {
        EventForm eventForm;
        if (TestUtil.findAll(em, EventForm.class).isEmpty()) {
            eventFieldRepository.saveAndFlush(eventField);
            eventForm = EventFormResourceIT.createEntity(em);
        } else {
            eventForm = TestUtil.findAll(em, EventForm.class).get(0);
        }
        em.persist(eventForm);
        em.flush();
        eventField.setEventForm(eventForm);
        eventFieldRepository.saveAndFlush(eventField);
        Long eventFormId = eventForm.getFormId();

        // Get all the eventFieldList where eventForm equals to eventFormId
        defaultEventFieldShouldBeFound("eventFormId.equals=" + eventFormId);

        // Get all the eventFieldList where eventForm equals to (eventFormId + 1)
        defaultEventFieldShouldNotBeFound("eventFormId.equals=" + (eventFormId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventFieldShouldBeFound(String filter) throws Exception {
        restEventFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=fieldId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].fieldId").value(hasItem(eventField.getFieldId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldCategorie").value(hasItem(DEFAULT_FIELD_CATEGORIE)))
            .andExpect(jsonPath("$.[*].fieldDescription").value(hasItem(DEFAULT_FIELD_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].fieldParams").value(hasItem(DEFAULT_FIELD_PARAMS)))
            .andExpect(jsonPath("$.[*].fieldAttributs").value(hasItem(DEFAULT_FIELD_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].fieldStat").value(hasItem(DEFAULT_FIELD_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restEventFieldMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=fieldId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventFieldShouldNotBeFound(String filter) throws Exception {
        restEventFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=fieldId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventFieldMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=fieldId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventField() throws Exception {
        // Get the eventField
        restEventFieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventField() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();

        // Update the eventField
        EventField updatedEventField = eventFieldRepository.findById(eventField.getFieldId()).get();
        // Disconnect from session so that the updates on updatedEventField are not directly saved in db
        em.detach(updatedEventField);
        updatedEventField
            .fieldName(UPDATED_FIELD_NAME)
            .fieldCategorie(UPDATED_FIELD_CATEGORIE)
            .fieldDescription(UPDATED_FIELD_DESCRIPTION)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldParams(UPDATED_FIELD_PARAMS)
            .fieldAttributs(UPDATED_FIELD_ATTRIBUTS)
            .fieldStat(UPDATED_FIELD_STAT);
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(updatedEventField);

        restEventFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventFieldDTO.getFieldId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventFieldDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
        EventField testEventField = eventFieldList.get(eventFieldList.size() - 1);
        assertThat(testEventField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testEventField.getFieldCategorie()).isEqualTo(UPDATED_FIELD_CATEGORIE);
        assertThat(testEventField.getFieldDescription()).isEqualTo(UPDATED_FIELD_DESCRIPTION);
        assertThat(testEventField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testEventField.getFieldParams()).isEqualTo(UPDATED_FIELD_PARAMS);
        assertThat(testEventField.getFieldAttributs()).isEqualTo(UPDATED_FIELD_ATTRIBUTS);
        assertThat(testEventField.getFieldStat()).isEqualTo(UPDATED_FIELD_STAT);
    }

    @Test
    @Transactional
    void putNonExistingEventField() throws Exception {
        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();
        eventField.setFieldId(count.incrementAndGet());

        // Create the EventField
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventFieldDTO.getFieldId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventField() throws Exception {
        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();
        eventField.setFieldId(count.incrementAndGet());

        // Create the EventField
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventField() throws Exception {
        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();
        eventField.setFieldId(count.incrementAndGet());

        // Create the EventField
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventFieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventFieldWithPatch() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();

        // Update the eventField using partial update
        EventField partialUpdatedEventField = new EventField();
        partialUpdatedEventField.setFieldId(eventField.getFieldId());

        partialUpdatedEventField.fieldDescription(UPDATED_FIELD_DESCRIPTION).fieldType(UPDATED_FIELD_TYPE).fieldStat(UPDATED_FIELD_STAT);

        restEventFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventField.getFieldId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventField))
            )
            .andExpect(status().isOk());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
        EventField testEventField = eventFieldList.get(eventFieldList.size() - 1);
        assertThat(testEventField.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testEventField.getFieldCategorie()).isEqualTo(DEFAULT_FIELD_CATEGORIE);
        assertThat(testEventField.getFieldDescription()).isEqualTo(UPDATED_FIELD_DESCRIPTION);
        assertThat(testEventField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testEventField.getFieldParams()).isEqualTo(DEFAULT_FIELD_PARAMS);
        assertThat(testEventField.getFieldAttributs()).isEqualTo(DEFAULT_FIELD_ATTRIBUTS);
        assertThat(testEventField.getFieldStat()).isEqualTo(UPDATED_FIELD_STAT);
    }

    @Test
    @Transactional
    void fullUpdateEventFieldWithPatch() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();

        // Update the eventField using partial update
        EventField partialUpdatedEventField = new EventField();
        partialUpdatedEventField.setFieldId(eventField.getFieldId());

        partialUpdatedEventField
            .fieldName(UPDATED_FIELD_NAME)
            .fieldCategorie(UPDATED_FIELD_CATEGORIE)
            .fieldDescription(UPDATED_FIELD_DESCRIPTION)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldParams(UPDATED_FIELD_PARAMS)
            .fieldAttributs(UPDATED_FIELD_ATTRIBUTS)
            .fieldStat(UPDATED_FIELD_STAT);

        restEventFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventField.getFieldId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventField))
            )
            .andExpect(status().isOk());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
        EventField testEventField = eventFieldList.get(eventFieldList.size() - 1);
        assertThat(testEventField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testEventField.getFieldCategorie()).isEqualTo(UPDATED_FIELD_CATEGORIE);
        assertThat(testEventField.getFieldDescription()).isEqualTo(UPDATED_FIELD_DESCRIPTION);
        assertThat(testEventField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testEventField.getFieldParams()).isEqualTo(UPDATED_FIELD_PARAMS);
        assertThat(testEventField.getFieldAttributs()).isEqualTo(UPDATED_FIELD_ATTRIBUTS);
        assertThat(testEventField.getFieldStat()).isEqualTo(UPDATED_FIELD_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingEventField() throws Exception {
        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();
        eventField.setFieldId(count.incrementAndGet());

        // Create the EventField
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventFieldDTO.getFieldId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventField() throws Exception {
        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();
        eventField.setFieldId(count.incrementAndGet());

        // Create the EventField
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventField() throws Exception {
        int databaseSizeBeforeUpdate = eventFieldRepository.findAll().size();
        eventField.setFieldId(count.incrementAndGet());

        // Create the EventField
        EventFieldDTO eventFieldDTO = eventFieldMapper.toDto(eventField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFieldMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventFieldDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventField in the database
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventField() throws Exception {
        // Initialize the database
        eventFieldRepository.saveAndFlush(eventField);

        int databaseSizeBeforeDelete = eventFieldRepository.findAll().size();

        // Delete the eventField
        restEventFieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventField.getFieldId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventField> eventFieldList = eventFieldRepository.findAll();
        assertThat(eventFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
