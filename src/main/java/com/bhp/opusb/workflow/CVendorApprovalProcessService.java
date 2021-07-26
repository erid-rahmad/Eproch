package com.bhp.opusb.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void resumeApproval(Long vendorId, Boolean approve) {
		
		List<Task> tasks = this.getAssigneTask(String.valueOf(vendorId));
		tasks.forEach(t-> {
			Map<String, Object> params = t.getProcessVariables();
			params.put("approve", approve);
			
			this.taskService.claim(t.getId(), String.valueOf(vendorId));
			taskService.complete(t.getId(), params);
		});
	}
	
}
