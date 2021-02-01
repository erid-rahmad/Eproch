package com.bhp.opusb.domain;

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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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

    /**
     * VDDOCM
     */
    @Column(name = "verification_no")
    private String verificationNo;

    /**
     * VDMATC
     */
    @Size(max = 1)
    @Column(name = "match_type", length = 1)
    private String matchType;

    /**
     * VDDOCO
     */
    @NotNull
    @Column(name = "po_no", nullable = false)
    private String poNo;

    @NotNull
    @Column(name = "receive_no", nullable = false)
    private String receiveNo;

    @NotNull
    @Column(name = "delivery_no", nullable = false)
    private String deliveryNo;

    /**
     * VDVRMK Supplier's remark.
     */
    @Column(name = "description")
    private String description;

    /**
     * VDSFXO
     */
    @Size(max = 10)
    @Column(name = "order_suffix", length = 10)
    private String orderSuffix;

    /**
     * VDUREC
     */
    @NotNull
    @Column(name = "qty", nullable = false)
    private Long qty;

    /**
     * VDPRRC Unit price.
     */
    @NotNull
    @Column(name = "price_actual", precision = 21, scale = 2, nullable = false)
    private BigDecimal priceActual;

    /**
     * VDFRRC Unit price in foreign currency.
     */
    @Column(name = "foreign_actual", precision = 21, scale = 2)
    private BigDecimal foreignActual;

    /**
     * VDAREC Receipt amount.
     */
    @NotNull
    @Column(name = "total_lines", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalLines;

    /**
     * VDFREC Receipt amount in foreign currency.
     */
    @Column(name = "foreign_total_lines", precision = 21, scale = 2)
    private BigDecimal foreignTotalLines;

    /**
     * VDSTAM Tax amount.
     */
    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    /**
     * VDCTAM Tax amount in foreign currency.
     */
    @Column(name = "foreign_tax_amount", precision = 21, scale = 2)
    private BigDecimal foreignTaxAmount;

    /**
     * VDLINN Invoice verification line no.
     */
    @Column(name = "line_no")
    private Integer lineNo;

    /**
     * VDLNID PO line no.
     */
    @Column(name = "line_no_po")
    private Integer lineNoPo;

    /**
     * VDNLIN Receipt line no.
     */
    @Column(name = "line_no_mr")
    private Integer lineNoMr;

    /**
     * PRLITM
     */
    @Size(max = 25)
    @Column(name = "item_desc_1", length = 25)
    private String itemDesc1;

    /**
     * PRAITM
     */
    @Size(max = 25)
    @Column(name = "item_desc_2", length = 25)
    private String itemDesc2;

    /**
     * VDCRR
     */
    @Column(name = "conversion_rate", precision = 21, scale = 2)
    private BigDecimal conversionRate;

    /**
     * VDRCDJ Receipt date.
     */
    @Column(name = "receive_date")
    private LocalDate receiveDate;

    /**
     * VDPST
     */
    @Column(name = "pay_stat")
    private String payStat;

    /**
     * VDTX Y means true, otherwise false
     */
    @Column(name = "taxable")
    private Boolean taxable;

    /**
     * VDDCTO
     */
    @Size(max = 2)
    @Column(name = "c_doc_type", length = 2)
    private String cDocType;

    /**
     * VDDCT
     */
    @Size(max = 2)
    @Column(name = "c_doc_type_mr", length = 2)
    private String cDocTypeMr;

    /**
     * Whether or not the respective receipt line is reversed.
     */
    @Column(name = "receipt_reversed")
    private Boolean receiptReversed;

    /**
     * Whether or not the respective AP invoice is reversed.
     */
    @Column(name = "ap_reversed")
    private Boolean apReversed;

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

    /**
     * VDUOM
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private CUnitOfMeasure uom;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private CCostCenter cCostCenter;

    /**
     * VDCRCD
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationLines")
    private CCurrency cCurrency;

    /**
     * VDEXR1 is mapped to tax category name.
     */
    @ManyToOne
    @JsonIgnoreProperties("mVerificationLines")
    private CTaxCategory cTaxCategory;

    /**
     * VDTXA1 is mapped to tax name.
     */
    @ManyToOne
    @JsonIgnoreProperties("mVerificationLines")
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

    public MVerificationLine verificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
        return this;
    }

    public void setVerificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
    }

    public String getMatchType() {
        return matchType;
    }

    public MVerificationLine matchType(String matchType) {
        this.matchType = matchType;
        return this;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
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

    public String getOrderSuffix() {
        return orderSuffix;
    }

    public MVerificationLine orderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
        return this;
    }

    public void setOrderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
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

    public Integer getLineNoPo() {
        return lineNoPo;
    }

    public MVerificationLine lineNoPo(Integer lineNoPo) {
        this.lineNoPo = lineNoPo;
        return this;
    }

    public void setLineNoPo(Integer lineNoPo) {
        this.lineNoPo = lineNoPo;
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

    public String getItemDesc1() {
        return itemDesc1;
    }

    public MVerificationLine itemDesc1(String itemDesc1) {
        this.itemDesc1 = itemDesc1;
        return this;
    }

    public void setItemDesc1(String itemDesc1) {
        this.itemDesc1 = itemDesc1;
    }

    public String getItemDesc2() {
        return itemDesc2;
    }

    public MVerificationLine itemDesc2(String itemDesc2) {
        this.itemDesc2 = itemDesc2;
        return this;
    }

    public void setItemDesc2(String itemDesc2) {
        this.itemDesc2 = itemDesc2;
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

    public String getPayStat() {
        return payStat;
    }

    public MVerificationLine payStat(String payStat) {
        this.payStat = payStat;
        return this;
    }

    public void setPayStat(String payStat) {
        this.payStat = payStat;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public MVerificationLine taxable(Boolean taxable) {
        this.taxable = taxable;
        return this;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public String getcDocType() {
        return cDocType;
    }

    public MVerificationLine cDocType(String cDocType) {
        this.cDocType = cDocType;
        return this;
    }

    public void setcDocType(String cDocType) {
        this.cDocType = cDocType;
    }

    public String getcDocTypeMr() {
        return cDocTypeMr;
    }

    public MVerificationLine cDocTypeMr(String cDocTypeMr) {
        this.cDocTypeMr = cDocTypeMr;
        return this;
    }

    public void setcDocTypeMr(String cDocTypeMr) {
        this.cDocTypeMr = cDocTypeMr;
    }

    public Boolean isReceiptReversed() {
        return receiptReversed;
    }

    public MVerificationLine receiptReversed(Boolean receiptReversed) {
        this.receiptReversed = receiptReversed;
        return this;
    }

    public void setReceiptReversed(Boolean receiptReversed) {
        this.receiptReversed = receiptReversed;
    }

    public Boolean isApReversed() {
        return apReversed;
    }

    public MVerificationLine apReversed(Boolean apReversed) {
        this.apReversed = apReversed;
        return this;
    }

    public void setApReversed(Boolean apReversed) {
        this.apReversed = apReversed;
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

    public CTaxCategory getCTaxCategory() {
        return cTaxCategory;
    }

    public MVerificationLine cTaxCategory(CTaxCategory cTaxCategory) {
        this.cTaxCategory = cTaxCategory;
        return this;
    }

    public void setCTaxCategory(CTaxCategory cTaxCategory) {
        this.cTaxCategory = cTaxCategory;
    }

    public CTax getCTax() {
        return cTax;
    }

    public MVerificationLine cTax(CTax cTax) {
        this.cTax = cTax;
        return this;
    }

    public void setCTax(CTax cTax) {
        this.cTax = cTax;
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
            ", matchType='" + getMatchType() + "'" +
            ", poNo='" + getPoNo() + "'" +
            ", receiveNo='" + getReceiveNo() + "'" +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", description='" + getDescription() + "'" +
            ", orderSuffix='" + getOrderSuffix() + "'" +
            ", qty=" + getQty() +
            ", priceActual=" + getPriceActual() +
            ", foreignActual=" + getForeignActual() +
            ", totalLines=" + getTotalLines() +
            ", foreignTotalLines=" + getForeignTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", lineNo=" + getLineNo() +
            ", lineNoPo=" + getLineNoPo() +
            ", lineNoMr=" + getLineNoMr() +
            ", itemDesc1='" + getItemDesc1() + "'" +
            ", itemDesc2='" + getItemDesc2() + "'" +
            ", conversionRate=" + getConversionRate() +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", payStat='" + getPayStat() + "'" +
            ", taxable='" + isTaxable() + "'" +
            ", cDocType='" + getcDocType() + "'" +
            ", cDocTypeMr='" + getcDocTypeMr() + "'" +
            ", receiptReversed='" + isReceiptReversed() + "'" +
            ", apReversed='" + isApReversed() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
