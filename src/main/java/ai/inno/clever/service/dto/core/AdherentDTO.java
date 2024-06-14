package ai.inno.clever.service.dto.core;

import ai.inno.clever.domain.enumeration.CompanyType;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

public class AdherentDTO implements Serializable {


    private Long id;

    private String ownerId;

    private String firstName;

    private String lastName;

    private String companyName;

    private String companyAddress;

    private String companyCity;

    private String companyRegion;

    private String companyCountry;

    private String contactPerson;

    private String country;

    private String region;

    private String city;

    private Instant dateOfBirth;

    private CompanyType companyEnterpriseCategory;

    private Instant companyEstablishmentDate;

    private String companyIndustry;

    private String companyPostCode;

    private String email;

    private String phone;

    private String position;

    private String taxCode;

    private String nrRegCom;

    private String companyRegistrationNo;

    private boolean submitted;

    private String linkCode;

    private String companyDetailsJSON;

    private Boolean useContactPerson;

    private String contactFirstName;

    private String contactLastName;

    private String contactEmail;

    private String contactPhone;

    private Set<DebtorDTO> debtors = new HashSet<>();


    private Map<String, Long> statuses = new HashMap<>();

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

    public String getCompanyRegion() {
        return companyRegion;
    }

    public void setCompanyRegion(String companyRegion) {
        this.companyRegion = companyRegion;
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

    public Boolean getUseContactPerson() {
        return useContactPerson;
    }

    public void setUseContactPerson(Boolean useContactPerson) {
        this.useContactPerson = useContactPerson;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
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
        if (!(o instanceof AdherentDTO)) {
            return false;
        }

        AdherentDTO adherentDTO = (AdherentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adherentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdherentDTO{" +
            "id=" + getId() +
            ", ownerId='" + getOwnerId() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", companyAddress='" + getCompanyAddress() + "'" +
            ", companyCity='" + getCompanyCity() + "'" +
            ", companyCountry='" + getCompanyCountry() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", companyRegistrationNo='" + getCompanyRegistrationNo() + "'" +
            ", linkCode='" + getLinkCode() + "'" +
            "}";
    }
}
