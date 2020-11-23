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
 * A MVerificationLine.
 */
@Entity
@Table(name = "m_verification_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVerificationLine extends AbstractAuditingEntity {

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

    @NotNull
    @Column(name = "total_lines", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalLines;

    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "line_no")
    private String lineNo;

    @Column(name = "conversion_rate")
    private String conversionRate;

    @Column(name = "foreign_actual", precision = 21, scale = 2)
    private BigDecimal foreignActual;

    @Column(name = "foreign_total_lines", precision = 21, scale = 2)
    private BigDecimal foreignTotalLines;

    @Column(name = "foreign_tax_amount", precision = 21, scale = 2)
    private BigDecimal foreignTaxAmount;

    @Column(name = "receive_date")
    private LocalDate receiveDate;

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
    private CElementValue cElement;

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

    public String getLineNo() {
        return lineNo;
    }

    public MVerificationLine lineNo(String lineNo) {
        this.lineNo = lineNo;
        return this;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getConversionRate() {
        return conversionRate;
    }

    public MVerificationLine conversionRate(String conversionRate) {
        this.conversionRate = conversionRate;
        return this;
    }

    public void setConversionRate(String conversionRate) {
        this.conversionRate = conversionRate;
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

    public CElementValue getCElement() {
        return cElement;
    }

    public MVerificationLine cElement(CElementValue cElementValue) {
        this.cElement = cElementValue;
        return this;
    }

    public void setCElement(CElementValue cElementValue) {
        this.cElement = cElementValue;
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
            ", totalLines=" + getTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", lineNo='" + getLineNo() + "'" +
            ", conversionRate='" + getConversionRate() + "'" +
            ", foreignActual=" + getForeignActual() +
            ", foreignTotalLines=" + getForeignTotalLines() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", receiveDate='" + getReceiveDate() + "'" +
            "}";
    }
}
