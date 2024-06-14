package ai.inno.clever.service.dto.core;


import ai.inno.clever.service.dto.core.enumeration.PaymentMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class DebtorDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private String city;

    private String registrationNo;

    private String industry;

    private String region;

    private String enterpriseCategory;

    private Instant establishmentDate;

    private String district;

    private String postalCode;

    private String taxCode;

    private String country;

    private BigDecimal factoringRequestValue;

    private BigDecimal salesVolumeInLast6Months;

    private String commercialRelationshipTrackRecord;

    private String salesVolumeInLast12Months;

    private String targetSalesVolumeInNext12Months;

    private PaymentMethod paymentMethod;

    private String commercialPaymentTerm;

    private String dso;

    private String setOfVolumePercentage12Months;

    private String noOfInvoicesPerMonth;

    private String companyDetailsJSON;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEnterpriseCategory() {
        return enterpriseCategory;
    }

    public void setEnterpriseCategory(String enterpriseCategory) {
        this.enterpriseCategory = enterpriseCategory;
    }

    public Instant getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(Instant establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getFactoringRequestValue() {
        return factoringRequestValue;
    }

    public void setFactoringRequestValue(BigDecimal factoringRequestValue) {
        this.factoringRequestValue = factoringRequestValue;
    }

    public BigDecimal getSalesVolumeInLast6Months() {
        return salesVolumeInLast6Months;
    }

    public void setSalesVolumeInLast6Months(BigDecimal salesVolumeInLast6Months) {
        this.salesVolumeInLast6Months = salesVolumeInLast6Months;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCommercialRelationshipTrackRecord() {
        return commercialRelationshipTrackRecord;
    }

    public void setCommercialRelationshipTrackRecord(String commercialRelationshipTrackRecord) {
        this.commercialRelationshipTrackRecord = commercialRelationshipTrackRecord;
    }

    public String getSalesVolumeInLast12Months() {
        return salesVolumeInLast12Months;
    }

    public void setSalesVolumeInLast12Months(String salesVolumeInLast12Months) {
        this.salesVolumeInLast12Months = salesVolumeInLast12Months;
    }

    public String getTargetSalesVolumeInNext12Months() {
        return targetSalesVolumeInNext12Months;
    }

    public void setTargetSalesVolumeInNext12Months(String targetSalesVolumeInNext12Months) {
        this.targetSalesVolumeInNext12Months = targetSalesVolumeInNext12Months;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCommercialPaymentTerm() {
        return commercialPaymentTerm;
    }

    public void setCommercialPaymentTerm(String commercialPaymentTerm) {
        this.commercialPaymentTerm = commercialPaymentTerm;
    }

    public String getDso() {
        return dso;
    }

    public void setDso(String dso) {
        this.dso = dso;
    }

    public String getSetOfVolumePercentage12Months() {
        return setOfVolumePercentage12Months;
    }

    public void setSetOfVolumePercentage12Months(String setOfVolumePercentage12Months) {
        this.setOfVolumePercentage12Months = setOfVolumePercentage12Months;
    }

    public String getNoOfInvoicesPerMonth() {
        return noOfInvoicesPerMonth;
    }

    public void setNoOfInvoicesPerMonth(String noOfInvoicesPerMonth) {
        this.noOfInvoicesPerMonth = noOfInvoicesPerMonth;
    }

    public String getCompanyDetailsJSON() {
        return companyDetailsJSON;
    }

    public void setCompanyDetailsJSON(String companyDetailsJSON) {
        this.companyDetailsJSON = companyDetailsJSON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DebtorDTO)) {
            return false;
        }

        DebtorDTO debtorDTO = (DebtorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, debtorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DebtorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", district='" + getDistrict() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
