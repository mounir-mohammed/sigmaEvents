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
import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.AccreditationType;
import ma.sig.events.domain.Area;
import ma.sig.events.domain.Attachement;
import ma.sig.events.domain.Category;
import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.domain.CheckAccreditationReport;
import ma.sig.events.domain.Code;
import ma.sig.events.domain.DayPassInfo;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.EventControl;
import ma.sig.events.domain.EventField;
import ma.sig.events.domain.EventForm;
import ma.sig.events.domain.Fonction;
import ma.sig.events.domain.InfoSupp;
import ma.sig.events.domain.Language;
import ma.sig.events.domain.Note;
import ma.sig.events.domain.OperationHistory;
import ma.sig.events.domain.Organiz;
import ma.sig.events.domain.PhotoArchive;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.domain.PrintingServer;
import ma.sig.events.domain.Setting;
import ma.sig.events.domain.Site;
import ma.sig.events.repository.EventRepository;
import ma.sig.events.service.criteria.EventCriteria;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.mapper.EventMapper;
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
 * Integration tests for the {@link EventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventResourceIT {

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ABREVIATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVENTDATE_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENTDATE_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EVENTDATE_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_EVENTDATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENTDATE_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EVENTDATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_EVENT_PRINTING_MODEL_ID = 1L;
    private static final Long UPDATED_EVENT_PRINTING_MODEL_ID = 2L;
    private static final Long SMALLER_EVENT_PRINTING_MODEL_ID = 1L - 1L;

    private static final byte[] DEFAULT_EVENT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_LOGO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_EVENT_BANNER_CENTER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_BANNER_CENTER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_BANNER_CENTER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_BANNER_CENTER_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_EVENT_BANNER_RIGHT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_BANNER_RIGHT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_BANNER_RIGHT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_BANNER_RIGHT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_EVENT_BANNER_LEFT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_BANNER_LEFT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_BANNER_LEFT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_BANNER_LEFT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_EVENT_BANNER_BAS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_BANNER_BAS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_BANNER_BAS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_EVENT_WITH_PHOTO = false;
    private static final Boolean UPDATED_EVENT_WITH_PHOTO = true;

    private static final Boolean DEFAULT_EVENT_NO_CODE = false;
    private static final Boolean UPDATED_EVENT_NO_CODE = true;

    private static final Boolean DEFAULT_EVENT_CODE_NO_FILTER = false;
    private static final Boolean UPDATED_EVENT_CODE_NO_FILTER = true;

    private static final Boolean DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION = false;
    private static final Boolean UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION = true;

    private static final Boolean DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE = false;
    private static final Boolean UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE = true;

    private static final Boolean DEFAULT_EVENT_CODE_BY_TYPE_FONCTION = false;
    private static final Boolean UPDATED_EVENT_CODE_BY_TYPE_FONCTION = true;

    private static final Boolean DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ = false;
    private static final Boolean UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ = true;

    private static final Boolean DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE = false;
    private static final Boolean UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE = true;

    private static final Boolean DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE = false;
    private static final Boolean UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE = true;

    private static final Boolean DEFAULT_EVENT_IMPORTED = false;
    private static final Boolean UPDATED_EVENT_IMPORTED = true;

    private static final Boolean DEFAULT_EVENT_ARCHIVED = false;
    private static final Boolean UPDATED_EVENT_ARCHIVED = true;

    private static final String DEFAULT_EVENT_ARCHIVE_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ARCHIVE_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_SOUS_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_SOUS_DOMAINE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EVENT_CLONED = false;
    private static final Boolean UPDATED_EVENT_CLONED = true;

    private static final String DEFAULT_EVENT_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EVENT_STAT = false;
    private static final Boolean UPDATED_EVENT_STAT = true;

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{eventId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventMockMvc;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .eventName(DEFAULT_EVENT_NAME)
            .eventColor(DEFAULT_EVENT_COLOR)
            .eventDescription(DEFAULT_EVENT_DESCRIPTION)
            .eventAbreviation(DEFAULT_EVENT_ABREVIATION)
            .eventdateStart(DEFAULT_EVENTDATE_START)
            .eventdateEnd(DEFAULT_EVENTDATE_END)
            .eventPrintingModelId(DEFAULT_EVENT_PRINTING_MODEL_ID)
            .eventLogo(DEFAULT_EVENT_LOGO)
            .eventLogoContentType(DEFAULT_EVENT_LOGO_CONTENT_TYPE)
            .eventBannerCenter(DEFAULT_EVENT_BANNER_CENTER)
            .eventBannerCenterContentType(DEFAULT_EVENT_BANNER_CENTER_CONTENT_TYPE)
            .eventBannerRight(DEFAULT_EVENT_BANNER_RIGHT)
            .eventBannerRightContentType(DEFAULT_EVENT_BANNER_RIGHT_CONTENT_TYPE)
            .eventBannerLeft(DEFAULT_EVENT_BANNER_LEFT)
            .eventBannerLeftContentType(DEFAULT_EVENT_BANNER_LEFT_CONTENT_TYPE)
            .eventBannerBas(DEFAULT_EVENT_BANNER_BAS)
            .eventBannerBasContentType(DEFAULT_EVENT_BANNER_BAS_CONTENT_TYPE)
            .eventWithPhoto(DEFAULT_EVENT_WITH_PHOTO)
            .eventNoCode(DEFAULT_EVENT_NO_CODE)
            .eventCodeNoFilter(DEFAULT_EVENT_CODE_NO_FILTER)
            .eventCodeByTypeAccreditation(DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION)
            .eventCodeByTypeCategorie(DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE)
            .eventCodeByTypeFonction(DEFAULT_EVENT_CODE_BY_TYPE_FONCTION)
            .eventCodeByTypeOrganiz(DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ)
            .eventDefaultPrintingLanguage(DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE)
            .eventPCenterPrintingLanguage(DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE)
            .eventImported(DEFAULT_EVENT_IMPORTED)
            .eventArchived(DEFAULT_EVENT_ARCHIVED)
            .eventArchiveFileName(DEFAULT_EVENT_ARCHIVE_FILE_NAME)
            .eventURL(DEFAULT_EVENT_URL)
            .eventDomaine(DEFAULT_EVENT_DOMAINE)
            .eventSousDomaine(DEFAULT_EVENT_SOUS_DOMAINE)
            .eventCloned(DEFAULT_EVENT_CLONED)
            .eventParams(DEFAULT_EVENT_PARAMS)
            .eventAttributs(DEFAULT_EVENT_ATTRIBUTS)
            .eventStat(DEFAULT_EVENT_STAT);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .eventName(UPDATED_EVENT_NAME)
            .eventColor(UPDATED_EVENT_COLOR)
            .eventDescription(UPDATED_EVENT_DESCRIPTION)
            .eventAbreviation(UPDATED_EVENT_ABREVIATION)
            .eventdateStart(UPDATED_EVENTDATE_START)
            .eventdateEnd(UPDATED_EVENTDATE_END)
            .eventPrintingModelId(UPDATED_EVENT_PRINTING_MODEL_ID)
            .eventLogo(UPDATED_EVENT_LOGO)
            .eventLogoContentType(UPDATED_EVENT_LOGO_CONTENT_TYPE)
            .eventBannerCenter(UPDATED_EVENT_BANNER_CENTER)
            .eventBannerCenterContentType(UPDATED_EVENT_BANNER_CENTER_CONTENT_TYPE)
            .eventBannerRight(UPDATED_EVENT_BANNER_RIGHT)
            .eventBannerRightContentType(UPDATED_EVENT_BANNER_RIGHT_CONTENT_TYPE)
            .eventBannerLeft(UPDATED_EVENT_BANNER_LEFT)
            .eventBannerLeftContentType(UPDATED_EVENT_BANNER_LEFT_CONTENT_TYPE)
            .eventBannerBas(UPDATED_EVENT_BANNER_BAS)
            .eventBannerBasContentType(UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE)
            .eventWithPhoto(UPDATED_EVENT_WITH_PHOTO)
            .eventNoCode(UPDATED_EVENT_NO_CODE)
            .eventCodeNoFilter(UPDATED_EVENT_CODE_NO_FILTER)
            .eventCodeByTypeAccreditation(UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION)
            .eventCodeByTypeCategorie(UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE)
            .eventCodeByTypeFonction(UPDATED_EVENT_CODE_BY_TYPE_FONCTION)
            .eventCodeByTypeOrganiz(UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ)
            .eventDefaultPrintingLanguage(UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE)
            .eventPCenterPrintingLanguage(UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE)
            .eventImported(UPDATED_EVENT_IMPORTED)
            .eventArchived(UPDATED_EVENT_ARCHIVED)
            .eventArchiveFileName(UPDATED_EVENT_ARCHIVE_FILE_NAME)
            .eventURL(UPDATED_EVENT_URL)
            .eventDomaine(UPDATED_EVENT_DOMAINE)
            .eventSousDomaine(UPDATED_EVENT_SOUS_DOMAINE)
            .eventCloned(UPDATED_EVENT_CLONED)
            .eventParams(UPDATED_EVENT_PARAMS)
            .eventAttributs(UPDATED_EVENT_ATTRIBUTS)
            .eventStat(UPDATED_EVENT_STAT);
        return event;
    }

    @BeforeEach
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testEvent.getEventColor()).isEqualTo(DEFAULT_EVENT_COLOR);
        assertThat(testEvent.getEventDescription()).isEqualTo(DEFAULT_EVENT_DESCRIPTION);
        assertThat(testEvent.getEventAbreviation()).isEqualTo(DEFAULT_EVENT_ABREVIATION);
        assertThat(testEvent.getEventdateStart()).isEqualTo(DEFAULT_EVENTDATE_START);
        assertThat(testEvent.getEventdateEnd()).isEqualTo(DEFAULT_EVENTDATE_END);
        assertThat(testEvent.getEventPrintingModelId()).isEqualTo(DEFAULT_EVENT_PRINTING_MODEL_ID);
        assertThat(testEvent.getEventLogo()).isEqualTo(DEFAULT_EVENT_LOGO);
        assertThat(testEvent.getEventLogoContentType()).isEqualTo(DEFAULT_EVENT_LOGO_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerCenter()).isEqualTo(DEFAULT_EVENT_BANNER_CENTER);
        assertThat(testEvent.getEventBannerCenterContentType()).isEqualTo(DEFAULT_EVENT_BANNER_CENTER_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerRight()).isEqualTo(DEFAULT_EVENT_BANNER_RIGHT);
        assertThat(testEvent.getEventBannerRightContentType()).isEqualTo(DEFAULT_EVENT_BANNER_RIGHT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerLeft()).isEqualTo(DEFAULT_EVENT_BANNER_LEFT);
        assertThat(testEvent.getEventBannerLeftContentType()).isEqualTo(DEFAULT_EVENT_BANNER_LEFT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerBas()).isEqualTo(DEFAULT_EVENT_BANNER_BAS);
        assertThat(testEvent.getEventBannerBasContentType()).isEqualTo(DEFAULT_EVENT_BANNER_BAS_CONTENT_TYPE);
        assertThat(testEvent.getEventWithPhoto()).isEqualTo(DEFAULT_EVENT_WITH_PHOTO);
        assertThat(testEvent.getEventNoCode()).isEqualTo(DEFAULT_EVENT_NO_CODE);
        assertThat(testEvent.getEventCodeNoFilter()).isEqualTo(DEFAULT_EVENT_CODE_NO_FILTER);
        assertThat(testEvent.getEventCodeByTypeAccreditation()).isEqualTo(DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION);
        assertThat(testEvent.getEventCodeByTypeCategorie()).isEqualTo(DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE);
        assertThat(testEvent.getEventCodeByTypeFonction()).isEqualTo(DEFAULT_EVENT_CODE_BY_TYPE_FONCTION);
        assertThat(testEvent.getEventCodeByTypeOrganiz()).isEqualTo(DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ);
        assertThat(testEvent.getEventDefaultPrintingLanguage()).isEqualTo(DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventPCenterPrintingLanguage()).isEqualTo(DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventImported()).isEqualTo(DEFAULT_EVENT_IMPORTED);
        assertThat(testEvent.getEventArchived()).isEqualTo(DEFAULT_EVENT_ARCHIVED);
        assertThat(testEvent.getEventArchiveFileName()).isEqualTo(DEFAULT_EVENT_ARCHIVE_FILE_NAME);
        assertThat(testEvent.getEventURL()).isEqualTo(DEFAULT_EVENT_URL);
        assertThat(testEvent.getEventDomaine()).isEqualTo(DEFAULT_EVENT_DOMAINE);
        assertThat(testEvent.getEventSousDomaine()).isEqualTo(DEFAULT_EVENT_SOUS_DOMAINE);
        assertThat(testEvent.getEventCloned()).isEqualTo(DEFAULT_EVENT_CLONED);
        assertThat(testEvent.getEventParams()).isEqualTo(DEFAULT_EVENT_PARAMS);
        assertThat(testEvent.getEventAttributs()).isEqualTo(DEFAULT_EVENT_ATTRIBUTS);
        assertThat(testEvent.getEventStat()).isEqualTo(DEFAULT_EVENT_STAT);
    }

    @Test
    @Transactional
    void createEventWithExistingId() throws Exception {
        // Create the Event with an existing ID
        event.setEventId(1L);
        EventDTO eventDTO = eventMapper.toDto(event);

        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEventNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setEventName(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEventdateStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setEventdateStart(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEventdateEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setEventdateEnd(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=eventId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(event.getEventId().intValue())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].eventColor").value(hasItem(DEFAULT_EVENT_COLOR)))
            .andExpect(jsonPath("$.[*].eventDescription").value(hasItem(DEFAULT_EVENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].eventAbreviation").value(hasItem(DEFAULT_EVENT_ABREVIATION)))
            .andExpect(jsonPath("$.[*].eventdateStart").value(hasItem(sameInstant(DEFAULT_EVENTDATE_START))))
            .andExpect(jsonPath("$.[*].eventdateEnd").value(hasItem(sameInstant(DEFAULT_EVENTDATE_END))))
            .andExpect(jsonPath("$.[*].eventPrintingModelId").value(hasItem(DEFAULT_EVENT_PRINTING_MODEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].eventLogoContentType").value(hasItem(DEFAULT_EVENT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_LOGO))))
            .andExpect(jsonPath("$.[*].eventBannerCenterContentType").value(hasItem(DEFAULT_EVENT_BANNER_CENTER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerCenter").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_CENTER))))
            .andExpect(jsonPath("$.[*].eventBannerRightContentType").value(hasItem(DEFAULT_EVENT_BANNER_RIGHT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerRight").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_RIGHT))))
            .andExpect(jsonPath("$.[*].eventBannerLeftContentType").value(hasItem(DEFAULT_EVENT_BANNER_LEFT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerLeft").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_LEFT))))
            .andExpect(jsonPath("$.[*].eventBannerBasContentType").value(hasItem(DEFAULT_EVENT_BANNER_BAS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerBas").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_BAS))))
            .andExpect(jsonPath("$.[*].eventWithPhoto").value(hasItem(DEFAULT_EVENT_WITH_PHOTO.booleanValue())))
            .andExpect(jsonPath("$.[*].eventNoCode").value(hasItem(DEFAULT_EVENT_NO_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].eventCodeNoFilter").value(hasItem(DEFAULT_EVENT_CODE_NO_FILTER.booleanValue())))
            .andExpect(
                jsonPath("$.[*].eventCodeByTypeAccreditation").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].eventCodeByTypeCategorie").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE.booleanValue())))
            .andExpect(jsonPath("$.[*].eventCodeByTypeFonction").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_FONCTION.booleanValue())))
            .andExpect(jsonPath("$.[*].eventCodeByTypeOrganiz").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ.booleanValue())))
            .andExpect(
                jsonPath("$.[*].eventDefaultPrintingLanguage").value(hasItem(DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].eventPCenterPrintingLanguage").value(hasItem(DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].eventImported").value(hasItem(DEFAULT_EVENT_IMPORTED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventArchived").value(hasItem(DEFAULT_EVENT_ARCHIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventArchiveFileName").value(hasItem(DEFAULT_EVENT_ARCHIVE_FILE_NAME)))
            .andExpect(jsonPath("$.[*].eventURL").value(hasItem(DEFAULT_EVENT_URL)))
            .andExpect(jsonPath("$.[*].eventDomaine").value(hasItem(DEFAULT_EVENT_DOMAINE)))
            .andExpect(jsonPath("$.[*].eventSousDomaine").value(hasItem(DEFAULT_EVENT_SOUS_DOMAINE)))
            .andExpect(jsonPath("$.[*].eventCloned").value(hasItem(DEFAULT_EVENT_CLONED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventParams").value(hasItem(DEFAULT_EVENT_PARAMS)))
            .andExpect(jsonPath("$.[*].eventAttributs").value(hasItem(DEFAULT_EVENT_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].eventStat").value(hasItem(DEFAULT_EVENT_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc
            .perform(get(ENTITY_API_URL_ID, event.getEventId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.eventId").value(event.getEventId().intValue()))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME))
            .andExpect(jsonPath("$.eventColor").value(DEFAULT_EVENT_COLOR))
            .andExpect(jsonPath("$.eventDescription").value(DEFAULT_EVENT_DESCRIPTION))
            .andExpect(jsonPath("$.eventAbreviation").value(DEFAULT_EVENT_ABREVIATION))
            .andExpect(jsonPath("$.eventdateStart").value(sameInstant(DEFAULT_EVENTDATE_START)))
            .andExpect(jsonPath("$.eventdateEnd").value(sameInstant(DEFAULT_EVENTDATE_END)))
            .andExpect(jsonPath("$.eventPrintingModelId").value(DEFAULT_EVENT_PRINTING_MODEL_ID.intValue()))
            .andExpect(jsonPath("$.eventLogoContentType").value(DEFAULT_EVENT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventLogo").value(Base64Utils.encodeToString(DEFAULT_EVENT_LOGO)))
            .andExpect(jsonPath("$.eventBannerCenterContentType").value(DEFAULT_EVENT_BANNER_CENTER_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventBannerCenter").value(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_CENTER)))
            .andExpect(jsonPath("$.eventBannerRightContentType").value(DEFAULT_EVENT_BANNER_RIGHT_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventBannerRight").value(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_RIGHT)))
            .andExpect(jsonPath("$.eventBannerLeftContentType").value(DEFAULT_EVENT_BANNER_LEFT_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventBannerLeft").value(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_LEFT)))
            .andExpect(jsonPath("$.eventBannerBasContentType").value(DEFAULT_EVENT_BANNER_BAS_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventBannerBas").value(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_BAS)))
            .andExpect(jsonPath("$.eventWithPhoto").value(DEFAULT_EVENT_WITH_PHOTO.booleanValue()))
            .andExpect(jsonPath("$.eventNoCode").value(DEFAULT_EVENT_NO_CODE.booleanValue()))
            .andExpect(jsonPath("$.eventCodeNoFilter").value(DEFAULT_EVENT_CODE_NO_FILTER.booleanValue()))
            .andExpect(jsonPath("$.eventCodeByTypeAccreditation").value(DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION.booleanValue()))
            .andExpect(jsonPath("$.eventCodeByTypeCategorie").value(DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE.booleanValue()))
            .andExpect(jsonPath("$.eventCodeByTypeFonction").value(DEFAULT_EVENT_CODE_BY_TYPE_FONCTION.booleanValue()))
            .andExpect(jsonPath("$.eventCodeByTypeOrganiz").value(DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ.booleanValue()))
            .andExpect(jsonPath("$.eventDefaultPrintingLanguage").value(DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE.booleanValue()))
            .andExpect(jsonPath("$.eventPCenterPrintingLanguage").value(DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE.booleanValue()))
            .andExpect(jsonPath("$.eventImported").value(DEFAULT_EVENT_IMPORTED.booleanValue()))
            .andExpect(jsonPath("$.eventArchived").value(DEFAULT_EVENT_ARCHIVED.booleanValue()))
            .andExpect(jsonPath("$.eventArchiveFileName").value(DEFAULT_EVENT_ARCHIVE_FILE_NAME))
            .andExpect(jsonPath("$.eventURL").value(DEFAULT_EVENT_URL))
            .andExpect(jsonPath("$.eventDomaine").value(DEFAULT_EVENT_DOMAINE))
            .andExpect(jsonPath("$.eventSousDomaine").value(DEFAULT_EVENT_SOUS_DOMAINE))
            .andExpect(jsonPath("$.eventCloned").value(DEFAULT_EVENT_CLONED.booleanValue()))
            .andExpect(jsonPath("$.eventParams").value(DEFAULT_EVENT_PARAMS))
            .andExpect(jsonPath("$.eventAttributs").value(DEFAULT_EVENT_ATTRIBUTS))
            .andExpect(jsonPath("$.eventStat").value(DEFAULT_EVENT_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getEventsByIdFiltering() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        Long id = event.getEventId();

        defaultEventShouldBeFound("eventId.equals=" + id);
        defaultEventShouldNotBeFound("eventId.notEquals=" + id);

        defaultEventShouldBeFound("eventId.greaterThanOrEqual=" + id);
        defaultEventShouldNotBeFound("eventId.greaterThan=" + id);

        defaultEventShouldBeFound("eventId.lessThanOrEqual=" + id);
        defaultEventShouldNotBeFound("eventId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventsByEventNameIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventName equals to DEFAULT_EVENT_NAME
        defaultEventShouldBeFound("eventName.equals=" + DEFAULT_EVENT_NAME);

        // Get all the eventList where eventName equals to UPDATED_EVENT_NAME
        defaultEventShouldNotBeFound("eventName.equals=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventNameIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventName in DEFAULT_EVENT_NAME or UPDATED_EVENT_NAME
        defaultEventShouldBeFound("eventName.in=" + DEFAULT_EVENT_NAME + "," + UPDATED_EVENT_NAME);

        // Get all the eventList where eventName equals to UPDATED_EVENT_NAME
        defaultEventShouldNotBeFound("eventName.in=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventName is not null
        defaultEventShouldBeFound("eventName.specified=true");

        // Get all the eventList where eventName is null
        defaultEventShouldNotBeFound("eventName.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventNameContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventName contains DEFAULT_EVENT_NAME
        defaultEventShouldBeFound("eventName.contains=" + DEFAULT_EVENT_NAME);

        // Get all the eventList where eventName contains UPDATED_EVENT_NAME
        defaultEventShouldNotBeFound("eventName.contains=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventNameNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventName does not contain DEFAULT_EVENT_NAME
        defaultEventShouldNotBeFound("eventName.doesNotContain=" + DEFAULT_EVENT_NAME);

        // Get all the eventList where eventName does not contain UPDATED_EVENT_NAME
        defaultEventShouldBeFound("eventName.doesNotContain=" + UPDATED_EVENT_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventColorIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventColor equals to DEFAULT_EVENT_COLOR
        defaultEventShouldBeFound("eventColor.equals=" + DEFAULT_EVENT_COLOR);

        // Get all the eventList where eventColor equals to UPDATED_EVENT_COLOR
        defaultEventShouldNotBeFound("eventColor.equals=" + UPDATED_EVENT_COLOR);
    }

    @Test
    @Transactional
    void getAllEventsByEventColorIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventColor in DEFAULT_EVENT_COLOR or UPDATED_EVENT_COLOR
        defaultEventShouldBeFound("eventColor.in=" + DEFAULT_EVENT_COLOR + "," + UPDATED_EVENT_COLOR);

        // Get all the eventList where eventColor equals to UPDATED_EVENT_COLOR
        defaultEventShouldNotBeFound("eventColor.in=" + UPDATED_EVENT_COLOR);
    }

    @Test
    @Transactional
    void getAllEventsByEventColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventColor is not null
        defaultEventShouldBeFound("eventColor.specified=true");

        // Get all the eventList where eventColor is null
        defaultEventShouldNotBeFound("eventColor.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventColorContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventColor contains DEFAULT_EVENT_COLOR
        defaultEventShouldBeFound("eventColor.contains=" + DEFAULT_EVENT_COLOR);

        // Get all the eventList where eventColor contains UPDATED_EVENT_COLOR
        defaultEventShouldNotBeFound("eventColor.contains=" + UPDATED_EVENT_COLOR);
    }

    @Test
    @Transactional
    void getAllEventsByEventColorNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventColor does not contain DEFAULT_EVENT_COLOR
        defaultEventShouldNotBeFound("eventColor.doesNotContain=" + DEFAULT_EVENT_COLOR);

        // Get all the eventList where eventColor does not contain UPDATED_EVENT_COLOR
        defaultEventShouldBeFound("eventColor.doesNotContain=" + UPDATED_EVENT_COLOR);
    }

    @Test
    @Transactional
    void getAllEventsByEventDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDescription equals to DEFAULT_EVENT_DESCRIPTION
        defaultEventShouldBeFound("eventDescription.equals=" + DEFAULT_EVENT_DESCRIPTION);

        // Get all the eventList where eventDescription equals to UPDATED_EVENT_DESCRIPTION
        defaultEventShouldNotBeFound("eventDescription.equals=" + UPDATED_EVENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByEventDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDescription in DEFAULT_EVENT_DESCRIPTION or UPDATED_EVENT_DESCRIPTION
        defaultEventShouldBeFound("eventDescription.in=" + DEFAULT_EVENT_DESCRIPTION + "," + UPDATED_EVENT_DESCRIPTION);

        // Get all the eventList where eventDescription equals to UPDATED_EVENT_DESCRIPTION
        defaultEventShouldNotBeFound("eventDescription.in=" + UPDATED_EVENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByEventDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDescription is not null
        defaultEventShouldBeFound("eventDescription.specified=true");

        // Get all the eventList where eventDescription is null
        defaultEventShouldNotBeFound("eventDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDescription contains DEFAULT_EVENT_DESCRIPTION
        defaultEventShouldBeFound("eventDescription.contains=" + DEFAULT_EVENT_DESCRIPTION);

        // Get all the eventList where eventDescription contains UPDATED_EVENT_DESCRIPTION
        defaultEventShouldNotBeFound("eventDescription.contains=" + UPDATED_EVENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByEventDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDescription does not contain DEFAULT_EVENT_DESCRIPTION
        defaultEventShouldNotBeFound("eventDescription.doesNotContain=" + DEFAULT_EVENT_DESCRIPTION);

        // Get all the eventList where eventDescription does not contain UPDATED_EVENT_DESCRIPTION
        defaultEventShouldBeFound("eventDescription.doesNotContain=" + UPDATED_EVENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByEventAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAbreviation equals to DEFAULT_EVENT_ABREVIATION
        defaultEventShouldBeFound("eventAbreviation.equals=" + DEFAULT_EVENT_ABREVIATION);

        // Get all the eventList where eventAbreviation equals to UPDATED_EVENT_ABREVIATION
        defaultEventShouldNotBeFound("eventAbreviation.equals=" + UPDATED_EVENT_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllEventsByEventAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAbreviation in DEFAULT_EVENT_ABREVIATION or UPDATED_EVENT_ABREVIATION
        defaultEventShouldBeFound("eventAbreviation.in=" + DEFAULT_EVENT_ABREVIATION + "," + UPDATED_EVENT_ABREVIATION);

        // Get all the eventList where eventAbreviation equals to UPDATED_EVENT_ABREVIATION
        defaultEventShouldNotBeFound("eventAbreviation.in=" + UPDATED_EVENT_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllEventsByEventAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAbreviation is not null
        defaultEventShouldBeFound("eventAbreviation.specified=true");

        // Get all the eventList where eventAbreviation is null
        defaultEventShouldNotBeFound("eventAbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventAbreviationContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAbreviation contains DEFAULT_EVENT_ABREVIATION
        defaultEventShouldBeFound("eventAbreviation.contains=" + DEFAULT_EVENT_ABREVIATION);

        // Get all the eventList where eventAbreviation contains UPDATED_EVENT_ABREVIATION
        defaultEventShouldNotBeFound("eventAbreviation.contains=" + UPDATED_EVENT_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllEventsByEventAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAbreviation does not contain DEFAULT_EVENT_ABREVIATION
        defaultEventShouldNotBeFound("eventAbreviation.doesNotContain=" + DEFAULT_EVENT_ABREVIATION);

        // Get all the eventList where eventAbreviation does not contain UPDATED_EVENT_ABREVIATION
        defaultEventShouldBeFound("eventAbreviation.doesNotContain=" + UPDATED_EVENT_ABREVIATION);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateStartIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateStart equals to DEFAULT_EVENTDATE_START
        defaultEventShouldBeFound("eventdateStart.equals=" + DEFAULT_EVENTDATE_START);

        // Get all the eventList where eventdateStart equals to UPDATED_EVENTDATE_START
        defaultEventShouldNotBeFound("eventdateStart.equals=" + UPDATED_EVENTDATE_START);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateStartIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateStart in DEFAULT_EVENTDATE_START or UPDATED_EVENTDATE_START
        defaultEventShouldBeFound("eventdateStart.in=" + DEFAULT_EVENTDATE_START + "," + UPDATED_EVENTDATE_START);

        // Get all the eventList where eventdateStart equals to UPDATED_EVENTDATE_START
        defaultEventShouldNotBeFound("eventdateStart.in=" + UPDATED_EVENTDATE_START);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateStart is not null
        defaultEventShouldBeFound("eventdateStart.specified=true");

        // Get all the eventList where eventdateStart is null
        defaultEventShouldNotBeFound("eventdateStart.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventdateStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateStart is greater than or equal to DEFAULT_EVENTDATE_START
        defaultEventShouldBeFound("eventdateStart.greaterThanOrEqual=" + DEFAULT_EVENTDATE_START);

        // Get all the eventList where eventdateStart is greater than or equal to UPDATED_EVENTDATE_START
        defaultEventShouldNotBeFound("eventdateStart.greaterThanOrEqual=" + UPDATED_EVENTDATE_START);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateStart is less than or equal to DEFAULT_EVENTDATE_START
        defaultEventShouldBeFound("eventdateStart.lessThanOrEqual=" + DEFAULT_EVENTDATE_START);

        // Get all the eventList where eventdateStart is less than or equal to SMALLER_EVENTDATE_START
        defaultEventShouldNotBeFound("eventdateStart.lessThanOrEqual=" + SMALLER_EVENTDATE_START);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateStartIsLessThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateStart is less than DEFAULT_EVENTDATE_START
        defaultEventShouldNotBeFound("eventdateStart.lessThan=" + DEFAULT_EVENTDATE_START);

        // Get all the eventList where eventdateStart is less than UPDATED_EVENTDATE_START
        defaultEventShouldBeFound("eventdateStart.lessThan=" + UPDATED_EVENTDATE_START);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateStart is greater than DEFAULT_EVENTDATE_START
        defaultEventShouldNotBeFound("eventdateStart.greaterThan=" + DEFAULT_EVENTDATE_START);

        // Get all the eventList where eventdateStart is greater than SMALLER_EVENTDATE_START
        defaultEventShouldBeFound("eventdateStart.greaterThan=" + SMALLER_EVENTDATE_START);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateEndIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateEnd equals to DEFAULT_EVENTDATE_END
        defaultEventShouldBeFound("eventdateEnd.equals=" + DEFAULT_EVENTDATE_END);

        // Get all the eventList where eventdateEnd equals to UPDATED_EVENTDATE_END
        defaultEventShouldNotBeFound("eventdateEnd.equals=" + UPDATED_EVENTDATE_END);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateEndIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateEnd in DEFAULT_EVENTDATE_END or UPDATED_EVENTDATE_END
        defaultEventShouldBeFound("eventdateEnd.in=" + DEFAULT_EVENTDATE_END + "," + UPDATED_EVENTDATE_END);

        // Get all the eventList where eventdateEnd equals to UPDATED_EVENTDATE_END
        defaultEventShouldNotBeFound("eventdateEnd.in=" + UPDATED_EVENTDATE_END);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateEnd is not null
        defaultEventShouldBeFound("eventdateEnd.specified=true");

        // Get all the eventList where eventdateEnd is null
        defaultEventShouldNotBeFound("eventdateEnd.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventdateEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateEnd is greater than or equal to DEFAULT_EVENTDATE_END
        defaultEventShouldBeFound("eventdateEnd.greaterThanOrEqual=" + DEFAULT_EVENTDATE_END);

        // Get all the eventList where eventdateEnd is greater than or equal to UPDATED_EVENTDATE_END
        defaultEventShouldNotBeFound("eventdateEnd.greaterThanOrEqual=" + UPDATED_EVENTDATE_END);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateEnd is less than or equal to DEFAULT_EVENTDATE_END
        defaultEventShouldBeFound("eventdateEnd.lessThanOrEqual=" + DEFAULT_EVENTDATE_END);

        // Get all the eventList where eventdateEnd is less than or equal to SMALLER_EVENTDATE_END
        defaultEventShouldNotBeFound("eventdateEnd.lessThanOrEqual=" + SMALLER_EVENTDATE_END);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateEndIsLessThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateEnd is less than DEFAULT_EVENTDATE_END
        defaultEventShouldNotBeFound("eventdateEnd.lessThan=" + DEFAULT_EVENTDATE_END);

        // Get all the eventList where eventdateEnd is less than UPDATED_EVENTDATE_END
        defaultEventShouldBeFound("eventdateEnd.lessThan=" + UPDATED_EVENTDATE_END);
    }

    @Test
    @Transactional
    void getAllEventsByEventdateEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventdateEnd is greater than DEFAULT_EVENTDATE_END
        defaultEventShouldNotBeFound("eventdateEnd.greaterThan=" + DEFAULT_EVENTDATE_END);

        // Get all the eventList where eventdateEnd is greater than SMALLER_EVENTDATE_END
        defaultEventShouldBeFound("eventdateEnd.greaterThan=" + SMALLER_EVENTDATE_END);
    }

    @Test
    @Transactional
    void getAllEventsByEventPrintingModelIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPrintingModelId equals to DEFAULT_EVENT_PRINTING_MODEL_ID
        defaultEventShouldBeFound("eventPrintingModelId.equals=" + DEFAULT_EVENT_PRINTING_MODEL_ID);

        // Get all the eventList where eventPrintingModelId equals to UPDATED_EVENT_PRINTING_MODEL_ID
        defaultEventShouldNotBeFound("eventPrintingModelId.equals=" + UPDATED_EVENT_PRINTING_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventsByEventPrintingModelIdIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPrintingModelId in DEFAULT_EVENT_PRINTING_MODEL_ID or UPDATED_EVENT_PRINTING_MODEL_ID
        defaultEventShouldBeFound("eventPrintingModelId.in=" + DEFAULT_EVENT_PRINTING_MODEL_ID + "," + UPDATED_EVENT_PRINTING_MODEL_ID);

        // Get all the eventList where eventPrintingModelId equals to UPDATED_EVENT_PRINTING_MODEL_ID
        defaultEventShouldNotBeFound("eventPrintingModelId.in=" + UPDATED_EVENT_PRINTING_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventsByEventPrintingModelIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPrintingModelId is not null
        defaultEventShouldBeFound("eventPrintingModelId.specified=true");

        // Get all the eventList where eventPrintingModelId is null
        defaultEventShouldNotBeFound("eventPrintingModelId.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventPrintingModelIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPrintingModelId is greater than or equal to DEFAULT_EVENT_PRINTING_MODEL_ID
        defaultEventShouldBeFound("eventPrintingModelId.greaterThanOrEqual=" + DEFAULT_EVENT_PRINTING_MODEL_ID);

        // Get all the eventList where eventPrintingModelId is greater than or equal to UPDATED_EVENT_PRINTING_MODEL_ID
        defaultEventShouldNotBeFound("eventPrintingModelId.greaterThanOrEqual=" + UPDATED_EVENT_PRINTING_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventsByEventPrintingModelIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPrintingModelId is less than or equal to DEFAULT_EVENT_PRINTING_MODEL_ID
        defaultEventShouldBeFound("eventPrintingModelId.lessThanOrEqual=" + DEFAULT_EVENT_PRINTING_MODEL_ID);

        // Get all the eventList where eventPrintingModelId is less than or equal to SMALLER_EVENT_PRINTING_MODEL_ID
        defaultEventShouldNotBeFound("eventPrintingModelId.lessThanOrEqual=" + SMALLER_EVENT_PRINTING_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventsByEventPrintingModelIdIsLessThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPrintingModelId is less than DEFAULT_EVENT_PRINTING_MODEL_ID
        defaultEventShouldNotBeFound("eventPrintingModelId.lessThan=" + DEFAULT_EVENT_PRINTING_MODEL_ID);

        // Get all the eventList where eventPrintingModelId is less than UPDATED_EVENT_PRINTING_MODEL_ID
        defaultEventShouldBeFound("eventPrintingModelId.lessThan=" + UPDATED_EVENT_PRINTING_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventsByEventPrintingModelIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPrintingModelId is greater than DEFAULT_EVENT_PRINTING_MODEL_ID
        defaultEventShouldNotBeFound("eventPrintingModelId.greaterThan=" + DEFAULT_EVENT_PRINTING_MODEL_ID);

        // Get all the eventList where eventPrintingModelId is greater than SMALLER_EVENT_PRINTING_MODEL_ID
        defaultEventShouldBeFound("eventPrintingModelId.greaterThan=" + SMALLER_EVENT_PRINTING_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventsByEventWithPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventWithPhoto equals to DEFAULT_EVENT_WITH_PHOTO
        defaultEventShouldBeFound("eventWithPhoto.equals=" + DEFAULT_EVENT_WITH_PHOTO);

        // Get all the eventList where eventWithPhoto equals to UPDATED_EVENT_WITH_PHOTO
        defaultEventShouldNotBeFound("eventWithPhoto.equals=" + UPDATED_EVENT_WITH_PHOTO);
    }

    @Test
    @Transactional
    void getAllEventsByEventWithPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventWithPhoto in DEFAULT_EVENT_WITH_PHOTO or UPDATED_EVENT_WITH_PHOTO
        defaultEventShouldBeFound("eventWithPhoto.in=" + DEFAULT_EVENT_WITH_PHOTO + "," + UPDATED_EVENT_WITH_PHOTO);

        // Get all the eventList where eventWithPhoto equals to UPDATED_EVENT_WITH_PHOTO
        defaultEventShouldNotBeFound("eventWithPhoto.in=" + UPDATED_EVENT_WITH_PHOTO);
    }

    @Test
    @Transactional
    void getAllEventsByEventWithPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventWithPhoto is not null
        defaultEventShouldBeFound("eventWithPhoto.specified=true");

        // Get all the eventList where eventWithPhoto is null
        defaultEventShouldNotBeFound("eventWithPhoto.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventNoCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventNoCode equals to DEFAULT_EVENT_NO_CODE
        defaultEventShouldBeFound("eventNoCode.equals=" + DEFAULT_EVENT_NO_CODE);

        // Get all the eventList where eventNoCode equals to UPDATED_EVENT_NO_CODE
        defaultEventShouldNotBeFound("eventNoCode.equals=" + UPDATED_EVENT_NO_CODE);
    }

    @Test
    @Transactional
    void getAllEventsByEventNoCodeIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventNoCode in DEFAULT_EVENT_NO_CODE or UPDATED_EVENT_NO_CODE
        defaultEventShouldBeFound("eventNoCode.in=" + DEFAULT_EVENT_NO_CODE + "," + UPDATED_EVENT_NO_CODE);

        // Get all the eventList where eventNoCode equals to UPDATED_EVENT_NO_CODE
        defaultEventShouldNotBeFound("eventNoCode.in=" + UPDATED_EVENT_NO_CODE);
    }

    @Test
    @Transactional
    void getAllEventsByEventNoCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventNoCode is not null
        defaultEventShouldBeFound("eventNoCode.specified=true");

        // Get all the eventList where eventNoCode is null
        defaultEventShouldNotBeFound("eventNoCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeNoFilterIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeNoFilter equals to DEFAULT_EVENT_CODE_NO_FILTER
        defaultEventShouldBeFound("eventCodeNoFilter.equals=" + DEFAULT_EVENT_CODE_NO_FILTER);

        // Get all the eventList where eventCodeNoFilter equals to UPDATED_EVENT_CODE_NO_FILTER
        defaultEventShouldNotBeFound("eventCodeNoFilter.equals=" + UPDATED_EVENT_CODE_NO_FILTER);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeNoFilterIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeNoFilter in DEFAULT_EVENT_CODE_NO_FILTER or UPDATED_EVENT_CODE_NO_FILTER
        defaultEventShouldBeFound("eventCodeNoFilter.in=" + DEFAULT_EVENT_CODE_NO_FILTER + "," + UPDATED_EVENT_CODE_NO_FILTER);

        // Get all the eventList where eventCodeNoFilter equals to UPDATED_EVENT_CODE_NO_FILTER
        defaultEventShouldNotBeFound("eventCodeNoFilter.in=" + UPDATED_EVENT_CODE_NO_FILTER);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeNoFilterIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeNoFilter is not null
        defaultEventShouldBeFound("eventCodeNoFilter.specified=true");

        // Get all the eventList where eventCodeNoFilter is null
        defaultEventShouldNotBeFound("eventCodeNoFilter.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeAccreditationIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeAccreditation equals to DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION
        defaultEventShouldBeFound("eventCodeByTypeAccreditation.equals=" + DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION);

        // Get all the eventList where eventCodeByTypeAccreditation equals to UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION
        defaultEventShouldNotBeFound("eventCodeByTypeAccreditation.equals=" + UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeAccreditationIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeAccreditation in DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION or UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION
        defaultEventShouldBeFound(
            "eventCodeByTypeAccreditation.in=" + DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION + "," + UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION
        );

        // Get all the eventList where eventCodeByTypeAccreditation equals to UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION
        defaultEventShouldNotBeFound("eventCodeByTypeAccreditation.in=" + UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeAccreditationIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeAccreditation is not null
        defaultEventShouldBeFound("eventCodeByTypeAccreditation.specified=true");

        // Get all the eventList where eventCodeByTypeAccreditation is null
        defaultEventShouldNotBeFound("eventCodeByTypeAccreditation.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeCategorie equals to DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE
        defaultEventShouldBeFound("eventCodeByTypeCategorie.equals=" + DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE);

        // Get all the eventList where eventCodeByTypeCategorie equals to UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE
        defaultEventShouldNotBeFound("eventCodeByTypeCategorie.equals=" + UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeCategorieIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeCategorie in DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE or UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE
        defaultEventShouldBeFound(
            "eventCodeByTypeCategorie.in=" + DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE + "," + UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE
        );

        // Get all the eventList where eventCodeByTypeCategorie equals to UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE
        defaultEventShouldNotBeFound("eventCodeByTypeCategorie.in=" + UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeCategorieIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeCategorie is not null
        defaultEventShouldBeFound("eventCodeByTypeCategorie.specified=true");

        // Get all the eventList where eventCodeByTypeCategorie is null
        defaultEventShouldNotBeFound("eventCodeByTypeCategorie.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeFonctionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeFonction equals to DEFAULT_EVENT_CODE_BY_TYPE_FONCTION
        defaultEventShouldBeFound("eventCodeByTypeFonction.equals=" + DEFAULT_EVENT_CODE_BY_TYPE_FONCTION);

        // Get all the eventList where eventCodeByTypeFonction equals to UPDATED_EVENT_CODE_BY_TYPE_FONCTION
        defaultEventShouldNotBeFound("eventCodeByTypeFonction.equals=" + UPDATED_EVENT_CODE_BY_TYPE_FONCTION);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeFonctionIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeFonction in DEFAULT_EVENT_CODE_BY_TYPE_FONCTION or UPDATED_EVENT_CODE_BY_TYPE_FONCTION
        defaultEventShouldBeFound(
            "eventCodeByTypeFonction.in=" + DEFAULT_EVENT_CODE_BY_TYPE_FONCTION + "," + UPDATED_EVENT_CODE_BY_TYPE_FONCTION
        );

        // Get all the eventList where eventCodeByTypeFonction equals to UPDATED_EVENT_CODE_BY_TYPE_FONCTION
        defaultEventShouldNotBeFound("eventCodeByTypeFonction.in=" + UPDATED_EVENT_CODE_BY_TYPE_FONCTION);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeFonctionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeFonction is not null
        defaultEventShouldBeFound("eventCodeByTypeFonction.specified=true");

        // Get all the eventList where eventCodeByTypeFonction is null
        defaultEventShouldNotBeFound("eventCodeByTypeFonction.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeOrganizIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeOrganiz equals to DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ
        defaultEventShouldBeFound("eventCodeByTypeOrganiz.equals=" + DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ);

        // Get all the eventList where eventCodeByTypeOrganiz equals to UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ
        defaultEventShouldNotBeFound("eventCodeByTypeOrganiz.equals=" + UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeOrganizIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeOrganiz in DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ or UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ
        defaultEventShouldBeFound(
            "eventCodeByTypeOrganiz.in=" + DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ + "," + UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ
        );

        // Get all the eventList where eventCodeByTypeOrganiz equals to UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ
        defaultEventShouldNotBeFound("eventCodeByTypeOrganiz.in=" + UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ);
    }

    @Test
    @Transactional
    void getAllEventsByEventCodeByTypeOrganizIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCodeByTypeOrganiz is not null
        defaultEventShouldBeFound("eventCodeByTypeOrganiz.specified=true");

        // Get all the eventList where eventCodeByTypeOrganiz is null
        defaultEventShouldNotBeFound("eventCodeByTypeOrganiz.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventDefaultPrintingLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDefaultPrintingLanguage equals to DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE
        defaultEventShouldBeFound("eventDefaultPrintingLanguage.equals=" + DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE);

        // Get all the eventList where eventDefaultPrintingLanguage equals to UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE
        defaultEventShouldNotBeFound("eventDefaultPrintingLanguage.equals=" + UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllEventsByEventDefaultPrintingLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDefaultPrintingLanguage in DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE or UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE
        defaultEventShouldBeFound(
            "eventDefaultPrintingLanguage.in=" + DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE + "," + UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE
        );

        // Get all the eventList where eventDefaultPrintingLanguage equals to UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE
        defaultEventShouldNotBeFound("eventDefaultPrintingLanguage.in=" + UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllEventsByEventDefaultPrintingLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDefaultPrintingLanguage is not null
        defaultEventShouldBeFound("eventDefaultPrintingLanguage.specified=true");

        // Get all the eventList where eventDefaultPrintingLanguage is null
        defaultEventShouldNotBeFound("eventDefaultPrintingLanguage.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventPCenterPrintingLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPCenterPrintingLanguage equals to DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE
        defaultEventShouldBeFound("eventPCenterPrintingLanguage.equals=" + DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE);

        // Get all the eventList where eventPCenterPrintingLanguage equals to UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE
        defaultEventShouldNotBeFound("eventPCenterPrintingLanguage.equals=" + UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllEventsByEventPCenterPrintingLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPCenterPrintingLanguage in DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE or UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE
        defaultEventShouldBeFound(
            "eventPCenterPrintingLanguage.in=" + DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE + "," + UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE
        );

        // Get all the eventList where eventPCenterPrintingLanguage equals to UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE
        defaultEventShouldNotBeFound("eventPCenterPrintingLanguage.in=" + UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllEventsByEventPCenterPrintingLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventPCenterPrintingLanguage is not null
        defaultEventShouldBeFound("eventPCenterPrintingLanguage.specified=true");

        // Get all the eventList where eventPCenterPrintingLanguage is null
        defaultEventShouldNotBeFound("eventPCenterPrintingLanguage.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventImportedIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventImported equals to DEFAULT_EVENT_IMPORTED
        defaultEventShouldBeFound("eventImported.equals=" + DEFAULT_EVENT_IMPORTED);

        // Get all the eventList where eventImported equals to UPDATED_EVENT_IMPORTED
        defaultEventShouldNotBeFound("eventImported.equals=" + UPDATED_EVENT_IMPORTED);
    }

    @Test
    @Transactional
    void getAllEventsByEventImportedIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventImported in DEFAULT_EVENT_IMPORTED or UPDATED_EVENT_IMPORTED
        defaultEventShouldBeFound("eventImported.in=" + DEFAULT_EVENT_IMPORTED + "," + UPDATED_EVENT_IMPORTED);

        // Get all the eventList where eventImported equals to UPDATED_EVENT_IMPORTED
        defaultEventShouldNotBeFound("eventImported.in=" + UPDATED_EVENT_IMPORTED);
    }

    @Test
    @Transactional
    void getAllEventsByEventImportedIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventImported is not null
        defaultEventShouldBeFound("eventImported.specified=true");

        // Get all the eventList where eventImported is null
        defaultEventShouldNotBeFound("eventImported.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventArchivedIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchived equals to DEFAULT_EVENT_ARCHIVED
        defaultEventShouldBeFound("eventArchived.equals=" + DEFAULT_EVENT_ARCHIVED);

        // Get all the eventList where eventArchived equals to UPDATED_EVENT_ARCHIVED
        defaultEventShouldNotBeFound("eventArchived.equals=" + UPDATED_EVENT_ARCHIVED);
    }

    @Test
    @Transactional
    void getAllEventsByEventArchivedIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchived in DEFAULT_EVENT_ARCHIVED or UPDATED_EVENT_ARCHIVED
        defaultEventShouldBeFound("eventArchived.in=" + DEFAULT_EVENT_ARCHIVED + "," + UPDATED_EVENT_ARCHIVED);

        // Get all the eventList where eventArchived equals to UPDATED_EVENT_ARCHIVED
        defaultEventShouldNotBeFound("eventArchived.in=" + UPDATED_EVENT_ARCHIVED);
    }

    @Test
    @Transactional
    void getAllEventsByEventArchivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchived is not null
        defaultEventShouldBeFound("eventArchived.specified=true");

        // Get all the eventList where eventArchived is null
        defaultEventShouldNotBeFound("eventArchived.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventArchiveFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchiveFileName equals to DEFAULT_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldBeFound("eventArchiveFileName.equals=" + DEFAULT_EVENT_ARCHIVE_FILE_NAME);

        // Get all the eventList where eventArchiveFileName equals to UPDATED_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldNotBeFound("eventArchiveFileName.equals=" + UPDATED_EVENT_ARCHIVE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventArchiveFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchiveFileName in DEFAULT_EVENT_ARCHIVE_FILE_NAME or UPDATED_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldBeFound("eventArchiveFileName.in=" + DEFAULT_EVENT_ARCHIVE_FILE_NAME + "," + UPDATED_EVENT_ARCHIVE_FILE_NAME);

        // Get all the eventList where eventArchiveFileName equals to UPDATED_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldNotBeFound("eventArchiveFileName.in=" + UPDATED_EVENT_ARCHIVE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventArchiveFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchiveFileName is not null
        defaultEventShouldBeFound("eventArchiveFileName.specified=true");

        // Get all the eventList where eventArchiveFileName is null
        defaultEventShouldNotBeFound("eventArchiveFileName.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventArchiveFileNameContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchiveFileName contains DEFAULT_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldBeFound("eventArchiveFileName.contains=" + DEFAULT_EVENT_ARCHIVE_FILE_NAME);

        // Get all the eventList where eventArchiveFileName contains UPDATED_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldNotBeFound("eventArchiveFileName.contains=" + UPDATED_EVENT_ARCHIVE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventArchiveFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventArchiveFileName does not contain DEFAULT_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldNotBeFound("eventArchiveFileName.doesNotContain=" + DEFAULT_EVENT_ARCHIVE_FILE_NAME);

        // Get all the eventList where eventArchiveFileName does not contain UPDATED_EVENT_ARCHIVE_FILE_NAME
        defaultEventShouldBeFound("eventArchiveFileName.doesNotContain=" + UPDATED_EVENT_ARCHIVE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllEventsByEventURLIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventURL equals to DEFAULT_EVENT_URL
        defaultEventShouldBeFound("eventURL.equals=" + DEFAULT_EVENT_URL);

        // Get all the eventList where eventURL equals to UPDATED_EVENT_URL
        defaultEventShouldNotBeFound("eventURL.equals=" + UPDATED_EVENT_URL);
    }

    @Test
    @Transactional
    void getAllEventsByEventURLIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventURL in DEFAULT_EVENT_URL or UPDATED_EVENT_URL
        defaultEventShouldBeFound("eventURL.in=" + DEFAULT_EVENT_URL + "," + UPDATED_EVENT_URL);

        // Get all the eventList where eventURL equals to UPDATED_EVENT_URL
        defaultEventShouldNotBeFound("eventURL.in=" + UPDATED_EVENT_URL);
    }

    @Test
    @Transactional
    void getAllEventsByEventURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventURL is not null
        defaultEventShouldBeFound("eventURL.specified=true");

        // Get all the eventList where eventURL is null
        defaultEventShouldNotBeFound("eventURL.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventURLContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventURL contains DEFAULT_EVENT_URL
        defaultEventShouldBeFound("eventURL.contains=" + DEFAULT_EVENT_URL);

        // Get all the eventList where eventURL contains UPDATED_EVENT_URL
        defaultEventShouldNotBeFound("eventURL.contains=" + UPDATED_EVENT_URL);
    }

    @Test
    @Transactional
    void getAllEventsByEventURLNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventURL does not contain DEFAULT_EVENT_URL
        defaultEventShouldNotBeFound("eventURL.doesNotContain=" + DEFAULT_EVENT_URL);

        // Get all the eventList where eventURL does not contain UPDATED_EVENT_URL
        defaultEventShouldBeFound("eventURL.doesNotContain=" + UPDATED_EVENT_URL);
    }

    @Test
    @Transactional
    void getAllEventsByEventDomaineIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDomaine equals to DEFAULT_EVENT_DOMAINE
        defaultEventShouldBeFound("eventDomaine.equals=" + DEFAULT_EVENT_DOMAINE);

        // Get all the eventList where eventDomaine equals to UPDATED_EVENT_DOMAINE
        defaultEventShouldNotBeFound("eventDomaine.equals=" + UPDATED_EVENT_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventDomaineIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDomaine in DEFAULT_EVENT_DOMAINE or UPDATED_EVENT_DOMAINE
        defaultEventShouldBeFound("eventDomaine.in=" + DEFAULT_EVENT_DOMAINE + "," + UPDATED_EVENT_DOMAINE);

        // Get all the eventList where eventDomaine equals to UPDATED_EVENT_DOMAINE
        defaultEventShouldNotBeFound("eventDomaine.in=" + UPDATED_EVENT_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventDomaineIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDomaine is not null
        defaultEventShouldBeFound("eventDomaine.specified=true");

        // Get all the eventList where eventDomaine is null
        defaultEventShouldNotBeFound("eventDomaine.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventDomaineContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDomaine contains DEFAULT_EVENT_DOMAINE
        defaultEventShouldBeFound("eventDomaine.contains=" + DEFAULT_EVENT_DOMAINE);

        // Get all the eventList where eventDomaine contains UPDATED_EVENT_DOMAINE
        defaultEventShouldNotBeFound("eventDomaine.contains=" + UPDATED_EVENT_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventDomaineNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventDomaine does not contain DEFAULT_EVENT_DOMAINE
        defaultEventShouldNotBeFound("eventDomaine.doesNotContain=" + DEFAULT_EVENT_DOMAINE);

        // Get all the eventList where eventDomaine does not contain UPDATED_EVENT_DOMAINE
        defaultEventShouldBeFound("eventDomaine.doesNotContain=" + UPDATED_EVENT_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventSousDomaineIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventSousDomaine equals to DEFAULT_EVENT_SOUS_DOMAINE
        defaultEventShouldBeFound("eventSousDomaine.equals=" + DEFAULT_EVENT_SOUS_DOMAINE);

        // Get all the eventList where eventSousDomaine equals to UPDATED_EVENT_SOUS_DOMAINE
        defaultEventShouldNotBeFound("eventSousDomaine.equals=" + UPDATED_EVENT_SOUS_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventSousDomaineIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventSousDomaine in DEFAULT_EVENT_SOUS_DOMAINE or UPDATED_EVENT_SOUS_DOMAINE
        defaultEventShouldBeFound("eventSousDomaine.in=" + DEFAULT_EVENT_SOUS_DOMAINE + "," + UPDATED_EVENT_SOUS_DOMAINE);

        // Get all the eventList where eventSousDomaine equals to UPDATED_EVENT_SOUS_DOMAINE
        defaultEventShouldNotBeFound("eventSousDomaine.in=" + UPDATED_EVENT_SOUS_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventSousDomaineIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventSousDomaine is not null
        defaultEventShouldBeFound("eventSousDomaine.specified=true");

        // Get all the eventList where eventSousDomaine is null
        defaultEventShouldNotBeFound("eventSousDomaine.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventSousDomaineContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventSousDomaine contains DEFAULT_EVENT_SOUS_DOMAINE
        defaultEventShouldBeFound("eventSousDomaine.contains=" + DEFAULT_EVENT_SOUS_DOMAINE);

        // Get all the eventList where eventSousDomaine contains UPDATED_EVENT_SOUS_DOMAINE
        defaultEventShouldNotBeFound("eventSousDomaine.contains=" + UPDATED_EVENT_SOUS_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventSousDomaineNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventSousDomaine does not contain DEFAULT_EVENT_SOUS_DOMAINE
        defaultEventShouldNotBeFound("eventSousDomaine.doesNotContain=" + DEFAULT_EVENT_SOUS_DOMAINE);

        // Get all the eventList where eventSousDomaine does not contain UPDATED_EVENT_SOUS_DOMAINE
        defaultEventShouldBeFound("eventSousDomaine.doesNotContain=" + UPDATED_EVENT_SOUS_DOMAINE);
    }

    @Test
    @Transactional
    void getAllEventsByEventClonedIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCloned equals to DEFAULT_EVENT_CLONED
        defaultEventShouldBeFound("eventCloned.equals=" + DEFAULT_EVENT_CLONED);

        // Get all the eventList where eventCloned equals to UPDATED_EVENT_CLONED
        defaultEventShouldNotBeFound("eventCloned.equals=" + UPDATED_EVENT_CLONED);
    }

    @Test
    @Transactional
    void getAllEventsByEventClonedIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCloned in DEFAULT_EVENT_CLONED or UPDATED_EVENT_CLONED
        defaultEventShouldBeFound("eventCloned.in=" + DEFAULT_EVENT_CLONED + "," + UPDATED_EVENT_CLONED);

        // Get all the eventList where eventCloned equals to UPDATED_EVENT_CLONED
        defaultEventShouldNotBeFound("eventCloned.in=" + UPDATED_EVENT_CLONED);
    }

    @Test
    @Transactional
    void getAllEventsByEventClonedIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventCloned is not null
        defaultEventShouldBeFound("eventCloned.specified=true");

        // Get all the eventList where eventCloned is null
        defaultEventShouldNotBeFound("eventCloned.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventParams equals to DEFAULT_EVENT_PARAMS
        defaultEventShouldBeFound("eventParams.equals=" + DEFAULT_EVENT_PARAMS);

        // Get all the eventList where eventParams equals to UPDATED_EVENT_PARAMS
        defaultEventShouldNotBeFound("eventParams.equals=" + UPDATED_EVENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventsByEventParamsIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventParams in DEFAULT_EVENT_PARAMS or UPDATED_EVENT_PARAMS
        defaultEventShouldBeFound("eventParams.in=" + DEFAULT_EVENT_PARAMS + "," + UPDATED_EVENT_PARAMS);

        // Get all the eventList where eventParams equals to UPDATED_EVENT_PARAMS
        defaultEventShouldNotBeFound("eventParams.in=" + UPDATED_EVENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventsByEventParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventParams is not null
        defaultEventShouldBeFound("eventParams.specified=true");

        // Get all the eventList where eventParams is null
        defaultEventShouldNotBeFound("eventParams.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventParamsContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventParams contains DEFAULT_EVENT_PARAMS
        defaultEventShouldBeFound("eventParams.contains=" + DEFAULT_EVENT_PARAMS);

        // Get all the eventList where eventParams contains UPDATED_EVENT_PARAMS
        defaultEventShouldNotBeFound("eventParams.contains=" + UPDATED_EVENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventsByEventParamsNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventParams does not contain DEFAULT_EVENT_PARAMS
        defaultEventShouldNotBeFound("eventParams.doesNotContain=" + DEFAULT_EVENT_PARAMS);

        // Get all the eventList where eventParams does not contain UPDATED_EVENT_PARAMS
        defaultEventShouldBeFound("eventParams.doesNotContain=" + UPDATED_EVENT_PARAMS);
    }

    @Test
    @Transactional
    void getAllEventsByEventAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAttributs equals to DEFAULT_EVENT_ATTRIBUTS
        defaultEventShouldBeFound("eventAttributs.equals=" + DEFAULT_EVENT_ATTRIBUTS);

        // Get all the eventList where eventAttributs equals to UPDATED_EVENT_ATTRIBUTS
        defaultEventShouldNotBeFound("eventAttributs.equals=" + UPDATED_EVENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventsByEventAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAttributs in DEFAULT_EVENT_ATTRIBUTS or UPDATED_EVENT_ATTRIBUTS
        defaultEventShouldBeFound("eventAttributs.in=" + DEFAULT_EVENT_ATTRIBUTS + "," + UPDATED_EVENT_ATTRIBUTS);

        // Get all the eventList where eventAttributs equals to UPDATED_EVENT_ATTRIBUTS
        defaultEventShouldNotBeFound("eventAttributs.in=" + UPDATED_EVENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventsByEventAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAttributs is not null
        defaultEventShouldBeFound("eventAttributs.specified=true");

        // Get all the eventList where eventAttributs is null
        defaultEventShouldNotBeFound("eventAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventAttributsContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAttributs contains DEFAULT_EVENT_ATTRIBUTS
        defaultEventShouldBeFound("eventAttributs.contains=" + DEFAULT_EVENT_ATTRIBUTS);

        // Get all the eventList where eventAttributs contains UPDATED_EVENT_ATTRIBUTS
        defaultEventShouldNotBeFound("eventAttributs.contains=" + UPDATED_EVENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventsByEventAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventAttributs does not contain DEFAULT_EVENT_ATTRIBUTS
        defaultEventShouldNotBeFound("eventAttributs.doesNotContain=" + DEFAULT_EVENT_ATTRIBUTS);

        // Get all the eventList where eventAttributs does not contain UPDATED_EVENT_ATTRIBUTS
        defaultEventShouldBeFound("eventAttributs.doesNotContain=" + UPDATED_EVENT_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllEventsByEventStatIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventStat equals to DEFAULT_EVENT_STAT
        defaultEventShouldBeFound("eventStat.equals=" + DEFAULT_EVENT_STAT);

        // Get all the eventList where eventStat equals to UPDATED_EVENT_STAT
        defaultEventShouldNotBeFound("eventStat.equals=" + UPDATED_EVENT_STAT);
    }

    @Test
    @Transactional
    void getAllEventsByEventStatIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventStat in DEFAULT_EVENT_STAT or UPDATED_EVENT_STAT
        defaultEventShouldBeFound("eventStat.in=" + DEFAULT_EVENT_STAT + "," + UPDATED_EVENT_STAT);

        // Get all the eventList where eventStat equals to UPDATED_EVENT_STAT
        defaultEventShouldNotBeFound("eventStat.in=" + UPDATED_EVENT_STAT);
    }

    @Test
    @Transactional
    void getAllEventsByEventStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where eventStat is not null
        defaultEventShouldBeFound("eventStat.specified=true");

        // Get all the eventList where eventStat is null
        defaultEventShouldNotBeFound("eventStat.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEventFormIsEqualToSomething() throws Exception {
        EventForm eventForm;
        if (TestUtil.findAll(em, EventForm.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            eventForm = EventFormResourceIT.createEntity(em);
        } else {
            eventForm = TestUtil.findAll(em, EventForm.class).get(0);
        }
        em.persist(eventForm);
        em.flush();
        event.addEventForm(eventForm);
        eventRepository.saveAndFlush(event);
        Long eventFormId = eventForm.getFormId();

        // Get all the eventList where eventForm equals to eventFormId
        defaultEventShouldBeFound("eventFormId.equals=" + eventFormId);

        // Get all the eventList where eventForm equals to (eventFormId + 1)
        defaultEventShouldNotBeFound("eventFormId.equals=" + (eventFormId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByEventFieldIsEqualToSomething() throws Exception {
        EventField eventField;
        if (TestUtil.findAll(em, EventField.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            eventField = EventFieldResourceIT.createEntity(em);
        } else {
            eventField = TestUtil.findAll(em, EventField.class).get(0);
        }
        em.persist(eventField);
        em.flush();
        event.addEventField(eventField);
        eventRepository.saveAndFlush(event);
        Long eventFieldId = eventField.getFieldId();

        // Get all the eventList where eventField equals to eventFieldId
        defaultEventShouldBeFound("eventFieldId.equals=" + eventFieldId);

        // Get all the eventList where eventField equals to (eventFieldId + 1)
        defaultEventShouldNotBeFound("eventFieldId.equals=" + (eventFieldId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByEventControlIsEqualToSomething() throws Exception {
        EventControl eventControl;
        if (TestUtil.findAll(em, EventControl.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            eventControl = EventControlResourceIT.createEntity(em);
        } else {
            eventControl = TestUtil.findAll(em, EventControl.class).get(0);
        }
        em.persist(eventControl);
        em.flush();
        event.addEventControl(eventControl);
        eventRepository.saveAndFlush(event);
        Long eventControlId = eventControl.getControlId();

        // Get all the eventList where eventControl equals to eventControlId
        defaultEventShouldBeFound("eventControlId.equals=" + eventControlId);

        // Get all the eventList where eventControl equals to (eventControlId + 1)
        defaultEventShouldNotBeFound("eventControlId.equals=" + (eventControlId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByAreaIsEqualToSomething() throws Exception {
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            area = AreaResourceIT.createEntity(em);
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        em.persist(area);
        em.flush();
        event.addArea(area);
        eventRepository.saveAndFlush(event);
        Long areaId = area.getAreaId();

        // Get all the eventList where area equals to areaId
        defaultEventShouldBeFound("areaId.equals=" + areaId);

        // Get all the eventList where area equals to (areaId + 1)
        defaultEventShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByFonctionIsEqualToSomething() throws Exception {
        Fonction fonction;
        if (TestUtil.findAll(em, Fonction.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            fonction = FonctionResourceIT.createEntity(em);
        } else {
            fonction = TestUtil.findAll(em, Fonction.class).get(0);
        }
        em.persist(fonction);
        em.flush();
        event.addFonction(fonction);
        eventRepository.saveAndFlush(event);
        Long fonctionId = fonction.getFonctionId();

        // Get all the eventList where fonction equals to fonctionId
        defaultEventShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the eventList where fonction equals to (fonctionId + 1)
        defaultEventShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        event.addCategory(category);
        eventRepository.saveAndFlush(event);
        Long categoryId = category.getCategoryId();

        // Get all the eventList where category equals to categoryId
        defaultEventShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the eventList where category equals to (categoryId + 1)
        defaultEventShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByPrintingModelIsEqualToSomething() throws Exception {
        PrintingModel printingModel;
        if (TestUtil.findAll(em, PrintingModel.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            printingModel = PrintingModelResourceIT.createEntity(em);
        } else {
            printingModel = TestUtil.findAll(em, PrintingModel.class).get(0);
        }
        em.persist(printingModel);
        em.flush();
        event.addPrintingModel(printingModel);
        eventRepository.saveAndFlush(event);
        Long printingModelId = printingModel.getPrintingModelId();

        // Get all the eventList where printingModel equals to printingModelId
        defaultEventShouldBeFound("printingModelId.equals=" + printingModelId);

        // Get all the eventList where printingModel equals to (printingModelId + 1)
        defaultEventShouldNotBeFound("printingModelId.equals=" + (printingModelId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByCodeIsEqualToSomething() throws Exception {
        Code code;
        if (TestUtil.findAll(em, Code.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            code = CodeResourceIT.createEntity(em);
        } else {
            code = TestUtil.findAll(em, Code.class).get(0);
        }
        em.persist(code);
        em.flush();
        event.addCode(code);
        eventRepository.saveAndFlush(event);
        Long codeId = code.getCodeId();

        // Get all the eventList where code equals to codeId
        defaultEventShouldBeFound("codeId.equals=" + codeId);

        // Get all the eventList where code equals to (codeId + 1)
        defaultEventShouldNotBeFound("codeId.equals=" + (codeId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByInfoSuppIsEqualToSomething() throws Exception {
        InfoSupp infoSupp;
        if (TestUtil.findAll(em, InfoSupp.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            infoSupp = InfoSuppResourceIT.createEntity(em);
        } else {
            infoSupp = TestUtil.findAll(em, InfoSupp.class).get(0);
        }
        em.persist(infoSupp);
        em.flush();
        event.addInfoSupp(infoSupp);
        eventRepository.saveAndFlush(event);
        Long infoSuppId = infoSupp.getInfoSuppId();

        // Get all the eventList where infoSupp equals to infoSuppId
        defaultEventShouldBeFound("infoSuppId.equals=" + infoSuppId);

        // Get all the eventList where infoSupp equals to (infoSuppId + 1)
        defaultEventShouldNotBeFound("infoSuppId.equals=" + (infoSuppId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByAttachementIsEqualToSomething() throws Exception {
        Attachement attachement;
        if (TestUtil.findAll(em, Attachement.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            attachement = AttachementResourceIT.createEntity(em);
        } else {
            attachement = TestUtil.findAll(em, Attachement.class).get(0);
        }
        em.persist(attachement);
        em.flush();
        event.addAttachement(attachement);
        eventRepository.saveAndFlush(event);
        Long attachementId = attachement.getAttachementId();

        // Get all the eventList where attachement equals to attachementId
        defaultEventShouldBeFound("attachementId.equals=" + attachementId);

        // Get all the eventList where attachement equals to (attachementId + 1)
        defaultEventShouldNotBeFound("attachementId.equals=" + (attachementId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByOrganizIsEqualToSomething() throws Exception {
        Organiz organiz;
        if (TestUtil.findAll(em, Organiz.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            organiz = OrganizResourceIT.createEntity(em);
        } else {
            organiz = TestUtil.findAll(em, Organiz.class).get(0);
        }
        em.persist(organiz);
        em.flush();
        event.addOrganiz(organiz);
        eventRepository.saveAndFlush(event);
        Long organizId = organiz.getOrganizId();

        // Get all the eventList where organiz equals to organizId
        defaultEventShouldBeFound("organizId.equals=" + organizId);

        // Get all the eventList where organiz equals to (organizId + 1)
        defaultEventShouldNotBeFound("organizId.equals=" + (organizId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByPhotoArchiveIsEqualToSomething() throws Exception {
        PhotoArchive photoArchive;
        if (TestUtil.findAll(em, PhotoArchive.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            photoArchive = PhotoArchiveResourceIT.createEntity(em);
        } else {
            photoArchive = TestUtil.findAll(em, PhotoArchive.class).get(0);
        }
        em.persist(photoArchive);
        em.flush();
        event.addPhotoArchive(photoArchive);
        eventRepository.saveAndFlush(event);
        Long photoArchiveId = photoArchive.getPhotoArchiveId();

        // Get all the eventList where photoArchive equals to photoArchiveId
        defaultEventShouldBeFound("photoArchiveId.equals=" + photoArchiveId);

        // Get all the eventList where photoArchive equals to (photoArchiveId + 1)
        defaultEventShouldNotBeFound("photoArchiveId.equals=" + (photoArchiveId + 1));
    }

    @Test
    @Transactional
    void getAllEventsBySiteIsEqualToSomething() throws Exception {
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            site = SiteResourceIT.createEntity(em);
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(site);
        em.flush();
        event.addSite(site);
        eventRepository.saveAndFlush(event);
        Long siteId = site.getSiteId();

        // Get all the eventList where site equals to siteId
        defaultEventShouldBeFound("siteId.equals=" + siteId);

        // Get all the eventList where site equals to (siteId + 1)
        defaultEventShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        event.addAccreditation(accreditation);
        eventRepository.saveAndFlush(event);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the eventList where accreditation equals to accreditationId
        defaultEventShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the eventList where accreditation equals to (accreditationId + 1)
        defaultEventShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByNoteIsEqualToSomething() throws Exception {
        Note note;
        if (TestUtil.findAll(em, Note.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            note = NoteResourceIT.createEntity(em);
        } else {
            note = TestUtil.findAll(em, Note.class).get(0);
        }
        em.persist(note);
        em.flush();
        event.addNote(note);
        eventRepository.saveAndFlush(event);
        Long noteId = note.getNoteId();

        // Get all the eventList where note equals to noteId
        defaultEventShouldBeFound("noteId.equals=" + noteId);

        // Get all the eventList where note equals to (noteId + 1)
        defaultEventShouldNotBeFound("noteId.equals=" + (noteId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByOperationHistoryIsEqualToSomething() throws Exception {
        OperationHistory operationHistory;
        if (TestUtil.findAll(em, OperationHistory.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            operationHistory = OperationHistoryResourceIT.createEntity(em);
        } else {
            operationHistory = TestUtil.findAll(em, OperationHistory.class).get(0);
        }
        em.persist(operationHistory);
        em.flush();
        event.addOperationHistory(operationHistory);
        eventRepository.saveAndFlush(event);
        Long operationHistoryId = operationHistory.getOperationHistoryId();

        // Get all the eventList where operationHistory equals to operationHistoryId
        defaultEventShouldBeFound("operationHistoryId.equals=" + operationHistoryId);

        // Get all the eventList where operationHistory equals to (operationHistoryId + 1)
        defaultEventShouldNotBeFound("operationHistoryId.equals=" + (operationHistoryId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        event.addPrintingCentre(printingCentre);
        eventRepository.saveAndFlush(event);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the eventList where printingCentre equals to printingCentreId
        defaultEventShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the eventList where printingCentre equals to (printingCentreId + 1)
        defaultEventShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    @Test
    @Transactional
    void getAllEventsBySettingIsEqualToSomething() throws Exception {
        Setting setting;
        if (TestUtil.findAll(em, Setting.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            setting = SettingResourceIT.createEntity(em);
        } else {
            setting = TestUtil.findAll(em, Setting.class).get(0);
        }
        em.persist(setting);
        em.flush();
        event.addSetting(setting);
        eventRepository.saveAndFlush(event);
        Long settingId = setting.getSettingId();

        // Get all the eventList where setting equals to settingId
        defaultEventShouldBeFound("settingId.equals=" + settingId);

        // Get all the eventList where setting equals to (settingId + 1)
        defaultEventShouldNotBeFound("settingId.equals=" + (settingId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByPrintingServerIsEqualToSomething() throws Exception {
        PrintingServer printingServer;
        if (TestUtil.findAll(em, PrintingServer.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            printingServer = PrintingServerResourceIT.createEntity(em);
        } else {
            printingServer = TestUtil.findAll(em, PrintingServer.class).get(0);
        }
        em.persist(printingServer);
        em.flush();
        event.addPrintingServer(printingServer);
        eventRepository.saveAndFlush(event);
        Long printingServerId = printingServer.getPrintingServerId();

        // Get all the eventList where printingServer equals to printingServerId
        defaultEventShouldBeFound("printingServerId.equals=" + printingServerId);

        // Get all the eventList where printingServer equals to (printingServerId + 1)
        defaultEventShouldNotBeFound("printingServerId.equals=" + (printingServerId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByCheckAccreditationHistoryIsEqualToSomething() throws Exception {
        CheckAccreditationHistory checkAccreditationHistory;
        if (TestUtil.findAll(em, CheckAccreditationHistory.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            checkAccreditationHistory = CheckAccreditationHistoryResourceIT.createEntity(em);
        } else {
            checkAccreditationHistory = TestUtil.findAll(em, CheckAccreditationHistory.class).get(0);
        }
        em.persist(checkAccreditationHistory);
        em.flush();
        event.addCheckAccreditationHistory(checkAccreditationHistory);
        eventRepository.saveAndFlush(event);
        Long checkAccreditationHistoryId = checkAccreditationHistory.getCheckAccreditationHistoryId();

        // Get all the eventList where checkAccreditationHistory equals to checkAccreditationHistoryId
        defaultEventShouldBeFound("checkAccreditationHistoryId.equals=" + checkAccreditationHistoryId);

        // Get all the eventList where checkAccreditationHistory equals to (checkAccreditationHistoryId + 1)
        defaultEventShouldNotBeFound("checkAccreditationHistoryId.equals=" + (checkAccreditationHistoryId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByCheckAccreditationReportIsEqualToSomething() throws Exception {
        CheckAccreditationReport checkAccreditationReport;
        if (TestUtil.findAll(em, CheckAccreditationReport.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            checkAccreditationReport = CheckAccreditationReportResourceIT.createEntity(em);
        } else {
            checkAccreditationReport = TestUtil.findAll(em, CheckAccreditationReport.class).get(0);
        }
        em.persist(checkAccreditationReport);
        em.flush();
        event.addCheckAccreditationReport(checkAccreditationReport);
        eventRepository.saveAndFlush(event);
        Long checkAccreditationReportId = checkAccreditationReport.getCheckAccreditationReportId();

        // Get all the eventList where checkAccreditationReport equals to checkAccreditationReportId
        defaultEventShouldBeFound("checkAccreditationReportId.equals=" + checkAccreditationReportId);

        // Get all the eventList where checkAccreditationReport equals to (checkAccreditationReportId + 1)
        defaultEventShouldNotBeFound("checkAccreditationReportId.equals=" + (checkAccreditationReportId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByAccreditationTypeIsEqualToSomething() throws Exception {
        AccreditationType accreditationType;
        if (TestUtil.findAll(em, AccreditationType.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            accreditationType = AccreditationTypeResourceIT.createEntity(em);
        } else {
            accreditationType = TestUtil.findAll(em, AccreditationType.class).get(0);
        }
        em.persist(accreditationType);
        em.flush();
        event.addAccreditationType(accreditationType);
        eventRepository.saveAndFlush(event);
        Long accreditationTypeId = accreditationType.getAccreditationTypeId();

        // Get all the eventList where accreditationType equals to accreditationTypeId
        defaultEventShouldBeFound("accreditationTypeId.equals=" + accreditationTypeId);

        // Get all the eventList where accreditationType equals to (accreditationTypeId + 1)
        defaultEventShouldNotBeFound("accreditationTypeId.equals=" + (accreditationTypeId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByDayPassInfoIsEqualToSomething() throws Exception {
        DayPassInfo dayPassInfo;
        if (TestUtil.findAll(em, DayPassInfo.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            dayPassInfo = DayPassInfoResourceIT.createEntity(em);
        } else {
            dayPassInfo = TestUtil.findAll(em, DayPassInfo.class).get(0);
        }
        em.persist(dayPassInfo);
        em.flush();
        event.addDayPassInfo(dayPassInfo);
        eventRepository.saveAndFlush(event);
        Long dayPassInfoId = dayPassInfo.getDayPassInfoId();

        // Get all the eventList where dayPassInfo equals to dayPassInfoId
        defaultEventShouldBeFound("dayPassInfoId.equals=" + dayPassInfoId);

        // Get all the eventList where dayPassInfo equals to (dayPassInfoId + 1)
        defaultEventShouldNotBeFound("dayPassInfoId.equals=" + (dayPassInfoId + 1));
    }

    @Test
    @Transactional
    void getAllEventsByLanguageIsEqualToSomething() throws Exception {
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            language = LanguageResourceIT.createEntity(em);
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        em.persist(language);
        em.flush();
        event.setLanguage(language);
        eventRepository.saveAndFlush(event);
        Long languageId = language.getLanguageId();

        // Get all the eventList where language equals to languageId
        defaultEventShouldBeFound("languageId.equals=" + languageId);

        // Get all the eventList where language equals to (languageId + 1)
        defaultEventShouldNotBeFound("languageId.equals=" + (languageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventShouldBeFound(String filter) throws Exception {
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=eventId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(event.getEventId().intValue())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].eventColor").value(hasItem(DEFAULT_EVENT_COLOR)))
            .andExpect(jsonPath("$.[*].eventDescription").value(hasItem(DEFAULT_EVENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].eventAbreviation").value(hasItem(DEFAULT_EVENT_ABREVIATION)))
            .andExpect(jsonPath("$.[*].eventdateStart").value(hasItem(sameInstant(DEFAULT_EVENTDATE_START))))
            .andExpect(jsonPath("$.[*].eventdateEnd").value(hasItem(sameInstant(DEFAULT_EVENTDATE_END))))
            .andExpect(jsonPath("$.[*].eventPrintingModelId").value(hasItem(DEFAULT_EVENT_PRINTING_MODEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].eventLogoContentType").value(hasItem(DEFAULT_EVENT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_LOGO))))
            .andExpect(jsonPath("$.[*].eventBannerCenterContentType").value(hasItem(DEFAULT_EVENT_BANNER_CENTER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerCenter").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_CENTER))))
            .andExpect(jsonPath("$.[*].eventBannerRightContentType").value(hasItem(DEFAULT_EVENT_BANNER_RIGHT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerRight").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_RIGHT))))
            .andExpect(jsonPath("$.[*].eventBannerLeftContentType").value(hasItem(DEFAULT_EVENT_BANNER_LEFT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerLeft").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_LEFT))))
            .andExpect(jsonPath("$.[*].eventBannerBasContentType").value(hasItem(DEFAULT_EVENT_BANNER_BAS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBannerBas").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER_BAS))))
            .andExpect(jsonPath("$.[*].eventWithPhoto").value(hasItem(DEFAULT_EVENT_WITH_PHOTO.booleanValue())))
            .andExpect(jsonPath("$.[*].eventNoCode").value(hasItem(DEFAULT_EVENT_NO_CODE.booleanValue())))
            .andExpect(jsonPath("$.[*].eventCodeNoFilter").value(hasItem(DEFAULT_EVENT_CODE_NO_FILTER.booleanValue())))
            .andExpect(
                jsonPath("$.[*].eventCodeByTypeAccreditation").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_ACCREDITATION.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].eventCodeByTypeCategorie").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_CATEGORIE.booleanValue())))
            .andExpect(jsonPath("$.[*].eventCodeByTypeFonction").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_FONCTION.booleanValue())))
            .andExpect(jsonPath("$.[*].eventCodeByTypeOrganiz").value(hasItem(DEFAULT_EVENT_CODE_BY_TYPE_ORGANIZ.booleanValue())))
            .andExpect(
                jsonPath("$.[*].eventDefaultPrintingLanguage").value(hasItem(DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].eventPCenterPrintingLanguage").value(hasItem(DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].eventImported").value(hasItem(DEFAULT_EVENT_IMPORTED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventArchived").value(hasItem(DEFAULT_EVENT_ARCHIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventArchiveFileName").value(hasItem(DEFAULT_EVENT_ARCHIVE_FILE_NAME)))
            .andExpect(jsonPath("$.[*].eventURL").value(hasItem(DEFAULT_EVENT_URL)))
            .andExpect(jsonPath("$.[*].eventDomaine").value(hasItem(DEFAULT_EVENT_DOMAINE)))
            .andExpect(jsonPath("$.[*].eventSousDomaine").value(hasItem(DEFAULT_EVENT_SOUS_DOMAINE)))
            .andExpect(jsonPath("$.[*].eventCloned").value(hasItem(DEFAULT_EVENT_CLONED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventParams").value(hasItem(DEFAULT_EVENT_PARAMS)))
            .andExpect(jsonPath("$.[*].eventAttributs").value(hasItem(DEFAULT_EVENT_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].eventStat").value(hasItem(DEFAULT_EVENT_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=eventId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventShouldNotBeFound(String filter) throws Exception {
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=eventId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=eventId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getEventId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .eventName(UPDATED_EVENT_NAME)
            .eventColor(UPDATED_EVENT_COLOR)
            .eventDescription(UPDATED_EVENT_DESCRIPTION)
            .eventAbreviation(UPDATED_EVENT_ABREVIATION)
            .eventdateStart(UPDATED_EVENTDATE_START)
            .eventdateEnd(UPDATED_EVENTDATE_END)
            .eventPrintingModelId(UPDATED_EVENT_PRINTING_MODEL_ID)
            .eventLogo(UPDATED_EVENT_LOGO)
            .eventLogoContentType(UPDATED_EVENT_LOGO_CONTENT_TYPE)
            .eventBannerCenter(UPDATED_EVENT_BANNER_CENTER)
            .eventBannerCenterContentType(UPDATED_EVENT_BANNER_CENTER_CONTENT_TYPE)
            .eventBannerRight(UPDATED_EVENT_BANNER_RIGHT)
            .eventBannerRightContentType(UPDATED_EVENT_BANNER_RIGHT_CONTENT_TYPE)
            .eventBannerLeft(UPDATED_EVENT_BANNER_LEFT)
            .eventBannerLeftContentType(UPDATED_EVENT_BANNER_LEFT_CONTENT_TYPE)
            .eventBannerBas(UPDATED_EVENT_BANNER_BAS)
            .eventBannerBasContentType(UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE)
            .eventWithPhoto(UPDATED_EVENT_WITH_PHOTO)
            .eventNoCode(UPDATED_EVENT_NO_CODE)
            .eventCodeNoFilter(UPDATED_EVENT_CODE_NO_FILTER)
            .eventCodeByTypeAccreditation(UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION)
            .eventCodeByTypeCategorie(UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE)
            .eventCodeByTypeFonction(UPDATED_EVENT_CODE_BY_TYPE_FONCTION)
            .eventCodeByTypeOrganiz(UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ)
            .eventDefaultPrintingLanguage(UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE)
            .eventPCenterPrintingLanguage(UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE)
            .eventImported(UPDATED_EVENT_IMPORTED)
            .eventArchived(UPDATED_EVENT_ARCHIVED)
            .eventArchiveFileName(UPDATED_EVENT_ARCHIVE_FILE_NAME)
            .eventURL(UPDATED_EVENT_URL)
            .eventDomaine(UPDATED_EVENT_DOMAINE)
            .eventSousDomaine(UPDATED_EVENT_SOUS_DOMAINE)
            .eventCloned(UPDATED_EVENT_CLONED)
            .eventParams(UPDATED_EVENT_PARAMS)
            .eventAttributs(UPDATED_EVENT_ATTRIBUTS)
            .eventStat(UPDATED_EVENT_STAT);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getEventId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testEvent.getEventColor()).isEqualTo(UPDATED_EVENT_COLOR);
        assertThat(testEvent.getEventDescription()).isEqualTo(UPDATED_EVENT_DESCRIPTION);
        assertThat(testEvent.getEventAbreviation()).isEqualTo(UPDATED_EVENT_ABREVIATION);
        assertThat(testEvent.getEventdateStart()).isEqualTo(UPDATED_EVENTDATE_START);
        assertThat(testEvent.getEventdateEnd()).isEqualTo(UPDATED_EVENTDATE_END);
        assertThat(testEvent.getEventPrintingModelId()).isEqualTo(UPDATED_EVENT_PRINTING_MODEL_ID);
        assertThat(testEvent.getEventLogo()).isEqualTo(UPDATED_EVENT_LOGO);
        assertThat(testEvent.getEventLogoContentType()).isEqualTo(UPDATED_EVENT_LOGO_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerCenter()).isEqualTo(UPDATED_EVENT_BANNER_CENTER);
        assertThat(testEvent.getEventBannerCenterContentType()).isEqualTo(UPDATED_EVENT_BANNER_CENTER_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerRight()).isEqualTo(UPDATED_EVENT_BANNER_RIGHT);
        assertThat(testEvent.getEventBannerRightContentType()).isEqualTo(UPDATED_EVENT_BANNER_RIGHT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerLeft()).isEqualTo(UPDATED_EVENT_BANNER_LEFT);
        assertThat(testEvent.getEventBannerLeftContentType()).isEqualTo(UPDATED_EVENT_BANNER_LEFT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerBas()).isEqualTo(UPDATED_EVENT_BANNER_BAS);
        assertThat(testEvent.getEventBannerBasContentType()).isEqualTo(UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE);
        assertThat(testEvent.getEventWithPhoto()).isEqualTo(UPDATED_EVENT_WITH_PHOTO);
        assertThat(testEvent.getEventNoCode()).isEqualTo(UPDATED_EVENT_NO_CODE);
        assertThat(testEvent.getEventCodeNoFilter()).isEqualTo(UPDATED_EVENT_CODE_NO_FILTER);
        assertThat(testEvent.getEventCodeByTypeAccreditation()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION);
        assertThat(testEvent.getEventCodeByTypeCategorie()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE);
        assertThat(testEvent.getEventCodeByTypeFonction()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_FONCTION);
        assertThat(testEvent.getEventCodeByTypeOrganiz()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ);
        assertThat(testEvent.getEventDefaultPrintingLanguage()).isEqualTo(UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventPCenterPrintingLanguage()).isEqualTo(UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventImported()).isEqualTo(UPDATED_EVENT_IMPORTED);
        assertThat(testEvent.getEventArchived()).isEqualTo(UPDATED_EVENT_ARCHIVED);
        assertThat(testEvent.getEventArchiveFileName()).isEqualTo(UPDATED_EVENT_ARCHIVE_FILE_NAME);
        assertThat(testEvent.getEventURL()).isEqualTo(UPDATED_EVENT_URL);
        assertThat(testEvent.getEventDomaine()).isEqualTo(UPDATED_EVENT_DOMAINE);
        assertThat(testEvent.getEventSousDomaine()).isEqualTo(UPDATED_EVENT_SOUS_DOMAINE);
        assertThat(testEvent.getEventCloned()).isEqualTo(UPDATED_EVENT_CLONED);
        assertThat(testEvent.getEventParams()).isEqualTo(UPDATED_EVENT_PARAMS);
        assertThat(testEvent.getEventAttributs()).isEqualTo(UPDATED_EVENT_ATTRIBUTS);
        assertThat(testEvent.getEventStat()).isEqualTo(UPDATED_EVENT_STAT);
    }

    @Test
    @Transactional
    void putNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setEventId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getEventId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setEventId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setEventId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setEventId(event.getEventId());

        partialUpdatedEvent
            .eventColor(UPDATED_EVENT_COLOR)
            .eventLogo(UPDATED_EVENT_LOGO)
            .eventLogoContentType(UPDATED_EVENT_LOGO_CONTENT_TYPE)
            .eventBannerBas(UPDATED_EVENT_BANNER_BAS)
            .eventBannerBasContentType(UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE)
            .eventWithPhoto(UPDATED_EVENT_WITH_PHOTO)
            .eventCodeByTypeAccreditation(UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION)
            .eventCodeByTypeCategorie(UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE)
            .eventCodeByTypeFonction(UPDATED_EVENT_CODE_BY_TYPE_FONCTION)
            .eventCodeByTypeOrganiz(UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ)
            .eventImported(UPDATED_EVENT_IMPORTED)
            .eventDomaine(UPDATED_EVENT_DOMAINE)
            .eventSousDomaine(UPDATED_EVENT_SOUS_DOMAINE)
            .eventAttributs(UPDATED_EVENT_ATTRIBUTS)
            .eventStat(UPDATED_EVENT_STAT);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getEventId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testEvent.getEventColor()).isEqualTo(UPDATED_EVENT_COLOR);
        assertThat(testEvent.getEventDescription()).isEqualTo(DEFAULT_EVENT_DESCRIPTION);
        assertThat(testEvent.getEventAbreviation()).isEqualTo(DEFAULT_EVENT_ABREVIATION);
        assertThat(testEvent.getEventdateStart()).isEqualTo(DEFAULT_EVENTDATE_START);
        assertThat(testEvent.getEventdateEnd()).isEqualTo(DEFAULT_EVENTDATE_END);
        assertThat(testEvent.getEventPrintingModelId()).isEqualTo(DEFAULT_EVENT_PRINTING_MODEL_ID);
        assertThat(testEvent.getEventLogo()).isEqualTo(UPDATED_EVENT_LOGO);
        assertThat(testEvent.getEventLogoContentType()).isEqualTo(UPDATED_EVENT_LOGO_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerCenter()).isEqualTo(DEFAULT_EVENT_BANNER_CENTER);
        assertThat(testEvent.getEventBannerCenterContentType()).isEqualTo(DEFAULT_EVENT_BANNER_CENTER_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerRight()).isEqualTo(DEFAULT_EVENT_BANNER_RIGHT);
        assertThat(testEvent.getEventBannerRightContentType()).isEqualTo(DEFAULT_EVENT_BANNER_RIGHT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerLeft()).isEqualTo(DEFAULT_EVENT_BANNER_LEFT);
        assertThat(testEvent.getEventBannerLeftContentType()).isEqualTo(DEFAULT_EVENT_BANNER_LEFT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerBas()).isEqualTo(UPDATED_EVENT_BANNER_BAS);
        assertThat(testEvent.getEventBannerBasContentType()).isEqualTo(UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE);
        assertThat(testEvent.getEventWithPhoto()).isEqualTo(UPDATED_EVENT_WITH_PHOTO);
        assertThat(testEvent.getEventNoCode()).isEqualTo(DEFAULT_EVENT_NO_CODE);
        assertThat(testEvent.getEventCodeNoFilter()).isEqualTo(DEFAULT_EVENT_CODE_NO_FILTER);
        assertThat(testEvent.getEventCodeByTypeAccreditation()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION);
        assertThat(testEvent.getEventCodeByTypeCategorie()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE);
        assertThat(testEvent.getEventCodeByTypeFonction()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_FONCTION);
        assertThat(testEvent.getEventCodeByTypeOrganiz()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ);
        assertThat(testEvent.getEventDefaultPrintingLanguage()).isEqualTo(DEFAULT_EVENT_DEFAULT_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventPCenterPrintingLanguage()).isEqualTo(DEFAULT_EVENT_P_CENTER_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventImported()).isEqualTo(UPDATED_EVENT_IMPORTED);
        assertThat(testEvent.getEventArchived()).isEqualTo(DEFAULT_EVENT_ARCHIVED);
        assertThat(testEvent.getEventArchiveFileName()).isEqualTo(DEFAULT_EVENT_ARCHIVE_FILE_NAME);
        assertThat(testEvent.getEventURL()).isEqualTo(DEFAULT_EVENT_URL);
        assertThat(testEvent.getEventDomaine()).isEqualTo(UPDATED_EVENT_DOMAINE);
        assertThat(testEvent.getEventSousDomaine()).isEqualTo(UPDATED_EVENT_SOUS_DOMAINE);
        assertThat(testEvent.getEventCloned()).isEqualTo(DEFAULT_EVENT_CLONED);
        assertThat(testEvent.getEventParams()).isEqualTo(DEFAULT_EVENT_PARAMS);
        assertThat(testEvent.getEventAttributs()).isEqualTo(UPDATED_EVENT_ATTRIBUTS);
        assertThat(testEvent.getEventStat()).isEqualTo(UPDATED_EVENT_STAT);
    }

    @Test
    @Transactional
    void fullUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setEventId(event.getEventId());

        partialUpdatedEvent
            .eventName(UPDATED_EVENT_NAME)
            .eventColor(UPDATED_EVENT_COLOR)
            .eventDescription(UPDATED_EVENT_DESCRIPTION)
            .eventAbreviation(UPDATED_EVENT_ABREVIATION)
            .eventdateStart(UPDATED_EVENTDATE_START)
            .eventdateEnd(UPDATED_EVENTDATE_END)
            .eventPrintingModelId(UPDATED_EVENT_PRINTING_MODEL_ID)
            .eventLogo(UPDATED_EVENT_LOGO)
            .eventLogoContentType(UPDATED_EVENT_LOGO_CONTENT_TYPE)
            .eventBannerCenter(UPDATED_EVENT_BANNER_CENTER)
            .eventBannerCenterContentType(UPDATED_EVENT_BANNER_CENTER_CONTENT_TYPE)
            .eventBannerRight(UPDATED_EVENT_BANNER_RIGHT)
            .eventBannerRightContentType(UPDATED_EVENT_BANNER_RIGHT_CONTENT_TYPE)
            .eventBannerLeft(UPDATED_EVENT_BANNER_LEFT)
            .eventBannerLeftContentType(UPDATED_EVENT_BANNER_LEFT_CONTENT_TYPE)
            .eventBannerBas(UPDATED_EVENT_BANNER_BAS)
            .eventBannerBasContentType(UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE)
            .eventWithPhoto(UPDATED_EVENT_WITH_PHOTO)
            .eventNoCode(UPDATED_EVENT_NO_CODE)
            .eventCodeNoFilter(UPDATED_EVENT_CODE_NO_FILTER)
            .eventCodeByTypeAccreditation(UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION)
            .eventCodeByTypeCategorie(UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE)
            .eventCodeByTypeFonction(UPDATED_EVENT_CODE_BY_TYPE_FONCTION)
            .eventCodeByTypeOrganiz(UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ)
            .eventDefaultPrintingLanguage(UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE)
            .eventPCenterPrintingLanguage(UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE)
            .eventImported(UPDATED_EVENT_IMPORTED)
            .eventArchived(UPDATED_EVENT_ARCHIVED)
            .eventArchiveFileName(UPDATED_EVENT_ARCHIVE_FILE_NAME)
            .eventURL(UPDATED_EVENT_URL)
            .eventDomaine(UPDATED_EVENT_DOMAINE)
            .eventSousDomaine(UPDATED_EVENT_SOUS_DOMAINE)
            .eventCloned(UPDATED_EVENT_CLONED)
            .eventParams(UPDATED_EVENT_PARAMS)
            .eventAttributs(UPDATED_EVENT_ATTRIBUTS)
            .eventStat(UPDATED_EVENT_STAT);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getEventId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testEvent.getEventColor()).isEqualTo(UPDATED_EVENT_COLOR);
        assertThat(testEvent.getEventDescription()).isEqualTo(UPDATED_EVENT_DESCRIPTION);
        assertThat(testEvent.getEventAbreviation()).isEqualTo(UPDATED_EVENT_ABREVIATION);
        assertThat(testEvent.getEventdateStart()).isEqualTo(UPDATED_EVENTDATE_START);
        assertThat(testEvent.getEventdateEnd()).isEqualTo(UPDATED_EVENTDATE_END);
        assertThat(testEvent.getEventPrintingModelId()).isEqualTo(UPDATED_EVENT_PRINTING_MODEL_ID);
        assertThat(testEvent.getEventLogo()).isEqualTo(UPDATED_EVENT_LOGO);
        assertThat(testEvent.getEventLogoContentType()).isEqualTo(UPDATED_EVENT_LOGO_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerCenter()).isEqualTo(UPDATED_EVENT_BANNER_CENTER);
        assertThat(testEvent.getEventBannerCenterContentType()).isEqualTo(UPDATED_EVENT_BANNER_CENTER_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerRight()).isEqualTo(UPDATED_EVENT_BANNER_RIGHT);
        assertThat(testEvent.getEventBannerRightContentType()).isEqualTo(UPDATED_EVENT_BANNER_RIGHT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerLeft()).isEqualTo(UPDATED_EVENT_BANNER_LEFT);
        assertThat(testEvent.getEventBannerLeftContentType()).isEqualTo(UPDATED_EVENT_BANNER_LEFT_CONTENT_TYPE);
        assertThat(testEvent.getEventBannerBas()).isEqualTo(UPDATED_EVENT_BANNER_BAS);
        assertThat(testEvent.getEventBannerBasContentType()).isEqualTo(UPDATED_EVENT_BANNER_BAS_CONTENT_TYPE);
        assertThat(testEvent.getEventWithPhoto()).isEqualTo(UPDATED_EVENT_WITH_PHOTO);
        assertThat(testEvent.getEventNoCode()).isEqualTo(UPDATED_EVENT_NO_CODE);
        assertThat(testEvent.getEventCodeNoFilter()).isEqualTo(UPDATED_EVENT_CODE_NO_FILTER);
        assertThat(testEvent.getEventCodeByTypeAccreditation()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_ACCREDITATION);
        assertThat(testEvent.getEventCodeByTypeCategorie()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_CATEGORIE);
        assertThat(testEvent.getEventCodeByTypeFonction()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_FONCTION);
        assertThat(testEvent.getEventCodeByTypeOrganiz()).isEqualTo(UPDATED_EVENT_CODE_BY_TYPE_ORGANIZ);
        assertThat(testEvent.getEventDefaultPrintingLanguage()).isEqualTo(UPDATED_EVENT_DEFAULT_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventPCenterPrintingLanguage()).isEqualTo(UPDATED_EVENT_P_CENTER_PRINTING_LANGUAGE);
        assertThat(testEvent.getEventImported()).isEqualTo(UPDATED_EVENT_IMPORTED);
        assertThat(testEvent.getEventArchived()).isEqualTo(UPDATED_EVENT_ARCHIVED);
        assertThat(testEvent.getEventArchiveFileName()).isEqualTo(UPDATED_EVENT_ARCHIVE_FILE_NAME);
        assertThat(testEvent.getEventURL()).isEqualTo(UPDATED_EVENT_URL);
        assertThat(testEvent.getEventDomaine()).isEqualTo(UPDATED_EVENT_DOMAINE);
        assertThat(testEvent.getEventSousDomaine()).isEqualTo(UPDATED_EVENT_SOUS_DOMAINE);
        assertThat(testEvent.getEventCloned()).isEqualTo(UPDATED_EVENT_CLONED);
        assertThat(testEvent.getEventParams()).isEqualTo(UPDATED_EVENT_PARAMS);
        assertThat(testEvent.getEventAttributs()).isEqualTo(UPDATED_EVENT_ATTRIBUTS);
        assertThat(testEvent.getEventStat()).isEqualTo(UPDATED_EVENT_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setEventId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventDTO.getEventId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setEventId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setEventId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, event.getEventId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
