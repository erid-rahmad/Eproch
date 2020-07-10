package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.domain.AdValidationRule;
import com.bhp.opusb.domain.enumeration.ADColumnType;

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

    @NotNull
    private String value;

    @NotNull
    private ADColumnType type;

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
    private String adOrganizationName;

    private Long adReferenceId;
    private String adReferenceName;

    private Long adValidationRuleId;
    private String adValidationRuleName;

    private Long adTriggerId;
    private String adTriggerName;

    private ADReference adReference;
    private AdValidationRule adValidationRule;
    
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ADColumnType getType() {
        return type;
    }

    public void setType(ADColumnType type) {
        this.type = type;
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

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getAdReferenceId() {
        return adReferenceId;
    }

    public void setAdReferenceId(Long aDReferenceId) {
        this.adReferenceId = aDReferenceId;
    }

    public String getAdReferenceName() {
        return adReferenceName;
    }

    public void setAdReferenceName(String adReferenceName) {
        this.adReferenceName = adReferenceName;
    }

    public Long getAdValidationRuleId() {
        return adValidationRuleId;
    }

    public void setAdValidationRuleId(Long adValidationRuleId) {
        this.adValidationRuleId = adValidationRuleId;
    }

    public String getAdValidationRuleName() {
        return adValidationRuleName;
    }

    public void setAdValidationRuleName(String adValidationRuleName) {
        this.adValidationRuleName = adValidationRuleName;
    }

    public Long getAdTriggerId() {
        return adTriggerId;
    }

    public void setAdTriggerId(Long adTriggerId) {
        this.adTriggerId = adTriggerId;
    }

    public String getAdTriggerName() {
        return adTriggerName;
    }

    public void setAdTriggerName(String adTriggerName) {
        this.adTriggerName = adTriggerName;
    }

    public ADReference getAdReference() {
        return adReference;
    }

    public void setAdReference(ADReference adReference) {
        this.adReference = adReference;
    }

    public AdValidationRule getAdValidationRule() {
        return adValidationRule;
    }

    public void setAdValidationRule(AdValidationRule adValidationRule) {
        this.adValidationRule = adValidationRule;
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
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
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
            ", adReferenceId=" + getAdReferenceId() +
            ", adValidationRuleId=" + getAdValidationRuleId() +
            ", adTriggerId=" + getAdTriggerId() +
            "}";
    }
}
