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
 * Criteria class for the {@link com.bhp.opusb.domain.AdButton} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdButtonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-buttons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdButtonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter name;

    private StringFilter tooltip;

    private StringFilter description;

    private BooleanFilter toolbar;

    private StringFilter icon;

    private LongFilter adOrganizationId;

    private LongFilter adTriggerId;

    public AdButtonCriteria() {
    }

    public AdButtonCriteria(AdButtonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.tooltip = other.tooltip == null ? null : other.tooltip.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.toolbar = other.toolbar == null ? null : other.toolbar.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adTriggerId = other.adTriggerId == null ? null : other.adTriggerId.copy();
    }

    @Override
    public AdButtonCriteria copy() {
        return new AdButtonCriteria(this);
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

    public StringFilter getTooltip() {
        return tooltip;
    }

    public void setTooltip(StringFilter tooltip) {
        this.tooltip = tooltip;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getToolbar() {
        return toolbar;
    }

    public void setToolbar(BooleanFilter toolbar) {
        this.toolbar = toolbar;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
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
        final AdButtonCriteria that = (AdButtonCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(name, that.name) &&
            Objects.equals(tooltip, that.tooltip) &&
            Objects.equals(description, that.description) &&
            Objects.equals(toolbar, that.toolbar) &&
            Objects.equals(icon, that.icon) &&
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
        tooltip,
        description,
        toolbar,
        icon,
        adOrganizationId,
        adTriggerId
        );
    }

    @Override
    public String toString() {
        return "AdButtonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (tooltip != null ? "tooltip=" + tooltip + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (toolbar != null ? "toolbar=" + toolbar + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adTriggerId != null ? "adTriggerId=" + adTriggerId + ", " : "") +
            "}";
    }

}
