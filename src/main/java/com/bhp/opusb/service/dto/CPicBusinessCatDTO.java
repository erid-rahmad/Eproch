package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPicBusinessCat} entity.
 */
public class CPicBusinessCatDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;


    private Long picId;
    private String picUserLogin;

    private Long businessCategoryId;
    private String businessCategoryName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
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

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long adUserId) {
        this.picId = adUserId;
    }

    public String getPicUserLogin() {
        return picUserLogin;
    }

    public void setPicUserLogin(String picUserLogin) {
        this.picUserLogin = picUserLogin;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
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

        CPicBusinessCatDTO cPicBusinessCatDTO = (CPicBusinessCatDTO) o;
        if (cPicBusinessCatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPicBusinessCatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPicBusinessCatDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", picId=" + getPicId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
