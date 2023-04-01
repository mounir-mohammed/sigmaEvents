package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NationalityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NationalityDTO.class);
        NationalityDTO nationalityDTO1 = new NationalityDTO();
        nationalityDTO1.setNationalityId(1L);
        NationalityDTO nationalityDTO2 = new NationalityDTO();
        assertThat(nationalityDTO1).isNotEqualTo(nationalityDTO2);
        nationalityDTO2.setNationalityId(nationalityDTO1.getNationalityId());
        assertThat(nationalityDTO1).isEqualTo(nationalityDTO2);
        nationalityDTO2.setNationalityId(2L);
        assertThat(nationalityDTO1).isNotEqualTo(nationalityDTO2);
        nationalityDTO1.setNationalityId(null);
        assertThat(nationalityDTO1).isNotEqualTo(nationalityDTO2);
    }
}
