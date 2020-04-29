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

/**
 * A ADTable.
 */
@Entity
@Table(name = "ad_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADTable extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "view")
    private Boolean view;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "adTable")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ADColumn> aDColumns = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTables")
    private ADClient adClient;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTables")
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

    public ADTable name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isView() {
        return view;
    }

    public ADTable view(Boolean view) {
        this.view = view;
        return this;
    }

    public void setView(Boolean view) {
        this.view = view;
    }

    public Boolean isActive() {
        return active;
    }

    public ADTable active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<ADColumn> getADColumns() {
        return aDColumns;
    }

    public ADTable aDColumns(Set<ADColumn> aDColumns) {
        this.aDColumns = aDColumns;
        return this;
    }

    public ADTable addADColumn(ADColumn aDColumn) {
        this.aDColumns.add(aDColumn);
        aDColumn.setAdTable(this);
        return this;
    }

    public ADTable removeADColumn(ADColumn aDColumn) {
        this.aDColumns.remove(aDColumn);
        aDColumn.setAdTable(null);
        return this;
    }

    public void setADColumns(Set<ADColumn> aDColumns) {
        this.aDColumns = aDColumns;
    }

    public ADClient getAdClient() {
        return adClient;
    }

    public ADTable adClient(ADClient aDClient) {
        this.adClient = aDClient;
        return this;
    }

    public void setAdClient(ADClient aDClient) {
        this.adClient = aDClient;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public ADTable adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof ADTable)) {
            return false;
        }
        return id != null && id.equals(((ADTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ADTable{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", view='" + isView() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
