package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventFormMapperTest {

    private EventFormMapper eventFormMapper;

    @BeforeEach
    public void setUp() {
        eventFormMapper = new EventFormMapperImpl();
    }
}
