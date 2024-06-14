package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Identificator {
    private String k;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }
}
