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
 * Criteria class for the {@link com.bhp.opusb.domain.AdTriggerParam} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdTriggerParamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-trigger-params?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdTriggerParamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter name;

    private BooleanFilter mandatory;

    private StringFilter mandatoryLogic;

    private StringFilter displayLogic;

    private StringFilter readOnlyLogic;

    private StringFilter defaultValue;

    private StringFilter formatPattern;

    private IntegerFilter minLength;

    private IntegerFilter maxLength;

    private LongFilter minValue;

    private LongFilter maxValue;

    private LongFilter adOrganizationId;

    private LongFilter adTriggerId;

    public AdTriggerParamCriteria() {
    }

    public AdTriggerParamCriteria(AdTriggerParamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.mandatory = other.mandatory == null ? null : other.mandatory.copy();
        this.mandatoryLogic = other.mandatoryLogic == null ? null : other.mandatoryLogic.copy();
        this.displayLogic = other.displayLogic == null ? null : other.displayLogic.copy();
        this.readOnlyLogic = other.readOnlyLogic == null ? null : other.readOnlyLogic.copy();
        this.defaultValue = other.defaultValue == null ? null : other.defaultValue.copy();
        this.formatPattern = other.formatPattern == null ? null : other.formatPattern.copy();
        this.minLength = other.minLength == null ? null : other.minLength.copy();
        this.maxLength = other.maxLength == null ? null : other.maxLength.copy();
        this.minValue = other.minValue == null ? null : other.minValue.copy();
        this.maxValue = other.maxValue == null ? null : other.maxValue.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adTriggerId = other.adTriggerId == null ? null : other.adTriggerId.copy();
    }

    @Override
    public AdTriggerParamCriteria copy() {
        return new AdTriggerParamCriteria(this);
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

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public StringFilter getDisplayLogic() {
        return displayLogic;
    }

    public void setDisplayLogic(StringFilter displayLogic) {
        this.displayLogic = displayLogic;
    }

    public StringFilter getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public void setReadOnlyLogic(StringFilter readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
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

    public IntegerFilter getMinLength() {
        return minLength;
    }

    public void setMinLength(IntegerFilter minLength) {
        this.minLength = minLength;
    }

    public IntegerFilter getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(IntegerFilter maxLength) {
        this.maxLength = maxLength;
    }

    public LongFilter getMinValue() {
        return minValue;
    }

    public void setMinValue(LongFilter minValue) {
        this.minValue = minValue;
    }

    public LongFilter getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(LongFilter maxValue) {
        this.maxValue = maxValue;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAdTriggerId() {
        return adTriggerId;
    }

    public void setAdTriggerId(LongFilter adTriggerId) {
        this.adTriggerId = adTriggerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdTriggerParamCriteria that = (AdTriggerParamCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(name, that.name) &&
            Objects.equals(mandatory, that.mandatory) &&
            Objects.equals(mandatoryLogic, that.mandatoryLogic) &&
            Objects.equals(displayLogic, that.displayLogic) &&
            Objects.equals(readOnlyLogic, that.readOnlyLogic) &&
            Objects.equals(defaultValue, that.defaultValue) &&
            Objects.equals(formatPattern, that.formatPattern) &&
            Objects.equals(minLength, that.minLength) &&
            Objects.equals(maxLength, that.maxLength) &&
            Objects.equals(minValue, that.minValue) &&
            Objects.equals(maxValue, that.maxValue) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adTriggerId, that.adTriggerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        name,
        mandatory,
        mandatoryLogic,
        displayLogic,
        readOnlyLogic,
        defaultValue,
        formatPattern,
        minLength,
        maxLength,
        minValue,
        maxValue,
        adOrganizationId,
        adTriggerId
        );
    }

    @Override
    public String toString() {
        return "AdTriggerParamCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (mandatory != null ? "mandatory=" + mandatory + ", " : "") +
                (mandatoryLogic != null ? "mandatoryLogic=" + mandatoryLogic + ", " : "") +
                (displayLogic != null ? "displayLogic=" + displayLogic + ", " : "") +
                (readOnlyLogic != null ? "readOnlyLogic=" + readOnlyLogic + ", " : "") +
                (defaultValue != null ? "defaultValue=" + defaultValue + ", " : "") +
                (formatPattern != null ? "formatPattern=" + formatPattern + ", " : "") +
                (minLength != null ? "minLength=" + minLength + ", " : "") +
                (maxLength != null ? "maxLength=" + maxLength + ", " : "") +
                (minValue != null ? "minValue=" + minValue + ", " : "") +
                (maxValue != null ? "maxValue=" + maxValue + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adTriggerId != null ? "adTriggerId=" + adTriggerId + ", " : "") +
            "}";
    }

}
