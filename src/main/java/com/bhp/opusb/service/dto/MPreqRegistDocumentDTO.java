package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPreqRegistDocument} entity.
 */
public class MPreqRegistDocumentDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long registrationId;

    private Long siupDocumentId;
    private String siupFileName, siupDownloadUrl;

    private Long spdaDocumentId;
    private String spdaFileName, spdaDownloadUrl;
    
    public Long getId() {
        return id;
    }

    public String getSpdaDownloadUrl() {
        return spdaDownloadUrl;
    }

    public void setSpdaDownloadUrl(String spdaDownloadUrl) {
        this.spdaDownloadUrl = spdaDownloadUrl;
    }

    public String getSpdaFileName() {
        return spdaFileName;
    }

    public void setSpdaFileName(String spdaFileName) {
        this.spdaFileName = spdaFileName;
    }

    public String getSiupDownloadUrl() {
        return siupDownloadUrl;
    }

    public void setSiupDownloadUrl(String siupDownloadUrl) {
        this.siupDownloadUrl = siupDownloadUrl;
    }

    public String getSiupFileName() {
        return siupFileName;
    }

    public void setSiupFileName(String siupFileName) {
        this.siupFileName = siupFileName;
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

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long mPrequalRegistrationId) {
        this.registrationId = mPrequalRegistrationId;
    }

    public Long getSiupDocumentId() {
        return siupDocumentId;
    }

    public void setSiupDocumentId(Long cAttachmentId) {
        this.siupDocumentId = cAttachmentId;
    }

    public Long getSpdaDocumentId() {
        return spdaDocumentId;
    }

    public void setSpdaDocumentId(Long cAttachmentId) {
        this.spdaDocumentId = cAttachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPreqRegistDocumentDTO mPreqRegistDocumentDTO = (MPreqRegistDocumentDTO) o;
        if (mPreqRegistDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPreqRegistDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPreqRegistDocumentDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", registrationId=" + getRegistrationId() +
            ", siupDocumentId=" + getSiupDocumentId() +
            ", spdaDocumentId=" + getSpdaDocumentId() +
            "}";
    }
}
