package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingType.class);
        PrintingType printingType1 = new PrintingType();
        printingType1.setPrintingTypeId(1L);
        PrintingType printingType2 = new PrintingType();
        printingType2.setPrintingTypeId(printingType1.getPrintingTypeId());
        assertThat(printingType1).isEqualTo(printingType2);
        printingType2.setPrintingTypeId(2L);
        assertThat(printingType1).isNotEqualTo(printingType2);
        printingType1.setPrintingTypeId(null);
        assertThat(printingType1).isNotEqualTo(printingType2);
    }
}
