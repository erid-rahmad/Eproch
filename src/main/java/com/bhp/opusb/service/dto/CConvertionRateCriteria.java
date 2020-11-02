package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CConvertionRate} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CConvertionRateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-convertion-rates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CConvertionRateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter validFrom;

    private LocalDateFilter validTo;

    private BigDecimalFilter rate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter sourceCurrencyId;

    private LongFilter targetCurrencyId;

    private LongFilter convertionTypeId;

    private LongFilter adOrganizationId;

    public CConvertionRateCriteria() {
    }

    public CConvertionRateCriteria(CConvertionRateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.rate = other.rate == null ? null : other.rate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.sourceCurrencyId = other.sourceCurrencyId == null ? null : other.sourceCurrencyId.copy();
        this.targetCurrencyId = other.targetCurrencyId == null ? null : other.targetCurrencyId.copy();
        this.convertionTypeId = other.convertionTypeId == null ? null : other.convertionTypeId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CConvertionRateCriteria copy() {
        return new CConvertionRateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateFilter validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateFilter validTo) {
        this.validTo = validTo;
    }

    public BigDecimalFilter getRate() {
        return rate;
    }

    public void setRate(BigDecimalFilter rate) {
        this.rate = rate;
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

    public LongFilter getSourceCurrencyId() {
        return sourceCurrencyId;
    }

    public void setSourceCurrencyId(LongFilter sourceCurrencyId) {
        this.sourceCurrencyId = sourceCurrencyId;
    }

    public LongFilter getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(LongFilter targetCurrencyId) {
        this.targetCurrencyId = targetCurrencyId;
    }

    public LongFilter getConvertionTypeId() {
        return convertionTypeId;
    }

    public void setConvertionTypeId(LongFilter convertionTypeId) {
        this.convertionTypeId = convertionTypeId;
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
        final CConvertionRateCriteria that = (CConvertionRateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(sourceCurrencyId, that.sourceCurrencyId) &&
            Objects.equals(targetCurrencyId, that.targetCurrencyId) &&
            Objects.equals(convertionTypeId, that.convertionTypeId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        validFrom,
        validTo,
        rate,
        uid,
        active,
        sourceCurrencyId,
        targetCurrencyId,
        convertionTypeId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CConvertionRateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (sourceCurrencyId != null ? "sourceCurrencyId=" + sourceCurrencyId + ", " : "") +
                (targetCurrencyId != null ? "targetCurrencyId=" + targetCurrencyId + ", " : "") +
                (convertionTypeId != null ? "convertionTypeId=" + convertionTypeId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}
