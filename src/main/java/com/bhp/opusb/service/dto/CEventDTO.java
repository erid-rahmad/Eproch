package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvent} entity.
 */
public class CEventDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    /**
     * Specify the type of event form
     */
    @Size(max = 10)
    @ApiModelProperty(value = "Specify the type of event form")
    private String formType;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    @JsonProperty("cProductClassificationId")
    private Long cProductClassificationId;

    @JsonProperty("cProductClassificationName")
    private String cProductClassificationName;

    private Long adFormId;
    private String adFormName;
    
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

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
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

    public Long getCProductClassificationId() {
        return cProductClassificationId;
    }

    public void setCProductClassificationId(Long cProductClassificationId) {
        this.cProductClassificationId = cProductClassificationId;
    }

    public String getCProductClassificationName() {
        return cProductClassificationName;
    }

    public void setCProductClassificationName(String cProductClassificationName) {
        this.cProductClassificationName = cProductClassificationName;
    }

    public Long getAdFormId() {
        return adFormId;
    }

    public void setAdFormId(Long adFormId) {
        this.adFormId = adFormId;
    }

    public String getAdFormName() {
        return adFormName;
    }

    public void setAdFormName(String adFormName) {
        this.adFormName = adFormName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEventDTO cEventDTO = (CEventDTO) o;
        if (cEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEventDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", formType='" + getFormType() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cProductClassificationId=" + getCProductClassificationId() +
            ", adFormId=" + getAdFormId() +
            "}";
    }
}
