package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintingServerMapperTest {

    private PrintingServerMapper printingServerMapper;

    @BeforeEach
    public void setUp() {
        printingServerMapper = new PrintingServerMapperImpl();
    }
}
