package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CivilityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CivilityDTO.class);
        CivilityDTO civilityDTO1 = new CivilityDTO();
        civilityDTO1.setCivilityId(1L);
        CivilityDTO civilityDTO2 = new CivilityDTO();
        assertThat(civilityDTO1).isNotEqualTo(civilityDTO2);
        civilityDTO2.setCivilityId(civilityDTO1.getCivilityId());
        assertThat(civilityDTO1).isEqualTo(civilityDTO2);
        civilityDTO2.setCivilityId(2L);
        assertThat(civilityDTO1).isNotEqualTo(civilityDTO2);
        civilityDTO1.setCivilityId(null);
        assertThat(civilityDTO1).isNotEqualTo(civilityDTO2);
    }
}
