package com.bhp.opusb.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MAuctionRule.
 */
@Entity
@Table(name = "m_auction_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MAuctionRule extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Allow vendors to place a bid during preview period.\nAvailable options are:\n1. Allow Pre-bids\n2. Don't allow Pre-bids\n3. Require Pre-bids
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "bid_prev_period", length = 10, nullable = false)
    private String bidPrevPeriod;

    /**
     * This field is enabled if bidPrePeriod = 1 or 3.
     */
    @Column(name = "pre_bid_end_date")
    private ZonedDateTime preBidEndDate;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    /**
     * The amount of time required for the first lot, in minutes.
     */
    @Column(name = "first_lot_run_time")
    private Integer firstLotRunTime;

    @Column(name = "bid_rank_overtime")
    private Integer bidRankOvertime;

    @Column(name = "start_overtime_within")
    private Integer startOvertimeWithin;

    @Column(name = "overtime_period")
    private Integer overtimePeriod;

    @Column(name = "est_award_date")
    private LocalDate estAwardDate;

    /**
     * Unit to use for increment/decrement price.\nAvailable options are:\n1. Amount\n2. Percentage
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "bid_improvement_unit", length = 10, nullable = false)
    private String bidImprovementUnit;

    /**
     * Rule to be applied when there are tie bids:\n1. Allow tie bids for all ranks\n2. Break tie bids by submit time\n3. No tie bids\n4. No tie bids for rank 2 or higher\n5. No tie bids for rank 3 or higher
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "tie_bids_rule", length = 10, nullable = false)
    private String tieBidsRule;

    /**
     * Whether or not to show bids between participants.
     */
    @Column(name = "show_response")
    private Boolean showResponse;

    /**
     * Whether or not to show leading bid to all participants
     */
    @Column(name = "show_leader")
    private Boolean showLeader;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionRules")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBidPrevPeriod() {
        return bidPrevPeriod;
    }

    public MAuctionRule bidPrevPeriod(String bidPrevPeriod) {
        this.bidPrevPeriod = bidPrevPeriod;
        return this;
    }

    public void setBidPrevPeriod(String bidPrevPeriod) {
        this.bidPrevPeriod = bidPrevPeriod;
    }

    public ZonedDateTime getPreBidEndDate() {
        return preBidEndDate;
    }

    public MAuctionRule preBidEndDate(ZonedDateTime preBidEndDate) {
        this.preBidEndDate = preBidEndDate;
        return this;
    }

    public void setPreBidEndDate(ZonedDateTime preBidEndDate) {
        this.preBidEndDate = preBidEndDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public MAuctionRule startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getFirstLotRunTime() {
        return firstLotRunTime;
    }

    public MAuctionRule firstLotRunTime(Integer firstLotRunTime) {
        this.firstLotRunTime = firstLotRunTime;
        return this;
    }

    public void setFirstLotRunTime(Integer firstLotRunTime) {
        this.firstLotRunTime = firstLotRunTime;
    }

    public Integer getBidRankOvertime() {
        return bidRankOvertime;
    }

    public MAuctionRule bidRankOvertime(Integer bidRankOvertime) {
        this.bidRankOvertime = bidRankOvertime;
        return this;
    }

    public void setBidRankOvertime(Integer bidRankOvertime) {
        this.bidRankOvertime = bidRankOvertime;
    }

    public Integer getStartOvertimeWithin() {
        return startOvertimeWithin;
    }

    public MAuctionRule startOvertimeWithin(Integer startOvertimeWithin) {
        this.startOvertimeWithin = startOvertimeWithin;
        return this;
    }

    public void setStartOvertimeWithin(Integer startOvertimeWithin) {
        this.startOvertimeWithin = startOvertimeWithin;
    }

    public Integer getOvertimePeriod() {
        return overtimePeriod;
    }

    public MAuctionRule overtimePeriod(Integer overtimePeriod) {
        this.overtimePeriod = overtimePeriod;
        return this;
    }

    public void setOvertimePeriod(Integer overtimePeriod) {
        this.overtimePeriod = overtimePeriod;
    }

    public LocalDate getEstAwardDate() {
        return estAwardDate;
    }

    public MAuctionRule estAwardDate(LocalDate estAwardDate) {
        this.estAwardDate = estAwardDate;
        return this;
    }

    public void setEstAwardDate(LocalDate estAwardDate) {
        this.estAwardDate = estAwardDate;
    }

    public String getBidImprovementUnit() {
        return bidImprovementUnit;
    }

    public MAuctionRule bidImprovementUnit(String bidImprovementUnit) {
        this.bidImprovementUnit = bidImprovementUnit;
        return this;
    }

    public void setBidImprovementUnit(String bidImprovementUnit) {
        this.bidImprovementUnit = bidImprovementUnit;
    }

    public String getTieBidsRule() {
        return tieBidsRule;
    }

    public MAuctionRule tieBidsRule(String tieBidsRule) {
        this.tieBidsRule = tieBidsRule;
        return this;
    }

    public void setTieBidsRule(String tieBidsRule) {
        this.tieBidsRule = tieBidsRule;
    }

    public Boolean isShowResponse() {
        return showResponse;
    }

    public MAuctionRule showResponse(Boolean showResponse) {
        this.showResponse = showResponse;
        return this;
    }

    public void setShowResponse(Boolean showResponse) {
        this.showResponse = showResponse;
    }

    public Boolean isShowLeader() {
        return showLeader;
    }

    public MAuctionRule showLeader(Boolean showLeader) {
        this.showLeader = showLeader;
        return this;
    }

    public void setShowLeader(Boolean showLeader) {
        this.showLeader = showLeader;
    }

    public UUID getUid() {
        return uid;
    }

    public MAuctionRule uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MAuctionRule active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MAuctionRule adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MAuctionRule)) {
            return false;
        }
        return id != null && id.equals(((MAuctionRule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MAuctionRule{" +
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
            "}";
    }
}
