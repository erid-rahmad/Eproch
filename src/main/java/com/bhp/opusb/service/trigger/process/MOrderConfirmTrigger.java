package com.bhp.opusb.service.trigger.process;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;

import com.bhp.opusb.repository.MPurchaseOrderRepository;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.bhp.opusb.util.NumberUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mOrderConfirmTrigger")
@Transactional
public class MOrderConfirmTrigger implements ProcessTrigger {

  private final Logger log = LoggerFactory.getLogger(MOrderConfirmTrigger.class);

  private final MPurchaseOrderRepository mPurchaseOrderRepository;

  MOrderConfirmTrigger(MPurchaseOrderRepository mPurchaseOrderRepository) {
    this.mPurchaseOrderRepository = mPurchaseOrderRepository;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    String documentNo = (String) params.get("documentNo");
    String description = (String) params.get("description");
    String confirmation = (String) params.get("confirmation");
    LocalDate deliveryDate = ZonedDateTime.parse((String) params.get("deliveryDate")).plusHours(7).toLocalDate();
    LocalDate shippingDate = ZonedDateTime.parse((String) params.get("shipDate")).plusHours(7).toLocalDate();
    log.debug("Request to confirm Order {}", documentNo);

    mPurchaseOrderRepository.findByDocumentNo(documentNo).forEach(order->{
      order.setConfirmed(true);
      order.setDescription(description);
      order.setDateDelivered(deliveryDate);
      order.setDateShipped(shippingDate);
      order.setConfirmation(confirmation);
      mPurchaseOrderRepository.save(order);
    });

    return new ProcessResult();
  }
}
