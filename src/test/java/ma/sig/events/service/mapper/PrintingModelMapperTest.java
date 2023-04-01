package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintingModelMapperTest {

    private PrintingModelMapper printingModelMapper;

    @BeforeEach
    public void setUp() {
        printingModelMapper = new PrintingModelMapperImpl();
    }
}
