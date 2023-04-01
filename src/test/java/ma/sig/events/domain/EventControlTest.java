package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventControlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventControl.class);
        EventControl eventControl1 = new EventControl();
        eventControl1.setControlId(1L);
        EventControl eventControl2 = new EventControl();
        eventControl2.setControlId(eventControl1.getControlId());
        assertThat(eventControl1).isEqualTo(eventControl2);
        eventControl2.setControlId(2L);
        assertThat(eventControl1).isNotEqualTo(eventControl2);
        eventControl1.setControlId(null);
        assertThat(eventControl1).isNotEqualTo(eventControl2);
    }
}
