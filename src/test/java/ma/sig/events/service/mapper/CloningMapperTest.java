package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CloningMapperTest {

    private CloningMapper cloningMapper;

    @BeforeEach
    public void setUp() {
        cloningMapper = new CloningMapperImpl();
    }
}
