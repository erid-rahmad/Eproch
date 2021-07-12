package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.List;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBlacklist} entity.
 */
public class MBlacklistDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private LocalDate reportDate;

    @Lob
    private String notes;

    @Size(max = 10)
    private String type;

    @NotNull
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


    private Long attachmentId;
    private String fileName, downloadUrl;

    private Long adOrganizationId;

    private Long vendorId;
    private String vendorName;

    private Long businessCategoryId;

    private Long subBusinessCategoryId;

    private Integer blacklistedPersonalCount;

    private List<MBlacklistLineDTO> lines, users, shareholders;
    private List<Long> deleteLineIds;
    
    public Long getId() {
        return id;
    }

    public Integer getBlacklistedPersonalCount() {
        return blacklistedPersonalCount;
    }

    public void setBlacklistedPersonalCount(Integer blacklistedPersonalCount) {
        this.blacklistedPersonalCount = blacklistedPersonalCount;
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

    public List<Long> getDeleteLineIds() {
        return deleteLineIds;
    }

    public void setDeleteLineIds(List<Long> deleteLineIds) {
        this.deleteLineIds = deleteLineIds;
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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public Long getSubBusinessCategoryId() {
        return subBusinessCategoryId;
    }

    public void setSubBusinessCategoryId(Long cBusinessCategoryId) {
        this.subBusinessCategoryId = cBusinessCategoryId;
    }

    public List<MBlacklistLineDTO> getLines(){
        return lines;
    }
    public List<MBlacklistLineDTO> getUsers(){
        return users;
    }
    public List<MBlacklistLineDTO> getShareholders(){
        return shareholders;
    }
    public void setLines(List<MBlacklistLineDTO> lines){
        this.lines = lines;
    }
    public void setUsers(List<MBlacklistLineDTO> users){
        this.users = users;
    }
    public void setShareholders(List<MBlacklistLineDTO> shareholders){
        this.shareholders = shareholders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBlacklistDTO mBlacklistDTO = (MBlacklistDTO) o;
        if (mBlacklistDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBlacklistDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBlacklistDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", type='" + getType() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", attachmentId=" + getAttachmentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", vendorId=" + getVendorId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", subBusinessCategoryId=" + getSubBusinessCategoryId() +
            "}";
    }
}
