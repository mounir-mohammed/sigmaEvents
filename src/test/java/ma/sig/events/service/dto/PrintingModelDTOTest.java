package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingModelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingModelDTO.class);
        PrintingModelDTO printingModelDTO1 = new PrintingModelDTO();
        printingModelDTO1.setPrintingModelId(1L);
        PrintingModelDTO printingModelDTO2 = new PrintingModelDTO();
        assertThat(printingModelDTO1).isNotEqualTo(printingModelDTO2);
        printingModelDTO2.setPrintingModelId(printingModelDTO1.getPrintingModelId());
        assertThat(printingModelDTO1).isEqualTo(printingModelDTO2);
        printingModelDTO2.setPrintingModelId(2L);
        assertThat(printingModelDTO1).isNotEqualTo(printingModelDTO2);
        printingModelDTO1.setPrintingModelId(null);
        assertThat(printingModelDTO1).isNotEqualTo(printingModelDTO2);
    }
}
