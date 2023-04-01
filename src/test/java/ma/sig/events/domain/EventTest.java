package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setEventId(1L);
        Event event2 = new Event();
        event2.setEventId(event1.getEventId());
        assertThat(event1).isEqualTo(event2);
        event2.setEventId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setEventId(null);
        assertThat(event1).isNotEqualTo(event2);
    }
}
