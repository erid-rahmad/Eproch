package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CRegDocTypeBusinessCategory} entity.
 */
public class CRegDocTypeBusinessCategoryDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean mandatory;

    private Boolean active;


    private Long documentTypeId;

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

    public Boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long cRegistrationDocTypeId) {
        this.documentTypeId = cRegistrationDocTypeId;
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

        CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO = (CRegDocTypeBusinessCategoryDTO) o;
        if (cRegDocTypeBusinessCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cRegDocTypeBusinessCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CRegDocTypeBusinessCategoryDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", active='" + isActive() + "'" +
            ", documentTypeId=" + getDocumentTypeId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
