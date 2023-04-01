package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LogHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogHistory.class);
        LogHistory logHistory1 = new LogHistory();
        logHistory1.setLogHistory(1L);
        LogHistory logHistory2 = new LogHistory();
        logHistory2.setLogHistory(logHistory1.getLogHistory());
        assertThat(logHistory1).isEqualTo(logHistory2);
        logHistory2.setLogHistory(2L);
        assertThat(logHistory1).isNotEqualTo(logHistory2);
        logHistory1.setLogHistory(null);
        assertThat(logHistory1).isNotEqualTo(logHistory2);
    }
}
