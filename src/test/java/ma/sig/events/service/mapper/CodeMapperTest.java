package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CodeMapperTest {

    private CodeMapper codeMapper;

    @BeforeEach
    public void setUp() {
        codeMapper = new CodeMapperImpl();
    }
}
