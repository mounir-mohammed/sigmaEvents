package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationTypeMapperTest {

    private OperationTypeMapper operationTypeMapper;

    @BeforeEach
    public void setUp() {
        operationTypeMapper = new OperationTypeMapperImpl();
    }
}
