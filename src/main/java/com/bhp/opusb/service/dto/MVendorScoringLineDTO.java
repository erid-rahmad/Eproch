package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorScoringLine} entity.
 */
public class MVendorScoringLineDTO implements Serializable {

    private Long id;

    private String evaluation;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long evaluationMethodLineId;
    private String evaluationMethodLineName;
    private String evaluationMethodLineFormType;
    private String evaluationMethodLineEvaluationType;
    private String evaluationMethodLineWeight;
    private String evaluationMethodLinePassingGrade;
    private String evaluationMethodLineEvaluation;

    public String getEvaluationMethodLineEvaluation() {
        return evaluationMethodLineEvaluation;
    }

    public void setEvaluationMethodLineEvaluation(String evaluationMethodLineEvaluation) {
        this.evaluationMethodLineEvaluation = evaluationMethodLineEvaluation;
    }

    private Long vendorScoringId;

    public String getEvaluationMethodLineEvaluationType() {
        return evaluationMethodLineEvaluationType;
    }

    public void setEvaluationMethodLineEvaluationType(String evaluationMethodLineEvaluationType) {
        this.evaluationMethodLineEvaluationType = evaluationMethodLineEvaluationType;
    }

    public String getEvaluationMethodLineWeight() {
        return evaluationMethodLineWeight;
    }

    public void setEvaluationMethodLineWeight(String evaluationMethodLineWeight) {
        this.evaluationMethodLineWeight = evaluationMethodLineWeight;
    }

    public String getEvaluationMethodLinePassingGrade() {
        return evaluationMethodLinePassingGrade;
    }

    public void setEvaluationMethodLinePassingGrade(String evaluationMethodLinePassingGrade) {
        this.evaluationMethodLinePassingGrade = evaluationMethodLinePassingGrade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getEvaluationMethodLineId() {
        return evaluationMethodLineId;
    }

    public void setEvaluationMethodLineId(Long cEvaluationMethodLineId) {
        this.evaluationMethodLineId = cEvaluationMethodLineId;
    }

    public String getEvaluationMethodLineName() {
        return evaluationMethodLineName;
    }

    public void setEvaluationMethodLineName(String evaluationMethodLineName) {
        this.evaluationMethodLineName = evaluationMethodLineName;
    }

    public String getEvaluationMethodLineFormType() {
        return evaluationMethodLineFormType;
    }

    public void setEvaluationMethodLineFormType(String evaluationMethodLineFormType) {
        this.evaluationMethodLineFormType = evaluationMethodLineFormType;
    }

    public Long getVendorScoringId() {
        return vendorScoringId;
    }

    public void setVendorScoringId(Long mVendorScoringId) {
        this.vendorScoringId = mVendorScoringId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorScoringLineDTO mVendorScoringLineDTO = (MVendorScoringLineDTO) o;
        if (mVendorScoringLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorScoringLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorScoringLineDTO{" +
            "id=" + id +
            ", evaluation='" + evaluation + '\'' +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", evaluationMethodLineId=" + evaluationMethodLineId +
            ", evaluationMethodLineEvaluationType='" + evaluationMethodLineEvaluationType + '\'' +
            ", evaluationMethodLineWeight='" + evaluationMethodLineWeight + '\'' +
            ", evaluationMethodLinePassingGrade='" + evaluationMethodLinePassingGrade + '\'' +
            ", evaluationMethodLineEvaluation='" + evaluationMethodLineEvaluation + '\'' +
            ", vendorScoringId=" + vendorScoringId +
            '}';
    }
}
