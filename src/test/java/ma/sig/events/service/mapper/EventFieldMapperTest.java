package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventFieldMapperTest {

    private EventFieldMapper eventFieldMapper;

    @BeforeEach
    public void setUp() {
        eventFieldMapper = new EventFieldMapperImpl();
    }
}
