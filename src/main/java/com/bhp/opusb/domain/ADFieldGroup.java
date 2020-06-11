package com.bhp.opusb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ADFieldGroup.
 */
@Entity
@Table(name = "ad_field_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADFieldGroup extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "collapsible")
    private Boolean collapsible;

    @Column(name = "collapse_by_default")
    private Boolean collapseByDefault;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", name='" + getName() + "'" +
            ", collapsible='" + isCollapsible() + "'" +
            ", collapseByDefault='" + isCollapseByDefault() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
