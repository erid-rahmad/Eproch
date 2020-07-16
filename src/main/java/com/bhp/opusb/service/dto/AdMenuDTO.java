package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.AdMenuAction;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdMenu} entity.
 */
public class AdMenuDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    @NotNull
    private String name;

    private String value;

    private String translationKey;

    private String description;

    @NotNull
    private String path;

    private AdMenuAction action;

    private String icon;

    private String redirect;

    private Integer sequence;

    private Boolean alwaysShow;

    private Boolean active;


    private Long adWindowId;
    private String adWindowName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long parentMenuId;
    private String parentMenuName;
    
    private List<AdMenuDTO> adMenus = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AdMenuAction getAction() {
        return action;
    }

    public void setAction(AdMenuAction action) {
        this.action = action;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean isAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdWindowId() {
        return adWindowId;
    }

    public void setAdWindowId(Long aDWindowId) {
        this.adWindowId = aDWindowId;
    }

    public String getAdWindowName() {
        return adWindowName;
    }

    public void setAdWindowName(String adWindowName) {
        this.adWindowName = adWindowName;
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

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long adMenuId) {
        this.parentMenuId = adMenuId;
    }

    public String getParentMenuName() {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    public List<AdMenuDTO> getAdMenus() {
        return adMenus;
    }

    public void setAdMenus(List<AdMenuDTO> adMenus) {
        this.adMenus = adMenus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdMenuDTO adMenuDTO = (AdMenuDTO) o;
        if (adMenuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adMenuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdMenuDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", translationKey='" + getTranslationKey() + "'" +
            ", description='" + getDescription() + "'" +
            ", path='" + getPath() + "'" +
            ", action='" + getAction() + "'" +
            ", icon='" + getIcon() + "'" +
            ", redirect='" + getRedirect() + "'" +
            ", sequence=" + getSequence() +
            ", alwaysShow='" + isAlwaysShow() + "'" +
            ", active='" + isActive() + "'" +
            ", adWindowId=" + getAdWindowId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", parentMenuId=" + getParentMenuId() +
            "}";
    }
}
