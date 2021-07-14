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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MProposalPrice} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MProposalPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-proposal-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MProposalPriceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BigDecimalFilter proposedPrice;

    private BigDecimalFilter ceilingPrice;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter biddingSubmissionId;

    private LongFilter adOrganizationId;

    private LongFilter attachmentId;

    public MProposalPriceCriteria() {
    }

    public MProposalPriceCriteria(MProposalPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.proposedPrice = other.proposedPrice == null ? null : other.proposedPrice.copy();
        this.ceilingPrice = other.ceilingPrice == null ? null : other.ceilingPrice.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.biddingSubmissionId = other.biddingSubmissionId == null ? null : other.biddingSubmissionId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.attachmentId = other.attachmentId == null ? null : other.attachmentId.copy();
    }

    @Override
    public MProposalPriceCriteria copy() {
        return new MProposalPriceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(StringFilter documentAction) {
        this.documentAction = documentAction;
    }

    public StringFilter getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(StringFilter documentStatus) {
        this.documentStatus = documentStatus;
    }

    public BigDecimalFilter getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimalFilter proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimalFilter getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimalFilter ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
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

    public LongFilter getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(LongFilter biddingSubmissionId) {
        this.biddingSubmissionId = biddingSubmissionId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
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
        final MProposalPriceCriteria that = (MProposalPriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(proposedPrice, that.proposedPrice) &&
            Objects.equals(ceilingPrice, that.ceilingPrice) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(biddingSubmissionId, that.biddingSubmissionId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentAction,
        documentStatus,
        proposedPrice,
        ceilingPrice,
        uid,
        active,
        biddingSubmissionId,
        adOrganizationId,
        attachmentId
        );
    }

    @Override
    public String toString() {
        return "MProposalPriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (proposedPrice != null ? "proposedPrice=" + proposedPrice + ", " : "") +
                (ceilingPrice != null ? "ceilingPrice=" + ceilingPrice + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (biddingSubmissionId != null ? "biddingSubmissionId=" + biddingSubmissionId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (attachmentId != null ? "attachmentId=" + attachmentId + ", " : "") +
            "}";
    }

}
