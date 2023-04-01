package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloningDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CloningDTO.class);
        CloningDTO cloningDTO1 = new CloningDTO();
        cloningDTO1.setCloningId(1L);
        CloningDTO cloningDTO2 = new CloningDTO();
        assertThat(cloningDTO1).isNotEqualTo(cloningDTO2);
        cloningDTO2.setCloningId(cloningDTO1.getCloningId());
        assertThat(cloningDTO1).isEqualTo(cloningDTO2);
        cloningDTO2.setCloningId(2L);
        assertThat(cloningDTO1).isNotEqualTo(cloningDTO2);
        cloningDTO1.setCloningId(null);
        assertThat(cloningDTO1).isNotEqualTo(cloningDTO2);
    }
}
