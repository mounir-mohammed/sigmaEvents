package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachement.class);
        Attachement attachement1 = new Attachement();
        attachement1.setAttachementId(1L);
        Attachement attachement2 = new Attachement();
        attachement2.setAttachementId(attachement1.getAttachementId());
        assertThat(attachement1).isEqualTo(attachement2);
        attachement2.setAttachementId(2L);
        assertThat(attachement1).isNotEqualTo(attachement2);
        attachement1.setAttachementId(null);
        assertThat(attachement1).isNotEqualTo(attachement2);
    }
}
