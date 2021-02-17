package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEventType} entity.
 */
public class CEventTypeDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long bindingTypeId;
    private String bindingTypeName;

    public Long getId() {
        return id;
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

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getBindingTypeId() {
        return bindingTypeId;
    }

    public void setBindingTypeId(Long cBiddingTypeId) {
        this.bindingTypeId = cBiddingTypeId;
    }

    public String getBindingTypeName() {
        return bindingTypeName;
    }

    public void setBindingTypeName(String bindingTypeName) {
        this.bindingTypeName = bindingTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEventTypeDTO cEventTypeDTO = (CEventTypeDTO) o;
        if (cEventTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEventTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEventTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", bindingTypeId=" + getBindingTypeId() +
            ", bindingTypeName=" + getBindingTypeName() +
            "}";
    }
}
