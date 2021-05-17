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
 * A CEvaluationMethodCriteria.
 */
@Entity
@Table(name = "c_evaluation_method_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CEvaluationMethodCriteria extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationMethodCriteria")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cEvaluationMethodCriteria")
    private CEvaluationMethodLine evaluationMethodLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationMethodCriteria")
    private CBiddingCriteria biddingCriteria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public CEvaluationMethodCriteria weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public UUID getUid() {
        return uid;
    }

    public CEvaluationMethodCriteria uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CEvaluationMethodCriteria active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CEvaluationMethodCriteria adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CEvaluationMethodLine getEvaluationMethodLine() {
        return evaluationMethodLine;
    }

    public CEvaluationMethodCriteria evaluationMethodLine(CEvaluationMethodLine cEvaluationMethodLine) {
        this.evaluationMethodLine = cEvaluationMethodLine;
        return this;
    }

    public void setEvaluationMethodLine(CEvaluationMethodLine cEvaluationMethodLine) {
        this.evaluationMethodLine = cEvaluationMethodLine;
    }

    public CBiddingCriteria getBiddingCriteria() {
        return biddingCriteria;
    }

    public CEvaluationMethodCriteria biddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
        return this;
    }

    public void setBiddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
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
        if (!(o instanceof CEvaluationMethodCriteria)) {
            return false;
        }
        return id != null && id.equals(((CEvaluationMethodCriteria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CEvaluationMethodCriteria{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
