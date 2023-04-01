package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthentificationTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthentificationTypeDTO.class);
        AuthentificationTypeDTO authentificationTypeDTO1 = new AuthentificationTypeDTO();
        authentificationTypeDTO1.setAuthentificationTypeId(1L);
        AuthentificationTypeDTO authentificationTypeDTO2 = new AuthentificationTypeDTO();
        assertThat(authentificationTypeDTO1).isNotEqualTo(authentificationTypeDTO2);
        authentificationTypeDTO2.setAuthentificationTypeId(authentificationTypeDTO1.getAuthentificationTypeId());
        assertThat(authentificationTypeDTO1).isEqualTo(authentificationTypeDTO2);
        authentificationTypeDTO2.setAuthentificationTypeId(2L);
        assertThat(authentificationTypeDTO1).isNotEqualTo(authentificationTypeDTO2);
        authentificationTypeDTO1.setAuthentificationTypeId(null);
        assertThat(authentificationTypeDTO1).isNotEqualTo(authentificationTypeDTO2);
    }
}
