package com.bhp.opusb.service.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.ADWindowType;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADWindow} entity.
 */
public class ADWindowDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private ADWindowType type;

    private Boolean active;
    private Long adClientId;
    private String adClientName;
    private Long adOrganizationId;
    private String adOrganizationName;
    
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

    public ADWindowType getType() {
        return type;
    }

    public void setType(ADWindowType type) {
        this.type = type;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdClientId() {
        return adClientId;
    }

    public void setAdClientId(Long aDClientId) {
        this.adClientId = aDClientId;
    }

    public String getAdClientName() {
        return adClientName;
    }

    public void setAdClientName(String adClientName) {
        this.adClientName = adClientName;
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

        ADWindowDTO aDWindowDTO = (ADWindowDTO) o;
        if (aDWindowDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aDWindowDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ADWindowDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", active='" + isActive() + "'" +
            ", adClientId=" + getAdClientId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
