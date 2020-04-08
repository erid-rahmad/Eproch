package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.BusinessCategory} entity.
 */
public class BusinessCategoryDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

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

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long businessCategoryId) {
        this.parentCategoryId = businessCategoryId;
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

        BusinessCategoryDTO businessCategoryDTO = (BusinessCategoryDTO) o;
        if (businessCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", parentCategoryId=" + getParentCategoryId() +
            ", parentCategoryName=" + getParentCategoryName() +
            "}";
    }
}
