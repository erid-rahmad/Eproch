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
 * A MBiddingEvalTeamLine.
 */
@Entity
@Table(name = "m_bidding_eval_team_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingEvalTeamLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Size(max = 10)
    @Column(name = "position", length = 10, nullable = false)
    private String position;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvalTeamLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvalTeamLines")
    private MBiddingEvaluationTeam evaluationTeam;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvalTeamLines")
    private AdUser adUser;

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

    public MBiddingEvalTeamLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingEvalTeamLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPosition() {
        return position;
    }

    public MBiddingEvalTeamLine position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingEvalTeamLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingEvaluationTeam getEvaluationTeam() {
        return evaluationTeam;
    }

    public MBiddingEvalTeamLine evaluationTeam(MBiddingEvaluationTeam mBiddingEvaluationTeam) {
        this.evaluationTeam = mBiddingEvaluationTeam;
        return this;
    }

    public void setEvaluationTeam(MBiddingEvaluationTeam mBiddingEvaluationTeam) {
        this.evaluationTeam = mBiddingEvaluationTeam;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public MBiddingEvalTeamLine adUser(AdUser adUser) {
        this.adUser = adUser;
        return this;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingEvalTeamLine)) {
            return false;
        }
        return id != null && id.equals(((MBiddingEvalTeamLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingEvalTeamLine{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", position='" + getPosition() + "'" +
            "}";
    }

    @PrePersist
    public void assignUuid() {
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}
