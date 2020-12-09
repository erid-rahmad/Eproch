package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVerification} entity.
 */
@ApiModel(description = "vendor Invoice Verification")
public class MVerificationDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * VHDOCM Invoice verification document no.
     */
    @NotNull
    @ApiModelProperty(value = "VHDOCM Invoice verification document no.", required = true)
    private String verificationNo;

    /**
     * VHTRDJ Transaction date
     */
    @NotNull
    @ApiModelProperty(value = "VHTRDJ Transaction date", required = true)
    private LocalDate verificationDate;

    private String description;

    /**
     * VHANUR
     */
    @Size(max = 20)
    @ApiModelProperty(value = "VHANUR")
    private String receiptNo;

    @NotNull
    private String invoiceNo;

    /**
     * VHDIVJ
     */
    @NotNull
    @ApiModelProperty(value = "VHDIVJ", required = true)
    private LocalDate invoiceDate;

    /**
     * VHTAX0
     */
    @ApiModelProperty(value = "VHTAX0")
    private String taxInvoice;

    /**
     * VHDSV Date service/tax.
     */
    @ApiModelProperty(value = "VHDSV Date service/tax.")
    private LocalDate taxDate;

    /**
     * VHAEXP Total amount of receipt lines.
     */
    @NotNull
    @ApiModelProperty(value = "VHAEXP Total amount of receipt lines.", required = true)
    private BigDecimal totalLines;

    /**
     * VHAREC Receipt amount (base currency).
     */
    @NotNull
    @ApiModelProperty(value = "VHAREC Receipt amount (base currency).", required = true)
    private BigDecimal grandTotal;

    /**
     * VHFREC Receipt amount in foreign currency.
     */
    @ApiModelProperty(value = "VHFREC Receipt amount in foreign currency.")
    private BigDecimal foreignGrandTotal;

    /**
     * VHSTAM Tax amount (base currency).
     */
    @NotNull
    @ApiModelProperty(value = "VHSTAM Tax amount (base currency).", required = true)
    private BigDecimal taxAmount;

    /**
     * VHCTAM Tax amount in foreign currency.
     */
    @ApiModelProperty(value = "VHCTAM Tax amount in foreign currency.")
    private BigDecimal foreignTaxAmount;

    /**
     * VHDTSUB
     */
    @ApiModelProperty(value = "VHDTSUB")
    private LocalDate dateSubmit;

    /**
     * VHDGJ GL date.
     */
    @ApiModelProperty(value = "VHDGJ GL date.")
    private LocalDate dateAcct;

    private BigDecimal withholdingAmt;

    private String invoiceAp;

    private String docType;

    /**
     * VHDMTJ Payment date.
     */
    @ApiModelProperty(value = "VHDMTJ Payment date.")
    private LocalDate payDate;

    /**
     * VHDDJ Promised date/payment schedule.
     */
    @ApiModelProperty(value = "VHDDJ Promised date/payment schedule.")
    private LocalDate dueDate;

    /**
     * VHAA Total actual amount.
     */
    @ApiModelProperty(value = "VHAA Total actual amount.")
    private BigDecimal payAmt;

    /**
     * VHRJDJ
     */
    @ApiModelProperty(value = "VHRJDJ")
    private LocalDate dateReject;

    /**
     * VHAPRD
     */
    @ApiModelProperty(value = "VHAPRD")
    private LocalDate dateApprove;

    @NotNull
    private String verificationStatus;

    private String payStatus;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationCode;
    private String adOrganizationName;

    /**
     * VHCRCD Invoice verification's currency
     */
    @ApiModelProperty(value = "VHCRCD Invoice verification's currency")

    private Long currencyId;
    private String currencyName;

    /**
     * VHCRCE Match PO's currency
     */
    @ApiModelProperty(value = "VHCRCE Match PO's currency")
    private Long matchPoCurrencyId;
    private String matchPoCurrencyName;
    /**
     * VHAN8 for supplier code.\nVHALPH for supplier name.
     */
    @ApiModelProperty(value = "VHAN8 for supplier code.\nVHALPH for supplier name.")

    private Long vendorId;
    private String vendorCode;
    private String vendorName;
    private String vendorTaxId;

    /**
     * VHAN8L
     */
    @ApiModelProperty(value = "VHAN8L")
    private Long vendorToId;
    private String vendorToCode;
    private String vendorToName;

    /**
     * VHEXR1 is mapped to tax category name.
     */
    @ApiModelProperty(value = "VHEXR1 is mapped to tax category name.")
    private Long cTaxCategoryId;

    @JsonProperty("cTaxCategoryName")
    private String cTaxCategoryName;

    /**
     * VHTXA1 is mapped to tax name.
     */
    @ApiModelProperty(value = "VHTXA1 is mapped to tax name.")
    private Long cTaxId;
    
    @JsonProperty("cTaxName")
    private String cTaxName;

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

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
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

    public int getTaxPeriod() {
        return taxDate.getMonth().getValue();
    }

    public BigDecimal getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimal getForeignGrandTotal() {
        return foreignGrandTotal;
    }

    public void setForeignGrandTotal(BigDecimal foreignGrandTotal) {
        this.foreignGrandTotal = foreignGrandTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public LocalDate getDateSubmit() {
        return dateSubmit;
    }

    public void setDateSubmit(LocalDate dateSubmit) {
        this.dateSubmit = dateSubmit;
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

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
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

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationCode() {
        return adOrganizationCode;
    }

    public void setAdOrganizationCode(String adOrganizationCode) {
        this.adOrganizationCode = adOrganizationCode;
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

    public Long getMatchPoCurrencyId() {
        return matchPoCurrencyId;
    }

    public void setMatchPoCurrencyId(Long cCurrencyId) {
        this.matchPoCurrencyId = cCurrencyId;
    }

    public String getMatchPoCurrencyName() {
        return matchPoCurrencyName;
    }

    public void setMatchPoCurrencyName(String matchPoCurrencyName) {
        this.matchPoCurrencyName = matchPoCurrencyName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorTaxId() {
        return vendorTaxId;
    }

    public void setVendorTaxId(String vendorTaxId) {
        this.vendorTaxId = vendorTaxId;
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

    public String getVendorToCode() {
        return vendorToCode;
    }

    public void setVendorToCode(String vendorToCode) {
        this.vendorToCode = vendorToCode;
    }

    public Long getCTaxCategoryId() {
        return cTaxCategoryId;
    }

    public void setCTaxCategoryId(Long cTaxCategoryId) {
        this.cTaxCategoryId = cTaxCategoryId;
    }

    public String getCTaxCategoryName() {
        return cTaxCategoryName;
    }

    public void setCTaxCategoryName(String cTaxCategoryName) {
        this.cTaxCategoryName = cTaxCategoryName;
    }

    public Long getCTaxId() {
        return cTaxId;
    }

    public void setCTaxId(Long cTaxId) {
        this.cTaxId = cTaxId;
    }

    public String getCTaxName() {
        return cTaxName;
    }

    public void setCTaxName(String cTaxName) {
        this.cTaxName = cTaxName;
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
            ", receiptNo='" + getReceiptNo() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", taxInvoice='" + getTaxInvoice() + "'" +
            ", taxDate='" + getTaxDate() + "'" +
            ", totalLines=" + getTotalLines() +
            ", grandTotal=" + getGrandTotal() +
            ", foreignGrandTotal=" + getForeignGrandTotal() +
            ", taxAmount=" + getTaxAmount() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", dateSubmit='" + getDateSubmit() + "'" +
            ", dateAcct='" + getDateAcct() + "'" +
            ", withholdingAmt=" + getWithholdingAmt() +
            ", invoiceAp='" + getInvoiceAp() + "'" +
            ", docType='" + getDocType() + "'" +
            ", payDate='" + getPayDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", payAmt=" + getPayAmt() +
            ", dateReject='" + getDateReject() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", verificationStatus='" + getVerificationStatus() + "'" +
            ", payStatus='" + getPayStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", currencyId=" + getCurrencyId() +
            ", matchPoCurrencyId=" + getMatchPoCurrencyId() +
            ", vendorId=" + getVendorId() +
            ", vendorToId=" + getVendorToId() +
            ", cTaxCategoryId=" + getCTaxCategoryId() +
            ", cTaxId=" + getCTaxId() +
            "}";
    }
}
