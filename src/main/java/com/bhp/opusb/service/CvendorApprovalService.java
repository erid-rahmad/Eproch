package com.bhp.opusb.service;

import java.util.Optional;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.CVendorRepository;

@Service
public class CvendorApprovalService {
	
	
	private final CVendorRepository cVendorRepository;
	
	private final Logger log = LoggerFactory.getLogger(CvendorApprovalService.class);
	
	private final String DOCUMENT_STATUS_AVP="ABP";
	private final String DOCUMENT_STATUS_SMP="SMT";
	
	public CvendorApprovalService(CVendorRepository cVendorRepository) {
		this.cVendorRepository= cVendorRepository;
	}
	
	public String approved(DelegateExecution execution) throws Exception{
		log.debug("approval process " + DOCUMENT_STATUS_AVP);
		CVendor vendor = this.getById(this.getVendorIdFromExecution(execution));
		vendor.setApproved(true);
		cVendorRepository.save(vendor);
		return DOCUMENT_STATUS_AVP;
	}
	
	public String rejected(DelegateExecution execution) throws Exception{
		log.debug("rejected process " + DOCUMENT_STATUS_SMP);
		CVendor vendor = this.getById(this.getVendorIdFromExecution(execution));
		vendor.setApproved(false);
		cVendorRepository.save(vendor);
		return DOCUMENT_STATUS_SMP;
	}
	
	public void updateDocumentStatus(DelegateExecution execution) throws Exception {
		log.debug("update document status");
		CVendor vendor = this.getById(this.getVendorIdFromExecution(execution));
		vendor.setDocumentStatus(this.getDocumentStatusFromExecution(execution));
		
		cVendorRepository.save(vendor);
	}
	
	private Long getVendorIdFromExecution(DelegateExecution execution) {
		return (Long) execution.getVariable("vendorId");
	}
	
	private String getDocumentStatusFromExecution(DelegateExecution execution) {
		return (String) execution.getVariable("documentStatus");
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
