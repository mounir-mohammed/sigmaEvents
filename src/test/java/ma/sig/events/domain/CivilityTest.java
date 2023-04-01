package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CivilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Civility.class);
        Civility civility1 = new Civility();
        civility1.setCivilityId(1L);
        Civility civility2 = new Civility();
        civility2.setCivilityId(civility1.getCivilityId());
        assertThat(civility1).isEqualTo(civility2);
        civility2.setCivilityId(2L);
        assertThat(civility1).isNotEqualTo(civility2);
        civility1.setCivilityId(null);
        assertThat(civility1).isNotEqualTo(civility2);
    }
}
