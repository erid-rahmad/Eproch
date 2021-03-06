package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.bhp.opusb.domain.enumeration.BusinessCategorySelection;
import com.bhp.opusb.domain.enumeration.BusinessCategorySelection;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CRegistrationDocType} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CRegistrationDocTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-registration-doc-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CRegistrationDocTypeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering BusinessCategorySelection
     */
    public static class BusinessCategorySelectionFilter extends Filter<BusinessCategorySelection> {

        public BusinessCategorySelectionFilter() {
        }

        public BusinessCategorySelectionFilter(BusinessCategorySelectionFilter filter) {
            super(filter);
        }

        @Override
        public BusinessCategorySelectionFilter copy() {
            return new BusinessCategorySelectionFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter hasExpirationDate;

    private BusinessCategorySelectionFilter mandatoryBusinessCategories;

    private BusinessCategorySelectionFilter additionalBusinessCategories;

    private BooleanFilter mandatoryForCompany;

    private BooleanFilter additionalForCompany;

    private BooleanFilter mandatoryForProfessional;

    private BooleanFilter additionalForProfessional;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;
    private StringFilter adOrganizationName;

    public CRegistrationDocTypeCriteria() {
    }

    public CRegistrationDocTypeCriteria(CRegistrationDocTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.hasExpirationDate = other.hasExpirationDate == null ? null : other.hasExpirationDate.copy();
        this.mandatoryBusinessCategories = other.mandatoryBusinessCategories == null ? null : other.mandatoryBusinessCategories.copy();
        this.additionalBusinessCategories = other.additionalBusinessCategories == null ? null : other.additionalBusinessCategories.copy();
        this.mandatoryForCompany = other.mandatoryForCompany == null ? null : other.mandatoryForCompany.copy();
        this.additionalForCompany = other.additionalForCompany == null ? null : other.additionalForCompany.copy();
        this.mandatoryForProfessional = other.mandatoryForProfessional == null ? null : other.mandatoryForProfessional.copy();
        this.additionalForProfessional = other.additionalForProfessional == null ? null : other.additionalForProfessional.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adOrganizationName = other.adOrganizationName == null ? null : other.adOrganizationName.copy();
    }

    @Override
    public CRegistrationDocTypeCriteria copy() {
        return new CRegistrationDocTypeCriteria(this);
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

    public BooleanFilter getHasExpirationDate() {
        return hasExpirationDate;
    }

    public void setHasExpirationDate(BooleanFilter hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
    }

    public BusinessCategorySelectionFilter getMandatoryBusinessCategories() {
        return mandatoryBusinessCategories;
    }

    public void setMandatoryBusinessCategories(BusinessCategorySelectionFilter mandatoryBusinessCategories) {
        this.mandatoryBusinessCategories = mandatoryBusinessCategories;
    }

    public BusinessCategorySelectionFilter getAdditionalBusinessCategories() {
        return additionalBusinessCategories;
    }

    public void setAdditionalBusinessCategories(BusinessCategorySelectionFilter additionalBusinessCategories) {
        this.additionalBusinessCategories = additionalBusinessCategories;
    }

    public BooleanFilter getMandatoryForCompany() {
        return mandatoryForCompany;
    }

    public void setMandatoryForCompany(BooleanFilter mandatoryForCompany) {
        this.mandatoryForCompany = mandatoryForCompany;
    }

    public BooleanFilter getAdditionalForCompany() {
        return additionalForCompany;
    }

    public void setAdditionalForCompany(BooleanFilter additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
    }

    public BooleanFilter getMandatoryForProfessional() {
        return mandatoryForProfessional;
    }

    public void setMandatoryForProfessional(BooleanFilter mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
    }

    public BooleanFilter getAdditionalForProfessional() {
        return additionalForProfessional;
    }

    public void setAdditionalForProfessional(BooleanFilter additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
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
        final CRegistrationDocTypeCriteria that = (CRegistrationDocTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(hasExpirationDate, that.hasExpirationDate) &&
            Objects.equals(mandatoryBusinessCategories, that.mandatoryBusinessCategories) &&
            Objects.equals(additionalBusinessCategories, that.additionalBusinessCategories) &&
            Objects.equals(mandatoryForCompany, that.mandatoryForCompany) &&
            Objects.equals(additionalForCompany, that.additionalForCompany) &&
            Objects.equals(mandatoryForProfessional, that.mandatoryForProfessional) &&
            Objects.equals(additionalForProfessional, that.additionalForProfessional) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adOrganizationName, that.adOrganizationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        hasExpirationDate,
        mandatoryBusinessCategories,
        additionalBusinessCategories,
        mandatoryForCompany,
        additionalForCompany,
        mandatoryForProfessional,
        additionalForProfessional,
        uid,
        active,
        adOrganizationId,
        adOrganizationName
        );
    }

    @Override
    public String toString() {
        return "CRegistrationDocTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (hasExpirationDate != null ? "hasExpirationDate=" + hasExpirationDate + ", " : "") +
                (mandatoryBusinessCategories != null ? "mandatoryBusinessCategories=" + mandatoryBusinessCategories + ", " : "") +
                (additionalBusinessCategories != null ? "additionalBusinessCategories=" + additionalBusinessCategories + ", " : "") +
                (mandatoryForCompany != null ? "mandatoryForCompany=" + mandatoryForCompany + ", " : "") +
                (additionalForCompany != null ? "additionalForCompany=" + additionalForCompany + ", " : "") +
                (mandatoryForProfessional != null ? "mandatoryForProfessional=" + mandatoryForProfessional + ", " : "") +
                (additionalForProfessional != null ? "additionalForProfessional=" + additionalForProfessional + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adOrganizationName != null ? "adOrganizationName=" + adOrganizationName + ", " : "") +
            "}";
    }

    

}
