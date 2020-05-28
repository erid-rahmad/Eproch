package com.bhp.opusb.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "target_endpoint")
    private String targetEndpoint;

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

    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "parentTab")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ADTab> aDTabs = new HashSet<>();

    @OneToMany(mappedBy = "adTab")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private Set<ADField> aDFields = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTabs")
    private ADClient adClient;

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

    public ADClient getAdClient() {
        return adClient;
    }

    public ADTab adClient(ADClient aDClient) {
        this.adClient = aDClient;
        return this;
    }

    public void setAdClient(ADClient aDClient) {
        this.adClient = aDClient;
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
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", targetEndpoint='" + getTargetEndpoint() + "'" +
            ", writable='" + isWritable() + "'" +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", filterQuery='" + getFilterQuery() + "'" +
            ", orderQuery='" + getOrderQuery() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
