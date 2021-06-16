package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bhp.opusb.util.DocumentUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPurchaseOrder} entity.
 */
public class MPurchaseOrderDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate dateTrx = LocalDate.now();

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction = DocumentUtil.STATUS_SUBMIT;

    @NotNull
    @Size(max = 10)
    private String documentStatus = DocumentUtil.STATUS_DRAFT;

    private Boolean approved = false;

    private Boolean processed = false;

    private BigDecimal grandTotal;

    private Boolean tax = false;

    private LocalDate datePromised;

    private String description;

    private UUID uid;

    private Boolean active = true;

    /**
     * Purchase Order
     */
    @ApiModelProperty(value = "Purchase Order")

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long documentTypeId;
    private String documentTypeName;

    private Long vendorId;
    private String vendorName;

    private Long currencyId;
    private String currencyName;

    private Long warehouseId;
    private String warehouseName;

    private Long costCenterId;
    private String costCenterName;

    private Long paymentTermId;
    private String paymentTermName;

    private List<MRequisitionLineDTO> requisitionLines = new ArrayList<>();

    private List<MPurchaseOrderLineDTO> poLines = new ArrayList<>();
    private Long biddingId;
    
    public Long getId() {
        return id;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long biddingId) {
        this.biddingId = biddingId;
    }

    public List<MPurchaseOrderLineDTO> getPoLines() {
        return poLines;
    }

    public void setPoLines(List<MPurchaseOrderLineDTO> poLines) {
        this.poLines = poLines;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getName() {
        return getDocumentNo();
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Boolean isTax() {
        return tax;
    }

    public void setTax(Boolean tax) {
        this.tax = tax;
    }

    public LocalDate getDatePromised() {
        return datePromised;
    }

    public void setDatePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long cDocumentTypeId) {
        this.documentTypeId = cDocumentTypeId;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long cWarehouseId) {
        this.warehouseId = cWarehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public Long getPaymentTermId() {
        return paymentTermId;
    }

    public void setPaymentTermId(Long cPaymentTermId) {
        this.paymentTermId = cPaymentTermId;
    }

    public String getPaymentTermName() {
        return paymentTermName;
    }

    public void setPaymentTermName(String paymentTermName) {
        this.paymentTermName = paymentTermName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPurchaseOrderDTO mPurchaseOrderDTO = (MPurchaseOrderDTO) o;
        if (mPurchaseOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPurchaseOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPurchaseOrderDTO{" +
            "id=" + getId() +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", grandTotal=" + getGrandTotal() +
            ", tax='" + isTax() + "'" +
            ", datePromised='" + getDatePromised() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", documentTypeId=" + getDocumentTypeId() +
            ", vendorId=" + getVendorId() +
            ", currencyId=" + getCurrencyId() +
            ", warehouseId=" + getWarehouseId() +
            ", costCenterId=" + getCostCenterId() +
            ", paymentTermId=" + getPaymentTermId() +
            "}";
    }

    public List<MRequisitionLineDTO> getRequisitionLines() {
        return requisitionLines;
    }

    public void setRequisitionLines(List<MRequisitionLineDTO> requisitionLines) {
        this.requisitionLines = requisitionLines;
    }
}
