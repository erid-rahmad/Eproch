package com.bhp.opusb.service.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdCallout} entity.
 */
public class AdCalloutDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    @Size(max = 30)
    private String name;

    private String description;

    @NotNull
    @Size(max = 15)
    private String type;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long triggerId;
    private String triggerName;

    private Long fieldId;
    private String fieldName;
    
    private AdTriggerDTO trigger;
    private Set<AdCalloutTargetDTO> adCalloutTargets = new HashSet<>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(Long adTriggerId) {
        this.triggerId = adTriggerId;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long aDFieldId) {
        this.fieldId = aDFieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public AdTriggerDTO getTrigger() {
        return trigger;
    }

    public void setTrigger(AdTriggerDTO trigger) {
        this.trigger = trigger;
    }

    public Set<AdCalloutTargetDTO> getAdCalloutTargets() {
        return adCalloutTargets;
    }

    public void setAdCalloutTargets(Set<AdCalloutTargetDTO> adCalloutTargets) {
        this.adCalloutTargets = adCalloutTargets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdCalloutDTO adCalloutDTO = (AdCalloutDTO) o;
        if (adCalloutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adCalloutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdCalloutDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", triggerId=" + getTriggerId() +
            ", fieldId=" + getFieldId() +
            "}";
    }
}
