package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Code.class);
        Code code1 = new Code();
        code1.setCodeId(1L);
        Code code2 = new Code();
        code2.setCodeId(code1.getCodeId());
        assertThat(code1).isEqualTo(code2);
        code2.setCodeId(2L);
        assertThat(code1).isNotEqualTo(code2);
        code1.setCodeId(null);
        assertThat(code1).isNotEqualTo(code2);
    }
}
