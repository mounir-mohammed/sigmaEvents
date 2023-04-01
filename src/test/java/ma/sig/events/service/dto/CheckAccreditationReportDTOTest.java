package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckAccreditationReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckAccreditationReportDTO.class);
        CheckAccreditationReportDTO checkAccreditationReportDTO1 = new CheckAccreditationReportDTO();
        checkAccreditationReportDTO1.setCheckAccreditationReportId(1L);
        CheckAccreditationReportDTO checkAccreditationReportDTO2 = new CheckAccreditationReportDTO();
        assertThat(checkAccreditationReportDTO1).isNotEqualTo(checkAccreditationReportDTO2);
        checkAccreditationReportDTO2.setCheckAccreditationReportId(checkAccreditationReportDTO1.getCheckAccreditationReportId());
        assertThat(checkAccreditationReportDTO1).isEqualTo(checkAccreditationReportDTO2);
        checkAccreditationReportDTO2.setCheckAccreditationReportId(2L);
        assertThat(checkAccreditationReportDTO1).isNotEqualTo(checkAccreditationReportDTO2);
        checkAccreditationReportDTO1.setCheckAccreditationReportId(null);
        assertThat(checkAccreditationReportDTO1).isNotEqualTo(checkAccreditationReportDTO2);
    }
}
