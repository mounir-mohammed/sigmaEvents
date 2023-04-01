package ma.sig.events.web.rest;

import static ma.sig.events.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ma.sig.events.IntegrationTest;
import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.AccreditationType;
import ma.sig.events.domain.Attachement;
import ma.sig.events.domain.Category;
import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.domain.City;
import ma.sig.events.domain.Civility;
import ma.sig.events.domain.Code;
import ma.sig.events.domain.Country;
import ma.sig.events.domain.DayPassInfo;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Fonction;
import ma.sig.events.domain.InfoSupp;
import ma.sig.events.domain.Nationality;
import ma.sig.events.domain.Note;
import ma.sig.events.domain.Organiz;
import ma.sig.events.domain.PhotoArchive;
import ma.sig.events.domain.Sexe;
import ma.sig.events.domain.Site;
import ma.sig.events.domain.Status;
import ma.sig.events.repository.AccreditationRepository;
import ma.sig.events.service.AccreditationService;
import ma.sig.events.service.criteria.AccreditationCriteria;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.mapper.AccreditationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AccreditationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccreditationResourceIT {

    private static final String DEFAULT_ACCREDITATION_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_SECOND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_SECOND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACCREDITATION_BIRTH_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCREDITATION_BIRTH_DAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACCREDITATION_BIRTH_DAY = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ACCREDITATION_SEXE = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_SEXE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_TEL = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_TEL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ACCREDITATION_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ACCREDITATION_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ACCREDITATION_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ACCREDITATION_CIN_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_CIN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_PASSEPORT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_PASSEPORT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_CARTE_PRESSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_CARTE_PRESSE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACCREDITATION_CREATION_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_ACCREDITATION_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ACCREDITATION_CREATION_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final ZonedDateTime DEFAULT_ACCREDITATION_UPDATE_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_ACCREDITATION_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ACCREDITATION_UPDATE_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final String DEFAULT_ACCREDITATION_CREATED_BYUSER = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_CREATED_BYUSER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACCREDITATION_DATE_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACCREDITATION_DATE_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ACCREDITATION_DATE_START = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final ZonedDateTime DEFAULT_ACCREDITATION_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACCREDITATION_DATE_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ACCREDITATION_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_ACCREDITATION_PRINT_STAT = false;
    private static final Boolean UPDATED_ACCREDITATION_PRINT_STAT = true;

    private static final Long DEFAULT_ACCREDITATION_PRINT_NUMBER = 1L;
    private static final Long UPDATED_ACCREDITATION_PRINT_NUMBER = 2L;
    private static final Long SMALLER_ACCREDITATION_PRINT_NUMBER = 1L - 1L;

    private static final String DEFAULT_ACCREDITATION_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCREDITATION_STAT = false;
    private static final Boolean UPDATED_ACCREDITATION_STAT = true;

    private static final String ENTITY_API_URL = "/api/accreditations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{accreditationId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccreditationRepository accreditationRepository;

    @Mock
    private AccreditationRepository accreditationRepositoryMock;

    @Autowired
    private AccreditationMapper accreditationMapper;

    @Mock
    private AccreditationService accreditationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccreditationMockMvc;

    private Accreditation accreditation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accreditation createEntity(EntityManager em) {
        Accreditation accreditation = new Accreditation()
            .accreditationFirstName(DEFAULT_ACCREDITATION_FIRST_NAME)
            .accreditationSecondName(DEFAULT_ACCREDITATION_SECOND_NAME)
            .accreditationLastName(DEFAULT_ACCREDITATION_LAST_NAME)
            .accreditationBirthDay(DEFAULT_ACCREDITATION_BIRTH_DAY)
            .accreditationSexe(DEFAULT_ACCREDITATION_SEXE)
            .accreditationOccupation(DEFAULT_ACCREDITATION_OCCUPATION)
            .accreditationDescription(DEFAULT_ACCREDITATION_DESCRIPTION)
            .accreditationEmail(DEFAULT_ACCREDITATION_EMAIL)
            .accreditationTel(DEFAULT_ACCREDITATION_TEL)
            .accreditationPhoto(DEFAULT_ACCREDITATION_PHOTO)
            .accreditationPhotoContentType(DEFAULT_ACCREDITATION_PHOTO_CONTENT_TYPE)
            .accreditationCinId(DEFAULT_ACCREDITATION_CIN_ID)
            .accreditationPasseportId(DEFAULT_ACCREDITATION_PASSEPORT_ID)
            .accreditationCartePresseId(DEFAULT_ACCREDITATION_CARTE_PRESSE_ID)
            .accreditationCarteProfessionnelleId(DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID)
            .accreditationCreationDate(DEFAULT_ACCREDITATION_CREATION_DATE)
            .accreditationUpdateDate(DEFAULT_ACCREDITATION_UPDATE_DATE)
            .accreditationCreatedByuser(DEFAULT_ACCREDITATION_CREATED_BYUSER)
            .accreditationDateStart(DEFAULT_ACCREDITATION_DATE_START)
            .accreditationDateEnd(DEFAULT_ACCREDITATION_DATE_END)
            .accreditationPrintStat(DEFAULT_ACCREDITATION_PRINT_STAT)
            .accreditationPrintNumber(DEFAULT_ACCREDITATION_PRINT_NUMBER)
            .accreditationParams(DEFAULT_ACCREDITATION_PARAMS)
            .accreditationAttributs(DEFAULT_ACCREDITATION_ATTRIBUTS)
            .accreditationStat(DEFAULT_ACCREDITATION_STAT);
        return accreditation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accreditation createUpdatedEntity(EntityManager em) {
        Accreditation accreditation = new Accreditation()
            .accreditationFirstName(UPDATED_ACCREDITATION_FIRST_NAME)
            .accreditationSecondName(UPDATED_ACCREDITATION_SECOND_NAME)
            .accreditationLastName(UPDATED_ACCREDITATION_LAST_NAME)
            .accreditationBirthDay(UPDATED_ACCREDITATION_BIRTH_DAY)
            .accreditationSexe(UPDATED_ACCREDITATION_SEXE)
            .accreditationOccupation(UPDATED_ACCREDITATION_OCCUPATION)
            .accreditationDescription(UPDATED_ACCREDITATION_DESCRIPTION)
            .accreditationEmail(UPDATED_ACCREDITATION_EMAIL)
            .accreditationTel(UPDATED_ACCREDITATION_TEL)
            .accreditationPhoto(UPDATED_ACCREDITATION_PHOTO)
            .accreditationPhotoContentType(UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE)
            .accreditationCinId(UPDATED_ACCREDITATION_CIN_ID)
            .accreditationPasseportId(UPDATED_ACCREDITATION_PASSEPORT_ID)
            .accreditationCartePresseId(UPDATED_ACCREDITATION_CARTE_PRESSE_ID)
            .accreditationCarteProfessionnelleId(UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID)
            .accreditationCreationDate(UPDATED_ACCREDITATION_CREATION_DATE)
            .accreditationUpdateDate(UPDATED_ACCREDITATION_UPDATE_DATE)
            .accreditationCreatedByuser(UPDATED_ACCREDITATION_CREATED_BYUSER)
            .accreditationDateStart(UPDATED_ACCREDITATION_DATE_START)
            .accreditationDateEnd(UPDATED_ACCREDITATION_DATE_END)
            .accreditationPrintStat(UPDATED_ACCREDITATION_PRINT_STAT)
            .accreditationPrintNumber(UPDATED_ACCREDITATION_PRINT_NUMBER)
            .accreditationParams(UPDATED_ACCREDITATION_PARAMS)
            .accreditationAttributs(UPDATED_ACCREDITATION_ATTRIBUTS)
            .accreditationStat(UPDATED_ACCREDITATION_STAT);
        return accreditation;
    }

    @BeforeEach
    public void initTest() {
        accreditation = createEntity(em);
    }

    @Test
    @Transactional
    void createAccreditation() throws Exception {
        int databaseSizeBeforeCreate = accreditationRepository.findAll().size();
        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);
        restAccreditationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeCreate + 1);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getAccreditationFirstName()).isEqualTo(DEFAULT_ACCREDITATION_FIRST_NAME);
        assertThat(testAccreditation.getAccreditationSecondName()).isEqualTo(DEFAULT_ACCREDITATION_SECOND_NAME);
        assertThat(testAccreditation.getAccreditationLastName()).isEqualTo(DEFAULT_ACCREDITATION_LAST_NAME);
        assertThat(testAccreditation.getAccreditationBirthDay()).isEqualTo(DEFAULT_ACCREDITATION_BIRTH_DAY);
        assertThat(testAccreditation.getAccreditationSexe()).isEqualTo(DEFAULT_ACCREDITATION_SEXE);
        assertThat(testAccreditation.getAccreditationOccupation()).isEqualTo(DEFAULT_ACCREDITATION_OCCUPATION);
        assertThat(testAccreditation.getAccreditationDescription()).isEqualTo(DEFAULT_ACCREDITATION_DESCRIPTION);
        assertThat(testAccreditation.getAccreditationEmail()).isEqualTo(DEFAULT_ACCREDITATION_EMAIL);
        assertThat(testAccreditation.getAccreditationTel()).isEqualTo(DEFAULT_ACCREDITATION_TEL);
        assertThat(testAccreditation.getAccreditationPhoto()).isEqualTo(DEFAULT_ACCREDITATION_PHOTO);
        assertThat(testAccreditation.getAccreditationPhotoContentType()).isEqualTo(DEFAULT_ACCREDITATION_PHOTO_CONTENT_TYPE);
        assertThat(testAccreditation.getAccreditationCinId()).isEqualTo(DEFAULT_ACCREDITATION_CIN_ID);
        assertThat(testAccreditation.getAccreditationPasseportId()).isEqualTo(DEFAULT_ACCREDITATION_PASSEPORT_ID);
        assertThat(testAccreditation.getAccreditationCartePresseId()).isEqualTo(DEFAULT_ACCREDITATION_CARTE_PRESSE_ID);
        assertThat(testAccreditation.getAccreditationCarteProfessionnelleId()).isEqualTo(DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID);
        assertThat(testAccreditation.getAccreditationCreationDate()).isEqualTo(DEFAULT_ACCREDITATION_CREATION_DATE);
        assertThat(testAccreditation.getAccreditationUpdateDate()).isEqualTo(DEFAULT_ACCREDITATION_UPDATE_DATE);
        assertThat(testAccreditation.getAccreditationCreatedByuser()).isEqualTo(DEFAULT_ACCREDITATION_CREATED_BYUSER);
        assertThat(testAccreditation.getAccreditationDateStart()).isEqualTo(DEFAULT_ACCREDITATION_DATE_START);
        assertThat(testAccreditation.getAccreditationDateEnd()).isEqualTo(DEFAULT_ACCREDITATION_DATE_END);
        assertThat(testAccreditation.getAccreditationPrintStat()).isEqualTo(DEFAULT_ACCREDITATION_PRINT_STAT);
        assertThat(testAccreditation.getAccreditationPrintNumber()).isEqualTo(DEFAULT_ACCREDITATION_PRINT_NUMBER);
        assertThat(testAccreditation.getAccreditationParams()).isEqualTo(DEFAULT_ACCREDITATION_PARAMS);
        assertThat(testAccreditation.getAccreditationAttributs()).isEqualTo(DEFAULT_ACCREDITATION_ATTRIBUTS);
        assertThat(testAccreditation.getAccreditationStat()).isEqualTo(DEFAULT_ACCREDITATION_STAT);
    }

    @Test
    @Transactional
    void createAccreditationWithExistingId() throws Exception {
        // Create the Accreditation with an existing ID
        accreditation.setAccreditationId(1L);
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        int databaseSizeBeforeCreate = accreditationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccreditationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccreditationFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setAccreditationFirstName(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccreditationLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setAccreditationLastName(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccreditationBirthDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setAccreditationBirthDay(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccreditations() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList
        restAccreditationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accreditationId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].accreditationId").value(hasItem(accreditation.getAccreditationId().intValue())))
            .andExpect(jsonPath("$.[*].accreditationFirstName").value(hasItem(DEFAULT_ACCREDITATION_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].accreditationSecondName").value(hasItem(DEFAULT_ACCREDITATION_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].accreditationLastName").value(hasItem(DEFAULT_ACCREDITATION_LAST_NAME)))
            .andExpect(jsonPath("$.[*].accreditationBirthDay").value(hasItem(DEFAULT_ACCREDITATION_BIRTH_DAY.toString())))
            .andExpect(jsonPath("$.[*].accreditationSexe").value(hasItem(DEFAULT_ACCREDITATION_SEXE)))
            .andExpect(jsonPath("$.[*].accreditationOccupation").value(hasItem(DEFAULT_ACCREDITATION_OCCUPATION)))
            .andExpect(jsonPath("$.[*].accreditationDescription").value(hasItem(DEFAULT_ACCREDITATION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].accreditationEmail").value(hasItem(DEFAULT_ACCREDITATION_EMAIL)))
            .andExpect(jsonPath("$.[*].accreditationTel").value(hasItem(DEFAULT_ACCREDITATION_TEL)))
            .andExpect(jsonPath("$.[*].accreditationPhotoContentType").value(hasItem(DEFAULT_ACCREDITATION_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].accreditationPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACCREDITATION_PHOTO))))
            .andExpect(jsonPath("$.[*].accreditationCinId").value(hasItem(DEFAULT_ACCREDITATION_CIN_ID)))
            .andExpect(jsonPath("$.[*].accreditationPasseportId").value(hasItem(DEFAULT_ACCREDITATION_PASSEPORT_ID)))
            .andExpect(jsonPath("$.[*].accreditationCartePresseId").value(hasItem(DEFAULT_ACCREDITATION_CARTE_PRESSE_ID)))
            .andExpect(jsonPath("$.[*].accreditationCarteProfessionnelleId").value(hasItem(DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID)))
            .andExpect(jsonPath("$.[*].accreditationCreationDate").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].accreditationUpdateDate").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].accreditationCreatedByuser").value(hasItem(DEFAULT_ACCREDITATION_CREATED_BYUSER)))
            .andExpect(jsonPath("$.[*].accreditationDateStart").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_DATE_START))))
            .andExpect(jsonPath("$.[*].accreditationDateEnd").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_DATE_END))))
            .andExpect(jsonPath("$.[*].accreditationPrintStat").value(hasItem(DEFAULT_ACCREDITATION_PRINT_STAT.booleanValue())))
            .andExpect(jsonPath("$.[*].accreditationPrintNumber").value(hasItem(DEFAULT_ACCREDITATION_PRINT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].accreditationParams").value(hasItem(DEFAULT_ACCREDITATION_PARAMS)))
            .andExpect(jsonPath("$.[*].accreditationAttributs").value(hasItem(DEFAULT_ACCREDITATION_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].accreditationStat").value(hasItem(DEFAULT_ACCREDITATION_STAT.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAccreditationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(accreditationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAccreditationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(accreditationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAccreditationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(accreditationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAccreditationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(accreditationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get the accreditation
        restAccreditationMockMvc
            .perform(get(ENTITY_API_URL_ID, accreditation.getAccreditationId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.accreditationId").value(accreditation.getAccreditationId().intValue()))
            .andExpect(jsonPath("$.accreditationFirstName").value(DEFAULT_ACCREDITATION_FIRST_NAME))
            .andExpect(jsonPath("$.accreditationSecondName").value(DEFAULT_ACCREDITATION_SECOND_NAME))
            .andExpect(jsonPath("$.accreditationLastName").value(DEFAULT_ACCREDITATION_LAST_NAME))
            .andExpect(jsonPath("$.accreditationBirthDay").value(DEFAULT_ACCREDITATION_BIRTH_DAY.toString()))
            .andExpect(jsonPath("$.accreditationSexe").value(DEFAULT_ACCREDITATION_SEXE))
            .andExpect(jsonPath("$.accreditationOccupation").value(DEFAULT_ACCREDITATION_OCCUPATION))
            .andExpect(jsonPath("$.accreditationDescription").value(DEFAULT_ACCREDITATION_DESCRIPTION))
            .andExpect(jsonPath("$.accreditationEmail").value(DEFAULT_ACCREDITATION_EMAIL))
            .andExpect(jsonPath("$.accreditationTel").value(DEFAULT_ACCREDITATION_TEL))
            .andExpect(jsonPath("$.accreditationPhotoContentType").value(DEFAULT_ACCREDITATION_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.accreditationPhoto").value(Base64Utils.encodeToString(DEFAULT_ACCREDITATION_PHOTO)))
            .andExpect(jsonPath("$.accreditationCinId").value(DEFAULT_ACCREDITATION_CIN_ID))
            .andExpect(jsonPath("$.accreditationPasseportId").value(DEFAULT_ACCREDITATION_PASSEPORT_ID))
            .andExpect(jsonPath("$.accreditationCartePresseId").value(DEFAULT_ACCREDITATION_CARTE_PRESSE_ID))
            .andExpect(jsonPath("$.accreditationCarteProfessionnelleId").value(DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID))
            .andExpect(jsonPath("$.accreditationCreationDate").value(sameInstant(DEFAULT_ACCREDITATION_CREATION_DATE)))
            .andExpect(jsonPath("$.accreditationUpdateDate").value(sameInstant(DEFAULT_ACCREDITATION_UPDATE_DATE)))
            .andExpect(jsonPath("$.accreditationCreatedByuser").value(DEFAULT_ACCREDITATION_CREATED_BYUSER))
            .andExpect(jsonPath("$.accreditationDateStart").value(sameInstant(DEFAULT_ACCREDITATION_DATE_START)))
            .andExpect(jsonPath("$.accreditationDateEnd").value(sameInstant(DEFAULT_ACCREDITATION_DATE_END)))
            .andExpect(jsonPath("$.accreditationPrintStat").value(DEFAULT_ACCREDITATION_PRINT_STAT.booleanValue()))
            .andExpect(jsonPath("$.accreditationPrintNumber").value(DEFAULT_ACCREDITATION_PRINT_NUMBER.intValue()))
            .andExpect(jsonPath("$.accreditationParams").value(DEFAULT_ACCREDITATION_PARAMS))
            .andExpect(jsonPath("$.accreditationAttributs").value(DEFAULT_ACCREDITATION_ATTRIBUTS))
            .andExpect(jsonPath("$.accreditationStat").value(DEFAULT_ACCREDITATION_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getAccreditationsByIdFiltering() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        Long id = accreditation.getAccreditationId();

        defaultAccreditationShouldBeFound("accreditationId.equals=" + id);
        defaultAccreditationShouldNotBeFound("accreditationId.notEquals=" + id);

        defaultAccreditationShouldBeFound("accreditationId.greaterThanOrEqual=" + id);
        defaultAccreditationShouldNotBeFound("accreditationId.greaterThan=" + id);

        defaultAccreditationShouldBeFound("accreditationId.lessThanOrEqual=" + id);
        defaultAccreditationShouldNotBeFound("accreditationId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationFirstName equals to DEFAULT_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldBeFound("accreditationFirstName.equals=" + DEFAULT_ACCREDITATION_FIRST_NAME);

        // Get all the accreditationList where accreditationFirstName equals to UPDATED_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldNotBeFound("accreditationFirstName.equals=" + UPDATED_ACCREDITATION_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationFirstName in DEFAULT_ACCREDITATION_FIRST_NAME or UPDATED_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldBeFound(
            "accreditationFirstName.in=" + DEFAULT_ACCREDITATION_FIRST_NAME + "," + UPDATED_ACCREDITATION_FIRST_NAME
        );

        // Get all the accreditationList where accreditationFirstName equals to UPDATED_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldNotBeFound("accreditationFirstName.in=" + UPDATED_ACCREDITATION_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationFirstName is not null
        defaultAccreditationShouldBeFound("accreditationFirstName.specified=true");

        // Get all the accreditationList where accreditationFirstName is null
        defaultAccreditationShouldNotBeFound("accreditationFirstName.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationFirstNameContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationFirstName contains DEFAULT_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldBeFound("accreditationFirstName.contains=" + DEFAULT_ACCREDITATION_FIRST_NAME);

        // Get all the accreditationList where accreditationFirstName contains UPDATED_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldNotBeFound("accreditationFirstName.contains=" + UPDATED_ACCREDITATION_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationFirstName does not contain DEFAULT_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldNotBeFound("accreditationFirstName.doesNotContain=" + DEFAULT_ACCREDITATION_FIRST_NAME);

        // Get all the accreditationList where accreditationFirstName does not contain UPDATED_ACCREDITATION_FIRST_NAME
        defaultAccreditationShouldBeFound("accreditationFirstName.doesNotContain=" + UPDATED_ACCREDITATION_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSecondNameIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSecondName equals to DEFAULT_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldBeFound("accreditationSecondName.equals=" + DEFAULT_ACCREDITATION_SECOND_NAME);

        // Get all the accreditationList where accreditationSecondName equals to UPDATED_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldNotBeFound("accreditationSecondName.equals=" + UPDATED_ACCREDITATION_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSecondNameIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSecondName in DEFAULT_ACCREDITATION_SECOND_NAME or UPDATED_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldBeFound(
            "accreditationSecondName.in=" + DEFAULT_ACCREDITATION_SECOND_NAME + "," + UPDATED_ACCREDITATION_SECOND_NAME
        );

        // Get all the accreditationList where accreditationSecondName equals to UPDATED_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldNotBeFound("accreditationSecondName.in=" + UPDATED_ACCREDITATION_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSecondNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSecondName is not null
        defaultAccreditationShouldBeFound("accreditationSecondName.specified=true");

        // Get all the accreditationList where accreditationSecondName is null
        defaultAccreditationShouldNotBeFound("accreditationSecondName.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSecondNameContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSecondName contains DEFAULT_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldBeFound("accreditationSecondName.contains=" + DEFAULT_ACCREDITATION_SECOND_NAME);

        // Get all the accreditationList where accreditationSecondName contains UPDATED_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldNotBeFound("accreditationSecondName.contains=" + UPDATED_ACCREDITATION_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSecondNameNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSecondName does not contain DEFAULT_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldNotBeFound("accreditationSecondName.doesNotContain=" + DEFAULT_ACCREDITATION_SECOND_NAME);

        // Get all the accreditationList where accreditationSecondName does not contain UPDATED_ACCREDITATION_SECOND_NAME
        defaultAccreditationShouldBeFound("accreditationSecondName.doesNotContain=" + UPDATED_ACCREDITATION_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationLastName equals to DEFAULT_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldBeFound("accreditationLastName.equals=" + DEFAULT_ACCREDITATION_LAST_NAME);

        // Get all the accreditationList where accreditationLastName equals to UPDATED_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldNotBeFound("accreditationLastName.equals=" + UPDATED_ACCREDITATION_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationLastName in DEFAULT_ACCREDITATION_LAST_NAME or UPDATED_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldBeFound(
            "accreditationLastName.in=" + DEFAULT_ACCREDITATION_LAST_NAME + "," + UPDATED_ACCREDITATION_LAST_NAME
        );

        // Get all the accreditationList where accreditationLastName equals to UPDATED_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldNotBeFound("accreditationLastName.in=" + UPDATED_ACCREDITATION_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationLastName is not null
        defaultAccreditationShouldBeFound("accreditationLastName.specified=true");

        // Get all the accreditationList where accreditationLastName is null
        defaultAccreditationShouldNotBeFound("accreditationLastName.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationLastNameContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationLastName contains DEFAULT_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldBeFound("accreditationLastName.contains=" + DEFAULT_ACCREDITATION_LAST_NAME);

        // Get all the accreditationList where accreditationLastName contains UPDATED_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldNotBeFound("accreditationLastName.contains=" + UPDATED_ACCREDITATION_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationLastName does not contain DEFAULT_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldNotBeFound("accreditationLastName.doesNotContain=" + DEFAULT_ACCREDITATION_LAST_NAME);

        // Get all the accreditationList where accreditationLastName does not contain UPDATED_ACCREDITATION_LAST_NAME
        defaultAccreditationShouldBeFound("accreditationLastName.doesNotContain=" + UPDATED_ACCREDITATION_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationBirthDayIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationBirthDay equals to DEFAULT_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldBeFound("accreditationBirthDay.equals=" + DEFAULT_ACCREDITATION_BIRTH_DAY);

        // Get all the accreditationList where accreditationBirthDay equals to UPDATED_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldNotBeFound("accreditationBirthDay.equals=" + UPDATED_ACCREDITATION_BIRTH_DAY);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationBirthDayIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationBirthDay in DEFAULT_ACCREDITATION_BIRTH_DAY or UPDATED_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldBeFound(
            "accreditationBirthDay.in=" + DEFAULT_ACCREDITATION_BIRTH_DAY + "," + UPDATED_ACCREDITATION_BIRTH_DAY
        );

        // Get all the accreditationList where accreditationBirthDay equals to UPDATED_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldNotBeFound("accreditationBirthDay.in=" + UPDATED_ACCREDITATION_BIRTH_DAY);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationBirthDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationBirthDay is not null
        defaultAccreditationShouldBeFound("accreditationBirthDay.specified=true");

        // Get all the accreditationList where accreditationBirthDay is null
        defaultAccreditationShouldNotBeFound("accreditationBirthDay.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationBirthDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationBirthDay is greater than or equal to DEFAULT_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldBeFound("accreditationBirthDay.greaterThanOrEqual=" + DEFAULT_ACCREDITATION_BIRTH_DAY);

        // Get all the accreditationList where accreditationBirthDay is greater than or equal to UPDATED_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldNotBeFound("accreditationBirthDay.greaterThanOrEqual=" + UPDATED_ACCREDITATION_BIRTH_DAY);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationBirthDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationBirthDay is less than or equal to DEFAULT_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldBeFound("accreditationBirthDay.lessThanOrEqual=" + DEFAULT_ACCREDITATION_BIRTH_DAY);

        // Get all the accreditationList where accreditationBirthDay is less than or equal to SMALLER_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldNotBeFound("accreditationBirthDay.lessThanOrEqual=" + SMALLER_ACCREDITATION_BIRTH_DAY);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationBirthDayIsLessThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationBirthDay is less than DEFAULT_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldNotBeFound("accreditationBirthDay.lessThan=" + DEFAULT_ACCREDITATION_BIRTH_DAY);

        // Get all the accreditationList where accreditationBirthDay is less than UPDATED_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldBeFound("accreditationBirthDay.lessThan=" + UPDATED_ACCREDITATION_BIRTH_DAY);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationBirthDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationBirthDay is greater than DEFAULT_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldNotBeFound("accreditationBirthDay.greaterThan=" + DEFAULT_ACCREDITATION_BIRTH_DAY);

        // Get all the accreditationList where accreditationBirthDay is greater than SMALLER_ACCREDITATION_BIRTH_DAY
        defaultAccreditationShouldBeFound("accreditationBirthDay.greaterThan=" + SMALLER_ACCREDITATION_BIRTH_DAY);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSexeIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSexe equals to DEFAULT_ACCREDITATION_SEXE
        defaultAccreditationShouldBeFound("accreditationSexe.equals=" + DEFAULT_ACCREDITATION_SEXE);

        // Get all the accreditationList where accreditationSexe equals to UPDATED_ACCREDITATION_SEXE
        defaultAccreditationShouldNotBeFound("accreditationSexe.equals=" + UPDATED_ACCREDITATION_SEXE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSexeIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSexe in DEFAULT_ACCREDITATION_SEXE or UPDATED_ACCREDITATION_SEXE
        defaultAccreditationShouldBeFound("accreditationSexe.in=" + DEFAULT_ACCREDITATION_SEXE + "," + UPDATED_ACCREDITATION_SEXE);

        // Get all the accreditationList where accreditationSexe equals to UPDATED_ACCREDITATION_SEXE
        defaultAccreditationShouldNotBeFound("accreditationSexe.in=" + UPDATED_ACCREDITATION_SEXE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSexeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSexe is not null
        defaultAccreditationShouldBeFound("accreditationSexe.specified=true");

        // Get all the accreditationList where accreditationSexe is null
        defaultAccreditationShouldNotBeFound("accreditationSexe.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSexeContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSexe contains DEFAULT_ACCREDITATION_SEXE
        defaultAccreditationShouldBeFound("accreditationSexe.contains=" + DEFAULT_ACCREDITATION_SEXE);

        // Get all the accreditationList where accreditationSexe contains UPDATED_ACCREDITATION_SEXE
        defaultAccreditationShouldNotBeFound("accreditationSexe.contains=" + UPDATED_ACCREDITATION_SEXE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationSexeNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationSexe does not contain DEFAULT_ACCREDITATION_SEXE
        defaultAccreditationShouldNotBeFound("accreditationSexe.doesNotContain=" + DEFAULT_ACCREDITATION_SEXE);

        // Get all the accreditationList where accreditationSexe does not contain UPDATED_ACCREDITATION_SEXE
        defaultAccreditationShouldBeFound("accreditationSexe.doesNotContain=" + UPDATED_ACCREDITATION_SEXE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationOccupationIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationOccupation equals to DEFAULT_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldBeFound("accreditationOccupation.equals=" + DEFAULT_ACCREDITATION_OCCUPATION);

        // Get all the accreditationList where accreditationOccupation equals to UPDATED_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldNotBeFound("accreditationOccupation.equals=" + UPDATED_ACCREDITATION_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationOccupationIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationOccupation in DEFAULT_ACCREDITATION_OCCUPATION or UPDATED_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldBeFound(
            "accreditationOccupation.in=" + DEFAULT_ACCREDITATION_OCCUPATION + "," + UPDATED_ACCREDITATION_OCCUPATION
        );

        // Get all the accreditationList where accreditationOccupation equals to UPDATED_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldNotBeFound("accreditationOccupation.in=" + UPDATED_ACCREDITATION_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationOccupationIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationOccupation is not null
        defaultAccreditationShouldBeFound("accreditationOccupation.specified=true");

        // Get all the accreditationList where accreditationOccupation is null
        defaultAccreditationShouldNotBeFound("accreditationOccupation.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationOccupationContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationOccupation contains DEFAULT_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldBeFound("accreditationOccupation.contains=" + DEFAULT_ACCREDITATION_OCCUPATION);

        // Get all the accreditationList where accreditationOccupation contains UPDATED_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldNotBeFound("accreditationOccupation.contains=" + UPDATED_ACCREDITATION_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationOccupationNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationOccupation does not contain DEFAULT_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldNotBeFound("accreditationOccupation.doesNotContain=" + DEFAULT_ACCREDITATION_OCCUPATION);

        // Get all the accreditationList where accreditationOccupation does not contain UPDATED_ACCREDITATION_OCCUPATION
        defaultAccreditationShouldBeFound("accreditationOccupation.doesNotContain=" + UPDATED_ACCREDITATION_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDescription equals to DEFAULT_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldBeFound("accreditationDescription.equals=" + DEFAULT_ACCREDITATION_DESCRIPTION);

        // Get all the accreditationList where accreditationDescription equals to UPDATED_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldNotBeFound("accreditationDescription.equals=" + UPDATED_ACCREDITATION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDescription in DEFAULT_ACCREDITATION_DESCRIPTION or UPDATED_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldBeFound(
            "accreditationDescription.in=" + DEFAULT_ACCREDITATION_DESCRIPTION + "," + UPDATED_ACCREDITATION_DESCRIPTION
        );

        // Get all the accreditationList where accreditationDescription equals to UPDATED_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldNotBeFound("accreditationDescription.in=" + UPDATED_ACCREDITATION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDescription is not null
        defaultAccreditationShouldBeFound("accreditationDescription.specified=true");

        // Get all the accreditationList where accreditationDescription is null
        defaultAccreditationShouldNotBeFound("accreditationDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDescriptionContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDescription contains DEFAULT_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldBeFound("accreditationDescription.contains=" + DEFAULT_ACCREDITATION_DESCRIPTION);

        // Get all the accreditationList where accreditationDescription contains UPDATED_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldNotBeFound("accreditationDescription.contains=" + UPDATED_ACCREDITATION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDescription does not contain DEFAULT_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldNotBeFound("accreditationDescription.doesNotContain=" + DEFAULT_ACCREDITATION_DESCRIPTION);

        // Get all the accreditationList where accreditationDescription does not contain UPDATED_ACCREDITATION_DESCRIPTION
        defaultAccreditationShouldBeFound("accreditationDescription.doesNotContain=" + UPDATED_ACCREDITATION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationEmail equals to DEFAULT_ACCREDITATION_EMAIL
        defaultAccreditationShouldBeFound("accreditationEmail.equals=" + DEFAULT_ACCREDITATION_EMAIL);

        // Get all the accreditationList where accreditationEmail equals to UPDATED_ACCREDITATION_EMAIL
        defaultAccreditationShouldNotBeFound("accreditationEmail.equals=" + UPDATED_ACCREDITATION_EMAIL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationEmailIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationEmail in DEFAULT_ACCREDITATION_EMAIL or UPDATED_ACCREDITATION_EMAIL
        defaultAccreditationShouldBeFound("accreditationEmail.in=" + DEFAULT_ACCREDITATION_EMAIL + "," + UPDATED_ACCREDITATION_EMAIL);

        // Get all the accreditationList where accreditationEmail equals to UPDATED_ACCREDITATION_EMAIL
        defaultAccreditationShouldNotBeFound("accreditationEmail.in=" + UPDATED_ACCREDITATION_EMAIL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationEmail is not null
        defaultAccreditationShouldBeFound("accreditationEmail.specified=true");

        // Get all the accreditationList where accreditationEmail is null
        defaultAccreditationShouldNotBeFound("accreditationEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationEmailContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationEmail contains DEFAULT_ACCREDITATION_EMAIL
        defaultAccreditationShouldBeFound("accreditationEmail.contains=" + DEFAULT_ACCREDITATION_EMAIL);

        // Get all the accreditationList where accreditationEmail contains UPDATED_ACCREDITATION_EMAIL
        defaultAccreditationShouldNotBeFound("accreditationEmail.contains=" + UPDATED_ACCREDITATION_EMAIL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationEmailNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationEmail does not contain DEFAULT_ACCREDITATION_EMAIL
        defaultAccreditationShouldNotBeFound("accreditationEmail.doesNotContain=" + DEFAULT_ACCREDITATION_EMAIL);

        // Get all the accreditationList where accreditationEmail does not contain UPDATED_ACCREDITATION_EMAIL
        defaultAccreditationShouldBeFound("accreditationEmail.doesNotContain=" + UPDATED_ACCREDITATION_EMAIL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationTelIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationTel equals to DEFAULT_ACCREDITATION_TEL
        defaultAccreditationShouldBeFound("accreditationTel.equals=" + DEFAULT_ACCREDITATION_TEL);

        // Get all the accreditationList where accreditationTel equals to UPDATED_ACCREDITATION_TEL
        defaultAccreditationShouldNotBeFound("accreditationTel.equals=" + UPDATED_ACCREDITATION_TEL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationTelIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationTel in DEFAULT_ACCREDITATION_TEL or UPDATED_ACCREDITATION_TEL
        defaultAccreditationShouldBeFound("accreditationTel.in=" + DEFAULT_ACCREDITATION_TEL + "," + UPDATED_ACCREDITATION_TEL);

        // Get all the accreditationList where accreditationTel equals to UPDATED_ACCREDITATION_TEL
        defaultAccreditationShouldNotBeFound("accreditationTel.in=" + UPDATED_ACCREDITATION_TEL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationTel is not null
        defaultAccreditationShouldBeFound("accreditationTel.specified=true");

        // Get all the accreditationList where accreditationTel is null
        defaultAccreditationShouldNotBeFound("accreditationTel.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationTelContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationTel contains DEFAULT_ACCREDITATION_TEL
        defaultAccreditationShouldBeFound("accreditationTel.contains=" + DEFAULT_ACCREDITATION_TEL);

        // Get all the accreditationList where accreditationTel contains UPDATED_ACCREDITATION_TEL
        defaultAccreditationShouldNotBeFound("accreditationTel.contains=" + UPDATED_ACCREDITATION_TEL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationTelNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationTel does not contain DEFAULT_ACCREDITATION_TEL
        defaultAccreditationShouldNotBeFound("accreditationTel.doesNotContain=" + DEFAULT_ACCREDITATION_TEL);

        // Get all the accreditationList where accreditationTel does not contain UPDATED_ACCREDITATION_TEL
        defaultAccreditationShouldBeFound("accreditationTel.doesNotContain=" + UPDATED_ACCREDITATION_TEL);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCinIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCinId equals to DEFAULT_ACCREDITATION_CIN_ID
        defaultAccreditationShouldBeFound("accreditationCinId.equals=" + DEFAULT_ACCREDITATION_CIN_ID);

        // Get all the accreditationList where accreditationCinId equals to UPDATED_ACCREDITATION_CIN_ID
        defaultAccreditationShouldNotBeFound("accreditationCinId.equals=" + UPDATED_ACCREDITATION_CIN_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCinIdIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCinId in DEFAULT_ACCREDITATION_CIN_ID or UPDATED_ACCREDITATION_CIN_ID
        defaultAccreditationShouldBeFound("accreditationCinId.in=" + DEFAULT_ACCREDITATION_CIN_ID + "," + UPDATED_ACCREDITATION_CIN_ID);

        // Get all the accreditationList where accreditationCinId equals to UPDATED_ACCREDITATION_CIN_ID
        defaultAccreditationShouldNotBeFound("accreditationCinId.in=" + UPDATED_ACCREDITATION_CIN_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCinIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCinId is not null
        defaultAccreditationShouldBeFound("accreditationCinId.specified=true");

        // Get all the accreditationList where accreditationCinId is null
        defaultAccreditationShouldNotBeFound("accreditationCinId.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCinIdContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCinId contains DEFAULT_ACCREDITATION_CIN_ID
        defaultAccreditationShouldBeFound("accreditationCinId.contains=" + DEFAULT_ACCREDITATION_CIN_ID);

        // Get all the accreditationList where accreditationCinId contains UPDATED_ACCREDITATION_CIN_ID
        defaultAccreditationShouldNotBeFound("accreditationCinId.contains=" + UPDATED_ACCREDITATION_CIN_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCinIdNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCinId does not contain DEFAULT_ACCREDITATION_CIN_ID
        defaultAccreditationShouldNotBeFound("accreditationCinId.doesNotContain=" + DEFAULT_ACCREDITATION_CIN_ID);

        // Get all the accreditationList where accreditationCinId does not contain UPDATED_ACCREDITATION_CIN_ID
        defaultAccreditationShouldBeFound("accreditationCinId.doesNotContain=" + UPDATED_ACCREDITATION_CIN_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPasseportIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPasseportId equals to DEFAULT_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldBeFound("accreditationPasseportId.equals=" + DEFAULT_ACCREDITATION_PASSEPORT_ID);

        // Get all the accreditationList where accreditationPasseportId equals to UPDATED_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldNotBeFound("accreditationPasseportId.equals=" + UPDATED_ACCREDITATION_PASSEPORT_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPasseportIdIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPasseportId in DEFAULT_ACCREDITATION_PASSEPORT_ID or UPDATED_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldBeFound(
            "accreditationPasseportId.in=" + DEFAULT_ACCREDITATION_PASSEPORT_ID + "," + UPDATED_ACCREDITATION_PASSEPORT_ID
        );

        // Get all the accreditationList where accreditationPasseportId equals to UPDATED_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldNotBeFound("accreditationPasseportId.in=" + UPDATED_ACCREDITATION_PASSEPORT_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPasseportIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPasseportId is not null
        defaultAccreditationShouldBeFound("accreditationPasseportId.specified=true");

        // Get all the accreditationList where accreditationPasseportId is null
        defaultAccreditationShouldNotBeFound("accreditationPasseportId.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPasseportIdContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPasseportId contains DEFAULT_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldBeFound("accreditationPasseportId.contains=" + DEFAULT_ACCREDITATION_PASSEPORT_ID);

        // Get all the accreditationList where accreditationPasseportId contains UPDATED_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldNotBeFound("accreditationPasseportId.contains=" + UPDATED_ACCREDITATION_PASSEPORT_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPasseportIdNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPasseportId does not contain DEFAULT_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldNotBeFound("accreditationPasseportId.doesNotContain=" + DEFAULT_ACCREDITATION_PASSEPORT_ID);

        // Get all the accreditationList where accreditationPasseportId does not contain UPDATED_ACCREDITATION_PASSEPORT_ID
        defaultAccreditationShouldBeFound("accreditationPasseportId.doesNotContain=" + UPDATED_ACCREDITATION_PASSEPORT_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCartePresseIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCartePresseId equals to DEFAULT_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldBeFound("accreditationCartePresseId.equals=" + DEFAULT_ACCREDITATION_CARTE_PRESSE_ID);

        // Get all the accreditationList where accreditationCartePresseId equals to UPDATED_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldNotBeFound("accreditationCartePresseId.equals=" + UPDATED_ACCREDITATION_CARTE_PRESSE_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCartePresseIdIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCartePresseId in DEFAULT_ACCREDITATION_CARTE_PRESSE_ID or UPDATED_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldBeFound(
            "accreditationCartePresseId.in=" + DEFAULT_ACCREDITATION_CARTE_PRESSE_ID + "," + UPDATED_ACCREDITATION_CARTE_PRESSE_ID
        );

        // Get all the accreditationList where accreditationCartePresseId equals to UPDATED_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldNotBeFound("accreditationCartePresseId.in=" + UPDATED_ACCREDITATION_CARTE_PRESSE_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCartePresseIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCartePresseId is not null
        defaultAccreditationShouldBeFound("accreditationCartePresseId.specified=true");

        // Get all the accreditationList where accreditationCartePresseId is null
        defaultAccreditationShouldNotBeFound("accreditationCartePresseId.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCartePresseIdContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCartePresseId contains DEFAULT_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldBeFound("accreditationCartePresseId.contains=" + DEFAULT_ACCREDITATION_CARTE_PRESSE_ID);

        // Get all the accreditationList where accreditationCartePresseId contains UPDATED_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldNotBeFound("accreditationCartePresseId.contains=" + UPDATED_ACCREDITATION_CARTE_PRESSE_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCartePresseIdNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCartePresseId does not contain DEFAULT_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldNotBeFound("accreditationCartePresseId.doesNotContain=" + DEFAULT_ACCREDITATION_CARTE_PRESSE_ID);

        // Get all the accreditationList where accreditationCartePresseId does not contain UPDATED_ACCREDITATION_CARTE_PRESSE_ID
        defaultAccreditationShouldBeFound("accreditationCartePresseId.doesNotContain=" + UPDATED_ACCREDITATION_CARTE_PRESSE_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCarteProfessionnelleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCarteProfessionnelleId equals to DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldBeFound("accreditationCarteProfessionnelleId.equals=" + DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID);

        // Get all the accreditationList where accreditationCarteProfessionnelleId equals to UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldNotBeFound(
            "accreditationCarteProfessionnelleId.equals=" + UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        );
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCarteProfessionnelleIdIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCarteProfessionnelleId in DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID or UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldBeFound(
            "accreditationCarteProfessionnelleId.in=" +
            DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID +
            "," +
            UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        );

        // Get all the accreditationList where accreditationCarteProfessionnelleId equals to UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldNotBeFound("accreditationCarteProfessionnelleId.in=" + UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCarteProfessionnelleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCarteProfessionnelleId is not null
        defaultAccreditationShouldBeFound("accreditationCarteProfessionnelleId.specified=true");

        // Get all the accreditationList where accreditationCarteProfessionnelleId is null
        defaultAccreditationShouldNotBeFound("accreditationCarteProfessionnelleId.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCarteProfessionnelleIdContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCarteProfessionnelleId contains DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldBeFound("accreditationCarteProfessionnelleId.contains=" + DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID);

        // Get all the accreditationList where accreditationCarteProfessionnelleId contains UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldNotBeFound(
            "accreditationCarteProfessionnelleId.contains=" + UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        );
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCarteProfessionnelleIdNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCarteProfessionnelleId does not contain DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldNotBeFound(
            "accreditationCarteProfessionnelleId.doesNotContain=" + DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        );

        // Get all the accreditationList where accreditationCarteProfessionnelleId does not contain UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        defaultAccreditationShouldBeFound(
            "accreditationCarteProfessionnelleId.doesNotContain=" + UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID
        );
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreationDate equals to DEFAULT_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldBeFound("accreditationCreationDate.equals=" + DEFAULT_ACCREDITATION_CREATION_DATE);

        // Get all the accreditationList where accreditationCreationDate equals to UPDATED_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldNotBeFound("accreditationCreationDate.equals=" + UPDATED_ACCREDITATION_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreationDate in DEFAULT_ACCREDITATION_CREATION_DATE or UPDATED_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldBeFound(
            "accreditationCreationDate.in=" + DEFAULT_ACCREDITATION_CREATION_DATE + "," + UPDATED_ACCREDITATION_CREATION_DATE
        );

        // Get all the accreditationList where accreditationCreationDate equals to UPDATED_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldNotBeFound("accreditationCreationDate.in=" + UPDATED_ACCREDITATION_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreationDate is not null
        defaultAccreditationShouldBeFound("accreditationCreationDate.specified=true");

        // Get all the accreditationList where accreditationCreationDate is null
        defaultAccreditationShouldNotBeFound("accreditationCreationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreationDate is greater than or equal to DEFAULT_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldBeFound("accreditationCreationDate.greaterThanOrEqual=" + DEFAULT_ACCREDITATION_CREATION_DATE);

        // Get all the accreditationList where accreditationCreationDate is greater than or equal to UPDATED_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldNotBeFound("accreditationCreationDate.greaterThanOrEqual=" + UPDATED_ACCREDITATION_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreationDate is less than or equal to DEFAULT_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldBeFound("accreditationCreationDate.lessThanOrEqual=" + DEFAULT_ACCREDITATION_CREATION_DATE);

        // Get all the accreditationList where accreditationCreationDate is less than or equal to SMALLER_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldNotBeFound("accreditationCreationDate.lessThanOrEqual=" + SMALLER_ACCREDITATION_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreationDate is less than DEFAULT_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldNotBeFound("accreditationCreationDate.lessThan=" + DEFAULT_ACCREDITATION_CREATION_DATE);

        // Get all the accreditationList where accreditationCreationDate is less than UPDATED_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldBeFound("accreditationCreationDate.lessThan=" + UPDATED_ACCREDITATION_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreationDate is greater than DEFAULT_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldNotBeFound("accreditationCreationDate.greaterThan=" + DEFAULT_ACCREDITATION_CREATION_DATE);

        // Get all the accreditationList where accreditationCreationDate is greater than SMALLER_ACCREDITATION_CREATION_DATE
        defaultAccreditationShouldBeFound("accreditationCreationDate.greaterThan=" + SMALLER_ACCREDITATION_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationUpdateDate equals to DEFAULT_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldBeFound("accreditationUpdateDate.equals=" + DEFAULT_ACCREDITATION_UPDATE_DATE);

        // Get all the accreditationList where accreditationUpdateDate equals to UPDATED_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldNotBeFound("accreditationUpdateDate.equals=" + UPDATED_ACCREDITATION_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationUpdateDate in DEFAULT_ACCREDITATION_UPDATE_DATE or UPDATED_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldBeFound(
            "accreditationUpdateDate.in=" + DEFAULT_ACCREDITATION_UPDATE_DATE + "," + UPDATED_ACCREDITATION_UPDATE_DATE
        );

        // Get all the accreditationList where accreditationUpdateDate equals to UPDATED_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldNotBeFound("accreditationUpdateDate.in=" + UPDATED_ACCREDITATION_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationUpdateDate is not null
        defaultAccreditationShouldBeFound("accreditationUpdateDate.specified=true");

        // Get all the accreditationList where accreditationUpdateDate is null
        defaultAccreditationShouldNotBeFound("accreditationUpdateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationUpdateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationUpdateDate is greater than or equal to DEFAULT_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldBeFound("accreditationUpdateDate.greaterThanOrEqual=" + DEFAULT_ACCREDITATION_UPDATE_DATE);

        // Get all the accreditationList where accreditationUpdateDate is greater than or equal to UPDATED_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldNotBeFound("accreditationUpdateDate.greaterThanOrEqual=" + UPDATED_ACCREDITATION_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationUpdateDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationUpdateDate is less than or equal to DEFAULT_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldBeFound("accreditationUpdateDate.lessThanOrEqual=" + DEFAULT_ACCREDITATION_UPDATE_DATE);

        // Get all the accreditationList where accreditationUpdateDate is less than or equal to SMALLER_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldNotBeFound("accreditationUpdateDate.lessThanOrEqual=" + SMALLER_ACCREDITATION_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationUpdateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationUpdateDate is less than DEFAULT_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldNotBeFound("accreditationUpdateDate.lessThan=" + DEFAULT_ACCREDITATION_UPDATE_DATE);

        // Get all the accreditationList where accreditationUpdateDate is less than UPDATED_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldBeFound("accreditationUpdateDate.lessThan=" + UPDATED_ACCREDITATION_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationUpdateDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationUpdateDate is greater than DEFAULT_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldNotBeFound("accreditationUpdateDate.greaterThan=" + DEFAULT_ACCREDITATION_UPDATE_DATE);

        // Get all the accreditationList where accreditationUpdateDate is greater than SMALLER_ACCREDITATION_UPDATE_DATE
        defaultAccreditationShouldBeFound("accreditationUpdateDate.greaterThan=" + SMALLER_ACCREDITATION_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreatedByuserIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreatedByuser equals to DEFAULT_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldBeFound("accreditationCreatedByuser.equals=" + DEFAULT_ACCREDITATION_CREATED_BYUSER);

        // Get all the accreditationList where accreditationCreatedByuser equals to UPDATED_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldNotBeFound("accreditationCreatedByuser.equals=" + UPDATED_ACCREDITATION_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreatedByuserIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreatedByuser in DEFAULT_ACCREDITATION_CREATED_BYUSER or UPDATED_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldBeFound(
            "accreditationCreatedByuser.in=" + DEFAULT_ACCREDITATION_CREATED_BYUSER + "," + UPDATED_ACCREDITATION_CREATED_BYUSER
        );

        // Get all the accreditationList where accreditationCreatedByuser equals to UPDATED_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldNotBeFound("accreditationCreatedByuser.in=" + UPDATED_ACCREDITATION_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreatedByuserIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreatedByuser is not null
        defaultAccreditationShouldBeFound("accreditationCreatedByuser.specified=true");

        // Get all the accreditationList where accreditationCreatedByuser is null
        defaultAccreditationShouldNotBeFound("accreditationCreatedByuser.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreatedByuserContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreatedByuser contains DEFAULT_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldBeFound("accreditationCreatedByuser.contains=" + DEFAULT_ACCREDITATION_CREATED_BYUSER);

        // Get all the accreditationList where accreditationCreatedByuser contains UPDATED_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldNotBeFound("accreditationCreatedByuser.contains=" + UPDATED_ACCREDITATION_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationCreatedByuserNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationCreatedByuser does not contain DEFAULT_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldNotBeFound("accreditationCreatedByuser.doesNotContain=" + DEFAULT_ACCREDITATION_CREATED_BYUSER);

        // Get all the accreditationList where accreditationCreatedByuser does not contain UPDATED_ACCREDITATION_CREATED_BYUSER
        defaultAccreditationShouldBeFound("accreditationCreatedByuser.doesNotContain=" + UPDATED_ACCREDITATION_CREATED_BYUSER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateStartIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateStart equals to DEFAULT_ACCREDITATION_DATE_START
        defaultAccreditationShouldBeFound("accreditationDateStart.equals=" + DEFAULT_ACCREDITATION_DATE_START);

        // Get all the accreditationList where accreditationDateStart equals to UPDATED_ACCREDITATION_DATE_START
        defaultAccreditationShouldNotBeFound("accreditationDateStart.equals=" + UPDATED_ACCREDITATION_DATE_START);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateStartIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateStart in DEFAULT_ACCREDITATION_DATE_START or UPDATED_ACCREDITATION_DATE_START
        defaultAccreditationShouldBeFound(
            "accreditationDateStart.in=" + DEFAULT_ACCREDITATION_DATE_START + "," + UPDATED_ACCREDITATION_DATE_START
        );

        // Get all the accreditationList where accreditationDateStart equals to UPDATED_ACCREDITATION_DATE_START
        defaultAccreditationShouldNotBeFound("accreditationDateStart.in=" + UPDATED_ACCREDITATION_DATE_START);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateStart is not null
        defaultAccreditationShouldBeFound("accreditationDateStart.specified=true");

        // Get all the accreditationList where accreditationDateStart is null
        defaultAccreditationShouldNotBeFound("accreditationDateStart.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateStart is greater than or equal to DEFAULT_ACCREDITATION_DATE_START
        defaultAccreditationShouldBeFound("accreditationDateStart.greaterThanOrEqual=" + DEFAULT_ACCREDITATION_DATE_START);

        // Get all the accreditationList where accreditationDateStart is greater than or equal to UPDATED_ACCREDITATION_DATE_START
        defaultAccreditationShouldNotBeFound("accreditationDateStart.greaterThanOrEqual=" + UPDATED_ACCREDITATION_DATE_START);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateStart is less than or equal to DEFAULT_ACCREDITATION_DATE_START
        defaultAccreditationShouldBeFound("accreditationDateStart.lessThanOrEqual=" + DEFAULT_ACCREDITATION_DATE_START);

        // Get all the accreditationList where accreditationDateStart is less than or equal to SMALLER_ACCREDITATION_DATE_START
        defaultAccreditationShouldNotBeFound("accreditationDateStart.lessThanOrEqual=" + SMALLER_ACCREDITATION_DATE_START);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateStartIsLessThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateStart is less than DEFAULT_ACCREDITATION_DATE_START
        defaultAccreditationShouldNotBeFound("accreditationDateStart.lessThan=" + DEFAULT_ACCREDITATION_DATE_START);

        // Get all the accreditationList where accreditationDateStart is less than UPDATED_ACCREDITATION_DATE_START
        defaultAccreditationShouldBeFound("accreditationDateStart.lessThan=" + UPDATED_ACCREDITATION_DATE_START);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateStart is greater than DEFAULT_ACCREDITATION_DATE_START
        defaultAccreditationShouldNotBeFound("accreditationDateStart.greaterThan=" + DEFAULT_ACCREDITATION_DATE_START);

        // Get all the accreditationList where accreditationDateStart is greater than SMALLER_ACCREDITATION_DATE_START
        defaultAccreditationShouldBeFound("accreditationDateStart.greaterThan=" + SMALLER_ACCREDITATION_DATE_START);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateEndIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateEnd equals to DEFAULT_ACCREDITATION_DATE_END
        defaultAccreditationShouldBeFound("accreditationDateEnd.equals=" + DEFAULT_ACCREDITATION_DATE_END);

        // Get all the accreditationList where accreditationDateEnd equals to UPDATED_ACCREDITATION_DATE_END
        defaultAccreditationShouldNotBeFound("accreditationDateEnd.equals=" + UPDATED_ACCREDITATION_DATE_END);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateEndIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateEnd in DEFAULT_ACCREDITATION_DATE_END or UPDATED_ACCREDITATION_DATE_END
        defaultAccreditationShouldBeFound(
            "accreditationDateEnd.in=" + DEFAULT_ACCREDITATION_DATE_END + "," + UPDATED_ACCREDITATION_DATE_END
        );

        // Get all the accreditationList where accreditationDateEnd equals to UPDATED_ACCREDITATION_DATE_END
        defaultAccreditationShouldNotBeFound("accreditationDateEnd.in=" + UPDATED_ACCREDITATION_DATE_END);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateEnd is not null
        defaultAccreditationShouldBeFound("accreditationDateEnd.specified=true");

        // Get all the accreditationList where accreditationDateEnd is null
        defaultAccreditationShouldNotBeFound("accreditationDateEnd.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateEnd is greater than or equal to DEFAULT_ACCREDITATION_DATE_END
        defaultAccreditationShouldBeFound("accreditationDateEnd.greaterThanOrEqual=" + DEFAULT_ACCREDITATION_DATE_END);

        // Get all the accreditationList where accreditationDateEnd is greater than or equal to UPDATED_ACCREDITATION_DATE_END
        defaultAccreditationShouldNotBeFound("accreditationDateEnd.greaterThanOrEqual=" + UPDATED_ACCREDITATION_DATE_END);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateEnd is less than or equal to DEFAULT_ACCREDITATION_DATE_END
        defaultAccreditationShouldBeFound("accreditationDateEnd.lessThanOrEqual=" + DEFAULT_ACCREDITATION_DATE_END);

        // Get all the accreditationList where accreditationDateEnd is less than or equal to SMALLER_ACCREDITATION_DATE_END
        defaultAccreditationShouldNotBeFound("accreditationDateEnd.lessThanOrEqual=" + SMALLER_ACCREDITATION_DATE_END);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateEndIsLessThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateEnd is less than DEFAULT_ACCREDITATION_DATE_END
        defaultAccreditationShouldNotBeFound("accreditationDateEnd.lessThan=" + DEFAULT_ACCREDITATION_DATE_END);

        // Get all the accreditationList where accreditationDateEnd is less than UPDATED_ACCREDITATION_DATE_END
        defaultAccreditationShouldBeFound("accreditationDateEnd.lessThan=" + UPDATED_ACCREDITATION_DATE_END);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationDateEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationDateEnd is greater than DEFAULT_ACCREDITATION_DATE_END
        defaultAccreditationShouldNotBeFound("accreditationDateEnd.greaterThan=" + DEFAULT_ACCREDITATION_DATE_END);

        // Get all the accreditationList where accreditationDateEnd is greater than SMALLER_ACCREDITATION_DATE_END
        defaultAccreditationShouldBeFound("accreditationDateEnd.greaterThan=" + SMALLER_ACCREDITATION_DATE_END);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintStatIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintStat equals to DEFAULT_ACCREDITATION_PRINT_STAT
        defaultAccreditationShouldBeFound("accreditationPrintStat.equals=" + DEFAULT_ACCREDITATION_PRINT_STAT);

        // Get all the accreditationList where accreditationPrintStat equals to UPDATED_ACCREDITATION_PRINT_STAT
        defaultAccreditationShouldNotBeFound("accreditationPrintStat.equals=" + UPDATED_ACCREDITATION_PRINT_STAT);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintStatIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintStat in DEFAULT_ACCREDITATION_PRINT_STAT or UPDATED_ACCREDITATION_PRINT_STAT
        defaultAccreditationShouldBeFound(
            "accreditationPrintStat.in=" + DEFAULT_ACCREDITATION_PRINT_STAT + "," + UPDATED_ACCREDITATION_PRINT_STAT
        );

        // Get all the accreditationList where accreditationPrintStat equals to UPDATED_ACCREDITATION_PRINT_STAT
        defaultAccreditationShouldNotBeFound("accreditationPrintStat.in=" + UPDATED_ACCREDITATION_PRINT_STAT);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintStat is not null
        defaultAccreditationShouldBeFound("accreditationPrintStat.specified=true");

        // Get all the accreditationList where accreditationPrintStat is null
        defaultAccreditationShouldNotBeFound("accreditationPrintStat.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintNumber equals to DEFAULT_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldBeFound("accreditationPrintNumber.equals=" + DEFAULT_ACCREDITATION_PRINT_NUMBER);

        // Get all the accreditationList where accreditationPrintNumber equals to UPDATED_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldNotBeFound("accreditationPrintNumber.equals=" + UPDATED_ACCREDITATION_PRINT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintNumberIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintNumber in DEFAULT_ACCREDITATION_PRINT_NUMBER or UPDATED_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldBeFound(
            "accreditationPrintNumber.in=" + DEFAULT_ACCREDITATION_PRINT_NUMBER + "," + UPDATED_ACCREDITATION_PRINT_NUMBER
        );

        // Get all the accreditationList where accreditationPrintNumber equals to UPDATED_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldNotBeFound("accreditationPrintNumber.in=" + UPDATED_ACCREDITATION_PRINT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintNumber is not null
        defaultAccreditationShouldBeFound("accreditationPrintNumber.specified=true");

        // Get all the accreditationList where accreditationPrintNumber is null
        defaultAccreditationShouldNotBeFound("accreditationPrintNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintNumber is greater than or equal to DEFAULT_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldBeFound("accreditationPrintNumber.greaterThanOrEqual=" + DEFAULT_ACCREDITATION_PRINT_NUMBER);

        // Get all the accreditationList where accreditationPrintNumber is greater than or equal to UPDATED_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldNotBeFound("accreditationPrintNumber.greaterThanOrEqual=" + UPDATED_ACCREDITATION_PRINT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintNumber is less than or equal to DEFAULT_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldBeFound("accreditationPrintNumber.lessThanOrEqual=" + DEFAULT_ACCREDITATION_PRINT_NUMBER);

        // Get all the accreditationList where accreditationPrintNumber is less than or equal to SMALLER_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldNotBeFound("accreditationPrintNumber.lessThanOrEqual=" + SMALLER_ACCREDITATION_PRINT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintNumber is less than DEFAULT_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldNotBeFound("accreditationPrintNumber.lessThan=" + DEFAULT_ACCREDITATION_PRINT_NUMBER);

        // Get all the accreditationList where accreditationPrintNumber is less than UPDATED_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldBeFound("accreditationPrintNumber.lessThan=" + UPDATED_ACCREDITATION_PRINT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationPrintNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationPrintNumber is greater than DEFAULT_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldNotBeFound("accreditationPrintNumber.greaterThan=" + DEFAULT_ACCREDITATION_PRINT_NUMBER);

        // Get all the accreditationList where accreditationPrintNumber is greater than SMALLER_ACCREDITATION_PRINT_NUMBER
        defaultAccreditationShouldBeFound("accreditationPrintNumber.greaterThan=" + SMALLER_ACCREDITATION_PRINT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationParams equals to DEFAULT_ACCREDITATION_PARAMS
        defaultAccreditationShouldBeFound("accreditationParams.equals=" + DEFAULT_ACCREDITATION_PARAMS);

        // Get all the accreditationList where accreditationParams equals to UPDATED_ACCREDITATION_PARAMS
        defaultAccreditationShouldNotBeFound("accreditationParams.equals=" + UPDATED_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationParamsIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationParams in DEFAULT_ACCREDITATION_PARAMS or UPDATED_ACCREDITATION_PARAMS
        defaultAccreditationShouldBeFound("accreditationParams.in=" + DEFAULT_ACCREDITATION_PARAMS + "," + UPDATED_ACCREDITATION_PARAMS);

        // Get all the accreditationList where accreditationParams equals to UPDATED_ACCREDITATION_PARAMS
        defaultAccreditationShouldNotBeFound("accreditationParams.in=" + UPDATED_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationParams is not null
        defaultAccreditationShouldBeFound("accreditationParams.specified=true");

        // Get all the accreditationList where accreditationParams is null
        defaultAccreditationShouldNotBeFound("accreditationParams.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationParamsContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationParams contains DEFAULT_ACCREDITATION_PARAMS
        defaultAccreditationShouldBeFound("accreditationParams.contains=" + DEFAULT_ACCREDITATION_PARAMS);

        // Get all the accreditationList where accreditationParams contains UPDATED_ACCREDITATION_PARAMS
        defaultAccreditationShouldNotBeFound("accreditationParams.contains=" + UPDATED_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationParamsNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationParams does not contain DEFAULT_ACCREDITATION_PARAMS
        defaultAccreditationShouldNotBeFound("accreditationParams.doesNotContain=" + DEFAULT_ACCREDITATION_PARAMS);

        // Get all the accreditationList where accreditationParams does not contain UPDATED_ACCREDITATION_PARAMS
        defaultAccreditationShouldBeFound("accreditationParams.doesNotContain=" + UPDATED_ACCREDITATION_PARAMS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationAttributs equals to DEFAULT_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldBeFound("accreditationAttributs.equals=" + DEFAULT_ACCREDITATION_ATTRIBUTS);

        // Get all the accreditationList where accreditationAttributs equals to UPDATED_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldNotBeFound("accreditationAttributs.equals=" + UPDATED_ACCREDITATION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationAttributs in DEFAULT_ACCREDITATION_ATTRIBUTS or UPDATED_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldBeFound(
            "accreditationAttributs.in=" + DEFAULT_ACCREDITATION_ATTRIBUTS + "," + UPDATED_ACCREDITATION_ATTRIBUTS
        );

        // Get all the accreditationList where accreditationAttributs equals to UPDATED_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldNotBeFound("accreditationAttributs.in=" + UPDATED_ACCREDITATION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationAttributs is not null
        defaultAccreditationShouldBeFound("accreditationAttributs.specified=true");

        // Get all the accreditationList where accreditationAttributs is null
        defaultAccreditationShouldNotBeFound("accreditationAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationAttributsContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationAttributs contains DEFAULT_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldBeFound("accreditationAttributs.contains=" + DEFAULT_ACCREDITATION_ATTRIBUTS);

        // Get all the accreditationList where accreditationAttributs contains UPDATED_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldNotBeFound("accreditationAttributs.contains=" + UPDATED_ACCREDITATION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationAttributs does not contain DEFAULT_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldNotBeFound("accreditationAttributs.doesNotContain=" + DEFAULT_ACCREDITATION_ATTRIBUTS);

        // Get all the accreditationList where accreditationAttributs does not contain UPDATED_ACCREDITATION_ATTRIBUTS
        defaultAccreditationShouldBeFound("accreditationAttributs.doesNotContain=" + UPDATED_ACCREDITATION_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationStatIsEqualToSomething() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationStat equals to DEFAULT_ACCREDITATION_STAT
        defaultAccreditationShouldBeFound("accreditationStat.equals=" + DEFAULT_ACCREDITATION_STAT);

        // Get all the accreditationList where accreditationStat equals to UPDATED_ACCREDITATION_STAT
        defaultAccreditationShouldNotBeFound("accreditationStat.equals=" + UPDATED_ACCREDITATION_STAT);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationStatIsInShouldWork() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationStat in DEFAULT_ACCREDITATION_STAT or UPDATED_ACCREDITATION_STAT
        defaultAccreditationShouldBeFound("accreditationStat.in=" + DEFAULT_ACCREDITATION_STAT + "," + UPDATED_ACCREDITATION_STAT);

        // Get all the accreditationList where accreditationStat equals to UPDATED_ACCREDITATION_STAT
        defaultAccreditationShouldNotBeFound("accreditationStat.in=" + UPDATED_ACCREDITATION_STAT);
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList where accreditationStat is not null
        defaultAccreditationShouldBeFound("accreditationStat.specified=true");

        // Get all the accreditationList where accreditationStat is null
        defaultAccreditationShouldNotBeFound("accreditationStat.specified=false");
    }

    @Test
    @Transactional
    void getAllAccreditationsByPhotoArchiveIsEqualToSomething() throws Exception {
        PhotoArchive photoArchive;
        if (TestUtil.findAll(em, PhotoArchive.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            photoArchive = PhotoArchiveResourceIT.createEntity(em);
        } else {
            photoArchive = TestUtil.findAll(em, PhotoArchive.class).get(0);
        }
        em.persist(photoArchive);
        em.flush();
        accreditation.addPhotoArchive(photoArchive);
        accreditationRepository.saveAndFlush(accreditation);
        Long photoArchiveId = photoArchive.getPhotoArchiveId();

        // Get all the accreditationList where photoArchive equals to photoArchiveId
        defaultAccreditationShouldBeFound("photoArchiveId.equals=" + photoArchiveId);

        // Get all the accreditationList where photoArchive equals to (photoArchiveId + 1)
        defaultAccreditationShouldNotBeFound("photoArchiveId.equals=" + (photoArchiveId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByInfoSuppIsEqualToSomething() throws Exception {
        InfoSupp infoSupp;
        if (TestUtil.findAll(em, InfoSupp.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            infoSupp = InfoSuppResourceIT.createEntity(em);
        } else {
            infoSupp = TestUtil.findAll(em, InfoSupp.class).get(0);
        }
        em.persist(infoSupp);
        em.flush();
        accreditation.addInfoSupp(infoSupp);
        accreditationRepository.saveAndFlush(accreditation);
        Long infoSuppId = infoSupp.getInfoSuppId();

        // Get all the accreditationList where infoSupp equals to infoSuppId
        defaultAccreditationShouldBeFound("infoSuppId.equals=" + infoSuppId);

        // Get all the accreditationList where infoSupp equals to (infoSuppId + 1)
        defaultAccreditationShouldNotBeFound("infoSuppId.equals=" + (infoSuppId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByNoteIsEqualToSomething() throws Exception {
        Note note;
        if (TestUtil.findAll(em, Note.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            note = NoteResourceIT.createEntity(em);
        } else {
            note = TestUtil.findAll(em, Note.class).get(0);
        }
        em.persist(note);
        em.flush();
        accreditation.addNote(note);
        accreditationRepository.saveAndFlush(accreditation);
        Long noteId = note.getNoteId();

        // Get all the accreditationList where note equals to noteId
        defaultAccreditationShouldBeFound("noteId.equals=" + noteId);

        // Get all the accreditationList where note equals to (noteId + 1)
        defaultAccreditationShouldNotBeFound("noteId.equals=" + (noteId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByCheckAccreditationHistoryIsEqualToSomething() throws Exception {
        CheckAccreditationHistory checkAccreditationHistory;
        if (TestUtil.findAll(em, CheckAccreditationHistory.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            checkAccreditationHistory = CheckAccreditationHistoryResourceIT.createEntity(em);
        } else {
            checkAccreditationHistory = TestUtil.findAll(em, CheckAccreditationHistory.class).get(0);
        }
        em.persist(checkAccreditationHistory);
        em.flush();
        accreditation.addCheckAccreditationHistory(checkAccreditationHistory);
        accreditationRepository.saveAndFlush(accreditation);
        Long checkAccreditationHistoryId = checkAccreditationHistory.getCheckAccreditationHistoryId();

        // Get all the accreditationList where checkAccreditationHistory equals to checkAccreditationHistoryId
        defaultAccreditationShouldBeFound("checkAccreditationHistoryId.equals=" + checkAccreditationHistoryId);

        // Get all the accreditationList where checkAccreditationHistory equals to (checkAccreditationHistoryId + 1)
        defaultAccreditationShouldNotBeFound("checkAccreditationHistoryId.equals=" + (checkAccreditationHistoryId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsBySiteIsEqualToSomething() throws Exception {
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            site = SiteResourceIT.createEntity(em);
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(site);
        em.flush();
        accreditation.addSite(site);
        accreditationRepository.saveAndFlush(accreditation);
        Long siteId = site.getSiteId();

        // Get all the accreditationList where site equals to siteId
        defaultAccreditationShouldBeFound("siteId.equals=" + siteId);

        // Get all the accreditationList where site equals to (siteId + 1)
        defaultAccreditationShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        accreditation.setEvent(event);
        accreditationRepository.saveAndFlush(accreditation);
        Long eventId = event.getEventId();

        // Get all the accreditationList where event equals to eventId
        defaultAccreditationShouldBeFound("eventId.equals=" + eventId);

        // Get all the accreditationList where event equals to (eventId + 1)
        defaultAccreditationShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByCivilityIsEqualToSomething() throws Exception {
        Civility civility;
        if (TestUtil.findAll(em, Civility.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            civility = CivilityResourceIT.createEntity(em);
        } else {
            civility = TestUtil.findAll(em, Civility.class).get(0);
        }
        em.persist(civility);
        em.flush();
        accreditation.setCivility(civility);
        accreditationRepository.saveAndFlush(accreditation);
        Long civilityId = civility.getCivilityId();

        // Get all the accreditationList where civility equals to civilityId
        defaultAccreditationShouldBeFound("civilityId.equals=" + civilityId);

        // Get all the accreditationList where civility equals to (civilityId + 1)
        defaultAccreditationShouldNotBeFound("civilityId.equals=" + (civilityId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsBySexeIsEqualToSomething() throws Exception {
        Sexe sexe;
        if (TestUtil.findAll(em, Sexe.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            sexe = SexeResourceIT.createEntity(em);
        } else {
            sexe = TestUtil.findAll(em, Sexe.class).get(0);
        }
        em.persist(sexe);
        em.flush();
        accreditation.setSexe(sexe);
        accreditationRepository.saveAndFlush(accreditation);
        Long sexeId = sexe.getSexeId();

        // Get all the accreditationList where sexe equals to sexeId
        defaultAccreditationShouldBeFound("sexeId.equals=" + sexeId);

        // Get all the accreditationList where sexe equals to (sexeId + 1)
        defaultAccreditationShouldNotBeFound("sexeId.equals=" + (sexeId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByNationalityIsEqualToSomething() throws Exception {
        Nationality nationality;
        if (TestUtil.findAll(em, Nationality.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            nationality = NationalityResourceIT.createEntity(em);
        } else {
            nationality = TestUtil.findAll(em, Nationality.class).get(0);
        }
        em.persist(nationality);
        em.flush();
        accreditation.setNationality(nationality);
        accreditationRepository.saveAndFlush(accreditation);
        Long nationalityId = nationality.getNationalityId();

        // Get all the accreditationList where nationality equals to nationalityId
        defaultAccreditationShouldBeFound("nationalityId.equals=" + nationalityId);

        // Get all the accreditationList where nationality equals to (nationalityId + 1)
        defaultAccreditationShouldNotBeFound("nationalityId.equals=" + (nationalityId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        accreditation.setCountry(country);
        accreditationRepository.saveAndFlush(accreditation);
        Long countryId = country.getCountryId();

        // Get all the accreditationList where country equals to countryId
        defaultAccreditationShouldBeFound("countryId.equals=" + countryId);

        // Get all the accreditationList where country equals to (countryId + 1)
        defaultAccreditationShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        accreditation.setCity(city);
        accreditationRepository.saveAndFlush(accreditation);
        Long cityId = city.getCityId();

        // Get all the accreditationList where city equals to cityId
        defaultAccreditationShouldBeFound("cityId.equals=" + cityId);

        // Get all the accreditationList where city equals to (cityId + 1)
        defaultAccreditationShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        accreditation.setCategory(category);
        accreditationRepository.saveAndFlush(accreditation);
        Long categoryId = category.getCategoryId();

        // Get all the accreditationList where category equals to categoryId
        defaultAccreditationShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the accreditationList where category equals to (categoryId + 1)
        defaultAccreditationShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByFonctionIsEqualToSomething() throws Exception {
        Fonction fonction;
        if (TestUtil.findAll(em, Fonction.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            fonction = FonctionResourceIT.createEntity(em);
        } else {
            fonction = TestUtil.findAll(em, Fonction.class).get(0);
        }
        em.persist(fonction);
        em.flush();
        accreditation.setFonction(fonction);
        accreditationRepository.saveAndFlush(accreditation);
        Long fonctionId = fonction.getFonctionId();

        // Get all the accreditationList where fonction equals to fonctionId
        defaultAccreditationShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the accreditationList where fonction equals to (fonctionId + 1)
        defaultAccreditationShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByOrganizIsEqualToSomething() throws Exception {
        Organiz organiz;
        if (TestUtil.findAll(em, Organiz.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            organiz = OrganizResourceIT.createEntity(em);
        } else {
            organiz = TestUtil.findAll(em, Organiz.class).get(0);
        }
        em.persist(organiz);
        em.flush();
        accreditation.setOrganiz(organiz);
        accreditationRepository.saveAndFlush(accreditation);
        Long organizId = organiz.getOrganizId();

        // Get all the accreditationList where organiz equals to organizId
        defaultAccreditationShouldBeFound("organizId.equals=" + organizId);

        // Get all the accreditationList where organiz equals to (organizId + 1)
        defaultAccreditationShouldNotBeFound("organizId.equals=" + (organizId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByAccreditationTypeIsEqualToSomething() throws Exception {
        AccreditationType accreditationType;
        if (TestUtil.findAll(em, AccreditationType.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            accreditationType = AccreditationTypeResourceIT.createEntity(em);
        } else {
            accreditationType = TestUtil.findAll(em, AccreditationType.class).get(0);
        }
        em.persist(accreditationType);
        em.flush();
        accreditation.setAccreditationType(accreditationType);
        accreditationRepository.saveAndFlush(accreditation);
        Long accreditationTypeId = accreditationType.getAccreditationTypeId();

        // Get all the accreditationList where accreditationType equals to accreditationTypeId
        defaultAccreditationShouldBeFound("accreditationTypeId.equals=" + accreditationTypeId);

        // Get all the accreditationList where accreditationType equals to (accreditationTypeId + 1)
        defaultAccreditationShouldNotBeFound("accreditationTypeId.equals=" + (accreditationTypeId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByStatusIsEqualToSomething() throws Exception {
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            status = StatusResourceIT.createEntity(em);
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        em.persist(status);
        em.flush();
        accreditation.setStatus(status);
        accreditationRepository.saveAndFlush(accreditation);
        Long statusId = status.getStatusId();

        // Get all the accreditationList where status equals to statusId
        defaultAccreditationShouldBeFound("statusId.equals=" + statusId);

        // Get all the accreditationList where status equals to (statusId + 1)
        defaultAccreditationShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByAttachementIsEqualToSomething() throws Exception {
        Attachement attachement;
        if (TestUtil.findAll(em, Attachement.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            attachement = AttachementResourceIT.createEntity(em);
        } else {
            attachement = TestUtil.findAll(em, Attachement.class).get(0);
        }
        em.persist(attachement);
        em.flush();
        accreditation.setAttachement(attachement);
        accreditationRepository.saveAndFlush(accreditation);
        Long attachementId = attachement.getAttachementId();

        // Get all the accreditationList where attachement equals to attachementId
        defaultAccreditationShouldBeFound("attachementId.equals=" + attachementId);

        // Get all the accreditationList where attachement equals to (attachementId + 1)
        defaultAccreditationShouldNotBeFound("attachementId.equals=" + (attachementId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByCodeIsEqualToSomething() throws Exception {
        Code code;
        if (TestUtil.findAll(em, Code.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            code = CodeResourceIT.createEntity(em);
        } else {
            code = TestUtil.findAll(em, Code.class).get(0);
        }
        em.persist(code);
        em.flush();
        accreditation.setCode(code);
        accreditationRepository.saveAndFlush(accreditation);
        Long codeId = code.getCodeId();

        // Get all the accreditationList where code equals to codeId
        defaultAccreditationShouldBeFound("codeId.equals=" + codeId);

        // Get all the accreditationList where code equals to (codeId + 1)
        defaultAccreditationShouldNotBeFound("codeId.equals=" + (codeId + 1));
    }

    @Test
    @Transactional
    void getAllAccreditationsByDayPassInfoIsEqualToSomething() throws Exception {
        DayPassInfo dayPassInfo;
        if (TestUtil.findAll(em, DayPassInfo.class).isEmpty()) {
            accreditationRepository.saveAndFlush(accreditation);
            dayPassInfo = DayPassInfoResourceIT.createEntity(em);
        } else {
            dayPassInfo = TestUtil.findAll(em, DayPassInfo.class).get(0);
        }
        em.persist(dayPassInfo);
        em.flush();
        accreditation.setDayPassInfo(dayPassInfo);
        accreditationRepository.saveAndFlush(accreditation);
        Long dayPassInfoId = dayPassInfo.getDayPassInfoId();

        // Get all the accreditationList where dayPassInfo equals to dayPassInfoId
        defaultAccreditationShouldBeFound("dayPassInfoId.equals=" + dayPassInfoId);

        // Get all the accreditationList where dayPassInfo equals to (dayPassInfoId + 1)
        defaultAccreditationShouldNotBeFound("dayPassInfoId.equals=" + (dayPassInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccreditationShouldBeFound(String filter) throws Exception {
        restAccreditationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accreditationId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].accreditationId").value(hasItem(accreditation.getAccreditationId().intValue())))
            .andExpect(jsonPath("$.[*].accreditationFirstName").value(hasItem(DEFAULT_ACCREDITATION_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].accreditationSecondName").value(hasItem(DEFAULT_ACCREDITATION_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].accreditationLastName").value(hasItem(DEFAULT_ACCREDITATION_LAST_NAME)))
            .andExpect(jsonPath("$.[*].accreditationBirthDay").value(hasItem(DEFAULT_ACCREDITATION_BIRTH_DAY.toString())))
            .andExpect(jsonPath("$.[*].accreditationSexe").value(hasItem(DEFAULT_ACCREDITATION_SEXE)))
            .andExpect(jsonPath("$.[*].accreditationOccupation").value(hasItem(DEFAULT_ACCREDITATION_OCCUPATION)))
            .andExpect(jsonPath("$.[*].accreditationDescription").value(hasItem(DEFAULT_ACCREDITATION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].accreditationEmail").value(hasItem(DEFAULT_ACCREDITATION_EMAIL)))
            .andExpect(jsonPath("$.[*].accreditationTel").value(hasItem(DEFAULT_ACCREDITATION_TEL)))
            .andExpect(jsonPath("$.[*].accreditationPhotoContentType").value(hasItem(DEFAULT_ACCREDITATION_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].accreditationPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACCREDITATION_PHOTO))))
            .andExpect(jsonPath("$.[*].accreditationCinId").value(hasItem(DEFAULT_ACCREDITATION_CIN_ID)))
            .andExpect(jsonPath("$.[*].accreditationPasseportId").value(hasItem(DEFAULT_ACCREDITATION_PASSEPORT_ID)))
            .andExpect(jsonPath("$.[*].accreditationCartePresseId").value(hasItem(DEFAULT_ACCREDITATION_CARTE_PRESSE_ID)))
            .andExpect(jsonPath("$.[*].accreditationCarteProfessionnelleId").value(hasItem(DEFAULT_ACCREDITATION_CARTE_PROFESSIONNELLE_ID)))
            .andExpect(jsonPath("$.[*].accreditationCreationDate").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].accreditationUpdateDate").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].accreditationCreatedByuser").value(hasItem(DEFAULT_ACCREDITATION_CREATED_BYUSER)))
            .andExpect(jsonPath("$.[*].accreditationDateStart").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_DATE_START))))
            .andExpect(jsonPath("$.[*].accreditationDateEnd").value(hasItem(sameInstant(DEFAULT_ACCREDITATION_DATE_END))))
            .andExpect(jsonPath("$.[*].accreditationPrintStat").value(hasItem(DEFAULT_ACCREDITATION_PRINT_STAT.booleanValue())))
            .andExpect(jsonPath("$.[*].accreditationPrintNumber").value(hasItem(DEFAULT_ACCREDITATION_PRINT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].accreditationParams").value(hasItem(DEFAULT_ACCREDITATION_PARAMS)))
            .andExpect(jsonPath("$.[*].accreditationAttributs").value(hasItem(DEFAULT_ACCREDITATION_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].accreditationStat").value(hasItem(DEFAULT_ACCREDITATION_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restAccreditationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=accreditationId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccreditationShouldNotBeFound(String filter) throws Exception {
        restAccreditationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accreditationId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccreditationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=accreditationId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccreditation() throws Exception {
        // Get the accreditation
        restAccreditationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();

        // Update the accreditation
        Accreditation updatedAccreditation = accreditationRepository.findById(accreditation.getAccreditationId()).get();
        // Disconnect from session so that the updates on updatedAccreditation are not directly saved in db
        em.detach(updatedAccreditation);
        updatedAccreditation
            .accreditationFirstName(UPDATED_ACCREDITATION_FIRST_NAME)
            .accreditationSecondName(UPDATED_ACCREDITATION_SECOND_NAME)
            .accreditationLastName(UPDATED_ACCREDITATION_LAST_NAME)
            .accreditationBirthDay(UPDATED_ACCREDITATION_BIRTH_DAY)
            .accreditationSexe(UPDATED_ACCREDITATION_SEXE)
            .accreditationOccupation(UPDATED_ACCREDITATION_OCCUPATION)
            .accreditationDescription(UPDATED_ACCREDITATION_DESCRIPTION)
            .accreditationEmail(UPDATED_ACCREDITATION_EMAIL)
            .accreditationTel(UPDATED_ACCREDITATION_TEL)
            .accreditationPhoto(UPDATED_ACCREDITATION_PHOTO)
            .accreditationPhotoContentType(UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE)
            .accreditationCinId(UPDATED_ACCREDITATION_CIN_ID)
            .accreditationPasseportId(UPDATED_ACCREDITATION_PASSEPORT_ID)
            .accreditationCartePresseId(UPDATED_ACCREDITATION_CARTE_PRESSE_ID)
            .accreditationCarteProfessionnelleId(UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID)
            .accreditationCreationDate(UPDATED_ACCREDITATION_CREATION_DATE)
            .accreditationUpdateDate(UPDATED_ACCREDITATION_UPDATE_DATE)
            .accreditationCreatedByuser(UPDATED_ACCREDITATION_CREATED_BYUSER)
            .accreditationDateStart(UPDATED_ACCREDITATION_DATE_START)
            .accreditationDateEnd(UPDATED_ACCREDITATION_DATE_END)
            .accreditationPrintStat(UPDATED_ACCREDITATION_PRINT_STAT)
            .accreditationPrintNumber(UPDATED_ACCREDITATION_PRINT_NUMBER)
            .accreditationParams(UPDATED_ACCREDITATION_PARAMS)
            .accreditationAttributs(UPDATED_ACCREDITATION_ATTRIBUTS)
            .accreditationStat(UPDATED_ACCREDITATION_STAT);
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(updatedAccreditation);

        restAccreditationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accreditationDTO.getAccreditationId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getAccreditationFirstName()).isEqualTo(UPDATED_ACCREDITATION_FIRST_NAME);
        assertThat(testAccreditation.getAccreditationSecondName()).isEqualTo(UPDATED_ACCREDITATION_SECOND_NAME);
        assertThat(testAccreditation.getAccreditationLastName()).isEqualTo(UPDATED_ACCREDITATION_LAST_NAME);
        assertThat(testAccreditation.getAccreditationBirthDay()).isEqualTo(UPDATED_ACCREDITATION_BIRTH_DAY);
        assertThat(testAccreditation.getAccreditationSexe()).isEqualTo(UPDATED_ACCREDITATION_SEXE);
        assertThat(testAccreditation.getAccreditationOccupation()).isEqualTo(UPDATED_ACCREDITATION_OCCUPATION);
        assertThat(testAccreditation.getAccreditationDescription()).isEqualTo(UPDATED_ACCREDITATION_DESCRIPTION);
        assertThat(testAccreditation.getAccreditationEmail()).isEqualTo(UPDATED_ACCREDITATION_EMAIL);
        assertThat(testAccreditation.getAccreditationTel()).isEqualTo(UPDATED_ACCREDITATION_TEL);
        assertThat(testAccreditation.getAccreditationPhoto()).isEqualTo(UPDATED_ACCREDITATION_PHOTO);
        assertThat(testAccreditation.getAccreditationPhotoContentType()).isEqualTo(UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE);
        assertThat(testAccreditation.getAccreditationCinId()).isEqualTo(UPDATED_ACCREDITATION_CIN_ID);
        assertThat(testAccreditation.getAccreditationPasseportId()).isEqualTo(UPDATED_ACCREDITATION_PASSEPORT_ID);
        assertThat(testAccreditation.getAccreditationCartePresseId()).isEqualTo(UPDATED_ACCREDITATION_CARTE_PRESSE_ID);
        assertThat(testAccreditation.getAccreditationCarteProfessionnelleId()).isEqualTo(UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID);
        assertThat(testAccreditation.getAccreditationCreationDate()).isEqualTo(UPDATED_ACCREDITATION_CREATION_DATE);
        assertThat(testAccreditation.getAccreditationUpdateDate()).isEqualTo(UPDATED_ACCREDITATION_UPDATE_DATE);
        assertThat(testAccreditation.getAccreditationCreatedByuser()).isEqualTo(UPDATED_ACCREDITATION_CREATED_BYUSER);
        assertThat(testAccreditation.getAccreditationDateStart()).isEqualTo(UPDATED_ACCREDITATION_DATE_START);
        assertThat(testAccreditation.getAccreditationDateEnd()).isEqualTo(UPDATED_ACCREDITATION_DATE_END);
        assertThat(testAccreditation.getAccreditationPrintStat()).isEqualTo(UPDATED_ACCREDITATION_PRINT_STAT);
        assertThat(testAccreditation.getAccreditationPrintNumber()).isEqualTo(UPDATED_ACCREDITATION_PRINT_NUMBER);
        assertThat(testAccreditation.getAccreditationParams()).isEqualTo(UPDATED_ACCREDITATION_PARAMS);
        assertThat(testAccreditation.getAccreditationAttributs()).isEqualTo(UPDATED_ACCREDITATION_ATTRIBUTS);
        assertThat(testAccreditation.getAccreditationStat()).isEqualTo(UPDATED_ACCREDITATION_STAT);
    }

    @Test
    @Transactional
    void putNonExistingAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();
        accreditation.setAccreditationId(count.incrementAndGet());

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accreditationDTO.getAccreditationId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();
        accreditation.setAccreditationId(count.incrementAndGet());

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();
        accreditation.setAccreditationId(count.incrementAndGet());

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccreditationWithPatch() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();

        // Update the accreditation using partial update
        Accreditation partialUpdatedAccreditation = new Accreditation();
        partialUpdatedAccreditation.setAccreditationId(accreditation.getAccreditationId());

        partialUpdatedAccreditation
            .accreditationSecondName(UPDATED_ACCREDITATION_SECOND_NAME)
            .accreditationBirthDay(UPDATED_ACCREDITATION_BIRTH_DAY)
            .accreditationDescription(UPDATED_ACCREDITATION_DESCRIPTION)
            .accreditationEmail(UPDATED_ACCREDITATION_EMAIL)
            .accreditationPhoto(UPDATED_ACCREDITATION_PHOTO)
            .accreditationPhotoContentType(UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE)
            .accreditationPasseportId(UPDATED_ACCREDITATION_PASSEPORT_ID)
            .accreditationCartePresseId(UPDATED_ACCREDITATION_CARTE_PRESSE_ID)
            .accreditationCarteProfessionnelleId(UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID)
            .accreditationUpdateDate(UPDATED_ACCREDITATION_UPDATE_DATE)
            .accreditationDateStart(UPDATED_ACCREDITATION_DATE_START)
            .accreditationDateEnd(UPDATED_ACCREDITATION_DATE_END)
            .accreditationPrintStat(UPDATED_ACCREDITATION_PRINT_STAT)
            .accreditationPrintNumber(UPDATED_ACCREDITATION_PRINT_NUMBER)
            .accreditationParams(UPDATED_ACCREDITATION_PARAMS);

        restAccreditationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccreditation.getAccreditationId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccreditation))
            )
            .andExpect(status().isOk());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getAccreditationFirstName()).isEqualTo(DEFAULT_ACCREDITATION_FIRST_NAME);
        assertThat(testAccreditation.getAccreditationSecondName()).isEqualTo(UPDATED_ACCREDITATION_SECOND_NAME);
        assertThat(testAccreditation.getAccreditationLastName()).isEqualTo(DEFAULT_ACCREDITATION_LAST_NAME);
        assertThat(testAccreditation.getAccreditationBirthDay()).isEqualTo(UPDATED_ACCREDITATION_BIRTH_DAY);
        assertThat(testAccreditation.getAccreditationSexe()).isEqualTo(DEFAULT_ACCREDITATION_SEXE);
        assertThat(testAccreditation.getAccreditationOccupation()).isEqualTo(DEFAULT_ACCREDITATION_OCCUPATION);
        assertThat(testAccreditation.getAccreditationDescription()).isEqualTo(UPDATED_ACCREDITATION_DESCRIPTION);
        assertThat(testAccreditation.getAccreditationEmail()).isEqualTo(UPDATED_ACCREDITATION_EMAIL);
        assertThat(testAccreditation.getAccreditationTel()).isEqualTo(DEFAULT_ACCREDITATION_TEL);
        assertThat(testAccreditation.getAccreditationPhoto()).isEqualTo(UPDATED_ACCREDITATION_PHOTO);
        assertThat(testAccreditation.getAccreditationPhotoContentType()).isEqualTo(UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE);
        assertThat(testAccreditation.getAccreditationCinId()).isEqualTo(DEFAULT_ACCREDITATION_CIN_ID);
        assertThat(testAccreditation.getAccreditationPasseportId()).isEqualTo(UPDATED_ACCREDITATION_PASSEPORT_ID);
        assertThat(testAccreditation.getAccreditationCartePresseId()).isEqualTo(UPDATED_ACCREDITATION_CARTE_PRESSE_ID);
        assertThat(testAccreditation.getAccreditationCarteProfessionnelleId()).isEqualTo(UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID);
        assertThat(testAccreditation.getAccreditationCreationDate()).isEqualTo(DEFAULT_ACCREDITATION_CREATION_DATE);
        assertThat(testAccreditation.getAccreditationUpdateDate()).isEqualTo(UPDATED_ACCREDITATION_UPDATE_DATE);
        assertThat(testAccreditation.getAccreditationCreatedByuser()).isEqualTo(DEFAULT_ACCREDITATION_CREATED_BYUSER);
        assertThat(testAccreditation.getAccreditationDateStart()).isEqualTo(UPDATED_ACCREDITATION_DATE_START);
        assertThat(testAccreditation.getAccreditationDateEnd()).isEqualTo(UPDATED_ACCREDITATION_DATE_END);
        assertThat(testAccreditation.getAccreditationPrintStat()).isEqualTo(UPDATED_ACCREDITATION_PRINT_STAT);
        assertThat(testAccreditation.getAccreditationPrintNumber()).isEqualTo(UPDATED_ACCREDITATION_PRINT_NUMBER);
        assertThat(testAccreditation.getAccreditationParams()).isEqualTo(UPDATED_ACCREDITATION_PARAMS);
        assertThat(testAccreditation.getAccreditationAttributs()).isEqualTo(DEFAULT_ACCREDITATION_ATTRIBUTS);
        assertThat(testAccreditation.getAccreditationStat()).isEqualTo(DEFAULT_ACCREDITATION_STAT);
    }

    @Test
    @Transactional
    void fullUpdateAccreditationWithPatch() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();

        // Update the accreditation using partial update
        Accreditation partialUpdatedAccreditation = new Accreditation();
        partialUpdatedAccreditation.setAccreditationId(accreditation.getAccreditationId());

        partialUpdatedAccreditation
            .accreditationFirstName(UPDATED_ACCREDITATION_FIRST_NAME)
            .accreditationSecondName(UPDATED_ACCREDITATION_SECOND_NAME)
            .accreditationLastName(UPDATED_ACCREDITATION_LAST_NAME)
            .accreditationBirthDay(UPDATED_ACCREDITATION_BIRTH_DAY)
            .accreditationSexe(UPDATED_ACCREDITATION_SEXE)
            .accreditationOccupation(UPDATED_ACCREDITATION_OCCUPATION)
            .accreditationDescription(UPDATED_ACCREDITATION_DESCRIPTION)
            .accreditationEmail(UPDATED_ACCREDITATION_EMAIL)
            .accreditationTel(UPDATED_ACCREDITATION_TEL)
            .accreditationPhoto(UPDATED_ACCREDITATION_PHOTO)
            .accreditationPhotoContentType(UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE)
            .accreditationCinId(UPDATED_ACCREDITATION_CIN_ID)
            .accreditationPasseportId(UPDATED_ACCREDITATION_PASSEPORT_ID)
            .accreditationCartePresseId(UPDATED_ACCREDITATION_CARTE_PRESSE_ID)
            .accreditationCarteProfessionnelleId(UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID)
            .accreditationCreationDate(UPDATED_ACCREDITATION_CREATION_DATE)
            .accreditationUpdateDate(UPDATED_ACCREDITATION_UPDATE_DATE)
            .accreditationCreatedByuser(UPDATED_ACCREDITATION_CREATED_BYUSER)
            .accreditationDateStart(UPDATED_ACCREDITATION_DATE_START)
            .accreditationDateEnd(UPDATED_ACCREDITATION_DATE_END)
            .accreditationPrintStat(UPDATED_ACCREDITATION_PRINT_STAT)
            .accreditationPrintNumber(UPDATED_ACCREDITATION_PRINT_NUMBER)
            .accreditationParams(UPDATED_ACCREDITATION_PARAMS)
            .accreditationAttributs(UPDATED_ACCREDITATION_ATTRIBUTS)
            .accreditationStat(UPDATED_ACCREDITATION_STAT);

        restAccreditationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccreditation.getAccreditationId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccreditation))
            )
            .andExpect(status().isOk());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getAccreditationFirstName()).isEqualTo(UPDATED_ACCREDITATION_FIRST_NAME);
        assertThat(testAccreditation.getAccreditationSecondName()).isEqualTo(UPDATED_ACCREDITATION_SECOND_NAME);
        assertThat(testAccreditation.getAccreditationLastName()).isEqualTo(UPDATED_ACCREDITATION_LAST_NAME);
        assertThat(testAccreditation.getAccreditationBirthDay()).isEqualTo(UPDATED_ACCREDITATION_BIRTH_DAY);
        assertThat(testAccreditation.getAccreditationSexe()).isEqualTo(UPDATED_ACCREDITATION_SEXE);
        assertThat(testAccreditation.getAccreditationOccupation()).isEqualTo(UPDATED_ACCREDITATION_OCCUPATION);
        assertThat(testAccreditation.getAccreditationDescription()).isEqualTo(UPDATED_ACCREDITATION_DESCRIPTION);
        assertThat(testAccreditation.getAccreditationEmail()).isEqualTo(UPDATED_ACCREDITATION_EMAIL);
        assertThat(testAccreditation.getAccreditationTel()).isEqualTo(UPDATED_ACCREDITATION_TEL);
        assertThat(testAccreditation.getAccreditationPhoto()).isEqualTo(UPDATED_ACCREDITATION_PHOTO);
        assertThat(testAccreditation.getAccreditationPhotoContentType()).isEqualTo(UPDATED_ACCREDITATION_PHOTO_CONTENT_TYPE);
        assertThat(testAccreditation.getAccreditationCinId()).isEqualTo(UPDATED_ACCREDITATION_CIN_ID);
        assertThat(testAccreditation.getAccreditationPasseportId()).isEqualTo(UPDATED_ACCREDITATION_PASSEPORT_ID);
        assertThat(testAccreditation.getAccreditationCartePresseId()).isEqualTo(UPDATED_ACCREDITATION_CARTE_PRESSE_ID);
        assertThat(testAccreditation.getAccreditationCarteProfessionnelleId()).isEqualTo(UPDATED_ACCREDITATION_CARTE_PROFESSIONNELLE_ID);
        assertThat(testAccreditation.getAccreditationCreationDate()).isEqualTo(UPDATED_ACCREDITATION_CREATION_DATE);
        assertThat(testAccreditation.getAccreditationUpdateDate()).isEqualTo(UPDATED_ACCREDITATION_UPDATE_DATE);
        assertThat(testAccreditation.getAccreditationCreatedByuser()).isEqualTo(UPDATED_ACCREDITATION_CREATED_BYUSER);
        assertThat(testAccreditation.getAccreditationDateStart()).isEqualTo(UPDATED_ACCREDITATION_DATE_START);
        assertThat(testAccreditation.getAccreditationDateEnd()).isEqualTo(UPDATED_ACCREDITATION_DATE_END);
        assertThat(testAccreditation.getAccreditationPrintStat()).isEqualTo(UPDATED_ACCREDITATION_PRINT_STAT);
        assertThat(testAccreditation.getAccreditationPrintNumber()).isEqualTo(UPDATED_ACCREDITATION_PRINT_NUMBER);
        assertThat(testAccreditation.getAccreditationParams()).isEqualTo(UPDATED_ACCREDITATION_PARAMS);
        assertThat(testAccreditation.getAccreditationAttributs()).isEqualTo(UPDATED_ACCREDITATION_ATTRIBUTS);
        assertThat(testAccreditation.getAccreditationStat()).isEqualTo(UPDATED_ACCREDITATION_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();
        accreditation.setAccreditationId(count.incrementAndGet());

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accreditationDTO.getAccreditationId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();
        accreditation.setAccreditationId(count.incrementAndGet());

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();
        accreditation.setAccreditationId(count.incrementAndGet());

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccreditationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accreditationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeDelete = accreditationRepository.findAll().size();

        // Delete the accreditation
        restAccreditationMockMvc
            .perform(delete(ENTITY_API_URL_ID, accreditation.getAccreditationId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
