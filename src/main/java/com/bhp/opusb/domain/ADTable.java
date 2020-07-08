package com.bhp.opusb.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ADTable.
 */
@Entity
@Table(name = "ad_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADTable extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "view")
    private Boolean view;

    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "adTable")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private Set<ADColumn> aDColumns = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTables")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public ADTable() {}

    public ADTable(@NotNull String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public ADTable uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
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

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

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
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", view='" + isView() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
