package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBudgetPlan} entity.
 */
public class CBudgetPlanDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    /**
     * Deduct from purchase requisition, purchase order, and/or bidding result
     */
    @ApiModelProperty(value = "Deduct from purchase requisition, purchase order, and/or bidding result")
    private String budgetDeduction;

    @NotNull
    private BigDecimal budgetAmount;

    @NotNull
    private BigDecimal amountAvailable;

    @NotNull
    private LocalDate documentDate;

    private Boolean preventOverBudget;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    @JsonProperty("cCostCenterId")
    private Long cCostCenterId;

    @JsonProperty("cCostCenterName")
    private String cCostCenterName;

    @JsonProperty("cCurrencyId")
    private Long cCurrencyId;

    @JsonProperty("cCurrencyName")
    private String cCurrencyName;
    
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

    public String getBudgetDeduction() {
        return budgetDeduction;
    }

    public void setBudgetDeduction(String budgetDeduction) {
        this.budgetDeduction = budgetDeduction;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(BigDecimal amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public Boolean isPreventOverBudget() {
        return preventOverBudget;
    }

    public void setPreventOverBudget(Boolean preventOverBudget) {
        this.preventOverBudget = preventOverBudget;
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

    public Long getCCostCenterId() {
        return cCostCenterId;
    }

    public void setCCostCenterId(Long cCostCenterId) {
        this.cCostCenterId = cCostCenterId;
    }

    public String getCCostCenterName() {
        return cCostCenterName;
    }

    public void setCCostCenterName(String cCostCenterName) {
        this.cCostCenterName = cCostCenterName;
    }

    public Long getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(Long cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }

    public String getCCurrencyName() {
        return cCurrencyName;
    }

    public void setCCurrencyName(String cCurrencyName) {
        this.cCurrencyName = cCurrencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CBudgetPlanDTO cBudgetPlanDTO = (CBudgetPlanDTO) o;
        if (cBudgetPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBudgetPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBudgetPlanDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", budgetDeduction='" + getBudgetDeduction() + "'" +
            ", budgetAmount=" + getBudgetAmount() +
            ", amountAvailable=" + getAmountAvailable() +
            ", documentDate='" + getDocumentDate() + "'" +
            ", preventOverBudget='" + isPreventOverBudget() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cCostCenterId=" + getCCostCenterId() +
            ", cCurrencyId=" + getCCurrencyId() +
            "}";
    }
}
