package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalVendorSuggestion} entity.
 */
public class MPrequalVendorSuggestionDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long prequalificationId;
    private String prequalificationName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long businessSubCategoryId;
    private String businessSubCategoryName;

    private Long vendorId;
    private String vendorCode;
    private String vendorName;
    
    public Long getId() {
        return id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getBusinessSubCategoryName() {
        return businessSubCategoryName;
    }

    public void setBusinessSubCategoryName(String businessSubCategoryName) {
        this.businessSubCategoryName = businessSubCategoryName;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public String getPrequalificationName() {
        return prequalificationName;
    }

    public void setPrequalificationName(String prequalificationName) {
        this.prequalificationName = prequalificationName;
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

    public Long getBusinessSubCategoryId() {
        return businessSubCategoryId;
    }

    public void setBusinessSubCategoryId(Long cBusinessCategoryId) {
        this.businessSubCategoryId = cBusinessCategoryId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO = (MPrequalVendorSuggestionDTO) o;
        if (mPrequalVendorSuggestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalVendorSuggestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalVendorSuggestionDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", prequalificationId=" + getPrequalificationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", businessSubCategoryId=" + getBusinessSubCategoryId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}
