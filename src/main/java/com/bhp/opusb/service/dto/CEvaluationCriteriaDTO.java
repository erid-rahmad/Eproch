package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.DecimalMax;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvaluationCriteria} entity.
 */
public class CEvaluationCriteriaDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @DecimalMax(value = "100")
    private BigDecimal scoringPercentage;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long evaluationMethodLineId;
    private String evaluationMethodLineName;

    private Long biddingCriteriaId;
    private String biddingCriteriaName;

    private Long biddingSubCriteriaId;
    private String biddingSubCriteriaName;

    private Long picId;
    private String picLogin;
    private String picFirstName;
    private String picLastName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getScoringPercentage() {
        return scoringPercentage;
    }

    public void setScoringPercentage(BigDecimal scoringPercentage) {
        this.scoringPercentage = scoringPercentage;
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

    public Long getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(Long cBiddingCriteriaId) {
        this.biddingCriteriaId = cBiddingCriteriaId;
    }

    public String getBiddingCriteriaName() {
        return biddingCriteriaName;
    }

    public void setBiddingCriteriaName(String biddingCriteriaName) {
        this.biddingCriteriaName = biddingCriteriaName;
    }

    public Long getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(Long cBiddingSubCriteriaId) {
        this.biddingSubCriteriaId = cBiddingSubCriteriaId;
    }

    public String getBiddingSubCriteriaName() {
        return biddingSubCriteriaName;
    }

    public void setBiddingSubCriteriaName(String biddingSubCriteriaName) {
        this.biddingSubCriteriaName = biddingSubCriteriaName;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long adUserId) {
        this.picId = adUserId;
    }

    public String getPicLogin() {
        return picLogin;
    }

    public void setPicLogin(String picLogin) {
        this.picLogin = picLogin;
    }

    public String getPicFirstName() {
        return picFirstName;
    }

    public void setPicFirstName(String picFirstName) {
        this.picFirstName = picFirstName;
    }

    public String getPicLastName() {
        return picLastName;
    }

    public void setPicLastName(String picLastName) {
        this.picLastName = picLastName;
    }

    public String getPicName() {
        if (picFirstName == null) {
            return picLogin;
        }

        return picFirstName + (picLastName == null ? "" : " " + picLastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEvaluationCriteriaDTO cEvaluationCriteriaDTO = (CEvaluationCriteriaDTO) o;
        if (cEvaluationCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEvaluationCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEvaluationCriteriaDTO{" +
            "id=" + getId() +
            ", scoringPercentage=" + getScoringPercentage() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", evaluationMethodLineId=" + getEvaluationMethodLineId() +
            ", biddingCriteriaId=" + getBiddingCriteriaId() +
            ", biddingSubCriteriaId=" + getBiddingSubCriteriaId() +
            ", picId=" + getPicId() +
            "}";
    }
}
