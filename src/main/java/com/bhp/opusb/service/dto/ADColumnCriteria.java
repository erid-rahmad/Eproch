package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.ADColumnType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ADColumn} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADColumnResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-columns?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADColumnCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ADColumnType
     */
    public static class ADColumnTypeFilter extends Filter<ADColumnType> {

        public ADColumnTypeFilter() {
        }

        public ADColumnTypeFilter(ADColumnTypeFilter filter) {
            super(filter);
        }

        @Override
        public ADColumnTypeFilter copy() {
            return new ADColumnTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter key;

    private ADColumnTypeFilter type;

    private BooleanFilter mandatory;

    private StringFilter mandatoryLogic;

    private StringFilter readOnlyLogic;

    private BooleanFilter updatable;

    private StringFilter defaultValue;

    private StringFilter formatPattern;

    private IntegerFilter minValue;

    private IntegerFilter maxValue;

    private BooleanFilter active;

    private LongFilter adClientId;

    private LongFilter adOrganizationId;

    private LongFilter adReferenceId;

    private LongFilter adTableId;

    public ADColumnCriteria() {
    }

    public ADColumnCriteria(ADColumnCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.key = other.key == null ? null : other.key.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.mandatory = other.mandatory == null ? null : other.mandatory.copy();
        this.mandatoryLogic = other.mandatoryLogic == null ? null : other.mandatoryLogic.copy();
        this.readOnlyLogic = other.readOnlyLogic == null ? null : other.readOnlyLogic.copy();
        this.updatable = other.updatable == null ? null : other.updatable.copy();
        this.defaultValue = other.defaultValue == null ? null : other.defaultValue.copy();
        this.formatPattern = other.formatPattern == null ? null : other.formatPattern.copy();
        this.minValue = other.minValue == null ? null : other.minValue.copy();
        this.maxValue = other.maxValue == null ? null : other.maxValue.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adClientId = other.adClientId == null ? null : other.adClientId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adReferenceId = other.adReferenceId == null ? null : other.adReferenceId.copy();
        this.adTableId = other.adTableId == null ? null : other.adTableId.copy();
    }

    @Override
    public ADColumnCriteria copy() {
        return new ADColumnCriteria(this);
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

    public BooleanFilter getKey() {
        return key;
    }

    public void setKey(BooleanFilter key) {
        this.key = key;
    }

    public ADColumnTypeFilter getType() {
        return type;
    }

    public void setType(ADColumnTypeFilter type) {
        this.type = type;
    }

    public BooleanFilter getMandatory() {
        return mandatory;
    }

    public void setMandatory(BooleanFilter mandatory) {
        this.mandatory = mandatory;
    }

    public StringFilter getMandatoryLogic() {
        return mandatoryLogic;
    }

    public void setMandatoryLogic(StringFilter mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
    }

    public StringFilter getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public void setReadOnlyLogic(StringFilter readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
    }

    public BooleanFilter getUpdatable() {
        return updatable;
    }

    public void setUpdatable(BooleanFilter updatable) {
        this.updatable = updatable;
    }

    public StringFilter getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(StringFilter defaultValue) {
        this.defaultValue = defaultValue;
    }

    public StringFilter getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(StringFilter formatPattern) {
        this.formatPattern = formatPattern;
    }

    public IntegerFilter getMinValue() {
        return minValue;
    }

    public void setMinValue(IntegerFilter minValue) {
        this.minValue = minValue;
    }

    public IntegerFilter getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(IntegerFilter maxValue) {
        this.maxValue = maxValue;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getAdClientId() {
        return adClientId;
    }

    public void setAdClientId(LongFilter adClientId) {
        this.adClientId = adClientId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAdReferenceId() {
        return adReferenceId;
    }

    public void setAdReferenceId(LongFilter adReferenceId) {
        this.adReferenceId = adReferenceId;
    }

    public LongFilter getAdTableId() {
        return adTableId;
    }

    public void setAdTableId(LongFilter adTableId) {
        this.adTableId = adTableId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ADColumnCriteria that = (ADColumnCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(key, that.key) &&
            Objects.equals(type, that.type) &&
            Objects.equals(mandatory, that.mandatory) &&
            Objects.equals(mandatoryLogic, that.mandatoryLogic) &&
            Objects.equals(readOnlyLogic, that.readOnlyLogic) &&
            Objects.equals(updatable, that.updatable) &&
            Objects.equals(defaultValue, that.defaultValue) &&
            Objects.equals(formatPattern, that.formatPattern) &&
            Objects.equals(minValue, that.minValue) &&
            Objects.equals(maxValue, that.maxValue) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adClientId, that.adClientId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adReferenceId, that.adReferenceId) &&
            Objects.equals(adTableId, that.adTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        key,
        type,
        mandatory,
        mandatoryLogic,
        readOnlyLogic,
        updatable,
        defaultValue,
        formatPattern,
        minValue,
        maxValue,
        active,
        adClientId,
        adOrganizationId,
        adReferenceId,
        adTableId
        );
    }

    @Override
    public String toString() {
        return "ADColumnCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (key != null ? "key=" + key + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (mandatory != null ? "mandatory=" + mandatory + ", " : "") +
                (mandatoryLogic != null ? "mandatoryLogic=" + mandatoryLogic + ", " : "") +
                (readOnlyLogic != null ? "readOnlyLogic=" + readOnlyLogic + ", " : "") +
                (updatable != null ? "updatable=" + updatable + ", " : "") +
                (defaultValue != null ? "defaultValue=" + defaultValue + ", " : "") +
                (formatPattern != null ? "formatPattern=" + formatPattern + ", " : "") +
                (minValue != null ? "minValue=" + minValue + ", " : "") +
                (maxValue != null ? "maxValue=" + maxValue + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adClientId != null ? "adClientId=" + adClientId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adReferenceId != null ? "adReferenceId=" + adReferenceId + ", " : "") +
                (adTableId != null ? "adTableId=" + adTableId + ", " : "") +
            "}";
    }

}
