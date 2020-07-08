package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdButton.
 */
@Entity
@Table(name = "ad_button")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdButton extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tooltip")
    private String tooltip;

    @Column(name = "description")
    private String description;

    @Column(name = "toolbar")
    private Boolean toolbar;

    @Column(name = "icon")
    private String icon;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adButtons")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("adButtons")
    private AdTrigger adTrigger;

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

    public AdButton uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdButton active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public AdButton name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTooltip() {
        return tooltip;
    }

    public AdButton tooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getDescription() {
        return description;
    }

    public AdButton description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isToolbar() {
        return toolbar;
    }

    public AdButton toolbar(Boolean toolbar) {
        this.toolbar = toolbar;
        return this;
    }

    public void setToolbar(Boolean toolbar) {
        this.toolbar = toolbar;
    }

    public String getIcon() {
        return icon;
    }

    public AdButton icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdButton adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdTrigger getAdTrigger() {
        return adTrigger;
    }

    public AdButton adTrigger(AdTrigger adTrigger) {
        this.adTrigger = adTrigger;
        return this;
    }

    public void setAdTrigger(AdTrigger adTrigger) {
        this.adTrigger = adTrigger;
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
        if (!(o instanceof AdButton)) {
            return false;
        }
        return id != null && id.equals(((AdButton) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdButton{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", tooltip='" + getTooltip() + "'" +
            ", description='" + getDescription() + "'" +
            ", toolbar='" + isToolbar() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
