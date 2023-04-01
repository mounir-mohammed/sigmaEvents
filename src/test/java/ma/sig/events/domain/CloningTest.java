package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cloning.class);
        Cloning cloning1 = new Cloning();
        cloning1.setCloningId(1L);
        Cloning cloning2 = new Cloning();
        cloning2.setCloningId(cloning1.getCloningId());
        assertThat(cloning1).isEqualTo(cloning2);
        cloning2.setCloningId(2L);
        assertThat(cloning1).isNotEqualTo(cloning2);
        cloning1.setCloningId(null);
        assertThat(cloning1).isNotEqualTo(cloning2);
    }
}
