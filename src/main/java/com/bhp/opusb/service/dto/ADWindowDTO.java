package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.ADWindowType;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADWindow} entity.
 */
public class ADWindowDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    @NotNull
    private String name;

    private String description;

    private String titleLogic;

    @NotNull
    private ADWindowType type;

    private Boolean treeView;

    private Boolean active;

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

    public String getTitleLogic() {
        return titleLogic;
    }

    public void setTitleLogic(String titleLogic) {
        this.titleLogic = titleLogic;
    }

    public ADWindowType getType() {
        return type;
    }

    public void setType(ADWindowType type) {
        this.type = type;
    }

    public Boolean isTreeView() {
        return treeView;
    }

    public void setTreeView(Boolean treeView) {
        this.treeView = treeView;
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
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", titleLogic='" + getTitleLogic() + "'" +
            ", type='" + getType() + "'" +
            ", treeView='" + isTreeView() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
