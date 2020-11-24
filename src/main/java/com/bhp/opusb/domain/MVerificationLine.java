package com.bhp.opusb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
 * A MVerificationLine.
 */
@Entity
@Table(name = "m_verification_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVerificationLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "verification_no")
    private String verificationNo;

    @NotNull
    @Column(name = "po_no", nullable = false)
    private String poNo;

    @NotNull
    @Column(name = "receive_no", nullable = false)
    private String receiveNo;

    @NotNull
    @Column(name = "delivery_no", nullable = false)
    private String deliveryNo;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "qty", nullable = false)
    private Long qty;

    @NotNull
    @Column(name = "price_actual", precision = 21, scale = 2, nullable = false)
    private BigDecimal priceActual;

    @Column(name = "foreign_actual", precision = 21, scale = 2)
    private BigDecimal foreignActual;

    @NotNull
    @Column(name = "total_lines", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalLines;

    @Column(name = "foreign_total_lines", precision = 21, scale = 2)
    private BigDecimal foreignTotalLines;

    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @Column(name = "foreign_tax_amount", precision = 21, scale = 2)
    private BigDecimal foreignTaxAmount;

    @Column(name = "line_no")
    private Integer lineNo;

    @Column(name = "line_no_mr")
    private Integer lineNoMr;

    @Column(name = "conversion_rate", precision = 21, scale = 2)
    private BigDecimal conversionRate;

    @Column(name = "receive_date")
    private LocalDate receiveDate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private MVerification verification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private CProduct product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private CUnitOfMeasure uom;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private CCostCenter cCostCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private CCurrency cCurrency;

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

    public MVerificationLine verificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
        return this;
    }

    public void setVerificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public MVerificationLine poNo(String poNo) {
        this.poNo = poNo;
        return this;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public MVerificationLine receiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
        return this;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public MVerificationLine deliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
        return this;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getDescription() {
        return description;
    }

    public MVerificationLine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQty() {
        return qty;
    }

    public MVerificationLine qty(Long qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getPriceActual() {
        return priceActual;
    }

    public MVerificationLine priceActual(BigDecimal priceActual) {
        this.priceActual = priceActual;
        return this;
    }

    public void setPriceActual(BigDecimal priceActual) {
        this.priceActual = priceActual;
    }

    public BigDecimal getForeignActual() {
        return foreignActual;
    }

    public MVerificationLine foreignActual(BigDecimal foreignActual) {
        this.foreignActual = foreignActual;
        return this;
    }

    public void setForeignActual(BigDecimal foreignActual) {
        this.foreignActual = foreignActual;
    }

    public BigDecimal getTotalLines() {
        return totalLines;
    }

    public MVerificationLine totalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
        return this;
    }

    public void setTotalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimal getForeignTotalLines() {
        return foreignTotalLines;
    }

    public MVerificationLine foreignTotalLines(BigDecimal foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
        return this;
    }

    public void setForeignTotalLines(BigDecimal foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public MVerificationLine taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public MVerificationLine foreignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
        return this;
    }

    public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public MVerificationLine lineNo(Integer lineNo) {
        this.lineNo = lineNo;
        return this;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public Integer getLineNoMr() {
        return lineNoMr;
    }

    public MVerificationLine lineNoMr(Integer lineNoMr) {
        this.lineNoMr = lineNoMr;
        return this;
    }

    public void setLineNoMr(Integer lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public MVerificationLine conversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
        return this;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public MVerificationLine receiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
        return this;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public UUID getUid() {
        return uid;
    }

    public MVerificationLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVerificationLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MVerification getVerification() {
        return verification;
    }

    public MVerificationLine verification(MVerification mVerification) {
        this.verification = mVerification;
        return this;
    }

    public void setVerification(MVerification mVerification) {
        this.verification = mVerification;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVerificationLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProduct getProduct() {
        return product;
    }

    public MVerificationLine product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MVerificationLine uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }

    public CCostCenter getCCostCenter() {
        return cCostCenter;
    }

    public MVerificationLine cCostCenter(CCostCenter cCostCenter) {
        this.cCostCenter = cCostCenter;
        return this;
    }

    public void setCCostCenter(CCostCenter cCostCenter) {
        this.cCostCenter = cCostCenter;
    }

    public CCurrency getCCurrency() {
        return cCurrency;
    }

    public MVerificationLine cCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
        return this;
    }

    public void setCCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
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
        if (!(o instanceof MVerificationLine)) {
            return false;
        }
        return id != null && id.equals(((MVerificationLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVerificationLine{" +
            "id=" + getId() +
            ", verificationNo='" + getVerificationNo() + "'" +
            ", poNo='" + getPoNo() + "'" +
            ", receiveNo='" + getReceiveNo() + "'" +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", description='" + getDescription() + "'" +
            ", qty=" + getQty() +
            ", priceActual=" + getPriceActual() +
            ", foreignActual=" + getForeignActual() +
            ", totalLines=" + getTotalLines() +
            ", foreignTotalLines=" + getForeignTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", lineNo=" + getLineNo() +
            ", lineNoMr=" + getLineNoMr() +
            ", conversionRate=" + getConversionRate() +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
