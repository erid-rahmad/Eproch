package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CConvertionRate} entity.
 */
public class CConvertionRateDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;

    @NotNull
    private BigDecimal rate;

    private UUID uid;

    private Boolean active;


    private Long sourceCurrencyId;
    private String sourceCurrencyName;

    private Long targetCurrencyId;
    private String targetCurrencyName;

    private Long convertionTypeId;
    private String convertionTypeName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public Long getSourceCurrencyId() {
        return sourceCurrencyId;
    }

    public void setSourceCurrencyId(Long cCurrencyId) {
        this.sourceCurrencyId = cCurrencyId;
    }

    public String getSourceCurrencyName() {
        return sourceCurrencyName;
    }

    public void setSourceCurrencyName(String sourceCurrencyName) {
        this.sourceCurrencyName = sourceCurrencyName;
    }
    
    public Long getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(Long cCurrencyId) {
        this.targetCurrencyId = cCurrencyId;
    }

    public String getTargetCurrencyName() {
        return targetCurrencyName;
    }

    public void setTargetCurrencyName(String targetCurrencyName) {
        this.targetCurrencyName = targetCurrencyName;
    }
    
    public Long getConvertionTypeId() {
        return convertionTypeId;
    }

    public void setConvertionTypeId(Long cConvertionTypeId) {
        this.convertionTypeId = cConvertionTypeId;
    }

    public String getConvertionTypeName() {
        return convertionTypeName;
    }

    public void setConvertionTypeName(String convertionTypeName) {
        this.convertionTypeName = convertionTypeName;
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

        CConvertionRateDTO cConvertionRateDTO = (CConvertionRateDTO) o;
        if (cConvertionRateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cConvertionRateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CConvertionRateDTO{" +
            "id=" + getId() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", rate=" + getRate() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", sourceCurrencyId=" + getSourceCurrencyId() +
            ", sourceCurrencyName=" + getSourceCurrencyName() +
            ", targetCurrencyId=" + getTargetCurrencyId() +
            ", targetCurrencyName=" + getTargetCurrencyName() +
            ", convertionTypeId=" + getConvertionTypeId() +
            ", convertionTypeName=" + getConvertionTypeName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            "}";
    }

    
}
