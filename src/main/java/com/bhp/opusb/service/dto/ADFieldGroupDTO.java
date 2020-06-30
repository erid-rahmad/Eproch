package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADFieldGroup} entity.
 */
public class ADFieldGroupDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    @NotNull
    private String name;

    private Boolean collapsible;

    private Boolean collapseByDefault;

    private Boolean active;

    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCollapsible() {
        return collapsible;
    }

    public void setCollapsible(Boolean collapsible) {
        this.collapsible = collapsible;
    }

    public Boolean isCollapseByDefault() {
        return collapseByDefault;
    }

    public void setCollapseByDefault(Boolean collapseByDefault) {
        this.collapseByDefault = collapseByDefault;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ADFieldGroupDTO aDFieldGroupDTO = (ADFieldGroupDTO) o;
        if (aDFieldGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aDFieldGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ADFieldGroupDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", collapsible='" + isCollapsible() + "'" +
            ", collapseByDefault='" + isCollapseByDefault() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
