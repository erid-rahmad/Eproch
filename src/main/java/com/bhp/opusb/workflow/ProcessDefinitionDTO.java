package com.bhp.opusb.workflow;

public class ProcessDefinitionDTO {
    private String id, category, name, key, description, resourceName, 
        deploymentId, dgrmResourceName, tenantId, derivedFrom, derivedFromRoot,
        processInstanceId;
    private Integer version, derivedVersion, suspensionState;
    private Boolean hasStartFormKey, hasGraphicalNotation;

    public String getId(){
        return id;
    }
    public Integer getSuspensionState() {
        return suspensionState;
    }
    public void setSuspensionState(Integer suspensionState) {
        this.suspensionState = suspensionState;
    }
    public Boolean getHasGraphicalNotation() {
        return hasGraphicalNotation;
    }
    public void setHasGraphicalNotation(Boolean hasGraphicalNotation) {
        this.hasGraphicalNotation = hasGraphicalNotation;
    }
    public Boolean getHasStartFormKey() {
        return hasStartFormKey;
    }
    public void setHasStartFormKey(Boolean hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }
    public Integer getDerivedVersion() {
        return derivedVersion;
    }
    public void setDerivedVersion(Integer derivedVersion) {
        this.derivedVersion = derivedVersion;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getDerivedFromRoot() {
        return derivedFromRoot;
    }
    public void setDerivedFromRoot(String derivedFromRoot) {
        this.derivedFromRoot = derivedFromRoot;
    }
    public String getDerivedFrom() {
        return derivedFrom;
    }
    public void setDerivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
    }
    public String getTenantId() {
        return tenantId;
    }
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    public String getDgrmResourceName() {
        return dgrmResourceName;
    }
    public void setDgrmResourceName(String dgrmResourceName) {
        this.dgrmResourceName = dgrmResourceName;
    }
    public String getDeploymentId() {
        return deploymentId;
    }
    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
    public String getResourceName() {
        return resourceName;
    }
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
    
}