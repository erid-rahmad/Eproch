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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CEvaluationMethod.
 */
@Entity
@Table(name = "c_evaluation_method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CEvaluationMethod extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @DecimalMax(value = "100")
    @Column(name = "price_limit", precision = 21, scale = 2)
    private BigDecimal priceLimit;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationMethods")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cEvaluationMethods")
    private CBiddingType biddingType;

    @ManyToOne
    @JsonIgnoreProperties("cEvaluationMethods")
    private CEventType eventType;

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

    public CEvaluationMethod name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceLimit() {
        return priceLimit;
    }

    public CEvaluationMethod priceLimit(BigDecimal priceLimit) {
        this.priceLimit = priceLimit;
        return this;
    }

    public void setPriceLimit(BigDecimal priceLimit) {
        this.priceLimit = priceLimit;
    }

    public UUID getUid() {
        return uid;
    }

    public CEvaluationMethod uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CEvaluationMethod active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CEvaluationMethod adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CBiddingType getBiddingType() {
        return biddingType;
    }

    public CEvaluationMethod biddingType(CBiddingType cBiddingType) {
        this.biddingType = cBiddingType;
        return this;
    }

    public void setBiddingType(CBiddingType cBiddingType) {
        this.biddingType = cBiddingType;
    }

    public CEventType getEventType() {
        return eventType;
    }

    public CEvaluationMethod eventType(CEventType cEventType) {
        this.eventType = cEventType;
        return this;
    }

    public void setEventType(CEventType cEventType) {
        this.eventType = cEventType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CEvaluationMethod)) {
            return false;
        }
        return id != null && id.equals(((CEvaluationMethod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CEvaluationMethod{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priceLimit=" + getPriceLimit() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
