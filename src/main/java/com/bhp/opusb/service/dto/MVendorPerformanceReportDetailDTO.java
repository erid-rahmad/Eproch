package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MVendorPerformanceReportDetailDTO implements Serializable {
    private BigDecimal awardedSpending, awardedSavings, poSpend, poCount, 
        invoiceSpend, invoiceCount, invoiceWithoutException, participant;

    private Integer activeContracts, eventInvited, eventParticipation;

    private List<MContractDTO> activeContractObj;

    private List<Map<String,Object>> performanceProjectAnalysis;

    public BigDecimal getAwardedSpending() {
        return awardedSpending;
    }

    public Integer getEventParticipation() {
        return eventParticipation;
    }

    public void setEventParticipation(Integer eventParticipation) {
        this.eventParticipation = eventParticipation;
    }

    public List<Map<String,Object>> getPerformanceProjectAnalysis() {
        return performanceProjectAnalysis;
    }

    public void setPerformanceProjectAnalysis(List<Map<String,Object>> performanceProjectAnalysis) {
        this.performanceProjectAnalysis = performanceProjectAnalysis;
    }

    public List<MContractDTO> getActiveContractObj() {
        return activeContractObj;
    }

    public void setActiveContractObj(List<MContractDTO> activeContractObj) {
        this.activeContractObj = activeContractObj;
    }

    public Integer getEventInvited() {
        return eventInvited;
    }

    public void setEventInvited(Integer eventInvited) {
        this.eventInvited = eventInvited;
    }

    public Integer getActiveContracts() {
        return activeContracts;
    }

    public void setActiveContracts(Integer activeContracts) {
        this.activeContracts = activeContracts;
    }

    public BigDecimal getParticipant() {
        return participant;
    }

    public void setParticipant(BigDecimal participant) {
        this.participant = participant;
    }

    public BigDecimal getInvoiceWithoutException() {
        return invoiceWithoutException;
    }

    public void setInvoiceWithoutException(BigDecimal invoiceWithoutException) {
        this.invoiceWithoutException = invoiceWithoutException;
    }

    public BigDecimal getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(BigDecimal invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public BigDecimal getInvoiceSpend() {
        return invoiceSpend;
    }

    public void setInvoiceSpend(BigDecimal invoiceSpend) {
        this.invoiceSpend = invoiceSpend;
    }

    public BigDecimal getPoCount() {
        return poCount;
    }

    public void setPoCount(BigDecimal poCount) {
        this.poCount = poCount;
    }

    public BigDecimal getPoSpend() {
        return poSpend;
    }

    public void setPoSpend(BigDecimal poSpend) {
        this.poSpend = poSpend;
    }

    public BigDecimal getAwardedSavings() {
        return awardedSavings;
    }

    public void setAwardedSavings(BigDecimal awardedSavings) {
        this.awardedSavings = awardedSavings;
    }

    public void setAwardedSpending(BigDecimal awardedSpending) {
        this.awardedSpending = awardedSpending;
    }
}
