package com.bhp.opusb.domain;

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
 * A MVendorScoring.
 */
@Entity
@Table(name = "m_vendor_scoring")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorScoring extends AbstractAuditingEntity {

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
    @JsonIgnoreProperties("mVendorScorings")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScorings")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScorings")
    private CEvaluationMethod evaluationMethod;

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

    public MVendorScoring uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorScoring active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MVendorScoring bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorScoring adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CEvaluationMethod getEvaluationMethod() {
        return evaluationMethod;
    }

    public MVendorScoring evaluationMethod(CEvaluationMethod cEvaluationMethod) {
        this.evaluationMethod = cEvaluationMethod;
        return this;
    }

    public void setEvaluationMethod(CEvaluationMethod cEvaluationMethod) {
        this.evaluationMethod = cEvaluationMethod;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorScoring)) {
            return false;
        }
        return id != null && id.equals(((MVendorScoring) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorScoring{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
