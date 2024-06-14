package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Solution {
    private String gRecaptchaResponse;

    public String getgRecaptchaResponse() {
        return gRecaptchaResponse;
    }

    public void setgRecaptchaResponse(String gRecaptchaResponse) {
        this.gRecaptchaResponse = gRecaptchaResponse;
    }
}
