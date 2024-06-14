package ai.inno.clever.service.dto;

public class AdherentEligibilityChecks {
    private boolean positivePaymentIncidents;
    private boolean underInsolvencyProceedingsBpi;
    private boolean outstandingDebts;
    private boolean positiveEquity;
    private boolean positiveNetProfitYm1;
    private String companyStatus;

    public boolean isPositivePaymentIncidents() {
        return positivePaymentIncidents;
    }

    public void setPositivePaymentIncidents(boolean positivePaymentIncidents) {
        this.positivePaymentIncidents = positivePaymentIncidents;
    }

    public boolean isUnderInsolvencyProceedingsBpi() {
        return underInsolvencyProceedingsBpi;
    }

    public void setUnderInsolvencyProceedingsBpi(boolean underInsolvencyProceedingsBpi) {
        this.underInsolvencyProceedingsBpi = underInsolvencyProceedingsBpi;
    }

    public boolean isOutstandingDebts() {
        return outstandingDebts;
    }

    public void setOutstandingDebts(boolean outstandingDebts) {
        this.outstandingDebts = outstandingDebts;
    }

    public boolean isPositiveEquity() {
        return positiveEquity;
    }

    public void setPositiveEquity(boolean positiveEquity) {
        this.positiveEquity = positiveEquity;
    }

    public boolean isPositiveNetProfitYm1() {
        return positiveNetProfitYm1;
    }

    public void setPositiveNetProfitYm1(boolean positiveNetProfitYm1) {
        this.positiveNetProfitYm1 = positiveNetProfitYm1;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    @Override
    public String toString() {
        return "AdherentEligibilityChecks{" +
            "positivePaymentIncidents=" + positivePaymentIncidents +
            ", underInsolvencyProceedingsBpi=" + underInsolvencyProceedingsBpi +
            ", outstandingDebts=" + outstandingDebts +
            ", positiveEquity=" + positiveEquity +
            ", positiveNetProfitYm1=" + positiveNetProfitYm1 +
            ", companyStatus=" + companyStatus +

            '}';
    }
}
