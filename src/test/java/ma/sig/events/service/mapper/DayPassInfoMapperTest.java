package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DayPassInfoMapperTest {

    private DayPassInfoMapper dayPassInfoMapper;

    @BeforeEach
    public void setUp() {
        dayPassInfoMapper = new DayPassInfoMapperImpl();
    }
}
