package com.bhp.opusb.service.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.ADField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADTab} entity.
 */
public class ADTabDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String targetEndpoint;

    private Boolean writable;

    private String displayLogic;

    private String readOnlyLogic;

    private String filterQuery;

    private String orderQuery;

    private Boolean active;

    private Long adClientId;

    private Long adOrganizationId;

    private Long adTableId;

    private Long parentColumnId;

    private Long foreignColumnId;

    private Long adWindowId;

    private Long parentTabId;
    
    @JsonProperty("adFields")
    private Set<ADField> aDFields = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdClientId() {
        return adClientId;
    }

    public void setAdClientId(Long aDClientId) {
        this.adClientId = aDClientId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getAdTableId() {
        return adTableId;
    }

    public void setAdTableId(Long aDTableId) {
        this.adTableId = aDTableId;
    }

    public Long getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(Long aDColumnId) {
        this.parentColumnId = aDColumnId;
    }

    public Long getForeignColumnId() {
        return foreignColumnId;
    }

    public void setForeignColumnId(Long aDColumnId) {
        this.foreignColumnId = aDColumnId;
    }

    public Long getAdWindowId() {
        return adWindowId;
    }

    public void setAdWindowId(Long aDWindowId) {
        this.adWindowId = aDWindowId;
    }

    public Long getParentTabId() {
        return parentTabId;
    }

    public void setParentTabId(Long aDTabId) {
        this.parentTabId = aDTabId;
    }

    public Set<ADField> getADFields() {
        return aDFields;
    }

    public void setADFields(Set<ADField> aDFields) {
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
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", targetEndpoint='" + getTargetEndpoint() + "'" +
            ", writable='" + isWritable() + "'" +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", filterQuery='" + getFilterQuery() + "'" +
            ", orderQuery='" + getOrderQuery() + "'" +
            ", active='" + isActive() + "'" +
            ", adClientId=" + getAdClientId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adTableId=" + getAdTableId() +
            ", parentColumnId=" + getParentColumnId() +
            ", foreignColumnId=" + getForeignColumnId() +
            ", adWindowId=" + getAdWindowId() +
            ", parentTabId=" + getParentTabId() +
            "}";
    }
}
