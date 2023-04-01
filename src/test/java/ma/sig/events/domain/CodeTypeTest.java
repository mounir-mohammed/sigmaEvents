package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeType.class);
        CodeType codeType1 = new CodeType();
        codeType1.setCodeTypeId(1L);
        CodeType codeType2 = new CodeType();
        codeType2.setCodeTypeId(codeType1.getCodeTypeId());
        assertThat(codeType1).isEqualTo(codeType2);
        codeType2.setCodeTypeId(2L);
        assertThat(codeType1).isNotEqualTo(codeType2);
        codeType1.setCodeTypeId(null);
        assertThat(codeType1).isNotEqualTo(codeType2);
    }
}
