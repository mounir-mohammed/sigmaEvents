package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoArchiveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoArchive.class);
        PhotoArchive photoArchive1 = new PhotoArchive();
        photoArchive1.setPhotoArchiveId(1L);
        PhotoArchive photoArchive2 = new PhotoArchive();
        photoArchive2.setPhotoArchiveId(photoArchive1.getPhotoArchiveId());
        assertThat(photoArchive1).isEqualTo(photoArchive2);
        photoArchive2.setPhotoArchiveId(2L);
        assertThat(photoArchive1).isNotEqualTo(photoArchive2);
        photoArchive1.setPhotoArchiveId(null);
        assertThat(photoArchive1).isNotEqualTo(photoArchive2);
    }
}
