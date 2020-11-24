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

    @NotNull
    @Column(name = "verification_no", nullable = false)
    private String verificationNo;

    @NotNull
    @Column(name = "verification_date", nullable = false)
    private LocalDate verificationDate;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "invoice_no", nullable = false)
    private String invoiceNo;

    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "tax_invoice")
    private String taxInvoice;

    @Column(name = "tax_date")
    private LocalDate taxDate;

    @NotNull
    @Column(name = "total_lines", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalLines;

    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @NotNull
    @Column(name = "grand_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal grandTotal;

    @NotNull
    @Column(name = "verification_status", nullable = false)
    private String verificationStatus;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "foreign_grand_total", precision = 21, scale = 2)
    private BigDecimal foreignGrandTotal;

    @Column(name = "foreign_tax_amount", precision = 21, scale = 2)
    private BigDecimal foreignTaxAmount;

    @Column(name = "data_submit")
    private LocalDate dataSubmit;

    @Column(name = "date_acct")
    private LocalDate dateAcct;

    @Column(name = "withholding_amt", precision = 21, scale = 2)
    private BigDecimal withholdingAmt;

    @Column(name = "invoice_ap")
    private String invoiceAp;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "pay_date")
    private LocalDate payDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "pay_status")
    private String payStatus;

    @Column(name = "pay_amt", precision = 21, scale = 2)
    private BigDecimal payAmt;

    @Column(name = "date_reject")
    private LocalDate dateReject;

    @Column(name = "date_approve")
    private LocalDate dateApprove;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private CCurrency currency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerifications")
    private AdUser pic;

    @ManyToOne
    @JsonIgnoreProperties("mVerifications")
    private CVendor vendorTo;

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

    public LocalDate getDataSubmit() {
        return dataSubmit;
    }

    public MVerification dataSubmit(LocalDate dataSubmit) {
        this.dataSubmit = dataSubmit;
        return this;
    }

    public void setDataSubmit(LocalDate dataSubmit) {
        this.dataSubmit = dataSubmit;
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

    public AdUser getPic() {
        return pic;
    }

    public MVerification pic(AdUser adUser) {
        this.pic = adUser;
        return this;
    }

    public void setPic(AdUser adUser) {
        this.pic = adUser;
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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        this.uid = UUID.randomUUID();
        this.verificationDate = LocalDate.now();
        this.verificationNo = String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime());
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
            "}";
    }
}
