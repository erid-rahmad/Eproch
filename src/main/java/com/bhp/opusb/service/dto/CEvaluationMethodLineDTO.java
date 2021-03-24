package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvaluationMethodLine} entity.
 */
public class CEvaluationMethodLineDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * Referenced from M_Reference
     */
    @NotNull
    @Size(max = 5)
    @ApiModelProperty(value = "Referenced from M_Reference", required = true)
    private String evaluation;

    @DecimalMax(value = "100")
    private BigDecimal weight;

    private BigDecimal passingGrade;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long evaluationMethodId;
    private String evaluationMethodName;
    
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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPassingGrade() {
        return passingGrade;
    }

    public void setPassingGrade(BigDecimal passingGrade) {
        this.passingGrade = passingGrade;
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

    public Long getEvaluationMethodId() {
        return evaluationMethodId;
    }

    public void setEvaluationMethodId(Long cEvaluationMethodId) {
        this.evaluationMethodId = cEvaluationMethodId;
    }

    public String getEvaluationMethodName() {
        return evaluationMethodName;
    }

    public void setEvaluationMethodName(String evaluationMethodName) {
        this.evaluationMethodName = evaluationMethodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEvaluationMethodLineDTO cEvaluationMethodLineDTO = (CEvaluationMethodLineDTO) o;
        if (cEvaluationMethodLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEvaluationMethodLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEvaluationMethodLineDTO{" +
            "id=" + getId() +
            ", evaluation='" + getEvaluation() + "'" +
            ", weight=" + getWeight() +
            ", passingGrade=" + getPassingGrade() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", evaluationMethodId=" + getEvaluationMethodId() +
            "}";
    }
}
