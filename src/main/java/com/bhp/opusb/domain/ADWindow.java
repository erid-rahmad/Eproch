package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.bhp.opusb.domain.enumeration.ADWindowType;

/**
 * A ADWindow.
 */
@Entity
@Table(name = "ad_window")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADWindow extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ADWindowType type;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "adWindow")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ADTab> aDTabs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDWindows")
    private ADClient adClient;

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

    public ADClient getAdClient() {
        return adClient;
    }

    public ADWindow adClient(ADClient aDClient) {
        this.adClient = aDClient;
        return this;
    }

    public void setAdClient(ADClient aDClient) {
        this.adClient = aDClient;
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
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
