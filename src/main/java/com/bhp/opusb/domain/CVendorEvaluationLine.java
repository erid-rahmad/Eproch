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
 * A CVendorEvaluationLine.
 */
@Entity
@Table(name = "c_vendor_evaluation_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CVendorEvaluationLine extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @DecimalMax(value = "100")
    @Column(name = "weight", precision = 21, scale = 2)
    private BigDecimal weight;

    @DecimalMax(value = "100")
    @Column(name = "user_weight", precision = 21, scale = 2)
    private BigDecimal userWeight;

    @DecimalMax(value = "100")
    @Column(name = "procurement_weight", precision = 21, scale = 2)
    private BigDecimal procurementWeight;

    @Column(name = "question")
    private String question;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorEvaluationLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cVendorEvaluationLines")
    private CQuestionCategory cQuestionCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public CVendorEvaluationLine weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getUserWeight() {
        return userWeight;
    }

    public CVendorEvaluationLine userWeight(BigDecimal userWeight) {
        this.userWeight = userWeight;
        return this;
    }

    public void setUserWeight(BigDecimal userWeight) {
        this.userWeight = userWeight;
    }

    public BigDecimal getProcurementWeight() {
        return procurementWeight;
    }

    public CVendorEvaluationLine procurementWeight(BigDecimal procurementWeight) {
        this.procurementWeight = procurementWeight;
        return this;
    }

    public void setProcurementWeight(BigDecimal procurementWeight) {
        this.procurementWeight = procurementWeight;
    }

    public String getQuestion() {
        return question;
    }

    public CVendorEvaluationLine question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public UUID getUid() {
        return uid;
    }

    public CVendorEvaluationLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CVendorEvaluationLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CVendorEvaluationLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CQuestionCategory getCQuestionCategory() {
        return cQuestionCategory;
    }

    public CVendorEvaluationLine cQuestionCategory(CQuestionCategory cQuestionCategory) {
        this.cQuestionCategory = cQuestionCategory;
        return this;
    }

    public void setCQuestionCategory(CQuestionCategory cQuestionCategory) {
        this.cQuestionCategory = cQuestionCategory;
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
        if (!(o instanceof CVendorEvaluationLine)) {
            return false;
        }
        return id != null && id.equals(((CVendorEvaluationLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CVendorEvaluationLine{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", userWeight=" + getUserWeight() +
            ", procurementWeight=" + getProcurementWeight() +
            ", question='" + getQuestion() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
