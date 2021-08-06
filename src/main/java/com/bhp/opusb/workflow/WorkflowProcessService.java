package com.bhp.opusb.workflow;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

@Service
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
	
	
	public List<Task> getAssigneTask(String assignee, String processKey){
		return taskService.createTaskQuery()
				.active()
				.taskAssignee(assignee)
				.processDefinitionKey(processKey)
				.list();
	}
	
	public void deleteProcessInstance(String processInstanceId) {
		runtimeService.deleteProcessInstance(processInstanceId, "RESETTING");
	}
	
	public void resumeTask(String assigne, String taskId, Map<String, Object> additionalParams) throws Exception{

		Optional<Task> oTask = Optional.fromNullable(this.getTaskById(taskId));
		if(oTask.isPresent()) {
			Task task = oTask.get();
			taskService.claim(taskId, assigne);
			taskService.complete(task.getId(), this.mergeMap(additionalParams, task.getProcessVariables()));
		}else {
			throw new Exception(String.format("task with task id %s is not exists" , taskId));
		}
		
	}
	
	protected Task getTaskById(String taskId) {
		return this.taskService.createTaskQuery().taskId(taskId).active().singleResult();
	}
	
	protected ProcessDefinitionDTO mapTaskToProcessDefinitionDTO(Task task) {
		ProcessDefinitionDTO processDefinitionDTO = new ProcessDefinitionDTO();
		processDefinitionDTO.setId(task.getId());
		processDefinitionDTO.setTenantId(task.getTenantId());
		processDefinitionDTO.setKey(task.getTaskDefinitionKey());
		processDefinitionDTO.setResourceName(task.getName());
		processDefinitionDTO.setProcessInstanceId(task.getProcessInstanceId());
		return processDefinitionDTO;
	}
	
	protected Map<String, Object> mergeMap(Map<String, Object> source, Map<String, Object> destination){
		source.entrySet().forEach(n-> {
			destination.put(n.getKey(), n.getValue());
		});
		
		return destination;
	}
}
