package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccreditationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accreditation.class);
        Accreditation accreditation1 = new Accreditation();
        accreditation1.setAccreditationId(1L);
        Accreditation accreditation2 = new Accreditation();
        accreditation2.setAccreditationId(accreditation1.getAccreditationId());
        assertThat(accreditation1).isEqualTo(accreditation2);
        accreditation2.setAccreditationId(2L);
        assertThat(accreditation1).isNotEqualTo(accreditation2);
        accreditation1.setAccreditationId(null);
        assertThat(accreditation1).isNotEqualTo(accreditation2);
    }
}
