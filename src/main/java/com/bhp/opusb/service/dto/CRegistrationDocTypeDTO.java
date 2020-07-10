package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CRegistrationDocType} entity.
 */
public class CRegistrationDocTypeDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean hasExpirationDate;

    private String mandatoryBusinessCategories;

    private String additionalBusinessCategories;

    private Boolean mandatoryForCompany;

    private Boolean additionalForCompany;

    private Boolean mandatoryForProfessional;

    private Boolean additionalForProfessional;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    
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

    public Boolean isAdditionalForCompany() {
        return additionalForCompany;
    }

    public void setAdditionalForCompany(Boolean additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
    }

    public Boolean isMandatoryForProfessional() {
        return mandatoryForProfessional;
    }

    public void setMandatoryForProfessional(Boolean mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
    }

    public Boolean isAdditionalForProfessional() {
        return additionalForProfessional;
    }

    public void setAdditionalForProfessional(Boolean additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CRegistrationDocTypeDTO cRegistrationDocTypeDTO = (CRegistrationDocTypeDTO) o;
        if (cRegistrationDocTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cRegistrationDocTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CRegistrationDocTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", hasExpirationDate='" + isHasExpirationDate() + "'" +
            ", mandatoryBusinessCategories='" + getMandatoryBusinessCategories() + "'" +
            ", additionalBusinessCategories='" + getAdditionalBusinessCategories() + "'" +
            ", mandatoryForCompany='" + isMandatoryForCompany() + "'" +
            ", additionalForCompany='" + isAdditionalForCompany() + "'" +
            ", mandatoryForProfessional='" + isMandatoryForProfessional() + "'" +
            ", additionalForProfessional='" + isAdditionalForProfessional() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
