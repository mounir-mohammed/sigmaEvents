package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FonctionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FonctionDTO.class);
        FonctionDTO fonctionDTO1 = new FonctionDTO();
        fonctionDTO1.setFonctionId(1L);
        FonctionDTO fonctionDTO2 = new FonctionDTO();
        assertThat(fonctionDTO1).isNotEqualTo(fonctionDTO2);
        fonctionDTO2.setFonctionId(fonctionDTO1.getFonctionId());
        assertThat(fonctionDTO1).isEqualTo(fonctionDTO2);
        fonctionDTO2.setFonctionId(2L);
        assertThat(fonctionDTO1).isNotEqualTo(fonctionDTO2);
        fonctionDTO1.setFonctionId(null);
        assertThat(fonctionDTO1).isNotEqualTo(fonctionDTO2);
    }
}
