package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InfoSuppMapperTest {

    private InfoSuppMapper infoSuppMapper;

    @BeforeEach
    public void setUp() {
        infoSuppMapper = new InfoSuppMapperImpl();
    }
}
