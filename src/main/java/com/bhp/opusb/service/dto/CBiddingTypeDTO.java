package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBiddingType} entity.
 */
public class CBiddingTypeDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean costEvaluationSelection;

    private Boolean selectedWinner;

    private UUID uid;

    private Boolean active;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long productClassificationId;
    private String productClassificationName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCostEvaluationSelection() {
        return costEvaluationSelection;
    }

    public void setCostEvaluationSelection(Boolean costEvaluationSelection) {
        this.costEvaluationSelection = costEvaluationSelection;
    }

    public Boolean isSelectedWinner() {
        return selectedWinner;
    }

    public void setSelectedWinner(Boolean selectedWinner) {
        this.selectedWinner = selectedWinner;
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

    public Long getProductClassificationId() {
        return productClassificationId;
    }

    public void setProductClassificationId(Long cProductClassificationId) {
        this.productClassificationId = cProductClassificationId;
    }

    public String getProductClassificationName() {
        return productClassificationName;
    }

    public void setProductClassificationName(String productClassificationName) {
        this.productClassificationName = productClassificationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CBiddingTypeDTO cBiddingTypeDTO = (CBiddingTypeDTO) o;
        if (cBiddingTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBiddingTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBiddingTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", costEvaluationSelection='" + isCostEvaluationSelection() + "'" +
            ", selectedWinner='" + isSelectedWinner() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", productClassificationId=" + getProductClassificationId() +
            ", productClassificationName=" + getProductClassificationName() +
            "}";
    }
}
