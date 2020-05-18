package com.bhp.opusb.service.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.ADReferenceType;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADReference} entity.
 */
public class ADReferenceDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String value;

    private String description;

    private ADReferenceType referenceType;

    private Boolean active;

    private Set<ADReferenceListDTO> aDReferenceLists = new HashSet<>();

    
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

    public ADReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ADReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<ADReferenceListDTO> getADReferenceLists() {
        return aDReferenceLists;
    }

    public void setADReferenceLists(Set<ADReferenceListDTO> aDReferenceLists) {
        this.aDReferenceLists = aDReferenceLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ADReferenceDTO aDReferenceDTO = (ADReferenceDTO) o;
        if (aDReferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aDReferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ADReferenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", referenceType='" + getReferenceType() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
