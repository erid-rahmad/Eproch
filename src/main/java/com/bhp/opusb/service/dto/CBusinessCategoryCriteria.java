package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.bhp.opusb.domain.enumeration.CBusinessCategorySector;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CBusinessCategory} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CBusinessCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-business-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CBusinessCategoryCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CBusinessCategorySector
     */
    public static class CBusinessCategorySectorFilter extends Filter<CBusinessCategorySector> {

        public CBusinessCategorySectorFilter() {
        }

        public CBusinessCategorySectorFilter(CBusinessCategorySectorFilter filter) {
            super(filter);
        }

        @Override
        public CBusinessCategorySectorFilter copy() {
            return new CBusinessCategorySectorFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private CBusinessCategorySectorFilter sector;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter cBusinessCategoryId;

    private LongFilter adOrganizationId;
    private StringFilter adOrganizationName;

    private LongFilter parentCategoryId;
    private StringFilter parentCategoryName;

    public CBusinessCategoryCriteria() {
    }

    public CBusinessCategoryCriteria(CBusinessCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.sector = other.sector == null ? null : other.sector.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.cBusinessCategoryId = other.cBusinessCategoryId == null ? null : other.cBusinessCategoryId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adOrganizationName = other.adOrganizationName == null ? null : other.adOrganizationName.copy();
        this.parentCategoryId = other.parentCategoryId == null ? null : other.parentCategoryId.copy();
        this.parentCategoryName = other.parentCategoryName == null ? null : other.parentCategoryName.copy();
    }

    @Override
    public CBusinessCategoryCriteria copy() {
        return new CBusinessCategoryCriteria(this);
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

    public CBusinessCategorySectorFilter getSector() {
        return sector;
    }

    public void setSector(CBusinessCategorySectorFilter sector) {
        this.sector = sector;
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

    public LongFilter getCBusinessCategoryId() {
        return cBusinessCategoryId;
    }

    public void setCBusinessCategoryId(LongFilter cBusinessCategoryId) {
        this.cBusinessCategoryId = cBusinessCategoryId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public StringFilter getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(StringFilter adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }
    
    public LongFilter getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(LongFilter parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public StringFilter getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(StringFilter parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CBusinessCategoryCriteria that = (CBusinessCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(sector, that.sector) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(cBusinessCategoryId, that.cBusinessCategoryId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adOrganizationName, that.adOrganizationName) &&
            Objects.equals(parentCategoryId, that.parentCategoryId) &&
            Objects.equals(parentCategoryName, that.parentCategoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        sector,
        uid,
        active,
        cBusinessCategoryId,
        adOrganizationId,
        adOrganizationName,
        parentCategoryId,
        parentCategoryName
        );
    }

    @Override
    public String toString() {
        return "CBusinessCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (sector != null ? "sector=" + sector + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (cBusinessCategoryId != null ? "cBusinessCategoryId=" + cBusinessCategoryId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adOrganizationName != null ? "adOrganizationName=" + adOrganizationName + ", " : "") +
                (parentCategoryId != null ? "parentCategoryId=" + parentCategoryId + ", " : "") +
                (parentCategoryName != null ? "parentCategoryName=" + parentCategoryName + ", " : "") +
            "}";
    }

    

}
