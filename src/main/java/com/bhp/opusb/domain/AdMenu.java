package com.bhp.opusb.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.AdMenuAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdMenu.
 */
@Entity
@Table(name = "ad_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdMenu extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid", nullable = false)
    private UUID uid;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private AdMenuAction action;

    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "parentMenu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdMenu> adMenus = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("adMenus")
    private ADWindow adWindow;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adMenus")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("adMenus")
    private AdMenu parentMenu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public AdMenu uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public AdMenu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AdMenu description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public AdMenu path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AdMenuAction getAction() {
        return action;
    }

    public AdMenu action(AdMenuAction action) {
        this.action = action;
        return this;
    }

    public void setAction(AdMenuAction action) {
        this.action = action;
    }

    public String getIcon() {
        return icon;
    }

    public AdMenu icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<AdMenu> getAdMenus() {
        return adMenus;
    }

    public AdMenu adMenus(Set<AdMenu> adMenus) {
        this.adMenus = adMenus;
        return this;
    }

    public AdMenu addAdMenu(AdMenu adMenu) {
        this.adMenus.add(adMenu);
        adMenu.setParentMenu(this);
        return this;
    }

    public AdMenu removeAdMenu(AdMenu adMenu) {
        this.adMenus.remove(adMenu);
        adMenu.setParentMenu(null);
        return this;
    }

    public void setAdMenus(Set<AdMenu> adMenus) {
        this.adMenus = adMenus;
    }

    public ADWindow getAdWindow() {
        return adWindow;
    }

    public AdMenu adWindow(ADWindow aDWindow) {
        this.adWindow = aDWindow;
        return this;
    }

    public void setAdWindow(ADWindow aDWindow) {
        this.adWindow = aDWindow;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdMenu adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdMenu getParentMenu() {
        return parentMenu;
    }

    public AdMenu parentMenu(AdMenu adMenu) {
        this.parentMenu = adMenu;
        return this;
    }

    public void setParentMenu(AdMenu adMenu) {
        this.parentMenu = adMenu;
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
        if (!(o instanceof AdMenu)) {
            return false;
        }
        return id != null && id.equals(((AdMenu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdMenu{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", path='" + getPath() + "'" +
            ", action='" + getAction() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
