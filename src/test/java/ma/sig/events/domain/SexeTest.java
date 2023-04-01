package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SexeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sexe.class);
        Sexe sexe1 = new Sexe();
        sexe1.setSexeId(1L);
        Sexe sexe2 = new Sexe();
        sexe2.setSexeId(sexe1.getSexeId());
        assertThat(sexe1).isEqualTo(sexe2);
        sexe2.setSexeId(2L);
        assertThat(sexe1).isNotEqualTo(sexe2);
        sexe1.setSexeId(null);
        assertThat(sexe1).isNotEqualTo(sexe2);
    }
}
