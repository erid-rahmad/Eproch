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

import com.bhp.opusb.domain.enumeration.ADWindowType;
import com.bhp.opusb.domain.enumeration.AdAccessLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ADWindow.
 */
@Entity
@Table(name = "ad_window")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADWindow extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "title_logic")
    private String titleLogic;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ADWindowType type;

    @Column(name = "tree_view")
    private Boolean treeView;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level")
    private AdAccessLevel accessLevel;

    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "adWindow")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ADTab> aDTabs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDWindows")
    private ADOrganization adOrganization;

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

    public ADWindow uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public ADWindow name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ADWindow description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleLogic() {
        return titleLogic;
    }

    public ADWindow titleLogic(String titleLogic) {
        this.titleLogic = titleLogic;
        return this;
    }

    public void setTitleLogic(String titleLogic) {
        this.titleLogic = titleLogic;
    }

    public ADWindowType getType() {
        return type;
    }

    public ADWindow type(ADWindowType type) {
        this.type = type;
        return this;
    }

    public void setType(ADWindowType type) {
        this.type = type;
    }

    public Boolean isTreeView() {
        return treeView;
    }

    public ADWindow treeView(Boolean treeView) {
        this.treeView = treeView;
        return this;
    }

    public void setTreeView(Boolean treeView) {
        this.treeView = treeView;
    }

    public AdAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public ADWindow accessLevel(AdAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
        return this;
    }

    public void setAccessLevel(AdAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Boolean isActive() {
        return active;
    }

    public ADWindow active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<ADTab> getADTabs() {
        return aDTabs;
    }

    public ADWindow aDTabs(Set<ADTab> aDTabs) {
        this.aDTabs = aDTabs;
        return this;
    }

    public ADWindow addADTab(ADTab aDTab) {
        this.aDTabs.add(aDTab);
        aDTab.setAdWindow(this);
        return this;
    }

    public ADWindow removeADTab(ADTab aDTab) {
        this.aDTabs.remove(aDTab);
        aDTab.setAdWindow(null);
        return this;
    }

    public void setADTabs(Set<ADTab> aDTabs) {
        this.aDTabs = aDTabs;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public ADWindow adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof ADWindow)) {
            return false;
        }
        return id != null && id.equals(((ADWindow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ADWindow{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", titleLogic='" + getTitleLogic() + "'" +
            ", type='" + getType() + "'" +
            ", treeView='" + isTreeView() + "'" +
            ", accessLevel='" + getAccessLevel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
