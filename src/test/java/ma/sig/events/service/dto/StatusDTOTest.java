package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusDTO.class);
        StatusDTO statusDTO1 = new StatusDTO();
        statusDTO1.setStatusId(1L);
        StatusDTO statusDTO2 = new StatusDTO();
        assertThat(statusDTO1).isNotEqualTo(statusDTO2);
        statusDTO2.setStatusId(statusDTO1.getStatusId());
        assertThat(statusDTO1).isEqualTo(statusDTO2);
        statusDTO2.setStatusId(2L);
        assertThat(statusDTO1).isNotEqualTo(statusDTO2);
        statusDTO1.setStatusId(null);
        assertThat(statusDTO1).isNotEqualTo(statusDTO2);
    }
}
