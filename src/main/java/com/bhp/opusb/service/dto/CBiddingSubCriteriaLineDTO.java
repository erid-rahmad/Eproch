package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBiddingSubCriteriaLine} entity.
 */
public class CBiddingSubCriteriaLineDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Integer score;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingSubCriteriaId;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Long getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(Long cBiddingSubCriteriaId) {
        this.biddingSubCriteriaId = cBiddingSubCriteriaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO = (CBiddingSubCriteriaLineDTO) o;
        if (cBiddingSubCriteriaLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBiddingSubCriteriaLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBiddingSubCriteriaLineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", score=" + getScore() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingSubCriteriaId=" + getBiddingSubCriteriaId() +
            "}";
    }
}
