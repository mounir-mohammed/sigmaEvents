package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttachementMapperTest {

    private AttachementMapper attachementMapper;

    @BeforeEach
    public void setUp() {
        attachementMapper = new AttachementMapperImpl();
    }
}
