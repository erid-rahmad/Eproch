package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A CConvertionRate.
 */
@Entity
@Table(name = "c_convertion_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CConvertionRate extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;

    @NotNull
    @Column(name = "rate", precision = 21, scale = 2, nullable = false)
    private BigDecimal rate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cConvertionRates")
    private CCurrency sourceCurrency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cConvertionRates")
    private CCurrency targetCurrency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cConvertionRates")
    private CConvertionType convertionType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cConvertionRates")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public CConvertionRate validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public CConvertionRate validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public CConvertionRate rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public UUID getUid() {
        return uid;
    }

    public CConvertionRate uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CConvertionRate active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CCurrency getSourceCurrency() {
        return sourceCurrency;
    }

    public CConvertionRate sourceCurrency(CCurrency cCurrency) {
        this.sourceCurrency = cCurrency;
        return this;
    }

    public void setSourceCurrency(CCurrency cCurrency) {
        this.sourceCurrency = cCurrency;
    }

    public CCurrency getTargetCurrency() {
        return targetCurrency;
    }

    public CConvertionRate targetCurrency(CCurrency cCurrency) {
        this.targetCurrency = cCurrency;
        return this;
    }

    public void setTargetCurrency(CCurrency cCurrency) {
        this.targetCurrency = cCurrency;
    }

    public CConvertionType getConvertionType() {
        return convertionType;
    }

    public CConvertionRate convertionType(CConvertionType cConvertionType) {
        this.convertionType = cConvertionType;
        return this;
    }

    public void setConvertionType(CConvertionType cConvertionType) {
        this.convertionType = cConvertionType;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CConvertionRate adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CConvertionRate)) {
            return false;
        }
        return id != null && id.equals(((CConvertionRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CConvertionRate{" +
            "id=" + getId() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", rate=" + getRate() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
