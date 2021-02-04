package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bhp.opusb.domain.enumeration.AdWatchListActionType;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdWatchListItem} entity.
 */
public class AdWatchListItemDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 10)
    private String code;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    /**
     * The name of Spring bean.
     */
    @ApiModelProperty(value = "The name of Spring bean.")
    private String serviceName;

    /**
     * RESTful API endpoint.
     */
    @ApiModelProperty(value = "RESTful API endpoint.")
    private String restApiEndpoint;

    /**
     * Websocket endpoint.
     */
    @ApiModelProperty(value = "Websocket endpoint.")
    private String websocketEndpoint;

    /**
     * Action to be performed when selecting the item.
     */
    @NotNull
    @ApiModelProperty(value = "Action to be performed when selecting the item.", required = true)
    private AdWatchListActionType actionType;

    /**
     * URL to be opened when selecting the item.
     */
    @Size(max = 50)
    @ApiModelProperty(value = "URL to be opened when selecting the item.")
    private String actionUrl;

    /**
     * Filter query to be applied to the target menu/url.
     */
    @ApiModelProperty(value = "Filter query to be applied to the target menu/url.")
    private String filterQuery;

    /**
     * Accent color (HEX color code) to be applied to the item.
     */
    @Size(max = 7)
    @ApiModelProperty(value = "Accent color (HEX color code) to be applied to the item.")
    private String accentColor;

    /**
     * Icon path to be applied to the item.
     */
    @Size(max = 100)
    @ApiModelProperty(value = "Icon path to be applied to the item.")
    private String icon;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    /**
     * Menu to be opened when selecting the item.
     */
    @ApiModelProperty(value = "Menu to be opened when selecting the item.")
    private Long adMenuId;
    private String adMenuName;
    private AdMenuDTO adMenu;

    private Long adWatchListId;
    private String adWatchListName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRestApiEndpoint() {
        return restApiEndpoint;
    }

    public void setRestApiEndpoint(String restApiEndpoint) {
        this.restApiEndpoint = restApiEndpoint;
    }

    public String getWebsocketEndpoint() {
        return websocketEndpoint;
    }

    public void setWebsocketEndpoint(String websocketEndpoint) {
        this.websocketEndpoint = websocketEndpoint;
    }

    public AdWatchListActionType getActionType() {
        return actionType;
    }

    public void setActionType(AdWatchListActionType actionType) {
        this.actionType = actionType;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getAdMenuId() {
        return adMenuId;
    }

    public void setAdMenuId(Long adMenuId) {
        this.adMenuId = adMenuId;
    }

    public String getAdMenuName() {
        return adMenuName;
    }

    public void setAdMenuName(String adMenuName) {
        this.adMenuName = adMenuName;
    }

    public AdMenuDTO getAdMenu() {
        return adMenu;
    }

    public void setAdMenu(AdMenuDTO adMenu) {
        this.adMenu = adMenu;
    }

    public Long getAdWatchListId() {
        return adWatchListId;
    }

    public void setAdWatchListId(Long adWatchListId) {
        this.adWatchListId = adWatchListId;
    }

    public String getAdWatchListName() {
        return adWatchListName;
    }

    public void setAdWatchListName(String adWatchListName) {
        this.adWatchListName = adWatchListName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdWatchListItemDTO adWatchListItemDTO = (AdWatchListItemDTO) o;
        if (adWatchListItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adWatchListItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdWatchListItemDTO{" +
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
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adMenuId=" + getAdMenuId() +
            ", adWatchListId=" + getAdWatchListId() +
            "}";
    }
}
