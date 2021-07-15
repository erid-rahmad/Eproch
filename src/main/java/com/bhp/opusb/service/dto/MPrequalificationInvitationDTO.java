package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationInvitation} entity.
 */
public class MPrequalificationInvitationDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long prequalificationId;
    private String prequalificationName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long businessCategoryId;
    private String businessCategoryName;

    private Long businessSubCategoryId;
    private String businessSubCategoryName;
    
    public Long getId() {
        return id;
    }

    public String getPrequalificationName() {
        return prequalificationName;
    }

    public void setPrequalificationName(String prequalificationName) {
        this.prequalificationName = prequalificationName;
    }

    public String getBusinessSubCategoryName() {
        return businessSubCategoryName;
    }

    public void setBusinessSubCategoryName(String businessSubCategoryName) {
        this.businessSubCategoryName = businessSubCategoryName;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public Long getBusinessSubCategoryId() {
        return businessSubCategoryId;
    }

    public void setBusinessSubCategoryId(Long cBusinessCategoryId) {
        this.businessSubCategoryId = cBusinessCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationInvitationDTO mPrequalificationInvitationDTO = (MPrequalificationInvitationDTO) o;
        if (mPrequalificationInvitationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationInvitationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationInvitationDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", prequalificationId=" + getPrequalificationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", businessSubCategoryId=" + getBusinessSubCategoryId() +
            "}";
    }
}
