package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NationalityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nationality.class);
        Nationality nationality1 = new Nationality();
        nationality1.setNationalityId(1L);
        Nationality nationality2 = new Nationality();
        nationality2.setNationalityId(nationality1.getNationalityId());
        assertThat(nationality1).isEqualTo(nationality2);
        nationality2.setNationalityId(2L);
        assertThat(nationality1).isNotEqualTo(nationality2);
        nationality1.setNationalityId(null);
        assertThat(nationality1).isNotEqualTo(nationality2);
    }
}
