package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CCurrency} entity.
 */
public class CCurrencyDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Z]{3}$")
    private String code;

    @NotNull
    private String symbol;

    @NotNull
    private String name;

    private Boolean active;

    
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CCurrencyDTO cCurrencyDTO = (CCurrencyDTO) o;
        if (cCurrencyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cCurrencyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CCurrencyDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
