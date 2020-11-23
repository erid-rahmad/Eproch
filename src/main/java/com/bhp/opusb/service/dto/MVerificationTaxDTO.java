package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVerificationTax} entity.
 */
public class MVerificationTaxDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Long taxPeriod;

    @NotNull
    private String traxCode;

    @NotNull
    private String statusCode;

    @NotNull
    private String docCode;

    @NotNull
    private Long year;

    private String returnDocType;

    private String repSerNo;

    private String taxExpCode;

    private LocalDate dateSSP;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long currencyId;
    private String currencyName;

    private Long taxCategoryId;
    private String taxCategoryName;

    private Long costCenterId;
    private String costCenterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxPeriod() {
        return taxPeriod;
    }

    public void setTaxPeriod(Long taxPeriod) {
        this.taxPeriod = taxPeriod;
    }

    public String getTraxCode() {
        return traxCode;
    }

    public void setTraxCode(String traxCode) {
        this.traxCode = traxCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getReturnDocType() {
        return returnDocType;
    }

    public void setReturnDocType(String returnDocType) {
        this.returnDocType = returnDocType;
    }

    public String getRepSerNo() {
        return repSerNo;
    }

    public void setRepSerNo(String repSerNo) {
        this.repSerNo = repSerNo;
    }

    public String getTaxExpCode() {
        return taxExpCode;
    }

    public void setTaxExpCode(String taxExpCode) {
        this.taxExpCode = taxExpCode;
    }

    public LocalDate getDateSSP() {
        return dateSSP;
    }

    public void setDateSSP(LocalDate dateSSP) {
        this.dateSSP = dateSSP;
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

    public String getCurrencyName() {
        return currencyName;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long cTaxCategoryId) {
        this.taxCategoryId = cTaxCategoryId;
    }

    public String getTaxCategoryName() {
        return taxCategoryName;
    }

    public void setTaxCategoryName(String taxCategoryName) {
        this.taxCategoryName = taxCategoryName;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long cCostCenterId) {
        this.costCenterId = cCostCenterId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVerificationTaxDTO mVerificationTaxDTO = (MVerificationTaxDTO) o;
        if (mVerificationTaxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVerificationTaxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVerificationTaxDTO{" +
            "id=" + getId() +
            ", taxPeriod=" + getTaxPeriod() +
            ", traxCode='" + getTraxCode() + "'" +
            ", statusCode='" + getStatusCode() + "'" +
            ", docCode='" + getDocCode() + "'" +
            ", year=" + getYear() +
            ", returnDocType='" + getReturnDocType() + "'" +
            ", repSerNo='" + getRepSerNo() + "'" +
            ", taxExpCode='" + getTaxExpCode() + "'" +
            ", dateSSP='" + getDateSSP() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", currencyId=" + getCurrencyId() +
            ", currencyName=" + getCurrencyName() +
            ", taxCategoryId=" + getTaxCategoryId() +
            ", taxCategoryName=" + getTaxCategoryName() +
            ", costCenterId=" + getCostCenterId() +
            ", costCenterName=" + getCostCenterName() +
            "}";
    }
}
