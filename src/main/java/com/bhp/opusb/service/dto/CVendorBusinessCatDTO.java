package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorBusinessCat} entity.
 */
public class CVendorBusinessCatDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long vendorId;

    private Long businessCategoryId;

    private Long adOrganizationId;
    
    public Long getId() {
        return id;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CVendorBusinessCatDTO cVendorBusinessCatDTO = (CVendorBusinessCatDTO) o;
        if (cVendorBusinessCatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cVendorBusinessCatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CVendorBusinessCatDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
