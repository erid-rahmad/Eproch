package com.bhp.opusb.service.dto;

import com.bhp.opusb.domain.CEvalMethodSubCriteria;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvaluationMethodCriteria} entity.
 */
public class CEvaluationMethodCriteriaDTO implements Serializable {

    private Long id;

    private Integer weight;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long evaluationMethodLineId;
    private String evaluationMethodLineName;

    private Long biddingCriteriaId;
    private String biddingCriteriaName;

    private List<CEvalMethodSubCriteriaDTO> evalMethodSubCriteriaList ;

    public List<CEvalMethodSubCriteriaDTO> getEvalMethodSubCriteriaList() {
        return evalMethodSubCriteriaList;
    }

    public void setEvalMethodSubCriteriaList(List<CEvalMethodSubCriteriaDTO> evalMethodSubCriteriaList) {
        this.evalMethodSubCriteriaList = evalMethodSubCriteriaList;
    }

    public String getBiddingCriteriaName() {
        return biddingCriteriaName;
    }

    public void setBiddingCriteriaName(String biddingCriteriaName) {
        this.biddingCriteriaName = biddingCriteriaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO = (CEvaluationMethodCriteriaDTO) o;
        if (cEvaluationMethodCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEvaluationMethodCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEvaluationMethodCriteriaDTO{" +
            "id=" + id +
            ", weight=" + weight +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", evaluationMethodLineId=" + evaluationMethodLineId +
            ", evaluationMethodLineName='" + evaluationMethodLineName + '\'' +
            ", biddingCriteriaId=" + biddingCriteriaId +
            ", biddingCriteriaName='" + biddingCriteriaName + '\'' +
            ", evalMethodSubCriteriaList=" + evalMethodSubCriteriaList +
            '}';
    }
}
