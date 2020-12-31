package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.bhp.opusb.domain.enumeration.CGalleryItemType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CGalleryItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CGalleryItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-gallery-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CGalleryItemCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CGalleryItemType
     */
    public static class CGalleryItemTypeFilter extends Filter<CGalleryItemType> {

        public CGalleryItemTypeFilter() {
        }

        public CGalleryItemTypeFilter(CGalleryItemTypeFilter filter) {
            super(filter);
        }

        @Override
        public CGalleryItemTypeFilter copy() {
            return new CGalleryItemTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CGalleryItemTypeFilter type;

    private IntegerFilter sequence;

    private BooleanFilter preview;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter cAttachmentId;

    private LongFilter adOrganizationId;

    private LongFilter cGalleryId;

    public CGalleryItemCriteria() {
    }

    public CGalleryItemCriteria(CGalleryItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.sequence = other.sequence == null ? null : other.sequence.copy();
        this.preview = other.preview == null ? null : other.preview.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.cAttachmentId = other.cAttachmentId == null ? null : other.cAttachmentId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.cGalleryId = other.cGalleryId == null ? null : other.cGalleryId.copy();
    }

    @Override
    public CGalleryItemCriteria copy() {
        return new CGalleryItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CGalleryItemTypeFilter getType() {
        return type;
    }

    public void setType(CGalleryItemTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getSequence() {
        return sequence;
    }

    public void setSequence(IntegerFilter sequence) {
        this.sequence = sequence;
    }

    public BooleanFilter getPreview() {
        return preview;
    }

    public void setPreview(BooleanFilter preview) {
        this.preview = preview;
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

    public LongFilter getCAttachmentId() {
        return cAttachmentId;
    }

    public void setCAttachmentId(LongFilter cAttachmentId) {
        this.cAttachmentId = cAttachmentId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getCGalleryId() {
        return cGalleryId;
    }

    public void setCGalleryId(LongFilter cGalleryId) {
        this.cGalleryId = cGalleryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CGalleryItemCriteria that = (CGalleryItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(sequence, that.sequence) &&
            Objects.equals(preview, that.preview) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(cAttachmentId, that.cAttachmentId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(cGalleryId, that.cGalleryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        sequence,
        preview,
        uid,
        active,
        cAttachmentId,
        adOrganizationId,
        cGalleryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CGalleryItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (sequence != null ? "sequence=" + sequence + ", " : "") +
                (preview != null ? "preview=" + preview + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (cAttachmentId != null ? "cAttachmentId=" + cAttachmentId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (cGalleryId != null ? "cGalleryId=" + cGalleryId + ", " : "") +
            "}";
    }

}
