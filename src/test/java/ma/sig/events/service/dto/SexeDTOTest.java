package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SexeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SexeDTO.class);
        SexeDTO sexeDTO1 = new SexeDTO();
        sexeDTO1.setSexeId(1L);
        SexeDTO sexeDTO2 = new SexeDTO();
        assertThat(sexeDTO1).isNotEqualTo(sexeDTO2);
        sexeDTO2.setSexeId(sexeDTO1.getSexeId());
        assertThat(sexeDTO1).isEqualTo(sexeDTO2);
        sexeDTO2.setSexeId(2L);
        assertThat(sexeDTO1).isNotEqualTo(sexeDTO2);
        sexeDTO1.setSexeId(null);
        assertThat(sexeDTO1).isNotEqualTo(sexeDTO2);
    }
}
