package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.UUID;

/**
 * A MPrequalificationCriteria.
 */
@Entity
@Table(name = "m_prequalification_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrequalificationCriteria extends AbstractAuditingEntity {

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
    @JsonIgnoreProperties("mPrequalificationCriteria")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationCriteria")
    private MPrequalificationInformation prequalification;

    @ManyToOne
    @JsonIgnoreProperties("mPrequalificationCriteria")
    private CPrequalMethodCriteria prequalMethodCriteria;

    @ManyToOne
    @JsonIgnoreProperties("mPrequalificationCriteria")
    private CPrequalMethodSubCriteria prequalMethodSubCriteria;

    @ManyToOne
    @JsonIgnoreProperties("mPrequalificationCriteria")
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

    public MPrequalificationCriteria requirement(String requirement) {
        this.requirement = requirement;
        return this;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public UUID getUid() {
        return uid;
    }

    public MPrequalificationCriteria uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPrequalificationCriteria active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPrequalificationCriteria adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MPrequalificationInformation getPrequalification() {
        return prequalification;
    }

    public MPrequalificationCriteria prequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
        return this;
    }

    public void setPrequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
    }

    public CPrequalMethodCriteria getPrequalMethodCriteria() {
        return prequalMethodCriteria;
    }

    public MPrequalificationCriteria prequalMethodCriteria(CPrequalMethodCriteria cPrequalMethodCriteria) {
        this.prequalMethodCriteria = cPrequalMethodCriteria;
        return this;
    }

    public void setPrequalMethodCriteria(CPrequalMethodCriteria cPrequalMethodCriteria) {
        this.prequalMethodCriteria = cPrequalMethodCriteria;
    }

    public CPrequalMethodSubCriteria getPrequalMethodSubCriteria() {
        return prequalMethodSubCriteria;
    }

    public MPrequalificationCriteria prequalMethodSubCriteria(CPrequalMethodSubCriteria cPrequalMethodSubCriteria) {
        this.prequalMethodSubCriteria = cPrequalMethodSubCriteria;
        return this;
    }

    public void setPrequalMethodSubCriteria(CPrequalMethodSubCriteria cPrequalMethodSubCriteria) {
        this.prequalMethodSubCriteria = cPrequalMethodSubCriteria;
    }

    public CBiddingSubCriteriaLine getBiddingSubCriteriaLine() {
        return biddingSubCriteriaLine;
    }

    public MPrequalificationCriteria biddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
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
        if (!(o instanceof MPrequalificationCriteria)) {
            return false;
        }
        return id != null && id.equals(((MPrequalificationCriteria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrequalificationCriteria{" +
            "id=" + getId() +
            ", requirement='" + getRequirement() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    private void prePersist(){
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}
