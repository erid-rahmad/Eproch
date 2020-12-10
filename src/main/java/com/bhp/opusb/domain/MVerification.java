package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * vendor Invoice Verification
 */
@Entity
@Table(name = "m_verification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVerification extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * VHDOCM Invoice verification document no.
     */
    @NotNull
    @Column(name = "verification_no", nullable = false)
    private String verificationNo;

    /**
     * VHTRDJ Transaction date
     */
    @NotNull
    @Column(name = "verification_date", nullable = false)
    private LocalDate verificationDate;

    @Column(name = "description")
    private String description;

    /**
     * VHANUR
     */
    @Size(max = 20)
    @Column(name = "receipt_no", length = 20)
    private String receiptNo;

    @NotNull
    @Column(name = "invoice_no", nullable = false)
    private String invoiceNo;

    /**
     * VHDIVJ
     */
    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    /**
     * VHTAX0
     */
    @Column(name = "tax_invoice")
    private String taxInvoice;

    /**
     * VHDSV Date service/tax.
     */
    @Column(name = "tax_date")
    private LocalDate taxDate;

    /**
     * VHAEXP Total amount of receipt lines.
     */
    @NotNull
    @Column(name = "total_lines", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalLines;

    /**
     * VHAREC Receipt amount (base currency).
     */
    @NotNull
    @Column(name = "grand_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal grandTotal;

    /**
     * VHFREC Receipt amount in foreign currency.
     */
    @Column(name = "foreign_grand_total", precision = 21, scale = 2)
    private BigDecimal foreignGrandTotal;

    /**
     * VHSTAM Tax amount (base currency).
     */
    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    /**
     * VHCTAM Tax amount in foreign currency.
     */
    @Column(name = "foreign_tax_amount", precision = 21, scale = 2)
    private BigDecimal foreignTaxAmount;

    /**
     * VHDTSUB
     */
    @Column(name = "date_submit")
    private LocalDate dateSubmit;

    /**
     * VHDGJ GL date.
     */
    @Column(name = "date_acct")
    private LocalDate dateAcct;

    @Column(name = "withholding_amt", precision = 21, scale = 2)
    private BigDecimal withholdingAmt;

    @Column(name = "invoice_ap")
    private String invoiceAp;

    @Column(name = "doc_type")
    private String docType;

    /**
     * VHDMTJ Payment date.
     */
    @Column(name = "pay_date")
    private LocalDate payDate;

    /**
     * VHDDJ Promised date/payment schedule.
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * VHAA Total actual amount.
     */
    @Column(name = "pay_amt", precision = 21, scale = 2)
    private BigDecimal payAmt;

    /**
     * VHRJDJ
     */
    @Column(name = "date_reject")
    private LocalDate dateReject;

    /**
     * VHAPRD
     */
    @Column(name = "date_approve")
    private LocalDate dateApprove;

    @NotNull
    @Column(name = "verification_status", nullable = false)
    private String verificationStatus;

    @Column(name = "pay_status")
    private String payStatus;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private ADOrganization adOrganization;

    /**
     * VHCRCD Invoice verification's currency
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private CCurrency currency;

    /**
     * VHCRCE Match PO's currency
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private CCurrency matchPoCurrency;

    /**
     * VHAN8 for supplier code.
     * VHALPH for supplier name.
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private CVendor vendor;

    /**
     * VHAN8L
     */
    @ManyToOne
    @JsonIgnoreProperties("mVerifications")
    private CVendor vendorTo;

    /**
     * VHEXR1 is mapped to tax category name.
     */
    @ManyToOne
    @JsonIgnoreProperties("mVerifications")
    private CTaxCategory cTaxCategory;

    /**
     * VHTXA1 is mapped to tax name.
     */
    @ManyToOne
    @JsonIgnoreProperties("mVerifications")
    private CTax cTax;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerificationNo() {
        return verificationNo;
    }

    public MVerification verificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
        return this;
    }

    public void setVerificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
    }

    public LocalDate getVerificationDate() {
        return verificationDate;
    }

    public MVerification verificationDate(LocalDate verificationDate) {
        this.verificationDate = verificationDate;
        return this;
    }

    public void setVerificationDate(LocalDate verificationDate) {
        this.verificationDate = verificationDate;
    }

    public String getDescription() {
        return description;
    }

    public MVerification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public MVerification receiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
        return this;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public MVerification invoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public MVerification invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTaxInvoice() {
        return taxInvoice;
    }

    public MVerification taxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
        return this;
    }

    public void setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    public LocalDate getTaxDate() {
        return taxDate;
    }

    public MVerification taxDate(LocalDate taxDate) {
        this.taxDate = taxDate;
        return this;
    }

    public void setTaxDate(LocalDate taxDate) {
        this.taxDate = taxDate;
    }

    public BigDecimal getTotalLines() {
        return totalLines;
    }

    public MVerification totalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
        return this;
    }

    public void setTotalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public MVerification grandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
        return this;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimal getForeignGrandTotal() {
        return foreignGrandTotal;
    }

    public MVerification foreignGrandTotal(BigDecimal foreignGrandTotal) {
        this.foreignGrandTotal = foreignGrandTotal;
        return this;
    }

    public void setForeignGrandTotal(BigDecimal foreignGrandTotal) {
        this.foreignGrandTotal = foreignGrandTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public MVerification taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public MVerification foreignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
        return this;
    }

    public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public LocalDate getDateSubmit() {
        return dateSubmit;
    }

    public MVerification dateSubmit(LocalDate dateSubmit) {
        this.dateSubmit = dateSubmit;
        return this;
    }

    public void setDateSubmit(LocalDate dateSubmit) {
        this.dateSubmit = dateSubmit;
    }

    public LocalDate getDateAcct() {
        return dateAcct;
    }

    public MVerification dateAcct(LocalDate dateAcct) {
        this.dateAcct = dateAcct;
        return this;
    }

    public void setDateAcct(LocalDate dateAcct) {
        this.dateAcct = dateAcct;
    }

    public BigDecimal getWithholdingAmt() {
        return withholdingAmt;
    }

    public MVerification withholdingAmt(BigDecimal withholdingAmt) {
        this.withholdingAmt = withholdingAmt;
        return this;
    }

    public void setWithholdingAmt(BigDecimal withholdingAmt) {
        this.withholdingAmt = withholdingAmt;
    }

    public String getInvoiceAp() {
        return invoiceAp;
    }

    public MVerification invoiceAp(String invoiceAp) {
        this.invoiceAp = invoiceAp;
        return this;
    }

    public void setInvoiceAp(String invoiceAp) {
        this.invoiceAp = invoiceAp;
    }

    public String getDocType() {
        return docType;
    }

    public MVerification docType(String docType) {
        this.docType = docType;
        return this;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public MVerification payDate(LocalDate payDate) {
        this.payDate = payDate;
        return this;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public MVerification dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public MVerification payAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
        return this;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public LocalDate getDateReject() {
        return dateReject;
    }

    public MVerification dateReject(LocalDate dateReject) {
        this.dateReject = dateReject;
        return this;
    }

    public void setDateReject(LocalDate dateReject) {
        this.dateReject = dateReject;
    }

    public LocalDate getDateApprove() {
        return dateApprove;
    }

    public MVerification dateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
        return this;
    }

    public void setDateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public MVerification verificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
        return this;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public MVerification payStatus(String payStatus) {
        this.payStatus = payStatus;
        return this;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public UUID getUid() {
        return uid;
    }

    public MVerification uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVerification active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVerification adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public MVerification currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CCurrency getMatchPoCurrency() {
        return matchPoCurrency;
    }

    public MVerification matchPoCurrency(CCurrency cCurrency) {
        this.matchPoCurrency = cCurrency;
        return this;
    }

    public void setMatchPoCurrency(CCurrency cCurrency) {
        this.matchPoCurrency = cCurrency;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MVerification vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CVendor getVendorTo() {
        return vendorTo;
    }

    public MVerification vendorTo(CVendor cVendor) {
        this.vendorTo = cVendor;
        return this;
    }

    public void setVendorTo(CVendor cVendor) {
        this.vendorTo = cVendor;
    }

    public CTaxCategory getCTaxCategory() {
        return cTaxCategory;
    }

    public MVerification cTaxCategory(CTaxCategory cTaxCategory) {
        this.cTaxCategory = cTaxCategory;
        return this;
    }

    public void setCTaxCategory(CTaxCategory cTaxCategory) {
        this.cTaxCategory = cTaxCategory;
    }

    public CTax getCTax() {
        return cTax;
    }

    public MVerification cTax(CTax cTax) {
        this.cTax = cTax;
        return this;
    }

    public void setCTax(CTax cTax) {
        this.cTax = cTax;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        this.uid = UUID.randomUUID();
        this.verificationDate = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVerification)) {
            return false;
        }
        return id != null && id.equals(((MVerification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVerification{" +
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
            "}";
    }
}
