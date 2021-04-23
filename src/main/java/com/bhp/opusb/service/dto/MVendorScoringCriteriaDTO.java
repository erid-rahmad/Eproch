package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorScoringCriteria} entity.
 */
public class MVendorScoringCriteriaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String requirement;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long evalMethodCriteriaLineId;

    private Long evalMethodSubCriteriaId;

    private Long vendorScoringLineId;

    private Long biddingSubCriteriaLineId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
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

    public Long getEvalMethodCriteriaLineId() {
        return evalMethodCriteriaLineId;
    }

    public void setEvalMethodCriteriaLineId(Long cEvalMethodCriteriaLineId) {
        this.evalMethodCriteriaLineId = cEvalMethodCriteriaLineId;
    }

    public Long getEvalMethodSubCriteriaId() {
        return evalMethodSubCriteriaId;
    }

    public void setEvalMethodSubCriteriaId(Long cEvalMethodSubCriteriaId) {
        this.evalMethodSubCriteriaId = cEvalMethodSubCriteriaId;
    }

    public Long getVendorScoringLineId() {
        return vendorScoringLineId;
    }

    public void setVendorScoringLineId(Long mVendorScoringLineId) {
        this.vendorScoringLineId = mVendorScoringLineId;
    }

    public Long getBiddingSubCriteriaLineId() {
        return biddingSubCriteriaLineId;
    }

    public void setBiddingSubCriteriaLineId(Long cBiddingSubCriteriaLineId) {
        this.biddingSubCriteriaLineId = cBiddingSubCriteriaLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO = (MVendorScoringCriteriaDTO) o;
        if (mVendorScoringCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorScoringCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorScoringCriteriaDTO{" +
            "id=" + getId() +
            ", requirement='" + getRequirement() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", evalMethodCriteriaLineId=" + getEvalMethodCriteriaLineId() +
            ", evalMethodSubCriteriaId=" + getEvalMethodSubCriteriaId() +
            ", vendorScoringLineId=" + getVendorScoringLineId() +
            ", biddingSubCriteriaLineId=" + getBiddingSubCriteriaLineId() +
            "}";
    }
}