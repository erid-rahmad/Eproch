package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MDocumentSchedule} entity.
 */
public class MDocumentScheduleDTO extends AbstractAuditingDTO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String docEvent;

    private UUID uid;

    private Boolean active;


    private Long biddingId;
    private String biddingName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long vendorSubmissionId;

    private Long vendorEvaluationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocEvent() {
        return docEvent;
    }

    public void setDocEvent(String docEvent) {
        this.docEvent = docEvent;
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

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
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

    public Long getVendorSubmissionId() {
        return vendorSubmissionId;
    }

    public void setVendorSubmissionId(Long mBiddingScheduleId) {
        this.vendorSubmissionId = mBiddingScheduleId;
    }

    public Long getVendorEvaluationId() {
        return vendorEvaluationId;
    }

    public void setVendorEvaluationId(Long mBiddingScheduleId) {
        this.vendorEvaluationId = mBiddingScheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MDocumentScheduleDTO mDocumentScheduleDTO = (MDocumentScheduleDTO) o;
        if (mDocumentScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mDocumentScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MDocumentScheduleDTO{" +
            "id=" + getId() +
            ", docEvent='" + getDocEvent() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingId=" + getBiddingId() +
            ", biddingName=" + getBiddingName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", vendorSubmissionId=" + getVendorSubmissionId() +
            ", vendorEvaluationId=" + getVendorEvaluationId() +
            "}";
    }
}
