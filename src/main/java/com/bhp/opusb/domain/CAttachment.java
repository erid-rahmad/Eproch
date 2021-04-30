package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.CAttachmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * A CAttachment.
 */
@Entity
@Table(name = "c_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CAttachment extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CAttachmentType type = CAttachmentType.LOCAL;

    /**
     * The original file name.
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * The small version of the image (for image file type).
     */
    @Column(name = "image_small")
    private String imageSmall;

    /**
     * The medium version of the image (for image file type).
     */
    @Column(name = "image_medium")
    private String imageMedium;

    /**
     * The large version of the image (for image file type).
     */
    @Column(name = "image_large")
    private String imageLarge;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "upload_dir")
    private String uploadDir;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cAttachments")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CAttachmentType getType() {
        return type;
    }

    public CAttachment type(CAttachmentType type) {
        this.type = type;
        return this;
    }

    public void setType(CAttachmentType type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public CAttachment fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Transient
    public String getDownloadUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/c-attachments/download/")
            .path(getId() + "-" + getFileName())
            .toUriString();
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public CAttachment imageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
        return this;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public CAttachment imageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
        return this;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public CAttachment imageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
        return this;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public String getMimeType() {
        return mimeType;
    }

    public CAttachment mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public CAttachment documentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public CAttachment uploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
        return this;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public UUID getUid() {
        return uid;
    }

    public CAttachment uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CAttachment active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CAttachment adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CAttachment)) {
            return false;
        }
        return id != null && id.equals(((CAttachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CAttachment{" +
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
            "}";
    }
}
