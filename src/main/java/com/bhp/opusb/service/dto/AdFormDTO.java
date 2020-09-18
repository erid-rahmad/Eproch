package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.AdAccessLevel;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdForm} entity.
 */
public class AdFormDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private String formName;

    private AdAccessLevel accessLevel;


    private Long adOrganizationId;
    private String adOrganizationName;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public AdAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AdAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdFormDTO adFormDTO = (AdFormDTO) o;
        if (adFormDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adFormDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdFormDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", formName='" + getFormName() + "'" +
            ", accessLevel='" + getAccessLevel() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
