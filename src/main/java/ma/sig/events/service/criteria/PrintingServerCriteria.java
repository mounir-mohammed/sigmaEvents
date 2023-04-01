package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.PrintingServer} entity. This class is used
 * in {@link ma.sig.events.web.rest.PrintingServerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /printing-servers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingServerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter printingServerId;

    private StringFilter printingServerName;

    private StringFilter printingServerDescription;

    private StringFilter printingServerHost;

    private StringFilter printingServerPort;

    private StringFilter printingServerDns;

    private StringFilter printingServerProxy;

    private StringFilter printingServerParam1;

    private StringFilter printingServerParam2;

    private StringFilter printingServerParam3;

    private BooleanFilter printingServerStat;

    private StringFilter printingServerParams;

    private StringFilter printingServerAttributs;

    private LongFilter printingCentreId;

    private LongFilter eventId;

    private Boolean distinct;

    public PrintingServerCriteria() {}

    public PrintingServerCriteria(PrintingServerCriteria other) {
        this.printingServerId = other.printingServerId == null ? null : other.printingServerId.copy();
        this.printingServerName = other.printingServerName == null ? null : other.printingServerName.copy();
        this.printingServerDescription = other.printingServerDescription == null ? null : other.printingServerDescription.copy();
        this.printingServerHost = other.printingServerHost == null ? null : other.printingServerHost.copy();
        this.printingServerPort = other.printingServerPort == null ? null : other.printingServerPort.copy();
        this.printingServerDns = other.printingServerDns == null ? null : other.printingServerDns.copy();
        this.printingServerProxy = other.printingServerProxy == null ? null : other.printingServerProxy.copy();
        this.printingServerParam1 = other.printingServerParam1 == null ? null : other.printingServerParam1.copy();
        this.printingServerParam2 = other.printingServerParam2 == null ? null : other.printingServerParam2.copy();
        this.printingServerParam3 = other.printingServerParam3 == null ? null : other.printingServerParam3.copy();
        this.printingServerStat = other.printingServerStat == null ? null : other.printingServerStat.copy();
        this.printingServerParams = other.printingServerParams == null ? null : other.printingServerParams.copy();
        this.printingServerAttributs = other.printingServerAttributs == null ? null : other.printingServerAttributs.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PrintingServerCriteria copy() {
        return new PrintingServerCriteria(this);
    }

    public LongFilter getPrintingServerId() {
        return printingServerId;
    }

    public LongFilter printingServerId() {
        if (printingServerId == null) {
            printingServerId = new LongFilter();
        }
        return printingServerId;
    }

    public void setPrintingServerId(LongFilter printingServerId) {
        this.printingServerId = printingServerId;
    }

    public StringFilter getPrintingServerName() {
        return printingServerName;
    }

    public StringFilter printingServerName() {
        if (printingServerName == null) {
            printingServerName = new StringFilter();
        }
        return printingServerName;
    }

    public void setPrintingServerName(StringFilter printingServerName) {
        this.printingServerName = printingServerName;
    }

    public StringFilter getPrintingServerDescription() {
        return printingServerDescription;
    }

    public StringFilter printingServerDescription() {
        if (printingServerDescription == null) {
            printingServerDescription = new StringFilter();
        }
        return printingServerDescription;
    }

    public void setPrintingServerDescription(StringFilter printingServerDescription) {
        this.printingServerDescription = printingServerDescription;
    }

    public StringFilter getPrintingServerHost() {
        return printingServerHost;
    }

    public StringFilter printingServerHost() {
        if (printingServerHost == null) {
            printingServerHost = new StringFilter();
        }
        return printingServerHost;
    }

    public void setPrintingServerHost(StringFilter printingServerHost) {
        this.printingServerHost = printingServerHost;
    }

    public StringFilter getPrintingServerPort() {
        return printingServerPort;
    }

    public StringFilter printingServerPort() {
        if (printingServerPort == null) {
            printingServerPort = new StringFilter();
        }
        return printingServerPort;
    }

    public void setPrintingServerPort(StringFilter printingServerPort) {
        this.printingServerPort = printingServerPort;
    }

    public StringFilter getPrintingServerDns() {
        return printingServerDns;
    }

    public StringFilter printingServerDns() {
        if (printingServerDns == null) {
            printingServerDns = new StringFilter();
        }
        return printingServerDns;
    }

    public void setPrintingServerDns(StringFilter printingServerDns) {
        this.printingServerDns = printingServerDns;
    }

    public StringFilter getPrintingServerProxy() {
        return printingServerProxy;
    }

    public StringFilter printingServerProxy() {
        if (printingServerProxy == null) {
            printingServerProxy = new StringFilter();
        }
        return printingServerProxy;
    }

    public void setPrintingServerProxy(StringFilter printingServerProxy) {
        this.printingServerProxy = printingServerProxy;
    }

    public StringFilter getPrintingServerParam1() {
        return printingServerParam1;
    }

    public StringFilter printingServerParam1() {
        if (printingServerParam1 == null) {
            printingServerParam1 = new StringFilter();
        }
        return printingServerParam1;
    }

    public void setPrintingServerParam1(StringFilter printingServerParam1) {
        this.printingServerParam1 = printingServerParam1;
    }

    public StringFilter getPrintingServerParam2() {
        return printingServerParam2;
    }

    public StringFilter printingServerParam2() {
        if (printingServerParam2 == null) {
            printingServerParam2 = new StringFilter();
        }
        return printingServerParam2;
    }

    public void setPrintingServerParam2(StringFilter printingServerParam2) {
        this.printingServerParam2 = printingServerParam2;
    }

    public StringFilter getPrintingServerParam3() {
        return printingServerParam3;
    }

    public StringFilter printingServerParam3() {
        if (printingServerParam3 == null) {
            printingServerParam3 = new StringFilter();
        }
        return printingServerParam3;
    }

    public void setPrintingServerParam3(StringFilter printingServerParam3) {
        this.printingServerParam3 = printingServerParam3;
    }

    public BooleanFilter getPrintingServerStat() {
        return printingServerStat;
    }

    public BooleanFilter printingServerStat() {
        if (printingServerStat == null) {
            printingServerStat = new BooleanFilter();
        }
        return printingServerStat;
    }

    public void setPrintingServerStat(BooleanFilter printingServerStat) {
        this.printingServerStat = printingServerStat;
    }

    public StringFilter getPrintingServerParams() {
        return printingServerParams;
    }

    public StringFilter printingServerParams() {
        if (printingServerParams == null) {
            printingServerParams = new StringFilter();
        }
        return printingServerParams;
    }

    public void setPrintingServerParams(StringFilter printingServerParams) {
        this.printingServerParams = printingServerParams;
    }

    public StringFilter getPrintingServerAttributs() {
        return printingServerAttributs;
    }

    public StringFilter printingServerAttributs() {
        if (printingServerAttributs == null) {
            printingServerAttributs = new StringFilter();
        }
        return printingServerAttributs;
    }

    public void setPrintingServerAttributs(StringFilter printingServerAttributs) {
        this.printingServerAttributs = printingServerAttributs;
    }

    public LongFilter getPrintingCentreId() {
        return printingCentreId;
    }

    public LongFilter printingCentreId() {
        if (printingCentreId == null) {
            printingCentreId = new LongFilter();
        }
        return printingCentreId;
    }

    public void setPrintingCentreId(LongFilter printingCentreId) {
        this.printingCentreId = printingCentreId;
    }

    public LongFilter getEventId() {
        return eventId;
    }

    public LongFilter eventId() {
        if (eventId == null) {
            eventId = new LongFilter();
        }
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PrintingServerCriteria that = (PrintingServerCriteria) o;
        return (
            Objects.equals(printingServerId, that.printingServerId) &&
            Objects.equals(printingServerName, that.printingServerName) &&
            Objects.equals(printingServerDescription, that.printingServerDescription) &&
            Objects.equals(printingServerHost, that.printingServerHost) &&
            Objects.equals(printingServerPort, that.printingServerPort) &&
            Objects.equals(printingServerDns, that.printingServerDns) &&
            Objects.equals(printingServerProxy, that.printingServerProxy) &&
            Objects.equals(printingServerParam1, that.printingServerParam1) &&
            Objects.equals(printingServerParam2, that.printingServerParam2) &&
            Objects.equals(printingServerParam3, that.printingServerParam3) &&
            Objects.equals(printingServerStat, that.printingServerStat) &&
            Objects.equals(printingServerParams, that.printingServerParams) &&
            Objects.equals(printingServerAttributs, that.printingServerAttributs) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            printingServerId,
            printingServerName,
            printingServerDescription,
            printingServerHost,
            printingServerPort,
            printingServerDns,
            printingServerProxy,
            printingServerParam1,
            printingServerParam2,
            printingServerParam3,
            printingServerStat,
            printingServerParams,
            printingServerAttributs,
            printingCentreId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingServerCriteria{" +
            (printingServerId != null ? "printingServerId=" + printingServerId + ", " : "") +
            (printingServerName != null ? "printingServerName=" + printingServerName + ", " : "") +
            (printingServerDescription != null ? "printingServerDescription=" + printingServerDescription + ", " : "") +
            (printingServerHost != null ? "printingServerHost=" + printingServerHost + ", " : "") +
            (printingServerPort != null ? "printingServerPort=" + printingServerPort + ", " : "") +
            (printingServerDns != null ? "printingServerDns=" + printingServerDns + ", " : "") +
            (printingServerProxy != null ? "printingServerProxy=" + printingServerProxy + ", " : "") +
            (printingServerParam1 != null ? "printingServerParam1=" + printingServerParam1 + ", " : "") +
            (printingServerParam2 != null ? "printingServerParam2=" + printingServerParam2 + ", " : "") +
            (printingServerParam3 != null ? "printingServerParam3=" + printingServerParam3 + ", " : "") +
            (printingServerStat != null ? "printingServerStat=" + printingServerStat + ", " : "") +
            (printingServerParams != null ? "printingServerParams=" + printingServerParams + ", " : "") +
            (printingServerAttributs != null ? "printingServerAttributs=" + printingServerAttributs + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
