package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoSuppTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoSuppTypeDTO.class);
        InfoSuppTypeDTO infoSuppTypeDTO1 = new InfoSuppTypeDTO();
        infoSuppTypeDTO1.setInfoSuppTypeId(1L);
        InfoSuppTypeDTO infoSuppTypeDTO2 = new InfoSuppTypeDTO();
        assertThat(infoSuppTypeDTO1).isNotEqualTo(infoSuppTypeDTO2);
        infoSuppTypeDTO2.setInfoSuppTypeId(infoSuppTypeDTO1.getInfoSuppTypeId());
        assertThat(infoSuppTypeDTO1).isEqualTo(infoSuppTypeDTO2);
        infoSuppTypeDTO2.setInfoSuppTypeId(2L);
        assertThat(infoSuppTypeDTO1).isNotEqualTo(infoSuppTypeDTO2);
        infoSuppTypeDTO1.setInfoSuppTypeId(null);
        assertThat(infoSuppTypeDTO1).isNotEqualTo(infoSuppTypeDTO2);
    }
}
