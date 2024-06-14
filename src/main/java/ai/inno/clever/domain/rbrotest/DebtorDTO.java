package ai.inno.clever.domain.rbrotest;

import ai.inno.clever.service.dto.core.enumeration.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DebtorDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String district;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String postalCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String taxCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String country;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal factoringRequestValue;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal salesVolumeInLast6Months;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String commercialRelationshipTrackRecord;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String salesVolumeInLast12Months;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String targetSalesVolumeInNext12Months;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentMethod paymentMethod;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String commercialPaymentTerm;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dso;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String setOfVolumePercentage12Months;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String noOfInvoicesPerMonth;

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

    @Override
    public String toString() {
        return "DebtorDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", city='" + city + '\'' +
            ", district='" + district + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", taxCode='" + taxCode + '\'' +
            ", country='" + country + '\'' +
            ", factoringRequestValue=" + factoringRequestValue +
            ", salesVolumeInLast6Months=" + salesVolumeInLast6Months +
            ", commercialRelationshipTrackRecord='" + commercialRelationshipTrackRecord + '\'' +
            ", salesVolumeInLast12Months='" + salesVolumeInLast12Months + '\'' +
            ", targetSalesVolumeInNext12Months='" + targetSalesVolumeInNext12Months + '\'' +
            ", paymentMethod=" + paymentMethod +
            ", commercialPaymentTerm='" + commercialPaymentTerm + '\'' +
            ", dso='" + dso + '\'' +
            ", setOfVolumePercentage12Months='" + setOfVolumePercentage12Months + '\'' +
            ", noOfInvoicesPerMonth='" + noOfInvoicesPerMonth + '\'' +
            '}';
    }
}

