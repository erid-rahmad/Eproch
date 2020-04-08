package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.bhp.opusb.domain.enumeration.ReferenceType;

/**
 * A DTO for the {@link com.bhp.opusb.domain.Reference} entity.
 */
public class ReferenceDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private ReferenceType referenceType;

    
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

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReferenceDTO referenceDTO = (ReferenceDTO) o;
        if (referenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", referenceType='" + getReferenceType() + "'" +
            "}";
    }
}
