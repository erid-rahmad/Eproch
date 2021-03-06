package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ScAccess} entity.
 */
public class ScAccessDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @Size(max = 50)
    private String name;

    private String description;

    private Boolean canWrite;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long typeId;
    private String typeName;

    private Long windowId;
    private String windowName;

    private Long formId;
    private String formName;

    private Long documentTypeId;
    private String documentTypeName;

    private Long referenceListId;
    private String referenceListName;

    private Long authorityId;
    private String authorityName;
    
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(Boolean canWrite) {
        this.canWrite = canWrite;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long scAccessTypeId) {
        this.typeId = scAccessTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getWindowId() {
        return windowId;
    }

    public void setWindowId(Long aDWindowId) {
        this.windowId = aDWindowId;
    }

    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long adFormId) {
        this.formId = adFormId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long cDocumentTypeId) {
        this.documentTypeId = cDocumentTypeId;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public Long getReferenceListId() {
        return referenceListId;
    }

    public void setReferenceListId(Long aDReferenceListId) {
        this.referenceListId = aDReferenceListId;
    }

    public String getReferenceListName() {
        return referenceListName;
    }

    public void setReferenceListName(String referenceListName) {
        this.referenceListName = referenceListName;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long scAuthorityId) {
        this.authorityId = scAuthorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScAccessDTO scAccessDTO = (ScAccessDTO) o;
        if (scAccessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scAccessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScAccessDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", canWrite='" + isCanWrite() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", typeId=" + getTypeId() +
            ", windowId=" + getWindowId() +
            ", windowName=" + getWindowName() +
            ", formId=" + getFormId() +
            ", formName=" + getFormName() +
            ", documentTypeId=" + getDocumentTypeId() +
            ", referenceListId=" + getReferenceListId() +
            ", authorityId=" + getAuthorityId() +
            "}";
    }
}
