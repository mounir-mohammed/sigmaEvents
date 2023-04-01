package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckAccreditationHistoryMapperTest {

    private CheckAccreditationHistoryMapper checkAccreditationHistoryMapper;

    @BeforeEach
    public void setUp() {
        checkAccreditationHistoryMapper = new CheckAccreditationHistoryMapperImpl();
    }
}
