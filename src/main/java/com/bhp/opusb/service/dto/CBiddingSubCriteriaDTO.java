package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBiddingSubCriteria} entity.
 */
public class CBiddingSubCriteriaDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingCriteriaId;
    private String biddingCriteriaName;

    private Long adUserUserId;
    private String adUserUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(Long cBiddingCriteriaId) {
        this.biddingCriteriaId = cBiddingCriteriaId;
    }

    public String getBiddingCriteriaName() {
        return biddingCriteriaName;
    }

    public void setBiddingCriteriaName(String biddingCriteriaName) {
        this.biddingCriteriaName = biddingCriteriaName;
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

        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO = (CBiddingSubCriteriaDTO) o;
        if (cBiddingSubCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBiddingSubCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBiddingSubCriteriaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", biddingCriteriaId=" + getBiddingCriteriaId() +
            ", biddingCriteriaName=" + getBiddingCriteriaName() +
            ", adUserUserId=" + getAdUserUserId() +
            ", adUserUserName=" + getAdUserUserName() +
            "}";
    }
}
