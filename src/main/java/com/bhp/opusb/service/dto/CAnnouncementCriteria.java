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
 * Criteria class for the {@link com.bhp.opusb.domain.CAnnouncement} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CAnnouncementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-announcements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CAnnouncementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter publishDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingId;

    private LongFilter biddingScheduleId;

    private LongFilter attachmentId;

    public CAnnouncementCriteria() {
    }

    public CAnnouncementCriteria(CAnnouncementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.publishDate = other.publishDate == null ? null : other.publishDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.biddingScheduleId = other.biddingScheduleId == null ? null : other.biddingScheduleId.copy();
        this.attachmentId = other.attachmentId == null ? null : other.attachmentId.copy();
    }

    @Override
    public CAnnouncementCriteria copy() {
        return new CAnnouncementCriteria(this);
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

    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public LongFilter getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(LongFilter biddingScheduleId) {
        this.biddingScheduleId = biddingScheduleId;
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
        final CAnnouncementCriteria that = (CAnnouncementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(publishDate, that.publishDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(biddingScheduleId, that.biddingScheduleId) &&
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
        biddingId,
        biddingScheduleId,
        attachmentId
        );
    }

    @Override
    public String toString() {
        return "CAnnouncementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (publishDate != null ? "publishDate=" + publishDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (biddingScheduleId != null ? "biddingScheduleId=" + biddingScheduleId + ", " : "") +
                (attachmentId != null ? "attachmentId=" + attachmentId + ", " : "") +
            "}";
    }

}
