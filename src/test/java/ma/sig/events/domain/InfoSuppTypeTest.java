package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoSuppTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoSuppType.class);
        InfoSuppType infoSuppType1 = new InfoSuppType();
        infoSuppType1.setInfoSuppTypeId(1L);
        InfoSuppType infoSuppType2 = new InfoSuppType();
        infoSuppType2.setInfoSuppTypeId(infoSuppType1.getInfoSuppTypeId());
        assertThat(infoSuppType1).isEqualTo(infoSuppType2);
        infoSuppType2.setInfoSuppTypeId(2L);
        assertThat(infoSuppType1).isNotEqualTo(infoSuppType2);
        infoSuppType1.setInfoSuppTypeId(null);
        assertThat(infoSuppType1).isNotEqualTo(infoSuppType2);
    }
}
