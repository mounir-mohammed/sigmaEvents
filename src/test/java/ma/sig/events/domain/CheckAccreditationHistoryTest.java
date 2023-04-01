package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckAccreditationHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckAccreditationHistory.class);
        CheckAccreditationHistory checkAccreditationHistory1 = new CheckAccreditationHistory();
        checkAccreditationHistory1.setCheckAccreditationHistoryId(1L);
        CheckAccreditationHistory checkAccreditationHistory2 = new CheckAccreditationHistory();
        checkAccreditationHistory2.setCheckAccreditationHistoryId(checkAccreditationHistory1.getCheckAccreditationHistoryId());
        assertThat(checkAccreditationHistory1).isEqualTo(checkAccreditationHistory2);
        checkAccreditationHistory2.setCheckAccreditationHistoryId(2L);
        assertThat(checkAccreditationHistory1).isNotEqualTo(checkAccreditationHistory2);
        checkAccreditationHistory1.setCheckAccreditationHistoryId(null);
        assertThat(checkAccreditationHistory1).isNotEqualTo(checkAccreditationHistory2);
    }
}
