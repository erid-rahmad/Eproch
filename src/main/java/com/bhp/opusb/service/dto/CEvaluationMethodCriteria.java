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
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CEvaluationMethod} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CEvaluationMethodResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-evaluation-methods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CEvaluationMethodCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BigDecimalFilter priceLimit;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingTypeId;

    private LongFilter eventTypeId;

    public CEvaluationMethodCriteria() {
    }

    public CEvaluationMethodCriteria(CEvaluationMethodCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.priceLimit = other.priceLimit == null ? null : other.priceLimit.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingTypeId = other.biddingTypeId == null ? null : other.biddingTypeId.copy();
        this.eventTypeId = other.eventTypeId == null ? null : other.eventTypeId.copy();
    }

    @Override
    public CEvaluationMethodCriteria copy() {
        return new CEvaluationMethodCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BigDecimalFilter getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(BigDecimalFilter priceLimit) {
        this.priceLimit = priceLimit;
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

    public LongFilter getBiddingTypeId() {
        return biddingTypeId;
    }

    public void setBiddingTypeId(LongFilter biddingTypeId) {
        this.biddingTypeId = biddingTypeId;
    }

    public LongFilter getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(LongFilter eventTypeId) {
        this.eventTypeId = eventTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CEvaluationMethodCriteria that = (CEvaluationMethodCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(priceLimit, that.priceLimit) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingTypeId, that.biddingTypeId) &&
            Objects.equals(eventTypeId, that.eventTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        priceLimit,
        uid,
        active,
        adOrganizationId,
        biddingTypeId,
        eventTypeId
        );
    }

    @Override
    public String toString() {
        return "CEvaluationMethodCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (priceLimit != null ? "priceLimit=" + priceLimit + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingTypeId != null ? "biddingTypeId=" + biddingTypeId + ", " : "") +
                (eventTypeId != null ? "eventTypeId=" + eventTypeId + ", " : "") +
            "}";
    }

}
