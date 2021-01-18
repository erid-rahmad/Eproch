package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bhp.opusb.domain.enumeration.AdWatchListActionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdWatchListItem.
 */
@Entity
@Table(name = "ad_watch_list_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdWatchListItem extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    /**
     * The name of Spring bean.
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * RESTful API endpoint.
     */
    @Column(name = "rest_api_endpoint")
    private String restApiEndpoint;

    /**
     * Websocket endpoint.
     */
    @Column(name = "websocket_endpoint")
    private String websocketEndpoint;

    /**
     * Action to be performed when selecting the item.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private AdWatchListActionType actionType;

    /**
     * URL to be opened when selecting the item.
     */
    @Size(max = 50)
    @Column(name = "action_url", length = 50)
    private String actionUrl;

    /**
     * Filter query to be applied to the target menu/url.
     */
    @Column(name = "filter_query")
    private String filterQuery;

    /**
     * Accent color (HEX color code) to be applied to the item.
     */
    @Size(max = 7)
    @Column(name = "accent_color", length = 7)
    private String accentColor;

    /**
     * Icon path to be applied to the item.
     */
    @Size(max = 100)
    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adWatchListItems")
    private ADOrganization adOrganization;

    /**
     * Menu to be opened when selecting the item.
     */
    @ManyToOne
    @JsonIgnoreProperties("adWatchListItems")
    private AdMenu adMenu;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adWatchListItems")
    @JsonBackReference
    private AdWatchList adWatchList;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public AdWatchListItem code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public AdWatchListItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AdWatchListItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceName() {
        return serviceName;
    }

    public AdWatchListItem serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRestApiEndpoint() {
        return restApiEndpoint;
    }

    public AdWatchListItem restApiEndpoint(String restApiEndpoint) {
        this.restApiEndpoint = restApiEndpoint;
        return this;
    }

    public void setRestApiEndpoint(String restApiEndpoint) {
        this.restApiEndpoint = restApiEndpoint;
    }

    public String getWebsocketEndpoint() {
        return websocketEndpoint;
    }

    public AdWatchListItem websocketEndpoint(String websocketEndpoint) {
        this.websocketEndpoint = websocketEndpoint;
        return this;
    }

    public void setWebsocketEndpoint(String websocketEndpoint) {
        this.websocketEndpoint = websocketEndpoint;
    }

    public AdWatchListActionType getActionType() {
        return actionType;
    }

    public AdWatchListItem actionType(AdWatchListActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(AdWatchListActionType actionType) {
        this.actionType = actionType;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public AdWatchListItem actionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
        return this;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getFilterQuery() {
        return filterQuery;
    }

    public AdWatchListItem filterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
        return this;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public AdWatchListItem accentColor(String accentColor) {
        this.accentColor = accentColor;
        return this;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getIcon() {
        return icon;
    }

    public AdWatchListItem icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public UUID getUid() {
        return uid;
    }

    public AdWatchListItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdWatchListItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdWatchListItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdMenu getAdMenu() {
        return adMenu;
    }

    public AdWatchListItem adMenu(AdMenu adMenu) {
        this.adMenu = adMenu;
        return this;
    }

    public void setAdMenu(AdMenu adMenu) {
        this.adMenu = adMenu;
    }

    public AdWatchList getAdWatchList() {
        return adWatchList;
    }

    public AdWatchListItem adWatchList(AdWatchList adWatchList) {
        this.adWatchList = adWatchList;
        return this;
    }

    public void setAdWatchList(AdWatchList adWatchList) {
        this.adWatchList = adWatchList;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdWatchListItem)) {
            return false;
        }
        return id != null && id.equals(((AdWatchListItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdWatchListItem{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", restApiEndpoint='" + getRestApiEndpoint() + "'" +
            ", websocketEndpoint='" + getWebsocketEndpoint() + "'" +
            ", actionType='" + getActionType() + "'" +
            ", actionUrl='" + getActionUrl() + "'" +
            ", filterQuery='" + getFilterQuery() + "'" +
            ", accentColor='" + getAccentColor() + "'" +
            ", icon='" + getIcon() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
