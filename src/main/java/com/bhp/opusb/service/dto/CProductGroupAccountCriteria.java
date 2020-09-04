package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.bhp.opusb.domain.enumeration.Depreciation;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CProductGroupAccount} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CProductGroupAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-product-group-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CProductGroupAccountCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Depreciation
     */
    public static class DepreciationFilter extends Filter<Depreciation> {

        public DepreciationFilter() {
        }

        public DepreciationFilter(DepreciationFilter filter) {
            super(filter);
        }

        @Override
        public DepreciationFilter copy() {
            return new DepreciationFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DepreciationFilter depreciation;

    private IntegerFilter lifeYear;

    private IntegerFilter lifeMonth;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter assetAccountId;

    private LongFilter depreciationAccountId;

    private LongFilter productGroupId;

    private LongFilter adOrganizationId;

    public CProductGroupAccountCriteria() {
    }

    public CProductGroupAccountCriteria(CProductGroupAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.depreciation = other.depreciation == null ? null : other.depreciation.copy();
        this.lifeYear = other.lifeYear == null ? null : other.lifeYear.copy();
        this.lifeMonth = other.lifeMonth == null ? null : other.lifeMonth.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.assetAccountId = other.assetAccountId == null ? null : other.assetAccountId.copy();
        this.depreciationAccountId = other.depreciationAccountId == null ? null : other.depreciationAccountId.copy();
        this.productGroupId = other.productGroupId == null ? null : other.productGroupId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CProductGroupAccountCriteria copy() {
        return new CProductGroupAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DepreciationFilter getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(DepreciationFilter depreciation) {
        this.depreciation = depreciation;
    }

    public IntegerFilter getLifeYear() {
        return lifeYear;
    }

    public void setLifeYear(IntegerFilter lifeYear) {
        this.lifeYear = lifeYear;
    }

    public IntegerFilter getLifeMonth() {
        return lifeMonth;
    }

    public void setLifeMonth(IntegerFilter lifeMonth) {
        this.lifeMonth = lifeMonth;
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

    public LongFilter getAssetAccountId() {
        return assetAccountId;
    }

    public void setAssetAccountId(LongFilter assetAccountId) {
        this.assetAccountId = assetAccountId;
    }

    public LongFilter getDepreciationAccountId() {
        return depreciationAccountId;
    }

    public void setDepreciationAccountId(LongFilter depreciationAccountId) {
        this.depreciationAccountId = depreciationAccountId;
    }

    public LongFilter getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(LongFilter productGroupId) {
        this.productGroupId = productGroupId;
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
        final CProductGroupAccountCriteria that = (CProductGroupAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(depreciation, that.depreciation) &&
            Objects.equals(lifeYear, that.lifeYear) &&
            Objects.equals(lifeMonth, that.lifeMonth) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(assetAccountId, that.assetAccountId) &&
            Objects.equals(depreciationAccountId, that.depreciationAccountId) &&
            Objects.equals(productGroupId, that.productGroupId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        depreciation,
        lifeYear,
        lifeMonth,
        uid,
        active,
        assetAccountId,
        depreciationAccountId,
        productGroupId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CProductGroupAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (depreciation != null ? "depreciation=" + depreciation + ", " : "") +
                (lifeYear != null ? "lifeYear=" + lifeYear + ", " : "") +
                (lifeMonth != null ? "lifeMonth=" + lifeMonth + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (assetAccountId != null ? "assetAccountId=" + assetAccountId + ", " : "") +
                (depreciationAccountId != null ? "depreciationAccountId=" + depreciationAccountId + ", " : "") +
                (productGroupId != null ? "productGroupId=" + productGroupId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}
