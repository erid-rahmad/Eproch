package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvalMethodSubCriteria} entity.
 */
public class CEvalMethodSubCriteriaDTO implements Serializable {

    private Long id;

    private Integer weight;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingCriteriaId;
    private String biddingCriteriaName;

    private Long biddingSubCriteriaId;
    private String biddingSubCriteriaName;

    private Long evaluationMethodCriteriaId;

    private List<CBiddingSubCriteriaDTO> biddingSubCriteriaDTO ;

    public List<CBiddingSubCriteriaDTO> getBiddingSubCriteriaDTO() {
        return biddingSubCriteriaDTO;
    }

    public void setBiddingSubCriteriaDTO(List<CBiddingSubCriteriaDTO> biddingSubCriteriaDTO) {
        this.biddingSubCriteriaDTO = biddingSubCriteriaDTO;
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

    public Long getEvaluationMethodCriteriaId() {
        return evaluationMethodCriteriaId;
    }

    public void setEvaluationMethodCriteriaId(Long cEvaluationMethodCriteriaId) {
        this.evaluationMethodCriteriaId = cEvaluationMethodCriteriaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO = (CEvalMethodSubCriteriaDTO) o;
        if (cEvalMethodSubCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEvalMethodSubCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEvalMethodSubCriteriaDTO{" +
            "id=" + id +
            ", weight=" + weight +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", biddingCriteriaId=" + biddingCriteriaId +
            ", biddingCriteriaName='" + biddingCriteriaName + '\'' +
            ", biddingSubCriteriaId=" + biddingSubCriteriaId +
            ", biddingSubCriteriaName='" + biddingSubCriteriaName + '\'' +
            ", evaluationMethodCriteriaId=" + evaluationMethodCriteriaId +
            ", biddingSubCriteriaDTO=" + biddingSubCriteriaDTO +
            '}';
    }
}
