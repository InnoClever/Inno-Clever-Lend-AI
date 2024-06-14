package ai.inno.clever.domain.rbrotest;

import java.util.List;

public class RecalculateLimitsDTO {
    private String factoringRequestId;
    private List<DebtorInformationRecalculate> debtorInformationRecalculateList;
    private AdherentLimitsSettings adherentLimitsSettings; //restart

    public String getFactoringRequestId() {
        return factoringRequestId;
    }

    public void setFactoringRequestId(String factoringRequestId) {
        this.factoringRequestId = factoringRequestId;
    }

    public List<DebtorInformationRecalculate> getDebtorInformationRecalculateList() {
        return debtorInformationRecalculateList;
    }

    public void setDebtorInformationRecalculateList(List<DebtorInformationRecalculate> debtorInformationRecalculateList) {
        this.debtorInformationRecalculateList = debtorInformationRecalculateList;
    }

    public AdherentLimitsSettings getAdherentLimitsSettings() {
        return adherentLimitsSettings;
    }

    public void setAdherentLimitsSettings(AdherentLimitsSettings adherentLimitsSettings) {
        this.adherentLimitsSettings = adherentLimitsSettings;
    }

    @Override
    public String toString() {
        return "RecalculateLimitsDTO{" +
            "factoringRequestId='" + factoringRequestId + '\'' +
            ", debtorInformationRecalculateList=" + debtorInformationRecalculateList +
            ", adherentLimitsSettings=" + adherentLimitsSettings +
            '}';
    }
}
