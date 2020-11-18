package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VerificationDTO {
    Verification form;
    List<MVerificationLineDTO> line;
    List<MVerificationLineDTO> remove;

    public Verification getForm() {
        return form;
    }

    public void setForm(Verification form) {
        this.form = form;
    }

    public List<MVerificationLineDTO> getLine() {
        return line;
    }

    public void setLine(List<MVerificationLineDTO> line) {
        this.line = line;
    }

    public List<MVerificationLineDTO> getRemove() {
        return remove;
    }

    public void setRemove(List<MVerificationLineDTO> remove) {
        this.remove = remove;
    }

    public class Verification {
        private Long id;
        private String verificationNo;
        private LocalDate verificationDate;
        private String description;
        private String invoiceNo;
        private LocalDate invoiceDate;
        private String taxInvoice;
        private LocalDate taxDate;
        private BigDecimal totalLines;
        private BigDecimal taxAmount;
        private BigDecimal grandTotal;
        private String verificationStatus;
        private Boolean active;
        private Long adOrganizationId;
        private Long currencyId;
        private Long vendorId;
        private Long picUserId;
        private BigDecimal foreignGrandTotal;
        private BigDecimal foreignTaxAmount;
        private LocalDate dataSubmit;
        private LocalDate dateAcct;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getVerificationNo() {
            return verificationNo;
        }

        public void setVerificationNo(String verificationNo) {
            this.verificationNo = verificationNo;
        }

        public LocalDate getVerificationDate() {
            return verificationDate;
        }

        public void setVerificationDate(LocalDate verificationDate) {
            this.verificationDate = verificationDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public LocalDate getInvoiceDate() {
            return invoiceDate;
        }

        public void setInvoiceDate(LocalDate invoiceDate) {
            this.invoiceDate = invoiceDate;
        }

        public String getTaxInvoice() {
            return taxInvoice;
        }

        public void setTaxInvoice(String taxInvoice) {
            this.taxInvoice = taxInvoice;
        }

        public LocalDate getTaxDate() {
            return taxDate;
        }

        public void setTaxDate(LocalDate taxDate) {
            this.taxDate = taxDate;
        }

        public BigDecimal getTotalLines() {
            return totalLines;
        }

        public void setTotalLines(BigDecimal totalLines) {
            this.totalLines = totalLines;
        }

        public BigDecimal getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(BigDecimal taxAmount) {
            this.taxAmount = taxAmount;
        }

        public BigDecimal getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(BigDecimal grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getVerificationStatus() {
            return verificationStatus;
        }

        public void setVerificationStatus(String verificationStatus) {
            this.verificationStatus = verificationStatus;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public Long getAdOrganizationId() {
            return adOrganizationId;
        }

        public void setAdOrganizationId(Long adOrganizationId) {
            this.adOrganizationId = adOrganizationId;
        }

        public Long getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(Long currencyId) {
            this.currencyId = currencyId;
        }

        public Long getVendorId() {
            return vendorId;
        }

        public void setVendorId(Long vendorId) {
            this.vendorId = vendorId;
        }

        public Long getPicUserId() {
            return picUserId;
        }

        public void setPicUserId(Long picUserId) {
            this.picUserId = picUserId;
        }

        public BigDecimal getForeignGrandTotal() {
            return foreignGrandTotal;
        }

        public void setForeignGrandTotal(BigDecimal foreignGrandTotal) {
            this.foreignGrandTotal = foreignGrandTotal;
        }

        public BigDecimal getForeignTaxAmount() {
            return foreignTaxAmount;
        }

        public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
            this.foreignTaxAmount = foreignTaxAmount;
        }

        public LocalDate getDataSubmit() {
            return dataSubmit;
        }

        public void setDataSubmit(LocalDate dataSubmit) {
            this.dataSubmit = dataSubmit;
        }

        public LocalDate getDateAcct() {
            return dateAcct;
        }

        public void setDateAcct(LocalDate dateAcct) {
            this.dateAcct = dateAcct;
        }

    }



}
