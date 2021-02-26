package com.bhp.opusb.service.dto;

import com.bhp.opusb.domain.MRequisitionLine;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MRequisition} entity.
 */
public class MRequisitionDTO extends AbstractAuditingDTO {

    private Long id;

    private LocalDate dateTrx;

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction;

    @NotNull
    @Size(max = 10)
    private String documentStatus;

    private Boolean approved;

    private Boolean processed;

    private LocalDate datePromised;

    private String description;

    private UUID uid;

    private Boolean active;

    private List<MRequisitionLine> mRequisitionLineList;

    @Override
    public String toString() {
        return "MRequisitionDTO{" +
            "id=" + id +
            ", dateTrx=" + dateTrx +
            ", documentNo='" + documentNo + '\'' +
            ", documentAction='" + documentAction + '\'' +
            ", documentStatus='" + documentStatus + '\'' +
            ", approved=" + approved +
            ", processed=" + processed +
            ", datePromised=" + datePromised +
            ", description='" + description + '\'' +
            ", uid=" + uid +
            ", active=" + active +
            ", mRequisitionLineList=" + mRequisitionLineList +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", documentTypeId=" + documentTypeId +
            ", documentTypeName='" + documentTypeName + '\'' +
            ", currencyId=" + currencyId +
            ", currencyName='" + currencyName + '\'' +
            ", warehouseId=" + warehouseId +
            ", warehouseName='" + warehouseName + '\'' +
            ", costCenterId=" + costCenterId +
            ", costCenterName='" + costCenterName + '\'' +
            '}';
    }

    public List<MRequisitionLine> getmRequisitionLineList() {
        return mRequisitionLineList;
    }

    public void setmRequisitionLineList(List<MRequisitionLine> mRequisitionLineList) {
        this.mRequisitionLineList = mRequisitionLineList;
    }

    /**
     * Purchase Requisition
     */
    @ApiModelProperty(value = "Purchase Requisition")

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long documentTypeId;
    private String documentTypeName;

    private Long currencyId;
    private String currencyName;

    private Long warehouseId;
    private String warehouseName;

    private Long costCenterId;
    private String costCenterName;

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MRequisitionDTO mRequisitionDTO = (MRequisitionDTO) o;
        if (mRequisitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mRequisitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
