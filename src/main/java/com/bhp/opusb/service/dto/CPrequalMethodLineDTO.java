package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPrequalMethodLine} entity.
 */
public class CPrequalMethodLineDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 10)
    private String type;

    @DecimalMax(value = "100")
    private BigDecimal weight;

    private BigDecimal passingGrade;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long prequalMethodId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPassingGrade() {
        return passingGrade;
    }

    public void setPassingGrade(BigDecimal passingGrade) {
        this.passingGrade = passingGrade;
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

    public Long getPrequalMethodId() {
        return prequalMethodId;
    }

    public void setPrequalMethodId(Long cPrequalificationMethodId) {
        this.prequalMethodId = cPrequalificationMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPrequalMethodLineDTO cPrequalMethodLineDTO = (CPrequalMethodLineDTO) o;
        if (cPrequalMethodLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPrequalMethodLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPrequalMethodLineDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", weight=" + getWeight() +
            ", passingGrade=" + getPassingGrade() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalMethodId=" + getPrequalMethodId() +
            "}";
    }
}
