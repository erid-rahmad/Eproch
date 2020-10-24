package com.bhp.opusb.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

/**
 * A ADTab.
 */
@Entity
@Table(name = "ad_tab")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADTab extends AbstractAuditingEntity {

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

    @Column(name = "icon_name")
    private String iconName;

    /**
     * Whether or not to show the tree view in the layout.
     */
    @Column(name = "tree_view")
    private Boolean treeView;

    /**
     * Target API endpoint for the CRUD operations. Override the same property in
     * AdTable.
     */
    @Column(name = "target_endpoint")
    private String targetEndpoint;

    /**
     * Indicates to use the form layout by default instead of displaying the table
     * layout first.
     */
    @Column(name = "single_row")
    private Boolean singleRow;

    /**
     * Whether or not the record is deletable from the table.
     */
    @Column(name = "deletable")
    private Boolean deletable;

    /**
     * Whether or not to allow insert a new record to the table.
     */
    @Column(name = "insertable")
    private Boolean insertable;

    /**
     * If false, force read-only to the whole fields in the tab.
     */
    @Column(name = "writable")
    private Boolean writable = true;

    @Column(name = "display_logic")
    private String displayLogic;

    @Column(name = "read_only_logic")
    private String readOnlyLogic;

    @Column(name = "filter_query")
    private String filterQuery;

    @Column(name = "order_query")
    private String orderQuery;

    @Column(name = "tab_sequence")
    private Integer tabSequence;

    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "parentTab")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Where(clause = "active = true")
    private Set<ADTab> aDTabs = new HashSet<>();

    @OneToMany(mappedBy = "adTab", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Where(clause = "active = true")
    @JsonManagedReference
    private Set<ADField> aDFields = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTabs")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTabs")
    private ADTable adTable;

    @ManyToOne
    @JsonIgnoreProperties("aDTabs")
    private ADColumn parentColumn;

    @ManyToOne
    @JsonIgnoreProperties("aDTabs")
    private ADColumn foreignColumn;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTabs")
    private ADWindow adWindow;

    @ManyToOne
    @JsonIgnoreProperties("aDTabs")
    private ADTab parentTab;

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

    public ADTab uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public ADTab name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ADTab description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconName() {
        return iconName;
    }

    public ADTab iconName(String iconName) {
        this.iconName = iconName;
        return this;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Boolean isTreeView() {
        return treeView;
    }

    public ADTab treeView(Boolean treeView) {
        this.treeView = treeView;
        return this;
    }

    public void setTreeView(Boolean treeView) {
        this.treeView = treeView;
    }

    public String getTargetEndpoint() {
        return targetEndpoint;
    }

    public ADTab targetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
        return this;
    }

    public void setTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public Boolean isSingleRow() {
        return singleRow;
    }

    public ADTab singleRow(Boolean singleRow) {
        this.singleRow = singleRow;
        return this;
    }

    public void setSingleRow(Boolean singleRow) {
        this.singleRow = singleRow;
    }

    public Boolean isDeletable() {
        return deletable;
    }

    public ADTab deletable(Boolean deletable) {
        this.deletable = deletable;
        return this;
    }

    public void setDeletable(Boolean deletable) {
        this.deletable = deletable;
    }

    public Boolean isInsertable() {
        return insertable;
    }

    public ADTab insertable(Boolean insertable) {
        this.insertable = insertable;
        return this;
    }

    public void setInsertable(Boolean insertable) {
        this.insertable = insertable;
    }

    public Boolean isWritable() {
        return writable;
    }

    public ADTab writable(Boolean writable) {
        this.writable = writable;
        return this;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    public String getDisplayLogic() {
        return displayLogic;
    }

    public ADTab displayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
        return this;
    }

    public void setDisplayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
    }

    public String getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public ADTab readOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
        return this;
    }

    public void setReadOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
    }

    public String getFilterQuery() {
        return filterQuery;
    }

    public ADTab filterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
        return this;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public String getOrderQuery() {
        return orderQuery;
    }

    public ADTab orderQuery(String orderQuery) {
        this.orderQuery = orderQuery;
        return this;
    }

    public void setOrderQuery(String orderQuery) {
        this.orderQuery = orderQuery;
    }

    public Integer getTabSequence() {
        return tabSequence;
    }

    public ADTab tabSequence(Integer tabSequence) {
        this.tabSequence = tabSequence;
        return this;
    }

    public void setTabSequence(Integer tabSequence) {
        this.tabSequence = tabSequence;
    }

    public Boolean isActive() {
        return active;
    }

    public ADTab active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<ADTab> getADTabs() {
        return aDTabs;
    }

    public ADTab aDTabs(Set<ADTab> aDTabs) {
        this.aDTabs = aDTabs;
        return this;
    }

    public ADTab addADTab(ADTab aDTab) {
        this.aDTabs.add(aDTab);
        aDTab.setParentTab(this);
        return this;
    }

    public ADTab removeADTab(ADTab aDTab) {
        this.aDTabs.remove(aDTab);
        aDTab.setParentTab(null);
        return this;
    }

    public void setADTabs(Set<ADTab> aDTabs) {
        this.aDTabs = aDTabs;
    }

    public Set<ADField> getADFields() {
        return aDFields;
    }

    public ADTab aDFields(Set<ADField> aDFields) {
        this.aDFields = aDFields;
        return this;
    }

    public ADTab addADField(ADField aDField) {
        this.aDFields.add(aDField);
        aDField.setAdTab(this);
        return this;
    }

    public ADTab removeADField(ADField aDField) {
        this.aDFields.remove(aDField);
        aDField.setAdTab(null);
        return this;
    }

    public void setADFields(Set<ADField> aDFields) {
        this.aDFields = aDFields;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public ADTab adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public ADTable getAdTable() {
        return adTable;
    }

    public ADTab adTable(ADTable aDTable) {
        this.adTable = aDTable;
        return this;
    }

    public void setAdTable(ADTable aDTable) {
        this.adTable = aDTable;
    }

    public ADColumn getParentColumn() {
        return parentColumn;
    }

    public ADTab parentColumn(ADColumn aDColumn) {
        this.parentColumn = aDColumn;
        return this;
    }

    public void setParentColumn(ADColumn aDColumn) {
        this.parentColumn = aDColumn;
    }

    public ADColumn getForeignColumn() {
        return foreignColumn;
    }

    public ADTab foreignColumn(ADColumn aDColumn) {
        this.foreignColumn = aDColumn;
        return this;
    }

    public void setForeignColumn(ADColumn aDColumn) {
        this.foreignColumn = aDColumn;
    }

    public ADWindow getAdWindow() {
        return adWindow;
    }

    public ADTab adWindow(ADWindow aDWindow) {
        this.adWindow = aDWindow;
        return this;
    }

    public void setAdWindow(ADWindow aDWindow) {
        this.adWindow = aDWindow;
    }

    public ADTab getParentTab() {
        return parentTab;
    }

    public ADTab parentTab(ADTab aDTab) {
        this.parentTab = aDTab;
        return this;
    }

    public void setParentTab(ADTab aDTab) {
        this.parentTab = aDTab;
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
        if (!(o instanceof ADTab)) {
            return false;
        }
        return id != null && id.equals(((ADTab) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ADTab{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", iconName='" + getIconName() + "'" +
            ", treeView='" + isTreeView() + "'" +
            ", targetEndpoint='" + getTargetEndpoint() + "'" +
            ", singleRow='" + isSingleRow() + "'" +
            ", deletable='" + isDeletable() + "'" +
            ", insertable='" + isInsertable() + "'" +
            ", writable='" + isWritable() + "'" +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", filterQuery='" + getFilterQuery() + "'" +
            ", orderQuery='" + getOrderQuery() + "'" +
            ", tabSequence=" + getTabSequence() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
