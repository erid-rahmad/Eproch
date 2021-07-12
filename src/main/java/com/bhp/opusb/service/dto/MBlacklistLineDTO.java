package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBlacklistLine} entity.
 */
public class MBlacklistLineDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @Lob
    private String notes;


    private Long blacklistId;

    private Long adOrganizationId;

    private Long picId;
    private String picName, picFirstName, picLastName, functionaryName;

    private Long functionaryId;
    
    public Long getId() {
        return id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicLastName() {
        return picLastName;
    }

    public void setPicLastName(String picLastName) {
        this.picLastName = picLastName;
    }

    public String getPicFirstName() {
        return picFirstName;
    }

    public void setPicFirstName(String picFirstName) {
        this.picFirstName = picFirstName;
    }

    public String getFunctionaryName() {
        return functionaryName;
    }

    public void setFunctionaryName(String functionaryName) {
        this.functionaryName = functionaryName;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(Long mBlacklistId) {
        this.blacklistId = mBlacklistId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long adUserId) {
        this.picId = adUserId;
    }

    public Long getFunctionaryId() {
        return functionaryId;
    }

    public void setFunctionaryId(Long cFunctionaryId) {
        this.functionaryId = cFunctionaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBlacklistLineDTO mBlacklistLineDTO = (MBlacklistLineDTO) o;
        if (mBlacklistLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBlacklistLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBlacklistLineDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", notes='" + getNotes() + "'" +
            ", blacklistId=" + getBlacklistId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", picId=" + getPicId() +
            ", functionaryId=" + getFunctionaryId() +
            "}";
    }
}
