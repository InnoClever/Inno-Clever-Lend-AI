package ai.inno.clever.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class NotificationsDTO implements Serializable {

    private Long id;

    //    @NotNull
    private String source;

    @NotNull
    private String recipient;

    private String title;

    private String body;


    private String link;

    private Instant datetime;

    private Instant acknowledgeDatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public Instant getAcknowledgeDatetime() {
        return acknowledgeDatetime;
    }

    public void setAcknowledgeDatetime(Instant acknowledgeDatetime) {
        this.acknowledgeDatetime = acknowledgeDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationsDTO)) {
            return false;
        }

        NotificationsDTO notificationsDTO = (NotificationsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationsDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", recipient='" + getRecipient() + "'" +
            ", title='" + getTitle() + "'" +
            ", body='" + getBody() + "'" +
            ", link='" + getLink() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", acknowledgeDatetime='" + getAcknowledgeDatetime() + "'" +
            "}";
    }
}
