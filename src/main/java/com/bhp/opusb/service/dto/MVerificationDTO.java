package com.bhp.opusb.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVerification} entity.
 */
@ApiModel(description = "vendor Invoice Verification")
public class MVerificationDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String verificationNo;

    @NotNull
    private LocalDate verificationDate;

    private String description;

    @NotNull
    private String invoiceNo;

    @NotNull
    private LocalDate invoiceDate;

    private String taxInvoice;

    private LocalDate taxDate;

    @NotNull
    private BigDecimal totalLines;

    @NotNull
    private BigDecimal taxAmount;

    @NotNull
    private BigDecimal grandTotal;

    @NotNull
    private String verificationStatus;

    private UUID uid;

    private Boolean active;

    private BigDecimal foreignGrandTotal;

    private BigDecimal foreignTaxAmount;

    private LocalDate dataSubmit;

    private LocalDate dateAcct;

    private BigDecimal withholdingAmt;

    private String invoiceAp;

    private String docType;

    private LocalDate payDate;

    private LocalDate dueDate;

    private String payStatus;

    private BigDecimal payAmt;

    private LocalDate dateReject;

    private LocalDate dateApprove;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long currencyId;
    private String currencyName;

    private Long vendorId;
    private String vendorName;

    private Long picId;
    private Long picName;

    private Long vendorToId;
    private String vendorToName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerificationNo() {
        return verificationNo;
    }

    public void setVerificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
    }

    public LocalDate getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(LocalDate verificationDate) {
        this.verificationDate = verificationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTaxInvoice() {
        return taxInvoice;
    }

    public void setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    public LocalDate getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(LocalDate taxDate) {
        this.taxDate = taxDate;
    }

    public BigDecimal getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getForeignGrandTotal() {
        return foreignGrandTotal;
    }

    public void setForeignGrandTotal(BigDecimal foreignGrandTotal) {
        this.foreignGrandTotal = foreignGrandTotal;
    }

    public BigDecimal getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public LocalDate getDataSubmit() {
        return dataSubmit;
    }

    public void setDataSubmit(LocalDate dataSubmit) {
        this.dataSubmit = dataSubmit;
    }

    public LocalDate getDateAcct() {
        return dateAcct;
    }

    public void setDateAcct(LocalDate dateAcct) {
        this.dateAcct = dateAcct;
    }

    public BigDecimal getWithholdingAmt() {
        return withholdingAmt;
    }

    public void setWithholdingAmt(BigDecimal withholdingAmt) {
        this.withholdingAmt = withholdingAmt;
    }

    public String getInvoiceAp() {
        return invoiceAp;
    }

    public void setInvoiceAp(String invoiceAp) {
        this.invoiceAp = invoiceAp;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public LocalDate getDateReject() {
        return dateReject;
    }

    public void setDateReject(LocalDate dateReject) {
        this.dateReject = dateReject;
    }

    public LocalDate getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long adUserId) {
        this.picId = adUserId;
    }

    public Long getPicName() {
        return picName;
    }

    public void setPicName(Long picName) {
        this.picName = picName;
    }

    public Long getVendorToId() {
        return vendorToId;
    }

    public void setVendorToId(Long cVendorId) {
        this.vendorToId = cVendorId;
    }

    public String getVendorToName() {
        return vendorToName;
    }

    public void setVendorToName(String vendorToName) {
        this.vendorToName = vendorToName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVerificationDTO mVerificationDTO = (MVerificationDTO) o;
        if (mVerificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVerificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVerificationDTO{" +
            "id=" + getId() +
            ", verificationNo='" + getVerificationNo() + "'" +
            ", verificationDate='" + getVerificationDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", taxInvoice='" + getTaxInvoice() + "'" +
            ", taxDate='" + getTaxDate() + "'" +
            ", totalLines=" + getTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", grandTotal=" + getGrandTotal() +
            ", verificationStatus='" + getVerificationStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", foreignGrandTotal=" + getForeignGrandTotal() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", dataSubmit='" + getDataSubmit() + "'" +
            ", dateAcct='" + getDateAcct() + "'" +
            ", withholdingAmt=" + getWithholdingAmt() +
            ", invoiceAp='" + getInvoiceAp() + "'" +
            ", docType='" + getDocType() + "'" +
            ", payDate='" + getPayDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", payStatus='" + getPayStatus() + "'" +
            ", payAmt=" + getPayAmt() +
            ", dateReject='" + getDateReject() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName='" + getAdOrganizationName() + "'" +
            ", currencyId=" + getCurrencyId() +
            ", currencyName='" + getCurrencyName() + "'" +
            ", vendorId=" + getVendorId() +
            ", vendorName=" + getVendorName() +
            ", picId=" + getPicId() +
            ", picName=" + getPicName() +
            ", vendorToId=" + getVendorToId() +
            ", vendorToName=" + getVendorToName() +
            "}";
    }


}
