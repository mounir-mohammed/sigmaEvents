package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckAccreditationReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckAccreditationReport.class);
        CheckAccreditationReport checkAccreditationReport1 = new CheckAccreditationReport();
        checkAccreditationReport1.setCheckAccreditationReportId(1L);
        CheckAccreditationReport checkAccreditationReport2 = new CheckAccreditationReport();
        checkAccreditationReport2.setCheckAccreditationReportId(checkAccreditationReport1.getCheckAccreditationReportId());
        assertThat(checkAccreditationReport1).isEqualTo(checkAccreditationReport2);
        checkAccreditationReport2.setCheckAccreditationReportId(2L);
        assertThat(checkAccreditationReport1).isNotEqualTo(checkAccreditationReport2);
        checkAccreditationReport1.setCheckAccreditationReportId(null);
        assertThat(checkAccreditationReport1).isNotEqualTo(checkAccreditationReport2);
    }
}
