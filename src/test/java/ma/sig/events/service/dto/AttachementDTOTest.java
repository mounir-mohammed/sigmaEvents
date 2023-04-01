package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachementDTO.class);
        AttachementDTO attachementDTO1 = new AttachementDTO();
        attachementDTO1.setAttachementId(1L);
        AttachementDTO attachementDTO2 = new AttachementDTO();
        assertThat(attachementDTO1).isNotEqualTo(attachementDTO2);
        attachementDTO2.setAttachementId(attachementDTO1.getAttachementId());
        assertThat(attachementDTO1).isEqualTo(attachementDTO2);
        attachementDTO2.setAttachementId(2L);
        assertThat(attachementDTO1).isNotEqualTo(attachementDTO2);
        attachementDTO1.setAttachementId(null);
        assertThat(attachementDTO1).isNotEqualTo(attachementDTO2);
    }
}
