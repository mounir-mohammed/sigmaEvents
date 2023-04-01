package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SexeMapperTest {

    private SexeMapper sexeMapper;

    @BeforeEach
    public void setUp() {
        sexeMapper = new SexeMapperImpl();
    }
}
