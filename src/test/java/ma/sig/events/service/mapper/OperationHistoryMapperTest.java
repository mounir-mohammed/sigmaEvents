package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHistoryMapperTest {

    private OperationHistoryMapper operationHistoryMapper;

    @BeforeEach
    public void setUp() {
        operationHistoryMapper = new OperationHistoryMapperImpl();
    }
}
