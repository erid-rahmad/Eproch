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
 * Criteria class for the {@link com.bhp.opusb.domain.MPrequalRegistration} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPrequalRegistrationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-prequal-registrations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPrequalRegistrationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter registrationStatus;

    private StringFilter reason;

    private ZonedDateTimeFilter answerDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter announcementId;

    private LongFilter prequalificationId;

    private LongFilter vendorId;

    public MPrequalRegistrationCriteria() {
    }

    public MPrequalRegistrationCriteria(MPrequalRegistrationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.registrationStatus = other.registrationStatus == null ? null : other.registrationStatus.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.answerDate = other.answerDate == null ? null : other.answerDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.announcementId = other.announcementId == null ? null : other.announcementId.copy();
        this.prequalificationId = other.prequalificationId == null ? null : other.prequalificationId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MPrequalRegistrationCriteria copy() {
        return new MPrequalRegistrationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(StringFilter registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public ZonedDateTimeFilter getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(ZonedDateTimeFilter answerDate) {
        this.answerDate = answerDate;
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

    public LongFilter getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(LongFilter announcementId) {
        this.announcementId = announcementId;
    }

    public LongFilter getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(LongFilter prequalificationId) {
        this.prequalificationId = prequalificationId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
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
        final MPrequalRegistrationCriteria that = (MPrequalRegistrationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registrationStatus, that.registrationStatus) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(answerDate, that.answerDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(announcementId, that.announcementId) &&
            Objects.equals(prequalificationId, that.prequalificationId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registrationStatus,
        reason,
        answerDate,
        uid,
        active,
        adOrganizationId,
        announcementId,
        prequalificationId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MPrequalRegistrationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registrationStatus != null ? "registrationStatus=" + registrationStatus + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (answerDate != null ? "answerDate=" + answerDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (announcementId != null ? "announcementId=" + announcementId + ", " : "") +
                (prequalificationId != null ? "prequalificationId=" + prequalificationId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}
