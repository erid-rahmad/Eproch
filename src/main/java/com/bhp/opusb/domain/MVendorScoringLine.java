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
 * A MVendorScoringLine.
 */
@Entity
@Table(name = "m_vendor_scoring_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorScoringLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "evaluation")
    private String evaluation;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScoringLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScoringLines")
    private CEvaluationMethodLine evaluationMethodLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScoringLines")
    private MVendorScoring vendorScoring;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public MVendorScoringLine evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public UUID getUid() {
        return uid;
    }

    public MVendorScoringLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorScoringLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorScoringLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CEvaluationMethodLine getEvaluationMethodLine() {
        return evaluationMethodLine;
    }

    public MVendorScoringLine evaluationMethodLine(CEvaluationMethodLine cEvaluationMethodLine) {
        this.evaluationMethodLine = cEvaluationMethodLine;
        return this;
    }

    public void setEvaluationMethodLine(CEvaluationMethodLine cEvaluationMethodLine) {
        this.evaluationMethodLine = cEvaluationMethodLine;
    }

    public MVendorScoring getVendorScoring() {
        return vendorScoring;
    }

    public MVendorScoringLine vendorScoring(MVendorScoring mVendorScoring) {
        this.vendorScoring = mVendorScoring;
        return this;
    }

    public void setVendorScoring(MVendorScoring mVendorScoring) {
        this.vendorScoring = mVendorScoring;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorScoringLine)) {
            return false;
        }
        return id != null && id.equals(((MVendorScoringLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorScoringLine{" +
            "id=" + getId() +
            ", evaluation='" + getEvaluation() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
