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
 * Criteria class for the {@link com.bhp.opusb.domain.Permission} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.PermissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /permissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PermissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter module;

    private BooleanFilter canWrite;

    public PermissionCriteria() {
    }

    public PermissionCriteria(PermissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.module = other.module == null ? null : other.module.copy();
        this.canWrite = other.canWrite == null ? null : other.canWrite.copy();
    }

    @Override
    public PermissionCriteria copy() {
        return new PermissionCriteria(this);
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

    public StringFilter getModule() {
        return module;
    }

    public void setModule(StringFilter module) {
        this.module = module;
    }

    public BooleanFilter getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(BooleanFilter canWrite) {
        this.canWrite = canWrite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PermissionCriteria that = (PermissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(module, that.module) &&
            Objects.equals(canWrite, that.canWrite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        module,
        canWrite
        );
    }

    @Override
    public String toString() {
        return "PermissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (module != null ? "module=" + module + ", " : "") +
                (canWrite != null ? "canWrite=" + canWrite + ", " : "") +
            "}";
    }

}
