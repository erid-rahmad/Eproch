package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bhp.opusb.domain.enumeration.CTransactionType;

import io.swagger.annotations.ApiModel;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPaymentTerm} entity.
 */
@ApiModel(description = "The CPaymentTerm entity.\n@author A true hipster")
public class CPaymentTermDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    private Boolean afterDelivery;

    private Boolean asDefault;

    private Boolean calculateBusinessDay;

    @NotNull
    private BigDecimal discount;

    @NotNull
    private BigDecimal discount2;

    @NotNull
    @Max(value = 10L)
    private Long discountDays;

    @NotNull
    @Max(value = 10L)
    private Long discountDays2;

    @Size(max = 1000)
    private String documentNote;

    @Max(value = 10L)
    private Long fixMonthCutOff;

    @Max(value = 10L)
    private Long fixMonthDay;

    @Max(value = 10L)
    private Long fixMonthOffset;

    private Boolean fixedDueDate;

    @Max(value = 10L)
    private Long graceDays;

    @Max(value = 1)
    private Integer netDay;

    @NotNull
    @Max(value = 10L)
    private Long netDays;

    private Boolean onNextBusinessDay;

    @NotNull
    private CTransactionType transactionType;

    private Boolean valid;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAfterDelivery() {
        return afterDelivery;
    }

    public void setAfterDelivery(Boolean afterDelivery) {
        this.afterDelivery = afterDelivery;
    }

    public Boolean isAsDefault() {
        return asDefault;
    }

    public void setAsDefault(Boolean asDefault) {
        this.asDefault = asDefault;
    }

    public Boolean isCalculateBusinessDay() {
        return calculateBusinessDay;
    }

    public void setCalculateBusinessDay(Boolean calculateBusinessDay) {
        this.calculateBusinessDay = calculateBusinessDay;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount2() {
        return discount2;
    }

    public void setDiscount2(BigDecimal discount2) {
        this.discount2 = discount2;
    }

    public Long getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(Long discountDays) {
        this.discountDays = discountDays;
    }

    public Long getDiscountDays2() {
        return discountDays2;
    }

    public void setDiscountDays2(Long discountDays2) {
        this.discountDays2 = discountDays2;
    }

    public String getDocumentNote() {
        return documentNote;
    }

    public void setDocumentNote(String documentNote) {
        this.documentNote = documentNote;
    }

    public Long getFixMonthCutOff() {
        return fixMonthCutOff;
    }

    public void setFixMonthCutOff(Long fixMonthCutOff) {
        this.fixMonthCutOff = fixMonthCutOff;
    }

    public Long getFixMonthDay() {
        return fixMonthDay;
    }

    public void setFixMonthDay(Long fixMonthDay) {
        this.fixMonthDay = fixMonthDay;
    }

    public Long getFixMonthOffset() {
        return fixMonthOffset;
    }

    public void setFixMonthOffset(Long fixMonthOffset) {
        this.fixMonthOffset = fixMonthOffset;
    }

    public Boolean isFixedDueDate() {
        return fixedDueDate;
    }

    public void setFixedDueDate(Boolean fixedDueDate) {
        this.fixedDueDate = fixedDueDate;
    }

    public Long getGraceDays() {
        return graceDays;
    }

    public void setGraceDays(Long graceDays) {
        this.graceDays = graceDays;
    }

    public Integer getNetDay() {
        return netDay;
    }

    public void setNetDay(Integer netDay) {
        this.netDay = netDay;
    }

    public Long getNetDays() {
        return netDays;
    }

    public void setNetDays(Long netDays) {
        this.netDays = netDays;
    }

    public Boolean isOnNextBusinessDay() {
        return onNextBusinessDay;
    }

    public void setOnNextBusinessDay(Boolean onNextBusinessDay) {
        this.onNextBusinessDay = onNextBusinessDay;
    }

    public CTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(CTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPaymentTermDTO cPaymentTermDTO = (CPaymentTermDTO) o;
        if (cPaymentTermDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPaymentTermDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPaymentTermDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", afterDelivery='" + isAfterDelivery() + "'" +
            ", asDefault='" + isAsDefault() + "'" +
            ", calculateBusinessDay='" + isCalculateBusinessDay() + "'" +
            ", discount=" + getDiscount() +
            ", discount2=" + getDiscount2() +
            ", discountDays=" + getDiscountDays() +
            ", discountDays2=" + getDiscountDays2() +
            ", documentNote='" + getDocumentNote() + "'" +
            ", fixMonthCutOff=" + getFixMonthCutOff() +
            ", fixMonthDay=" + getFixMonthDay() +
            ", fixMonthOffset=" + getFixMonthOffset() +
            ", fixedDueDate='" + isFixedDueDate() + "'" +
            ", graceDays=" + getGraceDays() +
            ", netDay=" + getNetDay() +
            ", netDays=" + getNetDays() +
            ", onNextBusinessDay='" + isOnNextBusinessDay() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", valid='" + isValid() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
