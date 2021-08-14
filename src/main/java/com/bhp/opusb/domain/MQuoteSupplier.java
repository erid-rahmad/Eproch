package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A MQuoteSupplier.
 */
@Entity
@Table(name = "m_quote_supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MQuoteSupplier extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mQuoteSuppliers")
    private MRfq quotation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mQuoteSuppliers")
    private CBusinessCategory businessClassification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mQuoteSuppliers")
    private CBusinessCategory businessCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mQuoteSuppliers")
    private CBusinessCategory businessSubCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mQuoteSuppliers")
    private CVendor vendor;

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

    public MQuoteSupplier uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MQuoteSupplier active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MQuoteSupplier dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public MRfq getQuotation() {
        return quotation;
    }

    public MQuoteSupplier quotation(MRfq mRfq) {
        this.quotation = mRfq;
        return this;
    }

    public void setQuotation(MRfq mRfq) {
        this.quotation = mRfq;
    }

    public CBusinessCategory getBusinessClassification() {
        return businessClassification;
    }

    public MQuoteSupplier businessClassification(CBusinessCategory cBusinessCategory) {
        this.businessClassification = cBusinessCategory;
        return this;
    }

    public void setBusinessClassification(CBusinessCategory cBusinessCategory) {
        this.businessClassification = cBusinessCategory;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public MQuoteSupplier businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }

    public CBusinessCategory getBusinessSubCategory() {
        return businessSubCategory;
    }

    public MQuoteSupplier businessSubCategory(CBusinessCategory cBusinessCategory) {
        this.businessSubCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessSubCategory(CBusinessCategory cBusinessCategory) {
        this.businessSubCategory = cBusinessCategory;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MQuoteSupplier vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MQuoteSupplier)) {
            return false;
        }
        return id != null && id.equals(((MQuoteSupplier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MQuoteSupplier{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist(){
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}
