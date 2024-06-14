package ai.inno.clever.service.dto.aisp;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Account {

    private String id;

    private String name;

//    private String nature;

    private BigDecimal balance;

    private String currency;


    private String accountHolderType;

    private String accountType;


    private String connectionId;

    private String aliasName;

    private String holderName;

    private String accountNumber;

    private String providerName;

    private List<Transaction> transactions;


    private String iban;

    private String sortCode;

    private String accountName;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

//    public String getNature() {
//        return nature;
//    }
//
//    public void setNature(String nature) {
//        this.nature = nature;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountHolderType() {
        return accountHolderType;
    }

    public void setAccountHolderType(String accountHolderType) {
        this.accountHolderType = accountHolderType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId()) &&
            Objects.equals(getName(), account.getName()) &&
            Objects.equals(getBalance(), account.getBalance()) &&
            Objects.equals(getCurrency(), account.getCurrency()) &&
            Objects.equals(getAccountHolderType(), account.getAccountHolderType()) &&
            Objects.equals(getAccountType(), account.getAccountType()) &&
            Objects.equals(getConnectionId(), account.getConnectionId()) &&
            Objects.equals(getAliasName(), account.getAliasName()) &&
            Objects.equals(getHolderName(), account.getHolderName()) &&
            Objects.equals(getAccountNumber(), account.getAccountNumber()) &&
            Objects.equals(getProviderName(), account.getProviderName()) &&
            Objects.equals(getTransactions(), account.getTransactions()) &&
            Objects.equals(getIban(), account.getIban()) &&
            Objects.equals(getSortCode(), account.getSortCode()) &&
            Objects.equals(getAccountName(), account.getAccountName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBalance(), getCurrency(), getAccountHolderType(), getAccountType(), getConnectionId(), getAliasName(), getHolderName(), getAccountNumber(), getProviderName(), getTransactions(), getIban(), getSortCode(), getAccountName());
    }
}
