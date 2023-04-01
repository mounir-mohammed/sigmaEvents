package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LogHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogHistoryDTO.class);
        LogHistoryDTO logHistoryDTO1 = new LogHistoryDTO();
        logHistoryDTO1.setLogHistory(1L);
        LogHistoryDTO logHistoryDTO2 = new LogHistoryDTO();
        assertThat(logHistoryDTO1).isNotEqualTo(logHistoryDTO2);
        logHistoryDTO2.setLogHistory(logHistoryDTO1.getLogHistory());
        assertThat(logHistoryDTO1).isEqualTo(logHistoryDTO2);
        logHistoryDTO2.setLogHistory(2L);
        assertThat(logHistoryDTO1).isNotEqualTo(logHistoryDTO2);
        logHistoryDTO1.setLogHistory(null);
        assertThat(logHistoryDTO1).isNotEqualTo(logHistoryDTO2);
    }
}
