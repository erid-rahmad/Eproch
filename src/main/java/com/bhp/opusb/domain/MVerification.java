package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
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
            "}";
    }
}
