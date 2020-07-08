package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import com.bhp.opusb.domain.enumeration.CBusinessCategorySector;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBusinessCategory} entity.
 */
public class CBusinessCategoryDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private CBusinessCategorySector sector;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long parentCategoryId;
    private String parentCategoryName;
    
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

    public CBusinessCategorySector getSector() {
        return sector;
    }

    public void setSector(CBusinessCategorySector sector) {
        this.sector = sector;
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
    
    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long cBusinessCategoryId) {
        this.parentCategoryId = cBusinessCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CBusinessCategoryDTO cBusinessCategoryDTO = (CBusinessCategoryDTO) o;
        if (cBusinessCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBusinessCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBusinessCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sector='" + getSector() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", parentCategoryId=" + getParentCategoryId() +
            ", parentCategoryName=" + getParentCategoryName() +
            "}";
    }

    
}
