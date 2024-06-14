package ai.inno.clever.domain.rbrotest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdherentDTOWrapper {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AdherentDTO adherent;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FactoringRequest factoringRequest;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String aispType;

    public String getAispType() {
        return aispType;
    }

    public void setAispType(String aispType) {
        this.aispType = aispType;
    }

    public AdherentDTO getAdherent() {
        return adherent;
    }

    public void setAdherent(AdherentDTO adherent) {
        this.adherent = adherent;
    }

    public FactoringRequest getFactoringRequest() {
        return factoringRequest;
    }

    public void setFactoringRequest(FactoringRequest factoringRequest) {
        this.factoringRequest = factoringRequest;
    }
}
