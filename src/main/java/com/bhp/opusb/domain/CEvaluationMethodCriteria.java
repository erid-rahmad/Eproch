package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A CEvaluationMethodCriteria.
 */
@Entity
@Table(name = "c_evaluation_method_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CEvaluationMethodCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("cEvaluationMethodCriteria")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cEvaluationMethodCriteria")
    private CEvaluationMethodLine evaluationMethodLine;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
