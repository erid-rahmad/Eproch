package com.bhp.opusb.service.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADTab} entity.
 */
public class ADTabDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    @NotNull
    private String name;

    private String description;

    private String iconName;

    /**
     * Whether or not to show the tree view in the layout.
     */
    @ApiModelProperty(value = "Whether or not to show the tree view in the layout.")
    private Boolean treeView;

    /**
     * Target API endpoint for the CRUD operations. Override the same property in
     * AdTable.
     */
    @ApiModelProperty(value = "Target API endpoint for the CRUD operations. Override the same property in AdTable.")
    private String targetEndpoint;

    /**
     * Indicates to use the form layout by default instead of displaying the table
     * layout first.
     */
    @ApiModelProperty(value = "Indicates to use the form layout by default instead of displaying the table layout first.")
    private Boolean singleRow;

    /**
     * Whether or not the record is deletable from the table.
     */
    @ApiModelProperty(value = "Whether or not the record is deletable from the table.")
    private Boolean deletable;

    /**
     * Whether or not to allow insert a new record to the table.
     */
    @ApiModelProperty(value = "Whether or not to allow insert a new record to the table.")
    private Boolean insertable;

    /**
     * Force read-only to the whole fields in the tab.
     */
    @ApiModelProperty(value = "Force read-only to the whole fields in the tab.")
    private Boolean writable;

    private String displayLogic;

    private String readOnlyLogic;

    private String filterQuery;

    private String orderQuery;

    private Integer tabSequence;

    private Boolean active;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adTableId;
    private String adTableName;

    private Long parentColumnId;
    private String parentColumnName;
    private String parentColumnSqlName;

    private Long foreignColumnId;
    private String foreignColumnName;
    private String foreignColumnSqlName;

    private Long adWindowId;
    private String adWindowName;

    private Long parentTabId;
    private String parentTabName;

    private Set<ADFieldDTO> aDFields = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Boolean isTreeView() {
        return treeView;
    }

    public void setTreeView(Boolean treeView) {
        this.treeView = treeView;
    }

    public String getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public Boolean isSingleRow() {
        return singleRow;
    }

    public void setSingleRow(Boolean singleRow) {
        this.singleRow = singleRow;
    }

    public Boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(Boolean deletable) {
        this.deletable = deletable;
    }

    public Boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(Boolean insertable) {
        this.insertable = insertable;
    }

    public Boolean isWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
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

    public String getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public String getOrderQuery() {
        return orderQuery;
    }

    public void setOrderQuery(String orderQuery) {
        this.orderQuery = orderQuery;
    }

    public Integer getTabSequence() {
        return tabSequence;
    }

    public void setTabSequence(Integer tabSequence) {
        this.tabSequence = tabSequence;
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

    public Long getAdTableId() {
        return adTableId;
    }

    public void setAdTableId(Long aDTableId) {
        this.adTableId = aDTableId;
    }

    public String getAdTableName() {
        return adTableName;
    }

    public void setAdTableName(String adTableName) {
        this.adTableName = adTableName;
    }

    public Long getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(Long aDColumnId) {
        this.parentColumnId = aDColumnId;
    }

    public String getParentColumnName() {
        return parentColumnName;
    }

    public void setParentColumnName(String parentColumnName) {
        this.parentColumnName = parentColumnName;
    }

    public String getParentColumnSqlName() {
        return parentColumnSqlName;
    }

    public void setParentColumnSqlName(String parentColumnSqlName) {
        this.parentColumnSqlName = parentColumnSqlName;
    }

    public Long getForeignColumnId() {
        return foreignColumnId;
    }

    public void setForeignColumnId(Long aDColumnId) {
        this.foreignColumnId = aDColumnId;
    }

    public String getForeignColumnName() {
        return foreignColumnName;
    }

    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName = foreignColumnName;
    }

    public String getForeignColumnSqlName() {
        return foreignColumnSqlName;
    }

    public void setForeignColumnSqlName(String foreignColumnSqlName) {
        this.foreignColumnSqlName = foreignColumnSqlName;
    }

    public Long getAdWindowId() {
        return adWindowId;
    }

    public void setAdWindowId(Long aDWindowId) {
        this.adWindowId = aDWindowId;
    }

    public String getAdWindowName() {
        return adWindowName;
    }

    public void setAdWindowName(String adWindowName) {
        this.adWindowName = adWindowName;
    }

    public Long getParentTabId() {
        return parentTabId;
    }

    public void setParentTabId(Long aDTabId) {
        this.parentTabId = aDTabId;
    }

    public String getParentTabName() {
        return parentTabName;
    }

    public void setParentTabName(String parentTabName) {
        this.parentTabName = parentTabName;
    }

    public Set<ADFieldDTO> getADFields() {
        return aDFields;
    }

    public void setADFields(Set<ADFieldDTO> aDFields) {
        this.aDFields = aDFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ADTabDTO aDTabDTO = (ADTabDTO) o;
        if (aDTabDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aDTabDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ADTabDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", iconName='" + getIconName() + "'" +
            ", targetEndpoint='" + getTargetEndpoint() + "'" +
            ", singleRow='" + isSingleRow() + "'" +
            ", deletable='" + isDeletable() + "'" +
            ", writable='" + isWritable() + "'" +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", filterQuery='" + getFilterQuery() + "'" +
            ", orderQuery='" + getOrderQuery() + "'" +
            ", tabSequence=" + getTabSequence() +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adTableId=" + getAdTableId() +
            ", parentColumnId=" + getParentColumnId() +
            ", foreignColumnId=" + getForeignColumnId() +
            ", adWindowId=" + getAdWindowId() +
            ", parentTabId=" + getParentTabId() +
            "}";
    }
}
