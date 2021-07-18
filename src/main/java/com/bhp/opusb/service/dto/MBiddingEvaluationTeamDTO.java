package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingEvaluationTeam} entity.
 */
public class MBiddingEvaluationTeamDTO extends AbstractAuditingDTO {
    
    private Long id;

    private String status;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingId;
    private String biddingNo, biddingName, biddingType, biddingEventType, biddingCostCenter;

    private Long prequalificationId;
    private String prequalificationNo, prequalificationName, prequalificationEventType;

    private List<MBiddingEvalTeamLineDTO> members = new ArrayList<>(), deletedLines = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public String getPrequalificationEventType() {
        return prequalificationEventType;
    }

    public void setPrequalificationEventType(String prequalificationEventType) {
        this.prequalificationEventType = prequalificationEventType;
    }

    public String getPrequalificationName() {
        return prequalificationName;
    }

    public void setPrequalificationName(String prequalificationName) {
        this.prequalificationName = prequalificationName;
    }

    public String getPrequalificationNo() {
        return prequalificationNo;
    }

    public void setPrequalificationNo(String prequalificationNo) {
        this.prequalificationNo = prequalificationNo;
    }

    public String getBiddingCostCenter() {
        return biddingCostCenter;
    }

    public void setBiddingCostCenter(String biddingCostCenter) {
        this.biddingCostCenter = biddingCostCenter;
    }

    public String getBiddingEventType() {
        return biddingEventType;
    }

    public void setBiddingEventType(String biddingEventType) {
        this.biddingEventType = biddingEventType;
    }

    public String getBiddingType() {
        return biddingType;
    }

    public void setBiddingType(String biddingType) {
        this.biddingType = biddingType;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
    }

    public String getBiddingNo() {
        return biddingNo;
    }

    public void setBiddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    public List<MBiddingEvalTeamLineDTO> getMembers(){
        return members;
    }

    public void setMembers(List<MBiddingEvalTeamLineDTO> members){
        this.members = members;
    }

    public List<MBiddingEvalTeamLineDTO> getDeletedLines(){
        return deletedLines;
    }

    public void setDeletedLines(List<MBiddingEvalTeamLineDTO> deletedLines){
        this.deletedLines = deletedLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO = (MBiddingEvaluationTeamDTO) o;
        if (mBiddingEvaluationTeamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingEvaluationTeamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingEvaluationTeamDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingId=" + getBiddingId() +
            ", prequalificationId=" + getPrequalificationId() +
            "}";
    }
}
