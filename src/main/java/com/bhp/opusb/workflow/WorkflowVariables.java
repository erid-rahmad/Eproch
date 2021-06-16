package com.bhp.opusb.workflow;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.delegate.DelegateExecution;

public class WorkflowVariables {

    public static final String TABLE_NAME = "tableName";
    public static final String ID = "id";
    public static final String DOC = "doc";
    public static final String APPROVED = "approved";

    private String tableName;
    private Long id;
    private Object dto;
    private Boolean approved;

    public WorkflowVariables(String tableName, Long id, Object dto) {
        this.tableName = tableName;
        this.id = id;
        this.dto = dto;
        this.approved = false;
    }

    public WorkflowVariables(DelegateExecution execution) {
        this.tableName = execution.getVariable(WorkflowVariables.TABLE_NAME).toString();
        this.id = (Long) execution.getVariable(WorkflowVariables.ID);
        this.dto = execution.getVariable(WorkflowVariables.DOC);
        this.approved = (Boolean) execution.getVariable(WorkflowVariables.APPROVED);
    }

    public WorkflowVariables(Map<String, Object> variables) {
        this.tableName = variables.get(WorkflowVariables.TABLE_NAME).toString();
        this.id = (Long) variables.get(WorkflowVariables.ID);
        this.dto = variables.get(WorkflowVariables.DOC);
        this.approved = (Boolean) variables.get(WorkflowVariables.APPROVED);
    }
    
    public String getTableName() {
        return tableName;
    }

    public Long getId() {
        return id;
    }

    public Object getDto() {
        return dto;
    }

    public Boolean isApproved() {
        return approved;
    }

    public Map<String, Object> createMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(WorkflowVariables.DOC, getDto());
        map.put(WorkflowVariables.TABLE_NAME, getTableName());
        map.put(WorkflowVariables.ID, getId());
        map.put(WorkflowVariables.APPROVED, isApproved());
        return map;
    }
}