package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccreditationTypeMapperTest {

    private AccreditationTypeMapper accreditationTypeMapper;

    @BeforeEach
    public void setUp() {
        accreditationTypeMapper = new AccreditationTypeMapperImpl();
    }
}
