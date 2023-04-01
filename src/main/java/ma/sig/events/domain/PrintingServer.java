package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PrintingServer.
 */
@Entity
@Table(name = "printing_server")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingServer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printing_server_id")
    private Long printingServerId;

    @Column(name = "printing_server_name")
    private String printingServerName;

    @Size(max = 200)
    @Column(name = "printing_server_description", length = 200)
    private String printingServerDescription;

    @Column(name = "printing_server_host")
    private String printingServerHost;

    @Column(name = "printing_server_port")
    private String printingServerPort;

    @Column(name = "printing_server_dns")
    private String printingServerDns;

    @Column(name = "printing_server_proxy")
    private String printingServerProxy;

    @Column(name = "printing_server_param_1")
    private String printingServerParam1;

    @Column(name = "printing_server_param_2")
    private String printingServerParam2;

    @Column(name = "printing_server_param_3")
    private String printingServerParam3;

    @Column(name = "printing_server_stat")
    private Boolean printingServerStat;

    @Column(name = "printing_server_params")
    private String printingServerParams;

    @Column(name = "printing_server_attributs")
    private String printingServerAttributs;

    @OneToMany(mappedBy = "printingServer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "eventForms",
            "eventFields",
            "eventControls",
            "areas",
            "fonctions",
            "categories",
            "printingModels",
            "codes",
            "infoSupps",
            "attachements",
            "organizs",
            "photoArchives",
            "sites",
            "accreditations",
            "notes",
            "operationHistories",
            "printingCentres",
            "settings",
            "printingServers",
            "checkAccreditationHistories",
            "checkAccreditationReports",
            "accreditationTypes",
            "dayPassInfos",
            "language",
        },
        allowSetters = true
    )
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getPrintingServerId() {
        return this.printingServerId;
    }

    public PrintingServer printingServerId(Long printingServerId) {
        this.setPrintingServerId(printingServerId);
        return this;
    }

    public void setPrintingServerId(Long printingServerId) {
        this.printingServerId = printingServerId;
    }

    public String getPrintingServerName() {
        return this.printingServerName;
    }

    public PrintingServer printingServerName(String printingServerName) {
        this.setPrintingServerName(printingServerName);
        return this;
    }

    public void setPrintingServerName(String printingServerName) {
        this.printingServerName = printingServerName;
    }

    public String getPrintingServerDescription() {
        return this.printingServerDescription;
    }

    public PrintingServer printingServerDescription(String printingServerDescription) {
        this.setPrintingServerDescription(printingServerDescription);
        return this;
    }

    public void setPrintingServerDescription(String printingServerDescription) {
        this.printingServerDescription = printingServerDescription;
    }

    public String getPrintingServerHost() {
        return this.printingServerHost;
    }

    public PrintingServer printingServerHost(String printingServerHost) {
        this.setPrintingServerHost(printingServerHost);
        return this;
    }

    public void setPrintingServerHost(String printingServerHost) {
        this.printingServerHost = printingServerHost;
    }

    public String getPrintingServerPort() {
        return this.printingServerPort;
    }

    public PrintingServer printingServerPort(String printingServerPort) {
        this.setPrintingServerPort(printingServerPort);
        return this;
    }

    public void setPrintingServerPort(String printingServerPort) {
        this.printingServerPort = printingServerPort;
    }

    public String getPrintingServerDns() {
        return this.printingServerDns;
    }

    public PrintingServer printingServerDns(String printingServerDns) {
        this.setPrintingServerDns(printingServerDns);
        return this;
    }

    public void setPrintingServerDns(String printingServerDns) {
        this.printingServerDns = printingServerDns;
    }

    public String getPrintingServerProxy() {
        return this.printingServerProxy;
    }

    public PrintingServer printingServerProxy(String printingServerProxy) {
        this.setPrintingServerProxy(printingServerProxy);
        return this;
    }

    public void setPrintingServerProxy(String printingServerProxy) {
        this.printingServerProxy = printingServerProxy;
    }

    public String getPrintingServerParam1() {
        return this.printingServerParam1;
    }

    public PrintingServer printingServerParam1(String printingServerParam1) {
        this.setPrintingServerParam1(printingServerParam1);
        return this;
    }

    public void setPrintingServerParam1(String printingServerParam1) {
        this.printingServerParam1 = printingServerParam1;
    }

    public String getPrintingServerParam2() {
        return this.printingServerParam2;
    }

    public PrintingServer printingServerParam2(String printingServerParam2) {
        this.setPrintingServerParam2(printingServerParam2);
        return this;
    }

    public void setPrintingServerParam2(String printingServerParam2) {
        this.printingServerParam2 = printingServerParam2;
    }

    public String getPrintingServerParam3() {
        return this.printingServerParam3;
    }

    public PrintingServer printingServerParam3(String printingServerParam3) {
        this.setPrintingServerParam3(printingServerParam3);
        return this;
    }

    public void setPrintingServerParam3(String printingServerParam3) {
        this.printingServerParam3 = printingServerParam3;
    }

    public Boolean getPrintingServerStat() {
        return this.printingServerStat;
    }

    public PrintingServer printingServerStat(Boolean printingServerStat) {
        this.setPrintingServerStat(printingServerStat);
        return this;
    }

    public void setPrintingServerStat(Boolean printingServerStat) {
        this.printingServerStat = printingServerStat;
    }

    public String getPrintingServerParams() {
        return this.printingServerParams;
    }

    public PrintingServer printingServerParams(String printingServerParams) {
        this.setPrintingServerParams(printingServerParams);
        return this;
    }

    public void setPrintingServerParams(String printingServerParams) {
        this.printingServerParams = printingServerParams;
    }

    public String getPrintingServerAttributs() {
        return this.printingServerAttributs;
    }

    public PrintingServer printingServerAttributs(String printingServerAttributs) {
        this.setPrintingServerAttributs(printingServerAttributs);
        return this;
    }

    public void setPrintingServerAttributs(String printingServerAttributs) {
        this.printingServerAttributs = printingServerAttributs;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setPrintingServer(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setPrintingServer(this));
        }
        this.printingCentres = printingCentres;
    }

    public PrintingServer printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public PrintingServer addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setPrintingServer(this);
        return this;
    }

    public PrintingServer removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setPrintingServer(null);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public PrintingServer event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrintingServer)) {
            return false;
        }
        return printingServerId != null && printingServerId.equals(((PrintingServer) o).printingServerId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingServer{" +
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
            "}";
    }
}
