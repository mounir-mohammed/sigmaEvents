package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeTypeDTO.class);
        CodeTypeDTO codeTypeDTO1 = new CodeTypeDTO();
        codeTypeDTO1.setCodeTypeId(1L);
        CodeTypeDTO codeTypeDTO2 = new CodeTypeDTO();
        assertThat(codeTypeDTO1).isNotEqualTo(codeTypeDTO2);
        codeTypeDTO2.setCodeTypeId(codeTypeDTO1.getCodeTypeId());
        assertThat(codeTypeDTO1).isEqualTo(codeTypeDTO2);
        codeTypeDTO2.setCodeTypeId(2L);
        assertThat(codeTypeDTO1).isNotEqualTo(codeTypeDTO2);
        codeTypeDTO1.setCodeTypeId(null);
        assertThat(codeTypeDTO1).isNotEqualTo(codeTypeDTO2);
    }
}
