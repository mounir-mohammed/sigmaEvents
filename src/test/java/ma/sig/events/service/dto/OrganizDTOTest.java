package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizDTO.class);
        OrganizDTO organizDTO1 = new OrganizDTO();
        organizDTO1.setOrganizId(1L);
        OrganizDTO organizDTO2 = new OrganizDTO();
        assertThat(organizDTO1).isNotEqualTo(organizDTO2);
        organizDTO2.setOrganizId(organizDTO1.getOrganizId());
        assertThat(organizDTO1).isEqualTo(organizDTO2);
        organizDTO2.setOrganizId(2L);
        assertThat(organizDTO1).isNotEqualTo(organizDTO2);
        organizDTO1.setOrganizId(null);
        assertThat(organizDTO1).isNotEqualTo(organizDTO2);
    }
}
