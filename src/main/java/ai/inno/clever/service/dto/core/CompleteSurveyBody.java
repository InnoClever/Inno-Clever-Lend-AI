package ai.inno.clever.service.dto.core;

public class CompleteSurveyBody {
    private AdherentDTO adherent;
    private String aispType;

    public AdherentDTO getAdherent() {
        return adherent;
    }

    public void setAdherent(AdherentDTO adherent) {
        this.adherent = adherent;
    }

    public String getAispType() {
        return aispType;
    }

    public void setAispType(String aispType) {
        this.aispType = aispType;
    }
}
