package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Setting.class);
        Setting setting1 = new Setting();
        setting1.setSettingId(1L);
        Setting setting2 = new Setting();
        setting2.setSettingId(setting1.getSettingId());
        assertThat(setting1).isEqualTo(setting2);
        setting2.setSettingId(2L);
        assertThat(setting1).isNotEqualTo(setting2);
        setting1.setSettingId(null);
        assertThat(setting1).isNotEqualTo(setting2);
    }
}
