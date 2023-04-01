package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoSuppTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoSupp.class);
        InfoSupp infoSupp1 = new InfoSupp();
        infoSupp1.setInfoSuppId(1L);
        InfoSupp infoSupp2 = new InfoSupp();
        infoSupp2.setInfoSuppId(infoSupp1.getInfoSuppId());
        assertThat(infoSupp1).isEqualTo(infoSupp2);
        infoSupp2.setInfoSuppId(2L);
        assertThat(infoSupp1).isNotEqualTo(infoSupp2);
        infoSupp1.setInfoSuppId(null);
        assertThat(infoSupp1).isNotEqualTo(infoSupp2);
    }
}
