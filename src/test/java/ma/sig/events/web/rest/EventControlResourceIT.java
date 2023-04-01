package ma.sig.events.web.rest;

import static ma.sig.events.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ma.sig.events.IntegrationTest;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.EventControl;
import ma.sig.events.domain.EventField;
import ma.sig.events.repository.EventControlRepository;
import ma.sig.events.service.criteria.EventControlCriteria;
import ma.sig.events.service.dto.EventControlDTO;
import ma.sig.events.service.mapper.EventControlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventControlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventControlResourceIT {

    private static final String DEFAULT_CONTROL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROL_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_VALUE_STRING = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTROL_VALUE_LONG = 1L;
    private static final Long UPDATED_CONTROL_VALUE_LONG = 2L;
    private static final Long SMALLER_CONTROL_VALUE_LONG = 1L - 1L;

    private static final ZonedDateTime DEFAULT_CONTROL_VALUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CONTROL_VALUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CONTROL_VALUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_CONTROL_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROL_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONTROL_VALUE_STAT = false;
    private static final Boolean UPDATED_CONTROL_VALUE_STAT = true;

    private static final String ENTITY_API_URL = "/api/event-controls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{controlId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventControlRepository eventControlRepository;

    @Autowired
    private EventControlMapper eventControlMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventControlMockMvc;

    private EventControl eventControl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventControl createEntity(EntityManager em) {
        EventControl eventControl = new EventControl()
            .controlName(DEFAULT_CONTROL_NAME)
            .controlDescription(DEFAULT_CONTROL_DESCRIPTION)
            .controlType(DEFAULT_CONTROL_TYPE)
            .controlValueString(DEFAULT_CONTROL_VALUE_STRING)
            .controlValueLong(DEFAULT_CONTROL_VALUE_LONG)
            .controlValueDate(DEFAULT_CONTROL_VALUE_DATE)
            .controlParams(DEFAULT_CONTROL_PARAMS)
            .controlAttributs(DEFAULT_CONTROL_ATTRIBUTS)
            .controlValueStat(DEFAULT_CONTROL_VALUE_STAT);
        return eventControl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventControl createUpdatedEntity(EntityManager em) {
        EventControl eventControl = new EventControl()
            .controlName(UPDATED_CONTROL_NAME)
            .controlDescription(UPDATED_CONTROL_DESCRIPTION)
            .controlType(UPDATED_CONTROL_TYPE)
            .controlValueString(UPDATED_CONTROL_VALUE_STRING)
            .controlValueLong(UPDATED_CONTROL_VALUE_LONG)
            .controlValueDate(UPDATED_CONTROL_VALUE_DATE)
            .controlParams(UPDATED_CONTROL_PARAMS)
            .controlAttributs(UPDATED_CONTROL_ATTRIBUTS)
            .controlValueStat(UPDATED_CONTROL_VALUE_STAT);
        return eventControl;
    }

    @BeforeEach
    public void initTest() {
        eventControl = createEntity(em);
    }

    @Test
    @Transactional
    void createEventControl() throws Exception {
        int databaseSizeBeforeCreate = eventControlRepository.findAll().size();
        // Create the EventControl
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);
        restEventControlMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeCreate + 1);
        EventControl testEventControl = eventControlList.get(eventControlList.size() - 1);
        assertThat(testEventControl.getControlName()).isEqualTo(DEFAULT_CONTROL_NAME);
        assertThat(testEventControl.getControlDescription()).isEqualTo(DEFAULT_CONTROL_DESCRIPTION);
        assertThat(testEventControl.getControlType()).isEqualTo(DEFAULT_CONTROL_TYPE);
        assertThat(testEventControl.getControlValueString()).isEqualTo(DEFAULT_CONTROL_VALUE_STRING);
        assertThat(testEventControl.getControlValueLong()).isEqualTo(DEFAULT_CONTROL_VALUE_LONG);
        assertThat(testEventControl.getControlValueDate()).isEqualTo(DEFAULT_CONTROL_VALUE_DATE);
        assertThat(testEventControl.getControlParams()).isEqualTo(DEFAULT_CONTROL_PARAMS);
        assertThat(testEventControl.getControlAttributs()).isEqualTo(DEFAULT_CONTROL_ATTRIBUTS);
        assertThat(testEventControl.getControlValueStat()).isEqualTo(DEFAULT_CONTROL_VALUE_STAT);
    }

    @Test
    @Transactional
    void createEventControlWithExistingId() throws Exception {
        // Create the EventControl with an existing ID
        eventControl.setControlId(1L);
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);

        int databaseSizeBeforeCreate = eventControlRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventControlMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventControls() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList
        restEventControlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=controlId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].controlId").value(hasItem(eventControl.getControlId().intValue())))
            .andExpect(jsonPath("$.[*].controlName").value(hasItem(DEFAULT_CONTROL_NAME)))
            .andExpect(jsonPath("$.[*].controlDescription").value(hasItem(DEFAULT_CONTROL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].controlType").value(hasItem(DEFAULT_CONTROL_TYPE)))
            .andExpect(jsonPath("$.[*].controlValueString").value(hasItem(DEFAULT_CONTROL_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].controlValueLong").value(hasItem(DEFAULT_CONTROL_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].controlValueDate").value(hasItem(sameInstant(DEFAULT_CONTROL_VALUE_DATE))))
            .andExpect(jsonPath("$.[*].controlParams").value(hasItem(DEFAULT_CONTROL_PARAMS)))
            .andExpect(jsonPath("$.[*].controlAttributs").value(hasItem(DEFAULT_CONTROL_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].controlValueStat").value(hasItem(DEFAULT_CONTROL_VALUE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getEventControl() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get the eventControl
        restEventControlMockMvc
            .perform(get(ENTITY_API_URL_ID, eventControl.getControlId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.controlId").value(eventControl.getControlId().intValue()))
            .andExpect(jsonPath("$.controlName").value(DEFAULT_CONTROL_NAME))
            .andExpect(jsonPath("$.controlDescription").value(DEFAULT_CONTROL_DESCRIPTION))
            .andExpect(jsonPath("$.controlType").value(DEFAULT_CONTROL_TYPE))
            .andExpect(jsonPath("$.controlValueString").value(DEFAULT_CONTROL_VALUE_STRING))
            .andExpect(jsonPath("$.controlValueLong").value(DEFAULT_CONTROL_VALUE_LONG.intValue()))
            .andExpect(jsonPath("$.controlValueDate").value(sameInstant(DEFAULT_CONTROL_VALUE_DATE)))
            .andExpect(jsonPath("$.controlParams").value(DEFAULT_CONTROL_PARAMS))
            .andExpect(jsonPath("$.controlAttributs").value(DEFAULT_CONTROL_ATTRIBUTS))
            .andExpect(jsonPath("$.controlValueStat").value(DEFAULT_CONTROL_VALUE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getEventControlsByIdFiltering() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        Long id = eventControl.getControlId();

        defaultEventControlShouldBeFound("controlId.equals=" + id);
        defaultEventControlShouldNotBeFound("controlId.notEquals=" + id);

        defaultEventControlShouldBeFound("controlId.greaterThanOrEqual=" + id);
        defaultEventControlShouldNotBeFound("controlId.greaterThan=" + id);

        defaultEventControlShouldBeFound("controlId.lessThanOrEqual=" + id);
        defaultEventControlShouldNotBeFound("controlId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlNameIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlName equals to DEFAULT_CONTROL_NAME
        defaultEventControlShouldBeFound("controlName.equals=" + DEFAULT_CONTROL_NAME);

        // Get all the eventControlList where controlName equals to UPDATED_CONTROL_NAME
        defaultEventControlShouldNotBeFound("controlName.equals=" + UPDATED_CONTROL_NAME);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlNameIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlName in DEFAULT_CONTROL_NAME or UPDATED_CONTROL_NAME
        defaultEventControlShouldBeFound("controlName.in=" + DEFAULT_CONTROL_NAME + "," + UPDATED_CONTROL_NAME);

        // Get all the eventControlList where controlName equals to UPDATED_CONTROL_NAME
        defaultEventControlShouldNotBeFound("controlName.in=" + UPDATED_CONTROL_NAME);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlName is not null
        defaultEventControlShouldBeFound("controlName.specified=true");

        // Get all the eventControlList where controlName is null
        defaultEventControlShouldNotBeFound("controlName.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlNameContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlName contains DEFAULT_CONTROL_NAME
        defaultEventControlShouldBeFound("controlName.contains=" + DEFAULT_CONTROL_NAME);

        // Get all the eventControlList where controlName contains UPDATED_CONTROL_NAME
        defaultEventControlShouldNotBeFound("controlName.contains=" + UPDATED_CONTROL_NAME);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlNameNotContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlName does not contain DEFAULT_CONTROL_NAME
        defaultEventControlShouldNotBeFound("controlName.doesNotContain=" + DEFAULT_CONTROL_NAME);

        // Get all the eventControlList where controlName does not contain UPDATED_CONTROL_NAME
        defaultEventControlShouldBeFound("controlName.doesNotContain=" + UPDATED_CONTROL_NAME);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlDescription equals to DEFAULT_CONTROL_DESCRIPTION
        defaultEventControlShouldBeFound("controlDescription.equals=" + DEFAULT_CONTROL_DESCRIPTION);

        // Get all the eventControlList where controlDescription equals to UPDATED_CONTROL_DESCRIPTION
        defaultEventControlShouldNotBeFound("controlDescription.equals=" + UPDATED_CONTROL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlDescription in DEFAULT_CONTROL_DESCRIPTION or UPDATED_CONTROL_DESCRIPTION
        defaultEventControlShouldBeFound("controlDescription.in=" + DEFAULT_CONTROL_DESCRIPTION + "," + UPDATED_CONTROL_DESCRIPTION);

        // Get all the eventControlList where controlDescription equals to UPDATED_CONTROL_DESCRIPTION
        defaultEventControlShouldNotBeFound("controlDescription.in=" + UPDATED_CONTROL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlDescription is not null
        defaultEventControlShouldBeFound("controlDescription.specified=true");

        // Get all the eventControlList where controlDescription is null
        defaultEventControlShouldNotBeFound("controlDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlDescription contains DEFAULT_CONTROL_DESCRIPTION
        defaultEventControlShouldBeFound("controlDescription.contains=" + DEFAULT_CONTROL_DESCRIPTION);

        // Get all the eventControlList where controlDescription contains UPDATED_CONTROL_DESCRIPTION
        defaultEventControlShouldNotBeFound("controlDescription.contains=" + UPDATED_CONTROL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlDescription does not contain DEFAULT_CONTROL_DESCRIPTION
        defaultEventControlShouldNotBeFound("controlDescription.doesNotContain=" + DEFAULT_CONTROL_DESCRIPTION);

        // Get all the eventControlList where controlDescription does not contain UPDATED_CONTROL_DESCRIPTION
        defaultEventControlShouldBeFound("controlDescription.doesNotContain=" + UPDATED_CONTROL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlType equals to DEFAULT_CONTROL_TYPE
        defaultEventControlShouldBeFound("controlType.equals=" + DEFAULT_CONTROL_TYPE);

        // Get all the eventControlList where controlType equals to UPDATED_CONTROL_TYPE
        defaultEventControlShouldNotBeFound("controlType.equals=" + UPDATED_CONTROL_TYPE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlTypeIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlType in DEFAULT_CONTROL_TYPE or UPDATED_CONTROL_TYPE
        defaultEventControlShouldBeFound("controlType.in=" + DEFAULT_CONTROL_TYPE + "," + UPDATED_CONTROL_TYPE);

        // Get all the eventControlList where controlType equals to UPDATED_CONTROL_TYPE
        defaultEventControlShouldNotBeFound("controlType.in=" + UPDATED_CONTROL_TYPE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlType is not null
        defaultEventControlShouldBeFound("controlType.specified=true");

        // Get all the eventControlList where controlType is null
        defaultEventControlShouldNotBeFound("controlType.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlTypeContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlType contains DEFAULT_CONTROL_TYPE
        defaultEventControlShouldBeFound("controlType.contains=" + DEFAULT_CONTROL_TYPE);

        // Get all the eventControlList where controlType contains UPDATED_CONTROL_TYPE
        defaultEventControlShouldNotBeFound("controlType.contains=" + UPDATED_CONTROL_TYPE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlTypeNotContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlType does not contain DEFAULT_CONTROL_TYPE
        defaultEventControlShouldNotBeFound("controlType.doesNotContain=" + DEFAULT_CONTROL_TYPE);

        // Get all the eventControlList where controlType does not contain UPDATED_CONTROL_TYPE
        defaultEventControlShouldBeFound("controlType.doesNotContain=" + UPDATED_CONTROL_TYPE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStringIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueString equals to DEFAULT_CONTROL_VALUE_STRING
        defaultEventControlShouldBeFound("controlValueString.equals=" + DEFAULT_CONTROL_VALUE_STRING);

        // Get all the eventControlList where controlValueString equals to UPDATED_CONTROL_VALUE_STRING
        defaultEventControlShouldNotBeFound("controlValueString.equals=" + UPDATED_CONTROL_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStringIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueString in DEFAULT_CONTROL_VALUE_STRING or UPDATED_CONTROL_VALUE_STRING
        defaultEventControlShouldBeFound("controlValueString.in=" + DEFAULT_CONTROL_VALUE_STRING + "," + UPDATED_CONTROL_VALUE_STRING);

        // Get all the eventControlList where controlValueString equals to UPDATED_CONTROL_VALUE_STRING
        defaultEventControlShouldNotBeFound("controlValueString.in=" + UPDATED_CONTROL_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueString is not null
        defaultEventControlShouldBeFound("controlValueString.specified=true");

        // Get all the eventControlList where controlValueString is null
        defaultEventControlShouldNotBeFound("controlValueString.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStringContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueString contains DEFAULT_CONTROL_VALUE_STRING
        defaultEventControlShouldBeFound("controlValueString.contains=" + DEFAULT_CONTROL_VALUE_STRING);

        // Get all the eventControlList where controlValueString contains UPDATED_CONTROL_VALUE_STRING
        defaultEventControlShouldNotBeFound("controlValueString.contains=" + UPDATED_CONTROL_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStringNotContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueString does not contain DEFAULT_CONTROL_VALUE_STRING
        defaultEventControlShouldNotBeFound("controlValueString.doesNotContain=" + DEFAULT_CONTROL_VALUE_STRING);

        // Get all the eventControlList where controlValueString does not contain UPDATED_CONTROL_VALUE_STRING
        defaultEventControlShouldBeFound("controlValueString.doesNotContain=" + UPDATED_CONTROL_VALUE_STRING);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueLongIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueLong equals to DEFAULT_CONTROL_VALUE_LONG
        defaultEventControlShouldBeFound("controlValueLong.equals=" + DEFAULT_CONTROL_VALUE_LONG);

        // Get all the eventControlList where controlValueLong equals to UPDATED_CONTROL_VALUE_LONG
        defaultEventControlShouldNotBeFound("controlValueLong.equals=" + UPDATED_CONTROL_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueLongIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueLong in DEFAULT_CONTROL_VALUE_LONG or UPDATED_CONTROL_VALUE_LONG
        defaultEventControlShouldBeFound("controlValueLong.in=" + DEFAULT_CONTROL_VALUE_LONG + "," + UPDATED_CONTROL_VALUE_LONG);

        // Get all the eventControlList where controlValueLong equals to UPDATED_CONTROL_VALUE_LONG
        defaultEventControlShouldNotBeFound("controlValueLong.in=" + UPDATED_CONTROL_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueLongIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueLong is not null
        defaultEventControlShouldBeFound("controlValueLong.specified=true");

        // Get all the eventControlList where controlValueLong is null
        defaultEventControlShouldNotBeFound("controlValueLong.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueLongIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueLong is greater than or equal to DEFAULT_CONTROL_VALUE_LONG
        defaultEventControlShouldBeFound("controlValueLong.greaterThanOrEqual=" + DEFAULT_CONTROL_VALUE_LONG);

        // Get all the eventControlList where controlValueLong is greater than or equal to UPDATED_CONTROL_VALUE_LONG
        defaultEventControlShouldNotBeFound("controlValueLong.greaterThanOrEqual=" + UPDATED_CONTROL_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueLongIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueLong is less than or equal to DEFAULT_CONTROL_VALUE_LONG
        defaultEventControlShouldBeFound("controlValueLong.lessThanOrEqual=" + DEFAULT_CONTROL_VALUE_LONG);

        // Get all the eventControlList where controlValueLong is less than or equal to SMALLER_CONTROL_VALUE_LONG
        defaultEventControlShouldNotBeFound("controlValueLong.lessThanOrEqual=" + SMALLER_CONTROL_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueLongIsLessThanSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueLong is less than DEFAULT_CONTROL_VALUE_LONG
        defaultEventControlShouldNotBeFound("controlValueLong.lessThan=" + DEFAULT_CONTROL_VALUE_LONG);

        // Get all the eventControlList where controlValueLong is less than UPDATED_CONTROL_VALUE_LONG
        defaultEventControlShouldBeFound("controlValueLong.lessThan=" + UPDATED_CONTROL_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueLongIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueLong is greater than DEFAULT_CONTROL_VALUE_LONG
        defaultEventControlShouldNotBeFound("controlValueLong.greaterThan=" + DEFAULT_CONTROL_VALUE_LONG);

        // Get all the eventControlList where controlValueLong is greater than SMALLER_CONTROL_VALUE_LONG
        defaultEventControlShouldBeFound("controlValueLong.greaterThan=" + SMALLER_CONTROL_VALUE_LONG);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueDate equals to DEFAULT_CONTROL_VALUE_DATE
        defaultEventControlShouldBeFound("controlValueDate.equals=" + DEFAULT_CONTROL_VALUE_DATE);

        // Get all the eventControlList where controlValueDate equals to UPDATED_CONTROL_VALUE_DATE
        defaultEventControlShouldNotBeFound("controlValueDate.equals=" + UPDATED_CONTROL_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueDateIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueDate in DEFAULT_CONTROL_VALUE_DATE or UPDATED_CONTROL_VALUE_DATE
        defaultEventControlShouldBeFound("controlValueDate.in=" + DEFAULT_CONTROL_VALUE_DATE + "," + UPDATED_CONTROL_VALUE_DATE);

        // Get all the eventControlList where controlValueDate equals to UPDATED_CONTROL_VALUE_DATE
        defaultEventControlShouldNotBeFound("controlValueDate.in=" + UPDATED_CONTROL_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueDate is not null
        defaultEventControlShouldBeFound("controlValueDate.specified=true");

        // Get all the eventControlList where controlValueDate is null
        defaultEventControlShouldNotBeFound("controlValueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueDate is greater than or equal to DEFAULT_CONTROL_VALUE_DATE
        defaultEventControlShouldBeFound("controlValueDate.greaterThanOrEqual=" + DEFAULT_CONTROL_VALUE_DATE);

        // Get all the eventControlList where controlValueDate is greater than or equal to UPDATED_CONTROL_VALUE_DATE
        defaultEventControlShouldNotBeFound("controlValueDate.greaterThanOrEqual=" + UPDATED_CONTROL_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueDate is less than or equal to DEFAULT_CONTROL_VALUE_DATE
        defaultEventControlShouldBeFound("controlValueDate.lessThanOrEqual=" + DEFAULT_CONTROL_VALUE_DATE);

        // Get all the eventControlList where controlValueDate is less than or equal to SMALLER_CONTROL_VALUE_DATE
        defaultEventControlShouldNotBeFound("controlValueDate.lessThanOrEqual=" + SMALLER_CONTROL_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueDate is less than DEFAULT_CONTROL_VALUE_DATE
        defaultEventControlShouldNotBeFound("controlValueDate.lessThan=" + DEFAULT_CONTROL_VALUE_DATE);

        // Get all the eventControlList where controlValueDate is less than UPDATED_CONTROL_VALUE_DATE
        defaultEventControlShouldBeFound("controlValueDate.lessThan=" + UPDATED_CONTROL_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueDate is greater than DEFAULT_CONTROL_VALUE_DATE
        defaultEventControlShouldNotBeFound("controlValueDate.greaterThan=" + DEFAULT_CONTROL_VALUE_DATE);

        // Get all the eventControlList where controlValueDate is greater than SMALLER_CONTROL_VALUE_DATE
        defaultEventControlShouldBeFound("controlValueDate.greaterThan=" + SMALLER_CONTROL_VALUE_DATE);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlParams equals to DEFAULT_CONTROL_PARAMS
        defaultEventControlShouldBeFound("controlParams.equals=" + DEFAULT_CONTROL_PARAMS);

        // Get all the eventControlList where controlParams equals to UPDATED_CONTROL_PARAMS
        defaultEventControlShouldNotBeFound("controlParams.equals=" + UPDATED_CONTROL_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlParamsIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlParams in DEFAULT_CONTROL_PARAMS or UPDATED_CONTROL_PARAMS
        defaultEventControlShouldBeFound("controlParams.in=" + DEFAULT_CONTROL_PARAMS + "," + UPDATED_CONTROL_PARAMS);

        // Get all the eventControlList where controlParams equals to UPDATED_CONTROL_PARAMS
        defaultEventControlShouldNotBeFound("controlParams.in=" + UPDATED_CONTROL_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlParams is not null
        defaultEventControlShouldBeFound("controlParams.specified=true");

        // Get all the eventControlList where controlParams is null
        defaultEventControlShouldNotBeFound("controlParams.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlParamsContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlParams contains DEFAULT_CONTROL_PARAMS
        defaultEventControlShouldBeFound("controlParams.contains=" + DEFAULT_CONTROL_PARAMS);

        // Get all the eventControlList where controlParams contains UPDATED_CONTROL_PARAMS
        defaultEventControlShouldNotBeFound("controlParams.contains=" + UPDATED_CONTROL_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlParamsNotContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlParams does not contain DEFAULT_CONTROL_PARAMS
        defaultEventControlShouldNotBeFound("controlParams.doesNotContain=" + DEFAULT_CONTROL_PARAMS);

        // Get all the eventControlList where controlParams does not contain UPDATED_CONTROL_PARAMS
        defaultEventControlShouldBeFound("controlParams.doesNotContain=" + UPDATED_CONTROL_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlAttributs equals to DEFAULT_CONTROL_ATTRIBUTS
        defaultEventControlShouldBeFound("controlAttributs.equals=" + DEFAULT_CONTROL_ATTRIBUTS);

        // Get all the eventControlList where controlAttributs equals to UPDATED_CONTROL_ATTRIBUTS
        defaultEventControlShouldNotBeFound("controlAttributs.equals=" + UPDATED_CONTROL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlAttributs in DEFAULT_CONTROL_ATTRIBUTS or UPDATED_CONTROL_ATTRIBUTS
        defaultEventControlShouldBeFound("controlAttributs.in=" + DEFAULT_CONTROL_ATTRIBUTS + "," + UPDATED_CONTROL_ATTRIBUTS);

        // Get all the eventControlList where controlAttributs equals to UPDATED_CONTROL_ATTRIBUTS
        defaultEventControlShouldNotBeFound("controlAttributs.in=" + UPDATED_CONTROL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlAttributs is not null
        defaultEventControlShouldBeFound("controlAttributs.specified=true");

        // Get all the eventControlList where controlAttributs is null
        defaultEventControlShouldNotBeFound("controlAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByControlAttributsContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlAttributs contains DEFAULT_CONTROL_ATTRIBUTS
        defaultEventControlShouldBeFound("controlAttributs.contains=" + DEFAULT_CONTROL_ATTRIBUTS);

        // Get all the eventControlList where controlAttributs contains UPDATED_CONTROL_ATTRIBUTS
        defaultEventControlShouldNotBeFound("controlAttributs.contains=" + UPDATED_CONTROL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlAttributs does not contain DEFAULT_CONTROL_ATTRIBUTS
        defaultEventControlShouldNotBeFound("controlAttributs.doesNotContain=" + DEFAULT_CONTROL_ATTRIBUTS);

        // Get all the eventControlList where controlAttributs does not contain UPDATED_CONTROL_ATTRIBUTS
        defaultEventControlShouldBeFound("controlAttributs.doesNotContain=" + UPDATED_CONTROL_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStatIsEqualToSomething() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueStat equals to DEFAULT_CONTROL_VALUE_STAT
        defaultEventControlShouldBeFound("controlValueStat.equals=" + DEFAULT_CONTROL_VALUE_STAT);

        // Get all the eventControlList where controlValueStat equals to UPDATED_CONTROL_VALUE_STAT
        defaultEventControlShouldNotBeFound("controlValueStat.equals=" + UPDATED_CONTROL_VALUE_STAT);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStatIsInShouldWork() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueStat in DEFAULT_CONTROL_VALUE_STAT or UPDATED_CONTROL_VALUE_STAT
        defaultEventControlShouldBeFound("controlValueStat.in=" + DEFAULT_CONTROL_VALUE_STAT + "," + UPDATED_CONTROL_VALUE_STAT);

        // Get all the eventControlList where controlValueStat equals to UPDATED_CONTROL_VALUE_STAT
        defaultEventControlShouldNotBeFound("controlValueStat.in=" + UPDATED_CONTROL_VALUE_STAT);
    }

    @Test
    @Transactional
    void getAllEventControlsByControlValueStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        // Get all the eventControlList where controlValueStat is not null
        defaultEventControlShouldBeFound("controlValueStat.specified=true");

        // Get all the eventControlList where controlValueStat is null
        defaultEventControlShouldNotBeFound("controlValueStat.specified=false");
    }

    @Test
    @Transactional
    void getAllEventControlsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            eventControlRepository.saveAndFlush(eventControl);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        eventControl.setEvent(event);
        eventControlRepository.saveAndFlush(eventControl);
        Long eventId = event.getEventId();

        // Get all the eventControlList where event equals to eventId
        defaultEventControlShouldBeFound("eventId.equals=" + eventId);

        // Get all the eventControlList where event equals to (eventId + 1)
        defaultEventControlShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllEventControlsByEventFieldIsEqualToSomething() throws Exception {
        EventField eventField;
        if (TestUtil.findAll(em, EventField.class).isEmpty()) {
            eventControlRepository.saveAndFlush(eventControl);
            eventField = EventFieldResourceIT.createEntity(em);
        } else {
            eventField = TestUtil.findAll(em, EventField.class).get(0);
        }
        em.persist(eventField);
        em.flush();
        eventControl.setEventField(eventField);
        eventControlRepository.saveAndFlush(eventControl);
        Long eventFieldId = eventField.getFieldId();

        // Get all the eventControlList where eventField equals to eventFieldId
        defaultEventControlShouldBeFound("eventFieldId.equals=" + eventFieldId);

        // Get all the eventControlList where eventField equals to (eventFieldId + 1)
        defaultEventControlShouldNotBeFound("eventFieldId.equals=" + (eventFieldId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventControlShouldBeFound(String filter) throws Exception {
        restEventControlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=controlId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].controlId").value(hasItem(eventControl.getControlId().intValue())))
            .andExpect(jsonPath("$.[*].controlName").value(hasItem(DEFAULT_CONTROL_NAME)))
            .andExpect(jsonPath("$.[*].controlDescription").value(hasItem(DEFAULT_CONTROL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].controlType").value(hasItem(DEFAULT_CONTROL_TYPE)))
            .andExpect(jsonPath("$.[*].controlValueString").value(hasItem(DEFAULT_CONTROL_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].controlValueLong").value(hasItem(DEFAULT_CONTROL_VALUE_LONG.intValue())))
            .andExpect(jsonPath("$.[*].controlValueDate").value(hasItem(sameInstant(DEFAULT_CONTROL_VALUE_DATE))))
            .andExpect(jsonPath("$.[*].controlParams").value(hasItem(DEFAULT_CONTROL_PARAMS)))
            .andExpect(jsonPath("$.[*].controlAttributs").value(hasItem(DEFAULT_CONTROL_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].controlValueStat").value(hasItem(DEFAULT_CONTROL_VALUE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restEventControlMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=controlId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventControlShouldNotBeFound(String filter) throws Exception {
        restEventControlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=controlId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventControlMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=controlId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventControl() throws Exception {
        // Get the eventControl
        restEventControlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventControl() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();

        // Update the eventControl
        EventControl updatedEventControl = eventControlRepository.findById(eventControl.getControlId()).get();
        // Disconnect from session so that the updates on updatedEventControl are not directly saved in db
        em.detach(updatedEventControl);
        updatedEventControl
            .controlName(UPDATED_CONTROL_NAME)
            .controlDescription(UPDATED_CONTROL_DESCRIPTION)
            .controlType(UPDATED_CONTROL_TYPE)
            .controlValueString(UPDATED_CONTROL_VALUE_STRING)
            .controlValueLong(UPDATED_CONTROL_VALUE_LONG)
            .controlValueDate(UPDATED_CONTROL_VALUE_DATE)
            .controlParams(UPDATED_CONTROL_PARAMS)
            .controlAttributs(UPDATED_CONTROL_ATTRIBUTS)
            .controlValueStat(UPDATED_CONTROL_VALUE_STAT);
        EventControlDTO eventControlDTO = eventControlMapper.toDto(updatedEventControl);

        restEventControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventControlDTO.getControlId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
        EventControl testEventControl = eventControlList.get(eventControlList.size() - 1);
        assertThat(testEventControl.getControlName()).isEqualTo(UPDATED_CONTROL_NAME);
        assertThat(testEventControl.getControlDescription()).isEqualTo(UPDATED_CONTROL_DESCRIPTION);
        assertThat(testEventControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testEventControl.getControlValueString()).isEqualTo(UPDATED_CONTROL_VALUE_STRING);
        assertThat(testEventControl.getControlValueLong()).isEqualTo(UPDATED_CONTROL_VALUE_LONG);
        assertThat(testEventControl.getControlValueDate()).isEqualTo(UPDATED_CONTROL_VALUE_DATE);
        assertThat(testEventControl.getControlParams()).isEqualTo(UPDATED_CONTROL_PARAMS);
        assertThat(testEventControl.getControlAttributs()).isEqualTo(UPDATED_CONTROL_ATTRIBUTS);
        assertThat(testEventControl.getControlValueStat()).isEqualTo(UPDATED_CONTROL_VALUE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingEventControl() throws Exception {
        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();
        eventControl.setControlId(count.incrementAndGet());

        // Create the EventControl
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventControlDTO.getControlId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventControl() throws Exception {
        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();
        eventControl.setControlId(count.incrementAndGet());

        // Create the EventControl
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventControl() throws Exception {
        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();
        eventControl.setControlId(count.incrementAndGet());

        // Create the EventControl
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventControlMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventControlWithPatch() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();

        // Update the eventControl using partial update
        EventControl partialUpdatedEventControl = new EventControl();
        partialUpdatedEventControl.setControlId(eventControl.getControlId());

        partialUpdatedEventControl
            .controlDescription(UPDATED_CONTROL_DESCRIPTION)
            .controlValueString(UPDATED_CONTROL_VALUE_STRING)
            .controlValueDate(UPDATED_CONTROL_VALUE_DATE)
            .controlValueStat(UPDATED_CONTROL_VALUE_STAT);

        restEventControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventControl.getControlId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventControl))
            )
            .andExpect(status().isOk());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
        EventControl testEventControl = eventControlList.get(eventControlList.size() - 1);
        assertThat(testEventControl.getControlName()).isEqualTo(DEFAULT_CONTROL_NAME);
        assertThat(testEventControl.getControlDescription()).isEqualTo(UPDATED_CONTROL_DESCRIPTION);
        assertThat(testEventControl.getControlType()).isEqualTo(DEFAULT_CONTROL_TYPE);
        assertThat(testEventControl.getControlValueString()).isEqualTo(UPDATED_CONTROL_VALUE_STRING);
        assertThat(testEventControl.getControlValueLong()).isEqualTo(DEFAULT_CONTROL_VALUE_LONG);
        assertThat(testEventControl.getControlValueDate()).isEqualTo(UPDATED_CONTROL_VALUE_DATE);
        assertThat(testEventControl.getControlParams()).isEqualTo(DEFAULT_CONTROL_PARAMS);
        assertThat(testEventControl.getControlAttributs()).isEqualTo(DEFAULT_CONTROL_ATTRIBUTS);
        assertThat(testEventControl.getControlValueStat()).isEqualTo(UPDATED_CONTROL_VALUE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateEventControlWithPatch() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();

        // Update the eventControl using partial update
        EventControl partialUpdatedEventControl = new EventControl();
        partialUpdatedEventControl.setControlId(eventControl.getControlId());

        partialUpdatedEventControl
            .controlName(UPDATED_CONTROL_NAME)
            .controlDescription(UPDATED_CONTROL_DESCRIPTION)
            .controlType(UPDATED_CONTROL_TYPE)
            .controlValueString(UPDATED_CONTROL_VALUE_STRING)
            .controlValueLong(UPDATED_CONTROL_VALUE_LONG)
            .controlValueDate(UPDATED_CONTROL_VALUE_DATE)
            .controlParams(UPDATED_CONTROL_PARAMS)
            .controlAttributs(UPDATED_CONTROL_ATTRIBUTS)
            .controlValueStat(UPDATED_CONTROL_VALUE_STAT);

        restEventControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventControl.getControlId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventControl))
            )
            .andExpect(status().isOk());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
        EventControl testEventControl = eventControlList.get(eventControlList.size() - 1);
        assertThat(testEventControl.getControlName()).isEqualTo(UPDATED_CONTROL_NAME);
        assertThat(testEventControl.getControlDescription()).isEqualTo(UPDATED_CONTROL_DESCRIPTION);
        assertThat(testEventControl.getControlType()).isEqualTo(UPDATED_CONTROL_TYPE);
        assertThat(testEventControl.getControlValueString()).isEqualTo(UPDATED_CONTROL_VALUE_STRING);
        assertThat(testEventControl.getControlValueLong()).isEqualTo(UPDATED_CONTROL_VALUE_LONG);
        assertThat(testEventControl.getControlValueDate()).isEqualTo(UPDATED_CONTROL_VALUE_DATE);
        assertThat(testEventControl.getControlParams()).isEqualTo(UPDATED_CONTROL_PARAMS);
        assertThat(testEventControl.getControlAttributs()).isEqualTo(UPDATED_CONTROL_ATTRIBUTS);
        assertThat(testEventControl.getControlValueStat()).isEqualTo(UPDATED_CONTROL_VALUE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingEventControl() throws Exception {
        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();
        eventControl.setControlId(count.incrementAndGet());

        // Create the EventControl
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventControlDTO.getControlId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventControl() throws Exception {
        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();
        eventControl.setControlId(count.incrementAndGet());

        // Create the EventControl
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventControl() throws Exception {
        int databaseSizeBeforeUpdate = eventControlRepository.findAll().size();
        eventControl.setControlId(count.incrementAndGet());

        // Create the EventControl
        EventControlDTO eventControlDTO = eventControlMapper.toDto(eventControl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventControlMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventControlDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventControl in the database
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventControl() throws Exception {
        // Initialize the database
        eventControlRepository.saveAndFlush(eventControl);

        int databaseSizeBeforeDelete = eventControlRepository.findAll().size();

        // Delete the eventControl
        restEventControlMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventControl.getControlId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventControl> eventControlList = eventControlRepository.findAll();
        assertThat(eventControlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
