package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingServerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingServerDTO.class);
        PrintingServerDTO printingServerDTO1 = new PrintingServerDTO();
        printingServerDTO1.setPrintingServerId(1L);
        PrintingServerDTO printingServerDTO2 = new PrintingServerDTO();
        assertThat(printingServerDTO1).isNotEqualTo(printingServerDTO2);
        printingServerDTO2.setPrintingServerId(printingServerDTO1.getPrintingServerId());
        assertThat(printingServerDTO1).isEqualTo(printingServerDTO2);
        printingServerDTO2.setPrintingServerId(2L);
        assertThat(printingServerDTO1).isNotEqualTo(printingServerDTO2);
        printingServerDTO1.setPrintingServerId(null);
        assertThat(printingServerDTO1).isNotEqualTo(printingServerDTO2);
    }
}
