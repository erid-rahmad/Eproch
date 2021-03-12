package com.bhp.opusb.service.dto;

import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.AiStatus;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AiExchangeOut} entity.
 */
public class AiExchangeOutDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String entityType;

    private Long entityId;

    
    @Lob
    private String payload;

    @NotNull
    private AiStatus status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public AiStatus getStatus() {
        return status;
    }

    public void setStatus(AiStatus status) {
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

        AiExchangeOutDTO aiExchangeOutDTO = (AiExchangeOutDTO) o;
        if (aiExchangeOutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aiExchangeOutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AiExchangeOutDTO{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", entityId=" + getEntityId() +
            ", payload='" + getPayload() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
