package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorScoring} entity.
 */
public class MVendorScoringDTO implements Serializable {

    private Long id;

    private UUID uid;

    private Boolean active;

    private Long biddingId;
    private String biddingName;

    private Long adOrganizationId;
    private String adOrganizationName;


    private Long evaluationMethodId;
    private String evaluationMethodName;

    private List<MVendorScoringLineDTO> vendorScoringLineDTOList;

    public List<MVendorScoringLineDTO> getVendorScoringLineDTOList() {
        return vendorScoringLineDTOList;
    }

    public void setVendorScoringLineDTOList(List<MVendorScoringLineDTO> vendorScoringLineDTOList) {
        this.vendorScoringLineDTOList = vendorScoringLineDTOList;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public String getEvaluationMethodName() {
        return evaluationMethodName;
    }

    public void setEvaluationMethodName(String evaluationMethodName) {
        this.evaluationMethodName = evaluationMethodName;
    }

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

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getEvaluationMethodId() {
        return evaluationMethodId;
    }

    public void setEvaluationMethodId(Long cEvaluationMethodId) {
        this.evaluationMethodId = cEvaluationMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorScoringDTO mVendorScoringDTO = (MVendorScoringDTO) o;
        if (mVendorScoringDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorScoringDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorScoringDTO{" +
            "id=" + id +
            ", uid=" + uid +
            ", active=" + active +
            ", biddingId=" + biddingId +
            ", biddingName='" + biddingName + '\'' +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", evaluationMethodId=" + evaluationMethodId +
            ", evaluationMethodName='" + evaluationMethodName + '\'' +
            ", vendorScoringLineDTOList=" + vendorScoringLineDTOList +
            '}';
    }
}
