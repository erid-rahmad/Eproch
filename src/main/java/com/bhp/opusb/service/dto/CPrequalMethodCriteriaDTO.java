package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPrequalMethodCriteria} entity.
 */
public class CPrequalMethodCriteriaDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private Integer weight;

    private UUID uid;

    private Boolean active;


    private Long biddingCriteriaId;
    private String biddingCriteriaName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long prequalMethodLineId;
    private List<CPrequalMethodSubCriteriaDTO> prequalMethodSubCriteriaDTOs ;
    
    public Long getId() {
        return id;
    }

    public List<CPrequalMethodSubCriteriaDTO> getPrequalMethodSubCriteriaDTOs() {
        return prequalMethodSubCriteriaDTOs;
    }

    public void setPrequalMethodSubCriteriaDTOs(List<CPrequalMethodSubCriteriaDTO> prequalMethodSubCriteriaDTOs) {
        this.prequalMethodSubCriteriaDTOs = prequalMethodSubCriteriaDTOs;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public String getBiddingCriteriaName() {
        return biddingCriteriaName;
    }

    public void setBiddingCriteriaName(String biddingCriteriaName) {
        this.biddingCriteriaName = biddingCriteriaName;
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

    public Long getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(Long cBiddingCriteriaId) {
        this.biddingCriteriaId = cBiddingCriteriaId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getPrequalMethodLineId() {
        return prequalMethodLineId;
    }

    public void setPrequalMethodLineId(Long cPrequalMethodLineId) {
        this.prequalMethodLineId = cPrequalMethodLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO = (CPrequalMethodCriteriaDTO) o;
        if (cPrequalMethodCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPrequalMethodCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPrequalMethodCriteriaDTO{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingCriteriaId=" + getBiddingCriteriaId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalMethodLineId=" + getPrequalMethodLineId() +
            "}";
    }
}
