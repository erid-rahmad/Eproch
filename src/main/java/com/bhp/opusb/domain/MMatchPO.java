package com.bhp.opusb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A MMatchPO.
 */
@Entity
@Table(name = "m_match_po")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MMatchPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "c_doc_type")
    private String cDocType;

    @Column(name = "c_vendor")
    private String cVendor;

    @Column(name = "c_element")
    private String cElement;

    @Column(name = "c_cost_center")
    private String cCostCenter;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "receipt_date")
    private LocalDate receiptDate;

    @Column(name = "delivery_no")
    private String deliveryNo;

    @Column(name = "m_product_code")
    private String mProductCode;

    @Column(name = "m_product_name")
    private String mProductName;

    @Column(name = "m_product_desc")
    private String mProductDesc;

    @Column(name = "c_uom")
    private String cUOM;

    @Column(name = "qty")
    private String qty;

    @Column(name = "c_currency")
    private String cCurrency;

    @Column(name = "c_conversion_rate")
    private String cConversionRate;

    @Column(name = "open_qty")
    private String openQty;

    @Column(name = "price_actual")
    private String priceActual;

    @Column(name = "foreign_actual")
    private String foreignActual;

    @Column(name = "open_amount")
    private String openAmount;

    @Column(name = "open_foreign_amount")
    private String openForeignAmount;

    @Column(name = "total_lines")
    private String totalLines;

    @Column(name = "foreign_total_lines")
    private String foreignTotalLines;

    @Column(name = "c_tax")
    private String cTax;

    @Column(name = "tax_amount")
    private String taxAmount;

    @Column(name = "foreign_tax_amount")
    private String foreignTaxAmount;

    @Column(name = "m_locator")
    private String mLocator;

    @Column(name = "ad_organization")
    private String adOrganization;

    @Column(name = "date_account")
    private LocalDate dateAccount;

    @Column(name = "c_doc_type_mr")
    private String cDocTypeMr;

    @Column(name = "order_suffix")
    private String orderSuffix;

    @Column(name = "line_no_po")
    private String lineNoPo;

    @Column(name = "line_no_mr")
    private String lineNoMr;

    @Column(name = "is_taxable")
    private Boolean isTaxable;

    @Column(name = "c_tax_code")
    private String cTaxCode;

    @Column(name = "c_tax_name")
    private String cTaxName;

    @Column(name = "description")
    private String description;

    @Column(name = "m_match_type")
    private String mMatchType;

    @Column(name = "m_warehouse")
    private String mWarehouse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getcVendor() {
        return cVendor;
    }

    public MMatchPO cVendor(String cVendor) {
        this.cVendor = cVendor;
        return this;
    }

    public void setcVendor(String cVendor) {
        this.cVendor = cVendor;
    }

    public String getcElement() {
        return cElement;
    }

    public MMatchPO cElement(String cElement) {
        this.cElement = cElement;
        return this;
    }

    public void setcElement(String cElement) {
        this.cElement = cElement;
    }

    public String getcCostCenter() {
        return cCostCenter;
    }

    public MMatchPO cCostCenter(String cCostCenter) {
        this.cCostCenter = cCostCenter;
        return this;
    }

    public void setcCostCenter(String cCostCenter) {
        this.cCostCenter = cCostCenter;
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

    public String getmProductCode() {
        return mProductCode;
    }

    public MMatchPO mProductCode(String mProductCode) {
        this.mProductCode = mProductCode;
        return this;
    }

    public void setmProductCode(String mProductCode) {
        this.mProductCode = mProductCode;
    }

    public String getmProductName() {
        return mProductName;
    }

    public MMatchPO mProductName(String mProductName) {
        this.mProductName = mProductName;
        return this;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmProductDesc() {
        return mProductDesc;
    }

    public MMatchPO mProductDesc(String mProductDesc) {
        this.mProductDesc = mProductDesc;
        return this;
    }

    public void setmProductDesc(String mProductDesc) {
        this.mProductDesc = mProductDesc;
    }

    public String getcUOM() {
        return cUOM;
    }

    public MMatchPO cUOM(String cUOM) {
        this.cUOM = cUOM;
        return this;
    }

    public void setcUOM(String cUOM) {
        this.cUOM = cUOM;
    }

    public String getQty() {
        return qty;
    }

    public MMatchPO qty(String qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getcCurrency() {
        return cCurrency;
    }

    public MMatchPO cCurrency(String cCurrency) {
        this.cCurrency = cCurrency;
        return this;
    }

    public void setcCurrency(String cCurrency) {
        this.cCurrency = cCurrency;
    }

    public String getcConversionRate() {
        return cConversionRate;
    }

    public MMatchPO cConversionRate(String cConversionRate) {
        this.cConversionRate = cConversionRate;
        return this;
    }

    public void setcConversionRate(String cConversionRate) {
        this.cConversionRate = cConversionRate;
    }

    public String getOpenQty() {
        return openQty;
    }

    public MMatchPO openQty(String openQty) {
        this.openQty = openQty;
        return this;
    }

    public void setOpenQty(String openQty) {
        this.openQty = openQty;
    }

    public String getPriceActual() {
        return priceActual;
    }

    public MMatchPO priceActual(String priceActual) {
        this.priceActual = priceActual;
        return this;
    }

    public void setPriceActual(String priceActual) {
        this.priceActual = priceActual;
    }

    public String getForeignActual() {
        return foreignActual;
    }

    public MMatchPO foreignActual(String foreignActual) {
        this.foreignActual = foreignActual;
        return this;
    }

    public void setForeignActual(String foreignActual) {
        this.foreignActual = foreignActual;
    }

    public String getOpenAmount() {
        return openAmount;
    }

    public MMatchPO openAmount(String openAmount) {
        this.openAmount = openAmount;
        return this;
    }

    public void setOpenAmount(String openAmount) {
        this.openAmount = openAmount;
    }

    public String getOpenForeignAmount() {
        return openForeignAmount;
    }

    public MMatchPO openForeignAmount(String openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
        return this;
    }

    public void setOpenForeignAmount(String openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
    }

    public String getTotalLines() {
        return totalLines;
    }

    public MMatchPO totalLines(String totalLines) {
        this.totalLines = totalLines;
        return this;
    }

    public void setTotalLines(String totalLines) {
        this.totalLines = totalLines;
    }

    public String getForeignTotalLines() {
        return foreignTotalLines;
    }

    public MMatchPO foreignTotalLines(String foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
        return this;
    }

    public void setForeignTotalLines(String foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public String getcTax() {
        return cTax;
    }

    public MMatchPO cTax(String cTax) {
        this.cTax = cTax;
        return this;
    }

    public void setcTax(String cTax) {
        this.cTax = cTax;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public MMatchPO taxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public MMatchPO foreignTaxAmount(String foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
        return this;
    }

    public void setForeignTaxAmount(String foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public String getmLocator() {
        return mLocator;
    }

    public MMatchPO mLocator(String mLocator) {
        this.mLocator = mLocator;
        return this;
    }

    public void setmLocator(String mLocator) {
        this.mLocator = mLocator;
    }

    public String getAdOrganization() {
        return adOrganization;
    }

    public MMatchPO adOrganization(String adOrganization) {
        this.adOrganization = adOrganization;
        return this;
    }

    public void setAdOrganization(String adOrganization) {
        this.adOrganization = adOrganization;
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

    public String getLineNoPo() {
        return lineNoPo;
    }

    public MMatchPO lineNoPo(String lineNoPo) {
        this.lineNoPo = lineNoPo;
        return this;
    }

    public void setLineNoPo(String lineNoPo) {
        this.lineNoPo = lineNoPo;
    }

    public String getLineNoMr() {
        return lineNoMr;
    }

    public MMatchPO lineNoMr(String lineNoMr) {
        this.lineNoMr = lineNoMr;
        return this;
    }

    public void setLineNoMr(String lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public Boolean isIsTaxable() {
        return isTaxable;
    }

    public MMatchPO isTaxable(Boolean isTaxable) {
        this.isTaxable = isTaxable;
        return this;
    }

    public void setIsTaxable(Boolean isTaxable) {
        this.isTaxable = isTaxable;
    }

    public String getcTaxCode() {
        return cTaxCode;
    }

    public MMatchPO cTaxCode(String cTaxCode) {
        this.cTaxCode = cTaxCode;
        return this;
    }

    public void setcTaxCode(String cTaxCode) {
        this.cTaxCode = cTaxCode;
    }

    public String getcTaxName() {
        return cTaxName;
    }

    public MMatchPO cTaxName(String cTaxName) {
        this.cTaxName = cTaxName;
        return this;
    }

    public void setcTaxName(String cTaxName) {
        this.cTaxName = cTaxName;
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

    public String getmWarehouse() {
        return mWarehouse;
    }

    public MMatchPO mWarehouse(String mWarehouse) {
        this.mWarehouse = mWarehouse;
        return this;
    }

    public void setmWarehouse(String mWarehouse) {
        this.mWarehouse = mWarehouse;
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
            ", cDocType='" + getcDocType() + "'" +
            ", cVendor='" + getcVendor() + "'" +
            ", cElement='" + getcElement() + "'" +
            ", cCostCenter='" + getcCostCenter() + "'" +
            ", poNo='" + getPoNo() + "'" +
            ", poDate='" + getPoDate() + "'" +
            ", receiptNo='" + getReceiptNo() + "'" +
            ", receiptDate='" + getReceiptDate() + "'" +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", mProductCode='" + getmProductCode() + "'" +
            ", mProductName='" + getmProductName() + "'" +
            ", mProductDesc='" + getmProductDesc() + "'" +
            ", cUOM='" + getcUOM() + "'" +
            ", qty='" + getQty() + "'" +
            ", cCurrency='" + getcCurrency() + "'" +
            ", cConversionRate='" + getcConversionRate() + "'" +
            ", openQty='" + getOpenQty() + "'" +
            ", priceActual='" + getPriceActual() + "'" +
            ", foreignActual='" + getForeignActual() + "'" +
            ", openAmount='" + getOpenAmount() + "'" +
            ", openForeignAmount='" + getOpenForeignAmount() + "'" +
            ", totalLines='" + getTotalLines() + "'" +
            ", foreignTotalLines='" + getForeignTotalLines() + "'" +
            ", cTax='" + getcTax() + "'" +
            ", taxAmount='" + getTaxAmount() + "'" +
            ", foreignTaxAmount='" + getForeignTaxAmount() + "'" +
            ", mLocator='" + getmLocator() + "'" +
            ", adOrganization='" + getAdOrganization() + "'" +
            ", dateAccount='" + getDateAccount() + "'" +
            ", cDocTypeMr='" + getcDocTypeMr() + "'" +
            ", orderSuffix='" + getOrderSuffix() + "'" +
            ", lineNoPo='" + getLineNoPo() + "'" +
            ", lineNoMr='" + getLineNoMr() + "'" +
            ", isTaxable='" + isIsTaxable() + "'" +
            ", cTaxCode='" + getcTaxCode() + "'" +
            ", cTaxName='" + getcTaxName() + "'" +
            ", description='" + getDescription() + "'" +
            ", mMatchType='" + getmMatchType() + "'" +
            ", mWarehouse='" + getmWarehouse() + "'" +
            "}";
    }
}
