package com.bhp.opusb.service.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.bhp.opusb.domain.AdTriggerParam;
import com.bhp.opusb.domain.enumeration.AdTriggerType;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdTrigger} entity.
 */
public class AdTriggerDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String value;

    private String description;

    private AdTriggerType type;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Set<AdTriggerParam> adTriggerParams = new HashSet<>();
    
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdTriggerType getType() {
        return type;
    }

    public void setType(AdTriggerType type) {
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

    public Set<AdTriggerParam> getAdTriggerParams() {
        return adTriggerParams;
    }

    public void setAdTriggerParams(Set<AdTriggerParam> adTriggerParams) {
        this.adTriggerParams = adTriggerParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdTriggerDTO adTriggerDTO = (AdTriggerDTO) o;
        if (adTriggerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adTriggerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdTriggerDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
