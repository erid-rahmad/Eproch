package com.bhp.opusb.service.dto;

import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.domain.MSubmissionSubItem;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingSubmission} entity.
 */
public class MBiddingSubmissionDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String joinBidding;

    @NotNull
    private BigDecimal proposedPrice;

    @NotNull
    private BigDecimal ceilingPrice;

    private UUID uid;

    private Boolean active;


    private Long biddingId;

    private Long biddingScheduleId;

    private Long vendorId;

    private Long adOrganizationId;

    private List<MBiddingSubmissionLineDTO> mBiddingSubmissionLineList ;

    private List<MSubmissionSubItemDTO> mSubmissionSubItemList;

    @Override
    public String toString() {
        return "MBiddingSubmissionDTO{" +
            "id=" + id +
            ", joinBidding='" + joinBidding + '\'' +
            ", proposedPrice=" + proposedPrice +
            ", ceilingPrice=" + ceilingPrice +
            ", uid=" + uid +
            ", active=" + active +
            ", biddingId=" + biddingId +
            ", biddingScheduleId=" + biddingScheduleId +
            ", vendorId=" + vendorId +
            ", adOrganizationId=" + adOrganizationId +
            ", mBiddingSubmissionLineList=" + mBiddingSubmissionLineList +
            ", mSubmissionSubItemList=" + mSubmissionSubItemList +
            '}';
    }

    public List<MBiddingSubmissionLineDTO> getmBiddingSubmissionLineList() {
        return mBiddingSubmissionLineList;
    }

    public void setmBiddingSubmissionLineList(List<MBiddingSubmissionLineDTO> mBiddingSubmissionLineList) {
        this.mBiddingSubmissionLineList = mBiddingSubmissionLineList;
    }

    public List<MSubmissionSubItemDTO> getmSubmissionSubItemList() {
        return mSubmissionSubItemList;
    }

    public void setmSubmissionSubItemList(List<MSubmissionSubItemDTO> mSubmissionSubItemList) {
        this.mSubmissionSubItemList = mSubmissionSubItemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJoinBidding() {
        return joinBidding;
    }

    public void setJoinBidding(String joinBidding) {
        this.joinBidding = joinBidding;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
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

    public Long getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(Long mBiddingScheduleId) {
        this.biddingScheduleId = mBiddingScheduleId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingSubmissionDTO)) {
            return false;
        }

        return id != null && id.equals(((MBiddingSubmissionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
