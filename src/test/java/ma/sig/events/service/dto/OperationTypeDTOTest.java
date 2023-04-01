package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationTypeDTO.class);
        OperationTypeDTO operationTypeDTO1 = new OperationTypeDTO();
        operationTypeDTO1.setOperationTypeId(1L);
        OperationTypeDTO operationTypeDTO2 = new OperationTypeDTO();
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
        operationTypeDTO2.setOperationTypeId(operationTypeDTO1.getOperationTypeId());
        assertThat(operationTypeDTO1).isEqualTo(operationTypeDTO2);
        operationTypeDTO2.setOperationTypeId(2L);
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
        operationTypeDTO1.setOperationTypeId(null);
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
    }
}
