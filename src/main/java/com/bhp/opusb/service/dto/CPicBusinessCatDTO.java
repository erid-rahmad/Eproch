package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPicBusinessCat} entity.
 */
public class CPicBusinessCatDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long contactId;

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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long cPersonInChargeId) {
        this.contactId = cPersonInChargeId;
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

        CPicBusinessCatDTO cPicBusinessCatDTO = (CPicBusinessCatDTO) o;
        if (cPicBusinessCatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPicBusinessCatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPicBusinessCatDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", contactId=" + getContactId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
