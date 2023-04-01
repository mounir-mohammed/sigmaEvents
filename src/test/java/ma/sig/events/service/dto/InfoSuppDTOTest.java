package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoSuppDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoSuppDTO.class);
        InfoSuppDTO infoSuppDTO1 = new InfoSuppDTO();
        infoSuppDTO1.setInfoSuppId(1L);
        InfoSuppDTO infoSuppDTO2 = new InfoSuppDTO();
        assertThat(infoSuppDTO1).isNotEqualTo(infoSuppDTO2);
        infoSuppDTO2.setInfoSuppId(infoSuppDTO1.getInfoSuppId());
        assertThat(infoSuppDTO1).isEqualTo(infoSuppDTO2);
        infoSuppDTO2.setInfoSuppId(2L);
        assertThat(infoSuppDTO1).isNotEqualTo(infoSuppDTO2);
        infoSuppDTO1.setInfoSuppId(null);
        assertThat(infoSuppDTO1).isNotEqualTo(infoSuppDTO2);
    }
}
