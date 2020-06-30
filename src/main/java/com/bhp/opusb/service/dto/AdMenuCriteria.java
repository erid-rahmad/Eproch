package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.AdMenuAction;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.AdMenu} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdMenuResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-menus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdMenuCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AdMenuAction
     */
    public static class AdMenuActionFilter extends Filter<AdMenuAction> {

        private static final long serialVersionUID = 1L;

        public AdMenuActionFilter() {
        }

        public AdMenuActionFilter(AdMenuActionFilter filter) {
            super(filter);
        }

        @Override
        public AdMenuActionFilter copy() {
            return new AdMenuActionFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private StringFilter name;

    private StringFilter value;

    private StringFilter translationKey;

    private StringFilter description;

    private StringFilter path;

    private AdMenuActionFilter action;

    private StringFilter icon;

    private StringFilter redirect;

    private IntegerFilter sequence;

    private BooleanFilter alwaysShow;

    private BooleanFilter active;

    private LongFilter adMenuId;

    private LongFilter adWindowId;

    private LongFilter adOrganizationId;

    private LongFilter parentMenuId;

    public AdMenuCriteria() {
    }

    public AdMenuCriteria(AdMenuCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.translationKey = other.translationKey == null ? null : other.translationKey.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.path = other.path == null ? null : other.path.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.redirect = other.redirect == null ? null : other.redirect.copy();
        this.sequence = other.sequence == null ? null : other.sequence.copy();
        this.alwaysShow = other.alwaysShow == null ? null : other.alwaysShow.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adMenuId = other.adMenuId == null ? null : other.adMenuId.copy();
        this.adWindowId = other.adWindowId == null ? null : other.adWindowId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.parentMenuId = other.parentMenuId == null ? null : other.parentMenuId.copy();
    }

    @Override
    public AdMenuCriteria copy() {
        return new AdMenuCriteria(this);
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

    public StringFilter getTranslationKey() {
        return translationKey;
    }

    public void setTranslationKey(StringFilter translationKey) {
        this.translationKey = translationKey;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getPath() {
        return path;
    }

    public void setPath(StringFilter path) {
        this.path = path;
    }

    public AdMenuActionFilter getAction() {
        return action;
    }

    public void setAction(AdMenuActionFilter action) {
        this.action = action;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public StringFilter getRedirect() {
        return redirect;
    }

    public void setRedirect(StringFilter redirect) {
        this.redirect = redirect;
    }

    public IntegerFilter getSequence() {
        return sequence;
    }

    public void setSequence(IntegerFilter sequence) {
        this.sequence = sequence;
    }

    public BooleanFilter getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(BooleanFilter alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getAdMenuId() {
        return adMenuId;
    }

    public void setAdMenuId(LongFilter adMenuId) {
        this.adMenuId = adMenuId;
    }

    public LongFilter getAdWindowId() {
        return adWindowId;
    }

    public void setAdWindowId(LongFilter adWindowId) {
        this.adWindowId = adWindowId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(LongFilter parentMenuId) {
        this.parentMenuId = parentMenuId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdMenuCriteria that = (AdMenuCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(translationKey, that.translationKey) &&
            Objects.equals(description, that.description) &&
            Objects.equals(path, that.path) &&
            Objects.equals(action, that.action) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(redirect, that.redirect) &&
            Objects.equals(sequence, that.sequence) &&
            Objects.equals(alwaysShow, that.alwaysShow) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adMenuId, that.adMenuId) &&
            Objects.equals(adWindowId, that.adWindowId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(parentMenuId, that.parentMenuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        name,
        value,
        translationKey,
        description,
        path,
        action,
        icon,
        redirect,
        sequence,
        alwaysShow,
        active,
        adMenuId,
        adWindowId,
        adOrganizationId,
        parentMenuId
        );
    }

    @Override
    public String toString() {
        return "AdMenuCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (translationKey != null ? "translationKey=" + translationKey + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (path != null ? "path=" + path + ", " : "") +
                (action != null ? "action=" + action + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (redirect != null ? "redirect=" + redirect + ", " : "") +
                (sequence != null ? "sequence=" + sequence + ", " : "") +
                (alwaysShow != null ? "alwaysShow=" + alwaysShow + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adMenuId != null ? "adMenuId=" + adMenuId + ", " : "") +
                (adWindowId != null ? "adWindowId=" + adWindowId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (parentMenuId != null ? "parentMenuId=" + parentMenuId + ", " : "") +
            "}";
    }

}
