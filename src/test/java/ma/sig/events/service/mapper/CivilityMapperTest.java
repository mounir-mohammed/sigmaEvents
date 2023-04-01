package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CivilityMapperTest {

    private CivilityMapper civilityMapper;

    @BeforeEach
    public void setUp() {
        civilityMapper = new CivilityMapperImpl();
    }
}
