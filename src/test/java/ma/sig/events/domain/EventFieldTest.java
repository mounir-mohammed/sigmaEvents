package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventFieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventField.class);
        EventField eventField1 = new EventField();
        eventField1.setFieldId(1L);
        EventField eventField2 = new EventField();
        eventField2.setFieldId(eventField1.getFieldId());
        assertThat(eventField1).isEqualTo(eventField2);
        eventField2.setFieldId(2L);
        assertThat(eventField1).isNotEqualTo(eventField2);
        eventField1.setFieldId(null);
        assertThat(eventField1).isNotEqualTo(eventField2);
    }
}
