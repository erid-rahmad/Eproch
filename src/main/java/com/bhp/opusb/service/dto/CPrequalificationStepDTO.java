package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPrequalificationStep} entity.
 */
public class CPrequalificationStepDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String type;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adFormId;
    private String adFormName;

    public Long getId() {
        return id;
    }

    public String getAdFormName() {
        return adFormName;
    }

    public void setAdFormName(String adFormName) {
        this.adFormName = adFormName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getAdFormId() {
        return adFormId;
    }

    public void setAdFormId(Long adFormId) {
        this.adFormId = adFormId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPrequalificationStepDTO cPrequalificationStepDTO = (CPrequalificationStepDTO) o;
        if (cPrequalificationStepDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPrequalificationStepDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPrequalificationStepDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adFormId=" + getAdFormId() +
            "}";
    }
}
