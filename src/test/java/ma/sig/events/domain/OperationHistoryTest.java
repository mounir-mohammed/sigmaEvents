package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationHistory.class);
        OperationHistory operationHistory1 = new OperationHistory();
        operationHistory1.setOperationHistoryId(1L);
        OperationHistory operationHistory2 = new OperationHistory();
        operationHistory2.setOperationHistoryId(operationHistory1.getOperationHistoryId());
        assertThat(operationHistory1).isEqualTo(operationHistory2);
        operationHistory2.setOperationHistoryId(2L);
        assertThat(operationHistory1).isNotEqualTo(operationHistory2);
        operationHistory1.setOperationHistoryId(null);
        assertThat(operationHistory1).isNotEqualTo(operationHistory2);
    }
}
