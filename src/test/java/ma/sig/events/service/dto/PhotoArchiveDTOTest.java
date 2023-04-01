package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoArchiveDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoArchiveDTO.class);
        PhotoArchiveDTO photoArchiveDTO1 = new PhotoArchiveDTO();
        photoArchiveDTO1.setPhotoArchiveId(1L);
        PhotoArchiveDTO photoArchiveDTO2 = new PhotoArchiveDTO();
        assertThat(photoArchiveDTO1).isNotEqualTo(photoArchiveDTO2);
        photoArchiveDTO2.setPhotoArchiveId(photoArchiveDTO1.getPhotoArchiveId());
        assertThat(photoArchiveDTO1).isEqualTo(photoArchiveDTO2);
        photoArchiveDTO2.setPhotoArchiveId(2L);
        assertThat(photoArchiveDTO1).isNotEqualTo(photoArchiveDTO2);
        photoArchiveDTO1.setPhotoArchiveId(null);
        assertThat(photoArchiveDTO1).isNotEqualTo(photoArchiveDTO2);
    }
}
