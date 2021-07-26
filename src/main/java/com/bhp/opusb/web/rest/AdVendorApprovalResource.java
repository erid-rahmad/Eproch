package com.bhp.opusb.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhp.opusb.service.dto.VendorApprovalDTO;
import com.bhp.opusb.workflow.CVendorApprovalProcessService;
import com.bhp.opusb.workflow.ProcessDefinitionDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.flowable.task.api.Task;

@RestController
@RequestMapping(value="api/c-vendors/approval")
public class AdVendorApprovalResource {
	
	private final CVendorApprovalProcessService cVendorApprovalProcessService;
	
	public AdVendorApprovalResource(CVendorApprovalProcessService cVendorApprovalProcessService) {
		this.cVendorApprovalProcessService= cVendorApprovalProcessService;
	}
	
	@GetMapping(value="/tasks")
	public ResponseEntity<List<ProcessDefinitionDTO>> getVendorApprovalTask(@RequestParam Long vendorId){
		
		List<ProcessDefinitionDTO> response = cVendorApprovalProcessService.getProcessDefinitionDTO(String.valueOf(vendorId));
		
//		return ResponseEntity.ok(response);
		return new ResponseEntity<List<ProcessDefinitionDTO>>(response, HttpStatus.OK);
				
	}
	
	@PatchMapping(value="/")
	public void resumeApproval(@RequestBody VendorApprovalDTO vendorApprovalDTO) {
		cVendorApprovalProcessService
			.resumeApproval(vendorApprovalDTO.getVendorId(), vendorApprovalDTO.getApprove());
	}
	
	@PostMapping(value="/delete-process")
	public void deleteProcess(@RequestParam String processInstanceId) {
		cVendorApprovalProcessService.deleteProcessInstance(processInstanceId);
	}
	
	@PostMapping(value="/manual-start")
	public void manualStart(@RequestParam Long vendorId) {
		cVendorApprovalProcessService.startService(vendorId);
	}
	
}

