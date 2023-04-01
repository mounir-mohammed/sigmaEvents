package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachementTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachementType.class);
        AttachementType attachementType1 = new AttachementType();
        attachementType1.setAttachementTypeId(1L);
        AttachementType attachementType2 = new AttachementType();
        attachementType2.setAttachementTypeId(attachementType1.getAttachementTypeId());
        assertThat(attachementType1).isEqualTo(attachementType2);
        attachementType2.setAttachementTypeId(2L);
        assertThat(attachementType1).isNotEqualTo(attachementType2);
        attachementType1.setAttachementTypeId(null);
        assertThat(attachementType1).isNotEqualTo(attachementType2);
    }
}
