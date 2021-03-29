package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.DecimalMax;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorEvaluationLine} entity.
 */
public class CVendorEvaluationLineDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @DecimalMax(value = "100")
    private BigDecimal weight;

    @DecimalMax(value = "100")
    private BigDecimal userWeight;

    @DecimalMax(value = "100")
    private BigDecimal procurementWeight;

    private String question;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long cQuestionCategoryId;
    private String cQuestionCategoryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(BigDecimal userWeight) {
        this.userWeight = userWeight;
    }

    public BigDecimal getProcurementWeight() {
        return procurementWeight;
    }

    public void setProcurementWeight(BigDecimal procurementWeight) {
        this.procurementWeight = procurementWeight;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public Long getCQuestionCategoryId() {
        return cQuestionCategoryId;
    }

    public void setCQuestionCategoryId(Long cQuestionCategoryId) {
        this.cQuestionCategoryId = cQuestionCategoryId;
    }

    public String getCQuestionCategoryName() {
        return cQuestionCategoryName;
    }

    public void setCQuestionCategoryName(String cQuestionCategoryName) {
        this.cQuestionCategoryName = cQuestionCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CVendorEvaluationLineDTO cVendorEvaluationLineDTO = (CVendorEvaluationLineDTO) o;
        if (cVendorEvaluationLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cVendorEvaluationLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CVendorEvaluationLineDTO{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", userWeight=" + getUserWeight() +
            ", procurementWeight=" + getProcurementWeight() +
            ", question='" + getQuestion() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cQuestionCategoryId=" + getCQuestionCategoryId() +
            "}";
    }
}
