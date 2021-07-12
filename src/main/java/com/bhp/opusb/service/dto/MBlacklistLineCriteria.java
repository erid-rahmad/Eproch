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
 * Criteria class for the {@link com.bhp.opusb.domain.MBlacklistLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBlacklistLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-blacklist-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBlacklistLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter blacklistId;

    private LongFilter adOrganizationId;

    private LongFilter picId;

    private LongFilter functionaryId;

    public MBlacklistLineCriteria() {
    }

    public MBlacklistLineCriteria(MBlacklistLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.blacklistId = other.blacklistId == null ? null : other.blacklistId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.picId = other.picId == null ? null : other.picId.copy();
        this.functionaryId = other.functionaryId == null ? null : other.functionaryId.copy();
    }

    @Override
    public MBlacklistLineCriteria copy() {
        return new MBlacklistLineCriteria(this);
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

    public LongFilter getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(LongFilter blacklistId) {
        this.blacklistId = blacklistId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getPicId() {
        return picId;
    }

    public void setPicId(LongFilter picId) {
        this.picId = picId;
    }

    public LongFilter getFunctionaryId() {
        return functionaryId;
    }

    public void setFunctionaryId(LongFilter functionaryId) {
        this.functionaryId = functionaryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBlacklistLineCriteria that = (MBlacklistLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(blacklistId, that.blacklistId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(picId, that.picId) &&
            Objects.equals(functionaryId, that.functionaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        blacklistId,
        adOrganizationId,
        picId,
        functionaryId
        );
    }

    @Override
    public String toString() {
        return "MBlacklistLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (blacklistId != null ? "blacklistId=" + blacklistId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (picId != null ? "picId=" + picId + ", " : "") +
                (functionaryId != null ? "functionaryId=" + functionaryId + ", " : "") +
            "}";
    }

}
