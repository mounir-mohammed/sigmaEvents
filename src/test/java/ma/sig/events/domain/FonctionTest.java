package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FonctionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fonction.class);
        Fonction fonction1 = new Fonction();
        fonction1.setFonctionId(1L);
        Fonction fonction2 = new Fonction();
        fonction2.setFonctionId(fonction1.getFonctionId());
        assertThat(fonction1).isEqualTo(fonction2);
        fonction2.setFonctionId(2L);
        assertThat(fonction1).isNotEqualTo(fonction2);
        fonction1.setFonctionId(null);
        assertThat(fonction1).isNotEqualTo(fonction2);
    }
}
