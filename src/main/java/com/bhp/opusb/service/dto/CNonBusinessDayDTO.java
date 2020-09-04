package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CNonBusinessDay} entity.
 */
public class CNonBusinessDayDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String category;

    @NotNull
    private LocalDate date;

    private String description;

    private UUID uid;

    private Boolean active;


    private Long calendarId;
    private String calendarName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long cCalendarId) {
        this.calendarId = cCalendarId;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
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

        CNonBusinessDayDTO cNonBusinessDayDTO = (CNonBusinessDayDTO) o;
        if (cNonBusinessDayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cNonBusinessDayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CNonBusinessDayDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", calendarId=" + getCalendarId() +
            ", calendarName='" + getCalendarName() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName='" + getAdOrganizationName() + "'" +
            "}";
    }

    
}
