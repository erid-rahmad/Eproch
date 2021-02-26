package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentStatusDTO {

  public static final String STATUS_UNPROCESSED = "N";
  public static final String STATUS_APPROVED = "A";
  public static final String STATUS_PAID = "P";

  @JsonProperty("VHKCO")
  private String orgCode;

  @JsonProperty("VHDOCM")
  private String documentNo;

  @JsonProperty("VHDOC")
  private String voucherNo;

  @JsonProperty("VHDCT")
  private String documentType;
  
  @JsonProperty("VHPST")
  private String status;

  @JsonProperty("VHAA")
  private BigDecimal amount;

  @JsonProperty("VHDMTJ")
  private LocalDate date;

  @JsonProperty("VHPTUPUSER")
  private String updatedBy;

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getDocumentNo() {
    return documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public String getVoucherNo() {
    return voucherNo;
  }

  public void setVoucherNo(String voucherNo) {
    this.voucherNo = voucherNo;
  }

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
