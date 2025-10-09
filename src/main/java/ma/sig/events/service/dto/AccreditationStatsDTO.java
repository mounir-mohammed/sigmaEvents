package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Map;

public class AccreditationStatsDTO implements Serializable {

    // === Top stats ===
    private Long totalAccreditations;
    private Long printedAccreditations;
    private Long inProgressAccreditations;

    // === Graphs ===
    private Map<String, Long> printedPerDay;
    private Map<String, Long> statusCounts;
    private Map<String, Long> byCategory;
    private Map<String, Long> byFunction;
    private Map<String, Long> bySite;
    private Map<String, Long> byOrganization;
    private Map<String, Long> byType;
    private Map<String, Long> byNationality;

    // === Getters / Setters ===
    public Long getTotalAccreditations() {
        return totalAccreditations;
    }

    public void setTotalAccreditations(Long totalAccreditations) {
        this.totalAccreditations = totalAccreditations;
    }

    public Long getPrintedAccreditations() {
        return printedAccreditations;
    }

    public void setPrintedAccreditations(Long printedAccreditations) {
        this.printedAccreditations = printedAccreditations;
    }

    public Long getInProgressAccreditations() {
        return inProgressAccreditations;
    }

    public void setInProgressAccreditations(Long inProgressAccreditations) {
        this.inProgressAccreditations = inProgressAccreditations;
    }

    public Map<String, Long> getPrintedPerDay() {
        return printedPerDay;
    }

    public void setPrintedPerDay(Map<String, Long> printedPerDay) {
        this.printedPerDay = printedPerDay;
    }

    public Map<String, Long> getStatusCounts() {
        return statusCounts;
    }

    public void setStatusCounts(Map<String, Long> statusCounts) {
        this.statusCounts = statusCounts;
    }

    public Map<String, Long> getByCategory() {
        return byCategory;
    }

    public void setByCategory(Map<String, Long> byCategory) {
        this.byCategory = byCategory;
    }

    public Map<String, Long> getByFunction() {
        return byFunction;
    }

    public void setByFunction(Map<String, Long> byFunction) {
        this.byFunction = byFunction;
    }

    public Map<String, Long> getBySite() {
        return bySite;
    }

    public void setBySite(Map<String, Long> bySite) {
        this.bySite = bySite;
    }

    public Map<String, Long> getByOrganization() {
        return byOrganization;
    }

    public void setByOrganization(Map<String, Long> byOrganization) {
        this.byOrganization = byOrganization;
    }

    public Map<String, Long> getByType() {
        return byType;
    }

    public void setByType(Map<String, Long> byType) {
        this.byType = byType;
    }

    public Map<String, Long> getByNationality() {
        return byNationality;
    }

    public void setByNationality(Map<String, Long> byNationality) {
        this.byNationality = byNationality;
    }
}
