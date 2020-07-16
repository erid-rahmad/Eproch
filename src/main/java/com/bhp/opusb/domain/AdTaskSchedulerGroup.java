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
 * A AdTaskSchedulerGroup.
 */
@Entity
@Table(name = "ad_task_scheduler_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdTaskSchedulerGroup extends AbstractAuditingEntity {

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

    @NotNull
    @Column(name = "value", nullable = false, unique = true)
    private String value;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "group")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private Set<AdTaskScheduler> adTaskSchedulers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTaskSchedulerGroups")
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

    public AdTaskSchedulerGroup uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdTaskSchedulerGroup active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public AdTaskSchedulerGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public AdTaskSchedulerGroup value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public AdTaskSchedulerGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AdTaskScheduler> getAdTaskSchedulers() {
        return adTaskSchedulers;
    }

    public AdTaskSchedulerGroup adTaskSchedulers(Set<AdTaskScheduler> adTaskSchedulers) {
        this.adTaskSchedulers = adTaskSchedulers;
        return this;
    }

    public AdTaskSchedulerGroup addAdTaskScheduler(AdTaskScheduler adTaskScheduler) {
        this.adTaskSchedulers.add(adTaskScheduler);
        adTaskScheduler.setGroup(this);
        return this;
    }

    public AdTaskSchedulerGroup removeAdTaskScheduler(AdTaskScheduler adTaskScheduler) {
        this.adTaskSchedulers.remove(adTaskScheduler);
        adTaskScheduler.setGroup(null);
        return this;
    }

    public void setAdTaskSchedulers(Set<AdTaskScheduler> adTaskSchedulers) {
        this.adTaskSchedulers = adTaskSchedulers;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdTaskSchedulerGroup adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof AdTaskSchedulerGroup)) {
            return false;
        }
        return id != null && id.equals(((AdTaskSchedulerGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdTaskSchedulerGroup{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
