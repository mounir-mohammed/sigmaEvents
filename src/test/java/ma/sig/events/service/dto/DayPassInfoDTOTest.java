package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DayPassInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayPassInfoDTO.class);
        DayPassInfoDTO dayPassInfoDTO1 = new DayPassInfoDTO();
        dayPassInfoDTO1.setDayPassInfoId(1L);
        DayPassInfoDTO dayPassInfoDTO2 = new DayPassInfoDTO();
        assertThat(dayPassInfoDTO1).isNotEqualTo(dayPassInfoDTO2);
        dayPassInfoDTO2.setDayPassInfoId(dayPassInfoDTO1.getDayPassInfoId());
        assertThat(dayPassInfoDTO1).isEqualTo(dayPassInfoDTO2);
        dayPassInfoDTO2.setDayPassInfoId(2L);
        assertThat(dayPassInfoDTO1).isNotEqualTo(dayPassInfoDTO2);
        dayPassInfoDTO1.setDayPassInfoId(null);
        assertThat(dayPassInfoDTO1).isNotEqualTo(dayPassInfoDTO2);
    }
}
