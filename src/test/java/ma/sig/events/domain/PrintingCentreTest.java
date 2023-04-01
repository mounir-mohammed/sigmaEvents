package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingCentreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingCentre.class);
        PrintingCentre printingCentre1 = new PrintingCentre();
        printingCentre1.setPrintingCentreId(1L);
        PrintingCentre printingCentre2 = new PrintingCentre();
        printingCentre2.setPrintingCentreId(printingCentre1.getPrintingCentreId());
        assertThat(printingCentre1).isEqualTo(printingCentre2);
        printingCentre2.setPrintingCentreId(2L);
        assertThat(printingCentre1).isNotEqualTo(printingCentre2);
        printingCentre1.setPrintingCentreId(null);
        assertThat(printingCentre1).isNotEqualTo(printingCentre2);
    }
}
