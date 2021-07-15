package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationInformation} entity.
 */
public class MPrequalificationInformationDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    private String name;

    @NotNull
    @Size(max = 10)
    private String type;

    @Lob
    private String announcementText;

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


    private Long attachmentId;
    private String fileName, downloadUrl;

    private Long adOrganizationId;
    
    public Long getId() {
        return id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
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

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long cAttachmentId) {
        this.attachmentId = cAttachmentId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationInformationDTO mPrequalificationInformationDTO = (MPrequalificationInformationDTO) o;
        if (mPrequalificationInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationInformationDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", announcementText='" + getAnnouncementText() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", attachmentId=" + getAttachmentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
