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
 * A MVendorScoringCriteria.
 */
@Entity
@Table(name = "m_vendor_scoring_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorScoringCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "requirement", nullable = false)
    private String requirement;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScoringCriteria")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("mVendorScoringCriteria")
    private CEvalMethodCriteriaLine evalMethodCriteriaLine;

    @ManyToOne
    @JsonIgnoreProperties("mVendorScoringCriteria")
    private CEvalMethodSubCriteria evalMethodSubCriteria;

    @ManyToOne
    @JsonIgnoreProperties("mVendorScoringCriteria")
    private MVendorScoringLine vendorScoringLine;

    @ManyToOne
    @JsonIgnoreProperties("mVendorScoringCriteria")
    private CBiddingSubCriteriaLine biddingSubCriteriaLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequirement() {
        return requirement;
    }

    public MVendorScoringCriteria requirement(String requirement) {
        this.requirement = requirement;
        return this;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public UUID getUid() {
        return uid;
    }

    public MVendorScoringCriteria uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorScoringCriteria active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorScoringCriteria adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CEvalMethodCriteriaLine getEvalMethodCriteriaLine() {
        return evalMethodCriteriaLine;
    }

    public MVendorScoringCriteria evalMethodCriteriaLine(CEvalMethodCriteriaLine cEvalMethodCriteriaLine) {
        this.evalMethodCriteriaLine = cEvalMethodCriteriaLine;
        return this;
    }

    public void setEvalMethodCriteriaLine(CEvalMethodCriteriaLine cEvalMethodCriteriaLine) {
        this.evalMethodCriteriaLine = cEvalMethodCriteriaLine;
    }

    public CEvalMethodSubCriteria getEvalMethodSubCriteria() {
        return evalMethodSubCriteria;
    }

    public MVendorScoringCriteria evalMethodSubCriteria(CEvalMethodSubCriteria cEvalMethodSubCriteria) {
        this.evalMethodSubCriteria = cEvalMethodSubCriteria;
        return this;
    }

    public void setEvalMethodSubCriteria(CEvalMethodSubCriteria cEvalMethodSubCriteria) {
        this.evalMethodSubCriteria = cEvalMethodSubCriteria;
    }

    public MVendorScoringLine getVendorScoringLine() {
        return vendorScoringLine;
    }

    public MVendorScoringCriteria vendorScoringLine(MVendorScoringLine mVendorScoringLine) {
        this.vendorScoringLine = mVendorScoringLine;
        return this;
    }

    public void setVendorScoringLine(MVendorScoringLine mVendorScoringLine) {
        this.vendorScoringLine = mVendorScoringLine;
    }

    public CBiddingSubCriteriaLine getBiddingSubCriteriaLine() {
        return biddingSubCriteriaLine;
    }

    public MVendorScoringCriteria biddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
        this.biddingSubCriteriaLine = cBiddingSubCriteriaLine;
        return this;
    }

    public void setBiddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
        this.biddingSubCriteriaLine = cBiddingSubCriteriaLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorScoringCriteria)) {
            return false;
        }
        return id != null && id.equals(((MVendorScoringCriteria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorScoringCriteria{" +
            "id=" + getId() +
            ", requirement='" + getRequirement() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
