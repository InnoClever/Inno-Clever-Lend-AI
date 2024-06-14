package ai.inno.clever.service.dto.aisp;

import java.util.Map;
import java.util.Objects;

public class Transaction {
    private String id;
    private String accountId;
    private Boolean duplicated;
    private String mode;
    private String madeOn;
    private String description;
    private String category;
    private String amount;
    private String currencyCode;
    private String bookingDateTime;
    private String status;
    private Map<String, String> extra;
    private String creditDebitIndicator;
    private String createdAt;
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreditDebitIndicator() {
        return creditDebitIndicator;
    }

    public void setCreditDebitIndicator(String creditDebitIndicator) {
        this.creditDebitIndicator = creditDebitIndicator;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDuplicated() {
        return duplicated;
    }

    public void setDuplicated(Boolean duplicated) {
        this.duplicated = duplicated;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMadeOn() {
        return madeOn;
    }

    public void setMadeOn(String madeOn) {
        this.madeOn = madeOn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id='" + id + '\'' +
            "accountId='" + accountId + '\'' +
            ", amount='" + amount + '\'' +
            ", status='" + status + '\'' +
            ", creditDebitIndicator='" + creditDebitIndicator + '\'' +
            ", bookingDateTime='" + bookingDateTime + '\'' +
            ", createdAt='" + createdAt + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) &&
            Objects.equals(getAccountId(), that.getAccountId()) &&
            Objects.equals(getDuplicated(), that.getDuplicated()) &&
            Objects.equals(getMode(), that.getMode()) &&
            Objects.equals(getMadeOn(), that.getMadeOn()) &&
            Objects.equals(getDescription(), that.getDescription()) &&
            Objects.equals(getCategory(), that.getCategory()) &&
            Objects.equals(getAmount(), that.getAmount()) &&
            Objects.equals(getCurrencyCode(), that.getCurrencyCode()) &&
            Objects.equals(getBookingDateTime(), that.getBookingDateTime()) &&
            Objects.equals(getStatus(), that.getStatus()) &&
            Objects.equals(getExtra(), that.getExtra()) &&
            Objects.equals(getCreditDebitIndicator(), that.getCreditDebitIndicator()) &&
            Objects.equals(getCreatedAt(), that.getCreatedAt()) &&
            Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountId(), getDuplicated(), getMode(), getMadeOn(), getDescription(), getCategory(), getAmount(), getCurrencyCode(), getBookingDateTime(), getStatus(), getExtra(), getCreditDebitIndicator(), getCreatedAt(), getUpdatedAt());
    }
}
