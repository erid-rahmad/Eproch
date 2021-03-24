package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingSchedule} entity.
 */
public class MBiddingScheduleDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private UUID uid;

    private Boolean active;


    private Long biddingId;
    private String biddingNo;
    private String biddingTitle;

    private Long adOrganizationId;
    private Long adOrganizationName;

    private Long eventTypeLineId;
    private String eventTypeLineName;
    
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

    public String getBiddingNo() {
        return biddingNo;
    }

    public void setBiddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
    }

    public String getBiddingTitle() {
        return biddingTitle;
    }

    public void setBiddingTitle(String biddingTitle) {
        this.biddingTitle = biddingTitle;
    }

    public String getBiddingName() {
        return biddingNo + " - " + biddingTitle;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(Long adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getEventTypeLineId() {
        return eventTypeLineId;
    }

    public void setEventTypeLineId(Long cEventTypelineId) {
        this.eventTypeLineId = cEventTypelineId;
    }

    public String getEventTypeLineName() {
        return eventTypeLineName;
    }

    public void setEventTypeLineName(String eventTypeLineName) {
        this.eventTypeLineName = eventTypeLineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingScheduleDTO mBiddingScheduleDTO = (MBiddingScheduleDTO) o;
        if (mBiddingScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingScheduleDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingId=" + getBiddingId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", eventTypeLineId=" + getEventTypeLineId() +
            "}";
    }
}
