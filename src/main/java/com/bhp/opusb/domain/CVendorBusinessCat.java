package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.UUID;

/**
 * A CVendorBusinessCat.
 */
@Entity
@Table(name = "c_vendor_business_cat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CVendorBusinessCat extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBusinessCats")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBusinessCats")
    private CBusinessCategory businessClassification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBusinessCats")
    private CBusinessCategory businessCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBusinessCats")
    private CBusinessCategory subBusinessCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorBusinessCats")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public CVendorBusinessCat uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CVendorBusinessCat active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public CVendorBusinessCat vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CBusinessCategory getBusinessClassification() {
        return businessClassification;
    }

    public CVendorBusinessCat businessClassification(CBusinessCategory cBusinessCategory) {
        this.businessClassification = cBusinessCategory;
        return this;
    }

    public void setBusinessClassification(CBusinessCategory cBusinessCategory) {
        this.businessClassification = cBusinessCategory;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public CVendorBusinessCat businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }

    public CBusinessCategory getSubBusinessCategory() {
        return subBusinessCategory;
    }

    public CVendorBusinessCat subBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.subBusinessCategory = cBusinessCategory;
        return this;
    }

    public void setSubBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.subBusinessCategory = cBusinessCategory;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CVendorBusinessCat adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CVendorBusinessCat)) {
            return false;
        }
        return id != null && id.equals(((CVendorBusinessCat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CVendorBusinessCat{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
