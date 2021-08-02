package com.bhp.opusb.service;

import java.util.Optional;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.enumeration.VendorDocumentType;
import com.bhp.opusb.repository.CVendorRepository;

@Service
public class CvendorApprovalService {
	
	
	private final CVendorRepository cVendorRepository;
	private final UserService userService;
	
	private final Logger log = LoggerFactory.getLogger(CvendorApprovalService.class);
	
	public CvendorApprovalService(CVendorRepository cVendorRepository, UserService userService) {
		this.cVendorRepository= cVendorRepository;
		this.userService= userService;
	}
	
	public String approved(DelegateExecution execution) throws Exception{
		log.debug("approval process " + VendorDocumentType.APPROVED_VENDOR.getValue());
		CVendor vendor = getVendor(execution);
		vendor.setApproved(true);
		cVendorRepository.save(vendor);
		return VendorDocumentType.APPROVED_VENDOR.getValue();
	}
	
	public String rejected(DelegateExecution execution) throws Exception{
		log.debug("rejected process " + VendorDocumentType.REJECTED.getValue());
		CVendor vendor = getVendor(execution);
		vendor.setApproved(false);
		cVendorRepository.save(vendor);
		return VendorDocumentType.REJECTED.getValue();
	}
	
	public void updateDocumentStatus(DelegateExecution execution) throws Exception {
		log.debug("update document status");
		CVendor vendor = getVendor(execution);
		vendor.setDocumentStatus(this.getDocumentStatusFromExecution(execution));
		
		cVendorRepository.save(vendor);
	}
	
	public void sendEmail(DelegateExecution execution) throws Exception{
		log.debug("sending emails");
		CVendor vendor = getVendor(execution);
		userService.sendActivationEmail(vendor);
	}
	
	private Long getVendorIdFromExecution(DelegateExecution execution) {
		return (Long) execution.getVariable("vendorId");
	}
	
	private String getDocumentStatusFromExecution(DelegateExecution execution) {
		return (String) execution.getVariable("documentStatus");
	}
	
	private CVendor getVendor(DelegateExecution execution) throws Exception{
		return this.getById(this.getVendorIdFromExecution(execution));
	}
	
	private CVendor getById(Long id) throws Exception{
		Optional<CVendor>vendor = cVendorRepository.findById(id);
		if(vendor.isPresent()) {
			return vendor.get();
		}else {
			throw new Exception(String.format("vendor with id %s cannot be found ! ", String.valueOf(id)));
		}
	}

}
