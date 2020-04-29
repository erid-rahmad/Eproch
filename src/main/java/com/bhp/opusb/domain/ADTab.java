package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ADTab.
 */
@Entity
@Table(name = "ad_tab")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADTab extends AbstractAuditingEntity implements Serializable {

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

    @Min(value = 0)
    @Column(name = "level")
    private Integer level;

    @Column(name = "writable")
    private Boolean writable;

    @Column(name = "display_logic")
    private String displayLogic;

    @Column(name = "read_only_logic")
    private String readOnlyLogic;

    @Column(name = "filter_query")
    private String filterQuery;

    @Column(name = "order_query")
    private String orderQuery;

    @Column(name = "active")
    private Boolean active;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDTabs")
    private ADWindow adWindow;

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

    public Integer getLevel() {
        return level;
    }

    public ADTab level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
            ", level=" + getLevel() +
            ", writable='" + isWritable() + "'" +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", filterQuery='" + getFilterQuery() + "'" +
            ", orderQuery='" + getOrderQuery() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
