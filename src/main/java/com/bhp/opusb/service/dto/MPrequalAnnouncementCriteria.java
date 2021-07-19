package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MPrequalAnnouncement} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPrequalAnnouncementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-prequal-announcements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPrequalAnnouncementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter publishDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter prequalificationId;

    private LongFilter prequalificationScheduleId;

    private LongFilter attachmentId;

    public MPrequalAnnouncementCriteria() {
    }

    public MPrequalAnnouncementCriteria(MPrequalAnnouncementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.publishDate = other.publishDate == null ? null : other.publishDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.prequalificationId = other.prequalificationId == null ? null : other.prequalificationId.copy();
        this.prequalificationScheduleId = other.prequalificationScheduleId == null ? null : other.prequalificationScheduleId.copy();
        this.attachmentId = other.attachmentId == null ? null : other.attachmentId.copy();
    }

    @Override
    public MPrequalAnnouncementCriteria copy() {
        return new MPrequalAnnouncementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(ZonedDateTimeFilter publishDate) {
        this.publishDate = publishDate;
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

    public LongFilter getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(LongFilter prequalificationId) {
        this.prequalificationId = prequalificationId;
    }

    public LongFilter getPrequalificationScheduleId() {
        return prequalificationScheduleId;
    }

    public void setPrequalificationScheduleId(LongFilter prequalificationScheduleId) {
        this.prequalificationScheduleId = prequalificationScheduleId;
    }

    public LongFilter getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(LongFilter attachmentId) {
        this.attachmentId = attachmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MPrequalAnnouncementCriteria that = (MPrequalAnnouncementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(publishDate, that.publishDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(prequalificationId, that.prequalificationId) &&
            Objects.equals(prequalificationScheduleId, that.prequalificationScheduleId) &&
            Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        publishDate,
        uid,
        active,
        adOrganizationId,
        prequalificationId,
        prequalificationScheduleId,
        attachmentId
        );
    }

    @Override
    public String toString() {
        return "MPrequalAnnouncementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (publishDate != null ? "publishDate=" + publishDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (prequalificationId != null ? "prequalificationId=" + prequalificationId + ", " : "") +
                (prequalificationScheduleId != null ? "prequalificationScheduleId=" + prequalificationScheduleId + ", " : "") +
                (attachmentId != null ? "attachmentId=" + attachmentId + ", " : "") +
            "}";
    }

}
