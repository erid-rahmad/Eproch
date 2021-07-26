package com.bhp.opusb.workflow;

import java.util.List;
import java.util.stream.Collectors;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

public class WorkflowProcessService {

	
	protected final RuntimeService runtimeService;
	protected final TaskService taskService;
	
	public WorkflowProcessService(RuntimeService runtimeService, TaskService taskService) {
		this.runtimeService= runtimeService;
		this.taskService= taskService;
	}
	
	
	public List<Task> getAssigneTask(String assignee){
		
		return taskService.createTaskQuery()
				.active()
				.taskAssignee(assignee)
				.list();
	}
	
	public List<ProcessDefinitionDTO> getProcessDefinitionDTO(String assignee){
		return this.getAssigneTask(assignee)
			.stream()
			.map(t->this.mapTaskToProcessDefinitionDTO(t))
			.collect(Collectors.toList());
	}
	
	public void deleteProcessInstance(String processInstanceId) {
		runtimeService.deleteProcessInstance(processInstanceId, "RESETTING");
	}
	
	private ProcessDefinitionDTO mapTaskToProcessDefinitionDTO(Task task) {
		ProcessDefinitionDTO processDefinitionDTO = new ProcessDefinitionDTO();
		processDefinitionDTO.setId(task.getId());
		processDefinitionDTO.setTenantId(task.getTenantId());
		processDefinitionDTO.setKey(task.getTaskDefinitionKey());
		processDefinitionDTO.setResourceName(task.getName());
		processDefinitionDTO.setProcessInstanceId(task.getProcessInstanceId());
		return processDefinitionDTO;
	}
}
