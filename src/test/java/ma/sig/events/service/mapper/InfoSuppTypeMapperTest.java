package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InfoSuppTypeMapperTest {

    private InfoSuppTypeMapper infoSuppTypeMapper;

    @BeforeEach
    public void setUp() {
        infoSuppTypeMapper = new InfoSuppTypeMapperImpl();
    }
}
