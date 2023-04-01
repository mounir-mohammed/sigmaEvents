package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaDTO.class);
        AreaDTO areaDTO1 = new AreaDTO();
        areaDTO1.setAreaId(1L);
        AreaDTO areaDTO2 = new AreaDTO();
        assertThat(areaDTO1).isNotEqualTo(areaDTO2);
        areaDTO2.setAreaId(areaDTO1.getAreaId());
        assertThat(areaDTO1).isEqualTo(areaDTO2);
        areaDTO2.setAreaId(2L);
        assertThat(areaDTO1).isNotEqualTo(areaDTO2);
        areaDTO1.setAreaId(null);
        assertThat(areaDTO1).isNotEqualTo(areaDTO2);
    }
}
