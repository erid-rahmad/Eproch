package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvalMethodCriteriaLine} entity.
 */
public class CEvalMethodCriteriaLineDTO implements Serializable {

    private Long id;

    private Integer weight;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingCriteriaId;
    private String biddingCriteriaName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO = (CEvalMethodCriteriaLineDTO) o;
        if (cEvalMethodCriteriaLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEvalMethodCriteriaLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEvalMethodCriteriaLineDTO{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingCriteriaId=" + getBiddingCriteriaId() +
            "}";
    }
}
