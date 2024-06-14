package ai.inno.clever.service.dto.accounting;

public class AccountingDTO {

    private String date;

    private Double debtorAmount;

    private Double creditorAmount;

    private String documentNo;

    private String account;

    private String description;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDebtorAmount() {
        return debtorAmount;
    }

    public void setDebtorAmount(Double debtorAmount) {
        this.debtorAmount = debtorAmount;
    }

    public Double getCreditorAmount() {
        return creditorAmount;
    }

    public void setCreditorAmount(Double creditorAmount) {
        this.creditorAmount = creditorAmount;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AccountingDTO{" +
            "date='" + date + '\'' +
            ", debtorAmount=" + debtorAmount +
            ", creditorAmount=" + creditorAmount +
            ", documentNo='" + documentNo + '\'' +
            ", account='" + account + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
