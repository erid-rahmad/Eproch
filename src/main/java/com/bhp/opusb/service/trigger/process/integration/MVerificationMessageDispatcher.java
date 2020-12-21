package com.bhp.opusb.service.trigger.process.integration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bhp.opusb.config.ApplicationProperties;
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
import org.springframework.web.client.RestTemplate;

@Service("mVerificationMessageDispatcher")
public class MVerificationMessageDispatcher implements ProcessTrigger {

  private static final Logger logger = LoggerFactory.getLogger(MVerificationMessageDispatcher.class);

  public static final String KEY_CONTEXT = "context";
  public static final String KEY_PAYLOAD = "payload";
  public static final String CONTEXT_HEADER = "invoice-verification-header";
  public static final String CONTEXT_LINES = "invoice-verification-lines";

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper;
  private final ApplicationProperties properties;
  private final CVendorLocationRepository cVendorLocationRepository;
  private final CVendorLocationMapper cVendorLocationMapper;

  public MVerificationMessageDispatcher(ObjectMapper objectMapper, ApplicationProperties properties,
      CVendorLocationRepository cVendorLocationRepository, CVendorLocationMapper cVendorLocationMapper) {
    this.objectMapper = objectMapper;
    this.properties = properties;
    this.cVendorLocationRepository = cVendorLocationRepository;
    this.cVendorLocationMapper = cVendorLocationMapper;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    final Object payload = params.get(KEY_PAYLOAD);
    final String context = (String) params.get(KEY_CONTEXT);
    boolean success = true;
    String response = null;

    if (context != null && payload != null) {
      String message = null;

      try {
        enrichMessage(payload, context);
        message = objectMapper.writeValueAsString(params);
        response = dispatchMessage(message, context);
      } catch (JsonProcessingException e) {
        logger.error("Failed dispatching invoice verification. {}. {}", e.getLocalizedMessage(), payload);
        success = false;
      }

      return new ProcessResult().add("success", success).add("response", success ? response : payload);
    }

    return new ProcessResult().add("error", true).add("message",
        "There is no context or payload in the given params. " + params);
  }

  private void enrichMessage(Object payload, String context) {
    if (context.equals(CONTEXT_HEADER)) {
      final MVerificationDTO header = (MVerificationDTO) payload;
      final String description = header.getDescription();

      header.setTotalLines(multiply(header.getTotalLines(), 100));
      header.setGrandTotal(multiply(header.getGrandTotal(), 100));
      header.setTaxAmount(multiply(header.getTaxAmount(), 100));
      header.setForeignGrandTotal(multiply(header.getForeignGrandTotal(), 100));
      header.setForeignTaxAmount(multiply(header.getForeignTaxAmount(), 100));

      if (description != null) {
        header.setDescription(description.length() > 30 ? description.substring(0, 29) : description);
      }
      
      cVendorLocationRepository.findFirstByInvoiceAddressTrueAndVendor_Id(header.getVendorId())
        .ifPresent(vendorLocation -> {
          CVendorLocationDTO vendorLocationDto = cVendorLocationMapper.toDto(vendorLocation);
          header.setVendorLocation(vendorLocationDto);
        });
    } else if (context.equals(CONTEXT_LINES)) {
      final List<?> lines = (List<?>) payload;
      
      for (Object line : lines) {
        MVerificationLineDTO lineDto = (MVerificationLineDTO) line;
        lineDto.setLineNo(lineDto.getLineNo() * 1000);
        lineDto.setLineNoPo(lineDto.getLineNoPo() * 1000);
        lineDto.setPriceActual(multiply(lineDto.getPriceActual(), 10000));
        lineDto.setTotalLines(multiply(lineDto.getTotalLines(), 100));
        lineDto.setTaxAmount(multiply(lineDto.getTaxAmount(), 100));
        lineDto.setForeignActual(multiply(lineDto.getForeignActual(), 10000));
        lineDto.setForeignTotalLines(multiply(lineDto.getForeignTotalLines(), 100));
        lineDto.setForeignTaxAmount(multiply(lineDto.getForeignTaxAmount(), 100));
      }
    }
  }

  private BigDecimal multiply(BigDecimal source, int multiplier) {
    if (source == null) {
      return new BigDecimal(0);
    }

    return source.multiply(new BigDecimal(multiplier));
  }

  private String dispatchMessage(String message, String context) {
    final String url = properties.getIntegration().getEndpoint().getInvoiceVerificationUrl();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ai-exchange-out", context);

    logger.debug("Dispatching context#{} to the external system. payload: {}", context, message);
    HttpEntity<String> request = new HttpEntity<>(message, headers);
    final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    logger.debug("Message dispatched. statusCode: {} - {}", response.getStatusCodeValue(), response.getStatusCode());
    return response.getBody();
  }

}
