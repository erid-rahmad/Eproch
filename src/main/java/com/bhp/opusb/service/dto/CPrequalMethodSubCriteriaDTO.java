package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPrequalMethodSubCriteria} entity.
 */
public class CPrequalMethodSubCriteriaDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private Integer weight;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingCriteriaId;

    private Long biddingSubCriteriaId;
    private String biddingSubCriteriaName;

    private Long prequalMethodCriteriaId;
    
    public Long getId() {
        return id;
    }

    public String getBiddingSubCriteriaName() {
        return biddingSubCriteriaName;
    }

    public void setBiddingSubCriteriaName(String biddingSubCriteriaName) {
        this.biddingSubCriteriaName = biddingSubCriteriaName;
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

    public Long getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(Long cBiddingCriteriaId) {
        this.biddingCriteriaId = cBiddingCriteriaId;
    }

    public Long getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(Long cBiddingSubCriteriaId) {
        this.biddingSubCriteriaId = cBiddingSubCriteriaId;
    }

    public Long getPrequalMethodCriteriaId() {
        return prequalMethodCriteriaId;
    }

    public void setPrequalMethodCriteriaId(Long cPrequalMethodCriteriaId) {
        this.prequalMethodCriteriaId = cPrequalMethodCriteriaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO = (CPrequalMethodSubCriteriaDTO) o;
        if (cPrequalMethodSubCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPrequalMethodSubCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPrequalMethodSubCriteriaDTO{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingCriteriaId=" + getBiddingCriteriaId() +
            ", biddingSubCriteriaId=" + getBiddingSubCriteriaId() +
            ", prequalMethodCriteriaId=" + getPrequalMethodCriteriaId() +
            "}";
    }
}
