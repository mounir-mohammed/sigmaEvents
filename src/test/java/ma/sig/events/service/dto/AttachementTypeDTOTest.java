package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachementTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachementTypeDTO.class);
        AttachementTypeDTO attachementTypeDTO1 = new AttachementTypeDTO();
        attachementTypeDTO1.setAttachementTypeId(1L);
        AttachementTypeDTO attachementTypeDTO2 = new AttachementTypeDTO();
        assertThat(attachementTypeDTO1).isNotEqualTo(attachementTypeDTO2);
        attachementTypeDTO2.setAttachementTypeId(attachementTypeDTO1.getAttachementTypeId());
        assertThat(attachementTypeDTO1).isEqualTo(attachementTypeDTO2);
        attachementTypeDTO2.setAttachementTypeId(2L);
        assertThat(attachementTypeDTO1).isNotEqualTo(attachementTypeDTO2);
        attachementTypeDTO1.setAttachementTypeId(null);
        assertThat(attachementTypeDTO1).isNotEqualTo(attachementTypeDTO2);
    }
}
