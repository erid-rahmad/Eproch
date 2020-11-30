package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MMatchPO.
 */
@Entity
@Table(name = "m_match_po")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MMatchPO extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * PRSHPN
     */
    @Size(max = 20)
    @Column(name = "delivery_no", length = 20)
    private String deliveryNo;

    /**
     * PRDCTO
     */
    @Size(max = 2)
    @Column(name = "c_doc_type", length = 2)
    private String cDocType;

    /**
     * PRDOCO
     */
    @Size(max = 20)
    @Column(name = "po_no", length = 20)
    private String poNo;

    /**
     * PRTRDJ
     */
    @Column(name = "po_date")
    private LocalDate poDate;

    /**
     * PRDOC
     */
    @Size(max = 20)
    @Column(name = "receipt_no", length = 20)
    private String receiptNo;

    /**
     * PRRCDJ
     */
    @Column(name = "receipt_date")
    private LocalDate receiptDate;

    /**
     * PRUREC for Receipt Quantity
     */
    @Column(name = "qty", precision = 21, scale = 2)
    private BigDecimal qty;

    /**
     * PRCRR
     */
    @Column(name = "c_conversion_rate", precision = 21, scale = 2)
    private BigDecimal cConversionRate;

    /**
     * PRUOPN for Open Quantity
     */
    @Column(name = "open_qty", precision = 21, scale = 2)
    private BigDecimal openQty;

    /**
     * PRPRRC for Unit Price
     */
    @Column(name = "price_actual", precision = 21, scale = 2)
    private BigDecimal priceActual;

    /**
     * PRFRRC for Unit Price in foreign currency
     */
    @Column(name = "foreign_actual", precision = 21, scale = 2)
    private BigDecimal foreignActual;

    /**
     * PRAOPN for Open Amount
     */
    @Column(name = "open_amount", precision = 21, scale = 2)
    private BigDecimal openAmount;

    /**
     * PRFAP for Open Amount in foreign currency
     */
    @Column(name = "open_foreign_amount", precision = 21, scale = 2)
    private BigDecimal openForeignAmount;

    /**
     * PRAREC for Total Line Amount
     */
    @Column(name = "total_lines", precision = 21, scale = 2)
    private BigDecimal totalLines;

    /**
     * PRFREC for Total Line Amount in foreign currency
     */
    @Column(name = "foreign_total_lines", precision = 21, scale = 2)
    private BigDecimal foreignTotalLines;

    /**
     * PRSTAM
     */
    @Column(name = "tax_amount", precision = 21, scale = 2)
    private BigDecimal taxAmount;

    /**
     * PRCTAM
     */
    @Column(name = "foreign_tax_amount", precision = 21, scale = 2)
    private BigDecimal foreignTaxAmount;

    /**
     * PRDGL for GL date
     */
    @Column(name = "date_account")
    private LocalDate dateAccount;

    /**
     * PRDCT
     */
    @Size(max = 2)
    @Column(name = "c_doc_type_mr", length = 2)
    private String cDocTypeMr;

    /**
     * PRSFXO
     */
    @Size(max = 10)
    @Column(name = "order_suffix", length = 10)
    private String orderSuffix;

    /**
     * PRLNID for PO line number
     */
    @Column(name = "line_no_po")
    private Integer lineNoPo;

    /**
     * PRNLIN for number of lines
     */
    @Column(name = "line_no_mr")
    private Integer lineNoMr;

    /**
     * PRTX Y means true, otherwise false
     */
    @Column(name = "taxable")
    private Boolean taxable;

    /**
     * PRVRMK
     */
    @Size(max = 30)
    @Column(name = "description", length = 30)
    private String description;

    /**
     * PRMATC
     */
    @Size(max = 1)
    @Column(name = "m_match_type", length = 1)
    private String mMatchType;

    /**
     * PRKCOO
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mMatchPOS")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mMatchPOS")
    private CCostCenter cCostCenter;

    /**
     * PRAN8 is mapped to vendor code.
     */
    @ManyToOne
    @JsonIgnoreProperties("mMatchPOS")
    private CVendor cVendor;

    /**
     * PRCRCD
     */
    @ManyToOne
    @JsonIgnoreProperties("mMatchPOS")
    private CCurrency cCurrency;

    /**
     * PREXR1 is mapped to tax code.
     * PRTXA1 is mapped to tax name.
     */
    @ManyToOne
    @JsonIgnoreProperties("mMatchPOS")
    private CTaxCategory cTaxCategory;

    /**
     * PRUOM is mapped to code, name, and symbol.
     */
    @ManyToOne
    @JsonIgnoreProperties("mMatchPOS")
    private CUnitOfMeasure cUom;

    /**
     * PRITM is mapped to product code.
     * PRLITM is mapped to product name.
     * PRAITM is mapped to product desc.
     */
    @ManyToOne
    @JsonIgnoreProperties("mMatchPOS")
    private CProduct mProduct;

    /**
     * PRMCU is mapped to warehouse code and name.
     */
    @ManyToOne
    @JsonIgnoreProperties("mMatchPOS")
    private CWarehouse mWarehouse;

    /**
     * PRLOCN is mapped to locator code.
     */
    @ManyToOne
    @JsonIgnoreProperties("mMatchPOS")
    private CLocator mLocator;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public MMatchPO deliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
        return this;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getcDocType() {
        return cDocType;
    }

    public MMatchPO cDocType(String cDocType) {
        this.cDocType = cDocType;
        return this;
    }

    public void setcDocType(String cDocType) {
        this.cDocType = cDocType;
    }

    public String getPoNo() {
        return poNo;
    }

    public MMatchPO poNo(String poNo) {
        this.poNo = poNo;
        return this;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public LocalDate getPoDate() {
        return poDate;
    }

    public MMatchPO poDate(LocalDate poDate) {
        this.poDate = poDate;
        return this;
    }

    public void setPoDate(LocalDate poDate) {
        this.poDate = poDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public MMatchPO receiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
        return this;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public MMatchPO receiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
        return this;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public MMatchPO qty(BigDecimal qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getcConversionRate() {
        return cConversionRate;
    }

    public MMatchPO cConversionRate(BigDecimal cConversionRate) {
        this.cConversionRate = cConversionRate;
        return this;
    }

    public void setcConversionRate(BigDecimal cConversionRate) {
        this.cConversionRate = cConversionRate;
    }

    public BigDecimal getOpenQty() {
        return openQty;
    }

    public MMatchPO openQty(BigDecimal openQty) {
        this.openQty = openQty;
        return this;
    }

    public void setOpenQty(BigDecimal openQty) {
        this.openQty = openQty;
    }

    public BigDecimal getPriceActual() {
        return priceActual;
    }

    public MMatchPO priceActual(BigDecimal priceActual) {
        this.priceActual = priceActual;
        return this;
    }

    public void setPriceActual(BigDecimal priceActual) {
        this.priceActual = priceActual;
    }

    public BigDecimal getForeignActual() {
        return foreignActual;
    }

    public MMatchPO foreignActual(BigDecimal foreignActual) {
        this.foreignActual = foreignActual;
        return this;
    }

    public void setForeignActual(BigDecimal foreignActual) {
        this.foreignActual = foreignActual;
    }

    public BigDecimal getOpenAmount() {
        return openAmount;
    }

    public MMatchPO openAmount(BigDecimal openAmount) {
        this.openAmount = openAmount;
        return this;
    }

    public void setOpenAmount(BigDecimal openAmount) {
        this.openAmount = openAmount;
    }

    public BigDecimal getOpenForeignAmount() {
        return openForeignAmount;
    }

    public MMatchPO openForeignAmount(BigDecimal openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
        return this;
    }

    public void setOpenForeignAmount(BigDecimal openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
    }

    public BigDecimal getTotalLines() {
        return totalLines;
    }

    public MMatchPO totalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
        return this;
    }

    public void setTotalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimal getForeignTotalLines() {
        return foreignTotalLines;
    }

    public MMatchPO foreignTotalLines(BigDecimal foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
        return this;
    }

    public void setForeignTotalLines(BigDecimal foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public MMatchPO taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public MMatchPO foreignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
        return this;
    }

    public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public LocalDate getDateAccount() {
        return dateAccount;
    }

    public MMatchPO dateAccount(LocalDate dateAccount) {
        this.dateAccount = dateAccount;
        return this;
    }

    public void setDateAccount(LocalDate dateAccount) {
        this.dateAccount = dateAccount;
    }

    public String getcDocTypeMr() {
        return cDocTypeMr;
    }

    public MMatchPO cDocTypeMr(String cDocTypeMr) {
        this.cDocTypeMr = cDocTypeMr;
        return this;
    }

    public void setcDocTypeMr(String cDocTypeMr) {
        this.cDocTypeMr = cDocTypeMr;
    }

    public String getOrderSuffix() {
        return orderSuffix;
    }

    public MMatchPO orderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
        return this;
    }

    public void setOrderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
    }

    public Integer getLineNoPo() {
        return lineNoPo;
    }

    public MMatchPO lineNoPo(Integer lineNoPo) {
        this.lineNoPo = lineNoPo;
        return this;
    }

    public void setLineNoPo(Integer lineNoPo) {
        this.lineNoPo = lineNoPo;
    }

    public Integer getLineNoMr() {
        return lineNoMr;
    }

    public MMatchPO lineNoMr(Integer lineNoMr) {
        this.lineNoMr = lineNoMr;
        return this;
    }

    public void setLineNoMr(Integer lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public MMatchPO taxable(Boolean taxable) {
        this.taxable = taxable;
        return this;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public String getDescription() {
        return description;
    }

    public MMatchPO description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getmMatchType() {
        return mMatchType;
    }

    public MMatchPO mMatchType(String mMatchType) {
        this.mMatchType = mMatchType;
        return this;
    }

    public void setmMatchType(String mMatchType) {
        this.mMatchType = mMatchType;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MMatchPO adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCostCenter getCCostCenter() {
        return cCostCenter;
    }

    public MMatchPO cCostCenter(CCostCenter cCostCenter) {
        this.cCostCenter = cCostCenter;
        return this;
    }

    public void setCCostCenter(CCostCenter cCostCenter) {
        this.cCostCenter = cCostCenter;
    }

    public CVendor getCVendor() {
        return cVendor;
    }

    public MMatchPO cVendor(CVendor cVendor) {
        this.cVendor = cVendor;
        return this;
    }

    public void setCVendor(CVendor cVendor) {
        this.cVendor = cVendor;
    }

    public CCurrency getCCurrency() {
        return cCurrency;
    }

    public MMatchPO cCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
        return this;
    }

    public void setCCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
    }

    public CTaxCategory getCTaxCategory() {
        return cTaxCategory;
    }

    public MMatchPO cTaxCategory(CTaxCategory cTaxCategory) {
        this.cTaxCategory = cTaxCategory;
        return this;
    }

    public void setCTaxCategory(CTaxCategory cTaxCategory) {
        this.cTaxCategory = cTaxCategory;
    }

    public CUnitOfMeasure getCUom() {
        return cUom;
    }

    public MMatchPO cUom(CUnitOfMeasure cUnitOfMeasure) {
        this.cUom = cUnitOfMeasure;
        return this;
    }

    public void setCUom(CUnitOfMeasure cUnitOfMeasure) {
        this.cUom = cUnitOfMeasure;
    }

    public CProduct getMProduct() {
        return mProduct;
    }

    public MMatchPO mProduct(CProduct cProduct) {
        this.mProduct = cProduct;
        return this;
    }

    public void setMProduct(CProduct cProduct) {
        this.mProduct = cProduct;
    }

    public CWarehouse getMWarehouse() {
        return mWarehouse;
    }

    public MMatchPO mWarehouse(CWarehouse cWarehouse) {
        this.mWarehouse = cWarehouse;
        return this;
    }

    public void setMWarehouse(CWarehouse cWarehouse) {
        this.mWarehouse = cWarehouse;
    }

    public CLocator getMLocator() {
        return mLocator;
    }

    public MMatchPO mLocator(CLocator cLocator) {
        this.mLocator = cLocator;
        return this;
    }

    public void setMLocator(CLocator cLocator) {
        this.mLocator = cLocator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MMatchPO)) {
            return false;
        }
        return id != null && id.equals(((MMatchPO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MMatchPO{" +
            "id=" + getId() +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", cDocType='" + getcDocType() + "'" +
            ", poNo='" + getPoNo() + "'" +
            ", poDate='" + getPoDate() + "'" +
            ", receiptNo='" + getReceiptNo() + "'" +
            ", receiptDate='" + getReceiptDate() + "'" +
            ", qty=" + getQty() +
            ", cConversionRate=" + getcConversionRate() +
            ", openQty=" + getOpenQty() +
            ", priceActual=" + getPriceActual() +
            ", foreignActual=" + getForeignActual() +
            ", openAmount=" + getOpenAmount() +
            ", openForeignAmount=" + getOpenForeignAmount() +
            ", totalLines=" + getTotalLines() +
            ", foreignTotalLines=" + getForeignTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", dateAccount='" + getDateAccount() + "'" +
            ", cDocTypeMr='" + getcDocTypeMr() + "'" +
            ", orderSuffix='" + getOrderSuffix() + "'" +
            ", lineNoPo=" + getLineNoPo() +
            ", lineNoMr=" + getLineNoMr() +
            ", taxable='" + isTaxable() + "'" +
            ", description='" + getDescription() + "'" +
            ", mMatchType='" + getmMatchType() + "'" +
            "}";
    }
}
