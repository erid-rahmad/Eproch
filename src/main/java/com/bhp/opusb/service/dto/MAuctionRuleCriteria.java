package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionRule} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionRuleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-rules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionRuleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bidPrevPeriod;

    private ZonedDateTimeFilter preBidEndDate;

    private ZonedDateTimeFilter startDate;

    private IntegerFilter firstLotRunTime;

    private IntegerFilter bidRankOvertime;

    private IntegerFilter startOvertimeWithin;

    private IntegerFilter overtimePeriod;

    private LocalDateFilter estAwardDate;

    private StringFilter bidImprovementUnit;

    private StringFilter tieBidsRule;

    private BooleanFilter showResponse;

    private BooleanFilter showLeader;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    public MAuctionRuleCriteria() {
    }

    public MAuctionRuleCriteria(MAuctionRuleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bidPrevPeriod = other.bidPrevPeriod == null ? null : other.bidPrevPeriod.copy();
        this.preBidEndDate = other.preBidEndDate == null ? null : other.preBidEndDate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.firstLotRunTime = other.firstLotRunTime == null ? null : other.firstLotRunTime.copy();
        this.bidRankOvertime = other.bidRankOvertime == null ? null : other.bidRankOvertime.copy();
        this.startOvertimeWithin = other.startOvertimeWithin == null ? null : other.startOvertimeWithin.copy();
        this.overtimePeriod = other.overtimePeriod == null ? null : other.overtimePeriod.copy();
        this.estAwardDate = other.estAwardDate == null ? null : other.estAwardDate.copy();
        this.bidImprovementUnit = other.bidImprovementUnit == null ? null : other.bidImprovementUnit.copy();
        this.tieBidsRule = other.tieBidsRule == null ? null : other.tieBidsRule.copy();
        this.showResponse = other.showResponse == null ? null : other.showResponse.copy();
        this.showLeader = other.showLeader == null ? null : other.showLeader.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public MAuctionRuleCriteria copy() {
        return new MAuctionRuleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBidPrevPeriod() {
        return bidPrevPeriod;
    }

    public void setBidPrevPeriod(StringFilter bidPrevPeriod) {
        this.bidPrevPeriod = bidPrevPeriod;
    }

    public ZonedDateTimeFilter getPreBidEndDate() {
        return preBidEndDate;
    }

    public void setPreBidEndDate(ZonedDateTimeFilter preBidEndDate) {
        this.preBidEndDate = preBidEndDate;
    }

    public ZonedDateTimeFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTimeFilter startDate) {
        this.startDate = startDate;
    }

    public IntegerFilter getFirstLotRunTime() {
        return firstLotRunTime;
    }

    public void setFirstLotRunTime(IntegerFilter firstLotRunTime) {
        this.firstLotRunTime = firstLotRunTime;
    }

    public IntegerFilter getBidRankOvertime() {
        return bidRankOvertime;
    }

    public void setBidRankOvertime(IntegerFilter bidRankOvertime) {
        this.bidRankOvertime = bidRankOvertime;
    }

    public IntegerFilter getStartOvertimeWithin() {
        return startOvertimeWithin;
    }

    public void setStartOvertimeWithin(IntegerFilter startOvertimeWithin) {
        this.startOvertimeWithin = startOvertimeWithin;
    }

    public IntegerFilter getOvertimePeriod() {
        return overtimePeriod;
    }

    public void setOvertimePeriod(IntegerFilter overtimePeriod) {
        this.overtimePeriod = overtimePeriod;
    }

    public LocalDateFilter getEstAwardDate() {
        return estAwardDate;
    }

    public void setEstAwardDate(LocalDateFilter estAwardDate) {
        this.estAwardDate = estAwardDate;
    }

    public StringFilter getBidImprovementUnit() {
        return bidImprovementUnit;
    }

    public void setBidImprovementUnit(StringFilter bidImprovementUnit) {
        this.bidImprovementUnit = bidImprovementUnit;
    }

    public StringFilter getTieBidsRule() {
        return tieBidsRule;
    }

    public void setTieBidsRule(StringFilter tieBidsRule) {
        this.tieBidsRule = tieBidsRule;
    }

    public BooleanFilter getShowResponse() {
        return showResponse;
    }

    public void setShowResponse(BooleanFilter showResponse) {
        this.showResponse = showResponse;
    }

    public BooleanFilter getShowLeader() {
        return showLeader;
    }

    public void setShowLeader(BooleanFilter showLeader) {
        this.showLeader = showLeader;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MAuctionRuleCriteria that = (MAuctionRuleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(bidPrevPeriod, that.bidPrevPeriod) &&
            Objects.equals(preBidEndDate, that.preBidEndDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(firstLotRunTime, that.firstLotRunTime) &&
            Objects.equals(bidRankOvertime, that.bidRankOvertime) &&
            Objects.equals(startOvertimeWithin, that.startOvertimeWithin) &&
            Objects.equals(overtimePeriod, that.overtimePeriod) &&
            Objects.equals(estAwardDate, that.estAwardDate) &&
            Objects.equals(bidImprovementUnit, that.bidImprovementUnit) &&
            Objects.equals(tieBidsRule, that.tieBidsRule) &&
            Objects.equals(showResponse, that.showResponse) &&
            Objects.equals(showLeader, that.showLeader) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        bidPrevPeriod,
        preBidEndDate,
        startDate,
        firstLotRunTime,
        bidRankOvertime,
        startOvertimeWithin,
        overtimePeriod,
        estAwardDate,
        bidImprovementUnit,
        tieBidsRule,
        showResponse,
        showLeader,
        uid,
        active,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "MAuctionRuleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (bidPrevPeriod != null ? "bidPrevPeriod=" + bidPrevPeriod + ", " : "") +
                (preBidEndDate != null ? "preBidEndDate=" + preBidEndDate + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (firstLotRunTime != null ? "firstLotRunTime=" + firstLotRunTime + ", " : "") +
                (bidRankOvertime != null ? "bidRankOvertime=" + bidRankOvertime + ", " : "") +
                (startOvertimeWithin != null ? "startOvertimeWithin=" + startOvertimeWithin + ", " : "") +
                (overtimePeriod != null ? "overtimePeriod=" + overtimePeriod + ", " : "") +
                (estAwardDate != null ? "estAwardDate=" + estAwardDate + ", " : "") +
                (bidImprovementUnit != null ? "bidImprovementUnit=" + bidImprovementUnit + ", " : "") +
                (tieBidsRule != null ? "tieBidsRule=" + tieBidsRule + ", " : "") +
                (showResponse != null ? "showResponse=" + showResponse + ", " : "") +
                (showLeader != null ? "showLeader=" + showLeader + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}
