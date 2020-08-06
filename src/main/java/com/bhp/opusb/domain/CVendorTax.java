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
 * A CVendorTax.
 */
@Entity
@Table(name = "c_vendor_tax")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CVendorTax extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_faktur")
    private Boolean isFaktur;

    @Column(name = "is_pkp")
    private Boolean isPkp;

    @Column(name = "rate")
    private String rate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorTaxes")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorTaxes")
    private CTaxCategory taxCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorTaxes")
    private CTaxRate taxRate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorTaxes")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsFaktur() {
        return isFaktur;
    }

    public CVendorTax isFaktur(Boolean isFaktur) {
        this.isFaktur = isFaktur;
        return this;
    }

    public void setIsFaktur(Boolean isFaktur) {
        this.isFaktur = isFaktur;
    }

    public Boolean isIsPkp() {
        return isPkp;
    }

    public CVendorTax isPkp(Boolean isPkp) {
        this.isPkp = isPkp;
        return this;
    }

    public void setIsPkp(Boolean isPkp) {
        this.isPkp = isPkp;
    }

    public String getRate() {
        return rate;
    }

    public CVendorTax rate(String rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public UUID getUid() {
        return uid;
    }

    public CVendorTax uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CVendorTax active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public CVendorTax vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CTaxCategory getTaxCategory() {
        return taxCategory;
    }

    public CVendorTax taxCategory(CTaxCategory cTaxCategory) {
        this.taxCategory = cTaxCategory;
        return this;
    }

    public void setTaxCategory(CTaxCategory cTaxCategory) {
        this.taxCategory = cTaxCategory;
    }

    public CTaxRate getTaxRate() {
        return taxRate;
    }

    public CVendorTax taxRate(CTaxRate cTaxRate) {
        this.taxRate = cTaxRate;
        return this;
    }

    public void setTaxRate(CTaxRate cTaxRate) {
        this.taxRate = cTaxRate;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CVendorTax adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CVendorTax)) {
            return false;
        }
        return id != null && id.equals(((CVendorTax) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CVendorTax{" +
            "id=" + getId() +
            ", isFaktur='" + isIsFaktur() + "'" +
            ", isPkp='" + isIsPkp() + "'" +
            ", rate='" + getRate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
