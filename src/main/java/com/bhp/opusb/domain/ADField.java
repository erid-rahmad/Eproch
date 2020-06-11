package com.bhp.opusb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ADField.
 */
@Entity
@Table(name = "ad_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADField extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "hint")
    private String hint;

    @Column(name = "static_text")
    private String staticText;

    @Column(name = "static_field")
    private Boolean staticField;

    @Column(name = "label_only")
    private Boolean labelOnly;

    @Column(name = "show_label")
    private Boolean showLabel = true;

    @Column(name = "show_in_grid")
    private Boolean showInGrid = true;

    @Column(name = "show_in_detail")
    private Boolean showInDetail = true;

    @Column(name = "grid_sequence")
    private Integer gridSequence;

    @Column(name = "detail_sequence")
    private Integer detailSequence;

    @Column(name = "display_logic")
    private String displayLogic;

    @Column(name = "read_only_logic")
    private String readOnlyLogic;

    @Column(name = "writable")
    private Boolean writable = true;

    @Column(name = "column_no")
    private Integer columnNo;

    @Column(name = "column_span")
    private Integer columnSpan;

    @Column(name = "updatable")
    private Boolean updatable;

    @Column(name = "always_updatable")
    private Boolean alwaysUpdatable;

    @Column(name = "copyable")
    private Boolean copyable;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "format_pattern")
    private String formatPattern;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDFields")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("aDFields")
    private ADReference adReference;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDFields")
    private ADColumn adColumn;

    @ManyToOne
    @JsonIgnoreProperties("aDFields")
    private AdValidationRule adValidationRule;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDFields")
    @JsonBackReference
    private ADTab adTab;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ADField name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ADField description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHint() {
        return hint;
    }

    public ADField hint(String hint) {
        this.hint = hint;
        return this;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getStaticText() {
        return staticText;
    }

    public ADField staticText(String staticText) {
        this.staticText = staticText;
        return this;
    }

    public void setStaticText(String staticText) {
        this.staticText = staticText;
    }

    public Boolean isStaticField() {
        return staticField;
    }

    public ADField staticField(Boolean staticField) {
        this.staticField = staticField;
        return this;
    }

    public void setStaticField(Boolean staticField) {
        this.staticField = staticField;
    }

    public Boolean isLabelOnly() {
        return labelOnly;
    }

    public ADField labelOnly(Boolean labelOnly) {
        this.labelOnly = labelOnly;
        return this;
    }

    public void setLabelOnly(Boolean labelOnly) {
        this.labelOnly = labelOnly;
    }

    public Boolean isShowLabel() {
        return showLabel;
    }

    public ADField showLabel(Boolean showLabel) {
        this.showLabel = showLabel;
        return this;
    }

    public void setShowLabel(Boolean showLabel) {
        this.showLabel = showLabel;
    }

    public Boolean isShowInGrid() {
        return showInGrid;
    }

    public ADField showInGrid(Boolean showInGrid) {
        this.showInGrid = showInGrid;
        return this;
    }

    public void setShowInGrid(Boolean showInGrid) {
        this.showInGrid = showInGrid;
    }

    public Boolean isShowInDetail() {
        return showInDetail;
    }

    public ADField showInDetail(Boolean showInDetail) {
        this.showInDetail = showInDetail;
        return this;
    }

    public void setShowInDetail(Boolean showInDetail) {
        this.showInDetail = showInDetail;
    }

    public Integer getGridSequence() {
        return gridSequence;
    }

    public ADField gridSequence(Integer gridSequence) {
        this.gridSequence = gridSequence;
        return this;
    }

    public void setGridSequence(Integer gridSequence) {
        this.gridSequence = gridSequence;
    }

    public Integer getDetailSequence() {
        return detailSequence;
    }

    public ADField detailSequence(Integer detailSequence) {
        this.detailSequence = detailSequence;
        return this;
    }

    public void setDetailSequence(Integer detailSequence) {
        this.detailSequence = detailSequence;
    }

    public String getDisplayLogic() {
        return displayLogic;
    }

    public ADField displayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
        return this;
    }

    public void setDisplayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
    }

    public String getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public ADField readOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
        return this;
    }

    public void setReadOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
    }

    public Boolean isWritable() {
        return writable;
    }

    public ADField writable(Boolean writable) {
        this.writable = writable;
        return this;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    public Integer getColumnNo() {
        return columnNo;
    }

    public ADField columnNo(Integer columnNo) {
        this.columnNo = columnNo;
        return this;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public Integer getColumnSpan() {
        return columnSpan;
    }

    public ADField columnSpan(Integer columnSpan) {
        this.columnSpan = columnSpan;
        return this;
    }

    public void setColumnSpan(Integer columnSpan) {
        this.columnSpan = columnSpan;
    }

    public Boolean isUpdatable() {
        return updatable;
    }

    public ADField updatable(Boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    public void setUpdatable(Boolean updatable) {
        this.updatable = updatable;
    }

    public Boolean isAlwaysUpdatable() {
        return alwaysUpdatable;
    }

    public ADField alwaysUpdatable(Boolean alwaysUpdatable) {
        this.alwaysUpdatable = alwaysUpdatable;
        return this;
    }

    public void setAlwaysUpdatable(Boolean alwaysUpdatable) {
        this.alwaysUpdatable = alwaysUpdatable;
    }

    public Boolean isCopyable() {
        return copyable;
    }

    public ADField copyable(Boolean copyable) {
        this.copyable = copyable;
        return this;
    }

    public void setCopyable(Boolean copyable) {
        this.copyable = copyable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public ADField defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public ADField formatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
        return this;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public Boolean isActive() {
        return active;
    }

    public ADField active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public ADField adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public ADReference getAdReference() {
        return adReference;
    }

    public ADField adReference(ADReference aDReference) {
        this.adReference = aDReference;
        return this;
    }

    public void setAdReference(ADReference aDReference) {
        this.adReference = aDReference;
    }

    public ADColumn getAdColumn() {
        return adColumn;
    }

    public ADField adColumn(ADColumn aDColumn) {
        this.adColumn = aDColumn;
        return this;
    }

    public void setAdColumn(ADColumn aDColumn) {
        this.adColumn = aDColumn;
    }

    public AdValidationRule getAdValidationRule() {
        return adValidationRule;
    }

    public ADField adValidationRule(AdValidationRule adValidationRule) {
        this.adValidationRule = adValidationRule;
        return this;
    }

    public void setAdValidationRule(AdValidationRule adValidationRule) {
        this.adValidationRule = adValidationRule;
    }

    public ADTab getAdTab() {
        return adTab;
    }

    public ADField adTab(ADTab aDTab) {
        this.adTab = aDTab;
        return this;
    }

    public void setAdTab(ADTab aDTab) {
        this.adTab = aDTab;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ADField)) {
            return false;
        }
        return id != null && id.equals(((ADField) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ADField{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", hint='" + getHint() + "'" +
            ", staticText='" + getStaticText() + "'" +
            ", staticField='" + isStaticField() + "'" +
            ", labelOnly='" + isLabelOnly() + "'" +
            ", showLabel='" + isShowLabel() + "'" +
            ", showInGrid='" + isShowInGrid() + "'" +
            ", showInDetail='" + isShowInDetail() + "'" +
            ", gridSequence=" + getGridSequence() +
            ", detailSequence=" + getDetailSequence() +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", writable='" + isWritable() + "'" +
            ", columnNo=" + getColumnNo() +
            ", columnSpan=" + getColumnSpan() +
            ", updatable='" + isUpdatable() + "'" +
            ", alwaysUpdatable='" + isAlwaysUpdatable() + "'" +
            ", copyable='" + isCopyable() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
