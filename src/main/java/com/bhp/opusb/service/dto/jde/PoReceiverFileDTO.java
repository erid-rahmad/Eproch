package com.bhp.opusb.service.dto.jde;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This DTO represents the JDE's F43121 table structure.
 */
public class PoReceiverFileDTO {
  
  @JsonProperty("PRMATC")
  private String matchType;
  
  @JsonProperty("PRAN8")
  private long addressNo;
  
  @JsonProperty("PRKCOO")
  private String orgCode;
  
  @JsonProperty("PRDCTO")
  private String docTypePo;
  
  @JsonProperty("PRDCT")
  private String docTypeMr;
  
  @JsonProperty("PRDOCO")
  private String poNo;
  
  @JsonProperty("PRDOC")
  private String receiptNo;
  
  @JsonProperty("PRTRDJ")
  private LocalDate poDate;
  
  @JsonProperty("PRRCDJ")
  private LocalDate receiptDate;
  
  @JsonProperty("PRDGL")
  private LocalDate dateAccount;
  
  @JsonProperty("PRLNID")
  private int lineNoPo;
  
  @JsonProperty("PRNLIN")
  private int lineNoMr;
  
  @JsonProperty("PRSFXO")
  private String orderSuffix;
  
  @JsonProperty("PRCRR")
  private BigDecimal conversionRate;
  
  @JsonProperty("PRSHPN")
  private String deliveryNo;
  
  @JsonProperty("PRVRMK")
  private String description;
  
  @JsonProperty("PRCRCD")
  private String currencyCode;
  
  @JsonProperty("PRTX")
  private String taxable;
  
  @JsonProperty("PREXR1")
  private String taxCategory;
  
  @JsonProperty("PRTXA1")
  private String tax;
  
  @JsonProperty("PRUOM")
  private String uom;
  
  @JsonProperty("PRMCU")
  private String warehouse;
  
  @JsonProperty("PRLOCN")
  private String locator;
  
  @JsonProperty("PRPRRC")
  private BigDecimal priceActual;
  
  @JsonProperty("PRFRRC")
  private BigDecimal foreignActual;
  
  @JsonProperty("PRSTAM")
  private BigDecimal taxAmount;
  
  @JsonProperty("PRCTAM")
  private BigDecimal foreignTaxAmount;
  
  @JsonProperty("PRAREC")
  private BigDecimal totalLines;
  
  @JsonProperty("PRFREC")
  private BigDecimal foreignTotalLines;
  
  @JsonProperty("PRAOPN")
  private BigDecimal openAmount;
  
  @JsonProperty("PRFAP")
  private BigDecimal openForeignAmount;
  
  @JsonProperty("PRUREC")
  private BigDecimal qty;
  
  @JsonProperty("PRUOPN")
  private BigDecimal openQty;
  
  @JsonProperty("PRITM")
  private String itemCode;
  
  @JsonProperty("PRLITM")
  private String itemDesc1;
  
  @JsonProperty("PRAITM")
  private String itemDesc2;

  @JsonProperty("PDDSC1")
  private String productDesc1;
  
  @JsonProperty("PDDSC2")
  private String productDesc2;

  public String getMatchType() {
    return matchType;
  }

  public void setMatchType(String matchType) {
    this.matchType = matchType;
  }

  public long getAddressNo() {
    return addressNo;
  }

  public void setAddressNo(long addressNo) {
    this.addressNo = addressNo;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getDocTypePo() {
    return docTypePo;
  }

  public void setDocTypePo(String docTypePo) {
    this.docTypePo = docTypePo;
  }

  public String getDocTypeMr() {
    return docTypeMr;
  }

  public void setDocTypeMr(String docTypeMr) {
    this.docTypeMr = docTypeMr;
  }

  public String getPoNo() {
    return poNo;
  }

  public void setPoNo(String poNo) {
    this.poNo = poNo;
  }

  public String getReceiptNo() {
    return receiptNo;
  }

  public void setReceiptNo(String receiptNo) {
    this.receiptNo = receiptNo;
  }

  public LocalDate getPoDate() {
    return poDate;
  }

  public void setPoDate(LocalDate poDate) {
    this.poDate = poDate;
  }

  public LocalDate getReceiptDate() {
    return receiptDate;
  }

  public void setReceiptDate(LocalDate receiptDate) {
    this.receiptDate = receiptDate;
  }

  public LocalDate getDateAccount() {
    return dateAccount;
  }

  public void setDateAccount(LocalDate dateAccount) {
    this.dateAccount = dateAccount;
  }

  public int getLineNoPo() {
    return lineNoPo;
  }

  public void setLineNoPo(int lineNoPo) {
    this.lineNoPo = lineNoPo;
  }

  public int getLineNoMr() {
    return lineNoMr;
  }

  public void setLineNoMr(int lineNoMr) {
    this.lineNoMr = lineNoMr;
  }

  public String getOrderSuffix() {
    return orderSuffix;
  }

  public void setOrderSuffix(String orderSuffix) {
    this.orderSuffix = orderSuffix;
  }

  public BigDecimal getConversionRate() {
    return conversionRate;
  }

  public void setConversionRate(BigDecimal conversionRate) {
    this.conversionRate = conversionRate;
  }

  public String getDeliveryNo() {
    return deliveryNo;
  }

  public void setDeliveryNo(String deliveryNo) {
    this.deliveryNo = deliveryNo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getTaxable() {
    return taxable;
  }

  public void setTaxable(String taxable) {
    this.taxable = taxable;
  }

  public String getTaxCategory() {
    return taxCategory;
  }

  public void setTaxCategory(String taxCategory) {
    this.taxCategory = taxCategory;
  }

  public String getTax() {
    return tax;
  }

  public void setTax(String tax) {
    this.tax = tax;
  }

  public String getUom() {
    return uom;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public String getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(String warehouse) {
    this.warehouse = warehouse;
  }

  public String getLocator() {
    return locator;
  }

  public void setLocator(String locator) {
    this.locator = locator;
  }

  public BigDecimal getPriceActual() {
    return priceActual;
  }

  public void setPriceActual(BigDecimal priceActual) {
    this.priceActual = priceActual;
  }

  public BigDecimal getForeignActual() {
    return foreignActual;
  }

  public void setForeignActual(BigDecimal foreignActual) {
    this.foreignActual = foreignActual;
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

  public BigDecimal getTotalLines() {
    return totalLines;
  }

  public void setTotalLines(BigDecimal totalLines) {
    this.totalLines = totalLines;
  }

  public BigDecimal getForeignTotalLines() {
    return foreignTotalLines;
  }

  public void setForeignTotalLines(BigDecimal foreignTotalLines) {
    this.foreignTotalLines = foreignTotalLines;
  }

  public BigDecimal getOpenAmount() {
    return openAmount;
  }

  public void setOpenAmount(BigDecimal openAmount) {
    this.openAmount = openAmount;
  }

  public BigDecimal getOpenForeignAmount() {
    return openForeignAmount;
  }

  public void setOpenForeignAmount(BigDecimal openForeignAmount) {
    this.openForeignAmount = openForeignAmount;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public void setQty(BigDecimal qty) {
    this.qty = qty;
  }

  public BigDecimal getOpenQty() {
    return openQty;
  }

  public void setOpenQty(BigDecimal openQty) {
    this.openQty = openQty;
  }

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getItemDesc1() {
    return itemDesc1;
  }

  public void setItemDesc1(String itemDesc1) {
    this.itemDesc1 = itemDesc1;
  }

  public String getItemDesc2() {
    return itemDesc2;
  }

  public void setItemDesc2(String itemDesc2) {
    this.itemDesc2 = itemDesc2;
  }

  public String getProductDesc1() {
    return productDesc1;
  }

  public void setProductDesc1(String productDesc1) {
    this.productDesc1 = productDesc1;
  }

  public String getProductDesc2() {
    return productDesc2;
  }

  public void setProductDesc2(String productDesc2) {
    this.productDesc2 = productDesc2;
  }
}
