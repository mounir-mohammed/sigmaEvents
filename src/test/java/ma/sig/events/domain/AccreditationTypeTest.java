package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccreditationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccreditationType.class);
        AccreditationType accreditationType1 = new AccreditationType();
        accreditationType1.setAccreditationTypeId(1L);
        AccreditationType accreditationType2 = new AccreditationType();
        accreditationType2.setAccreditationTypeId(accreditationType1.getAccreditationTypeId());
        assertThat(accreditationType1).isEqualTo(accreditationType2);
        accreditationType2.setAccreditationTypeId(2L);
        assertThat(accreditationType1).isNotEqualTo(accreditationType2);
        accreditationType1.setAccreditationTypeId(null);
        assertThat(accreditationType1).isNotEqualTo(accreditationType2);
    }
}
