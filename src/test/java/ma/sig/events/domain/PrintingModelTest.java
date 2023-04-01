package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingModel.class);
        PrintingModel printingModel1 = new PrintingModel();
        printingModel1.setPrintingModelId(1L);
        PrintingModel printingModel2 = new PrintingModel();
        printingModel2.setPrintingModelId(printingModel1.getPrintingModelId());
        assertThat(printingModel1).isEqualTo(printingModel2);
        printingModel2.setPrintingModelId(2L);
        assertThat(printingModel1).isNotEqualTo(printingModel2);
        printingModel1.setPrintingModelId(null);
        assertThat(printingModel1).isNotEqualTo(printingModel2);
    }
}
