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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CEvaluationCriteria.
 */
@Entity
@Table(name = "c_evaluation_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CEvaluationCriteria extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @DecimalMax(value = "100")
    @Column(name = "scoring_percentage", precision = 21, scale = 2)
    private BigDecimal scoringPercentage;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationCriteria")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationCriteria")
    private CEvaluationMethodLine evaluationMethodLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationCriteria")
    private CBiddingCriteria biddingCriteria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationCriteria")
    private CBiddingSubCriteria biddingSubCriteria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationCriteria")
    private AdUser pic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getScoringPercentage() {
        return scoringPercentage;
    }

    public CEvaluationCriteria scoringPercentage(BigDecimal scoringPercentage) {
        this.scoringPercentage = scoringPercentage;
        return this;
    }

    public void setScoringPercentage(BigDecimal scoringPercentage) {
        this.scoringPercentage = scoringPercentage;
    }

    public UUID getUid() {
        return uid;
    }

    public CEvaluationCriteria uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CEvaluationCriteria active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CEvaluationCriteria adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CEvaluationMethodLine getEvaluationMethodLine() {
        return evaluationMethodLine;
    }

    public CEvaluationCriteria evaluationMethodLine(CEvaluationMethodLine cEvaluationMethodLine) {
        this.evaluationMethodLine = cEvaluationMethodLine;
        return this;
    }

    public void setEvaluationMethodLine(CEvaluationMethodLine cEvaluationMethodLine) {
        this.evaluationMethodLine = cEvaluationMethodLine;
    }

    public CBiddingCriteria getBiddingCriteria() {
        return biddingCriteria;
    }

    public CEvaluationCriteria biddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
        return this;
    }

    public void setBiddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
    }

    public CBiddingSubCriteria getBiddingSubCriteria() {
        return biddingSubCriteria;
    }

    public CEvaluationCriteria biddingSubCriteria(CBiddingSubCriteria cBiddingSubCriteria) {
        this.biddingSubCriteria = cBiddingSubCriteria;
        return this;
    }

    public void setBiddingSubCriteria(CBiddingSubCriteria cBiddingSubCriteria) {
        this.biddingSubCriteria = cBiddingSubCriteria;
    }

    public AdUser getPic() {
        return pic;
    }

    public CEvaluationCriteria pic(AdUser adUser) {
        this.pic = adUser;
        return this;
    }

    public void setPic(AdUser adUser) {
        this.pic = adUser;
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
        if (!(o instanceof CEvaluationCriteria)) {
            return false;
        }
        return id != null && id.equals(((CEvaluationCriteria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CEvaluationCriteria{" +
            "id=" + getId() +
            ", scoringPercentage=" + getScoringPercentage() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
