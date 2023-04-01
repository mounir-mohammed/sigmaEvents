package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventFieldDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventFieldDTO.class);
        EventFieldDTO eventFieldDTO1 = new EventFieldDTO();
        eventFieldDTO1.setFieldId(1L);
        EventFieldDTO eventFieldDTO2 = new EventFieldDTO();
        assertThat(eventFieldDTO1).isNotEqualTo(eventFieldDTO2);
        eventFieldDTO2.setFieldId(eventFieldDTO1.getFieldId());
        assertThat(eventFieldDTO1).isEqualTo(eventFieldDTO2);
        eventFieldDTO2.setFieldId(2L);
        assertThat(eventFieldDTO1).isNotEqualTo(eventFieldDTO2);
        eventFieldDTO1.setFieldId(null);
        assertThat(eventFieldDTO1).isNotEqualTo(eventFieldDTO2);
    }
}
