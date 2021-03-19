package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPaymentSchedule} entity.
 */
@ApiModel(description = "The CPaymentSchedule entity.\n@author A true hipster")
public class CPaymentScheduleDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private BigDecimal discount;

    @NotNull
    private Long discountDays;

    private Long graceDays;

    @Size(max = 1)
    private String netDay;

    @NotNull
    private Long netDays;

    @NotNull
    private BigDecimal percentage;

    private Boolean valid;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long cPaymentTermId;
    private String cPaymentTermName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Long getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(Long discountDays) {
        this.discountDays = discountDays;
    }

    public Long getGraceDays() {
        return graceDays;
    }

    public void setGraceDays(Long graceDays) {
        this.graceDays = graceDays;
    }

    public String getNetDay() {
        return netDay;
    }

    public void setNetDay(String netDay) {
        this.netDay = netDay;
    }

    public Long getNetDays() {
        return netDays;
    }

    public void setNetDays(Long netDays) {
        this.netDays = netDays;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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

    public Long getCPaymentTermId() {
        return cPaymentTermId;
    }

    public void setCPaymentTermId(Long cPaymentTermId) {
        this.cPaymentTermId = cPaymentTermId;
    }

    public String getCPaymentTermName() {
        return cPaymentTermName;
    }

    public void setCPaymentTermName(String cPaymentTermName) {
        this.cPaymentTermName = cPaymentTermName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPaymentScheduleDTO cPaymentScheduleDTO = (CPaymentScheduleDTO) o;
        if (cPaymentScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPaymentScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPaymentScheduleDTO{" +
            "id=" + getId() +
            ", discount=" + getDiscount() +
            ", discountDays=" + getDiscountDays() +
            ", graceDays=" + getGraceDays() +
            ", netDay='" + getNetDay() + "'" +
            ", netDays=" + getNetDays() +
            ", percentage=" + getPercentage() +
            ", valid='" + isValid() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cPaymentTermId=" + getCPaymentTermId() +
            "}";
    }
}
