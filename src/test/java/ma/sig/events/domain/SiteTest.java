package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Site.class);
        Site site1 = new Site();
        site1.setSiteId(1L);
        Site site2 = new Site();
        site2.setSiteId(site1.getSiteId());
        assertThat(site1).isEqualTo(site2);
        site2.setSiteId(2L);
        assertThat(site1).isNotEqualTo(site2);
        site1.setSiteId(null);
        assertThat(site1).isNotEqualTo(site2);
    }
}
