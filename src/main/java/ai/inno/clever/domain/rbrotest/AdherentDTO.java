package ai.inno.clever.domain.rbrotest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdherentDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String aisp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ownerId;

    @SerializedName("First name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @SerializedName("Last name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyCity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyCountry;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyRegion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accumulatedFactoringValues;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactPerson;

    @SerializedName("Country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String country;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String region;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant dateOfBirth;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CompanyType companyEnterpriseCategory;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant companyEstablishmentDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyIndustry;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyPostCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyDetailsJSON;

    @SerializedName("Email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @SerializedName("Phone number")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String position;

    @SerializedName("Tax code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String taxCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nrRegCom;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyRegistrationNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean submitted;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String linkCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer turnoverLastYear;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deals;

    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Set<DebtorDTO> debtors = new HashSet<>();
    private Set<DebtorDTO> debtors;

    public String getAisp() {
        return aisp;
    }

    public void setAisp(String aisp) {
        this.aisp = aisp;
    }

    public String getCompanyRegion() {
        return companyRegion;
    }

    public void setCompanyRegion(String companyRegion) {
        this.companyRegion = companyRegion;
    }

    public String getAccumulatedFactoringValues() {
        return accumulatedFactoringValues;
    }

    public void setAccumulatedFactoringValues(String accumulatedFactoringValues) {
        this.accumulatedFactoringValues = accumulatedFactoringValues;
    }

    public Integer getTurnoverLastYear() {
        return turnoverLastYear;
    }

    public void setTurnoverLastYear(Integer turnoverLastYear) {
        this.turnoverLastYear = turnoverLastYear;
    }

    public String getDeals() {
        return deals;
    }

    public void setDeals(String deals) {
        this.deals = deals;
    }

    public String getCompanyDetailsJSON() {
        return companyDetailsJSON;
    }

    public void setCompanyDetailsJSON(String companyDetailsJSON) {
        this.companyDetailsJSON = companyDetailsJSON;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Map<String, Long> statuses = new HashMap<>();
    private Map<String, Long> statuses;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CompanyType getCompanyEnterpriseCategory() {
        return companyEnterpriseCategory;
    }

    public void setCompanyEnterpriseCategory(CompanyType companyEnterpriseCategory) {
        this.companyEnterpriseCategory = companyEnterpriseCategory;
    }

    public Instant getCompanyEstablishmentDate() {
        return companyEstablishmentDate;
    }

    public void setCompanyEstablishmentDate(Instant companyEstablishmentDate) {
        this.companyEstablishmentDate = companyEstablishmentDate;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    public String getCompanyPostCode() {
        return companyPostCode;
    }

    public void setCompanyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getNrRegCom() {
        return nrRegCom;
    }

    public void setNrRegCom(String nrRegCom) {
        this.nrRegCom = nrRegCom;
    }

    public String getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }

    public void setCompanyRegistrationNo(String companyRegistrationNo) {
        this.companyRegistrationNo = companyRegistrationNo;
    }

    public Boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }

    public String getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(String linkCode) {
        this.linkCode = linkCode;
    }

    public Set<DebtorDTO> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<DebtorDTO> debtors) {
        this.debtors = debtors;
    }

    public Map<String, Long> getStatuses() {
        return statuses;
    }

    public void setStatuses(Map<String, Long> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdherentDTO)) {
            return false;
        }

        AdherentDTO adherentDTO = (AdherentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adherentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "AdherentDTO{" +
            "aisp='" + aisp + '\'' +
            ", value=" + value +
            ", id=" + id +
            ", ownerId='" + ownerId + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", companyName='" + companyName + '\'' +
            ", companyAddress='" + companyAddress + '\'' +
            ", companyCity='" + companyCity + '\'' +
            ", companyCountry='" + companyCountry + '\'' +
            ", companyRegion='" + companyRegion + '\'' +
            ", accumulatedFactoringValues='" + accumulatedFactoringValues + '\'' +
            ", contactPerson='" + contactPerson + '\'' +
            ", country='" + country + '\'' +
            ", region='" + region + '\'' +
            ", city='" + city + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", companyEnterpriseCategory=" + companyEnterpriseCategory +
            ", companyEstablishmentDate=" + companyEstablishmentDate +
            ", companyIndustry='" + companyIndustry + '\'' +
            ", companyPostCode='" + companyPostCode + '\'' +
            ", companyDetailsJSON='" + companyDetailsJSON + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", position='" + position + '\'' +
            ", taxCode='" + taxCode + '\'' +
            ", nrRegCom='" + nrRegCom + '\'' +
            ", companyRegistrationNo='" + companyRegistrationNo + '\'' +
            ", submitted=" + submitted +
            ", linkCode='" + linkCode + '\'' +
            ", turnoverLastYear=" + turnoverLastYear +
            ", deals='" + deals + '\'' +
            ", debtors=" + debtors +
            ", statuses=" + statuses +
            '}';
    }
}

