package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CCountry} entity.
 */
public class CCountryDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$")
    private String code;

    private Boolean withRegion;

    private UUID uid;

    private Boolean active;


    private Long currencyId;
    private String currencyName;
    
    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isWithRegion() {
        return withRegion;
    }

    public void setWithRegion(Boolean withRegion) {
        this.withRegion = withRegion;
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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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

        CCountryDTO cCountryDTO = (CCountryDTO) o;
        if (cCountryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cCountryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CCountryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", withRegion='" + isWithRegion() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", currencyId='" + getCurrencyId() + "'" +
            ", currencyName='" + getCurrencyName() + "'" +
            ", adOrganizationId='" + getAdOrganizationId() + "'" +
            ", adOrganizationName=" + getAdOrganizationName() +
            "}";
    }

    

    
}
