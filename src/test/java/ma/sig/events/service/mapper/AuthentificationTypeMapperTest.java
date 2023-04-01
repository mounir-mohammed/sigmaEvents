package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthentificationTypeMapperTest {

    private AuthentificationTypeMapper authentificationTypeMapper;

    @BeforeEach
    public void setUp() {
        authentificationTypeMapper = new AuthentificationTypeMapperImpl();
    }
}
