package com.bhp.opusb.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.flowable.engine.RuntimeService;
import org.springframework.stereotype.Service;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

@Service
public class CVendorApprovalProcessService extends WorkflowProcessService{
	private final String PROCESS_ID= "vendorApproval";
	
	public CVendorApprovalProcessService(RuntimeService runtimeService, TaskService taskService) {
		super(runtimeService, taskService);
	}
	
	public void startService(Long cVendorId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vendorId", cVendorId);
		
		runtimeService.startProcessInstanceByKey(PROCESS_ID, params);
	}
	
	public void resumeApproval(Long vendorId, Map<String, Object> newParams) {
		
		List<Task> tasks = this.getAssigneTask(String.valueOf(vendorId), PROCESS_ID);
		tasks.forEach(t-> {
			Map<String, Object> params = t.getProcessVariables();
			
			if(!newParams.isEmpty() || !Objects.isNull(newParams)) {
				params= this.mergeMap(newParams, params);
			}
			
			this.taskService.claim(t.getId(), String.valueOf(vendorId));
			taskService.complete(t.getId(), params);
		});
	}
	
	public List<ProcessDefinitionDTO> getTaskList(String assigneId){
		return this.getAssigneTask(assigneId, PROCESS_ID)
				.stream().map(this::mapTaskToProcessDefinitionDTO)
				.collect(Collectors.toList());
	}
	
}
