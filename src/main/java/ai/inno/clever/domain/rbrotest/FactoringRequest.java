package ai.inno.clever.domain.rbrotest;

import ai.inno.clever.domain.rbrotest.mapper.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FactoringRequest {
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long seqId;

    private String date;

    private Status status;

    private Long progress;

    private String msg;

    private String processId;

    private Long adherentId;

    private BigDecimal value;

    private Set<DebtorDTO> debtors = new HashSet<>();

    private String ownerLogin;

    //    @JsonIgnoreProperties(value = { "factoringRequests" }, allowSetters = true)
    private Company company;

    public Set<DebtorDTO> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<DebtorDTO> debtors) {
        this.debtors = debtors;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

//    public Instant getDate() {
//        return date;
//    }
//
//    public void setDate(Instant date) {
//        this.date = date;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getProcessId() {
        return processId;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }
}

