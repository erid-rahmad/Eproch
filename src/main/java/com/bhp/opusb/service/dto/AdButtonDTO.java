package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdButton} entity.
 */
public class AdButtonDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private String name;

    private String tooltip;

    private String description;

    private Boolean toolbar;

    private String icon;

    private Long adOrganizationId;
    private Long adOrganizationName;

    private Long adTriggerId;
    private Long adTriggerName;
    
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

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isToolbar() {
        return toolbar;
    }

    public void setToolbar(Boolean toolbar) {
        this.toolbar = toolbar;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(Long adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getAdTriggerId() {
        return adTriggerId;
    }

    public void setAdTriggerId(Long adTriggerId) {
        this.adTriggerId = adTriggerId;
    }

    public Long getAdTriggerName() {
        return adTriggerName;
    }

    public void setAdTriggerName(Long adTriggerName) {
        this.adTriggerName = adTriggerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdButtonDTO adButtonDTO = (AdButtonDTO) o;
        if (adButtonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adButtonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdButtonDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", tooltip='" + getTooltip() + "'" +
            ", description='" + getDescription() + "'" +
            ", toolbar='" + isToolbar() + "'" +
            ", icon='" + getIcon() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adTriggerId=" + getAdTriggerId() +
            "}";
    }
}
