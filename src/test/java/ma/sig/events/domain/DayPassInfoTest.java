package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DayPassInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayPassInfo.class);
        DayPassInfo dayPassInfo1 = new DayPassInfo();
        dayPassInfo1.setDayPassInfoId(1L);
        DayPassInfo dayPassInfo2 = new DayPassInfo();
        dayPassInfo2.setDayPassInfoId(dayPassInfo1.getDayPassInfoId());
        assertThat(dayPassInfo1).isEqualTo(dayPassInfo2);
        dayPassInfo2.setDayPassInfoId(2L);
        assertThat(dayPassInfo1).isNotEqualTo(dayPassInfo2);
        dayPassInfo1.setDayPassInfoId(null);
        assertThat(dayPassInfo1).isNotEqualTo(dayPassInfo2);
    }
}
