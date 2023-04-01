package ma.sig.events.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ma.sig.events.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrintingServerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrintingServer.class);
        PrintingServer printingServer1 = new PrintingServer();
        printingServer1.setPrintingServerId(1L);
        PrintingServer printingServer2 = new PrintingServer();
        printingServer2.setPrintingServerId(printingServer1.getPrintingServerId());
        assertThat(printingServer1).isEqualTo(printingServer2);
        printingServer2.setPrintingServerId(2L);
        assertThat(printingServer1).isNotEqualTo(printingServer2);
        printingServer1.setPrintingServerId(null);
        assertThat(printingServer1).isNotEqualTo(printingServer2);
    }
}
