package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = new Category();
        category1.setCategoryId(1L);
        Category category2 = new Category();
        category2.setCategoryId(category1.getCategoryId());
        assertThat(category1).isEqualTo(category2);
        category2.setCategoryId(2L);
        assertThat(category1).isNotEqualTo(category2);
        category1.setCategoryId(null);
        assertThat(category1).isNotEqualTo(category2);
    }
}
