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
                      String template =
                          " <br> Dear #vendorName\n" +
                          " <p> As you are aware, the #contractName between #AdminName and #vendorName  has been in effect since #ContractDate.\n" +
                          "  We would like to renew the agreement for another #startDate to #expiredDate.\n" +
                          "   Please contact us to confirm the renewal</p>" +
                          "    <br>Thank you.\n" +
                          "    <br>Regards,\n" +
                          "    <br>Berca.co.id";
                      template=template.replace("#vendorName",adUser.getCVendor().getName());
                      template=template.replace("#contractName",mContract.getName());
                      template=template.replace("#AdminName",mContract.getAdOrganization().getName());
                      LocalDate localDate = mContract.getDateTrx().toLocalDate();
                      template=template.replace("#ContractDate",localDate.toString());
                      template=template.replace("#startDate",mContract.getStartDate().toString());
                      template=template.replace("#expiredDate",mContract.getExpirationDate().toString());
                      mailService.sendEmail(adUser.getUser().getEmail(),
                          "Contract Remaining", template, false, true);
                  });
              }
          }catch (Exception e){}
      });
      return new ProcessResult();
  }
}
