package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvaluationMethod} entity.
 */
public class CEvaluationMethodDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 60)
    private String name;

    @DecimalMax(value = "100")
    private BigDecimal priceLimit;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingTypeId;
    private String biddingTypeName;

    private Long eventTypeId;
    private String eventTypeName;

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(BigDecimal priceLimit) {
        this.priceLimit = priceLimit;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getBiddingTypeId() {
        return biddingTypeId;
    }

    public void setBiddingTypeId(Long cBiddingTypeId) {
        this.biddingTypeId = cBiddingTypeId;
    }

    public String getBiddingTypeName() {
        return biddingTypeName;
    }

    public void setBiddingTypeName(String biddingTypeName) {
        this.biddingTypeName = biddingTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEvaluationMethodDTO cEvaluationMethodDTO = (CEvaluationMethodDTO) o;
        if (cEvaluationMethodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEvaluationMethodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEvaluationMethodDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", priceLimit=" + priceLimit +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", biddingTypeId=" + biddingTypeId +
            ", biddingTypeName='" + biddingTypeName + '\'' +
            ", eventTypeId=" + eventTypeId +
            ", eventTypeName='" + eventTypeName + '\'' +
            '}';
    }
}
