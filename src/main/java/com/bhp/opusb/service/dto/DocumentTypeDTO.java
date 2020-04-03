package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.DocumentType} entity.
 */
public class DocumentTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean hasExpirationDate;

    private Boolean forCompany;

    private Boolean forProfessional;

    private Boolean active;

    
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

    public Boolean isHasExpirationDate() {
        return hasExpirationDate;
    }

    public void setHasExpirationDate(Boolean hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
    }

    public Boolean isForCompany() {
        return forCompany;
    }

    public void setForCompany(Boolean forCompany) {
        this.forCompany = forCompany;
    }

    public Boolean isForProfessional() {
        return forProfessional;
    }

    public void setForProfessional(Boolean forProfessional) {
        this.forProfessional = forProfessional;
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

        DocumentTypeDTO documentTypeDTO = (DocumentTypeDTO) o;
        if (documentTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", hasExpirationDate='" + isHasExpirationDate() + "'" +
            ", forCompany='" + isForCompany() + "'" +
            ", forProfessional='" + isForProfessional() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
