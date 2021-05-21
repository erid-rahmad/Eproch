package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationDateSet} entity.
 */
public class MPrequalificationDateSetDTO extends AbstractAuditingDTO {
    
    private Long id;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    @Size(max = 10)
    private String status = "N";

    private UUID uid;

    private Boolean active = true;


    private Long biddingScheduleId;
    private String biddingScheduleName;
    private Integer sequence;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(Long mBiddingScheduleId) {
        this.biddingScheduleId = mBiddingScheduleId;
    }

    public String getBiddingScheduleName() {
        return biddingScheduleName;
    }

    public void setBiddingScheduleName(String biddingScheduleName) {
        this.biddingScheduleName = biddingScheduleName;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationDateSetDTO mPrequalificationDateSetDTO = (MPrequalificationDateSetDTO) o;
        if (mPrequalificationDateSetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationDateSetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationDateSetDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingScheduleId=" + getBiddingScheduleId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
