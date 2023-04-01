package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventControlMapperTest {

    private EventControlMapper eventControlMapper;

    @BeforeEach
    public void setUp() {
        eventControlMapper = new EventControlMapperImpl();
    }
}
