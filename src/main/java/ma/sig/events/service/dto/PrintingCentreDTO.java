package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.PrintingCentre} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingCentreDTO implements Serializable {

    private Long printingCentreId;

    @Size(max = 200)
    private String printingCentreDescription;

    @NotNull
    @Size(max = 50)
    private String printingCentreName;

    @Lob
    private byte[] printingCentreLogo;

    private String printingCentreLogoContentType;
    private String printingCentreAdresse;

    private String printingCentreEmail;

    private String printingCentreTel;

    private String printingCentreFax;

    private String printingCentreResponsableName;

    private String printingParams;

    private String printingAttributs;

    private Boolean printingCentreStat;

    private CityDTO city;

    private CountryDTO country;

    private OrganizDTO organiz;

    private PrintingTypeDTO printingType;

    private PrintingServerDTO printingServer;

    private PrintingModelDTO printingModel;

    private LanguageDTO language;

    private EventDTO event;

    public Long getPrintingCentreId() {
        return printingCentreId;
    }

    public void setPrintingCentreId(Long printingCentreId) {
        this.printingCentreId = printingCentreId;
    }

    public String getPrintingCentreDescription() {
        return printingCentreDescription;
    }

    public void setPrintingCentreDescription(String printingCentreDescription) {
        this.printingCentreDescription = printingCentreDescription;
    }

    public String getPrintingCentreName() {
        return printingCentreName;
    }

    public void setPrintingCentreName(String printingCentreName) {
        this.printingCentreName = printingCentreName;
    }

    public byte[] getPrintingCentreLogo() {
        return printingCentreLogo;
    }

    public void setPrintingCentreLogo(byte[] printingCentreLogo) {
        this.printingCentreLogo = printingCentreLogo;
    }

    public String getPrintingCentreLogoContentType() {
        return printingCentreLogoContentType;
    }

    public void setPrintingCentreLogoContentType(String printingCentreLogoContentType) {
        this.printingCentreLogoContentType = printingCentreLogoContentType;
    }

    public String getPrintingCentreAdresse() {
        return printingCentreAdresse;
    }

    public void setPrintingCentreAdresse(String printingCentreAdresse) {
        this.printingCentreAdresse = printingCentreAdresse;
    }

    public String getPrintingCentreEmail() {
        return printingCentreEmail;
    }

    public void setPrintingCentreEmail(String printingCentreEmail) {
        this.printingCentreEmail = printingCentreEmail;
    }

    public String getPrintingCentreTel() {
        return printingCentreTel;
    }

    public void setPrintingCentreTel(String printingCentreTel) {
        this.printingCentreTel = printingCentreTel;
    }

    public String getPrintingCentreFax() {
        return printingCentreFax;
    }

    public void setPrintingCentreFax(String printingCentreFax) {
        this.printingCentreFax = printingCentreFax;
    }

    public String getPrintingCentreResponsableName() {
        return printingCentreResponsableName;
    }

    public void setPrintingCentreResponsableName(String printingCentreResponsableName) {
        this.printingCentreResponsableName = printingCentreResponsableName;
    }

    public String getPrintingParams() {
        return printingParams;
    }

    public void setPrintingParams(String printingParams) {
        this.printingParams = printingParams;
    }

    public String getPrintingAttributs() {
        return printingAttributs;
    }

    public void setPrintingAttributs(String printingAttributs) {
        this.printingAttributs = printingAttributs;
    }

    public Boolean getPrintingCentreStat() {
        return printingCentreStat;
    }

    public void setPrintingCentreStat(Boolean printingCentreStat) {
        this.printingCentreStat = printingCentreStat;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public OrganizDTO getOrganiz() {
        return organiz;
    }

    public void setOrganiz(OrganizDTO organiz) {
        this.organiz = organiz;
    }

    public PrintingTypeDTO getPrintingType() {
        return printingType;
    }

    public void setPrintingType(PrintingTypeDTO printingType) {
        this.printingType = printingType;
    }

    public PrintingServerDTO getPrintingServer() {
        return printingServer;
    }

    public void setPrintingServer(PrintingServerDTO printingServer) {
        this.printingServer = printingServer;
    }

    public PrintingModelDTO getPrintingModel() {
        return printingModel;
    }

    public void setPrintingModel(PrintingModelDTO printingModel) {
        this.printingModel = printingModel;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
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
        if (!(o instanceof PrintingCentreDTO)) {
            return false;
        }

        PrintingCentreDTO printingCentreDTO = (PrintingCentreDTO) o;
        if (this.printingCentreId == null) {
            return false;
        }
        return Objects.equals(this.printingCentreId, printingCentreDTO.printingCentreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.printingCentreId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingCentreDTO{" +
            "printingCentreId=" + getPrintingCentreId() +
            ", printingCentreDescription='" + getPrintingCentreDescription() + "'" +
            ", printingCentreName='" + getPrintingCentreName() + "'" +
            ", printingCentreLogo='" + getPrintingCentreLogo() + "'" +
            ", printingCentreAdresse='" + getPrintingCentreAdresse() + "'" +
            ", printingCentreEmail='" + getPrintingCentreEmail() + "'" +
            ", printingCentreTel='" + getPrintingCentreTel() + "'" +
            ", printingCentreFax='" + getPrintingCentreFax() + "'" +
            ", printingCentreResponsableName='" + getPrintingCentreResponsableName() + "'" +
            ", printingParams='" + getPrintingParams() + "'" +
            ", printingAttributs='" + getPrintingAttributs() + "'" +
            ", printingCentreStat='" + getPrintingCentreStat() + "'" +
            ", city=" + getCity() +
            ", country=" + getCountry() +
            ", organiz=" + getOrganiz() +
            ", printingType=" + getPrintingType() +
            ", printingServer=" + getPrintingServer() +
            ", printingModel=" + getPrintingModel() +
            ", language=" + getLanguage() +
            ", event=" + getEvent() +
            "}";
    }
}
