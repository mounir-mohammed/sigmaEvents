package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccreditationTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccreditationTypeDTO.class);
        AccreditationTypeDTO accreditationTypeDTO1 = new AccreditationTypeDTO();
        accreditationTypeDTO1.setAccreditationTypeId(1L);
        AccreditationTypeDTO accreditationTypeDTO2 = new AccreditationTypeDTO();
        assertThat(accreditationTypeDTO1).isNotEqualTo(accreditationTypeDTO2);
        accreditationTypeDTO2.setAccreditationTypeId(accreditationTypeDTO1.getAccreditationTypeId());
        assertThat(accreditationTypeDTO1).isEqualTo(accreditationTypeDTO2);
        accreditationTypeDTO2.setAccreditationTypeId(2L);
        assertThat(accreditationTypeDTO1).isNotEqualTo(accreditationTypeDTO2);
        accreditationTypeDTO1.setAccreditationTypeId(null);
        assertThat(accreditationTypeDTO1).isNotEqualTo(accreditationTypeDTO2);
    }
}
