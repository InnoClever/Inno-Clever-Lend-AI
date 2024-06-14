package ai.inno.clever.service.dto.aisp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bank {
    private String bankId;
    private String bankName;
    private String instruction;
    private String logoUrl;
    private String country;

    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> addAccount(Account account){
        this.accounts.add(account);
        return this.accounts;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return Objects.equals(getBankId(), bank.getBankId()) &&
            Objects.equals(getBankName(), bank.getBankName()) &&
            Objects.equals(getInstruction(), bank.getInstruction()) &&
            Objects.equals(getLogoUrl(), bank.getLogoUrl()) &&
            Objects.equals(getCountry(), bank.getCountry()) &&
            Objects.equals(getAccounts(), bank.getAccounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBankId(), getBankName(), getInstruction(), getLogoUrl(), getCountry(), getAccounts());
    }
}
