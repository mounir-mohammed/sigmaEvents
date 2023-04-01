package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Area.class);
        Area area1 = new Area();
        area1.setAreaId(1L);
        Area area2 = new Area();
        area2.setAreaId(area1.getAreaId());
        assertThat(area1).isEqualTo(area2);
        area2.setAreaId(2L);
        assertThat(area1).isNotEqualTo(area2);
        area1.setAreaId(null);
        assertThat(area1).isNotEqualTo(area2);
    }
}
