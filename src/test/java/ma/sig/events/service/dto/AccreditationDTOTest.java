package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccreditationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccreditationDTO.class);
        AccreditationDTO accreditationDTO1 = new AccreditationDTO();
        accreditationDTO1.setAccreditationId(1L);
        AccreditationDTO accreditationDTO2 = new AccreditationDTO();
        assertThat(accreditationDTO1).isNotEqualTo(accreditationDTO2);
        accreditationDTO2.setAccreditationId(accreditationDTO1.getAccreditationId());
        assertThat(accreditationDTO1).isEqualTo(accreditationDTO2);
        accreditationDTO2.setAccreditationId(2L);
        assertThat(accreditationDTO1).isNotEqualTo(accreditationDTO2);
        accreditationDTO1.setAccreditationId(null);
        assertThat(accreditationDTO1).isNotEqualTo(accreditationDTO2);
    }
}
