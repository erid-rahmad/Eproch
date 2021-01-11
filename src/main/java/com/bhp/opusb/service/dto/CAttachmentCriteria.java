package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.CAttachmentType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CAttachment} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CAttachmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-attachments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CAttachmentCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CAttachmentType
     */
    public static class CAttachmentTypeFilter extends Filter<CAttachmentType> {

        public CAttachmentTypeFilter() {
        }

        public CAttachmentTypeFilter(CAttachmentTypeFilter filter) {
            super(filter);
        }

        @Override
        public CAttachmentTypeFilter copy() {
            return new CAttachmentTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CAttachmentTypeFilter type;

    private StringFilter fileName;

    private StringFilter imageSmall;

    private StringFilter imageMedium;

    private StringFilter imageLarge;

    private StringFilter mimeType;

    private StringFilter documentType;

    private StringFilter uploadDir;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    public CAttachmentCriteria() {
    }

    public CAttachmentCriteria(CAttachmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.imageSmall = other.imageSmall == null ? null : other.imageSmall.copy();
        this.imageMedium = other.imageMedium == null ? null : other.imageMedium.copy();
        this.imageLarge = other.imageLarge == null ? null : other.imageLarge.copy();
        this.mimeType = other.mimeType == null ? null : other.mimeType.copy();
        this.documentType = other.documentType == null ? null : other.documentType.copy();
        this.uploadDir = other.uploadDir == null ? null : other.uploadDir.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CAttachmentCriteria copy() {
        return new CAttachmentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CAttachmentTypeFilter getType() {
        return type;
    }

    public void setType(CAttachmentTypeFilter type) {
        this.type = type;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(StringFilter imageSmall) {
        this.imageSmall = imageSmall;
    }

    public StringFilter getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(StringFilter imageMedium) {
        this.imageMedium = imageMedium;
    }

    public StringFilter getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(StringFilter imageLarge) {
        this.imageLarge = imageLarge;
    }

    public StringFilter getMimeType() {
        return mimeType;
    }

    public void setMimeType(StringFilter mimeType) {
        this.mimeType = mimeType;
    }

    public StringFilter getDocumentType() {
        return documentType;
    }

    public void setDocumentType(StringFilter documentType) {
        this.documentType = documentType;
    }

    public StringFilter getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(StringFilter uploadDir) {
        this.uploadDir = uploadDir;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CAttachmentCriteria that = (CAttachmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(imageSmall, that.imageSmall) &&
            Objects.equals(imageMedium, that.imageMedium) &&
            Objects.equals(imageLarge, that.imageLarge) &&
            Objects.equals(mimeType, that.mimeType) &&
            Objects.equals(documentType, that.documentType) &&
            Objects.equals(uploadDir, that.uploadDir) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        fileName,
        imageSmall,
        imageMedium,
        imageLarge,
        mimeType,
        documentType,
        uploadDir,
        uid,
        active,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CAttachmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (fileName != null ? "fileName=" + fileName + ", " : "") +
                (imageSmall != null ? "imageSmall=" + imageSmall + ", " : "") +
                (imageMedium != null ? "imageMedium=" + imageMedium + ", " : "") +
                (imageLarge != null ? "imageLarge=" + imageLarge + ", " : "") +
                (mimeType != null ? "mimeType=" + mimeType + ", " : "") +
                (documentType != null ? "documentType=" + documentType + ", " : "") +
                (uploadDir != null ? "uploadDir=" + uploadDir + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}
