package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBank} entity.
 */
public class CBankDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String value;

    @NotNull
    private String shortName;

    private String description;

    @Pattern(regexp = "^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$")
    private String swiftCode;

    private Boolean active;

    
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CBankDTO cBankDTO = (CBankDTO) o;
        if (cBankDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBankDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBankDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", swiftCode='" + getSwiftCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
