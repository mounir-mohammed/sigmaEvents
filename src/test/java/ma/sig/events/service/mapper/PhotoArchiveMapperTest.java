package ma.sig.events.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhotoArchiveMapperTest {

    private PhotoArchiveMapper photoArchiveMapper;

    @BeforeEach
    public void setUp() {
        photoArchiveMapper = new PhotoArchiveMapperImpl();
    }
}
