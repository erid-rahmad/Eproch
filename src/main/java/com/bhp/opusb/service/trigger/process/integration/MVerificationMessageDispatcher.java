package com.bhp.opusb.service.trigger.process.integration;

import java.math.BigDecimal;
import java.util.Map;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.AiExchangeOut;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.enumeration.AiStatus;
import com.bhp.opusb.repository.AiExchangeOutRepository;
import com.bhp.opusb.repository.CVendorLocationRepository;
import com.bhp.opusb.service.dto.CVendorLocationDTO;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.dto.MVerificationLineDTO;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.mapper.CVendorLocationMapper;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service("mVerificationMessageDispatcher")
public class MVerificationMessageDispatcher implements ProcessTrigger {

  private final Logger logger = LoggerFactory.getLogger(MVerificationMessageDispatcher.class);

  public static final String BEAN_NAME = "mVerificationMessageDispatcher";
  public static final String KEY_PAYLOAD = "payload";
  public static final String CONTEXT_HEADER = "invoice-verification-header";
  public static final String CONTEXT_LINES = "invoice-verification-lines";

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper;
  private final ApplicationProperties properties;
  private final CVendorLocationRepository cVendorLocationRepository;
  private final AiExchangeOutRepository aiExchangeOutRepository;
  private final CVendorLocationMapper cVendorLocationMapper;

  public MVerificationMessageDispatcher(ObjectMapper objectMapper, ApplicationProperties properties,
      CVendorLocationRepository cVendorLocationRepository, AiExchangeOutRepository aiExchangeOutRepository,
      CVendorLocationMapper cVendorLocationMapper) {
    this.objectMapper = objectMapper;
    this.properties = properties;
    this.cVendorLocationRepository = cVendorLocationRepository;
    this.aiExchangeOutRepository = aiExchangeOutRepository;
    this.cVendorLocationMapper = cVendorLocationMapper;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    final MVerificationDTO payload = (MVerificationDTO) params.get(KEY_PAYLOAD);
    boolean success = true;
    String response = null;

    if (payload != null) {
      String message = null;

      try {
        enrichMessage(payload);
        message = objectMapper.writeValueAsString(payload);
        response = dispatchMessage(message);
      } catch (JsonProcessingException | RestClientException e) {
        logger.warn("Failed dispatching invoice verification. {}. {}", e.getLocalizedMessage(), payload);

        aiExchangeOutRepository.save(new AiExchangeOut()
          .entityType(MVerification.class.getName())
          .entityId(payload.getId())
          .payload(message)
          .status(AiStatus.ERROR));

        success = false;
      }

      return new ProcessResult().add("success", success).add("response", success ? response : payload);
    }

    return new ProcessResult().add("error", true).add("message", "There is no payload specified");
  }

  private void enrichMessage(MVerificationDTO payload) {
      final String description = payload.getDescription();

      payload.setTotalLines(multiply(payload.getTotalLines(), 100));
      payload.setGrandTotal(multiply(payload.getGrandTotal(), 100));
      payload.setTaxAmount(multiply(payload.getTaxAmount(), 100));
      payload.setForeignGrandTotal(multiply(payload.getForeignGrandTotal(), 100));
      payload.setForeignTaxAmount(multiply(payload.getForeignTaxAmount(), 100));

      if (description != null) {
        payload.setDescription(description.length() > 30 ? description.substring(0, 29) : description);
      }
      
      cVendorLocationRepository.findFirstByInvoiceAddressTrueAndVendor_Id(payload.getVendorId())
        .ifPresent(vendorLocation -> {
          CVendorLocationDTO vendorLocationDto = cVendorLocationMapper.toDto(vendorLocation);
          payload.setVendorLocation(vendorLocationDto);
        });

      for (MVerificationLineDTO line : payload.getLines()) {
        line.setLineNo(line.getLineNo() * 1000);
        line.setLineNoPo(line.getLineNoPo() * 1000);
        line.setPriceActual(multiply(line.getPriceActual(), 10000));
        line.setTotalLines(multiply(line.getTotalLines(), 100));
        line.setTaxAmount(multiply(line.getTaxAmount(), 100));
        line.setForeignActual(multiply(line.getForeignActual(), 10000));
        line.setForeignTotalLines(multiply(line.getForeignTotalLines(), 100));
        line.setForeignTaxAmount(multiply(line.getForeignTaxAmount(), 100));
      }
  }

  private BigDecimal multiply(BigDecimal source, int multiplier) {
    if (source == null) {
      return new BigDecimal(0);
    }

    return source.multiply(new BigDecimal(multiplier));
  }

  private String dispatchMessage(String message) throws RestClientException {
    final String url = properties.getIntegration().getEndpoint().getInvoiceVerificationUrl();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ai-exchange-out", MVerification.class.getSimpleName());

    logger.debug("Dispatching message to external system. body: {}", message);
    HttpEntity<String> request = new HttpEntity<>(message, headers);
    final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    logger.debug("Message dispatched. statusCode: {} - {}", response.getStatusCodeValue(), response.getStatusCode());
    return response.getBody();
  }

}
