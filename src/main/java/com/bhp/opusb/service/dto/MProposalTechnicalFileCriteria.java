package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MProposalTechnicalFile} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MProposalTechnicalFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-proposal-technical-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MProposalTechnicalFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter cAttachmentId;

    private LongFilter adOrganizationId;

    private LongFilter biddingSubmissionId;

    private LongFilter biddingSubCriteriaId;

    public MProposalTechnicalFileCriteria() {
    }

    public MProposalTechnicalFileCriteria(MProposalTechnicalFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.cAttachmentId = other.cAttachmentId == null ? null : other.cAttachmentId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingSubmissionId = other.biddingSubmissionId == null ? null : other.biddingSubmissionId.copy();
        this.biddingSubCriteriaId = other.biddingSubCriteriaId == null ? null : other.biddingSubCriteriaId.copy();
    }

    @Override
    public MProposalTechnicalFileCriteria copy() {
        return new MProposalTechnicalFileCriteria(this);
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

    public LongFilter getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(LongFilter biddingSubmissionId) {
        this.biddingSubmissionId = biddingSubmissionId;
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
        final MProposalTechnicalFileCriteria that = (MProposalTechnicalFileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(cAttachmentId, that.cAttachmentId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingSubmissionId, that.biddingSubmissionId) &&
            Objects.equals(biddingSubCriteriaId, that.biddingSubCriteriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        cAttachmentId,
        adOrganizationId,
        biddingSubmissionId,
        biddingSubCriteriaId
        );
    }

    @Override
    public String toString() {
        return "MProposalTechnicalFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (cAttachmentId != null ? "cAttachmentId=" + cAttachmentId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingSubmissionId != null ? "biddingSubmissionId=" + biddingSubmissionId + ", " : "") +
                (biddingSubCriteriaId != null ? "biddingSubCriteriaId=" + biddingSubCriteriaId + ", " : "") +
            "}";
    }

}
