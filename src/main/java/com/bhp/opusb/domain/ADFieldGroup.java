package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ADFieldGroup.
 */
@Entity
@Table(name = "ad_field_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADFieldGroup extends AbstractAuditingEntity {

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

    @Column(name = "collapsible")
    private Boolean collapsible;

    @Column(name = "collapse_by_default")
    private Boolean collapseByDefault;

    @Column(name = "active")
    private Boolean active = true;

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

    public ADFieldGroup uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public ADFieldGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCollapsible() {
        return collapsible;
    }

    public ADFieldGroup collapsible(Boolean collapsible) {
        this.collapsible = collapsible;
        return this;
    }

    public void setCollapsible(Boolean collapsible) {
        this.collapsible = collapsible;
    }

    public Boolean isCollapseByDefault() {
        return collapseByDefault;
    }

    public ADFieldGroup collapseByDefault(Boolean collapseByDefault) {
        this.collapseByDefault = collapseByDefault;
        return this;
    }

    public void setCollapseByDefault(Boolean collapseByDefault) {
        this.collapseByDefault = collapseByDefault;
    }

    public Boolean isActive() {
        return active;
    }

    public ADFieldGroup active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        if (!(o instanceof ADFieldGroup)) {
            return false;
        }
        return id != null && id.equals(((ADFieldGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ADFieldGroup{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", collapsible='" + isCollapsible() + "'" +
            ", collapseByDefault='" + isCollapseByDefault() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
