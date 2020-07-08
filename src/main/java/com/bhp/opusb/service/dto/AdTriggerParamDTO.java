package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdTriggerParam} entity.
 */
public class AdTriggerParamDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private String name;

    private Boolean mandatory;

    private String mandatoryLogic;

    private String displayLogic;

    private String readOnlyLogic;

    private String defaultValue;

    private String formatPattern;

    private Integer minLength;

    private Integer maxLength;

    private Long minValue;

    private Long maxValue;


    private Long adOrganizationId;
    private Long adOrganizationName;

    private Long adTriggerId;
    private Long adTriggerName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getMandatoryLogic() {
        return mandatoryLogic;
    }

    public void setMandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
    }

    public String getDisplayLogic() {
        return displayLogic;
    }

    public void setDisplayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
    }

    public String getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public void setReadOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(Long adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getAdTriggerId() {
        return adTriggerId;
    }

    public void setAdTriggerId(Long adTriggerId) {
        this.adTriggerId = adTriggerId;
    }

    public Long getAdTriggerName() {
        return adTriggerName;
    }

    public void setAdTriggerName(Long adTriggerName) {
        this.adTriggerName = adTriggerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdTriggerParamDTO adTriggerParamDTO = (AdTriggerParamDTO) o;
        if (adTriggerParamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adTriggerParamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdTriggerParamDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", mandatoryLogic='" + getMandatoryLogic() + "'" +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", minLength=" + getMinLength() +
            ", maxLength=" + getMaxLength() +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adTriggerId=" + getAdTriggerId() +
            "}";
    }
}
