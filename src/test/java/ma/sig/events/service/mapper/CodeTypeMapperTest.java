package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CodeTypeMapperTest {

    private CodeTypeMapper codeTypeMapper;

    @BeforeEach
    public void setUp() {
        codeTypeMapper = new CodeTypeMapperImpl();
    }
}
