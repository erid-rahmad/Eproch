package com.bhp.opusb.service.trigger.process;

import java.util.HashMap;
import java.util.Map;

import com.bhp.opusb.service.AdUserService;
import com.bhp.opusb.service.MailService;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.bhp.opusb.util.NumberUtil;

import org.springframework.stereotype.Service;

@Service
public class RegistrationInvitationCodeProcessTrigger implements ProcessTrigger {

  private final AdUserService adUserService;
  private final MailService mailService;

  public RegistrationInvitationCodeProcessTrigger(AdUserService adUserService, MailService mailService) {
    this.adUserService = adUserService;
    this.mailService = mailService;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    Long vendorId = NumberUtil.getLongValue(params.get("vendorId"));
    adUserService.findByActiveVendorId(vendorId).forEach(adUser -> {
      Map<String, Object> contextVariables = new HashMap<>();
      contextVariables.put("vendorName", adUser.getCVendor().getName());

      // TODO Generate the code and store to the database.
      contextVariables.put("code", "KfFjbi5X");
      mailService.sendInvitationCodeEmail(adUser.getUser(), contextVariables);
    });
    return new ProcessResult();
  }
  
}
