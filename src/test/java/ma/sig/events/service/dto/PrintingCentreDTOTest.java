package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingCentreDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingCentreDTO.class);
        PrintingCentreDTO printingCentreDTO1 = new PrintingCentreDTO();
        printingCentreDTO1.setPrintingCentreId(1L);
        PrintingCentreDTO printingCentreDTO2 = new PrintingCentreDTO();
        assertThat(printingCentreDTO1).isNotEqualTo(printingCentreDTO2);
        printingCentreDTO2.setPrintingCentreId(printingCentreDTO1.getPrintingCentreId());
        assertThat(printingCentreDTO1).isEqualTo(printingCentreDTO2);
        printingCentreDTO2.setPrintingCentreId(2L);
        assertThat(printingCentreDTO1).isNotEqualTo(printingCentreDTO2);
        printingCentreDTO1.setPrintingCentreId(null);
        assertThat(printingCentreDTO1).isNotEqualTo(printingCentreDTO2);
    }
}
