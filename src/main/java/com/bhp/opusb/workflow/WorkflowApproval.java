package com.bhp.opusb.workflow;

import java.io.Serializable;
import java.util.Objects;

public class WorkflowApproval implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String tableName;
    private Long recordId;
    private Object dto;

    public WorkflowApproval(String id, String tableName, Long recordId, Object dto) {
        this.id = id;
        this.tableName = tableName;
        this.recordId = recordId;
        this.dto = dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Object getDto() {
        return dto;
    }

    public void setDto(Object dto) {
        this.dto = dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkflowApproval approval = (WorkflowApproval) o;
        if (approval.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), approval.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkflowApproval{" +
            "id=" + getId() +
            ", tableName='" + getTableName() + "'" +
            ", recordId=" + getRecordId() +
            ", dto=" + getDto() +
            "}";
    }
}