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
import ma.sig.events.domain.EventField;
import ma.sig.events.domain.EventForm;
import ma.sig.events.repository.EventFormRepository;
import ma.sig.events.service.criteria.EventFormCriteria;
import ma.sig.events.service.dto.EventFormDTO;
import ma.sig.events.service.mapper.EventFormMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventFormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventFormResourceIT {

    private static final String DEFAULT_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FORM_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_FORM_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_FORM_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FORM_STAT = false;
    private static final Boolean UPDATED_FORM_STAT = true;

    private static final String ENTITY_API_URL = "/api/event-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{formId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventFormRepository eventFormRepository;

    @Autowired
    private EventFormMapper eventFormMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventFormMockMvc;

    private EventForm eventForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventForm createEntity(EntityManager em) {
        EventForm eventForm = new EventForm()
            .formName(DEFAULT_FORM_NAME)
            .formDescription(DEFAULT_FORM_DESCRIPTION)
            .formParams(DEFAULT_FORM_PARAMS)
            .formAttributs(DEFAULT_FORM_ATTRIBUTS)
            .formStat(DEFAULT_FORM_STAT);
        return eventForm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventForm createUpdatedEntity(EntityManager em) {
        EventForm eventForm = new EventForm()
            .formName(UPDATED_FORM_NAME)
            .formDescription(UPDATED_FORM_DESCRIPTION)
            .formParams(UPDATED_FORM_PARAMS)
            .formAttributs(UPDATED_FORM_ATTRIBUTS)
            .formStat(UPDATED_FORM_STAT);
        return eventForm;
    }

    @BeforeEach
    public void initTest() {
        eventForm = createEntity(em);
    }

    @Test
    @Transactional
    void createEventForm() throws Exception {
        int databaseSizeBeforeCreate = eventFormRepository.findAll().size();
        // Create the EventForm
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);
        restEventFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventFormDTO)))
            .andExpect(status().isCreated());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeCreate + 1);
        EventForm testEventForm = eventFormList.get(eventFormList.size() - 1);
        assertThat(testEventForm.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
        assertThat(testEventForm.getFormDescription()).isEqualTo(DEFAULT_FORM_DESCRIPTION);
        assertThat(testEventForm.getFormParams()).isEqualTo(DEFAULT_FORM_PARAMS);
        assertThat(testEventForm.getFormAttributs()).isEqualTo(DEFAULT_FORM_ATTRIBUTS);
        assertThat(testEventForm.getFormStat()).isEqualTo(DEFAULT_FORM_STAT);
    }

    @Test
    @Transactional
    void createEventFormWithExistingId() throws Exception {
        // Create the EventForm with an existing ID
        eventForm.setFormId(1L);
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);

        int databaseSizeBeforeCreate = eventFormRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventForms() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList
        restEventFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=formId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(eventForm.getFormId().intValue())))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].formDescription").value(hasItem(DEFAULT_FORM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formParams").value(hasItem(DEFAULT_FORM_PARAMS)))
            .andExpect(jsonPath("$.[*].formAttributs").value(hasItem(DEFAULT_FORM_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].formStat").value(hasItem(DEFAULT_FORM_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getEventForm() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get the eventForm
        restEventFormMockMvc
            .perform(get(ENTITY_API_URL_ID, eventForm.getFormId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.formId").value(eventForm.getFormId().intValue()))
            .andExpect(jsonPath("$.formName").value(DEFAULT_FORM_NAME))
            .andExpect(jsonPath("$.formDescription").value(DEFAULT_FORM_DESCRIPTION))
            .andExpect(jsonPath("$.formParams").value(DEFAULT_FORM_PARAMS))
            .andExpect(jsonPath("$.formAttributs").value(DEFAULT_FORM_ATTRIBUTS))
            .andExpect(jsonPath("$.formStat").value(DEFAULT_FORM_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getEventFormsByIdFiltering() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        Long id = eventForm.getFormId();

        defaultEventFormShouldBeFound("formId.equals=" + id);
        defaultEventFormShouldNotBeFound("formId.notEquals=" + id);

        defaultEventFormShouldBeFound("formId.greaterThanOrEqual=" + id);
        defaultEventFormShouldNotBeFound("formId.greaterThan=" + id);

        defaultEventFormShouldBeFound("formId.lessThanOrEqual=" + id);
        defaultEventFormShouldNotBeFound("formId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormNameIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formName equals to DEFAULT_FORM_NAME
        defaultEventFormShouldBeFound("formName.equals=" + DEFAULT_FORM_NAME);

        // Get all the eventFormList where formName equals to UPDATED_FORM_NAME
        defaultEventFormShouldNotBeFound("formName.equals=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormNameIsInShouldWork() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formName in DEFAULT_FORM_NAME or UPDATED_FORM_NAME
        defaultEventFormShouldBeFound("formName.in=" + DEFAULT_FORM_NAME + "," + UPDATED_FORM_NAME);

        // Get all the eventFormList where formName equals to UPDATED_FORM_NAME
        defaultEventFormShouldNotBeFound("formName.in=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formName is not null
        defaultEventFormShouldBeFound("formName.specified=true");

        // Get all the eventFormList where formName is null
        defaultEventFormShouldNotBeFound("formName.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFormsByFormNameContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formName contains DEFAULT_FORM_NAME
        defaultEventFormShouldBeFound("formName.contains=" + DEFAULT_FORM_NAME);

        // Get all the eventFormList where formName contains UPDATED_FORM_NAME
        defaultEventFormShouldNotBeFound("formName.contains=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormNameNotContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formName does not contain DEFAULT_FORM_NAME
        defaultEventFormShouldNotBeFound("formName.doesNotContain=" + DEFAULT_FORM_NAME);

        // Get all the eventFormList where formName does not contain UPDATED_FORM_NAME
        defaultEventFormShouldBeFound("formName.doesNotContain=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formDescription equals to DEFAULT_FORM_DESCRIPTION
        defaultEventFormShouldBeFound("formDescription.equals=" + DEFAULT_FORM_DESCRIPTION);

        // Get all the eventFormList where formDescription equals to UPDATED_FORM_DESCRIPTION
        defaultEventFormShouldNotBeFound("formDescription.equals=" + UPDATED_FORM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formDescription in DEFAULT_FORM_DESCRIPTION or UPDATED_FORM_DESCRIPTION
        defaultEventFormShouldBeFound("formDescription.in=" + DEFAULT_FORM_DESCRIPTION + "," + UPDATED_FORM_DESCRIPTION);

        // Get all the eventFormList where formDescription equals to UPDATED_FORM_DESCRIPTION
        defaultEventFormShouldNotBeFound("formDescription.in=" + UPDATED_FORM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formDescription is not null
        defaultEventFormShouldBeFound("formDescription.specified=true");

        // Get all the eventFormList where formDescription is null
        defaultEventFormShouldNotBeFound("formDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFormsByFormDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formDescription contains DEFAULT_FORM_DESCRIPTION
        defaultEventFormShouldBeFound("formDescription.contains=" + DEFAULT_FORM_DESCRIPTION);

        // Get all the eventFormList where formDescription contains UPDATED_FORM_DESCRIPTION
        defaultEventFormShouldNotBeFound("formDescription.contains=" + UPDATED_FORM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formDescription does not contain DEFAULT_FORM_DESCRIPTION
        defaultEventFormShouldNotBeFound("formDescription.doesNotContain=" + DEFAULT_FORM_DESCRIPTION);

        // Get all the eventFormList where formDescription does not contain UPDATED_FORM_DESCRIPTION
        defaultEventFormShouldBeFound("formDescription.doesNotContain=" + UPDATED_FORM_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formParams equals to DEFAULT_FORM_PARAMS
        defaultEventFormShouldBeFound("formParams.equals=" + DEFAULT_FORM_PARAMS);

        // Get all the eventFormList where formParams equals to UPDATED_FORM_PARAMS
        defaultEventFormShouldNotBeFound("formParams.equals=" + UPDATED_FORM_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormParamsIsInShouldWork() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formParams in DEFAULT_FORM_PARAMS or UPDATED_FORM_PARAMS
        defaultEventFormShouldBeFound("formParams.in=" + DEFAULT_FORM_PARAMS + "," + UPDATED_FORM_PARAMS);

        // Get all the eventFormList where formParams equals to UPDATED_FORM_PARAMS
        defaultEventFormShouldNotBeFound("formParams.in=" + UPDATED_FORM_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formParams is not null
        defaultEventFormShouldBeFound("formParams.specified=true");

        // Get all the eventFormList where formParams is null
        defaultEventFormShouldNotBeFound("formParams.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFormsByFormParamsContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formParams contains DEFAULT_FORM_PARAMS
        defaultEventFormShouldBeFound("formParams.contains=" + DEFAULT_FORM_PARAMS);

        // Get all the eventFormList where formParams contains UPDATED_FORM_PARAMS
        defaultEventFormShouldNotBeFound("formParams.contains=" + UPDATED_FORM_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormParamsNotContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formParams does not contain DEFAULT_FORM_PARAMS
        defaultEventFormShouldNotBeFound("formParams.doesNotContain=" + DEFAULT_FORM_PARAMS);

        // Get all the eventFormList where formParams does not contain UPDATED_FORM_PARAMS
        defaultEventFormShouldBeFound("formParams.doesNotContain=" + UPDATED_FORM_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formAttributs equals to DEFAULT_FORM_ATTRIBUTS
        defaultEventFormShouldBeFound("formAttributs.equals=" + DEFAULT_FORM_ATTRIBUTS);

        // Get all the eventFormList where formAttributs equals to UPDATED_FORM_ATTRIBUTS
        defaultEventFormShouldNotBeFound("formAttributs.equals=" + UPDATED_FORM_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formAttributs in DEFAULT_FORM_ATTRIBUTS or UPDATED_FORM_ATTRIBUTS
        defaultEventFormShouldBeFound("formAttributs.in=" + DEFAULT_FORM_ATTRIBUTS + "," + UPDATED_FORM_ATTRIBUTS);

        // Get all the eventFormList where formAttributs equals to UPDATED_FORM_ATTRIBUTS
        defaultEventFormShouldNotBeFound("formAttributs.in=" + UPDATED_FORM_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formAttributs is not null
        defaultEventFormShouldBeFound("formAttributs.specified=true");

        // Get all the eventFormList where formAttributs is null
        defaultEventFormShouldNotBeFound("formAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFormsByFormAttributsContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formAttributs contains DEFAULT_FORM_ATTRIBUTS
        defaultEventFormShouldBeFound("formAttributs.contains=" + DEFAULT_FORM_ATTRIBUTS);

        // Get all the eventFormList where formAttributs contains UPDATED_FORM_ATTRIBUTS
        defaultEventFormShouldNotBeFound("formAttributs.contains=" + UPDATED_FORM_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formAttributs does not contain DEFAULT_FORM_ATTRIBUTS
        defaultEventFormShouldNotBeFound("formAttributs.doesNotContain=" + DEFAULT_FORM_ATTRIBUTS);

        // Get all the eventFormList where formAttributs does not contain UPDATED_FORM_ATTRIBUTS
        defaultEventFormShouldBeFound("formAttributs.doesNotContain=" + UPDATED_FORM_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormStatIsEqualToSomething() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formStat equals to DEFAULT_FORM_STAT
        defaultEventFormShouldBeFound("formStat.equals=" + DEFAULT_FORM_STAT);

        // Get all the eventFormList where formStat equals to UPDATED_FORM_STAT
        defaultEventFormShouldNotBeFound("formStat.equals=" + UPDATED_FORM_STAT);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormStatIsInShouldWork() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formStat in DEFAULT_FORM_STAT or UPDATED_FORM_STAT
        defaultEventFormShouldBeFound("formStat.in=" + DEFAULT_FORM_STAT + "," + UPDATED_FORM_STAT);

        // Get all the eventFormList where formStat equals to UPDATED_FORM_STAT
        defaultEventFormShouldNotBeFound("formStat.in=" + UPDATED_FORM_STAT);
    }

    @Test
    @Transactional
    void getAllEventFormsByFormStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        // Get all the eventFormList where formStat is not null
        defaultEventFormShouldBeFound("formStat.specified=true");

        // Get all the eventFormList where formStat is null
        defaultEventFormShouldNotBeFound("formStat.specified=false");
    }

    @Test
    @Transactional
    void getAllEventFormsByEventFieldIsEqualToSomething() throws Exception {
        EventField eventField;
        if (TestUtil.findAll(em, EventField.class).isEmpty()) {
            eventFormRepository.saveAndFlush(eventForm);
            eventField = EventFieldResourceIT.createEntity(em);
        } else {
            eventField = TestUtil.findAll(em, EventField.class).get(0);
        }
        em.persist(eventField);
        em.flush();
        eventForm.addEventField(eventField);
        eventFormRepository.saveAndFlush(eventForm);
        Long eventFieldId = eventField.getFieldId();

        // Get all the eventFormList where eventField equals to eventFieldId
        defaultEventFormShouldBeFound("eventFieldId.equals=" + eventFieldId);

        // Get all the eventFormList where eventField equals to (eventFieldId + 1)
        defaultEventFormShouldNotBeFound("eventFieldId.equals=" + (eventFieldId + 1));
    }

    @Test
    @Transactional
    void getAllEventFormsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            eventFormRepository.saveAndFlush(eventForm);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        eventForm.setEvent(event);
        eventFormRepository.saveAndFlush(eventForm);
        Long eventId = event.getEventId();

        // Get all the eventFormList where event equals to eventId
        defaultEventFormShouldBeFound("eventId.equals=" + eventId);

        // Get all the eventFormList where event equals to (eventId + 1)
        defaultEventFormShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventFormShouldBeFound(String filter) throws Exception {
        restEventFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=formId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(eventForm.getFormId().intValue())))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].formDescription").value(hasItem(DEFAULT_FORM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formParams").value(hasItem(DEFAULT_FORM_PARAMS)))
            .andExpect(jsonPath("$.[*].formAttributs").value(hasItem(DEFAULT_FORM_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].formStat").value(hasItem(DEFAULT_FORM_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restEventFormMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=formId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventFormShouldNotBeFound(String filter) throws Exception {
        restEventFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=formId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventFormMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=formId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventForm() throws Exception {
        // Get the eventForm
        restEventFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventForm() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();

        // Update the eventForm
        EventForm updatedEventForm = eventFormRepository.findById(eventForm.getFormId()).get();
        // Disconnect from session so that the updates on updatedEventForm are not directly saved in db
        em.detach(updatedEventForm);
        updatedEventForm
            .formName(UPDATED_FORM_NAME)
            .formDescription(UPDATED_FORM_DESCRIPTION)
            .formParams(UPDATED_FORM_PARAMS)
            .formAttributs(UPDATED_FORM_ATTRIBUTS)
            .formStat(UPDATED_FORM_STAT);
        EventFormDTO eventFormDTO = eventFormMapper.toDto(updatedEventForm);

        restEventFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventFormDTO.getFormId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventFormDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
        EventForm testEventForm = eventFormList.get(eventFormList.size() - 1);
        assertThat(testEventForm.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testEventForm.getFormDescription()).isEqualTo(UPDATED_FORM_DESCRIPTION);
        assertThat(testEventForm.getFormParams()).isEqualTo(UPDATED_FORM_PARAMS);
        assertThat(testEventForm.getFormAttributs()).isEqualTo(UPDATED_FORM_ATTRIBUTS);
        assertThat(testEventForm.getFormStat()).isEqualTo(UPDATED_FORM_STAT);
    }

    @Test
    @Transactional
    void putNonExistingEventForm() throws Exception {
        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();
        eventForm.setFormId(count.incrementAndGet());

        // Create the EventForm
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventFormDTO.getFormId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventForm() throws Exception {
        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();
        eventForm.setFormId(count.incrementAndGet());

        // Create the EventForm
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventForm() throws Exception {
        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();
        eventForm.setFormId(count.incrementAndGet());

        // Create the EventForm
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventFormDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventFormWithPatch() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();

        // Update the eventForm using partial update
        EventForm partialUpdatedEventForm = new EventForm();
        partialUpdatedEventForm.setFormId(eventForm.getFormId());

        partialUpdatedEventForm
            .formName(UPDATED_FORM_NAME)
            .formDescription(UPDATED_FORM_DESCRIPTION)
            .formParams(UPDATED_FORM_PARAMS)
            .formAttributs(UPDATED_FORM_ATTRIBUTS)
            .formStat(UPDATED_FORM_STAT);

        restEventFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventForm.getFormId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventForm))
            )
            .andExpect(status().isOk());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
        EventForm testEventForm = eventFormList.get(eventFormList.size() - 1);
        assertThat(testEventForm.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testEventForm.getFormDescription()).isEqualTo(UPDATED_FORM_DESCRIPTION);
        assertThat(testEventForm.getFormParams()).isEqualTo(UPDATED_FORM_PARAMS);
        assertThat(testEventForm.getFormAttributs()).isEqualTo(UPDATED_FORM_ATTRIBUTS);
        assertThat(testEventForm.getFormStat()).isEqualTo(UPDATED_FORM_STAT);
    }

    @Test
    @Transactional
    void fullUpdateEventFormWithPatch() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();

        // Update the eventForm using partial update
        EventForm partialUpdatedEventForm = new EventForm();
        partialUpdatedEventForm.setFormId(eventForm.getFormId());

        partialUpdatedEventForm
            .formName(UPDATED_FORM_NAME)
            .formDescription(UPDATED_FORM_DESCRIPTION)
            .formParams(UPDATED_FORM_PARAMS)
            .formAttributs(UPDATED_FORM_ATTRIBUTS)
            .formStat(UPDATED_FORM_STAT);

        restEventFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventForm.getFormId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventForm))
            )
            .andExpect(status().isOk());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
        EventForm testEventForm = eventFormList.get(eventFormList.size() - 1);
        assertThat(testEventForm.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testEventForm.getFormDescription()).isEqualTo(UPDATED_FORM_DESCRIPTION);
        assertThat(testEventForm.getFormParams()).isEqualTo(UPDATED_FORM_PARAMS);
        assertThat(testEventForm.getFormAttributs()).isEqualTo(UPDATED_FORM_ATTRIBUTS);
        assertThat(testEventForm.getFormStat()).isEqualTo(UPDATED_FORM_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingEventForm() throws Exception {
        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();
        eventForm.setFormId(count.incrementAndGet());

        // Create the EventForm
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventFormDTO.getFormId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventForm() throws Exception {
        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();
        eventForm.setFormId(count.incrementAndGet());

        // Create the EventForm
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventForm() throws Exception {
        int databaseSizeBeforeUpdate = eventFormRepository.findAll().size();
        eventForm.setFormId(count.incrementAndGet());

        // Create the EventForm
        EventFormDTO eventFormDTO = eventFormMapper.toDto(eventForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventFormMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventFormDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventForm in the database
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventForm() throws Exception {
        // Initialize the database
        eventFormRepository.saveAndFlush(eventForm);

        int databaseSizeBeforeDelete = eventFormRepository.findAll().size();

        // Delete the eventForm
        restEventFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventForm.getFormId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventForm> eventFormList = eventFormRepository.findAll();
        assertThat(eventFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
