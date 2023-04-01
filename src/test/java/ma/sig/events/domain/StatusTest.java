package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Status.class);
        Status status1 = new Status();
        status1.setStatusId(1L);
        Status status2 = new Status();
        status2.setStatusId(status1.getStatusId());
        assertThat(status1).isEqualTo(status2);
        status2.setStatusId(2L);
        assertThat(status1).isNotEqualTo(status2);
        status1.setStatusId(null);
        assertThat(status1).isNotEqualTo(status2);
    }
}
