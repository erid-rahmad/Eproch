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
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CRegDocTypeBusinessCategory} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CRegDocTypeBusinessCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-reg-doc-type-business-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CRegDocTypeBusinessCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter mandatory;

    private BooleanFilter active;

    private LongFilter documentTypeId;

    private LongFilter businessCategoryId;
    private StringFilter businessCategoryName;

    private LongFilter adOrganizationId;
    private StringFilter adOrganizationName;

    public CRegDocTypeBusinessCategoryCriteria() {
    }

    public CRegDocTypeBusinessCategoryCriteria(CRegDocTypeBusinessCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.mandatory = other.mandatory == null ? null : other.mandatory.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
        this.businessCategoryName = other.businessCategoryName == null ? null : other.businessCategoryName.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adOrganizationName = other.adOrganizationName == null ? null : other.adOrganizationName.copy();
    }

    @Override
    public CRegDocTypeBusinessCategoryCriteria copy() {
        return new CRegDocTypeBusinessCategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
    }

    public BooleanFilter getMandatory() {
        return mandatory;
    }

    public void setMandatory(BooleanFilter mandatory) {
        this.mandatory = mandatory;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(LongFilter documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public LongFilter getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(LongFilter businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
    }

    public StringFilter getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(StringFilter businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CRegDocTypeBusinessCategoryCriteria that = (CRegDocTypeBusinessCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(mandatory, that.mandatory) &&
            Objects.equals(active, that.active) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId) &&
            Objects.equals(businessCategoryName, that.businessCategoryName) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adOrganizationName, that.adOrganizationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        mandatory,
        active,
        documentTypeId,
        businessCategoryId,
        businessCategoryName,
        adOrganizationId,
        adOrganizationName
        );
    }

    @Override
    public String toString() {
        return "CRegDocTypeBusinessCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (mandatory != null ? "mandatory=" + mandatory + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
                (businessCategoryName != null ? "businessCategoryName=" + businessCategoryName + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adOrganizationName != null ? "adOrganizationName=" + adOrganizationName + ", " : "") +
            "}";
    }

    

}
