package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A CVendorBankAcct.
 */
@Entity
@Table(name = "c_vendor_bank_acct")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CVendorBankAcct extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "name")
    private String name;

    @Column(name = "branch")
    private String branch;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBankAccts")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBankAccts")
    private CBank bank;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBankAccts")
    private CCurrency currency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBankAccts")
    private CAttachment file;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBankAccts")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public CVendorBankAcct accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public CVendorBankAcct name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public CVendorBankAcct branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public UUID getUid() {
        return uid;
    }

    public CVendorBankAcct uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CVendorBankAcct active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public CVendorBankAcct vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CBank getBank() {
        return bank;
    }

    public CVendorBankAcct bank(CBank cBank) {
        this.bank = cBank;
        return this;
    }

    public void setBank(CBank cBank) {
        this.bank = cBank;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public CVendorBankAcct currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CAttachment getFile() {
        return file;
    }

    public CVendorBankAcct file(CAttachment cAttachment) {
        this.file = cAttachment;
        return this;
    }

    public void setFile(CAttachment cAttachment) {
        this.file = cAttachment;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CVendorBankAcct adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof CVendorBankAcct)) {
            return false;
        }
        return id != null && id.equals(((CVendorBankAcct) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CVendorBankAcct{" +
            "id=" + getId() +
            ", accountNo='" + getAccountNo() + "'" +
            ", name='" + getName() + "'" +
            ", branch='" + getBranch() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
