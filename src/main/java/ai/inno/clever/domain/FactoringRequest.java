package ai.inno.clever.domain;

import ai.inno.clever.domain.enumeration.Status;
import ai.inno.clever.service.dto.core.DebtorDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A FactoringRequest.
 */
@Document(collection = "factoring_request")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "factoringrequest")
public class FactoringRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    public static final String SEQUENCE_NAME = "factoring_request_sequence";

    @Id
    private String id;

    @Field("seqId")
    private long seqId;

    @Field("date")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant date;

    private Instant dateOfRegister;

    @Field("status")
    private Status status;

    @Field("progress")
    private Long progress;

    @Field("msg")
    private String msg;

    @Field("processId")
    private String processId;

    @Field("adherentId")
    private Long adherentId;

    @Field("value")
    private BigDecimal value;

    @Field("debtors")
    private Set<DebtorDTO> debtors = new HashSet<>();

    @DBRef
    @Field("company")
    @JsonIgnoreProperties(value = {"factoringRequests"}, allowSetters = true)
    private Company company;

    @Field("owner_login")
    private String ownerLogin;

    @Field("requester_login")
    private String requesterLogin;

    @Field("requester_email")
    private String requesterEmail;

    @Field("requester_full_name")
    private String requesterFullName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FactoringRequest id(String id) {
        this.id = id;
        return this;
    }

    public long getSeqId() {
        return seqId;
    }

    public void setSeqId(long seqId) {
        this.seqId = seqId;
    }

    public Instant getDate() {
        return this.date;
    }

    public FactoringRequest date(Instant date) {
        this.date = date;
        return this;
    }

    public Instant getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(Instant dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Status getStatus() {
        return this.status;
    }

    public FactoringRequest status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getProgress() {
        return this.progress;
    }

    public FactoringRequest progress(Long progress) {
        this.progress = progress;
        return this;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public String getMsg() {
        return this.msg;
    }

    public FactoringRequest msg(String msg) {
        this.msg = msg;
        return this;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Company getCompany() {
        return this.company;
    }

    public FactoringRequest company(Company company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getProcessId() {
        return processId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Long getAdherentId() {
        return adherentId;
    }

    public void setAdherentId(Long adherentId) {
        this.adherentId = adherentId;
    }

    public Set<DebtorDTO> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<DebtorDTO> debtors) {
        this.debtors = debtors;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getRequesterLogin() {
        return requesterLogin;
    }

    public void setRequesterLogin(String requesterLogin) {
        this.requesterLogin = requesterLogin;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getRequesterFullName() {
        return requesterFullName;
    }

    public void setRequesterFullName(String requesterFullName) {
        this.requesterFullName = requesterFullName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactoringRequest)) {
            return false;
        }
        return id != null && id.equals(((FactoringRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "FactoringRequest{" +
            "id='" + id + '\'' +
            ", seqId=" + seqId +
            ", date=" + date +
            ", dateOfRegister=" + dateOfRegister +
            ", status=" + status +
            ", progress=" + progress +
            ", msg='" + msg + '\'' +
            ", processId='" + processId + '\'' +
            ", adherentId=" + adherentId +
            ", value=" + value +
            ", debtors=" + debtors +
            ", company=" + company +
            ", ownerLogin='" + ownerLogin + '\'' +
            ", requesterLogin='" + requesterLogin + '\'' +
            ", requesterEmail='" + requesterEmail + '\'' +
            ", requesterFullName='" + requesterFullName + '\'' +
            '}';
    }
}
