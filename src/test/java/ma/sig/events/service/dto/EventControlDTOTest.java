package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventControlDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventControlDTO.class);
        EventControlDTO eventControlDTO1 = new EventControlDTO();
        eventControlDTO1.setControlId(1L);
        EventControlDTO eventControlDTO2 = new EventControlDTO();
        assertThat(eventControlDTO1).isNotEqualTo(eventControlDTO2);
        eventControlDTO2.setControlId(eventControlDTO1.getControlId());
        assertThat(eventControlDTO1).isEqualTo(eventControlDTO2);
        eventControlDTO2.setControlId(2L);
        assertThat(eventControlDTO1).isNotEqualTo(eventControlDTO2);
        eventControlDTO1.setControlId(null);
        assertThat(eventControlDTO1).isNotEqualTo(eventControlDTO2);
    }
}
