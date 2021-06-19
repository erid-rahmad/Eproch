package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorEvaluationLine} entity.
 */
public class MVendorEvaluationLineDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private BigDecimal score = new BigDecimal(0);

    private String remark;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long vendorEvaluationId;
    private String vendorEvaluationName;

    private Long evaluationLineId;
    private String evaluationLineName;
    private String evaluationLineQuestion;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getVendorEvaluationId() {
        return vendorEvaluationId;
    }

    public void setVendorEvaluationId(Long mVendorEvaluationId) {
        this.vendorEvaluationId = mVendorEvaluationId;
    }

    public String getVendorEvaluationName() {
        return vendorEvaluationName;
    }

    public void setVendorEvaluationName(String vendorEvaluationName) {
        this.vendorEvaluationName = vendorEvaluationName;
    }

    public Long getEvaluationLineId() {
        return evaluationLineId;
    }

    public void setEvaluationLineId(Long cVendorEvaluationLineId) {
        this.evaluationLineId = cVendorEvaluationLineId;
    }

    public String getEvaluationLineName() {
        return evaluationLineName;
    }

    public void setEvaluationLineName(String evaluationLineName) {
        this.evaluationLineName = evaluationLineName;
    }

    public String getEvaluationLineQuestion() {
        return evaluationLineQuestion;
    }

    public void setEvaluationLineQuestion(String evaluationLineQuestion) {
        this.evaluationLineQuestion = evaluationLineQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorEvaluationLineDTO mVendorEvaluationLineDTO = (MVendorEvaluationLineDTO) o;
        if (mVendorEvaluationLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorEvaluationLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorEvaluationLineDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", vendorEvaluationId=" + getVendorEvaluationId() +
            ", evaluationLineId=" + getEvaluationLineId() +
            "}";
    }
}
