package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.CTransactionType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CTax} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CTaxResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-taxes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CTaxCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CTransactionType
     */
    public static class CTransactionTypeFilter extends Filter<CTransactionType> {

        public CTransactionTypeFilter() {
        }

        public CTransactionTypeFilter(CTransactionTypeFilter filter) {
            super(filter);
        }

        @Override
        public CTransactionTypeFilter copy() {
            return new CTransactionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BigDecimalFilter rate;

    private LocalDateFilter validFrom;

    private CTransactionTypeFilter transactionType;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter taxCategoryId;

    public CTaxCriteria() {
    }

    public CTaxCriteria(CTaxCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.rate = other.rate == null ? null : other.rate.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.taxCategoryId = other.taxCategoryId == null ? null : other.taxCategoryId.copy();
    }

    @Override
    public CTaxCriteria copy() {
        return new CTaxCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BigDecimalFilter getRate() {
        return rate;
    }

    public void setRate(BigDecimalFilter rate) {
        this.rate = rate;
    }

    public LocalDateFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateFilter validFrom) {
        this.validFrom = validFrom;
    }

    public CTransactionTypeFilter getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(CTransactionTypeFilter transactionType) {
        this.transactionType = transactionType;
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

    public LongFilter getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(LongFilter taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CTaxCriteria that = (CTaxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(taxCategoryId, that.taxCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        rate,
        validFrom,
        transactionType,
        uid,
        active,
        adOrganizationId,
        taxCategoryId
        );
    }

    @Override
    public String toString() {
        return "CTaxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (taxCategoryId != null ? "taxCategoryId=" + taxCategoryId + ", " : "") +
            "}";
    }

}
