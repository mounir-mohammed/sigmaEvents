package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckAccreditationReportMapperTest {

    private CheckAccreditationReportMapper checkAccreditationReportMapper;

    @BeforeEach
    public void setUp() {
        checkAccreditationReportMapper = new CheckAccreditationReportMapperImpl();
    }
}
