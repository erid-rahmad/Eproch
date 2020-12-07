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
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MVerification} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVerificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-verifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVerificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter verificationNo;

    private LocalDateFilter verificationDate;

    private StringFilter description;

    private StringFilter receiptNo;

    private StringFilter invoiceNo;

    private LocalDateFilter invoiceDate;

    private StringFilter taxInvoice;

    private LocalDateFilter taxDate;

    private BigDecimalFilter totalLines;

    private BigDecimalFilter grandTotal;

    private BigDecimalFilter foreignGrandTotal;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter foreignTaxAmount;

    private LocalDateFilter dateSubmit;

    private LocalDateFilter dateAcct;

    private BigDecimalFilter withholdingAmt;

    private StringFilter invoiceAp;

    private StringFilter docType;

    private LocalDateFilter payDate;

    private LocalDateFilter dueDate;

    private BigDecimalFilter payAmt;

    private LocalDateFilter dateReject;

    private LocalDateFilter dateApprove;

    private StringFilter verificationStatus;

    private StringFilter payStatus;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter currencyId;

    private LongFilter matchPoCurrencyId;

    private LongFilter vendorId;

    private LongFilter vendorToId;

    public MVerificationCriteria() {
    }

    public MVerificationCriteria(MVerificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.verificationNo = other.verificationNo == null ? null : other.verificationNo.copy();
        this.verificationDate = other.verificationDate == null ? null : other.verificationDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.receiptNo = other.receiptNo == null ? null : other.receiptNo.copy();
        this.invoiceNo = other.invoiceNo == null ? null : other.invoiceNo.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.taxInvoice = other.taxInvoice == null ? null : other.taxInvoice.copy();
        this.taxDate = other.taxDate == null ? null : other.taxDate.copy();
        this.totalLines = other.totalLines == null ? null : other.totalLines.copy();
        this.grandTotal = other.grandTotal == null ? null : other.grandTotal.copy();
        this.foreignGrandTotal = other.foreignGrandTotal == null ? null : other.foreignGrandTotal.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.foreignTaxAmount = other.foreignTaxAmount == null ? null : other.foreignTaxAmount.copy();
        this.dateSubmit = other.dateSubmit == null ? null : other.dateSubmit.copy();
        this.dateAcct = other.dateAcct == null ? null : other.dateAcct.copy();
        this.withholdingAmt = other.withholdingAmt == null ? null : other.withholdingAmt.copy();
        this.invoiceAp = other.invoiceAp == null ? null : other.invoiceAp.copy();
        this.docType = other.docType == null ? null : other.docType.copy();
        this.payDate = other.payDate == null ? null : other.payDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.payAmt = other.payAmt == null ? null : other.payAmt.copy();
        this.dateReject = other.dateReject == null ? null : other.dateReject.copy();
        this.dateApprove = other.dateApprove == null ? null : other.dateApprove.copy();
        this.verificationStatus = other.verificationStatus == null ? null : other.verificationStatus.copy();
        this.payStatus = other.payStatus == null ? null : other.payStatus.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.matchPoCurrencyId = other.matchPoCurrencyId == null ? null : other.matchPoCurrencyId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.vendorToId = other.vendorToId == null ? null : other.vendorToId.copy();
    }

    @Override
    public MVerificationCriteria copy() {
        return new MVerificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVerificationNo() {
        return verificationNo;
    }

    public void setVerificationNo(StringFilter verificationNo) {
        this.verificationNo = verificationNo;
    }

    public LocalDateFilter getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(LocalDateFilter verificationDate) {
        this.verificationDate = verificationDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(StringFilter receiptNo) {
        this.receiptNo = receiptNo;
    }

    public StringFilter getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(StringFilter invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDateFilter getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public StringFilter getTaxInvoice() {
        return taxInvoice;
    }

    public void setTaxInvoice(StringFilter taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    public LocalDateFilter getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(LocalDateFilter taxDate) {
        this.taxDate = taxDate;
    }

    public BigDecimalFilter getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(BigDecimalFilter totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimalFilter getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimalFilter grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimalFilter getForeignGrandTotal() {
        return foreignGrandTotal;
    }

    public void setForeignGrandTotal(BigDecimalFilter foreignGrandTotal) {
        this.foreignGrandTotal = foreignGrandTotal;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimalFilter getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(BigDecimalFilter foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public LocalDateFilter getDateSubmit() {
        return dateSubmit;
    }

    public void setDateSubmit(LocalDateFilter dateSubmit) {
        this.dateSubmit = dateSubmit;
    }

    public LocalDateFilter getDateAcct() {
        return dateAcct;
    }

    public void setDateAcct(LocalDateFilter dateAcct) {
        this.dateAcct = dateAcct;
    }

    public BigDecimalFilter getWithholdingAmt() {
        return withholdingAmt;
    }

    public void setWithholdingAmt(BigDecimalFilter withholdingAmt) {
        this.withholdingAmt = withholdingAmt;
    }

    public StringFilter getInvoiceAp() {
        return invoiceAp;
    }

    public void setInvoiceAp(StringFilter invoiceAp) {
        this.invoiceAp = invoiceAp;
    }

    public StringFilter getDocType() {
        return docType;
    }

    public void setDocType(StringFilter docType) {
        this.docType = docType;
    }

    public LocalDateFilter getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateFilter payDate) {
        this.payDate = payDate;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimalFilter getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimalFilter payAmt) {
        this.payAmt = payAmt;
    }

    public LocalDateFilter getDateReject() {
        return dateReject;
    }

    public void setDateReject(LocalDateFilter dateReject) {
        this.dateReject = dateReject;
    }

    public LocalDateFilter getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(LocalDateFilter dateApprove) {
        this.dateApprove = dateApprove;
    }

    public StringFilter getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(StringFilter verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public StringFilter getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(StringFilter payStatus) {
        this.payStatus = payStatus;
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

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }

    public LongFilter getMatchPoCurrencyId() {
        return matchPoCurrencyId;
    }

    public void setMatchPoCurrencyId(LongFilter matchPoCurrencyId) {
        this.matchPoCurrencyId = matchPoCurrencyId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getVendorToId() {
        return vendorToId;
    }

    public void setVendorToId(LongFilter vendorToId) {
        this.vendorToId = vendorToId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVerificationCriteria that = (MVerificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(verificationNo, that.verificationNo) &&
            Objects.equals(verificationDate, that.verificationDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(receiptNo, that.receiptNo) &&
            Objects.equals(invoiceNo, that.invoiceNo) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(taxInvoice, that.taxInvoice) &&
            Objects.equals(taxDate, that.taxDate) &&
            Objects.equals(totalLines, that.totalLines) &&
            Objects.equals(grandTotal, that.grandTotal) &&
            Objects.equals(foreignGrandTotal, that.foreignGrandTotal) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(foreignTaxAmount, that.foreignTaxAmount) &&
            Objects.equals(dateSubmit, that.dateSubmit) &&
            Objects.equals(dateAcct, that.dateAcct) &&
            Objects.equals(withholdingAmt, that.withholdingAmt) &&
            Objects.equals(invoiceAp, that.invoiceAp) &&
            Objects.equals(docType, that.docType) &&
            Objects.equals(payDate, that.payDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(payAmt, that.payAmt) &&
            Objects.equals(dateReject, that.dateReject) &&
            Objects.equals(dateApprove, that.dateApprove) &&
            Objects.equals(verificationStatus, that.verificationStatus) &&
            Objects.equals(payStatus, that.payStatus) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(matchPoCurrencyId, that.matchPoCurrencyId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(vendorToId, that.vendorToId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        verificationNo,
        verificationDate,
        description,
        receiptNo,
        invoiceNo,
        invoiceDate,
        taxInvoice,
        taxDate,
        totalLines,
        grandTotal,
        foreignGrandTotal,
        taxAmount,
        foreignTaxAmount,
        dateSubmit,
        dateAcct,
        withholdingAmt,
        invoiceAp,
        docType,
        payDate,
        dueDate,
        payAmt,
        dateReject,
        dateApprove,
        verificationStatus,
        payStatus,
        uid,
        active,
        adOrganizationId,
        currencyId,
        matchPoCurrencyId,
        vendorId,
        vendorToId
        );
    }

    @Override
    public String toString() {
        return "MVerificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (verificationNo != null ? "verificationNo=" + verificationNo + ", " : "") +
                (verificationDate != null ? "verificationDate=" + verificationDate + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (receiptNo != null ? "receiptNo=" + receiptNo + ", " : "") +
                (invoiceNo != null ? "invoiceNo=" + invoiceNo + ", " : "") +
                (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
                (taxInvoice != null ? "taxInvoice=" + taxInvoice + ", " : "") +
                (taxDate != null ? "taxDate=" + taxDate + ", " : "") +
                (totalLines != null ? "totalLines=" + totalLines + ", " : "") +
                (grandTotal != null ? "grandTotal=" + grandTotal + ", " : "") +
                (foreignGrandTotal != null ? "foreignGrandTotal=" + foreignGrandTotal + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (foreignTaxAmount != null ? "foreignTaxAmount=" + foreignTaxAmount + ", " : "") +
                (dateSubmit != null ? "dateSubmit=" + dateSubmit + ", " : "") +
                (dateAcct != null ? "dateAcct=" + dateAcct + ", " : "") +
                (withholdingAmt != null ? "withholdingAmt=" + withholdingAmt + ", " : "") +
                (invoiceAp != null ? "invoiceAp=" + invoiceAp + ", " : "") +
                (docType != null ? "docType=" + docType + ", " : "") +
                (payDate != null ? "payDate=" + payDate + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (payAmt != null ? "payAmt=" + payAmt + ", " : "") +
                (dateReject != null ? "dateReject=" + dateReject + ", " : "") +
                (dateApprove != null ? "dateApprove=" + dateApprove + ", " : "") +
                (verificationStatus != null ? "verificationStatus=" + verificationStatus + ", " : "") +
                (payStatus != null ? "payStatus=" + payStatus + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (matchPoCurrencyId != null ? "matchPoCurrencyId=" + matchPoCurrencyId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (vendorToId != null ? "vendorToId=" + vendorToId + ", " : "") +
            "}";
    }

}
