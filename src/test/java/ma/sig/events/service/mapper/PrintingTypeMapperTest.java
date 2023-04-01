package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintingTypeMapperTest {

    private PrintingTypeMapper printingTypeMapper;

    @BeforeEach
    public void setUp() {
        printingTypeMapper = new PrintingTypeMapperImpl();
    }
}
