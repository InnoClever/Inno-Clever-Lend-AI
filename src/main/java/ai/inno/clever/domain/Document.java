package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    Identificator identificator;

    public Identificator getIdentificator() {
        return identificator;
    }

    public void setIdentificator(Identificator identificator) {
        this.identificator = identificator;
    }
}
