package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.bhp.opusb.domain.enumeration.AdWatchListActionType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.AdWatchListItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdWatchListItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-watch-list-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdWatchListItemCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AdWatchListActionType
     */
    public static class AdWatchListActionTypeFilter extends Filter<AdWatchListActionType> {

        public AdWatchListActionTypeFilter() {
        }

        public AdWatchListActionTypeFilter(AdWatchListActionTypeFilter filter) {
            super(filter);
        }

        @Override
        public AdWatchListActionTypeFilter copy() {
            return new AdWatchListActionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private StringFilter serviceName;

    private StringFilter restApiEndpoint;

    private StringFilter websocketEndpoint;

    private AdWatchListActionTypeFilter actionType;

    private StringFilter actionUrl;

    private StringFilter filterQuery;

    private StringFilter accentColor;

    private StringFilter icon;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter adMenuId;

    private LongFilter adWatchListId;

    public AdWatchListItemCriteria() {
    }

    public AdWatchListItemCriteria(AdWatchListItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.serviceName = other.serviceName == null ? null : other.serviceName.copy();
        this.restApiEndpoint = other.restApiEndpoint == null ? null : other.restApiEndpoint.copy();
        this.websocketEndpoint = other.websocketEndpoint == null ? null : other.websocketEndpoint.copy();
        this.actionType = other.actionType == null ? null : other.actionType.copy();
        this.actionUrl = other.actionUrl == null ? null : other.actionUrl.copy();
        this.filterQuery = other.filterQuery == null ? null : other.filterQuery.copy();
        this.accentColor = other.accentColor == null ? null : other.accentColor.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adMenuId = other.adMenuId == null ? null : other.adMenuId.copy();
        this.adWatchListId = other.adWatchListId == null ? null : other.adWatchListId.copy();
    }

    @Override
    public AdWatchListItemCriteria copy() {
        return new AdWatchListItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public StringFilter getServiceName() {
        return serviceName;
    }

    public void setServiceName(StringFilter serviceName) {
        this.serviceName = serviceName;
    }

    public StringFilter getRestApiEndpoint() {
        return restApiEndpoint;
    }

    public void setRestApiEndpoint(StringFilter restApiEndpoint) {
        this.restApiEndpoint = restApiEndpoint;
    }

    public StringFilter getWebsocketEndpoint() {
        return websocketEndpoint;
    }

    public void setWebsocketEndpoint(StringFilter websocketEndpoint) {
        this.websocketEndpoint = websocketEndpoint;
    }

    public AdWatchListActionTypeFilter getActionType() {
        return actionType;
    }

    public void setActionType(AdWatchListActionTypeFilter actionType) {
        this.actionType = actionType;
    }

    public StringFilter getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(StringFilter actionUrl) {
        this.actionUrl = actionUrl;
    }

    public StringFilter getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(StringFilter filterQuery) {
        this.filterQuery = filterQuery;
    }

    public StringFilter getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(StringFilter accentColor) {
        this.accentColor = accentColor;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
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

    public LongFilter getAdMenuId() {
        return adMenuId;
    }

    public void setAdMenuId(LongFilter adMenuId) {
        this.adMenuId = adMenuId;
    }

    public LongFilter getAdWatchListId() {
        return adWatchListId;
    }

    public void setAdWatchListId(LongFilter adWatchListId) {
        this.adWatchListId = adWatchListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdWatchListItemCriteria that = (AdWatchListItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(serviceName, that.serviceName) &&
            Objects.equals(restApiEndpoint, that.restApiEndpoint) &&
            Objects.equals(websocketEndpoint, that.websocketEndpoint) &&
            Objects.equals(actionType, that.actionType) &&
            Objects.equals(actionUrl, that.actionUrl) &&
            Objects.equals(filterQuery, that.filterQuery) &&
            Objects.equals(accentColor, that.accentColor) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adMenuId, that.adMenuId) &&
            Objects.equals(adWatchListId, that.adWatchListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        description,
        serviceName,
        restApiEndpoint,
        websocketEndpoint,
        actionType,
        actionUrl,
        filterQuery,
        accentColor,
        icon,
        uid,
        active,
        adOrganizationId,
        adMenuId,
        adWatchListId
        );
    }

    @Override
    public String toString() {
        return "AdWatchListItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (serviceName != null ? "serviceName=" + serviceName + ", " : "") +
                (restApiEndpoint != null ? "restApiEndpoint=" + restApiEndpoint + ", " : "") +
                (websocketEndpoint != null ? "websocketEndpoint=" + websocketEndpoint + ", " : "") +
                (actionType != null ? "actionType=" + actionType + ", " : "") +
                (actionUrl != null ? "actionUrl=" + actionUrl + ", " : "") +
                (filterQuery != null ? "filterQuery=" + filterQuery + ", " : "") +
                (accentColor != null ? "accentColor=" + accentColor + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adMenuId != null ? "adMenuId=" + adMenuId + ", " : "") +
                (adWatchListId != null ? "adWatchListId=" + adWatchListId + ", " : "") +
            "}";
    }

}
