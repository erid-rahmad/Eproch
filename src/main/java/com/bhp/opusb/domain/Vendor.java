package com.bhp.opusb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.bhp.opusb.domain.enumeration.VendorType;

import com.bhp.opusb.domain.enumeration.PaymentCategory;

import com.bhp.opusb.domain.enumeration.VendorApprovalStatus;

/**
 * The Vendor entity.
 */
@Entity
@Table(name = "vendor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "npwp", nullable = false)
    private Long npwp;

    @Column(name = "branch")
    private Boolean branch;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VendorType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_category", nullable = false)
    private PaymentCategory paymentCategory;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private VendorApprovalStatus approvalStatus;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "vendor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyFunctionary> companyFunctionaries = new HashSet<>();

    @OneToMany(mappedBy = "vendor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonInCharge> personInCharges = new HashSet<>();

    @OneToMany(mappedBy = "vendor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SupportingDocument> supportingDocuments = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "vendor_business_category",
               joinColumns = @JoinColumn(name = "vendor_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "business_category_id", referencedColumnName = "id"))
    private Set<BusinessCategory> businessCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Vendor code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Vendor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNpwp() {
        return npwp;
    }

    public Vendor npwp(Long npwp) {
        this.npwp = npwp;
        return this;
    }

    public void setNpwp(Long npwp) {
        this.npwp = npwp;
    }

    public Boolean isBranch() {
        return branch;
    }

    public Vendor branch(Boolean branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Boolean branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public Vendor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Vendor phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public Vendor fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public Vendor website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public VendorType getType() {
        return type;
    }

    public Vendor type(VendorType type) {
        this.type = type;
        return this;
    }

    public void setType(VendorType type) {
        this.type = type;
    }

    public PaymentCategory getPaymentCategory() {
        return paymentCategory;
    }

    public Vendor paymentCategory(PaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
        return this;
    }

    public void setPaymentCategory(PaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public VendorApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public Vendor approvalStatus(VendorApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
        return this;
    }

    public void setApprovalStatus(VendorApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Location getLocation() {
        return location;
    }

    public Vendor location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<CompanyFunctionary> getCompanyFunctionaries() {
        return companyFunctionaries;
    }

    public Vendor companyFunctionaries(Set<CompanyFunctionary> companyFunctionaries) {
        this.companyFunctionaries = companyFunctionaries;
        return this;
    }

    public Vendor addCompanyFunctionary(CompanyFunctionary companyFunctionary) {
        this.companyFunctionaries.add(companyFunctionary);
        companyFunctionary.setVendor(this);
        return this;
    }

    public Vendor removeCompanyFunctionary(CompanyFunctionary companyFunctionary) {
        this.companyFunctionaries.remove(companyFunctionary);
        companyFunctionary.setVendor(null);
        return this;
    }

    public void setCompanyFunctionaries(Set<CompanyFunctionary> companyFunctionaries) {
        this.companyFunctionaries = companyFunctionaries;
    }

    public Set<PersonInCharge> getPersonInCharges() {
        return personInCharges;
    }

    public Vendor personInCharges(Set<PersonInCharge> personInCharges) {
        this.personInCharges = personInCharges;
        return this;
    }

    public Vendor addPersonInCharge(PersonInCharge personInCharge) {
        this.personInCharges.add(personInCharge);
        personInCharge.setVendor(this);
        return this;
    }

    public Vendor removePersonInCharge(PersonInCharge personInCharge) {
        this.personInCharges.remove(personInCharge);
        personInCharge.setVendor(null);
        return this;
    }

    public void setPersonInCharges(Set<PersonInCharge> personInCharges) {
        this.personInCharges = personInCharges;
    }

    public Set<SupportingDocument> getSupportingDocuments() {
        return supportingDocuments;
    }

    public Vendor supportingDocuments(Set<SupportingDocument> supportingDocuments) {
        this.supportingDocuments = supportingDocuments;
        return this;
    }

    public Vendor addSupportingDocument(SupportingDocument supportingDocument) {
        this.supportingDocuments.add(supportingDocument);
        supportingDocument.setVendor(this);
        return this;
    }

    public Vendor removeSupportingDocument(SupportingDocument supportingDocument) {
        this.supportingDocuments.remove(supportingDocument);
        supportingDocument.setVendor(null);
        return this;
    }

    public void setSupportingDocuments(Set<SupportingDocument> supportingDocuments) {
        this.supportingDocuments = supportingDocuments;
    }

    public Set<BusinessCategory> getBusinessCategories() {
        return businessCategories;
    }

    public Vendor businessCategories(Set<BusinessCategory> businessCategories) {
        this.businessCategories = businessCategories;
        return this;
    }

    public Vendor addBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategories.add(businessCategory);
        businessCategory.getVendors().add(this);
        return this;
    }

    public Vendor removeBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategories.remove(businessCategory);
        businessCategory.getVendors().remove(this);
        return this;
    }

    public void setBusinessCategories(Set<BusinessCategory> businessCategories) {
        this.businessCategories = businessCategories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vendor)) {
            return false;
        }
        return id != null && id.equals(((Vendor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vendor{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", npwp=" + getNpwp() +
            ", branch='" + isBranch() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", website='" + getWebsite() + "'" +
            ", type='" + getType() + "'" +
            ", paymentCategory='" + getPaymentCategory() + "'" +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            "}";
    }
}
