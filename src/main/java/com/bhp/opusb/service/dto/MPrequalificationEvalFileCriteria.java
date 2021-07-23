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

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MPrequalificationEvalFile} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPrequalificationEvalFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-prequalification-eval-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPrequalificationEvalFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter attachmentId;

    private LongFilter adOrganizationId;

    private LongFilter prequalificationSubmissionId;

    private LongFilter biddingSubCriteriaId;

    public MPrequalificationEvalFileCriteria() {
    }

    public MPrequalificationEvalFileCriteria(MPrequalificationEvalFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.attachmentId = other.attachmentId == null ? null : other.attachmentId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.prequalificationSubmissionId = other.prequalificationSubmissionId == null ? null : other.prequalificationSubmissionId.copy();
        this.biddingSubCriteriaId = other.biddingSubCriteriaId == null ? null : other.biddingSubCriteriaId.copy();
    }

    @Override
    public MPrequalificationEvalFileCriteria copy() {
        return new MPrequalificationEvalFileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(LongFilter attachmentId) {
        this.attachmentId = attachmentId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getPrequalificationSubmissionId() {
        return prequalificationSubmissionId;
    }

    public void setPrequalificationSubmissionId(LongFilter prequalificationSubmissionId) {
        this.prequalificationSubmissionId = prequalificationSubmissionId;
    }

    public LongFilter getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(LongFilter biddingSubCriteriaId) {
        this.biddingSubCriteriaId = biddingSubCriteriaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MPrequalificationEvalFileCriteria that = (MPrequalificationEvalFileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(attachmentId, that.attachmentId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(prequalificationSubmissionId, that.prequalificationSubmissionId) &&
            Objects.equals(biddingSubCriteriaId, that.biddingSubCriteriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        attachmentId,
        adOrganizationId,
        prequalificationSubmissionId,
        biddingSubCriteriaId
        );
    }

    @Override
    public String toString() {
        return "MPrequalificationEvalFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (attachmentId != null ? "attachmentId=" + attachmentId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (prequalificationSubmissionId != null ? "prequalificationSubmissionId=" + prequalificationSubmissionId + ", " : "") +
                (biddingSubCriteriaId != null ? "biddingSubCriteriaId=" + biddingSubCriteriaId + ", " : "") +
            "}";
    }

}
