package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationType.class);
        OperationType operationType1 = new OperationType();
        operationType1.setOperationTypeId(1L);
        OperationType operationType2 = new OperationType();
        operationType2.setOperationTypeId(operationType1.getOperationTypeId());
        assertThat(operationType1).isEqualTo(operationType2);
        operationType2.setOperationTypeId(2L);
        assertThat(operationType1).isNotEqualTo(operationType2);
        operationType1.setOperationTypeId(null);
        assertThat(operationType1).isNotEqualTo(operationType2);
    }
}
