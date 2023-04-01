package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organiz.class);
        Organiz organiz1 = new Organiz();
        organiz1.setOrganizId(1L);
        Organiz organiz2 = new Organiz();
        organiz2.setOrganizId(organiz1.getOrganizId());
        assertThat(organiz1).isEqualTo(organiz2);
        organiz2.setOrganizId(2L);
        assertThat(organiz1).isNotEqualTo(organiz2);
        organiz1.setOrganizId(null);
        assertThat(organiz1).isNotEqualTo(organiz2);
    }
}
