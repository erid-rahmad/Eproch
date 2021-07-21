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
 * A MPreqRegistEvaluation.
 */
@Entity
@Table(name = "m_preq_regist_evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPreqRegistEvaluation extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "evaluation")
    private String evaluation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreqRegistEvaluations")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreqRegistEvaluations")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreqRegistEvaluations")
    private MPrequalificationInformation prequalification;

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

    public MPreqRegistEvaluation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPreqRegistEvaluation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public MPreqRegistEvaluation evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPreqRegistEvaluation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MPreqRegistEvaluation vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public MPrequalificationInformation getPrequalification() {
        return prequalification;
    }

    public MPreqRegistEvaluation prequalification(MPrequalificationInformation mPrequalificationInformation) {
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
        if (!(o instanceof MPreqRegistEvaluation)) {
            return false;
        }
        return id != null && id.equals(((MPreqRegistEvaluation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPreqRegistEvaluation{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist(){
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}
