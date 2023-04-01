package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Note.class);
        Note note1 = new Note();
        note1.setNoteId(1L);
        Note note2 = new Note();
        note2.setNoteId(note1.getNoteId());
        assertThat(note1).isEqualTo(note2);
        note2.setNoteId(2L);
        assertThat(note1).isNotEqualTo(note2);
        note1.setNoteId(null);
        assertThat(note1).isNotEqualTo(note2);
    }
}
