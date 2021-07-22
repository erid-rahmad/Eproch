package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationSubmission} entity.
 */
public class MPrequalificationSubmissionDTO extends AbstractAuditingDTO {
    
    private Long id;

    private Boolean joined;

    private ZonedDateTime dateTrx;

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction;

    @NotNull
    @Size(max = 12)
    private String documentStatus;

    private Boolean approved;

    private Boolean processed;

    private ZonedDateTime dateApprove;

    private ZonedDateTime dateReject;

    private String rejectedReason;

    private ZonedDateTime dateSubmit;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long prequalificationId;
    private String prequalificationName;
    private String prequalificationNo;
    private String prequalificationType;
    private String preqEventName;
    private String prequalificationStatus;
    private Long preqMethodId;

    private Long documentTypeId;
    private String documentTypeName;

    private Long vendorId;
    private String vendorName;
    
    public Long getId() {
        return id;
    }

    public Long getPreqMethodId() {
        return preqMethodId;
    }

    public void setPreqMethodId(Long preqMethodId) {
        this.preqMethodId = preqMethodId;
    }

    public String getPreqEventName() {
        return preqEventName;
    }

    public void setPreqEventName(String preqEventName) {
        this.preqEventName = preqEventName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getPrequalificationStatus() {
        return prequalificationStatus;
    }

    public void setPrequalificationStatus(String prequalificationStatus) {
        this.prequalificationStatus = prequalificationStatus;
    }

    public String getPrequalificationNo() {
        return prequalificationNo;
    }

    public void setPrequalificationNo(String prequalificationNo) {
        this.prequalificationNo = prequalificationNo;
    }

    public String getPrequalificationType() {
        return prequalificationType;
    }

    public void setPrequalificationType(String prequalificationType) {
        this.prequalificationType = prequalificationType;
    }

    public String getPrequalificationName() {
        return prequalificationName;
    }

    public void setPrequalificationName(String prequalificationName) {
        this.prequalificationName = prequalificationName;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public ZonedDateTime getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(ZonedDateTime dateTrx) {
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

    public ZonedDateTime getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTime getDateReject() {
        return dateReject;
    }

    public void setDateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public ZonedDateTime getDateSubmit() {
        return dateSubmit;
    }

    public void setDateSubmit(ZonedDateTime dateSubmit) {
        this.dateSubmit = dateSubmit;
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

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long cDocumentTypeId) {
        this.documentTypeId = cDocumentTypeId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO = (MPrequalificationSubmissionDTO) o;
        if (mPrequalificationSubmissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationSubmissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationSubmissionDTO{" +
            "id=" + getId() +
            ", joined='" + isJoined() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", dateSubmit='" + getDateSubmit() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalificationId=" + getPrequalificationId() +
            ", documentTypeId=" + getDocumentTypeId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}
