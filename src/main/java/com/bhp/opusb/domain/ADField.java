package com.bhp.opusb.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.ADColumnType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

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

    @Column(name = "uid")
    private UUID uid;

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
    private Integer columnNo = 1;

    @Column(name = "column_offset")
    private Integer columnOffset = 0;

    @Column(name = "column_span")
    private Integer columnSpan = 8;

    @Column(name = "row_no")
    private Integer rowNo;

    /**
     * Don't use the actual column in the table. Instead, refers to a specific DTO's field.
     */
    @Column(name = "virtual_column_name")
    private String virtualColumnName;

    /**
     * It's automatically filled by the dbSync process. updatable = false.
     */
    @Column(name = "mandatory")
    private Boolean mandatory;

    /**
     * This should be displayed only if mandatory = false.
     */
    @Column(name = "mandatory_logic")
    private String mandatoryLogic;

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

    /**
     * The minimum character length of the string column.
     */
    @Column(name = "min_length")
    private Integer minLength;

    /**
     * The maximum character length of the string column.
     */
    @Column(name = "max_length")
    private Integer maxLength;

    /**
     * The minimum number value of the numeric column.
     */
    @Column(name = "min_value")
    private Long minValue;

    /**
     * The minimum number value of the numeric column.
     */
    @Column(name = "max_value")
    private Long maxValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ADColumnType type;

    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "field", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Where(clause = "active = true")
    @JsonManagedReference
    private List<AdCallout> adCallouts = new ArrayList<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDFields")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("aDFields")
    private ADReference referenceType;

    @ManyToOne
    @JsonIgnoreProperties("aDFields")
    private ADReference adReference;

    @ManyToOne
    @JsonIgnoreProperties("aDFields")
    private ADColumn adColumn;

    @ManyToOne
    @JsonIgnoreProperties("aDFields")
    private AdValidationRule adValidationRule;

    @ManyToOne
    @JsonIgnoreProperties("aDFields")
    private AdButton adButton;

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

    public UUID getUid() {
        return uid;
    }

    public ADField uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
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

    public Integer getColumnOffset() {
        return columnOffset;
    }

    public ADField columnOffset(Integer columnOffset) {
        this.columnOffset = columnOffset;
        return this;
    }

    public void setColumnOffset(Integer columnOffset) {
        this.columnOffset = columnOffset;
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

    public Integer getRowNo() {
        return rowNo;
    }

    public ADField rowNo(Integer rowNo) {
        this.rowNo = rowNo;
        return this;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public String getVirtualColumnName() {
        return virtualColumnName;
    }

    public ADField virtualColumnName(String virtualColumnName) {
        this.virtualColumnName = virtualColumnName;
        return this;
    }

    public void setVirtualColumnName(String virtualColumnName) {
        this.virtualColumnName = virtualColumnName;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public ADField mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getMandatoryLogic() {
        return mandatoryLogic;
    }

    public ADField mandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
        return this;
    }

    public void setMandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
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

    public Integer getMinLength() {
        return minLength;
    }

    public ADField minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public ADField maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Long getMinValue() {
        return minValue;
    }

    public ADField minValue(Long minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public ADField maxValue(Long maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public ADColumnType getType() {
        return type;
    }

    public ADField type(ADColumnType type) {
        this.type = type;
        return this;
    }

    public void setType(ADColumnType type) {
        this.type = type;
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

    public List<AdCallout> getAdCallouts() {
        return adCallouts;
    }

    public ADField adCallouts(List<AdCallout> adCallouts) {
        this.adCallouts = adCallouts;
        return this;
    }

    public ADField addAdCallout(AdCallout adCallout) {
        this.adCallouts.add(adCallout);
        adCallout.setField(this);
        return this;
    }

    public ADField removeAdCallout(AdCallout adCallout) {
        this.adCallouts.remove(adCallout);
        adCallout.setField(null);
        return this;
    }

    public void setAdCallouts(List<AdCallout> adCallouts) {
        this.adCallouts = adCallouts;
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

    public ADReference getReferenceType() {
        return referenceType;
    }

    public ADField referenceType(ADReference aDReference) {
        this.referenceType = aDReference;
        return this;
    }

    public void setReferenceType(ADReference aDReference) {
        this.referenceType = aDReference;
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

    public AdButton getAdButton() {
        return adButton;
    }

    public ADField adButton(AdButton adButton) {
        this.adButton = adButton;
        return this;
    }

    public void setAdButton(AdButton adButton) {
        this.adButton = adButton;
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

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

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
            ", uid='" + getUid() + "'" +
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
            ", columnOffset=" + getColumnOffset() +
            ", columnSpan=" + getColumnSpan() +
            ", rowNo=" + getRowNo() +
            ", virtualColumnName='" + getVirtualColumnName() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", mandatoryLogic='" + getMandatoryLogic() + "'" +
            ", updatable='" + isUpdatable() + "'" +
            ", alwaysUpdatable='" + isAlwaysUpdatable() + "'" +
            ", copyable='" + isCopyable() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", minLength=" + getMinLength() +
            ", maxLength=" + getMaxLength() +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", type='" + getType() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
