package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckAccreditationHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckAccreditationHistoryDTO.class);
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO1 = new CheckAccreditationHistoryDTO();
        checkAccreditationHistoryDTO1.setCheckAccreditationHistoryId(1L);
        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO2 = new CheckAccreditationHistoryDTO();
        assertThat(checkAccreditationHistoryDTO1).isNotEqualTo(checkAccreditationHistoryDTO2);
        checkAccreditationHistoryDTO2.setCheckAccreditationHistoryId(checkAccreditationHistoryDTO1.getCheckAccreditationHistoryId());
        assertThat(checkAccreditationHistoryDTO1).isEqualTo(checkAccreditationHistoryDTO2);
        checkAccreditationHistoryDTO2.setCheckAccreditationHistoryId(2L);
        assertThat(checkAccreditationHistoryDTO1).isNotEqualTo(checkAccreditationHistoryDTO2);
        checkAccreditationHistoryDTO1.setCheckAccreditationHistoryId(null);
        assertThat(checkAccreditationHistoryDTO1).isNotEqualTo(checkAccreditationHistoryDTO2);
    }
}
