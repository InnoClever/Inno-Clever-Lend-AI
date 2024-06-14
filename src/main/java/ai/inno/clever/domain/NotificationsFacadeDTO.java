package ai.inno.clever.domain;


import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class NotificationsFacadeDTO implements Serializable {

    private String recipient;
    private String subjectKey;
    private String templateName;
    private Map<String, Object> model;
    private String url;

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubjectKey() {
        return subjectKey;
    }

    public void setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationsFacadeDTO that = (NotificationsFacadeDTO) o;
        return Objects.equals(recipient, that.recipient) && Objects.equals(subjectKey, that.subjectKey) && Objects.equals(templateName, that.templateName) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, subjectKey, templateName, url);
    }

    @Override
    public String toString() {
        return "NotificationsFacadeDTO{" +
            "recipient='" + recipient + '\'' +
            ", subjectKey='" + subjectKey + '\'' +
            ", templateName='" + templateName + '\'' +
            ", link='" + url + '\'' +
            '}';
    }
}
