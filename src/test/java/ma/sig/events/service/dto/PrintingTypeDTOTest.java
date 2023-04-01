package ma.sig.events.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingTypeDTO.class);
        PrintingTypeDTO printingTypeDTO1 = new PrintingTypeDTO();
        printingTypeDTO1.setPrintingTypeId(1L);
        PrintingTypeDTO printingTypeDTO2 = new PrintingTypeDTO();
        assertThat(printingTypeDTO1).isNotEqualTo(printingTypeDTO2);
        printingTypeDTO2.setPrintingTypeId(printingTypeDTO1.getPrintingTypeId());
        assertThat(printingTypeDTO1).isEqualTo(printingTypeDTO2);
        printingTypeDTO2.setPrintingTypeId(2L);
        assertThat(printingTypeDTO1).isNotEqualTo(printingTypeDTO2);
        printingTypeDTO1.setPrintingTypeId(null);
        assertThat(printingTypeDTO1).isNotEqualTo(printingTypeDTO2);
    }
}
