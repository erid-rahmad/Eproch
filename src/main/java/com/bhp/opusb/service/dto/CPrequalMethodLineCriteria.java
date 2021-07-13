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
 * Criteria class for the {@link com.bhp.opusb.domain.CPrequalMethodLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CPrequalMethodLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-prequal-method-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPrequalMethodLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private BigDecimalFilter weight;

    private BigDecimalFilter passingGrade;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter prequalMethodId;

    public CPrequalMethodLineCriteria() {
    }

    public CPrequalMethodLineCriteria(CPrequalMethodLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.passingGrade = other.passingGrade == null ? null : other.passingGrade.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.prequalMethodId = other.prequalMethodId == null ? null : other.prequalMethodId.copy();
    }

    @Override
    public CPrequalMethodLineCriteria copy() {
        return new CPrequalMethodLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public BigDecimalFilter getWeight() {
        return weight;
    }

    public void setWeight(BigDecimalFilter weight) {
        this.weight = weight;
    }

    public BigDecimalFilter getPassingGrade() {
        return passingGrade;
    }

    public void setPassingGrade(BigDecimalFilter passingGrade) {
        this.passingGrade = passingGrade;
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

    public LongFilter getPrequalMethodId() {
        return prequalMethodId;
    }

    public void setPrequalMethodId(LongFilter prequalMethodId) {
        this.prequalMethodId = prequalMethodId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CPrequalMethodLineCriteria that = (CPrequalMethodLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(passingGrade, that.passingGrade) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(prequalMethodId, that.prequalMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        weight,
        passingGrade,
        uid,
        active,
        adOrganizationId,
        prequalMethodId
        );
    }

    @Override
    public String toString() {
        return "CPrequalMethodLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (passingGrade != null ? "passingGrade=" + passingGrade + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (prequalMethodId != null ? "prequalMethodId=" + prequalMethodId + ", " : "") +
            "}";
    }

}
