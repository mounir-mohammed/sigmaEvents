package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthentificationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthentificationType.class);
        AuthentificationType authentificationType1 = new AuthentificationType();
        authentificationType1.setAuthentificationTypeId(1L);
        AuthentificationType authentificationType2 = new AuthentificationType();
        authentificationType2.setAuthentificationTypeId(authentificationType1.getAuthentificationTypeId());
        assertThat(authentificationType1).isEqualTo(authentificationType2);
        authentificationType2.setAuthentificationTypeId(2L);
        assertThat(authentificationType1).isNotEqualTo(authentificationType2);
        authentificationType1.setAuthentificationTypeId(null);
        assertThat(authentificationType1).isNotEqualTo(authentificationType2);
    }
}
