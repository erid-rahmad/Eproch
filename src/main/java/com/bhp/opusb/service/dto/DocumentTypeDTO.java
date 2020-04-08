package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;

import com.bhp.opusb.domain.DocumentTypeBusinessCategory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.bhp.opusb.domain.DocumentType} entity.
 */
public class DocumentTypeDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean hasExpirationDate;

    private String mandatoryBusinessCategories;

    private String additionalBusinessCategories;

    private Boolean mandatoryForCompany;

    private Boolean mandatoryForProfessional;

    private Boolean additionalForCompany;

    private Boolean additionalForProfessional;

    private Boolean active;
    
    private Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories = new HashSet<>();
    
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

    public String getMandatoryBusinessCategories() {
        return mandatoryBusinessCategories;
    }

    public void setMandatoryBusinessCategories(String mandatoryBusinessCategories) {
        this.mandatoryBusinessCategories = mandatoryBusinessCategories;
    }

    public String getAdditionalBusinessCategories() {
        return additionalBusinessCategories;
    }

    public void setAdditionalBusinessCategories(String additionalBusinessCategories) {
        this.additionalBusinessCategories = additionalBusinessCategories;
    }

    public Boolean isMandatoryForCompany() {
        return mandatoryForCompany;
    }

    public void setMandatoryForCompany(Boolean mandatoryForCompany) {
        this.mandatoryForCompany = mandatoryForCompany;
    }

    public Boolean isMandatoryForProfessional() {
        return mandatoryForProfessional;
    }

    public void setMandatoryForProfessional(Boolean mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
    }

    public Boolean isAdditionalForCompany() {
        return additionalForCompany;
    }

    public void setAdditionalForCompany(Boolean additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
    }

    public Boolean isAdditionalForProfessional() {
        return additionalForProfessional;
    }

    public void setAdditionalForProfessional(Boolean additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<DocumentTypeBusinessCategory> getDocumentTypeBusinessCategories() {
        return documentTypeBusinessCategories;
    }

    public void setDocumentTypeBusinessCategories(Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories) {
        this.documentTypeBusinessCategories = documentTypeBusinessCategories;
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
            ", mandatoryBusinessCategories='" + getMandatoryBusinessCategories() + "'" +
            ", additionalBusinessCategories='" + getAdditionalBusinessCategories() + "'" +
            ", mandatoryForCompany='" + isMandatoryForCompany() + "'" +
            ", mandatoryForProfessional='" + isMandatoryForProfessional() + "'" +
            ", additionalForCompany='" + isAdditionalForCompany() + "'" +
            ", additionalForProfessional='" + isAdditionalForProfessional() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
