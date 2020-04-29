package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ADField.
 */
@Entity
@Table(name = "ad_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADField extends AbstractAuditingEntity implements Serializable {

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
    private Boolean showLabel;

    @Column(name = "show_in_grid")
    private Boolean showInGrid;

    @Column(name = "show_in_detail")
    private Boolean showInDetail;

    @Column(name = "grid_sequence")
    private Integer gridSequence;

    @Column(name = "detail_sequence")
    private Integer detailSequence;

    @Column(name = "display_logic")
    private String displayLogic;

    @Column(name = "writable")
    private Boolean writable;

    @Column(name = "column_no")
    private Integer columnNo;

    @Column(name = "column_span")
    private Integer columnSpan;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDFields")
    private ADClient adClient;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDFields")
    private ADOrganization adOrganization;

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

    public ADClient getAdClient() {
        return adClient;
    }

    public ADField adClient(ADClient aDClient) {
        this.adClient = aDClient;
        return this;
    }

    public void setAdClient(ADClient aDClient) {
        this.adClient = aDClient;
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
            ", writable='" + isWritable() + "'" +
            ", columnNo=" + getColumnNo() +
            ", columnSpan=" + getColumnSpan() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
