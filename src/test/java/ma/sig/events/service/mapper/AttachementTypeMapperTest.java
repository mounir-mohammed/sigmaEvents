package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttachementTypeMapperTest {

    private AttachementTypeMapper attachementTypeMapper;

    @BeforeEach
    public void setUp() {
        attachementTypeMapper = new AttachementTypeMapperImpl();
    }
}
