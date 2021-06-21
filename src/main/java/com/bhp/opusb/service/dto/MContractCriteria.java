package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MContract} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MContractResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-contracts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MContractCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter useBanCode;

    private StringFilter banCode;

    private StringFilter purpose;

    private BooleanFilter forPriceConfirmation;

    private LocalDateFilter startDate;

    private LocalDateFilter expirationDate;

    private StringFilter evaluationPeriod;

    private ZonedDateTimeFilter dateTrx;

    private StringFilter documentNo;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private ZonedDateTimeFilter dateApprove;

    private ZonedDateTimeFilter dateReject;

    private StringFilter rejectedReason;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter costCenterId;

    private LongFilter documentTypeId;

    private LongFilter picId;

    private LongFilter vendorId;

    private LongFilter vendorEvaluationId;

    public MContractCriteria() {
    }

    public MContractCriteria(MContractCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.useBanCode = other.useBanCode == null ? null : other.useBanCode.copy();
        this.banCode = other.banCode == null ? null : other.banCode.copy();
        this.purpose = other.purpose == null ? null : other.purpose.copy();
        this.forPriceConfirmation = other.forPriceConfirmation == null ? null : other.forPriceConfirmation.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.expirationDate = other.expirationDate == null ? null : other.expirationDate.copy();
        this.evaluationPeriod = other.evaluationPeriod == null ? null : other.evaluationPeriod.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.dateApprove = other.dateApprove == null ? null : other.dateApprove.copy();
        this.dateReject = other.dateReject == null ? null : other.dateReject.copy();
        this.rejectedReason = other.rejectedReason == null ? null : other.rejectedReason.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.picId = other.picId == null ? null : other.picId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.vendorEvaluationId = other.vendorEvaluationId == null ? null : other.vendorEvaluationId.copy();
    }

    @Override
    public MContractCriteria copy() {
        return new MContractCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getUseBanCode() {
        return useBanCode;
    }

    public void setUseBanCode(BooleanFilter useBanCode) {
        this.useBanCode = useBanCode;
    }

    public StringFilter getBanCode() {
        return banCode;
    }

    public void setBanCode(StringFilter banCode) {
        this.banCode = banCode;
    }

    public StringFilter getPurpose() {
        return purpose;
    }

    public void setPurpose(StringFilter purpose) {
        this.purpose = purpose;
    }

    public BooleanFilter getForPriceConfirmation() {
        return forPriceConfirmation;
    }

    public void setForPriceConfirmation(BooleanFilter forPriceConfirmation) {
        this.forPriceConfirmation = forPriceConfirmation;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateFilter expirationDate) {
        this.expirationDate = expirationDate;
    }

    public StringFilter getEvaluationPeriod() {
        return evaluationPeriod;
    }

    public void setEvaluationPeriod(StringFilter evaluationPeriod) {
        this.evaluationPeriod = evaluationPeriod;
    }

    public ZonedDateTimeFilter getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(ZonedDateTimeFilter dateTrx) {
        this.dateTrx = dateTrx;
    }

    public StringFilter getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(StringFilter documentNo) {
        this.documentNo = documentNo;
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

    public BooleanFilter getApproved() {
        return approved;
    }

    public void setApproved(BooleanFilter approved) {
        this.approved = approved;
    }

    public BooleanFilter getProcessed() {
        return processed;
    }

    public void setProcessed(BooleanFilter processed) {
        this.processed = processed;
    }

    public ZonedDateTimeFilter getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(ZonedDateTimeFilter dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTimeFilter getDateReject() {
        return dateReject;
    }

    public void setDateReject(ZonedDateTimeFilter dateReject) {
        this.dateReject = dateReject;
    }

    public StringFilter getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(StringFilter rejectedReason) {
        this.rejectedReason = rejectedReason;
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

    public LongFilter getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(LongFilter costCenterId) {
        this.costCenterId = costCenterId;
    }

    public LongFilter getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(LongFilter documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public LongFilter getPicId() {
        return picId;
    }

    public void setPicId(LongFilter picId) {
        this.picId = picId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getVendorEvaluationId() {
        return vendorEvaluationId;
    }

    public void setVendorEvaluationId(LongFilter vendorEvaluationId) {
        this.vendorEvaluationId = vendorEvaluationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MContractCriteria that = (MContractCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(useBanCode, that.useBanCode) &&
            Objects.equals(banCode, that.banCode) &&
            Objects.equals(purpose, that.purpose) &&
            Objects.equals(forPriceConfirmation, that.forPriceConfirmation) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(expirationDate, that.expirationDate) &&
            Objects.equals(evaluationPeriod, that.evaluationPeriod) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(dateApprove, that.dateApprove) &&
            Objects.equals(dateReject, that.dateReject) &&
            Objects.equals(rejectedReason, that.rejectedReason) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(costCenterId, that.costCenterId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(picId, that.picId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(vendorEvaluationId, that.vendorEvaluationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        useBanCode,
        banCode,
        purpose,
        forPriceConfirmation,
        startDate,
        expirationDate,
        evaluationPeriod,
        dateTrx,
        documentNo,
        documentAction,
        documentStatus,
        approved,
        processed,
        dateApprove,
        dateReject,
        rejectedReason,
        uid,
        active,
        adOrganizationId,
        costCenterId,
        documentTypeId,
        picId,
        vendorId,
        vendorEvaluationId
        );
    }

    @Override
    public String toString() {
        return "MContractCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (useBanCode != null ? "useBanCode=" + useBanCode + ", " : "") +
                (banCode != null ? "banCode=" + banCode + ", " : "") +
                (purpose != null ? "purpose=" + purpose + ", " : "") +
                (forPriceConfirmation != null ? "forPriceConfirmation=" + forPriceConfirmation + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (expirationDate != null ? "expirationDate=" + expirationDate + ", " : "") +
                (evaluationPeriod != null ? "evaluationPeriod=" + evaluationPeriod + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (dateApprove != null ? "dateApprove=" + dateApprove + ", " : "") +
                (dateReject != null ? "dateReject=" + dateReject + ", " : "") +
                (rejectedReason != null ? "rejectedReason=" + rejectedReason + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (picId != null ? "picId=" + picId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (vendorEvaluationId != null ? "vendorEvaluationId=" + vendorEvaluationId + ", " : "") +
            "}";
    }

}
