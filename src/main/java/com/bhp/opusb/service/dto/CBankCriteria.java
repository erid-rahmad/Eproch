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

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CBank} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CBankResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-banks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CBankCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter value;

    private StringFilter shortName;

    private StringFilter description;

    private StringFilter swiftCode;

    private BooleanFilter active;

    public CBankCriteria() {
    }

    public CBankCriteria(CBankCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.shortName = other.shortName == null ? null : other.shortName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.swiftCode = other.swiftCode == null ? null : other.swiftCode.copy();
        this.active = other.active == null ? null : other.active.copy();
    }

    @Override
    public CBankCriteria copy() {
        return new CBankCriteria(this);
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

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public StringFilter getShortName() {
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(StringFilter swiftCode) {
        this.swiftCode = swiftCode;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CBankCriteria that = (CBankCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(swiftCode, that.swiftCode) &&
            Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        value,
        shortName,
        description,
        swiftCode,
        active
        );
    }

    @Override
    public String toString() {
        return "CBankCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (shortName != null ? "shortName=" + shortName + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (swiftCode != null ? "swiftCode=" + swiftCode + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
            "}";
    }

}
