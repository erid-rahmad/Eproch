package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CRegistrationDocument} entity.
 */
public class CRegistrationDocumentDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String documentNo;

    private LocalDate expirationDate;

    private UUID uid;

    private Boolean active;


    private Long typeId;

    private Long fileId;

    private Long vendorId;
    private String vendorName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long cRegistrationDocTypeId) {
        this.typeId = cRegistrationDocTypeId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long cAttachmentId) {
        this.fileId = cAttachmentId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CRegistrationDocumentDTO cRegistrationDocumentDTO = (CRegistrationDocumentDTO) o;
        if (cRegistrationDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cRegistrationDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CRegistrationDocumentDTO{" +
            "id=" + getId() +
            ", documentNo='" + getDocumentNo() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", typeId=" + getTypeId() +
            ", fileId=" + getFileId() +
            ", vendorId=" + getVendorId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
