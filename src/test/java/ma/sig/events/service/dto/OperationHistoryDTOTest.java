package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationHistoryDTO.class);
        OperationHistoryDTO operationHistoryDTO1 = new OperationHistoryDTO();
        operationHistoryDTO1.setOperationHistoryId(1L);
        OperationHistoryDTO operationHistoryDTO2 = new OperationHistoryDTO();
        assertThat(operationHistoryDTO1).isNotEqualTo(operationHistoryDTO2);
        operationHistoryDTO2.setOperationHistoryId(operationHistoryDTO1.getOperationHistoryId());
        assertThat(operationHistoryDTO1).isEqualTo(operationHistoryDTO2);
        operationHistoryDTO2.setOperationHistoryId(2L);
        assertThat(operationHistoryDTO1).isNotEqualTo(operationHistoryDTO2);
        operationHistoryDTO1.setOperationHistoryId(null);
        assertThat(operationHistoryDTO1).isNotEqualTo(operationHistoryDTO2);
    }
}
