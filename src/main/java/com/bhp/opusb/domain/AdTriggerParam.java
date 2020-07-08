package com.bhp.opusb.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdTriggerParam.
 */
@Entity
@Table(name = "ad_trigger_param")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdTriggerParam implements Serializable {

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

    @Column(name = "mandatory")
    private Boolean mandatory;

    @Column(name = "mandatory_logic")
    private String mandatoryLogic;

    @Column(name = "display_logic")
    private String displayLogic;

    @Column(name = "read_only_logic")
    private String readOnlyLogic;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "format_pattern")
    private String formatPattern;

    @Column(name = "min_length")
    private Integer minLength;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTriggerParams")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTriggerParams")
    private AdTrigger adTrigger;

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

    public AdTriggerParam uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdTriggerParam active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public AdTriggerParam name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public AdTriggerParam mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getMandatoryLogic() {
        return mandatoryLogic;
    }

    public AdTriggerParam mandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
        return this;
    }

    public void setMandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
    }

    public String getDisplayLogic() {
        return displayLogic;
    }

    public AdTriggerParam displayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
        return this;
    }

    public void setDisplayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
    }

    public String getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public AdTriggerParam readOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
        return this;
    }

    public void setReadOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public AdTriggerParam defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public AdTriggerParam formatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
        return this;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public AdTriggerParam minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public AdTriggerParam maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Long getMinValue() {
        return minValue;
    }

    public AdTriggerParam minValue(Long minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public AdTriggerParam maxValue(Long maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdTriggerParam adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdTrigger getAdTrigger() {
        return adTrigger;
    }

    public AdTriggerParam adTrigger(AdTrigger adTrigger) {
        this.adTrigger = adTrigger;
        return this;
    }

    public void setAdTrigger(AdTrigger adTrigger) {
        this.adTrigger = adTrigger;
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
        if (!(o instanceof AdTriggerParam)) {
            return false;
        }
        return id != null && id.equals(((AdTriggerParam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdTriggerParam{" +
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
            "}";
    }
}
