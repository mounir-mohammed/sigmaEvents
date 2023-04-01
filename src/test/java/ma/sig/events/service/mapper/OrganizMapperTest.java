package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizMapperTest {

    private OrganizMapper organizMapper;

    @BeforeEach
    public void setUp() {
        organizMapper = new OrganizMapperImpl();
    }
}
