package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdCalloutTarget} entity.
 */
public class AdCalloutTargetDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @Size(max = 30)
    private String sourceField;

    @NotNull
    @Size(max = 30)
    private String targetName;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long calloutId;
    private String calloutName;
    
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

    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
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

    public Long getCalloutId() {
        return calloutId;
    }

    public void setCalloutId(Long adCalloutId) {
        this.calloutId = adCalloutId;
    }

    public String getCalloutName() {
        return calloutName;
    }

    public void setCalloutName(String calloutName) {
        this.calloutName = calloutName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdCalloutTargetDTO adCalloutTargetDTO = (AdCalloutTargetDTO) o;
        if (adCalloutTargetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adCalloutTargetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdCalloutTargetDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", sourceField='" + getSourceField() + "'" +
            ", targetName='" + getTargetName() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", calloutId=" + getCalloutId() +
            "}";
    }
}
