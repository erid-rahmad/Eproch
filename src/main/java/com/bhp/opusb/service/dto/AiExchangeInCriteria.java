package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.AiStatus;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.AiExchangeIn} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AiExchangeInResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ai-exchange-ins?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AiExchangeInCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AiStatus
     */
    public static class AiStatusFilter extends Filter<AiStatus> {

        private static final long serialVersionUID = 1L;

        public AiStatusFilter() {
        }

        public AiStatusFilter(AiStatusFilter filter) {
            super(filter);
        }

        @Override
        public AiStatusFilter copy() {
            return new AiStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter entityType;

    private LongFilter entityId;

    private AiStatusFilter status;

    public AiExchangeInCriteria() {
    }

    public AiExchangeInCriteria(AiExchangeInCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.entityType = other.entityType == null ? null : other.entityType.copy();
        this.entityId = other.entityId == null ? null : other.entityId.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public AiExchangeInCriteria copy() {
        return new AiExchangeInCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEntityType() {
        return entityType;
    }

    public void setEntityType(StringFilter entityType) {
        this.entityType = entityType;
    }

    public LongFilter getEntityId() {
        return entityId;
    }

    public void setEntityId(LongFilter entityId) {
        this.entityId = entityId;
    }

    public AiStatusFilter getStatus() {
        return status;
    }

    public void setStatus(AiStatusFilter status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AiExchangeInCriteria that = (AiExchangeInCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(entityType, that.entityType) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        entityType,
        entityId,
        status
        );
    }

    @Override
    public String toString() {
        return "AiExchangeInCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entityType != null ? "entityType=" + entityType + ", " : "") +
                (entityId != null ? "entityId=" + entityId + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
