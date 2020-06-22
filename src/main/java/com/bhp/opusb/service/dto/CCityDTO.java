package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CCity} entity.
 */
public class CCityDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String name;

    private Boolean active;


    private Long countryId;
    private String countryName;

    private Long regionId;
    private String regionName;
    
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long cCountryId) {
        this.countryId = cCountryId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long cRegionId) {
        this.regionId = cRegionId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CCityDTO cCityDTO = (CCityDTO) o;
        if (cCityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cCityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CCityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", countryId=" + getCountryId() +
            ", countryName=" + getCountryName() +
            ", regionId=" + getRegionId() +
            ", regionName=" + getRegionName() +
            "}";
    }

    
}
