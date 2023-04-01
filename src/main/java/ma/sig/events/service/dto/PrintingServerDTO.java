package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.PrintingServer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingServerDTO implements Serializable {

    private Long printingServerId;

    private String printingServerName;

    @Size(max = 200)
    private String printingServerDescription;

    private String printingServerHost;

    private String printingServerPort;

    private String printingServerDns;

    private String printingServerProxy;

    private String printingServerParam1;

    private String printingServerParam2;

    private String printingServerParam3;

    private Boolean printingServerStat;

    private String printingServerParams;

    private String printingServerAttributs;

    private EventDTO event;

    public Long getPrintingServerId() {
        return printingServerId;
    }

    public void setPrintingServerId(Long printingServerId) {
        this.printingServerId = printingServerId;
    }

    public String getPrintingServerName() {
        return printingServerName;
    }

    public void setPrintingServerName(String printingServerName) {
        this.printingServerName = printingServerName;
    }

    public String getPrintingServerDescription() {
        return printingServerDescription;
    }

    public void setPrintingServerDescription(String printingServerDescription) {
        this.printingServerDescription = printingServerDescription;
    }

    public String getPrintingServerHost() {
        return printingServerHost;
    }

    public void setPrintingServerHost(String printingServerHost) {
        this.printingServerHost = printingServerHost;
    }

    public String getPrintingServerPort() {
        return printingServerPort;
    }

    public void setPrintingServerPort(String printingServerPort) {
        this.printingServerPort = printingServerPort;
    }

    public String getPrintingServerDns() {
        return printingServerDns;
    }

    public void setPrintingServerDns(String printingServerDns) {
        this.printingServerDns = printingServerDns;
    }

    public String getPrintingServerProxy() {
        return printingServerProxy;
    }

    public void setPrintingServerProxy(String printingServerProxy) {
        this.printingServerProxy = printingServerProxy;
    }

    public String getPrintingServerParam1() {
        return printingServerParam1;
    }

    public void setPrintingServerParam1(String printingServerParam1) {
        this.printingServerParam1 = printingServerParam1;
    }

    public String getPrintingServerParam2() {
        return printingServerParam2;
    }

    public void setPrintingServerParam2(String printingServerParam2) {
        this.printingServerParam2 = printingServerParam2;
    }

    public String getPrintingServerParam3() {
        return printingServerParam3;
    }

    public void setPrintingServerParam3(String printingServerParam3) {
        this.printingServerParam3 = printingServerParam3;
    }

    public Boolean getPrintingServerStat() {
        return printingServerStat;
    }

    public void setPrintingServerStat(Boolean printingServerStat) {
        this.printingServerStat = printingServerStat;
    }

    public String getPrintingServerParams() {
        return printingServerParams;
    }

    public void setPrintingServerParams(String printingServerParams) {
        this.printingServerParams = printingServerParams;
    }

    public String getPrintingServerAttributs() {
        return printingServerAttributs;
    }

    public void setPrintingServerAttributs(String printingServerAttributs) {
        this.printingServerAttributs = printingServerAttributs;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrintingServerDTO)) {
            return false;
        }

        PrintingServerDTO printingServerDTO = (PrintingServerDTO) o;
        if (this.printingServerId == null) {
            return false;
        }
        return Objects.equals(this.printingServerId, printingServerDTO.printingServerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.printingServerId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingServerDTO{" +
            "printingServerId=" + getPrintingServerId() +
            ", printingServerName='" + getPrintingServerName() + "'" +
            ", printingServerDescription='" + getPrintingServerDescription() + "'" +
            ", printingServerHost='" + getPrintingServerHost() + "'" +
            ", printingServerPort='" + getPrintingServerPort() + "'" +
            ", printingServerDns='" + getPrintingServerDns() + "'" +
            ", printingServerProxy='" + getPrintingServerProxy() + "'" +
            ", printingServerParam1='" + getPrintingServerParam1() + "'" +
            ", printingServerParam2='" + getPrintingServerParam2() + "'" +
            ", printingServerParam3='" + getPrintingServerParam3() + "'" +
            ", printingServerStat='" + getPrintingServerStat() + "'" +
            ", printingServerParams='" + getPrintingServerParams() + "'" +
            ", printingServerAttributs='" + getPrintingServerAttributs() + "'" +
            ", event=" + getEvent() +
            "}";
    }
}
