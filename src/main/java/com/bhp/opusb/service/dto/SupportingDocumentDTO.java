package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.SupportingDocument} entity.
 */
public class SupportingDocumentDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String documentNo;

    private LocalDate expirationDate;

    @Lob
    private byte[] file;

    private String fileContentType;

    private Long typeId;
    private String typeName;

    private Long vendorId;
    
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long documentTypeId) {
        this.typeId = documentTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupportingDocumentDTO supportingDocumentDTO = (SupportingDocumentDTO) o;
        if (supportingDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supportingDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupportingDocumentDTO{" +
            "id=" + getId() +
            ", documentNo='" + getDocumentNo() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", file='" + getFile() + "'" +
            ", typeId=" + getTypeId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}
