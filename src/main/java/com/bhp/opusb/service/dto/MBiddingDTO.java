package com.bhp.opusb.service.dto;

import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.domain.MProjectInformation;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBidding} entity.
 */
public class MBiddingDTO extends AbstractAuditingDTO {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String biddingNo;

    @NotNull
    private String name;

    private String vendorSelection;

    private BigDecimal ceilingPrice;

    private BigDecimal estimatedPrice;

    @NotNull
    @Size(max = 10)
    private String documentAction;

    @NotNull
    @Size(max = 10)
    private String documentStatus;

    private Boolean approved;

    private Boolean processed;

    private LocalDate dateReject;

    private LocalDate dateApprove;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long costCenterId;
    private String costCenterName;

    private Long requisitionId;

    private Long biddingTypeId;
    private String biddingTypeName;

    private Long eventTypeId;
    private String eventTypeName;

    private Long adUserUserId;
    private String adUserUserName;

    private List<MBiddingLine> biddingLineList;

    private List<MProjectInformation> projectInformationList;

    @Override
    public String toString() {
        return "MBiddingDTO{" +
            "id=" + id +
            ", biddingNo='" + biddingNo + '\'' +
            ", name='" + name + '\'' +
            ", vendorSelection='" + vendorSelection + '\'' +
            ", ceilingPrice=" + ceilingPrice +
            ", estimatedPrice=" + estimatedPrice +
            ", documentAction='" + documentAction + '\'' +
            ", documentStatus='" + documentStatus + '\'' +
            ", approved=" + approved +
            ", processed=" + processed +
            ", dateReject=" + dateReject +
            ", dateApprove=" + dateApprove +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", costCenterId=" + costCenterId +
            ", costCenterName='" + costCenterName + '\'' +
            ", requisitionId=" + requisitionId +
            ", biddingTypeId=" + biddingTypeId +
            ", biddingTypeName='" + biddingTypeName + '\'' +
            ", eventTypeId=" + eventTypeId +
            ", eventTypeName='" + eventTypeName + '\'' +
            ", adUserUserId=" + adUserUserId +
            ", adUserUserName='" + adUserUserName + '\'' +
            ", biddingLineList=" + biddingLineList +
            ", projectInformationList=" + projectInformationList +
            '}';
    }

    public List<MProjectInformation> getProjectInformationList() {
        return projectInformationList;
    }

    public void setProjectInformationList(List<MProjectInformation> projectInformationList) {
        this.projectInformationList = projectInformationList;
    }

    public List<MBiddingLine> getBiddingLineList() {
        return biddingLineList;
    }

    public void setBiddingLineList(List<MBiddingLine> biddingLineList) {
        this.biddingLineList = biddingLineList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiddingNo() {
        return biddingNo;
    }

    public void setBiddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorSelection() {
        return vendorSelection;
    }

    public void setVendorSelection(String vendorSelection) {
        this.vendorSelection = vendorSelection;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(BigDecimal estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
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

    public LocalDate getDateReject() {
        return dateReject;
    }

    public void setDateReject(LocalDate dateReject) {
        this.dateReject = dateReject;
    }

    public LocalDate getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
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

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long mRequisitionId) {
        this.requisitionId = mRequisitionId;
    }

    public Long getBiddingTypeId() {
        return biddingTypeId;
    }

    public void setBiddingTypeId(Long cBiddingTypeId) {
        this.biddingTypeId = cBiddingTypeId;
    }

    public String getBiddingTypeName() {
        return biddingTypeName;
    }

    public void setBiddingTypeName(String biddingTypeName) {
        this.biddingTypeName = biddingTypeName;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long cEventTypeId) {
        this.eventTypeId = cEventTypeId;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public Long getAdUserUserId() {
        return adUserUserId;
    }

    public void setAdUserUserId(Long adUserUserId) {
        this.adUserUserId = adUserUserId;
    }

    public String getAdUserUserName() {
        return adUserUserName;
    }

    public void setAdUserUserName(String adUserUserName) {
        this.adUserUserName = adUserUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingDTO mBiddingDTO = (MBiddingDTO) o;
        if (mBiddingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
