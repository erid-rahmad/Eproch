package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationCriteria} entity.
 */
public class MPrequalificationCriteriaDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String requirement;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long prequalificationId;

    private Long prequalMethodCriteriaId;

    private Long prequalMethodSubCriteriaId;

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

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    public Long getPrequalMethodCriteriaId() {
        return prequalMethodCriteriaId;
    }

    public void setPrequalMethodCriteriaId(Long cPrequalMethodCriteriaId) {
        this.prequalMethodCriteriaId = cPrequalMethodCriteriaId;
    }

    public Long getPrequalMethodSubCriteriaId() {
        return prequalMethodSubCriteriaId;
    }

    public void setPrequalMethodSubCriteriaId(Long cPrequalMethodSubCriteriaId) {
        this.prequalMethodSubCriteriaId = cPrequalMethodSubCriteriaId;
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

        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO = (MPrequalificationCriteriaDTO) o;
        if (mPrequalificationCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationCriteriaDTO{" +
            "id=" + getId() +
            ", requirement='" + getRequirement() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalificationId=" + getPrequalificationId() +
            ", prequalMethodCriteriaId=" + getPrequalMethodCriteriaId() +
            ", prequalMethodSubCriteriaId=" + getPrequalMethodSubCriteriaId() +
            ", biddingSubCriteriaLineId=" + getBiddingSubCriteriaLineId() +
            "}";
    }
}
