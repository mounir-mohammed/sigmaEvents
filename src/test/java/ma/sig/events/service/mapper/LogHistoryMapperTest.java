package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogHistoryMapperTest {

    private LogHistoryMapper logHistoryMapper;

    @BeforeEach
    public void setUp() {
        logHistoryMapper = new LogHistoryMapperImpl();
    }
}
