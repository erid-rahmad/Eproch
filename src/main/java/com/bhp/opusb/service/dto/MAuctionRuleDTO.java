package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionRule} entity.
 */
public class MAuctionRuleDTO extends AbstractAuditingDTO {
    
    private Long id;

    /**
     * Allow vendors to place a bid during preview period.\nAvailable options are:\n1. Allow Pre-bids\n2. Don't allow Pre-bids\n3. Require Pre-bids
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "Allow vendors to place a bid during preview period.\nAvailable options are:\n1. Allow Pre-bids\n2. Don't allow Pre-bids\n3. Require Pre-bids", required = true)
    private String bidPrevPeriod;

    /**
     * This field is enabled if bidPrePeriod = 1 or 3.
     */
    @ApiModelProperty(value = "This field is enabled if bidPrePeriod = 1 or 3.")
    private ZonedDateTime preBidEndDate;

    private ZonedDateTime startDate;

    /**
     * The amount of time required for the first lot, in minutes.
     */
    @ApiModelProperty(value = "The amount of time required for the first lot, in minutes.")
    private Integer firstLotRunTime;

    private Integer bidRankOvertime;

    private Integer startOvertimeWithin;

    private Integer overtimePeriod;

    private LocalDate estAwardDate;

    /**
     * Unit to use for increment/decrement price.\nAvailable options are:\n1. Amount\n2. Percentage
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "Unit to use for increment/decrement price.\nAvailable options are:\n1. Amount\n2. Percentage", required = true)
    private String bidImprovementUnit;

    /**
     * Rule to be applied when there are tie bids:\n1. Allow tie bids for all ranks\n2. Break tie bids by submit time\n3. No tie bids\n4. No tie bids for rank 2 or higher\n5. No tie bids for rank 3 or higher
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "Rule to be applied when there are tie bids:\n1. Allow tie bids for all ranks\n2. Break tie bids by submit time\n3. No tie bids\n4. No tie bids for rank 2 or higher\n5. No tie bids for rank 3 or higher", required = true)
    private String tieBidsRule;

    /**
     * Whether or not to show bids between participants.
     */
    @ApiModelProperty(value = "Whether or not to show bids between participants.")
    private Boolean showResponse;

    /**
     * Whether or not to show leading bid to all participants
     */
    @ApiModelProperty(value = "Whether or not to show leading bid to all participants")
    private Boolean showLeader;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBidPrevPeriod() {
        return bidPrevPeriod;
    }

    public void setBidPrevPeriod(String bidPrevPeriod) {
        this.bidPrevPeriod = bidPrevPeriod;
    }

    public ZonedDateTime getPreBidEndDate() {
        return preBidEndDate;
    }

    public void setPreBidEndDate(ZonedDateTime preBidEndDate) {
        this.preBidEndDate = preBidEndDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getFirstLotRunTime() {
        return firstLotRunTime;
    }

    public void setFirstLotRunTime(Integer firstLotRunTime) {
        this.firstLotRunTime = firstLotRunTime;
    }

    public Integer getBidRankOvertime() {
        return bidRankOvertime;
    }

    public void setBidRankOvertime(Integer bidRankOvertime) {
        this.bidRankOvertime = bidRankOvertime;
    }

    public Integer getStartOvertimeWithin() {
        return startOvertimeWithin;
    }

    public void setStartOvertimeWithin(Integer startOvertimeWithin) {
        this.startOvertimeWithin = startOvertimeWithin;
    }

    public Integer getOvertimePeriod() {
        return overtimePeriod;
    }

    public void setOvertimePeriod(Integer overtimePeriod) {
        this.overtimePeriod = overtimePeriod;
    }

    public LocalDate getEstAwardDate() {
        return estAwardDate;
    }

    public void setEstAwardDate(LocalDate estAwardDate) {
        this.estAwardDate = estAwardDate;
    }

    public String getBidImprovementUnit() {
        return bidImprovementUnit;
    }

    public void setBidImprovementUnit(String bidImprovementUnit) {
        this.bidImprovementUnit = bidImprovementUnit;
    }

    public String getTieBidsRule() {
        return tieBidsRule;
    }

    public void setTieBidsRule(String tieBidsRule) {
        this.tieBidsRule = tieBidsRule;
    }

    public Boolean isShowResponse() {
        return showResponse;
    }

    public void setShowResponse(Boolean showResponse) {
        this.showResponse = showResponse;
    }

    public Boolean isShowLeader() {
        return showLeader;
    }

    public void setShowLeader(Boolean showLeader) {
        this.showLeader = showLeader;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionRuleDTO mAuctionRuleDTO = (MAuctionRuleDTO) o;
        if (mAuctionRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionRuleDTO{" +
            "id=" + getId() +
            ", bidPrevPeriod='" + getBidPrevPeriod() + "'" +
            ", preBidEndDate='" + getPreBidEndDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", firstLotRunTime=" + getFirstLotRunTime() +
            ", bidRankOvertime=" + getBidRankOvertime() +
            ", startOvertimeWithin=" + getStartOvertimeWithin() +
            ", overtimePeriod=" + getOvertimePeriod() +
            ", estAwardDate='" + getEstAwardDate() + "'" +
            ", bidImprovementUnit='" + getBidImprovementUnit() + "'" +
            ", tieBidsRule='" + getTieBidsRule() + "'" +
            ", showResponse='" + isShowResponse() + "'" +
            ", showLeader='" + isShowLeader() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
