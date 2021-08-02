package com.bhp.opusb.service.trigger.process;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.MContractRepository;
import com.bhp.opusb.repository.MPurchaseOrderRepository;
import com.bhp.opusb.service.MailService;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service("mContractTaskRemaining")
@Transactional
public class MContractTaskRemaining implements ProcessTrigger {

  private final Logger log = LoggerFactory.getLogger(MContractTaskRemaining.class);

  private final MPurchaseOrderRepository mPurchaseOrderRepository;
    private final MailService mailService;
    private final MContractRepository mContractRepository;

    private final AdUserRepository adUserRepository;


    MContractTaskRemaining(MPurchaseOrderRepository mPurchaseOrderRepository, MailService mailService, MContractRepository mContractRepository, AdUserRepository adUserRepository) {
    this.mPurchaseOrderRepository = mPurchaseOrderRepository;
        this.mailService = mailService;
        this.mContractRepository = mContractRepository;
        this.adUserRepository = adUserRepository;
    }

  @Override
  public TriggerResult run(Map<String, Object> params) {
      log.info("this scheduler On");
      List<MContract> mContractTaskDTOS =mContractRepository.findAll();
      mContractTaskDTOS.forEach(mContract -> {
          try {
              LocalDate lt = LocalDate.now();
              LocalDate lt_=mContract.getExpirationDate();
              long daysBetween = ChronoUnit.DAYS.between(lt, lt_);
              int x= (int) (daysBetween%mContract.getReminderSent());
              if (daysBetween <= mContract.getEmailNotification() && daysBetween >=0 && x==0 ){
                  List<AdUser> adUsers= adUserRepository.findBycVendor(mContract.getVendor());
                  adUsers.forEach(adUser -> {
                      mailService.sendEmail(adUser.getUser().getEmail(),
                          "REMAINING", "REMAINING ABOUT CONTRACT ", false, true);
                  });
              }
          }catch (Exception e){}
      });
      return new ProcessResult();
  }
}
