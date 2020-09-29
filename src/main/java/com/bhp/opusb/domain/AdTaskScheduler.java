package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.AdSchedulerTrigger;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdTaskScheduler.
 */
@Entity
@Table(name = "ad_task_scheduler")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdTaskScheduler extends AbstractAuditingEntity {

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
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false, unique = true)
    private String value;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trigger", nullable = false)
    private AdSchedulerTrigger trigger;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "periodic_count")
    private Integer periodicCount;

    @Column(name = "periodic_unit")
    private String periodicUnit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTaskSchedulers")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("adTaskSchedulers")
    private AdTask adTask;

    @ManyToOne
    @JsonIgnoreProperties("adTaskSchedulers")
    private AdTrigger adTrigger;

    @ManyToOne
    @JsonIgnoreProperties("adTaskSchedulers")
    @JsonBackReference
    private AdTaskSchedulerGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public AdTaskScheduler() {}

    public AdTaskScheduler(Boolean active, AdSchedulerTrigger trigger, String cronExpression, Integer periodicCount, String periodicUnit) {
        this.active = active;
        this.trigger = trigger;
        this.cronExpression = cronExpression;
        this.periodicCount = periodicCount;
        this.periodicUnit = periodicUnit;
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

    public AdTaskScheduler uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdTaskScheduler active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public AdTaskScheduler name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public AdTaskScheduler value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public AdTaskScheduler description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdSchedulerTrigger getTrigger() {
        return trigger;
    }

    public AdTaskScheduler trigger(AdSchedulerTrigger trigger) {
        this.trigger = trigger;
        return this;
    }

    public void setTrigger(AdSchedulerTrigger trigger) {
        this.trigger = trigger;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public AdTaskScheduler cronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getPeriodicCount() {
        return periodicCount;
    }

    public AdTaskScheduler periodicCount(Integer periodicCount) {
        this.periodicCount = periodicCount;
        return this;
    }

    public void setPeriodicCount(Integer periodicCount) {
        this.periodicCount = periodicCount;
    }

    public String getPeriodicUnit() {
        return periodicUnit;
    }

    public AdTaskScheduler periodicUnit(String periodicUnit) {
        this.periodicUnit = periodicUnit;
        return this;
    }

    public void setPeriodicUnit(String periodicUnit) {
        this.periodicUnit = periodicUnit;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdTaskScheduler adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdTask getAdTask() {
        return adTask;
    }

    public AdTaskScheduler adTask(AdTask adTask) {
        this.adTask = adTask;
        return this;
    }

    public void setAdTask(AdTask adTask) {
        this.adTask = adTask;
    }

    public AdTrigger getAdTrigger() {
        return adTrigger;
    }

    public AdTaskScheduler adTrigger(AdTrigger adTrigger) {
        this.adTrigger = adTrigger;
        return this;
    }

    public void setAdTrigger(AdTrigger adTrigger) {
        this.adTrigger = adTrigger;
    }

    public AdTaskSchedulerGroup getGroup() {
        return group;
    }

    public AdTaskScheduler group(AdTaskSchedulerGroup adTaskSchedulerGroup) {
        this.group = adTaskSchedulerGroup;
        return this;
    }

    public void setGroup(AdTaskSchedulerGroup adTaskSchedulerGroup) {
        this.group = adTaskSchedulerGroup;
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
        if (!(o instanceof AdTaskScheduler)) {
            return false;
        }
        return id != null && id.equals(((AdTaskScheduler) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdTaskScheduler{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", trigger='" + getTrigger() + "'" +
            ", cronExpression='" + getCronExpression() + "'" +
            ", periodicCount=" + getPeriodicCount() +
            ", periodicUnit='" + getPeriodicUnit() + "'" +
            "}";
    }
}
