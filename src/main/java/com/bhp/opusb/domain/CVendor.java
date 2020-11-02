package com.bhp.opusb.domain;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CVendor.
 */
@Entity
@Table(name = "c_vendor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CVendor extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "id_no")
    private String idNo;

    @Column(name = "tin")
    private String tin;

    @Column(name = "tax_id_no")
    private String taxIdNo;

    @Column(name = "tax_id_name")
    private String taxIdName;

    @Column(name = "branch")
    private Boolean branch;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @NotNull
    @Column(name = "payment_category", nullable = false)
    private String paymentCategory;

    @NotNull
    @Column(name = "approval_status", nullable = false)
    private String approvalStatus;

    @NotNull
    @Column(name = "date_trx", nullable = false)
    private LocalDate dateTrx;

    @NotNull
    @Column(name = "document_no", nullable = false)
    private String documentNo;

    @NotNull
    @Column(name = "document_action", nullable = false)
    private String documentAction;

    @NotNull
    @Column(name = "document_status", nullable = false)
    private String documentStatus;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(unique = true)
    private CAttachment taxIdFile;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendors")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cVendors")
    private CVendorGroup vendorGroup;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendors")
    private CDocumentType documentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CVendor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public CVendor type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public CVendor location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIdNo() {
        return idNo;
    }

    public CVendor idNo(String idNo) {
        this.idNo = idNo;
        return this;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTin() {
        return tin;
    }

    public CVendor tin(String tin) {
        this.tin = tin;
        return this;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getTaxIdNo() {
        return taxIdNo;
    }

    public CVendor taxIdNo(String taxIdNo) {
        this.taxIdNo = taxIdNo;
        return this;
    }

    public void setTaxIdNo(String taxIdNo) {
        this.taxIdNo = taxIdNo;
    }

    public String getTaxIdName() {
        return taxIdName;
    }

    public CVendor taxIdName(String taxIdName) {
        this.taxIdName = taxIdName;
        return this;
    }

    public void setTaxIdName(String taxIdName) {
        this.taxIdName = taxIdName;
    }

    public Boolean isBranch() {
        return branch;
    }

    public CVendor branch(Boolean branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Boolean branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public CVendor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public CVendor phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public CVendor fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public CVendor website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPaymentCategory() {
        return paymentCategory;
    }

    public CVendor paymentCategory(String paymentCategory) {
        this.paymentCategory = paymentCategory;
        return this;
    }

    public void setPaymentCategory(String paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public CVendor approvalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
        return this;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public CVendor dateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public CVendor documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public CVendor documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public CVendor documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public CVendor approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public CVendor processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public UUID getUid() {
        return uid;
    }

    public CVendor uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CVendor active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CAttachment getTaxIdFile() {
        return taxIdFile;
    }

    public CVendor taxIdFile(CAttachment cAttachment) {
        this.taxIdFile = cAttachment;
        return this;
    }

    public void setTaxIdFile(CAttachment cAttachment) {
        this.taxIdFile = cAttachment;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CVendor adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CVendorGroup getVendorGroup() {
        return vendorGroup;
    }

    public CVendor vendorGroup(CVendorGroup cVendorGroup) {
        this.vendorGroup = cVendorGroup;
        return this;
    }

    public void setVendorGroup(CVendorGroup cVendorGroup) {
        this.vendorGroup = cVendorGroup;
    }

    public CDocumentType getDocumentType() {
        return documentType;
    }

    public CVendor documentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
        return this;
    }

    public void setDocumentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CVendor)) {
            return false;
        }
        return id != null && id.equals(((CVendor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CVendor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", location='" + getLocation() + "'" +
            ", idNo='" + getIdNo() + "'" +
            ", tin='" + getTin() + "'" +
            ", taxIdNo='" + getTaxIdNo() + "'" +
            ", taxIdName='" + getTaxIdName() + "'" +
            ", branch='" + isBranch() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", website='" + getWebsite() + "'" +
            ", paymentCategory='" + getPaymentCategory() + "'" +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
