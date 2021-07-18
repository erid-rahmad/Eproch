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
 * A MBiddingEvaluationTeam.
 */
@Entity
@Table(name = "m_bidding_evaluation_team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingEvaluationTeam extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvaluationTeams")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("mBiddingEvaluationTeams")
    private MBidding bidding;

    @ManyToOne
    @JsonIgnoreProperties("mBiddingEvaluationTeams")
    private MPrequalificationInformation prequalification;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public MBiddingEvaluationTeam status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingEvaluationTeam uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingEvaluationTeam active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingEvaluationTeam adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBiddingEvaluationTeam bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public MPrequalificationInformation getPrequalification() {
        return prequalification;
    }

    public MBiddingEvaluationTeam prequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
        return this;
    }

    public void setPrequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingEvaluationTeam)) {
            return false;
        }
        return id != null && id.equals(((MBiddingEvaluationTeam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingEvaluationTeam{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    public void assignUuid() {
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}
