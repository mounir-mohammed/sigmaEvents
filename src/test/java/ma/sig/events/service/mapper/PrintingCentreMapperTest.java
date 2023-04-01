package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintingCentreMapperTest {

    private PrintingCentreMapper printingCentreMapper;

    @BeforeEach
    public void setUp() {
        printingCentreMapper = new PrintingCentreMapperImpl();
    }
}
