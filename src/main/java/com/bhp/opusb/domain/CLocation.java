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
 * A CLocation.
 */
@Entity
@Table(name = "c_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CLocation extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "tax_invoice_address")
    private Boolean taxInvoiceAddress;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cLocations")
    private CCity city;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cLocations")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public CLocation streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CLocation postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean isTaxInvoiceAddress() {
        return taxInvoiceAddress;
    }

    public CLocation taxInvoiceAddress(Boolean taxInvoiceAddress) {
        this.taxInvoiceAddress = taxInvoiceAddress;
        return this;
    }

    public void setTaxInvoiceAddress(Boolean taxInvoiceAddress) {
        this.taxInvoiceAddress = taxInvoiceAddress;
    }

    public UUID getUid() {
        return uid;
    }

    public CLocation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CLocation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CCity getCity() {
        return city;
    }

    public CLocation city(CCity cCity) {
        this.city = cCity;
        return this;
    }

    public void setCity(CCity cCity) {
        this.city = cCity;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CLocation adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CLocation)) {
            return false;
        }
        return id != null && id.equals(((CLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CLocation{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", taxInvoiceAddress='" + isTaxInvoiceAddress() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
