package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccreditationMapperTest {

    private AccreditationMapper accreditationMapper;

    @BeforeEach
    public void setUp() {
        accreditationMapper = new AccreditationMapperImpl();
    }
}
