package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventFormDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventFormDTO.class);
        EventFormDTO eventFormDTO1 = new EventFormDTO();
        eventFormDTO1.setFormId(1L);
        EventFormDTO eventFormDTO2 = new EventFormDTO();
        assertThat(eventFormDTO1).isNotEqualTo(eventFormDTO2);
        eventFormDTO2.setFormId(eventFormDTO1.getFormId());
        assertThat(eventFormDTO1).isEqualTo(eventFormDTO2);
        eventFormDTO2.setFormId(2L);
        assertThat(eventFormDTO1).isNotEqualTo(eventFormDTO2);
        eventFormDTO1.setFormId(null);
        assertThat(eventFormDTO1).isNotEqualTo(eventFormDTO2);
    }
}
