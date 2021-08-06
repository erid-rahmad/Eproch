package com.bhp.opusb.service.trigger.process.integration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.AiExchangeOut;
import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.enumeration.AiStatus;
import com.bhp.opusb.repository.AiExchangeOutRepository;
import com.bhp.opusb.service.MPurchaseOrderLineService;
import com.bhp.opusb.service.MPurchaseOrderService;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.mapper.MPurchaseOrderLineMapper;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@Service("mPurchaseOrderMessageDispatcher")
public class MPurchaseOrderMessageDispatcher implements ProcessTrigger {

  private final Logger logger = LoggerFactory.getLogger(MPurchaseOrderMessageDispatcher.class);

  public static final String BEAN_NAME = "mPurchaseOrderMessageDispatcher";
  public static final String KEY_ID = "id";

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper;
  private final ApplicationProperties properties;
  private final MPurchaseOrderService mPurchaseOrderService;
  private final MPurchaseOrderLineService mPurchaseOrderLineService;
  private final MPurchaseOrderLineMapper mPurchaseOrderLineMapper;
  private final AiExchangeOutRepository aiExchangeOutRepository;

  public MPurchaseOrderMessageDispatcher(ObjectMapper objectMapper, ApplicationProperties properties,
      MPurchaseOrderLineService mPurchaseOrderLineService, AiExchangeOutRepository aiExchangeOutRepository,
      MPurchaseOrderLineMapper mPurchaseOrderLineMapper, MPurchaseOrderService mPurchaseOrderService
      ) {
    this.objectMapper = objectMapper;
    this.properties = properties;
    this.mPurchaseOrderService = mPurchaseOrderService;
    this.mPurchaseOrderLineService = mPurchaseOrderLineService;
    this.mPurchaseOrderLineMapper = mPurchaseOrderLineMapper;
    this.aiExchangeOutRepository = aiExchangeOutRepository;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    final Long id = (Long) params.get(KEY_ID);
    boolean success = true;
    String response = null;

    if (id != null) {
      MPurchaseOrderDTO dto = mPurchaseOrderService.findOne(id).get();
      dto.setPoLines(mPurchaseOrderLineMapper.toDto(mPurchaseOrderLineService.mPOLineList(id)));
      
      for(MPurchaseOrderLineDTO poldto: dto.getPoLines()){
        String message = null;
        try {
          MPurchaseOrderIntegrationDTO poidto = new MPurchaseOrderIntegrationDTO();
          poidto.setAdOrganizationCode(dto.getAdOrganizationCode());
          poidto.setCostCenterCode(dto.getCostCenterCode());
          poidto.setCurrencyCode(dto.getCurrencyName());
          poidto.setDateDelivered(dto.getDateDelivered());
          poidto.setDatePromised(dto.getDatePromised());
          poidto.setDateShipped(dto.getDateShipped());
          poidto.setDateTrx(LocalDate.now());
          poidto.setDescription(dto.getDescription());
          poidto.setDocumentNo(dto.getDocumentNo());
          poidto.setOrderAmount(poldto.getOrderAmount());
          poidto.setPaymentTermCode(dto.getPaymentTermCode());
          poidto.setProductCode(poldto.getProductCode());
          poidto.setProductName(poldto.getProductName());
          poidto.setQuantity(poldto.getQuantity());
          poidto.setRemark(poldto.getRemark());
          poidto.setRequisitionDocumentNo(poldto.getRequisitionNo());
          poidto.setTaxCode(poldto.getTaxCode());
          poidto.setUnitPrice(poldto.getUnitPrice());
          poidto.setUomCode(poldto.getUomName());
          poidto.setVendorCode(dto.getVendorCode());
          poidto.setWarehouseCode(dto.getWarehouseCode());

          message = objectMapper.writeValueAsString(poidto);
          System.out.println(message);
  
        } catch (JsonProcessingException | RestClientException e) {
          logger.warn("Failed dispatching PO data. {}. {}", e.getLocalizedMessage(), message);
  
          aiExchangeOutRepository.save(new AiExchangeOut()
            .entityType(MPurchaseOrder.class.getName())
            .entityId(id)
            .payload(message)
            .status(AiStatus.ERROR));
  
          success = false;
        }
      }
      
      return new ProcessResult().add("success", success).add("response", success ? response : id);
    }

    return new ProcessResult().add("error", true).add("message", "There is no payload specified");
  }


  private String dispatchMessage(String message) throws RestClientException {
    final String url = properties.getIntegration().getEndpoint().getVendorIntegrationUrl();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ai-exchange-out", MPurchaseOrder.class.getSimpleName());

    logger.debug("Dispatching message to external system. body: {}", message);
    HttpEntity<String> request = new HttpEntity<>(message, headers);
    final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    logger.debug("Message dispatched. statusCode: {} - {}", response.getStatusCodeValue(), response.getStatusCode());
    return response.getBody();
  }

  public static class MPurchaseOrderIntegrationDTO {
    @JsonProperty("AD_ORG")
    private String adOrganizationCode;
    @JsonProperty("CODE_COMBINATION_ID")
    private String costCenterCode;
    @JsonProperty("CURRENCY_CODE")
    private String currencyCode;
    @JsonProperty("CREATION_DATE")
    private LocalDate dateTrx;
    @JsonProperty("PROMISED_DATE")
    private LocalDate datePromised;

    private LocalDate dateDelivered;
    private LocalDate dateShipped;
    private String description;

    @JsonProperty("SEGMENT1")
    private String documentNo;
    @JsonProperty("TERMS_ID")
    private String paymentTermCode;

    private String taxCode;

    @JsonProperty("VENDOR_ID")
    private String vendorCode;
    @JsonProperty("DESTINATION_ORGANIZATION_ID")
    private String warehouseCode;
    @JsonProperty("PO_HEADER_ID")
    private String poDocumentNo;

    private BigDecimal orderAmount;

    @JsonProperty("ITEM_ID")
    private String productCode;
    @JsonProperty("ITEM_DESCRIPTION")
    private String productName;
    @JsonProperty("QUANTITY")
    private BigDecimal quantity;
    @JsonProperty("UNIT_PRICE")
    private BigDecimal unitPrice;
    
    private String remark;

    @JsonProperty("REQ_DISTRIBUTION_ID")
    private String requisitionDocumentNo;
    @JsonProperty("UNIT_MEAS_LOOKUP_CODE")
    private String uomCode;

    public String getAdOrganizationCode() {
      return adOrganizationCode;
    }
    public BigDecimal getUnitPrice() {
      return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
    }
    public String getUomCode() {
      return uomCode;
    }
    public void setUomCode(String uomCode) {
      this.uomCode = uomCode;
    }
    public String getRequisitionDocumentNo() {
      return requisitionDocumentNo;
    }
    public void setRequisitionDocumentNo(String requisitionDocumentNo) {
      this.requisitionDocumentNo = requisitionDocumentNo;
    }
    public String getRemark() {
      return remark;
    }
    public void setRemark(String remark) {
      this.remark = remark;
    }
    public BigDecimal getQuantity() {
      return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
      this.quantity = quantity;
    }
    public String getProductName() {
      return productName;
    }
    public void setProductName(String productName) {
      this.productName = productName;
    }
    public String getProductCode() {
      return productCode;
    }
    public void setProductCode(String productCode) {
      this.productCode = productCode;
    }
    public BigDecimal getOrderAmount() {
      return orderAmount;
    }
    public void setOrderAmount(BigDecimal orderAmount) {
      this.orderAmount = orderAmount;
    }
    public String getPoDocumentNo() {
      return poDocumentNo;
    }
    public void setPoDocumentNo(String poDocumentNo) {
      this.poDocumentNo = poDocumentNo;
    }
    public String getWarehouseCode() {
      return warehouseCode;
    }
    public void setWarehouseCode(String warehouseCode) {
      this.warehouseCode = warehouseCode;
    }
    public String getVendorCode() {
      return vendorCode;
    }
    public void setVendorCode(String vendorCode) {
      this.vendorCode = vendorCode;
    }
    public String getTaxCode() {
      return taxCode;
    }
    public void setTaxCode(String taxCode) {
      this.taxCode = taxCode;
    }
    public String getPaymentTermCode() {
      return paymentTermCode;
    }
    public void setPaymentTermCode(String paymentTermCode) {
      this.paymentTermCode = paymentTermCode;
    }
    public String getDocumentNo() {
      return documentNo;
    }
    public void setDocumentNo(String documentNo) {
      this.documentNo = documentNo;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public LocalDate getDateShipped() {
      return dateShipped;
    }
    public void setDateShipped(LocalDate dateShipped) {
      this.dateShipped = dateShipped;
    }
    public LocalDate getDateDelivered() {
      return dateDelivered;
    }
    public void setDateDelivered(LocalDate dateDelivered) {
      this.dateDelivered = dateDelivered;
    }
    public LocalDate getDatePromised() {
      return datePromised;
    }
    public void setDatePromised(LocalDate datePromised) {
      this.datePromised = datePromised;
    }
    public LocalDate getDateTrx() {
      return dateTrx;
    }
    public void setDateTrx(LocalDate dateTrx) {
      this.dateTrx = dateTrx;
    }
    public String getCurrencyCode() {
      return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
    }
    public String getCostCenterCode() {
      return costCenterCode;
    }
    public void setCostCenterCode(String costCenterCode) {
      this.costCenterCode = costCenterCode;
    }
    public void setAdOrganizationCode(String adOrganizationCode) {
      this.adOrganizationCode = adOrganizationCode;
    }
  }
}
