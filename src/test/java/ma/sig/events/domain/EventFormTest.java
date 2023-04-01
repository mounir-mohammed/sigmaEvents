package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventFormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventForm.class);
        EventForm eventForm1 = new EventForm();
        eventForm1.setFormId(1L);
        EventForm eventForm2 = new EventForm();
        eventForm2.setFormId(eventForm1.getFormId());
        assertThat(eventForm1).isEqualTo(eventForm2);
        eventForm2.setFormId(2L);
        assertThat(eventForm1).isNotEqualTo(eventForm2);
        eventForm1.setFormId(null);
        assertThat(eventForm1).isNotEqualTo(eventForm2);
    }
}
