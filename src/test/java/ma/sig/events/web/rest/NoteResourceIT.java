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
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Note;
import ma.sig.events.repository.NoteRepository;
import ma.sig.events.service.criteria.NoteCriteria;
import ma.sig.events.service.dto.NoteDTO;
import ma.sig.events.service.mapper.NoteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoteResourceIT {

    private static final String DEFAULT_NOTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE_TYPE_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_TYPE_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE_TYPE_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_TYPE_ATTRIBUTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTE_STAT = false;
    private static final Boolean UPDATED_NOTE_STAT = true;

    private static final String ENTITY_API_URL = "/api/notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{noteId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoteMockMvc;

    private Note note;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createEntity(EntityManager em) {
        Note note = new Note()
            .noteValue(DEFAULT_NOTE_VALUE)
            .noteDescription(DEFAULT_NOTE_DESCRIPTION)
            .noteTypeParams(DEFAULT_NOTE_TYPE_PARAMS)
            .noteTypeAttributs(DEFAULT_NOTE_TYPE_ATTRIBUTS)
            .noteStat(DEFAULT_NOTE_STAT);
        return note;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createUpdatedEntity(EntityManager em) {
        Note note = new Note()
            .noteValue(UPDATED_NOTE_VALUE)
            .noteDescription(UPDATED_NOTE_DESCRIPTION)
            .noteTypeParams(UPDATED_NOTE_TYPE_PARAMS)
            .noteTypeAttributs(UPDATED_NOTE_TYPE_ATTRIBUTS)
            .noteStat(UPDATED_NOTE_STAT);
        return note;
    }

    @BeforeEach
    public void initTest() {
        note = createEntity(em);
    }

    @Test
    @Transactional
    void createNote() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();
        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);
        restNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isCreated());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate + 1);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getNoteValue()).isEqualTo(DEFAULT_NOTE_VALUE);
        assertThat(testNote.getNoteDescription()).isEqualTo(DEFAULT_NOTE_DESCRIPTION);
        assertThat(testNote.getNoteTypeParams()).isEqualTo(DEFAULT_NOTE_TYPE_PARAMS);
        assertThat(testNote.getNoteTypeAttributs()).isEqualTo(DEFAULT_NOTE_TYPE_ATTRIBUTS);
        assertThat(testNote.getNoteStat()).isEqualTo(DEFAULT_NOTE_STAT);
    }

    @Test
    @Transactional
    void createNoteWithExistingId() throws Exception {
        // Create the Note with an existing ID
        note.setNoteId(1L);
        NoteDTO noteDTO = noteMapper.toDto(note);

        int databaseSizeBeforeCreate = noteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNoteValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setNoteValue(null);

        // Create the Note, which fails.
        NoteDTO noteDTO = noteMapper.toDto(note);

        restNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isBadRequest());

        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotes() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=noteId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].noteId").value(hasItem(note.getNoteId().intValue())))
            .andExpect(jsonPath("$.[*].noteValue").value(hasItem(DEFAULT_NOTE_VALUE)))
            .andExpect(jsonPath("$.[*].noteDescription").value(hasItem(DEFAULT_NOTE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].noteTypeParams").value(hasItem(DEFAULT_NOTE_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].noteTypeAttributs").value(hasItem(DEFAULT_NOTE_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].noteStat").value(hasItem(DEFAULT_NOTE_STAT.booleanValue())));
    }

    @Test
    @Transactional
    void getNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get the note
        restNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, note.getNoteId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.noteId").value(note.getNoteId().intValue()))
            .andExpect(jsonPath("$.noteValue").value(DEFAULT_NOTE_VALUE))
            .andExpect(jsonPath("$.noteDescription").value(DEFAULT_NOTE_DESCRIPTION))
            .andExpect(jsonPath("$.noteTypeParams").value(DEFAULT_NOTE_TYPE_PARAMS))
            .andExpect(jsonPath("$.noteTypeAttributs").value(DEFAULT_NOTE_TYPE_ATTRIBUTS))
            .andExpect(jsonPath("$.noteStat").value(DEFAULT_NOTE_STAT.booleanValue()));
    }

    @Test
    @Transactional
    void getNotesByIdFiltering() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        Long id = note.getNoteId();

        defaultNoteShouldBeFound("noteId.equals=" + id);
        defaultNoteShouldNotBeFound("noteId.notEquals=" + id);

        defaultNoteShouldBeFound("noteId.greaterThanOrEqual=" + id);
        defaultNoteShouldNotBeFound("noteId.greaterThan=" + id);

        defaultNoteShouldBeFound("noteId.lessThanOrEqual=" + id);
        defaultNoteShouldNotBeFound("noteId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotesByNoteValueIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteValue equals to DEFAULT_NOTE_VALUE
        defaultNoteShouldBeFound("noteValue.equals=" + DEFAULT_NOTE_VALUE);

        // Get all the noteList where noteValue equals to UPDATED_NOTE_VALUE
        defaultNoteShouldNotBeFound("noteValue.equals=" + UPDATED_NOTE_VALUE);
    }

    @Test
    @Transactional
    void getAllNotesByNoteValueIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteValue in DEFAULT_NOTE_VALUE or UPDATED_NOTE_VALUE
        defaultNoteShouldBeFound("noteValue.in=" + DEFAULT_NOTE_VALUE + "," + UPDATED_NOTE_VALUE);

        // Get all the noteList where noteValue equals to UPDATED_NOTE_VALUE
        defaultNoteShouldNotBeFound("noteValue.in=" + UPDATED_NOTE_VALUE);
    }

    @Test
    @Transactional
    void getAllNotesByNoteValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteValue is not null
        defaultNoteShouldBeFound("noteValue.specified=true");

        // Get all the noteList where noteValue is null
        defaultNoteShouldNotBeFound("noteValue.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByNoteValueContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteValue contains DEFAULT_NOTE_VALUE
        defaultNoteShouldBeFound("noteValue.contains=" + DEFAULT_NOTE_VALUE);

        // Get all the noteList where noteValue contains UPDATED_NOTE_VALUE
        defaultNoteShouldNotBeFound("noteValue.contains=" + UPDATED_NOTE_VALUE);
    }

    @Test
    @Transactional
    void getAllNotesByNoteValueNotContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteValue does not contain DEFAULT_NOTE_VALUE
        defaultNoteShouldNotBeFound("noteValue.doesNotContain=" + DEFAULT_NOTE_VALUE);

        // Get all the noteList where noteValue does not contain UPDATED_NOTE_VALUE
        defaultNoteShouldBeFound("noteValue.doesNotContain=" + UPDATED_NOTE_VALUE);
    }

    @Test
    @Transactional
    void getAllNotesByNoteDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteDescription equals to DEFAULT_NOTE_DESCRIPTION
        defaultNoteShouldBeFound("noteDescription.equals=" + DEFAULT_NOTE_DESCRIPTION);

        // Get all the noteList where noteDescription equals to UPDATED_NOTE_DESCRIPTION
        defaultNoteShouldNotBeFound("noteDescription.equals=" + UPDATED_NOTE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByNoteDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteDescription in DEFAULT_NOTE_DESCRIPTION or UPDATED_NOTE_DESCRIPTION
        defaultNoteShouldBeFound("noteDescription.in=" + DEFAULT_NOTE_DESCRIPTION + "," + UPDATED_NOTE_DESCRIPTION);

        // Get all the noteList where noteDescription equals to UPDATED_NOTE_DESCRIPTION
        defaultNoteShouldNotBeFound("noteDescription.in=" + UPDATED_NOTE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByNoteDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteDescription is not null
        defaultNoteShouldBeFound("noteDescription.specified=true");

        // Get all the noteList where noteDescription is null
        defaultNoteShouldNotBeFound("noteDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByNoteDescriptionContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteDescription contains DEFAULT_NOTE_DESCRIPTION
        defaultNoteShouldBeFound("noteDescription.contains=" + DEFAULT_NOTE_DESCRIPTION);

        // Get all the noteList where noteDescription contains UPDATED_NOTE_DESCRIPTION
        defaultNoteShouldNotBeFound("noteDescription.contains=" + UPDATED_NOTE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByNoteDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteDescription does not contain DEFAULT_NOTE_DESCRIPTION
        defaultNoteShouldNotBeFound("noteDescription.doesNotContain=" + DEFAULT_NOTE_DESCRIPTION);

        // Get all the noteList where noteDescription does not contain UPDATED_NOTE_DESCRIPTION
        defaultNoteShouldBeFound("noteDescription.doesNotContain=" + UPDATED_NOTE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeParams equals to DEFAULT_NOTE_TYPE_PARAMS
        defaultNoteShouldBeFound("noteTypeParams.equals=" + DEFAULT_NOTE_TYPE_PARAMS);

        // Get all the noteList where noteTypeParams equals to UPDATED_NOTE_TYPE_PARAMS
        defaultNoteShouldNotBeFound("noteTypeParams.equals=" + UPDATED_NOTE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeParamsIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeParams in DEFAULT_NOTE_TYPE_PARAMS or UPDATED_NOTE_TYPE_PARAMS
        defaultNoteShouldBeFound("noteTypeParams.in=" + DEFAULT_NOTE_TYPE_PARAMS + "," + UPDATED_NOTE_TYPE_PARAMS);

        // Get all the noteList where noteTypeParams equals to UPDATED_NOTE_TYPE_PARAMS
        defaultNoteShouldNotBeFound("noteTypeParams.in=" + UPDATED_NOTE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeParams is not null
        defaultNoteShouldBeFound("noteTypeParams.specified=true");

        // Get all the noteList where noteTypeParams is null
        defaultNoteShouldNotBeFound("noteTypeParams.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeParamsContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeParams contains DEFAULT_NOTE_TYPE_PARAMS
        defaultNoteShouldBeFound("noteTypeParams.contains=" + DEFAULT_NOTE_TYPE_PARAMS);

        // Get all the noteList where noteTypeParams contains UPDATED_NOTE_TYPE_PARAMS
        defaultNoteShouldNotBeFound("noteTypeParams.contains=" + UPDATED_NOTE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeParamsNotContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeParams does not contain DEFAULT_NOTE_TYPE_PARAMS
        defaultNoteShouldNotBeFound("noteTypeParams.doesNotContain=" + DEFAULT_NOTE_TYPE_PARAMS);

        // Get all the noteList where noteTypeParams does not contain UPDATED_NOTE_TYPE_PARAMS
        defaultNoteShouldBeFound("noteTypeParams.doesNotContain=" + UPDATED_NOTE_TYPE_PARAMS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeAttributs equals to DEFAULT_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldBeFound("noteTypeAttributs.equals=" + DEFAULT_NOTE_TYPE_ATTRIBUTS);

        // Get all the noteList where noteTypeAttributs equals to UPDATED_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldNotBeFound("noteTypeAttributs.equals=" + UPDATED_NOTE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeAttributs in DEFAULT_NOTE_TYPE_ATTRIBUTS or UPDATED_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldBeFound("noteTypeAttributs.in=" + DEFAULT_NOTE_TYPE_ATTRIBUTS + "," + UPDATED_NOTE_TYPE_ATTRIBUTS);

        // Get all the noteList where noteTypeAttributs equals to UPDATED_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldNotBeFound("noteTypeAttributs.in=" + UPDATED_NOTE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeAttributs is not null
        defaultNoteShouldBeFound("noteTypeAttributs.specified=true");

        // Get all the noteList where noteTypeAttributs is null
        defaultNoteShouldNotBeFound("noteTypeAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeAttributsContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeAttributs contains DEFAULT_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldBeFound("noteTypeAttributs.contains=" + DEFAULT_NOTE_TYPE_ATTRIBUTS);

        // Get all the noteList where noteTypeAttributs contains UPDATED_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldNotBeFound("noteTypeAttributs.contains=" + UPDATED_NOTE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteTypeAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteTypeAttributs does not contain DEFAULT_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldNotBeFound("noteTypeAttributs.doesNotContain=" + DEFAULT_NOTE_TYPE_ATTRIBUTS);

        // Get all the noteList where noteTypeAttributs does not contain UPDATED_NOTE_TYPE_ATTRIBUTS
        defaultNoteShouldBeFound("noteTypeAttributs.doesNotContain=" + UPDATED_NOTE_TYPE_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllNotesByNoteStatIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteStat equals to DEFAULT_NOTE_STAT
        defaultNoteShouldBeFound("noteStat.equals=" + DEFAULT_NOTE_STAT);

        // Get all the noteList where noteStat equals to UPDATED_NOTE_STAT
        defaultNoteShouldNotBeFound("noteStat.equals=" + UPDATED_NOTE_STAT);
    }

    @Test
    @Transactional
    void getAllNotesByNoteStatIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteStat in DEFAULT_NOTE_STAT or UPDATED_NOTE_STAT
        defaultNoteShouldBeFound("noteStat.in=" + DEFAULT_NOTE_STAT + "," + UPDATED_NOTE_STAT);

        // Get all the noteList where noteStat equals to UPDATED_NOTE_STAT
        defaultNoteShouldNotBeFound("noteStat.in=" + UPDATED_NOTE_STAT);
    }

    @Test
    @Transactional
    void getAllNotesByNoteStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where noteStat is not null
        defaultNoteShouldBeFound("noteStat.specified=true");

        // Get all the noteList where noteStat is null
        defaultNoteShouldNotBeFound("noteStat.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByAccreditationIsEqualToSomething() throws Exception {
        Accreditation accreditation;
        if (TestUtil.findAll(em, Accreditation.class).isEmpty()) {
            noteRepository.saveAndFlush(note);
            accreditation = AccreditationResourceIT.createEntity(em);
        } else {
            accreditation = TestUtil.findAll(em, Accreditation.class).get(0);
        }
        em.persist(accreditation);
        em.flush();
        note.setAccreditation(accreditation);
        noteRepository.saveAndFlush(note);
        Long accreditationId = accreditation.getAccreditationId();

        // Get all the noteList where accreditation equals to accreditationId
        defaultNoteShouldBeFound("accreditationId.equals=" + accreditationId);

        // Get all the noteList where accreditation equals to (accreditationId + 1)
        defaultNoteShouldNotBeFound("accreditationId.equals=" + (accreditationId + 1));
    }

    @Test
    @Transactional
    void getAllNotesByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            noteRepository.saveAndFlush(note);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        note.setEvent(event);
        noteRepository.saveAndFlush(note);
        Long eventId = event.getEventId();

        // Get all the noteList where event equals to eventId
        defaultNoteShouldBeFound("eventId.equals=" + eventId);

        // Get all the noteList where event equals to (eventId + 1)
        defaultNoteShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoteShouldBeFound(String filter) throws Exception {
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=noteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].noteId").value(hasItem(note.getNoteId().intValue())))
            .andExpect(jsonPath("$.[*].noteValue").value(hasItem(DEFAULT_NOTE_VALUE)))
            .andExpect(jsonPath("$.[*].noteDescription").value(hasItem(DEFAULT_NOTE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].noteTypeParams").value(hasItem(DEFAULT_NOTE_TYPE_PARAMS)))
            .andExpect(jsonPath("$.[*].noteTypeAttributs").value(hasItem(DEFAULT_NOTE_TYPE_ATTRIBUTS)))
            .andExpect(jsonPath("$.[*].noteStat").value(hasItem(DEFAULT_NOTE_STAT.booleanValue())));

        // Check, that the count call also returns 1
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=noteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoteShouldNotBeFound(String filter) throws Exception {
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=noteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=noteId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNote() throws Exception {
        // Get the note
        restNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note
        Note updatedNote = noteRepository.findById(note.getNoteId()).get();
        // Disconnect from session so that the updates on updatedNote are not directly saved in db
        em.detach(updatedNote);
        updatedNote
            .noteValue(UPDATED_NOTE_VALUE)
            .noteDescription(UPDATED_NOTE_DESCRIPTION)
            .noteTypeParams(UPDATED_NOTE_TYPE_PARAMS)
            .noteTypeAttributs(UPDATED_NOTE_TYPE_ATTRIBUTS)
            .noteStat(UPDATED_NOTE_STAT);
        NoteDTO noteDTO = noteMapper.toDto(updatedNote);

        restNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noteDTO.getNoteId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getNoteValue()).isEqualTo(UPDATED_NOTE_VALUE);
        assertThat(testNote.getNoteDescription()).isEqualTo(UPDATED_NOTE_DESCRIPTION);
        assertThat(testNote.getNoteTypeParams()).isEqualTo(UPDATED_NOTE_TYPE_PARAMS);
        assertThat(testNote.getNoteTypeAttributs()).isEqualTo(UPDATED_NOTE_TYPE_ATTRIBUTS);
        assertThat(testNote.getNoteStat()).isEqualTo(UPDATED_NOTE_STAT);
    }

    @Test
    @Transactional
    void putNonExistingNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setNoteId(count.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noteDTO.getNoteId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setNoteId(count.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setNoteId(count.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoteWithPatch() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note using partial update
        Note partialUpdatedNote = new Note();
        partialUpdatedNote.setNoteId(note.getNoteId());

        partialUpdatedNote.noteValue(UPDATED_NOTE_VALUE);

        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNote.getNoteId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNote))
            )
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getNoteValue()).isEqualTo(UPDATED_NOTE_VALUE);
        assertThat(testNote.getNoteDescription()).isEqualTo(DEFAULT_NOTE_DESCRIPTION);
        assertThat(testNote.getNoteTypeParams()).isEqualTo(DEFAULT_NOTE_TYPE_PARAMS);
        assertThat(testNote.getNoteTypeAttributs()).isEqualTo(DEFAULT_NOTE_TYPE_ATTRIBUTS);
        assertThat(testNote.getNoteStat()).isEqualTo(DEFAULT_NOTE_STAT);
    }

    @Test
    @Transactional
    void fullUpdateNoteWithPatch() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note using partial update
        Note partialUpdatedNote = new Note();
        partialUpdatedNote.setNoteId(note.getNoteId());

        partialUpdatedNote
            .noteValue(UPDATED_NOTE_VALUE)
            .noteDescription(UPDATED_NOTE_DESCRIPTION)
            .noteTypeParams(UPDATED_NOTE_TYPE_PARAMS)
            .noteTypeAttributs(UPDATED_NOTE_TYPE_ATTRIBUTS)
            .noteStat(UPDATED_NOTE_STAT);

        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNote.getNoteId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNote))
            )
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getNoteValue()).isEqualTo(UPDATED_NOTE_VALUE);
        assertThat(testNote.getNoteDescription()).isEqualTo(UPDATED_NOTE_DESCRIPTION);
        assertThat(testNote.getNoteTypeParams()).isEqualTo(UPDATED_NOTE_TYPE_PARAMS);
        assertThat(testNote.getNoteTypeAttributs()).isEqualTo(UPDATED_NOTE_TYPE_ATTRIBUTS);
        assertThat(testNote.getNoteStat()).isEqualTo(UPDATED_NOTE_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setNoteId(count.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noteDTO.getNoteId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setNoteId(count.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setNoteId(count.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeDelete = noteRepository.findAll().size();

        // Delete the note
        restNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, note.getNoteId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
