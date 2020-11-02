package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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

    @Column(name = "e_invoice")
    private Boolean eInvoice;

    @Column(name = "taxable_employers")
    private Boolean taxableEmployers;

    @Column(name = "rate", precision = 21, scale = 2)
    private BigDecimal rate;

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
    private CTax tax;

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

    public Boolean isEInvoice() {
        return eInvoice;
    }

    public CVendorTax eInvoice(Boolean eInvoice) {
        this.eInvoice = eInvoice;
        return this;
    }

    public void setEInvoice(Boolean eInvoice) {
        this.eInvoice = eInvoice;
    }

    public Boolean isTaxableEmployers() {
        return taxableEmployers;
    }

    public CVendorTax taxableEmployers(Boolean taxableEmployers) {
        this.taxableEmployers = taxableEmployers;
        return this;
    }

    public void setTaxableEmployers(Boolean taxableEmployers) {
        this.taxableEmployers = taxableEmployers;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public CVendorTax rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
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

    public CTax getTax() {
        return tax;
    }

    public CVendorTax tax(CTax cTax) {
        this.tax = cTax;
        return this;
    }

    public void setTax(CTax cTax) {
        this.tax = cTax;
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
            ", eInvoice='" + isEInvoice() + "'" +
            ", taxableEmployers='" + isTaxableEmployers() + "'" +
            ", rate=" + getRate() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
