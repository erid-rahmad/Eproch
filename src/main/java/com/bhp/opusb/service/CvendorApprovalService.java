package com.bhp.opusb.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.enumeration.VendorDocumentType;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.service.dto.CVendorDTO;
import com.bhp.opusb.service.mapper.CVendorMapper;
import com.bhp.opusb.service.trigger.process.integration.CVendorMessageDispatcher;

@Service
public class CvendorApprovalService {
	
	
	private final CVendorRepository cVendorRepository;
	private final CVendorMapper cVendorMapper;
	private final AdUserRepository adUserRepository;
	private final UserService userService;
	private final MailService mailService;
	private final AiMessageDispatcher messageDispatcher;
	
	private final Logger log = LoggerFactory.getLogger(CvendorApprovalService.class);
	
	public CvendorApprovalService(
		CVendorRepository cVendorRepository, CVendorMapper cVendorMapper,
		UserService userService, AiMessageDispatcher messageDispatcher,
		MailService mailService,
		AdUserRepository adUserRepository) {
		this.cVendorRepository= cVendorRepository;
		this.userService= userService;
		this.mailService= mailService;
		this.adUserRepository= adUserRepository;
		this.cVendorMapper = cVendorMapper;
		this.messageDispatcher = messageDispatcher;
	}
	
	public String approved(DelegateExecution execution) throws Exception{
		log.debug("approval process " + VendorDocumentType.APPROVED_VENDOR.getValue());
		CVendor vendor = getVendor(execution);
		vendor.setApproved(true);
		cVendorRepository.save(vendor);

		final Map<String, Object> headerPayload = new HashMap<>(1);
		CVendorDTO cVendorDTO = cVendorMapper.toDto(vendor);

        headerPayload.put(CVendorMessageDispatcher.KEY_PAYLOAD, cVendorDTO);
        messageDispatcher.dispatch(CVendorMessageDispatcher.BEAN_NAME, headerPayload);

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
		if((Boolean) execution.getVariable("approve")){
			sendActivationEmail(vendor);
		}else{

		}
	}

	public void approveEmail(DelegateExecution execution) throws Exception{
		CVendor vendor = getVendor(execution);
		sendActivationEmail(vendor);
		
	}

	public void rejectEmail(DelegateExecution execution) throws Exception{
		CVendor vendor= getVendor(execution);
		sendRejectionEmail(vendor);
		
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

	private void sendActivationEmail(CVendor vendor){
		userService.sendActivationEmail(vendor);
	}

	private void sendRejectionEmail(CVendor vendor){
		Map<String, Object> contextVariables= new HashMap<String, Object>();
		contextVariables.put("vendor", vendor);

		adUserRepository.findBycVendorId(vendor.getId())
			.stream()
			.map(u -> u.getUser())
			.forEach(u -> {
				mailService.sendEmailFromTemplate(u, "mail/vendorRejectionNotificationEmail", 
						"email.verification.approval.reject.title", 
						contextVariables);
			});
	}

}
