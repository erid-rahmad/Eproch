package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.CAttachmentType;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CAttachment} entity.
 */
public class CAttachmentDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private CAttachmentType type;

    /**
     * The original file name.
     */
    @ApiModelProperty(value = "The original file name.")
    private String fileName;

    /**
     * The small version of the image (for image file type).
     */
    @ApiModelProperty(value = "The small version of the image (for image file type).")
    private String imageSmall;

    /**
     * The medium version of the image (for image file type).
     */
    @ApiModelProperty(value = "The medium version of the image (for image file type).")
    private String imageMedium;

    /**
     * The large version of the image (for image file type).
     */
    @ApiModelProperty(value = "The large version of the image (for image file type).")
    private String imageLarge;

    private String mimeType;

    private String documentType;

    private String uploadDir;

    private UUID uid;

    private Boolean active;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CAttachmentType getType() {
        return type;
    }

    public void setType(CAttachmentType type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CAttachmentDTO cAttachmentDTO = (CAttachmentDTO) o;
        if (cAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CAttachmentDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", imageSmall='" + getImageSmall() + "'" +
            ", imageMedium='" + getImageMedium() + "'" +
            ", imageLarge='" + getImageLarge() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", uploadDir='" + getUploadDir() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
