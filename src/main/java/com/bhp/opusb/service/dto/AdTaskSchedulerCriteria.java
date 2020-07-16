package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.AdSchedulerTrigger;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.AdTaskScheduler} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdTaskSchedulerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-task-schedulers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdTaskSchedulerCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AdSchedulerTrigger
     */
    public static class AdSchedulerTriggerFilter extends Filter<AdSchedulerTrigger> {

        private static final long serialVersionUID = 1L;

        public AdSchedulerTriggerFilter() {
        }

        public AdSchedulerTriggerFilter(AdSchedulerTriggerFilter filter) {
            super(filter);
        }

        @Override
        public AdSchedulerTriggerFilter copy() {
            return new AdSchedulerTriggerFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter name;

    private StringFilter value;

    private StringFilter description;

    private AdSchedulerTriggerFilter trigger;

    private StringFilter cronExpression;

    private IntegerFilter periodicCount;

    private StringFilter periodicUnit;

    private LongFilter adOrganizationId;

    private LongFilter adTaskId;

    private LongFilter groupId;

    public AdTaskSchedulerCriteria() {
    }

    public AdTaskSchedulerCriteria(AdTaskSchedulerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.trigger = other.trigger == null ? null : other.trigger.copy();
        this.cronExpression = other.cronExpression == null ? null : other.cronExpression.copy();
        this.periodicCount = other.periodicCount == null ? null : other.periodicCount.copy();
        this.periodicUnit = other.periodicUnit == null ? null : other.periodicUnit.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adTaskId = other.adTaskId == null ? null : other.adTaskId.copy();
        this.groupId = other.groupId == null ? null : other.groupId.copy();
    }

    @Override
    public AdTaskSchedulerCriteria copy() {
        return new AdTaskSchedulerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public AdSchedulerTriggerFilter getTrigger() {
        return trigger;
    }

    public void setTrigger(AdSchedulerTriggerFilter trigger) {
        this.trigger = trigger;
    }

    public StringFilter getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(StringFilter cronExpression) {
        this.cronExpression = cronExpression;
    }

    public IntegerFilter getPeriodicCount() {
        return periodicCount;
    }

    public void setPeriodicCount(IntegerFilter periodicCount) {
        this.periodicCount = periodicCount;
    }

    public StringFilter getPeriodicUnit() {
        return periodicUnit;
    }

    public void setPeriodicUnit(StringFilter periodicUnit) {
        this.periodicUnit = periodicUnit;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAdTaskId() {
        return adTaskId;
    }

    public void setAdTaskId(LongFilter adTaskId) {
        this.adTaskId = adTaskId;
    }

    public LongFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdTaskSchedulerCriteria that = (AdTaskSchedulerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(description, that.description) &&
            Objects.equals(trigger, that.trigger) &&
            Objects.equals(cronExpression, that.cronExpression) &&
            Objects.equals(periodicCount, that.periodicCount) &&
            Objects.equals(periodicUnit, that.periodicUnit) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adTaskId, that.adTaskId) &&
            Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        name,
        value,
        description,
        trigger,
        cronExpression,
        periodicCount,
        periodicUnit,
        adOrganizationId,
        adTaskId,
        groupId
        );
    }

    @Override
    public String toString() {
        return "AdTaskSchedulerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (trigger != null ? "trigger=" + trigger + ", " : "") +
                (cronExpression != null ? "cronExpression=" + cronExpression + ", " : "") +
                (periodicCount != null ? "periodicCount=" + periodicCount + ", " : "") +
                (periodicUnit != null ? "periodicUnit=" + periodicUnit + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adTaskId != null ? "adTaskId=" + adTaskId + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
            "}";
    }

}
